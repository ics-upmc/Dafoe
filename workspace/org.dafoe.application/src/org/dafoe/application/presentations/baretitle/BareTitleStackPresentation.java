/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.dafoe.application.presentations.baretitle;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.eclipse.ui.internal.presentations.PresentablePart;
import org.eclipse.ui.presentations.IPresentablePart;
import org.eclipse.ui.presentations.IStackPresentationSite;
import org.eclipse.ui.presentations.PresentationUtil;
import org.eclipse.ui.presentations.StackDropResult;
import org.eclipse.ui.presentations.StackPresentation;
import org.eclipse.ui.themes.IThemeManager;

public class BareTitleStackPresentation extends StackPresentation {

	//private static final String PART_DATA = "part";
	
	private static final int TITLE_HEIGHT = 22;
	
	private boolean activeFocus = false;
	
	/**
	 * Main widget for the presentation
	 */
	private Composite presentationControl;
	private Composite titleArea;
	private ViewForm contentArea;
	private Composite clientArea;
	/**
	 * Currently selected part
	 */
	private IPresentablePart current;
	
	Color backColor;
	Color foreColor;
		
	/**
	 * close button
	 */
	private ToolItem close;
	
	/**
	 * View menu button
	 */
	private ToolItem viewMenu;
	
	/**
	 * Minimize button
	 */
	private ToolItem minView;
	
	
	void GetColors() {
		
		
		IThemeManager themeManager =
			PlatformUI
			.getWorkbench()
			.getThemeManager();
	
		PresentablePart toto = (PresentablePart) current;
		if (toto != null) {
			WorkbenchPartReference coco = (WorkbenchPartReference) toto
					.getPane().getPartReference();
			IWorkbenchPart part = coco.getPart(true);
			String le_theme = "org.dafoe.application.themeId";
			if (coco.getId().contains("corpus")) {
				le_theme = "org.dafoe.corpuslevel.themeId";
			} else {
				if (coco.getId().contains("terminologic") || coco.getId().contains("saillance") || coco.getId().contains("terminologycard") || coco.getId().contains("terminology")) {
					le_theme = "org.dafoe.terminologiclevel.themeId";
				} else {
					if (coco.getId().contains("terminoontologic")) {
						le_theme = "org.dafoe.terminoontologiclevel.themeId";
					} else {
						if (coco.getId().contains("ontologic")) {
							
							le_theme = "org.dafoe.ontologiclevel.themeId";
							
						}
					}
				}
			}
			
			backColor = themeManager.getTheme(le_theme).getColorRegistry().get("org.dafoe.application.backcolor");
			foreColor = themeManager.getTheme(le_theme).getColorRegistry().get("org.dafoe.application.forecolor");
		}
	}
	
	
	private MouseListener mouseListener = new MouseAdapter() {		
		// If we single-click on an empty space on the toolbar, move focus to the
		// active control
		public void mouseDown(MouseEvent e) {
			if (current != null) {
				current.setFocus();
			}
		}
	};
		
	/** 
	 * Drag listener for regions outside the toolbar
	 */
	Listener dragListener = new Listener() {
		public void handleEvent(Event event) {
			Point loc = new Point(event.x, event.y);
			Control ctrl = (Control)event.widget;
			
			getSite().dragStart(ctrl.toDisplay(loc), false);
		}
	};
	
