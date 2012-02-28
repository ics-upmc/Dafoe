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

import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.core.terminological.model.ISentence;

import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseDocumentImpl;

//TODO: Auto-generated Javadoc
/**
 * The Class DocumentImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class DocumentImpl extends BaseDocumentImpl implements IDocument {

	
	/**
	 * Instantiates a new document impl.
	 */
	public DocumentImpl() {
		super();
		super.setSentences(new HashSet<ISentence>());
		//super.setRelatedCorpus(CorpusImpl.getInstance()); // to advoid, then produce an infinite loop
		super.setRelatedCorpus(new CorpusImpl());
	}

	
	//@Override
	public boolean addSentence(ISentence sentence) {
		
		((SentenceImpl)sentence).setDocument(this);
		return super.getSentences().add(sentence);
		
	}

	
	/*@Override
	public void setRelatedCorpus(ICorpus corpus) {
		super.setRelatedCorpus((CorpusImpl) corpus);
		
		//gestion de l'association bidirectionnelle avec le corpus
		//corpus.addDocument(this);
		if(null!= corpus){
		corpus.addDocument(this);
		}
		
	}*/

	

	
	
	
}