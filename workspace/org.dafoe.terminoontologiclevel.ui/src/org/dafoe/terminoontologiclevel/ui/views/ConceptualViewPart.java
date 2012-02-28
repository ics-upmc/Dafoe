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
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IClassInstance;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.dafoe.terminoontologiclevel.ui.dialog.ChoiceOntoObjectTypeDialog;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.themes.IThemeManager;

public class ConceptualViewPart extends ViewPart {

	public final static String ID = "org.dafoe.terminoontologiclevel.ui.views.ConceptualViewID"; //$NON-NLS-1$

	private ITerminoConcept currentTerminoConcept;
	
	private Color ontologicColor;
	
	private Composite backComp, comp;
	
	private Text terminoConceptText;
	
	private TableViewer mappingTableViewer;
	
	private TableViewerColumn objectTypeColumn;
	
	private TableViewerColumn objectNameColumn;
	
	private static int lastSelectedColumn;
	
	private static int direction;

	public ConceptualViewPart() {
		currentTerminoConcept = null;
		IThemeManager themeManager = PlatformUI.getWorkbench()
				.getThemeManager();
		ontologicColor = themeManager
				.getTheme("org.dafoe.ontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public void createPartControl(Composite parent) {

		FontRegistry fontRegistry = new FontRegistry(parent.getShell()
				.getDisplay());

		fontRegistry.put(
				"TC", new FontData[] { new FontData("Arial", 10, SWT.BOLD) }); //$NON-NLS-1$ //$NON-NLS-2$

		backComp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		backComp.setLayout(layout);
		backComp.setBackground(ontologicColor);

		GridData gd;

		comp = new Composite(backComp, SWT.NONE);
		layout = new GridLayout(1, false);
		layout.horizontalSpacing = 5;
		layout.verticalSpacing = 5;
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		comp.setLayout(layout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		comp.setLayoutData(gd);

		//

		terminoConceptText = new Text(comp, SWT.BORDER | SWT.CENTER
				| SWT.READ_ONLY);
		terminoConceptText.setEditable(false);
		terminoConceptText.setFont(fontRegistry.get("TC")); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		terminoConceptText.setLayoutData(gd);

		//

		mappingTableViewer = new TableViewer(comp, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.FULL_SELECTION | SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		mappingTableViewer.getTable().setLayoutData(gd);
		mappingTableViewer.getTable().setLinesVisible(true);
		mappingTableViewer.getTable().setHeaderVisible(true);
		mappingTableViewer.setContentProvider(new OntoObjectContentProvider());

		// Columns

		objectTypeColumn = new TableViewerColumn(mappingTableViewer, SWT.NONE,
				0);
		objectTypeColumn.getColumn().setText(
				Messages.getString("ConceptualViewPart.2")); //$NON-NLS-1$
		// objectTypeColumn.getColumn().setWidth(200);
		objectTypeColumn.setLabelProvider(new OntoObjectLabelProvider(1));

		objectTypeColumn.getColumn().addSelectionListener(
				new SelectionAdapter() {

					public void widgetSelected(SelectionEvent e) {

						MappingTableViewerSorter sorter = new MappingTableViewerSorter(
								MappingTableViewerSorter.TYPE_SORT);
						mappingTableViewer.setSorter(sorter);
						mappingTableViewer.getTable().setSortColumn(
								objectTypeColumn.getColumn());
						mappingTableViewer.getTable().setSortDirection(
								direction);

					}

				});

		objectNameColumn = new TableViewerColumn(mappingTableViewer, SWT.NONE,
				1);
		objectNameColumn.getColumn().setText(
				Messages.getString("ConceptualViewPart.3")); //$NON-NLS-1$
		// objectNameColumn.getColumn().setWidth(50);
		objectNameColumn.setLabelProvider(new OntoObjectLabelProvider(2));

		objectNameColumn.getColumn().addSelectionListener(
				new SelectionAdapter() {

					public void widgetSelected(SelectionEvent e) {

						MappingTableViewerSorter sorter = new MappingTableViewerSorter(
								MappingTableViewerSorter.LABEL_SORT);
						mappingTableViewer.setSorter(sorter);
						mappingTableViewer.getTable().setSortColumn(
								objectNameColumn.getColumn());
						mappingTableViewer.getTable().setSortDirection(
								direction);

					}

				});

		comp.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = comp.getClientArea();
				Point preferredSize = mappingTableViewer.getTable()
						.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2
						* mappingTableViewer.getTable().getBorderWidth();
				if (preferredSize.y > area.height
						+ mappingTableViewer.getTable().getHeaderHeight()) {

					Point vBarSize = mappingTableViewer.getTable()
							.getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = mappingTableViewer.getTable().getSize();

				if (oldSize.x > area.width) {
					objectTypeColumn.getColumn().setWidth(200);
					objectNameColumn.getColumn().setWidth(width - 200);
					mappingTableViewer.getTable().setSize(area.width,
							area.height);
				} else {

					mappingTableViewer.getTable().setSize(area.width,
							area.height);
					objectTypeColumn.getColumn().setWidth(200);
					objectNameColumn.getColumn().setWidth(width - 200);
				}
			}
		});

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.currentTerminoConceptEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateInformation();
							}

						});

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(ControlerFactoryImpl.renameTCEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateInformation();
							}

						});

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.newTCToOntoObjectMappingEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateInformation();
							}

						});

		mappingTableViewer.getTable().addListener(SWT.MouseDoubleClick,
				new AssociatedConceptTableViewerListener());

		hookContextMenu();

		updateInformation();
		
		createDropTargetListerner();
	}

	//

	private void updateInformation() {
		currentTerminoConcept = ControlerFactoryImpl.getTerminoOntoControler()
				.getCurrentTerminoConcept();

		if (currentTerminoConcept != null) {
			terminoConceptText.setText(currentTerminoConcept.getLabel());
			mappingTableViewer.setInput(UtilTools
					.setToList(currentTerminoConcept.getMappedOntoObjects()));
		}

	}

	//

	private void hookContextMenu() {

		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(mappingTableViewer.getTable());
		mappingTableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, mappingTableViewer);
	}

	//

	@Override
	public void setFocus() {
	}

	//

	public void deleteTCtoOntoObjectMapping() {

		if (currentTerminoConcept != null) {

			TableItem[] tableItems = mappingTableViewer.getTable()
					.getSelection();

			if (tableItems.length == 1) {

				IOntoObject sel = (IOntoObject) (tableItems[0].getData());

				DatabaseAdapter
						.deleteTCtoOntoObject(currentTerminoConcept, sel);

				ControlerFactoryImpl.getTerminoOntoControler()
						.setNewTCToOntoObjectMapping();

			}

		}
	}

	//

	class OntoObjectContentProvider implements IStructuredContentProvider {

		@SuppressWarnings("unchecked")
		@Override
		public Object[] getElements(Object inputElement) {
			return ((List<IOntoObject>) inputElement).toArray();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	//

	class OntoObjectLabelProvider extends ColumnLabelProvider {
		int col;

		public OntoObjectLabelProvider(int col) {
			super();
			this.col = col;
		}

		/**
		 * 
		 */
		public String getText(Object element) {
			IOntoObject ontoObject = (IOntoObject) element;
			String value = null;

			if (this.col == 1) {

				if (ontoObject instanceof IClass) {

					value = Messages.getString("ConceptualViewPart.4"); //$NON-NLS-1$

				} else if (ontoObject instanceof IObjectProperty) {

					value = Messages.getString("ConceptualViewPart.5"); //$NON-NLS-1$

				} else if (ontoObject instanceof IDatatypeProperty) {

					value = Messages.getString("ConceptualViewPart.6"); //$NON-NLS-1$

				} else if (ontoObject instanceof IClassInstance) {

					value = Messages.getString("ConceptualViewPart.7"); //$NON-NLS-1$

				}

			} else if (col == 2) {

				if (ontoObject instanceof IClass) {
					value = ((IClass) ontoObject).getLabel();

				} else if (ontoObject instanceof IObjectProperty) {

					value = ((IObjectProperty) ontoObject).getLabel();

				} else if (ontoObject instanceof IDatatypeProperty) {

					value = ((IDatatypeProperty) ontoObject).getLabel();

				} else if (ontoObject instanceof IClassInstance) {

					value = ((IClassInstance) ontoObject).getId() + ""; //$NON-NLS-1$
				}

			}

			return value;
		}

		/**
		 * 
		 */

		public Image getImage(Object element) {
			IOntoObject ontoObject = (IOntoObject) element;
			String imageId = null;
			Image img = null;
			ImageDescriptor imgDesc;

			if (this.col == 1) {

				System.out.println(ontoObject.getClass().getName());

				if (ontoObject instanceof IClass) {
					imageId = org.dafoe.terminoontologiclevel.ui.Activator.CLASS_IMG_ID;
				} else if (ontoObject instanceof IObjectProperty) {
					imageId = org.dafoe.terminoontologiclevel.ui.Activator.OBJECT_PROPERTY_IMG_ID;
				} else if (ontoObject instanceof IDatatypeProperty) {
					imageId = org.dafoe.terminoontologiclevel.ui.Activator.DATATYPE_PROPERTY_IMG_ID;
				} else if (ontoObject instanceof IClassInstance) {
					imageId = org.dafoe.terminoontologiclevel.ui.Activator.INSTANCE_IMG_ID;
				}

				if (imageId != null) {
					imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
							.getDefault().getImageRegistry().getDescriptor(
									imageId);
					img = imgDesc.createImage();

				}
			}

			return img;
		}
	}

	//

	class MappingTableViewerSorter extends ViewerSorter {

		private final static int TYPE_SORT = 0;
		private final static int LABEL_SORT = 1;

		private int col;

		/**
		 *
		 */
		public MappingTableViewerSorter(int col) {
			super();

			this.col = col;
			if (this.col == lastSelectedColumn) {
				if (direction == SWT.UP) {
					direction = SWT.DOWN;
				} else {
					direction = SWT.UP;
				}
			} else {
				lastSelectedColumn = this.col;
				direction = SWT.UP;
			}
		}

		/*
		 * (non-Javadoc)
		 */

		public int compare(Viewer viewer, Object o1, Object o2) {
			int res = 0;

			IOntoObject ontoObject1 = (IOntoObject) o1;
			IOntoObject ontoObject2 = (IOntoObject) o2;

			switch (col) {
			case TYPE_SORT:
				int type1,
				type2;
				type1 = getLevel(ontoObject1);
				type2 = getLevel(ontoObject2);

				res = type1 - type2;
				res = res < 0 ? -1 : (res > 0) ? 1 : 0;

				break;

			case LABEL_SORT:
				String label1,
				label2;
				label1 = getLabel(ontoObject1);
				label2 = getLabel(ontoObject2);
				res = label1.compareToIgnoreCase(label2);
				break;
			}

			if (direction == SWT.DOWN)
				res = -res;

			return res;

		}

		private int getLevel(IOntoObject ontoObject) {
			int level = 0;

			if (ontoObject instanceof IClass) {
				level = 0;
			} else if (ontoObject instanceof IObjectProperty) {
				level = 1;
			} else if (ontoObject instanceof IDatatypeProperty) {
				level = 2;
			} else if (ontoObject instanceof IClassInstance) {
				level = 3;
			}

			return level;
		}

		private String getLabel(IOntoObject ontoObject) {
			String label = ""; //$NON-NLS-1$

			if (ontoObject instanceof IClass) {
				label = ((IClass) ontoObject).getLabel();
			} else if (ontoObject instanceof IObjectProperty) {
				label = ((IObjectProperty) ontoObject).getLabel();
			} else if (ontoObject instanceof IDatatypeProperty) {
				label = ((IDatatypeProperty) ontoObject).getLabel();
			} else if (ontoObject instanceof IClassInstance) {
				label = ((IClassInstance) ontoObject).getId().toString();
			}

			return label;
		}
	}

	//

	class AssociatedConceptTableViewerListener implements Listener {
		public void handleEvent(Event event) {

			Point pt = new Point(event.x, event.y);
			TableItem item = mappingTableViewer.getTable().getItem(pt);

			if (item != null) {

				IOntoObject ontoObject = (IOntoObject) (item.getData());

				if (ontoObject instanceof IClass) {
					ControlerFactoryImpl.getOntoControler().setCurrentClass(
							(IClass) ontoObject);

					switchPerspective("org.dafoe.ontologiclevel.perspectiveClassesId");

				} else if ((ontoObject instanceof IObjectProperty)
						|| (ontoObject instanceof IDatatypeProperty)) {
					ControlerFactoryImpl.getOntoControler().setCurrentProperty(
							(IProperty) ontoObject);

					switchPerspective("org.dafoe.ontologiclevel.perspectivePropertiesId");

				}
				if (ontoObject instanceof IClassInstance) {
					ControlerFactoryImpl.getOntoControler().setCurrentInstance(
							(IClassInstance) ontoObject);
					switchPerspective("org.dafoe.ontologiclevel.perspectiveIndividualsId");

				}
			}
		}
	}

	//

	private void switchPerspective(String perspectiveId) {

		final IWorkbenchWindow workbenchWindow = this.getSite()
				.getWorkbenchWindow();

		IPerspectiveDescriptor newPerspective = null;

		IPerspectiveDescriptor[] perspectives = workbenchWindow.getWorkbench()
				.getPerspectiveRegistry().getPerspectives();

		for (int i = 0; i < perspectives.length; i++) {
			if (perspectives[i].getId().compareTo(perspectiveId) == 0) { //$NON-NLS-1$
				newPerspective = perspectives[i];
			}
		}

		if (workbenchWindow.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getPerspective() != newPerspective) {
			workbenchWindow.getActivePage().setPerspective(newPerspective);
		}

	}
	
	
    
	 // Create the drop target on the table of ConceptualViewPart.table
	private void createDropTargetListerner(){
		
    DropTarget target = new DropTarget(mappingTableViewer.getTable(), DND.DROP_MOVE|DND.DROP_COPY|DND.DROP_DEFAULT);
	    
		final TextTransfer textTransfer = TextTransfer.getInstance(); 
		Transfer[] types = new Transfer[] {textTransfer}; 
		target.setTransfer(types); 
		
		target.addDropListener(new DropTargetAdapter() {
		
			public void dragEnter(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = (event.operations & DND.DROP_COPY) != 0 ? DND.DROP_COPY : DND.DROP_NONE;
				}

				for (int i = 0, n = event.dataTypes.length; i < n; i++) {
					if (TextTransfer.getInstance().isSupportedType(event.dataTypes[i])) {
						event.currentDataType = event.dataTypes[i];
					}
				}
			}

			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
			}	
			public void drop(DropTargetEvent event) {
				
				System.out.println(event.detail);
					//ITerminoConcept currentDroppedTC= ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoConcept();
                    System.out.println("choice diag");
                     ChoiceOntoObjectTypeDialog diag= new ChoiceOntoObjectTypeDialog(mappingTableViewer.getTable().getShell(), "Termino-Concept to Onto-Object mapping");
                     
                     diag.open();
				

			}
		});
		
		
	}
	
	
//class NewBuildedOntoObjectPropertyChangeListener implements PropertyChangeListener {
//		
//		public void propertyChange(PropertyChangeEvent evt) {
//		
//			
//			try {
//				mappingTableViewer.refresh();	
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//
//		}
//	}  
}
