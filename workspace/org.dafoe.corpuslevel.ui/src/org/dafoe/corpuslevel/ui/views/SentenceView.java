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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.model.IControlerTerminology;
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.corpuslevel.common.DatabaseAdapter;
import org.dafoe.corpuslevel.ui.Activator;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

/**
 * <p>
 * @param <ITermOccurences>
 */

public class SentenceView<ITermOccurences> extends ViewPart {

	private TableViewer phrases_viewer;

	protected java.util.Hashtable<ISentence,List<ITermOccurrence>> occurences = new java.util.Hashtable<ISentence,List<ITermOccurrence>>();

	private IControlerTerminology terminoControler=ControlerFactoryImpl.getTerminologyControler();

	private CurrentTermPropertyChangeListener currentTermPropertyChangeListener;
	private CurrentDocumentPropertyChangeListener currentDocumentPropertyChangeListener;

	private Action actionAllCorpus;
	private Action actionAligner;
	private Action actionSequence;

	//

	public TableViewer GetViewer() {
		return phrases_viewer;
	}

	//

	public List<ITermOccurrence> GetListOccurence(ISentence sentence) {
		return occurences.get(sentence);
	}


	/**
	 * The constructor.
	 */
	public SentenceView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */

	public void createPartControl(Composite parent) {

		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		comp.setLayout(layout);

		Composite lecorps = comp;

		Composite compright= new Composite(lecorps,SWT.NONE);
		GridData GD = new GridData(GridData.FILL_BOTH);
		GD.horizontalSpan=6;

		compright.setLayoutData(GD);

		GridLayout GLRig= new GridLayout(1,true);
		compright.setLayout(GLRig);

		CreatePhraseViewer(compright);
		GD = new GridData(GridData.FILL_BOTH);

		phrases_viewer.getTable().setLayoutData(GD);

		makeActions();

		contributeToActionBars();

		currentTermPropertyChangeListener = new CurrentTermPropertyChangeListener();

		ControlerFactoryImpl.getTerminologyControler().addPropertyChangeListener(ControlerFactoryImpl.currentTermEvent, currentTermPropertyChangeListener);

		currentDocumentPropertyChangeListener =  new CurrentDocumentPropertyChangeListener();
		ControlerFactoryImpl.getTerminologyControler().addPropertyChangeListener(ControlerFactoryImpl.currentDocumentEvent, currentDocumentPropertyChangeListener);


		RefreshAll();
	}

	//

	class CurrentTermPropertyChangeListener implements PropertyChangeListener {

		@Override
		public void propertyChange(java.beans.PropertyChangeEvent arg0) {

			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
			
			if (currentTerm != null) {
				org.dafoe.corpuslevel.common.DatabaseAdapter.alignSentences(currentTerm);
				List<ISentence> list= org.dafoe.corpuslevel.common.DatabaseAdapter.getSentences();
				//List<ISentence> list = org.dafoe.corpuslevel.common.DatabaseAdapter.alignSentences2(currentTerm);
				phrases_viewer.setInput(list);
				for (int i=0;i<list.size();i++) {
					occurences.put(list.get(i), DatabaseAdapter.getOccurrences(currentTerm, list.get(i)));
				}
			}
		}
	}

	//

	class CurrentDocumentPropertyChangeListener implements PropertyChangeListener {

		@Override
		public void propertyChange(java.beans.PropertyChangeEvent arg0) {
			IDocument doc = ControlerFactoryImpl.getTerminologyControler().getCurrentDocument();

			if (doc != null) {

				occurences.clear();

				List<ISentence> list= new ArrayList<ISentence>();
				Set<ISentence> set = new HashSet<ISentence>();

				try {

					set = doc.getSentences();
					list= UtilTools.setToList(set);

				} catch (Exception e) {
					e.printStackTrace();
				}

				phrases_viewer.setInput(list);

				ITerm curterm = terminoControler.getCurrentTerm();

				if (curterm != null) {

					for (int i = 0; i < list.size(); i++) {

						occurences.put(list.get(i), DatabaseAdapter.getOccurrences(curterm, list.get(i)));

					}
				}
			}
		}

	}

	//
	
	void RefreshAll() {
		phrases_viewer.refresh();
	}

	//
	
	void CreatePhraseViewer(Composite parent) {

		phrases_viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		phrases_viewer.setContentProvider(new PhraseContentProvider());
		
		TableViewerColumn columt = new TableViewerColumn(phrases_viewer, SWT.NONE);
		columt.getColumn().setWidth(100);
		columt.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof ISentence) {
					ISentence phrase = (ISentence) element;
					return Integer.toString(phrase.getId());
				} else {
					return "";
				}
			}
			
		});
