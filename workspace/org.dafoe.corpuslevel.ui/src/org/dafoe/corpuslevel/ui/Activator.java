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
package org.dafoe.corpuslevel.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = Messages.getString("Activator.0"); //$NON-NLS-1$
	public static final String ALL_TERMS = "ALL_TERMS"; //$NON-NLS-1$
	public static final String DOC_TERMS = "DOC_TERMS"; //$NON-NLS-1$
	
	public static final String ALL_SENTENCES = "ALL_SENTENCES"; //$NON-NLS-1$
	public static final String ALIGN_SENTENCES = "ALIGN_SENTENCES"; //$NON-NLS-1$
	public static final String SEQ_SENTENCES = "SEQ_SENTENCES"; //$NON-NLS-1$
	public static final String REFRESH_IMG_ID = "REFRESH";
	
	
	// The shared instance
	private static Activator plugin;
	
	
	
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
		try{
		
		}catch(Exception excep) {
			
		}
		
		this.getImageRegistry().put(ALL_TERMS,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/allterms.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(DOC_TERMS,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/docsterms.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(ALL_SENTENCES,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/allsent16.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(ALIGN_SENTENCES,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/alignsent16.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(SEQ_SENTENCES,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/seqsent16.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(REFRESH_IMG_ID,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/refresh.gif")); //$NON-NLS-1$
	
		
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
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
