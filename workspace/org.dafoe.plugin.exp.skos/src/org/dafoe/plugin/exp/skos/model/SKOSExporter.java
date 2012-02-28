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

package org.dafoe.plugin.exp.skos.model;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.dafoe.api.skos.vocabulary.SKOSCollection;
import org.dafoe.api.skos.vocabulary.SKOSConcept;
import org.dafoe.api.skos.vocabulary.SKOSConceptScheme;
import org.dafoe.api.skos.vocabulary.SKOSModel;
import org.dafoe.api.skos.vocabulary.SKOSOrderedCollection;
import org.dafoe.framework.builder.model.SerializableObjectFactory;
import org.dafoe.framework.core.mock.MockBinaryTCRelation;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminoontological.model.ITcList;
import org.dafoe.framework.core.terminoontological.model.ITcSet;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotationType;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoCollection;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_ANNOTATION_TYPE_ENUM;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TcArrayListImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TcHashSetImpl;
import org.dafoe.framework.tools.StringFormatterTools;
import org.dafoe.plugin.exp.skos.adaptater.Services;
import org.dafoe.plugin.exp.skos.util.LocalizedText;
import org.dafoe.plugin.exp.skos.util.SKOS_ANNOTATION_TYPE_ENUM;



// TODO: Auto-generated Javadoc
/**
 * The Class SKOSExporter.
 */
public class SKOSExporter {
	
	
	/** The skos model. */
	private SKOSModel skosModel= null; 
	
	/** The current concept scheme. */
	private  SKOSConceptScheme currentConceptScheme;
	
	/** The current termino ontology. */
	private ITerminoOntology currentTerminoOntology;
	
	/** The mapped t cs. */
	private Map<ITerminoConcept, SKOSConcept> mappedTCs= null;
	
	/** The top t cs. */
	private List<ITerminoConcept> topTCs= null;
	
	/** The binary tc rs. */
	private List<MockBinaryTCRelation> binaryTCRs= null;
	
	/** The next sub tree t cs. */
	private List<ITerminoConcept> nextSubTreeTCs= null;
	
	//private Map<ITerminoOntoAnnotationType, String> mappedAnnotationType= null;
	
	/**
	 * Instantiates a new sKOS exporter.
	 */
	public SKOSExporter() {
		
	}
	
	/**
	 * Instantiates a new sKOS exporter.
	 *
	 * @param terminoOnto the termino onto
	 */
	public SKOSExporter(ITerminoOntology terminoOnto){
		this.currentTerminoOntology = terminoOnto;
		skosModel= SKOSModel.newInstance();
		currentConceptScheme= createSKOSConceptScheme(terminoOnto);
		
		topTCs= terminoOnto.getTopTerminoConcepts();
		System.out.println("top tc size="+ topTCs.size());
		
		binaryTCRs= terminoOnto.getBinaryTerminoConceptRelations();
		System.out.println("btcr size="+ binaryTCRs.size());
		
		mappedTCs= new HashMap< ITerminoConcept,SKOSConcept>();
		nextSubTreeTCs= buildDirectSubTree(topTCs);
		
		//initMappedAnnotationType();
	}
	
	/**
	 * Gets the current concept scheme.
	 *
	 * @return the current concept scheme
	 */
	public SKOSConceptScheme getCurrentConceptScheme() {
		return currentConceptScheme;
	}
	
	/**
	 * Gets the termino ontology.
	 *
	 * @return the termino ontology
	 */
	public ITerminoOntology getTerminoOntology() {
		return currentTerminoOntology;
	}
	
	/**
	 * Sets the current termino onto.
	 *
	 * @param terminoOnto the new current termino onto
	 */
	public void setCurrentTerminoOnto(ITerminoOntology terminoOnto) {
		this.currentTerminoOntology = terminoOnto;
		skosModel= SKOSModel.newInstance();
		currentConceptScheme= createSKOSConceptScheme(terminoOnto);
		
		topTCs= terminoOnto.getTopTerminoConcepts();
		binaryTCRs= terminoOnto.getBinaryTerminoConceptRelations();
		
		mappedTCs= new HashMap< ITerminoConcept,SKOSConcept>();
		nextSubTreeTCs= buildDirectSubTree(topTCs);
		
		//initMappedAnnotationType();
	}
	
