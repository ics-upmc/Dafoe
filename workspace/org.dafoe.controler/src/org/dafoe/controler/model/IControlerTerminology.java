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
package org.dafoe.controler.model;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.core.terminological.model.IMethod;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;


/**
 * The Interface IControlerTerminology.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:texier@ensma.fr">Guillaume TEXIER</a>
 */
public interface IControlerTerminology {

	/**
	 * Gets the current term.
	 * 
	 * @return the current term
	 */
	ITerm getCurrentTerm();
	
	/**
	 * Sets the current term.
	 * 
	 * @param term the new current term
	 */
	void setCurrentTerm(ITerm term);
	
	/**
	 * Gets the current terms.
	 * 
	 * @return the current terms
	 */
	List<ITerm> getCurrentTerms();
	
	/**
	 * Sets the current terms.
	 * 
	 * @param terms the new current terms
	 */
	void setCurrentTerms(List<ITerm> terms);

	/**
	 * Gets the new variant.
	 * 
	 * @return the new variant
	 */
	ITerm getNewVariant();
	
	/**
	 * Sets the new variant.
	 * 
	 * @param variant the new new variant
	 */
	void setNewVariant(ITerm variant);

	/**
	 * Gets the current variants.
	 * 
	 * @return the current variants
	 */
	List<ITerm> getCurrentVariants();
	
	/**
	 * Sets the current variants.
	 * 
	 * @param variants the new current variants
	 */
	void setCurrentVariants(List<ITerm> variants);
	
	/**
	 * Sets the variant detached.
	 */
	void setVariantDetached();
	
	/**
	 * Sets the term label modified.
	 */
	void setTermLabelModified();

	/**
	 * Sets the new status.
	 */
	void setNewStatus();

	/**
	 * Sets the new relation status.
	 */
	void setNewRelationStatus();

	/**
	 * Sets the terms deleted.
	 */
	void setTermsDeleted();

	/**
	 * Sets the relation deleted.
	 */
	void setRelationDeleted();

	/**
	 * Sets the terms imported.
	 */
	void setTermsImported();

	/**
	 * Gets the current relation type.
	 * 
	 * @return the current relation type
	 */
	ITypeRelationTermino getCurrentRelationType();
	
	/**
	 * Sets the current relation type.
	 * 
	 * @param relationType the new current relation type
	 */
	void setCurrentRelationType(ITypeRelationTermino relationType);
	
	/**
	 * Gets the method.
	 * 
	 * @return the method
	 */
	IMethod getMethod();
	
	/**
	 * Sets the method.
	 * 
	 * @param method the new method
	 */
	void setMethod(IMethod method);

	/**
	 * Gets the term relation.
	 * 
	 * @return the term relation
	 */
	ITermRelation getTermRelation();
	
	/**
	 * Sets the term relation.
	 * 
	 * @param relation the new term relation
	 */
	void setTermRelation(ITermRelation relation);
		
	/**
	 * Gets the current corpus.
	 * 
	 * @return the current corpus
	 */
	ICorpus getcurrentCorpus();
	
	/**
	 * Sets the current corpus.
	 * 
	 * @param corpus the new current corpus
	 */
	void setCurrentCorpus(ICorpus corpus);
	
	
    List<ICorpus> getCurrentLoadedCorpus();
	
	void setCurrentLoadedCorpus(List<ICorpus> loadedCorpus);
	
	/**
	 * Gets the current document.
	 * 
	 * @return the current document
	 */
	IDocument getCurrentDocument();
	
	/**
	 * Sets the current document.
	 * 
	 * @param document the new current document
	 */
	void setCurrentDocument(IDocument document);
	
	/**
	 * Adds the property change listener.
	 * 
	 * @param propertyName the property name
	 * @param listener the listener
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Removes the property change listener.
	 * 
	 * @param listener the listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);

}
