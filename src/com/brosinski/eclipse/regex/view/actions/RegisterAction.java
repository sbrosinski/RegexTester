package com.brosinski.eclipse.regex.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;

import com.brosinski.eclipse.regex.view.RegExView;
import com.brosinski.eclipse.regex.view.RegisterDialog;
import com.brosinski.eclipse.regex.view.UserDAO;


public class RegisterAction extends Action {

	private RegExView view;
	
	public RegisterAction(RegExView view) {
		setText("Register");
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		RegisterDialog registerDialog = new RegisterDialog(view.getViewSite().getShell());
		if (registerDialog.open() == Window.OK) {
		    UserDAO.save(registerDialog.getUser());
		}
	}
	
}
