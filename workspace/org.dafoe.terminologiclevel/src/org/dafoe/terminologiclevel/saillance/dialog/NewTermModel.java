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

package org.dafoe.terminologiclevel.saillance.dialog;

import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;

public class NewTermModel {

		private String label;
		private LINGUISTIC_STATUS linguisticStatus;
		
		public NewTermModel(String label, LINGUISTIC_STATUS linguisticStatus){
			this.label = label;
			this.linguisticStatus = linguisticStatus;
		}
		
		public void setLabel(String label){
			this.label = label;
		}
		
		public void setLinguisticStatus(LINGUISTIC_STATUS linguisticStatus){
			this.linguisticStatus = linguisticStatus;
		}
		
		public String getLabel(){
			return this.label;
		}
		
		public LINGUISTIC_STATUS getLinguisticStatus(){
			return this.linguisticStatus;
		}		
		
}
