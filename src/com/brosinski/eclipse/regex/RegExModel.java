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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegExModel {

    private static RegExModel _instance;

    public final static int MODE_FIND = 0;

    public final static int MODE_MATCH = 1;

    public final static int MODE_SPLIT = 2;

    public final static int MODE_REPLACE = 3;

    private int patternFlags;

    private Matches matches;

    private boolean foundMatches;

    private int matchMode;

    private String replace;

    private String _regExp;

    private String _searchText;

    private RegExListenerManager regExListenerManager;

    private String description;
    
    private RegExModel() {
        matches = new Matches();
        matchMode = MODE_FIND;
        patternFlags = 0;
        foundMatches = false;
        regExListenerManager = new RegExListenerManager();
    }

    public static RegExModel getInstance() {
        if (_instance == null) {
            _instance = new RegExModel();
        }
        return _instance;
    }

    public int getMatchMode() {
        return matchMode;
    }
    
    public void requestUpdate() {
        regExListenerManager.updateRequested();
    }
    
    public void addRegExListener(IRegExListener listener) {
    	regExListenerManager.addListener(listener);
    }

    public void removeRegExListener(IRegExListener listener) {
    	regExListenerManager.removeListener(listener);
    }

    public void process() {
        foundMatches = false;
        matches = new Matches();
        if (_regExp.equals("") || _searchText.equals("")) {
        	regExListenerManager.publishFoundNoMatches();
            return;
        }
        try {
            Pattern pattern = Pattern.compile(_regExp, getPatternFlags());
            Matcher match = pattern.matcher(_searchText);
            switch (matchMode) {
            case MODE_FIND: {
                find(match);
                break;
            }
            case MODE_MATCH: {
                match(match);
                break;
            }
            case MODE_REPLACE: {
                replace(match);
                break;
            }
            case MODE_SPLIT: {
                split(match, pattern);
                break;
            }
            }
        } catch (PatternSyntaxException patternSyntaxException) {
        	regExListenerManager.publishExpressionError(patternSyntaxException.getMessage());
        }
    }

    private void find(Matcher match) {
        if (processMatches(match)) {
        	regExListenerManager.publishFoundMatches(matches);
        } else {
        	regExListenerManager.publishFoundNoMatches();
        }
    }
    
    private void match(Matcher match) {

        if (match.matches()) {
            foundMatches = true;
            matches.addMatch(new Match(match.start(), match.end(), match
                    .group(0)));
            regExListenerManager.publishFoundMatches(matches);
        } else {
        	regExListenerManager.publishFoundNoMatches();
        }

    }

    private boolean processMatches(Matcher match) {
        while (match.find()) {
            foundMatches = true;

            Match aMatch = new Match(match.start(), match.end(), match.group(0));
            for (int i = 0; i < match.groupCount() + 1; i++) {
                aMatch.addGroup(new Group(i, match.group(i)));
            }
            matches.addMatch(aMatch);
        }
        return foundMatches;
    }

    private void replace(Matcher match) {
        if (replace != null) {
            if (processMatches(match)) {
                String resultText = match.replaceAll(replace);
                regExListenerManager.publishDoneWithReplace(new ReplaceResult(resultText, matches));
            } else {
            	regExListenerManager.publishFoundNoMatches();
            }
        }
    }


    private void split(Matcher match, Pattern pattern) {
        if (processMatches(match)) {
        	regExListenerManager.publishFoundMatches(matches);
        	regExListenerManager.publishDoneWithSplit(pattern.split(_searchText));        
        } else {
        	regExListenerManager.publishFoundNoMatches();
        }
    }    
    

    public void setMatchMode(int mode) {
        matchMode = mode;
    }

    public void setReplace(String theReplace) {
        replace = theReplace;
    }

    public String getReplace() {
        return replace;
    }

    public void reset() {
        foundMatches = false;
        matches = new Matches();
        _regExp = "";
        _searchText = "";
    }

    public boolean foundMatches() {
        return foundMatches;
    }

    public Matches getMatches() {
        return matches;
    }

    public void addPatternFlag(int flag) {
        patternFlags += flag;
    }

    public void removePatternFlag(int flag) {
        patternFlags -= flag;
    }

    public void resetPatternFlag() {
        patternFlags = 0;
    }

    public int getPatternFlags() {
        return patternFlags;
    }

    public String getPatternFlagsAsString() {
        if (patternFlags == 0)
            return "";
        StringBuffer out = new StringBuffer();
        if ((patternFlags & Pattern.CANON_EQ) != 0)
            out.append("Pattern.CANON_EQ+");
        if ((patternFlags & Pattern.CASE_INSENSITIVE) != 0)
            out.append("Pattern.CASE_INSENSITIVE+");
        if ((patternFlags & Pattern.COMMENTS) != 0)
            out.append("Pattern.COMMENTS+");
        if ((patternFlags & Pattern.DOTALL) != 0)
            out.append("Pattern.DOTALL+");
        if ((patternFlags & Pattern.MULTILINE) != 0)
            out.append("Pattern.MULTILINE+");
        if ((patternFlags & Pattern.UNICODE_CASE) != 0)
            out.append("Pattern.UNICODE_CASE+");
        if ((patternFlags & Pattern.UNIX_LINES) != 0)
            out.append("Pattern.UNIX_LINES+");
        String outStr = out.toString();
        return outStr.substring(0, outStr.length() - 1);
    }

    public String getRegExAsLiteral() {
        StringBuffer out = new StringBuffer();
        char[] chars = getRegExp().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '\\') {
                out.append("\\\\");
            } else if (chars[i] == '"') {
                out.append("\\\"");
            } else {
                out.append(chars[i]);
            }
        }
        return out.toString();
    }

    /**
     * @return Returns the _regExp.
     */
    public String getRegExp() {
        return _regExp;
    }

    /**
     * @param exp The _regExp to set.
     */
    public void setRegExp(String exp) {
        _regExp = exp;
    }

    /**
     * @return Returns the _searchText.
     */
    public String getSearchText() {
        return _searchText;
    }

    /**
     * @param text The _searchText to set.
     */
    public void setSearchText(String text) {
        _searchText = text;
    }

    /**
     * @param descr
     */
    public void setDescription(String description) {
        this.description = description;        
    }
    
    public String getDescription() {
        return description;
    }
    
}