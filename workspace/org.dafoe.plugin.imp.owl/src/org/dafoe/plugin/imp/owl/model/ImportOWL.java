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

package org.dafoe.plugin.imp.owl.model;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IOntoAnnotationType;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassAnnotationImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DataTypePropertyAnnotationImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyAnnotationImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntoAnnotationTypeImpl;
import org.dafoe.plugin.imp.owl.adaptater.Services;
import org.dafoe.plugin.imp.owl.util.Util;
import org.hibernate.Transaction;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.inference.OWLClassReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLDataPropertyExpression;
import org.semanticweb.owl.model.OWLDataRange;
import org.semanticweb.owl.model.OWLDataType;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyExpression;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.util.ToldClassHierarchyReasoner;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

public class ImportOWL {

	private OWLOntologyManager manager;

	private OWLClassReasoner reasoner;

	private OWLOntology onto;

	private File ontoFile;

	private IOntology importedOntology;

	private Hashtable<OWLClass, IClass> class_owl_dafoe = new Hashtable<OWLClass, IClass>();

	private Hashtable<IClass, OWLClass> class_dafoe_owl = new Hashtable<IClass, OWLClass>();

	private List<OWLClass> definedClasses = new ArrayList<OWLClass>();

	private Hashtable<OWLObjectProperty, IProperty> oProp_owl_dafoe = new Hashtable<OWLObjectProperty, IProperty>();

	private Hashtable<IProperty, OWLObjectProperty> oProp_dafoe_owl = new Hashtable<IProperty, OWLObjectProperty>();

	private Hashtable<String, IOntoAnnotationType> annot_owl_dafoe = new Hashtable<String, IOntoAnnotationType>();

	private List<OWLObjectProperty> inverseProperties = new ArrayList<OWLObjectProperty>();

	private String language = "";

	// VT: number of object to import
	private int totalTime = 0;
	
	private Hashtable<OWLDataProperty, IProperty> dProp_owl_dafoe = new Hashtable<OWLDataProperty, IProperty>();

	private Hashtable<IProperty, OWLDataProperty> dProp_dafoe_owl = new Hashtable<IProperty, OWLDataProperty>();


	// VT: The Display is useless
	public ImportOWL(File ontoFile, IOntology ontology) {
		this.ontoFile = ontoFile;
		this.importedOntology = ontology;
		
		
		
		try {
			URI physicalURI = ontoFile.toURI();
			manager = OWLManager.createOWLOntologyManager();

			// Now load the ontology.
			onto = manager.loadOntologyFromPhysicalURI(physicalURI);

			// Report information about the ontology
			System.out.println("Ontology Loaded...");

			// Initialize the reasoner
			reasoner = new ToldClassHierarchyReasoner(manager);
			reasoner.loadOntologies(Collections.singleton(this.onto));

			// Set the ontology namespace
			ontology.setNameSpace(onto.getURI().toString());

			class_owl_dafoe.clear();
			class_dafoe_owl.clear();
			oProp_owl_dafoe.clear();
			oProp_dafoe_owl.clear();
			dProp_owl_dafoe.clear();
			dProp_dafoe_owl.clear();

			int total = this.onto.getReferencedClasses().size();
			total += this.onto.getReferencedObjectProperties().size();
			total += this.onto.getReferencedDataProperties().size();

			// VT:
			totalTime = total;

		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		} catch (OWLReasonerException e) {
			e.printStackTrace();
		}

	}

	public int getTotalTime() {
		return totalTime;
	}

	public IOntology getImportedOntology() {
		return importedOntology;
	}

