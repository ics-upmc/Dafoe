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

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IConstraintHighThan;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;

import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseHighThanImpl;

/**
 * This is the object class that relates to the m23_constraint_high_than table.
 * Any customizations belong here.
 */
public class HighThanImpl extends BaseHighThanImpl implements IConstraintHighThan{


	public HighThanImpl () {
		super();
	}

//	@Override
	public void setRelatedClass(IClass relatedClass) {
		super.setRelatedClass((ClassImpl) relatedClass);
		
	}

//	@Override
	public void setRelatedProperty(IDatatypeProperty relatedProperty) {
		super.setRelatedProperty((DatatypePropertyImpl) relatedProperty);
		
	}

	
}