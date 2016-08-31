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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.brosinski.eclipse.regex.RegExPlugin;
import com.brosinski.eclipse.regex.view.RegExView;

public class ClearAction extends Action {
	public static final int	MODE_REGEX	= 0;
	public static final int	MODE_SEARCH	= 1;
	public static final int	MODE_RESULT	= 2;
	public static final int	MODE_ALL	= 3;

	private static ImageDescriptor image = RegExPlugin.getDefault().getImageDescriptor("clear_co.gif");
	private int mode = MODE_ALL;
	private RegExView view;
	

	public ClearAction(RegExView regExView) {
		super("ClearAction", image);
		view = regExView;
		setToolTipText("Clear...");
		setMenuCreator(new ClearActionMenuCreator(this));
	}

	public void setMode(int aMode) {
		mode =aMode;
	}
	
	public void run() {
	    view.clear(mode);
	    view.updateFoundStatus();
	}

}
