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
