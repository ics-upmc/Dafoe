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

package org.dafoe.terminoontologiclevel.ui.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

public class RelationalViewPart extends ViewPart {

	public static String ID = "org.dafoe.terminoontologiclevel.ui.views.RelationalViewID"; //$NON-NLS-1$

	private static final String TC1 = Messages
			.getString("RelationalViewPart.8"); //$NON-NLS-1$
	private static final String TC2 = Messages
			.getString("RelationalViewPart.7"); //$NON-NLS-1$
	private static final String RTC_TYPE = Messages
			.getString("RelationalViewPart.6"); //$NON-NLS-1$
	private static final String RTC_STATUS = Messages
			.getString("RelationalViewPart.5"); //$NON-NLS-1$

	private static final String[] PROPS = { TC1, RTC_TYPE, TC2, RTC_STATUS };

	private TableViewer rtcTableViewer;

	private TableViewerColumn tc1Column;
	private TableViewerColumn rtcTypeColumn;
	private TableViewerColumn tc2Column;
	private TableViewerColumn statusColumn;

	private Composite generalComposite;

	public static Shell shell = null;

	private List<BinaryTCRelation> rtcs = new ArrayList<BinaryTCRelation>();

	private ITerminoConcept currentTerminoConcept;

//	private Color currentTCBackgroundColor;

	//

	public RelationalViewPart() {
		currentTerminoConcept = null;

//		IThemeManager themeManager = PlatformUI.getWorkbench()
//				.getThemeManager();

//		currentTCBackgroundColor = themeManager
//				.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

	}

	//

