/*******************************************************************************
 * Copyright (c) 2012 Stephan Brosinski
 *  
 * All rights reserved. 
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Stephan Brosinski - initial API and implementation
 ******************************************************************************/
package com.brosinski.eclipse.regex.view;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.part.ViewPart;

import com.brosinski.eclipse.regex.Group;
import com.brosinski.eclipse.regex.IRegExListener;
import com.brosinski.eclipse.regex.Match;
import com.brosinski.eclipse.regex.Matches;
import com.brosinski.eclipse.regex.RegExModel;
import com.brosinski.eclipse.regex.RegExPlugin;
import com.brosinski.eclipse.regex.ReplaceResult;
import com.brosinski.eclipse.regex.view.actions.AboutAction;
import com.brosinski.eclipse.regex.view.actions.ClearAction;
import com.brosinski.eclipse.regex.view.actions.NextMatchAction;
import com.brosinski.eclipse.regex.view.actions.PrevMatchAction;
import com.brosinski.eclipse.regex.view.actions.StandardTextFieldAction;
import com.brosinski.eclipse.regex.view.actions.StyledTextActionHandler;


public class RegExView extends ViewPart implements SelectionListener,
        IRegExListener, ILiveEvalListener, IExpressionLoaderListener, IPropertyChangeListener {

    private StyledText txt_RegExp;

    private StyledText txt_SearchText;

    private StyledText txt_Result;

    private Label lbl_RegExp;

    private Button btn_Find, btn_LiveEval;

    private Composite cmp_ButtonGroup;

    private MenuItem mit_CopyLiteral, mit_mReplace, mit_mFind, mit_mMatch,
            mit_PasteLiteral, mit_mSplit, mit_EvalSelection;

    private int currentCarresPos = 0;

    private final RegExModel regex;

    private LiveEval liveEval;

    private IAction prevMatchAction;

    private IAction nextMatchAction;

    private IAction clearAction;

    private IPreferenceStore prefs;
    
    private Menu men_PatternFlags;

    private String[] modeLabels = new String[] { "Find", "Match", "Split", "Replace"};

    private static final Color COLOR_WHITE = Display.getCurrent()
            .getSystemColor(SWT.COLOR_WHITE);

    private static final Color COLOR_RED = Display.getCurrent().getSystemColor(
            SWT.COLOR_RED);

    private Menu men_MatchMode;

    private MenuItem mit_MatchMode;

    private AboutAction aboutAction;

    
    public RegExView() {
    
        regex = RegExModel.getInstance();
        prefs = RegExPlugin.getDefault().getPreferenceStore();
        prefs.addPropertyChangeListener(this);
        regex.addRegExListener(this);

        ExpressionLoader.getInstance().addExpressionLoaderListener(this);
       
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#dispose()
     */
    public void dispose() {
        regex.removeRegExListener(this);
        prefs.removePropertyChangeListener(this);
        ExpressionLoader.getInstance().removeExpressionLoaderListener(this);
    }
    
    
    public void createPartControl(final Composite parent) {

        makeActions();

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;

        parent.setLayout(gridLayout);
        GridData gridData = new GridData();
        parent.setLayoutData(gridData);

        lbl_RegExp = new Label(parent, SWT.LEFT);
        lbl_RegExp.setText("Regular Expression");

        txt_RegExp = new StyledText(parent, SWT.LEFT | SWT.BORDER | SWT.SINGLE);

      
            txt_RegExp.addKeyListener(new BracketMatchingKeyAdapter());
            txt_RegExp.addKeyListener(new AssistKeyAdapter());
        

        // GRO: Store/Restore last input value from PreferenceStore
        IPreferenceStore preferenceStore = RegExPlugin.getDefault().getPreferenceStore();
        String preferenceKey = "txt_RegExp";
        txt_RegExp.addFocusListener(new SaveLastValueToPreferenceStoreFocusAdapter(preferenceStore, preferenceKey));
        txt_RegExp.setText(preferenceStore.getString(preferenceKey));
        
        
        txt_RegExp.addFocusListener(new FocusListener() {

            private int caretOffset;

            public void focusGained(FocusEvent e) {
                ((StyledText) e.widget).setCaretOffset(caretOffset);
            }

            public void focusLost(FocusEvent e) {
                caretOffset = ((StyledText) e.widget).getCaretOffset();
            }
        });

        txt_RegExp.setFont(new Font(Display.getCurrent(), new FontData(prefs.getString("font.regex.name"), prefs.getInt("font.regex.height"), prefs.getInt("font.regex.style"))));
        txt_RegExp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        txt_RegExp.setMenu(new Menu(parent.getShell()));
        setTextMenuItems(txt_RegExp);
        new MenuItem(txt_RegExp.getMenu(), SWT.BAR);
        setPatternFlagMenu(txt_RegExp.getMenu());

        txt_RegExp.getMenu().addMenuListener(new MenuListener() {

               	
            public void menuHidden(MenuEvent e) {
     
            }

            public void menuShown(MenuEvent e) {
                if (txt_RegExp.getSelectionCount() > 0) {
                	mit_EvalSelection.setEnabled(true);
                } else {
                	mit_EvalSelection.setEnabled(false);
                }

            }
        });

        // RegEx Menu: Match Mode
        
            men_MatchMode = new Menu(txt_RegExp.getMenu());
            mit_MatchMode = new MenuItem(txt_RegExp.getMenu(),
                                SWT.CASCADE);
            mit_MatchMode.setText("Match mode");
            mit_MatchMode.setMenu(men_MatchMode);

            mit_mFind = new MenuItem(men_MatchMode, SWT.RADIO);
            mit_mFind.setText("Find sequence");
            mit_mFind.setData(new Integer(RegExModel.MODE_FIND));
            mit_mFind.setSelection(true);
            mit_mFind.addSelectionListener(this);

            mit_mMatch = new MenuItem(men_MatchMode, SWT.RADIO);
            mit_mMatch.setText("Match complete text");
            mit_mMatch.setData(new Integer(RegExModel.MODE_MATCH));
            mit_mMatch.addSelectionListener(this);

            mit_mSplit = new MenuItem(men_MatchMode, SWT.RADIO);
            mit_mSplit.setText("Split");
            mit_mSplit.setData(new Integer(RegExModel.MODE_SPLIT));
            mit_mSplit.addSelectionListener(this);

            mit_mReplace = new MenuItem(men_MatchMode, SWT.RADIO);
            mit_mReplace.setText("Replace...");
            mit_mReplace.setData(new Integer(RegExModel.MODE_REPLACE));
            mit_mReplace.addSelectionListener(this);

        

        // RegEx Menu Item: Copy As String Literal
        mit_CopyLiteral = new MenuItem(txt_RegExp.getMenu(), SWT.NONE);
        mit_CopyLiteral.setText("Copy As String &Literal");
        mit_CopyLiteral.addSelectionListener(this);

        mit_PasteLiteral = new MenuItem(txt_RegExp.getMenu(), SWT.NONE);
        mit_PasteLiteral.setText("&Paste String Literal");
        mit_PasteLiteral.addSelectionListener(this);

        
        	new MenuItem(txt_RegExp.getMenu(), SWT.BAR);
            mit_EvalSelection = new MenuItem(txt_RegExp.getMenu(), SWT.NONE);
	        mit_EvalSelection.setText("Eval Selection Only");
	        mit_EvalSelection.addSelectionListener(new SelectionListener() {
	
				public void widgetSelected(SelectionEvent e) {
			        regex.setRegExp(txt_RegExp.getSelectionText());
			        regex.setSearchText(txt_SearchText.getText());
			        regex.process();
					
				}
	
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}});
        
        
        
        SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
        sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

        txt_SearchText = new StyledText(sashForm, SWT.LEFT | SWT.MULTI
                | SWT.V_SCROLL | SWT.BORDER);
        txt_SearchText.setFont(new Font(Display.getCurrent(), new FontData(prefs.getString("font.searchtext.name"), prefs.getInt("font.searchtext.height"), prefs.getInt("font.searchtext.style"))));
        txt_SearchText.setWordWrap(true);
        txt_SearchText.setLayoutData(new GridData(GridData.FILL_BOTH));
        txt_SearchText.addVerifyKeyListener(new VerifyKeyListener() {

            public void verifyKey(VerifyEvent event) {
                if (event.keyCode == SWT.TAB) {
                    txt_RegExp.setCaretOffset(currentCarresPos);
                    currentCarresPos = txt_SearchText.getCaretOffset();
                    txt_RegExp.setFocus();
                    event.doit = false;
                }
            }
        });

        txt_SearchText.setMenu(new Menu(parent.getShell()));
        setTextMenuItems(txt_SearchText);
        
        
        // GRO: Store/Restore last input value from PreferenceStore 
        preferenceKey = "txt_SearchText";
        txt_SearchText.addFocusListener(new SaveLastValueToPreferenceStoreFocusAdapter(preferenceStore, preferenceKey));
        txt_SearchText.setText(preferenceStore.getString(preferenceKey));

      

        txt_Result = new StyledText(sashForm, SWT.LEFT | SWT.MULTI
                | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);

        txt_Result.setFont(new Font(Display.getCurrent(), new FontData(prefs.getString("font.result.name"), prefs.getInt("font.result.height"), prefs.getInt("font.result.style"))));
        txt_Result.setWordWrap(true);
        txt_Result.setLayoutData(new GridData(GridData.FILL_BOTH));
        txt_Result.setBackground(Display.getCurrent().getSystemColor(
                SWT.COLOR_WIDGET_BACKGROUND));
        txt_Result.setMenu(new Menu(parent.getShell()));
        setTextMenuItems(txt_Result);

        cmp_ButtonGroup = new Composite(parent, 0);
        RowLayout buttonRow = new RowLayout();
        buttonRow.pack = false;
        buttonRow.marginLeft = 0;
        buttonRow.spacing = 20;
        cmp_ButtonGroup.setLayout(buttonRow);

        btn_Find = new Button(cmp_ButtonGroup, SWT.CENTER | SWT.FLAT);
        btn_Find.setText("&Find");
        btn_Find.addSelectionListener(this);

        liveEval = new LiveEval(txt_RegExp, txt_SearchText);
        liveEval.addLiveEvalListener(this);

        btn_LiveEval = new Button(cmp_ButtonGroup, SWT.CHECK | SWT.FLAT);
        btn_LiveEval.setText("&Live Evaluation");
        if (prefs.getBoolean("EvalSwitch")) {
            btn_LiveEval.setSelection(true);
            liveEval.start();
        }

        btn_LiveEval.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                Button check = (Button) e.widget;
                if (check.getSelection()) {
                    liveEval.start();
                } else {
                    liveEval.stop();
                }
            }
        });

        StyledTextActionHandler styledTextActionHandler = new StyledTextActionHandler(
                this.getViewSite().getActionBars());
        styledTextActionHandler.addStyledText(txt_RegExp);
        styledTextActionHandler.addStyledText(txt_SearchText);
        styledTextActionHandler.addStyledText(txt_Result);

        if (liveEval.isLiveEval()) {
            processRegEx();
            updateFoundStatus();
        }

    }

    public void updateFoundStatus() {
        if (regex.foundMatches()) {
            if (regex.getMatches().getMatchPos() > 0)
                prevMatchAction.setEnabled(true);
            else
                prevMatchAction.setEnabled(false);

            if (regex.getMatches().getMatchPos() < regex.getMatches()
                    .getMatchCount() - 1)
                nextMatchAction.setEnabled(true);
            else
                nextMatchAction.setEnabled(false);
        } else {
            prevMatchAction.setEnabled(false);
            nextMatchAction.setEnabled(false);
        }
    }

    private void makeActions() {
        nextMatchAction = new NextMatchAction(this);
        prevMatchAction = new PrevMatchAction(this);
        clearAction = new ClearAction(this);
        
        
        aboutAction = new AboutAction(this);
		
        getViewSite().getActionBars().getToolBarManager().add(clearAction);
        getViewSite().getActionBars().getToolBarManager().add(new Separator());
        getViewSite().getActionBars().getToolBarManager().add(prevMatchAction);
        getViewSite().getActionBars().getToolBarManager().add(nextMatchAction);
        //getViewSite().getActionBars().getMenuManager().add(registerAction);
        getViewSite().getActionBars().getMenuManager().add(aboutAction);
    }

    public void selectNextMatch() {
        Match match = regex.getMatches().nextMatch();
        if (match != null) {
            StyledText searchText = txt_SearchText;
            StyledText resultText = txt_Result;
            searchText.setSelection(match.getStart(), match.getEnd());

            String resultStr = resultText.getText();
            Pattern patt = Pattern.compile("start=" + match.getStart()
                    + ", end=" + match.getEnd());
            Matcher matcher = patt.matcher(resultStr);
            if (matcher.find()) {
                resultText.setSelection(matcher.start(), matcher.end());
            }
        }
    }

    public void selectPreviousMatch() {
        Match match = regex.getMatches().prevMatch();

        if (match != null) {
            StyledText searchText = txt_SearchText;
            StyledText resultText = txt_Result;
            searchText.setSelection(match.getStart(), match.getEnd());
            String resultStr = resultText.getText();

            Pattern patt = Pattern.compile("start=" + match.getStart()
                    + ", end=" + match.getEnd());
            Matcher matcher = patt.matcher(resultStr);
            if (matcher.find()) {
                resultText.setSelection(matcher.start(), matcher.end());
            }
        }

    }

    public void clear(int mode) {

        if (mode == ClearAction.MODE_REGEX || mode == ClearAction.MODE_ALL) {
            txt_RegExp.setText("");
        }
        if (mode == ClearAction.MODE_SEARCH || mode == ClearAction.MODE_ALL) {
            txt_SearchText.setText("");
        }
        if (mode == ClearAction.MODE_RESULT || mode == ClearAction.MODE_ALL) {
            txt_Result.setText("");
        }

        regex.reset();

    }

    private void setPatternFlagMenu(Menu menu) {
        class FlagStatusAdapter extends SelectionAdapter {

            public void widgetSelected(SelectionEvent e) {
                MenuItem menItem = (MenuItem) e.widget;
                Integer flag = (Integer) menItem.getData();
                if (menItem.getSelection()) {
                    regex.addPatternFlag(flag.intValue());
                } else {
                    regex.removePatternFlag(flag.intValue());
                }
                if (liveEval.isLiveEval()) {
                    processRegEx();
                    updateFoundStatus();
                }
                txt_RegExp.setToolTipText(regex.getPatternFlagsAsString());
            }
        }
        FlagStatusAdapter flagStatusAdapter = new FlagStatusAdapter();

        men_PatternFlags = new Menu(menu);
        MenuItem mit_PatternFlags = new MenuItem(menu, SWT.CASCADE);
        mit_PatternFlags.setText("Pattern &Flags");
        mit_PatternFlags.setMenu(men_PatternFlags);

        MenuItem mit_Flags_CanonEq = new MenuItem(men_PatternFlags, SWT.CHECK);
        mit_Flags_CanonEq.setText("Canonical &Equivalence");
        mit_Flags_CanonEq.setData(new Integer(Pattern.CANON_EQ));
        mit_Flags_CanonEq.setSelection(prefs.getBoolean("Pattern.CANON_EQ"));
        mit_Flags_CanonEq.addSelectionListener(flagStatusAdapter);

        MenuItem mit_Flags_CaseIns = new MenuItem(men_PatternFlags, SWT.CHECK);
        mit_Flags_CaseIns.setText("Case &Insensitive");
        mit_Flags_CaseIns.setData(new Integer(Pattern.CASE_INSENSITIVE));
        mit_Flags_CaseIns.setSelection(prefs
                .getBoolean("Pattern.CASE_INSENSITIVE"));
        mit_Flags_CaseIns.addSelectionListener(flagStatusAdapter);

        MenuItem mit_Flags_Comments = new MenuItem(men_PatternFlags, SWT.CHECK);
        mit_Flags_Comments.setText("&Comments");
        mit_Flags_Comments.setData(new Integer(Pattern.COMMENTS));
        mit_Flags_Comments.setSelection(prefs.getBoolean("Pattern.COMMENTS"));
        mit_Flags_Comments.addSelectionListener(flagStatusAdapter);

        MenuItem mit_Flags_Dotall = new MenuItem(men_PatternFlags, SWT.CHECK);
        mit_Flags_Dotall.setText("&Dotall Mode");
        mit_Flags_Dotall.setData(new Integer(Pattern.DOTALL));
        mit_Flags_Dotall.setSelection(prefs.getBoolean("Pattern.DOTALL"));
        mit_Flags_Dotall.addSelectionListener(flagStatusAdapter);

        MenuItem mit_Flags_Multiline = new MenuItem(men_PatternFlags, SWT.CHECK);
        mit_Flags_Multiline.setText("&Multiline Mode");
        mit_Flags_Multiline.setData(new Integer(Pattern.MULTILINE));
        mit_Flags_Multiline.setSelection(prefs.getBoolean("Pattern.MULTILINE"));
        mit_Flags_Multiline.addSelectionListener(flagStatusAdapter);

        MenuItem mit_Flags_unicodeCase = new MenuItem(men_PatternFlags,
                SWT.CHECK);
        mit_Flags_unicodeCase.setText("&Unicode Case");
        mit_Flags_unicodeCase.setData(new Integer(Pattern.UNICODE_CASE));
        mit_Flags_unicodeCase.setSelection(prefs
                .getBoolean("Pattern.UNICODE_CASE"));
        mit_Flags_unicodeCase.addSelectionListener(flagStatusAdapter);

        MenuItem mit_Flags_unixLines = new MenuItem(men_PatternFlags, SWT.CHECK);
        mit_Flags_unixLines.setText("Unix &Lines");
        mit_Flags_unixLines.setData(new Integer(Pattern.UNIX_LINES));
        mit_Flags_unixLines
                .setSelection(prefs.getBoolean("Pattern.UNIX_LINES"));
        mit_Flags_unixLines.addSelectionListener(flagStatusAdapter);

        new MenuItem(men_PatternFlags, SWT.BAR);
        MenuItem mit_Flags_deactivateAll = new MenuItem(men_PatternFlags,
                SWT.NONE);
        mit_Flags_deactivateAll.setText("Deactivate &All");
        mit_Flags_deactivateAll.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                MenuItem menItem = (MenuItem) e.widget;
                Menu menu = menItem.getParent();
                MenuItem[] menItems = menu.getItems();
                for (int i = 0; i < menItems.length; i++) {
                    menItems[i].setSelection(false);
                }
                regex.resetPatternFlag();
            }
        });

    }

    public void setFocus() {
        txt_RegExp.setFocus();
    }

    private void setTextMenuItems(StyledText text) {
        MenuItem mitCut = new MenuItem(text.getMenu(), SWT.NONE);
        mitCut.setText("Cu&t");
        mitCut.setData(new StandardTextFieldAction(text,
                StandardTextFieldAction.CUT));
        mitCut.addSelectionListener(this);

        MenuItem mitCopy = new MenuItem(text.getMenu(), SWT.NONE);
        mitCopy.setText("&Copy");
        mitCopy.setData(new StandardTextFieldAction(text,
                StandardTextFieldAction.COPY));
        mitCopy.addSelectionListener(this);

        MenuItem mitPaste = new MenuItem(text.getMenu(), SWT.NONE);
        mitPaste.setText("&Paste");
        mitPaste.setData(new StandardTextFieldAction(text,
                StandardTextFieldAction.PASTE));
        mitPaste.addSelectionListener(this);

        MenuItem mitSelectAll = new MenuItem(text.getMenu(), SWT.NONE);
        mitSelectAll.setText("Select &All");
        mitSelectAll.setData(new StandardTextFieldAction(text,
                StandardTextFieldAction.SELECT_ALL));
        mitSelectAll.addSelectionListener(this);
    }

    public void widgetSelected(SelectionEvent e) {
        Widget widget = e.widget;
        Object data = widget.getData();

        // selected one of the standard text field menu commands
        // Cut, Copy, Paste, Select All
        if (data instanceof StandardTextFieldAction) {
            ((StandardTextFieldAction) data).perform();
        }

        // Selected MenuItem "Copy As String Literal"
        if (widget == mit_CopyLiteral) {
            Clipboard clipboard = new Clipboard(e.display);
            TextTransfer textTransfer = TextTransfer.getInstance();
            clipboard.setContents(new Object[] { regex.getRegExAsLiteral() },
                    new Transfer[] { textTransfer });
            clipboard.dispose();
        }

        if (widget == mit_mFind || widget == mit_mMatch
                || widget == mit_mReplace || widget == mit_mSplit) {
            MenuItem menuItem = (MenuItem) widget;
            if (menuItem.getSelection()) {
                int mode = ((Integer) data).intValue();
                setMatchMode(mode);
                if (liveEval.isLiveEval()) {
                    processRegEx();
                    updateFoundStatus();
                }
            }
        }

        if (widget == mit_PasteLiteral) {
            pasteLiteral(e.display);
        }

        // Selected MenuItem "Replace ..."
        if (widget == mit_mReplace) {
            MenuItem menuItem = (MenuItem) widget;
            if (menuItem.getSelection())
                activateReplaceMode();
        }
        
        if (widget == btn_Find) {
        	processRegEx();
        	updateFoundStatus();
        }

    }

    private void setMatchMode(int mode) {
        regex.setMatchMode(mode);
        btn_Find.setText(modeLabels[mode]);
        MenuItem[] modeItems = men_MatchMode.getItems();
        if (!modeItems[mode].getSelection()) {
            for (int i = 0; i < modeItems.length; i++) {
                modeItems[i].setSelection(false);
            }
            modeItems[mode].setSelection(true);
        }
     }

    private void pasteLiteral(Display display) {
        Clipboard clipboard = new Clipboard(display);
        TextTransfer textTransfer = TextTransfer.getInstance();
        String clipboardText = (String) clipboard.getContents(textTransfer);
        clipboard.dispose();

        if (clipboardText != null) {
            StringBuffer out = new StringBuffer();
            char[] chars = clipboardText.toCharArray();
            boolean lastWasBackslash = false;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '\\') {
                    if (lastWasBackslash) {
                        out.append(chars[i]);
                        lastWasBackslash = false;
                    } else {
                        lastWasBackslash = true;
                    }
                } else {
                    out.append(chars[i]);
                }

            }

            txt_RegExp.insert(out.toString());
        }
    }

    private void activateReplaceMode() {
        InputDialog inputDialog = new InputDialog(
                getViewSite().getShell(),
                "Replace Match by ...",
                "Enter a value which should be used to replace every instance of a found match:",
                regex.getReplace(), new IInputValidator() {

                    public String isValid(String newText) {
                        // TODO Auto-generated method stub
                        return null;
                    }
                });
        int retCode = inputDialog.open();

        if (retCode == Window.OK) {
            String replace = inputDialog.getValue();

            regex.setMatchMode(RegExModel.MODE_REPLACE);
            regex.setReplace(replace);

            btn_Find.setText(modeLabels[RegExModel.MODE_REPLACE]);

            if (liveEval.isLiveEval()) {
                processRegEx();
                updateFoundStatus();
            }
        }
    }

    public void widgetDefaultSelected(SelectionEvent e) {
        // do nothing
    }

    private void processRegEx() {
        updateModel();
        regex.process();
    }
    
    public void updateModel() {
        regex.setRegExp(txt_RegExp.getText());
        regex.setSearchText(txt_SearchText.getText());       
    }
    
    public void updateView(Expression state) {
        updateView(state.getName(), state.getRegex(), state.getSearchText(), state.getMatchMode(), state.getPatternFlag());
        updateModel();
    }
    
    public void updateView(String descr, String regEx, String search, int matchMode, int patternFlags) {
        txt_RegExp.setText(regEx);
        txt_SearchText.setText(search);
        setMatchMode(matchMode);
        setPatternFlags(patternFlags);
        setRegExDecription(descr);
    }



    private void setRegExDecription(String descr) {
        
        regex.setDescription(descr);
    }

    private void setPatternFlags(int patternFlags) {
        MenuItem[] flagItems = men_PatternFlags.getItems();
        for (int i = 0; i < flagItems.length; i++) {
            if (flagItems[i].getData() == null)
                continue;
            if ((patternFlags & ((Integer) flagItems[i].getData()).intValue()) != 0) {
                flagItems[i].setSelection(true);
            } else {
                flagItems[i].setSelection(false);
            }
        }
        
    }

    private void displayReplace(ReplaceResult replaceResult) {
        StringBuffer out = new StringBuffer("Replaced "
                + replaceResult.getMatches().getMatchCount()
                + " match(es):\n\n");
        out.append(replaceResult.getResultText());
        txt_Result.setText(out.toString());
    }

    private void displaySplit(String[] splitResult) {
        StringBuffer out = new StringBuffer("Split text into "
                + splitResult.length + " parts:\n\n");
        for (int i = 0; i < splitResult.length; i++) {
            out.append("part ");
            out.append(i);
            out.append(" = ");
            out.append(splitResult[i]);
            out.append("\n\n");
        }
        out.append(txt_Result.getText());
        txt_Result.setText(out.toString());
    }

    private void displayMatches(Matches matches) {
        StringBuffer out = new StringBuffer("Found " + matches.getMatchCount()
                + " match(es):\n\n");
        txt_SearchText.setStyleRange(null);

        StyleRange[] styleRanges = new StyleRange[matches.getMatchCount()];
        int c = 0;

        for (Iterator i = matches.iterator(); i.hasNext();) {
            Match match = (Match) i.next();

            styleRanges[c++] = new StyleRange(match.getStart(), match.getEnd()
                    - match.getStart(), COLOR_RED, COLOR_WHITE);

            out.append("start=");
            out.append(match.getStart());
            out.append(", end=");
            out.append(match.getEnd());
            out.append("\n");
            for (Iterator groups = match.getGroups().iterator(); groups
                    .hasNext();) {
                Group group = (Group) groups.next();
                out.append("Group(");
                out.append(group.getIndex());
                out.append(") = ");
                out.append(group.getText());
                out.append("\n");
            }

            out.append("\n");

        }
        txt_SearchText.setStyleRanges(styleRanges);
        txt_Result.setText(out.toString());
    }


	public void foundMatches(Matches matches) {
        displayMatches(matches);		
	}


	public void foundNoMatches() {
        txt_SearchText.setStyleRange(null);
        txt_Result.setText("No matches found!");
	}


	public void expressionError(String errMsg) {
        txt_Result.setText("A Syntax Error occured: \\n" + errMsg);		
	}

	public void doneWithReplace(ReplaceResult result) {
        displayReplace(result);
	}

	public void doneWithSplit(String[] result) {
        displaySplit(result);		
	}


	public void evalActivated() {
        btn_LiveEval.setSelection(true);
        processRegEx();
        updateFoundStatus();
	}


	public void evalDeactivated() {
		 btn_LiveEval.setSelection(false);	
	}


	public void evalDone() {
		updateFoundStatus();
	}


	public void doEval() {
		processRegEx();	
	}


    /* (non-Javadoc)
     * @see com.brosinski.eclipse.regex.IRegExListener#updateRequested()
     */
    public void updateRequested() {
        updateModel();
    }

    /* (non-Javadoc)
     * @see com.brosinski.eclipse.regex.view.IExpressionLoaderListener#loadExpression(com.brosinski.eclipse.regex.view.Expression)
     */
    public void loadExpression(Expression expression) {
        updateView(expression);
    }

    protected void setRegExFont(FontData fontData) {
        txt_RegExp.setFont(new Font(Display.getCurrent(), fontData));
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent event) {
       if (event.getProperty().startsWith("font.")) {
           txt_RegExp.setFont(new Font(Display.getCurrent(), new FontData(prefs.getString("font.regex.name"), prefs.getInt("font.regex.height"), prefs.getInt("font.regex.style")))); 
           txt_SearchText.setFont(new Font(Display.getCurrent(), new FontData(prefs.getString("font.searchtext.name"), prefs.getInt("font.searchtext.height"), prefs.getInt("font.searchtext.style")))); 
           txt_Result.setFont(new Font(Display.getCurrent(), new FontData(prefs.getString("font.result.name"), prefs.getInt("font.result.height"), prefs.getInt("font.result.style")))); 
       }
        
    }
 
    
}