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

package org.dafoe.terminologiclevel;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.hibernate.Session;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.dafoe.terminologiclevel"; //$NON-NLS-1$

	public static final String VALIDATED_IMG_ID = "VALIDATED"; //$NON-NLS-1$
	public static final String REJECTED_IMG_ID = "REJECTED"; //$NON-NLS-1$
	public static final String STUDIED_IMG_ID = "STUDIED"; //$NON-NLS-1$
	public static final String CONCEPTUALIZED_IMG_ID = "CONCEPTUALIZED"; //$NON-NLS-1$
	public static final String DELETED_IMG_ID = "DELETED"; //$NON-NLS-1$
	public static final String DESTROY_IMG_ID = "DESTROY"; //$NON-NLS-1$
	public static final String NEW_IMG_ID = "NEW"; //$NON-NLS-1$
	public static final String NEW_RT_TYPE_IMG_ID = "NEW_RT_TYPE"; //$NON-NLS-1$
	public static final String ADD_VARIANT_IMG_ID = "ADD_VARIANT"; //$NON-NLS-1$
	public static final String SUPP_VARIANT_IMG_ID = "SUPP_VARIANT"; //$NON-NLS-1$
	public static final String CONFIGURE_INFORMATION_IMG_ID = "CONFIGURE_INFO"; //$NON-NLS-1$

	public static final String REMOVE_RELATION_IMG_ID = "REMOVE_RELATION_RT"; //$NON-NLS-1$

	public static final String IMPORT_IMG_ID = "IMPORT"; //$NON-NLS-1$
	public static final String EXPORT_IMG_ID = "EXPORT"; //$NON-NLS-1$
	public static final String ANTI_DICTIONARY_ENABLE_IMG_ID = "ANTI_DICTIONARY_ENABLE"; //$NON-NLS-1$
	public static final String ANTI_DICTIONARY_DISABLE_IMG_ID = "ANTI_DICTIONARY_DISABLE"; //$NON-NLS-1$
	public static final String GROUP_TERMS_IMG_ID = "GROUP_TERMS"; //$NON-NLS-1$
	public static final String UNGROUP_TERMS_IMG_ID = "UNGROUP_TERMS"; //$NON-NLS-1$
	public static final String STAR_TERM_IMG_ID = "STAR_TERM"; //$NON-NLS-1$

	public static final String RELATION_TYPE_IMG_ID = "RELATION_TYPE"; //$NON-NLS-1$
	public static final String METHOD_IMG_ID = "METHOD"; //$NON-NLS-1$
	public static final String LINK_IMG_ID = "LINK"; //$NON-NLS-1$
	public static final String UNLINK_IMG_ID = "UNLINK"; //$NON-NLS-1$
	public static final String TERMINO_CONTEPT_IMG_ID = "TC"; //$NON-NLS-1$


	public static final String DIALOG_IMG = "DIALOG_IMG"; //$NON-NLS-1$

	public Session dafoeSession;

	// The shared instance
	private static Activator plugin;

	public static int CURRENT_PERSPECTIVE = 0;


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
		
		//configureLogger();
		
		
		
		try{
			//dafoeSession = SessionFactoryImpl.openSession();
			//VT: load a dynamic session per database
			dafoeSession = SessionFactoryImpl.getCurrentDynamicSession();


		} catch (Exception e){
			e.printStackTrace();
		}		

		ImageRegistry imgReg = this.getImageRegistry();

		imgReg.put(VALIDATED_IMG_ID, Activator
				.getImageDescriptor("icons/validated.gif")); //$NON-NLS-1$
		imgReg.put(REJECTED_IMG_ID, Activator
				.getImageDescriptor("icons/rejected.gif")); //$NON-NLS-1$
		imgReg.put(STUDIED_IMG_ID, Activator
				.getImageDescriptor("icons/studied.gif")); //$NON-NLS-1$
		imgReg.put(CONCEPTUALIZED_IMG_ID, Activator
				.getImageDescriptor("icons/conceptualized.gif")); //$NON-NLS-1$
		imgReg.put(DELETED_IMG_ID, Activator
				.getImageDescriptor("icons/delete.gif")); //$NON-NLS-1$
		imgReg.put(DESTROY_IMG_ID, Activator
				.getImageDescriptor("icons/destroy.gif")); //$NON-NLS-1$
		imgReg.put(NEW_IMG_ID, Activator
				.getImageDescriptor("icons/new.gif")); //$NON-NLS-1$
		imgReg.put(NEW_RT_TYPE_IMG_ID, Activator
				.getImageDescriptor("icons/newRTType.gif")); //$NON-NLS-1$
		imgReg.put(SUPP_VARIANT_IMG_ID, Activator
				.getImageDescriptor("icons/suppress.gif")); //$NON-NLS-1$
		imgReg.put(CONFIGURE_INFORMATION_IMG_ID, Activator
				.getImageDescriptor("icons/configure.gif")); //$NON-NLS-1$
		imgReg.put(REMOVE_RELATION_IMG_ID, Activator
				.getImageDescriptor("icons/suppress.gif")); //$NON-NLS-1$
		imgReg.put(IMPORT_IMG_ID, Activator
				.getImageDescriptor("icons/import.gif")); //$NON-NLS-1$
		imgReg.put(EXPORT_IMG_ID, Activator
				.getImageDescriptor("icons/export.gif")); //$NON-NLS-1$
		imgReg.put(ANTI_DICTIONARY_ENABLE_IMG_ID, Activator
				.getImageDescriptor("icons/antiDictionaryEnable.gif")); //$NON-NLS-1$
		imgReg.put(ANTI_DICTIONARY_DISABLE_IMG_ID, Activator
				.getImageDescriptor("icons/antiDictionaryDisable.gif")); //$NON-NLS-1$
		imgReg.put(GROUP_TERMS_IMG_ID, Activator
				.getImageDescriptor("icons/group.gif")); //$NON-NLS-1$
		imgReg.put(UNGROUP_TERMS_IMG_ID, Activator
				.getImageDescriptor("icons/ungroup.gif")); //$NON-NLS-1$
		imgReg.put(STAR_TERM_IMG_ID, Activator
				.getImageDescriptor("icons/star.gif")); //$NON-NLS-1$
		imgReg.put(RELATION_TYPE_IMG_ID,Activator
				.getImageDescriptor("icons/relationType.gif")); //$NON-NLS-1$
		imgReg.put(METHOD_IMG_ID,Activator
				.getImageDescriptor("icons/method.gif")); //$NON-NLS-1$
		imgReg.put(LINK_IMG_ID,Activator
				.getImageDescriptor("icons/new.gif")); //$NON-NLS-1$
		imgReg.put(UNLINK_IMG_ID,Activator
				.getImageDescriptor("icons/unlink.gif")); //$NON-NLS-1$
		imgReg.put(TERMINO_CONTEPT_IMG_ID,Activator
				.getImageDescriptor("icons/tc.gif")); //$NON-NLS-1$
		imgReg.put(DIALOG_IMG,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/fourmi.gif")); //$NON-NLS-1$

		// Default terminology initialization
		
		DatabaseAdapter.initTerminology();
				
		// Default termino-ontology loading
		
		ITerminoOntology terminoOnto = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.getDefaultTerminoOntology();

		ControlerFactoryImpl.getTerminoOntoControler().setCurrentTerminoOntology(terminoOnto);

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
	
	/*
	 * 
	 */
	
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/*
	 * 
	 */
	
	public static File getPluginFolder() {
		File _pluginFolder = null;
		if (_pluginFolder == null) {
			URL url = Platform.getBundle(PLUGIN_ID).getEntry("/"); //$NON-NLS-1$
			try {
				// url = Platform..resolve(url);
				url = Platform.asLocalURL(url);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			_pluginFolder = new File(url.getPath());
		}

		return _pluginFolder;
	}
	
}
