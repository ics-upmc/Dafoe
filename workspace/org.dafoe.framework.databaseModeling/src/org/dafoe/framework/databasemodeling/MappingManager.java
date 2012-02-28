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
package org.dafoe.framework.databasemodeling;


import java.util.Map;
import java.util.TreeMap;


/**
 * The MappingManager class init correspondance between database datatype and ontology datatatype
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class MappingManager {

	
	private static String[] BASIC_DATA_TYPE_ARRAY = { "short", "unsignedShort",
			"integer", "positiveInteger", "negativeInteger",
			"nonPositiveInteger", "nonNegativeInteger", "int", "unsignedInt",
			"long", "unsignedLong", "decimal", "float", "double", "string",
			"normalizedString", "token", "language", "NMTOKEN", "Name",
			"NCName", "time", "date", "datetime", "gYearMonth", "gMonthDay",
			"gDay", "gMonth", "boolean", "byte", "unsignedByte", "hexBinary",
			"anyURI" };

	private static String[] SQL_TYPE_ARRAY = { "SMALLINT", "SMALLINT", "INTEGER", "INTEGER",
			"INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER",
			"INTEGER", "DECIMAL", "FLOAT", "DOUBLE PRECISION",
			"CHARACTER VARYING", "CHARACTER VARYING", "CHARACTER VARYING",
			"CHARACTER VARYING", "CHARACTER VARYING", "CHARACTER VARYING",
			"CHARACTER VARYING", "TIME", "DATE", "TIMESTAMP", "DATE", "DATE",
			"DATE", "DATE", "BIT", "BIT VARYING", "BIT VARYING",
			"CHARACTER VARYING", "CHARACTER VARYING"};

	// sorted mapping table
	private static Map<String, String> mappingBasicDatatypeToSqlType = new  TreeMap<String, String>();

	//init mapping table
	static {

		for(int i=0; i<BASIC_DATA_TYPE_ARRAY.length;i++ ){
			mappingBasicDatatypeToSqlType.put(BASIC_DATA_TYPE_ARRAY[i],SQL_TYPE_ARRAY[i]);
		}
	}
	
	public static Map<String, String> getMappinBasicDatatypeToSqlType() {
		return mappingBasicDatatypeToSqlType;
	}
	
	public static String[] getBasicDataTypeArray() {
		return BASIC_DATA_TYPE_ARRAY;
	}
	
	public static String[] getSqlTypeArray() {
		return SQL_TYPE_ARRAY;
	}
	
	

   public static String getSqlTypeFromBasicDatatype(String basicDatatype){
	   
	   return mappingBasicDatatypeToSqlType.get(basicDatatype);
   }
	  
	

}
