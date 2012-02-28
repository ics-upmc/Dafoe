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

package org.dafoe.plugin.imp.skos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dafoe.api.skos.vocabulary.LocalizedText;
import org.dafoe.api.skos.vocabulary.SKOSCollection;
import org.dafoe.api.skos.vocabulary.SKOSConcept;
import org.dafoe.api.skos.vocabulary.SKOSConceptScheme;
import org.dafoe.api.skos.vocabulary.SKOSModel;
import org.dafoe.api.skos.vocabulary.SKOSOrderedCollection;
import org.dafoe.framework.builder.model.SerializableObjectFactory;
import org.dafoe.framework.core.mock.MockBinaryTCRelation;
import org.dafoe.framework.core.terminoontological.model.ITcList;
import org.dafoe.framework.core.terminoontological.model.ITcSet;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotationType;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_ANNOTATION_TYPE_ENUM;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TcArrayListImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TcHashSetImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptAnnotationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl;
import org.dafoe.plugin.imp.skos.adaptater.Services;
import org.dafoe.plugin.imp.skos.util.SKOSAnnotationTypeENUM;
import org.dafoe.plugin.imp.skos.util.Util;
import org.hibernate.Transaction;

public class ImportSKOS {

	private SKOSModel skosModel = null;

	private SKOSConceptScheme currentConceptScheme;

	private ITerminoOntology currentTerminoOntology;

	private Map<SKOSConcept, ITerminoConcept> mappedSkosConcept = null;

	private List<SKOSConcept> topSkosConcept = null;

	// private List<MockBinaryTCRelation> binaryTCRs= null;

	private List<SKOSConcept> nextSubTreeSkosConcepts = null;

	// private Map<ITerminoOntoAnnotationType, String> mappedAnnotationType=
	// null;

	public ImportSKOS() {

	}

	public ImportSKOS(SKOSConceptScheme cScheme, String terminontologyName){
		this.currentConceptScheme = cScheme;
		skosModel = SKOSModel.newInstance();
		currentTerminoOntology = createTerminoOntology(cScheme,terminontologyName);

		//topSkosConcept = Util.toList(cScheme.getTopConcepts());
		topSkosConcept = Util.toList(Services.getTopConcepts(cScheme));
		
		// System.out.println("top tc size="+ topTCs.size());

		// binaryTCRs= terminoOnto.getBinaryTerminoConceptRelations();
		// System.out.println("btcr size="+ binaryTCRs.size());

		mappedSkosConcept = new HashMap<SKOSConcept, ITerminoConcept>();
		nextSubTreeSkosConcepts = buildDirectSubTree(topSkosConcept);

		// initMappedAnnotationType();
	}
	

	public SKOSConceptScheme getCurrentConceptScheme() {
		return currentConceptScheme;
	}

	public ITerminoOntology getTerminoOntology() {
		return currentTerminoOntology;
	}

	public void setCurrentConceptScheme(SKOSConceptScheme cScheme) {

		this.currentConceptScheme = cScheme;
		skosModel = SKOSModel.newInstance();
		currentTerminoOntology = createTerminoOntology(cScheme);

		//topSkosConcept = Util.toList(cScheme.getTopConcepts());
		topSkosConcept = Util.toList(Services.getTopConcepts(cScheme));
		// System.out.println("top tc size="+ topTCs.size());

		// binaryTCRs= terminoOnto.getBinaryTerminoConceptRelations();
		// System.out.println("btcr size="+ binaryTCRs.size());

		mappedSkosConcept = new HashMap<SKOSConcept, ITerminoConcept>();
		nextSubTreeSkosConcepts = buildDirectSubTree(topSkosConcept);
		// initMappedAnnotationType();
	}

	public void initMappedAnnotationType() {

		// ITerminoOntoAnnotationType
	}

	

	public void run() {

		importTopSkosConcept(); //OK

		importSubSkosConcepts(nextSubTreeSkosConcepts); //OK

		// importSkosAssociation;

		 importSkosCollection(); //OK

		 importSkosOrderedCollection();//OK

	}

