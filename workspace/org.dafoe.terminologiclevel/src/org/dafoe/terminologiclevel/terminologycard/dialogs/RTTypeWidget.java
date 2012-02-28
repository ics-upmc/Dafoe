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

package org.dafoe.terminologiclevel.terminologycard.dialogs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.terminological.model.IMethod;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class RTTypeWidget extends Composite {

	private static Boolean CAN_MODIFY = false;
	private static String RELATION_TYPE_COL_NAME = Messages.getString("RTTypeWidget.0"); //$NON-NLS-1$
	private static String METHOD_COL_NAME = Messages.getString("RTTypeWidget.1"); //$NON-NLS-1$
	private static String DEFAULT_RELATION_TYPE_LABEL = Messages.getString("RTTypeWidget.2"); //$NON-NLS-1$
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private String selectionEventType, deletionEventType;

	private TreeViewer treeViewer;
	
	private List<ITypeRelationTermino> relationTypes;

	private ITypeRelationTermino currentRelationType;

	private CellEditor[] cellEditors;

	private Action addRelationTypeAction;
	private Action removeRelationTypeAction;
	private Action addMethodAction;
	private Action removeMethodAction;
	private Action removeMethodsAction;
	
	private Shell shell;
	
	public RTTypeWidget(Composite parent, int style) {
		super(parent, style);
		
		this.setLayout(new GridLayout());
				
		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		final Color TERMINOLOGIC_LEVEL_COLOR = themeManager.getTheme("org.dafoe.terminologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setBackground(TERMINOLOGIC_LEVEL_COLOR);

		
		GridData gd;
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		this.setLayoutData(gd);
		
		shell = parent.getShell();
		
		createContent();
	}
	
	//	
	
	private void createContent(){
		
		GridData gd;

		Composite mainComposite = new Composite(this, SWT.NONE);
		//mainComposite.setText("Terminologic relation types");
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		mainComposite.setLayoutData(gd);
		mainComposite.setLayout(new GridLayout(1, false));
		
		FontRegistry fontRegistry = new FontRegistry(shell.getDisplay());
		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		Label label = new Label(mainComposite, SWT.NONE);
		label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
		label.setText(Messages.getString("RTTypeWidget.6")); //$NON-NLS-1$
		
		Label titleBarSeparator = new Label(mainComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		treeViewer = new TreeViewer(mainComposite, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd.heightHint = 300;
		gd.minimumHeight = 200;
		//gd.minimumWidth = 300;
		tree.setLayoutData(gd);
		treeViewer.setContentProvider(new RelationTypesTreeContentProvider());
		treeViewer.setLabelProvider(new RelationTypesTreeLabelProvider());
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				
				addRelationTypeAction.setEnabled(true);
				
				removeRelationTypeAction.setEnabled(false);				
				addMethodAction.setEnabled(false);
				removeMethodAction.setEnabled(false);
				removeMethodsAction.setEnabled(false);
				
				TreeItem[] sel = treeViewer.getTree().getSelection();

				//TreeItem sel = ((TreeItem)event.getSource()).getItem(0);
				
				if (sel.length != 0) {

					if (sel[0].getData() instanceof ITypeRelationTermino) {
						
						removeRelationTypeAction.setEnabled(true);
						addMethodAction.setEnabled(true);
						
						currentRelationType = (ITypeRelationTermino) (sel[0].getData());
						
						if (currentRelationType.getMethods().size() > 0) {
						
							removeMethodsAction.setEnabled(true);
							
						}
						
					} else {
						
						if (sel[0].getData() instanceof IMethod) {
						
							removeMethodAction.setEnabled(true);
							
							currentRelationType = (ITypeRelationTermino)(sel[0].getParentItem().getData());
							
							ControlerFactoryImpl.getTerminologyControler().setMethod((IMethod)(sel[0].getData()));
						}
						
					}

					if (currentRelationType != null) {

						ControlerFactoryImpl.getTerminologyControler().setCurrentRelationType(currentRelationType);

					}
				}
				
				if (currentRelationType != null) {
					propertyChangeSupport.firePropertyChange(selectionEventType, null, currentRelationType);
				}
				
			}
		});
		
		cellEditors = new CellEditor[2];
		cellEditors[0] = new TextCellEditor(treeViewer.getTree());
		cellEditors[1] = new TextCellEditor(treeViewer.getTree());
		
		treeViewer.setColumnProperties(new String[]{RELATION_TYPE_COL_NAME, METHOD_COL_NAME});
		treeViewer.setCellEditors(cellEditors);
		treeViewer.setCellModifier(new myCellModifier());
		
		new Label(mainComposite, SWT.NONE);
		
		treeViewer.setInput(getRTTypes());
		treeViewer.expandAll();

		treeViewer.addDoubleClickListener(new UpdateListener());
		
	    Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
	    DragSource source = new DragSource(treeViewer.getTree(), DND.DROP_MOVE | DND.DROP_COPY);
	    source.setTransfer(types);
		
	    final TreeItem[] dragSourceItem = new TreeItem[1];

	    source.addDragListener(new DragSourceListener() { 
	    	
	    	public void dragStart(DragSourceEvent event) { 
	    		TreeItem[] selection = treeViewer.getTree().getSelection();
	    		event.doit = false;
	    		
	            if (selection.length > 0) {
	            	
	            	if (selection[0].getData() instanceof ITypeRelationTermino) {
	            		event.doit = true;
	            		dragSourceItem[0] = selection[0];
	            		System.out.println("DO IT"); //$NON-NLS-1$
	            	}
	            }
	    	} 

	    	public void dragSetData(DragSourceEvent event) { 
	    		ITypeRelationTermino TRType = (ITypeRelationTermino)(dragSourceItem[0].getData());
	    		int id = TRType.getId();
	    		event.data = String.valueOf(id);
	    	} 
	    	
	    	public void dragFinished(DragSourceEvent event) { 
	    	} 
	    }); 

		makeActions();
		
		hookPopupMenu();

	}
	
	//
	
	public void setSelectionEventType(String event){
		this.selectionEventType = event;
	}
	
	//
	
	public void setDeletionEventType(String event){
		this.deletionEventType = event;
	}

	//
	
	public String getSelectionEventType(){
		return this.selectionEventType;
	}
	
	//
	
	public String getDeletionEventType(){
		return this.deletionEventType;
	}

	//
	
	public void setSelection(ITypeRelationTermino sel){
		
		
		if(sel != null) {
			
			TreeItem[] selection = getTreeItemsReferencingTCType(sel);
			
			treeViewer.getTree().setSelection(selection);
			
		}

	}
	
	//
	
	private TreeItem[] getTreeItemsReferencingTCType(ITypeRelationTermino rtType){
		List<TreeItem> roots = new ArrayList<TreeItem>();
		List<TreeItem> res = new ArrayList<TreeItem>();
		
		roots = Arrays.asList(treeViewer.getTree().getItems());	
		
		retrieveItems(rtType, roots, res);
		
		return res.toArray(new TreeItem[]{});
	}
	
	//
	
	private void retrieveItems(ITypeRelationTermino rtType, List<TreeItem> tis, List<TreeItem> res){
		
		if (tis != null){
			
			if(tis.size() != 0) {
				
				Iterator<TreeItem> it = tis.iterator();
				
				while (it.hasNext()){
					
					TreeItem treeItem = it.next();
					
					if (treeItem.getData() instanceof ITypeRelationTermino) {
					
						if (((ITypeRelationTermino)treeItem.getData()) == rtType) {

							res.add(treeItem);

						}

						List<TreeItem> childrenList = Arrays.asList(treeItem.getItems());

						retrieveItems(rtType, childrenList, res);
					
					}
					
				}
				
			}
			
		}
		
		
	}

	
	//
	
	public ITypeRelationTermino getSelection(){
		return this.currentRelationType;
	}

	//
	
	public void deselect(){
		treeViewer.getTree().deselectAll();
	}

	//
	
	public void refresh(){
		treeViewer.setInput(getRTTypes());
	}
	
	//
	
	public void expandAll(){
		treeViewer.expandAll();
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
	
    public List<ITypeRelationTermino> getRTTypes(){
    	relationTypes = DatabaseAdapter.getRelationTypes();
    	return relationTypes;
    }
    
	class RelationTypesTreeContentProvider implements ITreeContentProvider {
		
		public Object[] getChildren(Object parentElement) {
			final ITypeRelationTermino relationType = (ITypeRelationTermino) parentElement;
			return getMethodsFromRelationType(relationType).toArray();
		}
		
		private List<IMethod> getMethodsFromRelationType(ITypeRelationTermino relationType) {
			try {
				List<IMethod> res;

				res = UtilTools.setToList(relationType.getMethods());
				
				return res;
				
			} catch (Exception e) {
				
				e.printStackTrace();
				return new ArrayList<IMethod>();
				
			}
		}
		

		public Object getParent(Object element) {
			if (element instanceof IMethod){
				return ((IMethod) element).getTypeRelation();
			} else {
				return null;
			}
		}

		public boolean hasChildren(Object element) {
			if (element instanceof ITypeRelationTermino){
				
				return (getChildren(element).length != 0);

			} else {
				
				return false;
			}
		}

		public Object[] getElements(Object inputElement) {
			final Object[] relationTypesArray = ((List<ITypeRelationTermino>) inputElement).toArray();
			return relationTypesArray;
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
		
	}
			
	//
	
	class RelationTypesTreeLabelProvider extends BaseLabelProvider implements ILabelProvider {

		public Image getImage(Object element) {
			Image img;
			ImageDescriptor imgDesc;
			if (element instanceof ITypeRelationTermino) {
				imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(org.dafoe.terminologiclevel.Activator.RELATION_TYPE_IMG_ID);

				img = imgDesc.createImage();
				return img;
				
			} else if (element instanceof IMethod){
				imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(org.dafoe.terminologiclevel.Activator.METHOD_IMG_ID);

				img = imgDesc.createImage();
				return img;
				
			} else {
				
				return null;
			}
		}

		public String getText(Object element) {
			if (element instanceof ITypeRelationTermino) {
				return ((ITypeRelationTermino)element).getLabel();
			} else {
				return ((IMethod)element).getTool();
			}
		}
		
	}
	
	class myCellModifier implements ICellModifier{
		private CellEditor editor;
		private Viewer myViewer;

		public boolean canModify(Object element, String property) {
			System.out.println(CAN_MODIFY);
			return CAN_MODIFY;
		}

		public Object getValue(Object element, String property) {
			
			if (element instanceof ITypeRelationTermino){
				
				return ((ITypeRelationTermino)element).getLabel();
				
			} else if (element instanceof IMethod){
				
				return ((IMethod)element).getPattern();
				
			}
			return null;
		}

		public void modify(Object element, String property, Object value) {
			Object data = ((TreeItem) element).getData();

			if (data instanceof ITypeRelationTermino){
				
				((ITypeRelationTermino)data).setLabel((String)value);
				DatabaseAdapter.updateRelationType((ITypeRelationTermino)data, (String)value);
				
				propertyChangeSupport.firePropertyChange(selectionEventType, null, currentRelationType);
				
			} else if(data instanceof IMethod){
				
				((IMethod)data).setPattern((String)value);
				
			}
			CAN_MODIFY = false;
			
			treeViewer.refresh(data);

		}
		
	}
	
	//
	
	class UpdateListener implements IDoubleClickListener {

		public void doubleClick(DoubleClickEvent event) {
			CAN_MODIFY = true;
			
			Object selectedObject = ((IStructuredSelection)event.getSelection()).getFirstElement();
			
			treeViewer.editElement(selectedObject, 0);
				
		}
		
	}

	//
	
	private void makeActions(){
	
		// create a new relation type
		
		addRelationTypeAction = new Action(){

			public void run() {
				ITypeRelationTermino relationType= DatabaseAdapter.createRelationType(DEFAULT_RELATION_TYPE_LABEL);
				relationTypes.add(relationType);
				treeViewer.refresh();
				treeViewer.setSelection(new StructuredSelection(relationType));
			}

		};
		
		// remove a relation type => check if it is used in a terminological relation. If not, delete.
		
		removeRelationTypeAction = new Action(){
			
			public void run() {

				TreeItem[] sel = treeViewer.getTree().getSelection();
				ITypeRelationTermino relationType;
				
				if (sel.length == 1){

					relationType = (ITypeRelationTermino)sel[0].getData();
					
					List<ITypeRelationTermino> rtTypes = new ArrayList<ITypeRelationTermino>();
					rtTypes.add(relationType);
					
					if (relationType.getTermRelations().size() != 0) {
						
						WarningRTTypeRemovingDialog dial = new WarningRTTypeRemovingDialog(shell);
						
						dial.open();
					
						if (dial.getReturnCode() == IDialogConstants.OK_ID) {
							
							
							DatabaseAdapter.deleteRelationTypes(rtTypes);

							currentRelationType = null;
							
							ControlerFactoryImpl.getTerminologyControler().setRelationDeleted();

							treeViewer.refresh();
								
						}

					} else {
						
						relationTypes.remove(relationType);

						currentRelationType = null;

						DatabaseAdapter.deleteRelationTypes(rtTypes);
						
						treeViewer.refresh();
						
					}
					
					currentRelationType = null;
					
					propertyChangeSupport.firePropertyChange(deletionEventType, true, false);
				}
				
			}
		};
		
		// link a method to the selected relationType
		
		addMethodAction = new Action(){
			
			public void run() {
				
				TerminologyMethodCreatorDialog dial = new TerminologyMethodCreatorDialog(shell);
				
				int res = dial.open();
				
				if (res == IDialogConstants.OK_ID){
					
					IMethod method = ControlerFactoryImpl.getTerminologyControler().getMethod();
					
					DatabaseAdapter.updateMethod(method, null, currentRelationType);
					
					if (dial.methodRemoved()) {
					
						treeViewer.setInput(getRTTypes());
						
						treeViewer.expandAll();
						
					} else {
					
						treeViewer.refresh(currentRelationType);
					
						treeViewer.expandToLevel(currentRelationType, 1);
					}
					
				}
			}
		};

		// unlink the selected method 
		
		removeMethodAction = new Action(){
			
			public void run() {
				
				IMethod method = ControlerFactoryImpl.getTerminologyControler().getMethod();
				
				List<IMethod> methods = new ArrayList<IMethod>();
				
				methods.add(method);
				
				DatabaseAdapter.unlinkMethods(currentRelationType, methods);
				
				treeViewer.setSelection(new StructuredSelection(currentRelationType));

				treeViewer.refresh(currentRelationType);
				
					
			}
		};

		// unlink all the method of a selected relation type
		
		removeMethodsAction = new Action(){
			
			public void run() {
				
				List<IMethod> methods = new ArrayList<IMethod>();
			
				methods = UtilTools.setToList(currentRelationType.getMethods());
				
				DatabaseAdapter.unlinkMethods(currentRelationType, methods);
				
				treeViewer.setSelection(new StructuredSelection(currentRelationType));

				treeViewer.refresh();
							
			}
		};
	
	}
	
	private void hookPopupMenu(){
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(treeViewer.getTree());
		treeViewer.getControl().setMenu(menu);
		
		Action act = this.addRelationTypeAction;
		act.setText(Messages.getString("RTTypeWidget.8")); //$NON-NLS-1$
		menuMgr.add(act);
		
		act = this.removeRelationTypeAction;
		act.setText("Delete relation type"); //$NON-NLS-1$
		menuMgr.add(act);

		menuMgr.add(new Separator());
		
		act = this.addMethodAction;
		act.setText("Add method"); //$NON-NLS-1$
		menuMgr.add(act);
		
		act = this.removeMethodAction;
		act.setText(Messages.getString("RTTypeWidget.9")); //$NON-NLS-1$
		menuMgr.add(act);
		
		act = this.removeMethodsAction;
		act.setText(Messages.getString("RTTypeWidget.10")); //$NON-NLS-1$
		menuMgr.add(act);
	}


}
