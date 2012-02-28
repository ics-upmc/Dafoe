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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;

public class TerminologyRelationTypeMappingControler implements PropertyChangeListener {

	public static final String RTType_SELECTED_EVENT = "RTTypeSelected"; //$NON-NLS-1$
	public static final String RTType_DELETED_EVENT = "RTTypeDeleted"; //$NON-NLS-1$
	public static final String RTCType_SELECTED_EVENT = "RTCTypeSelected"; //$NON-NLS-1$
	public static final String LINKED_RTCType_SELECTED_EVENT = "LinkedRTCTypeSelected"; //$NON-NLS-1$
	public static final String UNLINKED_RTCType_SELECTED_EVENT = "UnlinkedRTCTypeSelected"; //$NON-NLS-1$
	public static final String LINKED_RTType_SELECTED_EVENT = "LinkedRTTypeSelected"; //$NON-NLS-1$
	public static final String UNLINKED_RTType_SELECTED_EVENT = "UnlinkedRTTypeSelected"; //$NON-NLS-1$
	public static final String NEW_RTType_EVENT = "NewRTType"; //$NON-NLS-1$
	public static final String NEW_RTCType_EVENT = "NewRTCType"; //$NON-NLS-1$

	private TerminologyRelationTypeMappingDialog dial;
	private ITypeRelationTermino currentRTType;
	private ITypeRelationTc currentRTCType;
	
	//
	
	public TerminologyRelationTypeMappingControler(TerminologyRelationTypeMappingDialog dial){
		this.dial = dial;
	}

	//
	
	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName().equals(TerminologyRelationTypeMappingControler.RTType_SELECTED_EVENT)) {

			currentRTType = (ITypeRelationTermino)evt.getNewValue();
			
			dial.getRTTypeToRTCTypeWidget().updateContent(currentRTType);
			
			dial.getRTCTypeToRTTypeWidget().createDropTarget();
			
			dial.getRTTypeToRTCTypeWidget().removeDropTarget();
			
			dial.getRTCTypesWidget().removeDropTarget();
			
			dial.getRTCTypesWidget().refresh(currentRTType);
						
		} else if (evt.getPropertyName().equals(TerminologyRelationTypeMappingControler.RTCType_SELECTED_EVENT)) {
		
			currentRTCType = (ITypeRelationTc)evt.getNewValue();
			
			dial.getRTCTypeToRTTypeWidget().updateContent(currentRTCType);
			
			dial.getRTTypeToRTCTypeWidget().createDropTarget();
			
			dial.getRTCTypesWidget().createDropTarget();
			
			dial.getRTCTypeToRTTypeWidget().removeDropTarget();
			
			dial.getRTCTypesWidget().refresh(currentRTType);
			
		} else if (evt.getPropertyName().equals(TerminologyRelationTypeMappingControler.LINKED_RTCType_SELECTED_EVENT)){

			currentRTCType = (ITypeRelationTc)evt.getNewValue();
			
			dial.getRTCTypeToRTTypeWidget().updateContent(currentRTCType);

			dial.getRTCTypesWidget().setSelection(currentRTCType);
			
			dial.getRTCTypesWidget().refresh(currentRTType);
						
		} else if (evt.getPropertyName().equals(TerminologyRelationTypeMappingControler.NEW_RTType_EVENT)){
			
			dial.getRTTypesWidget().refresh();
			
			dial.getRTTypesWidget().expandAll();
			
		} else if (evt.getPropertyName().equals(TerminologyRelationTypeMappingControler.LINKED_RTType_SELECTED_EVENT)){

			currentRTType = (ITypeRelationTermino)evt.getNewValue();
			
			dial.getRTTypeToRTCTypeWidget().updateContent(currentRTType);

			dial.getRTTypesWidget().setSelection(currentRTType);
			
			dial.getRTCTypesWidget().refresh(currentRTType);
			
			
		} else if (evt.getPropertyName().equals(TerminologyRelationTypeMappingControler.NEW_RTCType_EVENT)){
			
			dial.getRTCTypesWidget().refresh();
			
			dial.getRTCTypesWidget().expandAll();
			
		} else if (evt.getPropertyName().equals(TerminologyRelationTypeMappingControler.UNLINKED_RTCType_SELECTED_EVENT)){
			
			dial.getRTCTypeToRTTypeWidget().updateContent(currentRTCType);
			
			dial.getRTCTypesWidget().refresh(currentRTType);
			
		} else if (evt.getPropertyName().equals(TerminologyRelationTypeMappingControler.UNLINKED_RTType_SELECTED_EVENT)){
			
			dial.getRTTypeToRTCTypeWidget().updateContent(currentRTType);
			
			dial.getRTCTypesWidget().refresh(currentRTType);
			
		} else if (evt.getPropertyName().equals(TerminologyRelationTypeMappingControler.RTType_DELETED_EVENT)){
			
			currentRTType = null;
			
			dial.getRTCTypesWidget().refresh(currentRTType);
			
			dial.getRTTypeToRTCTypeWidget().updateContent(currentRTType);
			
			dial.getRTCTypeToRTTypeWidget().updateContent(currentRTCType);
		}
	}

}
