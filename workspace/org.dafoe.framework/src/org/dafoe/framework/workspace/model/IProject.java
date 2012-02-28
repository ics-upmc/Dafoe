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
package org.dafoe.framework.workspace.model;

import java.util.Date;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;

public interface IProject {

	Integer getId();

	String getName();

	void setName(String name);

	Date getDateCreation();

	void setDateCreation(Date dateCreation);

	Date getDateLastUpdate();

	void setDateLastUpdate(Date dateLastUpdate);

	Set<ICorpus> getCorpus();

	public void addCorpus(ICorpus c);

	Set<ITerminology> getTerminologies();

	void addTerminology(ITerminology t);

	Set<ITerminoOntology> getTerminoOntologies();

	void addToTerminoOntology(ITerminoOntology to);

	void addOntology(IOntology o);

}
