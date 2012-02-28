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

import java.util.ArrayList;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.terminologiclevel.saillance.TermLabelProvider;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class SyntacticRelationAreaViewPart extends ViewPart {
	public static final String ID = "org.dafoe.terminologiclevel.terminologycard.SyntacticRelationAreaViewPartId"; //$NON-NLS-1$

	TableViewer headPositionTableViewer;
	TableViewer modifierPositionTableViewer;
	
	Text headProductionText;
	Text modifierProductionText;
	
	TableViewerColumn termHeadColumn;
	TableViewerColumn termModifierColumn;

	Composite generalComposite;
	
	private TermSorter headSorter;
	private TermSorter modifierSorter;
		
	public SyntacticRelationAreaViewPart() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		
		generalComposite = new Composite(parent, SWT.NONE);

		GridLayout generalLayout = new GridLayout();
		generalLayout.numColumns = 5;
		generalComposite.setLayout(generalLayout);

		GridData myGridData = new GridData(GridData.FILL, GridData.CENTER, false, false);
		Label headProductionLabel = new Label(generalComposite, SWT.NONE); 
		headProductionLabel.setText(Messages.getString("SyntacticRelationAreaViewPart.1")); //$NON-NLS-1$
		headProductionLabel.setLayoutData(myGridData);
		
		myGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		headProductionText = new Text(generalComposite, SWT.BORDER);
		headProductionText.setLayoutData(myGridData);
		
		new Label(generalComposite, SWT.NONE);
		
		myGridData = new GridData(GridData.FILL, GridData.CENTER, false, false);
		Label modifierProductionLabel = new Label(generalComposite, SWT.NONE);
		modifierProductionLabel.setText(Messages.getString("SyntacticRelationAreaViewPart.2")); //$NON-NLS-1$
		modifierProductionLabel.setLayoutData(myGridData);
		
		myGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		modifierProductionText = new Text(generalComposite, SWT.BORDER);
		modifierProductionText.setLayoutData(myGridData);
		
		myGridData = new GridData(GridData.FILL, GridData.CENTER, false, false);
		Label headTableViewerTitleLabel = new Label(generalComposite, SWT.NONE);
		headTableViewerTitleLabel.setText(Messages.getString("SyntacticRelationAreaViewPart.3")); //$NON-NLS-1$
		headTableViewerTitleLabel.setLayoutData(myGridData);

		new Label(generalComposite, SWT.NONE);
		new Label(generalComposite, SWT.NONE);
		new Label(generalComposite, SWT.NONE);
		new Label(generalComposite, SWT.NONE);
		
		
		myGridData = new GridData(GridData.FILL_BOTH);
		myGridData.horizontalSpan = 2;
		headPositionTableViewer = new TableViewer(generalComposite, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		headPositionTableViewer.getTable().setLinesVisible(true);
		headPositionTableViewer.getTable().setHeaderVisible(true);
		headPositionTableViewer.getTable().setLayoutData(myGridData);
		headPositionTableViewer.setContentProvider(new HeadProductionContentProvider());

		termHeadColumn = new TableViewerColumn(headPositionTableViewer, SWT.NONE, 0);
		termHeadColumn.getColumn().setText(Messages.getString("SyntacticRelationAreaViewPart.4")); //$NON-NLS-1$
		termHeadColumn.getColumn().setWidth(200);
		termHeadColumn.getColumn().setResizable(false);
		termHeadColumn.setLabelProvider(new TermLabelProvider());
		
		termHeadColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setHeadSorter();

			}
		});
		
		headPositionTableViewer.getTable().setSortColumn(termHeadColumn.getColumn());
		headPositionTableViewer.getTable().setSortDirection(SWT.UP);

		new Label(generalComposite, SWT.NONE);

		myGridData = new GridData(GridData.FILL_BOTH);
		myGridData.horizontalSpan = 2;
		modifierPositionTableViewer = new TableViewer(generalComposite, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
			
		modifierPositionTableViewer.getTable().setLinesVisible(true);
		modifierPositionTableViewer.getTable().setHeaderVisible(true);
		modifierPositionTableViewer.getTable().setLayoutData(myGridData);
		modifierPositionTableViewer.setContentProvider(new ModifierProductionContentProvider());
		

		termModifierColumn = new TableViewerColumn(modifierPositionTableViewer, SWT.NONE, 0);
		termModifierColumn.getColumn().setText(Messages.getString("SyntacticRelationAreaViewPart.5")); //$NON-NLS-1$
		termModifierColumn.getColumn().setWidth(200);
		termModifierColumn.getColumn().setResizable(false);
		termModifierColumn.setLabelProvider(new TermLabelProvider());

		termModifierColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setModifierSorter();
				
			}
		});
		
		modifierPositionTableViewer.getTable().setSortColumn(termModifierColumn.getColumn());
		modifierPositionTableViewer.getTable().setSortDirection(SWT.UP);

		generalComposite.addControlListener(new ControlAdapter() {
		    public void controlResized(ControlEvent e) {
		      Rectangle area = generalComposite.getClientArea();
		      Point preferredSize = headPositionTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		      int width = area.width - 2 * headPositionTableViewer.getTable().getBorderWidth();
		      
		      if (preferredSize.y > area.height + headPositionTableViewer.getTable().getHeaderHeight()) {
		        Point vBarSize = headPositionTableViewer.getTable().getVerticalBar().getSize();
		        width -= vBarSize.x;
		      }
		      
		      Point oldSize = headPositionTableViewer.getTable().getSize();
		      width /= 2;
		      
		      if ((oldSize.x) > area.width) {
		    	  termHeadColumn.getColumn().setWidth(width - 10);
		    	  termModifierColumn.getColumn().setWidth(width - 10);
		    	  headPositionTableViewer.getTable().setSize(width, area.height);
		    	  modifierPositionTableViewer.getTable().setSize(width, area.height);
		      } else {
		    	  headPositionTableViewer.getTable().setSize(width, area.height);
		    	  modifierPositionTableViewer.getTable().setSize(width, area.height);
		    	  termHeadColumn.getColumn().setWidth(width - 10);
		    	  termModifierColumn.getColumn().setWidth(width - 10);
		      }
		    }
		  });
		
		modifierPositionTableViewer.getTable().addListener(SWT.MouseDoubleClick, 
				new SyntacticalRelationTableViewerListener(modifierPositionTableViewer));

		headPositionTableViewer.getTable().addListener(SWT.MouseDoubleClick, 
				new SyntacticalRelationTableViewerListener(headPositionTableViewer));
		
		updateInformation();
	}
	
	private void updateInformation(){
		updateModifiers();
		updateHeads();
	}
	
	public void setHeadSorter(){
		
		headSorter = new TermSorter(TermSorter.HEAD_SORTER);
		headPositionTableViewer.setSorter(headSorter);
		headPositionTableViewer.getTable().setSortColumn(termHeadColumn.getColumn());
		headPositionTableViewer.getTable().setSortDirection(headSorter.getDirection());

	}
	
	public void setModifierSorter(){
		
		modifierSorter = new TermSorter(TermSorter.MODIFIER_SORTER);
		modifierPositionTableViewer.setSorter(modifierSorter);
		modifierPositionTableViewer.getTable().setSortColumn(termModifierColumn.getColumn());
		modifierPositionTableViewer.getTable().setSortDirection(modifierSorter.getDirection());

	}

	public void updateModifiers(){
		ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
		
		if (currentTerm != null) {
			
			//ES: to be done. All terms are given for data test purposes
			
			List<ITerm> modifiers = new ArrayList<ITerm>();

			modifiers = DatabaseAdapter.getTerms();
			
			modifierPositionTableViewer.setInput(modifiers);
			
			modifierProductionText.setText(modifiers.size() + ""); //$NON-NLS-1$
		}

	}

	public void updateHeads(){
		ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
		
		if (currentTerm != null) {
			
			//ES: to be done. All terms are given for data test purposes
			
			List<ITerm> heads = new ArrayList<ITerm>();

			heads = DatabaseAdapter.getTerms();
			
			headPositionTableViewer.setInput(heads);
			
			headProductionText.setText(heads.size() + ""); //$NON-NLS-1$
		}

	}

	@Override
	public void setFocus() {

	}
	
	
	class SyntacticalRelationTableViewerListener implements Listener {

		private TableViewer theViewer;
		
		public SyntacticalRelationTableViewerListener(TableViewer aViewer){
			super();
			this.theViewer = aViewer;
		}
		
		public void handleEvent(Event event) {
			
			Point pt = new Point(event.x, event.y);
			TableItem item = theViewer.getTable().getItem(pt);
			
			if (item == null)
				return;
			
			ITerm selectedTerm = (ITerm) (item.getData());
			
			ControlerFactoryImpl.getTerminologyControler().setCurrentTerm(selectedTerm);
			
			
		}	
		
	}

	
	class ProductionContentProvider implements IStructuredContentProvider {
		private List<ITerm> productions;

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((List<ITerm>) inputElement).toArray();
		}

		public void dispose() {

		}

		@SuppressWarnings("unchecked")
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			this.productions = (List<ITerm>) newInput;
		}
		public List<ITerm> getProductions() {
			return productions;
		}

	}

	class HeadProductionContentProvider extends ProductionContentProvider {
	}
	
	class ModifierProductionContentProvider extends ProductionContentProvider {
	}
	
	
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		
		GridLayout tmp = new GridLayout();
		tmp.numColumns = 1;
	    shell.setLayout(tmp);
	    shell.setSize(300, 300);
	    

		shell.open();
		while(!shell.isDisposed()){
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}

	}
}
