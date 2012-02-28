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

package org.dafoe.ontologiclevel;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;

public class TreePropertyLabelProvider extends BaseLabelProvider implements
ITableLabelProvider {

	

	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex==0) {
			if (element instanceof IDatatypeProperty) {
				return Activator.getDefault().getImageRegistry().get(Activator.DATA_PROPERTIES_ID);
			} else {
				if (element instanceof IObjectProperty) {
					return Activator.getDefault().getImageRegistry().get(Activator.OBJECT_PROPERTIES_ID);
				}
			}
		}
		
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		if (element instanceof IDatatypeProperty) {
			switch (columnIndex) {
				case 0:
					return ((IDatatypeProperty) element).getLabel();
				case 1:
					return ((IDatatypeProperty) element).getRange().getLabel();
				default:
					return "";
			}
			
			
		} else {
			if (element instanceof IObjectProperty) {
				switch (columnIndex) {
				case 0:
					return ((IObjectProperty) element).getLabel();
				case 1:
					//ES
					if (((IObjectProperty) element).getRange() != null) {
						return ((IObjectProperty) element).getRange().getLabel();
					};
					return "";
				default:
					return "";
				}
			}
				
			
		}
		
		
		
		
		return null;
	}

}