	/**
	 * Inits the mapped annotation type.
	 */
	public void initMappedAnnotationType(){
		
		//ITerminoOntoAnnotationType
	}
	
	/*
	public void setMappedAnnotationType(
			Map<ITerminoOntoAnnotationType, String> mappedAnnotationType) {
		this.mappedAnnotationType = mappedAnnotationType;
	}
	*/
	
	/**
	 * Run.
	 */
	public void run(){
		
		exportTopTC();
		
		exportSubTCs(nextSubTreeTCs);
		
		//exportBinaryTCRelation();
		
		
		exportCollectionTcSet();
		
		//exportCollectionTcArrayList();
		
	}
	
    /**
     * Export top tc.
     */
    public void exportTopTC(){ //ok
		
    	for(ITerminoConcept topTC: topTCs){
			SKOSConcept topSkosConcept= createSkosConcept(topTC);
			
			currentConceptScheme.addTopConcept(topSkosConcept);
			//topSkosConcept= createSkosConcept(topTC, true);
			
			mappedTCs.put(topTC, topSkosConcept);			
		}
		
	}
	
    /**
     * Export sub t cs.
     *
     * @param tcs the tcs
     */
    public void exportSubTCs(List<ITerminoConcept> tcs){ //ok
    	
    	// resolve all tc of the current subTreeTCs
    	for(ITerminoConcept tc: tcs){
    		resolveNode(tc);
    	}
    	
    	// build a new subTreeTCs from the list of current TCs
    	nextSubTreeTCs= buildDirectSubTree(tcs);
    	
    	if(nextSubTreeTCs.size()>0){ // if there exists a subTreeTCs, then export it
    	//System.out.println("size= "+nextSubTreeTCs.size());
    	exportSubTCs(nextSubTreeTCs);
    	}
    }
    
   
    /**
     * Export tc relation.
     *
     * @param tcrs the tcrs
     */
    public void exportTCRelation(List<ITerminoConceptRelation> tcrs){ //
    	for(ITerminoConceptRelation tcr: tcrs){
    		MockBinaryTCRelation bTCR= createBinaryTCRelation(tcr);
    		createSkosAssociation(bTCR);
    	}
    }
  
    /**
     * Export binary tc relation.
     *
     * @param bTCRs the b tc rs
     */
    public void exportBinaryTCRelation(List<MockBinaryTCRelation> bTCRs){ //ok
    	for(MockBinaryTCRelation bTCR: bTCRs){
    		
    		createSkosAssociation(bTCR);
    	}
    }
    
    /**
     * Export binary tc relation.
     */
    public void exportBinaryTCRelation(){//ok
    	for(MockBinaryTCRelation bTCR: binaryTCRs){
    		
    		createSkosAssociation(bTCR);
    	}
    }
    
    /**
     * Export collection tc set.
     */
    public void exportCollectionTcSet(){
    	for(ITcSet tcSet: currentTerminoOntology.getTcSets()){
    		
    		SKOSCollection currentSkosCol= createSkosCollection(tcSet);
    		
    	    Iterator<ITerminoConcept> iter= tcSet.iterator();
    	    
    	    //ITerminoConcept tmpTC= iter.next();
    		while(iter.hasNext()){
    			//if(iter.next() instanceof ITerminoConcept){
    				ITerminoConcept tc= (ITerminoConcept) iter.next();
    				
    				currentSkosCol.addMember(mappedTCs.get(tc));
    			//}
    		}
    		//currentConceptScheme.addCollection(currentSkosCol)
    	}
    }
    
