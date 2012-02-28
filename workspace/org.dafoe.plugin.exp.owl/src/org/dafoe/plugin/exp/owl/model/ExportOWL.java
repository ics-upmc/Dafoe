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

package org.dafoe.plugin.exp.owl.model;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.plugin.exp.owl.util.LocalizedText;
import org.dafoe.plugin.exp.owl.util.Util;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

public class ExportOWL {

	private Map<OWLClass, IClass> classOwlDAFOE = new Hashtable<OWLClass, IClass>();	

	private Map<IClass, OWLClass> classDAFOEOwl = new Hashtable<IClass, OWLClass>();
	
	@SuppressWarnings("unchecked")
	private Map<OWLProperty, IProperty> propertyOWLDafoe = new Hashtable<OWLProperty, IProperty>();
	
	@SuppressWarnings("unchecked")
	private Map<IProperty, OWLProperty> propertyDafoeOWL = new Hashtable<IProperty, OWLProperty>();
	
	private String ontologyLanguage = "FR";

	private String nameSpace = "";
	
	private IRI nameSpaceIRI;
	
	private OWLOntologyManager manager;

	private OWLDataFactory factory;

	public static final String INTERSECTION_OF = "AND";
	
	public static final String UNION_OF = "OR";
	
	public static final String COMPLEMENT_OF = "NOT";
	
	public static final String WHITE_SPACE = " ";
	
	public ExportOWL() {		
	}	
	
	/**
	 * @param currentFile
	 * @param ontology
	 * @throws Exception
	 */
	public void export(File currentFile, IOntology ontology) throws Exception {
		manager = OWLManager.createOWLOntologyManager();	
		factory = manager.getOWLDataFactory();
		
		ontologyLanguage = ontology.getLanguage();
		nameSpace = ontology.getNameSpace();
		if (nameSpace == "") {
			nameSpace = "http://dafoe.org/" + ontology.getId();
		}
		
		nameSpaceIRI = IRI.create(nameSpace);
		final OWLOntology createOntology = manager.createOntology(nameSpaceIRI);
		
		manager.saveOntology(createOntology, IRI.create(currentFile.toURI()));
		
		this.classDAFOEOwl.clear();
		this.classOwlDAFOE.clear();
		
		for (IClass c : org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopClasses(ontology)) {
			this.exportClass(c, createOntology);
		}		
			
		for (IClass c : org.dafoe.ontologiclevel.common.DatabaseAdapter.getClasses(ontology)) {
			if (c.getLogicalDefinition() != null) {
				this.processEquivalentClass(c, createOntology);				
			}
		}
			
		for (IProperty p : org.dafoe.ontologiclevel.common.DatabaseAdapter
				.getTopProperties(ontology)) {
			this.exportProperty(p, createOntology);
		}
		
		manager.saveOntology(createOntology, IRI.create(currentFile.toURI()));
	}
	
