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

import org.dafoe.framework.core.terminological.model.IOriginRelation;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseOriginRelationImpl;

/**
 * This is the object class that relates to the m21_origin_relation table.
 * Any customizations belong here.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */

public class OriginRelationImpl extends BaseOriginRelationImpl implements IOriginRelation {


	public OriginRelationImpl () {
		super();
		super.setMethod(new MethodImpl());
		super.setSentence(new SentenceImpl());
		super.setTermRelation(new TermRelationImpl());
		
	}

	

	/*@Override
	public void setMethod(IMethod method) {
		super.setMethod((MethodImpl) method);
		
		if(method!= null){
			method.addOriginRelationTermino(this);
		}
		
	}

	@Override
	public void setSentence(ISentence sentence) {
		super.setSentence((SentenceImpl) sentence);
		
	}

	@Override
	public void setTermRelation(ITermRelation termRelation) {
		super.setTermRelation((TermRelationImpl) termRelation);
		
	}*/


}