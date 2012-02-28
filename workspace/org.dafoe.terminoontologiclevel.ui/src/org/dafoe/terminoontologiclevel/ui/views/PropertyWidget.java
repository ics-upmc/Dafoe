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
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminological.model.ISentence;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class PropertyWidget extends Composite {

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	private String deletionEventType;

	private Composite mainComposite;

	private Tree tree;

	private TreeItem rootit;

	private Hashtable<IProperty, List<TreeItem>> propTreeItem = new Hashtable<IProperty, List<TreeItem>>();

	private Shell shell;

	private boolean showDataTypeProperty;

	public PropertyWidget(Composite parent, int style,
			boolean showDataTypeProperty) {
		super(parent, style);

		this.setLayout(new GridLayout());

		GridData gd;

		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		this.setLayoutData(gd);

		shell = parent.getShell();

		this.showDataTypeProperty = showDataTypeProperty;

		createContent();
	}

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

		loadProperties();

		tree.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("widgetDefaultSelected"); //$NON-NLS-1$
			}

			public void widgetSelected(SelectionEvent e) {
				System.out.println("widgetSelected"); //$NON-NLS-1$
			}
		});

		//

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
				int id;
				String type;

				if (showDataTypeProperty) {
					id = ((IDatatypeProperty) (dragSourceItem[0].getData()))
							.getId();
					type = "IDatatypeProperty"; //$NON-NLS-1$
				} else {
					id = ((IObjectProperty) (dragSourceItem[0].getData()))
							.getId();
					type = "IObjectProperty"; //$NON-NLS-1$
				}

				event.data = type + "#" + String.valueOf(id); //$NON-NLS-1$
			}

			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE)
					dragSourceItem[0].dispose();
				dragSourceItem[0] = null;
			}
		});

	}

	public void showSelection(IProperty prop) {
		List<TreeItem> treeItems = null;

		if (prop instanceof IObjectProperty) {
			treeItems = propTreeItem.get((IObjectProperty) prop);
		} else if (prop instanceof IDatatypeProperty) {
			treeItems = propTreeItem.get((IDatatypeProperty) prop);
		}

		if (treeItems != null) {
			TreeItem[] tis = treeItems.toArray(new TreeItem[] {});

			tree.setSelection(tis);
		}
	}

	public void setDeletionEventType(String event) {
		this.deletionEventType = event;
	}

	public String getDeletionEventType() {
		return this.deletionEventType;
	}

	public List<ISentence> getSelection() {
		List<ISentence> res = new ArrayList<ISentence>();

		return res;
	}

	public void refresh() {
		// sentencesTableViewer.setInput(???);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}

	void loadProperties() {

		List<IProperty> properties = org.dafoe.ontologiclevel.common.DatabaseAdapter
				.getTopProperties(ControlerFactoryImpl.getOntoControler()
						.getCurrentOntology());
		tree.clearAll(true);
		tree.removeAll();
		tree.redraw();
		propTreeItem.clear();

		rootit = new TreeItem(tree, SWT.NONE);
		rootit.setText(Messages.getString("ClassesViewPart.0")); //$NON-NLS-1$
		rootit
				.setImage(org.dafoe.terminoontologiclevel.ui.Activator
						.getDefault()
						.getImageRegistry()
						.get(
								org.dafoe.terminoontologiclevel.ui.Activator.CLASS_IMG_ID));

		for (int i = 0; i < properties.size(); i++) {

			IProperty prop = properties.get(i);

			if (prop instanceof IObjectProperty) {
				if (!showDataTypeProperty) {
					creerArbre((IObjectProperty) prop, rootit, ""); //$NON-NLS-1$
				}

			} else {
				if (showDataTypeProperty) {
					creerArbre((IDatatypeProperty) prop, rootit, ""); //$NON-NLS-1$
				}
			}
		}

		rootit.setExpanded(true);
	}

	TreeItem creerTreeItem(IProperty property, TreeItem pere) {
		try {
			TreeItem it = new TreeItem(pere, SWT.NONE);
			it.setData(property);

			if (!propTreeItem.containsKey(property)) {
				propTreeItem.put(property, new ArrayList<TreeItem>());
			}

			try {
				propTreeItem.get(property).add(it);
			} catch (Exception e) {
				e.printStackTrace();
			}

			it.setText(property.getLabel());

			if (property instanceof IObjectProperty) {
				it
						.setImage(org.dafoe.terminoontologiclevel.ui.Activator
								.getDefault()
								.getImageRegistry()
								.get(
										org.dafoe.terminoontologiclevel.ui.Activator.OBJECT_PROPERTY_IMG_ID));
			} else {
				it
						.setImage(org.dafoe.terminoontologiclevel.ui.Activator
								.getDefault()
								.getImageRegistry()
								.get(
										org.dafoe.terminoontologiclevel.ui.Activator.DATATYPE_PROPERTY_IMG_ID));
			}

			return it;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	TreeItem creerArbre(IObjectProperty property, TreeItem pere, String filtre) {
		try {
			TreeItem it = creerTreeItem(property, pere);

			Iterator<IObjectProperty> itcl = property.getChildren().iterator();

			if (filtre != "") { //$NON-NLS-1$
				if (it.getText().contains(filtre)) {

					it.setForeground(new Color(shell.getDisplay(), 145, 0, 0));

				} else {
					it.setForeground(new Color(shell.getDisplay(), 200, 200,
							200));
				}
			}

			while (itcl.hasNext()) {
				IObjectProperty mc = itcl.next();

				creerArbre(mc, it, filtre);
			}
			it.setExpanded(true);

			return it;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	//

	TreeItem creerArbre(IDatatypeProperty property, TreeItem pere, String filtre) {
		try {
			TreeItem it = creerTreeItem(property, pere);

			Iterator<IDatatypeProperty> itcl = property.getChildren()
					.iterator();

			if (filtre != "") { //$NON-NLS-1$
				if (it.getText().contains(filtre)) {

					it.setForeground(new Color(shell.getDisplay(), 145, 0, 0));

				} else {
					it.setForeground(new Color(shell.getDisplay(), 200, 200,
							200));
				}
			}

			while (itcl.hasNext()) {
				IDatatypeProperty mc = itcl.next();

				creerArbre(mc, it, filtre);
			}
			it.setExpanded(true);

			return it;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	//

	public void changeSelection(IProperty property) {
		if (propTreeItem.containsKey(property)) {
			List<TreeItem> laliste = propTreeItem.get(property);
			if (laliste.size() > 0) {
				tree.select(laliste.get(0));
			}

		}
	}

}