	@Override
	public void createPartControl(Composite parent) {

		GridLayout generalGl = new GridLayout(1, false);
		generalComposite = new Composite(parent, SWT.NONE);

		generalComposite.setLayout(generalGl);

		rtcTableViewer = new TableViewer(generalComposite, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		rtcTableViewer.setUseHashlookup(true);
		rtcTableViewer.setColumnProperties(PROPS);
		rtcTableViewer.setContentProvider(new RTCContentProvider());

		GridData myGridData = new GridData(GridData.FILL_BOTH);

		// Columns

		tc1Column = new TableViewerColumn(rtcTableViewer, SWT.NONE, 0);
		tc1Column.getColumn().setText(RelationalViewPart.TC1);
		tc1Column.getColumn().setWidth(700);
		tc1Column.setLabelProvider(new RTCLabelProvider(1));

		tc1Column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(RTCTableViewerSorter.TC1_SORT);

			}

		});

		rtcTypeColumn = new TableViewerColumn(rtcTableViewer, SWT.NONE, 1);
		rtcTypeColumn.getColumn().setText(RelationalViewPart.RTC_TYPE);
		rtcTypeColumn.getColumn().setWidth(150);
		rtcTypeColumn.setLabelProvider(new RTCLabelProvider(2));

		rtcTypeColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(RTCTableViewerSorter.RTC_TYPE_SORT);

			}

		});

		tc2Column = new TableViewerColumn(rtcTableViewer, SWT.NONE, 2);
		tc2Column.getColumn().setText(RelationalViewPart.TC2);
		tc2Column.getColumn().setWidth(700);
		tc2Column.setLabelProvider(new RTCLabelProvider(3));

		tc2Column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(RTCTableViewerSorter.TC2_SORT);

			}

		});

		statusColumn = new TableViewerColumn(rtcTableViewer, SWT.CENTER, 3);
		statusColumn.getColumn().setText(RelationalViewPart.RTC_STATUS);
		statusColumn.getColumn().setWidth(50);
		statusColumn.setLabelProvider(new StatusColumnLabelProvider());

		statusColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(RTCTableViewerSorter.STATUS_SORT);

			}

		});

		// Table viewer configuration

		rtcTableViewer.getTable().setLinesVisible(true);
		rtcTableViewer.getTable().setHeaderVisible(true);
		rtcTableViewer.getTable().setLayoutData(myGridData);

		rtcTableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						TableItem[] sel = rtcTableViewer.getTable()
								.getSelection();

						if (sel.length != 0) {

							BinaryTCRelation selectedRelation = (BinaryTCRelation) (sel[0]
									.getData());

							if (selectedRelation != null) {

								ControlerFactoryImpl.getTerminoOntoControler()
										.setCurrentRTC(
												selectedRelation.getOrigin());

							}
						}

					}
				});

		generalComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = generalComposite.getClientArea();
				Point preferredSize = rtcTableViewer.getTable().computeSize(
						SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2
						* rtcTableViewer.getTable().getBorderWidth();
				if (preferredSize.y > area.height
						+ rtcTableViewer.getTable().getHeaderHeight()) {

					Point vBarSize = rtcTableViewer.getTable().getVerticalBar()
							.getSize();
					width -= vBarSize.x;

				}

				Point oldSize = rtcTableViewer.getTable().getSize();

				if (oldSize.x > area.width) {

					tc1Column.getColumn().setWidth(width * 30 / 100);
					rtcTypeColumn.getColumn().setWidth(width * 30 / 100);
					tc2Column.getColumn().setWidth(width * 30 / 100);
					statusColumn.getColumn().setWidth(width * 10 / 100);
					rtcTableViewer.getTable().setSize(area.width, area.height);
				} else {

					rtcTableViewer.getTable().setSize(area.width, area.height);
					tc1Column.getColumn().setWidth(width * 30 / 100);
					rtcTypeColumn.getColumn().setWidth(width * 30 / 100);
					tc2Column.getColumn().setWidth(width * 30 / 100);
					statusColumn.getColumn().setWidth(width * 10 / 100);
				}
			}
		});

		shell = this.getSite().getShell();

		hookContextMenu();

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.newRTCStatusEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateRTCs();
							}

						});

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.currentTerminoConceptEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateRTCs();
							}

						});

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(ControlerFactoryImpl.renameTCEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateRTCs();
							}

						});

		updateRTCs();
	}

	//

	@Override
	public void setFocus() {
	}

	//

	class RTCContentProvider implements IStructuredContentProvider {
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((List<BinaryTCRelation>) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	//

	class RTCLabelProvider extends ColumnLabelProvider {
		int col;

		public RTCLabelProvider(int col) {
			super();
			this.col = col;
		}

		/**
		 * 
		 */

		public Font getFont(Object element) {

			BinaryTCRelation relation = (BinaryTCRelation) element;
			Font font = null;

			if (((this.col == 1) && (relation.getTc1() == currentTerminoConcept))
					|| ((this.col == 3) && (relation.getTc2() == currentTerminoConcept))) {

				FontData fontData = new FontData("Arial", 10, SWT.BOLD); //$NON-NLS-1$
				font = new Font(shell.getDisplay(), fontData);

			}

			return font;

		}

		public String getText(Object element) {
			BinaryTCRelation relation = (BinaryTCRelation) element;

			if (this.col == 1) {

				return relation.getTc1().getLabel();

			} else if (col == 2) {

				return relation.getType().getName();

			} else if (col == 3) {

				return relation.getTc2().getLabel();

			}

			return ""; //$NON-NLS-1$
		}

		public Color getBackground(Object element) {

			// BinaryTCRelation relation = (BinaryTCRelation) element;
			Color color = null;
			/*
			 * if (((this.col == 1) && (relation.getTc1() ==
			 * currentTerminoConcept)) || ((this.col == 3) && (relation.getTc2()
			 * == currentTerminoConcept))){
			 * 
			 * color = currentTCBackgroundColor;
			 * 
			 * }
			 */
			return color;
		}

		public Color getForeground(Object element) {
			// BinaryTCRelation relation = (BinaryTCRelation) element;
			// Color white = shell.getDisplay().getSystemColor(SWT.COLOR_WHITE);
			Color color = null;

			/*
			 * if (((this.col == 1) && (relation.getTc1() ==
			 * currentTerminoConcept)) || ((this.col == 3) && (relation.getTc2()
			 * == currentTerminoConcept))){
			 * 
			 * color = white;
			 * 
			 * }
			 */
			return color;
		}
	}

	//

	class StatusColumnLabelProvider extends OwnerDrawLabelProvider {
		@Override
		protected void measure(Event event, Object element) {
		}

		@Override
		protected void paint(Event event, Object element) {

			BinaryTCRelation relation = (BinaryTCRelation) element;
			TERMINO_ONTO_OBJECT_STATE status = relation.getState();
			Color color = null;

			if (relation != null) {

				if (status == TERMINO_ONTO_OBJECT_STATE.VALIDATED) {

					color = Display.getDefault()
							.getSystemColor(SWT.COLOR_GREEN);

				} else if (status == TERMINO_ONTO_OBJECT_STATE.REJECTED) {

					color = Display.getDefault().getSystemColor(SWT.COLOR_RED);

				} else if (status == TERMINO_ONTO_OBJECT_STATE.STUDIED) {

					color = Display.getDefault().getSystemColor(
							SWT.COLOR_YELLOW);

				}

				/*
				 * else if (status == ITerminoOntoObjectStatus.DELETED) {
				 * 
				 * color = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
				 * }
				 */
				Image img = null;

				if (status != TERMINO_ONTO_OBJECT_STATE.FORMALIZED) {
					img = createImage(Display.getDefault(), color);

				} else {

					String imageId;
					imageId = org.dafoe.terminoontologiclevel.ui.Activator.CONCEPTUALIZED_IMG_ID;
					ImageDescriptor imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
							.getDefault().getImageRegistry().getDescriptor(
									imageId);

					img = imgDesc.createImage();

				}

				if (img != null) {

					Rectangle bounds = ((TableItem) event.item)
							.getBounds(event.index);
					Rectangle imgBounds = img.getBounds();
					bounds.width /= 2;
					bounds.width -= imgBounds.width / 2;
					bounds.height /= 2;
					bounds.height -= imgBounds.height / 2;

					int x = bounds.width > 0 ? bounds.x + bounds.width
							: bounds.x;
					int y = bounds.height > 0 ? bounds.y + bounds.height
							: bounds.y;

					event.gc.drawImage(img, x, y);
				}

			}
		}

		Image createImage(Display display, Color color) {
			Image image = new Image(display, 10, 10);
			GC gc = new GC(image);
			gc.setBackground(color);
			gc.fillRectangle(0, 0, 10, 10);
			gc.dispose();

			return image;
		}
	}

	//

	//

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(rtcTableViewer.getTable());
		rtcTableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, rtcTableViewer);

	}

	//

	public void updateRTCs() {

		currentTerminoConcept = ControlerFactoryImpl.getTerminoOntoControler()
				.getCurrentTerminoConcept();

		if (currentTerminoConcept != null) {

			rtcs = DatabaseAdapter.getRTCsFromTC(currentTerminoConcept);

			rtcTableViewer.setInput(rtcs);

		}

	}

	//

	public void setSorter(int sortedColumn) {
		rtcTableViewer.setSorter(new RTCTableViewerSorter(sortedColumn));
		rtcTableViewer.getTable().setSortColumn(
				rtcTableViewer.getTable().getColumn(sortedColumn));
		rtcTableViewer.getTable().setSortDirection(
				RTCTableViewerSorter.getDirection());

	}

}
