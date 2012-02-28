/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/
package org.dafoe.application.preferences;

import org.dafoe.application.Activator;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class DataBaseSettingsPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {
	
	StringFieldEditor fieldEditorDbName;
	StringFieldEditor fieldEditorUserName;
	StringFieldEditor fieldEditorPassword;
	
	
	public final static String PREFERENCE_DATABASE_NAME = "database";
	public final static String PREFERENCE_USER_NAME = "user";
	public final static String PREFERENCE_PASSWORD = "password";

	public DataBaseSettingsPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Configure database connection properties");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		fieldEditorDbName= new StringFieldEditor("", "DataBaseName:", getFieldEditorParent());
		fieldEditorUserName=  new StringFieldEditor("", "UserName:", getFieldEditorParent());
		fieldEditorPassword=  new StringFieldEditor("", "Password:", getFieldEditorParent());
		
		
		
		/*addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"&Directory preference:", getFieldEditorParent()));
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN,
				"&An example of a boolean preference",
				getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_CHOICE,
			"An example of a multiple-choice preference",
			1,
			new String[][] { { "&Choice 1", "choice1" }, {
				"C&hoice 2", "choice2" }
		}, getFieldEditorParent()));
		
		addField(
			new StringFieldEditor(PreferenceConstants.P_STRING, "A &text preference:", getFieldEditorParent()));
			*/
		
		addField(fieldEditorDbName);
		addField(fieldEditorUserName);
		addField(fieldEditorPassword);
		
		/*
		
		Composite generalComposite = new Composite(getFieldEditorParent(), SWT.NONE);	
		GridLayout generalLayout = new GridLayout();
		generalLayout.numColumns = 2;		
		generalComposite.setLayout(generalLayout);
						
				GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.horizontalIndent = 40;
				Label label = new Label(generalComposite, SWT.NULL);
				label.setText("Password :"); //$NON-NLS-1$
				label.setLayoutData(gridData);
				
				Text txtPassWord= new Text(generalComposite, SWT.BORDER);
				gridData = new GridData(GridData.FILL_HORIZONTAL);	
				txtPassWord.setLayoutData(gridData);
				txtPassWord.setEditable(true);
				txtPassWord.setEnabled(true);
				txtPassWord.setEchoChar('*');
				txtPassWord.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		
		*/
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	/*
	@Override
	public boolean performOk() {
		IPreferenceStore prefStore = Activator.getDefault()
		.getPreferenceStore();
		
		// TODO Auto-generated method stub
		boolean isInitialized=false;
		String dburl="jdbc:postgresql://localhost:5432/"+fieldEditorDbName.getStringValue();
		String userName=""+fieldEditorUserName.getStringValue();
		String password=""+fieldEditorPassword.getStringValue();
		
		isInitialized= org.dafoe.application.datasourceaccess.Activator.initDataSource(dburl,userName,password);
		
		System.out.println("---------- init DB= "+isInitialized);
		
		// Display the message box
		
		if(isInitialized){
		// save preference
			prefStore.setValue(PREFERENCE_DATABASE_NAME, dburl);
			prefStore.setValue(PREFERENCE_USER_NAME, userName);
			prefStore.setValue(PREFERENCE_PASSWORD, password);
			
        MessageBox mb = new MessageBox(Activator.getDefault().getWorkbench().getDisplay().getActiveShell(), SWT.ICON_INFORMATION);
        //mb.setText("Message from SWT");
        mb.setMessage("Connection Succesfully to database");
        int val = mb.open();
		}
		
		if(!isInitialized){
	        MessageBox mb = new MessageBox(Activator.getDefault().getWorkbench().getDisplay().getActiveShell(),SWT.ICON_ERROR);
	        //mb.setText("Message from SWT");
	        mb.setMessage("Unable to connect the database");
	        int val = mb.open();
			}
		
		return super.performOk();
	}
	
	@Override
	protected void performApply() {
		// TODO Auto-generated method stub
		
		
		super.performApply();
		
	}
	*/
}