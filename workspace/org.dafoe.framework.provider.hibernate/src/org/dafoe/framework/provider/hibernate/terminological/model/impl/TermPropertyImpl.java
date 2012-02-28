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
package org.dafoe.framework.provider.hibernate.terminological.model.impl;

import java.util.HashSet;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermProperty;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermPropertyImpl;

/**
 * The Class TermPropertyImpl.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TermPropertyImpl extends BaseTermPropertyImpl implements ITermProperty {

/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	public TermPropertyImpl () {
		super();
		super.setTerms(new HashSet<ITerm>());
		
	}

//@Override
	public boolean addTerm(ITerm term) {
		
		boolean ok1= super.getTerms().add(term);
		boolean ok2= ((TermImpl)term).getProperties().add(this);
		
		return ok1 && ok2;
	}
}