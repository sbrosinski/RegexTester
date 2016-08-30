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
