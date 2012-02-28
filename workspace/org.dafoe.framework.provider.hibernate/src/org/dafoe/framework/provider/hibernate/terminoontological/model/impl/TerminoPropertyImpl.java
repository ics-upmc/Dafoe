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
package org.dafoe.framework.provider.hibernate.terminoontological.model.impl;

import java.util.HashSet;

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoProperty;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTerminoPropertyImpl;

/**
 * The Class TerminoPropertyImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TerminoPropertyImpl extends BaseTerminoPropertyImpl implements ITerminoProperty{


	/**
 * Instantiates a new termino property impl.
 */
public TerminoPropertyImpl () {
		super();
		super.setTerminoConcepts(new HashSet<ITerminoConcept>());
	}


	/* (non-Javadoc)
 * @see org.dafoe.framework.core.terminoontological.model.ITerminoProperty#addTerminoConcept(org.dafoe.framework.core.terminoontological.model.ITerminoConcept)
 */
//@Override
	public boolean addTerminoConcept(ITerminoConcept tc) {
		
		boolean ok1= super.getTerminoConcepts().add(tc);
		boolean ok2= ((TerminoConceptImpl)tc).getTerminoProperties().add(this);
		
		return ok1 && ok2;
		
	}
}