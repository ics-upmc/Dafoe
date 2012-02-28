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

package org.dafoe.terminoontologiclevel.ui.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotationType;
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.themes.IThemeManager;

public class TerminologicalViewPart extends ViewPart {

	private static String SENTENCE_DELETED = "SENTENCE_DELETED"; //$NON-NLS-1$

	private Composite currentLanguageTerms;

	private Composite otherLanguageTerms;

	private Text terminoConceptText;

	private TableViewer termsTableViewer;

	private TableViewer termsOtherLgTableViewer;

	private SentenceWidget sentencesWidget;

	private TableViewerColumn termColumn;

	private TableViewerColumn languageColumn;

	private TableViewerColumn termLgColumn;

	private TableViewerColumn languagesColumn;

	private ITerminoConcept currentTerminoConcept;

	private Color terminologicColor;

	private Shell shell;

	private Action makeStarTermAction;

	private Action addTermAction;

	private Action deleteTermAction;

	private Action addTranslatedTermAction;

	private Action deleteTranslatedTermAction;

	private static int directionTerm;

	private static int lastTransTermSelectedColumn;

	private static int directionTransTerm;

	public TerminologicalViewPart() {
		currentTerminoConcept = null;
		IThemeManager themeManager = PlatformUI.getWorkbench()
				.getThemeManager();
		terminologicColor = themeManager
				.getTheme("org.dafoe.terminologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	//

	@Override
	public void createPartControl(Composite parent) {

		shell = parent.getShell();

		FontRegistry fontRegistry = new FontRegistry(parent.getShell()
				.getDisplay());

		fontRegistry.put(
				"TC", new FontData[] { new FontData("Arial", 10, SWT.BOLD) }); //$NON-NLS-1$ //$NON-NLS-2$

		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, true);
		layout.horizontalSpacing = 5;
		layout.verticalSpacing = 5;
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		comp.setLayout(layout);

		GridData gd;

		terminoConceptText = new Text(comp, SWT.BORDER | SWT.CENTER
				| SWT.READ_ONLY);
		terminoConceptText.setEditable(false);
		terminoConceptText.setFont(fontRegistry.get("TC")); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
		terminoConceptText.setLayoutData(gd);

		// terminologic composite

		Composite terminoComposite = new Composite(comp, SWT.NONE);
		layout = new GridLayout(1, true);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		terminoComposite.setLayout(layout);
		terminoComposite.setLayoutData(gd);
		terminoComposite.setBackground(terminologicColor);

		// terms in current language

		currentLanguageTerms = new Composite(terminoComposite, SWT.NONE);
		layout = new GridLayout(2, false);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		currentLanguageTerms.setLayout(layout);
		currentLanguageTerms.setLayoutData(gd);

		termsTableViewer = new TableViewer(currentLanguageTerms, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		termsTableViewer.getTable().setLayoutData(gridData);
		termsTableViewer.getTable().setLinesVisible(true);
		termsTableViewer.getTable().setHeaderVisible(true);
		termsTableViewer.setContentProvider(new TermContentProvider());
		termsTableViewer.setSorter(new TermsTableViewerSorter());

		// Columns

		termColumn = new TableViewerColumn(termsTableViewer, SWT.NONE, 0);
		termColumn.getColumn().setText(
				Messages.getString("TerminologicalViewPart.13")); //$NON-NLS-1$
		termColumn.setLabelProvider(new TermLabelProvider());
		termColumn.getColumn().setAlignment(SWT.CENTER);

		termColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				TermsTableViewerSorter sorter = new TermsTableViewerSorter();
				termsTableViewer.setSorter(sorter);
				termsTableViewer.getTable().setSortColumn(
						termColumn.getColumn());
				termsTableViewer.getTable().setSortDirection(directionTerm);

			}

		});

		languageColumn = new TableViewerColumn(termsTableViewer, SWT.CENTER, 1);
		languageColumn.getColumn().setText(
				Messages.getString("TerminologicalViewPart.14")); //$NON-NLS-1$
		languageColumn.getColumn().setAlignment(SWT.CENTER);
		languageColumn.setLabelProvider(new DefaultLanguageLabelProvider());

