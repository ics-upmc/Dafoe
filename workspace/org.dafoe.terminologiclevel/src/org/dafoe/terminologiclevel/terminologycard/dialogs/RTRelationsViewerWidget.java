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

import java.util.List;

import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class RTRelationsViewerWidget extends Composite {

	private TableViewer rtsViewer;
	private TableViewerColumn arg1Column;
	private TableViewerColumn arg2Column;
	private TableViewerColumn typeColumn;
			
	private Color bgColor;
	
	public RTRelationsViewerWidget(Composite parent, int style) {
		
		super(parent, style);

		this.setLayout(new GridLayout());

		this.setBackground(bgColor);

		createContent();
	}
	
	private void createContent(){
		
		GridData gd;

//		rtsComposite = new Composite(parent, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		this.setLayoutData(gd);
		this.setLayout(new GridLayout(1, false));
		

		rtsViewer = new TableViewer(this, SWT.BORDER | SWT.V_SCROLL);
		rtsViewer.getTable().setHeaderVisible(true);
		rtsViewer.getTable().setLinesVisible(true);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		rtsViewer.getTable().setLayoutData(gd);
		
		rtsViewer.setContentProvider(new RTContentProvider());

		arg1Column = new TableViewerColumn(rtsViewer, SWT.NONE);
		arg1Column.getColumn().setText(Messages.getString("RTRelationsViewerWidget.0")); //$NON-NLS-1$
		arg1Column.setLabelProvider(new RTColumnLabelProvider(0));
		
		typeColumn = new TableViewerColumn(rtsViewer, SWT.NONE);
		typeColumn.getColumn().setText(Messages.getString("RTRelationsViewerWidget.1")); //$NON-NLS-1$
		typeColumn.setLabelProvider(new RTColumnLabelProvider(1));

		arg2Column = new TableViewerColumn(rtsViewer, SWT.NONE);
		arg2Column.getColumn().setText(Messages.getString("RTRelationsViewerWidget.2")); //$NON-NLS-1$
		arg2Column.setLabelProvider(new RTColumnLabelProvider(2));
		

		this.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = RTRelationsViewerWidget.this.getClientArea();
				Point preferredSize = rtsViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * rtsViewer.getTable().getBorderWidth();

				if (preferredSize.y > area.height + rtsViewer.getTable().getHeaderHeight()) {
					Point vBarSize = rtsViewer.getTable().getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = rtsViewer.getTable().getSize();
				if (oldSize.x > area.width) {
					arg1Column.getColumn().setWidth(width * 33 / 100);
					typeColumn.getColumn().setWidth(width * 33 / 100);
					arg2Column.getColumn().setWidth(width - arg1Column.getColumn().getWidth() - 
							typeColumn.getColumn().getWidth());
					rtsViewer.getTable().setSize(area.width, area.height);
				} else {
					rtsViewer.getTable().setSize(area.width, area.height);
					arg1Column.getColumn().setWidth(width * 33 / 100);
					typeColumn.getColumn().setWidth(width * 33 / 100);
					arg2Column.getColumn().setWidth(width - arg1Column.getColumn().getWidth() - 
							typeColumn.getColumn().getWidth());

				}
			}
		});

	}

	//

	public void setContent(List<ITermRelation> trs){
		rtsViewer.setInput(trs);
	}
	
	//
	class RTContentProvider implements IStructuredContentProvider{

		public Object[] getElements(Object inputElement) {
			return ((List<ITermRelation>) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}
		
	//
	
	class RTColumnLabelProvider extends ColumnLabelProvider {

		int col;
		
		public RTColumnLabelProvider(int aCol) {
			super();
			this.col = aCol;
		}

		/**
		 * 
		 */
		public String getText(Object element) {
			ITermRelation rt = (ITermRelation) element;
			String result = ""; //$NON-NLS-1$
			
			if (col == 0) {
				
				result = rt.getTerm1().getLabel();
				
			} else if (col == 1) {
				
				result = rt.getTypeRel().getLabel();
			
				
			} else if (col == 2) {
				
				result = rt.getTerm2().getLabel();
				
			}

			return result;

		}
	}	
}
