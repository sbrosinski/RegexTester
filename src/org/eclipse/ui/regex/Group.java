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
package org.eclipse.ui.regex;


public class Group {
	
	private int index;
	private String text;
	
	public Group(int index, String text) {
		this.index = index;
		this.text = text;
	}
	
	

	/**
	 * @return Returns the index.
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}
}
