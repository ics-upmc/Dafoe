/*
 * (c) CRITT Informatique - Copyright 2009 
 */

package org.dafoe.api.skos.vocabulary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * The SKOSModel class.
 *  @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class SKOSModel {
	private static Model model = null;
	private static Set<SKOSConcept> concepts = null;
	private static Set<SKOSConceptScheme> conceptSchemes = null;
	private static Set<SKOSCollection> collections = null;
	private static Set<SKOSOrderedCollection> orderedCollections = null;

	private SKOSModel() {
		if (model == null) {
			model = ModelFactory.createDefaultModel();
			model.setNsPrefix("skos", SKOS.NS);
			concepts = new HashSet<SKOSConcept>();
			conceptSchemes = new HashSet<SKOSConceptScheme>();
			collections = new HashSet<SKOSCollection>();
			orderedCollections = new HashSet<SKOSOrderedCollection>();
		}
	}

	public static void createModel() {
		new SKOSModel();
	}

	
	public static SKOSModel newInstance(){
		return new SKOSModel();
	}

	public static Model getModel() {
		return model;
	}

	
	public static void addConcept(SKOSConcept c) {
		concepts.add(c);
	}

	public static void addConceptScheme(SKOSConceptScheme cs) {
		conceptSchemes.add(cs);
	}

	public static void addCollection(SKOSCollection c) {
		collections.add(c);
	}

	public static void addOrderedCollection(SKOSOrderedCollection oc) {
		orderedCollections.add(oc);
	}

	public static SKOSConcept[] getConcepts() {
		return concepts.toArray(new SKOSConcept[0]);
	}

	public static SKOSConceptScheme[] getConceptSchemes() {
		return conceptSchemes.toArray(new SKOSConceptScheme[0]);
	}

	public static SKOSCollection[] getCollections() {
		return collections.toArray(new SKOSCollection[0]);
	}

	public static SKOSOrderedCollection[] getOrderedCollections() {
		return orderedCollections.toArray(new SKOSOrderedCollection[0]);
	}

	public static SKOSConcept getConcept(String uri) {
		SKOSConcept res = null;
		Iterator<SKOSConcept> iter = concepts.iterator();
		while (iter.hasNext()) {
			SKOSConcept c = iter.next();
			if (c.getUri().equals(uri)) {
				res = c;
			}
		}
		return res;
	}

	public static SKOSConceptScheme getConceptScheme(String uri) {
		SKOSConceptScheme res = null;
		Iterator<SKOSConceptScheme> iter = conceptSchemes.iterator();
		while (iter.hasNext()) {
			SKOSConceptScheme c = iter.next();
			if (c.getUri().equals(uri)) {
				res = c;
			}
		}
		return res;
	}

	public static SKOSCollection getCollection(String uri) {
		SKOSCollection res = null;
		Iterator<SKOSCollection> iter = collections.iterator();
		while (iter.hasNext()) {
			SKOSCollection c = iter.next();
			if (c.getUri().equals(uri)) {
				res = c;
			}
		}
		return res;
	}

	public static SKOSOrderedCollection getOrderedCollection(String uri) {
		SKOSOrderedCollection res = null;
		Iterator<SKOSOrderedCollection> iter = orderedCollections.iterator();
		while (iter.hasNext()) {
			SKOSOrderedCollection c = iter.next();
			if (c.getUri().equals(uri)) {
				res = c;
			}
		}
		return res;
	}

	public static void writeTurle(OutputStream o) {
		model.write(o, "TURTLE");
	}

	public static void writeTurle(File f) {
		writeFile(f, "TURTLE");
	}

	public static void writeRdfXml(OutputStream o) {
		model.write(o, "RDF/XML");
	}

	public static void writeRdfXml(File f) {
		writeFile(f, "RDF/XML");
	}

	public static void writeRdfXmlAbbrev(OutputStream o) {
		model.write(o, "RDF/XML-ABBREV");
	}

	public static void writeRdfXmlAbbrev(File f) {
		writeFile(f, "RDF/XML-ABBREV");
	}

	public static void writeNTriple(OutputStream o) {
		model.write(o, "N-TRIPLE");
	}

	public static void writeNTriple(File f) {
		writeFile(f, "N-TRIPLE");
	}

	public static void read(File f) {
		try {
			FileInputStream in = new FileInputStream(f.getPath());

			if (in == null) {
				throw new IllegalArgumentException("File: " + f.getPath()
						+ " not found");
			}
			model.read(in, "");

			buildConcepts();
			buildConceptSchemes();
			buildCollections();
			buildOrderedCollections();
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}

	private static void buildConcepts() {
		Resource conceptClass = SKOSModel.getModel().getResource(SKOS.Concept.getURI());
		ResIterator i = SKOSModel.getModel().listSubjectsWithProperty(RDF.type,	conceptClass);
		while (i.hasNext()) {
			Resource conceptResource = i.nextResource();
			SKOSConcept concept = new SKOSConcept(conceptResource);
			concepts.add(concept);
		}
	}

	private static void buildConceptSchemes() {
		Resource conceptSchemeClass = SKOSModel.getModel().getResource(SKOS.ConceptScheme.getURI());
		ResIterator i = SKOSModel.getModel().listSubjectsWithProperty(RDF.type,	conceptSchemeClass);
		while (i.hasNext()) {
			Resource conceptResource = i.nextResource();
			SKOSConceptScheme conceptScheme = new SKOSConceptScheme(conceptResource);
			conceptSchemes.add(conceptScheme);
		}
	}

	private static void buildCollections() {
		Resource collectionClass = SKOSModel.getModel().getResource(
				SKOS.Collection.getURI());
		ResIterator i = SKOSModel.getModel().listSubjectsWithProperty(RDF.type, collectionClass);
		while (i.hasNext()) {
			Resource collectionResource = i.nextResource();
			SKOSCollection collection = new SKOSCollection(collectionResource);
			collections.add(collection);
		}
	}

	private static void buildOrderedCollections() {
		Resource orderedCollectionClass = SKOSModel.getModel().getResource(
				SKOS.OrderedCollection.getURI());
		ResIterator i = SKOSModel.getModel().listSubjectsWithProperty(RDF.type,	orderedCollectionClass);
		while (i.hasNext()) {
			Resource conceptResource = i.nextResource();
			SKOSOrderedCollection orderedCollection = new SKOSOrderedCollection(conceptResource);
			orderedCollections.add(orderedCollection);
		}
	}

	public static void writeFile(File f, String outputFormat) {
		try {
			FileOutputStream o = new FileOutputStream(f.getAbsolutePath());
			model.write(o, outputFormat);
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}
