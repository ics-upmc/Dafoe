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

package org.dafoe.ontologiclevel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.ontologiclevel.Dialog.NewClassDialog2;
import org.dafoe.ontologiclevel.Dialog.SubsumeDialog;
import org.dafoe.ontologiclevel.autocomplete.clazz.AutocompleteClassSelector;
import org.dafoe.ontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

public class ClassesViewPart extends ViewPart {

	private Action renameAction;
	
	private Action addSiblingAction;

	private Action addChildAction;

	private Action deleteAction;

	private Action deleteRecursiveAction;

	private Action subsumeAction;

	private Action removeSubsumeAction;

	private Logger logger = Logger.getLogger(ClassesViewPart.class);

	private TreeItem lastSelected = null;

	private Hashtable<IClass,List<TreeItem>> classTreeItem = new Hashtable<IClass,List<TreeItem>>();

	private Tree tree;

	private TreeItem rootit;

	private Text textrecherche;

	private String recherche = ""; //$NON-NLS-1$
	
	private Shell shell;
	
	private AutocompleteClassSelector auto;
	
	private ITerminoOntoObject linkedOntoObject;

	//
	
	public ClassesViewPart() {
	}

	//
	
	public void createPartControl(Composite parent) {		

		shell = parent.getShell();
		
		parent.setLayout(new GridLayout(1,true));
		
		Composite comprecherche = new Composite(parent, SWT.NONE);
		comprecherche.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comprecherche.setLayout(new GridLayout(3, false));

		textrecherche = new Text(comprecherche, SWT.BORDER);
		textrecherche.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		auto = new AutocompleteClassSelector(textrecherche, DatabaseAdapter.getClasses(Activator.getCurrentOntology()));
		
		textrecherche.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				
				IClass currentClass = Activator.getCurrentClass();
				
				if (e.keyCode == SWT.CR){
					
					String label = textrecherche.getText();
					
					IClass clazz = auto.getClassFromSelectedLabel(label);
					
					if  (clazz != null) {
						
						currentClass = clazz;
						
						Activator.setCurrentClass(currentClass);
						
						recherche = textrecherche.getText();

						changeSelection(currentClass);
						
						
					} else {
						
						textrecherche.setText(currentClass.getLabel());
						
					}
					
					
				} else if (e.keyCode == SWT.ESC){
					
					if (currentClass != null) {
					
						textrecherche.setText(currentClass.getLabel());
					
					}
				}
				
			}

