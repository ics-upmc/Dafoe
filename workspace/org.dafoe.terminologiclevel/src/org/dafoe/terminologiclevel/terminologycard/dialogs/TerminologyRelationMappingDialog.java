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

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;



public class TerminologyRelationMappingDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages.getString("TerminologyRelationMappingDialog.0"); //$NON-NLS-1$
	private static String DIALOG_MESSAGE = Messages.getString("TerminologyRelationMappingDialog.1"); //$NON-NLS-1$
	
	private String message;
	private String title;
	private List<ITypeRelationTermino> relationTypes;
	private ITypeRelationTermino currentRelationType;
	
	private ITermRelation currentRT;
	
	private CurrentRTWidget currentRTWidget;
	private CurrentRTCWidget currentRTC;
	private TCWidget arg1TC, arg2TC;
	private RTCRelationsWidget existingRTCs;
	private RTCTypeWidget rtcTypes;
	private OccurrencesWidget sentences;
	
	private TerminologyRelationMappingControler controler;
	
	public TerminologyRelationMappingDialog(Shell parent){
		super(parent);
		setShellStyle(SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE | SWT.CLOSE | SWT.MAX);
		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		this.controler = new TerminologyRelationMappingControler(this);
		currentRT = ControlerFactoryImpl.getTerminologyControler().getTermRelation();
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
		composite.setLayout(new GridLayout(4, false));
		
		// Current RT
		
		currentRTWidget = new CurrentRTWidget(composite, SWT.NONE);
		currentRTWidget.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		
		// New RTC
    	
		currentRTC = new CurrentRTCWidget(composite, SWT.NONE);
		currentRTC.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));
		currentRTC.setLinkEventType(TerminologyRelationMappingControler.LINK_RTC_EVENT);
		currentRTC.setUnlinkEventType(TerminologyRelationMappingControler.UNLINK_RTC_EVENT);
		currentRTC.addPropertyChangeListener(this.controler);

		// RT occurences
		
		sentences = new OccurrencesWidget(composite, SWT.NONE);
		sentences.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		sentences.setLayout(new GridLayout(1, false));
		
		// Existing RTCs
		
		existingRTCs = new RTCRelationsWidget(composite, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd.minimumHeight = 250;
		existingRTCs.setLayoutData(gd);
		existingRTCs.setLayout(new GridLayout(1, false));	
		existingRTCs.setEventType(TerminologyRelationMappingControler.NEW_RTC_SELECTED_EVENT);
		existingRTCs.addPropertyChangeListener(this.controler);
		
		// TCs for RTC Arg1
		
		arg1TC = new TCWidget(composite, SWT.NONE, Messages.getString("TerminologyRelationMappingDialog.2"), currentRT.getTerm1()); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		arg1TC.setLayoutData(gd);
		arg1TC.setEventType(TerminologyRelationMappingControler.TC_ARG1_SELECTED_EVENT);
		arg1TC.addPropertyChangeListener(this.controler);

		// RTC types
		
		rtcTypes = new RTCTypeWidget(composite, SWT.NONE, currentRT.getTypeRel());
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		rtcTypes.setLayoutData(gd);
		rtcTypes.setEventType(TerminologyRelationMappingControler.RTC_TYPE_SELECTED_EVENT);
		rtcTypes.addPropertyChangeListener(this.controler);		
				
		// TCs for RTC Arg2
		
		arg2TC = new TCWidget(composite, SWT.NONE, Messages.getString("TerminologyRelationMappingDialog.3"), currentRT.getTerm2()); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		arg2TC.setLayoutData(gd);
		arg2TC.setEventType(TerminologyRelationMappingControler.TC_ARG2_SELECTED_EVENT);		
		arg2TC.addPropertyChangeListener(this.controler);
		
		this.controler.initNewRTCInformationAreas();
		
		makeActions();
		
		hookPopupMenu();

		return composite;

    }

    private void updateInformationAreas(){
    	
    }
    
    public CurrentRTCWidget getCurrentRTCWidget(){
    	return this.currentRTC;
    }
    
    public RTCRelationsWidget getExistingRTCsWidget(){
    	return this.existingRTCs;
    }
   
    public TCWidget getTCArg1Widget(){
    	return arg1TC;
    }
    
    public TCWidget getTCArg2Widget(){
    	return arg2TC;
    }
    
    public RTCTypeWidget getRTCTypeWidget(){
    	return rtcTypes;
    }

    public List<ITypeRelationTermino> createData(){
    	relationTypes = DatabaseAdapter.getRelationTypes();
    	return relationTypes;
    }
    
	public void createButtonsForButtonBar(Composite parent){
		this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	public ITypeRelationTermino getSelection(){
		return this.currentRelationType;
	}

	
	
	
	//
	
	class AddButtonListener implements Listener {


		public void handleEvent(Event event) {

		}
	}
	
	//
	
	class RemoveButtonListener implements Listener {


		public void handleEvent(Event event) {
		}
	}
	
	//
	
	class UpdateListener implements IDoubleClickListener {

		public void doubleClick(DoubleClickEvent event) {

				
		}
		
	}

	//
	
	private void makeActions(){
	

	
	}
	
	private void hookPopupMenu(){
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		//Menu menu = menuMgr.createContextMenu(treeViewer.getTree());
	}


}
