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

import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseCorpusImpl;

// TODO: Auto-generated Javadoc
/**
 * This is the object class that relates to the corpus table.
 * Any customizations belong here.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class CorpusImpl extends BaseCorpusImpl implements ICorpus {
    
    /** The instance corpus. */
    private static CorpusImpl instanceCorpus= null;

	/**
	 * Instantiates a new corpus impl.
	 */
	public CorpusImpl () {
		super();
		super.setDocuments(new HashSet<IDocument>());
		
	}

		
	/**
	 * Gets the single instance of CorpusImpl.
	 *
	 * @return single instance of CorpusImpl
	 */
	
	public static CorpusImpl getInstance() {
		if (instanceCorpus != null) { // There is a corpus in
											// the JVM

			return instanceCorpus; // return it

		} else { // try to retrieve corpus from the database

			List<CorpusImpl> allCorpus = SessionFactoryImpl
					.getCurrentDynamicSession().createCriteria(
							CorpusImpl.class).list();

			if (allCorpus.size() > 0) {// a corpus already exists in the
				// database
				instanceCorpus = allCorpus.get(0);

			} else {// there is no corpus in the database, then create one
				instanceCorpus= new CorpusImpl();
			}

		}

		return instanceCorpus;
	}

	

 //@Override
	
 public boolean addDocument(IDocument doc) {
		
		((DocumentImpl)doc).setRelatedCorpus(this);	
		return super.getDocuments().add(doc);
	}

}