			public void keyReleased(KeyEvent e) {
				
			}
			
		});
		
		/*
		Button butrecherche = new Button(comprecherche, SWT.PUSH);
		butrecherche.setText(Messages.getString("ClassesViewPart.Filtrer")); //$NON-NLS-1$

		butrecherche.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				recherche = textrecherche.getText();
				LoadClasses();
			}
		});

		*/

		Button butupdate = new Button(comprecherche,SWT.PUSH);
		butupdate.setText(Messages.getString("ClassesViewPart.Update")); //$NON-NLS-1$

		butupdate.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				loadClasses();
			}
		});


		tree = new Tree(parent,SWT.NONE);

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
				//System.out.println(event.data.toString());

			}

			@Override
			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				if (event.item != null) {
					IClass newPar = (IClass) event.item.getData();
					// verification de l'integrite des données
					/*if (newPar == Activator.getCurrentClass()) {
		        		MessageBox msg = new MessageBox(getSite().getShell(),SWT.ICON_WARNING | SWT.OK);
		        		msg.setMessage("Vous ne pouvez pas attacher cette classe ici. 1");
		        		msg.open();
		        		return;
		        	}*/
					Set<IClass> lesparents = newPar.getAncestors();
					lesparents.add(newPar);
					Set<IClass> lesenfants = Activator.getCurrentClass().getDescendants();
					lesenfants.add(Activator.getCurrentClass());
					/*if (lesparents.contains(Activator.getCurrentClass())) {
		        		MessageBox msg = new MessageBox(getSite().getShell(),SWT.ICON_WARNING | SWT.OK);
		        		msg.setMessage("Vous ne pouvez pas attacher cette classe ici. 2");
		        		msg.open();
		        		return;
		        	} */
					lesparents.retainAll(lesenfants);
					if (lesparents.size()>0) {
						MessageBox msg = new MessageBox(getSite().getShell(),SWT.ICON_WARNING | SWT.OK);
						msg.setMessage(Messages.getString("ClassesViewPart.VousNePouvezPasAttacherCetteClasseIci")); //$NON-NLS-1$
						event.detail = DND.DROP_NONE;

						msg.open();
						return;
					}


					if (event.detail == DND.DROP_MOVE) {
						org.dafoe.ontologiclevel.common.DatabaseAdapter.changeParent(Activator.getCurrentClass(), newPar);
					} else {
						Activator.getCurrentClass().addParent(newPar);
						org.dafoe.ontologiclevel.common.DatabaseAdapter.saveClass(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), Activator.getCurrentClass(), null);
					}
					Activator.ontoControler.UpdateClassTree();
					Activator.ontoControler.setCurrentClass(Activator.getCurrentClass());
				}
			}
		});

		this.createActions();
		this.createToolbar();
		this.createContextMenu();

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.currentClassEvent, 
				new CurrentClassChangeListener());

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.addClassParentEvent,
				new AddClassParentListener());

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.updateClasseTreeEvent, 
				new UpdateClasseTree());

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.updateClasseEvent, 
				new UpdateClasse());

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.currentOntologyEvent, 
				new ChangeOntology());

		//org.dafoe.ontologiclevel.common.DatabaseAdapter.loadClasses(Activator.getCurrentOntology());

		loadClasses();

		lastSelected = rootit;

		tree.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {

				try {
					IClass currentClass = Activator.getCurrentClass();

					if (tree.getSelection().length >0) {
						lastSelected = tree.getSelection()[0];
						currentClass = ((IClass) tree.getSelection()[0].getData());
						Activator.setCurrentClass(currentClass);
					}

					addChildAction.setEnabled(true);
					addSiblingAction.setEnabled(currentClass!=null);
					subsumeAction.setEnabled(currentClass!=null);
					deleteAction.setEnabled(currentClass!=null);
					deleteRecursiveAction.setEnabled(currentClass!=null);
					removeSubsumeAction.setEnabled(currentClass!=null);
					renameAction.setEnabled(currentClass!=null);


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

		// Register menu for extension.
		//getSite().registerContextMenu(menuMgr, myOntologicTree.getTreeViewer());

	}

	//

	private IClass getNodeFromInputDialog() {
		
		NewClassDialog2 dial2 = new NewClassDialog2(ClassesViewPart.this.getSite().getShell());
		
		if (dial2.open() == IDialogConstants.OK_ID) {
		
			String className = dial2.getClassName();
			
			if (!className.equals("")) { //$NON-NLS-1$
				
				IClass clazz = new ClassImpl();
				clazz.setLabel(dial2.getClassName());
				clazz.setState(ONTO_OBJECT_STATE.VALIDATED);
				clazz.setNameSpace(dial2.getClasseNameSpace());
				
				// Activator.getCurrentOntology().addOntoObject(clazz);
				
				linkedOntoObject = dial2.getTerminoOntoObject();
				
				return clazz;
			} 
		} 

		return null;

	}

	//

	private void createActions() {
		
		
		
		renameAction = new Action() { //$NON-NLS-1$
			public void run() {

				/*
				if (tree.getSelection().length > 0) {

					TreeItem curit = tree.getSelection()[0];

					ITerminoConcept tc = (ITerminoConcept) curit.getData();

					RenameTCDialog dial = new RenameTCDialog(shell, tc
							.getLabel());

					dial.open();

					if (dial.getReturnCode() == IDialogConstants.OK_ID) {

						if (tc != null) {

							DatabaseAdapter.updateTC(tc, dial.getTCName(), -1);

							loadTerminoConcepts();

							ControlerFactoryImpl.getTerminoOntoControler()
									.removePropertyChangeListener(
											updateTCTreePropertyChangeListener);
							ControlerFactoryImpl.getTerminoOntoControler()
									.UpdateTCsTree();
							ControlerFactoryImpl
									.getTerminoOntoControler()
									.addPropertyChangeListener(
											ControlerFactoryImpl.updateTCsTreeEvent,
											updateTCTreePropertyChangeListener);

							DatabaseAdapter.loadTerminoConcepts();

							auto.setSelectionItems(DatabaseAdapter
									.getTerminoConcepts());

						}

					}
				}
				*/
			}
		};
		renameAction.setEnabled(false);
		renameAction.setToolTipText(Messages.getString("ClassePropertyViewPart.0")); //$NON-NLS-1$
		renameAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.EDIT_ID));

		
		
		
		
		
		addChildAction = new Action(Messages.getString("ClassesViewPart.4")) { //$NON-NLS-1$
			public void run() {
				IClass newClasse = getNodeFromInputDialog();

				if (newClasse == null) {
					return;
				}

				if (newClasse.getLabel().equals("")) { //$NON-NLS-1$
					return;
				}

				TreeItem parent_it = tree.getSelection()[0];

				IClass parent = (IClass) parent_it.getData();
				
				if (parent != null) {
					newClasse.addParent(parent);
				}
				
				DatabaseAdapter.saveClass(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), newClasse, linkedOntoObject);
				
				linkedOntoObject = null;
				
				if (parent != null) {

					Iterator<TreeItem> iter = classTreeItem.get(parent).iterator();
					while (iter.hasNext()) {
						TreeItem curit = iter.next();
						creerTreeItem(newClasse, curit);

					}
				} else {
					creerTreeItem(newClasse, rootit);
				}

				Activator.setCurrentClass(newClasse);
				
				auto.setSelectionItems(DatabaseAdapter.getClasses(Activator.getCurrentOntology()));

			}
		};
		addChildAction.setEnabled(true);
		addChildAction.setToolTipText(Messages.getString("ClassesViewPart.4"));
		addChildAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.ADD_CHILD_ID));

		//

		addSiblingAction = new Action(Messages.getString("ClassesViewPart.7")) { //$NON-NLS-1$
			public void run() {
				IClass newClasse = getNodeFromInputDialog();

				if (newClasse == null) {
					return;
				}

				if (newClasse.getLabel().equals("")) { //$NON-NLS-1$
					return;
				}

				TreeItem frere_it = tree.getSelection()[0];
				IClass parent = null;

				if (frere_it.getParent() != null) {
					parent = (IClass) frere_it.getParentItem().getData();

					// parent is not te super root "Things"
					if (parent != null) {

						newClasse.addParent(parent);

					}
				}

				DatabaseAdapter.saveClass(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), newClasse, linkedOntoObject);

				linkedOntoObject = null;
				
				if (parent != null) {

					Iterator<TreeItem> iter = classTreeItem.get(parent).iterator();
					while (iter.hasNext()) {
						TreeItem curit = iter.next();
						creerTreeItem(newClasse, curit);

					}
				} else {

					creerTreeItem(newClasse, rootit);
				}
				
				Activator.setCurrentClass(newClasse);
				
				auto.setSelectionItems(DatabaseAdapter.getClasses(Activator.getCurrentOntology()));


			}
		};
		addSiblingAction.setEnabled(false);
		addSiblingAction.setToolTipText(Messages.getString("ClassesViewPart.7"));
		addSiblingAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.ADD_SIBLING_ID));

		//

		this.subsumeAction = new Action(Messages.getString("ClassesViewPart.9")) { //$NON-NLS-1$
			public void run() {



				TreeItem frere_it = tree.getSelection()[0];
				IClass tosubsume = null;
				if (frere_it.getData() != null) {
					
					SubsumeDialog dial = new SubsumeDialog(ClassesViewPart.this.getSite().getShell(),Activator.getCurrentOntology());
					tosubsume = (IClass) frere_it.getData();

					dial.SetClassToSubsume(tosubsume);
					
					if (dial.open()==IDialogConstants.OK_ID) {
						IClass mere = dial.getSelectedClass();
						mere.addChild(tosubsume);
						DatabaseAdapter.saveClass(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), mere, null);

						if (frere_it.getParentItem()==rootit){
							frere_it.dispose();
						}
						
						Iterator<TreeItem> iter = classTreeItem.get(mere).iterator();
						
						while (iter.hasNext()) {

							TreeItem curit = iter.next();
							curit.setExpanded(true);
							tree.select(curit);
							creerArbre(tosubsume, curit,""); //$NON-NLS-1$

						}
						
						Activator.setCurrentClass(mere);
						
						auto.setSelectionItems(DatabaseAdapter.getClasses(Activator.getCurrentOntology()));

					}

				}

			}
		};
		this.subsumeAction.setEnabled(false);

		//

		deleteAction = new Action(Messages.getString("ClassesViewPart.13")) { //$NON-NLS-1$
			public void run() {
				if (tree.getSelection().length>0) {

					MessageBox msg = new MessageBox(ClassesViewPart.this
							.getSite().getShell(),SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
					msg.setMessage(Messages.getString("ClassesViewPart.VoulezVousSupprimerCetteClasse")); //$NON-NLS-1$
					int res = msg.open();

					if (res== SWT.OK) {

						TreeItem curit = tree.getSelection()[0];
						
						IClass todel =(IClass) curit.getData();
						
						if (todel != null) {
							
							DatabaseAdapter.deleteClass(Activator.getCurrentOntology(), todel);

							Activator.setCurrentClass(null);
									
							loadClasses();

							auto.setSelectionItems(DatabaseAdapter.getClasses(Activator.getCurrentOntology()));

						}
					}
				}
			}
		};

		deleteAction.setEnabled(false);
		deleteAction.setToolTipText(Messages.getString("ClassesViewPart.13")); //$NON-NLS-1$
		deleteAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.DELETE_ID));


		deleteRecursiveAction = new Action(Messages.getString("ClassesViewPart.14")) { //$NON-NLS-1$
			public void run() {
				if (tree.getSelection().length>0) {

					MessageBox msg = new MessageBox(ClassesViewPart.this
							.getSite().getShell(),SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
					msg.setMessage(Messages.getString("ClassesViewPart.VoulezVousSupprimerCetteClasse")); //$NON-NLS-1$
					int res = msg.open();

					if (res== SWT.OK) {

						TreeItem curit = tree.getSelection()[0];
						IClass todel =(IClass) curit.getData();
						if (todel != null) {
							
							DatabaseAdapter.deleteRecursiveClass(Activator.getCurrentOntology(), todel);
							
							Activator.setCurrentClass(null);

							loadClasses();
							
							auto.setSelectionItems(DatabaseAdapter.getClasses(Activator.getCurrentOntology()));

						}

					}
				}
			}
		};
		deleteRecursiveAction.setEnabled(false);
		deleteRecursiveAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.DELETE_ID));


		removeSubsumeAction = new Action(Messages.getString("ClassesViewPart.15")) { //$NON-NLS-1$
			public void run() {

			}
		};
		removeSubsumeAction.setEnabled(false);
	}

	//

	private void fillContextMenu(IMenuManager mgr) {
		mgr.add(addSiblingAction);
		mgr.add(addChildAction);
		//mgr.add(subsumeAction);
		mgr.add(new Separator());
		mgr.add(deleteAction);
		mgr.add(deleteRecursiveAction);
		//mgr.add(removeSubsumeAction);
	}

	//

	private void createToolbar() {
				
		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
		mgr.add(renameAction);
		mgr.add(new Separator());
		mgr.add(this.addSiblingAction);
		mgr.add(this.addChildAction);
		mgr.add(new Separator());
		mgr.add(deleteAction);
		//mgr.add(deleteRecursiveAction);

	}

	//

	private void loadClasses() {

		ArrayList<IClass> lesClasses = (ArrayList<IClass>) org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopClasses(Activator.getCurrentOntology());
		tree.clearAll(true);
		tree.removeAll();
		tree.redraw();
		classTreeItem.clear();

		rootit = new TreeItem(tree, SWT.NONE);
		rootit.setText(Messages.getString("ClassesViewPart.0")); //$NON-NLS-1$
		rootit.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.CLASSES_ID));

		for (int i = 0; i < lesClasses.size(); i++) {
			creerArbre(lesClasses.get(i), rootit, recherche);
		}

		rootit.setExpanded(true);

		IClass currentClass = Activator.getCurrentClass();
		lastSelected = rootit;
		tree.select(rootit);

		if (currentClass!=null) {

			if (lastSelected.getData()!=currentClass) {
				changeSelection(currentClass);
			} 
			
			if (tree.getSelection().length >0) {
				lastSelected = tree.getSelection()[0];
			}
		} 

	}

	//
	
	private TreeItem creerTreeItem (IClass classe,TreeItem pere) {
		TreeItem it = new TreeItem(pere, SWT.NONE);
		it.setData(classe);

		if (!classTreeItem.containsKey(classe)) {
			classTreeItem.put(classe, new ArrayList<TreeItem>());
		}

		try {
			classTreeItem.get(classe).add(it);
		} catch (Exception e) {
			e.printStackTrace();
		}

		it.setText(classe.getLabel());
		
		String imageID = org.dafoe.ontologiclevel.Activator.CLASSES_ID;
		
		if (classe.getLogicalDefinition() != null){
		
			if (classe.getLogicalDefinition().compareTo("") != 0) {
				imageID = org.dafoe.ontologiclevel.Activator.DEFINED_CLASSES_ID;
			}
			
		}
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(imageID));




		return it;
	}

	//
	
	private TreeItem creerArbre(IClass classe, TreeItem pere, String filtre) {
		try {
			TreeItem it = creerTreeItem(classe, pere);

			Iterator<IClass> itcl = classe.getChildren().iterator();

			while(itcl.hasNext()) {
				IClass mc =itcl.next();

				creerArbre(mc,it,filtre);
			}

			it.setExpanded(true);

			return it;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	//

	public void setFocus() {
	}

	//

	class CurrentClassChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent arg0) {
			try {				

				IClass currentClass = Activator.getCurrentClass();

				addChildAction.setEnabled(true);
				addSiblingAction.setEnabled(currentClass!=null);
				subsumeAction.setEnabled(currentClass!=null);
				deleteAction.setEnabled(currentClass!=null);
				deleteRecursiveAction.setEnabled(currentClass!=null);
				removeSubsumeAction.setEnabled(currentClass!=null);
				if (currentClass!=null) {

					if (lastSelected.getData()!=currentClass) {

						changeSelection(currentClass);
						lastSelected = tree.getSelection()[0];
					}

				} else {
					lastSelected = rootit;
					tree.select(rootit);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//

	class AddClassParentListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent arg0) {
			IClass currentClass = (IClass) arg0.getOldValue();
			IClass parent = (IClass) arg0.getNewValue();

			Iterator<TreeItem> iter = classTreeItem.get(currentClass).iterator();
			while (iter.hasNext()) {
				try {
					TreeItem it = iter.next();
					if (it.getParentItem()==rootit){
						it.dispose();
					}
				} catch (Exception excep) {
					excep.printStackTrace();
				}
			}

			iter = classTreeItem.get(parent).iterator();
			while (iter.hasNext()) {
				TreeItem it = (TreeItem) iter.next();
				it.setExpanded(true);
				creerArbre(currentClass,it,recherche);
			}
		}

	}

	//

	public void changeSelection(IClass classe) {

		if (classTreeItem.containsKey(classe)) {
			
			List<TreeItem> laliste = classTreeItem.get(classe);
			
			if (laliste.size() > 0) {
				tree.select(laliste.get(0));
				tree.setTopItem(laliste.get(0));
			}


		}
	}

	//

	class UpdateClasseTree implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			loadClasses();
		}

	}

	//

	class UpdateClasse implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			try {
				List<TreeItem> laliste = classTreeItem.get(Activator.getCurrentClass());
				Iterator<TreeItem> iter = laliste.iterator();
				while (iter.hasNext()) {
					TreeItem it = iter.next();
					it.setText(((IClass) Activator.getCurrentClass()).getLabel());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//

	class ChangeOntology implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			textrecherche.setText("");
			
			loadClasses();
			
			if (classTreeItem.size()>0) {
				Activator.setCurrentClass(classTreeItem.keys().nextElement());
			}

			auto.setSelectionItems(DatabaseAdapter.getClasses(Activator.getCurrentOntology()));
			
		}
	}


}