	public void importTopSkosConcept() { // ok

		for (SKOSConcept c : topSkosConcept) {
			ITerminoConcept topTC = createTerminoConcept(c);

			currentTerminoOntology.addTerminoOntoObject(topTC);
			// topSkosConcept= createSkosConcept(topTC, true);

			mappedSkosConcept.put(c, topTC);

			currentTerminoOntology.addTerminoOntoObject(topTC);
			
			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(currentTerminoOntology);
			
			tx.commit();
		}

	}

	public void importSubSkosConcepts(List<SKOSConcept> cs) { // ok

		// resolve all tc of the current subTreeTCs
		for (SKOSConcept c : cs) {
			resolveNode(c);
		}

		// build a new subTreeTCs from the list of current TCs
		nextSubTreeSkosConcepts = buildDirectSubTree(cs);

		if (nextSubTreeSkosConcepts.size() > 0) { // if there exists a
													// subTreeTCs, we export it
			// System.out.println("size= "+nextSubTreeTCs.size());
			importSubSkosConcepts(nextSubTreeSkosConcepts);
		}
	}

	/*
	 * public void importTCRelation(List<ITerminoConceptRelation> tcrs){ //
	 * for(ITerminoConceptRelation tcr: tcrs){ MockBinaryTCRelation bTCR=
	 * createBinaryTCRelation(tcr); createSkosAssociation(bTCR); } }
	 * 
	 * public void importBinaryTCRelation(List<MockBinaryTCRelation> bTCRs){
	 * //ok for(MockBinaryTCRelation bTCR: bTCRs){
	 * 
	 * createSkosAssociation(bTCR); } }
	 * 
	 * public void importBinaryTCRelation(){//ok for(MockBinaryTCRelation bTCR:
	 * binaryTCRs){
	 * 
	 * createSkosAssociation(bTCR); } }
	 */

	public void importSkosAssociation() {

	}

	public void importSkosCollection() {
		for (SKOSCollection col : currentConceptScheme.getCollections()) {

			ITcSet tcSet = createTcSet(col);

			for (SKOSConcept c : col.getMembers()) {
				tcSet.add(mappedSkosConcept.get(c));
			}
			
			currentTerminoOntology.addTerminoOntoObject(tcSet);
			
			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(currentTerminoOntology);
			
			tx.commit();

		}
	}

	public void importSkosOrderedCollection() {
		for (SKOSOrderedCollection col : currentConceptScheme
				.getOrderedCollections()) {

			ITcList tcList = createTcList(col);

			for (SKOSConcept c : col.getMembers()) {
				tcList.add(mappedSkosConcept.get(c));
			}

			currentTerminoOntology.addTerminoOntoObject(tcList);
			
			
			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(currentTerminoOntology);
			
			tx.commit();
			
		}
	}

	// map a terminoOntology into a skosConceptScheme
	private ITerminoOntology createTerminoOntology(SKOSConceptScheme cScheme) {// ok
		String uri = cScheme.getUri();
		ITerminoOntology to = new TerminoOntologyImpl();
		to.setNameSpace(uri);
		to.setName(uri);
		to.setLanguage("");

		return to;
	}
	
	// map a terminoOntology into a skosConceptScheme
	private ITerminoOntology createTerminoOntology(SKOSConceptScheme cScheme, String terminoontologyName) {// ok
		String uri = cScheme.getUri();
		ITerminoOntology to = new TerminoOntologyImpl();
		to.setNameSpace(uri);
		to.setName(terminoontologyName);
		to.setLanguage("");

		return to;
	}

	// map a tc into a skosConcept
	private ITerminoConcept createTerminoConcept(SKOSConcept c) {// ok
		String label= "";
		
		LocalizedText[] l = c.getPrefLabels();
		if(l!= null && l.length>0){
			
			LocalizedText defaultPrefLabel = l[0];
			
			label= defaultPrefLabel.getText();
		}else{
			label= c.getUri();
		}
		
		ITerminoConcept tc = new TerminoConceptImpl();
	
		tc.setLabel(label);
		// tc.setDefinition(c.getDefinition(language))


		// use a dafoe session to save now
		Transaction tx= Services.getDafoeSession().beginTransaction();
		
		Services.getDafoeSession().saveOrUpdate(tc);
		
		tx.commit();
		
		 addTerminoOntoAnnotation(c,tc);

		return tc;
	}



