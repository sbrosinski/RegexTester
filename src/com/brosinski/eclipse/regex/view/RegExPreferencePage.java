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

import java.util.regex.Pattern;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.brosinski.eclipse.regex.RegExPlugin;


public class RegExPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button btn_CanonicalEquivalence;
	private Button btn_CaseInsensitive;
	private Button btn_Comments;
	private Button btn_DotallMode;
	private Button btn_MultilineMode;
	private Button btn_UnicodeCase;
	private Button btn_UnixLines;
	private Button btn_EvalRegEx;
	private Button btn_EvalSearch;
	private Button btn_EvalBoth;
	private Button btn_EvalSwitch;
	
	public RegExPreferencePage() {
	}


	public void init(IWorkbench workbench)  {
		this.setPreferenceStore(RegExPlugin.getDefault().getPreferenceStore());
	}

	protected void performDefaults() {
		btn_CanonicalEquivalence.setSelection(getPreferenceStore().getDefaultBoolean("Pattern.CANON_EQ"));
		btn_CaseInsensitive.setSelection(getPreferenceStore().getDefaultBoolean("Pattern.CASE_INSENSITIVE"));
		btn_Comments.setSelection(getPreferenceStore().getDefaultBoolean("Pattern.COMMENTS"));
		btn_DotallMode.setSelection(getPreferenceStore().getDefaultBoolean("Pattern.DOTALL"));
		btn_MultilineMode.setSelection(getPreferenceStore().getDefaultBoolean("Pattern.MULTILINE"));
		btn_UnicodeCase.setSelection(getPreferenceStore().getDefaultBoolean("Pattern.UNICODE_CASE"));
		btn_UnixLines.setSelection(getPreferenceStore().getDefaultBoolean("Pattern.UNIX_LINES"));
		btn_EvalRegEx.setSelection(getPreferenceStore().getDefaultBoolean("EvalRegEx"));
		btn_EvalSearch.setSelection(getPreferenceStore().getDefaultBoolean("EvalSearch"));
		btn_EvalBoth.setSelection(getPreferenceStore().getDefaultBoolean("EvalBoth"));
		btn_EvalSwitch.setSelection(getPreferenceStore().getDefaultBoolean("EvalSwitch"));
	}
	
	public boolean performOk() {
		getPreferenceStore().setValue("Pattern.CANON_EQ",btn_CanonicalEquivalence.getSelection());
		getPreferenceStore().setValue("Pattern.CASE_INSENSITIVE",btn_CaseInsensitive.getSelection());
		getPreferenceStore().setValue("Pattern.COMMENTS",btn_Comments.getSelection());
		getPreferenceStore().setValue("Pattern.DOTALL",btn_DotallMode.getSelection());
		getPreferenceStore().setValue("Pattern.MULTILINE",btn_MultilineMode.getSelection());
		getPreferenceStore().setValue("Pattern.UNICODE_CASE",btn_UnicodeCase.getSelection());
		getPreferenceStore().setValue("Pattern.UNIX_LINES",btn_UnixLines.getSelection());
		getPreferenceStore().setValue("EvalRegEx",btn_EvalRegEx.getSelection());
		getPreferenceStore().setValue("EvalSearch",btn_EvalSearch.getSelection());
		getPreferenceStore().setValue("EvalBoth",btn_EvalBoth.getSelection());
		getPreferenceStore().setValue("EvalSwitch",btn_EvalSwitch.getSelection());		
		RegExPlugin.getDefault().savePluginPreferences();
		return true;
	}

	protected Control createContents(Composite parent)  {

		Group group=new Group(parent,SWT.SHADOW_ETCHED_IN);
		group.setText("Default Pattern Flags");
		GridLayout groupGridLayout=new GridLayout();
		groupGridLayout.numColumns=2;
		
		group.setLayout(groupGridLayout);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		
		btn_CanonicalEquivalence=new Button(group,SWT.CHECK);
		btn_CanonicalEquivalence.setText("Canonical &Equivalence");
		btn_CanonicalEquivalence.setData(new Integer(Pattern.CANON_EQ));
		btn_CanonicalEquivalence.setSelection(getPreferenceStore().getBoolean("Pattern.CANON_EQ"));
		
		btn_CaseInsensitive=new Button(group,SWT.CHECK);
		btn_CaseInsensitive.setText("Case &Insensitive");
		btn_CaseInsensitive.setData(new Integer(Pattern.CASE_INSENSITIVE));
		btn_CaseInsensitive.setSelection(getPreferenceStore().getBoolean("Pattern.CASE_INSENSITIVE"));
				
		btn_Comments=new Button(group,SWT.CHECK);
		btn_Comments.setText("&Comments");
		btn_Comments.setData(new Integer(Pattern.COMMENTS));
		btn_Comments.setSelection(getPreferenceStore().getBoolean("Pattern.COMMENTS"));
					
		btn_DotallMode=new Button(group,SWT.CHECK);
		btn_DotallMode.setText("&Dotall Mode");
		btn_DotallMode.setData(new Integer(Pattern.DOTALL));		
		btn_DotallMode.setSelection(getPreferenceStore().getBoolean("Pattern.DOTALL"));
				
		btn_MultilineMode=new Button(group,SWT.CHECK);
		btn_MultilineMode.setText("&Multiline Mode");
		btn_MultilineMode.setData(new Integer(Pattern.MULTILINE));
		btn_MultilineMode.setSelection(getPreferenceStore().getBoolean("Pattern.MULTILINE"));
		
		btn_UnicodeCase=new Button(group,SWT.CHECK);
		btn_UnicodeCase.setText("&Unicode Case");
		btn_UnicodeCase.setData(new Integer(Pattern.UNICODE_CASE));
		btn_UnicodeCase.setSelection(getPreferenceStore().getBoolean("Pattern.UNICODE_CASE"));
		
		btn_UnixLines=new Button(group,SWT.CHECK);
		btn_UnixLines.setText("Unix &Lines");
		btn_UnixLines.setData(new Integer(Pattern.UNIX_LINES));
		btn_UnixLines.setSelection(getPreferenceStore().getBoolean("Pattern.UNIX_LINES"));

		Group evalGroup=new Group(parent,SWT.SHADOW_ETCHED_IN);
		evalGroup.setText("Live Evaluation");
		GridLayout evalGridLayout=new GridLayout();
		evalGridLayout.numColumns=1;
		evalGroup.setLayout(evalGridLayout);
		evalGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		btn_EvalRegEx=new Button(evalGroup,SWT.RADIO);
		btn_EvalRegEx.setText("React On &Regular Expression Changes");
		btn_EvalRegEx.setSelection(getPreferenceStore().getBoolean("EvalRegEx"));
		
		btn_EvalSearch=new Button(evalGroup,SWT.RADIO);
		btn_EvalSearch.setText("React On &Search Text Changes");
		btn_EvalSearch.setSelection(getPreferenceStore().getBoolean("EvalSearch"));
				
		btn_EvalBoth=new Button(evalGroup,SWT.RADIO);
		btn_EvalBoth.setText("&React On Everything");
		btn_EvalBoth.setSelection(getPreferenceStore().getBoolean("EvalBoth"));
				
		btn_EvalSwitch=new Button(evalGroup,SWT.CHECK);
		btn_EvalSwitch.setText("Use Live &Evaluation");										
		btn_EvalSwitch.setSelection(getPreferenceStore().getBoolean("EvalSwitch"));
		
		return null;
	}
}
