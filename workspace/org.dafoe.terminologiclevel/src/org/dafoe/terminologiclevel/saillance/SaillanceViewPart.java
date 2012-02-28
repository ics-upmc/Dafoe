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

package org.dafoe.terminologiclevel.saillance;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;

/**
 */

public class SaillanceViewPart extends ViewPart {
	public static final String ID = "org.dafoe.terminologiclevel.saillance.SaillanceViewPartId"; //$NON-NLS-1$

	public static Shell shell;

	private TableViewer tableTermViewer;
	private int visibleRowsCount = 0;

	private Composite generalComposite;
	private Text termFilterText;
	private Text termNumberText;
	private Text percentValidatedText;
	private Combo statusFilterCombo;

	private Button antiDictionaryFilterButton;
	private TermSaillanceFilter termFilter;
	private StatusSaillanceFilter statusFilter;

	private AntiDictionaryTermFilter antiDictionaryFilter;
	private NamedEntitySaillanceFilter namedEntitySaillanceFilter;
	private IntersectionTermFilter intersectionFilter;
	private RegularExpressionSaillanceFilter regularExpressionFilter;

	private SaillanceViewPartNewVariantPropertyChangeListener newVariantPropertyChangeListener;
	private SaillanceViewPartNewStatusPropertyChangeListener newStatusPropertyChangeListener;
	private SaillanceViewPartUpdateTermPropertyChangeListener updateTermPropertyChangeListener;
	private SaillanceViewPartDetachVariantPropertyChangeListener detachVariantPropertyChangeListener;
	private SaillanceViewPartTermLabelModifiedPropertyChangeListener termLabelModifiedPropertyChangeListener;
	private SaillanceViewPartTermsDeletedPropertyChangeListener termsDeletedPropertyChangeListener;
	private SaillanceViewPartTermsImportedPropertyChangeListener termsImportedPropertyChangeListener;
	private SaillanceViewPartCorpusChangedPropertyChangeListener corpusChangedPropertyChangeListener;
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

	/*
	 * Some getters
	 */

	public TableViewer getTableTermViewer() {
		return this.tableTermViewer;
	}

	public Text getTermNumberText() {
		return this.termNumberText;
	}

	public StatusSaillanceFilter getStatusFilter() {
		return this.statusFilter;
	}

	public NamedEntitySaillanceFilter getNamedEntitySaillanceFilter() {
		return this.namedEntitySaillanceFilter;
	}

	public Combo getFilterStatusCombo() {
		return this.statusFilterCombo;
	}

	public Button getAntiDictionaryFilterButton() {
		return this.antiDictionaryFilterButton;
	}

	public AntiDictionaryTermFilter getAntiDictionaryTermFilter() {
		return this.antiDictionaryFilter;
	}

	public IntersectionTermFilter getIntersectionTermFilter() {
		return this.intersectionFilter;
	}

	public RegularExpressionSaillanceFilter getRegularExpressionTermFilter() {
		return this.regularExpressionFilter;
	}

	/*
	 * Some setters
	 */

	public void setStatusFilter(StatusSaillanceFilter statusFilter) {
		this.statusFilter = statusFilter;
	}

	public void setAntiDictionaryTermFilter(
			AntiDictionaryTermFilter antiDictionaryFilter) {
		this.antiDictionaryFilter = antiDictionaryFilter;
	}

	public void setNamedEntitySaillanceFilter(
			NamedEntitySaillanceFilter namedEntitySaillanceFilter) {
		this.namedEntitySaillanceFilter = namedEntitySaillanceFilter;
	}

	public void setIntersectionTermFilter(
			IntersectionTermFilter intersectionFilter) {
		this.intersectionFilter = intersectionFilter;
	}

	public void getRegularExpressionTermFilter(
			RegularExpressionSaillanceFilter regularExpressionFilter) {
		this.regularExpressionFilter = regularExpressionFilter;
	}

	/*
	 * Some management methods
	 */

