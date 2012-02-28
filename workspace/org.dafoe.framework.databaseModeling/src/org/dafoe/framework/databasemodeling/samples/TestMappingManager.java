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
package org.dafoe.framework.databasemodeling.samples;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dafoe.framework.databasemodeling.MappingManager;


/**
 * 
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TestMappingManager {

	
	@SuppressWarnings("unchecked")
	private static void testGetMapping(){
		Set props = MappingManager.getMappinBasicDatatypeToSqlType().entrySet();
		Iterator iter = props.iterator();
		
		//Iterator<Entry<IProperty, Object>> iter= props.iterator();
		
		while(iter.hasNext()){
			Map.Entry me = (Map.Entry)iter.next();
			
			System.out.println("b= "+me.getKey()+" | sql="+me.getValue());			
			
			
		}
		
	}
	public static void main(String[] args) {
		System.out.println(MappingManager.getBasicDataTypeArray().length+"||"+MappingManager.getSqlTypeArray().length);
		
		testGetMapping();
		

	}

}