	// map a TCHashSet into a skosCollection
	// must be run after all tc have been mapped
	private ITcSet createTcSet(SKOSCollection c) {
		ITcSet set = new TcHashSetImpl();
		set.setLabel(c.getUri());

		return set;

	}

	// map a TCArrayList into a skosOrderedCollection
	// must be run after all tc have been mapped
	private ITcList createTcList(SKOSOrderedCollection oC) {

		ITcList list = new TcArrayListImpl();
		list.setLabel(oC.getUri());

		return list;
	}

	// map terminoOntoAnnotation into skosAnnotation
	// execute after the tc has been mapped
	private void addTerminoOntoAnnotation(SKOSConcept skosConcept,
			ITerminoConcept tc) {
		addPrefLabelAnnotation(skosConcept, tc);
		addAltLabelAnnotation(skosConcept, tc);
		addHiddenLabelAnnotation(skosConcept, tc);
		addNoteAnnotation(skosConcept, tc);
		addDefinitionAnnotation(skosConcept, tc);
		addExampleAnnotation(skosConcept, tc);
		addHistoryNoteAnnotation(skosConcept, tc);
		addEditorialNoteAnnotation(skosConcept, tc);
		addChangeNoteAnnotation(skosConcept, tc);

	}

	//

	private void addPrefLabelAnnotation(SKOSConcept skosConcept,ITerminoConcept tc) {
		LocalizedText[] prefs = skosConcept.getPrefLabels();

		for (LocalizedText p : prefs) {
			ITerminoOntoAnnotation a = createPrefLabelAnnotation(p);
			
			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(a);
			
			tx.commit();
			
			tc.addAnnotation(a);
		}

	}

	private void addAltLabelAnnotation(SKOSConcept skosConcept,
			ITerminoConcept tc) {
		LocalizedText[] prefs = skosConcept.getAltLabels();

		for (LocalizedText p : prefs) {
		ITerminoOntoAnnotation a= createAltLabelAnnotation(p);
		
		// use a dafoe session to save now
		Transaction tx= Services.getDafoeSession().beginTransaction();
		
		Services.getDafoeSession().saveOrUpdate(a);
		
		tx.commit();
		
		tc.addAnnotation(a);
		}
	}

	private void addHiddenLabelAnnotation(SKOSConcept skosConcept,
			ITerminoConcept tc) {

		LocalizedText[] prefs = skosConcept.getHiddenLabels();

		for (LocalizedText p : prefs) {
			ITerminoOntoAnnotation a= createHiddenLabelAnnotation(p);

			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(a);
			
			tx.commit();
			
			tc.addAnnotation(a);
		}
	}

	private void addNoteAnnotation(SKOSConcept skosConcept, ITerminoConcept tc) {
		LocalizedText[] prefs = skosConcept.getNote();

		for (LocalizedText p : prefs) {
			ITerminoOntoAnnotation a=  createNoteAnnotation(p);

			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(a);
			
			tx.commit();
			
			tc.addAnnotation(a);
		}
	}

	private void addDefinitionAnnotation(SKOSConcept skosConcept,
			ITerminoConcept tc) {
		LocalizedText[] prefs = skosConcept.getDefinition();

		for (LocalizedText p : prefs) {
			ITerminoOntoAnnotation a= createDefinitionAnnotation(p);

			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(a);
			
			tx.commit();
			
			tc.addAnnotation(a);
		}
	}

	private void addExampleAnnotation(SKOSConcept skosConcept,
			ITerminoConcept tc) {
		LocalizedText[] prefs = skosConcept.getExample();

		for (LocalizedText p : prefs) {
			ITerminoOntoAnnotation a= createExampleAnnotation(p);

			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(a);
			
			tx.commit();
			
			tc.addAnnotation(a);
		}
	}

	private void addHistoryNoteAnnotation(SKOSConcept skosConcept,
			ITerminoConcept tc) {
		LocalizedText[] prefs = skosConcept.getHistoryNote();

		for (LocalizedText p : prefs) {
			ITerminoOntoAnnotation a= createHistoryNoteAnnotation(p);

			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(a);
			
			tx.commit();
			
			tc.addAnnotation(a);
		}
	}

