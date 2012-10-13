
package com.brosinski.eclipse.regex;


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
