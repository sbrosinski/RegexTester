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


public class ReplaceResult {
	
	private Matches matches;
	private String resultText;
	
	public ReplaceResult(String resultText, Matches matches) {
		this.resultText = resultText;
		this.matches = matches;
		
	}

	/**
	 * @return Returns the afterReplace.
	 */
	public String getResultText() {
		return resultText;
	}

	/**
	 * @return Returns the matches.
	 */
	public Matches getMatches() {
		return matches;
	}
}
