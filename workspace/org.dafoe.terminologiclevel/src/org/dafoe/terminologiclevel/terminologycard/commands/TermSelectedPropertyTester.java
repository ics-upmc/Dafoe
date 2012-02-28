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

package org.dafoe.terminologiclevel.terminologycard.commands;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.core.expressions.PropertyTester;

public class TermSelectedPropertyTester extends PropertyTester {

	public TermSelectedPropertyTester() {
		// TODO Auto-generated constructor stub
	}

	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		
		ITerm term = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
		
		if (property.equals("isTermSelected")){ //$NON-NLS-1$
			
			return term != null;
			
		} else {
			
			return false;
			
		}
		
	}

}