    /**
     * Export collection tc array list.
     */
    public void exportCollectionTcArrayList(){
    	for(ITcList tcList: currentTerminoOntology.getTcLists()){
    		
    		SKOSOrderedCollection currentSkosCol= createSkosOrderedCollection(tcList);
    		
    	    Iterator<ITerminoConcept> iter= tcList.iterator();
    	    
    		while(iter.hasNext()){
    			//if(iter.next() instanceof ITerminoConcept){
    				ITerminoConcept tc= (ITerminoConcept) iter.next();
    				
    				currentSkosCol.addMemberList(mappedTCs.get(tc));
    			//}
    		}
    		//currentConceptScheme.addOrderedCollection(currentSkosCol)
    	}
    }
    
	
	// map a Dafoe terminoOntology into a skosConceptScheme
	/**
	 * Creates the skos concept scheme.
	 *
	 * @param terminoOnto the termino onto
	 * @return the sKOS concept scheme
	 */
	private SKOSConceptScheme createSKOSConceptScheme(ITerminoOntology terminoOnto){//ok
		String uri= terminoOnto.getNameSpace()+"/"+terminoOnto.getName();
		
		// format uri
		String encodedUri= StringFormatterTools.replaceRDFUnAcceptableCharacter(uri);
		//encoding the uri according to the UTF-8 format
		/*
		try {
			encodedUri= HUrl.hEncodeParam(uri, HUrl.URLENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		SKOSConceptScheme sConceptScheme = new SKOSConceptScheme(uri) ;
			
		return sConceptScheme;
	}
	
	//map a Dafoe tc into a skosConcept
	/**
	 * Creates the skos concept.
	 *
	 * @param tc the tc
	 * @return the sKOS concept
	 */
	private SKOSConcept createSkosConcept(ITerminoConcept tc){//ok
		String uri= tc.getTerminoOntology().getNameSpace()+"/"+tc.getLabel();
		
		// format uri
		String encodedUri= StringFormatterTools.replaceRDFUnAcceptableCharacter(uri);
		//encoding the uri according to the UTF-8 format
		/*
		try {
			encodedUri= HUrl.hEncodeParam(uri, HUrl.URLENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		SKOSConcept sConcept = new SKOSConcept(encodedUri) ;
				
		addSkosAnnotation(sConcept, tc);
		
		return sConcept;
	}
	
	//map a Dafoe tc into a skosConcept
	/**
	 * Creates the skos concept.
	 *
	 * @param tc the tc
	 * @param isTopConcept the is top concept
	 * @return the sKOS concept
	 */
	private SKOSConcept createSkosConcept(ITerminoConcept tc, boolean isTopConcept){//ok
				
		SKOSConcept sConcept = createSkosConcept(tc);
		sConcept.setAsTopConcept(isTopConcept);
				
		addSkosAnnotation(sConcept, tc);
				
		return sConcept;
	}
	
	
	/**
	 * Creates the skos collection from a Dafoe TCHashSet.
	 * must be run after all tc have been mapped
	 * @param toC the to c
	 * @return the sKOS collection
	 */
	private  SKOSCollection createSkosCollection(ITerminoOntoCollection toC){
		String uri= toC.getTerminoOntology().getNameSpace()+"/"+toC.getLabel();
		
		
		// format uri
		String encodedUri= StringFormatterTools.replaceRDFUnAcceptableCharacter(uri);
		//encoding the uri according to the UTF-8 format
		/*
		try {
			encodedUri= HUrl.hEncodeParam(uri, HUrl.URLENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		if(toC instanceof TcHashSetImpl){
			TcHashSetImpl cH= (TcHashSetImpl)toC;
			SKOSCollection skosC= new SKOSCollection(encodedUri);
			/* 
			for(ITerminoOntoObject to: cH.getCollection()){
				if(to instanceof ITerminoConcept){
					ITerminoConcept tc= (ITerminoConcept)to;
					skosC.addMember(mappedTCs.get(tc));
				}
			}
			*/
			return skosC;
		}
		return null;
	}
	
	
	/**
	 * Creates the skos ordered collection from a Dafoe TCArrayList .
	 * must be run after all tc have been mapped
	 * @param toC the to c
	 * @return the sKOS ordered collection
	 */
	private  SKOSOrderedCollection createSkosOrderedCollection(ITerminoOntoCollection toC){
	
		String uri= toC.getTerminoOntology().getNameSpace()+"/"+toC.getLabel();
		
		// format uri
		String encodedUri= StringFormatterTools.replaceRDFUnAcceptableCharacter(uri);
		//encoding the uri according to the UTF-8 format
		/*
		try {
			encodedUri= HUrl.hEncodeParam(uri, HUrl.URLENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		if(toC instanceof TcArrayListImpl){
			TcArrayListImpl cH= (TcArrayListImpl)toC;
			SKOSOrderedCollection skosC= new SKOSOrderedCollection(encodedUri);
			/*
			for(ITerminoOntoObject to: cH.getCollection()){
				if(to instanceof ITerminoConcept){
					ITerminoConcept tc= (ITerminoConcept)to;
					skosC.addMemberList(mappedTCs.get(tc));
				}
			}
			*/
			return skosC;
		}
		
		return null;
	}


/**
 * Adds the skos annotation to a skosconcept using the Dafoe terminoOntoAnnotation of a TC.
 * must be run after all tc have been mapped
 * @param skosConcept the skos concept
 * @param tc the tc
 */
private  void addSkosAnnotation(SKOSConcept skosConcept,ITerminoConcept tc){//
		
		
		Set<ITerminoOntoAnnotation> tcAnnotations= tc.getAnnotations();
		
		for(ITerminoOntoAnnotation tcAnnot: tcAnnotations){
			ITerminoOntoAnnotationType tcAnnoType= tcAnnot.getTerminoOntoAnnotationType();
			
			//System.out.println("################ label= "+tcAnnoType.getLabel());
			 SKOS_ANNOTATION_TYPE_ENUM skosAnnotType = getMatchedSkosAnnotationType(tcAnnoType);
			
			 LocalizedText tcAnnotValue= new LocalizedText(tcAnnot.getValue());
			boolean isOtherMatchType= true;
			
			if(skosAnnotType== null){ // no mapping found between skosAnnotype and toAnnotType, then default mapping
				
				//LocalizedText tcAnnotValue= new LocalizedText(tcAnnot.getValue());
				skosConcept.addPrefLabel(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
				
				isOtherMatchType= false;
			
			}
			else{ // a mapping exists
				
				// map skos labels
				if(skosAnnotType.getLabel().equals(SKOS_ANNOTATION_TYPE_ENUM.SKOS_PREF_LABEL.getLabel())){
										
					skosConcept.addPrefLabel(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
					
					isOtherMatchType= false;
				}
				if(skosAnnotType.getLabel().equals(SKOS_ANNOTATION_TYPE_ENUM.SKOS_ALT_LABEL.getLabel())){
										
					skosConcept.addAltLabel(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
					
					isOtherMatchType= false;
				}
				if(skosAnnotType.getLabel().equals(SKOS_ANNOTATION_TYPE_ENUM.SKOS_HIDDEN_LABEL.getLabel())){
					
					skosConcept.addHiddenLabel(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
					
					isOtherMatchType= false;
				}
				
				// map skos documentary notes
				if(skosAnnotType.getLabel().equals(SKOS_ANNOTATION_TYPE_ENUM.SKOS_NOTE.getLabel())){
					
					skosConcept.addNote(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
					
					isOtherMatchType= false;
				}
				if(skosAnnotType.getLabel().equals(SKOS_ANNOTATION_TYPE_ENUM.SKOS_DEFINITION.getLabel())){
					
					skosConcept.addDefinition(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
					
					isOtherMatchType= false;
				}
				if(skosAnnotType.getLabel().equals(SKOS_ANNOTATION_TYPE_ENUM.SKOS_EXAMPLE.getLabel())){
					
					skosConcept.addExample(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
					
					isOtherMatchType= false;
				}
				if(skosAnnotType.getLabel().equals(SKOS_ANNOTATION_TYPE_ENUM.SKOS_HISTORY_NOTE.getLabel())){
					
					skosConcept.addHistoryNote(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
					
					isOtherMatchType= false;
				}
				if(skosAnnotType.getLabel().equals(SKOS_ANNOTATION_TYPE_ENUM.SKOS_EDITORIAL_NOTE.getLabel())){
					
					skosConcept.addEditorialNote(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
					
					isOtherMatchType= false;
				}
				if(skosAnnotType.getLabel().equals(SKOS_ANNOTATION_TYPE_ENUM.SKOS_CHANGE_NOTE.getLabel())){
					
					skosConcept.addChangeNote(tcAnnotValue.getText(),tcAnnotValue.getLanguage());
					
					isOtherMatchType= false;
				}
				
				if(isOtherMatchType){// a non enumerated type of annoatype, write a generic algo
					//TO COMPLETE explain differente with if(skosAnnotType== null)
					//System.out.println(" OTHER ");
				}
			}
			
		} //for
		
		// export related TerminoObject as skos Label:
		// star term is skos:prefLabel and variant term are skos:altLabel 
		Set<ITerm> terms= tc.getMappedTerms();
		
		for(ITerm t: terms){
			if(t.getStarTerm()==null){// then t is itself the star term
				
				// map the star term as skos prefLabel
					
					String language= ""; //VT: TO COMPLETE, calculate language
					
					
					skosConcept.addPrefLabel(t.getLabel(),language);
										
			}else{ // the term is a variant
				// map the variant term as skos altLabel
				String language= ""; //VT: TO COMPLETE, calculate language
				
				
				skosConcept.addAltLabel(t.getLabel(),language);
				
			}
		}
	}
	
	/**
	 * Gets the matched skos annotation type corresponding to a Dafoe terminoOntoAnnotationType.
	 *
	 * @param toAnnotType the to annot type
	 * @return the matched skos annotation type
	 */
	private SKOS_ANNOTATION_TYPE_ENUM getMatchedSkosAnnotationType(ITerminoOntoAnnotationType toAnnotType){
		//return mappedAnnotationType.get(toAnnotType);
		TERMINO_ONTO_ANNOTATION_TYPE_ENUM dafoeAnnotType= Services.getTerminoOntoAnnotationTypeFromDatabase(toAnnotType);
		return Services.getMappedAnnotationType().get(dafoeAnnotType);
	}
	
	
	/**
	 * Creates the skos association from a Dafoe terminoConcept Relation.
	 * must be execute after all TCs have been mapped
	 * @param bTCR the b tcr
	 */
	private void createSkosAssociation(MockBinaryTCRelation bTCR){//ok
		ITerminoConcept leftTC= bTCR.getTc1();
		SKOSConcept leftSkosConcept= mappedTCs.get(leftTC);
		
		ITerminoConcept rightTC= bTCR.getTc2();
		SKOSConcept rightSkosConcept= mappedTCs.get(rightTC);
		
		leftSkosConcept.addRelated(rightSkosConcept);
		rightSkosConcept.addRelated(leftSkosConcept);
		
		
	}
	
	//
	/**
	 * Creates the binary tc relation.
	 *
	 * @param tcr the tcr
	 * @return the mock binary tc relation
	 */
	private MockBinaryTCRelation createBinaryTCRelation(//
			ITerminoConceptRelation tcr) {

		return SerializableObjectFactory.getBinaryTCRelation(tcr);

	}
	
	
	// parcours en largeur 
	/**
	 * Resolve node.
	 *
	 * @param tc the tc
	 */
	private void resolveNode(ITerminoConcept tc){//ok
				
		SKOSConcept skosConcept= createSkosConcept(tc);
		
		// insert the created skosConcept in the hierarchic of skosConcepts
		
		Set<ITerminoConcept> parentTCs= tc.getParents();
	
		for(ITerminoConcept parentTC: parentTCs){
			
			if(mappedTCs.containsKey(parentTC)){// always true then we resolve only child TC, not top TC
				SKOSConcept parentSKOS= mappedTCs.get(parentTC);
				parentSKOS.addNarrower(skosConcept);
				skosConcept.addBroader(parentSKOS);
			}
		}
		mappedTCs.put(tc, skosConcept);
	}
	
	/**
	 * Builds the direct sub tree.
	 *
	 * @param parentTree the parent tree
	 * @return the list
	 */
	private  List<ITerminoConcept> buildDirectSubTree(List<ITerminoConcept> parentTree){//ok
		List<ITerminoConcept> directSubTree= new ArrayList<ITerminoConcept>();
		
		for(ITerminoConcept tc: parentTree){
			directSubTree.addAll(tc.getChildren());
		}
		return directSubTree;
	}
	
	
	/**
	 * Write file.
	 *
	 * @param f the f
	 * @param format the format
	 */
	@SuppressWarnings("static-access")
	public void writeFile(File f, SKOSOutputFormat format){//ok
		skosModel.writeFile(f, format.toString());
	}
	
   /**
    * Write output.
    *
    * @param o the o
    * @param format the format
    */
   @SuppressWarnings("static-access")
   public  void writeOutput(OutputStream o, SKOSOutputFormat format){//ok
		skosModel.getModel().write(o, format.toString());
	}
	
 
}
