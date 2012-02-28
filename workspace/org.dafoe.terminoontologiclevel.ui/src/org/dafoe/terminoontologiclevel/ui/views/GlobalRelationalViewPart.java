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
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

public class GlobalRelationalViewPart extends ViewPart {
	public static String ID = "org.dafoe.terminoontologiclevel.ui.views.GlobalRelationalViewID"; //$NON-NLS-1$

	private static final String TC1 = Messages
			.getString("GlobalRelationalViewPart.1"); //$NON-NLS-1$
	private static final String TC2 = Messages
			.getString("GlobalRelationalViewPart.2"); //$NON-NLS-1$
	private static final String RTC_TYPE = Messages
			.getString("GlobalRelationalViewPart.3"); //$NON-NLS-1$
	private static final String RTC_STATUS = Messages
			.getString("GlobalRelationalViewPart.4"); //$NON-NLS-1$

	private static final String[] PROPS = { TC1, RTC_TYPE, TC2, RTC_STATUS };

	private TableViewer rtcTableViewer;

	private TableViewerColumn tc1Column;
	private TableViewerColumn rtcTypeColumn;
	private TableViewerColumn tc2Column;
	private TableViewerColumn statusColumn;

	private TCWidget arg1TC;
	private TCWidget arg2TC;
	private RTCTypeWidget rtcType;

	private ComboBoxCellEditor rtcTypeEditor;
	private CellEditor[] editors;
	private Composite generalComposite;
	private Composite rtcComposite;

	public static Shell shell = null;

	private List<BinaryTCRelation> rtcs = new ArrayList<BinaryTCRelation>();

	private List<ITypeRelationTc> rtcTypes;

	private BinaryTCRelation currentRelation;

	private GlobalRelationalViewPartControler controler;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	private String eventType;

	private GlobalRelationalViewPartNewRTCPropertyChangeListener newRTCPropertyChangeListener;
	private GlobalRelationalViewPartNewStatusPropertyChangeListener newStatusPropertyChangeListener;
	private GlobalRelationalViewPartDeleteRTCPropertyChangeListener deleteRTCPropertyChangeListener;
	private GlobalRelationalViewPartUpdateRTCPropertyChangeListener updateRTCPropertyChangeListener;
	private GlobalRelationalViewPartUpdateRTCTypePropertyChangeListener updateRTCTypePropertyChangeListener;

	//

	public GlobalRelationalViewPart() {
		this.controler = new GlobalRelationalViewPartControler(this);

		this.eventType = GlobalRelationalViewPartControler.NEW_RTC_SELECTED_EVENT;
	}

	//

