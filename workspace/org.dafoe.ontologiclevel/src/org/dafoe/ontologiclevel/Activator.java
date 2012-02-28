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


import java.io.IOException;
import java.net.URL;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.model.IControlerOntology;
import org.dafoe.controler.model.IControlerTerminoOntology;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.dafoe.ontologiclevel"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	public static final String CLASSES_ID = "CLASSES"; //$NON-NLS-1$
	public static final String DEFINED_CLASSES_ID = "DEFINED_CLASSES"; //$NON-NLS-1$
	
	public static final String DATA_PROPERTIES_ID = "DATA_PROPERTIES"; //$NON-NLS-1$
	public static final String PROPERTY_ID = "PROPERTY"; //$NON-NLS-1$
	
	public static final String OBJECT_PROPERTIES_ID = "OBJECT_PROPERTIES_ID"; //$NON-NLS-1$
	public static final String NEW_CLASS_ID = "NEW_CLASS_ID"; //$NON-NLS-1$
	
	public static final String TERMINO_CONCEPT_ID = "TERMINO_CONCEPT_ID"; //$NON-NLS-1$
	public static final String TERMINO_CONCEPT_RELATION_ID = "TERMINO_CONCEPT_RELATION_ID"; //$NON-NLS-1$
	public static final String TERMINO_CONCEPT_RELATION_TYPE_ID = "TERMINO_CONCEPT_RELATION_TYPE_ID"; //$NON-NLS-1$
	
	public static String icone_classe_path=""; //$NON-NLS-1$
	public static String icone_defined_classe_path=""; //$NON-NLS-1$
	public static String icone_data_property_path=""; //$NON-NLS-1$
	public static String icone_object_property_path=""; //$NON-NLS-1$
	public static String icone_instance_path=""; //$NON-NLS-1$
	
	public static final String ADD_CHILD_ID = "ADD_CHILD_ID"; //$NON-NLS-1$
	public static final String ADD_SIBLING_ID = "ADD_SIBLING_ID"; //$NON-NLS-1$
	public static final String EDIT_ID = "EDIT_ID"; //$NON-NLS-1$
	public static final String DELETE_ID = "DELETE_ID"; //$NON-NLS-1$
	
	public static IControlerOntology ontoControler = ControlerFactoryImpl.getOntoControler();
	
	public static IControlerTerminoOntology terminoControleur = ControlerFactoryImpl.getTerminoOntoControler();
	
	
	
	public static void setTerminoConcept(ITerminoOntoObject termi) {
		if (termi instanceof ITerminoConcept){
			terminoControleur.setCurrentTerminoConcept((ITerminoConcept) termi);
		} 
	}
	
	//public static IClass currentClasse= null;
	public static IClass getCurrentClass() {
		return ontoControler.getCurrentClass();
	}
	
	public static void setCurrentClass(IClass cl) {
		ontoControler.setCurrentClass(cl);
	}
	
	public static IOntology getCurrentOntology() {
		return ontoControler.getCurrentOntology();
	}
	
	public static void setCurrentOntology(IOntology onto) {
		ontoControler.setCurrentOntology(onto);
	}
	
	public static void changeOntology() {
		ontoControler.changeOntology();
	}
	
	//public static IProperty currentProperty= null;
	
	public static IProperty getCurrentProperty() {
		return ontoControler.getCurrentProperty();
	}
	
	public static void setCurrentProperty(IProperty pr) {
		ontoControler.setCurrentProperty(pr);
	}
	
	
	
	//public ISessionDafoe dafoeSession;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		//dafoeSession= SessionFactoryImpl.getSessionDafoe();
		 
		 icone_classe_path = org.dafoe.ressources.Activator.getDefault().getBundle().getLocation().replace("initial@reference:file:", "")+"icones/classe_16.gif"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		 icone_defined_classe_path = org.dafoe.ressources.Activator.getDefault().getBundle().getLocation().replace("initial@reference:file:", "")+"icones/defined_classe_16.gif"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		 icone_data_property_path = org.dafoe.ressources.Activator.getDefault().getBundle().getLocation().replace("initial@reference:file:", "")+"icones/data_property_16.gif"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		 icone_object_property_path = org.dafoe.ressources.Activator.getDefault().getBundle().getLocation().replace("initial@reference:file:", "")+"icones/object_property_16.gif"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		 icone_instance_path = org.dafoe.ressources.Activator.getDefault().getBundle().getLocation().replace("initial@reference:file:", "")+"icones/object_instance_16.gif"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		 
		 URL [] urls = FileLocator.findEntries(org.dafoe.ressources.Activator.getDefault().getBundle(),new Path("icones/classe_16.gif") ); //$NON-NLS-1$
			
			try {
				URL u = FileLocator.toFileURL(urls[0]);
				icone_classe_path=u.toExternalForm();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			urls = FileLocator.findEntries(org.dafoe.ressources.Activator.getDefault().getBundle(),new Path("icones/defined_classe_16.gif") ); //$NON-NLS-1$
			try {
				URL u = FileLocator.toFileURL(urls[0]);
				icone_defined_classe_path=u.toExternalForm();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			urls = FileLocator.findEntries(org.dafoe.ressources.Activator.getDefault().getBundle(),new Path("icones/data_property_16.gif") ); //$NON-NLS-1$
			try {
				URL u = FileLocator.toFileURL(urls[0]);
				icone_data_property_path=u.toExternalForm();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			urls = FileLocator.findEntries(org.dafoe.ressources.Activator.getDefault().getBundle(),new Path("icones/object_property_16.gif") ); //$NON-NLS-1$
			try {
				URL u = FileLocator.toFileURL(urls[0]);
				icone_object_property_path=u.toExternalForm();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			urls = FileLocator.findEntries(org.dafoe.ressources.Activator.getDefault().getBundle(),new Path("icones/instance_16.gif") ); //$NON-NLS-1$
			try {
				URL u = FileLocator.toFileURL(urls[0]);
				icone_instance_path=u.toExternalForm();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		 
		
		 
		
			this.getImageRegistry().put(CLASSES_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/classe_16.gif")); //$NON-NLS-1$
			this.getImageRegistry().put(DEFINED_CLASSES_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/defined_classe_16.gif")); //$NON-NLS-1$
			this.getImageRegistry().put(DATA_PROPERTIES_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/data_property_16.gif")); //$NON-NLS-1$
			this.getImageRegistry().put(PROPERTY_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/property_16.gif")); //$NON-NLS-1$
			this.getImageRegistry().put(OBJECT_PROPERTIES_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/object_property_16.gif")); //$NON-NLS-1$
			this.getImageRegistry().put(NEW_CLASS_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/new_class.gif")); //$NON-NLS-1$
			this.getImageRegistry().put(TERMINO_CONCEPT_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/termino_concept.gif")); //$NON-NLS-1$
			this.getImageRegistry().put(TERMINO_CONCEPT_RELATION_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/rtc.gif")); //$NON-NLS-1$
			this.getImageRegistry().put(TERMINO_CONCEPT_RELATION_TYPE_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/rtc_type.gif")); //$NON-NLS-1$
				
			this.getImageRegistry().put(ADD_CHILD_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/add_child.gif")); //$NON-NLS-1$
			this.getImageRegistry().put(ADD_SIBLING_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/add_sibling.gif")); //$NON-NLS-1$
				
			this.getImageRegistry().put(DELETE_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/suppress.gif")); //$NON-NLS-1$
			
			this.getImageRegistry().put(EDIT_ID,
					Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/edit.gif")); //$NON-NLS-1$
		
			
		// Default ontology loading
		
		IOntology onto = org.dafoe.ontologiclevel.common.DatabaseAdapter.loadDefaultOntology();
		
		setCurrentOntology(onto);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
