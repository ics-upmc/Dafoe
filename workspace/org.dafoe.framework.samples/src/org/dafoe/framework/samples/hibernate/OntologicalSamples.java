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
package org.dafoe.framework.samples.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IClassExtension;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IOntoAnnotationType;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.ExtensionManagerImpl;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassAnnotationImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassExtension;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DataTypePropertyAnnotationImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyAnnotationImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntoAnnotationTypeImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntologyImpl;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

// TODO: Auto-generated Javadoc
/**
 * The Class OntologicalSamples.
 */
/**
 * The Class OntologicalSamples provides examples of how to manage data in the
 * ontological layer.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class OntologicalSamples {

	/**
	 * Instantiates a new ontological samples.
	 */
	public OntologicalSamples() {
		// TODO Auto-generated constructor stub
	}

	public static void initDataSource() {
		IDataSource con = new DataSource("jdbc:postgresql://localhost:5432/DAFOE2", "postgres",
				"postgres");

		SessionFactoryImpl.openDynamicSession(con);
	}

	static Session getDafoeSession() {
		initDataSource();
		// VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	

	public static void testCreateClasse() {//ok
		System.out.println("void testCreateClasse()");
		Session hSession = getDafoeSession();

		Transaction tx = hSession.beginTransaction();
		IOntology onto = new OntologyImpl();
		onto.setName("toto_onto_3");
		onto.setNameSpace("www.toto_3");

		hSession.save(onto);

		IClass cls = new ClassImpl();
		cls.setLabel("toto_class_3");
		cls.setState(ONTO_OBJECT_STATE.REJECTED);
		// cls.setOntology(onto);

		hSession.save(cls);

		tx.commit();
	}

	public static void testCreateDeleteClasse() {//ok
		System.out.println("void testCreateDeleteClasse()");
		Session hSession = getDafoeSession();

		// create 
		
		Transaction tx = hSession.beginTransaction();

		IOntology onto = new OntologyImpl();
		IClass cls = new ClassImpl();
		
		onto.setName("toto_onto_3");
		onto.setNameSpace("www.toto_3");
		onto.setLanguage("fr");
		
		hSession.save(onto);

		
		cls.setLabel("toto_class_3");
		cls.setState(ONTO_OBJECT_STATE.REJECTED);
		
		
		onto.addOntoObject(cls);

		hSession.save(cls);

		tx.commit();

		hSession.refresh(onto);
		
		// delete
		
		tx = hSession.beginTransaction();
		
		onto.removeOntoObject(cls);
		
		//hSession.delete(cls);

		Object obj = hSession.load(ClassImpl.class, cls.getId());

		hSession.delete(obj);
		
		tx.commit();
		

	}
	
	@SuppressWarnings("unchecked")
	public static List<IOntology> getAllClassFromOntology() {//ok

		System.out.println("List<IOntology> getAllClassFromontology() ");
		Session hSession = getDafoeSession();

		Transaction tx = hSession.beginTransaction();

		List<IOntology> ontos = hSession.createCriteria(OntologyImpl.class)
				.list();

		System.out.println("size= " + ontos.size());

		for (IOntology onto : ontos) {
			Set<IClass> cls = onto.getClasses();
			for (IClass c : cls) {
				System.out.println("class id= " + c.getId() + " || label= "
						+ "|| child size= " + c.getChildren().size()
						+ "|| parent size= " + c.getParents().size()
						+ "|| dataprop size= "
						+ c.getDatatypeProperties().size()
						+ " objectprop size=" + c.getObjectProperties().size());
			}
		}
		tx.commit();
		return ontos;
	}

	/**
	 * Delete class sample.
	 * 
	 * @param classId
	 *            the class id
	 */
	public void deleteClassSample(int classId) throws HibernateException {//ok
		Session hSession = getDafoeSession();

		Object obj = hSession.load(ClassImpl.class, classId);

		hSession.delete(obj);

	}

	/**
	 * Gets the class sample.
	 * 
	 * @param classId
	 *            the class id
	 * 
	 * @return the class sample
	 */
	public IClass getClassSample(int classId) throws HibernateException {//ok
		Session hSession = getDafoeSession();

		IClass cls = (IClass) hSession.load(ClassImpl.class, classId);
		System.out.println("class id= " + cls.getId() + " || label= "
				+ "|| child size= " + cls.getChildren().size()
				+ "|| parent size= " + cls.getParents().size());

		return cls;
	}

	/**
	 * Gets the all class sample.
	 * 
	 * @return the all class sample
	 */
	@SuppressWarnings("unchecked")
	public static List<IClass> getAllClassSample() throws HibernateException {//ok
		Session hSession = getDafoeSession();

		List<IClass> classes = hSession.createCriteria(ClassImpl.class).list();
		System.out.println(hSession.getFlushMode().toString());
		System.out.println("size= " + classes.size());

		for (IClass cls : classes) {
			// IDocument doc=s.getDocument();
			System.out.println("class id= " + cls.getId() + " || label= "
					+ "|| child size= " + cls.getChildren().size()
					+ "|| parent size= " + cls.getParents().size()
					+ "|| dataprop size= " + cls.getDatatypeProperties().size()
					+ " objectprop size=" + cls.getObjectProperties().size());
		}

		return classes;

	}

	@SuppressWarnings("unchecked")
	public void testgetParents() throws HibernateException {//ok

		Session hSession = getDafoeSession();

		List<IClass> classes = hSession
				.createCriteria(ObjectPropertyImpl.class).list();
		
		System.out.println("size= " + classes.size());

		for (IClass cls : classes) {
			Set<IClass> parents = cls.getParents();
			Iterator<IClass> iter = parents.iterator();
			System.out.println("PARENTS de: " + cls.getLabel().toUpperCase());
			while (iter.hasNext()) {
				System.out.println("label= " + iter.next().getLabel());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void testgetChildren() throws HibernateException {//ok

		Session hSession = getDafoeSession();

		List<IClass> classes = hSession
				.createCriteria(ObjectPropertyImpl.class).list();
		
		System.out.println("size= " + classes.size());

		for (IClass cls : classes) {
			Set<IClass> children = cls.getChildren();
			Iterator<IClass> iter = children.iterator();
			System.out.println("CHILDREN de: " + cls.getLabel());
			while (iter.hasNext()) {
				System.out.println("label= " + iter.next().getLabel());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void testgetDataProperty() throws HibernateException {//ok

		Session hSession = getDafoeSession();

		List<IClass> classes = hSession
				.createCriteria(ObjectPropertyImpl.class).list();
		
		System.out.println("size= " + classes.size());

		for (IClass cls : classes) {
			Set<IDatatypeProperty> dprops = cls.getDatatypeProperties();
			Iterator<IDatatypeProperty> iter = dprops.iterator();
			System.out.println("DATA_PROPERTIES de: " + cls.getLabel());
			while (iter.hasNext()) {
				System.out.println("label= " + iter.next().getLabel());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void testgetObjectProperty() throws HibernateException {//ok

		Session hSession = getDafoeSession();

		List<IClass> classes = hSession
				.createCriteria(ObjectPropertyImpl.class).list();
		
		System.out.println("size= " + classes.size());

		for (IClass cls : classes) {
			Set<IObjectProperty> oprops = cls.getObjectProperties();
			Iterator<IObjectProperty> iter = oprops.iterator();
			System.out.println("OBJECT_PROPERTIES de: " + cls.getLabel());
			while (iter.hasNext()) {
				System.out.println("label= " + iter.next().getLabel());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void testgetDataPropertyDomains() throws HibernateException {//ok

		Session hSession = getDafoeSession();

		List<IDatatypeProperty> dProps = hSession.createCriteria(
				ObjectPropertyImpl.class).list();
		
		System.out.println("size= " + dProps.size());

		for (IDatatypeProperty dprop : dProps) {
			Set<IClass> domains = dprop.getDomains();
			Iterator<IClass> iter = domains.iterator();
			System.out.println("DataProp_DOMAINS de: " + dprop.getLabel());
			while (iter.hasNext()) {
				System.out.println("label= " + iter.next().getLabel());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void testgetObjectPropertyDomains() throws HibernateException {//ok

		Session hSession = getDafoeSession();

		// List<IObjectProperty> dProps =
		// dSession.findAllInstance(ObjectPropertyImpl.class);
		List<IObjectProperty> dProps = hSession.createCriteria(
				ObjectPropertyImpl.class).list();
		System.out.println("size= " + dProps.size());

		for (IObjectProperty dprop : dProps) {
			Set<IClass> domains = dprop.getDomains();
			Iterator<IClass> iter = domains.iterator();
			System.out.println("ObjectProp_DOMAINS de: " + dprop.getLabel());
			while (iter.hasNext()) {
				System.out.println("label= " + iter.next().getLabel());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void testgetProperty() throws HibernateException {//ok

		Session hSession = getDafoeSession();

		List<IClass> classes = hSession
				.createCriteria(ObjectPropertyImpl.class).list();
		System.out.println("size= " + classes.size());

		for (IClass cls : classes) {
			Set<IProperty> props = cls.getProperties();
			Iterator<IProperty> iter = props.iterator();
			System.out.println("PROPERTIES de: " + cls.getLabel());
			while (iter.hasNext()) {
				System.out.println("label= " + iter.next().getLabel());
			}
		}

	}

	public static void testAddClassAnnotation() {//ok
		System.out.println("/////////////////  testAnnotation()");
		Session hSession = getDafoeSession();
		org.hibernate.Transaction tx = hSession.beginTransaction();

		// build an annotation type
		IOntoAnnotationType annotType = new OntoAnnotationTypeImpl();
		annotType.setLabel("uri");
		annotType.setDataType("STRING");

		hSession.save(annotType);

		// build an annotation value
		IOntoAnnotation annot = new ClassAnnotationImpl();
		annot.setValue("org.dafoe");
		annot.setOntoAnnotationType(annotType);// annotation type setting
		hSession.save(annot);

		// build an IClass and annote it
		IClass toto = new ClassImpl();
		toto.setLabel("toto");
		toto.setState(ONTO_OBJECT_STATE.VALIDATED);
		toto.addAnnotation(annot);// annotation setting
		hSession.save(toto);

		tx.commit();
		System.out.println("///////////////// end testAnnotation()");
	}

	public static void testGetClassAnnotation(int classId) {//ok
		System.out.println("/////////////////  testGetAnnotation()");
		Session hSession = getDafoeSession();
		org.hibernate.Transaction tx = hSession.beginTransaction();

		IClass cls = (IClass) hSession.load(ClassImpl.class, classId);
		Set<IOntoAnnotation> annots = cls.getAnnotations();
		for (IOntoAnnotation annot : annots) {
			IOntoAnnotationType annotType = annot.getOntoAnnotationType();
			System.out.println("OntAnnot label= " + annotType.getLabel()
					+ " || OntoAnnot type= " + annotType.getDataType()
					+ " || Annot Value= " + annot.getValue());
		}

		tx.commit();
		System.out.println("///////////////// end testGetAnnotation()");
	}

	public static void testAddObjectPropAnnotation() {//ok
		System.out.println("/////////////////  testAnnotation()");
		Session hSession = getDafoeSession();
		org.hibernate.Transaction tx = hSession.beginTransaction();

		// build an annotation type
		IOntoAnnotationType annotType = new OntoAnnotationTypeImpl();
		annotType.setLabel("uri_object_prop");
		annotType.setDataType("STRING");

		hSession.save(annotType);

		// build an annotation value
		IOntoAnnotation annot = new ObjectPropertyAnnotationImpl();
		annot.setValue("org.dafoe.object_prop");
		annot.setOntoAnnotationType(annotType); // annotation type setting
		hSession.save(annot);

		// build an IObjectProperty and annote it
		IObjectProperty toto = new ObjectPropertyImpl();
		toto.setLabel("p_toto");
		toto.setState(ONTO_OBJECT_STATE.VALIDATED);
		toto.addAnnotation(annot); // annotation setting
		hSession.save(toto);

		tx.commit();
		System.out.println("///////////////// end testAnnotation()");
	}

	public static void testGetObjectPropAnnotation(int propId) {//ok
		System.out.println("/////////////////  testGetAnnotation()");
		Session hSession = getDafoeSession();
		org.hibernate.Transaction tx = hSession.beginTransaction();

		IObjectProperty oProp = (IObjectProperty) hSession.load(
				ObjectPropertyImpl.class, propId);
		Set<IOntoAnnotation> annots = oProp.getAnnotations();
		for (IOntoAnnotation annot : annots) {
			IOntoAnnotationType annotType = annot.getOntoAnnotationType();
			System.out.println("OntAnnot label= " + annotType.getLabel()
					+ " || OntoAnnot type= " + annotType.getDataType()
					+ " || Annot Value= " + annot.getValue());
		}

		tx.commit();
		System.out.println("///////////////// end testGetAnnotation()");
	}

	public static void testAddDatatypePropAnnotation() {//ok
		System.out.println("/////////////////  testAnnotation()");
		Session hSession = getDafoeSession();
		org.hibernate.Transaction tx = hSession.beginTransaction();
		IOntoAnnotationType annotType = new OntoAnnotationTypeImpl();
		annotType.setLabel("uri_datatype_prop");
		annotType.setDataType("STRING");

		hSession.save(annotType);

		IOntoAnnotation annot = new DataTypePropertyAnnotationImpl();
		annot.setValue("org.dafoe.datatype_prop");
		annot.setOntoAnnotationType(annotType);
		hSession.save(annot);

		IDatatypeProperty toto = new DatatypePropertyImpl();
		toto.setLabel("d_toto");
		toto.setState(ONTO_OBJECT_STATE.VALIDATED);
		toto.addAnnotation(annot);
		hSession.save(toto);

		tx.commit();
		System.out.println("///////////////// end testAnnotation()");
	}

	public static void testGetDatatypePropAnnotation(int propId) {//ok
		System.out.println("/////////////////  testGetAnnotation()");
		Session hSession = getDafoeSession();
		org.hibernate.Transaction tx = hSession.beginTransaction();

		IDatatypeProperty dProp = (IDatatypeProperty) hSession.load(
				DatatypePropertyImpl.class, propId);
		Set<IOntoAnnotation> annots = dProp.getAnnotations();
		for (IOntoAnnotation annot : annots) {
			IOntoAnnotationType annotType = annot.getOntoAnnotationType();
			System.out.println("OntAnnot label= " + annotType.getLabel()
					+ " || OntoAnnot type= " + annotType.getDataType()
					+ " || Annot Value= " + annot.getValue());
		}

		tx.commit();
		System.out.println("///////////////// end testGetAnnotation()");
	}

	@SuppressWarnings("unchecked")
	public static void testGetClassExtension() {//ok
		Session hSession = getDafoeSession();

		List<IClass> classes = hSession.createCriteria(ClassImpl.class).list();
		
		System.out.println("size= " + classes.size());

		for (IClass cls : classes) {
			// IDocument doc=s.getDocument();
			System.out.println(cls.getLabel()+" extension size= "+cls.getClassExtensions().size());
			for(IClassExtension ext: cls.getClassExtensions()){
				System.out.println("===>"+ ext.toString());
			}
		}
	}
	
	
	public static IClassExtension testCreateClassExtension(IClass cls,
			Set<IProperty> props) {//ok

		System.out.println("testCreateClassExtension ");

		IClassExtension clsExt = new ClassExtension();
		Object value;
		clsExt.setId(4);
		clsExt.setPreferedClass(cls);

		for (IProperty prop: props) {
			
			value = 15;

			clsExt.getTuple().put(prop, value);

		}

		return clsExt;
	}

	public static void testSaveClassExtension(){//ok
		
		Session hSession = getDafoeSession();

		IClass cls = (IClass) hSession.load(ClassImpl.class, 2);
		System.out.println("size before add= "+cls.getClassExtensions().size());
		IClassExtension ext= testCreateClassExtension(cls,cls.getProperties());
		
		//ExtensionManagerImpl.saveClassExtension(ext);
		
		cls.addClassExtension(ext);
		
		System.out.println("size after add= "+cls.getClassExtensions().size());
	}
	
	
	public static void testDeleteClassExtension(){//ok
	
		Session hSession = getDafoeSession();

		IClass cls = (IClass) hSession.load(ClassImpl.class, 2);
		System.out.println("size before delete= "+cls.getClassExtensions().size());
		//IClassExtension ext= testCreateClassExtension(cls,cls.getProperties());
		
		ExtensionManagerImpl.deleteClassExtension(cls, 4);
		
		//cls.removeClassExtension(10);
		
		//cls.remo(ext);
		
		System.out.println("size after delete= "+cls.getClassExtensions().size());
		
	}
	
	
	
	
	/////////////  MAPPING ////////////////////////////////////////////////////
	
	public static void testGetClassMappedTOObject(){
		
		Session hSession = getDafoeSession();

		System.out.println("=============== Begin");

		IClass cl = (IClass) hSession.load(ClassImpl.class, 1);
		
		System.out.println("Class = " + cl.getLabel());
		
		Set<ITerminoOntoObject> too = cl.getMappedTerminoOntoObjects();
		Iterator<ITerminoOntoObject> it = too.iterator();
		
		while(it.hasNext()) {
			
			System.out.println(it.next().getClass().getName());
		}
	
		System.out.println("=============== End");

	}
	
	
	
	
	
	
	
	/**
	 * The main method is used to test samples method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) throws HibernateException {

		// new OntologicalSamples().createClassSample();

		// System.out.println("/////////////  get all class");
		getAllClassSample();

		// new OntologicalSamples().createClassWithChildSample();

		// new OntologicalSamples().createObjectPropertySample();

		// new OntologicalSamples().createDatatypePropertySample();

		// System.out.println("//////////////  get parents");
		// new OntologicalSamples().testgetParents();

		// new OntologicalSamples().testgetChildren();

		// new OntologicalSamples().testgetDataProperty();

		// new OntologicalSamples().testgetObjectProperty();

		// new OntologicalSamples().testgetDataPropertyDomains();

		// new OntologicalSamples().testgetObjectPropertyDomains();

		// System.out.println("/////////////////  get property");
		// new OntologicalSamples().testgetProperty();

		// testAddClassAnnotation(); //ok
		// testGetClassAnnotation(1);//ok

		// testAddObjectPropAnnotation();

		// testCreateClasse();

		//testCreateDeleteClasse();
		
		//getAllClassFromOntology();//ok
		
		//testGetClassExtension();//ok
		
		//testSaveClassExtension();//ok
		
		//testDeleteClassExtension();
		
		//testGetClassMappedTOObject();

	}
}
