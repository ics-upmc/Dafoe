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

import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.ontologiclevel.Dialog.NewPropertyDialog2;
import org.dafoe.ontologiclevel.autocomplete.property.AutocompletePropertySelector;
import org.dafoe.ontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;


public class PropertiesViewPart extends ViewPart {

	Hashtable<IProperty,List<TreeItem>> propTreeItem = new Hashtable<IProperty,List<TreeItem>>();
	
	private Action renameAction;
	
	private Action addSiblingAction;

	private Action addChildAction;
	
	private Action deleteAction;
	
	private Tree tree;

	private TreeItem rootit;
	
	private Text textrecherche;
	
	private String recherche = ""; //$NON-NLS-1$

	private Shell shell;

	private AutocompletePropertySelector auto;
	
	//
	
	public PropertiesViewPart() {
	}

	public void createPartControl(Composite parent) {
		
		shell = parent.getShell();
		
		parent.setLayout(new GridLayout(1,true));
		
		Composite comprecherche = new Composite(parent, SWT.NONE);
		comprecherche.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comprecherche.setLayout(new GridLayout(2, false));
		
		textrecherche = new Text(comprecherche, SWT.BORDER);
		textrecherche.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		auto = new AutocompletePropertySelector(textrecherche, DatabaseAdapter.getProperties(Activator.getCurrentOntology()));

		textrecherche.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				
				IProperty currentProperty = Activator.getCurrentProperty();
				
				if (e.keyCode == SWT.CR){
					
					String label = textrecherche.getText();
					
					IProperty prop = auto.getPropertyFromSelectedLabel(label);
					
					if  (prop != null) {
						
						currentProperty = prop;
						
						Activator.setCurrentProperty(currentProperty);
						
						recherche = textrecherche.getText();

						changeSelection(currentProperty);

						
					} else {
						
						textrecherche.setText(currentProperty.getLabel());
						
					}
					
					
				} else if (e.keyCode == SWT.ESC){
					
					if(currentProperty != null) {
						textrecherche.setText(currentProperty.getLabel());
					}
				}
				
			}

