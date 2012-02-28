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

package org.dafoe.terminologiclevel.terminologycard;

import java.util.List;

import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class RelationsTypesTableViewer {
	private static final String TERM_COLUMN_NAME 	= Messages.getString("RelationsTypesTableViewer.0"); //$NON-NLS-1$

	
	private TableViewer relationTypesTableViewer;
	
	private ITypeRelationTermino selectedRelationType = null;
	private List<ITypeRelationTermino> relationTypes = null;

	private static int relationTypeSortDirection;
	
	/**
	 * The constructor.
	 */
	public RelationsTypesTableViewer(Composite parent) {
		createPartControl(parent);
	}
	
	public void createPartControl(Composite parent) {
		final Composite parentControl = parent;
		
		relationTypesTableViewer = new TableViewer(parent, SWT.V_SCROLL | SWT.FULL_SELECTION);

		relationTypesTableViewer.getTable().setLinesVisible(true);
		relationTypesTableViewer.getTable().setHeaderVisible(true);


		final TableViewerColumn termColumn = new TableViewerColumn(relationTypesTableViewer, SWT.NONE, 0);
		termColumn.getColumn().setText(RelationsTypesTableViewer.TERM_COLUMN_NAME);
		termColumn.getColumn().setWidth(200);
		termColumn.setLabelProvider(new RelationTypeLabelProvider());

		termColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				
				RelationTypeSorter relationTypeSorter = new RelationTypeSorter();
				relationTypesTableViewer.setSorter(relationTypeSorter);

				relationTypesTableViewer.getTable().setSortColumn(relationTypesTableViewer.getTable().getColumn(0));

				relationTypesTableViewer.getTable().setSortDirection(
						RelationsTypesTableViewer.relationTypeSortDirection);
			}
		});


		relationTypesTableViewer.addSelectionChangedListener(new RelationTypeSelectionListener());
		
		parent.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = parentControl.getClientArea();
				int width = area.width - (2
						* relationTypesTableViewer.getTable().getBorderWidth())
						- relationTypesTableViewer.getTable().getVerticalBar().getSize().x
						- 10;

				int statusColumnWidth = 50;
				int termColumnWidth = width - statusColumnWidth;
				
				termColumn.getColumn().setWidth(termColumnWidth);
				
			}
		});

		
		relationTypesTableViewer.setContentProvider(new ViewContentProvider());

		createData();
		
		relationTypesTableViewer.setInput(relationTypes);

	}

	private void createData() {

		try {
			
			relationTypes = DatabaseAdapter.getRelationTypes();
			
		} catch (Exception e) {
			e.printStackTrace();
			relationTypes = null;
		}

	}

	public void setLayoutData(GridData aGridData){
		
		relationTypesTableViewer.getTable().setLayoutData(aGridData);
		
	}
	
	public ITypeRelationTermino getSelection() {
		
		return selectedRelationType;
		
	}
		
	class RelationTypeSelectionListener implements ISelectionChangedListener{

		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			
			if (selection instanceof IStructuredSelection){
			
				IStructuredSelection currentSelection = (IStructuredSelection) selection;
				selectedRelationType = (ITypeRelationTermino) currentSelection.getFirstElement();

			}
			
		}
		
	}
	
	class ViewContentProvider implements IStructuredContentProvider {
		private List<ITypeRelationTermino> relationTypes;

		public Object[] getElements(Object inputElement) {

			return relationTypes.toArray();
		}

		public void dispose() {

		}

		@SuppressWarnings("unchecked")
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			this.relationTypes = (List<ITypeRelationTermino>) newInput;
		}

	}
	
	class RelationTypeLabelProvider extends ColumnLabelProvider {

		public RelationTypeLabelProvider() {
			super();
		}

		/**
		 * 
		 */
		public String getText(Object element) {
			ITypeRelationTermino relationType = (ITypeRelationTermino) element;
			return relationType.getLabel() + ""; //$NON-NLS-1$
		}

		/**
		 * 
		 */
		public Image getImage(Object element) {
			return null;
		}
	}
	
	class RelationTypeSorter extends ViewerSorter {

		/**
		 *
		 */
		public RelationTypeSorter() {
			super();
			if (relationTypeSortDirection == SWT.UP) {
				relationTypeSortDirection = SWT.DOWN;
			} else {
				relationTypeSortDirection = SWT.UP;
			}		
		}

		/*
		 * (non-Javadoc)
		 */

		public int compare(Viewer viewer, Object o1, Object o2) {
			int res = 0;
			ITypeRelationTermino relationType1 = (ITypeRelationTermino) o1;
			ITypeRelationTermino relationType2 = (ITypeRelationTermino) o2;

			res = relationType1.getLabel().compareToIgnoreCase(relationType2.getLabel());

			if (relationTypeSortDirection == SWT.DOWN)
				res = -res;
			return res;

		}

		/**
		 * @return the col
		 */
		
	}

	
    /*
	private static Image createImage(Display display, Color color) {
		Image image = new Image(display, 10, 10);
		GC gc = new GC(image);
		gc.setBackground(color);
		gc.fillRectangle(0, 0, 10, 10);
		gc.dispose();

		return image;
	}
	*/

}