	private void addEditorialNoteAnnotation(SKOSConcept skosConcept,
			ITerminoConcept tc) {
		LocalizedText[] prefs = skosConcept.getEditorialNote();

		for (LocalizedText p : prefs) {
			ITerminoOntoAnnotation a= createEditorialNoteAnnotation(p);

			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(a);
			
			tx.commit();
			
			tc.addAnnotation(a);
		}
	}

	private void addChangeNoteAnnotation(SKOSConcept skosConcept,
			ITerminoConcept tc) {
		LocalizedText[] prefs = skosConcept.getChangeNote();

		for (LocalizedText p : prefs) {
			ITerminoOntoAnnotation a= createChangeNoteAnnotation(p);

			// use a dafoe session to save now
			Transaction tx= Services.getDafoeSession().beginTransaction();
			
			Services.getDafoeSession().saveOrUpdate(a);
			
			tx.commit();
			
			tc.addAnnotation(a);
		}
	}

	//
	private ITerminoOntoAnnotation createPrefLabelAnnotation(LocalizedText text) {
		TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTenum = getMatchedTerminoOntoAnnotationType(SKOSAnnotationTypeENUM.SKOS_PREF_LABEL);
		ITerminoOntoAnnotationType annotType=  Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotTenum);
		
		ITerminoOntoAnnotation toAnnot = new TerminoConceptAnnotationImpl();
		String value = text.getText() + " @" + text.getLanguage();
		toAnnot.setValue(value);
		toAnnot.setTerminoOntoAnnotationType(annotType);

