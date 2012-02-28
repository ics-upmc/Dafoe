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
package org.dafoe.application.presentations;

import org.eclipse.swt.graphics.Color;

public class MyDefaultTabFolderColors {
	 public Color foreground;
	    public int[] percentages;
	    public Color[] background;
	    public boolean vertical;
	    
	    public MyDefaultTabFolderColors() {
	        
	    }
	    
	    public MyDefaultTabFolderColors(Color fgColor, Color[] bgColors,
	            int[] percentages, boolean vertical) {
	        
	        foreground = fgColor;
	        background = bgColors;
	        this.percentages = percentages;
	        this.vertical = vertical;
	    }
	    
	    public MyDefaultTabFolderColors setForeground(Color fg) {
	        foreground = fg;
	        return this;
	    }
	    
	    public MyDefaultTabFolderColors setBackground(Color[] background, int[] percentages, boolean vertical) {
	        this.background = background;
	        this.percentages = percentages;
	        this.vertical = vertical;
	        return this;
	    }
}