	public void run() {
		System.out.println("--------- Reading " + ontoFile.getAbsolutePath() + " ---------");
		URI physicalURI = ontoFile.toURI();

		// Initialization
		try {
			// Build an ontology manager
			manager = OWLManager.createOWLOntologyManager();

			// Now load the ontology.
			onto = manager.loadOntologyFromPhysicalURI(physicalURI);

			// Report information about the ontology
			System.out.println("Ontology Loaded...");

			// Initialize the reasoner
			reasoner = new ToldClassHierarchyReasoner(manager);
			reasoner.loadOntologies(Collections.singleton(this.onto));

			// Set the ontology namespace
			importedOntology.setNameSpace(onto.getURI().toString());
			
			// save ontology before adding classes and propperties
			Transaction tx= SessionFactoryImpl.getCurrentDynamicSession().beginTransaction();
			
			SessionFactoryImpl.getCurrentDynamicSession().save(this.importedOntology);

			tx.commit();

			// Get the Thing class
			URI classURI = OWLRDFVocabulary.OWL_THING.getURI();
			OWLClass thingClass = manager.getOWLDataFactory().getOWLClass(
					classURI);

			class_owl_dafoe.clear();
			class_dafoe_owl.clear();
			oProp_owl_dafoe.clear();
			oProp_dafoe_owl.clear();
			dProp_owl_dafoe.clear();
			dProp_dafoe_owl.clear();

			int total = this.onto.getReferencedClasses().size();
			total += this.onto.getReferencedObjectProperties().size();
			total += this.onto.getReferencedDataProperties().size();

			// Annotation type intialization
			annot_owl_dafoe = initAnnotationTypes();

			// Classes importation
			importClass(thingClass, importedOntology);

			buildDefinedClassesLogicalDefinition();

			// ObjectProperty importation

			for (OWLObjectProperty root : getObjectPropertyRoots()) {
				importObjectProperty(root, importedOntology);
			}
			
			// VT: DatatypeProperty importation

			for (OWLDataProperty root : getDatatypePropertyRoots()) {
				importDataTypeProperty(root, importedOntology);
			}
			//

			reasoner.clearOntologies();

		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		} catch (OWLReasonerException e) {
			e.printStackTrace();
		}
	}

	private IClass importClass(OWLClass clazz, IOntology ontology) {
		String label = null;
		IClass pere = null;
		try {
			final URI uri = clazz.getURI();

			if (reasoner.isSatisfiable(clazz)) {
				label = uri.getFragment();
			}

			String classNameSpace = uri.getScheme() + "://" + uri.getHost()
					+ uri.getPath();
			System.out.println("class label = " + label);

			pere = new ClassImpl();
			pere.setLabel(label);
			pere.setState(ONTO_OBJECT_STATE.VALIDATED);

			if (!classNameSpace.equals(ontology.getNameSpace())) {
				pere.setNameSpace(classNameSpace);
			}

			if (clazz.isDefined(this.onto)) {
				definedClasses.add(clazz);
				String logicalDefinition = "";
				Set<OWLDescription> descs = clazz
						.getEquivalentClasses(this.onto);

				for (OWLDescription desc : descs) {
					logicalDefinition = logicalDefinition.concat(desc
							.toString()
							+ ";");
				}
				pere.setLogicalDefinition(logicalDefinition);
			}

			link(clazz, pere);
			ontology.addOntoObject(pere);
			buildClassAnnotations(clazz, pere);
			Services.saveClass(ontology, pere, null);

			Set<Set<OWLClass>> children = reasoner.getSubClasses(clazz);

			for (Set<OWLClass> setOfClasses : children) {
				System.out.println("#Subclasses = " + children.size());
				for (OWLClass child : setOfClasses) {
					IClass enfant = null;

					if (!child.equals(clazz)) {

						if (class_owl_dafoe.containsKey(child)) {
							enfant = class_owl_dafoe.get(child);
						} else {
							enfant = importClass(child, ontology);
						}

						if (pere != null) {
							//Services.saveClass(ontology, pere, null);
							pere.addChild(enfant);
						}
					}
				}
			}

			if (pere != null) {
				Services.saveClass(ontology, pere, null);
			}

			return pere;

		} catch (OWLReasonerException e) {
			e.printStackTrace();
			return null;
		}

	}

	private void buildDefinedClassesLogicalDefinition() {
		for (OWLClass definedClass : definedClasses) {
			buildDefinedClassLogicalDefinition(definedClass);
		}
	}

