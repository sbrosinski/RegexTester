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

public class PrevMatchAction extends Action {
	private static ImageDescriptor image  = RegExPlugin.getDefault().getImageDescriptor("backward_nav.gif");
	private RegExView view;
	
	public PrevMatchAction(RegExView regExView) { 
		super("PrevMatchAction", image);
		view = regExView;
		setEnabled(false);
		setToolTipText("Show Previous Match");
	}
	
	public void run() {
	    view.selectPreviousMatch();
	    view.updateFoundStatus();
	}
	
}
