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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.ontologiclevel.common.DatabaseAdapter;
import org.dafoe.terminoontologiclevel.ui.dialog.NewClassDialog2;
import org.dafoe.terminoontologiclevel.ui.dialog.SubsumeClassDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class ClassWidget extends Composite {

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	private Action addSiblingAction;

	private Action addChildAction;

	private Action deleteAction;

	private Action deleteRecursiveAction;

	private Action subsumeAction;

	private Composite mainComposite;

	private Tree tree;

	private TreeItem lastSelected = null;

	private TreeItem rootit;

	private Hashtable<IClass, List<TreeItem>> classTreeItem = new Hashtable<IClass, List<TreeItem>>();

	private Shell shell;

	//

	public ClassWidget(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new GridLayout());

		GridData gd;

		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		this.setLayoutData(gd);

		shell = parent.getShell();

		createContent();
	}

	//	

	private void createContent() {

		GridData gd;

		mainComposite = new Composite(this, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		mainComposite.setLayoutData(gd);
		mainComposite.setLayout(new GridLayout(1, false));

		tree = new Tree(mainComposite, SWT.NONE);

		tree.setLayoutData(new GridData(GridData.FILL_BOTH));

		org.dafoe.ontologiclevel.common.DatabaseAdapter
				.loadClasses(ControlerFactoryImpl.getOntoControler()
						.getCurrentOntology());

		loadClasses();

		lastSelected = rootit;

		tree.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {

				IClass currentClass = ControlerFactoryImpl.getOntoControler()
						.getCurrentClass();

				if (tree.getSelection().length > 0) {
					lastSelected = tree.getSelection()[0];
					ControlerFactoryImpl.getOntoControler().setCurrentClass(
							((IClass) tree.getSelection()[0].getData()));
				}

				addChildAction.setEnabled(true);
				addSiblingAction.setEnabled(currentClass != null);
				subsumeAction.setEnabled(currentClass != null);
				deleteAction.setEnabled(currentClass != null);
				deleteRecursiveAction.setEnabled(currentClass != null);

			}
		});

		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };

		DragSource source = new DragSource(tree, DND.DROP_MOVE | DND.DROP_COPY);
		final TreeItem[] dragSourceItem = new TreeItem[1];

		source.setTransfer(types);

		source.addDragListener(new DragSourceListener() {
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = tree.getSelection();
				if (selection.length > 0) {
					event.doit = true;
					dragSourceItem[0] = selection[0];
				} else {
					event.doit = false;
				}
			};

			public void dragSetData(DragSourceEvent event) {
				int id = ((IClass) (dragSourceItem[0].getData())).getId();
				event.data = "IClass" + "#" + String.valueOf(id); //$NON-NLS-1$ //$NON-NLS-2$
			}

			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE)
					dragSourceItem[0].dispose();
				dragSourceItem[0] = null;
			}
		});

		this.createActions();
		this.createContextMenu();
	}

	// 

	public void showSelection(IClass cl) {
		List<TreeItem> treeItems = classTreeItem.get(cl);

		if (treeItems != null) {

			TreeItem[] tis = treeItems.toArray(new TreeItem[] {});

			tree.setSelection(tis);

		}
	}

	//

	public List<ISentence> getSelection() {
		List<ISentence> res = new ArrayList<ISentence>();

		// to be done

		return res;
	}

	//

	public void refresh() {
		// sentencesTableViewer.setInput(???);
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

	void loadClasses() {

		ArrayList<IClass> lesClasses = (ArrayList<IClass>) org.dafoe.ontologiclevel.common.DatabaseAdapter
				.getTopClasses(ControlerFactoryImpl.getOntoControler()
						.getCurrentOntology());
		tree.clearAll(true);
		tree.removeAll();
		tree.redraw();
		classTreeItem.clear();

		rootit = new TreeItem(tree, SWT.NONE);
		rootit.setText(Messages.getString("ClassesViewPart.0")); //$NON-NLS-1$
		rootit
				.setImage(org.dafoe.terminoontologiclevel.ui.Activator
						.getDefault()
						.getImageRegistry()
						.get(
								org.dafoe.terminoontologiclevel.ui.Activator.CLASS_IMG_ID));

		for (int i = 0; i < lesClasses.size(); i++) {
			creerArbre(lesClasses.get(i), rootit, ""); //$NON-NLS-1$
		}

		rootit.setExpanded(true);

		IClass currentClass = ControlerFactoryImpl.getOntoControler()
				.getCurrentClass();
		lastSelected = rootit;
		tree.select(rootit);

		if (currentClass != null) {

			if (lastSelected.getData() != currentClass) {

				changeSelection(currentClass);

			}

			if (tree.getSelection().length > 0) {
				lastSelected = tree.getSelection()[0];
			}
		}

	}

	//

	TreeItem creerTreeItem(IClass classe, TreeItem pere) {
		TreeItem it = new TreeItem(pere, SWT.NONE);

		it.setData(classe);

		pere.setExpanded(true);

		if (!classTreeItem.containsKey(classe)) {
			classTreeItem.put(classe, new ArrayList<TreeItem>());
		}

		try {
			classTreeItem.get(classe).add(it);
		} catch (Exception e) {
			e.printStackTrace();
		}

		it.setText(classe.getLabel());

		String imageID = org.dafoe.terminoontologiclevel.ui.Activator.CLASS_IMG_ID;

		if (classe.getLogicalDefinition() != null) {

			if (classe.getLogicalDefinition().compareTo("") != 0) {
				imageID = org.dafoe.terminoontologiclevel.ui.Activator.DEFINED_CLASS_IMG_ID;
			}

		}
		it.setImage(org.dafoe.terminoontologiclevel.ui.Activator.getDefault()
				.getImageRegistry().get(imageID));

		return it;
	}

	//

	TreeItem creerArbre(IClass classe, TreeItem pere, String filtre) {
		try {
			TreeItem it = creerTreeItem(classe, pere);

			Iterator<IClass> itcl = classe.getChildren().iterator();

			if (filtre != "") { //$NON-NLS-1$
				if (it.getText().contains(filtre)) {

					it.setForeground(new Color(shell.getDisplay(), 145, 0, 0));

				} else {
					it.setForeground(new Color(shell.getDisplay(), 200, 200,
							200));
				}
			}

			while (itcl.hasNext()) {
				IClass mc = itcl.next();

				creerArbre(mc, it, filtre);
			}

			it.setExpanded(true);

			return it;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	//

	public void changeSelection(IClass classe) {
		if (classTreeItem.containsKey(classe)) {
			List<TreeItem> laliste = classTreeItem.get(classe);
			if (laliste.size() > 0) {
				tree.select(laliste.get(0));
			}

		}
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
		// getSite().registerContextMenu(menuMgr,
		// myOntologicTree.getTreeViewer());

	}

	//

	private void fillContextMenu(IMenuManager mgr) {
		mgr.add(addSiblingAction);
		mgr.add(addChildAction);
		// mgr.add(subsumeAction);
		mgr.add(new Separator());
		mgr.add(deleteAction);
		mgr.add(deleteRecursiveAction);
	}

	//

	private void createActions() {
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
				DatabaseAdapter.saveClass(ControlerFactoryImpl
						.getOntoControler().getCurrentOntology(), newClasse,
						null);
				if (parent != null) {

					Iterator<TreeItem> iter = classTreeItem.get(parent)
							.iterator();
					while (iter.hasNext()) {
						TreeItem curit = iter.next();
						creerTreeItem(newClasse, curit);

					}
				} else {
					creerTreeItem(newClasse, rootit);
				}

				ControlerFactoryImpl.getOntoControler().setCurrentClass(
						newClasse);

			}
		};

		addChildAction.setEnabled(true);

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

				DatabaseAdapter.saveClass(ControlerFactoryImpl
						.getOntoControler().getCurrentOntology(), newClasse,
						null);

				if (parent != null) {

					Iterator<TreeItem> iter = classTreeItem.get(parent)
							.iterator();
					while (iter.hasNext()) {
						TreeItem curit = iter.next();
						creerTreeItem(newClasse, curit);

					}
				} else {

					creerTreeItem(newClasse, rootit);
				}

				ControlerFactoryImpl.getOntoControler().setCurrentClass(
						newClasse);
			}
		};
		addSiblingAction.setEnabled(false);

		//

		this.subsumeAction = new Action(Messages.getString("ClassesViewPart.9")) { //$NON-NLS-1$
			public void run() {

				TreeItem frere_it = tree.getSelection()[0];
				IClass tosubsume = null;
				if (frere_it.getData() != null) {

					SubsumeClassDialog dial = new SubsumeClassDialog(shell,
							ControlerFactoryImpl.getOntoControler()
									.getCurrentOntology());
					tosubsume = (IClass) frere_it.getData();

					dial.SetClassToSubsume(tosubsume);
					if (dial.open() == IDialogConstants.OK_ID) {
						IClass mere = dial.getSelectedClass();
						mere.addChild(tosubsume);
						DatabaseAdapter.saveClass(ControlerFactoryImpl
								.getOntoControler().getCurrentOntology(), mere,
								null);

						if (frere_it.getParentItem() == rootit) {
							// rootit.clear(rootit.indexOf(frere_it), true);
							frere_it.dispose();
						}
						Iterator<TreeItem> iter = classTreeItem.get(mere)
								.iterator();
						while (iter.hasNext()) {

							TreeItem curit = iter.next();
							curit.setExpanded(true);
							tree.select(curit);
							creerArbre(tosubsume, curit, ""); //$NON-NLS-1$

						}

						ControlerFactoryImpl.getOntoControler()
								.setCurrentClass(mere);
					}
				}
			}
		};
		this.subsumeAction.setEnabled(false);

		//

		deleteAction = new Action(Messages.getString("ClassesViewPart.13")) { //$NON-NLS-1$
			public void run() {
				if (tree.getSelection().length > 0) {

					MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION
							| SWT.OK | SWT.CANCEL);
					msg
							.setMessage(Messages
									.getString("ClassesViewPart.VoulezVousSupprimerCetteClasse")); //$NON-NLS-1$
					int res = msg.open();

					if (res == SWT.OK) {

						TreeItem curit = tree.getSelection()[0];
						IClass todel = (IClass) curit.getData();
						if (todel != null) {

							DatabaseAdapter.deleteClass(ControlerFactoryImpl
									.getOntoControler().getCurrentOntology(),
									todel);
							loadClasses();
						}
					}
				}
			}
		};

		deleteAction.setEnabled(false);

		deleteRecursiveAction = new Action(Messages
				.getString("ClassesViewPart.14")) { //$NON-NLS-1$
			public void run() {
				if (tree.getSelection().length > 0) {

					MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION
							| SWT.OK | SWT.CANCEL);
					msg
							.setMessage(Messages
									.getString("ClassesViewPart.VoulezVousSupprimerCetteClasse")); //$NON-NLS-1$
					int res = msg.open();

					if (res == SWT.OK) {

						TreeItem curit = tree.getSelection()[0];
						IClass todel = (IClass) curit.getData();
						if (todel != null) {
							DatabaseAdapter.deleteRecursiveClass(
									ControlerFactoryImpl.getOntoControler()
											.getCurrentOntology(), todel);
							loadClasses();
						}
					}
				}
			}
		};
		deleteRecursiveAction.setEnabled(false);

	}

	//

	private IClass getNodeFromInputDialog() {
		NewClassDialog2 dial = new NewClassDialog2(shell);

		if (dial.open() == IDialogConstants.OK_ID) {
			String le_nom = dial.getClassName();

			if (!le_nom.equals("")) { //$NON-NLS-1$

				IClass maValeur = new ClassImpl();
				maValeur.setLabel(dial.getClassName());
				maValeur.setState(ONTO_OBJECT_STATE.VALIDATED);

				System.out.println(ControlerFactoryImpl.getOntoControler()
						.getCurrentOntology());

				// ControlerFactoryImpl.getOntoControler().getCurrentOntology().addOntoObject(maValeur);

				return maValeur;
			}
		}
		return null;

	}

}
