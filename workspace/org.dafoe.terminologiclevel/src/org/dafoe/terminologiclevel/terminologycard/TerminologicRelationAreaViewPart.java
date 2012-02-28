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

package org.dafoe.terminologiclevel.terminologycard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.terminologiclevel.saillance.dialog.TermDialogSelector;
import org.dafoe.terminologiclevel.terminologycard.dialogs.TerminologicRelationCreatorDialog;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
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

public class TerminologicRelationAreaViewPart extends ViewPart {
	public static final String ID = "org.dafoe.terminologiclevel.terminologycard.TerminologicRelationtAreaViewPartId"; //$NON-NLS-1$

	public static final String TERM1 = Messages
			.getString("TerminologicRelationAreaViewPart.0"); //$NON-NLS-1$
	public static final String TERM2 = Messages
			.getString("TerminologicRelationAreaViewPart.1"); //$NON-NLS-1$
	public static final String RELATION_TYPE = Messages
			.getString("TerminologicRelationAreaViewPart.9"); //$NON-NLS-1$
	public static final String RELATION_STATUS = Messages
			.getString("TerminologicRelationAreaViewPart.10"); //$NON-NLS-1$
	public static final String[] PROPS = { TERM1, RELATION_TYPE, TERM2,	RELATION_STATUS };

	private TerminologyRelationViewPartNewRelationPropertyChangeListener newRelationPropertyChangeListener;
	private TerminologyRelationViewPartNewTermPropertyChangeListener newTermPropertyChangeListener;
	private TerminologyRelationViewPartNewRelationStatusPropertyChangeListener newRelationStatusPropertyChangeListener;
	private TerminologyRelationViewPartNewTermLabelPropertyChangeListener newTermLabelPropertyChangeListener ;
	private TerminologyRelationViewPartRelationDeletedPropertyChangeListener relationDeletedPropertyChangeListener ;
	
	/*
	private Action validateRelationAction;
	private Action studyRelationAction;
	private Action rejectRelationAction;
	private Action addRelationAction;
	private Action removeRelationAction;

*/
	private StatusRelationFilter statusFilter;
	private StatusRelationAntiFilter statusAntiFilter;
	
	private TableViewer terminologyRelationTableViewer;

	private TableViewerColumn term1Column;
	private TableViewerColumn relationTypeColumn;
	private TableViewerColumn term2Column;
	private TableViewerColumn statusColumn;

	private ComboBoxCellEditor relationTypeEditor;
	private CellEditor[] editors;
	private Composite generalComposite;

	public static Shell shell = null;

	private List<ITermRelation> relations = new ArrayList<ITermRelation>();
	
	private List<ITypeRelationTermino> relationTypes;

	public TerminologicRelationAreaViewPart() {
	}

