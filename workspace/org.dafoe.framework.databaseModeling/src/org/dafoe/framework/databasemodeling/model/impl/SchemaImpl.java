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
package org.dafoe.framework.databasemodeling.model.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.databasemodeling.model.ISchema;
import org.dafoe.framework.databasemodeling.model.ITable;
import org.dafoe.framework.databasemodeling.model.impl.base.BaseSchemaImpl;

/**
 * This is the object class that relates to the m24_schema table.
 * Any customizations belong here.
 */
public class SchemaImpl extends BaseSchemaImpl implements ISchema{


	public SchemaImpl () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SchemaImpl (java.lang.Integer _id) {
		super(_id);
	}

	@Override
	public void addTable(ITable table) {
		super.addToTables(table);
		((TableImpl)table).setSchema(this);
	}

	@Override
	public void replaceTable(ITable oldTable, ITable newTable) {
		ITable tmp=new TableImpl();
		tmp= oldTable;
		
		super.getTables().remove(oldTable);
		((TableImpl)oldTable).setSchema(null);
		
		
		this.addTable(newTable);
				
	}

	@Override
	public void initSchema(Map<IClass, ITable> mappedClasses) {
		System.out.println("size map init schema"+mappedClasses.size());
		Set props = mappedClasses.entrySet();
		Iterator iter = props.iterator();
		
		while(iter.hasNext()){
			Map.Entry me = (Map.Entry)iter.next();
			
			this.addTable((ITable) me.getValue());
			
		}
		
	}

}