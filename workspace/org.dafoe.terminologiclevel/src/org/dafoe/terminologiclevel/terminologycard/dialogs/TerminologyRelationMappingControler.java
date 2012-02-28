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
import java.util.Iterator;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;

public class TerminologyRelationMappingControler implements PropertyChangeListener{

	public static final String TC_ARG1_SELECTED_EVENT = "TCArg1Selected"; //$NON-NLS-1$
	public static final String TC_ARG2_SELECTED_EVENT = "TCArg2Selected"; //$NON-NLS-1$
	public static final String NEW_RTC_SELECTED_EVENT = "NewRTCSelected"; //$NON-NLS-1$
	public static final String RTC_TYPE_SELECTED_EVENT = "RTCTypeSelected"; //$NON-NLS-1$
	public static final String LINK_RTC_EVENT = "LinkRTC"; //$NON-NLS-1$
	public static final String UNLINK_RTC_EVENT = "UnlinkRTC"; //$NON-NLS-1$
	
	private TerminologyRelationMappingDialog dial;
	private BinaryTCRelation currentRTC;
	private ITermRelation currentRT;
	
	public TerminologyRelationMappingControler(TerminologyRelationMappingDialog dial){
		this.dial = dial;
		currentRTC = new BinaryTCRelation();
		currentRT = ControlerFactoryImpl.getTerminologyControler().getTermRelation();
		
	}
	

	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName().equals(TerminologyRelationMappingControler.TC_ARG1_SELECTED_EVENT)) {
			
			ITerminoConcept tc = (ITerminoConcept)evt.getNewValue();
			currentRTC.setTc1(tc);
			dial.getCurrentRTCWidget().getTC1Text().setText(tc.getLabel());
			dial.getExistingRTCsWidget().refresh(currentRTC);
					
			BinaryTCRelation sel = dial.getExistingRTCsWidget().getSelection();
			
			// A selection exists
			if (sel != null) {
				// if the selected tc is different from arg1 => unselect
				if (tc != sel.getTc1()) {
					dial.getExistingRTCsWidget().deselect();
				}
				
			}

			updateRTCsSelection();
			updateCurrentRTCButtonStatus();

		} else if (evt.getPropertyName().equals(TerminologyRelationMappingControler.TC_ARG2_SELECTED_EVENT)) {
			
			ITerminoConcept tc = (ITerminoConcept)evt.getNewValue();
			currentRTC.setTc2(tc);
			dial.getCurrentRTCWidget().getTC2Text().setText(tc.getLabel());
			dial.getExistingRTCsWidget().refresh(currentRTC);

			BinaryTCRelation sel = dial.getExistingRTCsWidget().getSelection();
			
			// A selection exists
			if (sel != null) {
				// if the selected tc is different from arg2 => unselect
				if (tc != sel.getTc2()) {
					dial.getExistingRTCsWidget().deselect();
				}
			} 
				
			updateRTCsSelection();
			updateCurrentRTCButtonStatus();
				
		} else if (evt.getPropertyName().equals(TerminologyRelationMappingControler.RTC_TYPE_SELECTED_EVENT)) {

			ITypeRelationTc rtcType = (ITypeRelationTc)evt.getNewValue();
			currentRTC.setType(rtcType);
			dial.getCurrentRTCWidget().getRTCTypeText().setText(rtcType.getName());
			dial.getExistingRTCsWidget().refresh(currentRTC);
			BinaryTCRelation sel = dial.getExistingRTCsWidget().getSelection();
			
			// A selection exists
			if (sel != null) {
				// if the selected rtc type is different from  type => unselect
				if (rtcType != sel.getType()) {
					dial.getExistingRTCsWidget().deselect();
				}
			} 
			
			updateRTCsSelection();
			updateCurrentRTCButtonStatus();
					
		} else if (evt.getPropertyName().equals(TerminologyRelationMappingControler.NEW_RTC_SELECTED_EVENT)) {
			
			BinaryTCRelation rtc = (BinaryTCRelation)evt.getNewValue();
			dial.getCurrentRTCWidget().getTC1Text().setText(rtc.getTc1().getLabel());
			dial.getCurrentRTCWidget().getTC2Text().setText(rtc.getTc2().getLabel());
			dial.getCurrentRTCWidget().getRTCTypeText().setText(rtc.getType().getName());
			
			dial.getTCArg1Widget().removePropertyChangeListener(this);
			dial.getTCArg2Widget().removePropertyChangeListener(this);
			dial.getRTCTypeWidget().removePropertyChangeListener(this);
			
			dial.getTCArg1Widget().setSelection(rtc.getTc1());
			dial.getTCArg2Widget().setSelection(rtc.getTc2());
			dial.getRTCTypeWidget().setSelection(rtc.getType());
			
			dial.getTCArg1Widget().addPropertyChangeListener(this);
			dial.getTCArg2Widget().addPropertyChangeListener(this);
			dial.getRTCTypeWidget().addPropertyChangeListener(this);

			currentRTC.setTc1(rtc.getTc1());
			currentRTC.setTc2(rtc.getTc2());
			currentRTC.setType(rtc.getType());
			currentRTC.setOrigin(rtc.getOrigin());
			
			updateCurrentRTCButtonStatus();
			
		} else if (evt.getPropertyName().equals(TerminologyRelationMappingControler.LINK_RTC_EVENT)) {
			
			ITerminoConceptRelation tcr = null;
			
			if (dial.getExistingRTCsWidget().getSelection() == null) {
				
				tcr = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.createRTC(currentRTC);
				
				currentRTC.setOrigin(tcr);
				
				dial.getTCArg1Widget().updateContent();
				dial.getRTCTypeWidget().refresh();
				dial.getTCArg2Widget().updateContent();
				
			} 
			
			org.dafoe.terminoontologiclevel.common.DatabaseAdapter.linkRTCWithRT(currentRTC, currentRT);
			
			dial.getExistingRTCsWidget().updateInformation();
			
			dial.getCurrentRTCWidget().getTC1Text().setText(""); //$NON-NLS-1$
			dial.getCurrentRTCWidget().getTC2Text().setText(""); //$NON-NLS-1$
			dial.getCurrentRTCWidget().getRTCTypeText().setText(""); //$NON-NLS-1$
			
			currentRTC = new BinaryTCRelation();

			dial.getCurrentRTCWidget().setLinkEnabled(false);
			dial.getCurrentRTCWidget().setUnlinkEnabled(true);

		} else if (evt.getPropertyName().equals(TerminologyRelationMappingControler.UNLINK_RTC_EVENT)) {
			
			org.dafoe.terminoontologiclevel.common.DatabaseAdapter.unlinkRT(currentRTC, currentRT);
			
			dial.getExistingRTCsWidget().updateInformation();
			
			dial.getCurrentRTCWidget().getTC1Text().setText(""); //$NON-NLS-1$
			dial.getCurrentRTCWidget().getRTCTypeText().setText(""); //$NON-NLS-1$
			dial.getCurrentRTCWidget().getTC2Text().setText(""); //$NON-NLS-1$
			
			dial.getCurrentRTCWidget().setUnlinkEnabled(false);

			dial.getTCArg1Widget().updateContent();
			dial.getRTCTypeWidget().refresh();
			dial.getTCArg2Widget().updateContent();

		}
		
	}
	
	
	// if the independent selection of RTC1,RTCType and RTC2 results to an existing relation definition,
	// returns the corresponding TCRelation, otherwise returns null
	
	private BinaryTCRelation checkRTCExistency(BinaryTCRelation rel){
		ITerminoConcept tc1 = rel.getTc1();
		ITypeRelationTc type = rel.getType();
		ITerminoConcept tc2 = rel.getTc2();
		
		BinaryTCRelation result = null;
		List<BinaryTCRelation> tcRelations = dial.getExistingRTCsWidget().getRTCs();
		
		if (tcRelations != null) {
			Iterator<BinaryTCRelation> it = tcRelations.iterator();
			
			while (it.hasNext() && (result == null)){
				BinaryTCRelation tcr = it.next();
				
				if ((tcr.getTc1() == tc1) && (tcr.getType() == type) && (tcr.getTc2() == tc2)) {
					result = tcr;
				}
				
			}
			
		}
		
		return result;
	}

	private void updateRTCsSelection(){
		
		BinaryTCRelation rel = checkRTCExistency(currentRTC);

		// the selected elements correspond to an existing rtc => select the rtc
		if (rel != null) {
			dial.getExistingRTCsWidget().removePropertyChangeListener(this);
			dial.getExistingRTCsWidget().setSelection(rel);
			dial.getExistingRTCsWidget().addPropertyChangeListener(this);
			currentRTC.setOrigin(rel.getOrigin());
			
		} 
	}
	
	private void updateCurrentRTCButtonStatus(){
		dial.getCurrentRTCWidget().setUnlinkEnabled(false);
		dial.getCurrentRTCWidget().setLinkEnabled(false);
		
		boolean isAlreadyLinked = isRtcLnkedWithCurrentRt(currentRTC);
		
		dial.getCurrentRTCWidget().setUnlinkEnabled(isAlreadyLinked);
						
		if ((currentRTC.getTc1() == null) || (currentRTC.getTc2() == null) || (currentRTC.getType() == null)){
			
			dial.getCurrentRTCWidget().setLinkEnabled(false);
			
		} else {
			
			dial.getCurrentRTCWidget().setLinkEnabled(!isAlreadyLinked);
			
		}
		
		

	}
	
	private boolean isRtcLnkedWithCurrentRt(BinaryTCRelation rel){
		boolean res = false;
		
		ITermRelation rt = ControlerFactoryImpl.getTerminologyControler().getTermRelation();
		
		if (rt.getMappedTerminoConceptRelations() != null) {
		
			if (rt.getMappedTerminoConceptRelations().contains(currentRTC.getOrigin())) {
				res = true;
			}
		
		}
		return res;
	}
	
	public void initNewRTCInformationAreas(){
		ITermRelation tr = ControlerFactoryImpl.getTerminologyControler().getTermRelation();
		ITypeRelationTermino trType = tr.getTypeRel();
		ITerm term1 = tr.getTerm1();
		ITerm term2 = tr.getTerm2();
		
		ITypeRelationTc rtcType = null;
		ITerminoConcept tc1 = null;
		ITerminoConcept tc2 = null;
		
		if (trType.getMappedTcRelationTypes().size() != 0){
			Iterator<ITypeRelationTc> trTypeIt = trType.getMappedTcRelationTypes().iterator(); 
			
			rtcType = trTypeIt.next();
			
			currentRTC.setType(rtcType);
			
			this.dial.getCurrentRTCWidget().getRTCTypeText().setText(rtcType.getName());
		}
		
		if (term1.getMappedTerminoConcepts().size() != 0) {
			Iterator<ITerminoConcept> tcIt = term1.getMappedTerminoConcepts().iterator(); 
			
			tc1 = tcIt.next();
			
			currentRTC.setTc1(tc1);
			
			this.dial.getCurrentRTCWidget().getTC1Text().setText(tc1.getLabel());
		}
		
		if (term2.getMappedTerminoConcepts().size() != 0) {
			Iterator<ITerminoConcept> tcIt = term2.getMappedTerminoConcepts().iterator(); 
			
			tc2 = tcIt.next();
			
			currentRTC.setTc2(tc2);
			
			this.dial.getCurrentRTCWidget().getTC2Text().setText(tc2.getLabel());
		}
		
		updateRTCsSelection();
		
	}
	

}
