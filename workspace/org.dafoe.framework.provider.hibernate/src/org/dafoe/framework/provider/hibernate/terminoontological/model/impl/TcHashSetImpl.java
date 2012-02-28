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
package org.dafoe.framework.provider.hibernate.terminoontological.model.impl;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITcSet;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoCollection;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTcHashSetImpl;

/**
 * This is the object class that relates to the m22_tc_collection table.
 * Any customizations belong here.
 */
public class TcHashSetImpl extends BaseTcHashSetImpl implements ITcSet{

	private Set<ITerminoConcept> contents=null; // a transient object
	
	public TcHashSetImpl () {
		super();
		super.setElements(new HashSet<ITerminoConcept>());
	}

	@Override
	public boolean add(ITerminoOntoObject to) {
		if(to instanceof ITerminoConcept){
			return getCollection().add((ITerminoConcept)to);
		}
		return false;
	}

	@Override
	public boolean addAll(ITerminoOntoCollection c) {
		if(c instanceof TcHashSetImpl){
			return getCollection().addAll(((TcHashSetImpl) c).getCollection());
		}
		return false;
	}

	@Override
	public void clear() {
		super.getElements().clear();
		
	}

	@Override
	public boolean contains(ITerminoOntoObject to) {
		if(to instanceof ITerminoConcept){
			return getCollection().contains((ITerminoConcept)to);
		}
		return false;
	}

	@Override
	public boolean containsAll(ITerminoOntoCollection c) {
		if(c instanceof TcHashSetImpl){
			return getCollection().containsAll(((TcHashSetImpl) c).getCollection());
		}
		return false;
	}

	@Override
	public boolean equals(ITerminoOntoObject to) {
		if(to instanceof ITerminoConcept){
			return getCollection().equals(to);
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		
		return super.getElements().isEmpty();
	}

	@Override
	public Iterator<ITerminoConcept> iterator() {
		
		return getCollection().iterator();
	}

	@Override
	public boolean remove(ITerminoOntoObject to) {
		if(to instanceof ITerminoConcept){
			return getCollection().remove((ITerminoConcept)to);
		}
		return false;
	}

	@Override
	public boolean removeAll(ITerminoOntoCollection c) {
		if(c instanceof TcHashSetImpl){
			return getCollection().removeAll(((TcHashSetImpl) c).getCollection());
		}
		return false;
	}

	@Override
	public boolean retainAll(ITerminoOntoCollection c) {
		if(c instanceof TcHashSetImpl){
			return getCollection().retainAll(((TcHashSetImpl) c).getCollection());
		}
		return false;
	}

	@Override
	public int size() {
		
		return getCollection().size();
	}

	
	public Set<ITerminoConcept> getCollection() {
		
		if(contents== null){
			contents= new HashSet<ITerminoConcept>();
			contents.addAll(super.getElements());
		}
		
		return contents;
		
		
	}

//***********************************VT: TO COMPLETE
	
	@Override
	public TERMINO_ONTO_OBJECT_STATE getState() {
		
		//return Util.getTerminoOntoObjectStateFromDatabase(super.getStatus());
		return null;
	}



	@Override
	public void setState(TERMINO_ONTO_OBJECT_STATE state) {
		//super.setStatus(state.getValue());
		
	}
	
	
	@Override
	public boolean addAnnotation(ITerminoOntoAnnotation annot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMappedOntoObject(IOntoObject ontoObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<ITerminoOntoAnnotation> getAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IOntoObject> getMappedOntoObjects() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean removeAnnotation(ITerminoOntoAnnotation annot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeMappedOntoObject(IOntoObject ontoObject) {
		// TODO Auto-generated method stub
		return false;
	}


	
}