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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelationMember;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.dafoe.terminoontologiclevel.ui.Activator;
import org.dafoe.terminoontologiclevel.ui.dialog.NewTCDialog;
import org.dafoe.terminoontologiclevel.ui.dialog.RenameTCDialog;
import org.dafoe.terminoontologiclevel.ui.dialog.SubsumeTCDialog;
import org.dafoe.terminoontologiclevel.ui.tcautocomplete.AutocompleteTCSelector;
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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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

public class TerminoConceptViewPart extends ViewPart {

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

	private Text textrecherche;

	private String recherche = ""; //$NON-NLS-1$

	private Shell shell;

	private Hashtable<ITerminoConcept, List<TreeItem>> tcsTreeItem = new Hashtable<ITerminoConcept, List<TreeItem>>();

	private UpdateTCTreePropertyChangeListener updateTCTreePropertyChangeListener;

	private AutocompleteTCSelector auto;

	private CurrentTerminoOntologyPropertyChangeListener toListener;

	//

	public TerminoConceptViewPart() {
	}

	//

	public void createPartControl(Composite parent) {

		shell = parent.getShell();

		parent.setLayout(new GridLayout(1, true));

		Composite comprecherche = new Composite(parent, SWT.NONE);
		comprecherche.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comprecherche.setLayout(new GridLayout(2, false));
		// comprecherche.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
		// false));

		textrecherche = new Text(comprecherche, SWT.BORDER);
		// textrecherche.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
		// false, 2, 1));
		textrecherche.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		DatabaseAdapter.loadTerminoConcepts();

		auto = new AutocompleteTCSelector(textrecherche, DatabaseAdapter
				.getTerminoConcepts());

		textrecherche.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {

				ITerminoConcept currentTC = ControlerFactoryImpl
						.getTerminoOntoControler().getCurrentTerminoConcept();

				if (e.keyCode == SWT.CR) {

					String label = textrecherche.getText();

					ITerminoConcept tc = auto.getTCFromSelectedLabel(label);

					if (tc != null) {

						currentTC = tc;

						ControlerFactoryImpl.getTerminoOntoControler()
								.setCurrentTerminoConcept(currentTC);

						recherche = textrecherche.getText();

						changeSelection(currentTC);

					} else {

						textrecherche.setText(currentTC.getLabel());

					}

				} else if (e.keyCode == SWT.ESC) {

					textrecherche.setText(currentTC.getLabel());

				}

			}

			public void keyReleased(KeyEvent e) {

			}

		});

		/*
		 * Button butrecherche = new Button(comprecherche,SWT.PUSH);
		 * //butrecherche.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
		 * false, false));
		 * butrecherche.setText(Messages.getString("TerminoConceptViewPart.3"));
		 * //$NON-NLS-1$
		 * 
		 * butrecherche.addSelectionListener(new SelectionListener() {
		 * 
		 * public void widgetDefaultSelected(SelectionEvent e) { }
		 * 
		 * public void widgetSelected(SelectionEvent e) { recherche =
		 * textrecherche.getText(); loadTerminoConcepts(); }});
		 */

		Button butupdate = new Button(comprecherche, SWT.PUSH);
		// butupdate.setLayoutData(new GridData(SWT.END, SWT.CENTER, false,
		// false));
		butupdate.setText(Messages.getString("TerminoConceptViewPart.4")); //$NON-NLS-1$

		butupdate.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				loadTerminoConcepts();
			}
		});

		tree = new Tree(parent, SWT.BORDER);

		tree.setLayoutData(new GridData(GridData.FILL_BOTH));

		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY;

		final DragSource source = new DragSource(tree, operations);
		final TreeItem[] dragSourceItem = new TreeItem[1];

		source.setTransfer(types);
		source.addDragListener(new DragSourceListener() {
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = tree.getSelection();
				if (selection.length > 0/* && selection[0].getItemCount() == 0 */) {
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
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL
						| DND.FEEDBACK_INSERT_AFTER | DND.FEEDBACK_SELECT;
			}

			@Override
			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				if (event.item != null) {
					ITerminoConcept newPar = (ITerminoConcept) event.item
							.getData();
					Set<ITerminoConcept> lesParents = new HashSet<ITerminoConcept>();

					lesParents = newPar.getAncestors();
					lesParents.add(newPar);

					Set<ITerminoConcept> lesEnfants = new HashSet<ITerminoConcept>();

					lesEnfants = ControlerFactoryImpl.getTerminoOntoControler()
							.getCurrentTerminoConcept().getDescendants();
					lesEnfants.add(ControlerFactoryImpl
							.getTerminoOntoControler()
							.getCurrentTerminoConcept());

					lesParents.retainAll(lesEnfants);

					if (lesParents.size() > 0) {
						MessageBox msg = new MessageBox(shell, SWT.ICON_WARNING
								| SWT.OK);
						msg.setMessage(Messages
								.getString("TerminoConceptViewPart.5")); //$NON-NLS-1$
						event.detail = DND.DROP_NONE;

						msg.open();
						return;
					}

					ITerminoConcept currentTC = ControlerFactoryImpl
							.getTerminoOntoControler()
							.getCurrentTerminoConcept();

					if (event.detail == DND.DROP_MOVE) {

						DatabaseAdapter.ChangeParent(currentTC, newPar);

					} else {

						currentTC.addParent(newPar);
						DatabaseAdapter.saveTerminoConcept(currentTC);
					}

					DatabaseAdapter.refresh(currentTC);
					ControlerFactoryImpl.getTerminoOntoControler()
							.UpdateTCsTree();
					// ES ?
					// Activator.ontoControler.setCurrentClass(Activator.getCurrentClass());
				}

			}
		});

		this.createActions();
		this.createToolbar();
		this.createContextMenu();

		toListener = new CurrentTerminoOntologyPropertyChangeListener();

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.currentTerminoOntologyEvent,
						toListener);

		updateTCTreePropertyChangeListener = new UpdateTCTreePropertyChangeListener();

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.updateTCsTreeEvent,
						updateTCTreePropertyChangeListener);

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.externalObjectToTCSourceEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								changeSelection(ControlerFactoryImpl
										.getTerminoOntoControler()
										.getCurrentTerminoConcept());
							}

						});

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.NEW_TERMINOCONCEPT_EVENT,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								loadTerminoConcepts();
							}

						});

		loadTerminoConcepts();

		lastSelected = rootit;

		tree.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				try {

					ITerminoConcept currentTC = ControlerFactoryImpl
							.getTerminoOntoControler()
							.getCurrentTerminoConcept();

					addChildAction.setEnabled(true);
					addSiblingAction.setEnabled(currentTC != null);
					subsumeAction.setEnabled(currentTC != null);
					deleteAction.setEnabled(currentTC != null);
					deleteRecursiveAction.setEnabled(currentTC != null);
					removeSubsumeAction.setEnabled(currentTC != null);
					renameAction.setEnabled(currentTC != null);

					if (tree.getSelection().length > 0) {
						lastSelected = tree.getSelection()[0];
						ControlerFactoryImpl.getTerminoOntoControler()
								.setCurrentTerminoConcept(
										((ITerminoConcept) lastSelected
												.getData()));
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		tree.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
				try {

					ITerminoConcept currentTC = ControlerFactoryImpl
							.getTerminoOntoControler()
							.getCurrentTerminoConcept();

					addChildAction.setEnabled(true);
					addSiblingAction.setEnabled(currentTC != null);
					subsumeAction.setEnabled(currentTC != null);
					deleteAction.setEnabled(currentTC != null);
					deleteRecursiveAction.setEnabled(currentTC != null);
					removeSubsumeAction.setEnabled(currentTC != null);
					renameAction.setEnabled(currentTC != null);

					if (tree.getSelection().length > 0) {
						lastSelected = tree.getSelection()[0];
						ControlerFactoryImpl.getTerminoOntoControler()
								.setCurrentTerminoConcept(
										((ITerminoConcept) lastSelected
												.getData()));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			public void mouseUp(MouseEvent e) {
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
		// getSite().registerContextMenu(menuMgr,
		// myOntologicTree.getTreeViewer());

	}

	//

	private ITerminoConcept getNodeFromInputDialog(String title) {
		NewTCDialog dial2 = new NewTCDialog(shell, title);

		dial2.open();

		if (dial2.getReturnCode() == IDialogConstants.OK_ID) {
			String label = dial2.getTCName();

			if (!label.equals("")) { //$NON-NLS-1$

				ITerminoConcept tc = DatabaseAdapter.createTerminoConcept(
						label, null);

				// ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoOntology().addTerminoOntoObject(tc);

				return tc;
			}
		}
		return null;

	}

	//

	private void createActions() {

		renameAction = new Action(Messages
				.getString("TerminoConceptViewPart.0")) { //$NON-NLS-1$
			public void run() {

				if (tree.getSelection().length > 0) {

					TreeItem curit = tree.getSelection()[0];

					ITerminoConcept tc = (ITerminoConcept) curit.getData();

					RenameTCDialog dial = new RenameTCDialog(shell, tc
							.getLabel());

					dial.open();

					if (dial.getReturnCode() == IDialogConstants.OK_ID) {

						if (tc != null) {

							DatabaseAdapter.updateTC(tc, dial.getTCName(),
									TERMINO_ONTO_OBJECT_STATE.UNKNOWN);

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
			}
		};
		renameAction.setEnabled(false);
		renameAction.setToolTipText(Messages
				.getString("TerminoConceptViewPart.0")); //$NON-NLS-1$
		renameAction.setImageDescriptor(Activator.getDefault()
				.getImageRegistry().getDescriptor(Activator.EDIT_ID));

		addChildAction = new Action(Messages.getString("ClassesViewPart.4")) { //$NON-NLS-1$
			public void run() {
				ITerminoConcept newTC = getNodeFromInputDialog(Messages
						.getString("ClassesViewPart.4")); //$NON-NLS-1$

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

					Iterator<TreeItem> iter = tcsTreeItem.get(parent)
							.iterator();

					while (iter.hasNext()) {

						TreeItem curit = iter.next();

						creerTreeItem(newTC, curit);
					}

				} else {
					creerTreeItem(newTC, rootit);
				}

				ControlerFactoryImpl.getTerminoOntoControler()
						.setCurrentTerminoConcept(newTC);
				ControlerFactoryImpl.getTerminoOntoControler()
						.setNewTerminoConceptEvent();
				DatabaseAdapter.loadTerminoConcepts();

				auto.setSelectionItems(DatabaseAdapter.getTerminoConcepts());

			}
		};
		addChildAction.setEnabled(true);
		addChildAction.setToolTipText(Messages.getString("ClassesViewPart.4")); //$NON-NLS-1$
		addChildAction.setImageDescriptor(Activator.getDefault()
				.getImageRegistry().getDescriptor(Activator.ADD_CHILD_ID));

		addSiblingAction = new Action(Messages.getString("ClassesViewPart.7")) { //$NON-NLS-1$
			public void run() {
				ITerminoConcept newTC = getNodeFromInputDialog(Messages
						.getString("ClassesViewPart.7")); //$NON-NLS-1$

				if (newTC == null) {
					return;
				}

				if (newTC.getLabel().equals("")) { //$NON-NLS-1$
					return;
				}

				TreeItem frere_it = tree.getSelection()[0];
				ITerminoConcept parent = null;

				if (frere_it.getParent() != null) {
					parent = (ITerminoConcept) frere_it.getParentItem()
							.getData();
				}

				newTC.addParent(parent);
				DatabaseAdapter.saveTerminoConcept(newTC);

				if (parent != null) {

					Iterator<TreeItem> iter = tcsTreeItem.get(parent)
							.iterator();
					while (iter.hasNext()) {
						TreeItem curit = iter.next();
						creerTreeItem(newTC, curit);

					}
				} else {

					creerTreeItem(newTC, rootit);
				}

				ControlerFactoryImpl.getTerminoOntoControler()
						.setNewTerminoConceptEvent();
				ControlerFactoryImpl.getTerminoOntoControler()
						.setCurrentTerminoConcept(newTC);

				DatabaseAdapter.loadTerminoConcepts();

				auto.setSelectionItems(DatabaseAdapter.getTerminoConcepts());

			}
		};
		addSiblingAction.setEnabled(false);
		addSiblingAction
				.setToolTipText(Messages.getString("ClassesViewPart.7")); //$NON-NLS-1$
		addSiblingAction.setImageDescriptor(Activator.getDefault()
				.getImageRegistry().getDescriptor(Activator.ADD_SIBLING_ID));

		this.subsumeAction = new Action(Messages.getString("ClassesViewPart.9")) { //$NON-NLS-1$
			public void run() {

				TreeItem frere_it = tree.getSelection()[0];
				ITerminoConcept tosubsume = null;
				if (frere_it.getData() != null) {
					SubsumeTCDialog dial = new SubsumeTCDialog(shell,
							ControlerFactoryImpl.getTerminoOntoControler()
									.getCurrentTerminoOntology());
					tosubsume = (ITerminoConcept) frere_it.getData();

					dial.SetClassToSubsume(tosubsume);
					if (dial.open() == IDialogConstants.OK_ID) {
						ITerminoConcept mere = dial.getSelectedTC();
						mere.addChild(tosubsume);
						DatabaseAdapter.saveTerminoConcept(mere);

						if (frere_it.getParentItem() == rootit) {
							// rootit.clear(rootit.indexOf(frere_it), true);
							frere_it.dispose();
						}
						Iterator<TreeItem> iter = tcsTreeItem.get(mere)
								.iterator();
						while (iter.hasNext()) {

							TreeItem curit = iter.next();
							curit.setExpanded(true);
							tree.select(curit);
							creerArbre(tosubsume, curit, ""); //$NON-NLS-1$

						}

						ControlerFactoryImpl.getTerminoOntoControler()
								.setNewTerminoConceptEvent();
						ControlerFactoryImpl.getTerminoOntoControler()
								.setCurrentTerminoConcept(mere);

						DatabaseAdapter.loadTerminoConcepts();

						auto.setSelectionItems(DatabaseAdapter
								.getTerminoConcepts());

					}

				}

			}
		};
		this.subsumeAction.setEnabled(false);

		deleteAction = new Action(Messages.getString("ClassesViewPart.13")) { //$NON-NLS-1$
			public void run() {
				if (tree.getSelection().length > 0) {

					MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION
							| SWT.OK | SWT.CANCEL);
					msg.setMessage(Messages
							.getString("TerminoConceptViewPart.8")); //$NON-NLS-1$
					int res = msg.open();

					if (res == SWT.OK) {

						TreeItem curit = tree.getSelection()[0];
						ITerminoConcept todel = (ITerminoConcept) curit
								.getData();

						if (todel != null) {

							Set<ITerminoConceptRelationMember> rels = todel
									.getTerminoConceptRelationsMember();

							if (rels == null) { // there is no relation to
												// delete before, then delete

								DatabaseAdapter.deleteTC(todel);

								loadTerminoConcepts();

								DatabaseAdapter.loadTerminoConcepts();

								auto.setSelectionItems(DatabaseAdapter
										.getTerminoConcepts());
							} else {

								if (rels.size() >0) { // relations exists, then
														// delete relation
														// before delete the tc
									MessageBox msgExistRelation = new MessageBox(shell, SWT.ICON_WARNING
											| SWT.OK );
									msgExistRelation
											.setMessage(Messages
													.getString("This Termino-concept participate to some relation(s). Please Delete them before delete the Termino-concept"));
								
									 msgExistRelation.open();
									
								} else { // relation does not exist, then delete
											// the tc
									// delete tc
									DatabaseAdapter.deleteTC(todel);

									loadTerminoConcepts();

									DatabaseAdapter.loadTerminoConcepts();

									auto.setSelectionItems(DatabaseAdapter
											.getTerminoConcepts());
								}

							}

						}

					}
				}
			}
		};
		deleteAction.setEnabled(false);
		deleteAction.setToolTipText(Messages.getString("ClassesViewPart.13")); //$NON-NLS-1$
		deleteAction.setImageDescriptor(Activator.getDefault()
				.getImageRegistry().getDescriptor(Activator.DELETE_ID));

		deleteRecursiveAction = new Action(Messages
				.getString("ClassesViewPart.14")) { //$NON-NLS-1$
			public void run() {
				if (tree.getSelection().length > 0) {

					MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION
							| SWT.OK | SWT.CANCEL);
					msg.setMessage(Messages
							.getString("TerminoConceptViewPart.9")); //$NON-NLS-1$
					int res = msg.open();

					if (res == SWT.OK) {

						TreeItem curit = tree.getSelection()[0];
						ITerminoConcept todel = (ITerminoConcept) curit
								.getData();
						if (todel != null) {

							DatabaseAdapter.deleteRecursiveTC(todel);

							loadTerminoConcepts();

							DatabaseAdapter.loadTerminoConcepts();

							auto.setSelectionItems(DatabaseAdapter
									.getTerminoConcepts());

						}

					}
				}
			}
		};
		deleteRecursiveAction.setEnabled(false);
		deleteRecursiveAction.setToolTipText(Messages
				.getString("ClassesViewPart.14"));
		deleteRecursiveAction.setImageDescriptor(Activator.getDefault()
				.getImageRegistry().getDescriptor(Activator.DELETE_ID));

		removeSubsumeAction = new Action(Messages
				.getString("ClassesViewPart.15")) { //$NON-NLS-1$
			public void run() {

			}
		};
		removeSubsumeAction.setEnabled(false);
	}

	private void fillContextMenu(IMenuManager mgr) {
		mgr.add(renameAction);
		mgr.add(new Separator());
		mgr.add(addSiblingAction);
		mgr.add(addChildAction);
		mgr.add(new Separator());
		mgr.add(deleteAction);
		mgr.add(deleteRecursiveAction);
	}

	private void createToolbar() {

		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
		mgr.add(renameAction);
		mgr.add(new Separator());
		mgr.add(this.addSiblingAction);
		mgr.add(this.addChildAction);
		mgr.add(new Separator());
		mgr.add(deleteAction);
		// mgr.add(deleteRecursiveAction);

	}

	//

	void loadTerminoConcepts() {

		ArrayList<ITerminoConcept> tcs = (ArrayList<ITerminoConcept>) DatabaseAdapter
				.getTopTerminoConcepts(ControlerFactoryImpl
						.getTerminoOntoControler().getCurrentTerminoOntology());

		System.out.println("to name ="
				+ ControlerFactoryImpl.getTerminoOntoControler()
						.getCurrentTerminoOntology().getName());
		System.out.println("loadtc size = " + tcs.size());
		tree.clearAll(true);
		tree.removeAll();
		tree.redraw();
		tcsTreeItem.clear();

		rootit = new TreeItem(tree, SWT.NONE);
		rootit.setText(Messages.getString("TerminoConceptViewPart.10")); //$NON-NLS-1$
		rootit
				.setImage(org.dafoe.terminoontologiclevel.ui.Activator
						.getDefault()
						.getImageRegistry()
						.get(
								org.dafoe.terminoontologiclevel.ui.Activator.TERMINO_CONCEPT_ID));

		for (int i = 0; i < tcs.size(); i++) {
			creerArbre(tcs.get(i), rootit, recherche);
		}

		rootit.setExpanded(true);

		ITerminoConcept currentTC = ControlerFactoryImpl
				.getTerminoOntoControler().getCurrentTerminoConcept();
		lastSelected = rootit;
		tree.select(rootit);

		if (currentTC != null) {

			if (lastSelected.getData() != currentTC) {

				changeSelection(currentTC);

			}
			if (tree.getSelection().length > 0) {
				lastSelected = tree.getSelection()[0];
			}
		}

	}

	//

	TreeItem creerTreeItem(ITerminoConcept tc, TreeItem pere) {
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

		it.setText(tc.getLabel());
		it
				.setImage(org.dafoe.terminoontologiclevel.ui.Activator
						.getDefault()
						.getImageRegistry()
						.get(
								org.dafoe.terminoontologiclevel.ui.Activator.TERMINO_CONCEPT_ID));

		return it;
	}

	//

	TreeItem creerArbre(ITerminoConcept tc, TreeItem pere, String filtre) {
		try {
			TreeItem it = creerTreeItem(tc, pere);

			Iterator<ITerminoConcept> itcl = tc.getChildren().iterator();

			/*
			 * if (filtre != "") { //$NON-NLS-1$
			 * 
			 * if (it.getText().contains(filtre)) {
			 * 
			 * //it.setForeground(new Color(shell.getDisplay(),145,0,0));
			 * it.setBackground
			 * (shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
			 * 
			 * } else {
			 * 
			 * // it.setForeground(new Color(shell.getDisplay(),200,200,200)); }
			 * }
			 */

			while (itcl.hasNext()) {
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

	public void setFocus() {
	}

	//

	public void changeSelection(ITerminoConcept tc) {
		if (tcsTreeItem.containsKey(tc)) {
			List<TreeItem> laliste = tcsTreeItem.get(tc);

			if (laliste.size() > 0) {
				tree.select(laliste.get(0));
				tree.setTopItem(laliste.get(0));
			}

		}
	}

	//

	class UpdateTCTreePropertyChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			loadTerminoConcepts();

		}

	}

	class CurrentTerminoOntologyPropertyChangeListener implements
			PropertyChangeListener {
		@Override
		public void propertyChange(java.beans.PropertyChangeEvent arg0) {
			ITerminoOntology to = ControlerFactoryImpl
					.getTerminoOntoControler().getCurrentTerminoOntology();
			// System.out.println("TerminoConceptViewPart.CurrentTerminoOntologyPropertyChangeListener.propertyChange()");
			if (to != null) {
				// txtTerminoOnto.setText(to.getName());
				loadTerminoConcepts();
			} else {

			}

		}

	}

}