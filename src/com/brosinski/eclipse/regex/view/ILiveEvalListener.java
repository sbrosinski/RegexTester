
package com.brosinski.eclipse.regex.view;

import com.brosinski.eclipse.regex.event.IListener;


public interface ILiveEvalListener extends IListener {
	
	public void evalActivated();
	public void evalDeactivated();
	public void evalDone();
	public void doEval();
	
}
