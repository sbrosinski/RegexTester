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

import com.brosinski.eclipse.regex.event.IListener;


public interface ILiveEvalListener extends IListener {
	
	public void evalActivated();
	public void evalDeactivated();
	public void evalDone();
	public void doEval();
	
}
