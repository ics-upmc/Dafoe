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
import org.dafoe.framework.core.ontological.model.IConstraintAllValueFrom;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseAllValueFromImpl;

/**
 * The Class AllValueFromImpl.
 * 
 * 
 */
public class AllValueFromImpl extends BaseAllValueFromImpl implements IConstraintAllValueFrom {


	public AllValueFromImpl () {
		super();
	}

//	@Override
	public void setRelatedClass(IClass relatedClass) {
		super.setRelatedClass((ClassImpl) relatedClass);
		
	}

//	@Override
	public void setRelatedProperty(IObjectProperty relatedProperty) {
		super.setRelatedProperty((ObjectPropertyImpl) relatedProperty);
		
	}

	
}