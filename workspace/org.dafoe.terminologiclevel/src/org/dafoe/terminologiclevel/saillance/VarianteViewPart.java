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

package org.dafoe.terminologiclevel.saillance;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;


public class VarianteViewPart extends ViewPart {
	public static final String ID = "org.dafoe.terminologiclevel.saillance.SaillanceVarianteViewPartId"; //$NON-NLS-1$

	static final Logger logger = Logger.getLogger(SaillanceViewPart.class); 
	
	private VariantViewPartCurrentTermPropertyChangeListener currentTermPropertyChangeListener;
	private VariantViewPartNewVariantPropertyChangeListener newVariantPropertyChangeListener;
	private VariantViewPartNewStatusPropertyChangeListener newStatusPropertyChangeListener;
	private VariantViewPartDetachVariantPropertyChangeListener detachVariantPropertyChangeListener;
	private VariantViewPartTermsDeletedPropertyChangeListener termsDeletedPropertyChangeListener;
	
	private TableViewer tableVarianteViewer;

	private Composite generalComposite;
	
	private static final String PROD_T = Messages
			.getString("SaillanceViewPart.1"); //$NON-NLS-1$
	private static final String PROD_E = Messages
			.getString("SaillanceViewPart.2"); //$NON-NLS-1$
	private static final String TF_IDF = Messages
			.getString("SaillanceViewPart.3"); //$NON-NLS-1$
	private static final String NB_VAR = Messages
			.getString("SaillanceViewPart.4"); //$NON-NLS-1$
	private static final String FREQ = Messages
			.getString("SaillanceViewPart.5"); //$NON-NLS-1$
	private static final String POIDS_TYPO = Messages
			.getString("SaillanceViewPart.6"); //$NON-NLS-1$
	private static final String POIDS_POS = Messages
			.getString("SaillanceViewPart.7"); //$NON-NLS-1$
	private static final String TERM = Messages
			.getString("SaillanceViewPart.8"); //$NON-NLS-1$
	private static final String STATUS = Messages
			.getString("SaillanceViewPart.9"); //$NON-NLS-1$

	private static String[] saillanceCriteria = new String[] { TERM, PROD_T,
			PROD_E, TF_IDF, NB_VAR, FREQ, POIDS_TYPO, POIDS_POS, STATUS };

	public TableViewer getTableVariantViewer() {
		return this.tableVarianteViewer;
	}


	public VariantViewPartNewVariantPropertyChangeListener getVariantViewPartNewVariantPropertyChangeListener(){
		return this.newVariantPropertyChangeListener;
	}
	
	class ViewContentProvider implements IStructuredContentProvider {

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((List<ITerm>) inputElement).toArray();
		}

