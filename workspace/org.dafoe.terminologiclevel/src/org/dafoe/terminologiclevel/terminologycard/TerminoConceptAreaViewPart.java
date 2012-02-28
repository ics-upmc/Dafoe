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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;

public class TerminoConceptAreaViewPart extends ViewPart {
	public static final String ID = "org.dafoe.terminologiclevel.terminologycard.TerminoConceptAreaViewPartId"; //$NON-NLS-1$

	private static int direction;

	private ITerm currentTerm;
	private Shell shell;
	private TableViewer associatedConcepts;
	private TableViewerColumn col;
	private Composite parentRef;
	
	public TerminoConceptAreaViewPart() {
	}

	@Override
	public void createPartControl(Composite parent) {
		
		this.parentRef = parent;
		
		GridLayout mainLayout = new GridLayout();
		mainLayout.marginBottom = 2;
		mainLayout.marginHeight = 5;
		mainLayout.marginRight = 15;
		mainLayout.numColumns = 2;
		parent.setLayout(mainLayout);
		
		Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.getString("TerminoConceptAreaViewPart.1") + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		GridData gridData = new GridData(SWT.BEGINNING);
		gridData.horizontalIndent = 15;
		label.setLayoutData(gridData);

		new Label(parent, SWT.NONE);
		
		associatedConcepts = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		associatedConcepts.getTable().setLinesVisible(true);
		associatedConcepts.getTable().setHeaderVisible(true);
		associatedConcepts.setContentProvider(new TerminoConceptContentProvider());
		
		col = new TableViewerColumn(associatedConcepts, SWT.NONE, 0);
		col.getColumn().setResizable(false);
		col.getColumn().setText(Messages.getString("TerminoConceptAreaViewPart.3")); //$NON-NLS-1$
		col.setLabelProvider(new TerminoConceptLabelProvider());
		
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalIndent = 15;
		gridData.horizontalSpan = 2;
		
		associatedConcepts.getTable().setLayoutData(gridData);
		
		col.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter();

			}
		});
		
		associatedConcepts.getTable().setSortColumn(col.getColumn());
		associatedConcepts.getTable().setSortDirection(SWT.UP);

		
	    DropTarget target = new DropTarget(associatedConcepts.getTable(), DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);
	    
		final TextTransfer textTransfer = TextTransfer.getInstance(); 
		Transfer[] types = new Transfer[] {textTransfer}; 
		target.setTransfer(types); 
		
		target.addDropListener(new DropTargetAdapter() {
			public void dragEnter(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = (event.operations & DND.DROP_COPY) != 0 ? DND.DROP_COPY : DND.DROP_NONE;
				}

				for (int i = 0, n = event.dataTypes.length; i < n; i++) {
					if (TextTransfer.getInstance().isSupportedType(event.dataTypes[i])) {
						event.currentDataType = event.dataTypes[i];
					}
				}
			}

			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
			}
			
			public void drop(DropTargetEvent event) {
				if (TextTransfer.getInstance().isSupportedType(event.currentDataType)) {
					// Get the dropped data
					//DropTarget target = (DropTarget) event.widget;
					String data = (String) event.data;
					
					ITerminoConcept testTc = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.getTerminoConceptByLabel(data);
					
					if (testTc == null){
					
						ITerminoConcept tc = org.dafoe.terminoontologiclevel.common
							.DatabaseAdapter.createTerminoConcept(data, currentTerm);

						TERMINO_OBJECT_STATE status = TERMINO_OBJECT_STATE.CONCEPTUALIZED;

						List<ITerm> terms = new ArrayList<ITerm>();
						terms.add(currentTerm);
						DatabaseAdapter.updateTermsStatus(terms, status);

						ControlerFactoryImpl.getTerminologyControler().setNewStatus();	

						DatabaseAdapter.initTerminoConceptOccurrences(currentTerm, tc);
						
						ControlerFactoryImpl.getTerminoOntoControler().setNewTerminoConceptEvent();
						ControlerFactoryImpl.getTerminoOntoControler().setCurrentTerminoConcept(tc);

						updateInformationArea();		

					} else {
						
						String msg = Messages.getString("TerminoConceptAreaViewPart.4") + data + Messages.getString("TerminoConceptAreaViewPart.5"); //$NON-NLS-1$ //$NON-NLS-2$
						msg += Messages.getString("TerminoConceptAreaViewPart.6"); //$NON-NLS-1$
						
						MessageDialog.openWarning(shell, Messages.getString("TerminoConceptAreaViewPart.7"), msg); //$NON-NLS-1$
					}
				}
			}
		});
		
		new Label(parent, SWT.NONE);
		
		shell = parent.getShell();
		
	    
		ControlerFactoryImpl.getTerminologyControler().addPropertyChangeListener(ControlerFactoryImpl.currentTermEvent, new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				
				updateInformationArea();		
			}
			
		});
		
		parent.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = parentRef.getClientArea();
				Point preferredSize = associatedConcepts.getTable()
						.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width
						- 2
						* associatedConcepts.getTable()
								.getBorderWidth();
				if (preferredSize.y > area.height
						+ associatedConcepts.getTable()
								.getHeaderHeight()) {

					Point vBarSize = associatedConcepts.getTable()
							.getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = associatedConcepts.getTable()
						.getSize();

				if (oldSize.x > area.width) {

					col.getColumn().setWidth(width - 40);
					associatedConcepts.getTable().setSize(
							area.width, area.height);
				} else {

					associatedConcepts.getTable().setSize(
							area.width, area.height);
					col.getColumn().setWidth(width - 40);
				}
			}
		});
		
		
		associatedConcepts.getTable().addListener(
				SWT.MouseDoubleClick,
				new AssociatedConceptTableViewerListener());
		
		
	    updateInformationArea();		

	}

	@Override
	public void setFocus() {

	}
	
	public void setSorter() {
		
		associatedConcepts.setSorter(new TerminoConceptSorter());
		associatedConcepts.getTable().setSortColumn(col.getColumn());
		associatedConcepts.getTable().setSortDirection(TerminoConceptAreaViewPart.getDirection());
		
	}
	
	
	public void updateInformationArea(){
		
		currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
		
		if (currentTerm != null) {
			ArrayList<ITerminoConcept> terminoConcepts = (ArrayList<ITerminoConcept>)DatabaseAdapter.getTerminoConceptFromTerm(currentTerm);
			associatedConcepts.setInput(terminoConcepts);
		}

	}

	public boolean checkTerminoConceptExistency(ITerminoConcept tc){
		boolean res = false;

		
		
		return res;
	}
	
	public static int getDirection() {
		return direction;
	}
	
	class TerminoConceptContentProvider implements IStructuredContentProvider {

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			ArrayList<ITerminoConcept> terminoConcepts = (ArrayList<ITerminoConcept>)inputElement;
			Object[] tmp = terminoConcepts.toArray();
			return tmp;
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	
	}
	
	//
	
	class TerminoConceptLabelProvider extends ColumnLabelProvider {
		public String getText(Object element){
			ITerminoConcept tc = (ITerminoConcept)element;
			return tc.getLabel();
		}
		
		public Image getImage(Object element){
	
			return null;
		}
	}

	class TerminoConceptSorter extends ViewerSorter {

		public TerminoConceptSorter() {
			super();
		
			if (direction == SWT.UP) {
				
					direction = SWT.DOWN;
					
			} else {
				
					direction = SWT.UP;
				
			} 
			
		}

		
		/*
		 * (non-Javadoc)
		 */

		public int compare(Viewer viewer, Object o1, Object o2) {
			int res = 0;
			ITerminoConcept tc1 = (ITerminoConcept) o1;
			ITerminoConcept tc2 = (ITerminoConcept) o2;

			res = tc1.getLabel().compareToIgnoreCase(tc2.getLabel());
			
			if (direction == SWT.DOWN) {
				res = -res;
			}

			return res;
		}

	}

	//
	
	class AssociatedConceptTableViewerListener implements Listener {
		public void handleEvent(Event event) {
			
			Point pt = new Point(event.x, event.y);
			TableItem item = associatedConcepts.getTable().getItem(pt);

			if (item != null) {

				ITerminoConcept tc = (ITerminoConcept) (item.getData());

				ControlerFactoryImpl.getTerminoOntoControler().setCurrentTerminoConcept(tc);
				
				ControlerFactoryImpl.getTerminoOntoControler().setExternalObjectToTCSourceEvent();
				
				switchPerspective();
			
			}
			
		}
		
	}

	//
	
	private void switchPerspective() {
		
		final IWorkbenchWindow workbenchWindow = this.getSite().getWorkbenchWindow();
		
		IPerspectiveDescriptor newPerspective = null;
		
		IPerspectiveDescriptor[] perspectives = workbenchWindow.getWorkbench().getPerspectiveRegistry().getPerspectives();
		
		for (int i = 0; i < perspectives.length; i++) {
			if (perspectives[i].getId().compareTo("org.dafoe.terminoontologiclevel.ui.perspectiveTerminoConceptId") == 0){ //$NON-NLS-1$
				newPerspective = perspectives[i];
			}
		}
		
		if (workbenchWindow.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective() != newPerspective) {
			workbenchWindow.getActivePage().setPerspective(newPerspective);
		}
		
	}
}