	public void createAntiDictionaryFilter(String path) {

		String buttonStatusImg = org.dafoe.terminologiclevel.Activator.ANTI_DICTIONARY_ENABLE_IMG_ID;

		ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault().getImageRegistry().getDescriptor(buttonStatusImg);

		// this.removeAntiDictionaryFilter();

		// this.removeIntersectionFilter();

		antiDictionaryFilter = new AntiDictionaryTermFilter(path);

		this.getAntiDictionaryFilterButton().setImage(imgDesc.createImage());

		this.setAntiDictionaryTermFilter(antiDictionaryFilter);

		this.getTableTermViewer().addFilter(antiDictionaryFilter);

	}

	public void removeAntiDictionaryFilter() {
		if (this.antiDictionaryFilter != null) {
			String buttonStatusImg = org.dafoe.terminologiclevel.Activator.ANTI_DICTIONARY_DISABLE_IMG_ID;

			ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
					.getDefault().getImageRegistry()
					.getDescriptor(buttonStatusImg);

			this.getAntiDictionaryFilterButton()
					.setImage(imgDesc.createImage());
			this.getAntiDictionaryFilterButton().setSelection(false);

			this.getTableTermViewer().removeFilter(
					this.getAntiDictionaryTermFilter());

		}
	}

	public void createIntersectionFilter(String path) {

		// this.removeAntiDictionaryFilter();

		// this.removeIntersectionFilter();

		intersectionFilter = new IntersectionTermFilter(path);

		this.setIntersectionTermFilter(intersectionFilter);

		this.getTableTermViewer().addFilter(intersectionFilter);

	}

	public void removeIntersectionFilter() {
		if (this.intersectionFilter != null) {
			this.getTableTermViewer().removeFilter(intersectionFilter);
		}
	}

	public void createRegularExpressionFilter(Pattern pattern,
			boolean wholeString) {

		regularExpressionFilter = new RegularExpressionSaillanceFilter(pattern,
				wholeString);

		this.getTableTermViewer().addFilter(regularExpressionFilter);

	}

	public void removeRegularExpressionFilter() {
		if (this.regularExpressionFilter != null) {
			this.getTableTermViewer().removeFilter(regularExpressionFilter);
		}
	}

	public void createNamedEntityFilter(boolean filterNamedEntity) {
		boolean currentNamedEntityFilter;

		if (namedEntitySaillanceFilter != null) {

			currentNamedEntityFilter = namedEntitySaillanceFilter
					.isNamedEntityFiltered();

			// only apply the filter if not already applied
			if (filterNamedEntity != currentNamedEntityFilter) {

				this.removeNamedEntityFilter();

				namedEntitySaillanceFilter = new NamedEntitySaillanceFilter(
						filterNamedEntity);

				this.setNamedEntitySaillanceFilter(namedEntitySaillanceFilter);

				this.getTableTermViewer().addFilter(namedEntitySaillanceFilter);
			}

		} else {

			namedEntitySaillanceFilter = new NamedEntitySaillanceFilter(
					filterNamedEntity);

			this.setNamedEntitySaillanceFilter(namedEntitySaillanceFilter);

			this.getTableTermViewer().addFilter(namedEntitySaillanceFilter);
		}

	}

	public void removeNamedEntityFilter() {
		if (this.namedEntitySaillanceFilter != null) {
			this.getTableTermViewer().removeFilter(namedEntitySaillanceFilter);
		}
	}

	public void removeAllfilters() {
		removeAntiDictionaryFilter();
		removeIntersectionFilter();
		removeNamedEntityFilter();
	}

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	class ViewContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {

			List<ITerm> starTerms = new ArrayList<ITerm>();

			Iterator<ITerm> it = DatabaseAdapter.getTerms().iterator();

			while (it.hasNext()) {
				ITerm term = (ITerm) (it.next());

				if (term.getStarTerm() == null) {
					starTerms.add(term);
				}
			}

			return starTerms.toArray();
		}

