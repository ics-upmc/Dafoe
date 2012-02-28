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

import java.net.URI;

import org.semanticweb.owl.model.OWLAnnotationVisitor;
import org.semanticweb.owl.model.OWLConstantAnnotation;
import org.semanticweb.owl.model.OWLObjectAnnotation;

/* 
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * <p>Simple visitor that grabs any labels on an entity.</p>
 * 
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 17-03-2007<br>
 * <br>
 */
public class AnnotationExtractor implements OWLAnnotationVisitor {

    private URI annotationType;
    private String annotationValue;

    //
    
    public AnnotationExtractor() {
    }

    //
    
    public void visit(OWLConstantAnnotation annotation) {
    	
    	annotationType = annotation.getAnnotationURI();
    	
    	annotationValue = annotation.getAnnotationValue().getLiteral();
    	
    }

    //
    
    public void visit(OWLObjectAnnotation annotation) {
    }

    //
    
    public URI getAnnotationType() {
        return annotationType;
    }
    
    //
    
    public String getAnnotationValue() {
        return annotationValue;
    }
}
