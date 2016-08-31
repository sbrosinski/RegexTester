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

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;


public class AssistKeyAdapter extends KeyAdapter {

	private StyledText textField;
	
	public void keyPressed(KeyEvent e) {
		textField = (StyledText) e.widget;
		int caretOffset = textField.getCaretOffset();

		if (textField.getText().equals("") || caretOffset == 0) return;

		String textSoFar = textField.getText(0, caretOffset - 1);

		ArrayList proposals = Assistant.getAssistItems(textSoFar);
				
		if (proposals.size() != 0 && e.stateMask == SWT.CONTROL && e.keyCode == 32) {
			Proposal proposal = showProposals(proposals);

				if (proposal != null) {

					textField.replaceTextRange(caretOffset
							- proposal.getReplaceCount(), proposal
							.getReplaceCount(), proposal
							.getSubstitute());
					textField.setCaretOffset(caretOffset
							+ proposal.getSubstitute().length() - proposal.getReplaceCount());
				} else {
					textField.setCaretOffset(caretOffset);
				}
		
				
		} 
	}
	
	protected Proposal showProposals(ArrayList proposals) {
		AssistPopup assist = new AssistPopup(textField.getShell());
		assist.setProposals(proposals);
		Point pos = textField.toDisplay(textField.getCaret()
				.getLocation());
		return assist.open(new Rectangle(
				pos.x + 10, pos.y - 90, 140, 120));
		
	}
	


	
}