		public void dispose() {

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	/**
	 * The constructor.
	 */
	public SaillanceViewPart() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */

	public void createPartControl(Composite parent) {

		// log4j.info("BEGIN createPartControl(...)");

		shell = this.getSite().getShell();

		// General Grid Layout
		GridLayout generalGl = new GridLayout(1, false);
		generalComposite = new Composite(parent, SWT.NONE);
		generalComposite.setLayout(generalGl);
		generalComposite.setSize(new Point(800, 500));

		// Filters Composite
		Composite filtersComposite = new Composite(generalComposite, SWT.NONE);
		generalGl = new GridLayout(6, false);
		filtersComposite.setLayout(generalGl);

		// Filter Label
		Label filterLabel = new Label(filtersComposite, SWT.NONE);
		filterLabel.setText(Messages.getString("SaillanceViewPart.10")); //$NON-NLS-1$

		// the term filter
		GridLayout termFilterGl = new GridLayout(2, false);
		Composite termFilterComposite = new Composite(filtersComposite,
				SWT.NONE);
		termFilterComposite.setLayout(termFilterGl);
		Label termFilterLabel = new Label(termFilterComposite, SWT.NONE);
		termFilterLabel.setText(Messages.getString("SaillanceViewPart.11")); //$NON-NLS-1$
		termFilterText = new Text(termFilterComposite, SWT.BORDER);
		termFilterText.setText(Messages.getString("SaillanceViewPart.12")); //$NON-NLS-1$
		GridData myGd = new GridData(GridData.FILL_BOTH);
		termFilterText.setLayoutData(myGd);

		ModifyListener termFilterListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				tableTermViewer.removeFilter(termFilter);
				termFilter = new TermSaillanceFilter(termFilterText.getText());
				tableTermViewer.addFilter(termFilter);
			}
		};

		termFilterText.addModifyListener(termFilterListener);

		// the status filter

		GridLayout statusFilterGl = new GridLayout(2, false);
		Composite statusFilterComposite = new Composite(filtersComposite,
				SWT.NONE);
		statusFilterComposite.setLayout(statusFilterGl);
		Label statusFilterLabel = new Label(statusFilterComposite, SWT.NONE);
		statusFilterLabel.setText(Messages.getString("SaillanceViewPart.13")); //$NON-NLS-1$
		statusFilterCombo = new Combo(statusFilterComposite, SWT.NULL);
		String[] status = new String[] {
				Messages.getString("SaillanceViewPart.0"), //$NON-NLS-1$
				Messages.getString("SaillanceViewPart.16"), //$NON-NLS-1$ 
				Messages.getString("SaillanceViewPart.14"), //$NON-NLS-1$ 
				Messages.getString("SaillanceViewPart.15"), //$NON-NLS-1$ 
				Messages.getString("SaillanceViewPart.17"), //$NON-NLS-1$
				Messages.getString("SaillanceViewPart.19") }; //$NON-NLS-1$

		statusFilterCombo.setItems(status);
		statusFilterCombo.select(0);

		myGd = new GridData(GridData.FILL_BOTH);
		statusFilterCombo.setLayoutData(myGd);