		currentLanguageTerms.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = currentLanguageTerms.getClientArea();
				Point preferredSize = termsTableViewer.getTable().computeSize(
						SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2
						* termsTableViewer.getTable().getBorderWidth();
				if (preferredSize.y > area.height
						+ termsTableViewer.getTable().getHeaderHeight()) {

					Point vBarSize = termsTableViewer.getTable()
							.getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = termsTableViewer.getTable().getSize();

				if (oldSize.x > area.width) {
					termColumn.getColumn().setWidth(width * 80 / 100);
					languageColumn.getColumn().setWidth(width * 20 / 100);
					termsTableViewer.getTable()
							.setSize(area.width, area.height);
				} else {

					termsTableViewer.getTable()
							.setSize(area.width, area.height);
					termColumn.getColumn().setWidth(width * 80 / 100);
					languageColumn.getColumn().setWidth(width * 20 / 100);
				}
			}
		});

		Button addTermCurrentLg = new Button(currentLanguageTerms, SWT.PUSH);
		String imageId = org.dafoe.terminoontologiclevel.ui.Activator.NEW_IMG_ID;
		ImageDescriptor imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
				.getDefault().getImageRegistry().getDescriptor(imageId);
		Image image = imgDesc.createImage();
		addTermCurrentLg.setImage(image);
		addTermCurrentLg.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
				true, false));

		addTermCurrentLg.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				addTerm();
			}
		});

		Button removeTermCurrentLg = new Button(currentLanguageTerms, SWT.PUSH);
		imageId = org.dafoe.terminoontologiclevel.ui.Activator.REMOVE_IMG_ID;
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator.getDefault()
				.getImageRegistry().getDescriptor(imageId);
		image = imgDesc.createImage();
		removeTermCurrentLg.setImage(image);
		removeTermCurrentLg.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.CENTER, true, false));

		removeTermCurrentLg.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				removeTerm();

			}
		});

		// Sentences

		sentencesWidget = new SentenceWidget(comp, SWT.NONE);
		layout = new GridLayout(1, false);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		sentencesWidget.setLayout(layout);
		sentencesWidget.setLayoutData(gd);
		sentencesWidget
				.setDeletionEventType(TerminologicalViewPart.SENTENCE_DELETED);
		sentencesWidget
				.addPropertyChangeListener(new SentenceDeletedChangeListener());

		// terms in other languages

		otherLanguageTerms = new Composite(terminoComposite, SWT.NONE);
		layout = new GridLayout(2, false);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		otherLanguageTerms.setLayout(layout);
		otherLanguageTerms.setLayoutData(gd);

		termsOtherLgTableViewer = new TableViewer(otherLanguageTerms,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		termsOtherLgTableViewer.getTable().setLayoutData(gridData);
		termsOtherLgTableViewer.getTable().setLinesVisible(true);
		termsOtherLgTableViewer.getTable().setHeaderVisible(true);
		termsOtherLgTableViewer
				.setContentProvider(new TranslatedTermContentProvider());

		// Columns

		termLgColumn = new TableViewerColumn(termsOtherLgTableViewer, SWT.NONE,
				0);
		termLgColumn.getColumn().setText(
				Messages.getString("TerminologicalViewPart.17")); //$NON-NLS-1$
		termLgColumn.setLabelProvider(new TranslatedTermLabelProvider());

		termLgColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				TanslatedTermsTableViewerSorter sorter = new TanslatedTermsTableViewerSorter(
						TanslatedTermsTableViewerSorter.TERM_SORT);
				termsOtherLgTableViewer.setSorter(sorter);
				termsOtherLgTableViewer.getTable().setSortColumn(
						termLgColumn.getColumn());
				termsOtherLgTableViewer.getTable().setSortDirection(
						directionTransTerm);

			}

		});

		languagesColumn = new TableViewerColumn(termsOtherLgTableViewer,
				SWT.NONE, 1);
		languagesColumn.getColumn().setText(
				Messages.getString("TerminologicalViewPart.18")); //$NON-NLS-1$
		languagesColumn.setLabelProvider(new OtherLanguageLabelProvider());

		languagesColumn.getColumn().addSelectionListener(
				new SelectionAdapter() {

					public void widgetSelected(SelectionEvent e) {
						TanslatedTermsTableViewerSorter sorter = new TanslatedTermsTableViewerSorter(
								TanslatedTermsTableViewerSorter.FLAG_SORT);
						termsOtherLgTableViewer.setSorter(sorter);
						termsOtherLgTableViewer.getTable().setSortColumn(
								languagesColumn.getColumn());
						termsOtherLgTableViewer.getTable().setSortDirection(
								directionTransTerm);

					}

				});

		otherLanguageTerms.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = otherLanguageTerms.getClientArea();
				Point preferredSize = termsOtherLgTableViewer.getTable()
						.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2
						* termsOtherLgTableViewer.getTable().getBorderWidth();
				if (preferredSize.y > area.height
						+ termsOtherLgTableViewer.getTable().getHeaderHeight()) {

					Point vBarSize = termsOtherLgTableViewer.getTable()
							.getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = termsOtherLgTableViewer.getTable().getSize();

				if (oldSize.x > area.width) {

					termLgColumn.getColumn().setWidth(width * 80 / 100);
					languagesColumn.getColumn().setWidth(width * 20 / 100);
					termsOtherLgTableViewer.getTable().setSize(area.width,
							area.height);
				} else {
					termsOtherLgTableViewer.getTable().setSize(area.width,
							area.height);
					termLgColumn.getColumn().setWidth(width * 80 / 100);
					languagesColumn.getColumn().setWidth(width * 20 / 100);
				}
			}
		});

		Button addTermOtherLg = new Button(otherLanguageTerms, SWT.PUSH);
		imageId = org.dafoe.terminoontologiclevel.ui.Activator.NEW_IMG_ID;
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator.getDefault()
				.getImageRegistry().getDescriptor(imageId);
		image = imgDesc.createImage();
		addTermOtherLg.setImage(image);
		addTermOtherLg.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
				true, false));

		addTermOtherLg.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				addTranslatedTerm();
			}
		});

		Button removeTermOtherLg = new Button(otherLanguageTerms, SWT.PUSH);
		imageId = org.dafoe.terminoontologiclevel.ui.Activator.REMOVE_IMG_ID;
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator.getDefault()
				.getImageRegistry().getDescriptor(imageId);
		image = imgDesc.createImage();
		removeTermOtherLg.setImage(image);
		removeTermOtherLg.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
				true, false));

		removeTermOtherLg.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				removeTranslatedTerm();
			}
		});

		//

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(
						ControlerFactoryImpl.currentTerminoConceptEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateInformation();
							}

						});

		ControlerFactoryImpl.getTerminoOntoControler()
				.addPropertyChangeListener(ControlerFactoryImpl.renameTCEvent,
						new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {

								updateInformation();
							}

						});

		makeActions();
		hookPopupMenus();

		updateInformation();

	}

	//

	public void updateInformation() {

		currentTerminoConcept = ControlerFactoryImpl.getTerminoOntoControler()
				.getCurrentTerminoConcept();

		if (currentTerminoConcept != null) {
			terminoConceptText.setText(currentTerminoConcept.getLabel());
			termsTableViewer.setInput(UtilTools.setToList(currentTerminoConcept
					.getMappedTerms()));
			termsOtherLgTableViewer
					.setInput(getTCTranslationAnnotations(currentTerminoConcept));
			sentencesWidget.setInput(getSentences());
		}
	}

	//

	class TermContentProvider implements IStructuredContentProvider {
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((List<ITerm>) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	//

	class TermLabelProvider extends ColumnLabelProvider {
		public TermLabelProvider() {
			super();
		}

		/**
		 * 
		 */
		public String getText(Object element) {
			ITerm term = (ITerm) element;
			return term.getLabel();

		}

		@Override
		public Color getBackground(Object element) {
			ITerm term = (ITerm) element;
			Color starTermColor = null;

			if (currentTerminoConcept != null) {

				Set<ITerminoOntoAnnotation> annotations = currentTerminoConcept
						.getAnnotations();
				Iterator<ITerminoOntoAnnotation> it = annotations.iterator();
				ITerminoOntoAnnotation annotation = null;

				while (it.hasNext()) {
					ITerminoOntoAnnotation tmp = it.next();

					if (tmp.getTerminoOntoAnnotationType().getLabel()
							.compareTo(DatabaseAdapter.STAR_TERM_ANNOTATION) == 0) {
						annotation = tmp;
					}
				}

				if (annotation != null) {

					if (annotation.getValue().compareTo(term.getId() + "") == 0) { //$NON-NLS-1$

						starTermColor = shell.getDisplay().getSystemColor(
								SWT.COLOR_YELLOW);

					}
				}

			}
			return starTermColor;
		}

	}

	//

	class DefaultLanguageLabelProvider extends OwnerDrawLabelProvider {
		String defaultLanguage;

		public DefaultLanguageLabelProvider() {
			super();
			defaultLanguage = DatabaseAdapter.getDefaultLanguage();
		}

		@Override
		protected void measure(Event event, Object element) {
		}

		@Override
		protected void paint(Event event, Object element) {

			Image img = org.dafoe.terminoontologiclevel.ui.Activator
					.getDefault()
					.getImageRegistry()
					.get(
							org.dafoe.terminoontologiclevel.ui.Activator.FR_FLAG_IMG_ID);

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

	//

	class TranslatedTermContentProvider implements IStructuredContentProvider {
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((List<ITerminoOntoAnnotation>) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	//

	class TranslatedTermLabelProvider extends ColumnLabelProvider {
		public TranslatedTermLabelProvider() {
			super();
		}

		/**
		 * 
		 */
		public String getText(Object element) {
			ITerminoOntoAnnotation annotation = (ITerminoOntoAnnotation) element;
			return annotation.getValue();

		}
	}

	//

	class OtherLanguageLabelProvider extends OwnerDrawLabelProvider {

		public OtherLanguageLabelProvider() {
			super();
		}

		@Override
		protected void measure(Event event, Object element) {
		}

		@Override
		protected void paint(Event event, Object element) {

			ITerminoOntoAnnotation annotation = (ITerminoOntoAnnotation) element;
			ITerminoOntoAnnotationType annotationType = annotation
					.getTerminoOntoAnnotationType();
			Image img = null;

			if (annotationType.getLabel().compareTo(
					DatabaseAdapter.LABEL_EN_ANNOTATION) == 0) {
				img = org.dafoe.terminoontologiclevel.ui.Activator
						.getDefault()
						.getImageRegistry()
						.get(
								org.dafoe.terminoontologiclevel.ui.Activator.US_FLAG_IMG_ID);
			} else if (annotationType.getLabel().compareTo(
					DatabaseAdapter.LABEL_SP_ANNOTATION) == 0) {
				img = org.dafoe.terminoontologiclevel.ui.Activator
						.getDefault()
						.getImageRegistry()
						.get(
								org.dafoe.terminoontologiclevel.ui.Activator.SP_FLAG_IMG_ID);
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

	//

	@Override
	public void setFocus() {

	}

	//

	private List<ISentence> getSentences() {

		List<ISentence> sentences;

		sentences = DatabaseAdapter.getSentencesFromTC(currentTerminoConcept);

		return sentences;

	}

	//

	private void makeActions() {

		ImageDescriptor imgDesc;

		// remove a relation type => check if it is used in a terminological
		// relation. If not, delete.

		makeStarTermAction = new Action() {

			public void run() {

				TableItem[] sel = termsTableViewer.getTable().getSelection();

				if (sel.length > 0) {

					ITerm selTerm = (ITerm) (sel[0].getData());

					ITerminoConcept tc = ControlerFactoryImpl
							.getTerminoOntoControler()
							.getCurrentTerminoConcept();

					ITerminoOntoAnnotation starTermAnnotation = getStarTermAnnotation(tc);

					// this annotation already exists
					if (starTermAnnotation != null) {
						DatabaseAdapter.updateAnnotation(starTermAnnotation,
								selTerm.getId() + ""); //$NON-NLS-1$
					} else {
						DatabaseAdapter.createTCAnnotation(tc,
								DatabaseAdapter.STAR_TERM_ANNOTATION, selTerm
										.getId()
										+ ""); //$NON-NLS-1$
					}

					updateInformation();
				}

			}
		};
		makeStarTermAction.setText(Messages
				.getString("TerminologicalViewPart.0")); //$NON-NLS-1$
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(
						org.dafoe.terminoontologiclevel.ui.Activator.STAR_IMG_ID);
		makeStarTermAction.setImageDescriptor(imgDesc);
		makeStarTermAction.setEnabled(true);

		//

		addTermAction = new Action() {

			public void run() {

				addTerm();

			}
		};
		addTermAction.setText(Messages.getString("TerminologicalViewPart.1")); //$NON-NLS-1$
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(
						org.dafoe.terminoontologiclevel.ui.Activator.NEW_IMG_ID);
		addTermAction.setImageDescriptor(imgDesc);
		addTermAction.setEnabled(true);

		deleteTermAction = new Action() {

			public void run() {

				removeTerm();

			}
		};
		deleteTermAction
				.setText(Messages.getString("TerminologicalViewPart.2")); //$NON-NLS-1$
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(
						org.dafoe.terminoontologiclevel.ui.Activator.REMOVE_IMG_ID);
		deleteTermAction.setImageDescriptor(imgDesc);
		deleteTermAction.setEnabled(true);

		//

		addTranslatedTermAction = new Action() {

			public void run() {

				addTranslatedTerm();

			}
		};
		addTranslatedTermAction.setText(Messages
				.getString("TerminologicalViewPart.3")); //$NON-NLS-1$
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(
						org.dafoe.terminoontologiclevel.ui.Activator.NEW_IMG_ID);
		addTranslatedTermAction.setImageDescriptor(imgDesc);
		addTranslatedTermAction.setEnabled(true);

		//

		deleteTranslatedTermAction = new Action() {

			public void run() {

				removeTranslatedTerm();

			}
		};
		deleteTranslatedTermAction.setText(Messages
				.getString("TerminologicalViewPart.4")); //$NON-NLS-1$
		imgDesc = org.dafoe.terminoontologiclevel.ui.Activator
				.getDefault()
				.getImageRegistry()
				.getDescriptor(
						org.dafoe.terminoontologiclevel.ui.Activator.REMOVE_IMG_ID);
		deleteTranslatedTermAction.setImageDescriptor(imgDesc);
		deleteTranslatedTermAction.setEnabled(true);

	}

	//

	private void hookPopupMenus() {
		MenuManager menuMgr = new MenuManager("#PopupMenu1"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(termsTableViewer.getTable());
		termsTableViewer.getControl().setMenu(menu);

		menuMgr.add(this.makeStarTermAction);
		menuMgr.add(this.addTermAction);
		menuMgr.add(this.deleteTermAction);

		menuMgr = new MenuManager("#PopupMenu2"); //$NON-NLS-1$
		menu = menuMgr.createContextMenu(termsOtherLgTableViewer.getTable());
		termsOtherLgTableViewer.getControl().setMenu(menu);

		menuMgr.add(this.addTranslatedTermAction);
		menuMgr.add(this.deleteTranslatedTermAction);
	}

	//

	private ITerminoOntoAnnotation getStarTermAnnotation(ITerminoConcept tc) {
		Set<ITerminoOntoAnnotation> annotations = tc.getAnnotations();
		Iterator<ITerminoOntoAnnotation> it = annotations.iterator();

		ITerminoOntoAnnotation starTermAnnotation = null;

		while (it.hasNext()) {
			ITerminoOntoAnnotation tmp = it.next();

			if (tmp.getTerminoOntoAnnotationType().getLabel().compareTo(
					DatabaseAdapter.STAR_TERM_ANNOTATION) == 0) {
				starTermAnnotation = tmp;
			}
		}

		return starTermAnnotation;

	}

	//

	private List<ITerminoOntoAnnotation> getTCTranslationAnnotations(
			ITerminoConcept tc) {
		Set<ITerminoOntoAnnotation> annotations = tc.getAnnotations();
		Iterator<ITerminoOntoAnnotation> it = annotations.iterator();

		List<ITerminoOntoAnnotation> res = new ArrayList<ITerminoOntoAnnotation>();

		while (it.hasNext()) {
			ITerminoOntoAnnotation tmp = it.next();

			if ((tmp.getTerminoOntoAnnotationType().getLabel().compareTo(
					DatabaseAdapter.LABEL_EN_ANNOTATION) == 0)
					|| (tmp.getTerminoOntoAnnotationType().getLabel()
							.compareTo(DatabaseAdapter.LABEL_SP_ANNOTATION) == 0)) {
				res.add(tmp);
			}
		}

		return res;

	}

	//

	class TermsTableViewerSorter extends ViewerSorter {

		/**
		 *
		 */
		public TermsTableViewerSorter() {
			super();

			if (directionTerm == SWT.UP) {
				directionTerm = SWT.DOWN;
			} else {
				directionTerm = SWT.UP;
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

			if (directionTerm == SWT.DOWN)
				res = -res;

			return res;

		}

	}

	//

	class TanslatedTermsTableViewerSorter extends ViewerSorter {

		private final static int TERM_SORT = 0;
		private final static int FLAG_SORT = 1;

		private int col;

		/**
		 *
		 */
		public TanslatedTermsTableViewerSorter(int col) {
			super();

			this.col = col;
			if (this.col == lastTransTermSelectedColumn) {
				if (directionTransTerm == SWT.UP) {
					directionTransTerm = SWT.DOWN;
				} else {
					directionTransTerm = SWT.UP;
				}
			} else {
				lastTransTermSelectedColumn = this.col;
				directionTransTerm = SWT.UP;
			}
		}

		/*
		 * (non-Javadoc)
		 */

		public int compare(Viewer viewer, Object o1, Object o2) {
			int res = 0;

			ITerminoOntoAnnotation annot1 = (ITerminoOntoAnnotation) o1;
			ITerminoOntoAnnotation annot2 = (ITerminoOntoAnnotation) o2;

			switch (col) {
			case TERM_SORT:
				res = annot1.getValue().compareToIgnoreCase(annot2.getValue());
				break;
			case FLAG_SORT:
				res = annot1.getTerminoOntoAnnotationType().getLabel()
						.compareToIgnoreCase(
								annot2.getTerminoOntoAnnotationType()
										.getLabel());
				break;
			}

			if (directionTransTerm == SWT.DOWN)
				res = -res;

			return res;

		}

	}

	//

	class SentenceDeletedChangeListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent arg0) {

			DatabaseAdapter.deleteSentencesFromTC(ControlerFactoryImpl
					.getTerminoOntoControler().getCurrentTerminoConcept(),
					sentencesWidget.getSelection());

			updateInformation();

		}
	}

	// 

	private void addTerm() {

		NewTermDialog dialog = new NewTermDialog(shell,
				TerminologicalViewPart.this, false);
		dialog.open();

		if (dialog.getReturnCode() == IDialogConstants.OK_ID) {

			if (dialog.getTerm().compareTo("") != 0) { //$NON-NLS-1$

				ITerminoConcept tc = ControlerFactoryImpl
						.getTerminoOntoControler().getCurrentTerminoConcept();

				ITerm t = org.dafoe.terminology.common.DatabaseAdapter
						.createTermFromLabel(dialog.getTerm(),
								TERMINO_OBJECT_STATE.VALIDATED,
								LINGUISTIC_STATUS.TERM);

				DatabaseAdapter.linkTCWithTerm(tc, t);

				updateInformation();
			}

		}
	}

	// 

	private void removeTerm() {

		TableItem[] sel = termsTableViewer.getTable().getSelection();

		if (sel.length > 0) {

			ITerm selTerm = (ITerm) (sel[0].getData());

			ITerminoConcept tc = ControlerFactoryImpl.getTerminoOntoControler()
					.getCurrentTerminoConcept();

			ITerminoOntoAnnotation starTermAnnotation = getStarTermAnnotation(tc);

			if (starTermAnnotation != null) {

				if (starTermAnnotation.getValue().compareTo(
						selTerm.getId() + "") == 0) { //$NON-NLS-1$

					DatabaseAdapter.deleteTCAnnotation(tc, starTermAnnotation);

				}

			}

			List<ITerminoConcept> tcs = new ArrayList<ITerminoConcept>();
			tcs.add(tc);
			DatabaseAdapter.unlinkTerm(tcs, selTerm);

			updateInformation();
		}

	}

	// 

	private void addTranslatedTerm() {
		NewTermDialog dialog = new NewTermDialog(shell,
				TerminologicalViewPart.this, true);
		dialog.open();

		if (dialog.getReturnCode() == IDialogConstants.OK_ID) {

			ITerminoConcept tc = ControlerFactoryImpl.getTerminoOntoControler()
					.getCurrentTerminoConcept();

			String selLanguage = dialog.getLanguage();
			String databaseAnnotation = DatabaseAdapter.LABEL_EN_ANNOTATION;

			if (selLanguage.compareTo(NewTermDialog.SPANISH) == 0) {
				databaseAnnotation = DatabaseAdapter.LABEL_SP_ANNOTATION;
			} else if (selLanguage.compareTo(NewTermDialog.ENGLISH) == 0) {
				databaseAnnotation = DatabaseAdapter.LABEL_EN_ANNOTATION;
			}

			DatabaseAdapter.createTCAnnotation(tc, databaseAnnotation, dialog
					.getTerm());

			updateInformation();
		}
	}

	// 

	private void removeTranslatedTerm() {
		TableItem[] sel = termsOtherLgTableViewer.getTable().getSelection();

		if (sel.length > 0) {

			ITerminoOntoAnnotation selAnnot = (ITerminoOntoAnnotation) (sel[0]
					.getData());

			ITerminoConcept tc = ControlerFactoryImpl.getTerminoOntoControler()
					.getCurrentTerminoConcept();

			DatabaseAdapter.deleteTCAnnotation(tc, selAnnot);

			updateInformation();
		}
	}
}
