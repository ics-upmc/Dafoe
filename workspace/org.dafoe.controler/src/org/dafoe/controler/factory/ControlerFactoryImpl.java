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
package org.dafoe.controler.factory;

import org.dafoe.controler.model.IControlerOntology;
import org.dafoe.controler.model.IControlerTerminoOntology;
import org.dafoe.controler.model.IControlerTerminology;
import org.dafoe.controler.model.IPlatformControler;
import org.dafoe.controler.model.impl.ControlerOnlotogyApiImpl;
import org.dafoe.controler.model.impl.ControlerPlatformApiImpl;
import org.dafoe.controler.model.impl.ControlerTerminologyApiImpl;
import org.dafoe.controler.model.impl.ControlerTerminoOntologyApiImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlerFactoryImpl.
 * 
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * @author <a href="mailto:texier@ensma.fr">Guillaume TEXIER</a>
 */
public class ControlerFactoryImpl {

	
/** The instance crtl termino. */
private static IControlerTerminology instanceCrtlTermino; 	

/** The instance ctrl termino onto. */
private static IControlerTerminoOntology instanceCtrlTerminoOnto;

/** The instance ctrl onto. */
private static IControlerOntology instanceCtrlOnto;

private static IPlatformControler instanceCtrPlatform;

/**
 * Gets the terminology controler.
 * 
 * @return the terminology controler
 */
public static IControlerTerminology getTerminologyControler(){
		if(instanceCrtlTermino==null){
			instanceCrtlTermino= new ControlerTerminologyApiImpl();
		}
		return instanceCrtlTermino;
	}

/**
 * Gets the termino onto controler.
 * 
 * @return the termino onto controler
 */
public static IControlerTerminoOntology getTerminoOntoControler(){
	if(instanceCtrlTerminoOnto== null){
		instanceCtrlTerminoOnto= new ControlerTerminoOntologyApiImpl();
	}
	
	return instanceCtrlTerminoOnto;
}

/**
 * Gets the onto controler.
 * 
 * @return the onto controler
 */
public static IControlerOntology getOntoControler(){
	if(instanceCtrlOnto== null){
		instanceCtrlOnto= new ControlerOnlotogyApiImpl();
	}
	
	return instanceCtrlOnto;
}

public static IPlatformControler getPlatformControler(){
	if(instanceCtrPlatform== null){
		instanceCtrPlatform= new ControlerPlatformApiImpl();
	}
	
	return instanceCtrPlatform;
}

/** The Constant currentCorpusEvent. */
public static final String currentCorpusEvent = "currentCorpusEvent";

/** The Constant currentTermEvent. */
public static final String currentTermEvent = "currentTerm";

/** The Constant currentTermsEvent. */
public static final String currentTermsEvent = "currentTerms";

/** The Constant currentVariantsEvent. */
public static final String currentVariantsEvent = "currentVariants";

/** The Constant currentDocumentEvent. */
public static final String currentDocumentEvent = "currentDocument";

/** The Constant newVariantEvent. */
public static final String newVariantEvent = "newVariant";

/** The Constant newStatusEvent. */
public static final String newStatusEvent = "newStatus";

/** The Constant newVariantDetachedEvent. */
public static final String newVariantDetachedEvent = "variantDetached";

/** The Constant termLabelModifiedEvent. */
public static final String termLabelModifiedEvent= "termLabelModified";

/** The Constant currentRelationEvent. */
public static final String currentRelationEvent = "currentRelation";

/** The Constant newRelationStatusEvent. */
public static final String newRelationStatusEvent = "newStatusRelation";

/** The Constant termsDeletedEvent. */
public static final String termsDeletedEvent = "termsDeleted";

/** The Constant termsImportedEvent. */
public static final String termsImportedEvent = "termsImported";

/** The Constant currentRelationTypeEvent. */
public static final String currentRelationTypeEvent = "currentRelationType";

/** The Constant currentMethodEvent. */
public static final String currentMethodEvent = "currentMethod";

/** The Constant relationDeletedEvent. */
public static final String relationDeletedEvent = "relationDeleted";

public static final String CURRENT_LOADED_CORPUS_EVENT="currentLoadedCorpusEvent";

//******* Onto layer

/** The Constant currentTerminoOntologyEvent. */
public static final String currentTerminoOntologyEvent = "currentTerminoOntology";

/** The Constant currentTerminoConceptEvent. */
public static final String currentTerminoConceptEvent = "currentTerminoConcept";

/** The Constant currentRTCEvent. */
public static final String currentRTCEvent = "currentRTC";

/** The Constant renameTCEvent. */
public static final String renameTCEvent = "renameTC";

/** The Constant newTCToOntoObjectMappingEvent. */
public static final String newTCToOntoObjectMappingEvent = "TCtoOntoObjectEvent";

/** The Constant updateTCsTreeEvent. */
public static final String updateTCsTreeEvent = "updateTCsTreeEvent";

/** The Constant addTCParentEvent. */
public static final String addTCParentEvent = "addTCParentEvent";

/** The Constant updateTCTreeEvent. */
public static final String updateTCTreeEvent = "updateTCTreeEvent";

/** The Constant updateTCEvent. */
public static final String updateTCEvent = "updateTCEvent";

/** The Constant newRTCStatusEvent. */
public static final String newRTCStatusEvent = "newRTCStatusEvent";

/** The Constant deleteRTCEvent. */
public static final String deleteRTCEvent = "deleteRTCEvent";

/** The Constant updateRTCTypeEvent. */
public static final String updateRTCTypeEvent = "updateRTCTypeEvent";

/** The Constant externalObjectToTCSourceEvent. */
public static final String externalObjectToTCSourceEvent = "externalObjectToTCSourceEvent";

/** The Constant currentClassEvent. */
public static final String currentClassEvent = "currentClassEvent";

/** The Constant currentPropertyEvent. */
public static final String currentPropertyEvent = "currentPropertyEvent";

/** The Constant currentInstanceEvent. */
public static final String currentInstanceEvent = "currentInstanceEvent";

/** The Constant currentOntologyEvent. */
public static final String currentOntologyEvent = "currentOntologyEvent";

/** The Constant changeOntologyEvent. */
public static final String changeOntologyEvent = "changeOntologyEvent";

/** The Constant selectNoOntology. */
public static final String selectNoOntology = "selectNoOntology";


/** The Constant addClassParentEvent. */
public static final String addClassParentEvent = "addClassParentEvent";

/** The Constant selectNoClassEvent. */
public static final String selectNoClassEvent = "selectNoClassEvent";

/** The Constant updateClasseTreeEvent. */
public static final String updateClasseTreeEvent = "updateClasseTreeEvent";

/** The Constant updateClasseEvent. */
public static final String updateClasseEvent = "updateClasseEvent";

/** The Constant updatePropertyEvent. */
public static final String updatePropertyEvent = "updatePropertyEvent";

	
// **************************  TC layer
// state
/** The Constant CURRENT_TERMINO_ONTOLOGY_EVENT. */
public static final String CURRENT_TERMINO_ONTOLOGY_EVENT="currentTerminoOntologyEvent";

public static final String NEW_TERMINOCONCEPT_EVENT="newTerminoConceptEvent";


/** The Constant CURRENT_TERMINOCONCEPT_EVENT. */
public static final String CURRENT_TERMINOCONCEPT_EVENT="currentTerminoConceptEvent";

/** The Constant CURRENT_TERMINOCONCEPT_RELATION_EVENT. */
public static final String CURRENT_TERMINOCONCEPT_RELATION_EVENT="currentTerminoConceptRelationEvent";

// service
/** The Constant ADD_TERMINOCONCEPT_EVENT. */
public static final String ADD_TERMINOCONCEPT_EVENT="addTerminoConceptEvent";

/** The Constant UPDATE_TERMINOCONCEPT_EVENT. */
public static final String UPDATE_TERMINOCONCEPT_EVENT="updateTerminoConceptEvent";

/** The Constant ADD_TERMINOCONCEPT_RELATION_EVENT. */
public static final String ADD_TERMINOCONCEPT_RELATION_EVENT="addTerminoConceptRelationEvent";
}