	/**
	 * TODO: process a complexe structure (i.e. AND(30, OR(30,50)).
	 * 
	 * @param dafoeClass
	 * @param createOntology
	 */
	private void processEquivalentClass(IClass dafoeClass, OWLOntology createOntology) {
		final String logicalDefinition = dafoeClass.getLogicalDefinition();
		
		String sublogicalDefinition = null;
		if (logicalDefinition.startsWith(ExportOWL.INTERSECTION_OF)) {
			sublogicalDefinition = logicalDefinition.substring(ExportOWL.INTERSECTION_OF.length() + 1, logicalDefinition.length() - 1);
		} else if (logicalDefinition.startsWith(ExportOWL.UNION_OF)) {
			sublogicalDefinition = logicalDefinition.substring(ExportOWL.UNION_OF.length() + 1, logicalDefinition.length() - 1);
		} else if (logicalDefinition.startsWith(ExportOWL.COMPLEMENT_OF)) {
			sublogicalDefinition = logicalDefinition.substring(ExportOWL.COMPLEMENT_OF.length() + 1, logicalDefinition.length() - 1);
		}
		
		final String[] ids = sublogicalDefinition.split(ExportOWL.WHITE_SPACE);
		final OWLClassExpression[] objectIntersectionOfTab = new OWLClassExpression[ids.length];
		for (int i = 0; i < ids.length; i++) {
			objectIntersectionOfTab[i] = classDAFOEOwl.get(org.dafoe.ontologiclevel.common.DatabaseAdapter.getClasse(Integer.valueOf(ids[i])));							
		}
		
		OWLClassExpression classExpression = null;
		if (logicalDefinition.startsWith(ExportOWL.INTERSECTION_OF)) {
			classExpression = factory.getOWLObjectIntersectionOf(objectIntersectionOfTab);
		} else if (logicalDefinition.startsWith(ExportOWL.UNION_OF)) {
			classExpression = factory.getOWLObjectUnionOf(objectIntersectionOfTab);
		} else if (logicalDefinition.startsWith(ExportOWL.COMPLEMENT_OF)) {
			classExpression = factory.getOWLObjectComplementOf(objectIntersectionOfTab[0]);
		}
		
		final OWLClassExpression owlClass = classDAFOEOwl.get(dafoeClass);
		final OWLEquivalentClassesAxiom owlEquivalentClassesAxiom = factory.getOWLEquivalentClassesAxiom(classExpression, owlClass);
		final AddAxiom myClassAddAxiom = new AddAxiom(createOntology, owlEquivalentClassesAxiom);
		manager.applyChange(myClassAddAxiom);
	}
	
	/**
	 * Transform IClass object (DAFOE) to OWLClass object.
	 * 
	 * @param dafoeClass
	 * @param createOntology
	 * @return
	 */
	private OWLClass exportClass(IClass dafoeClass, OWLOntology createOntology) {
		IRI currentNameSpace = null;
		
		if (dafoeClass.getNameSpace() == null || dafoeClass.getNameSpace().isEmpty()) {
			currentNameSpace = this.nameSpaceIRI;
		} else {
			currentNameSpace = IRI.create(dafoeClass.getNameSpace());
		}
		
		System.out.println("Exporting class: "+dafoeClass.getLabel());
		// Creating OWLClass.
		final OWLClass pere = factory.getOWLClass(IRI.create(currentNameSpace + "#" + dafoeClass.getLabel()));
		final OWLDeclarationAxiom owlDeclarationAxiom = factory.getOWLDeclarationAxiom(pere);
		final AddAxiom myClassAddAxiom = new AddAxiom(createOntology, owlDeclarationAxiom);
		manager.applyChange(myClassAddAxiom);
		
		// Creating OWLAnnotation.
		for (IOntoAnnotation anno : dafoeClass.getAnnotations()) {
			System.out.println("\t->Exporting Annotation: "+anno.getOntoAnnotationType().getLabel()+"("+anno.getOntoAnnotationType().getId()+") "+anno.getValue());
			LocalizedText annotValue= new LocalizedText(anno.getValue());
			if (anno.getOntoAnnotationType().getId() == 1) {
								
				addOWLAnnotation(pere, createOntology, OWLRDFVocabulary.RDFS_LABEL.getIRI(), annotValue.getText(), anno.getOntoAnnotationType().getDataType(), annotValue.getLanguage());
			} else if (anno.getOntoAnnotationType().getId() == 2) {
				
				addOWLAnnotation(pere, createOntology, OWLRDFVocabulary.RDFS_COMMENT.getIRI(), annotValue.getText(), anno.getOntoAnnotationType().getDataType(), annotValue.getLanguage());
			} else {
				addOWLAnnotation(pere, createOntology, IRI.create(anno.getOntoAnnotationType().getLabel()), annotValue.getText(), anno.getOntoAnnotationType().getDataType(), annotValue.getLanguage());
			}
		}
		
		// VT: Mappings with TerminoOntoObject are exported as OWLAnnotation.
		
		for(ITerminoOntoObject toObj: dafoeClass.getMappedTerminoOntoObjects()){
			LocalizedText toObjectLabel= new LocalizedText(toObj.getLabel());
			// export tc as label as Label Annotation
			String iriLabel0= Util.normaliseAnnotationFromClass(toObj)+":Label";
			IRI iri0= IRI.create(iriLabel0);
			addOWLAnnotation(pere, createOntology, iri0, toObjectLabel.getText(), "string", toObjectLabel.getLanguage());
			
			// export annotation on tc
			for (ITerminoOntoAnnotation anno : toObj.getAnnotations()) {
					String iriLabel= toObj.getClass().getCanonicalName()+" : "+anno.getTerminoOntoAnnotationType().getLabel();
					IRI iri= IRI.create(iriLabel);
					LocalizedText annotValue= new LocalizedText(anno.getValue());
					addOWLAnnotation(pere, createOntology, iri, annotValue.getText(), anno.getTerminoOntoAnnotationType().getType(), annotValue.getLanguage());
					
				}
		}
		
		

		
		// Creating OWLClass subclasses.
		cacheMapping(pere, dafoeClass);
		for (IClass sc : dafoeClass.getChildren()) {
			OWLClass enfant = null;
			if (this.classDAFOEOwl.containsKey(sc)) {
				enfant = this.classDAFOEOwl.get(sc);
			} else {
				enfant = exportClass(sc, createOntology);
				// ontology.addOntoObject(enfant);
			}
			
			final OWLSubClassOfAxiom owlSubClassOfAxiom = factory.getOWLSubClassOfAxiom(enfant, pere);		
			final AddAxiom addAxiom = new AddAxiom(createOntology, owlSubClassOfAxiom);
			manager.applyChange(addAxiom);
		}

		return pere;
	}
	
