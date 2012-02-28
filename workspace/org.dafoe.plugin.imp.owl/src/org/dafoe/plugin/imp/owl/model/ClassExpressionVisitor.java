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

package org.dafoe.plugin.imp.owl.model;

import java.util.Hashtable;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataAllRestriction;
import org.semanticweb.owl.model.OWLDataExactCardinalityRestriction;
import org.semanticweb.owl.model.OWLDataMaxCardinalityRestriction;
import org.semanticweb.owl.model.OWLDataMinCardinalityRestriction;
import org.semanticweb.owl.model.OWLDataSomeRestriction;
import org.semanticweb.owl.model.OWLDataValueRestriction;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLDescriptionVisitor;
import org.semanticweb.owl.model.OWLObjectAllRestriction;
import org.semanticweb.owl.model.OWLObjectComplementOf;
import org.semanticweb.owl.model.OWLObjectExactCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectIntersectionOf;
import org.semanticweb.owl.model.OWLObjectMaxCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectMinCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectOneOf;
import org.semanticweb.owl.model.OWLObjectSelfRestriction;
import org.semanticweb.owl.model.OWLObjectSomeRestriction;
import org.semanticweb.owl.model.OWLObjectUnionOf;
import org.semanticweb.owl.model.OWLObjectValueRestriction;


public class ClassExpressionVisitor implements OWLDescriptionVisitor {

	String res = ""; 
	
	Hashtable<OWLClass, IClass> class_owl_dafoe;
	
	//
	
	public ClassExpressionVisitor(Hashtable<OWLClass, IClass> class_owl_dafoe){
		this.class_owl_dafoe = class_owl_dafoe;
	}

	public String getResult(){
		return res;
	}
	
	@Override
	public void visit(OWLClass desc) {		
		IClass dafoeClass = this.class_owl_dafoe.get(desc);		
		res += dafoeClass.getId();
	}

	@Override
	public void visit(OWLObjectIntersectionOf desc) {
		res += "AND(";
		
		Set<OWLDescription> operands = desc.getOperands();
		
		int i = 0;
		for(OWLDescription op: operands){
			i++;
			op.accept(this);
			if (i < operands.size()) {
				res += " ";
			}
		}

		res += ")";
	}

	@Override
	public void visit(OWLObjectUnionOf desc) {
		res += "OR(";

		Set<OWLDescription> operands = desc.getOperands();
		
		int i = 0;
		for(OWLDescription op: operands){
			i++;
			op.accept(this);
			if (i < operands.size()) {
				res += " ";
			}
		}
		
		res += ")";
	}

	@Override
	public void visit(OWLObjectComplementOf desc) {
		res += "NOT(";

		desc.getOperand().accept(this);
		
		res += ")";
	}

	@Override
	public void visit(OWLObjectSomeRestriction desc) {

	}

	@Override
	public void visit(OWLObjectAllRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLObjectValueRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLObjectMinCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLObjectExactCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLObjectMaxCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLObjectSelfRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLObjectOneOf desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLDataSomeRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLDataAllRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLDataValueRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLDataMinCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLDataExactCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWLDataMaxCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

}
