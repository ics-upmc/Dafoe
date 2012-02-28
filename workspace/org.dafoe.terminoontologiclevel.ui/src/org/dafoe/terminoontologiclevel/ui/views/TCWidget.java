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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.dafoe.terminoontologiclevel.ui.dialog.NewTCDialog;
import org.dafoe.terminoontologiclevel.ui.dialog.RenameTCDialog;
import org.dafoe.terminoontologiclevel.ui.dialog.SubsumeTCDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.FontRegistry;
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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class TCWidget extends Composite {

	public static String SELECTION_EVENT = "TCSelected"; //$NON-NLS-1$
	
	private String title;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private String eventType;
	
	private ITerminoConcept currentTC;
	
//	private ITerm  currentTerm;
	
	private Action renameAction;

	private Action addSiblingAction;

	private Action addChildAction;

	private Action deleteAction;

	private Action deleteRecursiveAction;

	private Action subsumeAction;

	private Action removeSubsumeAction;

	private TreeItem lastSelected = null;

	private Tree tree;
	
	private TreeItem rootit;

	private Shell shell;

	private Hashtable<ITerminoConcept, List<TreeItem>> tcsTreeItem = new Hashtable<ITerminoConcept, List<TreeItem>>();

	private UpdateTCTreePropertyChangeListener updateTCTreePropertyChangeListener;
	
	//
	
	public TCWidget(Composite parent, int style, String title, ITerm termArg) {
		super(parent, style);
		
		this.setLayout(new GridLayout());
		this.title = title;
				
		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		final Color TERMINO_ONTOLOGIC_LEVEL_COLOR = themeManager.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setBackground(TERMINO_ONTOLOGIC_LEVEL_COLOR);
				
//		this.currentTerm = termArg;
		
		shell = parent.getShell();

		createContent();
	}

	//
	
	private void createContent() {
		GridData gd;
		
		Composite tcComposite = new Composite(this, SWT.NONE);
		//tcComposite.setText(title);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		tcComposite.setLayoutData(gd);
		tcComposite.setLayout(new GridLayout(1, false));

		FontRegistry fontRegistry = new FontRegistry(this.getDisplay());
		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		
		if (title != null) {
			Label label = new Label(tcComposite, SWT.NONE);
			label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
			label.setText(title);
			gd = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
			label.setLayoutData(gd);

			Label titleBarSeparator = new Label(tcComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
			titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		}
		

		tree = new Tree(tcComposite, SWT.BORDER);

		tree.setLayoutData(new GridData(GridData.FILL_BOTH));



		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY ;

		final DragSource source = new DragSource(tree, operations);
		final TreeItem[] dragSourceItem = new TreeItem[1];

		source.setTransfer(types);
		source.addDragListener(new DragSourceListener() {
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = tree.getSelection();
				if (selection.length > 0/* && selection[0].getItemCount() == 0*/) {
					event.doit = true;
					dragSourceItem[0] = selection[0];
				} else {
					event.doit = false;
				}
			};

			public void dragSetData(DragSourceEvent event) {
				event.data = dragSourceItem[0].getText();
			}

			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE)
					dragSourceItem[0].dispose();
				dragSourceItem[0] = null;
			}
		});


		DropTarget target = new DropTarget(tree, operations);
		target.setTransfer(types);


		target.addDropListener(new DropTargetAdapter() {
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL | DND.FEEDBACK_INSERT_AFTER | DND.FEEDBACK_SELECT;
			}

			@Override


			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				if (event.item != null) {
					ITerminoConcept newPar = (ITerminoConcept) event.item.getData();
					Set<ITerminoConcept> lesParents = new HashSet<ITerminoConcept>();

					lesParents = newPar.getAncestors();
					lesParents.add(newPar);

					Set<ITerminoConcept> lesEnfants = new HashSet<ITerminoConcept>();

					lesEnfants = currentTC.getDescendants();
					lesEnfants.add(currentTC);

					lesParents.retainAll(lesEnfants);
					
					if (lesParents.size()>0) {
						MessageBox msg = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
						msg.setMessage(Messages.getString("TerminoConceptViewPart.5")); //$NON-NLS-1$
						event.detail = DND.DROP_NONE;

						msg.open();
						return;
					}

					if (event.detail == DND.DROP_MOVE) {

						DatabaseAdapter.ChangeParent(currentTC, newPar);

					} else {

						currentTC.addParent(newPar);
						DatabaseAdapter.saveTerminoConcept(currentTC);
					}
					
					DatabaseAdapter.refresh(currentTC);
					ControlerFactoryImpl.getTerminoOntoControler().UpdateTCsTree();
	
				}

			}
		});

		this.createActions();
		
		this.createContextMenu();

		updateTCTreePropertyChangeListener = new UpdateTCTreePropertyChangeListener();

		ControlerFactoryImpl.getTerminoOntoControler().addPropertyChangeListener(
				ControlerFactoryImpl.updateTCsTreeEvent, updateTCTreePropertyChangeListener);

		
		loadTerminoConcepts();

		lastSelected = rootit;

		tree.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				try {


					if (tree.getSelection().length >0) {
						
						lastSelected = tree.getSelection()[0];
						ITerminoConcept oldTcArg1 = currentTC;
						currentTC = (((ITerminoConcept) lastSelected.getData()));
						propertyChangeSupport.firePropertyChange(eventType, oldTcArg1, currentTC);
												
					}
					
					renameAction.setEnabled(true);
					addChildAction.setEnabled(true);
					addSiblingAction.setEnabled(currentTC != null);
					subsumeAction.setEnabled(currentTC != null);
					deleteAction.setEnabled(currentTC != null);
					deleteRecursiveAction.setEnabled(currentTC != null);
					removeSubsumeAction.setEnabled(currentTC != null);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
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
		Menu menu = menuMgr.createContextMenu(tree);
		tree.setMenu(menu);

	}

	//
	
	private ITerminoConcept getNodeFromInputDialog(String title) {
		NewTCDialog dial2 = new NewTCDialog(shell, title);

		dial2.open();
		
		if (dial2.getReturnCode() == IDialogConstants.OK_ID) {
			String label = dial2.getTCName();

			if (!label.equals("")) { //$NON-NLS-1$
			
				ITerminoConcept tc =  DatabaseAdapter.createTerminoConcept(label, null);
				
				return tc;
			} 
		} 
		return null;

	}
	
	//
	
	private void createActions() {
		
		renameAction = new Action(Messages.getString("TCWidget.0")) { //$NON-NLS-1$
			public void run() {
				
				if (tree.getSelection().length>0) {

					TreeItem curit = tree.getSelection()[0];
					
					ITerminoConcept tc = (ITerminoConcept) curit.getData();

					RenameTCDialog dial = new RenameTCDialog(shell, tc.getLabel());
					
					dial.open();
					
					if (dial.getReturnCode() == IDialogConstants.OK_ID) {

						if (tc != null) {

							DatabaseAdapter.updateTC(tc, dial.getTCName(), TERMINO_ONTO_OBJECT_STATE.UNKNOWN);

							loadTerminoConcepts();

							ControlerFactoryImpl.getTerminoOntoControler().removePropertyChangeListener(updateTCTreePropertyChangeListener);
							ControlerFactoryImpl.getTerminoOntoControler().UpdateTCsTree();
							ControlerFactoryImpl.getTerminoOntoControler().addPropertyChangeListener(
									ControlerFactoryImpl.updateTCsTreeEvent, updateTCTreePropertyChangeListener);

						}
					}
				}
			}
		};
		renameAction.setEnabled(true);
		
		addChildAction = new Action(Messages.getString("ClassesViewPart.4")) { //$NON-NLS-1$
			public void run() {
				ITerminoConcept newTC = getNodeFromInputDialog(Messages.getString("ClassesViewPart.4"));  //$NON-NLS-1$

				if (newTC == null) {
					return;
				}

				if (newTC.getLabel().equals("")) { //$NON-NLS-1$
					return;
				}

				TreeItem parent_it = tree.getSelection()[0];

				ITerminoConcept parent = (ITerminoConcept) parent_it.getData();
				
				if (parent != null) {
					newTC.addParent(parent);
				}
				
				DatabaseAdapter.saveTerminoConcept(newTC);
				
				if (parent != null) {

					Iterator<TreeItem> iter = tcsTreeItem.get(parent).iterator();
					
					while (iter.hasNext()) {
					
						TreeItem curit = iter.next();
						
						creerTreeItem(newTC, curit);
					}
					
				} else {
					creerTreeItem(newTC, rootit);
				}

				currentTC = newTC;
				
				setSelection(currentTC);
				
				ControlerFactoryImpl.getTerminoOntoControler().removePropertyChangeListener(updateTCTreePropertyChangeListener);
				ControlerFactoryImpl.getTerminoOntoControler().UpdateTCsTree();
				ControlerFactoryImpl.getTerminoOntoControler().addPropertyChangeListener(
						ControlerFactoryImpl.updateTCsTreeEvent, updateTCTreePropertyChangeListener);

			}
		};
		addChildAction.setEnabled(true);

		addSiblingAction = new Action(Messages.getString("ClassesViewPart.7")) { //$NON-NLS-1$
			public void run() {
				ITerminoConcept newTC = getNodeFromInputDialog(Messages.getString("ClassesViewPart.7")); //$NON-NLS-1$

				if (newTC == null) {
					return;
				}

				if (newTC.getLabel().equals("")) { //$NON-NLS-1$
					return;
				}

				TreeItem frere_it = tree.getSelection()[0];
				ITerminoConcept parent = null;
				
				if (frere_it.getParent() != null) {
					parent = (ITerminoConcept) frere_it.getParentItem().getData();
				}
				
				newTC.addParent(parent);
				DatabaseAdapter.saveTerminoConcept(newTC);
				
				if (parent != null) {

					Iterator<TreeItem> iter = tcsTreeItem.get(parent).iterator();
					while (iter.hasNext()) {
						TreeItem curit = iter.next();
						creerTreeItem(newTC, curit);

					}
				} else {

					creerTreeItem(newTC, rootit);
				}

				currentTC = newTC;
				
				ControlerFactoryImpl.getTerminoOntoControler().removePropertyChangeListener(updateTCTreePropertyChangeListener);
				ControlerFactoryImpl.getTerminoOntoControler().UpdateTCsTree();
				ControlerFactoryImpl.getTerminoOntoControler().addPropertyChangeListener(
						ControlerFactoryImpl.updateTCsTreeEvent, updateTCTreePropertyChangeListener);

			}
		};
		addSiblingAction.setEnabled(false);

		this.subsumeAction = new Action(Messages.getString("ClassesViewPart.9")) { //$NON-NLS-1$
			public void run() {

				TreeItem frere_it = tree.getSelection()[0];
				ITerminoConcept tosubsume =null;
				if (frere_it.getData()!=null) {
					SubsumeTCDialog dial = new SubsumeTCDialog(shell, 
							ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoOntology());
					tosubsume = (ITerminoConcept) frere_it.getData();

					dial.SetClassToSubsume(tosubsume);
					
					if (dial.open() == IDialogConstants.OK_ID) {
						ITerminoConcept mere = dial.getSelectedTC();
						mere.addChild(tosubsume);
						DatabaseAdapter.saveTerminoConcept(mere);

						if (frere_it.getParentItem()==rootit){
							//rootit.clear(rootit.indexOf(frere_it), true);
							frere_it.dispose();
						}
						Iterator<TreeItem> iter = tcsTreeItem.get(mere).iterator();
						while (iter.hasNext()) {

							TreeItem curit = iter.next();
							curit.setExpanded(true);
							tree.select(curit);
							creerArbre(tosubsume, curit,""); //$NON-NLS-1$

						}

						currentTC = mere;

						ControlerFactoryImpl.getTerminoOntoControler().removePropertyChangeListener(updateTCTreePropertyChangeListener);
						ControlerFactoryImpl.getTerminoOntoControler().UpdateTCsTree();
						ControlerFactoryImpl.getTerminoOntoControler().addPropertyChangeListener(
								ControlerFactoryImpl.updateTCsTreeEvent, updateTCTreePropertyChangeListener);

					}

				}

			}
		};
		this.subsumeAction.setEnabled(false);

		deleteAction = new Action(Messages.getString("ClassesViewPart.13")) { //$NON-NLS-1$
			public void run() {
				if (tree.getSelection().length>0) {

					MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
					msg.setMessage(Messages.getString("TerminoConceptViewPart.8")); //$NON-NLS-1$
					int res = msg.open();

					if (res == SWT.OK) {

						TreeItem curit = tree.getSelection()[0];
						ITerminoConcept todel =(ITerminoConcept) curit.getData();
						if (todel != null) {
							DatabaseAdapter.deleteTC(todel);
							loadTerminoConcepts();
							
							ControlerFactoryImpl.getTerminoOntoControler().removePropertyChangeListener(updateTCTreePropertyChangeListener);
							ControlerFactoryImpl.getTerminoOntoControler().UpdateTCsTree();
							ControlerFactoryImpl.getTerminoOntoControler().addPropertyChangeListener(
									ControlerFactoryImpl.updateTCsTreeEvent, updateTCTreePropertyChangeListener);

						}

					}
				}
			}
		};
		deleteAction.setEnabled(false);

		deleteRecursiveAction = new Action(Messages.getString("ClassesViewPart.14")) { //$NON-NLS-1$
			public void run() {
				if (tree.getSelection().length>0) {

					MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
					msg.setMessage(Messages.getString("TerminoConceptViewPart.9")); //$NON-NLS-1$
					int res = msg.open();

					if (res == SWT.OK) {

						TreeItem curit = tree.getSelection()[0];
						ITerminoConcept todel =(ITerminoConcept) curit.getData();
						if (todel != null) {
							DatabaseAdapter.deleteRecursiveTC(todel);
							loadTerminoConcepts();
							ControlerFactoryImpl.getTerminoOntoControler().removePropertyChangeListener(updateTCTreePropertyChangeListener);
							ControlerFactoryImpl.getTerminoOntoControler().UpdateTCsTree();
							ControlerFactoryImpl.getTerminoOntoControler().addPropertyChangeListener(
									ControlerFactoryImpl.updateTCsTreeEvent, updateTCTreePropertyChangeListener);
						}

					}
				}
			}
		};
		
		deleteRecursiveAction.setEnabled(false);

		removeSubsumeAction = new Action(Messages.getString("ClassesViewPart.15")) { //$NON-NLS-1$
			public void run() {

			}
		};
		
		removeSubsumeAction.setEnabled(false);
	}

	//
	
	private void fillContextMenu(IMenuManager mgr) {
		mgr.add(renameAction);
		mgr.add(new Separator());
		mgr.add(addSiblingAction);
		mgr.add(addChildAction);
		mgr.add(new Separator());
		mgr.add(deleteAction);
		mgr.add(deleteRecursiveAction);
	}

	//

	void loadTerminoConcepts() {

		ArrayList<ITerminoConcept> tcs = (ArrayList<ITerminoConcept>) DatabaseAdapter.getTopTerminoConcepts(
				ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoOntology());
		
		tree.clearAll(true);
		tree.removeAll();
		tree.redraw();
		tcsTreeItem.clear();

		rootit = new TreeItem(tree,SWT.NONE);
		rootit.setText(Messages.getString("TerminoConceptViewPart.10")); //$NON-NLS-1$
		rootit.setImage(org.dafoe.terminoontologiclevel.ui.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminoontologiclevel.ui.Activator.TERMINO_CONCEPT_ID));

		for (int i = 0; i < tcs.size(); i++) {
			//CreerArbre(tcs.get(i), rootit, recherche);
			creerArbre(tcs.get(i), rootit, ""); //$NON-NLS-1$
		}

		rootit.setExpanded(true);

		lastSelected = rootit;
		tree.select(rootit);

		if (currentTC != null) {

			if (lastSelected.getData() != currentTC) {

				changeSelection(currentTC);

			} 
			if (tree.getSelection().length >0) {
				lastSelected = tree.getSelection()[0];
			}
		} 

	}

	//

	TreeItem creerTreeItem (ITerminoConcept tc, TreeItem pere) {
		TreeItem it = new TreeItem(pere, SWT.NONE);
		it.setData(tc);

		pere.setExpanded(true);

		if (!tcsTreeItem.containsKey(tc)) {
			tcsTreeItem.put(tc, new ArrayList<TreeItem>());
		}

		try {
			tcsTreeItem.get(tc).add(it);
		} catch (Exception e) {
			e.printStackTrace();
		}

		it.setText(tc .getLabel());
		it.setImage(org.dafoe.terminoontologiclevel.ui.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminoontologiclevel.ui.Activator.TERMINO_CONCEPT_ID));

		return it;
	}

	//

	TreeItem creerArbre(ITerminoConcept tc, TreeItem pere, String filtre) {
		try {
			TreeItem it = creerTreeItem(tc, pere);

			Iterator<ITerminoConcept> itcl = tc.getChildren().iterator();

			if (filtre!="") { //$NON-NLS-1$
				if (it.getText().contains(filtre)) {

					it.setForeground(new Color(shell.getDisplay(),145,0,0));

				} else {
					it.setForeground(new Color(shell.getDisplay(),200,200,200));
				}
			}

			while(itcl.hasNext()) {
				ITerminoConcept _tc = itcl.next();

				creerArbre(_tc, it, filtre);
			}

			it.setExpanded(true);

			return it;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	//

	public void changeSelection(ITerminoConcept tc) {
		if (tcsTreeItem.containsKey(tc)) {
			List<TreeItem> laliste = tcsTreeItem.get(tc);
			if (laliste.size()>0) {
				tree.select(laliste.get(0));
			}

		}
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
	
	public void setSelection(ITerminoConcept sel){
		
		
		if(sel != null) {

			TreeItem[] selection = getTreeItemsReferencingTC(sel);
			
			tree.setSelection(selection);
			
		}
		
	}
	
	public void deselect(){
		tree.deselectAll();
	}
	
	
	private TreeItem[] getTreeItemsReferencingTC(ITerminoConcept tc){
		List<TreeItem> roots = new ArrayList<TreeItem>();
		List<TreeItem> res = new ArrayList<TreeItem>();
		
		roots = Arrays.asList(tree.getItems());	
		
		retrieveItems(tc, roots, res);
		
		return res.toArray(new TreeItem[]{});
	}
	
	
	
	private void retrieveItems(ITerminoConcept tc, List<TreeItem> tis, List<TreeItem> res){
		
		if (tis != null){
			
			if(tis.size() != 0) {
				
				Iterator<TreeItem> it = tis.iterator();
				
				while (it.hasNext()){
					
					TreeItem treeItem = it.next();
					
					if (((ITerminoConcept)treeItem.getData()) == tc) {
						
						res.add(treeItem);
						
					}

					List<TreeItem> childrenList = Arrays.asList(treeItem.getItems());

					retrieveItems(tc, childrenList, res);
					
				}
				
			}
			
		}
				
	}
	
	//
	
	class UpdateTCTreePropertyChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			loadTerminoConcepts();
			
		}
		
	}
	
	//
	public void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}
		  
	public ITerminoConcept getCurrentTerminoConcept(){
		return currentTC;
	}

	public void updateContent(){
		loadTerminoConcepts();
	}
	
	
/*	
	public void expandAll() {
		treeViewer.expandAll();
	}
*/	

}
