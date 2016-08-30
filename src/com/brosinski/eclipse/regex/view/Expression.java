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


public class Expression {

    private String regex = "";

    private String searchText = "";

    private String name = "";

    private int matchMode = 0;

    private int patternFlag = 0;
    

    public Expression(String name) {
        this.name = name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int hash = 7;
		hash = 31 * hash + (null == name ? 0 : name.hashCode());
		return hash;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (! (o instanceof Expression)) return false;
        Expression state = (Expression) o;
        if (name.equals(state.getName())) {
            	return true;
        }              
        return false;
    }
    
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(int matchMode) {
        this.matchMode = matchMode;
    }

    public int getPatternFlag() {
        return patternFlag;
    }

    public void setPatternFlag(int patternFlag) {
        this.patternFlag = patternFlag;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}