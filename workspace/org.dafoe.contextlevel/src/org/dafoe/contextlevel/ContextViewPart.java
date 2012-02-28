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
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener4;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.themes.IThemeManager;

public class ContextViewPart extends ViewPart {

	public ContextViewPart() {		
	}

	private static final Color ONTOLOGIC_LEVEL_COLOR = new Color(Display.getDefault(), 252, 195, 0);
	
	private static final Color TERMINO_ONTOLOGIC_LEVEL_COLOR = new Color(Display.getDefault(), 234, 102, 28);
	
	private static final Color TERMINOLOGIC_LEVEL_COLOR = new Color(Display.getDefault(), 227, 0, 55);
	
	private static final Color CORPUS_LEVEL_COLOR = new Color(Display.getDefault(), 175, 19, 30);
	
	private Composite menuLevel;
	
	@Override
	public void createPartControl(final Composite parent) {
		GridLayout myGridLayout = new GridLayout(1, false);
		parent.setLayout(myGridLayout);	

		//final Label contextLabel = new Label(parent, SWT.NONE);
		GridData myGridData = new GridData(GridData.FILL_BOTH);
		//contextLabel.setLayoutData(myGridData);
		/*final Image image = new Image(Display.getDefault(), getClass().getResourceAsStream(
        "dragon.gif"));*/
    
		Composite labellevel = new Composite(parent, SWT.NONE);
		labellevel.setLayoutData(myGridData);
		
		labellevel.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				//gc.drawImage(image, 0, e.height-image.getImageData().height-5);
				Transform tr = new Transform(Display.getDefault());				
				
				Font font = new Font(Display.getDefault(),"Arial",18,SWT.BOLD | SWT.ITALIC);  //$NON-NLS-1$
				gc.setFont(font); 
				tr.translate(e.width-25,e.height);
				tr.rotate(270);
				
				gc.setTransform(tr);
				gc.drawString(Activator.getDefault().getContextLevel().getCurrentContextLevelName(), 0, 0);
				
				
			}			
		});		
		
		/*contextLabel.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				//gc.drawImage(image, 0, e.height-image.getImageData().height-5);
				Transform tr = new Transform(Display.getDefault());				
				
				Font font = new Font(Display.getDefault(),"Arial",18,SWT.BOLD | SWT.ITALIC);  //$NON-NLS-1$
				gc.setFont(font); 
				tr.translate(e.width-25,e.height);
				tr.rotate(270);
				
				gc.setTransform(tr);
				gc.drawString(Activator.getDefault().getContextLevel().getCurrentContextLevelName(), 0, 0);
				
				
			}			
		});		
		contextLabel.setRedraw(true);*/
		
		menuLevel = new Composite(parent, SWT.NONE);
		FillLayout myFillLayout = new FillLayout(SWT.VERTICAL);
		myFillLayout.spacing = 10;
		menuLevel.setLayout(myFillLayout);
			
		Button ontologicLevel = new Button(menuLevel, SWT.FLAT);
		ontologicLevel.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				if (Activator.getDefault().getContextLevel().getCurrentContextLevel().equals("ontologic")) { //$NON-NLS-1$
					gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
					gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
					parent.setBackground(ONTOLOGIC_LEVEL_COLOR);
				} else {
					
					gc.setBackground(ONTOLOGIC_LEVEL_COLOR);
					gc.setForeground(ONTOLOGIC_LEVEL_COLOR);
				}
				
				gc.fillRectangle(1 , 1, e.width -1 , e.height - 1);				
			}			
		});

		Button terminoOntologicLevel = new Button(menuLevel, SWT.FLAT);
		terminoOntologicLevel.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;

				if (Activator.getDefault().getContextLevel().getCurrentContextLevel().equals("terminoontologic")) { //$NON-NLS-1$
					gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
					gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
					parent.setBackground(TERMINO_ONTOLOGIC_LEVEL_COLOR);
				} else {
					
					gc.setBackground(TERMINO_ONTOLOGIC_LEVEL_COLOR);
					gc.setForeground(TERMINO_ONTOLOGIC_LEVEL_COLOR);
				}
				
				gc.fillRectangle(1, 1, e.width - 1, e.height - 1);				
			}			
		});

		Button terminologicLevel = new Button(menuLevel, SWT.FLAT);
		terminologicLevel.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;

				if (Activator.getDefault().getContextLevel().getCurrentContextLevel().equals("terminologic")) { //$NON-NLS-1$
					gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
					gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
					
				} else {
					
					gc.setBackground(TERMINOLOGIC_LEVEL_COLOR);
					gc.setForeground(TERMINOLOGIC_LEVEL_COLOR);
				}
				
				gc.fillRectangle( 1, 1, e.width - 1, e.height - 1);					
			}			
		});
		
		Button corpusLevel = new Button(menuLevel, SWT.FLAT);
		corpusLevel.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;

				if (Activator.getDefault().getContextLevel().getCurrentContextLevel().equals("corpus")) { //$NON-NLS-1$
					gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
					gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				} else {
					
					gc.setBackground(CORPUS_LEVEL_COLOR);
					gc.setForeground(CORPUS_LEVEL_COLOR);
				}
				
				gc.fillRectangle( 1, 1, e.width - 1, e.height - 1);				
			}			
		});
		
		myGridData = new GridData(GridData.FILL_HORIZONTAL);
		myGridData.verticalAlignment = SWT.END;
		myGridData.heightHint = 200;
		menuLevel.setLayoutData(myGridData);
		
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
				menuLevel.setRedraw(true);				
			}

			public void perspectiveChanged(IWorkbenchPage page,
					IPerspectiveDescriptor perspective, String changeId) {
			}

			public void perspectivePreDeactivate(IWorkbenchPage page,
					IPerspectiveDescriptor perspective) {
			}		
		});
	}
	
	@Override
	public void setFocus() {
	}
}
