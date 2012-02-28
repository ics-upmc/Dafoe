/*
 * (c) CRITT Informatique - Copyright 2009 
 */

package org.dafoe.api.skos.vocabulary;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * The SKOSCollection class.
 *  @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class SKOSCollection extends SKOSResource{

	public SKOSCollection(String uri){
		super(SKOS.Collection, uri);
		SKOSModel.addCollection(this);
	}
		
	public SKOSCollection(Resource rsc){
		super(rsc);
		SKOSModel.addCollection(this);
	}
		
	public void addMember(SKOSConcept c){
		this.rsc.addProperty(SKOS.member, c.getResource());
		
	}
	
	public SKOSConcept[] getMembers(){
		return getSKOSResourceForProperty(SKOS.member).toArray(new SKOSConcept[0]);
	}
}
