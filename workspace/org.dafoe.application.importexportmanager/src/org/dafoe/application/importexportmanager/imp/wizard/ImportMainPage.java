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
package org.dafoe.application.importexportmanager.imp.wizard;

import java.util.ArrayList;
import java.util.List;

import org.dafoe.application.importexportmanager.Activator;
import org.dafoe.application.importexportmanager.adaptater.Services;
import org.dafoe.application.importexportmanager.controler.ControlerFactory;

import org.dafoe.application.importexportmanager.imp.model.PluginComponentImp;

import org.dafoe.application.importexportmanager.imp.model.PluginLevelImp;
import org.dafoe.application.importexportmanager.model.ImportExportComponent;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * The Class ImportMainPage.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class ImportMainPage extends WizardPage implements Listener{

	protected TreeViewer importPluginTreeViewer;

	private Text txtFilter;

	private Label lbFilter;

	public ImportMainPage() {
		super("main"); //$NON-NLS-1$
		this.setTitle("Select");
		this.setDescription("Export new resources from plugin type.");
	}

	public void createControl(Composite parent) {
		Composite body = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		body.setLayout(layout);

		// Composite body = comp;

		Composite comp0 = new Composite(body, SWT.NONE);
		GridData GDCOMP = new GridData(GridData.FILL_HORIZONTAL);
		comp0.setLayoutData(GDCOMP);
		GridLayout GL2 = new GridLayout(3, false);
		comp0.setLayout(GL2);

		lbFilter = new Label(comp0, SWT.NONE);
		lbFilter.setText("Filter");

		txtFilter = new Text(comp0, SWT.SINGLE);
		GridData GDText = new GridData(GridData.FILL_HORIZONTAL);
		GDText.horizontalSpan = 2;
		txtFilter.setLayoutData(GDText);

		importPluginTreeViewer = new TreeViewer(body, SWT.LINE_SOLID
				| SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		importPluginTreeViewer.getTree().setLayoutData(
				new GridData(GridData.FILL_BOTH));
		importPluginTreeViewer
				.setContentProvider(new ImportPluginContentProvider());
		importPluginTreeViewer.setInput(Services
				.getAllImportContributors());
		importPluginTreeViewer
				.setLabelProvider(new ImportPluginLabelProvider());

		importPluginTreeViewer.getTree().addListener(SWT.Selection, this);

		// set the composite as the control for this page
		setControl(body);

	}

	@Override
	public boolean isPageComplete() {
		return true;
	}

	public void handleEvent(Event event) {
		if (event.widget == importPluginTreeViewer.getTree()) {
			//System.out.println("ImportMainPage.handleEvent()"); //$NON-NLS-1$

			saveDataToModel();
		}
	}

	public IWizardPage getNextPage() {
						
		//System.out.println("coucou");
		
		BlanckPage targetPage = ((ImportManagementWizard) getWizard()).targetPage;
		
		targetPage.initPage();
		return targetPage;
	}


	public boolean canFlipToNextPage() {
		ImportManagementWizard wizard = (ImportManagementWizard) getWizard();
		return wizard.refModel.isValid();

	}

	private void saveDataToModel() {
		ImportManagementWizard wizard = (ImportManagementWizard) getWizard();

		System.out.println("ImportMainPage.saveDataToModel()"); //$NON-NLS-1$

		ISelection selection = importPluginTreeViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSel = (IStructuredSelection) selection;
			if (!sSel.isEmpty()) {
				java.lang.Object selo = sSel.getFirstElement();
				if (selo instanceof PluginComponentImp) {
					System.out.println("plugin"); //$NON-NLS-1$
					ControlerFactory.getControler()
							.setCurrentSelectedPluginComponent(
									(PluginComponentImp) selo);
					//
					wizard.refModel.setSelectedPlugin((PluginComponentImp) selo);

				}
				if (selo instanceof PluginLevelImp) {
					System.out.println("plugin level"); //$NON-NLS-1$
					ControlerFactory.getControler()
							.setCurrentSelectedPluginComponent(
									(PluginLevelImp) selo);
					wizard.refModel.setSelectedPlugin((PluginLevelImp) selo);

				}

				// disable/enable the NEXT button
				setPageComplete(wizard.refModel.isValid());
			}
		}

	}

	class ImportPluginLabelProvider implements ILabelProvider {

		@Override
		public Image getImage(Object element) {
			if (element instanceof PluginLevelImp) {
				return Activator.getDefault().getImageRegistry().get(
						Activator.DIRECTORY_IMG_ID);
			}
			return null;
		}

		@Override
		public String getText(Object element) {
			String text = ""; //$NON-NLS-1$
			if (element instanceof PluginComponentImp) {// receive a plugin
				PluginComponentImp plugin = (PluginComponentImp) element;
				return plugin.getName();
			}
			if (element instanceof PluginLevelImp) { // receive a plugin level
				PluginLevelImp level = (PluginLevelImp) element;
				return level.getName();
			}
			return text;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}
	}

	class ImportPluginContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof PluginComponentImp) {// receive a plugin
				return new ArrayList<PluginComponentImp>().toArray();
			}
			if (parentElement instanceof PluginLevelImp) {
				PluginLevelImp level = (PluginLevelImp) parentElement;
				return level.getPlugins().toArray();
			}

			return new ArrayList<ImportExportComponent>().toArray();
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {

			if (element instanceof PluginComponentImp) {// receive a plugin
				return false;
			}
			if (element instanceof PluginLevelImp) {
				PluginLevelImp level = (PluginLevelImp) element;
				return (level.getPlugins().size() != 0);
			}

			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return ((List<ImportExportComponent>) inputElement).toArray();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
}