	private void buildDefinedClassLogicalDefinition(OWLClass clazz) {
		IClass dafoeClass = class_owl_dafoe.get(clazz);
		Set<OWLDescription> descs = clazz.getEquivalentClasses(this.onto);
		String logicalDefinition = "";

		for (OWLDescription desc : descs) {

			ClassExpressionVisitor vis = new ClassExpressionVisitor(
					class_owl_dafoe);
			desc.accept(vis);
			logicalDefinition = vis.getResult();

			System.out.println(logicalDefinition);

			// we deal with only one description
			break;
		}

		dafoeClass.setLogicalDefinition(logicalDefinition);

		Services.saveClass(importedOntology, dafoeClass, null);
	}

	private IProperty importObjectProperty(OWLObjectProperty p, IOntology ontology) {
		String label;

		label = p.getURI().getFragment();

		System.out.println("Import Property " + label);

		IObjectProperty dafoeProperty = null;

		if (oProp_owl_dafoe.containsKey(p)) {
			return oProp_owl_dafoe.get(p);

		}

		dafoeProperty = new ObjectPropertyImpl();
		dafoeProperty.setLabel(label);
		dafoeProperty.setState(ONTO_OBJECT_STATE.VALIDATED);
		dafoeProperty.setRange(null);
		ontology.addOntoObject(dafoeProperty);
		buildObjectPropertyAnnotations(p, dafoeProperty);
		buildObjectPropertyRange(p, dafoeProperty);
		buildObjectPropertyDomain(p, dafoeProperty);
		link(p, dafoeProperty);
		Services.saveProperty(ontology, dafoeProperty, null);

		Set<OWLObjectPropertyExpression> subProps = p
				.getSubProperties(this.onto);

		for (OWLObjectPropertyExpression subProp : subProps) {
			OWLObjectProperty subOWLProp = subProp.asOWLObjectProperty();
			IObjectProperty child = null;

			if (oProp_owl_dafoe.containsKey(subOWLProp)) {
				child = (IObjectProperty) oProp_owl_dafoe.get(subOWLProp);
			} else {
				child = (IObjectProperty) importObjectProperty(subOWLProp,
						ontology);
			}

			dafoeProperty.addChild(child);
		}

		// Property characteristics

		Services.saveProperty(ontology, dafoeProperty, null);
		return dafoeProperty;
	}

	// VT:
	
	private IProperty importDataTypeProperty(OWLDataProperty p, IOntology ontology) {
		String label;

		label = p.getURI().getFragment();

		System.out.println("Import Property " + label);

		DatatypePropertyImpl dafoeProperty = null;

		if (dProp_owl_dafoe.containsKey(p)) {
			return dProp_owl_dafoe.get(p);
			
		}

		dafoeProperty = new DatatypePropertyImpl();
		dafoeProperty.setLabel(label);
		dafoeProperty.setState(ONTO_OBJECT_STATE.VALIDATED);
		dafoeProperty.setRange(null);
		ontology.addOntoObject(dafoeProperty);
		//buildObjectPropertyAnnotations(p, dafoeProperty);
		//buildObjectPropertyRange(p, dafoeProperty);
		//buildObjectPropertyDomain(p, dafoeProperty);
		link(p, dafoeProperty);
		Services.saveProperty(ontology, dafoeProperty, null);

		Set<OWLDataPropertyExpression> subProps = p
				.getSubProperties(this.onto);

		for (OWLDataPropertyExpression subProp : subProps) {
			OWLDataProperty subOWLProp = subProp.asOWLDataProperty();
			DatatypePropertyImpl child = null;

			if (dProp_owl_dafoe.containsKey(subOWLProp)) {
				child = (DatatypePropertyImpl) dProp_owl_dafoe.get(subOWLProp);
			} else {
				child = (DatatypePropertyImpl) importDataTypeProperty(subOWLProp,
						ontology);
			}

			dafoeProperty.addChild(child);
		}

		// Property characteristics

		Services.saveProperty(ontology, dafoeProperty, null);
		return dafoeProperty;
	}
	
	//
	private void link(OWLClass c, IClass pere) {
		if (!class_owl_dafoe.containsKey(c)) {
			class_owl_dafoe.put(c, pere);
		}
		if (!class_dafoe_owl.containsKey(pere)) {
			class_dafoe_owl.put(pere, c);
		}
	}