	@Override
	public void createPartControl(Composite parent) {
		
		updateRelationTypes();

		GridLayout generalGl = new GridLayout(1, false);
		generalComposite = new Composite(parent, SWT.NONE);
		editors = new CellEditor[TerminologicRelationAreaViewPart.PROPS.length];

		generalComposite.setLayout(generalGl);

		terminologyRelationTableViewer = new TableViewer(generalComposite,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		terminologyRelationTableViewer.setUseHashlookup(true);
		terminologyRelationTableViewer.setColumnProperties(PROPS);
		terminologyRelationTableViewer
				.setContentProvider(new TerminologicRelationContentProvider());

		TextCellEditor term1Editor = new TextCellEditor(
				terminologyRelationTableViewer.getTable(), SWT.SINGLE
						| SWT.READ_ONLY);

		relationTypeEditor = new ComboBoxCellEditor(
				terminologyRelationTableViewer.getTable(),
				createComboBoxContent(), SWT.READ_ONLY);
		
		TextCellEditor term2Editor = new TextCellEditor(
				terminologyRelationTableViewer.getTable(), SWT.SINGLE
						| SWT.READ_ONLY);

		TextCellEditor statusEditor = new TextCellEditor(
				terminologyRelationTableViewer.getTable(), SWT.SINGLE
						| SWT.READ_ONLY);

		editors[0] = term1Editor;
		editors[1] = relationTypeEditor;
		editors[2] = term2Editor;
		editors[3] = statusEditor;

		terminologyRelationTableViewer.setCellEditors(editors);

		terminologyRelationTableViewer.getTable().addListener(
				SWT.MouseDoubleClick,
				new TerminologyRelationTableViewerListener());

		GridData myGridData = new GridData(GridData.FILL_BOTH);

		// Columns

		term1Column = new TableViewerColumn(terminologyRelationTableViewer,
				SWT.NONE, 0);
		term1Column.getColumn().setText(TerminologicRelationAreaViewPart.TERM1);
		term1Column.getColumn().setWidth(700);
		term1Column.setLabelProvider(new TermLabelProvider(1));

		term1Column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(TerminologyRelationTableViewerSorter.TERM1_SORT);

			}

		});


		
		relationTypeColumn = new TableViewerColumn(
				terminologyRelationTableViewer, SWT.NONE, 1);
		relationTypeColumn.getColumn().setText(
				TerminologicRelationAreaViewPart.RELATION_TYPE);
		relationTypeColumn.getColumn().setWidth(150);
		relationTypeColumn.setLabelProvider(new RelationTypeLabelProvider());

		relationTypeColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(TerminologyRelationTableViewerSorter.RELATION_TYPE_SORT);
				
			}

		});


		
		term2Column = new TableViewerColumn(terminologyRelationTableViewer,
				SWT.NONE, 2);
		term2Column.getColumn().setText(TerminologicRelationAreaViewPart.TERM2);
		term2Column.getColumn().setWidth(700);
		term2Column.setLabelProvider(new TermLabelProvider(2));

		term2Column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(TerminologyRelationTableViewerSorter.TERM2_SORT);

			}

		});

		
		statusColumn = new TableViewerColumn(terminologyRelationTableViewer,
				SWT.CENTER, 3);
		statusColumn.getColumn().setText(
				TerminologicRelationAreaViewPart.RELATION_STATUS);
		statusColumn.getColumn().setWidth(50);
		statusColumn.setLabelProvider(new StatusColumnLabelProvider());

		statusColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(TerminologyRelationTableViewerSorter.STATUS_SORT);

			}

		});

		
		// Table viewer configuration

		terminologyRelationTableViewer.getTable().setLinesVisible(true);
		terminologyRelationTableViewer.getTable().setHeaderVisible(true);
		terminologyRelationTableViewer.getTable().setLayoutData(myGridData);

		terminologyRelationTableViewer.setCellModifier(new TerminologyRelationCellModifier(
						terminologyRelationTableViewer));

		updateRelations();
		
		
		terminologyRelationTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				TableItem[] sel = terminologyRelationTableViewer.getTable().getSelection();

				if (sel.length != 0) {
					
					ITermRelation selectedRelation = (ITermRelation) (sel[0].getData());

					if (selectedRelation != null) {

						ControlerFactoryImpl.getTerminologyControler().setTermRelation(selectedRelation);

					}
				}
				
			}
		});


		generalComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = generalComposite.getClientArea();
				Point preferredSize = terminologyRelationTableViewer.getTable()
						.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width
						- 2
						* terminologyRelationTableViewer.getTable()
								.getBorderWidth();
				if (preferredSize.y > area.height
						+ terminologyRelationTableViewer.getTable()
								.getHeaderHeight()) {

					Point vBarSize = terminologyRelationTableViewer.getTable()
							.getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = terminologyRelationTableViewer.getTable()
						.getSize();

				if (oldSize.x > area.width) {

					term1Column.getColumn().setWidth(width * 35 / 100);
					relationTypeColumn.getColumn().setWidth(width * 20 / 100);
					term2Column.getColumn().setWidth(width * 35 / 100);
					statusColumn.getColumn().setWidth(width * 10 / 100);
					terminologyRelationTableViewer.getTable().setSize(
							area.width, area.height);
				} else {

					terminologyRelationTableViewer.getTable().setSize(
							area.width, area.height);
					term1Column.getColumn().setWidth(width * 35 / 100);
					relationTypeColumn.getColumn().setWidth(width * 20 / 100);
					term2Column.getColumn().setWidth(width * 35 / 100);
					statusColumn.getColumn().setWidth(width * 10 / 100);
				}
			}
		});

		hookContextMenu();
		
		shell = this.getSite().getShell();

		newTermPropertyChangeListener = new TerminologyRelationViewPartNewTermPropertyChangeListener();
		newRelationPropertyChangeListener = new TerminologyRelationViewPartNewRelationPropertyChangeListener();
		newRelationStatusPropertyChangeListener = new TerminologyRelationViewPartNewRelationStatusPropertyChangeListener();
		newTermLabelPropertyChangeListener = new TerminologyRelationViewPartNewTermLabelPropertyChangeListener();
		relationDeletedPropertyChangeListener = new TerminologyRelationViewPartRelationDeletedPropertyChangeListener();
		
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.currentTermEvent, newTermPropertyChangeListener);
		
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.currentRelationEvent, newRelationPropertyChangeListener);
		
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.newRelationStatusEvent, newRelationStatusPropertyChangeListener);

		ControlerFactoryImpl.getTerminologyControler()
		.addPropertyChangeListener(ControlerFactoryImpl.termLabelModifiedEvent, newTermLabelPropertyChangeListener);

		ControlerFactoryImpl.getTerminologyControler()
		.addPropertyChangeListener(ControlerFactoryImpl.relationDeletedEvent, relationDeletedPropertyChangeListener);
	}

	public void updateRelations(){

		relations = getRelationsFromTerm();
		
		terminologyRelationTableViewer.setInput(relations);

	}
	
	public void updateRelationTypes(){
		relationTypes = DatabaseAdapter.getRelationTypes();
	}
	
	public void updateRelationTypesComboBoxCellEditor(){
		this.relationTypeEditor = new ComboBoxCellEditor(
				terminologyRelationTableViewer.getTable(),
				createComboBoxContent(), SWT.READ_ONLY);
		editors[1] = this.relationTypeEditor;
	}
	
	
	@Override
	public void setFocus() {

	}
	
	public TableViewer getTableViewer(){
		return terminologyRelationTableViewer;
	}

	public StatusRelationFilter getStatusFilter(){
		return statusFilter;
	}
	
	public void createStatusFilter(TERMINO_OBJECT_STATE status){
		if (statusFilter != null) {
			terminologyRelationTableViewer.removeFilter(statusFilter);
		}
		statusFilter = new StatusRelationFilter(status);
		terminologyRelationTableViewer.addFilter(statusFilter);
	}
	
	public void removeFilters(){
		if (statusFilter != null) {
			terminologyRelationTableViewer.removeFilter(statusFilter);
			statusFilter = null;
		}
		if (statusAntiFilter != null) {
			terminologyRelationTableViewer.removeFilter(statusAntiFilter);
			statusAntiFilter = null;
		}
	}
	
	public void setSorter(int sortedColumn){
		terminologyRelationTableViewer.setSorter(
				new TerminologyRelationTableViewerSorter(sortedColumn));		
		terminologyRelationTableViewer.getTable().setSortColumn(
				terminologyRelationTableViewer.getTable().getColumn(sortedColumn));
		terminologyRelationTableViewer.getTable().setSortDirection(TerminologyRelationTableViewerSorter.getDirection());

	}
	
	public StatusRelationAntiFilter getStatusAntiFilter(){
		return statusAntiFilter;
	}
	
	public void createStatusAntiFilter(TERMINO_OBJECT_STATE status){
		if (statusAntiFilter != null) {
			terminologyRelationTableViewer.removeFilter(statusAntiFilter);
		}
		statusAntiFilter = new StatusRelationAntiFilter(status);
		terminologyRelationTableViewer.addFilter(statusAntiFilter);
	}
	
	public void removeStatusAntiFilter(){
		terminologyRelationTableViewer.removeFilter(statusAntiFilter);
		statusAntiFilter = null;
	}

	public String[] createComboBoxContent() {
		updateRelationTypes();
		String[] res = new String[relationTypes.size()];
		Iterator<ITypeRelationTermino> it = relationTypes.iterator();
		int i = 0;

		while (it.hasNext()) {
			res[i] = it.next().getLabel();
			i++;
		}

		return res;
	}

	class TerminologicRelationContentProvider implements IStructuredContentProvider {
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((List<ITermRelation>) inputElement).toArray();
		}

		public void dispose() {
			// Do nothing
		}

		@SuppressWarnings("unchecked")
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			relations = (List<ITermRelation>) newInput;
		}
	}

	class RelationTypeLabelProvider extends ColumnLabelProvider {

		public RelationTypeLabelProvider() {
			super();
		}

		public String getText(Object element) {
			String result = ""; //$NON-NLS-1$
			ITermRelation rel = (ITermRelation) element;

			result = rel.getTypeRel().getLabel();

			return result;
		}

	}

	class TermLabelProvider extends ColumnLabelProvider {
		int term;

		public TermLabelProvider(int term) {
			super();
			this.term = term;
		}

		/**
		 * 
		 */
		public String getText(Object element) {
			ITermRelation relation = (ITermRelation) element;

			if (this.term == 1) {
				return relation.getTerm1().getLabel();
			} else if (term == 2) {
				return relation.getTerm2().getLabel();
			}

			return ""; //$NON-NLS-1$
		}

		/**
		 * 
		 */
		public Image getImage(Object element) {
			ITermRelation relation = (ITermRelation) element;
			Image img = null;

			Set<ITerm> variantes = null;

			if (this.term == 1) {
				variantes = relation.getTerm1().getVariantes();
			} else if (term == 2) {
				variantes = relation.getTerm2().getVariantes();
			}

			if (variantes != null) {
				if (variantes.size() > 0) {
					ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
							.getDefault()
							.getImageRegistry()
							.getDescriptor(org.dafoe.terminologiclevel.Activator.STAR_TERM_IMG_ID);

					img = imgDesc.createImage();
				}
			}
			return img;
		}
	}

	class StatusColumnLabelProvider extends OwnerDrawLabelProvider {
		@Override
		protected void measure(Event event, Object element) {
		}

		@Override
		protected void paint(Event event, Object element) {

			ITermRelation relation = (ITermRelation) element;
			TERMINO_OBJECT_STATE status = relation.getState();
			Color color = null;

			if (relation != null) {
				
				if (status == TERMINO_OBJECT_STATE.VALIDATED) {
					
					color = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
					
				} else if (status == TERMINO_OBJECT_STATE.REJECTED) {
					
					color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
					
				} else if (status == TERMINO_OBJECT_STATE.STUDIED) {
					
					color = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
					
				} else if (status == TERMINO_OBJECT_STATE.DELETED) {
					
					color = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
				}
				
				Image img = null;
				
				if (status != TERMINO_OBJECT_STATE.CONCEPTUALIZED){
					
					img = createImage(Display.getDefault(), color);
					
				} else {
					
					String imageId;
					imageId = org.dafoe.terminologiclevel.Activator.CONCEPTUALIZED_IMG_ID;
					ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
					.getDefault()
					.getImageRegistry()
					.getDescriptor(imageId);
					
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
					
					int x = bounds.width > 0 ? bounds.x + bounds.width : bounds.x;
					int y = bounds.height > 0 ? bounds.y + bounds.height : bounds.y;

					event.gc.drawImage(img, x, y);
				}			}
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

	class TerminologyRelationCellModifier implements ICellModifier {
		final TableViewer viewer;

		public TerminologyRelationCellModifier(TableViewer viewer) {
			super();
			this.viewer = viewer;
		}

		public boolean canModify(Object element, String property) {
			final int columnIndex = getPropertyIndex(property);
			return columnIndex == 1;

		}

		public Object getValue(Object element, String property) {

			final ITermRelation termRelation = (ITermRelation) element;

			if (TerminologicRelationAreaViewPart.RELATION_TYPE.equals(property)) {

				String typeRelationLabel = termRelation.getTypeRel().getLabel();
				int i = 0;
				boolean found = false;
				String[] comboBoxContent = createComboBoxContent();

				while ((!found) && (i < comboBoxContent.length)) {

					found = (comboBoxContent[i].compareTo(typeRelationLabel) == 0);

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
				ITypeRelationTermino relationType = null;

				final ITermRelation termRelation = (ITermRelation) tableItem.getData();
				//final ITermRelation termRelation = ControlerFactoryImpl.getTerminologyControler().getTermRelation();
				try {
					if (TerminologicRelationAreaViewPart.RELATION_TYPE
							.equals(property)) {

						String[] comboBoxContent = createComboBoxContent();

						int index = ((Integer) value).intValue();

						String valueString = comboBoxContent[index].trim();

						if (!termRelation.getTypeRel().getLabel().equals(
								valueString)) {

							relationType = relationTypes.get(index);
							DatabaseAdapter.updateRelation(termRelation, null, null, relationType, TERMINO_OBJECT_STATE.UNKNOWN);

						}
					}

				} catch (Exception e) {

					e.printStackTrace();

				}

				viewer.refresh(termRelation);
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

	
	class TerminologyRelationTableViewerListener implements Listener {
		
		public void handleEvent(Event event) {

			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
			
			Point pt = new Point(event.x, event.y);
			TableItem item = terminologyRelationTableViewer.getTable().getItem(pt);
			ITermRelation rel;
			
			if (item == null) {
			
				return;
				
			} else {
				
				rel = (ITermRelation) (item.getData());
				
			}
			
			for (int i = 0; i < terminologyRelationTableViewer.getTable().getColumnCount(); i++) {
				
				Rectangle rect = item.getBounds(i);
				
				if (rect.contains(pt)) {
					
					//int index = terminologyRelationTableViewer.getTable().indexOf(item);
				
					ITerm term = null;

					if (i == 0) {
						
						term = rel.getTerm1();
					
					} else if (i == 2) {
					
						term = rel.getTerm2();
					
					}

					List<ITerm> currentTermFamily = getTermFamily(currentTerm);
					
					if ((i == 0) || (i == 2)) {

						// if the term is the current term or one of its variants, it can be modified
						// by only a term belonging to that family

						// ITerm oldTerm = term;
						
						TermDialogSelector dial;
						
						if ((currentTermFamily.contains(term))){
						
							//term = selectNewTerm(currentTermFamily, null);
							dial = new TermDialogSelector(shell, currentTermFamily);
						
							term = dial.getSelection();
						// otherwise, the term may be only a term that is not the current term or one of its variants	
							
						} else {
							
							//term = selectNewTerm(DatabaseAdapter.getTerms(), currentTermFamily);	
							dial = new TermDialogSelector(shell, DatabaseAdapter.getTerms());
						}						

						term = dial.getSelection();
						
						if (term != null) {

							if (i == 0) {

								DatabaseAdapter.updateRelation(rel, term, null, null, TERMINO_OBJECT_STATE.UNKNOWN);

							} else if (i == 2) {

								DatabaseAdapter.updateRelation(rel, null, term, null, TERMINO_OBJECT_STATE.UNKNOWN);
							}
							
							terminologyRelationTableViewer.refresh(rel);
						}

					} 
				}
			}
		}
	}

	/*
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}
	 */
	
	/*
	private void fillLocalPullDown(IMenuManager manager) {
		// manager.add(validateTermAction);
		// manager.add(new Separator());
	}
	*/

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(terminologyRelationTableViewer.getTable());
		terminologyRelationTableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, terminologyRelationTableViewer);

	}

	
	/*
	private void fillTerminologyRelationContextMenu(IMenuManager manager) {
		Action act = this.validateRelationAction;
		act.setText(Messages.getString("TerminologicRelationAreaViewPart.27")); //$NON-NLS-1$
		manager.add(act);
		act = this.studyRelationAction;
		act.setText(Messages.getString("TerminologicRelationAreaViewPart.28")); //$NON-NLS-1$
		manager.add(act);
		act = this.rejectRelationAction;
		act.setText(Messages.getString("TerminologicRelationAreaViewPart.29")); //$NON-NLS-1$
		manager.add(act);
		// Other plug-ins can contribute there actions here
		// manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	 */
	
	/*
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(this.addRelationAction);
		manager.add(this.removeRelationAction);
		manager.add(new Separator());
		manager.add(this.validateRelationAction);
		manager.add(this.studyRelationAction);
		manager.add(this.rejectRelationAction);
	}

*/
	
	/*
	private void makeActions() {
		ImageDescriptor imgDesc;

		// Add Relation
		
		this.addRelationAction = new Action() {
			
			public void run() {
				ITermRelation relation = createNewRelation();
				ITermRelation newRelation;
				
				if (relation != null) {
					
					newRelation = DatabaseAdapter.createRelation(relation);

					updateRelations();
					
					terminologyRelationTableViewer.setSelection(new StructuredSelection(newRelation));

				}
			}
		};
		
		imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(org.dafoe.terminologiclevel.Activator.NEW_IMG_ID);
		this.addRelationAction.setImageDescriptor(imgDesc);
		this.addRelationAction.setEnabled(true);
		this.addRelationAction.setToolTipText(Messages
				.getString("TerminologicRelationAreaViewPart.30")); //$NON-NLS-1$

		
		// Remove Relation
		
		this.removeRelationAction = new Action() {
			
			public void run() {
				
				TableItem[] sel = terminologyRelationTableViewer.getTable()
						.getSelection();
				ITermRelation relation = null;
				Transaction dTx;

				if (sel != null) {

					relation = (ITermRelation) sel[0].getData();

					List<ITermRelation> rels = new ArrayList<ITermRelation>();
					rels.add(relation);
					
					DatabaseAdapter.deleteRelations(rels);
					
					updateRelations();

				}
			}
		};
		
		imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(org.dafoe.terminologiclevel.Activator.REMOVE_RELATION_IMG_ID);
		this.removeRelationAction.setImageDescriptor(imgDesc);
		this.removeRelationAction.setEnabled(true);
		this.removeRelationAction.setToolTipText(Messages
				.getString("TerminologicRelationAreaViewPart.31")); //$NON-NLS-1$

		
		// Validation Relation
		
		this.validateRelationAction = new Action() {
			
			public void run() {

				updateTermRelationStatus(ITerminoObjectStatus.VALIDATED);

			}
		};
		imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(org.dafoe.terminologiclevel.Activator.VALIDATED_IMG_ID);
		this.validateRelationAction.setImageDescriptor(imgDesc);
		this.validateRelationAction.setEnabled(true);
		this.validateRelationAction.setToolTipText(Messages
				.getString("TerminologicRelationAreaViewPart.32")); //$NON-NLS-1$

		// Study term action
		this.studyRelationAction = new Action() {
			public void run() {

				updateTermRelationStatus(ITerminoObjectStatus.STUDIED);

			}
		};
		imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(org.dafoe.terminologiclevel.Activator.STUDIED_IMG_ID);
		this.studyRelationAction.setImageDescriptor(imgDesc);
		this.studyRelationAction.setEnabled(true);
		this.studyRelationAction.setToolTipText(Messages
				.getString("TerminologicRelationAreaViewPart.33")); //$NON-NLS-1$

		// reject term action
		this.rejectRelationAction = new Action() {
			public void run() {

				updateTermRelationStatus(ITerminoObjectStatus.REJECTED);

			}
		};
		imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(org.dafoe.terminologiclevel.Activator.REJECTED_IMG_ID);
		this.rejectRelationAction.setImageDescriptor(imgDesc);
		this.rejectRelationAction.setEnabled(true);
		this.rejectRelationAction.setToolTipText(Messages
				.getString("TerminologicRelationAreaViewPart.34")); //$NON-NLS-1$

	}
	
	*/

	/*
	private void updateTermRelationStatus(int status) {

		TableItem[] sel = terminologyRelationTableViewer.getTable()
				.getSelection();
		ITermRelation rel = null;
		List<ITerminoObjectStatus> itos = null;
		Transaction dTx;

		if (sel != null) {

			rel = (ITermRelation) sel[0].getData();

			DatabaseAdapter.updateRelation(rel, null, null, null, status);
			
			terminologyRelationTableViewer.refresh();

			terminologyRelationTableViewer.getTable().setSelection(sel[0]);

		}
	}

	 */
	
	public static ITerm selectNewTerm(List<ITerm> terms, List<ITerm> antiFilter) {
		ITerm term = null;
		TermSelectorDialog dialog = new TermSelectorDialog(shell, terms, antiFilter);
		int res = dialog.open();

		if (res == IDialogConstants.OK_ID) {

			term = dialog.getSelection();
		}

		return term;
	}

	public ITermRelation createNewRelation() {
		ITermRelation relation = null;
		TerminologicRelationCreatorDialog dialog = new TerminologicRelationCreatorDialog(
				TerminologicRelationAreaViewPart.this);
		int res = dialog.open();

		if (res == IDialogConstants.OK_ID) {

			relation = dialog.getCreatedRelation();

		}

		return relation;

	}


	public List<ITypeRelationTermino> getRelationTypes() {
		return this.relationTypes;
	}
	
	private List<ITerm> getTermFamily(ITerm term){
		List<ITerm> res = new ArrayList<ITerm>();
		
		if (term.getStarTerm() == null) {
			res.add(term);
			res.addAll(term.getVariantes());
		} else {
			ITerm starTerm = term.getStarTerm();
			res.add(starTerm);
			res.addAll(starTerm.getVariantes());
		}
		
		return res;
	}
	
	private List<ITermRelation> getRelationsFromTerm(){
		
		ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
		
		List<ITermRelation> relationsTerm1 = new ArrayList<ITermRelation>();
		List<ITermRelation> relationsTerm2 = new ArrayList<ITermRelation>();

		if (currentTerm != null){
			relationsTerm1 = UtilTools.setToList(currentTerm.getRelationsWhereInTerm1());
			relationsTerm2 = UtilTools.setToList(currentTerm.getRelationsWhereInTerm2());
			
			relationsTerm1.addAll(relationsTerm2);
		}
		
		return relationsTerm1;
	}
	
	
	class TerminologyRelationViewPartNewRelationPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			/*
			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

			List<ITermRelation> relations = null;
			
			if (currentTerm != null) {
				relations =  getRelationsFromTerm(currentTerm);
			}
			*/
			
			relations = getRelationsFromTerm();
			
			terminologyRelationTableViewer.setInput(relations);			
			
			terminologyRelationTableViewer.setSelection(new StructuredSelection(
					ControlerFactoryImpl.getTerminologyControler().getTermRelation()));
	
		}
	}

	class TerminologyRelationViewPartNewTermPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			/*
			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

			List<ITermRelation> relations = null;
			
			if (currentTerm != null) {
				relations =  DatabaseAdapter.getRelationsFromTerm(currentTerm);
			}

			*/
			relations = getRelationsFromTerm();

			terminologyRelationTableViewer.setInput(relations);			
	
		}
	}

	class TerminologyRelationViewPartNewRelationStatusPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			ITermRelation currentRelation = ControlerFactoryImpl.getTerminologyControler().getTermRelation();

			terminologyRelationTableViewer.refresh(currentRelation);
			
	
		}
	}
	
	class TerminologyRelationViewPartNewTermLabelPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			/*
			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

			List<ITermRelation> relations = null;
			
			if (currentTerm != null) {
				relations =  DatabaseAdapter.getRelationsFromTerm(currentTerm);
			}
			*/
			
			relations = getRelationsFromTerm();

			terminologyRelationTableViewer.setInput(relations);			
			
		}
	}


	class TerminologyRelationViewPartRelationDeletedPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			/*
			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

			List<ITermRelation> relations = null;
			
			if (currentTerm != null) {
				relations =  DatabaseAdapter.getRelationsFromTerm(currentTerm);
			}
			*/
			
			relations = getRelationsFromTerm();

			terminologyRelationTableViewer.setInput(relations);			
			
		}
	}
}
