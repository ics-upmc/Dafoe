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

package org.dafoe.terminoontologiclevel.ui.views;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.core.terminological.model.ISentence;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class SentenceWidget extends Composite {

//	private static String SENTENCE_COL_NAME = Messages
//			.getString("SentenceWidget.0"); //$NON-NLS-1$

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	private String deletionEventType;

	private Composite mainComposite;

	private TableViewer sentencesTableViewer;

	private TableViewerColumn sentencesColumn;

//	private List<ISentence> sentences;

//	private ITerminoConcept currentTC;

	private Action removeSentenceAction;

//	private Shell shell;

	public SentenceWidget(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new GridLayout());

		IThemeManager themeManager = PlatformUI.getWorkbench()
				.getThemeManager();

		final Color CORPUS_LEVEL_COLOR = themeManager
				.getTheme("org.dafoe.corpuslevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setBackground(CORPUS_LEVEL_COLOR);

		GridData gd;

		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		this.setLayoutData(gd);

		createContent();
	}

	//	

	private void createContent() {

		GridData gd;

		mainComposite = new Composite(this, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		mainComposite.setLayoutData(gd);
		mainComposite.setLayout(new GridLayout(1, false));

		sentencesTableViewer = new TableViewer(mainComposite, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		sentencesTableViewer.getTable().setLayoutData(gridData);
		sentencesTableViewer.getTable().setLinesVisible(true);
		sentencesTableViewer.getTable().setHeaderVisible(true);

		sentencesColumn = new TableViewerColumn(sentencesTableViewer, SWT.NONE,
				0);
		sentencesColumn.getColumn().setText(
				Messages.getString("TerminologicalViewPart.16")); //$NON-NLS-1$
		sentencesColumn.getColumn().setResizable(true);
		sentencesColumn.getColumn().setWidth(300);

		mainComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = mainComposite.getClientArea();
				Point preferredSize = sentencesTableViewer.getTable()
						.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2
						* sentencesTableViewer.getTable().getBorderWidth();
				if (preferredSize.y > area.height
						+ sentencesTableViewer.getTable().getHeaderHeight()) {

					Point vBarSize = sentencesTableViewer.getTable()
							.getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = sentencesTableViewer.getTable().getSize();

				if (oldSize.x > area.width) {

					sentencesColumn.getColumn().setWidth(width);
					sentencesTableViewer.getTable().setSize(area.width,
							area.height);
				} else {
					sentencesTableViewer.getTable().setSize(area.width,
							area.height);
					sentencesColumn.getColumn().setWidth(width);
				}
			}
		});

		sentencesTableViewer.setContentProvider(new SentenceContentProvider());
		sentencesTableViewer.setLabelProvider(new SentenceTableLabelProvider());
		sentencesTableViewer.setInput(new ArrayList<ISentence>());

		// sentencesColumn.getColumn().pack();

		makeActions();

		hookPopupMenu();

	}

	//

	public void setDeletionEventType(String event) {
		this.deletionEventType = event;
	}

	//

	public String getDeletionEventType() {
		return this.deletionEventType;
	}

	//

	public void setInput(List<ISentence> sentences) {
		this.sentencesTableViewer.setInput(sentences);
	}

	//

	public List<ISentence> getSelection() {
		List<ISentence> res = new ArrayList<ISentence>();

		if (sentencesTableViewer.getTable().getSelection().length != 0) {
			res
					.add((ISentence) (sentencesTableViewer.getTable()
							.getSelection()[0].getData()));
		}
		;

		return res;
	}

	//

	public void deselect() {
		sentencesTableViewer.getTable().deselectAll();
	}

	//

	public void refresh() {
		// sentencesTableViewer.setInput(???);
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

	class SentenceContentProvider implements IStructuredContentProvider {

		@SuppressWarnings("unchecked")
		@Override
		public Object[] getElements(Object inputElement) {
			return ((List<ISentence>) inputElement).toArray();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	//

	class SentenceTableLabelProvider extends BaseLabelProvider implements
			ILabelProvider {

		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			if (element instanceof ISentence) {
				return ((ISentence) element).getContent();
			}

			return null;
		}

	}

	//

	private void makeActions() {
		ImageDescriptor imgDesc;

		// remove a relation type => check if it is used in a terminological
		// relation. If not, delete.

		removeSentenceAction = new Action() {

			public void run() {

				propertyChangeSupport.firePropertyChange(deletionEventType,
						true, false);

			}
		};

		removeSentenceAction.setText(Messages.getString("SentenceWidget.1")); //$NON-NLS-1$
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(
						org.dafoe.terminoontologiclevel.ui.Activator.REMOVE_IMG_ID);
		removeSentenceAction.setImageDescriptor(imgDesc);
		removeSentenceAction.setEnabled(true);

	}

	//

	private void hookPopupMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(sentencesTableViewer.getTable());
		sentencesTableViewer.getControl().setMenu(menu);

		menuMgr.add(this.removeSentenceAction);
	}

}