			public void keyReleased(KeyEvent e) {
				
			}
			
		});

		tree = new Tree(parent,SWT.NONE);

		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		this.createActions();
		this.createToolbar();
		this.createContextMenu();

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.currentPropertyEvent, 
				new CurrentPropertyChangeListener());
		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.currentOntologyEvent, 
				new changeOntology());
		
		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.updatePropertyEvent, 
				new updatePropertyListener());

		loadProperties();
		
		tree.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				try {
					IProperty currentProperty = Activator.getCurrentProperty();

					if (tree.getSelection().length >0) {

						currentProperty = ((IProperty) tree.getSelection()[0].getData());
						Activator.setCurrentProperty(currentProperty);
					}

					addChildAction.setEnabled(true);
					addSiblingAction.setEnabled(currentProperty!=null);
					deleteAction.setEnabled(currentProperty!=null);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
		}});
		
	}

	//
	
	void loadProperties() {
		
		
		List<IProperty> lesClasses =org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopProperties(Activator.getCurrentOntology());
		tree.clearAll(true);
		tree.removeAll();
		tree.redraw();
		propTreeItem.clear();
		
		rootit = new TreeItem(tree,SWT.NONE);
		rootit.setText("Thing"); //$NON-NLS-1$
		rootit.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
						.getImageRegistry().get(
								org.dafoe.ontologiclevel.Activator.CLASSES_ID));
		
		for (int i=0;i<lesClasses.size();i++) {
			IProperty prop = lesClasses.get(i);
			if (prop instanceof IObjectProperty) { 
				creerArbre((IObjectProperty) prop,rootit,recherche);
			} else {
				creerArbre((IDatatypeProperty) prop,rootit,recherche);
			}
			
		}
		rootit.setExpanded(true);
		
	}
	

	//
	
	TreeItem creerTreeItem (IProperty classe,TreeItem pere) {
		try {
			TreeItem it = new TreeItem(pere,SWT.NONE);
			it.setData(classe);
			
			if (!propTreeItem.containsKey(classe)) {
				propTreeItem.put(classe, new ArrayList<TreeItem>());
			}
			
			try {
				propTreeItem.get(classe).add(it);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			it.setText(classe.getLabel());
			if (classe instanceof IObjectProperty) {
				it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
							.getImageRegistry().get(
									org.dafoe.ontologiclevel.Activator.OBJECT_PROPERTIES_ID));
			} else {
				it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
						.getImageRegistry().get(
								org.dafoe.ontologiclevel.Activator.DATA_PROPERTIES_ID));
			}
			
			return it;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//
	
	TreeItem creerArbre(IObjectProperty prop, TreeItem pere, String filtre) {
		try {
			TreeItem it = creerTreeItem(prop, pere);
			
			Iterator<IObjectProperty>  itcl = prop.getChildren().iterator();
			
			if (filtre!="") { //$NON-NLS-1$
				if (it.getText().contains(filtre)) {
					
					//it.setForeground(new Color(getSite().getShell().getDisplay(),145,0,0));
					it.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
				} else {
					//it.setForeground(new Color(getSite().getShell().getDisplay(),200,200,200));
				}
			}
			

			while(itcl.hasNext()) {
				IObjectProperty mc =itcl.next();
				
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
	
	TreeItem creerArbre(IDatatypeProperty prop, TreeItem pere, String filtre) {
		try {
			TreeItem it = creerTreeItem(prop, pere);
			
			Iterator<IDatatypeProperty>  itcl = prop.getChildren().iterator();
			if (filtre!="") { //$NON-NLS-1$
				if (it.getText().contains(filtre)) {
					
					//it.setForeground(new Color(getSite().getShell().getDisplay(),145,0,0));
					it.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
					
				} else {
					//it.setForeground(new Color(getSite().getShell().getDisplay(),200,200,200));
				}
			}
			
			while(itcl.hasNext()) {
				IDatatypeProperty mc =itcl.next();
				
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
	
	/*
	private String getNameNodeFromInputDialog() {
		InputDialog refNodeName = new InputDialog(PropertiesViewPart.this
				.getSite().getShell(), Messages.getString("PropertiesViewPart.0"), //$NON-NLS-1$
				Messages.getString("PropertiesViewPart.1"), "", null); //$NON-NLS-1$ //$NON-NLS-2$
		if (refNodeName.open() == Window.OK) {
			return refNodeName.getValue();
		} else {
			return null;
		}
	}
*/
	
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
		renameAction.setToolTipText(Messages.getString("PropertiesViewPart.2")); //$NON-NLS-1$
		renameAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.EDIT_ID));
		
		
		
		addChildAction = new Action(Messages.getString("PropertiesViewPart.3")) { //$NON-NLS-1$
			public void run() {
				NewPropertyDialog2 dial = new NewPropertyDialog2(getSite().getShell(),Activator.getCurrentOntology());
				
				dial.setParentProp(Activator.getCurrentProperty());
				
				int res = dial.open();
				
				if (res == Window.OK) {
					loadProperties();
	
					Activator.setCurrentProperty(dial.getProperty());
					
					auto.setSelectionItems(DatabaseAdapter.getProperties(Activator.getCurrentOntology()));
				}
			}
		};
		addChildAction.setEnabled(true);
		addChildAction.setToolTipText(Messages.getString("PropertiesViewPart.3"));  //$NON-NLS-1$
		addChildAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.ADD_CHILD_ID));


		addSiblingAction = new Action(Messages.getString("PropertiesViewPart.5")) { //$NON-NLS-1$
			public void run() {
				NewPropertyDialog2 dial = new NewPropertyDialog2(getSite().getShell(),Activator.getCurrentOntology());
				
				if (dial.open()==Window.OK) {
					loadProperties();
					Activator.setCurrentProperty(dial.getProperty());
					auto.setSelectionItems(DatabaseAdapter.getProperties(Activator.getCurrentOntology()));
				}
			}
		};
		addSiblingAction.setEnabled(false);
		addSiblingAction.setToolTipText(Messages.getString("PropertiesViewPart.5"));  //$NON-NLS-1$
		addSiblingAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.ADD_SIBLING_ID));


		

		deleteAction = new Action(Messages.getString("PropertiesViewPart.4")) {  //$NON-NLS-1$
			public void run() {
				if (tree.getSelection().length>0) {
					
					MessageBox msg = new MessageBox(getSite().getShell(),SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
					msg.setMessage("Voulez-vous supprimer cette propriété"); //$NON-NLS-1$
					int res = msg.open();
					
					if (res == SWT.OK) {
						
						TreeItem curit = tree.getSelection()[0];
						IProperty todel =(IProperty) curit.getData();
						if (todel != null) {
							DatabaseAdapter.deleteProperty(todel);
							loadProperties();
							auto.setSelectionItems(DatabaseAdapter.getProperties(Activator.getCurrentOntology()));
						}
						
					}
				}
			}
		};
		
		deleteAction.setEnabled(false);
		deleteAction.setToolTipText(Messages.getString("PropertiesViewPart.4"));  //$NON-NLS-1$
		deleteAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.DELETE_ID));


		/*deleteRecursiveAction = new Action(Messages.getString("PropertiesViewPart.14")) { //$NON-NLS-1$
			public void run() {
				
			}
		};
		deleteRecursiveAction.setEnabled(false);*/

		/*removeSubsumeAction = new Action(Messages.getString("PropertiesViewPart.15")) { //$NON-NLS-1$
			public void run() {
				super.run();
			}
		};
		removeSubsumeAction.setEnabled(false);*/
	}

	//
	
	private void fillContextMenu(IMenuManager mgr) {
		//mgr.add(addRootAction);
		mgr.add(addSiblingAction);
		mgr.add(addChildAction);
		//mgr.add(subsumeAction);
		mgr.add(new Separator());
		mgr.add(deleteAction);
		//mgr.add(deleteRecursiveAction);
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
	}

	//
	
	public void setFocus() {
	}
	
	//
	
	class CurrentPropertyChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			
			IProperty currentProp = Activator.getCurrentProperty();
			
			addSiblingAction.setEnabled(true);
			deleteAction.setEnabled(currentProp!=null);
			renameAction.setEnabled(currentProp!=null);

			if (currentProp!=null) {
				
					changeSelection(currentProp);
					
			} else {
				
				tree.select(rootit);
			}
			
		}
		
	}
	
	//
	
	class updatePropertyListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
		
			IProperty currentProp=Activator.getCurrentProperty();
		
			if (currentProp!=null) {
				changeSelection(currentProp);
				try {
					List<TreeItem> laliste = propTreeItem.get(currentProp);
					Iterator<TreeItem> iter = laliste.iterator();
					while (iter.hasNext()) {
						TreeItem it = iter.next();
						it.setText(currentProp.getLabel());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				
				tree.select(rootit);
			}
			
		}
		
	}
	
	//
	
	class changeOntology implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			loadProperties();
			auto.setSelectionItems(DatabaseAdapter.getProperties(Activator.getCurrentOntology()));
		}
		
	}

	//
	
	public void changeSelection(IProperty prop) {
		
		if (propTreeItem.containsKey(prop)) {
			List<TreeItem> laliste = propTreeItem.get(prop);
			
			if (laliste.size() > 0) {
				TreeItem curit = laliste.get(0);
				tree.select(curit);
				tree.setTopItem(curit);
			}
		}
	}
	
}
