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

package org.dafoe.terminologiclevel.saillance.commands;

import org.dafoe.terminologiclevel.saillance.TermDictionaryWriter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class ExportTerms extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		String path = selectXMLFile(event);

		if (path != null) {
			TermDictionaryWriter.buildExport(path);
		}
		
		return null;
	}
	
	
	private String selectXMLFile(ExecutionEvent event) {

		String path = null;
		
		try {

			FileDialog dialog = new FileDialog(HandlerUtil.getActiveSite(event).getWorkbenchWindow().getShell(), SWT.SAVE);

			dialog.setFilterExtensions(new String[] { "*.xml", "*.*" }); //$NON-NLS-1$ //$NON-NLS-2$

			dialog.setFilterPath(org.dafoe.terminologiclevel.Activator
					.getPluginFolder().getPath());
			path = dialog.open();

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		return path;
	}

}
