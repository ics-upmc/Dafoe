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

package org.dafoe.terminologiclevel.terminologycard.dialogs;



import java.util.ArrayList;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermRelationImpl;
import org.dafoe.terminologiclevel.saillance.dialog.TermDialogSelector;
import org.dafoe.terminologiclevel.terminologycard.Messages;
import org.dafoe.terminologiclevel.terminologycard.TerminologicRelationAreaViewPart;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TerminologicRelationCreatorDialog extends TitleAreaDialog {
	private String title;
	private String message;

	private ITermRelation newRelation;
	private TerminologicRelationAreaViewPart viewPart;
	
	private ITerm term1, term2, currentTerm;

	private ComboViewer terminologicTypeCombo;
	private Text term1Text, term2Text;
	private Button okButton;
	
	public TerminologicRelationCreatorDialog(TerminologicRelationAreaViewPart viewPart){
		super(TerminologicRelationAreaViewPart.shell);
		this.title = Messages.getString("TerminologicRelationCreatorDialog.0"); //$NON-NLS-1$
		this.message = Messages.getString("TerminologicRelationCreatorDialog.1"); //$NON-NLS-1$
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		this.viewPart = viewPart;
		initNewRelation();
	}
		
	public boolean close(){
		return super.close();
	}
	
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		this.setTitle(this.title);
		this.setMessage(this.message);
		this.getShell().setText(this.title);
		return contents;
	}

    protected Control createDialogArea(Composite parent) {

    	currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
    	
    	Composite generalComposite = (Composite) super.createDialogArea(parent);
    	GridLayout layout = new GridLayout();
    	layout.numColumns = 1;
    	generalComposite.setLayout(layout);
    	
    	Composite composite = new Composite (generalComposite, SWT.NONE);
		GridData myGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		myGridData.verticalIndent = 10;
  	    layout = new GridLayout();
    	layout.numColumns = 3;
    	layout.makeColumnsEqualWidth = false;
    	composite.setLayout(layout);
    	composite.setLayoutData(myGridData);
    	
    	//
		// term 1 
    	//
    	
		myGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		myGridData.horizontalIndent = 15;
		Label term1Label = new Label(composite, SWT.NULL);
		term1Label.setText(Messages.getString("TerminologicRelationCreatorDialog.2")); //$NON-NLS-1$
		term1Label.setLayoutData(myGridData);

		term1Text = new Text(composite, SWT.BORDER);
		myGridData = new GridData(GridData.FILL_HORIZONTAL);	
		term1Text.setLayoutData(myGridData);
		term1Text.setEditable(true);
		term1Text.setEnabled(true);
    	
    	Button term1Button = new Button(composite, SWT.PUSH);
    	myGridData = new GridData(GridData.FILL_HORIZONTAL);
    	term1Button.setLayoutData(myGridData);
    	term1Button.setText(Messages.getString("TerminologicRelationCreatorDialog.3")); //$NON-NLS-1$
    	term1Button.addSelectionListener(new SelectionAdapter() {
	          public void widgetSelected(SelectionEvent e) {
	        	  	TermDialogSelector dial;
	        	  	List<ITerm> termFamily = getTermFamily(currentTerm);
	        	  	
	        	  	if (term2 != null){
	        	  		
	        	  		if (termFamily.contains(term2)){
	        	  			List<ITerm> terms = DatabaseAdapter.getTerms();
	        	  			terms.removeAll(termFamily);
	        	  			dial = new TermDialogSelector(TerminologicRelationAreaViewPart.shell, terms);
	        	  			
	        	  		} else {
	        	  			
	        	  			dial = new TermDialogSelector(TerminologicRelationAreaViewPart.shell, termFamily);
	        	  			
	        	  		}
	        	  		
	        	  	} else {
	        	  		
	        	  		dial = new TermDialogSelector(TerminologicRelationAreaViewPart.shell, DatabaseAdapter.getTerms());
	        	  		
	        	  	}
	        	  
	        	  	term1 = dial.getSelection();

	        	  	if (term1 != null){

	        	  		newRelation.setTerm1(term1);
	        	  		term1Text.setText(term1.getLabel());
	        	  		okButton.setEnabled(TerminologicRelationCreatorDialog.this.computeOKButtonEnabled());
	        	  	}
	            };
	            
	         });

    	//
    	// type relation
    	//
		
    	myGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    	myGridData.horizontalIndent = 15;
		Label termRelationTypeLabel = new Label(composite, SWT.NULL);
		termRelationTypeLabel.setText(Messages.getString("TerminologicRelationCreatorDialog.5")); //$NON-NLS-1$
		termRelationTypeLabel.setLayoutData(myGridData);
		
		terminologicTypeCombo = new ComboViewer(composite);
		terminologicTypeCombo.setContentProvider(new ArrayContentProvider());
		
		terminologicTypeCombo.setLabelProvider(new LabelProvider(){
			public String getText(Object element){
				if(element instanceof ITypeRelationTermino)
					
					return ((ITypeRelationTermino)element).getLabel();
				
				return super.getText(element);
			}
		});
		
		terminologicTypeCombo.setInput(DatabaseAdapter.getRelationTypes());
		
		myGridData = new GridData(GridData.FILL_HORIZONTAL);	
		terminologicTypeCombo.getCombo().setLayoutData(myGridData);
		
		terminologicTypeCombo.addSelectionChangedListener(new ISelectionChangedListener(){

			public void selectionChanged(SelectionChangedEvent event) {
				ISelection comboSelection = terminologicTypeCombo.getSelection();
				
				if (comboSelection != null && !comboSelection.isEmpty() && comboSelection instanceof IStructuredSelection){
					ITypeRelationTermino typeRelation = (ITypeRelationTermino)((IStructuredSelection)comboSelection).getFirstElement();
					//typeRelation.addTermRelation(newRelation);
					
					newRelation.setTypeRel(typeRelation);
	    	  		okButton.setEnabled(TerminologicRelationCreatorDialog.this.computeOKButtonEnabled());
				}
			}
			
		});
		
		
    	Button terminologicTypeButton = new Button(composite, SWT.PUSH);
    	myGridData = new GridData(GridData.FILL_HORIZONTAL);
    	terminologicTypeButton.setLayoutData(myGridData);
    	terminologicTypeButton.setText(Messages.getString("TerminologicRelationCreatorDialog.6")); //$NON-NLS-1$
    	terminologicTypeButton.addSelectionListener(new SelectionAdapter() {
	          public void widgetSelected(SelectionEvent e) {
	        	  ITypeRelationTermino relationType = createNewRelationType();
	        	  if (relationType != null){
	        		  relationType.addTermRelation(newRelation);
	        		  //newRelation.setTypeRel(relationType);
	        		  updateRelationTypeComboBoxcontent();
	        		  terminologicTypeCombo.setSelection(new StructuredSelection(relationType));
	        		  okButton.setEnabled(TerminologicRelationCreatorDialog.this.computeOKButtonEnabled());
	        	  }
	        	  
	            };
	         });
		
    	//
		// term 2
    	//
       	
		myGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		myGridData.horizontalIndent = 15;
		Label term2Label = new Label(composite, SWT.NULL);
		term2Label.setText(Messages.getString("TerminologicRelationCreatorDialog.8")); //$NON-NLS-1$
		term2Label.setLayoutData(myGridData);

		term2Text = new Text(composite, SWT.BORDER);
		myGridData = new GridData(GridData.FILL_HORIZONTAL);	
		term2Text.setLayoutData(myGridData);
		term2Text.setEditable(true);
		term2Text.setEnabled(true);
    	
    	Button term2Button = new Button(composite, SWT.PUSH);
    	myGridData = new GridData(GridData.FILL_HORIZONTAL);
    	term2Button.setLayoutData(myGridData);
    	term2Button.setText(Messages.getString("TerminologicRelationCreatorDialog.9")); //$NON-NLS-1$
    	term2Button.addSelectionListener(new SelectionAdapter() {
	          public void widgetSelected(SelectionEvent e) {
	        	  	TermDialogSelector dial;
	        	  	List<ITerm> termFamily = getTermFamily(currentTerm);
	        	  	
	        	  	if (term1 != null){
	        	  		
	        	  		if (termFamily.contains(term1)){
	        	  			List<ITerm> terms = DatabaseAdapter.getTerms();
	        	  			terms.removeAll(termFamily);
	        	  			dial = new TermDialogSelector(TerminologicRelationAreaViewPart.shell, terms);
	        	  			
	        	  		} else {
	        	  			
	        	  			dial = new TermDialogSelector(TerminologicRelationAreaViewPart.shell, termFamily);
	        	  			
	        	  		}
	        	  		
	        	  	} else {
	        	  		
	        	  		dial = new TermDialogSelector(TerminologicRelationAreaViewPart.shell, DatabaseAdapter.getTerms());
	        	  		
	        	  	}
	        	  
	        	  	term2 = dial.getSelection();

	        	  	if (term2 != null){

	        	  		newRelation.setTerm2(term2);
	        	  		term2Text.setText(term2.getLabel());
	        	  		okButton.setEnabled(TerminologicRelationCreatorDialog.this.computeOKButtonEnabled());
	        	  	}
	          }
    	});
   	
    	return composite;
    }

	public void createButtonsForButtonBar(Composite parent){
		okButton = this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);
		
		this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	private boolean computeOKButtonEnabled(){
		boolean res = false;
		res = (newRelation.getTerm1() != null) && (newRelation.getTypeRel() != null) && (newRelation.getTerm2() != null);
		return res;
	}
	
	private void initNewRelation(){
		
		this.newRelation = new TermRelationImpl();
		
		this.newRelation.setState(TERMINO_OBJECT_STATE.VALIDATED);
				
	}
	
	public void updateRelationTypeComboBoxcontent(){
		terminologicTypeCombo.setInput(DatabaseAdapter.getRelationTypes());
	}
	
	public ITermRelation getCreatedRelation() {
		
		return newRelation;
		
	}
	
	private ITypeRelationTermino createNewRelationType(){
		ITypeRelationTermino relationType = null;
		
		TerminologyRelationTypeCreatorDialog dialog = new TerminologyRelationTypeCreatorDialog(this.getShell());
		int res = dialog.open();

		if (res == IDialogConstants.OK_ID) {
			
			relationType = dialog.getSelection();

		}

		return relationType;
		
	}
	
	private List<ITerm> getTermFamily(ITerm term){
		List<ITerm> res = new ArrayList<ITerm>();
		
		if (term.getStarTerm() == null) {
			res.add(term);
			res.addAll(term.getVariantes());
		} else {
			ITerm starTerm = term.getStarTerm();
			res.add(starTerm);
			res.addAll(starTerm.getVariantes());
		}
		
		return res;
	}

}
