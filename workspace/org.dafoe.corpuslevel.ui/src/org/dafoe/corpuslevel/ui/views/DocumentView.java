/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/
package org.dafoe.corpuslevel.ui.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.Collator;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.model.IControlerTerminology;
import org.dafoe.corpuslevel.common.DatabaseAdapter;
import org.dafoe.corpuslevel.ui.Activator;
import org.dafoe.corpuslevel.ui.documentautocomplete.AutocompleteDocumentSelector;
import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;


//



public class DocumentView extends org.eclipse.ui.part.ViewPart {

	private int direction = 0;
	private TableViewer Document_viewer;
	private IControlerTerminology terminoControler = ControlerFactoryImpl.getTerminologyControler();
	private List<IDocument> documents;
	private Action deleteAction;
	private Text txtSearch;
	
	
	//
	private IDocument currentDocument;
	private AutocompleteDocumentSelector auto;
	
	
	//VT:
	private Action refreshAction;
	//
	
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
	}

	@Override
	public void setPartProperty(String key, String value) {
		super.setPartProperty(key, value);
	}

	//org.dafoe.ui.thirdparty.corpus.model.ICorpusApi corpusApi;


	class DocumentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return  ((List<IDocument>) parent).toArray();
		}
	}

	class DocumentLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			switch (index) {
			case 0:
				return getText(((IDocument) obj).getName());
			case 1:
				return getText(((IDocument) obj).getId());
			default:
				return getText(((IDocument) obj).getName());
			}
		}
		public Image getColumnImage(Object obj, int index) {
			return null;
		}
		public Image getImage(Object obj) {
			return null;
		}
	}

	private static class DocumentComparator extends ViewerComparator {
		int sens =1;

		public DocumentComparator(int s) {
			sens=s;

		}

		public int compare(Viewer viewer,Object e1,Object e2) {
			try {
				IDocument t1 = (IDocument)e1;
				IDocument t2 = (IDocument)e2;
				Collator myCollator = Collator.getInstance();
				return myCollator.compare(t1.getName(), t2.getName())*sens;
			} catch (Exception excep) {
				return 0;
			}

		}
	}


	@Override
	public void createPartControl(final Composite parent) {

		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		comp.setLayout(layout);


		Composite lecorps = comp;

		Composite comp0 = new Composite(lecorps,SWT.NONE);
		GridData GDCOMP = new GridData(GridData.FILL_HORIZONTAL);
		comp0.setLayoutData(GDCOMP);
		GridLayout GL2 = new GridLayout(3,false);
		comp0.setLayout(GL2);

		txtSearch = new Text(comp0,SWT.SINGLE); 
		GridData GDText = new GridData(GridData.FILL_HORIZONTAL);
		GDText.horizontalSpan=2;
		txtSearch.setLayoutData(GDText);

		//VT: 
		//btnSearch = new Button(comp0,SWT.PUSH);
		//btnSearch.addSelectionListener(RechercheListener);
		//btnSearch.setText(Messages.getString("DocumentView.1")); //$NON-NLS-1$

		Document_viewer = new TableViewer(lecorps, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		Document_viewer.getTable().setHeaderVisible(true);
		Document_viewer.getTable().addControlListener(new ControlListener() {

			@Override
			public void controlMoved(ControlEvent e) {

			}

			@Override
			public void controlResized(ControlEvent e) {
				int valeur = Document_viewer.getTable().getClientArea().width;
				Document_viewer.getTable().getColumn(0).setWidth(valeur/10*8);
				Document_viewer.getTable().getColumn(1).setWidth(valeur/10*2);

			}});

		org.dafoe.corpuslevel.common.DatabaseAdapter.loadDocuments();

		documents = org.dafoe.corpuslevel.common.DatabaseAdapter.getDocuments();

		Document_viewer.setContentProvider(new DocumentProvider());
		Document_viewer.setInput(documents);
		Document_viewer.setLabelProvider(new DocumentLabelProvider());
		
		
		//VT: add autocomplete document search
		auto = new AutocompleteDocumentSelector(txtSearch,documents);
		
		txtSearch.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				
				if (e.keyCode == SWT.CR){
					
					String label = txtSearch.getText();
					
					IDocument doc = auto.getDocumentFromSelectedLabel(label);
					
					if  (doc != null) {
						
						currentDocument = doc;
						
						ControlerFactoryImpl.getTerminologyControler().setCurrentDocument(doc);
						
						Document_viewer.setSelection(new StructuredSelection(currentDocument));
						
						Document_viewer.reveal(currentDocument);
						
					} else {
						
						txtSearch.setText(currentDocument.getName());
						
					}
					
					
				} else if (e.keyCode == SWT.ESC){
					
					txtSearch.setText(currentDocument.getName());
					
				}
				
			}

			public void keyReleased(KeyEvent e) {
				
			}
			
		});
		
		

		org.eclipse.swt.layout.GridData GD = new GridData(GridData.FILL_BOTH);
		//lab.setLayoutData(GD);

		Table table = Document_viewer.getTable();
		table.setLayoutData(GD);
		TableColumn TC = new TableColumn(table, SWT.CENTER);
		TC.setText(Messages.getString("DocumentView.2")); //$NON-NLS-1$
		TC.setWidth(200);
		TC.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Table myTable = Document_viewer.getTable();
				if (direction <1) {
					direction = 1;
					Document_viewer.setComparator(new DocumentComparator(direction));
					myTable.setSortColumn(myTable.getColumn(0));
					myTable.setSortDirection(SWT.UP);

				} else if (direction == 1) {
					direction = -1;
					Document_viewer.setComparator(new DocumentComparator(direction));
					myTable.setSortColumn(myTable.getColumn(0));
					myTable.setSortDirection(SWT.DOWN);

				}
				Document_viewer.refresh();
			}
		});

		table.showColumn(TC);
		TC = new TableColumn(table, SWT.CENTER);
		TC.setText(Messages.getString("DocumentView.3")); //$NON-NLS-1$
		TC.setWidth(200);
		table.showColumn(TC);


		if(documents.size()>0) {
			Document_viewer.getTable().setSelection(0);
			IDocument curdoc =  documents.get(0);
			terminoControler.setCurrentDocument(curdoc);
			terminoControler.setCurrentCorpus(curdoc.getRelatedCorpus());
		}

		Document_viewer.refresh();

		Document_viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();

				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss = (IStructuredSelection) selection;
					if (!ss.isEmpty()) {
						java.lang.Object selo = ss.getFirstElement();//ss.toArray()[0];
						if (selo instanceof IDocument) {
							IDocument curdoc = (IDocument) selo;
							terminoControler.setCurrentDocument(curdoc);
							terminoControler.setCurrentCorpus(curdoc.getRelatedCorpus());
						}

					}

				}
			}});
		
		// abonnement au loadedCorpus listener
		/*
		updateLoadedCorpusPropertyChangeListener= new DocumentViewPartUpdateloadedCorpusPropertyChangeListener();
		
		ControlerFactoryImpl.getTerminologyControler()
		.addPropertyChangeListener(ControlerFactoryImpl.CURRENT_LOADED_CORPUS_EVENT, updateLoadedCorpusPropertyChangeListener);
		*/

		createActions();
		createContextMenu();
		contributeToActionBars();
	}

	//

	
	//

	private ISelectionListener listener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {

			showSelection(sourcepart, selection);

		}
	};

	/**
	 * Shows the given selection in this view.
	 */
	public void showSelection(IWorkbenchPart sourcepart, ISelection selection) {
		System.out.println(selection.getClass().toString());
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
		}
	}

	//

	@Override
	public void setFocus() {
		Document_viewer.getControl().setFocus();
	}

	//

	private void createActions() {

		deleteAction = new Action(Messages.getString("DocumentView.4")) { //$NON-NLS-1$
			public void run() {
				if (Document_viewer.getTable().getSelection().length>0) {

					MessageBox msg = new MessageBox(Activator.getDefault()
							.getWorkbench().getDisplay().getActiveShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
					msg.setMessage(Messages.getString("DocumentView.5")); //$NON-NLS-1$
					int res = msg.open();

					IDocument currentDoc = org.dafoe.controler.factory.ControlerFactoryImpl
					.getTerminologyControler().getCurrentDocument();

					System.out.println( " selected doc= "+currentDoc.getName()); //$NON-NLS-1$

					if (res == SWT.OK) {
						DatabaseAdapter.deleteDocument(currentDoc);
						removeDocumentFromViewer(currentDoc);
						refreshDocumentViewer();
						System.out.println("-------------------  OK  -----------------"); //$NON-NLS-1$
						
					}

				}

			}
		};
		deleteAction.setEnabled(true);
		
		
		refreshAction = new Action("Refresh") { //$NON-NLS-1$
			public void run() {
			
				org.dafoe.corpuslevel.common.DatabaseAdapter.loadDocuments();

				documents = org.dafoe.corpuslevel.common.DatabaseAdapter.getDocuments();
                Document_viewer.getTable().removeAll();
                
				Document_viewer.setInput(documents);
				
				Document_viewer.refresh();
				
			}
		};
		refreshAction.setEnabled(true);
		refreshAction.setToolTipText("refresh documents list");
		refreshAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.REFRESH_IMG_ID));
	}

	//

	private void fillContextMenu(IMenuManager mgr) {

		//mgr.add(new Separator());
		mgr.add(deleteAction);
		mgr.add(new Separator());
		mgr.add(refreshAction);
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
		Menu menu = menuMgr.createContextMenu(Document_viewer.getTable());
		Document_viewer.getTable().setMenu(menu);

	}


	//VT:
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	//VT:
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshAction);
		
	}
	
	//VT: for refresh document_viewer after loading corpus

	public List<IDocument> getDocuments() {
		return documents;
	}

	//

	public TableViewer getDocument_viewer() {
		return Document_viewer;
	}

	//

	public  void addDocumentToViewer(IDocument doc){
		documents.add(doc);

	}

	//

	public void removeDocumentFromViewer(IDocument doc){
		documents.remove(doc);
	}

	//

	public void refreshDocumentViewer(){
		Document_viewer.refresh();
	}
	
class DocumentViewPartUpdateloadedCorpusPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
						
			List<ICorpus> loadedCorpus = ControlerFactoryImpl.getTerminologyControler().getCurrentLoadedCorpus();
			
			for (ICorpus currentCorpus : loadedCorpus) {
				
				for (IDocument currentDocument : currentCorpus.getDocuments()) {
					System.out.println("--------------> add doc= "+ currentDocument.getName());
					documents.add(currentDocument);
				}
			}
			
			try {
				Document_viewer.refresh();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}
	}  // end DocumentViewPartUpdateloadedCorpusPropertyChangeListener class 



} // end DocumentView class
