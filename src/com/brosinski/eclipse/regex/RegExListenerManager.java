
package com.brosinski.eclipse.regex;

import java.util.Iterator;

import com.brosinski.eclipse.regex.event.ListenerManager;

public class RegExListenerManager extends ListenerManager {
	
	public void publishExpressionError(String errMsg) {
		for (Iterator i = getListeners().iterator(); i.hasNext();) {
			IRegExListener listener = (IRegExListener) i.next();
			listener.expressionError(errMsg);
		}
	}

	public void publishFoundMatches(Matches foundMatches) {
		for (Iterator i = getListeners().iterator(); i.hasNext(); ) {
			IRegExListener listener = (IRegExListener) i.next();
			listener.foundMatches(foundMatches);
		}
	}

	public void publishFoundNoMatches() {
		for (Iterator i = getListeners().iterator(); i.hasNext(); ) {
			IRegExListener listener = (IRegExListener) i.next();
			listener.foundNoMatches();
		}
	}
	
	public void publishDoneWithReplace(ReplaceResult result) {
		for (Iterator i = getListeners().iterator(); i.hasNext(); ) {
			IRegExListener listener = (IRegExListener) i.next();
			listener.doneWithReplace(result);
		}
	}
	
	public void publishDoneWithSplit(String[] result) {
		for (Iterator i = getListeners().iterator(); i.hasNext(); ) {
			IRegExListener listener = (IRegExListener) i.next();
			listener.doneWithSplit(result);
		}
	}
	
	public void updateRequested() {
		for (Iterator i = getListeners().iterator(); i.hasNext(); ) {
			IRegExListener listener = (IRegExListener) i.next();
			listener.updateRequested();
		}
	}	
	
}
