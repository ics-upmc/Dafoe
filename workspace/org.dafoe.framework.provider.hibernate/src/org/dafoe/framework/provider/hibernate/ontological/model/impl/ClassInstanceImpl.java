/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/
package org.dafoe.framework.provider.hibernate.ontological.model.impl;

import org.dafoe.framework.core.ontological.model.IClassInstance;

import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseClassInstanceImpl;

/**
 * The Class ClassInstanceImpl.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Val�ry TEGUIAK</a>
 */
public class ClassInstanceImpl extends BaseClassInstanceImpl implements IClassInstance{

	
	

	public ClassInstanceImpl () {
		super();
		super.setItsClass(new ClassImpl());
		
	}

	
	
	/* (non-Javadoc)
 * @see org.dafoe.framework.core.ontological.model.IClassInstance#setItsClass(org.dafoe.framework.core.ontological.model.IClass)
 */
/*@Override
	public void setItsClass(IClass itsClass) {
		super.setItsClass((ClassImpl) itsClass);
		
		if(itsClass != null){
		itsClass.addClassInstance(this);
		}
		
	}*/

	
}