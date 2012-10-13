package com.brosinski.eclipse.regex.view;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class RegisterDialog extends TitleAreaDialog {

	private User user;

	private Text nameText;

	private Text emailText;

	private Text codeText;

	private int countTries = 0;
	
	public RegisterDialog(Shell parentShell) {
		super(parentShell);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		getShell().setText("Register RegEx Tester");
		
		Composite superComposite = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(superComposite, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		
		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Name: ");

		nameText = new Text(composite, SWT.BORDER);
		nameText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		

		Label emailLabel = new Label(composite, SWT.NONE);
		emailLabel.setText("Email: ");

		emailText = new Text(composite, SWT.BORDER);
		emailText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		Label codeLabel = new Label(composite, SWT.NONE);
		codeLabel.setText("Code: ");

		codeText = new Text(composite, SWT.BORDER);
		codeText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		
		setMessage("Thank you for registering RegEx Tester Pro.");
		setTitleAreaColor(new RGB(255, 255, 255));
		return composite;
	}

	protected void okPressed() {
		if (countTries == 3) {
			cancelPressed();
		}
		if (nameText.getText() == null || nameText.getText().equals("")) {
			setErrorMessage("Please enter your name!");
			return;
		}
		if (emailText.getText() == null || emailText.getText().equals("")) {
			setErrorMessage("Please enter your email address!");
			return;
		}
		if (codeText.getText() == null || codeText.getText().equals("")) {
			setErrorMessage("Please enter your registration code!");
			return;
		}
		user = new User(nameText.getText(), emailText.getText(), codeText.getText());
		if (Registration.isUserValid(user)) {
			super.okPressed();
		} else {
			countTries++;
			setErrorMessage("Invalid Registration Code!");
		}
	}

	public User getUser() {
		return user;
	}

}