		SelectionListener statusFilterListener = new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {

				tableTermViewer.removeFilter(statusFilter);
				System.out.println(statusFilterCombo.getSelectionIndex());
				// VT:
				// statusFilter = new
				// StatusSaillanceFilter(statusFilterCombo.getSelectionIndex() -
				// 1);
				TERMINO_OBJECT_STATE state = org.dafoe.framework.provider.hibernate.util.Util
						.getTerminoObjectStateFromDatabase(statusFilterCombo
								.getSelectionIndex() - 1);
				statusFilter = new StatusSaillanceFilter(state);

				tableTermViewer.addFilter(statusFilter);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

				tableTermViewer.removeFilter(statusFilter);
				statusFilter = new StatusSaillanceFilter(
						TERMINO_OBJECT_STATE.UNKNOWN);
				tableTermViewer.addFilter(statusFilter);

			}

		};

		statusFilterCombo.addSelectionListener(statusFilterListener);

		/*
		 * // the linguistic status filter
		 * 
		 * GridLayout linguisticStatusFilterGl = new GridLayout(2, false);
		 * Composite linguisticStatusFilterComposite = new
		 * Composite(filtersComposite, SWT.NONE);
		 * linguisticStatusFilterComposite.setLayout(linguisticStatusFilterGl);
		 * Label linguisticStatusFilterLabel = new
		 * Label(linguisticStatusFilterComposite, SWT.NONE);
		 * linguisticStatusFilterLabel
		 * .setText(Messages.getString(Messages.getString
		 * ("SaillanceViewPart.22"))); //$NON-NLS-1$ linguisticStatusFilterCombo
		 * = new Combo(linguisticStatusFilterComposite, SWT.NULL); String[]
		 * linguisticStatus = new String[] {
		 * Messages.getString(Messages.getString("SaillanceViewPart.23")),
		 * //$NON-NLS-1$
		 * Messages.getString(Messages.getString("SaillanceViewPart.25")),
		 * //$NON-NLS-1$
		 * Messages.getString(Messages.getString(Messages.getString
		 * ("SaillanceViewPart.30"))), //$NON-NLS-1$
		 * Messages.getString(Messages.getString("SaillanceViewPart.26")),
		 * //$NON-NLS-1$
		 * Messages.getString(Messages.getString("SaillanceViewPart.29"))};
		 * //$NON-NLS-1$
		 * 
		 * linguisticStatusFilterCombo.setItems(linguisticStatus);
		 * linguisticStatusFilterCombo.select(0);
		 * 
		 * myGd = new GridData(GridData.FILL_BOTH);
		 * linguisticStatusFilterCombo.setLayoutData(myGd);
		 * 
		 * SelectionListener linguisticStatusFilterListener = new
		 * SelectionListener() {
		 * 
		 * public void widgetSelected(SelectionEvent e) {
		 * 
		 * tableTermViewer.removeFilter(linguisticStatusFilter);
		 * System.out.println(linguisticStatusFilterCombo.getSelectionIndex());
		 * 
		 * LinguisticStatus state=
		 * org.dafoe.framework.provider.hibernate.util.Util
		 * .getLinguisticStatusFromDatabase
		 * (linguisticStatusFilterCombo.getSelectionIndex()-1);
		 * linguisticStatusFilter = new LinguisticStatusSaillanceFilter(state);
		 * 
		 * tableTermViewer.addFilter(linguisticStatusFilter); }
		 * 
		 * public void widgetDefaultSelected(SelectionEvent e) {
		 * 
		 * tableTermViewer.removeFilter(linguisticStatusFilter);
		 * linguisticStatusFilter = new
		 * LinguisticStatusSaillanceFilter(LinguisticStatus.UNKNOWN);
		 * tableTermViewer.addFilter(linguisticStatusFilter);
		 * 
		 * }
		 * 
		 * };
		 * 
		 * linguisticStatusFilterCombo.addSelectionListener(
		 * linguisticStatusFilterListener);
		 */

		// the anti-dictionary filter

		GridLayout antiDictionaryFilterGl = new GridLayout(2, false);
		Composite antiDictionaryFilterComposite = new Composite(
				filtersComposite, SWT.NONE);
		antiDictionaryFilterComposite.setLayout(antiDictionaryFilterGl);
		Label antiDictionaryFilterLabel = new Label(
				antiDictionaryFilterComposite, SWT.NONE);
		antiDictionaryFilterLabel.setText(Messages
				.getString("SaillanceViewPart.18")); //$NON-NLS-1$
		antiDictionaryFilterButton = new Button(antiDictionaryFilterComposite,
				SWT.TOGGLE);

		ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(
						org.dafoe.terminologiclevel.Activator.ANTI_DICTIONARY_DISABLE_IMG_ID);

		antiDictionaryFilterButton.setImage(imgDesc.createImage());

		SelectionAdapter antiDictionaryListener = new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {

				String buttonStatusImg;
				String path = null;

				if (antiDictionaryFilterButton.getSelection()) {

					path = selectXMLFile();

					if (path != null) {
						buttonStatusImg = org.dafoe.terminologiclevel.Activator.ANTI_DICTIONARY_ENABLE_IMG_ID;

						ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
								.getDefault().getImageRegistry()
								.getDescriptor(buttonStatusImg);

						if (imgDesc != null) {
							antiDictionaryFilterButton.setImage(imgDesc
									.createImage());
						}

						tableTermViewer.removeFilter(antiDictionaryFilter);
						antiDictionaryFilter = new AntiDictionaryTermFilter(
								path);
						tableTermViewer.addFilter(antiDictionaryFilter);

					} else {

						antiDictionaryFilterButton.setSelection(false);

					}

				} else {

					buttonStatusImg = org.dafoe.terminologiclevel.Activator.ANTI_DICTIONARY_DISABLE_IMG_ID;

					ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
							.getDefault().getImageRegistry()
							.getDescriptor(buttonStatusImg);
					antiDictionaryFilterButton.setImage(imgDesc.createImage());

					tableTermViewer.removeFilter(antiDictionaryFilter);

					antiDictionaryFilterButton.setSelection(false);

				}

			}
		};

		antiDictionaryFilterButton.addSelectionListener(antiDictionaryListener);

		myGd = new GridData(GridData.FILL_BOTH);
		antiDictionaryFilterButton.setLayoutData(myGd);

		// term number

		GridLayout termNumberGl = new GridLayout(2, false);
		Composite termNumberComposite = new Composite(filtersComposite,
				SWT.NONE);
		termNumberComposite.setLayout(termNumberGl);

		Label termNumberLabel = new Label(termNumberComposite, SWT.NONE);
		termNumberLabel
				.setText(Messages.getString("SaillanceViewPart.21") + ": "); //$NON-NLS-1$ //$NON-NLS-2$

		termNumberText = new Text(termNumberComposite, SWT.NONE);
		termNumberText.setEditable(false);
		Font font = new Font(shell.getDisplay(), "Arial", 10, SWT.BOLD); //$NON-NLS-1$
		termNumberText.setFont(font);
		Color color = shell.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		termNumberText.setForeground(color);
		myGd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		termNumberText.setLayoutData(myGd);

		// % validated terms

		Label percentValidatedLabel = new Label(termNumberComposite, SWT.NONE);
		percentValidatedLabel.setText(Messages
				.getString("SaillanceViewPart.24") + ": "); //$NON-NLS-1$ //$NON-NLS-2$

		percentValidatedText = new Text(termNumberComposite, SWT.NONE);
		percentValidatedText.setEditable(false);
		percentValidatedText.setFont(font);
		percentValidatedText.setForeground(color);
		myGd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		percentValidatedText.setLayoutData(myGd);

		// the table

		// log4j.info("  > create Table");

		myGd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);

		tableTermViewer = new TableViewer(generalComposite, SWT.VIRTUAL
				| SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION
				| SWT.BORDER);

		tableTermViewer.getTable().setLinesVisible(true);
		tableTermViewer.getTable().setHeaderVisible(true);
		tableTermViewer.getTable().setLayoutData(myGd);

		TableViewerColumn column;

		// log4j.info("  > create Columns");

		for (int i = 0; i < saillanceCriteria.length - 1; i++) {

			column = new TableViewerColumn(tableTermViewer, SWT.NONE, i);
			column.getColumn().setText(saillanceCriteria[i]);

			if (i == 0) {

				column.getColumn().setWidth(200);
				column.setLabelProvider(new TermLabelProvider());

			} else {

				column.getColumn().setWidth(50);
				column.getColumn().setMoveable(true);
				column.setLabelProvider(new StandardColumnLabelProvider(i));

			}

			final int j = i;

			column.getColumn().addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {

					ITerm currentTerm = ControlerFactoryImpl
							.getTerminologyControler().getCurrentTerm();

					tableTermViewer.setSorter(new TermSaillanceSorter(j));
					tableTermViewer.getTable().setSortColumn(
							tableTermViewer.getTable().getColumn(j));
					tableTermViewer.getTable().setSortDirection(
							TermSaillanceSorter.getDirection());

					if (currentTerm != null) {
						tableTermViewer.setSelection(new StructuredSelection(
								currentTerm));
					}
				}
			});

		}

		// log4j.info("  > create status column");

		column = new TableViewerColumn(tableTermViewer, SWT.CENTER,
				saillanceCriteria.length - 1);

		column.getColumn().setText(Messages.getString("SaillanceViewPart.20")); //$NON-NLS-1$
		column.getColumn().setWidth(50);
		column.getColumn().setMoveable(true);
		column.setLabelProvider(new StatusColumnLabelProvider());
		column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				tableTermViewer.setSorter(new TermSaillanceSorter(
						saillanceCriteria.length - 1));

				tableTermViewer.getTable().setSortColumn(
						tableTermViewer.getTable().getColumn(
								saillanceCriteria.length - 1));

				tableTermViewer.getTable().setSortDirection(
						TermSaillanceSorter.getDirection());
			}

		});

		// log4j.info("  > create linguistic status column");

		column = new TableViewerColumn(tableTermViewer, SWT.CENTER,
				saillanceCriteria.length);

		column.getColumn().setText(Messages.getString("SaillanceViewPart.31")); //$NON-NLS-1$
		column.getColumn().setWidth(50);
		column.getColumn().setMoveable(true);
		column.setLabelProvider(new LinguisticStatusColumnLabelProvider());
		column.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				tableTermViewer.setSorter(new TermSaillanceSorter(
						saillanceCriteria.length));

				tableTermViewer.getTable().setSortColumn(
						tableTermViewer.getTable().getColumn(
								saillanceCriteria.length));

				tableTermViewer.getTable().setSortDirection(
						TermSaillanceSorter.getDirection());
			}

		});
		// log4j.info("  > set table provider");

		tableTermViewer.setContentProvider(new ViewContentProvider());

		generalComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = generalComposite.getClientArea();
				Point preferredSize = tableTermViewer.getTable().computeSize(
						SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2
						* tableTermViewer.getTable().getBorderWidth();
				if (preferredSize.y > area.height
						+ tableTermViewer.getTable().getHeaderHeight()) {

					Point vBarSize = tableTermViewer.getTable()
							.getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = tableTermViewer.getTable().getSize();

				if (oldSize.x > area.width) {

					tableTermViewer.getTable().getColumn(0)
							.setWidth(width * 30 / 100);
					for (int i = 1; i < saillanceCriteria.length; i++) {
						int colWidth = width * 70 / 100
								/ (saillanceCriteria.length - 1);
						tableTermViewer.getTable().getColumn(i)
								.setWidth(colWidth);
					}
					tableTermViewer.getTable().setSize(area.width, area.height);
				} else {

					tableTermViewer.getTable().setSize(area.width, area.height);
					tableTermViewer.getTable().getColumn(0)
							.setWidth(width * 30 / 100);
					for (int i = 1; i < saillanceCriteria.length; i++) {
						int colWidth = width * 70 / 100
								/ (saillanceCriteria.length - 1);
						tableTermViewer.getTable().getColumn(i)
								.setWidth(colWidth);
					}
				}
			}
		});

		// log4j.info("  > create Data");

		createData();

		// log4j.info("  > assign Table");

		tableTermViewer.setInput(DatabaseAdapter.getTerms());

		tableTermViewer.getTable().setSortColumn(
				tableTermViewer.getTable().getColumn(0));
		tableTermViewer.getTable().setSortDirection(SWT.UP);

		// Filters

		// log4j.info("  > set table filters");

		termFilter = new TermSaillanceFilter(""); //$NON-NLS-1$
		// VT:
		// statusFilter = new StatusSaillanceFilter(-1);
		statusFilter = new StatusSaillanceFilter(TERMINO_OBJECT_STATE.UNKNOWN);
		antiDictionaryFilter = new AntiDictionaryTermFilter(""); //$NON-NLS-1$

		tableTermViewer.addFilter(termFilter);
		tableTermViewer.addFilter(statusFilter);
		tableTermViewer.addFilter(antiDictionaryFilter);

		tableTermViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						List<ITerm> currentTerms = new ArrayList<ITerm>();
						TableItem[] sel = tableTermViewer.getTable()
								.getSelection();

						if (sel.length != 0) {
							ITerm selectedTerm = (ITerm) (sel[0].getData());

							for (int i = 0; i < sel.length; i++) {
								currentTerms.add((ITerm) (sel[i].getData()));
							}

							if (selectedTerm != null) {

								ControlerFactoryImpl.getTerminologyControler()
										.setCurrentTerm(selectedTerm);
								ControlerFactoryImpl.getTerminologyControler()
										.setCurrentTerms(currentTerms);

							}
						}

					}
				});

		/* Compute the number of visible rows */
		tableTermViewer.getTable().addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				final Table table = tableTermViewer.getTable();
				Rectangle rect = table.getClientArea();
				int itemHeight = table.getItemHeight();
				int headerHeight = table.getHeaderHeight();
				visibleRowsCount = (rect.height - headerHeight + itemHeight - 1)
						/ itemHeight;
			}
		});

		// log4j.info("  > add double click listener ");

		tableTermViewer.getTable().addListener(SWT.MouseDoubleClick,
				new SaillanceViewPartTableViewerListener());

		// log4j.info("  > add property change listeners ");

		newVariantPropertyChangeListener = new SaillanceViewPartNewVariantPropertyChangeListener();
		newStatusPropertyChangeListener = new SaillanceViewPartNewStatusPropertyChangeListener();
		updateTermPropertyChangeListener = new SaillanceViewPartUpdateTermPropertyChangeListener();
		detachVariantPropertyChangeListener = new SaillanceViewPartDetachVariantPropertyChangeListener();
		termLabelModifiedPropertyChangeListener = new SaillanceViewPartTermLabelModifiedPropertyChangeListener();
		termsDeletedPropertyChangeListener = new SaillanceViewPartTermsDeletedPropertyChangeListener();
		termsImportedPropertyChangeListener = new SaillanceViewPartTermsImportedPropertyChangeListener();
		corpusChangedPropertyChangeListener = new SaillanceViewPartCorpusChangedPropertyChangeListener();

		ControlerFactoryImpl.getTerminologyControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.newVariantEvent,
						newVariantPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
				.addPropertyChangeListener(ControlerFactoryImpl.newStatusEvent,
						newStatusPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.currentTermEvent,
						updateTermPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.newVariantDetachedEvent,
						detachVariantPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.termLabelModifiedEvent,
						termLabelModifiedPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.termsDeletedEvent,
						termsDeletedPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.termsImportedEvent,
						termsImportedPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.currentCorpusEvent,
						corpusChangedPropertyChangeListener);

		// log4j.info("  > hook context menu ");

		hookContextMenu();

		// log4j.info("  > display statistics ");

		displayTermsStatistics();

		getSite().setSelectionProvider(tableTermViewer);

		// log4j.info("END createPartControl(...)");

	}

	public void displayTermsStatistics() {

		int res = 0;
		int count = 0;

		Iterator<ITerm> it = DatabaseAdapter.getTerms().iterator();

		while (it.hasNext()) {
			ITerm term = it.next();
			count++;

			// if term validated, status code = 1
			if (term.getState() == TERMINO_OBJECT_STATE.VALIDATED) {
				res++;
			}
		}

		if (count == 0) {
			res = 0;
		}

		termNumberText.setText(Integer.toString(count));

		if (count != 0) {
			res = (int) (((float) res / (float) count) * 100);
		}

		percentValidatedText.setText(Integer.toString(res) + " %"); //$NON-NLS-1$
	}

	private void createData() {

		DatabaseAdapter.loadTerms();

	}

	private void hookContextMenu() {

		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(tableTermViewer.getTable());
		tableTermViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, tableTermViewer);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */

	public void setFocus() {

	}

	public String selectXMLFile() {

		// log4j.info("BEGIN selectXMLFile(...)");

		String path = null;

		try {

			FileDialog dialog = new FileDialog(SaillanceViewPart.this.getSite()
					.getShell(), SWT.OPEN);

			dialog.setFilterExtensions(new String[] { "*.xml", "*.*" }); //$NON-NLS-1$ //$NON-NLS-2$

			dialog.setFilterPath(org.dafoe.terminologiclevel.Activator
					.getPluginFolder().getPath());

			path = dialog.open();

			// log4j.info("  > path selected: " + path);

		} catch (Exception e) {

			e.printStackTrace();

			// log4j.error("  > selectXMLFile Exception:");
			// log4j.error(e.getMessage());

		}
		// log4j.info("END selectXMLFile");

		return path;
	}

	class SaillanceViewPartTableViewerListener implements Listener {
		public void handleEvent(Event event) {

			Point pt = new Point(event.x, event.y);
			TableItem item = tableTermViewer.getTable().getItem(pt);
			if (item == null)
				return;
			for (int i = 0; i < tableTermViewer.getTable().getColumnCount(); i++) {

				Rectangle rect = item.getBounds(i);

				if (rect.contains(pt)) {

					ITerm selectedTerm = (ITerm) (item.getData());

					// Term column
					if (i == 0) {

						if ((selectedTerm.getState() != TERMINO_OBJECT_STATE.REJECTED)
								&& (selectedTerm.getState() != TERMINO_OBJECT_STATE.DELETED)) {

							switchPerspective();

						}

						// Frequency column
					} else if (i == 5) {

						// TO BE IMPLEMENTED
						System.out
								.println(Messages
										.getString("SaillanceViewPart.27") + selectedTerm.getLabel() + Messages.getString("SaillanceViewPart.28")); //$NON-NLS-1$ //$NON-NLS-2$

					}
				}
			}
		}
	}

	private void switchPerspective() {
		// log4j.info("BEGIN switchPerspective(...)");

		final IWorkbenchWindow workbenchWindow = SaillanceViewPart.this
				.getSite().getWorkbenchWindow();
		IPerspectiveDescriptor newPerspective = null;

		IPerspectiveDescriptor[] perspectives = workbenchWindow.getWorkbench()
				.getPerspectiveRegistry().getPerspectives();

		for (int i = 0; i < perspectives.length; i++) {
			if (perspectives[i].getId().compareTo(
					"org.dafoe.terminologiclevel.perspectivesCard") == 0) { //$NON-NLS-1$
				newPerspective = perspectives[i];
			}
		}

		if (workbenchWindow.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getPerspective() != newPerspective) {
			workbenchWindow.getActivePage().setPerspective(newPerspective);
		}

		// log4j.info("  > perspective selected: " + newPerspective.getLabel());
		//
		// log4j.info("END switchPerspective");

	}

	class SaillanceViewPartNewVariantPropertyChangeListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler()
					.getCurrentTerm();

			tableTermViewer.refresh(currentTerm);

			displayTermsStatistics();

		}
	}

	class SaillanceViewPartNewStatusPropertyChangeListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			List<ITerm> currentTerms = ControlerFactoryImpl
					.getTerminologyControler().getCurrentTerms();

			tableTermViewer.refresh(currentTerms);

			displayTermsStatistics();
		}
	}

	class SaillanceViewPartUpdateTermPropertyChangeListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler()
					.getCurrentTerm();

			tableTermViewer.refresh();

			displayTermsStatistics();

			tableTermViewer.setSelection(new StructuredSelection(currentTerm),
					true);
			
			// Center table on new selection
			int selectionIndex = tableTermViewer.getTable().getSelectionIndex();
			tableTermViewer.getTable().setTopIndex(
					Math.max(0, selectionIndex - ((visibleRowsCount-1) / 2)));
		}
	}

	class SaillanceViewPartDetachVariantPropertyChangeListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			tableTermViewer.refresh();
		}
	}

	class SaillanceViewPartTermLabelModifiedPropertyChangeListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			ITerm currentTerm = ControlerFactoryImpl.getTerminologyControler()
					.getCurrentTerm();

			tableTermViewer.refresh(currentTerm);
		}
	}

	class SaillanceViewPartTermsDeletedPropertyChangeListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			tableTermViewer.refresh();

			displayTermsStatistics();

		}
	}

	class SaillanceViewPartTermsImportedPropertyChangeListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			tableTermViewer.refresh();

			displayTermsStatistics();
		}
	}

	class SaillanceViewPartCorpusChangedPropertyChangeListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {

			createData();

			tableTermViewer.setInput(DatabaseAdapter.getTerms());

			displayTermsStatistics();
		}
	}

}