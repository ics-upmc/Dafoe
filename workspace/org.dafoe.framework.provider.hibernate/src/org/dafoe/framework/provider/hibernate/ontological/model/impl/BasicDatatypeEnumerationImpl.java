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

import org.dafoe.framework.core.ontological.model.IBasicDatatypeEnumeration;
import org.dafoe.framework.core.ontological.model.IEnumeration;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseBasicDatatypeImpl;

/**
 * The Class BasicDatatypeEnumerationImpl is a wrapper (non-serializable) for the EnumerationImpl class.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class BasicDatatypeEnumerationImpl implements IBasicDatatypeEnumeration {

	/** The related enum. */
	IEnumeration relatedEnum;

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.ontological.model.IBasicDatatypeEnumeration#getId()
	 */
//	@Override
	public Integer getId() {

		return relatedEnum.getId();
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.ontological.model.IBasicDatatypeEnumeration#getRelatedEnumeration()
	 */
//	@Override
	public IEnumeration getRelatedEnumeration() {

		return relatedEnum;
	}

	/**
	 * Instantiates a new basic datatype enumeration impl.
	 * 
	 * @param enumeration the enumeration
	 */
	public BasicDatatypeEnumerationImpl(IEnumeration enumeration) {
		this.relatedEnum = enumeration;
	}

}
