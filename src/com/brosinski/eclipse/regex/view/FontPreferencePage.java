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

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.brosinski.eclipse.regex.RegExPlugin;


public class FontPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, SelectionListener {

    private Button regexFontButton;
    private Text regexFontText;
    private FontData regexFontData;
    private Text searchTextFontText;
    private Button searchTextFontButton;
    private Text resultFontText;
    private Button resultFontButton;
    private FontData searchTextFontData;
    private FontData resultFontData;
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     */
    protected void performDefaults() {
        String fontName   = getPreferenceStore().getDefaultString("font.regex.name");
        int fontHeight = getPreferenceStore().getDefaultInt("font.regex.height");
        int fontStyle  = getPreferenceStore().getDefaultInt("font.regex.style");
        regexFontData = new FontData(fontName, fontHeight, fontStyle);
        regexFontText.setText(buildDescription(fontName, fontHeight));
    }
    
    private void setFontData() {
        if (regexFontData != null) {
			getPreferenceStore().setValue("font.regex.name", regexFontData.getName());
			getPreferenceStore().setValue("font.regex.height", regexFontData.getHeight());
			getPreferenceStore().setValue("font.regex.style", regexFontData.getStyle());
        }
        if (searchTextFontData != null) {
			getPreferenceStore().setValue("font.searchtext.name", searchTextFontData.getName());
			getPreferenceStore().setValue("font.searchtext.height", searchTextFontData.getHeight());
			getPreferenceStore().setValue("font.searchtext.style", searchTextFontData.getStyle());
        }
        if (resultFontData != null) {
			getPreferenceStore().setValue("font.result.name", resultFontData.getName());
			getPreferenceStore().setValue("font.result.height", resultFontData.getHeight());
			getPreferenceStore().setValue("font.result.style", resultFontData.getStyle());
        }        
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.PreferencePage#performApply()
     */
    protected void performApply() {
        setFontData();
    }
    
	/* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferencePage#performOk()
     */
    public boolean performOk() {
        setFontData();
		RegExPlugin.getDefault().savePluginPreferences();
		return true;
    }
    
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
        
        Label regexFontLabel = new Label(composite, SWT.NONE);
        regexFontLabel.setText("Regular Expression Font:");
        GridData gridData = new GridData();
        gridData.horizontalSpan = 2;
        regexFontLabel.setLayoutData(gridData);
        
        regexFontText = new Text(composite, SWT.BORDER);
        regexFontText.setText(buildDescription(fontName("regex"), fontHeight("regex")));
        regexFontText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        regexFontText.setFont(createFont("regex"));
        
        regexFontButton = new Button(composite, SWT.PUSH);
        regexFontButton.setText("Change...");
        regexFontButton.addSelectionListener(this);
        regexFontButton.setData("regex");
        regexFontButton.setLayoutData(new GridData());

        Label searchTextFontLabel = new Label(composite, SWT.NONE);
        searchTextFontLabel.setText("Search Text Font:");
        gridData = new GridData();
        gridData.horizontalSpan = 2;
        searchTextFontLabel.setLayoutData(gridData);
        
        searchTextFontText = new Text(composite, SWT.BORDER);
        searchTextFontText.setText(buildDescription(fontName("searchtext"), fontHeight("searchtext")));
        searchTextFontText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        searchTextFontText.setFont(createFont("searchtext"));
        
        searchTextFontButton = new Button(composite, SWT.PUSH);
        searchTextFontButton.setText("Change...");
        searchTextFontButton.addSelectionListener(this);
        searchTextFontButton.setData("searchtext");
        searchTextFontButton.setLayoutData(new GridData());

        Label resultFontLabel = new Label(composite, SWT.NONE);
        resultFontLabel.setText("Result Font:");
        gridData = new GridData();
        gridData.horizontalSpan = 2;
        resultFontLabel.setLayoutData(gridData);
        
        resultFontText = new Text(composite, SWT.BORDER);
        resultFontText.setText(buildDescription(fontName("result"), fontHeight("result")));
        resultFontText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        resultFontText.setFont(createFont("result"));
        
        resultFontButton = new Button(composite, SWT.PUSH);
        resultFontButton.setText("Change...");
        resultFontButton.addSelectionListener(this);
        resultFontButton.setData("result");
        resultFontButton.setLayoutData(new GridData());       
        
        return parent;
    }
    
    private String fontName(String type) {
        return getPreferenceStore().getString("font." + type + ".name");
    }

    private int fontHeight(String type) {
        return getPreferenceStore().getInt("font." + type + ".height");
    }

    private int fontStyle(String type) {
        return getPreferenceStore().getInt("font." + type + ".style");
    }
    
    private FontData fontData(String type) {
        return new FontData(fontName(type), fontHeight(type), fontStyle(type));
    }
    
    private Font createFont(String type) {
        return new Font(Display.getCurrent(), fontData(type));
    }
    

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        this.setPreferenceStore(RegExPlugin.getDefault().getPreferenceStore());
        
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        if (e.widget == regexFontButton || e.widget == searchTextFontButton || e.widget == resultFontButton) {
            FontDialog fontDialog = new FontDialog(getShell());
            fontDialog.setFontList(new FontData[]{fontData((String) ((Button) e.widget).getData())});
            FontData fontData = fontDialog.open();
            if (fontData != null) {
                if (e.widget == regexFontButton) {
	                regexFontData = fontData;
	                regexFontText.setText(fontData.getName() + ", " + fontData.getHeight());
	                regexFontText.setFont(new Font(Display.getCurrent(), fontData));
                }
                if (e.widget == searchTextFontButton) {
	                searchTextFontData = fontData;
	                searchTextFontText.setText(fontData.getName() + ", " + fontData.getHeight());
	                searchTextFontText.setFont(new Font(Display.getCurrent(), fontData));
                }
                if (e.widget == resultFontButton) {
	                resultFontData = fontData;
	                resultFontText.setText(fontData.getName() + ", " + fontData.getHeight());
	                resultFontText.setFont(new Font(Display.getCurrent(), fontData));
                }
            }
        }
        
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
        
    }

    private String buildDescription(String fontName, int fontHeight) {
        return fontName + ", " + fontHeight;
    }

}
