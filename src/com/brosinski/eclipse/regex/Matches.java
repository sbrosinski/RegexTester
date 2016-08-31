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
import java.util.Iterator;


/**
 * Encapsulates all matches found during a single matching session. 
 * @author sbrosinski
 * @version $$Id: Matches.java,v 1.6 2004/09/06 10:35:16 sbrosin Exp $$
 */
public class Matches {

	/** a list containing all matches. */
	private ArrayList	matchList_;
	
	/** virtual marker, can be set to a certain position within the matches
	 *  list. Is used to provide previousMatch/nextMatch result scrolling.
	 * */
	private int			posMarker_;


	/**
	 * Constructs a new Matches object.
	 */
	public Matches() {
		matchList_ = new ArrayList();
		// set position marker "in front" of list
		posMarker_ = -1;
	}

	public Iterator iterator() {
	    return matchList_.iterator();
	}
	
	
	/**
	 * Gets the number of matches found.
	 * @return number of matches.
	 */
	public int getMatchCount() {
		return matchList_.size();
	}


	/** 
	 * Gets the postion of the internal list marker.
	 * @return position, 0 to numberOfMatches-1
	 */
	public int getMatchPos() {
		return posMarker_;
	}


	/**
	 * Returns the match found for a certain range withing the
	 * search text.
	 * @param start the range's start index
	 * @param end	the range's end index
	 * @return one Match or null, if no match was found
	 */
	public Match getMatchByRange(int start, int end) {
		for (int i = 0; i < matchList_.size(); i++) {
			Match match = (Match) matchList_.get(i);
			if (start >= match.getStart() && end <= match.getEnd())
				return match;
		}
		return null;
	}


	public void addMatch(Match match) {
		matchList_.add(match);
	}


	public Match nextMatch() {
		if (matchList_ == null)
			return null;

		if (!(posMarker_ + 1 > matchList_.size() - 1)) {
			posMarker_++;
			Match match = (Match) matchList_.get(posMarker_);
			return match;
		}

		return null;
	}


	public Match prevMatch() {
		if (matchList_ == null)
			return null;

		if (!(posMarker_ - 1 < 0)) {
			posMarker_--;
			Match match = (Match) matchList_.get(posMarker_);
			return match;
		}

		return null;
	}


	public void resetMatchPos() {
		posMarker_ = -1;
	}


	public void setMatchPos(int pos) {
		posMarker_ = pos;
	}


	public void reset() {
		matchList_ = new ArrayList();
	}

}