	public BareTitleStackPresentation(Composite parent, IStackPresentationSite stackSite) {
		super(stackSite);
		
		// Create a top-level control for the presentation.
		presentationControl = new Composite(parent, SWT.NONE);
		titleArea = new Composite(presentationControl, SWT.NONE);
		contentArea = new ViewForm(presentationControl, SWT.NONE);
		titleArea.addMouseListener(mouseListener);
		PresentationUtil.addDragListener(titleArea, dragListener);
		
		clientArea = new Composite(contentArea, SWT.NONE);
		clientArea.setVisible(false);
		
		contentArea.setContent(clientArea);
		
		// Add a dispose listener. This will call the presentationDisposed()
		// method when the widget is destroyed.
		presentationControl.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				presentationDisposed();
			}
		});

		
		presentationControl.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle clientArea = presentationControl.getClientArea();			
				e.gc.setLineWidth(1);				
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_DARK_GRAY));
				e.gc.drawRectangle(clientArea.x, clientArea.y, clientArea.width-1, clientArea.height-1);
				Rectangle contentAreaBounds = contentArea.getBounds();
				int ypos = contentAreaBounds.y - 1;				
				e.gc.drawLine(clientArea.x, ypos, clientArea.x + clientArea.width - 1, ypos);
			}			
		});
		
		titleArea.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GetColors();
				
				Rectangle titleRect = titleArea.getClientArea();
				GC gc = e.gc;
				//e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_GRAY));
				e.gc.setBackground(backColor);
				if(activeFocus)
					e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLUE));
				else 
					e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
				e.gc.fillGradientRectangle(titleRect.x, titleRect.y, titleRect.x + titleRect.width, titleRect.y + titleRect.height, true);
				
				if(current != null) {
				int textWidth = titleRect.width - 1;
				if (textWidth > 0) {
					Font gcFont = gc.getFont();
					gc.setFont(presentationControl.getFont());
					String text = current.getTitle();
					
					Point extent = gc.textExtent(text, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC);	
					int textY = titleRect.y + (titleRect.height - extent.y) / 2;
					
					if(activeFocus)					
						gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
					else 
						gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
					gc.setFont(JFaceResources.getBannerFont());
					gc.drawText(text, titleRect.x + 3, textY, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC);					
				}				
			}
			}
		});
		layout();
	}

	protected void presentationDisposed() {
	    // Remove any listeners that were attached to any
		// global Eclipse resources. This is necessary in order to prevent
		// memory leaks.
	}
	
	protected int getBorderWidth() {
		return 1;
	}
	
	public void layout() {
		// Determine the inner bounds of the presentation
		
		
		Rectangle presentationClientArea = presentationControl.getClientArea();
		presentationClientArea.x += getBorderWidth();
		presentationClientArea.width -= getBorderWidth() * 2;
		presentationClientArea.y += getBorderWidth();
		presentationClientArea.height -= getBorderWidth() * 2;
		titleArea.setBounds(presentationClientArea.x, presentationClientArea.y, presentationClientArea.width, presentationClientArea.y + TITLE_HEIGHT);
		int yy = TITLE_HEIGHT  + 2;
		contentArea.setBounds(presentationClientArea.x, presentationClientArea.y + yy, presentationClientArea.width, presentationClientArea.height - yy);
		
		titleArea.setBackground(titleArea.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));
		//contentArea.setBackground(titleArea.getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
		
		// Position the view's widgets
		if (current != null) {
			Rectangle clientRectangle = contentArea.getBounds();
			Point clientAreaStart = presentationControl.getParent().toControl(
					presentationControl.toDisplay(clientRectangle.x, clientRectangle.y));
			// current isn't parented by this widget hierarchy, the coordinates must be
			// relative to the workbench window. The workbench window parents every
			// part.
			/*current.setBounds(new Rectangle(clientAreaStart.x, 
					clientAreaStart.y,
					clientRectangle.width, 
					clientRectangle.height));*/
			
			if (current != null) {
				Control toolbar = current.getToolBar();
				
				contentArea.layout();
				
				Rectangle clientRectangle2 = clientArea.getBounds();
				Point clientAreaStart2 = presentationControl.getParent().toControl(
						contentArea.toDisplay(clientRectangle.x, clientRectangle.y));
				// current isn't parented by this widget hierarchy, the coordinates must be
				// relative to the workbench window. The workbench window parents every
				// part.
				current.setBounds(new Rectangle(clientAreaStart2.x, 
						clientAreaStart2.y,
						clientRectangle2.width, 
						clientRectangle2.height));
			}		
		}
		
		
		
	}
	
	public void setBounds(Rectangle bounds) {
		presentationControl.setBounds(bounds);
		layout();
	}

	public void dispose() {
	}

	public void setActive(int newState) {
		activeFocus = (newState == AS_ACTIVE_FOCUS);
		titleArea.redraw();
	}

	public void setVisible(boolean isVisible) {
	}

	public void setState(int state) {
	}

	public Control getControl() {
		return presentationControl;
	}

	public void addPart(IPresentablePart newPart, Object cookie) {
		newPart.setBounds(contentArea.getBounds());
		this.current = newPart;
		layout();
	}

	public void removePart(IPresentablePart oldPart) {
	}

	public void selectPart(IPresentablePart toSelect) {
	}

	public StackDropResult dragOver(Control currentControl, Point location) {
		return null;
	}

	public void showSystemMenu() {
	}

	public void showPaneMenu() {
	}

	public Control[] getTabList(IPresentablePart part) {
		if(current != null) {
			return new Control[] {current.getControl()};
		} else {
			return new Control[0];
		}
	}
}
