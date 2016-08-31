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

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.brosinski.eclipse.regex.RegExModel;


public class CopyDeclarationAdapter extends SelectionAdapter {

	public void widgetSelected(SelectionEvent e) {
		String text = "Pattern.compile(\"" + RegExModel.getInstance().getRegExAsLiteral();
		if (RegExModel.getInstance().getPatternFlags() == 0)
			text += "\");";
		else
			text += "\"," + RegExModel.getInstance().getPatternFlagsAsString() + ");";
		Clipboard clipboard = new Clipboard(e.display);
		TextTransfer textTransfer = TextTransfer.getInstance();
		clipboard.setContents(new Object[]{text},
				new Transfer[]{textTransfer});
		clipboard.dispose();

	}
	
}
