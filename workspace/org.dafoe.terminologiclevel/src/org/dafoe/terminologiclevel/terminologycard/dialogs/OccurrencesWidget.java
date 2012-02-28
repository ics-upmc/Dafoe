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

import org.dafoe.framework.core.terminological.model.ISentence;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class OccurrencesWidget extends Composite {

	private TableViewer occurrencesViewer;
	private TableViewerColumn sentenceColumn;
	
	private List<ISentence> sentences;
	public OccurrencesWidget(Composite parent, int style) {
		
		super(parent, style);

		this.setLayout(new GridLayout());

		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		final Color CORPUS_LEVEL_COLOR = themeManager.getTheme("org.dafoe.corpuslevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setBackground(CORPUS_LEVEL_COLOR);

		createContent();
	
	}
	
	private void createContent(){
		
		GridData gd;

		Group occurencesComposite = new Group(this, SWT.NONE);
		occurencesComposite.setText(Messages.getString("OccurrencesWidget.0")); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		occurencesComposite.setLayoutData(gd);
		occurencesComposite.setLayout(new GridLayout(1, false));
		
		occurrencesViewer = new TableViewer(occurencesComposite, SWT.FULL_SELECTION);
		occurrencesViewer.getTable().setLinesVisible(true);
		occurrencesViewer.getTable().setHeaderVisible(true);
		gd.heightHint = 300;
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		occurrencesViewer.getTable().setLayoutData(gd);
		
		sentenceColumn = new TableViewerColumn(occurrencesViewer, SWT.NONE);
		sentenceColumn.getColumn().setWidth(150);
		sentenceColumn.getColumn().setText(Messages.getString("OccurrencesWidget.1")); //$NON-NLS-1$
		sentenceColumn.setLabelProvider(new SentencesLabelProvider());
			
		this.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = OccurrencesWidget.this.getClientArea();
				Point preferredSize = occurrencesViewer.getTable().computeSize(
						SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2
						* occurrencesViewer.getTable().getBorderWidth();

				if (preferredSize.y > area.height
						+ occurrencesViewer.getTable().getHeaderHeight()) {
					Point vBarSize = occurrencesViewer.getTable().getVerticalBar()
							.getSize();
					width -= vBarSize.x;
				}
				Point oldSize = occurrencesViewer.getTable().getSize();
				if (oldSize.x > area.width) {
					sentenceColumn.getColumn().setWidth(width -10);
					occurrencesViewer.getTable().setSize(area.width, area.height);
				} else {
					occurrencesViewer.getTable().setSize(area.width, area.height);
					sentenceColumn.getColumn().setWidth(width -10);
				}
			}
		});

		occurrencesViewer.setContentProvider(new SentencesContentProvider());
		
		updateInformation();
	}

	//
	
	public void updateInformation(){
		sentences = null;
		occurrencesViewer.setInput(sentences);
	}
	
	//
	
	class SentencesContentProvider implements IStructuredContentProvider{

		public Object[] getElements(Object inputElement) {
			return ((List<ISentence>) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}
	
	class SentencesLabelProvider extends ColumnLabelProvider {

		public SentencesLabelProvider() {
			super();
		}

		/**
		 * 
		 */
		public String getText(Object element) {

			return ((ISentence)element).getContent();

		}

		/**
		 * 
		 */
		public Color getBackground(Object element) {
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
			return ((ISentence)object).getContent();
		}

	}
	
}
