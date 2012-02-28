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

package org.dafoe.plugin.imp.owl.adaptater;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IOntoAnnotationType;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.BasicDatatypeImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntoAnnotationTypeImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * The Class Services.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class Services {

	public static Session getDafoeSession() {
		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	public static IOntoAnnotationType getComentType() {
		return (IOntoAnnotationType) getDafoeSession().load(
				OntoAnnotationTypeImpl.class, 2);
	}

	public static IOntoAnnotationType getLabelType() {
		return (IOntoAnnotationType) getDafoeSession().load(
				OntoAnnotationTypeImpl.class, 1);
	}

	public static void saveClass(IOntology onto, IClass clazz,
			ITerminoOntoObject teminoOntoObject) {

		try {

			Transaction dTx = getDafoeSession().beginTransaction();
			onto.addOntoObject(clazz);
			if (teminoOntoObject != null) {
				clazz.addMappedTerminoOntoObject(teminoOntoObject);
			}

			getDafoeSession().save(clazz);
			dTx.commit();
			getDafoeSession().refresh(onto);
			if (teminoOntoObject != null) {
				getDafoeSession().refresh(teminoOntoObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static IBasicDatatype findOrCreateBasicDataType(String label) {
		try {
			List<IBasicDatatype> lestypes = getDafoeSession().createSQLQuery(
					"SELECT m23_basic_datatype.* FROM m23_basic_datatype WHERE label LIKE '"
							+ label + "'ORDER BY id").addEntity(
					"m23_basic_datatype", BasicDatatypeImpl.class).list();

			if (lestypes.size() > 0) {
				return lestypes.get(0);
			}
		} catch (Exception excep) {

		}
		IBasicDatatype res = new BasicDatatypeImpl();
		res.setLabel(label);
		saveBasicdatatype(res);
		return res;

	}

	//

	public static void saveBasicdatatype(IBasicDatatype prop) {
		try {
			Transaction dTx = null;
			dTx = getDafoeSession().beginTransaction();
			dTx.begin();
			getDafoeSession().save(prop);
			dTx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	public static void saveProperty(IOntology onto, IProperty prop,
			ITerminoOntoObject teminoOntoObject) {

		try {

			Transaction dTx = getDafoeSession().beginTransaction();

			onto.addOntoObject(prop);

			if (teminoOntoObject != null) {

				prop.addMappedTerminoOntoObject(teminoOntoObject);

			}

			getDafoeSession().save(prop);

			dTx.commit();

			getDafoeSession().refresh(onto);

			if (teminoOntoObject != null) {

				getDafoeSession().refresh(teminoOntoObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	public static void saveOntology1(IOntology object) {

		try {

			Transaction dTx = getDafoeSession().beginTransaction();

			dTx.begin();

			getDafoeSession().save(object);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	@SuppressWarnings("unchecked")
	public static List<IOntoAnnotationType> getAnnotationsTypes() {
		try {
			return (List<IOntoAnnotationType>) getDafoeSession()
					.createCriteria(IOntoAnnotationType.class).list();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IOntoAnnotationType>();
		}
	}

	//

	public static void saveAnnotationType(IOntoAnnotationType annoType) {

		try {

			Transaction dTx = null;

			dTx = getDafoeSession().beginTransaction();

			dTx.begin();

			getDafoeSession().save(annoType);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static  List<File> getAllFiles(File directory){
		List<File> files= new ArrayList<File>();
		
		if(directory.isDirectory()){
			for (File f: directory.listFiles()){
				if(f.isFile()){
					files.add(f);
				}
			}
		}
		
		//System.out.println("size files= "+files.size()); //$NON-NLS-1$
		return files;
	}
}