	/**
	 * Create OWL Annotation.
	 * 
	 * @param currentEntity
	 * @param createOntology
	 * @param annotation
	 * @param value
	 * @param type
	 * @param language
	 */
	private void addOWLAnnotation(OWLEntity currentEntity, OWLOntology createOntology, IRI annotation, String value, String type, String language) {
		OWLLiteral current = null;
		
		// TODO: use constant instead ...
		if (type.toLowerCase().equals("string")) {
			if (language == null) {
				current = factory.getOWLLiteral(value);				
			} else {
				current = factory.getOWLLiteral(value, language);
			}
		}
		
		final OWLAnnotation owlAnnotation = factory.getOWLAnnotation(factory.getOWLAnnotationProperty(annotation), current);
		OWLAxiom ax = factory.getOWLAnnotationAssertionAxiom(currentEntity.getIRI(), owlAnnotation);
		manager.applyChange(new AddAxiom(createOntology, ax));
	}
	
	/**
	 * @param c
	 * @param pere
	 */
	private void cacheMapping(OWLClass c, IClass pere) {
		if (!this.classOwlDAFOE.containsKey(c)) {
			this.classOwlDAFOE.put(c, pere);
		}
		if (!this.classDAFOEOwl.containsKey(pere)) {
			this.classDAFOEOwl.put(pere, c);
		}
	}
	
	/**
	 * @param c
	 * @param pere
	 */
	@SuppressWarnings("unchecked")
	private void cacheMapping(OWLProperty c, IProperty pere) {
		if (!this.propertyOWLDafoe.containsKey(c)) {
			propertyOWLDafoe.put(c, pere);
		}
		if (!this.propertyDafoeOWL.containsKey(pere)) {
			propertyDafoeOWL.put(pere, c);
		}
	}
	