	@Override
	public void createPartControl(Composite parent) {

		GridLayout layout = new GridLayout(3, false);
		generalComposite = new Composite(parent, SWT.NONE);
		editors = new CellEditor[GlobalRelationalViewPart.PROPS.length];
		generalComposite.setLayout(layout);

		// RTCs

		rtcComposite = new Composite(generalComposite, SWT.NONE);
		layout = new GridLayout(1, false);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		rtcComposite.setLayout(layout);
		rtcComposite.setLayoutData(layoutData);

		rtcTableViewer = new TableViewer(rtcComposite, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		rtcTableViewer.setUseHashlookup(true);
		rtcTableViewer.setColumnProperties(PROPS);
		rtcTableViewer.setContentProvider(new RTCContentProvider());

		TextCellEditor tc1Editor = new TextCellEditor(
				rtcTableViewer.getTable(), SWT.SINGLE | SWT.READ_ONLY);

		rtcTypeEditor = new ComboBoxCellEditor(rtcTableViewer.getTable(),
				createComboBoxContent(), SWT.READ_ONLY);

		TextCellEditor tc2Editor = new TextCellEditor(
				rtcTableViewer.getTable(), SWT.SINGLE | SWT.READ_ONLY);

		TextCellEditor statusEditor = new TextCellEditor(rtcTableViewer
				.getTable(), SWT.SINGLE | SWT.READ_ONLY);

		editors[0] = tc1Editor;
		editors[1] = rtcTypeEditor;
		editors[2] = tc2Editor;
		editors[3] = statusEditor;

		rtcTableViewer.setCellEditors(editors);

		rtcTableViewer.getTable().addListener(SWT.MouseDoubleClick,
				new RTCTableViewerListener());

		GridData myGridData = new GridData(GridData.FILL_BOTH);

		// Columns

		tc1Column = new TableViewerColumn(rtcTableViewer, SWT.NONE, 0);
		tc1Column.getColumn().setText(GlobalRelationalViewPart.TC1);
		tc1Column.getColumn().setWidth(700);
		tc1Column.setLabelProvider(new TCLabelProvider(1));

		tc1Column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(RTCTableViewerSorter.TC1_SORT);

			}

		});

		rtcTypeColumn = new TableViewerColumn(rtcTableViewer, SWT.NONE, 1);
		rtcTypeColumn.getColumn().setText(GlobalRelationalViewPart.RTC_TYPE);
		rtcTypeColumn.getColumn().setWidth(150);
		rtcTypeColumn.setLabelProvider(new RTCTypeLabelProvider());

		rtcTypeColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(RTCTableViewerSorter.RTC_TYPE_SORT);

			}

		});

		tc2Column = new TableViewerColumn(rtcTableViewer, SWT.NONE, 2);
		tc2Column.getColumn().setText(GlobalRelationalViewPart.TC2);
		tc2Column.getColumn().setWidth(700);
		tc2Column.setLabelProvider(new TCLabelProvider(2));

		tc2Column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(RTCTableViewerSorter.TC2_SORT);

			}

		});

		statusColumn = new TableViewerColumn(rtcTableViewer, SWT.CENTER, 3);
		statusColumn.getColumn().setText(GlobalRelationalViewPart.RTC_STATUS);
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

		rtcTableViewer.setCellModifier(new RTCCellModifier(rtcTableViewer));

		updateRTCs();

		rtcTableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						TableItem[] sel = rtcTableViewer.getTable()
								.getSelection();

						if (sel.length != 0) {

							BinaryTCRelation tc = (BinaryTCRelation) (sel[0]
									.getData());
							BinaryTCRelation oldTCR = currentRelation;
							currentRelation = tc;
							if (oldTCR == tc) {
								oldTCR = null;
							}

							if (currentRelation != null) {

								ControlerFactoryImpl.getTerminoOntoControler()
										.setCurrentRTC(
												currentRelation.getOrigin());

								propertyChangeSupport.firePropertyChange(
										eventType, oldTCR, currentRelation);

							}
						}

					}
				});

		rtcComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = rtcComposite.getClientArea();
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

					tc1Column.getColumn().setWidth(width * 35 / 100);
					rtcTypeColumn.getColumn().setWidth(width * 20 / 100);
					tc2Column.getColumn().setWidth(width * 35 / 100);
					statusColumn.getColumn().setWidth(width * 10 / 100);
					rtcTableViewer.getTable().setSize(area.width, area.height);
				} else {

					rtcTableViewer.getTable().setSize(area.width, area.height);
					tc1Column.getColumn().setWidth(width * 35 / 100);
					rtcTypeColumn.getColumn().setWidth(width * 20 / 100);
					tc2Column.getColumn().setWidth(width * 35 / 100);
					statusColumn.getColumn().setWidth(width * 10 / 100);
				}
			}
		});

		// TC Arg1

		Composite tc1Composite = new Composite(generalComposite, SWT.NONE);
		tc1Composite.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		tc1Composite.setLayoutData(gd);

		arg1TC = new TCWidget(tc1Composite, SWT.NONE, Messages
				.getString("GlobalRelationalViewPart.5"), null); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		arg1TC.setLayoutData(gd);
		arg1TC
				.setEventType(GlobalRelationalViewPartControler.TC_ARG1_SELECTED_EVENT);
		arg1TC.addPropertyChangeListener(this.controler);

		// RTC Type

		Composite rtcTypeComposite = new Composite(generalComposite, SWT.NONE);
		rtcTypeComposite.setLayout(new GridLayout(1, false));
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		rtcTypeComposite.setLayoutData(gd);

		rtcType = new RTCTypeWidget(rtcTypeComposite, SWT.NONE, null);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		rtcType.setLayoutData(gd);
		rtcType
				.setEventType(GlobalRelationalViewPartControler.RTC_TYPE_SELECTED_EVENT);
		rtcType.createDropTarget();
		rtcType.addPropertyChangeListener(this.controler);

		// TC Arg2

		Composite tc2Composite = new Composite(generalComposite, SWT.NONE);
		tc2Composite.setLayout(new GridLayout(1, false));
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		tc2Composite.setLayoutData(gd);

		arg2TC = new TCWidget(tc2Composite, SWT.NONE, Messages
				.getString("GlobalRelationalViewPart.6"), null); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		arg2TC.setLayoutData(gd);
		arg2TC
				.setEventType(GlobalRelationalViewPartControler.TC_ARG2_SELECTED_EVENT);
		arg2TC.addPropertyChangeListener(this.controler);

		hookContextMenu();
		// makeActions();

		shell = this.getSite().getShell();

		this.addPropertyChangeListener(this.controler);

		newRTCPropertyChangeListener = new GlobalRelationalViewPartNewRTCPropertyChangeListener();
		newStatusPropertyChangeListener = new GlobalRelationalViewPartNewStatusPropertyChangeListener();
		deleteRTCPropertyChangeListener = new GlobalRelationalViewPartDeleteRTCPropertyChangeListener();
		updateRTCPropertyChangeListener = new GlobalRelationalViewPartUpdateRTCPropertyChangeListener();
		updateRTCTypePropertyChangeListener = new GlobalRelationalViewPartUpdateRTCTypePropertyChangeListener();

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.currentRTCEvent,
						newRTCPropertyChangeListener);

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.newRTCStatusEvent,
						newStatusPropertyChangeListener);

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(ControlerFactoryImpl.deleteRTCEvent,
						deleteRTCPropertyChangeListener);

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.updateTCsTreeEvent,
						updateRTCPropertyChangeListener);

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.updateRTCTypeEvent,
						updateRTCTypePropertyChangeListener);
	}

	//

	public void updateRtcTypes() {
		rtcTypes = DatabaseAdapter.getRTCTypes();
	}

	//

	public void updateRTCTypesComboBoxCellEditor() {
		this.rtcTypeEditor = new ComboBoxCellEditor(rtcTableViewer.getTable(),
				createComboBoxContent(), SWT.READ_ONLY);
		editors[1] = this.rtcTypeEditor;
	}

	//

	public String[] createComboBoxContent() {
		updateRtcTypes();
		String[] res = new String[rtcTypes.size()];
		Iterator<ITypeRelationTc> it = rtcTypes.iterator();
		int i = 0;

		while (it.hasNext()) {
			res[i] = it.next().getName();
			i++;
		}

		return res;
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

	class RTCTypeLabelProvider extends ColumnLabelProvider {

		public RTCTypeLabelProvider() {
			super();
		}

		public String getText(Object element) {
			BinaryTCRelation rtc = (BinaryTCRelation) element;
			String result = ""; //$NON-NLS-1$

			result = rtc.getType().getName();

			return result;
		}

	}

	//

	class TCLabelProvider extends ColumnLabelProvider {
		int term;

		public TCLabelProvider(int term) {
			super();
			this.term = term;
		}

		/**
		 * 
		 */
		public String getText(Object element) {
			BinaryTCRelation relation = (BinaryTCRelation) element;

			if (this.term == 1) {
				return relation.getTc1().getLabel();
			} else if (term == 2) {
				return relation.getTc2().getLabel();
			}

			return ""; //$NON-NLS-1$
		}

		/**
		 * 
		 */
		public Image getImage(Object element) {
			BinaryTCRelation relation = (BinaryTCRelation) element;
			Image img = null;
			Color color;
			ITerminoConcept tc = null;

			if (this.term == 1) {
				tc = relation.getTc1();
			} else if (term == 2) {
				tc = relation.getTc2();
			}

			if (tc != null) {
				if (tc.isSimple()) {
					color = Display.getDefault().getSystemColor(
							SWT.COLOR_DARK_GREEN);
				} else {
					color = Display.getDefault().getSystemColor(
							SWT.COLOR_DARK_BLUE);
				}

				img = createImage(Display.getDefault(), color);
			}

			return img;
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

	class RTCCellModifier implements ICellModifier {
		final TableViewer viewer;

		public RTCCellModifier(TableViewer viewer) {
			super();
			this.viewer = viewer;
		}

		public boolean canModify(Object element, String property) {
			final int columnIndex = getPropertyIndex(property);
			return columnIndex == 1;

		}

		public Object getValue(Object element, String property) {

			final BinaryTCRelation rtcRelation = (BinaryTCRelation) element;

			if (GlobalRelationalViewPart.RTC_TYPE.equals(property)) {

				String rtcTypeLabel = rtcRelation.getType().getName();
				int i = 0;
				boolean found = false;
				String[] comboBoxContent = createComboBoxContent();

				while ((!found) && (i < comboBoxContent.length)) {

					found = (comboBoxContent[i].compareTo(rtcTypeLabel) == 0);

					if (!found) {
						i++;
					}
				}

				return i;

			}

			return null;

		}

		public void modify(Object element, String property, Object value) {

			if (element != null) {
				TableItem tableItem = (TableItem) element;
				ITypeRelationTc rtcType = null;

				final BinaryTCRelation rtc = (BinaryTCRelation) tableItem
						.getData();
				// final ITermRelation termRelation =
				// ControlerFactoryImpl.getTerminologyControler().getTermRelation();
				try {
					if (GlobalRelationalViewPart.RTC_TYPE.equals(property)) {

						String[] comboBoxContent = createComboBoxContent();

						int index = ((Integer) value).intValue();

						String valueString = comboBoxContent[index].trim();

						if (!rtc.getType().getName().equals(valueString)) {

							rtcType = rtcTypes.get(index);

							DatabaseAdapter.updateRTC(rtc, null, null, rtcType,
									TERMINO_ONTO_OBJECT_STATE.UNKNOWN);

							rtc.setType(rtcType);
						}
					}

				} catch (Exception e) {

					e.printStackTrace();

				}

				viewer.refresh(rtc);

				viewer.setSelection(new StructuredSelection(rtc));
			}

		}

		private int getPropertyIndex(String property) {
			final Object[] columnProperties = this.viewer.getColumnProperties();
			for (int i = 0; i < columnProperties.length; i++) {
				if (property.equals(columnProperties[i])) {
					return i;
				}
			}
			return 0;
		}
	}

	//

	class RTCTableViewerListener implements Listener {

		public void handleEvent(Event event) {
		}
	}

	//

	/*
	 * private void fillLocalPullDown(IMenuManager manager) { //
	 * manager.add(validateTermAction); // manager.add(new Separator()); }
	 */

	//

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(rtcTableViewer.getTable());
		rtcTableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, rtcTableViewer);

	}

	//

	public void updateRTCs() {

		rtcs = DatabaseAdapter.getTerminoConceptualRelations();
		System.out.println("#RTC = " + rtcs.size()); //$NON-NLS-1$
		rtcTableViewer.setInput(rtcs);

	}

	//

	public void setSorter(int sortedColumn) {
		rtcTableViewer.setSorter(new RTCTableViewerSorter(sortedColumn));
		rtcTableViewer.getTable().setSortColumn(
				rtcTableViewer.getTable().getColumn(sortedColumn));
		rtcTableViewer.getTable().setSortDirection(
				RTCTableViewerSorter.getDirection());

	}

	//

	public TCWidget getTCArg1Widget() {
		return arg1TC;
	}

	//

	public TCWidget getTCArg2Widget() {
		return arg2TC;
	}

	//

	public RTCTypeWidget getRTCTypeWidget() {
		return rtcType;
	}

	//

	public BinaryTCRelation getSelection() {
		return currentRelation;
	}

	//

	public void setSelection(BinaryTCRelation rtc) {
		this.rtcTableViewer.setSelection(new StructuredSelection(rtc));
		currentRelation = rtc;
	}

	//

	public void refresh(BinaryTCRelation tcr) {
		this.rtcTableViewer.refresh(tcr);
	}

	//

	public void deselect() {
		this.rtcTableViewer.getTable().deselectAll();
		currentRelation = null;
	}

	//

	public List<BinaryTCRelation> getRTCs() {
		return this.rtcs;
	}

	//

	public void setEventType(String event) {
		this.eventType = event;
	}

	//

	public void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	//

	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}

	//

	class GlobalRelationalViewPartNewRTCPropertyChangeListener implements
			PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			BinaryTCRelation newSelection = null;

			updateRTCs();

			ITerminoConceptRelation currentRTC = ControlerFactoryImpl
					.getTerminoOntoControler().getCurrentRTC();

			newSelection = getBinaryRelationFromRTC(currentRTC);

			if (newSelection != null) {
				rtcTableViewer.setSelection(new StructuredSelection(
						newSelection));
			}

		}

	}

	//

	class GlobalRelationalViewPartNewStatusPropertyChangeListener implements
			PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {

			ITerminoConceptRelation currentRTC = ControlerFactoryImpl
					.getTerminoOntoControler().getCurrentRTC();

			BinaryTCRelation rtcSelection = getBinaryRelationFromRTC(currentRTC);

			rtcSelection.setState(currentRTC.getState());

			if (rtcSelection != null) {

				rtcTableViewer.refresh(rtcSelection);
			}

		}

	}

	//

	class GlobalRelationalViewPartDeleteRTCPropertyChangeListener implements
			PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {

			updateRTCs();

		}

	}

	//

	class GlobalRelationalViewPartUpdateRTCPropertyChangeListener implements
			PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {

			rtcTableViewer.refresh();

		}

	}

	//

	class GlobalRelationalViewPartUpdateRTCTypePropertyChangeListener implements
			PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {

			updateRTCTypesComboBoxCellEditor();

			rtcTableViewer.refresh();

		}

	}

	//

	private BinaryTCRelation getBinaryRelationFromRTC(
			ITerminoConceptRelation rtc) {
		BinaryTCRelation binaryRTC = null;

		Iterator<BinaryTCRelation> it = rtcs.iterator();
		while (it.hasNext()) {
			BinaryTCRelation tmp = it.next();

			if (tmp.getOrigin() == rtc) {
				binaryRTC = tmp;
				break;
			}
		}

		return binaryRTC;
	}
}
