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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.terminologiclevel.terminologycard.tcautocomplete.AutocompleteTCSelector;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class TCWidget extends Composite {

	public static String SELECTION_EVENT = "TCSelected"; //$NON-NLS-1$
	
	private String title;
	
	TreeViewer treeViewer;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private String eventType;
	
	private ITerminoConcept currentTerminoConcept;
	
	private ITerm  currentTerm;
	
	// autocomplete field
	private Text txtSearchTC;
	private String recherche = "";
	private AutocompleteTCSelector auto;
	private Hashtable<ITerminoConcept, List<TreeItem>> tcsTreeItem = new Hashtable<ITerminoConcept, List<TreeItem>>();
	//
	
	public TCWidget(Composite parent, int style, String title, ITerm termArg) {
		super(parent, style);
		
		this.setLayout(new GridLayout());
		this.title = title;
				
		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		final Color TERMINO_ONTOLOGIC_LEVEL_COLOR = themeManager.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setBackground(TERMINO_ONTOLOGIC_LEVEL_COLOR);
				
		this.currentTerm = termArg;
		
		createContent();
	}

	private void createContent(){
		
		GridData gd;
		
		Composite tcComposite = new Composite(this, SWT.NONE);
		//tcComposite.setText(title);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		tcComposite.setLayoutData(gd);
		tcComposite.setLayout(new GridLayout(1, false));

		FontRegistry fontRegistry = new FontRegistry(this.getDisplay());
		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		Label label = new Label(tcComposite, SWT.NONE);
		label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
		label.setText(title);
		gd = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		label.setLayoutData(gd);
		
		Label titleBarSeparator = new Label(tcComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		// build an autocomplete text field
		txtSearchTC = new Text(tcComposite, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		txtSearchTC.setLayoutData(gridData);

		
				auto = new AutocompleteTCSelector(txtSearchTC, DatabaseAdapter
						.getTerminoConcepts());

		
		txtSearchTC.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {

				ITerminoConcept currentTC = ControlerFactoryImpl
						.getTerminoOntoControler().getCurrentTerminoConcept();

				if (e.keyCode == SWT.CR) {

					String label = txtSearchTC.getText();

					ITerminoConcept tc = auto.getTCFromSelectedLabel(label);

					if (tc != null) {

						currentTC = tc;

						ControlerFactoryImpl.getTerminoOntoControler()
								.setCurrentTerminoConcept(currentTC);

						recherche = txtSearchTC.getText();

						//changeSelection(currentTC);
						
						
						
						
						//

					} else {

						txtSearchTC.setText(currentTC.getLabel());

					}

				} else if (e.keyCode == SWT.ESC) {

					txtSearchTC.setText(currentTC.getLabel());

				}

			}

			public void keyReleased(KeyEvent e) {

			}

		});
		
		//
		
		treeViewer = new TreeViewer(tcComposite, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 250;
		treeViewer.getTree().setLayoutData(gd);
		treeViewer.setContentProvider(new TCContentProvider());
		treeViewer.setLabelProvider(new TCLabelProvider());
		
		treeViewer.setInput(getTerminoConcepts());
		
		treeViewer.expandAll();
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				TreeItem[] sel = treeViewer.getTree().getSelection();
				ITerminoConcept tc;
				
				if(sel.length != 0) {
					tc = (ITerminoConcept)(sel[0].getData());
					ITerminoConcept oldTcArg1 = currentTerminoConcept;
					currentTerminoConcept = tc;
					propertyChangeSupport.firePropertyChange(eventType, oldTcArg1, currentTerminoConcept);

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
	    		ITerminoConcept TRType = (ITerminoConcept)(dragSourceItem[0].getData());
	    		int id = TRType.getId();
	    		event.data = String.valueOf(id);
	    	} 
	    	
	    	public void dragFinished(DragSourceEvent event) { 
	    	} 
	    }); 

	}
	
	public void setEventType(String event){
		this.eventType = event;
	}
	
	public String getEventType(){
		return this.eventType;
	}
	
	public void setSelection(ITerminoConcept sel){
		
		
		if(sel != null) {

			TreeItem[] selection = getTreeItemsReferencingTC(sel);
			
			treeViewer.getTree().setSelection(selection);
			
		}
		
	}
	
	public void deselect(){
		treeViewer.getTree().deselectAll();
	}
	
	
	private TreeItem[] getTreeItemsReferencingTC(ITerminoConcept tc){
		List<TreeItem> roots = new ArrayList<TreeItem>();
		List<TreeItem> res = new ArrayList<TreeItem>();
		
		roots = Arrays.asList(treeViewer.getTree().getItems());	
		
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
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}
		  
	public ITerminoConcept getCurrentTerminoConcept(){
		return currentTerminoConcept;
	}

	public void updateContent(){
		treeViewer.setInput(getTerminoConcepts());
	}
	
	public void expandAll() {
		treeViewer.expandAll();
	}
	
	private List<ITerminoConcept> getTerminoConcepts(){
		
		org.dafoe.terminoontologiclevel.common.DatabaseAdapter.loadTerminoConcepts();
		List<ITerminoConcept> tcs = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.getTerminoConcepts();
		
		return tcs;
		
	}
	
	class TCContentProvider implements ITreeContentProvider {
		
		public Object[] getChildren(Object parentElement) {
			
			return ((ITerminoConcept)parentElement).getChildren().toArray();
			
		}

		public Object getParent(Object element) {
			
			return null;
			
		}

		public boolean hasChildren(Object element) {
			
			return ((ITerminoConcept)element).getChildren().size() != 0;
			
		}

		public Object[] getElements(Object inputElement) {
			List<ITerminoConcept> terminoConcepts = (List<ITerminoConcept>) inputElement;
			List<ITerminoConcept> roots = new ArrayList<ITerminoConcept>();
			
			if (terminoConcepts != null) {
				Iterator<ITerminoConcept> it = terminoConcepts.iterator();
				while(it.hasNext()){
					ITerminoConcept tc = it.next();
					if(tc.getParents().size() == 0) {
						roots.add(tc);
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

	public void changeSelection(ITerminoConcept tc) {
		if (tcsTreeItem.containsKey(tc)) {
			List<TreeItem> laliste = tcsTreeItem.get(tc);

			if (laliste.size() > 0) {
				treeViewer.getTree().select(laliste.get(0));
				treeViewer.getTree().setTopItem(laliste.get(0));
			}

		}
	}

	//
	class TCLabelProvider extends BaseLabelProvider implements ILabelProvider {

		public Image getImage(Object element) {
			Image img = null;
			String imgIndex = null;
			ImageDescriptor imgDesc;
			
			if (element instanceof ITerminoConcept) {
				
				ITerminoConcept tc = (ITerminoConcept) element;
				
				Set<ITerm> associatedTerms = tc.getMappedTerms();
				
				if (associatedTerms.contains(currentTerm)) {
						
					imgIndex = org.dafoe.terminologiclevel.Activator.LINK_IMG_ID;

				} else {
					
					imgIndex = org.dafoe.terminologiclevel.Activator.UNLINK_IMG_ID;
					
				}
			} 

			if (imgIndex != null) {
				
				imgDesc = org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
					.getDescriptor(imgIndex);

				img = imgDesc.createImage();
			}
			
			return img;
		}

		public String getText(Object element) {
			if (element instanceof ITerminoConcept) {
				return ((ITerminoConcept)element).getLabel();
			} else {
				return null;
			}
		}
		
	}

}
