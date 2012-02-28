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

package org.dafoe.ontologiclevel.common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IOntoAnnotationType;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.BasicDatatypeImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassAnnotationImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DataTypePropertyAnnotationImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyAnnotationImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntoAnnotationTypeImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptRelationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TypeRelationTcImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * The DatabaseAdapter Class provide services for querying database.
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * @author <a href="mailto:texier@ensma.fr">Guillaume TEXIER</a>
 */
public class DatabaseAdapter {


	private static final String DEFAULT_ONTOLOGY_NAME = "Dafoe ontology";

	
	protected static List<IClass> classes = new ArrayList<IClass>();
	
	
	protected static List<IObjectProperty> objectProperties = new ArrayList<IObjectProperty>();
	
	
	protected static List<IDatatypeProperty> dataProperties = new ArrayList<IDatatypeProperty>();

	//

	/**
	 * Gets the current created dafoe session.
	 *
	 * @return the dafoe session
	 */
	public static Session getDafoeSession()  {
		return SessionFactoryImpl.getCurrentDynamicSession() ;
	}

	//

	
	public static void commitAll() {
		/*if (dTx!=null) {
			dTx.commit();
		}*/
	}

	//

	/**
	 * Refresh.
	 *
	 * @param classe the classe
	 */
	public static void refresh(IClass classe) {
		getDafoeSession().refresh(classe);

	}

	//

