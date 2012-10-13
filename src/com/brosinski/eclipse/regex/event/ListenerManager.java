
package com.brosinski.eclipse.regex.event;

import java.util.ArrayList;
import java.util.List;


public abstract class ListenerManager {
	
	private List listeners = new ArrayList();
	
	public void addListener(IListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IListener listener) {
		listeners.remove(listener);
	}
	
	protected List getListeners() {
	    return listeners;
	}
}
