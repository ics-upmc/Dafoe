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

package org.dafoe.terminoontologiclevel.ui;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.hibernate.Session;
import org.osgi.framework.BundleContext;

/**
 * DAFOE Platform project
 * CRITT INFORMATIQUE in collaboration with LISI/ENSMA
 * Teleport 2 - 1 avenue Clement Ader
 * BP 40109 - 86961 Futuroscope Chasseneuil Cedex
 *
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.dafoe.terminoontologiclevel.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	public Session dafoeSession;
		
	public static final String TERMINO_CONCEPT_ID = "TERMINO_CONCEPT"; //$NON-NLS-1$
	public static final String RTC_TYPE_ID = "RTC_TYPE_ID"; //$NON-NLS-1$
    public static final String CONCEPTUALIZED_IMG_ID = "CONCEPTUALIZED"; //$NON-NLS-1$
    public static final String NEW_IMG_ID = "NEW"; //$NON-NLS-1$
    public static final String REMOVE_IMG_ID = "REMOVE_IMG"; //$NON-NLS-1$
    public static final String VALIDATED_IMG_ID = "VALIDATED_IMG"; //$NON-NLS-1$
    public static final String STUDIED_IMG_ID = "STUDIED_IMG"; //$NON-NLS-1$
    public static final String REJECTED_IMG_ID = "REJECTED_IMG"; //$NON-NLS-1$

    public static final String CLASS_IMG_ID = "CLASS_IMG_ID"; //$NON-NLS-1$
    public static final String DEFINED_CLASS_IMG_ID = "DEFINED_CLASS_IMG_ID"; //$NON-NLS-1$
	public static final String NEW_CLASS_ID = "NEW_CLASS_ID"; //$NON-NLS-1$
    public static final String OBJECT_PROPERTY_IMG_ID = "OBJECT_PROPERTY_IMG_ID"; //$NON-NLS-1$
    public static final String DATATYPE_PROPERTY_IMG_ID = "DATATYPE_PROPERTY_IMG_ID"; //$NON-NLS-1$
    public static final String INSTANCE_IMG_ID = "INSTANCE_IMG_ID"; //$NON-NLS-1$
    
    
    public static final String STAR_IMG_ID = "STAR_IMG"; //$NON-NLS-1$
    public static final String FR_FLAG_IMG_ID = "FR_FLAG_IMG"; //$NON-NLS-1$
    public static final String US_FLAG_IMG_ID = "US_FLAG_IMG"; //$NON-NLS-1$
    public static final String SP_FLAG_IMG_ID = "SP_FLAG_IMG"; //$NON-NLS-1$

    public static final String DIALOG_IMG_ID = "DIALOG_IMG"; //$NON-NLS-1$
	
    
    public static final String ADD_CHILD_ID = "ADD_CHILD_ID"; //$NON-NLS-1$
	public static final String ADD_SIBLING_ID = "ADD_SIBLING_ID"; //$NON-NLS-1$
	public static final String EDIT_ID = "EDIT_ID"; //$NON-NLS-1$
	public static final String DELETE_ID = "DELETE_ID"; //$NON-NLS-1$

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
				
		try {

			dafoeSession = SessionFactoryImpl.getCurrentDynamicSession();
		
		} catch (Exception excep) {
			
		}
		
		
		
		this.getImageRegistry().put(TERMINO_CONCEPT_ID,	Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/termino_concept.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(RTC_TYPE_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/rtc_type.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(CONCEPTUALIZED_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/conceptualized.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(NEW_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/new.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(REMOVE_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/suppress.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(VALIDATED_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/validated.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(STUDIED_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/studied.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(REJECTED_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/rejected.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(FR_FLAG_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/FRA.gif")); //$NON-NLS-1$

		this.getImageRegistry().put(CLASS_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/classe_16.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(DEFINED_CLASS_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/defined_classe_16.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(NEW_CLASS_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/new_class.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(OBJECT_PROPERTY_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/object_property_16.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(DATATYPE_PROPERTY_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/data_property_16.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(INSTANCE_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/instance_16.gif")); //$NON-NLS-1$
		
		
		this.getImageRegistry().put(STAR_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/star.gif")); //$NON-NLS-1$
		
		this.getImageRegistry().put(US_FLAG_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/USA.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(SP_FLAG_IMG_ID, Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,"icones/SPAIN.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(DIALOG_IMG_ID,  Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/fourmi.gif")); //$NON-NLS-1$
		
		
		
		this.getImageRegistry().put(ADD_CHILD_ID,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/add_child.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(ADD_SIBLING_ID,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/add_sibling.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(EDIT_ID,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/edit.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(DELETE_ID,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/suppress.gif")); //$NON-NLS-1$
		
		
		// Default termino-ontology loading
		
		ITerminoOntology terminoOnto = DatabaseAdapter.getDefaultTerminoOntology();
		
		ControlerFactoryImpl.getTerminoOntoControler().setCurrentTerminoOntology(terminoOnto);

		// Default ontology loading
		
		if (ControlerFactoryImpl.getOntoControler().getCurrentOntology() == null) {
			
			IOntology onto = org.dafoe.ontologiclevel.common.DatabaseAdapter.loadDefaultOntology();
			
			ControlerFactoryImpl.getOntoControler().setCurrentOntology(onto);
			
		}
		

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
