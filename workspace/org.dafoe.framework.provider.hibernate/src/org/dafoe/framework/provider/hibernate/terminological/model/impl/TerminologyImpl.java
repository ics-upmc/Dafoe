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
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITermSyntacticRelation;
import org.dafoe.framework.core.terminological.model.ITerminoObject;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTerminologyImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl;


/**
 * The Class TerminologyImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TerminologyImpl extends BaseTerminologyImpl implements
		ITerminology {
/** The instance terminology. */
	private static ITerminology instanceTerminology = null;
	/**
	 * Instantiates a new terminology impl.
	 */
	public TerminologyImpl() {
		super();
		super.setMappedTerminoOntologies(new HashSet<ITerminoOntology>());
		super.setTermRelations(new HashSet<ITermRelation>());
		super.setTerms(new HashSet<ITerm>());
		super.setTermSyntacticRelations(new HashSet<ITermSyntacticRelation>());
	}

	/**
	 * Gets the single instance of TerminologyImpl.
	 * 
	 * @return single instance of TerminologyImpl
	 */
	public static ITerminology getInstance() {
		if (instanceTerminology != null) { // There is a terminology in
											// the JVM

			return instanceTerminology; // return it

		} else { // no terminoloy in the JVM:
			     // try to retrieve terminology from the database

			List<TerminologyImpl> allTerminos = SessionFactoryImpl
					.getCurrentDynamicSession().createCriteria(
							TerminologyImpl.class).list();

			if (allTerminos.size() > 0) {// a terminology already exists in the
				// database
				instanceTerminology = allTerminos.get(0);

			} else {// there is no terminolgy in the database, then create one
				instanceTerminology= new TerminologyImpl();
			}

		}

		return instanceTerminology;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.terminological.model.ITerminology#addTerminoObject
	 * (org.dafoe.framework.core.terminological.model.ITerminoObject)
	 */
	// @Override
	public boolean addTerminoObject(ITerminoObject obj) {
		if (obj instanceof ITerm) {
			ITerm term = (ITerm) obj;
			
			((TermImpl) term).setTerminology(this);
			return super.getTerms().add(term);


		}
		if (obj instanceof ITermRelation) {
			ITermRelation termRel = (ITermRelation) obj;
			
			((TermRelationImpl) termRel).setTerminology(this);
			
			return super.getTermRelations().add(termRel);
		}
		if (obj instanceof ITermSyntacticRelation) {
			ITermSyntacticRelation termSynRel = (ITermSyntacticRelation) obj;
			

			((TermSyntacticRelationImpl) termSynRel).setTerminology(this);
			return super.getTermSyntacticRelations().add(termSynRel);
		}

		if (obj instanceof ITypeRelationTermino) {
			ITypeRelationTermino typeRel = (ITypeRelationTermino) obj;
			
			((TypeRelationTerminoImpl) typeRel).setTerminology(this);
			return super.getTypeRelationTermino().add(typeRel);
		}
      return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.terminological.model.ITerminology#getTerminoObjects
	 * ()
	 */
	// @Override
	public Set<ITerminoObject> getTerminoObjects() {
		Set<ITerminoObject> tObjects = new HashSet<ITerminoObject>();

		tObjects.addAll(super.getTerms());
		tObjects.addAll(super.getTermRelations());
		tObjects.addAll(super.getTermSyntacticRelations());

		return tObjects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.terminological.model.ITerminology#
	 * removeTerminoObject
	 * (org.dafoe.framework.core.terminological.model.ITerminoObject)
	 */
	// @Override
	public boolean removeTerminoObject(ITerminoObject obj) {
		if (obj instanceof ITerm) {
			ITerm term = (ITerm) obj;
			
			((TermImpl) term).setTerminology(null);
			
			return super.getTerms().remove(term);

		}
		if (obj instanceof ITermRelation) {
			ITermRelation termRel = (ITermRelation) obj;
			
			((TermRelationImpl) termRel).setTerminology(null);
			return super.getTermRelations().remove(termRel);

		}
		if (obj instanceof ITermSyntacticRelation) {
			ITermSyntacticRelation termSynRel = (ITermSyntacticRelation) obj;
			
			((TermSyntacticRelationImpl) termSynRel).setTerminology(null);
			
			return super.getTermSyntacticRelations().remove(termSynRel);

		}

		if (obj instanceof ITypeRelationTermino) {
			ITypeRelationTermino typeRel = (ITypeRelationTermino) obj;
			
			((TypeRelationTerminoImpl) typeRel).setTerminology(null);
			
			return super.getTypeRelationTermino().remove(typeRel);
		}
		
		return false;

	}

	// mapping with TerminoOntology
	// @Override
	
	public boolean addMappedTerminoOntology(ITerminoOntology to) {
		boolean ok1= super.getMappedTerminoOntologies().add(to);
		boolean ok2= ((TerminoOntologyImpl)to).getMappedTerminologies().add(this);
		
		return ok1 && ok2;

	}

	@Override
	public boolean removeMappedTerminoOntology(ITerminoOntology to) {
		boolean ok1= super.getMappedTerminoOntologies().remove(to);
		boolean ok2= ((TerminoOntologyImpl)to).getMappedTerminologies().remove(this);
		return ok1 && ok2;
	}

	@Override
	public Set<ITerminoOntology> getMappedTerminoOntlogies() {
		
		return super.getMappedTerminoOntologies();
	}

	
}