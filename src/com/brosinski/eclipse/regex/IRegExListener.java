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
package com.brosinski.eclipse.regex;

import com.brosinski.eclipse.regex.event.IListener;


public interface IRegExListener extends IListener {
	
	public void foundMatches(Matches matches);
	public void foundNoMatches();
	public void expressionError(String errMsg);
	public void doneWithReplace(ReplaceResult result);
	public void doneWithSplit(String[] result);
    public void updateRequested();
}