		public void dispose() {

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			//ESvariants = (List<ITerm>) newInput;
		}

	}

	/**
	 * The constructor.
	 */
	public VarianteViewPart() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

		
		// General Grid Layout
		GridLayout generalGl = new GridLayout(1, false);
		generalComposite = new Composite(parent, SWT.NONE);
		generalComposite.setLayout(generalGl);
		generalComposite.setSize(new Point(800, 500));

		// the tree
		GridData myGd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);

		tableVarianteViewer = new TableViewer(generalComposite, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		// treeViewer.setColumnProperties(TermSaillance.getSaillanceCriteria());
		// treeViewer.setSorter(new TermSaillanceSorter(8));
		tableVarianteViewer.getTable().setLinesVisible(true);
		tableVarianteViewer.getTable().setHeaderVisible(true);
		tableVarianteViewer.getTable().setLayoutData(myGd);

		TableViewerColumn column;

		for (int i = 0; i < saillanceCriteria.length - 1; i++) {

			column = new TableViewerColumn(tableVarianteViewer, SWT.NONE, i);
			column.getColumn().setText(saillanceCriteria[i]);

			if (i == 0) {

				column.getColumn().setWidth(200);

			} else {

				column.getColumn().setWidth(50);

			}
			column.setLabelProvider(new StandardColumnLabelProvider(i));

			final int j = i;

			column.getColumn().addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {

					tableVarianteViewer.setSorter(new TermSaillanceSorter(j));
					tableVarianteViewer.getTable().setSortColumn(
							tableVarianteViewer.getTable().getColumn(j));
					tableVarianteViewer.getTable().setSortDirection(
							TermSaillanceSorter.getDirection());
				}
			});

		}

		column = new TableViewerColumn(tableVarianteViewer, SWT.CENTER,
				saillanceCriteria.length - 1);

		column.getColumn().setText(Messages.getString("SaillanceViewPart.20")); //$NON-NLS-1$
		column.getColumn().setWidth(50);
		column.setLabelProvider(new StatusColumnLabelProvider());
		column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				tableVarianteViewer.setSorter(new TermSaillanceSorter(
						saillanceCriteria.length - 1));

				tableVarianteViewer.getTable().setSortColumn(
						tableVarianteViewer.getTable().getColumn(
								saillanceCriteria.length - 1));

				tableVarianteViewer.getTable().setSortDirection(
						TermSaillanceSorter.getDirection());
			}

		});
		
		
		column = new TableViewerColumn(tableVarianteViewer, SWT.CENTER,
				saillanceCriteria.length );

		column.getColumn().setText(Messages.getString("SaillanceViewPart.31"));  //$NON-NLS-1$
		column.getColumn().setWidth(50);
		column.getColumn().setMoveable(true);
		column.setLabelProvider(new LinguisticStatusColumnLabelProvider());
		
		

		tableVarianteViewer.setContentProvider(new ViewContentProvider());

		//EStableVarianteViewer.setInput(variants);
		tableVarianteViewer.setInput(new ArrayList<ITerm>());

		tableVarianteViewer.getTable().setSortColumn(
				tableVarianteViewer.getTable().getColumn(0));
		tableVarianteViewer.getTable().setSortDirection(SWT.UP);

		tableVarianteViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				List<ITerm> currentVariants = new ArrayList<ITerm>();
				TableItem[] sel = tableVarianteViewer.getTable().getSelection();

				for (int i = 0; i < sel.length; i++) {
					currentVariants.add((ITerm) (sel[i].getData()));
				}

				ControlerFactoryImpl.getTerminologyControler().setCurrentVariants(currentVariants);

			}
		});

		generalComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = generalComposite.getClientArea();
				Point preferredSize = tableVarianteViewer.getTable()
						.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * tableVarianteViewer.getTable().getBorderWidth();
				if (preferredSize.y > area.height + tableVarianteViewer.getTable().getHeaderHeight()) {

					Point vBarSize = tableVarianteViewer.getTable().getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = tableVarianteViewer.getTable().getSize();

				if (oldSize.x > area.width) {

					tableVarianteViewer.getTable().getColumn(0).setWidth(width * 30 / 100);					
					for(int i = 1; i < saillanceCriteria.length; i++) {
						int colWidth = width * 70 / 100 / (saillanceCriteria.length - 1);
						tableVarianteViewer.getTable().getColumn(i).setWidth(colWidth);
					}
					tableVarianteViewer.getTable().setSize(area.width, area.height);
				} else {

					tableVarianteViewer.getTable().setSize(area.width, area.height);
					tableVarianteViewer.getTable().getColumn(0).setWidth(width * 30 / 100);					
					for(int i = 1; i < saillanceCriteria.length; i++) {
						int colWidth = width * 70 / 100 / (saillanceCriteria.length - 1);
						tableVarianteViewer.getTable().getColumn(i).setWidth(colWidth);
					}
				}
			}
		});

		currentTermPropertyChangeListener = new VariantViewPartCurrentTermPropertyChangeListener();
		newVariantPropertyChangeListener = new VariantViewPartNewVariantPropertyChangeListener();
		newStatusPropertyChangeListener = new VariantViewPartNewStatusPropertyChangeListener();
		detachVariantPropertyChangeListener = new VariantViewPartDetachVariantPropertyChangeListener();
		termsDeletedPropertyChangeListener = new VariantViewPartTermsDeletedPropertyChangeListener();
		
		
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.currentTermEvent, currentTermPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.newVariantEvent, newVariantPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.newStatusEvent, newStatusPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.newVariantDetachedEvent, detachVariantPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
		.addPropertyChangeListener(ControlerFactoryImpl.termsDeletedEvent, termsDeletedPropertyChangeListener);
		
		//makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		//contributeToActionBars();
		
		getSite().setSelectionProvider(tableVarianteViewer);

	}

	private void hookContextMenu() {

		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(tableVarianteViewer.getTable());
		tableVarianteViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, tableVarianteViewer);
	}



	private void hookDoubleClickAction() {

		tableVarianteViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {

				System.out.println("Double Click"); //$NON-NLS-1$
				
			}
		});
	}


	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableVarianteViewer.getControl().setFocus();
	}
	
	class VariantViewPartCurrentTermPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

			List<ITerm> variants = null;
			
			if (currentTerm != null) 
				variants = UtilTools.setToList(currentTerm.getVariantes());
			
			tableVarianteViewer.setInput(variants);
			
			 ControlerFactoryImpl.getTerminologyControler().setCurrentVariants(variants);
		}
		
	}

	class VariantViewPartNewVariantPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

			List<ITerm> variants = null;
			
			if (currentTerm != null) 
				variants = UtilTools.setToList(currentTerm.getVariantes());
			
			tableVarianteViewer.setInput(variants);			
			
			ControlerFactoryImpl.getTerminologyControler().setCurrentVariants(variants);
		}
	}
	
	class VariantViewPartNewStatusPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

			List<ITerm> variants = null;
			
			if (currentTerm != null) 
				variants = UtilTools.setToList(currentTerm.getVariantes());
			
			tableVarianteViewer.setInput(variants);			
		}
	}

	class VariantViewPartDetachVariantPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

			List<ITerm> variants = null;
			
			if (currentTerm != null) 
				variants = UtilTools.setToList(currentTerm.getVariantes());
			
			tableVarianteViewer.setInput(variants);			
			
			ControlerFactoryImpl.getTerminologyControler().setCurrentVariants(variants);
		}
	}

	class VariantViewPartTermsDeletedPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			List<ITerm> variants = ControlerFactoryImpl.getTerminologyControler().getCurrentVariants();
			
			// 
			if (variants != null) {
			
				if (variants.size() > 0) {
					
					if (variants.get(0).getState() == TERMINO_OBJECT_STATE.DELETED) {
						
						tableVarianteViewer.setInput(new ArrayList<ITerm>());
						
					}
				}
			
			}
		}
	}

}