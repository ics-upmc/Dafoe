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
package org.dafoe.corpuslevel.ui.commands;

import org.dafoe.corpuslevel.ui.Activator;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class DestroyDocumentHandler.
 * 
 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class DestroyDocumentHandler extends AbstractHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IDocument currentDoc = org.dafoe.controler.factory.ControlerFactoryImpl
				.getTerminologyControler().getCurrentDocument();

		boolean answer = MessageDialog.openQuestion(Activator.getDefault()
				.getWorkbench().getDisplay().getActiveShell(),
				"delete documment", "Are you sure you want to delete this document?"); //$NON-NLS-1$ //$NON-NLS-2$

		System.out.println(answer);

		if (answer) {

			System.out.println(" selected doc=  " + currentDoc.getName()); //$NON-NLS-1$

			// DatabaseAdapter.deleteDocument(currentDoc);

		}

		return null;
	}

}
