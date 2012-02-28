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

package org.dafoe.welcomelevel;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.dafoe.welcomelevel";

	// The shared instance
	private static Activator plugin;

	public static final String fond_id = "fond_id";
	public static final String corpus_up = "corpus_up";
	public static final String corpus_hover = "corpus_hover";
	public static final String corpus_down = "corpus_down";
	public static final String onto_up = "onto_up";
	public static final String onto_hover = "onto_hover";
	public static final String onto_down = "onto_down";
	public static final String termonto_up = "termonto_up";
	public static final String termonto_hover = "termonto_hover";
	public static final String termonto_down = "termonto_down";
	public static final String termino_up = "termino_up";
	public static final String termino_hover = "termino_hover";
	public static final String termino_down = "termino_down";

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		ImageDescriptor des = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID, "icones/fond.gif");
		des.createImage();
		this.getImageRegistry().put(fond_id, des);

		ImageDescriptor des1 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/corpus_up.gif");
		des1.createImage();
		this.getImageRegistry().put(corpus_up, des1);

		ImageDescriptor des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/corpus_down.gif");
		des2.createImage();
		this.getImageRegistry().put(corpus_down, des2);

		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/corpus_hover.gif");
		des2.createImage();
		this.getImageRegistry().put(corpus_hover, des2);

		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID, "icones/onto_up.gif");
		des2.createImage();
		this.getImageRegistry().put(onto_up, des2);
		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/onto_hover.gif");
		des2.createImage();
		this.getImageRegistry().put(onto_hover, des2);
		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/onto_down.gif");
		des2.createImage();
		this.getImageRegistry().put(onto_down, des2);

		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/termonto_up.gif");
		des2.createImage();
		this.getImageRegistry().put(termonto_up, des2);
		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/termonto_hover.gif");
		des2.createImage();
		this.getImageRegistry().put(termonto_hover, des2);
		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/termonto_down.gif");
		des2.createImage();
		this.getImageRegistry().put(termonto_down, des2);

		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/termino_up.gif");
		des2.createImage();
		this.getImageRegistry().put(termino_up, des2);
		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/termino_hover.gif");
		des2.createImage();
		this.getImageRegistry().put(termino_hover, des2);
		des2 = Activator.imageDescriptorFromPlugin(
				org.dafoe.ressources.Activator.PLUGIN_ID,
				"icones/termino_down.gif");
		des2.createImage();
		this.getImageRegistry().put(termino_down, des2);

		/*
		 * this.getImageRegistry().put(corpus_up,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID, "icones/corpus_up.gif"));
		 * this.getImageRegistry().put(corpus_down,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID,
		 * "icones/corpus_down.gif")); this.getImageRegistry().put(corpus_hover,
		 * Activator
		 * .imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID,
		 * "icones/corpus_hover.gif"));
		 * 
		 * this.getImageRegistry().put(onto_up,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID, "icones/onto_up.gif"));
		 * this.getImageRegistry().put(onto_hover,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID, "icones/onto_hover.gif"));
		 * this.getImageRegistry().put(onto_down,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID, "icones/onto_down.gif"));
		 * 
		 * this.getImageRegistry().put(termonto_up,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID,
		 * "icones/termonto_up.gif"));
		 * this.getImageRegistry().put(termonto_hover,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID,
		 * "icones/termonto_hover.gif"));
		 * this.getImageRegistry().put(termonto_down,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID,
		 * "icones/termonto_down.gif"));
		 * 
		 * this.getImageRegistry().put(termino_up,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID, "icones/termino_up.gif"));
		 * this.getImageRegistry().put(termino_hover,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID,
		 * "icones/termino_hover.gif"));
		 * this.getImageRegistry().put(termino_down,
		 * Activator.imageDescriptorFromPlugin
		 * (org.dafoe.ressources.Activator.PLUGIN_ID,
		 * "icones/termino_down.gif"));
		 */
	}

	@Override
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
}
