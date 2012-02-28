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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IClassExtension;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.tools.StringFormatterTools;

public class ClassExtension implements IClassExtension {

	private Integer id;
	// the minimal primitive class associated with this extension
	private IClass preferedClass;

	private Map<IProperty, Object> tuple = new HashMap<IProperty, Object>();

	public ClassExtension() {
		// TODO Auto-generated constructor stub
	}

	public ClassExtension(ResultSet rs, Integer rowId, Set<IProperty> props,
			IClass prefClass) {

		
		this.preferedClass = prefClass;

		Iterator<IProperty> iter = props.iterator();

		IProperty currentProp = null;
		Object currentPropValue = null;
		try {
			
			rs.absolute(rowId);
			this.id=rs.getInt("id");//

			while (iter.hasNext()) {
				currentProp = iter.next();
				currentPropValue = rs.getObject(StringFormatterTools
						.normalizeColumnNameFromPropertyLabel(currentProp
								.getLabel()));
				tuple.put(currentProp, currentPropValue);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

//	@Override
	public Integer getId() {

		return this.id;
	}

//	@Override
	public Object getValueOf(IProperty prop) {

		return tuple.get(prop);
	}

//	@Override
	public void setId(Integer id) {
		this.id = id;

	}

//	@Override
	public void setValueOf(IProperty prop, Object value) {
		tuple.put(prop, value);

	}

//	@Override
	public IClass getPreferedClass() {

		return this.preferedClass;
	}

//	@Override
	public void setPreferedClass(IClass cls) {
		this.preferedClass = cls;

	}

//	@Override
	public Set<IClass> instanceOf() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
	public Map<IProperty, Object> getTuple() {
		return tuple;
	}

//	@Override
	// ok
	public String getInsertionQuery() {
		String query = null;

		if (tuple.size() > 0) {// value exist for some properties
			query = "INSERT INTO "
					+ StringFormatterTools
							.normalizeSqlTableNameFromClassLabel(this
									.getPreferedClass().getLabel());
			String queryProp = " (id ";
			String queryValue = " (" + this.id + " ";

			// Set<Entry<IProperty,Object>> props= tuple.entrySet();

			Set props = tuple.entrySet();
			Iterator iter = props.iterator();

			// Iterator<Entry<IProperty, Object>> iter= props.iterator();

			while (iter.hasNext()) {
				Map.Entry me = (Map.Entry) iter.next();

				// String currentPropLabel= iter.next().getKey().getLabel();
				// queryProp=
				// queryProp+" ,"+StringTools.propertyLabelToColumnName(currentPropLabel);
				// queryValue=
				// queryValue+" ,"+iter.next().getValue().toString();

				String currentPropLabel = ((IProperty) me.getKey()).getLabel();
				queryProp = queryProp
						+ " ,"
						+ StringFormatterTools
								.normalizeColumnNameFromPropertyLabel(currentPropLabel);
				queryValue = queryValue + " , " + me.getValue().toString();
			}
			queryProp = queryProp + " )";
			queryValue = queryValue + " )";

			query = query + queryProp + " VALUES " + queryValue + ";";
		}

		System.out.println("INSERT QUERY= " + query);
		return query;
	}

//	@Override
	// ok
	public String getUpdateQuery() {

		String query = null;

		if (tuple.size() > 0) {// value exist for some properties
			query = "UPDATE "
					+ StringFormatterTools
							.normalizeSqlTableNameFromClassLabel(this
									.getPreferedClass().getLabel()) + " SET ";
			String queryProp = "";
			String queryWhere = " WHERE id= " + this.id + " ;";

			// Set<Entry<IProperty,Object>> props= tuple.entrySet();

			Set props = tuple.entrySet();
			Iterator iter = props.iterator();

			// Iterator<Entry<IProperty, Object>> iter= props.iterator();

			while (iter.hasNext()) {
				Map.Entry me = (Map.Entry) iter.next();

				// String currentPropLabel= iter.next().getKey().getLabel();

				String currentPropLabel = ((IProperty) me.getKey()).getLabel();
				queryProp = queryProp
						+ " "
						+ StringFormatterTools
								.normalizeColumnNameFromPropertyLabel(currentPropLabel)
						+ " = " + "'" + me.getValue().toString() + "',";

			}

			// revome de last comma
			queryProp = queryProp.substring(0, queryProp.lastIndexOf(","));// length()-1);

			query = query + queryProp + queryWhere;
		}

		System.out.println("UPDATE QUERY= " + query);
		return query;
	}

	@Override
	public String toString() {
		String s = "prop: id value= "+this.getId()+" || ";

		Set props = tuple.entrySet();
		Iterator iter = props.iterator();

		// Iterator<Entry<IProperty, Object>> iter= props.iterator();

		while (iter.hasNext()) {
			Map.Entry me = (Map.Entry) iter.next();

			// String currentPropLabel= iter.next().getKey().getLabel();

			String currentPropValue = " prop: "
					+ ((IProperty) me.getKey()).getLabel() + " value= "
					+ me.getValue().toString() + " ||";
			s = s + currentPropValue;
		}
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (null == obj) return false;
		if (!(obj instanceof IClassExtension)) return false;
		else {
			IClassExtension mObj = (IClassExtension) obj;
			if (null == this.getId() || null == mObj.getId()) return false;
			else return (this.getId().equals(mObj.getId()));
		}
	}
	


	
}
