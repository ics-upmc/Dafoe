package org.dafoe.application.importexportmanager;

import org.dafoe.application.importexportmanager.exp.wizard.ExportManagementWizard;
import org.dafoe.application.importexportmanager.imp.wizard.ImportManagementWizard;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.hibernate.Session;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.dafoe.application.importexportmanager";

	// The shared instance
	private static Activator plugin;
	
	public Session dafoeSession;
	
	private ImportManagementWizard defaultImportManagementWizard;
	
	private ExportManagementWizard defaultExportManagementWizard;
	
	public static final String DIRECTORY_IMG_ID = "DIRECTORY_ID";
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
					
		this.getImageRegistry().put(DIRECTORY_IMG_ID,
				Activator.imageDescriptorFromPlugin(org.dafoe.ressources.Activator.PLUGIN_ID, "icones/prj_obj.gif")); //$NON-NLS-1$
	
		
		
		defaultImportManagementWizard= new ImportManagementWizard();
		
			defaultExportManagementWizard= new ExportManagementWizard();
		
		

		try {
			dafoeSession = SessionFactoryImpl.getCurrentDynamicSession();;
			
			} catch (Exception excep) {
				
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

	public ImportManagementWizard getDefaultImportManagementWizard() {
		return defaultImportManagementWizard;
	}
	
	public ExportManagementWizard getDefaultExportManagementWizard() {
		return defaultExportManagementWizard;
	}
	
}
