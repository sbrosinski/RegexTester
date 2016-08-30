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


public class Assistant {


	public static ArrayList getAssistItems(String textLeftOfCursor) {
		ArrayList proposals = new ArrayList();
		if (textLeftOfCursor.endsWith("\\")) {
			proposals.add(new Proposal("\\A", "Beginning input \\A", 1));
			proposals.add(new Proposal("\\b", "Word boundary \\b", 1));
			proposals.add(new Proposal("\\B", "Non-word boundary \\B", 1));
			proposals.add(new Proposal("\\d", "Digit \\d", 1));
			proposals.add(new Proposal("\\D", "Non-digit \\D", 1));
			proposals
					.add(new Proposal("\\G", "End of previous match \\G", 1));
			proposals.add(new Proposal("\\n", "Newline \\n", 1));
			proposals.add(new Proposal("\\r", "Carriage-return \\r", 1));
			proposals.add(new Proposal("\\s", "Whitespace \\s", 1));
			proposals.add(new Proposal("\\S", "Non-whitespace \\S", 1));
			proposals.add(new Proposal("\\t", "Tab \\t", 1));
			proposals.add(new Proposal("\\w", "Word \\w", 1));
			proposals.add(new Proposal("\\W", "Non-word \\W", 1));
			proposals.add(new Proposal("\\z", "End of input \\z", 1));

		}

		if (textLeftOfCursor.endsWith("(?")) {
			proposals.add(new Proposal("(?:", "Non-capturing group (?:", 2));
			proposals.add(new Proposal("(?=", "Positive lookahead (?=", 2));
			proposals.add(new Proposal("(?!", "Negative lookahead (?!", 2));
			proposals
					.add(new Proposal("(?<=", "Positive lookbehind (?<=", 2));
			proposals
					.add(new Proposal("(?<!", "Negative lookbehind (?<!", 2));

		}

		if (textLeftOfCursor.endsWith("[")) {
			proposals.add(new Proposal("[0-9]", "[0-9]", 1));
			proposals.add(new Proposal("[a-z]", "[a-z]", 1));
			proposals.add(new Proposal("[A-Z]", "[A-Z]", 1));
			proposals.add(new Proposal("[a-zA-Z]", "[a-zA-Z]", 1));
			proposals.add(new Proposal("[a-zA-Z0-9]", "[a-zA-Z0-9]", 1));
		}
		
		return proposals;
	}
	
}
