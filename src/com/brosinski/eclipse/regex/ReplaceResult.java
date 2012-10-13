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
