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

import java.util.ArrayList;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.terminologiclevel.terminologycard.TerminoConceptAreaViewPart;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class TerminoConceptMappingDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages.getString("TerminoConceptMappingDialog.0"); //$NON-NLS-1$
	private static String DIALOG_MESSAGE = Messages.getString("TerminoConceptMappingDialog.1"); //$NON-NLS-1$

	private static int direction;

	private String title;
	private String message;

	private Color terminoOntologicColor, terminologicColor;
	
	private TerminoConceptAreaViewPart viewPart;
	private Composite inComposite;
	private TableViewerColumn col;
	private Text termLabel, conceptLabel;
	private TableViewer associatedConcepts;
	private TCWidget terminoConcepts;
	
	private Composite linkedConceptsComposite;
	
	private ITerm currentTerm = null;
	
	private Action unlinkAction;
	
	private List<ITerminoConcept> tcs = new ArrayList<ITerminoConcept>();
	
	private Shell shell;
	
	
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	
	public TerminoConceptMappingDialog(Shell parent, TerminoConceptAreaViewPart viewPart) {
		super(parent);
		
		this.viewPart = viewPart;
		setShellStyle(SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE);
		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		this.currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
		
		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		terminoOntologicColor = themeManager.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
		terminologicColor = themeManager.getTheme("org.dafoe.terminologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		shell = parent;
	}

	public boolean close(){
		return super.close();
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		this.setTitle(this.title);
		this.setMessage(this.message);
		this.getShell().setText(this.title);
		return contents;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		FontRegistry fontRegistry = new FontRegistry(this.getShell().getDisplay());

		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		
		final Composite container = (Composite) super.createDialogArea(parent);

		inComposite = new Composite(container, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		inComposite.setLayout(layout);
		GridData gd = new GridData(SWT.FILL, SWT.END, true, true, 1, 1);
		gd.minimumWidth = 600;
		inComposite.setLayoutData(gd);


		//
		
		Composite terminoCompositeColor = new Composite(inComposite, SWT.NONE);
		layout = new GridLayout();
		terminoCompositeColor.setLayout(layout);
		terminoCompositeColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		terminoCompositeColor.setBackground(terminologicColor);
		
		Composite terminoComposite = new Composite(terminoCompositeColor, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		terminoComposite.setLayout(layout);
		terminoComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		new Label(terminoComposite, SWT.NONE).setText(Messages.getString("TerminoConceptMappingDialog.4")); //$NON-NLS-1$

		termLabel = new Text(terminoComposite, SWT.SINGLE | SWT.BORDER);
		termLabel.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
		termLabel.setEditable(false);
		Color black = parent.getShell().getDisplay().getSystemColor(SWT.COLOR_BLACK);
		termLabel.setForeground(black);
		
		if(currentTerm != null) {
			termLabel.setText(currentTerm.getLabel());
		}
		GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		termLabel.setLayoutData(gridData);

		//
		
		new Label(inComposite, SWT.NONE);
		new Label(inComposite, SWT.NONE);

		Label titleBarSeparator = new Label(inComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label label = new Label(inComposite, SWT.NONE);
		label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
		label.setText(Messages.getString("TerminoConceptMappingDialog.7")); //$NON-NLS-1$
		gridData = new GridData(GridData.CENTER, GridData.CENTER, true, false, 2, 1);
		label.setLayoutData(gridData);
		
		Label titleBarSeparator2 = new Label(inComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		new Label(inComposite, SWT.NONE);
		new Label(inComposite, SWT.NONE);
		
		//
		
		Composite linkedConceptsCompositeColor = new Composite(inComposite, SWT.NONE);
		layout = new GridLayout();
		linkedConceptsCompositeColor.setLayout(layout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 350;
		linkedConceptsCompositeColor.setLayoutData(gd);
		linkedConceptsCompositeColor.setBackground(terminoOntologicColor);
		
		linkedConceptsComposite = new Composite(linkedConceptsCompositeColor, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		linkedConceptsComposite.setLayout(layout);
		linkedConceptsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label title = new Label(linkedConceptsComposite, SWT.NONE);
		title.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
		title.setText(Messages.getString("TerminoConceptMappingDialog.9")); //$NON-NLS-1$
		gd = new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 3, 1);
		title.setLayoutData(gd);

		Label titleBarSeparator3 = new Label(linkedConceptsComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		associatedConcepts = new TableViewer(linkedConceptsComposite, 
				SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		associatedConcepts.setContentProvider(new TerminoConceptContentProvider());
		associatedConcepts.getTable().setHeaderVisible(true);
		
		col = new TableViewerColumn(associatedConcepts, SWT.NONE);
		col.getColumn().setText(Messages.getString("TerminoConceptMappingDialog.10")); //$NON-NLS-1$
		//col.getColumn().setResizable(false);
		col.setLabelProvider(new TerminoConceptLabelProvider());
		
		col.getColumn().addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				setSorter();

			}
		});

		inComposite.addControlListener(new ControlAdapter() {
		    public void controlResized(ControlEvent e) {
		      Rectangle area = inComposite.getClientArea();
		      Point preferredSize = associatedConcepts.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		      int width = area.width - 2 * associatedConcepts.getTable().getBorderWidth();
		      
		      if (preferredSize.y > area.height + associatedConcepts.getTable().getHeaderHeight()) {
		        Point vBarSize = associatedConcepts.getTable().getVerticalBar().getSize();
		        width -= vBarSize.x;
		      }
		      
		      Point oldSize = associatedConcepts.getTable().getSize();
		      
		      if ((oldSize.x) > area.width) {
		    	  col.getColumn().setWidth(width - 10);
		    	  associatedConcepts.getTable().setSize(width, area.height);
		      } else {
		    	  associatedConcepts.getTable().setSize(width, area.height);
		    	  col.getColumn().setWidth(width - 10);
		    	  
		      }
		    }
		  });

		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;

		associatedConcepts.getTable().setLinesVisible(true);
		associatedConcepts.getTable().setLayoutData(gridData);
		

		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		DropTarget dropTarget = new DropTarget(this.associatedConcepts.getTable(), DND.DROP_MOVE | DND.DROP_DEFAULT);
	    dropTarget.setTransfer(types); 
	    
	    dropTarget.addDropListener(new DropTargetAdapter() {
			
			public void dragEnter(DropTargetEvent event) {
				
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = (event.operations & DND.DROP_MOVE) != 0 ? DND.DROP_MOVE : DND.DROP_NONE;
				}

				for (int i = 0, n = event.dataTypes.length; i < n; i++) {
					if (TextTransfer.getInstance().isSupportedType(event.dataTypes[i])) {
						event.currentDataType = event.dataTypes[i];
					}
				}
			}

			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				
				if (event.item != null) {
					TableItem item = (TableItem) event.item;
					event.feedback |= DND.FEEDBACK_SELECT;

				}

			}
			
			public void drop(DropTargetEvent event) {
				ITerminoConcept tc;
				
				tc = terminoConcepts.getCurrentTerminoConcept();
				
				if (! tc.getMappedTerms().contains(currentTerm)) {
				
					org.dafoe.terminoontologiclevel.common.DatabaseAdapter.linkTCWithTerm(tc, currentTerm);
				
					updateContent();
					
					initTerminoConceptOccurrences(currentTerm, tc);

					terminoConcepts.updateContent();
					
					terminoConcepts.expandAll();
					
				} else {
		
					String msg = Messages.getString("TerminoConceptMappingDialog.11") + tc.getLabel() + Messages.getString("TerminoConceptMappingDialog.12"); //$NON-NLS-1$ //$NON-NLS-2$
					msg += "\n\n"; //$NON-NLS-1$
					msg += Messages.getString("TerminoConceptMappingDialog.14"); //$NON-NLS-1$
					
					MessageDialog.openWarning(shell, Messages.getString("TerminoConceptMappingDialog.15"), msg); //$NON-NLS-1$
					
				}
			}				

		});

		new Label(linkedConceptsComposite, SWT.NONE).setText(Messages.getString("TerminoConceptMappingDialog.16")); //$NON-NLS-1$
		
		conceptLabel = new Text(linkedConceptsComposite, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		conceptLabel.setLayoutData(gridData);
		
		Button newTerminoConceptButton = new Button(linkedConceptsComposite, SWT.PUSH);
		gridData = new GridData(GridData.END, GridData.CENTER, false, false);
		String imageId = org.dafoe.terminologiclevel.Activator.NEW_IMG_ID;
		ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry().getDescriptor(imageId);
		Image image = imgDesc.createImage();
		newTerminoConceptButton.setImage(image);
		newTerminoConceptButton.setLayoutData(gridData);
		
		newTerminoConceptButton.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
				String label = conceptLabel.getText().trim();
				
				if(label.compareTo("") != 0){ //$NON-NLS-1$
					
					ITerminoConcept testTc = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.getTerminoConceptByLabel(label);
					
					// there isn't another TC with the same label
					if (testTc == null) {
					
						ITerminoConcept tc = org.dafoe.terminoontologiclevel.common
							.DatabaseAdapter.createTerminoConcept(label, currentTerm);
					
						updateContent();
					
						initTerminoConceptOccurrences(currentTerm, tc);
						
						TERMINO_OBJECT_STATE status = TERMINO_OBJECT_STATE.CONCEPTUALIZED;
						
						List<ITerm> terms = new ArrayList<ITerm>();
						terms.add(currentTerm);
						
						DatabaseAdapter.updateTermsStatus(terms, status);
						
						ControlerFactoryImpl.getTerminologyControler().setNewStatus();	

						conceptLabel.setText(""); //$NON-NLS-1$
						
						terminoConcepts.updateContent();
						
						terminoConcepts.expandAll();
						
					} else {
						
						String msg = Messages.getString("TerminoConceptMappingDialog.19") + label + Messages.getString("TerminoConceptMappingDialog.20"); //$NON-NLS-1$ //$NON-NLS-2$
						msg += "\n\n"; //$NON-NLS-1$
						msg += Messages.getString("TerminoConceptMappingDialog.22"); //$NON-NLS-1$
						
						MessageDialog.openWarning(TerminoConceptMappingDialog.this.getShell(), Messages.getString("TerminoConceptMappingDialog.23"), msg); //$NON-NLS-1$
						
						conceptLabel.setFocus();
						conceptLabel.selectAll();
					}
				} 
				
			}

			public void mouseUp(MouseEvent e) {
			}
			
		});
				
		
		
		
		terminoConcepts = new TCWidget(inComposite, SWT.NONE, Messages.getString("TerminoConceptMappingDialog.24"), currentTerm); //$NON-NLS-1$
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 250;
		terminoConcepts.setLayoutData(gd);

		makeActions();
		hookContextMenu();
		
		updateContent();

		return container;
	}

	//
	
	public void updateContent(){
		
		tcs = DatabaseAdapter.getTerminoConceptFromTerm(currentTerm);
		associatedConcepts.setInput(tcs);
		
	}
	
	//

	public void initTerminoConceptOccurrences(ITerm term, ITerminoConcept tc){
		DatabaseAdapter.initTerminoConceptOccurrences(term, tc);
	}
	
	//
	

	public void setSorter() {
		
		associatedConcepts.setSorter(new TerminoConceptSorter());
		associatedConcepts.getTable().setSortColumn(col.getColumn());
		associatedConcepts.getTable().setSortDirection(TerminoConceptAreaViewPart.getDirection());
		
	}

	//
	
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		
		okButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				viewPart.updateInformationArea();
			}
			
		});
		
		
		Button cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL,
				true);
		
	}

	//
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				TerminoConceptMappingDialog.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(associatedConcepts.getControl());
		associatedConcepts.getControl().setMenu(menu);
		viewPart.getSite().registerContextMenu(menuMgr, associatedConcepts);
	}

	//
	
	private void fillContextMenu(IMenuManager manager) {
		Action act = this.unlinkAction;
		act.setText(Messages.getString("TerminoConceptMappingDialog.25"));  //$NON-NLS-1$
		manager.add(act);
	}
	
	//
	
	private void makeActions(){
		
		unlinkAction = new Action()	{
			public void run() {

				TableItem[] sel = associatedConcepts.getTable().getSelection();

				if (sel.length != 0) {
					List<ITerminoConcept> selection = new ArrayList<ITerminoConcept>();
					ITerminoConcept tc;
					
					for (int i = 0; i < sel.length; i++) {
						tc = (ITerminoConcept)(sel[i].getData());
						selection.add(tc);
					}
					
					org.dafoe.terminoontologiclevel.common.DatabaseAdapter.unlinkTerm(selection, currentTerm);
															
					updateContent();
					
					associatedConcepts.refresh();
					
					terminoConcepts.updateContent();
					
					terminoConcepts.expandAll();
					
					// No more TCs associated to the current terms => change status
					if (tcs.size() == 0) {					
					
						TERMINO_OBJECT_STATE status = TERMINO_OBJECT_STATE.VALIDATED;

						List<ITerm> terms = new ArrayList<ITerm>();
						terms.add(currentTerm);
						DatabaseAdapter.updateTermsStatus(terms, status);

						ControlerFactoryImpl.getTerminologyControler().setNewStatus();	

					}
				}
				
			}
		};
		
		ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.getDescriptor(org.dafoe.terminologiclevel.Activator.SUPP_VARIANT_IMG_ID);
		this.unlinkAction.setImageDescriptor(imgDesc);
		this.unlinkAction.setEnabled(true);
		this.unlinkAction.setToolTipText("Unlink TC"); //$NON-NLS-1$
	}
	
	//
	
	class TerminoConceptContentProvider implements IStructuredContentProvider {

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
	
	//
	
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

}