	private void link(OWLObjectProperty p, IProperty pere) {
		if (!oProp_owl_dafoe.containsKey(p)) {
			oProp_owl_dafoe.put(p, pere);
		}
		if (!oProp_dafoe_owl.containsKey(pere)) {
			oProp_dafoe_owl.put(pere, p);
		}
	}
	
	private void link(OWLDataProperty p, IProperty pere) {
		if (!dProp_owl_dafoe.containsKey(p)) {
			dProp_owl_dafoe.put(p, pere);
		}
		if (!dProp_dafoe_owl.containsKey(pere)) {
			dProp_dafoe_owl.put(pere, p);
		}
	}

	private Set<OWLObjectProperty> getObjectPropertyRoots() {
		Set<OWLObjectProperty> subPropertiesOfRoot = new HashSet<OWLObjectProperty>();

		for (OWLObjectProperty prop : this.onto.getReferencedObjectProperties()) {

			if (prop.getSuperProperties(this.onto).size() == 0) {
				subPropertiesOfRoot.add(prop);
			}
		}

		return subPropertiesOfRoot;
	}

	//VT:
	private Set<OWLDataProperty> getDatatypePropertyRoots() {
		Set<OWLDataProperty> subPropertiesOfRoot = new HashSet<OWLDataProperty>();

		for (OWLDataProperty prop : this.onto.getReferencedDataProperties()) {

			if (prop.getSuperProperties(this.onto).size() == 0) {
				subPropertiesOfRoot.add(prop);
			}
		}

		return subPropertiesOfRoot;
	}
	@SuppressWarnings("unchecked")
	private void buildClassAnnotations(OWLClass clazz, IClass dafoeClass) {
		AnnotationExtractor ae = new AnnotationExtractor();
		Set<OWLAnnotation> annotations = clazz.getAnnotations(this.onto);
		String annotationType;
		String annotationValue;

		IOntoAnnotationType dafoeAnnotationType;
		IOntoAnnotation dafoeAnnotation;

		for (OWLAnnotation anno : annotations) {
			anno.accept(ae);

			annotationType = ae.getAnnotationType().toString();
			annotationValue = ae.getAnnotationValue();

			if (annot_owl_dafoe.containsKey(annotationType)) {

				dafoeAnnotationType = annot_owl_dafoe.get(annotationType);

			} else {

				dafoeAnnotationType = new OntoAnnotationTypeImpl();

				dafoeAnnotationType.setLabel(annotationType.toString());

				dafoeAnnotationType.setDataType("string");

				Services.saveAnnotationType(dafoeAnnotationType);

				annot_owl_dafoe.put(annotationType, dafoeAnnotationType);

			}

			dafoeAnnotation = new ClassAnnotationImpl();
			dafoeAnnotation.setOntoAnnotationType(dafoeAnnotationType);
			dafoeAnnotation.setValue(annotationValue);
			dafoeClass.addAnnotation(dafoeAnnotation);

		}

	}

	@SuppressWarnings("unchecked")
	private void buildObjectPropertyAnnotations(OWLObjectProperty prop,
			IObjectProperty dafoeProperty) {
		AnnotationExtractor ae = new AnnotationExtractor();
		Set<OWLAnnotation> annotations = prop.getAnnotations(this.onto);
		String annotationType;
		String annotationValue;

		IOntoAnnotationType dafoeAnnotationType;
		IOntoAnnotation dafoeAnnotation;

		for (OWLAnnotation anno : annotations) {
			anno.accept(ae);

			annotationType = ae.getAnnotationType().toString();
			annotationValue = ae.getAnnotationValue();

			if (annot_owl_dafoe.containsKey(annotationType)) {

				dafoeAnnotationType = annot_owl_dafoe.get(annotationType);

			} else {

				dafoeAnnotationType = new OntoAnnotationTypeImpl();
				dafoeAnnotationType.setLabel(annotationType.toString());
				dafoeAnnotationType.setDataType("string");
				Services.saveAnnotationType(dafoeAnnotationType);
				annot_owl_dafoe.put(annotationType, dafoeAnnotationType);
			}

			dafoeAnnotation = new ObjectPropertyAnnotationImpl();
			dafoeAnnotation.setOntoAnnotationType(dafoeAnnotationType);
			dafoeAnnotation.setValue(annotationValue);
			dafoeProperty.addAnnotation(dafoeAnnotation);
		}
	}

