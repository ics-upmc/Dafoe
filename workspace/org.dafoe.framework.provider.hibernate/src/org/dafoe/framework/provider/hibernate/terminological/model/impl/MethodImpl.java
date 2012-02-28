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

import org.dafoe.framework.core.terminological.model.IMethod;
import org.dafoe.framework.core.terminological.model.IOriginRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;

import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseMethodImpl;


//TODO: Auto-generated Javadoc
/**
* This is the object class that relates to the m21_method table.
* Any customizations belong here.
* 
*  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
*/
public class MethodImpl extends BaseMethodImpl implements IMethod{


	public MethodImpl () {
		super();
		
		super.setOriginRelations(new HashSet<IOriginRelation>());
		super.setTypeRelation(new HashSet<ITypeRelationTermino>());
	}

	
	
//@Override
public boolean addOriginRelationTermino(IOriginRelation originRel) {
	((OriginRelationImpl)originRel).setMethod(this);
	
	return super.getOriginRelations().add(originRel);
	
}

//@Override
public boolean addTypeRelationTermino(ITypeRelationTermino typeRel) {
	
	((TypeRelationTerminoImpl)typeRel).addToMethods(this);
	return super.getTypeRelation().add(typeRel);
}


	


	

	

}