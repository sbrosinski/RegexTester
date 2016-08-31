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
package com.brosinski.eclipse.regex.view.actions;

import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;


public class ClearActionMenuCreator implements IMenuCreator {

	Menu menu;
	ClearAction clearAction;
	ClearSelection clearSelection = new ClearSelection();
	
	class ClearSelection extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			MenuItem menuItem = (MenuItem) e.widget;
			boolean selected = menuItem.getSelection();
			int mode=((Integer)menuItem.getData()).intValue();
			
			if (selected) {
				clearAction.setMode(mode);
			}
		}			
	}	
	
	public ClearActionMenuCreator(ClearAction action) {
		clearAction = action;
	}

	public void dispose() {
		if (menu != null) {
			menu.dispose();		
		}
	}


	public Menu getMenu(Control parent) {
		if (menu != null) {
			return menu;
		}
		
		menu = new Menu(parent);

		MenuItem mitClearBoth= new MenuItem(menu,SWT.RADIO);
		mitClearBoth.setData(new Integer(ClearAction.MODE_ALL));
		mitClearBoth.setText("Everything");
		mitClearBoth.addSelectionListener(clearSelection);
		mitClearBoth.setSelection(true);
		
		MenuItem mitClearRegEx = new MenuItem(menu, SWT.RADIO);
		mitClearRegEx.setText("Regular Expression");
		mitClearRegEx.setData(new Integer(ClearAction.MODE_REGEX));
		mitClearRegEx.addSelectionListener(clearSelection);
	
		MenuItem mitClearSearch= new MenuItem(menu,SWT.RADIO);
		mitClearSearch.setData(new Integer(ClearAction.MODE_SEARCH));
		mitClearSearch.setText("Search Text");
		mitClearSearch.addSelectionListener(clearSelection);
		
		MenuItem mitClearResult= new MenuItem(menu,SWT.RADIO);
		mitClearResult.setData(new Integer(ClearAction.MODE_RESULT));
		mitClearResult.setText("Result");
		mitClearResult.addSelectionListener(clearSelection);
		
		
		return menu;
	}


	public Menu getMenu(Menu parent) {
		return null;
	}

}
