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
package org.dafoe.framework.provider.hibernate.ontological.model.impl;

import java.util.Set;

import org.dafoe.framework.core.ontological.model.IEnumeration;
import org.dafoe.framework.core.ontological.model.IEnumerationValue;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;

import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseEnumerationImpl;
import org.dafoe.framework.provider.hibernate.util.Util;

/**
 * The Class EnumerationImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class EnumerationImpl extends BaseEnumerationImpl implements IEnumeration{


	public EnumerationImpl () {
		super();
	}

//	@Override
	public boolean addValue(IEnumerationValue value) {
		//super.getValues().add(value);
		((EnumerationValueImpl)value).setEnumeration(this);
		return super.getValues().add(value);
	}

//	@Override
	public boolean removeValue(IEnumerationValue value) {
		boolean isRemoved =false;
		isRemoved= super.getValues().remove(value);
		((EnumerationValueImpl)value).setEnumeration(null);
		
		return isRemoved;
		
		//VT: enumValue must be delete in database
	}

	
	//VT: to be implement
//	@Override
	public boolean addAnnotation(IOntoAnnotation annot) {
		// TODO Auto-generated method stub
		return false;
	}

	

//	@Override
	public Set<IOntoAnnotation> getAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
	public IOntology getOntology() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public boolean removeAnnotation(IOntoAnnotation annot) {
		// TODO Auto-generated method stub
		return false;
	}



	///////// VT:  TO COMPLETE
	
	@Override
	public ONTO_OBJECT_STATE getState() {
		
		return null;//Util.getOntoObjectStateFromDatabase(super.getStatus());
	}

	@Override
	public void setState(ONTO_OBJECT_STATE state) {
		//super.setStatus(state.getValue());
		
	}	
	
	@Override
	public boolean addMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<ITerminoOntoObject> getMappedTerminoOntoObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		// TODO Auto-generated method stub
		return false;
	}

	
}