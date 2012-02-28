/*
 * (c) CRITT Informatique - Copyright 2009 
 */

package org.dafoe.api.skos.vocabulary;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * The SKOSConceptScheme class.
 *  @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class SKOSConceptScheme extends SKOSResource{

	public SKOSConceptScheme(String uri){
		super(SKOS.ConceptScheme, uri);
		SKOSModel.addConceptScheme(this);
	}
		
	public SKOSConceptScheme(Resource rsc){
		super(rsc);
		SKOSModel.addConceptScheme(this);
	}
		
	
	//VT: it is better to use mySkosConcept.setAsTopConcept(TRUE/FLASE)
	public void addTopConcept(SKOSConcept c){
		//this.rsc.addLiteral(SKOS.hasTopConcept, c.getResource());
		this.rsc.addProperty(SKOS.hasTopConcept, c.getResource());
		
	}
	
	
	public SKOSConcept[] getTopConcepts(){
		return getSKOSResourceForProperty(SKOS.hasTopConcept).toArray(new SKOSConcept[0]);
	}
	
	//VT
	/*
	public void addCollection(SKOSCollection c){
		
		SKOSModel.addCollection(c);
		
	}
	
	public void addOrderedCollection(SKOSOrderedCollection oc){
		
		SKOSModel.addOrderedCollection(oc);
		
	}
	
	*/
	
	public SKOSCollection[] getCollections(){
		//return getSKOSResourceForProperty(SKOS.collection).toArray(new SKOSCollection[0]);
		return SKOSModel.getCollections();
	}
	
	public SKOSOrderedCollection[] getOrderedCollections(){
		//return getSKOSResourceForProperty(SKOS.collection).toArray(new SKOSCollection[0]);
		return SKOSModel.getOrderedCollections();
	}
		
	
	
}
