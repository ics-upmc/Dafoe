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
package org.dafoe.application.presentations;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.presentations.IStackPresentationSite;
import org.eclipse.ui.presentations.StackPresentation;
import org.eclipse.ui.presentations.WorkbenchPresentationFactory;

public class DafoePresentationFactory extends WorkbenchPresentationFactory {

	private static int viewTabPosition = PlatformUI.getPreferenceStore().getInt(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION);
	
	@Override
	public StackPresentation createEditorPresentation(Composite parent,
			IStackPresentationSite site) {
		/*
		DefaultTabFolder folder = new DefaultTabFolder(parent,viewTabPosition
				| SWT.BORDER, site
				.supportsState(IStackPresentationSite.STATE_MINIMIZED), site
				.supportsState(IStackPresentationSite.STATE_MAXIMIZED));
		
		
		final IPreferenceStore store = PlatformUI.getPreferenceStore();
		final int minimumCharacters = store
				.getInt(IWorkbenchPreferenceConstants.VIEW_MINIMUM_CHARACTERS);
		if (minimumCharacters >= 0) {
			folder.setMinimumCharacters(minimumCharacters);
		}

		PresentablePartFolder partFolder = new PresentablePartFolder(folder);

		folder.setUnselectedCloseVisible(false);
		folder.setUnselectedImageVisible(false);*/
		
		
		//return new  DafoeStackPresentation(parent,site);
		
		return super.createEditorPresentation(parent, site);
	}

	@Override
	public StackPresentation createStandaloneViewPresentation(Composite parent,
			IStackPresentationSite site, boolean showTitle) {
		//return new  DafoeStackPresentation(parent,site);
		return super.createStandaloneViewPresentation(parent, site, showTitle);
	}

	@Override
	public StackPresentation createViewPresentation(Composite parent,
			IStackPresentationSite site) {
		//return new  DafoeStackPresentation(parent,site);
		return super.createViewPresentation(parent, site);
	}

}
