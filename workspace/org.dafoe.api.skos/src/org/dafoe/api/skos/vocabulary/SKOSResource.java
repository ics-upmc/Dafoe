/*
 * (c) CRITT Informatique - Copyright 2009 
 */

package org.dafoe.api.skos.vocabulary;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * The SKOSResource class.
 *  @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */

public abstract class SKOSResource {
	protected Resource rsc; // a reference to the actual RDF Resource
	protected String localUri; // a local URI identifying the resource
	
	/**
	 * SKOS Resource constructor. 
	 * If a URI is provided, the created SKOS resource is identified by this URI.
	 * If no URI is provided (anonymous resource), the created SKOS resource is identified by the 
	 * identifier associated with the underlying generated RDF resource. 
	 * @param uri: a SKOS resource identifier
	 * 
	 */
	public SKOSResource(String uri){
		if ((uri != null) && (!uri.equals(""))){
			this.rsc = SKOSModel.getModel().createResource(uri);
			this.localUri = uri;
		} else {
			this.rsc = SKOSModel.getModel().createResource();
			this.localUri = this.rsc.getId().toString();
		}
	}
	
	/**
	 * SKOS Resource constructor. 
	 * Create a SKOS resource from an RDF resource.
	 * If the RDF Resource is not defined, create an anonymous RDF resource and then, assign it to the 
	 * SKOS resource.
	 * If the RDF resource is not anonymous, its URI is assigned to the created SKOS resource. Otherwise,
	 * the created SKOS resource is identified by the identifier associated with the underlying 
	 * generated anonymous RDF resource. 
	 * @param rsc: an RDF resource
	 */
	public SKOSResource(Resource rsc){
		if (rsc == null) {
			this.rsc = SKOSModel.getModel().createResource();
		} else {
			this.rsc = rsc;
		}
		if(!rsc.isAnon()){
			this.localUri = rsc.getURI();
		} else {
			this.localUri = rsc.getId().toString();
		}
	}
	
	/**
	 * SKOS Resource constructor. 
	 * Create a SKOS resource from an RDF resource and a uri.
	 * @param rsc: an RDF resource
	 * @param uri: a SKOS resource identifier
	 */

	public SKOSResource(Resource rsc, String uri){
		if (rsc != null) {
			if ((uri != null) && (!uri.equals(""))){
				this.rsc = SKOSModel.getModel().createResource(uri, rsc);
				this.localUri = uri;
			} else {
				this.rsc = SKOSModel.getModel().createResource(rsc);
				this.localUri = this.rsc.getId().toString();
			}
		} else {
			this.rsc = SKOSModel.getModel().createResource();
			
			if(!rsc.isAnon()){
				this.localUri = rsc.getURI();
			} else {
				this.localUri = rsc.getId().toString();
			}			
		}
	}

	/**
	 * 
	 * @return the RDF resource on which the SKOS resource is based.
	 */
	public Resource getResource(){
		return this.rsc;
	}

	/**
	 * 
	 * @return the SKOS resource identifier
	 */
	public String getUri(){
		return this.localUri;
	}

	/**
	 * 
	 * @return true if the SKOS resource is anonymous, otherwise false.
	 */
	public boolean isAnon(){
		return this.rsc.isAnon();
	}
	
	/**
	 * 
	 * @param p: a SKOS predicate (a RDF Property)
	 * @return the set of SKOS resources that are involved as the subject 
	 * of a statement in which the predicate is used
	 */
	protected Set<SKOSResource> getSKOSResourceForProperty(Property p){
		Set<SKOSResource> res = new HashSet<SKOSResource>();		
		StmtIterator iter = this.getResource().listProperties(p);
		while (iter.hasNext()) {
			Statement st = iter.nextStatement();
			if (!this.isAnon()){
				if(st.getSubject().getURI().equals(this.getUri())){
					Resource obj = (Resource)st.getObject();
					if(!obj.isAnon()){
						res.add(SKOSModel.getConcept((obj.getURI())));
					} else {
						res.add(SKOSModel.getConcept((obj.getId().toString())));
					}
				}
			} else {
				AnonId id = this.getResource().getId();
				if(st.getSubject().getId().equals(id)){
					Resource obj = (Resource)st.getObject();
					if(!obj.isAnon()){
						res.add(SKOSModel.getConcept((obj.getURI())));
					} else {
						res.add(SKOSModel.getConcept((obj.getId().toString())));
					}
				}
			}
		}
		return res;
	}

}
