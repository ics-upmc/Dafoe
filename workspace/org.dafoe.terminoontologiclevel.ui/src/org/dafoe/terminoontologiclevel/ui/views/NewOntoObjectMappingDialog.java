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

import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IClassInstance;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class NewOntoObjectMappingDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages.getString("NewOntoObjectMappingDialog.0"); //$NON-NLS-1$
	private static String DIALOG_MESSAGE = Messages.getString("NewOntoObjectMappingDialog.1"); //$NON-NLS-1$

	private static final String CLASS = Messages.getString("NewOntoObjectMappingDialog.2"); //$NON-NLS-1$
	private static final String OBJECT_PROPERTY = Messages.getString("NewOntoObjectMappingDialog.3"); //$NON-NLS-1$
	private static final String DATATYPE_PROPERTY = Messages.getString("NewOntoObjectMappingDialog.4"); //$NON-NLS-1$
	private static final String INSTANCE = Messages.getString("NewOntoObjectMappingDialog.5"); //$NON-NLS-1$
	
	private String title;
	private String message;

	private ITerminoConcept currentTerminoConcept;
	private Composite inComposite;
	private Text terminoConceptText;
	private Color ontologicColor, terminoontologicColor;
	private TableViewer mappingTableViewer;
	private TableViewerColumn objectTypeColumn, objectNameColumn;
	private Composite existingMappingsComposite;
	private Composite ontoComposite, ontoStackComposite;
	private StackLayout stackLayout;
	private ClassWidget page0;
	private PropertyWidget page1, page2;
	
	private String selectedPage;
	
	private Action removeTCtoOntoObjectMappingAction;
	
	private static int lastSelectedColumn;
	private static int direction;

	private Shell shell;
		
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	
	public NewOntoObjectMappingDialog(Shell parent, ConceptualViewPart viewPart) {
		super(parent);
		
		setShellStyle(SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE);
		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;		

		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		terminoontologicColor = themeManager.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor");  //$NON-NLS-1$ //$NON-NLS-2$
		ontologicColor = themeManager.getTheme("org.dafoe.ontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor");  //$NON-NLS-1$ //$NON-NLS-2$

		this.setTitleImage(org.dafoe.terminoontologiclevel.ui.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminoontologiclevel.ui.Activator.DIALOG_IMG_ID));
		
		shell = parent;
	}

	//
	
	public boolean close(){
		return super.close();
	}

	//
	
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		this.setTitle(this.title);
		this.setMessage(this.message);
		this.getShell().setText(this.title);
		return contents;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		currentTerminoConcept = ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoConcept();
		
		FontRegistry fontRegistry = new FontRegistry(this.getShell().getDisplay());

		fontRegistry.put("TC", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		final Composite container = (Composite) super.createDialogArea(parent);

		inComposite = new Composite(container, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 2;
		inComposite.setLayout(layout);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		inComposite.setLayoutData(gd);

		//
		
		Composite terminoOntoComposite = new Composite(inComposite, SWT.NONE);
		terminoOntoComposite.setBackground(terminoontologicColor);
		layout = new GridLayout();
		terminoOntoComposite.setLayout(layout);
		gd = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
		terminoOntoComposite.setLayoutData(gd);
		
		terminoConceptText = new Text(terminoOntoComposite, SWT.BORDER | SWT.CENTER | SWT.READ_ONLY);
		terminoConceptText.setEditable(false);
		terminoConceptText.setFont(fontRegistry.get("TC")); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		terminoConceptText.setLayoutData(gd);

		//
		
		Composite existingMappingsColorComposite = new Composite(inComposite, SWT.NONE);
		existingMappingsColorComposite.setBackground(ontologicColor);
		layout = new GridLayout(); 
		existingMappingsColorComposite.setLayout(layout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		existingMappingsColorComposite.setLayoutData(gd);
		
		existingMappingsComposite = new Composite(existingMappingsColorComposite, SWT.NONE);
		layout = new GridLayout(); 
		existingMappingsComposite.setLayout(layout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		existingMappingsComposite.setLayoutData(gd);
		
		Label associatedObjectLabel = new Label(existingMappingsComposite, SWT.NONE);
		associatedObjectLabel.setText(Messages.getString("NewOntoObjectMappingDialog.6") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		associatedObjectLabel.setFont(fontRegistry.get("TC")); //$NON-NLS-1$

		//
		
		mappingTableViewer = new TableViewer(existingMappingsComposite,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		mappingTableViewer.getTable().setLayoutData(gd);
		mappingTableViewer.getTable().setLinesVisible(true);
		mappingTableViewer.getTable().setHeaderVisible(true);
		mappingTableViewer.setContentProvider(new OntoObjectContentProvider());
		
		mappingTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				TableItem[] sel = mappingTableViewer.getTable().getSelection();
				removeTCtoOntoObjectMappingAction.setEnabled(false);
				
				if (sel.length != 0) {
					removeTCtoOntoObjectMappingAction.setEnabled(true);
					
					IOntoObject ontoObject = (IOntoObject)(sel[0].getData());
					
					if ((ontoObject instanceof IClass) && (selectedPage.compareTo(NewOntoObjectMappingDialog.CLASS) == 0)){
						
						page0.showSelection((IClass)ontoObject);
						
					} else if ((ontoObject instanceof IObjectProperty) && (selectedPage.compareTo(NewOntoObjectMappingDialog.OBJECT_PROPERTY) == 0)){
						
						page1.showSelection((IObjectProperty)ontoObject);
						
					} else if ((ontoObject instanceof IDatatypeProperty) && (selectedPage.compareTo(NewOntoObjectMappingDialog.DATATYPE_PROPERTY) == 0)){
						
						page2.showSelection((IDatatypeProperty)ontoObject);
						
					}
					
				}
				
			}
		
		});
		
		// Columns
		
		objectTypeColumn = new TableViewerColumn(mappingTableViewer, SWT.NONE, 0);
		objectTypeColumn.getColumn().setText(Messages.getString("NewOntoObjectMappingDialog.9")); //$NON-NLS-1$
		//objectTypeColumn.getColumn().setWidth(200);
		objectTypeColumn.setLabelProvider(new OntoObjectLabelProvider(1));

		objectTypeColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				MappingTableViewerSorter sorter = new MappingTableViewerSorter(MappingTableViewerSorter.TYPE_SORT);
				mappingTableViewer.setSorter(sorter);		
				mappingTableViewer.getTable().setSortColumn(objectTypeColumn.getColumn());
				mappingTableViewer.getTable().setSortDirection(direction);
	
			}

		});
	
		objectNameColumn = new TableViewerColumn(mappingTableViewer, SWT.NONE, 1);
		objectNameColumn.getColumn().setText(Messages.getString("NewOntoObjectMappingDialog.10")); //$NON-NLS-1$
		//objectNameColumn.getColumn().setWidth(50);
		objectNameColumn.setLabelProvider(new OntoObjectLabelProvider(2));

		objectNameColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				MappingTableViewerSorter sorter = new MappingTableViewerSorter(MappingTableViewerSorter.LABEL_SORT);
				mappingTableViewer.setSorter(sorter);		
				mappingTableViewer.getTable().setSortColumn(objectNameColumn.getColumn());
				mappingTableViewer.getTable().setSortDirection(direction);

			}

		});
		
		existingMappingsComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = existingMappingsComposite.getClientArea();
				Point preferredSize = mappingTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * mappingTableViewer.getTable().getBorderWidth();
				if (preferredSize.y > area.height + mappingTableViewer.getTable() .getHeaderHeight()) {

					Point vBarSize = mappingTableViewer.getTable().getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = mappingTableViewer.getTable().getSize();

				if (oldSize.x > area.width) {
					objectTypeColumn.getColumn().setWidth(200);
					objectNameColumn.getColumn().setWidth(width - 200);
					mappingTableViewer.getTable().setSize(area.width, area.height);
				} else {

					mappingTableViewer.getTable().setSize(area.width, area.height);
					objectTypeColumn.getColumn().setWidth(200);
					objectNameColumn.getColumn().setWidth(width - 200);
				}
			}
		});
		
	    DropTarget target = new DropTarget(mappingTableViewer.getTable(), DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);
	    
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
				if (TextTransfer.getInstance().isSupportedType(event.currentDataType)) {
					// Get the dropped data
					String data = (String) event.data;
					System.out.println("Drop: " + data); //$NON-NLS-1$
					
					String[] tmp = data.split("#"); //$NON-NLS-1$
					String type = tmp[0];
					int id = Integer.parseInt(tmp[1]);
					
					if (type.compareTo("IClass") == 0) { //$NON-NLS-1$
						
						IClass cl = DatabaseAdapter.getClassById(id);
						
						linkToClass(cl);

					} else if (type.compareTo("IObjectProperty") == 0) { //$NON-NLS-1$
						
						IObjectProperty prop = DatabaseAdapter.getObjectPropertyById(id);
						
						linkToObjectProperty(prop);

					} else if (type.compareTo("IDatatypeProperty") == 0) { //$NON-NLS-1$
						
						IDatatypeProperty prop = DatabaseAdapter.getDataTypePropertyById(id);
					
						linkToDatatypeProperty(prop);
					}
				}
			}
		});
		
		//
		
		ontoComposite = new Composite(inComposite, SWT.NONE);
		ontoComposite.setBackground(ontologicColor);
		layout = new GridLayout(); 
		ontoComposite.setLayout(layout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		ontoComposite.setLayoutData(gd);
		
		ontoStackComposite = new Composite(ontoComposite, SWT.NONE);
		stackLayout = new StackLayout();
		ontoStackComposite.setLayout(stackLayout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		ontoStackComposite.setLayoutData(gd);

		// Page 0: classes
		page0 = new ClassWidget(ontoStackComposite, SWT.NONE);
		
		// Page 1: object properties
	    page1 = new PropertyWidget(ontoStackComposite, SWT.NONE, false);

		// Page 2: datatype properties
	    page2 = new PropertyWidget(ontoStackComposite, SWT.NONE, true);
	    
	    stackLayout.topControl = page0;
	    selectedPage = NewOntoObjectMappingDialog.CLASS;
	    ontoStackComposite.layout();
		
		//
		
		Composite ontoColorSelectorComposite = new Composite(inComposite, SWT.NONE);
		ontoColorSelectorComposite.setBackground(ontologicColor);
		layout = new GridLayout();
		ontoColorSelectorComposite.setLayout(layout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		ontoColorSelectorComposite.setLayoutData(gd);
		
		Composite ontoSelectorComposite = new Composite(ontoColorSelectorComposite, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		ontoSelectorComposite.setLayout(layout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		ontoSelectorComposite.setLayoutData(gd);

		Label newObjectLabel = new Label(ontoSelectorComposite, SWT.NONE);
		newObjectLabel.setText(Messages.getString("NewOntoObjectMappingDialog.16") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		newObjectLabel.setFont(fontRegistry.get("TC")); //$NON-NLS-1$
		
		new Label(ontoSelectorComposite, SWT.NONE);
		
		Listener radioGroupListener = new Listener () {
			public void handleEvent (Event event) {
				Button button = (Button) event.widget;

				if (button.getSelection()) {
					updateOntoCompositeContent(button.getText());
				}
				
			}
		};

		new Label(ontoSelectorComposite, SWT.NONE);
		Button conceptButton = new Button (ontoSelectorComposite, SWT.RADIO);
		conceptButton.setText(NewOntoObjectMappingDialog.CLASS);
		conceptButton.setSelection(true);
		conceptButton.addListener(SWT.Selection, radioGroupListener);
		
		new Label(ontoSelectorComposite, SWT.NONE);
		Button objectPropertyButton = new Button (ontoSelectorComposite, SWT.RADIO);
		objectPropertyButton.setText(NewOntoObjectMappingDialog.OBJECT_PROPERTY);
		objectPropertyButton.addListener(SWT.Selection, radioGroupListener);
		
		new Label(ontoSelectorComposite, SWT.NONE);
		Button datatypePropertyButton = new Button (ontoSelectorComposite, SWT.RADIO);
		datatypePropertyButton.setText(NewOntoObjectMappingDialog.DATATYPE_PROPERTY);
		datatypePropertyButton.addListener(SWT.Selection, radioGroupListener);
		
		new Label(ontoSelectorComposite, SWT.NONE);
		Button instancePropertyButton = new Button (ontoSelectorComposite, SWT.RADIO);
		instancePropertyButton.setText(NewOntoObjectMappingDialog.INSTANCE);
		instancePropertyButton.addListener(SWT.Selection, radioGroupListener);
		
		makeActions();
		
		updateInformation();
		
		return container;
	}

	//
	
	private void linkToClass(IClass cl){
		
		if (!currentTerminoConcept.getMappedOntoObjects().contains(cl)) {
			
			DatabaseAdapter.linkTCtoClass(currentTerminoConcept, cl);
			
			updateInformation();
						
		} else {
			
			String msg = "The " + cl.getLabel() + Messages.getString("NewOntoObjectMappingDialog.20"); //$NON-NLS-2$
			msg += "\n\n"; //$NON-NLS-1$
			msg += Messages.getString("NewOntoObjectMappingDialog.21"); //$NON-NLS-1$
			
			MessageDialog.openWarning(shell, Messages.getString("NewOntoObjectMappingDialog.22"), msg); //$NON-NLS-1$
			
		}
		
	}
	
	//
	
	private void linkToObjectProperty(IObjectProperty prop){
		
		if (!currentTerminoConcept.getMappedOntoObjects().contains(prop)) {
			
			DatabaseAdapter.linkTCtoObjectProperty(currentTerminoConcept, prop);
			
			updateInformation();
						
		} else {
			
			String msg = Messages.getString("NewOntoObjectMappingDialog.23") + prop.getLabel() + Messages.getString("NewOntoObjectMappingDialog.24"); //$NON-NLS-1$ //$NON-NLS-2$
			msg += "\n\n"; //$NON-NLS-1$
			msg += Messages.getString("NewOntoObjectMappingDialog.25"); //$NON-NLS-1$
			
			MessageDialog.openWarning(shell, Messages.getString("NewOntoObjectMappingDialog.26"), msg); //$NON-NLS-1$
			
		}
		
	}

	//
	
	private void linkToDatatypeProperty(IDatatypeProperty prop){
		
		if (!currentTerminoConcept.getMappedOntoObjects().contains(prop)) {
			
			DatabaseAdapter.linkTCtoDatatypeProperty(currentTerminoConcept, prop);
			
			updateInformation();
						
		} else {
			
			String msg = Messages.getString("NewOntoObjectMappingDialog.27") + prop.getLabel() + Messages.getString("NewOntoObjectMappingDialog.28"); //$NON-NLS-1$ //$NON-NLS-2$
			msg += "\n\n"; //$NON-NLS-1$
			msg += Messages.getString("NewOntoObjectMappingDialog.29"); //$NON-NLS-1$
			
			MessageDialog.openWarning(shell, Messages.getString("NewOntoObjectMappingDialog.30"), msg); //$NON-NLS-1$
			
		}
		
	}
	
	//
	
	private void updateOntoCompositeContent(String context){
		
		if (context.compareTo(NewOntoObjectMappingDialog.CLASS) == 0) {
			
			stackLayout.topControl = page0;			
			selectedPage = NewOntoObjectMappingDialog.CLASS;
			
		} else if(context.compareTo(NewOntoObjectMappingDialog.OBJECT_PROPERTY) == 0) {
			
			stackLayout.topControl = page1;			
			selectedPage = NewOntoObjectMappingDialog.OBJECT_PROPERTY;
			
		} else if(context.compareTo(NewOntoObjectMappingDialog.DATATYPE_PROPERTY) == 0) {
			
			stackLayout.topControl = page2;	
			selectedPage = NewOntoObjectMappingDialog.DATATYPE_PROPERTY;
		}
		
		ontoStackComposite.layout();
	}
	
	//
	
	private void updateInformation() {
		
		currentTerminoConcept = ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoConcept();
		
		if (currentTerminoConcept != null) {
			
			terminoConceptText.setText(currentTerminoConcept.getLabel());
			mappingTableViewer.setInput(UtilTools.setToList(currentTerminoConcept.getMappedOntoObjects()));
		}
		
	}
	//
		
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	
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
					
					value = NewOntoObjectMappingDialog.CLASS;
					
				} else if (ontoObject instanceof IObjectProperty) {
					
					value = NewOntoObjectMappingDialog.OBJECT_PROPERTY;
					
				} else if (ontoObject instanceof IDatatypeProperty) {
					
					value = NewOntoObjectMappingDialog.DATATYPE_PROPERTY;
					
				} else if (ontoObject instanceof IClassInstance) {
					
					value = NewOntoObjectMappingDialog.INSTANCE;
					
				}
				
			} else if (col == 2) {
				
				
				if (ontoObject instanceof IClass) {
					value = ((IClass)ontoObject).getLabel();
					
				} else if (ontoObject instanceof IObjectProperty) {
					
					value = ((IObjectProperty)ontoObject).getLabel();
					
				} else if (ontoObject instanceof IDatatypeProperty) {
					
					value = ((IDatatypeProperty)ontoObject).getLabel();
					
				} else if (ontoObject instanceof IClassInstance) {
					
					value = ((IClassInstance)ontoObject).getId() + ""; //$NON-NLS-1$
				}
				
			} 

			return value;
		}

		/**
		 * 
		 */

		public Image getImage(Object element){
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
						.getDefault().getImageRegistry().getDescriptor(imageId);
					img = imgDesc.createImage();

				}
			}
			
			return img;
		}
	}

	//
	
	private void makeActions() {
		ImageDescriptor imgDesc;
		
		this.removeTCtoOntoObjectMappingAction = new Action() {
			
			public void run() {
				
				TableItem[] tableItems = mappingTableViewer.getTable().getSelection();

				if (tableItems != null) {

					IOntoObject sel = (IOntoObject)(tableItems[0].getData());
					
					DatabaseAdapter.deleteTCtoOntoObject(currentTerminoConcept, sel);
					
					ControlerFactoryImpl.getTerminoOntoControler().setNewTCToOntoObjectMapping();

				}
			}
		};
		
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(org.dafoe.terminoontologiclevel.ui.Activator.REMOVE_IMG_ID);
		this.removeTCtoOntoObjectMappingAction.setImageDescriptor(imgDesc);
		this.removeTCtoOntoObjectMappingAction.setEnabled(true);
		this.removeTCtoOntoObjectMappingAction.setToolTipText(Messages.getString("NewOntoObjectMappingDialog.32")); //$NON-NLS-1$

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
				int type1, type2;
				type1 = getLevel(ontoObject1);
				type2 = getLevel(ontoObject2);
				
				res = type1 - type2;
				res = res < 0 ? -1 : (res > 0) ? 1 : 0;
				
				break;
				
			case LABEL_SORT:
				String label1, label2;
				label1 = getLabel(ontoObject1);
				label2 = getLabel(ontoObject2);
				res = label1.compareToIgnoreCase(label2);
				break;
			}

			if (direction == SWT.DOWN)
				res = -res;
			
			return res;

		}
		
		private int getLevel(IOntoObject ontoObject){
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

		private String getLabel(IOntoObject ontoObject){
			String label = ""; //$NON-NLS-1$
			
			if (ontoObject instanceof IClass) {
				label = ((IClass)ontoObject).getLabel();
			} else if (ontoObject instanceof IObjectProperty) {
				label = ((IObjectProperty)ontoObject).getLabel();
			} else if (ontoObject instanceof IDatatypeProperty) {
				label = ((IDatatypeProperty)ontoObject).getLabel();
			} else if (ontoObject instanceof IClassInstance) {
				label = ((IClassInstance)ontoObject).getId().toString();
			}

			return label;
		}
	}

	//
	
	public static void main(String[] args) {
	    Display display = new Display();
	    final Shell shell = new Shell(display);
	    
	    shell.setLayout(new GridLayout());
	    shell.setSize(300, 150);

	    final Button openDialog = new Button(shell, SWT.PUSH);
	    openDialog.setText("Open ..."); //$NON-NLS-1$

	    openDialog.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent e) {
	    		NewOntoObjectMappingDialog dialog = new NewOntoObjectMappingDialog(shell, null);

	    		dialog.open();
	    	}

	    	public void widgetDefaultSelected(SelectionEvent e) {
	    	}
	    });

	    shell.open();

	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }

	    display.dispose();
	    
	}
	
}
