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

package org.dafoe.terminologiclevel.terminologycard.dialogs.models;

import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermRelationImpl;

public class TerminologicRelationModel {
	private String term1;
	private String relationType;
	private String term2;
	private int status;
	
	public static List <TerminologicRelationModel> createTerminologicRelationData(){
		List <TerminologicRelationModel> data = new ArrayList <TerminologicRelationModel>();
		try {
			data = org.dafoe.terminologiclevel.Activator.getDefault().dafoeSession.createCriteria(TermRelationImpl.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}
	
	public TerminologicRelationModel(){
		this.term1 = ""; //$NON-NLS-1$
		this.relationType = ""; //$NON-NLS-1$
		this.term2 = ""; //$NON-NLS-1$
		this.status = 3;
	}
	
	public TerminologicRelationModel(String term1, String relationType, String term2, int status){
		this.term1 = term1;
		this.relationType = relationType;
		this.term2 = term2;
		this.status = status;
	}

	public void setTerm1(String term){
		this.term1 = term;
	}

	public void setRelationType(String type){
		this.relationType = type;
	}

	public void setTerm2(String term){
		this.term2 = term;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public String getTerm1(){
		return this.term1;
	}

	public String getRelationType(){
		return this.relationType;
	}

	public String getTerm2(){
		return this.term2;
	}
	
	public int getStatus(){
		return this.status;
	}
}
