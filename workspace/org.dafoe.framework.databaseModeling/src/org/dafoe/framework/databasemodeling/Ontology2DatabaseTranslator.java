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
package org.dafoe.framework.databasemodeling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IConstraint;
import org.dafoe.framework.core.ontological.model.IConstraintHasValue;
import org.dafoe.framework.core.ontological.model.IConstraintHighThan;
import org.dafoe.framework.core.ontological.model.IConstraintLessThan;
import org.dafoe.framework.core.ontological.model.IConstraintOneOf;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IEnumerationValue;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.databasemodeling.model.IColumn;
import org.dafoe.framework.databasemodeling.model.ISchema;
import org.dafoe.framework.databasemodeling.model.ITable;
import org.dafoe.framework.databasemodeling.model.impl.ColumnImpl;
import org.dafoe.framework.databasemodeling.model.impl.ConstraintCheckInImpl;
import org.dafoe.framework.databasemodeling.model.impl.ConstraintCheckInferiorImpl;
import org.dafoe.framework.databasemodeling.model.impl.ConstraintCheckSuperiorImpl;
import org.dafoe.framework.databasemodeling.model.impl.ConstraintForeignKeyImpl;
import org.dafoe.framework.databasemodeling.model.impl.ConstraintImpl;
import org.dafoe.framework.databasemodeling.model.impl.SchemaImpl;
import org.dafoe.framework.databasemodeling.model.impl.TableImpl;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.HasValueImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.HighThanImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.LessThanImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.MinMaxImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OneOfImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.SomeValueFromImpl;
import org.dafoe.framework.tools.StringFormatterTools;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * The Class Ontology2DatabaseTranslator.
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class Ontology2DatabaseTranslator {

	/** The mapped classes. */
	public static Map<IClass, ITable> mappedClasses = new HashMap<IClass, ITable>();
    
	/** The classes with object properties. */
	private static List<IClass> classesWithObjectProperties= new ArrayList<IClass>();
	
	

	/**
	 * Gets the dafoe session.
	 * 
	 * @return the dafoe session
	 */
	static Session getDafoeSession() {

		// VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	/**
	 * Map class to table.
	 * 
	 * @param cls the cls
	 * 
	 * @return the i table
	 */
	private static ITable mapClassToTable(IClass cls) {
		ITable tab = new TableImpl();
		tab.setName(StringFormatterTools.normalizeSqlTableNameFromClassLabel(cls.getLabel()));

		return tab;
	}

	/**
	 * Map property to column.
	 * 
	 * @param prop the prop
	 * 
	 * @return the i column
	 */
	private static IColumn mapPropertyToColumn(IProperty prop) {
		IColumn col = new ColumnImpl();
		if (prop instanceof IDatatypeProperty) {
			col= mapPropertyToColumn((IDatatypeProperty)prop);
		}
		if (prop instanceof IObjectProperty) {
			col= mapPropertyToColumn((IObjectProperty)prop);
		}

		return col;
	}

	/**
	 * Map property to column.
	 * 
	 * @param dProp the d prop
	 * 
	 * @return the i column
	 */
	private static IColumn mapPropertyToColumn(IDatatypeProperty dProp) {
		IColumn col = new ColumnImpl();
		col.setName(StringFormatterTools.normalizeColumnNameFromPropertyLabel(dProp.getLabel()));
		col.setSqlType(MappingManager.getSqlTypeFromBasicDatatype(dProp.getRange()
				.getLabel()));

		return col;
	}

	/**
	 * Map property to column.
	 * 
	 * @param prop the prop
	 * 
	 * @return the i column
	 */
	public static IColumn mapPropertyToColumn(IObjectProperty prop) {
		IColumn col = new ColumnImpl();
		col.setName(StringFormatterTools.normalizeColumnNameFromPropertyLabel(prop.getLabel()));
		col.setSqlType("INTEGER");

		return col;
	}

	/**
	 * Gets the sql data type from owl data type.
	 * 
	 * @param owlDtp the owl dtp
	 * 
	 * @return the sql data type from owl data type
	 */
	public static String normalizeSqlDataTypeFromOwlDataType1(String owlDtp) {
		
		return MappingManager.getSqlTypeFromBasicDatatype(owlDtp);
	}
/*
	public static ITable mapping(IClass currentClass) {

	
		return currentTable;
	}
*/
	
	/**
	 * Mapping.
	 * 
	 * @param keyClass the key class
	 * @param valueOldTable the value old table
	 * @param valueNewTable the value new table
	 * 
	 * @return the i schema
	 */
	
	private static void replaceTableInMappedClasses(IClass keyClass, ITable valueOldTable,ITable valueNewTable){
		//System.out.println("==============>  CONTAINS CLASS "+keyClass.getLabel()+" : "+mappedClasses.containsKey(keyClass));
		//System.out.println("size before remove "+mappedClasses.size());
		mappedClasses.remove(keyClass);
		//System.out.println("size after remove and before add "+mappedClasses.size());
		mappedClasses.put(keyClass, valueNewTable);
		//System.out.println("size after add "+mappedClasses.size());
	}
	
	
	/**
	 * Mapping.
	 * 
	 * @param onto the onto
	 * 
	 * @return the i schema
	 */
	public static ISchema mapping(IOntology onto) {

		

		ISchema schema = new SchemaImpl();
		schema.setName("schema_" + onto.getName());
		Set<IClass> classes = onto.getClasses();

		// first pass

		System.out.println("// first pass map datatypeproperties");

		for (IClass currentClass : classes) {

			if(currentClass.getObjectProperties().size()>0){
				classesWithObjectProperties.add(currentClass); //init for the second pass 
			}
			ITable currentTable = mapClassToTable(currentClass);

			mappedClasses.put(currentClass, currentTable);// save table to
			// resolve fk

			System.out.println("currentClass= " + currentClass.getLabel()
					+ "|| currentTable= " + currentTable.getName());

			// ************************ map datatyproperties
			// ****************************

			for (IDatatypeProperty currentDtprop : currentClass
					.getDatatypeProperties()) {

				IColumn currentCol = mapPropertyToColumn(currentDtprop);

				currentTable.addColumn(currentCol);

				System.out.println("currentDtprop= " + currentDtprop.getLabel()
						+ "|| currentCol = " + currentCol.getName());
				// ********************* map constraint less than

				IConstraintLessThan consL = getLessThanConstraintFromClassAndProperty(
						currentClass, currentDtprop);

				if (consL != null) {
					ConstraintImpl cInf = new ConstraintCheckInferiorImpl();
					cInf.setName(currentTable.getName() + "_check_sup_"
							+ consL.getMatchableValue() + "_"
							+ currentCol.getName());
					cInf.setRelatedColumn(currentCol);
					cInf.setRelatedTable(currentTable);
					cInf.setExpression("CHECK (" + currentCol.getName() + " < "
							+ consL.getMatchableValue() + ")");

					currentTable.addConstraint(cInf);

					System.out.println("exp= " + cInf.getExpression());

				}// end map constraint less than

				// ************************ map constraint High than

				IConstraintHighThan consH = getHighThanConstraintFromClassAndProperty(
						currentClass, currentDtprop);

				if (consH != null) {
					ConstraintImpl cSup = new ConstraintCheckSuperiorImpl();

					cSup.setName(currentTable.getName() + "_check_sup_"
							+ consH.getMatchableValue() + "_"
							+ currentCol.getName());
					cSup.setRelatedColumn(currentCol);
					cSup.setRelatedTable(currentTable);
					cSup.setExpression("CHECK (" + currentCol.getName() + " > "
							+ consH.getMatchableValue() + ")");

					currentTable.addConstraint(cSup);

					System.out.println("exp= " + cSup.getExpression());

				}// end map constraint high than

				// ************************ map constraint One Of

				IConstraintOneOf consOneOf = getOneOfConstraintFromClassAndProperty(
						currentClass, currentDtprop);

				if (consOneOf != null) {
					//Set<IEnumerationValue> enumValues = new HashSet<IEnumerationValue>();
					ConstraintImpl cCheckIn = new ConstraintCheckInImpl();

					cCheckIn.setName(currentTable.getName() + "_check_one_"
							+ consOneOf.getRelatedEnumeration().getLabel()
							+ "_" + currentCol.getName());

					cCheckIn.setRelatedColumn(currentCol);
					cCheckIn.setRelatedTable(currentTable);

					// construct check in expression

					String exp = "CHECK (" + currentCol.getName() + " IN ( ";

					for (IEnumerationValue enumV : consOneOf
							.getRelatedEnumeration().getValues()) {
						// while (iterEnum.hasNext()) {
						String expVal = "'" + enumV.getLabel() + "',";
						exp = exp + expVal;

					}
					exp = exp.substring(0, exp.lastIndexOf(","));
					exp = exp + "))";

					// end construct check in expression
					cCheckIn.setExpression(exp);

					System.out.println("exp= " + cCheckIn.getExpression());
					currentTable.addConstraint(cCheckIn);

				}// end map constraint One Of

				// ************************ map constraint HasValue map an check
				// in constraint

				IConstraintHasValue consHasValue = getHasValueConstraintFromClassAndProperty(
						currentClass, currentDtprop);

				if (consHasValue != null) {
					ConstraintImpl cCheckInHas = new ConstraintCheckInImpl();

					cCheckInHas.setName(currentTable.getName() + "_check_has_"
							+ consHasValue.getMatchableValue() + "_"
							+ currentCol.getName());
					cCheckInHas.setRelatedColumn(currentCol);
					cCheckInHas.setRelatedTable(currentTable);

					// construct check in expression

					String expHas = "CHECK (" + currentCol.getName() + " IN ( "
							+ consHasValue.getMatchableValue() + "')";

					// end construct check in expression
					cCheckInHas.setExpression(expHas);

					currentTable.addConstraint(cCheckInHas);

					System.out.println("exp= " + cCheckInHas.getExpression());

				}// end map constraint hasValue

			}// end map datatyproperties
			
		}

		System.out.println("// end first pass map datatypeproperties");

		// 2nd pass to resolve fk
		// ************************** map objectproperties
		// ************************

		System.out
				.println("// begin 2nd pass to resolve fk map objectproperties");

		// Iterator<IClass> iter = classes.iterator();
		// for (IClass currentClass : classes) {

	

		//while (iter.hasNext()) {
			//Map.Entry me = (Map.Entry) iter.next();
        for(IClass currentClass: classesWithObjectProperties){
			//IClass currentClass = (IClass) me.getKey();
			ITable oldTable= (ITable) mappedClasses.get(currentClass);
			
			// clone table form oldtable as clone
			ITable currentTable = new TableImpl(oldTable);
			
			System.out.println("currentClass= " + currentClass.getLabel()
					+ "|| currentTable= " + currentTable.getName());

			Set<IObjectProperty> oProps = new HashSet<IObjectProperty>();
			oProps = currentClass.getObjectProperties();

			//if (oProps.size() > 0) { // only classes with object prop interrest
										// us
				for (IObjectProperty currentOprop : oProps) {

					IColumn currentCol = mapPropertyToColumn(currentOprop);

					System.out.println("currentOprop= "
							+ currentOprop.getLabel() + "|| Range "
							+ currentOprop.getRange().getLabel()
							+ "|| currentCol = " + currentCol.getName());
					currentTable.addColumn(currentCol);

					// using foreign key

					ConstraintForeignKeyImpl fkCons = new ConstraintForeignKeyImpl();

					fkCons.setName(currentTable.getName() + "_fk_"
							+ currentCol.getName());
					fkCons.setRelatedColumn(currentCol);
					fkCons.setRelatedTable(currentTable);
					String exp = "FOREIGN KEY ("+currentCol.getName()+" ) ";
							//+ normalizeForeignKeyColumnNameFromClassLabel(currentOprop
								//	.getRange().getLabel()) + ")";
					exp = exp
							+ " REFERENCES "
							+ StringFormatterTools.normalizeSqlTableNameFromClassLabel(currentOprop
									.getRange().getLabel())+" (id)";

					fkCons.setExpression(exp);
					// VT: A revoir
					IClass refClass = currentOprop.getRange();

					ITable refTable = mappedClasses.get(refClass);

					System.out.println("reftable " + refTable.getName());
					fkCons.setTargetTable((TableImpl) refTable);

					currentTable.addConstraint(fkCons);

				} // end map objectproperties

				replaceTableInMappedClasses(currentClass, oldTable, currentTable);
				 //schema.replaceTable(oldTable, currentTable);// 
			//}
		}
		System.out.println("// end 2nd pass to resolve fk map objectproperties");

		schema.initSchema(mappedClasses);// load table in schema
		
		return schema;
	}

	/**
	 * Gets the high than constraint from class and property.
	 * 
	 * @param cls the cls
	 * @param prop the prop
	 * 
	 * @return the high than constraint from class and property
	 */
	@SuppressWarnings("unchecked")
	private static IConstraintHighThan getHighThanConstraintFromClassAndProperty(
			IClass cls, IDatatypeProperty prop) {
		IConstraintHighThan cons = null;
		List<IConstraintHighThan> AllCons = new ArrayList<IConstraintHighThan>();
		String query = "SELECT * FROM m23_constraint_high_than   WHERE m23_constraint_high_than.class_id="
				+ cls.getId()
				+ " AND m23_constraint_high_than.datatype_prop_id="
				+ prop.getId();
		try {
			Transaction dTx = getDafoeSession().beginTransaction();
			AllCons = getDafoeSession().createSQLQuery(query).addEntity(
					HighThanImpl.class).list();

			if (AllCons.size() > 0) {
				return AllCons.get(0);
			}
			dTx.commit();

		} catch (Exception excep) {
			excep.printStackTrace();
			return cons;

		}

	
		return cons;
	}

	/**
	 * Gets the less than constraint from class and property.
	 * 
	 * @param cls the cls
	 * @param prop the prop
	 * 
	 * @return the less than constraint from class and property
	 */
	@SuppressWarnings("unchecked")
	private static IConstraintLessThan getLessThanConstraintFromClassAndProperty(
			IClass cls, IDatatypeProperty prop) {
		IConstraintLessThan cons = null;
		List<IConstraintLessThan> AllCons = new ArrayList<IConstraintLessThan>();

		try {
			Transaction dTx = getDafoeSession().beginTransaction();
			AllCons = getDafoeSession()
					.createSQLQuery(
							"SELECT m23_constraint_less_than.* FROM m23_constraint_less_than   WHERE m23_constraint_less_than.class_id="
									+ cls.getId().toString()
									+ " AND m23_constraint_less_than.datatype_prop_id="
									+ prop.getId().toString() + " ORDER BY id")
					.addEntity("m23_constraint_less_than", LessThanImpl.class)
					.list();

			if (AllCons.size() > 0) {
				return AllCons.get(0);
			}
			dTx.commit();

		} catch (Exception excep) {
			excep.printStackTrace();
			return cons;

		}

		return cons;
	}

	/**
	 * Gets the checks for value constraint from class and property.
	 * 
	 * @param cls the cls
	 * @param prop the prop
	 * 
	 * @return the checks for value constraint from class and property
	 */
	@SuppressWarnings("unchecked")
	private static IConstraintHasValue getHasValueConstraintFromClassAndProperty(
			IClass cls, IDatatypeProperty prop) {
		IConstraintHasValue cons = null;
		List<IConstraintHasValue> AllCons = new ArrayList<IConstraintHasValue>();

		try {
			Transaction dTx = getDafoeSession().beginTransaction();
			AllCons = getDafoeSession()
					.createSQLQuery(
							"SELECT * FROM m23_constraint_has_value   WHERE m23_constraint_has_value.class_id="
									+ cls.getId().toString()
									+ "AND m23_constraint_has_value.datatype_prop_id="
									+ prop.getId().toString() + " ORDER BY id")
					.addEntity(HasValueImpl.class).list();

			if (AllCons.size() > 0) {
				return AllCons.get(0);
			}
			dTx.commit();

		} catch (Exception excep) {
			excep.printStackTrace();
			return cons;

		}

		return cons;
	}

	/**
	 * Gets the one of constraint from class and property.
	 * 
	 * @param cls the cls
	 * @param prop the prop
	 * 
	 * @return the one of constraint from class and property
	 */
	@SuppressWarnings("unchecked")
	private static IConstraintOneOf getOneOfConstraintFromClassAndProperty(
			IClass cls, IDatatypeProperty prop) {
		IConstraintOneOf cons = null;
		List<IConstraintOneOf> AllCons = new ArrayList<IConstraintOneOf>();

		try {
			Transaction dTx = getDafoeSession().beginTransaction();
			AllCons = getDafoeSession().createSQLQuery(
					"SELECT * FROM m23_constraint_one_of   WHERE m23_constraint_one_of.class_id="
							+ cls.getId().toString()
							+ "AND m23_constraint_one_of.datatype_prop_id="
							+ prop.getId().toString() + " ORDER BY id")
					.addEntity(OneOfImpl.class).list();

			if (AllCons.size() > 0) {
				return AllCons.get(0);
			}
			dTx.commit();

		} catch (Exception excep) {
			excep.printStackTrace();
			return cons;

		}

		return cons;
	}

	/**
	 * Gets the like constraint from class and property.
	 * 
	 * @param cls the cls
	 * @param prop the prop
	 * 
	 * @return the like constraint from class and property
	 */
	@SuppressWarnings("unchecked")
	private static IConstraint getLikeConstraintFromClassAndProperty(
			IClass cls, IDatatypeProperty prop) {
		IConstraint cons = null;
		List<IConstraint> AllCons = new ArrayList<IConstraint>();

		try {
			Transaction dTx = getDafoeSession().beginTransaction();
			AllCons = getDafoeSession().createSQLQuery(
					"SELECT * FROM m23_constraint_like   WHERE m23_constraint_like.class_id="
							+ cls.getId().toString()
							+ "AND m23_constraint_like.datatype_prop_id="
							+ prop.getId().toString() + " ORDER BY id")
					.addEntity(OneOfImpl.class).list();

			if (AllCons.size() > 0) {
				return AllCons.get(0);
			}

		} catch (Exception excep) {
			excep.printStackTrace();
			return cons;

		}

		return cons;
	}

	/**
	 * Gets the min max constraint from class and property.
	 * 
	 * @param cls the cls
	 * @param prop the prop
	 * 
	 * @return the min max constraint from class and property
	 */
	@SuppressWarnings("unchecked")
	private static IConstraint getMinMaxConstraintFromClassAndProperty(
			IClass cls, IObjectProperty prop) {
		IConstraint cons = null;
		List<IConstraint> AllCons = new ArrayList<IConstraint>();

		try {
			Transaction dTx = getDafoeSession().beginTransaction();
			AllCons = getDafoeSession().createSQLQuery(
					"SELECT * FROM m23_constraint_min_max   WHERE m23_constraint_min_max.class_id="
							+ cls.getId().toString()
							+ "AND m23_constraint_min_max.datatype_prop_id="
							+ prop.getId().toString() + " ORDER BY id")
					.addEntity(MinMaxImpl.class).list();

			if (AllCons.size() > 0) {
				return AllCons.get(0);
			}

		} catch (Exception excep) {
			excep.printStackTrace();
			return cons;

		}

		return cons;
	}

	/**
	 * Gets the some value constraint from class and property.
	 * 
	 * @param cls the cls
	 * @param prop the prop
	 * 
	 * @return the some value constraint from class and property
	 */
	@SuppressWarnings("unchecked")
	private static IConstraint getSomeValueConstraintFromClassAndProperty(
			IClass cls, IObjectProperty prop) {
		IConstraint cons = null;
		List<IConstraint> AllCons = new ArrayList<IConstraint>();

		try {
			Transaction dTx = getDafoeSession().beginTransaction();
			AllCons = getDafoeSession()
					.createSQLQuery(
							"SELECT * FROM m23_constraint_some_value_from   WHERE m23_constraint_some_value_from.class_id="
									+ cls.getId().toString()
									+ "AND m23_constraint_some_value_from.datatype_prop_id="
									+ prop.getId().toString() + " ORDER BY id")
					.addEntity(SomeValueFromImpl.class).list();

			if (AllCons.size() > 0) {
				return AllCons.get(0);
			}

		} catch (Exception excep) {
			excep.printStackTrace();
			return cons;

		}

		return cons;
	}

	/**
	 * Gets the all value constraint from class and property.
	 * 
	 * @param cls the cls
	 * @param prop the prop
	 * 
	 * @return the all value constraint from class and property
	 */
	@SuppressWarnings("unchecked")
	public static IConstraint getAllValueConstraintFromClassAndProperty(
			IClass cls, IObjectProperty prop) {
		IConstraint cons = null;
		List<IConstraint> AllCons = new ArrayList<IConstraint>();

		try {
			Transaction dTx = getDafoeSession().beginTransaction();
			AllCons = getDafoeSession()
					.createSQLQuery(
							"SELECT * FROM m23_constraint_all_value_from   WHERE m23_constraint_all_value_from.class_id="
									+ cls.getId().toString()
									+ "AND m23_constraint_all_value_from.datatype_prop_id="
									+ prop.getId().toString() + " ORDER BY id")
					.addEntity(SomeValueFromImpl.class).list();

			if (AllCons.size() > 0) {
				return AllCons.get(0);
			}

		} catch (Exception excep) {
			excep.printStackTrace();
			return cons;

		}

		return cons;
	}
	
	
	
}
