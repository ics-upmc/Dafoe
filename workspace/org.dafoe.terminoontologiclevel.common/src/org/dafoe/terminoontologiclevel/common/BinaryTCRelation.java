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

package org.dafoe.terminoontologiclevel.common;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;

public class BinaryTCRelation {
	private static String TC1_CHANGE_EVENT = "TC1_changed";

	private static String TC2_CHANGE_EVENT = "TC2_changed";
	
	private static String RTCTYPE_CHANGE_EVENT = "RTCType_changed";
	
	private static String RTCSTATUS_CHANGE_EVENT = "RTCStatus_changed";

	ITerminoConcept tc1;
	
	ITerminoConcept tc2;
	
	ITypeRelationTc type;
	
	TERMINO_ONTO_OBJECT_STATE status;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	ITerminoConceptRelation origin;

	public BinaryTCRelation() {
	}

	public void setTc1(ITerminoConcept tc) {
		ITerminoConcept oldTc1 = this.tc1;
		this.tc1 = tc;

		propertyChangeSupport.firePropertyChange(TC1_CHANGE_EVENT, oldTc1, tc1);
	}

	public ITerminoConcept getTc1() {
		return this.tc1;
	}

	public void setTc2(ITerminoConcept tc) {
		ITerminoConcept oldTc2 = this.tc2;
		this.tc2 = tc;
		propertyChangeSupport.firePropertyChange(TC2_CHANGE_EVENT, oldTc2, tc2);
	}

	public ITerminoConcept getTc2() {
		return this.tc2;
	}

	public void setType(ITypeRelationTc type) {
		ITypeRelationTc oldType = this.type;
		this.type = type;
		propertyChangeSupport.firePropertyChange(RTCTYPE_CHANGE_EVENT, oldType,
				type);
	}

	public ITypeRelationTc getType() {
		return this.type;
	}

	public void setState(TERMINO_ONTO_OBJECT_STATE status) {
		TERMINO_ONTO_OBJECT_STATE oldStatus = this.status;
		this.status = status;
		propertyChangeSupport.firePropertyChange(RTCSTATUS_CHANGE_EVENT,
				oldStatus, status);
	}

	public TERMINO_ONTO_OBJECT_STATE getState() {
		return this.status;
	}

	//

	public void setOrigin(ITerminoConceptRelation tcr) {
		this.origin = tcr;
	}

	//

	public ITerminoConceptRelation getOrigin() {
		return this.origin;
	}

	//

	public void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	//

	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

}
