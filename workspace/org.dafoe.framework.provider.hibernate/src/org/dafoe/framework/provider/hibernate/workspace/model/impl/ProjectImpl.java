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
package org.dafoe.framework.provider.hibernate.workspace.model.impl;

import java.util.HashSet;

import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.provider.hibernate.workspace.model.impl.base.BaseProjectImpl;
import org.dafoe.framework.workspace.model.IProject;

/**
 * This is the object class that relates to the m0_project table.
 * Any customizations belong here.
 */
public class ProjectImpl extends BaseProjectImpl implements IProject{


	public ProjectImpl () {
		super();
		super.setCorpus(new HashSet<ICorpus>());
		super.setTerminologies(new HashSet<ITerminology>());
		super.setTerminoOntologies(new HashSet<ITerminoOntology>());
		super.setOntologies(new HashSet<IOntology>());
	}

	@Override
	public void addCorpus(ICorpus c) {
		super.addToCorpus(c);
		
	}

	@Override
	public void addOntology(IOntology o) {
		super.addToOntologies(o);
		
	}

	@Override
	public void addTerminology(ITerminology t) {
		super.addToTerminologies(t);
		
	}

	@Override
	public void addToTerminoOntology(ITerminoOntology to) {
		super.addToTerminoOntologies(to);
		
	}





	
}