	private void buildObjectPropertyRange(OWLObjectProperty prop,
			IObjectProperty dafoeProperty) {
		Set<OWLDescription> ranges = prop.getRanges(this.onto);

		// we deal with a unique class range

		for (OWLDescription range : ranges) {
			Set<OWLClass> classes = range.getClassesInSignature();
			for (OWLClass clazz : classes) {
				if (class_owl_dafoe.containsKey(clazz)) {
					dafoeProperty.setRange(class_owl_dafoe.get(clazz));
				}
				break;
			}
			break;
		}
	}

	private void buildObjectPropertyDomain(OWLObjectProperty prop,
			IObjectProperty dafoeProperty) {
		Set<OWLDescription> domains = prop.getDomains(this.onto);

		// we deal with a unique class range

		for (OWLDescription domain : domains) {
			Set<OWLClass> classes = domain.getClassesInSignature();
			for (OWLClass clazz : classes) {
				if (class_owl_dafoe.containsKey(clazz)) {
					dafoeProperty.addDomain(class_owl_dafoe.get(clazz));
				}
			}
		}
	}

	private Hashtable<String, IOntoAnnotationType> initAnnotationTypes() {
		Hashtable<String, IOntoAnnotationType> annot_owl_dafoe = new Hashtable<String, IOntoAnnotationType>();

		List<IOntoAnnotationType> annotationTypes = Services
				.getAnnotationsTypes();

		for (IOntoAnnotationType annotationType : annotationTypes) {
			String label = annotationType.getLabel();
			annot_owl_dafoe.put(label, annotationType);
		}

		return annot_owl_dafoe;
	}
	
	
	// VT: resolve datatype property 
	private void buildDatatypePropertyAnnotations(OWLDataProperty prop,
			IDatatypeProperty dafoeProperty) {
		AnnotationExtractor ae = new AnnotationExtractor();
		Set<OWLAnnotation> annotations = prop.getAnnotations(this.onto);
		String annotationType;
		String annotationValue;

		IOntoAnnotationType dafoeAnnotationType;
		IOntoAnnotation dafoeAnnotation;

		for (OWLAnnotation anno : annotations) {
			anno.accept(ae);

			annotationType = ae.getAnnotationType().toString();
			annotationValue = ae.getAnnotationValue();

			if (annot_owl_dafoe.containsKey(annotationType)) {

				dafoeAnnotationType = annot_owl_dafoe.get(annotationType);

			} else {

				dafoeAnnotationType = new OntoAnnotationTypeImpl();
				dafoeAnnotationType.setLabel(annotationType.toString());
				dafoeAnnotationType.setDataType("string");
				Services.saveAnnotationType(dafoeAnnotationType);
				annot_owl_dafoe.put(annotationType, dafoeAnnotationType);
			}

			dafoeAnnotation = new DataTypePropertyAnnotationImpl();
			dafoeAnnotation.setOntoAnnotationType(dafoeAnnotationType);
			dafoeAnnotation.setValue(annotationValue);
			dafoeProperty.addAnnotation(dafoeAnnotation);
		}
	}

	private void buildDatatypePropertyRange(OWLDataProperty prop,
			IDatatypeProperty dafoeProperty) {
		Set<OWLDataRange> ranges = prop.getRanges(this.onto);
        
		OWLDataType range = prop.asOWLDataType();

		IBasicDatatype basic= Util.getBasicDatatypeFromOWLRangeInDatabase(range);
		
		dafoeProperty.setRange(basic);
	
	}

    
    	
	private void buildDatatypePropertyDomain(OWLDataProperty prop,
			IDatatypeProperty dafoeProperty) {
		Set<OWLDescription> domains = prop.getDomains(this.onto);

		// we deal with a unique class range

		for (OWLDescription domain : domains) {
			Set<OWLClass> classes = domain.getClassesInSignature();
			for (OWLClass clazz : classes) {
				if (class_owl_dafoe.containsKey(clazz)) {
					dafoeProperty.addDomain(class_owl_dafoe.get(clazz));
				}
			}
		}
	}
}