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
package org.dafoe.framework.provider.hibernate.terminological.model.impl;

import java.util.HashSet;
import java.util.Set;

import org.dafoe.framework.core.terminological.model.IMethod;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTypeRelationTerminoImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TypeRelationTcImpl;
import org.dafoe.framework.provider.hibernate.util.Util;

/**
 * The Class TypeRelationTerminoImpl.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TypeRelationTerminoImpl  extends BaseTypeRelationTerminoImpl implements ITypeRelationTermino{

	
	public TypeRelationTerminoImpl () {
		super();
		
		super.setMethods(new HashSet<IMethod>());
		super.setTermRelations(new HashSet<ITermRelation>());
		
		super.setMappedTcRelationTypes(new HashSet<ITypeRelationTc>());
		setState(TERMINO_OBJECT_STATE.UNKNOWN);
		
	}

	
	
//	@Override
	public void addMethod(IMethod method) {
		
		super.addToMethods(method);
		((MethodImpl)method).addToTypeRelation(this);
	}

	
//	@Override
	public void addTermRelation(ITermRelation termRel) {
		
		super.addToTermRelations(termRel);
		((TermRelationImpl)termRel).setTypeRel(this);
		
	}

	// mapping with TcTypeRelation
//	@Override
	public boolean addMappedTcRelationType(ITypeRelationTc obj) {
		super.addToMappedTcRelationTypes(obj);
		
	      boolean ok1= super.getMappedTcRelationTypes().add(obj);
	      boolean ok2= ((TypeRelationTcImpl)obj).getMappedTermRelationTypes().add(this);
	      return ok1 && ok2;
	}


//	@Override
	public boolean removeMappedTcRelationType(ITypeRelationTc obj) {
		boolean isRemoved1= false;
		boolean isRemoved2= false;
		
		isRemoved1= super.getMappedTcRelationTypes().remove(obj);
		isRemoved2= ((ITypeRelationTc) obj).getMappedTermRelationTypes().remove(this);
		
		return isRemoved1 && isRemoved2;
	}



	@Override
	public void setState(TERMINO_OBJECT_STATE status) {
		super.setStatus(status.getValue());
		
	}
	
	@Override
	public TERMINO_OBJECT_STATE getState() {
		return Util.getTerminoObjectStateFromDatabase(super.getStatus());
		
	}
	
	

	@Override
	public boolean addMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		
		boolean ok1= false;
		boolean ok2= false;
		
		if(toObject instanceof ITypeRelationTc){
			TypeRelationTcImpl typeTcr= (TypeRelationTcImpl)toObject;
			ok1= super.getMappedTcRelationTypes().add(typeTcr);
			ok2= ((TypeRelationTcImpl)typeTcr).getMappedTermRelationTypes().add(this);
			
			return ok1 && ok2;
		}
		return false;
	}

	@Override
	public Set<ITerminoOntoObject> getMappedTerminoOntoObjects() {
		Set<ITerminoOntoObject> toObjs= new HashSet<ITerminoOntoObject>();
		toObjs.addAll(this.getMappedTcRelationTypes());
		
		return toObjs;
	}

	@Override
	public boolean removeMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		boolean ok1= false;
		boolean ok2= false;
		
		if(toObject instanceof ITypeRelationTc){
			TypeRelationTcImpl typeTcr= (TypeRelationTcImpl)toObject;
			ok1= super.getMappedTcRelationTypes().remove(typeTcr);
			ok2= ((TypeRelationTcImpl)typeTcr).getMappedTermRelationTypes().remove(this);
			
			return ok1 && ok2;
		}
		return false;
	}


	}