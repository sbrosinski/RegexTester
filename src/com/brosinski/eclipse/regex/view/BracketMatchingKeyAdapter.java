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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Display;

public class BracketMatchingKeyAdapter extends KeyAdapter {

	public void keyPressed(KeyEvent e) {
		StyledText txt_RegExp = (StyledText) e.widget;
		txt_RegExp.setStyleRange(null);
		
		if (matchableBracket(e.character)) { 
			
			markMatchingBracket(txt_RegExp, e.character);

		} else {
				
			final int KEY_LEFT_ARROW = 16777219;
			final int KEY_RIGHT_ARROW = 16777220;
	
			if (e.keyCode == KEY_LEFT_ARROW || e.keyCode == KEY_RIGHT_ARROW) {
			
				int caretOffset = txt_RegExp.getCaretOffset();
				String lastText = " ";
				if (caretOffset - 1 >= 0) {
					lastText = txt_RegExp.getText(caretOffset - 1, caretOffset - 1);
				}
		
				if (matchableBracket(lastText.charAt(0))) {
					markMatchingBracket(txt_RegExp, lastText.charAt(0));
				}
			
			}
		
		}

	}

	protected void markMatchingBracket(StyledText txt_RegExp, char bracket) {
		int caretOffset = txt_RegExp.getCaretOffset();
		int brakePos = -1;
		int ignoreInnerBracket = 0;
		
		if (isClosingBracket(bracket)) {
			String text = txt_RegExp.getText(0, caretOffset - 1);
			for (int i = text.length() - 2; i >= 0; i--) {
				if (text.charAt(i) == bracket) {
					ignoreInnerBracket++;
				}
				if (text.charAt(i) == getMatchingBracket(bracket)) {
					if (ignoreInnerBracket == 0) {
						brakePos = i;
						break;
					} else {
						ignoreInnerBracket--;
					}
				}
			}
		}
			
		if (isOpeningBracket(bracket)) {
			if (caretOffset != txt_RegExp.getText().length()) {
				String text = txt_RegExp.getText(caretOffset, txt_RegExp.getText().length() - 1);
				for (int i = 0; i < text.length(); i++) {
					if (text.charAt(i) == bracket) {
						ignoreInnerBracket++;
					}
					if (text.charAt(i) == getMatchingBracket(bracket)) {
						if (ignoreInnerBracket == 0) {
							brakePos = caretOffset + i;
							break;
						} else {
							ignoreInnerBracket--;
						}
					}
				}
			}
		}		
		
		if (brakePos != -1) {
			StyleRange styleRange1 = new StyleRange(brakePos, 1, Display
					.getCurrent().getSystemColor(SWT.COLOR_RED), Display
					.getCurrent().getSystemColor(SWT.COLOR_WHITE), SWT.BOLD);
			StyleRange styleRange2 = new StyleRange(caretOffset - 1, 1, Display
					.getCurrent().getSystemColor(SWT.COLOR_RED), Display
					.getCurrent().getSystemColor(SWT.COLOR_WHITE), SWT.BOLD);
			
			if (isOpeningBracket(bracket)) {
				txt_RegExp
					.setStyleRanges(new StyleRange[]{styleRange2, styleRange1});
			}
			if (isClosingBracket(bracket)) {
				txt_RegExp
					.setStyleRanges(new StyleRange[]{styleRange1, styleRange2});
			}
		}
	}
	
	protected boolean matchableBracket(char bracket) {
		if (bracket == ')' || bracket == ']' || bracket == '(' || bracket == '[' || bracket == '}' || bracket == '{') {
			return true;
		} else  {
			return false;
		}
	}
	
	protected char getMatchingBracket(char bracket) {
		if (bracket == ')') return '(';
		if (bracket == '(') return ')';
		if (bracket == ']') return '[';
		if (bracket == '[') return ']';
		if (bracket == '}') return '{';
		if (bracket == '{') return '}';
		return ' ';
	}
	
	protected boolean isOpeningBracket(char bracket) {
		if (bracket == '(') return true;
		if (bracket == '[') return true;
		if (bracket == '{') return true;
		return false;	
	}
	
	protected boolean isClosingBracket(char bracket) {
		if (bracket == ')') return true;
		if (bracket == ']') return true;
		if (bracket == '}') return true;
		return false;	
	}
}