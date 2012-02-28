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

import org.dafoe.framework.core.terminological.model.IOriginRelation;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;

import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseSentenceImpl;

/**
 * The Class SentenceImpl.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class SentenceImpl extends BaseSentenceImpl implements ISentence {

	/**
	 * Instantiates a new sentence impl.
	 */
	public SentenceImpl() {
		super();
		//super.setDocument(new DocumentImpl());
		super.setTermOccurrences(new HashSet<ITermOccurrence>());
		super.setOriginRelations(new HashSet<IOriginRelation>());
		
	}


	/*@Override
	public void setDocument(IDocument document) {

		super.setDocument((DocumentImpl) document);
		
		if(document !=null){
			document.addSentence(this);
		}
	}*/

	

	
//	@Override
	public boolean addTermOccurrence(ITermOccurrence termoccurence) {

		//termoccurence.setRelatedSentence(this);
		((TermOccurrenceImpl)termoccurence).setRelatedSentence(this);
		
		return super.getTermOccurrences().add(termoccurence);

	}

	
//	@Override
	public boolean addTermOccurrence(ITermOccurrence termOcc, ITerm term) {

		//termOcc.setRelatedSentence(this);
		//termOcc.setRelatedTerm(term);
		
		((TermOccurrenceImpl)termOcc).setRelatedSentence(this);
		((TermOccurrenceImpl)termOcc).setRelatedTerm((TermImpl) term);

		return super.getTermOccurrences().add(termOcc);

	}

//	@Override
	public boolean addOriginRelations(IOriginRelation originRel) {
		
		((OriginRelationImpl)originRel).setSentence(this);
		return super.getOriginRelations().add(originRel);
		
	}

}
