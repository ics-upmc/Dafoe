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

package org.dafoe.terminologiclevel.saillance.dialog;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.dafoe.terminologiclevel.saillance.RegularExpressionSaillanceFilter;
import org.dafoe.terminologiclevel.saillance.SaillanceViewPart;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FilterRegularExpressionDialog extends TitleAreaDialog {
	
	private static String DIALOG_TITLE = Messages.getString("FilterRegularExpressionDialog.0"); //$NON-NLS-1$
	private static String DIALOG_MESSAGE = Messages.getString("FilterRegularExpressionDialog.1"); //$NON-NLS-1$
//	private static String TERM_LABEL = Messages.getString("FilterRegularExpressionDialog.2"); //$NON-NLS-1$
//	private static String LINGUISTIC_STATUS_LABEL = Messages.getString("FilterRegularExpressionDialog.3"); //$NON-NLS-1$

	private String title;
	private String message;
	
	private Text regExpText;
	private Button showButton;
	private Button okButton;
	private String regularExpression;
	private boolean caseSensitive;
	private boolean wholeString;
	
	private Shell parentShell;
	private SaillanceViewPart viewPart;
	private RegularExpressionSaillanceFilter termFilter, oldTermFilter;
	
	public FilterRegularExpressionDialog(Shell parent, SaillanceViewPart viewPart){
		super(parent);
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;
		this.caseSensitive = false;
		this.wholeString = false;
		this.viewPart = viewPart;
		this.parentShell = parent;
		this.oldTermFilter = this.viewPart.getRegularExpressionTermFilter();
	}
		
	public boolean close(){
		return super.close();
	}
	
	protected Control createContents(Composite parent) {
		
		Control contents = super.createContents(parent);
		this.setTitle(DIALOG_TITLE);
		this.setMessage(DIALOG_MESSAGE);
		this.getShell().setText(DIALOG_TITLE);
		return contents;
	}

    protected Control createDialogArea(Composite parent) {

    	Composite generalComposite = (Composite) super.createDialogArea(parent);
       	
    	Composite composite = new Composite(generalComposite, SWT.NONE);
    	composite.setLayout(new GridLayout(4, true));
       	
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Label regLabel = new Label(composite, SWT.NONE);
		regLabel.setText(Messages.getString("FilterRegularExpressionDialog.4")); //$NON-NLS-1$
		
		regExpText = new Text(composite, SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		layoutData.horizontalSpan = 3;
		regExpText.setLayoutData(layoutData);

		regExpText.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
				okButton.setEnabled(false);
			}

			public void mouseUp(MouseEvent e) {
			}
			
		});
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		new Label(composite, SWT.NONE);
				
		Button caseSensitiveCheckBox = new Button(composite, SWT.CHECK);
		caseSensitiveCheckBox.setText(Messages.getString("FilterRegularExpressionDialog.5")); //$NON-NLS-1$
		layoutData = new GridData(GridData.END, GridData.CENTER, true, false);
		caseSensitiveCheckBox.setLayoutData(layoutData);
		
		caseSensitiveCheckBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
					caseSensitive = !caseSensitive;
				}
			});


		Button wholeStringCheckBox = new Button(composite, SWT.CHECK);
		wholeStringCheckBox.setText(Messages.getString("FilterRegularExpressionDialog.6")); //$NON-NLS-1$
		layoutData = new GridData(GridData.END, GridData.CENTER, true, false);
		wholeStringCheckBox.setLayoutData(layoutData);
		
		wholeStringCheckBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
					wholeString = !wholeString;
				}
			});

		return composite;  
    }

	public void createButtonsForButtonBar(Composite parent){
		showButton = this.createButton(parent, 10000, Messages.getString("FilterRegularExpressionDialog.7"), true); //$NON-NLS-1$
		
		showButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			
				applyFilter();
				
			}
		});
		
		
		okButton = this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
		okButton.setEnabled(false);
	
		Button cancelButton = this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
		
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (termFilter != null) {
					viewPart.removeRegularExpressionFilter();
				}	
			}
		});
		
	}
	
	private void displayError(){
		Color errorForegroundColor = parentShell.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
		Color errorBackroundColor = parentShell.getDisplay().getSystemColor(SWT.COLOR_RED);
		regExpText.setForeground(errorForegroundColor);
		regExpText.setBackground(errorBackroundColor);
	}
	
	private boolean applyFilter(){
		try {
			Pattern pattern;
			
			regularExpression = regExpText.getText();
			
			if (!caseSensitive) {

				pattern = Pattern.compile(regularExpression, Pattern.CASE_INSENSITIVE);
				
			} else {
				
				pattern = Pattern.compile(regularExpression);
				
			}
			
			viewPart.removeRegularExpressionFilter();
			
			viewPart.createRegularExpressionFilter(pattern, wholeString);
			
			this.okButton.setEnabled(true);
			
			return true;
			
		} catch (PatternSyntaxException ex) {
			displayError();
			this.okButton.setEnabled(false);
			return false;
		}
	}
		
}
