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
