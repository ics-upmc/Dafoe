/*
 * (c) CRITT Informatique - Copyright 2009 
 */

package org.dafoe.api.skos.vocabulary;

import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * The SKOSOrderedCollection class.
 *  @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class SKOSOrderedCollection extends SKOSResource{
	
	private RDFList list;
	
	public SKOSOrderedCollection(String uri){
		super(SKOS.OrderedCollection, uri);
		list = SKOSModel.getModel().createList();
		SKOSModel.addOrderedCollection(this);
	}

	public SKOSOrderedCollection(Resource rsc){
		super(rsc);
		list = SKOSModel.getModel().createList();
		SKOSModel.addOrderedCollection(this);
	}

	public void addMemberList(SKOSConcept c){
		list = list.with(c.getResource());
		this.rsc.addProperty(SKOS.memberList, list);
	}
	
	public SKOSConcept[] getMembers(){
		return getSKOSResourceForProperty(SKOS.memberList).toArray(new SKOSConcept[0]);
	}
}
