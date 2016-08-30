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

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a single match with was found.
 * @author sbrosinski
 * @version $$Id: Match.java,v 1.4 2004/06/15 11:49:17 sbrosin Exp $$
 */
 
public class Match {
	/** the match's start index within the search text */ 
	private int	start_;
	/** the match's end index within the search text */ 	
	private int	end_;

	/** contains the match text actually found */ 
	private String text_;

	private ArrayList groups_;

	/**
	 * Construct a Match.
	 * @param id	an id, identifying the match uniquely
	 * @param start the match's start index within the search text
	 * @param end   the match's end index within the search text 
	 * @param text  contains the match text actually found
	 */
	public Match(int start, int end, String text) {
		start_ 	= start;
		end_ 	= end;
		text_	= text;
		groups_ = new ArrayList();
	}

	public void addGroup(Group group) {
		groups_.add(group);
	}

	public int getGroupCount() {
		return groups_.size();
	}
	
	public List getGroups() {
		return groups_;
	}

	/**
	 * Gets the end index of found match.
	 * @return end index of found match
	 */
	public int getEnd() {
		return end_;
	}


	/**
	 * Gets the start index of found match.
	 * @return start index of found match
	 */
	public int getStart() {
		return start_;
	}


	/**
	 * Gets the match text.
	 * @return the match text
	 */
	public String getText() {
		return text_;
	}



	/**
	 * Sets the end end index of found match.
	 * @param end end index of found match
	 */
	public void setEnd(int end) {
		end_ = end;
	}


	/**
	 * Sets the start end index of found match.
	 * @param start start index of found match
	 */
	public void setStart(int start) {
		start_ = start;
	}


	/**
	 * Sets the match text.
	 * @param text the match text
	 */
	public void setText(String text) {
		text_ = text;
	}

}
