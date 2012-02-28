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

import java.util.List;
import java.util.Set;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class RTCRelationsWidget extends Composite {

	private TableViewer existingRTCViewer;
	private TableViewerColumn arg1Column;
	private TableViewerColumn arg2Column;
	private TableViewerColumn typeColumn;
	
	private Composite existingRTCsComposite;
	
	private List<BinaryTCRelation> tcRelations;
	private BinaryTCRelation currentRelation;
	
	private Color bgColor, bgLinkedRTCColor, fgLinkedRTCColor;
	
	private String eventType;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	
	public RTCRelationsWidget(Composite parent, int style) {
		
		super(parent, style);

		this.setLayout(new GridLayout());

		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		bgColor = themeManager.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		bgLinkedRTCColor = this.getShell().getDisplay().getSystemColor(SWT.COLOR_YELLOW);
		
		fgLinkedRTCColor = this.getShell().getDisplay().getSystemColor(SWT.COLOR_BLACK);
		
		this.setBackground(bgColor);

		createContent();
	}
	
	private void createContent(){
		
		GridData gd;

		existingRTCsComposite = new Composite(this, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		existingRTCsComposite.setLayoutData(gd);
		existingRTCsComposite.setLayout(new GridLayout(1, false));
		
		FontRegistry fontRegistry = new FontRegistry(this.getDisplay());
		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		Label label = new Label(existingRTCsComposite, SWT.NONE);
		label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
		label.setText(Messages.getString("RTCRelationsWidget.3")); //$NON-NLS-1$

		Label titleBarSeparator = new Label(existingRTCsComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		//existingRTCViewer = new TableViewer(existingRTCsComposite, SWT.FULL_SELECTION);
		existingRTCViewer = new TableViewer(existingRTCsComposite, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		existingRTCViewer.getTable().setHeaderVisible(true);
		existingRTCViewer.getTable().setLinesVisible(true);
/*		gd.heightHint = 300;
*/		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		existingRTCViewer.getTable().setLayoutData(gd);
		
		existingRTCViewer.setContentProvider(new RTCContentProvider());

		arg1Column = new TableViewerColumn(existingRTCViewer, SWT.NONE);
		arg1Column.getColumn().setText(Messages.getString("RTCRelationsWidget.4")); //$NON-NLS-1$
		arg1Column.setLabelProvider(new RTCColumnLabelProvider(0));
		
		typeColumn = new TableViewerColumn(existingRTCViewer, SWT.NONE);
		typeColumn.getColumn().setText(Messages.getString("RTCRelationsWidget.5")); //$NON-NLS-1$
		typeColumn.setLabelProvider(new RTCColumnLabelProvider(1));

		arg2Column = new TableViewerColumn(existingRTCViewer, SWT.NONE);
		arg2Column.getColumn().setText(Messages.getString("RTCRelationsWidget.6")); //$NON-NLS-1$
		arg2Column.setLabelProvider(new RTCColumnLabelProvider(2));
		

		existingRTCsComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = existingRTCsComposite.getClientArea();
				Point preferredSize = existingRTCViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * existingRTCViewer.getTable().getBorderWidth();

				if (preferredSize.y > area.height + existingRTCViewer.getTable().getHeaderHeight()) {
					Point vBarSize = existingRTCViewer.getTable().getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = existingRTCViewer.getTable().getSize();
				if (oldSize.x > area.width) {
					arg1Column.getColumn().setWidth(width * 33 / 100);
					typeColumn.getColumn().setWidth(width * 33 / 100);
					//arg2Column.getColumn().setWidth(width * 33 / 100);
					arg2Column.getColumn().setWidth(width - arg1Column.getColumn().getWidth() - 
							typeColumn.getColumn().getWidth());
					existingRTCViewer.getTable().setSize(area.width, area.height);
				} else {
					existingRTCViewer.getTable().setSize(area.width, area.height);
					arg1Column.getColumn().setWidth(width * 33 / 100);
					typeColumn.getColumn().setWidth(width * 33 / 100);
					//arg2Column.getColumn().setWidth(width * 33 / 100);
					arg2Column.getColumn().setWidth(width - arg1Column.getColumn().getWidth() - 
							typeColumn.getColumn().getWidth());

				}
			}
		});

		
		existingRTCViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				TableItem[] sel = existingRTCViewer.getTable().getSelection();
				BinaryTCRelation tc;
				
				if(sel.length != 0) {
					
					tc = (BinaryTCRelation)(sel[0].getData());
					BinaryTCRelation oldTCR = currentRelation;
					currentRelation = tc;
					if (oldTCR == tc) {
						oldTCR = null;
					}
					
					if (currentRelation != null)
						System.out.println(Messages.getString("RTCRelationsWidget.7") + currentRelation.getTc1().getLabel() + " - " + currentRelation.getType().getName() + " - " + currentRelation.getTc2().getLabel()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					
					propertyChangeSupport.firePropertyChange(eventType, oldTCR, currentRelation);

				}
				
			}
		});

		updateInformation();
	}

	//
	
	public void updateInformation(){
		tcRelations = DatabaseAdapter.getTerminoConceptualRelations();
		System.out.println("#RTC = " + tcRelations.size()); //$NON-NLS-1$
		existingRTCViewer.setInput(tcRelations);
	}

	//
	
	public void setEventType(String event){
		this.eventType = event;
	}
	
	//
	
	public String getEventType(){
		return this.eventType;
	}
	
	//
	
	public List<BinaryTCRelation> getRTCs(){
		return this.tcRelations;
	}
	

	//
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	//
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}

	//
	
	public void setSelection(BinaryTCRelation rtc){
		existingRTCViewer.setSelection(new StructuredSelection(rtc));
		currentRelation = rtc;
	}
	
	//
	
	public void deselect(){
		existingRTCViewer.getTable().deselectAll();	
		currentRelation = null;
	}
	
	//
	
	public BinaryTCRelation getSelection(){
		return currentRelation;
	}

	//
	
	class RTCContentProvider implements IStructuredContentProvider{

		public Object[] getElements(Object inputElement) {
			return ((List<ITerminoConceptRelation>) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}
	
	//
	
	public void refresh(BinaryTCRelation tcr){
		this.existingRTCViewer.refresh(tcr);
	}
	
	//
	
	public void refresh(){
		
		if(currentRelation != null) {
			this.existingRTCViewer.refresh(currentRelation);
		}
	}
	
	//
	
	class RTCColumnLabelProvider extends ColumnLabelProvider {

		int col;
		
		public RTCColumnLabelProvider(int aCol) {
			super();
			this.col = aCol;
		}

		/**
		 * 
		 */
		public String getText(Object element) {
			BinaryTCRelation rtc = (BinaryTCRelation) element;
			String result = ""; //$NON-NLS-1$
			
			if (col == 0) {
				
				result = rtc.getTc1().getLabel();
				
			} else if (col == 1) {
				
				result = rtc.getType().getName();
			
				
			} else if (col == 2) {
				
				result = rtc.getTc2().getLabel();
				
			}

			return result;

		}

		/**
		 * 
		 */
		public Color getBackground(Object element) {
			ITerminoConceptRelation rtc = ((BinaryTCRelation) element).getOrigin();
			ITermRelation rt = ControlerFactoryImpl.getTerminologyControler().getTermRelation();
			
			Set<ITerminoConceptRelation> tcrRefs = rt.getMappedTerminoConceptRelations();
			
			if (tcrRefs != null) {
			
				if(tcrRefs.contains(rtc)){

					return bgLinkedRTCColor;

				}
			}
			
			return null;
		}

		/**
		 * 
		 */
		public Color getForeground(Object element) {
			ITerminoConceptRelation rtc = ((BinaryTCRelation) element).getOrigin();
			ITermRelation rt = ControlerFactoryImpl.getTerminologyControler().getTermRelation();
			
			Set<ITerminoConceptRelation> tcrRefs = rt.getMappedTerminoConceptRelations();
			
			if (tcrRefs != null){
			
				if(tcrRefs.contains(rtc)){

					return fgLinkedRTCColor;

				}
			}
			
			return null;
		}
		
		/**
		 * 
		 */
		public int getToolTipDisplayDelayTime(Object object) {
			return 500;
		}

		/**
		 * 
		 */
		public Color getToolTipBackgroundColor(Object object) {
			//return Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
			return null;
		}

		/**
		 * 
		 */
		public Color getToolTipForegroundColor(Object object) {
			//return Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
			return null;
		}

		/**
		 * 
		 */
		public String getToolTipText(Object object) {
			//ITerm term = (ITerm) object;
			return "test du tooltip"; //$NON-NLS-1$
		}

	}
	
}
