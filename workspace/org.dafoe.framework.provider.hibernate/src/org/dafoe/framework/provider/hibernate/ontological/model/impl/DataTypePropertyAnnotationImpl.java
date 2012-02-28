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
package org.dafoe.framework.provider.hibernate.ontological.model.impl;

import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IOntoAnnotationType;

import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseDataTypePropertyAnnotationImpl;

/**
 * This is the object class that relates to the m23_annotation_value_2_datatype_prop table.
 * Any customizations belong here.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class DataTypePropertyAnnotationImpl extends BaseDataTypePropertyAnnotationImpl implements IOntoAnnotation{


	public DataTypePropertyAnnotationImpl () {
		super();
		super.setItsProperty(new DatatypePropertyImpl());
		super.setOntoAnnotationType(new OntoAnnotationTypeImpl());
		
	}

	

	
//	@Override
	public void setOntoAnnotationType(IOntoAnnotationType annotation) {
		super.setOntoAnnotationType((OntoAnnotationTypeImpl) annotation);
		
	}
}