		//TO COMPLETE save to before?
		return toAnnot;
	}

	private ITerminoOntoAnnotation createAltLabelAnnotation(LocalizedText text) {
		
		TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTenum = getMatchedTerminoOntoAnnotationType(SKOSAnnotationTypeENUM.SKOS_ALT_LABEL);
		ITerminoOntoAnnotationType annotType=  Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotTenum);
		ITerminoOntoAnnotation toAnnot = new TerminoConceptAnnotationImpl();
		String value = text.getText() + " @" + text.getLanguage();
		toAnnot.setValue(value);
		toAnnot.setTerminoOntoAnnotationType(annotType);

		//TO COMPLETE  save to before?
		return toAnnot;
	}

	private ITerminoOntoAnnotation createHiddenLabelAnnotation(LocalizedText text) {
		
		TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTenum = getMatchedTerminoOntoAnnotationType(SKOSAnnotationTypeENUM.SKOS_HIDDEN_LABEL);
		ITerminoOntoAnnotationType annotType=  Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotTenum);
		
		ITerminoOntoAnnotation toAnnot = new TerminoConceptAnnotationImpl();
		String value = text.getText() + " @" + text.getLanguage();
		toAnnot.setValue(value);
		toAnnot.setTerminoOntoAnnotationType(annotType);

		//TO COMPLETE  save to before?
		return toAnnot;
	}

	private ITerminoOntoAnnotation createNoteAnnotation(LocalizedText text) {

		TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTenum = getMatchedTerminoOntoAnnotationType(SKOSAnnotationTypeENUM.SKOS_NOTE);
		ITerminoOntoAnnotationType annotType=  Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotTenum);
		
		ITerminoOntoAnnotation toAnnot = new TerminoConceptAnnotationImpl();
		String value = text.getText() + " @" + text.getLanguage();
		toAnnot.setValue(value);
		toAnnot.setTerminoOntoAnnotationType(annotType);

		//TO COMPLETE  save to before?
		return toAnnot;
	}

	private ITerminoOntoAnnotation createDefinitionAnnotation(LocalizedText text) {

		TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTenum = getMatchedTerminoOntoAnnotationType(SKOSAnnotationTypeENUM.SKOS_DEFINITION);
		ITerminoOntoAnnotationType annotType=  Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotTenum);

		ITerminoOntoAnnotation toAnnot = new TerminoConceptAnnotationImpl();
		String value = text.getText() + " @" + text.getLanguage();
		toAnnot.setValue(value);
		toAnnot.setTerminoOntoAnnotationType(annotType);

		//TO COMPLETE  save to before?
		return toAnnot;
	}

	private ITerminoOntoAnnotation createExampleAnnotation(LocalizedText text) {

		TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTenum = getMatchedTerminoOntoAnnotationType(SKOSAnnotationTypeENUM.SKOS_EXAMPLE);
		ITerminoOntoAnnotationType annotType=  Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotTenum);
		
		ITerminoOntoAnnotation toAnnot = new TerminoConceptAnnotationImpl();
		String value = text.getText() + " @" + text.getLanguage();
		toAnnot.setValue(value);
		toAnnot.setTerminoOntoAnnotationType(annotType);

		//TO COMPLETE  save to before?
		return toAnnot;
	}

	private ITerminoOntoAnnotation createHistoryNoteAnnotation(LocalizedText text) {

		TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTenum = getMatchedTerminoOntoAnnotationType(SKOSAnnotationTypeENUM.SKOS_HISTORY_NOTE);
		ITerminoOntoAnnotationType annotType=  Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotTenum);

		ITerminoOntoAnnotation toAnnot = new TerminoConceptAnnotationImpl();
		String value = text.getText() + " @" + text.getLanguage();
		toAnnot.setValue(value);
		toAnnot.setTerminoOntoAnnotationType(annotType);

		//TO COMPLETE  save to before?
		return toAnnot;
	}

	private ITerminoOntoAnnotation createEditorialNoteAnnotation(LocalizedText text) {

		TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTenum = getMatchedTerminoOntoAnnotationType(SKOSAnnotationTypeENUM.SKOS_EDITORIAL_NOTE);
		ITerminoOntoAnnotationType annotType=  Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotTenum);
		
		ITerminoOntoAnnotation toAnnot = new TerminoConceptAnnotationImpl();
		String value = text.getText() + " @" + text.getLanguage();
		toAnnot.setValue(value);
		toAnnot.setTerminoOntoAnnotationType(annotType);

		//TO COMPLETE  save to before?
		return toAnnot;
	}

	private ITerminoOntoAnnotation createChangeNoteAnnotation(LocalizedText text) {

		TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTenum = getMatchedTerminoOntoAnnotationType(SKOSAnnotationTypeENUM.SKOS_CHANGE_NOTE);
		ITerminoOntoAnnotationType annotType=  Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotTenum);
		
		ITerminoOntoAnnotation toAnnot = new TerminoConceptAnnotationImpl();
		String value = text.getText() + " @" + text.getLanguage();
		toAnnot.setValue(value);
		toAnnot.setTerminoOntoAnnotationType(annotType);

		//TO COMPLETE  save to before?
		return toAnnot;
	}

	private TERMINO_ONTO_ANNOTATION_TYPE_ENUM getMatchedTerminoOntoAnnotationType(
			SKOSAnnotationTypeENUM skosAnnotType) {
		// return mappedAnnotationType.get(toAnnotType);

		return Services.getMappedAnnotationType().get(skosAnnotType);
	}

	// map a terminoConcept Relation into a skos Association
	// execute after all TCs have been mapped
	private void createSkosAssociation(MockBinaryTCRelation bTCR) {// ok

	}

	//
	private MockBinaryTCRelation createBinaryTCRelation(//
			ITerminoConceptRelation tcr) {

		return SerializableObjectFactory.getBinaryTCRelation(tcr);

	}

	// parcours en largeur
	private void resolveNode(SKOSConcept c) {// ok

		ITerminoConcept tc = createTerminoConcept(c);

		currentTerminoOntology.addTerminoOntoObject(tc);
		// insert the created skosConcept in the hierarchic of skosConcepts

		for (SKOSConcept parentC : c.getBroaders()) {

			if (mappedSkosConcept.containsKey(parentC)) {// always true then we
															// resolve only
				System.out.println("ICI");											// child TC, not top
															// TC
				ITerminoConcept parentTC = mappedSkosConcept.get(parentC);
				parentTC.addChild(tc);

			}
		}
		
		// use a dafoe session to save now
		Transaction tx= Services.getDafoeSession().beginTransaction();
		
		Services.getDafoeSession().saveOrUpdate(currentTerminoOntology);
		
		tx.commit();
		
		mappedSkosConcept.put(c, tc);
	}

	private List<SKOSConcept> buildDirectSubTree(List<SKOSConcept> parentTree) {// ok
		List<SKOSConcept> directSubTree = new ArrayList<SKOSConcept>();

		for (SKOSConcept c : parentTree) {
			directSubTree.addAll(Util.toList(c.getNarrowers()));
		}
		return directSubTree;
	}



}
