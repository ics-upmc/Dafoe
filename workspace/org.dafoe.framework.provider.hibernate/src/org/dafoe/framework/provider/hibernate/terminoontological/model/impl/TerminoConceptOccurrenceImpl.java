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

import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermOccurrenceImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTerminoConceptOccurrenceImpl;

/**
 * The Class TerminoConceptOccurrenceImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TerminoConceptOccurrenceImpl extends BaseTerminoConceptOccurrenceImpl implements ITerminoConceptOccurrence{


	/**
 * Instantiates a new termino concept occurrence impl.
 */
public TerminoConceptOccurrenceImpl () {
		super();
		
		
	}



	/* (non-Javadoc)
 * @see org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence#setTermOccurence(org.dafoe.framework.core.terminological.model.ITermOccurrence)
 */
//@Override
	public void setRelatedTermOccurence(ITermOccurrence termOccurence) {
		super.setRelatedTermOccurence((TermOccurrenceImpl) termOccurence);
		
	}




	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence#setTerminoConcept(org.dafoe.framework.core.terminoontological.model.ITerminoConcept)
	 */
	/*@Override
	public void setTerminoConcept(ITerminoConcept terminoConcept) {
		super.setTerminoConcept((TerminoConceptImpl) terminoConcept);
		
	}*/
}