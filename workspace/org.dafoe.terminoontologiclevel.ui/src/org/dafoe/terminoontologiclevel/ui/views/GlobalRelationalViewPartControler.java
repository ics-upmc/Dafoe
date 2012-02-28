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

package org.dafoe.terminoontologiclevel.ui.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;

public class GlobalRelationalViewPartControler implements
		PropertyChangeListener {

	public static final String TC_ARG1_SELECTED_EVENT = "TCArg1Selected"; //$NON-NLS-1$
	public static final String TC_ARG2_SELECTED_EVENT = "TCArg2Selected"; //$NON-NLS-1$
	public static final String NEW_RTC_SELECTED_EVENT = "NewRTCSelected"; //$NON-NLS-1$
	public static final String RTC_TYPE_SELECTED_EVENT = "RTCTypeSelected"; //$NON-NLS-1$

	private BinaryTCRelation currentRTC;

	private GlobalRelationalViewPart viewPart;

	public GlobalRelationalViewPartControler(GlobalRelationalViewPart viewPart) {
		this.viewPart = viewPart;
		currentRTC = new BinaryTCRelation();
		ControlerFactoryImpl.getTerminologyControler()
				.getTermRelation();
	}

	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals(
				GlobalRelationalViewPartControler.TC_ARG1_SELECTED_EVENT)) {

			ITerminoConcept tc = (ITerminoConcept) evt.getNewValue();
			currentRTC.setTc1(tc);
			viewPart.refresh(currentRTC);

			BinaryTCRelation sel = viewPart.getSelection();

			// A selection exists
			if (sel != null) {
				// if the selected tc is different from arg1 => unselect
				if (tc != sel.getTc1()) {
					viewPart.deselect();
				}

			}

			updateRTCsSelection();

		} else if (evt.getPropertyName().equals(
				GlobalRelationalViewPartControler.TC_ARG2_SELECTED_EVENT)) {

			ITerminoConcept tc = (ITerminoConcept) evt.getNewValue();
			currentRTC.setTc2(tc);
			viewPart.refresh(currentRTC);

			BinaryTCRelation sel = viewPart.getSelection();

			// A selection exists
			if (sel != null) {
				// if the selected tc is different from arg2 => unselect
				if (tc != sel.getTc2()) {
					viewPart.deselect();
				}
			}

			updateRTCsSelection();

		} else if (evt.getPropertyName().equals(
				GlobalRelationalViewPartControler.RTC_TYPE_SELECTED_EVENT)) {

			ITypeRelationTc rtcType = (ITypeRelationTc) evt.getNewValue();
			currentRTC.setType(rtcType);
			viewPart.refresh(currentRTC);
			BinaryTCRelation sel = viewPart.getSelection();

			// A selection exists
			if (sel != null) {
				// if the selected rtc type is different from type => unselect
				if (rtcType != sel.getType()) {
					viewPart.deselect();
				}
			}

			updateRTCsSelection();

		} else if (evt.getPropertyName().equals(
				GlobalRelationalViewPartControler.NEW_RTC_SELECTED_EVENT)) {

			BinaryTCRelation rtc = (BinaryTCRelation) evt.getNewValue();

			viewPart.getTCArg1Widget().removePropertyChangeListener(this);
			viewPart.getTCArg2Widget().removePropertyChangeListener(this);
			viewPart.getRTCTypeWidget().removePropertyChangeListener(this);

			viewPart.getTCArg1Widget().setSelection(rtc.getTc1());
			viewPart.getTCArg2Widget().setSelection(rtc.getTc2());
			viewPart.getRTCTypeWidget().setSelection(rtc.getType());

			viewPart.getTCArg1Widget().addPropertyChangeListener(this);
			viewPart.getTCArg2Widget().addPropertyChangeListener(this);
			viewPart.getRTCTypeWidget().addPropertyChangeListener(this);

			currentRTC.setTc1(rtc.getTc1());
			currentRTC.setTc2(rtc.getTc2());
			currentRTC.setType(rtc.getType());
			currentRTC.setOrigin(rtc.getOrigin());

		}

	}

	// if the independent selection of RTC1,RTCType and RTC2 results to an
	// existing relation definition,
	// returns the corresponding TCRelation, otherwise returns null

	private BinaryTCRelation checkRTCExistency(BinaryTCRelation rel) {
		ITerminoConcept tc1 = rel.getTc1();
		ITypeRelationTc type = rel.getType();
		ITerminoConcept tc2 = rel.getTc2();

		BinaryTCRelation result = null;
		List<BinaryTCRelation> tcRelations = viewPart.getRTCs();

		if (tcRelations != null) {
			Iterator<BinaryTCRelation> it = tcRelations.iterator();

			while (it.hasNext() && (result == null)) {
				BinaryTCRelation tcr = it.next();

				if ((tcr.getTc1() == tc1) && (tcr.getType() == type)
						&& (tcr.getTc2() == tc2)) {
					result = tcr;
				}

			}

		}

		return result;
	}

	private void updateRTCsSelection() {

		BinaryTCRelation rel = checkRTCExistency(currentRTC);

		// the selected elements correspond to an existing rtc => select the rtc
		if (rel != null) {
			viewPart.removePropertyChangeListener(this);
			viewPart.setSelection(rel);
			viewPart.addPropertyChangeListener(this);
			currentRTC.setOrigin(rel.getOrigin());

		}
	}

}
