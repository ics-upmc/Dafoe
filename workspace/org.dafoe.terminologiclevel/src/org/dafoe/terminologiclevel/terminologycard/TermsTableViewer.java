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

package org.dafoe.terminologiclevel.terminologycard;

import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TableItem;

public class TermsTableViewer {
	private static final String TERM_COLUMN_NAME 	= Messages.getString("TermsTableViewer.0"); //$NON-NLS-1$
	private static final String STATUS_COLUMN_NAME 	= Messages.getString("TermsTableViewer.1"); //$NON-NLS-1$
	
	private TableViewer termsTableViewer;
	private static int termSortDirection;
	private static int statusSortDirection;
	
	private ITerm selectedTerm = null;
	private List<ITerm> terms = null;
	private List<ITerm> antiFilter = null;

	/**
	 * The constructor.
	 */
	public TermsTableViewer(Composite parent, List<ITerm> terms) {
		this.terms = terms;
		createPartControl(parent);
	}
	
	public TermsTableViewer(Composite parent, List<ITerm> terms, List<ITerm> antiFilter) {
		this.terms = terms;
		this.antiFilter = antiFilter;
		createPartControl(parent);
	}
	
	public void createPartControl(Composite parent) {
		final Composite parentControl = parent;
		
		//termsTableViewer = new TableViewer(parent, SWT.VIRTUAL
		//		| SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		termsTableViewer = new TableViewer(parent, SWT.VIRTUAL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		// treeViewer.setColumnProperties(TermSaillance.getSaillanceCriteria());
		// treeViewer.setSorter(new TermSaillanceSorter(8));
		termsTableViewer.getTable().setLinesVisible(true);
		termsTableViewer.getTable().setHeaderVisible(true);


		final TableViewerColumn termColumn = new TableViewerColumn(termsTableViewer, SWT.NONE, 0);
		termColumn.getColumn().setText(TermsTableViewer.TERM_COLUMN_NAME);
		termColumn.getColumn().setWidth(200);
		termColumn.setLabelProvider(new TermLabelProvider());

		termColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				
				TermsSorter termSorter = new TermsSorter();
				termsTableViewer.setSorter(termSorter);

				termsTableViewer.getTable().setSortColumn(termsTableViewer.getTable().getColumn(0));

				termsTableViewer.getTable().setSortDirection(
						TermsTableViewer.termSortDirection);
			}
		});

		final TableViewerColumn statusColumn = new TableViewerColumn(termsTableViewer, SWT.NONE, 1);
		statusColumn.getColumn().setText(TermsTableViewer.STATUS_COLUMN_NAME);
		statusColumn.getColumn().setWidth(50);
		statusColumn.setLabelProvider(new StatusColumnLabelProvider());
		
		statusColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				StatusSorter statusSorter = new StatusSorter();
				termsTableViewer.setSorter(statusSorter);

				termsTableViewer.getTable().setSortColumn(termsTableViewer.getTable().getColumn(1));

				termsTableViewer.getTable().setSortDirection(
						TermsTableViewer.statusSortDirection);

			}
		});

		termsTableViewer.addSelectionChangedListener(new TermSelectionListener());
		
		
		parent.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = parentControl.getClientArea();
				int width = area.width - (2
						* termsTableViewer.getTable().getBorderWidth())
						- termsTableViewer.getTable().getVerticalBar().getSize().x
						- 10;

				int statusColumnWidth = 50;
				int termColumnWidth = width - statusColumnWidth;
				
				termColumn.getColumn().setWidth(termColumnWidth);
				statusColumn.getColumn().setWidth(statusColumnWidth);
				
			}
		});

		
		termsTableViewer.setContentProvider(new ViewContentProvider());
		if (this.antiFilter != null) {
			termsTableViewer.addFilter(new TermsFilter(this.antiFilter));
		}
		
		termsTableViewer.setInput(terms);

	}
	

	public void setLayoutData(GridData aGridData){
		
		termsTableViewer.getTable().setLayoutData(aGridData);
		
	}
	
	public ITerm getSelection() {
		
		return selectedTerm;
		
	}
		
	public TableViewer getTableViewer(){
		return this.termsTableViewer;
	}
	
	
	
	class TermSelectionListener implements ISelectionChangedListener{

		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			
			if (selection instanceof IStructuredSelection){
				IStructuredSelection currentSelection = (IStructuredSelection) selection;
				selectedTerm = (ITerm) currentSelection.getFirstElement();

			}
			
		}
		
	}
	
	class ViewContentProvider implements IStructuredContentProvider {
		private List<ITerm> terms = new ArrayList<ITerm>();

		public Object[] getElements(Object inputElement) {
			return terms.toArray();
		}

		public void dispose() {

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			this.terms = (List<ITerm>) newInput;
		}

	}

	class TermsFilter extends ViewerFilter {
		private List<ITerm> filter;
		
		public TermsFilter(List<ITerm> filter){
			this.filter = filter;
		}
		
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			return !filter.contains((ITerm)element);
		}

	}
	
	class TermLabelProvider extends ColumnLabelProvider {

		public TermLabelProvider() {
			super();
		}

		/**
		 * 
		 */
		public String getText(Object element) {
			ITerm term = (ITerm) element;
			return term.getLabel() + ""; //$NON-NLS-1$
		}

		/**
		 * 
		 */
		public Image getImage(Object element) {
			ITerm term = (ITerm) element;
			Image img = null;

			if (term.getVariantes() != null) {
				if(term.getVariantes().size() > 0) {
				ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
						.getDefault()
						.getImageRegistry()
						.getDescriptor(org.dafoe.terminologiclevel.Activator.STAR_TERM_IMG_ID);

				img = imgDesc.createImage();
				}
			}
			return img;
		}
	}
	
	class StatusColumnLabelProvider extends OwnerDrawLabelProvider {

		@Override
		protected void measure(Event event, Object element) {
		}

		@Override
		protected void paint(Event event, Object element) {
			ITerm term = (ITerm) element;
			if (term != null) {
				
				TERMINO_OBJECT_STATE status = term.getState();

				Color color = null;
				
				if (status == TERMINO_OBJECT_STATE.VALIDATED) {
					color = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
				} else if (status == TERMINO_OBJECT_STATE.REJECTED) {
					color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
				} else if (status == TERMINO_OBJECT_STATE.STUDIED) {
					color = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
				} else if (status == TERMINO_OBJECT_STATE.CONCEPTUALIZED) {
					color = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
				}
				
				Image img = null;
				
				if (status != TERMINO_OBJECT_STATE.CONCEPTUALIZED){
					
					img = createImage(Display.getDefault(), color);
					
				} else {
					
					ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
					.getDefault()
					.getImageRegistry()
					.getDescriptor(org.dafoe.terminologiclevel.Activator.CONCEPTUALIZED_IMG_ID);
					
					img = imgDesc.createImage();
				}
				
				
				if (img != null) {
					Rectangle bounds = ((TableItem) event.item)
							.getBounds(event.index);
					Rectangle imgBounds = img.getBounds();
					bounds.width /= 2;
					bounds.width -= imgBounds.width / 2;
					bounds.height /= 2;
					bounds.height -= imgBounds.height / 2;
					
					int x = bounds.width > 0 ? bounds.x + bounds.width : bounds.x;
					int y = bounds.height > 0 ? bounds.y + bounds.height : bounds.y;

					event.gc.drawImage(img, x, y);
				}
			}
		}
	}

	class TermsSorter extends ViewerSorter {

		/**
		 *
		 */
		public TermsSorter() {
			super();
			if (termSortDirection == SWT.UP) {
				termSortDirection = SWT.DOWN;
			} else {
				termSortDirection = SWT.UP;
			}		
		}

		/*
		 * (non-Javadoc)
		 */

		public int compare(Viewer viewer, Object o1, Object o2) {
			int res = 0;
			ITerm t1 = (ITerm) o1;
			ITerm t2 = (ITerm) o2;

			res = t1.getLabel().compareToIgnoreCase(t2.getLabel());

			if (termSortDirection == SWT.DOWN)
				res = -res;
			return res;

		}

		/**
		 * @return the col
		 */
		
	}

	class StatusSorter extends ViewerSorter {

		/**
		 *
		 */
		public StatusSorter() {
			super();
			if (statusSortDirection == SWT.UP) {
				statusSortDirection = SWT.DOWN;
			} else {
				statusSortDirection = SWT.UP;
			}		
		}

		/*
		 * (non-Javadoc)
		 */

		public int compare(Viewer viewer, Object o1, Object o2) {
			int res = 0;
			ITerm t1 = (ITerm) o1;
			ITerm t2 = (ITerm) o2;

			//VT:
			//res = t1.getState() - t2.getState();;
			res = t1.getState().getValue() - t2.getState().getValue();
			res = res < 0 ? -1 : (res > 0) ? 1 : 0;
			if (statusSortDirection == SWT.DOWN)
				res = -res;
			return res;

		}

		/**
		 * @return the col
		 */
		
	}

	private static Image createImage(Display display, Color color) {
		Image image = new Image(display, 10, 10);
		GC gc = new GC(image);
		gc.setBackground(color);
		gc.fillRectangle(0, 0, 10, 10);
		gc.dispose();

		return image;
	}

}
