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

package org.dafoe.terminologiclevel.saillance.commands;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class MakeStarTerm extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		ITerm variant, starTerm;

		variant = (ITerm) ControlerFactoryImpl.getTerminologyControler().getCurrentVariants().get(0);
		
		starTerm = variant.getStarTerm();

		DatabaseAdapter.makeStarTerm(variant);

		ControlerFactoryImpl.getTerminologyControler().setCurrentTerm(variant);

		ControlerFactoryImpl.getTerminologyControler().setNewVariant(starTerm);
		
		return null;
	}

}
