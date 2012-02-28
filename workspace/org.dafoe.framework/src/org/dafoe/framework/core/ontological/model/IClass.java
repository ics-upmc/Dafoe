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
package org.dafoe.framework.core.ontological.model;

import java.util.Set;



/**
 * The IClass Interface represent a class of an ontology.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IClass extends IOntoObject {

	//********************************* GETTERS AND SETTERS ***************************************************************
	/**
	 * Gets the id of the IClass.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the label of the IClass.
	 * 
	 * @return the label
	 */
	String getLabel();

	/**
	 * Sets the label of the IClass.
	 * 
	 * @param label the new label
	 */
	void setLabel(String label);

	
	
	/**
	 * Gets instances of the IClass.
	 * 
	 * @return the class instances
	 */
	Set<IClassInstance> getClassInstances();

	
	/**
	 * Gets direct children of the IClass.
	 * 
	 * @return the children
	 */
	Set<IClass> getChildren();

	/**
	 * Gets all children (all the sub hierarchy) of the IClass.
	 * 
	 * @return the all children
	 */
	Set<IClass> getDescendants();

	
	/**
	 * Gets direct parents  of the IClass.
	 * 
	 * @return the parent
	 */
	Set<IClass> getParents();

	/**
	 * Gets all parents (all the super hierarchy) of the IClass.
	 * 
	 * @return the all parents
	 */
	Set<IClass> getAncestors();

	/**
	 * Gets properties of the IClass (Both Object and Datatype properties).
	 * 
	 * @return the properties
	 */
	Set<IProperty> getProperties();
	
	/**
	 * Gets inherited properties of the IClass.
	 * 
	 * @return the inherited properties
	 */
	Set<IProperty> getInheritedProperties();

	/**
	 * Gets object properties of the IClass.
	 * 
	 * @return the object properties
	 */
	Set<IObjectProperty> getObjectProperties();
	
	/**
	 * Gets datatype properties of the IClass.
	 * 
	 * @return the datatype properties
	 */
	Set<IDatatypeProperty> getDatatypeProperties();
	

	/**
	 * Gets inherited datatype properties of the IClass.
	 * 
	 * @return the inherited datatype properties
	 */
	Set<IDatatypeProperty> getInheritedDatatypeProperties();
	
	/**
	 * Gets inherited object properties of the IClass.
	 * 
	 * @return the inherited object properties
	 */
	Set<IObjectProperty> getInheritedObjectProperties();

	
	
	// *****************************************  SERVICES *********************************************************************

	/**
	 * Adds a new subClass to the IClass.
	 *
	 * @param child the new subClass
	 * @return true, if successful
	 */
	boolean addChild(IClass child);

	
	/**
	 * Removes a subClass from the IClass.
	 *
	 * @param child the subClass to remove
	 * @return true, if successful
	 */
	boolean removeChild(IClass child);
	
	
	/**
	 *  Adds a new superClass to the IClass.
	 *
	 * @param parent the new superClass
	 * @return true, if successful
	 */
	boolean addParent(IClass parent);

	
	/**
	 * Removes a superClass from the IClass.
	 *
	 * @param parent the superClass to remove
	 * @return true, if successful
	 */
	boolean removeParent(IClass parent);
	
	
	/**
	 * Adds a new property (Object or Datatype) to the IClass.
	 *
	 * @param prop the new property 
	 * @return true, if successful
	 */
	boolean addProperty(IProperty prop);

	
	/**
	 * Removes the property (Object or Datatype) from the IClass.
	 *
	 * param prop the property to remove
	 * @return true, if successful
	 */
	boolean removeProperty(IProperty prop);
	
	
	/**
	 * Adds a new object property to the IClass.
	 *
	 * @param objProp the new object property
	 * @return true, if successful
	 */
	boolean addObjectProperty(IObjectProperty objProp);

	
	/**
	 * Removes the object property from the IClass.
	 *
	 * @param objProp the object property to remove
	 * @return true, if successful
	 */
	boolean removeObjectProperty(IObjectProperty objProp);

	
	/**
	 * Adds the datatype property to the IClass.
	 *
	 * @param dProp the new datatype property
	 * @return true, if successful
	 */
	boolean addDatatypeProperty(IDatatypeProperty dProp);

	
	/**
	 * Removes the datatype property from the IClass.
	 *
	 * @param dProp the datatype property to remove
	 * @return true, if successful
	 */
	boolean removeDatatypeProperty(IDatatypeProperty dProp);
	
	
	/**
	 * Adds a new instance to the IClass.
	 *
	 * @param classInst the new instance
	 * @return true, if successful
	 */
	boolean addClassInstance(IClassInstance classInst);

	
	/**
	 * Removes instance from the IClass.
	 *
	 * @param classInst the instance to remove 
	 * @return true, if successful
	 */
	boolean removeClassInstance(IClassInstance classInst);

     
	/**
	 * Gets the logical definition (using the first order logic) of the defined IClass.
	 *
	 * @return the logical definition
	 */
	String getLogicalDefinition ();
	
	/**
	 * Sets the logical definition for a defined IClass.
	 *
	 * @param logicalDefinition the new logical definition
	 */
	void setLogicalDefinition (String logicalDefinition);
		
	/**
	 * Gets the sql query useful to retrieve data of a defined IClass.
	 *
	 * @return the sql query
	 */
	String getSqlInstanceQuery ();
	
	/**
	 * Sets the sql query useful to retrieve data of a defined IClass.
	 *
	 * @param sqlInstanceQuery the new sql query
	 */
	void setSqlInstanceQuery (String sqlInstanceQuery);
	
	/**
	 * Gets the class extensions.
	 *
	 * @return the class extensions
	 */
	Set<IClassExtension> getClassExtensions();
		
	/**
	 * Adds the class extension.
	 *
	 * @param classExt the class ext
	 * @return true, if successful
	 */
	boolean addClassExtension(IClassExtension classExt);

	/**
	 * Removes the class extension.
	 *
	 * @param classExt the class ext
	 * @return true, if successful
	 */
	boolean removeClassExtension(IClassExtension classExt);
	
	//boolean removeClassExtension(Integer classExtId);
	
	
	//********* CONSTRAINT **********************************
	
	
	/**
	 * Gets the constraint one of.
	 *
	 * @return the constraint one of
	 */
	Set<IConstraintOneOf> getConstraintOneOf ();
	
	
	//public void addToConstraintOneOf (Object obj)

	
	/**
	 * Gets the constraint high than.
	 *
	 * @return the constraint high than
	 */
	Set<IConstraintHighThan> getConstraintHighThan ();
	
	//void addToConstraintHighThan (Object obj) ;
	
	/**
	 * Gets the constraint less than.
	 *
	 * @return the constraint less than
	 */
	Set<IConstraintLessThan> getConstraintLessThan () ;
	//void addToConstraintLessThan (Object obj);
	
	/**
	 * Gets the constraint has value.
	 *
	 * @return the constraint has value
	 */
	Set<IConstraintHasValue> getConstraintHasValue ();
	
	
	//void addToConstraintHasValue (Object obj);
	
	/**
	 * Gets the constraint like.
	 *
	 * @return the constraint like
	 */
	Set<IConstraintLike> getConstraintLike ();
	
	//void addToConstraintLike (Object obj);
	
	/**
	 * Gets the constraint min max.
	 *
	 * @return the constraint min max
	 */
	Set<IConstraintMinMax> getConstraintMinMax ();
	
	//void addToConstraintMinMax (Object obj);
	
	
	/**
	 * Gets the constraint all value from.
	 *
	 * @return the constraint all value from
	 */
	Set<IConstraintAllValueFrom> getConstraintAllValueFrom ();
	
	
	//void addToConstraintAllValueFrom (Object obj); 
	
	/**
	 * Gets the constraint some value from.
	 *
	 * @return the constraint some value from
	 */
	Set<IConstraintSomeValueFrom> getConstraintSomeValueFrom ();
	
	
	

}
