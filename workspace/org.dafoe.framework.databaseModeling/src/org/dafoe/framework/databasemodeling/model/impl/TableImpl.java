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

import java.util.HashSet;

import org.dafoe.framework.databasemodeling.model.IColumn;
import org.dafoe.framework.databasemodeling.model.ITable;
import org.dafoe.framework.databasemodeling.model.impl.base.BaseTableImpl;

/**
 * This is the object class that relates to the m24_table table.
 * Any customizations belong here.
 */
public class TableImpl extends BaseTableImpl implements ITable {


	public TableImpl () {
		super();
		super.setColumns(new HashSet<IColumn>());
		super.setConstraints(new HashSet<ConstraintImpl>());
	}

	/**
	 * Constructor for primary key
	 */
	public TableImpl (ITable oldTable) {
		// copy name
		this.setName(oldTable.getName());
				
		// copy all column
		
		for(IColumn col: oldTable.getColumns()){
			this.addColumn(col);
		}
		
		// copy all constraint
		for(ConstraintImpl cons: oldTable.getConstraints()){
			this.addConstraint(cons);
		}
	}

	@Override
	public void addColumn(IColumn col) {
		super.addToColumns(col);
		((ColumnImpl)col).setTable(this);
		
	}

	@Override
	public void addConstraint(ConstraintImpl cons) {
		super.addToConstraints(cons);
		cons.setRelatedTable(this);
		
	}

	
}