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

import java.util.Map; 
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.jface.menus.IMenuStateIds;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

public abstract class ToggleHandler extends AbstractHandler implements IElementUpdater {
	 
	private String commandId;
 
	public final Object execute(ExecutionEvent event) throws ExecutionException {
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		this.commandId = event.getCommand().getId();
 
		// update toggled state
		State state = event.getCommand().getState(IMenuStateIds.STYLE);
		if (state == null)
			throw new ExecutionException(
					"You need to declare a ToggleState with id=STYLE for your command to use ToggleHandler!"); //$NON-NLS-1$
		boolean currentState = (Boolean) state.getValue();
		boolean newState = !currentState;
		state.setValue(newState);
 
		// trigger element update
		executeToggle(event, newState);
		commandService.refreshElements(event.getCommand().getId(), null);
 
		// return value is reserved for future apis
		return null;
	}
 
	protected abstract void executeToggle(ExecutionEvent event, boolean checked);
 
	/**
	 * Update command element with toggle state
	 */
	@SuppressWarnings("unchecked")
	public void updateElement(UIElement element, Map parameters) {
		if (this.commandId != null) {
			ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(
					ICommandService.class);
			Command command = commandService.getCommand(commandId);
			State state = command.getState(IMenuStateIds.STYLE);
			if (state != null)
				element.setChecked((Boolean) state.getValue());
		}
	}
 
}

