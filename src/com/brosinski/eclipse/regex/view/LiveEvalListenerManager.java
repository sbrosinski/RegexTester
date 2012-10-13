package com.brosinski.eclipse.regex.view;

import java.util.Iterator;

import com.brosinski.eclipse.regex.event.ListenerManager;


public class LiveEvalListenerManager extends ListenerManager {

	public void publishEvalActivated() {
		for (Iterator i = getListeners().iterator(); i.hasNext(); ) {
			ILiveEvalListener listener = (ILiveEvalListener) i.next();
			listener.evalActivated();
		}
		};
		
	public void publishEvalDeactivated() {
		for (Iterator i = getListeners().iterator(); i.hasNext(); ) {
			ILiveEvalListener listener = (ILiveEvalListener) i.next();
			listener.evalDeactivated();
		}
		};
		
	public void publishEvalDone() {
		for (Iterator i = getListeners().iterator(); i.hasNext(); ) {
			ILiveEvalListener listener = (ILiveEvalListener) i.next();
			listener.evalDone();
		}
		};
		
	public void publishDoEval() {
		for (Iterator i = getListeners().iterator(); i.hasNext(); ) {
			ILiveEvalListener listener = (ILiveEvalListener) i.next();
			listener.doEval();
		}
		};
		
	
}