	@SuppressWarnings("unchecked")
	private OWLProperty exportProperty(IProperty dafoeProperty, OWLOntology createOntology) {
		if (propertyDafoeOWL.containsKey(dafoeProperty)) {
			return propertyDafoeOWL.get(dafoeProperty);
		}
		
		IRI currentNameSpace = null;
		if (dafoeProperty.getNameSpace() == null || dafoeProperty.getNameSpace().isEmpty()) {
			currentNameSpace = this.nameSpaceIRI;
		} else {
			currentNameSpace = IRI.create(dafoeProperty.getNameSpace());
		}
		
		
		OWLProperty pere = null;

		System.out.println(dafoeProperty.getLabel());
		if (dafoeProperty instanceof IDatatypeProperty) {
			pere = factory.getOWLDataProperty(IRI.create(currentNameSpace + "#" + dafoeProperty.getLabel()));
			
			// Retrieve range.
			final IBasicDatatype range = ((IDatatypeProperty)dafoeProperty).getRange();
			if (range != null) {				
				OWLDatatype dataType = factory.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#" + range.getLabel())); 
				final OWLDataPropertyRangeAxiom rangeAxiom = factory.getOWLDataPropertyRangeAxiom((OWLDataProperty)pere, dataType);			
				manager.applyChange(new AddAxiom(createOntology, rangeAxiom));
			}
		} else {
			pere = factory.getOWLObjectProperty(IRI.create(currentNameSpace + "#" + dafoeProperty.getLabel()));
			// Retrieve range.			
			final IClass range = ((IObjectProperty)dafoeProperty).getRange();
			
			if (range != null) {
				if (classDAFOEOwl.containsKey(range)) {
					final OWLObjectPropertyRangeAxiom rangeAxiom = factory.getOWLObjectPropertyRangeAxiom((OWLObjectProperty)pere, classDAFOEOwl.get(range));
					manager.applyChange(new AddAxiom(createOntology, rangeAxiom));
				}
			}
		}
		
		// Creating OWLAnnoation.
		for (IOntoAnnotation anno : dafoeProperty.getAnnotations()) {
			LocalizedText annotValue= new LocalizedText(anno.getValue());
			if (anno.getOntoAnnotationType().getId() == 1) {
				addOWLAnnotation(pere, createOntology, OWLRDFVocabulary.RDFS_LABEL.getIRI(), annotValue.getText(), anno.getOntoAnnotationType().getDataType(), annotValue.getLanguage());
			} else if (anno.getOntoAnnotationType().getId() == 2) {
				addOWLAnnotation(pere, createOntology, OWLRDFVocabulary.RDFS_COMMENT.getIRI(), annotValue.getText(), anno.getOntoAnnotationType().getDataType(), annotValue.getLanguage());
			} else {
				addOWLAnnotation(pere, createOntology, IRI.create(anno.getOntoAnnotationType().getLabel()), annotValue.getText(), anno.getOntoAnnotationType().getDataType(), annotValue.getLanguage());
			}
		}
		
		this.cacheMapping(pere, dafoeProperty);

		// Retrieve domains.
		for (IClass dom : dafoeProperty.getDomains()) {
			System.out.println("Domain: "+dom.getLabel());
			if (this.classDAFOEOwl.containsKey(dom)) {
				if (dafoeProperty instanceof IDatatypeProperty) {
					final OWLDataPropertyDomainAxiom domainAxiom = factory.getOWLDataPropertyDomainAxiom((OWLDataProperty)pere, this.classDAFOEOwl.get(dom));
					manager.applyChange(new AddAxiom(createOntology, domainAxiom));
				} else {
					final OWLObjectPropertyDomainAxiom domainAxiom = factory.getOWLObjectPropertyDomainAxiom((OWLObjectProperty)pere, this.classDAFOEOwl.get(dom));
					manager.applyChange(new AddAxiom(createOntology, domainAxiom));					
				}
			} else {
				System.out.println("Problems ... class not defined.");
			}
		}
		
		if (dafoeProperty instanceof IObjectProperty) {
			OWLProperty enfant = null;
			IObjectProperty oprop = (IObjectProperty) dafoeProperty;

			for (IObjectProperty sc : oprop.getChildren()) {
				if (this.propertyDafoeOWL.containsKey(sc)) {
					enfant = (OWLProperty)propertyDafoeOWL.get(sc);
				} else {
					enfant = (OWLProperty) exportProperty(sc, createOntology);
				}

				final OWLSubObjectPropertyOfAxiom owlSubObjectPropertyOfAxiom = factory.getOWLSubObjectPropertyOfAxiom((OWLObjectPropertyExpression)enfant, (OWLObjectPropertyExpression)pere);
				final AddAxiom addAxiom = new AddAxiom(createOntology, owlSubObjectPropertyOfAxiom);						
				manager.applyChange(addAxiom);
			}
		} else if (dafoeProperty instanceof IDatatypeProperty) {
			OWLProperty enfant = null;
			IDatatypeProperty dprop = (IDatatypeProperty) dafoeProperty;

			for (IDatatypeProperty sc : dprop.getChildren()) {
				if (this.propertyDafoeOWL.containsKey(sc)) {
					enfant = (OWLProperty) propertyDafoeOWL.get(sc);
				} else {
					enfant = (OWLProperty) exportProperty(sc, createOntology);
				}
				final OWLSubDataPropertyOfAxiom owlSubDataPropertyOfAxiom = factory.getOWLSubDataPropertyOfAxiom((OWLDataPropertyExpression)enfant, (OWLDataPropertyExpression)pere);
				final AddAxiom addAxiom = new AddAxiom(createOntology, owlSubDataPropertyOfAxiom);						
				manager.applyChange(addAxiom);
			}
		}
		
		if (dafoeProperty instanceof IObjectProperty) {
			IObjectProperty oprop = (IObjectProperty) dafoeProperty;
			if (oprop.isTransitive()) {
				final OWLTransitiveObjectPropertyAxiom owlTransitiveObjectPropertyAxiom = factory.getOWLTransitiveObjectPropertyAxiom((OWLObjectPropertyExpression)pere);
				final AddAxiom addAxiom = new AddAxiom(createOntology, owlTransitiveObjectPropertyAxiom);
				manager.applyChange(addAxiom);
			}
			if (oprop.isSymetric()) {
				final OWLSymmetricObjectPropertyAxiom owlSymmetricObjectPropertyAxiom = factory.getOWLSymmetricObjectPropertyAxiom((OWLObjectPropertyExpression)pere);
				final AddAxiom addAxiom = new AddAxiom(createOntology, owlSymmetricObjectPropertyAxiom);
				manager.applyChange(addAxiom);
			}
			if (oprop.getInverseOf() != null) {
				if (this.propertyDafoeOWL.containsKey(oprop.getInverseOf())) {
					final OWLObjectPropertyExpression owlProperty = (OWLObjectPropertyExpression)propertyDafoeOWL.get(oprop.getInverseOf());					
					final OWLInverseObjectPropertiesAxiom owlInverseObjectPropertiesAxiom = factory.getOWLInverseObjectPropertiesAxiom(owlProperty, (OWLObjectPropertyExpression)pere);
					final AddAxiom addAxiom = new AddAxiom(createOntology, owlInverseObjectPropertiesAxiom);
					manager.applyChange(addAxiom);
				} else {
					final OWLObjectPropertyExpression owlProperty = (OWLObjectPropertyExpression)this.exportProperty(oprop.getInverseOf(), createOntology);
					final OWLInverseObjectPropertiesAxiom owlInverseObjectPropertiesAxiom = factory.getOWLInverseObjectPropertiesAxiom(owlProperty, (OWLObjectPropertyExpression)pere);
					final AddAxiom addAxiom = new AddAxiom(createOntology, owlInverseObjectPropertiesAxiom);
					manager.applyChange(addAxiom);
				}
			}
		}
		
		return pere;
	}
	

}