/*
		TableColumn column = new TableColumn(phrases_viewer.getTable(), SWT.NONE);
		int valeur = phrases_viewer.getTable().getClientArea().width;
		column.setWidth(75);
		column.setText(Messages.getString("SentenceView.0")); //$NON-NLS-1$
		column.setMoveable(true);
		column.setResizable(true);

		column = new TableColumn(phrases_viewer.getTable(), SWT.NONE);
		column.setWidth(150);
		column.setText(Messages.getString("SentenceView.1")); //$NON-NLS-1$
		column.setMoveable(true);
		column.setResizable(true);
*/
		TableViewerColumn columv = new TableViewerColumn(phrases_viewer,SWT.NONE);
		columv.getColumn().setWidth(200);
		columv.getColumn().setText(Messages.getString("SentenceView.2")); //$NON-NLS-1$
		columv.getColumn().setMoveable(true);
		columv.getColumn().setResizable(true);
		columv.setLabelProvider(new PhraseOccurenceLabelProvider(this,columv));

//		phrases_viewer.getTable().addControlListener(new ControlListener() {
//
//			@Override
//			public void controlMoved(ControlEvent e) {
//			}
//
//			@Override
//			public void controlResized(ControlEvent e) {
//				int valeur = phrases_viewer.getTable().getClientArea().width;
//			}
//		});
		
		org.dafoe.corpuslevel.common.DatabaseAdapter.loadSentences();
		List<ISentence> phrases = org.dafoe.corpuslevel.common.DatabaseAdapter.getSentences();
		phrases_viewer.setInput(phrases);
		phrases_viewer.getTable().setHeaderVisible(true);

	}

	//
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	//
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionAllCorpus);
		manager.add(actionAligner);
		manager.add(actionSequence);
	}

	//
	
	private void makeActions() {
		actionAllCorpus = new Action() {
			public void run() {

				org.dafoe.corpuslevel.common.DatabaseAdapter.loadSentences();
				List<ISentence> phrases= org.dafoe.corpuslevel.common.DatabaseAdapter.getSentences();
				phrases_viewer.setInput(phrases);

				ITerm curterm = terminoControler.getCurrentTerm();

				if (curterm!=null) {
					for (int i=0;i<phrases.size();i++) {


						occurences.put(phrases.get(i), DatabaseAdapter.getOccurrences(curterm, phrases.get(i)));
					}
				}

			}
		};
		actionAllCorpus.setText(Messages.getString("SentenceView.3")); //$NON-NLS-1$
		actionAllCorpus.setToolTipText(Messages.getString("SentenceView.4")); //$NON-NLS-1$
		actionAllCorpus.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.ALL_SENTENCES));

		actionAligner = new Action() {
			public void run() {
				ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
				if (currentTerm!=null) {
					org.dafoe.corpuslevel.common.DatabaseAdapter.alignSentences(currentTerm);
					List<ISentence> list= org.dafoe.corpuslevel.common.DatabaseAdapter.getSentences();
					phrases_viewer.setInput(list);
					for (int i=0;i<list.size();i++) {
						occurences.put(list.get(i), DatabaseAdapter.getOccurrences(currentTerm, list.get(i)));
					}
				}
			}
		};
		actionAligner.setText(Messages.getString("SentenceView.5")); //$NON-NLS-1$
		actionAligner.setToolTipText(Messages.getString("SentenceView.6")); //$NON-NLS-1$
		actionAligner.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.ALIGN_SENTENCES));


		actionSequence = new Action() {
			public void run() {
				ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
				if (currentTerm!=null) {
					org.dafoe.corpuslevel.common.DatabaseAdapter.sequenceSentences(currentTerm);
					List<ISentence> list= org.dafoe.corpuslevel.common.DatabaseAdapter.getSentences();
					phrases_viewer.setInput(list);
					for (int i=0;i<list.size();i++) {
						occurences.put(list.get(i), DatabaseAdapter.getOccurrences(currentTerm, list.get(i)));
					}
				}
			}
		};

		actionSequence.setText(Messages.getString("SentenceView.7")); //$NON-NLS-1$
		actionSequence.setToolTipText(Messages.getString("SentenceView.8")); //$NON-NLS-1$
		actionSequence.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.SEQ_SENTENCES));


	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		phrases_viewer.getControl().setFocus();
	}

}