	/**
	 * Load all classes of an ontology .
	 *
	 * @param ontology the ontology
	 */
	public static void loadClasses(IOntology ontology) {
		try {
			Transaction dTx = getDafoeSession().beginTransaction();
			
			for(IClass c: ontology.getClasses()){
				classes.add(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Gets the classes.
	 *
	 * @return the classes
	 */
	public static List<IClass> getClasses() {
		return classes;
	}

	//

	/**
	 * Gets the top classes a given ontology.
	 *
	 * @param ontology the ontology
	 * @return the top classes
	 */
	public static List<IClass> getTopClasses(IOntology ontology) {

		try {
			
			List<IClass> lesClasses = getDafoeSession().createSQLQuery("SELECT m23_class.* FROM m23_class left join m23_class_2parent2_class as lien on lien.class_child_id=m23_class.id WHERE lien.class_child_id is null and m23_class.ontology_id="+ontology.getId().toString()+" ORDER BY label").addEntity("m23_class",ClassImpl.class).list();

			return lesClasses;
			
		} catch(Exception excep) {

			return new ArrayList<IClass>();

		}

	}

	//

	/**
	 * Gets the classes.
	 *
	 * @param ontology the ontology
	 * @return the classes
	 */
	public static List<IClass> getClasses(IOntology ontology) {

		try {
			List<IClass> lesClasses = getDafoeSession().createSQLQuery("SELECT m23_class.* FROM m23_class WHERE m23_class.ontology_id="+ontology.getId().toString()+" ORDER BY label").addEntity("m23_class",ClassImpl.class).list();

			return lesClasses;
		} catch(Exception excep) {

			return new ArrayList<IClass>();

		}

	}

	//

	/**
	 * Gets the top termino concepts.
	 *
	 * @return the top termino concepts
	 */
	public static List<ITerminoConcept> getTopTerminoConcepts() {

		try {
			List<ITerminoConcept> lesClasses = getDafoeSession().createSQLQuery("SELECT m22_terminoconcept.* FROM m22_terminoconcept left join m22_tc_2parent2_tc as lien on lien.tc_child_id=m22_terminoconcept.id WHERE lien.tc_child_id is null ORDER BY label").addEntity("m22_terminoconcept",TerminoConceptImpl.class).list();

			return lesClasses;
		} catch(Exception excep) {

			return new ArrayList<ITerminoConcept>();

		}


	}

	//

	/**
	 * Gets the top object properties.
	 *
	 * @param ontology the ontology
	 * @return the top object properties
	 */
	public static List<IObjectProperty> getTopObjectProperties(IOntology ontology) {

		try {
			List<IObjectProperty> lesClasses = getDafoeSession().
				createSQLQuery("SELECT m23_object_property.* FROM m23_object_property"+
					" WHERE (parent_id=0 or parent_id is null) and m23_object_property.ontology_id="+ontology.getId().toString()).addEntity("m23_object_property",ObjectPropertyImpl.class).list();

			return lesClasses;
		} catch(Exception excep) {

			return new ArrayList<IObjectProperty>();

		}


	}

	//

	/**
	 * Gets the top data properties.
	 *
	 * @param ontology the ontology
	 * @return the top data properties
	 */
	public static List<IDatatypeProperty> getTopDataProperties(IOntology ontology) {

		try {
			List<IDatatypeProperty> lesClasses = getDafoeSession().
			createSQLQuery("SELECT m23_datatype_property.* FROM m23_datatype_property "+
					"WHERE (parent_id=0 or parent_id is null) and m23_datatype_property.ontology_id="+ontology.getId().toString()).addEntity("m23_datatype_property",DatatypePropertyImpl.class).list();

			return lesClasses;
		} catch(Exception excep) {

			return new ArrayList<IDatatypeProperty>();

		}


	}

	//

	/**
	 * Gets the top properties.
	 *
	 * @param ontology the ontology
	 * @return the top properties
	 */
	public static List<IProperty> getTopProperties(IOntology ontology) {

		try {
			ArrayList<IProperty> props = new ArrayList<IProperty>();
			props.addAll(getTopDataProperties(ontology));
			props.addAll(getTopObjectProperties(ontology));
			return props;

		} catch(Exception excep) {

			return new ArrayList<IProperty>();

		}


	}

	//

	/**
	 * Gets the termino concept relations.
	 *
	 * @return the termino concept relations
	 */
	public static List<ITerminoConceptRelation> getTerminoConceptRelations() {

		try {
			return (List<ITerminoConceptRelation>)getDafoeSession().createCriteria(TerminoConceptRelationImpl.class).list();
		} catch(Exception excep) {

			return new ArrayList<ITerminoConceptRelation>();

		}


	}

	//

	/**
	 * Gets the top termino concept relation types.
	 *
	 * @return the top termino concept relation types
	 */
	public static List<ITypeRelationTc> getTopTerminoConceptRelationTypes() {

		try {
			
			List<ITypeRelationTc> res  = getDafoeSession().
				createSQLQuery("SELECT m22_type_relation_tc.* FROM m22_type_relation_tc WHERE m22_type_relation_tc.parent_id is null ORDER BY name").addEntity("m22_type_relation_tc", TypeRelationTcImpl.class).list();

			return res;
			
		} catch(Exception excep) {

			return new ArrayList<ITypeRelationTc>();

		}

	}

	//

	/**
	 * Gets the parents.
	 *
	 * @param classe the classe
	 * @return the parents
	 */
	public static List<IClass>getParents(IClass classe) {

		try {
			List<IClass> lesClasses = getDafoeSession().
			createSQLQuery("SELECT m23_class.* FROM m23_class inner join m23_class_2parent2_class as lien on lien.class_parent_id=m23_class.id WHERE lien.class_child_id="+classe.getId()+" ORDER BY label").addEntity("m23_class",ClassImpl.class).list();

			return lesClasses;
		} catch(Exception excep) {

			return new ArrayList<IClass>();

		}
	}

	//

	/**
	 * Removes the classe from children.
	 *
	 * @param pere the pere
	 * @param fils the fils
	 */
	public static void removeClasseFromChildren(IClass pere,IClass fils) {
		Transaction dTx =null;

		try {
			dTx = getDafoeSession().getTransaction();


			dTx.begin();
			fils.removeParent(pere);
			getDafoeSession().save(fils);

			dTx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	//

	/**
	 * Gets the classe.
	 *
	 * @param classeid the classeid
	 * @return the classe
	 */
	public static IClass getClasse(int classeid) {
		try {
			return (IClass)getDafoeSession().load(ClassImpl.class, classeid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//

	/**
	 * Save ontology.
	 *
	 * @param object the object
	 */
	public static void saveOntology(IOntology object) {

		try {

			Transaction dTx = getDafoeSession().beginTransaction();

			dTx.begin();
			
			getDafoeSession().save(object);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Save class.
	 *
	 * @param onto the onto
	 * @param clazz the clazz
	 * @param teminoOntoObject the temino onto object
	 */
	public static void saveClass(IOntology onto, IClass clazz, ITerminoOntoObject teminoOntoObject) {

		try {

			Transaction dTx = getDafoeSession().beginTransaction();

			onto.addOntoObject(clazz);

			if (teminoOntoObject != null) {

				clazz.addMappedTerminoOntoObject(teminoOntoObject);
				
			}
			
			getDafoeSession().save(clazz);

			dTx.commit();
			
			getDafoeSession().refresh(onto);
			
			if (teminoOntoObject != null) {

				getDafoeSession().refresh(teminoOntoObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Save annotation in database.
	 *
	 * @param ann the annotation
	 */
	public static void saveAnnotation(IOntoAnnotation ann) {

		try {

			Transaction dTx = getDafoeSession().beginTransaction();

			dTx.begin();

			getDafoeSession().save(ann);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	
	/**
	 * Creates the class annotation.
	 *
	 * @param clazz the class
	 * @param annotType the annotation type
	 * @param value the value of annotation
	 */
	public static void createClassAnnotation(IClass clazz, IOntoAnnotationType annotType, String value){
		Transaction dTx;
		
		try {
			
			dTx = getDafoeSession().beginTransaction();
			
			IOntoAnnotation annot = new ClassAnnotationImpl(); 
			
			annot.setOntoAnnotationType(annotType);
			
			annot.setValue(value);
			
			clazz.addAnnotation(annot);
			
			getDafoeSession().save(annot);
			
			dTx.commit();
			
			getDafoeSession().refresh(clazz);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//
	
	/**
	 * Creates the object property annotation.
	 *
	 * @param prop the prop
	 * @param annotType the annotation type
	 * @param value the value of annotation
	 */
	public static void createObjectPropertyAnnotation(IObjectProperty prop, IOntoAnnotationType annotType, String value){
		Transaction dTx;
		
		try {
			
			dTx = getDafoeSession().beginTransaction();
			
			IOntoAnnotation annot = new ObjectPropertyAnnotationImpl(); 
			
			annot.setOntoAnnotationType(annotType);
			
			annot.setValue(value);
			
			prop.addAnnotation(annot);
			
			getDafoeSession().save(annot);
			
			dTx.commit();
			
			getDafoeSession().refresh(prop);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//
	
	/**
	 * Creates the datatype annotation.
	 *
	 * @param prop the prop
	 * @param annotType the annotation type
	 * @param value the value
	 */
	public static void createDatatypeAnnotation(IDatatypeProperty prop, IOntoAnnotationType annotType, String value){
		Transaction dTx;
		
		try {
			
			dTx = getDafoeSession().beginTransaction();
			
			IOntoAnnotation annot = new DataTypePropertyAnnotationImpl(); 
			
			annot.setOntoAnnotationType(annotType);
			
			annot.setValue(value);
			
			prop.addAnnotation(annot);
			
			getDafoeSession().save(annot);
			
			dTx.commit();
			
			getDafoeSession().refresh(prop);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//

	/**
	 * Save annotation type.
	 *
	 * @param annoType the annotation type
	 */
	public static void saveAnnotationType(IOntoAnnotationType annoType) {

		try {

			Transaction dTx =null;

			dTx = getDafoeSession().beginTransaction();

			dTx.begin();

			getDafoeSession().save(annoType);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Save termino onto object.
	 *
	 * @param ann the annotation
	 */
	public static void saveTerminoOntoObject(ITerminoOntoObject ann) {

		try {

			Transaction dTx =null;

			dTx = getDafoeSession().beginTransaction();

			dTx.begin();
			getDafoeSession().save(ann);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Change parent hierarchy of a class.
	 *
	 * @param classe the class
	 * @param newparent the newparent
	 */
	public static void changeParent(IClass classe,IClass newparent) {
		Transaction dTx;
		
		try {

			if (classe==newparent) {
				return;
			}

			dTx = getDafoeSession().beginTransaction();

			dTx.begin();

			Iterator<IClass> iter = classe.getParents().iterator();
			
			while (iter.hasNext()) {
				IClass parent = iter.next();
				//parent.getChildren().remove(classe);
				parent.removeChild(classe);
				getDafoeSession().save(parent);
			}

			classe.addParent(newparent);

			getDafoeSession().save(classe);

			dTx.commit();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	
	/**
	 * Gets the object property for range.
	 *
	 * @param onto the onto
	 * @param clazz the class
	 * @return the object property for range
	 */
	public static List<IObjectProperty> getObjectPropertyForRange(IOntology onto, IClass clazz){
		
		System.out.println("SELECT m23_object_property.* FROM m23_object_property WHERE ((m23_object_property.ontology_id="+onto.getId().toString()+") and (m23_object_property.range_id=" + clazz.getId() + ")) ORDER BY label");
		
		List<IObjectProperty> objProps = new ArrayList<IObjectProperty>();
		try {
			objProps= getDafoeSession().
			createSQLQuery("SELECT m23_object_property.* FROM m23_object_property WHERE ((m23_object_property.ontology_id="+onto.getId().toString()+") and (m23_object_property.range_id=" + clazz.getId() + ")) ORDER BY label").addEntity("m23_object_property",ObjectPropertyImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return objProps;
	}
	
	//

	/**
	 * Delete class.
	 *
	 * @param onto the onto
	 * @param clazz the class
	 */
	public static void deleteClass(IOntology onto, IClass clazz) {
		Transaction dTx;
		try {
			
			
			
			List<IObjectProperty> objProperties = getObjectPropertyForRange(onto, clazz);
			
			dTx = getDafoeSession().beginTransaction();

			// remove links with possible property ranges
			
			Iterator<IObjectProperty> itObjProperties = objProperties.iterator();
			
			while(itObjProperties.hasNext()){
				IObjectProperty objProperty = itObjProperties.next();
				
				objProperty.setRange(null);
			}
			
			// remove links with possible property domains
			
			Set<IProperty> props = clazz.getProperties();
			
			Iterator<IProperty> itProp = props.iterator();

			while (itProp.hasNext()) {

				IProperty prop = itProp.next();
				prop.removeDomain(clazz);
				
				if (prop instanceof IObjectProperty) {
					clazz.removeObjectProperty((IObjectProperty)prop);
				} else {
					clazz.removeDatatypeProperty((IDatatypeProperty)prop);
				}
			}
						
			// remove mapping links with termino-onto objects
			
			Set<ITerminoOntoObject> terminoOntoObjects = clazz.getMappedTerminoOntoObjects();
			
			Iterator<ITerminoOntoObject> itTerminoOntoObject = terminoOntoObjects.iterator();
			
			while(itTerminoOntoObject.hasNext()) {
				ITerminoOntoObject terminoOntoObject = itTerminoOntoObject.next();
				
				terminoOntoObject.removeMappedOntoObject(clazz);
				clazz.removeMappedTerminoOntoObject(terminoOntoObject);
			}
			
			// remove links with possible super/sub classes
			
			Iterator<IClass> iter = clazz.getChildren().iterator();
			
			while (iter.hasNext()) {
				IClass enfant = iter.next();
				enfant.getParents().remove(clazz);
				getDafoeSession().save(enfant);
			}

			iter = clazz.getParents().iterator();
			
			while (iter.hasNext()) {
				IClass parent = iter.next();
				parent.getChildren().remove(clazz);
				getDafoeSession().save(parent);
			}
			
			onto.removeOntoObject(clazz);
			
			//VT:V0.6 pourquoi cette partie?
		Object obj = getDafoeSession().load(ClassImpl.class, clazz.getId());
//
			getDafoeSession().delete(obj);
			
			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Delete recursive class, so also delete its sub hierarchy.
	 *
	 * @param onto the onto
	 * @param clazz the class
	 */
	public static void deleteRecursiveClass(IOntology onto, IClass clazz) {
		Transaction dTx;
		try {

			dTx = getDafoeSession().beginTransaction();

			recursiveDelete(onto, clazz);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Recursive delete, so also delete sub hierarchy.
	 *
	 * @param onto the onto
	 * @param clazz the class
	 */
	public static void recursiveDelete(IOntology onto, IClass clazz) {

		Iterator<IClass> iter = clazz.getChildren().iterator();
		while (iter.hasNext()) {

			recursiveDelete(onto, iter.next());
		}

		try {
			
			onto.removeOntoObject(clazz);
			
			Object obj = getDafoeSession().load(ClassImpl.class, clazz.getId());

			getDafoeSession().delete(obj);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	/**
	 * Load all object properties from database.
	 */
	public static void loadObjectProperties() {
		try {
			//objectProperties = getDafoeSession().findAllInstance(IObjectProperty.class);
			objectProperties= (List<IObjectProperty>)getDafoeSession().createCriteria(IObjectProperty.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the object properties.
	 *
	 * @return the object properties
	 */
	public static List<IObjectProperty> getObjectProperties() {
		return objectProperties;
	}
	
	/**
	 * Gets the object property with a given id from the database.
	 *
	 * @param propid the propid
	 * @return the object property
	 */
	public static IObjectProperty getObjectProperty(int propid) {
		try {
			return (IObjectProperty)getDafoeSession().load(ObjectPropertyImpl.class, propid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//

	/**
	 * Load all datatype properties from database.
	 */
	public static void loadDatatypeProperties() {
		try {
			//dataProperties = getDafoeSession().findAllInstance(IDatatypeProperty.class);
			dataProperties = (List<IDatatypeProperty>)getDafoeSession().createCriteria(IDatatypeProperty.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Gets the datatype properties.
	 *
	 * @return the datatype properties
	 */
	public static List<IDatatypeProperty> getDatatypeProperties() {
		return dataProperties;
	}

	//

	/**
	 * Gets the datatype property with a given id from the database.
	 *
	 * @param propid the propid
	 * @return the datatype property
	 */
	public static IDatatypeProperty getDatatypeProperty(int propid) {
		try {
			return (IDatatypeProperty)getDafoeSession().load(DatatypePropertyImpl.class, propid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//
	
	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public static List<IProperty> getProperties() {

		List<IProperty> res = new ArrayList<IProperty>();
		
		loadDatatypeProperties();
		loadObjectProperties();	
		
		res.addAll(dataProperties);
		res.addAll(objectProperties);
	
		return res;
	}

	//
	
	/**
	 * Gets all properties of an ontology.
	 *
	 * @param ontology the ontology
	 * @return the properties
	 */
	public static List<IProperty> getProperties(IOntology ontology) {
		List<IProperty> res = new ArrayList<IProperty>();

		try {

			List<IDatatypeProperty> dataProps = getDafoeSession().
			createSQLQuery("SELECT m23_datatype_property.* FROM m23_datatype_property WHERE m23_datatype_property.ontology_id="+ontology.getId().toString()+" ORDER BY label").addEntity("m23_datatype_property",DatatypePropertyImpl.class).list();

			List<IObjectProperty> objProps = getDafoeSession().
				createSQLQuery("SELECT m23_object_property.* FROM m23_object_property WHERE m23_object_property.ontology_id="+ontology.getId().toString()+" ORDER BY label").addEntity("m23_object_property",ObjectPropertyImpl.class).list();

			res.addAll(dataProps);
			res.addAll(objProps);
			
		} catch(Exception excep) {

			excep.printStackTrace();
			
			return res;

		}
	
		return res;
	}

	
	//

	/**
	 * Gets the data types.
	 *
	 * @return the data types
	 */
	public static List<IBasicDatatype> getDataTypes() {
		try {
			return (List<IBasicDatatype>)getDafoeSession().createCriteria(IBasicDatatype.class).list();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IBasicDatatype>();
		}
	}

	//

	/**
	 * Gets all ontologies from database.
	 *
	 * @return the ontologies
	 */
	public static List<IOntology> getOntologies() {
		try {
			return (List<IOntology>)getDafoeSession().createCriteria(IOntology.class).list();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IOntology>();
		}
	}

	//

	/**
	 * Gets all annotations types from database.
	 *
	 * @return the annotations types
	 */
	public static List<IOntoAnnotationType> getAnnotationsTypes() {
		try {
			return (List<IOntoAnnotationType>)getDafoeSession().createCriteria(IOntoAnnotationType.class).list();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IOntoAnnotationType>();
		}
	}

	//

	/**
	 * Save the given property in the database.
	 *
	 * @param onto the onto
	 * @param prop the prop
	 * @param teminoOntoObject the temino onto object
	 */
	public static void saveProperty(IOntology onto, IProperty prop, ITerminoOntoObject teminoOntoObject) {

		try {

			Transaction dTx = getDafoeSession().beginTransaction();

			onto.addOntoObject(prop);

			if (teminoOntoObject != null) {

				prop.addMappedTerminoOntoObject(teminoOntoObject);
				
			}
			
			getDafoeSession().save(prop);

			dTx.commit();
			
			getDafoeSession().refresh(onto);
			
			if (teminoOntoObject != null) {

				getDafoeSession().refresh(teminoOntoObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

//	/**
//	 * Gets the children.
//	 *
//	 * @param classe the classe
//	 * @return the children
//	 */
//	public static List<IObjectProperty>getChildren(IObjectProperty  classe) {
//
//		try {
//			List<IObjectProperty> lesClasses = getDafoeSession().
//			createSQLQuery("SELECT * FROM m23_object_property WHERE parent_id="+classe.getId()+" ORDER BY label").addEntity("m23_object_property",ObjectPropertyImpl.class).list();
//
//			return lesClasses;
//			
//		} catch(Exception excep) {
//
//			return new ArrayList<IObjectProperty>();
//
//		}
//	}
//
//	//
//
//	/**
//	 * Gets the children.
//	 *
//	 * @param classe the classe
//	 * @return the childrent
//	 */
//	public static List<IDatatypeProperty>getChildren(IDatatypeProperty  classe) {
//
//		try {
//			List<IDatatypeProperty> lesClasses = getDafoeSession().
//			createSQLQuery("SELECT * FROM m23_datatype_property WHERE parent_id="+classe.getId()+" ORDER BY label").addEntity("m23_datatype_property",DatatypePropertyImpl.class).list();
//
//			return lesClasses;
//			
//		} catch(Exception excep) {
//
//			return new ArrayList<IDatatypeProperty>();
//
//		}
//	}
//
//	//
//
//	/**
//	 * Gets the annotations.
//	 *
//	 * @param classe the classe
//	 * @return the annotations
//	 */
//	public static List<IOntoAnnotation>getAnnotations(IClass  classe) {
//
//		try {
//			List<IOntoAnnotation> lesClasses = getDafoeSession().
//			createSQLQuery("SELECT * FROM m23_annotation_value_2_class WHERE class_id="+classe.getId()).addEntity("m23_annotation_value_2_class",ClassAnnotationImpl.class).list();
//
//			return lesClasses;
//			
//		} catch(Exception excep) {
//
//			return new ArrayList<IOntoAnnotation>();
//
//		}
//	}

	//

	/**
	 * Delete property.
	 *
	 * @param p the p
	 */
	public static void deleteProperty(IProperty p) {
		Transaction dTx;
		try {

			dTx = getDafoeSession().beginTransaction();

			dTx.begin();

			getDafoeSession().delete(p);

			dTx.commit();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Delete annotation.
	 *
	 * @param ontoObject the onto object
	 * @param annot the annot
	 */
	public static void deleteAnnotation(IOntoObject ontoObject, IOntoAnnotation annot) {
		Transaction dTx;
		try {
			
			ontoObject.removeAnnotation(annot);

			dTx = getDafoeSession().beginTransaction();
			
			getDafoeSession().delete(annot);

			dTx.commit();

			getDafoeSession().refresh(ontoObject);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	//

	/**
	 * Removes the domain association between class and property.
	 *
	 * @param classe the classe
	 * @param p the p
	 */
	public static void removeAssoc(IClass classe, IProperty p) {
		Transaction dTx;
		try {

			dTx = getDafoeSession().beginTransaction();

			p.removeDomain(classe);

			dTx.commit();

			getDafoeSession().refresh(classe);
			
			getDafoeSession().refresh(p);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Load default ontology.
	 *
	 * @return the i ontology
	 */
	public static IOntology loadDefaultOntology() {
		List<IOntology> ontologies = getOntologies();
		Iterator<IOntology> iterator = ontologies.iterator();
		IOntology res = null;

		while(iterator.hasNext()) {
			IOntology ontology = iterator.next();
			if (ontology.getName().compareTo(DEFAULT_ONTOLOGY_NAME) == 0) {

				res = ontology;

			}
		}

		return res;
	}

	//

	/**
	 * Find or create basic data type.
	 *
	 * @param label the label
	 * @return the i basic datatype
	 */
	public static IBasicDatatype findOrCreateBasicDataType(String label) {
		try {

			List<IBasicDatatype> lestypes = getDafoeSession().
			createSQLQuery("SELECT m23_basic_datatype.* FROM m23_basic_datatype WHERE label LIKE '"+label+"'ORDER BY id").addEntity("m23_basic_datatype",BasicDatatypeImpl.class).list();

			if (lestypes.size()>0) {
				return lestypes.get(0);
			} 
		} catch(Exception excep) {


		}
		IBasicDatatype res = new BasicDatatypeImpl();
		res.setLabel(label);
		saveBasicdatatype(res);
		return res;

	}

	//

	/**
	 * Save basicdatatype.
	 *
	 * @param prop the prop
	 */
	public static void saveBasicdatatype(IBasicDatatype prop) {
		try {
			Transaction dTx =null;
			dTx = getDafoeSession().beginTransaction();
			dTx.begin();
			getDafoeSession().save(prop);
			dTx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Gets the coment type.
	 *
	 * @return the coment type
	 */
	public static IOntoAnnotationType getComentType() {
		return (IOntoAnnotationType)getDafoeSession().load(OntoAnnotationTypeImpl.class, 2);
	}

	//

	/**
	 * Gets the label type.
	 *
	 * @return the label type
	 */
	public static IOntoAnnotationType getLabelType() {
		return (IOntoAnnotationType)getDafoeSession().load(OntoAnnotationTypeImpl.class, 1);
	}

	//

	/**
	 * Vide ontology.
	 *
	 * @param onto the onto
	 */
	public static void videOntology(IOntology onto) {
		Transaction dTx;
		try {

			dTx = getDafoeSession().beginTransaction();

			dTx.begin();
			for(IClass classe:getTopClasses(onto)) {
				getDafoeSession().delete(classe);
			}
			for(IProperty prop:getTopProperties(onto)) {
				getDafoeSession().delete(prop);
			}
			dTx.commit();


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Delete ontology from database.
	 *
	 * @param onto the onto
	 */
	public static void deleteOntology(IOntology onto) {
		Transaction dTx;
		try {

			dTx = getDafoeSession().beginTransaction();

			dTx.begin();
            
            onto.removeAllMappedTerminoOntology(onto.getMappedTerminoOntologies()); 
            
			getDafoeSession().delete(onto);

			dTx.commit();


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Count elements of ontology.
	 *
	 * @param ontology the ontology
	 * @return the int
	 */
	public static int countElements(IOntology ontology) {
		int res = 0;
		
		try {
			
			res+= ((BigInteger)getDafoeSession().createSQLQuery("SELECT count(m23_class.id) FROM m23_class WHERE m23_class.ontology_id="+ontology.getId().toString()).uniqueResult()).intValue();
			res+=((BigInteger)getDafoeSession().createSQLQuery("SELECT count(m23_datatype_property.id) FROM m23_datatype_property WHERE m23_datatype_property.ontology_id="+ontology.getId().toString()).uniqueResult()).intValue();
			res+=((BigInteger)getDafoeSession().createSQLQuery("SELECT count(m23_object_property.id) FROM m23_object_property WHERE m23_object_property.ontology_id="+ontology.getId().toString()).uniqueResult()).intValue();


		} catch(Exception excep) {

			excep.printStackTrace();
		}
		return res;
	}
	
	//

	/**
	 * Delete logical definition of a class.
	 *
	 * @param cl the cl
	 */
	public static void deleteLogicalDefinition(IClass cl) {
		Transaction dTx;
		try {

			dTx = getDafoeSession().beginTransaction();

			dTx.begin();

			cl.setLogicalDefinition("");

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		

}
