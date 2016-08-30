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
package com.brosinski.eclipse.regex.view.actions;

import org.eclipse.swt.custom.StyledText;


public class StandardTextFieldAction {
	
	private StyledText styledText;
	private int type;
	
	public static final int COPY 	   = 1;
	public static final int CUT 	   = 2;
	public static final int PASTE	   = 3;
	public static final int SELECT_ALL = 4;
	
	public StandardTextFieldAction(StyledText styledText,int type) {
		this.styledText = styledText;
		this.type = type;
	}
	
	public void perform() {
		if (type == COPY) 		styledText.copy();
		if (type == CUT) 		styledText.cut();
		if (type == PASTE)	 	styledText.paste();
		if (type == SELECT_ALL) styledText.selectAll();
	}
	
}
