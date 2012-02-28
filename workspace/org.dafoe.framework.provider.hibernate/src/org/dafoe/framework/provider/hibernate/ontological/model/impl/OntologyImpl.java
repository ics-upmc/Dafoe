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
package org.dafoe.framework.provider.hibernate.ontological.model.impl;

import java.util.HashSet;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseOntologyImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl;

/**
 * This is the object class that relates to the m23_ontology table. Any
 * customizations belong here.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class OntologyImpl extends BaseOntologyImpl implements IOntology {

	
	public OntologyImpl() {
		super();
		super.setClasses(new HashSet<IClass>());
		super.setDatatypeProperties(new HashSet<IDatatypeProperty>());
		super.setObjectProperties(new HashSet<IObjectProperty>());
		super.setMappedTerminoOntologies(new HashSet<ITerminoOntology>());
		super.setLanguage("");
	}

	

	
	
//	@Override
	public Set<IProperty> getProperties() {
		Set<IProperty> props= new HashSet<IProperty>();
		props.addAll(this.getObjectProperties());
		props.addAll(this.getDatatypeProperties());
		
		return props;
	}
	
//	@Override
	public boolean addMappedTerminoOntology(ITerminoOntology to) {
		
		boolean ok1= super.getMappedTerminoOntologies().add(to);
		boolean ok2= ((TerminoOntologyImpl)to).getMappedOntologies().add(this);

		return ok1 && ok2;
	}

	@Override
	public Set<ITerminoOntology> getMappedTerminoOntologies() {

		return super.getMappedTerminoOntologies();
	}

//	@Override
	public boolean removeMappedTerminoOntology(ITerminoOntology to) {
		
		//1: remove existing mappings between currentOntology.OntoObject and to.TerminoOntoObject
		// TO COMPLETE
		
		//2: remove exiting mapping bewteen currentOntology and mappedTerminoOntology
		
		boolean ok1= super.getMappedTerminoOntologies().remove(to);
		boolean ok2= ((TerminoOntologyImpl)to).getMappedOntologies().remove(this);
		
		return ok1 && ok2;
	}

//	@Override
	public boolean addOntoObject(IOntoObject ontoObject) {
		
		if (ontoObject instanceof IClass) {
			IClass cls = (IClass) ontoObject;
			
			((ClassImpl)cls).setOntology(this);
			
			return super.getClasses().add(cls);
		}
		if (ontoObject instanceof IObjectProperty) {
			IObjectProperty objProp = (IObjectProperty) ontoObject;
						
			((ObjectPropertyImpl)objProp).setOntology(this);
			return super.getObjectProperties().add(objProp);
		}
		if (ontoObject instanceof IDatatypeProperty) {
			IDatatypeProperty dProp = (IDatatypeProperty) ontoObject;
			
			((DatatypePropertyImpl)dProp).setOntology(this);
			
			return super.getDatatypeProperties().add(dProp);
		}
		return false;
	}

//	@Override
	public boolean removeOntoObject(IOntoObject ontoObject) {
		if (ontoObject instanceof IClass) {
			IClass cls = (IClass) ontoObject;
			
			((ClassImpl)cls).setOntology(null);
			return super.getClasses().remove(cls);
		}
		if (ontoObject instanceof IObjectProperty) {
			IObjectProperty objProp = (IObjectProperty) ontoObject;
			
			
			((ObjectPropertyImpl)objProp).setOntology(null);
			return super.getObjectProperties().remove(objProp);
		}
		if (ontoObject instanceof IDatatypeProperty) {
			IDatatypeProperty dProp = (IDatatypeProperty) ontoObject;
			
			
			((DatatypePropertyImpl)dProp).setOntology(null);
			return super.getDatatypeProperties().remove(dProp);
		}
		return false;

	}





	@Override
	public boolean removeAllMappedTerminoOntology(
			Set<ITerminoOntology> mappedTos) {
		boolean isRemoved= true;
		
		//1: remove exiting mapping bewteen currentOntology and mappedTerminoOntology
		for(ITerminoOntology to: mappedTos){
			isRemoved= isRemoved && removeMappedTerminoOntology(to);
		}
		return isRemoved;
	}


}