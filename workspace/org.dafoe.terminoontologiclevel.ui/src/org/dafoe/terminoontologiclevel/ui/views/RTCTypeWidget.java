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


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.dafoe.terminoontologiclevel.ui.dialog.NewRTCTypeDialog;
import org.dafoe.terminoontologiclevel.ui.dialog.RenameRTCTypeDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
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
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class RTCTypeWidget extends Composite {

	private TreeViewer treeViewer;

	private DropTarget dropTarget;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private String eventType;
	
//	private ITypeRelationTermino currentRTType;
	
	private ITypeRelationTc currentRTCType;
	
	private Action renameAction;

	private Action addRootAction;
	
	private Action addSiblingAction;

	private Action addChildAction;

	private Action deleteAction;

	private Action deleteRecursiveAction;

	private Shell shell;
	
	//
	
	public RTCTypeWidget(Composite parent, int style, ITypeRelationTermino rtType) {
		super(parent, style);
		
		this.setLayout(new GridLayout());
				
		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		final Color TERMINO_ONTOLOGIC_LEVEL_COLOR = themeManager.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setBackground(TERMINO_ONTOLOGIC_LEVEL_COLOR);

		shell = parent.getShell();
		
//		currentRTType = rtType;
		
		createContent();
	}

	//
	
	private void createContent(){
		
		GridData gd;
		
		Composite mainComposite = new Composite(this, SWT.NONE);
		//arg1Composite.setText("Termino-concept relation types");
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		mainComposite.setLayoutData(gd);
		mainComposite.setLayout(new GridLayout(1, false));


		FontRegistry fontRegistry = new FontRegistry(shell.getDisplay());
		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		Label label = new Label(mainComposite, SWT.NONE);
		label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
		label.setText(Messages.getString("RTCTypeWidget.3")); //$NON-NLS-1$
		
		Label titleBarSeparator = new Label(mainComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		treeViewer = new TreeViewer(mainComposite, SWT.V_SCROLL| SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		//gd.heightHint = 250;
		treeViewer.getTree().setLayoutData(gd);
		treeViewer.setContentProvider(new RTCtypeContentProvider());
		treeViewer.setLabelProvider(new RTCTypeLabelProvider());
		
		treeViewer.setInput(getRTCTypes());
		
		treeViewer.expandAll();
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				TreeItem[] sel = treeViewer.getTree().getSelection();
				ITypeRelationTc tc;
				 
				if (sel.length != 0) {
					tc = (ITypeRelationTc)(sel[0].getData());
					System.out.println(tc.getName());
					currentRTCType = tc;
					propertyChangeSupport.firePropertyChange(eventType, null, currentRTCType);

				}
				
			}
		});
		
	    Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
	    DragSource source = new DragSource(treeViewer.getTree(), DND.DROP_MOVE | DND.DROP_COPY);
	    source.setTransfer(types);
		
	    final TreeItem[] dragSourceItem = new TreeItem[1];

	    source.addDragListener(new DragSourceListener() { 
	    	
	    	public void dragStart(DragSourceEvent event) { 
	    		TreeItem[] selection = treeViewer.getTree().getSelection();
	    		event.doit = false;
	    		
	            if (selection.length > 0) {

	            	event.doit = true;
	            	dragSourceItem[0] = selection[0];

	            }
	    	} 

	    	public void dragSetData(DragSourceEvent event) { 
	    		ITypeRelationTc TRType = (ITypeRelationTc)(dragSourceItem[0].getData());
	    		int id = TRType.getId();
	    		event.data = String.valueOf(id);
	    	} 
	    	
	    	public void dragFinished(DragSourceEvent event) { 
	    	} 
	    }); 

	    createActions();
	    
	    createContextMenu();

	}
	
	//
	
	public void createDropTarget(){
	    if (dropTarget != null) {
	    	dropTarget.dispose();
	    }
		
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
	    dropTarget = new DropTarget(this.treeViewer.getTree(), DND.DROP_MOVE | DND.DROP_DEFAULT);
	    dropTarget.setTransfer(types); 
	    
	    dropTarget.addDropListener(new DropTargetAdapter() {
			
			public void dragEnter(DropTargetEvent event) {
				
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = (event.operations & DND.DROP_MOVE) != 0 ? DND.DROP_MOVE : DND.DROP_NONE;
				}

				for (int i = 0, n = event.dataTypes.length; i < n; i++) {
					if (TextTransfer.getInstance().isSupportedType(event.dataTypes[i])) {
						event.currentDataType = event.dataTypes[i];
					}
				}
			}

			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				
				if (event.item != null) {
//					TreeItem item = (TreeItem) event.item;
					event.feedback |= DND.FEEDBACK_SELECT;

				}

			}
			
			public void drop(DropTargetEvent event) {
				ITypeRelationTc rtcType;
				
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				if (event.item != null) {
					rtcType = (ITypeRelationTc) event.item.getData();
				} else {
					rtcType = null;
				}
				
				DatabaseAdapter.changeParent(currentRTCType, rtcType);
				
				treeViewer.setInput(getRTCTypes());
				
				treeViewer.expandAll();
			}				

		});
		
	}
	
	//
	
	public void removeDropTarget(){
	    if (dropTarget != null) {
	    	dropTarget.dispose();
	    }
	    dropTarget = null;
	}
	
	//
	
	public void setEventType(String event){
		this.eventType = event;
	}
	
	//
	
	public String getEventType(){
		return this.eventType;
	}
	
	//
	
	public void setSelection(ITypeRelationTc sel){
		
		if(sel != null) {

			TreeItem[] selection = getTreeItemsReferencingRTCType(sel);
			
			treeViewer.getTree().setSelection(selection);
			
		}

	}

	//
	
	public ITypeRelationTc getSelection(){
		
		return currentRTCType;

	}

	//
	
	public void deselect(){
		treeViewer.getTree().deselectAll();
	}
	
	//
	
	public void refresh(){
		treeViewer.setInput(getRTCTypes());
	}
	
	//
	
	public void refresh(ITypeRelationTermino rtType){
//		currentRTType = rtType;
		refresh();
	}

	//
	
	public void expandAll(){
		treeViewer.expandAll();
	}

	//
	
	private TreeItem[] getTreeItemsReferencingRTCType(ITypeRelationTc rtcType){
		List<TreeItem> roots = new ArrayList<TreeItem>();
		List<TreeItem> res = new ArrayList<TreeItem>();
		
		roots = Arrays.asList(treeViewer.getTree().getItems());	
		
		retrieveItems(rtcType, roots, res);
		
		return res.toArray(new TreeItem[]{});
	}
	
	//
	
	private void retrieveItems(ITypeRelationTc rtcType, List<TreeItem> tis, List<TreeItem> res){
		
		if (tis != null){
			
			if(tis.size() != 0) {
				
				Iterator<TreeItem> it = tis.iterator();
				
				while (it.hasNext()){
					
					TreeItem treeItem = it.next();
					
					if (((ITypeRelationTc)treeItem.getData()) == rtcType) {
						
						res.add(treeItem);
						
					}

					List<TreeItem> childrenList = Arrays.asList(treeItem.getItems());

					retrieveItems(rtcType, childrenList, res);
					
				}
				
			}
			
		}
		
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
	
	public ITypeRelationTc getCurrentRTCTypet(){
		return currentRTCType;
	}

	//
	
	private List<ITypeRelationTc> getRTCTypes(){
		
		org.dafoe.terminoontologiclevel.common.DatabaseAdapter.loadRTCTypes();
		List<ITypeRelationTc> rtcTypes = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.getRTCTypes();
		
		return rtcTypes;
		
	}
	
	//
	
	class RTCtypeContentProvider implements ITreeContentProvider {
		List<ITypeRelationTc> rTCTypes;
		
		public Object[] getChildren(Object parentElement) {
			List<ITypeRelationTc> children = new ArrayList<ITypeRelationTc>();
			
			if (rTCTypes != null) {
			
				Iterator<ITypeRelationTc> it = rTCTypes.iterator();

				while(it.hasNext()){
					
					ITypeRelationTc child = it.next();
					
					if(child.getParent() == parentElement){
						children.add(child);
					}
					
				}

			}
			
			return children.toArray();
			
		}

		public Object getParent(Object element) {
			
			System.out.println(element);
			return null;
			
		}

		public boolean hasChildren(Object element) {
			
			return getChildren(element).length != 0;
			
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			rTCTypes = ((List<ITypeRelationTc>) inputElement);
			
			List<ITypeRelationTc> roots = new ArrayList<ITypeRelationTc>();
			
			if (rTCTypes != null) {
				
				Iterator<ITypeRelationTc> it = rTCTypes.iterator();
				
				while(it.hasNext()){
					
					ITypeRelationTc rtcType = it.next();
					
					if(rtcType.getParent() == null) {
						roots.add(rtcType);
					}
				}
			}
			return roots.toArray();
		}
		
		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
	//
	
	class RTCTypeLabelProvider extends BaseLabelProvider implements ILabelProvider {

		public Image getImage(Object element) {
			Image img = null;
			String imgIndex = null;
			ImageDescriptor imgDesc;

			if (element instanceof ITypeRelationTc) {
				
//				ITypeRelationTc rtcType = (ITypeRelationTc) element;
						
				imgIndex = org.dafoe.terminoontologiclevel.ui.Activator.RTC_TYPE_ID;
				
			} 
			
			if (imgIndex != null) {
				
				imgDesc = org.dafoe.terminoontologiclevel.ui.Activator.getDefault().getImageRegistry()
					.getDescriptor(imgIndex);

				img = imgDesc.createImage();
			}

			return img;
		}

		public String getText(Object element) {
			if (element instanceof ITypeRelationTc) {
				return ((ITypeRelationTc)element).getName();
			} else {
				return null;
			}
		}
		
	}
	
	//
	
	private void createActions() {
		
		renameAction = new Action(Messages.getString("RTCTypeWidget.0")) { //$NON-NLS-1$
			public void run() {
				
				if (treeViewer.getTree().getSelection().length > 0) {

					TreeItem curit = treeViewer.getTree().getSelection()[0];
					
					ITypeRelationTc rtcType = (ITypeRelationTc) curit.getData();

					if (rtcType != null) {
						
						RenameRTCTypeDialog dial = new RenameRTCTypeDialog(shell, rtcType.getName());

						dial.open();

						if (dial.getReturnCode() == IDialogConstants.OK_ID) {

							DatabaseAdapter.updateRTCType(rtcType, dial.getRTCtypeName());

							treeViewer.refresh(rtcType);

							ControlerFactoryImpl.getTerminoOntoControler().setUpdateRTCTypee();
						}
					}
				}
			}
		};
		
		addRootAction = new Action(Messages.getString("RTCTypeWidget.1")) { //$NON-NLS-1$
			public void run() {
				
				NewRTCTypeDialog dial = new NewRTCTypeDialog(shell, Messages.getString("RTCTypeWidget.2")); //$NON-NLS-1$

				dial.open();

				if (dial.getReturnCode() == IDialogConstants.OK_ID) {
					
					currentRTCType = DatabaseAdapter.createRTCType(dial.getRTCtypeName(), null);
					
					treeViewer.setInput(getRTCTypes());
					
					treeViewer.expandAll();

					treeViewer.setSelection(new StructuredSelection(currentRTCType));

					ControlerFactoryImpl.getTerminoOntoControler().setUpdateRTCTypee();

				}			
			}
		};

		addSiblingAction = new Action(Messages.getString("RTCTypeWidget.4")) { //$NON-NLS-1$
			public void run() {
				if (treeViewer.getTree().getSelection().length > 0) {

					TreeItem curit = treeViewer.getTree().getSelection()[0];
					
					ITypeRelationTc rtcType = (ITypeRelationTc) curit.getData();

					if (rtcType != null) {
						
						NewRTCTypeDialog dial = new NewRTCTypeDialog(shell, Messages.getString("RTCTypeWidget.5")); //$NON-NLS-1$

						dial.open();

						if (dial.getReturnCode() == IDialogConstants.OK_ID) {

							currentRTCType = DatabaseAdapter.createRTCType(dial.getRTCtypeName(), rtcType.getParent());

							treeViewer.setInput(getRTCTypes());
							
							treeViewer.expandAll();
							
							treeViewer.setSelection(new StructuredSelection(currentRTCType));

							ControlerFactoryImpl.getTerminoOntoControler().setUpdateRTCTypee();
						}
					}
				}
			}

		};

		addChildAction = new Action(Messages.getString("RTCTypeWidget.6")) { //$NON-NLS-1$
			public void run() {
				if (treeViewer.getTree().getSelection().length > 0) {

					TreeItem curit = treeViewer.getTree().getSelection()[0];
					
					ITypeRelationTc rtcType = (ITypeRelationTc) curit.getData();

					if (rtcType != null) {
						
						NewRTCTypeDialog dial = new NewRTCTypeDialog(shell, Messages.getString("RTCTypeWidget.7")); //$NON-NLS-1$

						dial.open();

						if (dial.getReturnCode() == IDialogConstants.OK_ID) {

							currentRTCType = DatabaseAdapter.createRTCType(dial.getRTCtypeName(), rtcType);

							treeViewer.setInput(getRTCTypes());
							
							treeViewer.expandAll();

							treeViewer.setSelection(new StructuredSelection(currentRTCType));

							ControlerFactoryImpl.getTerminoOntoControler().setUpdateRTCTypee();
						}
					}
				}
			}
		};

		deleteAction = new Action(Messages.getString("RTCTypeWidget.8")) { //$NON-NLS-1$
			public void run() {
				
				if (treeViewer.getTree().getSelection().length > 0) {

					TreeItem curit = treeViewer.getTree().getSelection()[0];
					
					ITypeRelationTc rtcType = (ITypeRelationTc) curit.getData();

					if (rtcType != null) {

						
						
					}
				}
			}
		};

		deleteRecursiveAction = new Action(Messages.getString("RTCTypeWidget.9")) { //$NON-NLS-1$
			public void run() {
			}
		};

	}
	
	//
	
	private void createContextMenu() {
		MenuManager menuMgr = new MenuManager();

		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				fillContextMenu(mgr);
			}
		});

		// Create menu.
		Menu menu = menuMgr.createContextMenu(treeViewer.getTree());
		treeViewer.getTree().setMenu(menu);

	}

	//
	
	private void fillContextMenu(IMenuManager mgr) {
		mgr.add(renameAction);
		mgr.add(new Separator());
		mgr.add(addRootAction);
		mgr.add(addSiblingAction);
		mgr.add(addChildAction);
		mgr.add(new Separator());
		mgr.add(deleteAction);
		mgr.add(deleteRecursiveAction);
	}

}
