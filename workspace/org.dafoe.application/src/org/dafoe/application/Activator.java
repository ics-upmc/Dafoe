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
package org.dafoe.application;

import java.util.ArrayList;
import java.util.List;

import org.dafoe.contextlevel.ILevel;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.dafoe.application"; //$NON-NLS-1$

	public static final String DAFOE_ACTIVE_IMG_ID = "DAFOE_ACTIVE_IMG_ID"; //$NON-NLS-1$
	public static final String DAFOE_INACTIVE_IMG_ID = "DAFOE_INACTIVE_IMG_ID"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	public boolean showContext = true;

	// public static Logger log4j = DafoeLogger.getLogger(Activator.PLUGIN_ID);

	/**
	 * The constructor
	 */
	public Activator() {
	}

	static public String DRAPEAU = "drapeau"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		List<String> myCollection = new ArrayList<String>();
		myCollection.add("org.dafoe.welcomelevel.perspectiveId"); //$NON-NLS-1$
		ILevel createLevel = org.dafoe.contextlevel.Activator
				.getDefault()
				.createLevel(
						Messages.getString("Activator.WELCOME_NAME"), "org.dafoe.application.themeId", //$NON-NLS-1$ //$NON-NLS-2$
						myCollection);
		org.dafoe.contextlevel.Activator.getDefault().getContextLevel()
				.addLevel("welcome", createLevel); //$NON-NLS-1$

		myCollection = new ArrayList<String>();
		myCollection.add("org.dafoe.corpuslevel.perspectiveId"); //$NON-NLS-1$
		createLevel = org.dafoe.contextlevel.Activator
				.getDefault()
				.createLevel(
						Messages.getString("Activator.CORPUS_NAME"), "org.dafoe.corpuslevel.themeId", //$NON-NLS-1$ //$NON-NLS-2$
						myCollection);
		org.dafoe.contextlevel.Activator.getDefault().getContextLevel()
				.addLevel("corpus", createLevel); //$NON-NLS-1$

		myCollection = new ArrayList<String>();
		myCollection.add("org.dafoe.terminologiclevel.perspectiveSaillance"); //$NON-NLS-1$
		myCollection.add("org.dafoe.terminologiclevel.perspectivesCard"); //$NON-NLS-1$
		myCollection
				.add("org.dafoe.terminologiclevel.perspectiveMappingRelation"); //$NON-NLS-1$
		myCollection
				.add("org.dafoe.terminologiclevel.perspectiveMappingRelationType"); //$NON-NLS-1$

		createLevel = org.dafoe.contextlevel.Activator
				.getDefault()
				.createLevel(
						Messages.getString("Activator.TERMINOLOGIC_NAME"), "org.dafoe.terminologiclevel.themeId", //$NON-NLS-1$ //$NON-NLS-2$
						myCollection);
		org.dafoe.contextlevel.Activator.getDefault().getContextLevel()
				.addLevel("terminologic", createLevel); //$NON-NLS-1$

		myCollection = new ArrayList<String>();
		//myCollection.add("org.dafoe.terminoontologiclevel.perspectiveId"); //$NON-NLS-1$
		myCollection
				.add("org.dafoe.terminoontologiclevel.ui.perspectiveTerminoConceptId"); //$NON-NLS-1$
		myCollection
				.add("org.dafoe.terminoontologiclevel.ui.perspectiveTerminoConceptRelationId"); //$NON-NLS-1$

		createLevel = org.dafoe.contextlevel.Activator
				.getDefault()
				.createLevel(
						Messages.getString("Activator.TERMINO_ONTOLOGIC_NAME"), "org.dafoe.terminoontologiclevel.themeId", //$NON-NLS-1$ //$NON-NLS-2$
						myCollection);
		org.dafoe.contextlevel.Activator.getDefault().getContextLevel()
				.addLevel("terminoontologic", createLevel); //$NON-NLS-1$

		myCollection = new ArrayList<String>();
		myCollection.add("org.dafoe.ontologiclevel.perspectiveClassesId"); //$NON-NLS-1$
		myCollection.add("org.dafoe.ontologiclevel.perspectivePropertiesId"); //$NON-NLS-1$
		myCollection.add("org.dafoe.ontologiclevel.perspectiveIndividualsId"); //$NON-NLS-1$
		createLevel = org.dafoe.contextlevel.Activator
				.getDefault()
				.createLevel(
						Messages.getString("Activator.ONTOLOGIC"), "org.dafoe.ontologiclevel.themeId", //$NON-NLS-1$ //$NON-NLS-2$
						myCollection);
		org.dafoe.contextlevel.Activator.getDefault().getContextLevel()
				.addLevel("ontologic", createLevel); //$NON-NLS-1$

		// / Gestion des images de l'application
		// ES
		// Bundle bundle =
		// Platform.getBundle(org.dafoe.ressources.Activator.PLUGIN_ID);

		// ES
		// boolean b = BundleUtility.isReady(bundle);
		ImageDescriptor DRAPEAUID = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"images/drapeau/" + Messages.getString("Activator.4")); //$NON-NLS-1$ //$NON-NLS-2$
		DRAPEAUID.createImage();

		this.getImageRegistry().put(DRAPEAU, DRAPEAUID);

		this.getImageRegistry().put(DAFOE_ACTIVE_IMG_ID,
				Activator.getImageDescriptor("icons/dafoeActive.gif")); //$NON-NLS-1$
		this.getImageRegistry().put(DAFOE_INACTIVE_IMG_ID,
				Activator.getImageDescriptor("icons/dafoeInactive.gif")); //$NON-NLS-1$

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
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
