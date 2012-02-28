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
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.terminologiclevel.terminologycard.dialogs.RenameTermDialog;
import org.dafoe.terminologiclevel.terminologycard.termautocomplete.AutocompleteTermSelector;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class TerminologyAreaViewPart extends ViewPart {
	public static final String ID = "org.dafoe.terminologiclevel.terminologycard.TerminologyAreaViewPartId"; //$NON-NLS-1$

	//private static String NEW_VARIANT_DIALOG_TITLE = Messages.getString("TerminologyAreaViewPart.0"); //$NON-NLS-1$
	//private static String NEW_VARIANT_DIALOG_MESSAGE = Messages.getString("TerminologyAreaViewPart.2"); //$NON-NLS-1$

	public final static int TERM_SORT = 0;
	public final static int STATUS_SORT = 1;

	private static int direction;
	private static int lastSelectedColumn;

	private TableViewer variantsViewer;
	private Composite generalComposite;
	private TableViewerColumn variantColumn;
	private TableViewerColumn variantStatusColumn;
	private Label validationStatusLabel;
	private Text starTerm;
	private Button[] linguisticStatusRadioButtons;

	private AutocompleteTermSelector auto;
	
	private TerminologyAreaViewPartCurrentTermPropertyChangeListener currentTermPropertyChangeListener;
	private TerminologyAreaViewPartNewVariantPropertyChangeListener newVariantPropertyChangeListener;	
	private TerminologyAreaViewPartNewStatusPropertyChangeListener newStatusPropertyChangeListener;
	private TerminologyAreaViewPartDetachVariantPropertyChangeListener detachVariantPropertyChangeListener;
	
	private ITerm currentTerm;
	
	private Action renameAction;

	//
	
	public TerminologyAreaViewPart() {
	}

	//
	
	public TableViewer getTableVariantViewer() {
		return this.variantsViewer;
	}

	//
	
	private void updateStarTerm() {
		
			starTerm.setText(currentTerm.getLabel());
			starTerm.update();

			setEnabledLinguisticStatus(true);
			
			setSelectionLinguisticStatus(currentTerm);
			
			validationStatusLabel.setImage(setStatusImage(currentTerm));			

			List<ITerm> variants = UtilTools.setToList(currentTerm.getVariantes());
			
			variantsViewer.setInput(variants);

	}

	//
	
	public void updateVariants(IWorkbenchPart sourcepart,
			IStructuredSelection selection){
		if (!selection.isEmpty()) {

			Object sel = selection.getFirstElement();
			
			ITerm variant = (ITerm) sel;
			
			variantsViewer.setInput(variant.getStarTerm().getVariantes());
			variantsViewer.getTable().setSelection(
					variantsViewer.getTable().getItemCount() - 1);
		}
	}
	
	//
	
	class VariantViewContentProvider implements IStructuredContentProvider {
		private List<ITerm> variantes;

		public Object[] getElements(Object inputElement) {
			return ((List<ITerm>) inputElement).toArray();
		}

		public void dispose() {

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			this.variantes = (List<ITerm>) newInput;
		}

	}

	//
	
	@Override
	public void createPartControl(Composite parent) {
		
		currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
			
		generalComposite = new Composite(parent, SWT.NONE);
		GridLayout generalLayout = new GridLayout();
		generalLayout.numColumns = 1;
		generalComposite.setLayout(generalLayout);

		// star term area

		GridData myGridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		Label starTermLabel = new Label(generalComposite, SWT.NONE);
		starTermLabel.setText(Messages.getString("TerminologyAreaViewPart.1")); //$NON-NLS-1$
		starTermLabel.setLayoutData(myGridData);

		myGridData = new GridData(GridData.FILL_HORIZONTAL);
		Composite starTermComposite = new Composite(generalComposite, SWT.NONE);
		starTermComposite.setLayoutData(myGridData);

		GridLayout starTermLayout = new GridLayout(2, false);
		starTermComposite.setLayout(starTermLayout);

		myGridData = new GridData(GridData.FILL_HORIZONTAL);
		starTerm = new Text(starTermComposite, SWT.BORDER);
		starTerm.setLayoutData(myGridData);
		
		auto = new AutocompleteTermSelector(starTerm, DatabaseAdapter.getTerms());

		
		starTerm.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				starTerm.selectAll();
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {
			}
			
		});
		
		starTerm.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
				starTerm.selectAll();
				
			}

			public void focusLost(FocusEvent e) {
				
				starTerm.setText(currentTerm.getLabel());
				
			}
			
		});
		
		starTerm.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				
				if (e.keyCode == SWT.CR){
					
					String label = starTerm.getText();
					
					ITerm term = auto.getTermFromSelectedLabel(label);
					
					if  (term != null) {
						
						currentTerm = term;
						
						ControlerFactoryImpl.getTerminologyControler().setCurrentTerm(currentTerm);
						
					} else {
						
						starTerm.setText(currentTerm.getLabel());
						
					}
					
					
				} else if (e.keyCode == SWT.ESC){
					
					starTerm.setText(currentTerm.getLabel());
					
				}
				
			}

			public void keyReleased(KeyEvent e) {
				
			}
			
		});



	    Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
	    DragSource source = new DragSource(starTerm, DND.DROP_MOVE | DND.DROP_COPY);
	    source.setTransfer(types);
		
		 source.addDragListener(new DragSourceListener() { 
			 public void dragStart(DragSourceEvent event) { 
				 if (starTerm.getText().length() == 0) { 
					 event.doit = false; 
				 } 
			 } 
			 public void dragSetData(DragSourceEvent event) { 
			        DragSource ds = (DragSource) event.widget;
			        Text text = (Text) ds.getControl();
			        event.data = text.getSelectionText();
			 } 
			 public void dragFinished(DragSourceEvent event) { 
			 } 
		 }); 

		
		validationStatusLabel = new Label(starTermComposite, SWT.BORDER);
		Image img = setStatusImage(currentTerm);
		validationStatusLabel.setImage(img);

		// star term linguistic status

		Composite linguisticStatusTermComposite = new Composite(
				generalComposite, SWT.NONE);

		GridLayout linguisticStatusTermLayout = new GridLayout(3, false);
		linguisticStatusTermComposite.setLayout(linguisticStatusTermLayout);

		myGridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);

		linguisticStatusRadioButtons = new Button[3];

		// Is term
		linguisticStatusRadioButtons[0] = new Button(
				linguisticStatusTermComposite, SWT.RADIO);
		linguisticStatusRadioButtons[0].setText(Messages
				.getString("TerminologyAreaViewPart.6")); //$NON-NLS-1$ //$NON-NLS-2$
		linguisticStatusRadioButtons[0].setSelection(false);
		linguisticStatusRadioButtons[0].setLayoutData(myGridData);

		// Is named entity
		linguisticStatusRadioButtons[1] = new Button(
				linguisticStatusTermComposite, SWT.RADIO);
		linguisticStatusRadioButtons[1].setText(Messages
				.getString("TerminologyAreaViewPart.8")); //$NON-NLS-1$ //$NON-NLS-2$
		linguisticStatusRadioButtons[1].setSelection(false);
		linguisticStatusRadioButtons[0].setLayoutData(myGridData);

		// Is indifferentiated
		linguisticStatusRadioButtons[2] = new Button(
				linguisticStatusTermComposite, SWT.RADIO);
		linguisticStatusRadioButtons[2].setText(Messages
				.getString("TerminologyAreaViewPart.4")); //$NON-NLS-1$ //$NON-NLS-2$
		linguisticStatusRadioButtons[2].setSelection(false);
		linguisticStatusRadioButtons[0].setLayoutData(myGridData);

		setEnabledLinguisticStatus(currentTerm != null);

		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				Button button = (Button) e.widget;

				if (button.getSelection()) {
					
					if (currentTerm != null) {
						
						if (button == linguisticStatusRadioButtons[0]) {
							
							DatabaseAdapter.updateTermLinguisticStatus(currentTerm, LINGUISTIC_STATUS.TERM);
							
						} else if (button == linguisticStatusRadioButtons[1]) {
							
							DatabaseAdapter.updateTermLinguisticStatus(currentTerm, LINGUISTIC_STATUS.NAMED_ENTITY);
							
						} else if (button == linguisticStatusRadioButtons[2]) {
							
							DatabaseAdapter.updateTermLinguisticStatus(currentTerm, LINGUISTIC_STATUS.INDIFFERENTIATED);
						}
					}
				}
				
			}
		};

		linguisticStatusRadioButtons[0].addListener(SWT.Selection, listener);
		linguisticStatusRadioButtons[1].addListener(SWT.Selection, listener);
		linguisticStatusRadioButtons[2].addListener(SWT.Selection, listener);

		
		// variants
		myGridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		Label variantesTermLabel = new Label(generalComposite, SWT.NONE);
		variantesTermLabel.setText(Messages
				.getString("TerminologyAreaViewPart.10")); //$NON-NLS-1$
		variantesTermLabel.setLayoutData(myGridData);

		myGridData = new GridData(GridData.FILL_BOTH);
		variantsViewer = new TableViewer(generalComposite, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		variantColumn = new TableViewerColumn(variantsViewer, SWT.NONE, 0);
		variantColumn.getColumn().setText(
				Messages.getString("TerminologyAreaViewPart.11")); //$NON-NLS-1$
		variantColumn.getColumn().setWidth(400);
		variantColumn.setLabelProvider(new StandardColumnLabelProvider());
		
		variantsViewer.setSorter(new VariantSorter(TERM_SORT));
		variantsViewer.getTable().setSortColumn(variantColumn.getColumn());
		variantsViewer.getTable().setSortDirection(TerminologyAreaViewPart.getDirection());
		
		variantColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(TERM_SORT);
				
			}

		});
		
		
		variantStatusColumn = new TableViewerColumn(variantsViewer, SWT.NONE, 1);
		variantStatusColumn.getColumn().setWidth(50);
		variantStatusColumn.getColumn().setText(
				Messages.getString("TerminologyAreaViewPart.12")); //$NON-NLS-1$
		variantStatusColumn.setLabelProvider(new StatusColumnLabelProvider());

		variantStatusColumn.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter(STATUS_SORT);

			}

		});

		variantsViewer.setSorter(new VariantSorter(STATUS_SORT));
		variantsViewer.getTable().setSortColumn(variantStatusColumn.getColumn());
		variantsViewer.getTable().setSortDirection(TerminologyAreaViewPart.getDirection());
		
		variantsViewer.getTable().setLinesVisible(true);
		variantsViewer.getTable().setHeaderVisible(true);

		variantsViewer.setContentProvider(new VariantViewContentProvider());

		variantsViewer.getTable().setSortColumn(variantColumn.getColumn());
		variantsViewer.getTable().setSortDirection(SWT.UP);

		
		// myVariantesViewer.setLabelProvider(new MyLabelProvider());
		variantsViewer.getTable().setLayoutData(myGridData);

		variantsViewer.setInput(createData());

		variantsViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				
				List<ITerm> currentVariants = new ArrayList<ITerm>();
				TableItem[] sel = variantsViewer.getTable().getSelection();

				for (int i = 0; i < sel.length; i++) {
					currentVariants.add((ITerm) (sel[i].getData()));
				}

				ControlerFactoryImpl.getTerminologyControler().setCurrentVariants(currentVariants);
				
			}
		});

		
		
		generalComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = generalComposite.getClientArea();
				Point preferredSize = variantsViewer.getTable().computeSize(
						SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2
						* variantsViewer.getTable().getBorderWidth();

				if (preferredSize.y > area.height
						+ variantsViewer.getTable().getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = variantsViewer.getTable().getVerticalBar()
							.getSize();
					width -= vBarSize.x;
				}
				Point oldSize = variantsViewer.getTable().getSize();
				if (oldSize.x > area.width) {
					// table is getting smaller so make the columns
					// smaller first and then resize the table to
					// match the client area width
					variantColumn.getColumn().setWidth(width * 8 / 10);
					variantStatusColumn.getColumn().setWidth(
							width - variantColumn.getColumn().getWidth() - 10);
					variantsViewer.getTable().setSize(area.width, area.height);
				} else {
					// table is getting bigger so make the table
					// bigger first and then make the columns wider
					// to match the client area width
					variantsViewer.getTable().setSize(area.width, area.height);
					variantColumn.getColumn().setWidth(width * 8 / 10);
					variantStatusColumn.getColumn().setWidth(
							width - variantColumn.getColumn().getWidth() - 10);
				}
			}
		});

		if (currentTerm != null) {
			updateStarTerm();
			updateLinguisticStatus(linguisticStatusRadioButtons, currentTerm);
		}

		currentTermPropertyChangeListener = new TerminologyAreaViewPartCurrentTermPropertyChangeListener();
		newVariantPropertyChangeListener= new TerminologyAreaViewPartNewVariantPropertyChangeListener();
		newStatusPropertyChangeListener = new TerminologyAreaViewPartNewStatusPropertyChangeListener();
		detachVariantPropertyChangeListener = new TerminologyAreaViewPartDetachVariantPropertyChangeListener();
		
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.currentTermEvent, currentTermPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.newVariantEvent, newVariantPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.newStatusEvent, newStatusPropertyChangeListener);
		ControlerFactoryImpl.getTerminologyControler()
			.addPropertyChangeListener(ControlerFactoryImpl.newVariantDetachedEvent, detachVariantPropertyChangeListener);
		
		makeActions();
		
		hookContextMenu();
		// hookDoubleClickAction();
		contributeToActionBars();
		
		getSite().setSelectionProvider(variantsViewer);
		
		
	}

	//
	
	public static int getDirection(){
		return direction;
	}
	
	//
	
	public void setSorter(int sortedColumn){
		
		variantsViewer.setSorter(new VariantSorter(sortedColumn));

		variantsViewer.getTable().setSortColumn(variantsViewer.getTable().getColumn(sortedColumn));

		variantsViewer.getTable().setSortDirection(TerminologyAreaViewPart.getDirection());
	
	}
	
	//
	
	private List<ITerm> createData() {

		return null;

	}

	//
	
	private void setEnabledLinguisticStatus(boolean enabled) {
		for (int i = 0; i < linguisticStatusRadioButtons.length; i++) {
			linguisticStatusRadioButtons[i].setEnabled(enabled);
		}
	}

	//
	
	private void setSelectionLinguisticStatus(ITerm currentTerm) {
		
		for (int i = 0; i < linguisticStatusRadioButtons.length; i++) {
			linguisticStatusRadioButtons[i].setSelection(false);
		}
		
		updateLinguisticStatus(linguisticStatusRadioButtons, currentTerm);

	}

	//
	
	private Image setStatusImage(ITerm term) {
		Image img = null;
		Color color = null;

		if (term != null) {
			TERMINO_OBJECT_STATE status = term.getState();

			if (status == TERMINO_OBJECT_STATE.VALIDATED) {
				color = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
			} else if (status == TERMINO_OBJECT_STATE.REJECTED) {
				color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
			} else if (status == TERMINO_OBJECT_STATE.STUDIED) {
				color = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
			} else if (status == TERMINO_OBJECT_STATE.DELETED) {
				color = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
			}

			if (status != TERMINO_OBJECT_STATE.CONCEPTUALIZED) {
				img = createImage(Display.getDefault(), color);
				
			} else {
				ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator
						.getDefault()
						.getImageRegistry()
						.getDescriptor(org.dafoe.terminologiclevel.Activator.CONCEPTUALIZED_IMG_ID);
				img = imgDesc.createImage();

				Image scaledImage = null;
				final int width = img.getBounds().width;
				final int height = img.getBounds().height;

				scaledImage = new Image(Display.getDefault(), img
						.getImageData().scaledTo((int) (width * 1.3),
								(int) (height * 1.3)));

				img = scaledImage;
			}

		} else {
			color = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
			img = createImage(Display.getDefault(), color);
		}

		return img;
	}

	//
	
	@Override
	public void setFocus() {
	}

	//
	
	private void hookContextMenu() {

		MenuManager variantMenuMgr = new MenuManager("#VariantPopupMenu"); //$NON-NLS-1$
		Menu variantMenu = variantMenuMgr.createContextMenu(variantsViewer.getTable());
		variantsViewer.getControl().setMenu(variantMenu);
		getSite().registerContextMenu(variantMenuMgr, variantsViewer);
		
		MenuManager menuMgr = new MenuManager("#StarTermPopupMenu"); //$NON-NLS-1$
		Menu starTermMenu = menuMgr.createContextMenu(starTerm);
		starTerm.setMenu(starTermMenu);
		
		Action act = this.renameAction;
		menuMgr.add(act);

	}

	//
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
	}

	//
	
	private void fillLocalPullDown(IMenuManager manager) {
	}


	//
	
	/*
	private void updateTermStatus(int status) {
		List<ITerm> terms = new ArrayList<ITerm>();
		
		terms.add(currentTerm);
		
		DatabaseAdapter.updateTermsStatus(terms, status);
		
		ControlerFactoryImpl.getTerminologyControler().setNewStatus();	

	}
*/
	
	//
	
	private static Image createImage(Display display, Color color) {
		Image image = new Image(display, 15, 15);
		GC gc = new GC(image);
		gc.setBackground(color);
		gc.fillRectangle(0, 0, 15, 15);
		gc.dispose();

		return image;
	}

	//
	
	private static void updateLinguisticStatus(Button[] linguisticStatusRadioButtons, ITerm term){

		if (term != null) {
			//VT: use type-safe approach
			 //int linguisticStatus = term.isNamedEntity();
			LINGUISTIC_STATUS linguisticStatus = term.getLinguisticStatus();
			if (linguisticStatus == LINGUISTIC_STATUS.NAMED_ENTITY){
				linguisticStatusRadioButtons[1].setSelection(true);
			} else if (linguisticStatus == LINGUISTIC_STATUS.TERM) {
				linguisticStatusRadioButtons[0].setSelection(true);
			} else if (linguisticStatus == LINGUISTIC_STATUS.INDIFFERENTIATED) {
				linguisticStatusRadioButtons[2].setSelection(true);
			}
		}
	}
	
	//
	
	class VariantSorter extends ViewerSorter {
		private int col;
		
		public VariantSorter(int col) {
			this.col = col;
			if (this.col == lastSelectedColumn) {
				if (direction == SWT.UP) {
					direction = SWT.DOWN;
				} else {
					direction = SWT.UP;
				}
			} else {
				lastSelectedColumn = this.col;
				direction = SWT.UP;
			}
		}

		
		/*
		 * (non-Javadoc)
		 */

		public int compare(Viewer viewer, Object o1, Object o2) {
			int res = 0;

			ITerm t1 = (ITerm) o1;
			ITerm t2 = (ITerm) o2;

			switch (col) {
			case TERM_SORT:
				res = compareTerm(t1, t2);
				break;

			case STATUS_SORT:
				res = compareStatus(t1, t2);
				break;
			}
			
			if (direction == SWT.DOWN)
				res = -res;

			return res;
		}

		protected int compareTerm(ITerm t1, ITerm t2) {
			return t1.getLabel().compareToIgnoreCase(t2.getLabel());
		}

		private int compareStatus(ITerm t1, ITerm t2) {
			//VT:
			//int result = t1.getState() - t2.getState();
			int result = t1.getState().getValue() - t2.getState().getValue();
			result = result < 0 ? -1 : (result > 0) ? 1 : 0;
			return result;
		}

	}
	

	//
	
	private void makeActions() {
		
		renameAction = new Action() {
			public void run() {
				RenameTermDialog dial = new RenameTermDialog(TerminologyAreaViewPart.this.getSite().getShell()
						, currentTerm.getLabel());
				dial.open();
				
				
				if (dial.getReturnCode() == IDialogConstants.OK_ID){

					String label = dial.getTermLabel();;

					if (label.compareTo("") != 0) {

						starTerm.setText(label);
						
						currentTerm.setLabel(label);

						DatabaseAdapter.updateTermLabel(currentTerm, label);

						ControlerFactoryImpl.getTerminologyControler().setTermLabelModified();

						//starTerm.selectAll();

					}
				}
			}

		};
		renameAction.setText("Rename");
		
	}
	
	//

	class TerminologyAreaViewPartCurrentTermPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

			updateStarTerm();			
		}
		
	}

	//
	
	class TerminologyAreaViewPartNewVariantPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
			
			updateStarTerm();			
		}
	}

	//
	
	class TerminologyAreaViewPartNewStatusPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
			
			updateStarTerm();			
		}
	}

	//
	
	class TerminologyAreaViewPartDetachVariantPropertyChangeListener implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			
			currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
			
			updateStarTerm();			
		}
	}

}
