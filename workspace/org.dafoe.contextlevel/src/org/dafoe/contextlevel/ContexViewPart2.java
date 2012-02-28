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
package org.dafoe.contextlevel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener4;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.themes.IThemeManager;

public class ContexViewPart2 extends ViewPart {

	/*
	private static final Color ONTOLOGIC_LEVEL_COLOR = new Color(Display.getDefault(), 252, 195, 0);
	
	private static final Color TERMINO_ONTOLOGIC_LEVEL_COLOR = new Color(Display.getDefault(), 234, 102, 28);
	
	private static final Color TERMINOLOGIC_LEVEL_COLOR = new Color(Display.getDefault(), 227, 0, 55);
	
	private static final Color CORPUS_LEVEL_COLOR = new Color(Display.getDefault(), 175, 19, 30);
	*/
	
	private static final Color DEFAULT_LEVEL_COLOR = new Color(Display.getDefault(), 255, 255, 255);
	
	public void createPartControl(final Composite parent) {
		/*GridLayout myGridLayout = new GridLayout(1, false);
		parent.setLayout(myGridLayout);	

		final Label contextLabel = new Label(parent, SWT.NONE);
		GridData myGridData = new GridData(GridData.FILL_BOTH);
		contextLabel.setLayoutData(myGridData);*/
		
		final Image image = new Image(Display.getDefault(), getClass().getResourceAsStream("dragon.gif"));
    
		/*
		IThemeManager themeManager =
			PlatformUI
			.getWorkbench()
			.getThemeManager();
		*/
		
		/*
		ONTOLOGIC_LEVEL_COLOR = themeManager.getTheme("org.dafoe.ontologic.themeId").getColorRegistry().get("org.dafoe.application.backcolor");
		
		TERMINO_ONTOLOGIC_LEVEL_COLOR = themeManager.getTheme("org.dafoe.termino_ontologic.themeId").getColorRegistry().get("org.dafoe.application.backcolor");
		
		TERMINOLOGIC_LEVEL_COLOR = themeManager.getTheme("org.dafoe.terminologic.themeId").getColorRegistry().get("org.dafoe.application.backcolor");
		
		CORPUS_LEVEL_COLOR =  themeManager.getTheme("org.dafoe.corpus.themeId").getColorRegistry().get("org.dafoe.application.backcolor");
		
		DEFAULT_LEVEL_COLOR = themeManager.getTheme("org.dafoe.application.themeId").getColorRegistry().get("org.dafoe.application.backcolor");
		
		*/
		
		parent.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				
				int h = ((Control) e.getSource()).getBounds().height;
				int w = ((Control) e.getSource()).getBounds().width;
				//gc.setClipping(new Rectangle(0,0,e.width,e.height));
				
				
				gc.drawImage(image, 0,0,image.getImageData().width,image.getImageData().height,0, h-image.getImageData().height-5,w,image.getImageData().height);
				Transform tr = new Transform(Display.getDefault());				
				
				Font font = new Font(Display.getDefault(),"Arial",18,SWT.BOLD | SWT.ITALIC);  //$NON-NLS-1$
				gc.setFont(font); 
				tr.translate(w-25,h-image.getImageData().height-15);
				tr.rotate(270);
				
				IThemeManager themeManager =
					PlatformUI
					.getWorkbench()
					.getThemeManager();
				
				gc.setForeground(themeManager.getCurrentTheme().getColorRegistry().get("org.dafoe.application.backcolor"));
				gc.setTransform(tr);
				gc.drawString(Activator.getDefault().getContextLevel().getCurrentContextLevelName(), 0, 0);
				
				//parent.setBackground(themeManager.getCurrentTheme().getColorRegistry().get("org.dafoe.application.backcolor"));
				parent.setBackground(DEFAULT_LEVEL_COLOR);
				
				
				
				
			
				
			}			
		});		
		
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().addPerspectiveListener(new IPerspectiveListener4() {
			public void perspectiveClosed(IWorkbenchPage page,
					IPerspectiveDescriptor perspective) {
			}

			public void perspectiveDeactivated(IWorkbenchPage page,
					IPerspectiveDescriptor perspective) {
			}

			public void perspectiveOpened(IWorkbenchPage page,
					IPerspectiveDescriptor perspective) {		
				
			}

			public void perspectiveSavedAs(IWorkbenchPage page,
					IPerspectiveDescriptor oldPerspective,
					IPerspectiveDescriptor newPerspective) {
			}

			public void perspectiveChanged(IWorkbenchPage page,
					IPerspectiveDescriptor perspective,
					IWorkbenchPartReference partRef, String changeId) {
			}

			public void perspectiveActivated(IWorkbenchPage page,
					IPerspectiveDescriptor perspective) {
				IDafoeContextLevel contextLevel = Activator.getDefault().getContextLevel();
				contextLevel.setCurrentPerspectiveId(perspective.getId());
				IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
				themeManager.setCurrentTheme(contextLevel.getCurrentContextLevelTheme());
				//menuLevel.setRedraw(true);				
			}

			public void perspectiveChanged(IWorkbenchPage page,
					IPerspectiveDescriptor perspective, String changeId) {
			}

			public void perspectivePreDeactivate(IWorkbenchPage page,
					IPerspectiveDescriptor perspective) {
			}		
		});
	}

	
	public void setFocus() {
	}

}
