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




import java.util.List;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TerminologyRelationTypeMappingDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages.getString("TerminologyRelationTypeMappingDialog.0"); //$NON-NLS-1$
	private static String DIALOG_MESSAGE = Messages.getString("TerminologyRelationTypeMappingDialog.1");	 //$NON-NLS-1$
	
	private RTCTypeWidget rtcTypes;
	private RTTypeWidget rtTypes;
	private RTTypeToRTCTypeWidget rtTypeToRTCType;
	private RTCTypeToRTTypeWidget rtcTypeToRTType;
	
	private String title;
	private String message;
	private List<ITypeRelationTermino> relationTypes;
	
	private TerminologyRelationTypeMappingControler controler;
	
	public TerminologyRelationTypeMappingDialog(Shell parent){
		super(parent);
		setShellStyle(SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE);
		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		this.controler = new TerminologyRelationTypeMappingControler(this);
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
 
    	GridData gd ;
    	
		Composite generalComposite = (Composite) super.createDialogArea(parent);
		generalComposite.setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(generalComposite, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setLayout(new GridLayout(3, true));
		
		rtTypes = new RTTypeWidget(composite, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		gd.minimumHeight = 250;
		rtTypes.setLayoutData(gd);
		rtTypes.setSelectionEventType(TerminologyRelationTypeMappingControler.RTType_SELECTED_EVENT);
		rtTypes.setDeletionEventType(TerminologyRelationTypeMappingControler.RTType_DELETED_EVENT);
		rtTypes.addPropertyChangeListener(controler);
		
		rtTypeToRTCType = new RTTypeToRTCTypeWidget(composite, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		rtTypeToRTCType.setLayoutData(gd);
		rtTypeToRTCType.setNewRTCTypeEventType(TerminologyRelationTypeMappingControler.NEW_RTCType_EVENT);
		rtTypeToRTCType.setRTCTypeSelectionEventType(TerminologyRelationTypeMappingControler.LINKED_RTCType_SELECTED_EVENT);
		rtTypeToRTCType.setUnlinkRTCTypeEventType(TerminologyRelationTypeMappingControler.UNLINKED_RTCType_SELECTED_EVENT);
		rtTypeToRTCType.addPropertyChangeListener(controler);
		
		rtcTypes = new RTCTypeWidget(composite, SWT.NONE, null);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		gd.minimumHeight = 250;
		rtcTypes.setLayoutData(gd);
		rtcTypes.setEventType(TerminologyRelationTypeMappingControler.RTCType_SELECTED_EVENT);
		rtcTypes.addPropertyChangeListener(this.controler);		
		
		rtcTypeToRTType = new RTCTypeToRTTypeWidget(composite, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		rtcTypeToRTType.setLayoutData(gd);
		rtcTypeToRTType.setNewRTTypeEventType(TerminologyRelationTypeMappingControler.NEW_RTType_EVENT);
		rtcTypeToRTType.setRTTypeSelectionEventType(TerminologyRelationTypeMappingControler.LINKED_RTType_SELECTED_EVENT);
		rtcTypeToRTType.setUnlinkRTTypeEventType(TerminologyRelationTypeMappingControler.UNLINKED_RTType_SELECTED_EVENT);
		rtcTypeToRTType.addPropertyChangeListener(controler);

		return composite;

    }

    //
    
    public RTTypeWidget getRTTypesWidget() {
    	return this.rtTypes;
    }
    
    //
    
    public RTCTypeWidget getRTCTypesWidget() {
    	return this.rtcTypes;
    }

    //
    
    public RTCTypeToRTTypeWidget getRTCTypeToRTTypeWidget() {
    	return this.rtcTypeToRTType;
    }

    //
    
    public RTTypeToRTCTypeWidget getRTTypeToRTCTypeWidget() {
    	return this.rtTypeToRTCType;
    }

    //
    
    public List<ITypeRelationTermino> createData(){
    	relationTypes = DatabaseAdapter.getRelationTypes();
    	return relationTypes;
    }
    
    //
    
	public void createButtonsForButtonBar(Composite parent){
		this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
		
}
