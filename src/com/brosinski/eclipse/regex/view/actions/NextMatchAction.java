
package com.brosinski.eclipse.regex.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.brosinski.eclipse.regex.RegExPlugin;
import com.brosinski.eclipse.regex.view.RegExView;

public class NextMatchAction extends Action {
	private static ImageDescriptor image  = RegExPlugin.getDefault().getImageDescriptor("forward_nav.gif");
	private RegExView view;
	
	public NextMatchAction(RegExView regExView) { 
		super("NextMatchAction", image);
		view = regExView;
		setEnabled(false);
		setToolTipText("Show Next Match");
	}
	
	public void run() {
	    view.selectNextMatch();
	    view.updateFoundStatus();
	}
	
}
