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

import java.beans.PropertyChangeListener;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.model.IControlerTerminology;
import org.dafoe.corpuslevel.ui.Activator;
import org.dafoe.corpuslevel.ui.termautocomplete.AutocompleteTermSelector;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;




public class TermView extends ViewPart {

	private int direction = 0;
	private TableViewer mots_viewer;
	private List<ITerm> mots;

	private IControlerTerminology terminoControler=ControlerFactoryImpl.getTerminologyControler();
	private CurrentDocumentPropertyChangeListener currentDocumentPropertyChangeListener;

	private Action actionAllTerms;
	private Action actionDocTerms;

	private TableColumn TC;

	private Text tRecherche;
	//private Button BRecherche;
	
	private ITerm currentTerm;

	private AutocompleteTermSelector auto;
	
	//

	class DocumentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return  ((java.util.List<ITerm>) parent).toArray();
		}
	}

	//

	class DocumentLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			switch (index) {
			case 0:
				return getText(((ITerm) obj).getLabel());
			default:
				return getText(((ITerm) obj).getLabel());
			}
		}
		public Image getColumnImage(Object obj, int index) {
			return null;
		}
		public Image getImage(Object obj) {
			return null;
		}
	}

	//

	private static class TermComparator extends ViewerComparator {
		int sens =1;
		public TermComparator(int s) {
			sens=s;
		}

		public int compare(Viewer viewer,Object e1,Object e2) {
			try {
				ITerm t1 = (ITerm)e1;
				ITerm t2 = (ITerm)e2;
				Collator myCollator = Collator.getInstance();
				return myCollator.compare(t1.getLabel(), t2.getLabel())*sens;
			} catch (Exception excep) {
				return 0;
			}

		}
	}

	@Override
	public void createPartControl(Composite parent) {

		Color myColor = new Color(Display.getDefault(), new RGB(200,212,123));
		parent.setBackground(myColor);

		org.dafoe.corpuslevel.common.DatabaseAdapter.loadTerms();
		mots = org.dafoe.corpuslevel.common.DatabaseAdapter.getTerms();

		System.out.println("TermView.createPartControl() - mots.size() = " + mots.size());

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

		tRecherche = new Text(comp0,SWT.SINGLE);
		GridData GDText = new GridData(GridData.FILL_HORIZONTAL);
		GDText.horizontalSpan=2;
		tRecherche.setLayoutData(GDText);
		
		auto = new AutocompleteTermSelector(tRecherche, mots);

		tRecherche.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				
				if (e.keyCode == SWT.CR){
					
					String label = tRecherche.getText();
					
					ITerm term = auto.getTermFromSelectedLabel(label);
					
					if  (term != null) {
						
						currentTerm = term;
						
						ControlerFactoryImpl.getTerminologyControler().setCurrentTerm(term);
						
						mots_viewer.setSelection(new StructuredSelection(currentTerm));
						
						mots_viewer.reveal(currentTerm);
						
					} else {
						
						tRecherche.setText(currentTerm.getLabel());
						
					}
					
					
				} else if (e.keyCode == SWT.ESC){
					
					tRecherche.setText(currentTerm.getLabel());
					
				}
				
			}

			public void keyReleased(KeyEvent e) {
				
			}
			
		});

		
		
		/*
		BRecherche = new Button(comp0,SWT.PUSH);

		BRecherche.addSelectionListener(RechercheListener);
		BRecherche.setText(Messages.getString("TermView.1")); //$NON-NLS-1$
		 */
		
		mots_viewer = new TableViewer(lecorps, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

		mots_viewer.setContentProvider(new DocumentProvider());
		mots_viewer.setInput(mots);
		mots_viewer.setLabelProvider(new DocumentLabelProvider());

		org.eclipse.swt.layout.GridData GD = new GridData(GridData.FILL_BOTH);

		Table table = mots_viewer.getTable();
		table.setLayoutData(GD);

		TC = new TableColumn(table, SWT.LEFT);
		TC.setText(Messages.getString("TermView.4")); //$NON-NLS-1$
		TC.setWidth(200);
		table.showColumn(TC);

		TC.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Table myTable = mots_viewer.getTable();
				if (direction <1) {
					direction = 1;
					mots_viewer.setComparator(new TermComparator(direction));
					myTable.setSortColumn(TC);
					myTable.setSortDirection(SWT.UP);

				} else if (direction == 1) {
					direction = -1;
					mots_viewer.setComparator(new TermComparator(direction));
					myTable.setSortColumn(TC);
					myTable.setSortDirection(SWT.DOWN);
				}
				mots_viewer.refresh();
			}
		});


		mots_viewer.getTable().setHeaderVisible(true);


		mots_viewer.getTable().addControlListener(new ControlListener() {

			@Override
			public void controlMoved(ControlEvent e) {
			}

			@Override
			public void controlResized(ControlEvent e) {
				int valeur = mots_viewer.getTable().getClientArea().width;
				mots_viewer.getTable().getColumn(0).setWidth(valeur);

			}});


		if(mots.size()>0) {
			mots_viewer.getTable().setSelection(0);
		}

		mots_viewer.refresh();
		mots_viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {


			}
		});

		mots_viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();

				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss = (IStructuredSelection) selection;
					if (!ss.isEmpty()) {
						java.lang.Object selo = ss.getFirstElement();//ss.toArray()[0];
						if (selo instanceof ITerm) {
							currentTerm = (ITerm) selo;
							terminoControler.setCurrentTerm(currentTerm);

						}

					}

				}
			}});

		currentDocumentPropertyChangeListener = new CurrentDocumentPropertyChangeListener();

		ControlerFactoryImpl.getTerminologyControler().addPropertyChangeListener(ControlerFactoryImpl.currentDocumentEvent, currentDocumentPropertyChangeListener);

		makeActions();

		contributeToActionBars();
	}

	//

	private SelectionListener RechercheListener = new SelectionListener() {

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			IDocument curdoc = ControlerFactoryImpl.getTerminologyControler().getCurrentDocument();

			if (curdoc == null) {
				org.dafoe.corpuslevel.common.DatabaseAdapter.loadTerms(tRecherche.getText());

			} else {
				org.dafoe.corpuslevel.common.DatabaseAdapter.loadTerms(curdoc,tRecherche.getText());

			}
			mots = org.dafoe.corpuslevel.common.DatabaseAdapter.getTerms();

			//mots=org.dafoe.corpuslevel.ui.Activator.getDefault().dafoeSession.findAllInstance(ITerm.class);
			mots_viewer.setInput(mots);
		}

	};

	//

	@Override
	public void setFocus() {
	}

	//

	class CurrentDocumentPropertyChangeListener implements PropertyChangeListener {
		@Override
		public void propertyChange(java.beans.PropertyChangeEvent arg0) {
			IDocument curdoc = ControlerFactoryImpl.getTerminologyControler().getCurrentDocument();

			if (curdoc != null) {
				
				org.dafoe.corpuslevel.common.DatabaseAdapter.loadTerms(curdoc);
				
				mots = org.dafoe.corpuslevel.common.DatabaseAdapter.getTerms();
				
				System.out.println("# terms loaded = " + mots.size());
				
			} else {
				mots = new ArrayList<ITerm>();
			}
			
			mots_viewer.setInput(mots);
		}

	}

	//

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		//fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	//

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionAllTerms);
		manager.add(actionDocTerms);

	}

	//

	private void makeActions() {
		actionAllTerms = new Action() {
			public void run() {
				org.dafoe.corpuslevel.common.DatabaseAdapter.loadTerms();
				mots = org.dafoe.corpuslevel.common.DatabaseAdapter.getTerms();

				//mots=org.dafoe.corpuslevel.ui.Activator.getDefault().dafoeSession.findAllInstance(ITerm.class);
				mots_viewer.setInput(mots);
			}
		};
		actionAllTerms.setText(Messages.getString("TermView.2")); //$NON-NLS-1$
		actionAllTerms.setToolTipText(Messages.getString("TermView.5")); //$NON-NLS-1$
		actionAllTerms.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.ALL_TERMS));

		actionDocTerms = new Action() {
			public void run() {
				IDocument curdoc = ControlerFactoryImpl.getTerminologyControler().getCurrentDocument();
				if (curdoc!=null) {
					org.dafoe.corpuslevel.common.DatabaseAdapter.loadTerms(curdoc);
					mots = org.dafoe.corpuslevel.common.DatabaseAdapter.getTerms();

					//mots=org.dafoe.corpuslevel.ui.Activator.getDefault().dafoeSession.findAllInstance(ITerm.class);
					mots_viewer.setInput(mots);
				}
			}
		};
		actionDocTerms.setText(Messages.getString("TermView.6")); //$NON-NLS-1$
		actionDocTerms.setToolTipText(Messages.getString("TermView.7")); //$NON-NLS-1$
		actionDocTerms.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.DOC_TERMS));

	}
}
