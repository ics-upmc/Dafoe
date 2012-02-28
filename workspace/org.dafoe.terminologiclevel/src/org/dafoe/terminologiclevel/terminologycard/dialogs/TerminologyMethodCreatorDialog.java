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

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.IMethod;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class TerminologyMethodCreatorDialog extends TitleAreaDialog {
	private static String DIALOG_TITLE = Messages.getString("TerminologyMethodCreatorDialog.0"); //$NON-NLS-1$
	private static String DIALOG_MESSAGE = Messages.getString("TerminologyMethodCreatorDialog.1"); //$NON-NLS-1$
	private static String TOOL_COL_NAME = Messages.getString("TerminologyMethodCreatorDialog.2"); //$NON-NLS-1$
		
	private Composite generalComposite;
	private TableViewer methodViewer;
	private TableViewerColumn patternColumn;
	private TableViewerColumn toolColumn;
	private Button addMethodButton;
	private Button removeMethodButton;
	
	private CellEditor[] cellEditors;
	private String title;
	private String message;
	private List<IMethod> methods;
	private IMethod currentMethod;
	
	private boolean methodRemoved;
	
	public TerminologyMethodCreatorDialog(Shell parent){
		super(parent);
		setShellStyle(SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE);
		this.title = DIALOG_TITLE;
		this.message = DIALOG_MESSAGE;
		this.setTitleImage(org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry()
				.get(org.dafoe.terminologiclevel.Activator.DIALOG_IMG));
		methodRemoved = false;
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

    protected Control createDialogArea(Composite parent) {
 
    	
		generalComposite = (Composite) super.createDialogArea(parent);
		
		Composite composite = new Composite(generalComposite, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(gd);
		composite.setLayout(new GridLayout(2, false));
		
		
		methodViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = methodViewer.getTable();
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd.minimumHeight = 200;
		gd.minimumWidth = 300;
		table.setLayoutData(gd);
		methodViewer.getTable().setLinesVisible(true);
		methodViewer.getTable().setHeaderVisible(true);
		methodViewer.setContentProvider(new MethodTableContentProvider());
		
		toolColumn = new TableViewerColumn(methodViewer, SWT.NONE);
		
		toolColumn.getColumn().setText(Messages.getString("TerminologyMethodCreatorDialog.3")); //$NON-NLS-1$
		toolColumn.getColumn().setWidth(150);
		toolColumn.getColumn().setResizable(false);
		
		toolColumn.setLabelProvider(new MethodLabelProvider());
		
		cellEditors = new CellEditor[1];
		cellEditors[0] = new TextCellEditor(methodViewer.getTable());
		
		methodViewer.setColumnProperties(new String[]{TOOL_COL_NAME});
		
		methodViewer.setCellEditors(cellEditors);
		methodViewer.setCellModifier(new myCellMofidier());
		
		addMethodButton = new Button(composite, SWT.NONE);
		addMethodButton.addListener(SWT.Selection, new AddButtonListener());
		String imageId = org.dafoe.terminologiclevel.Activator.NEW_IMG_ID;
		ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry().getDescriptor(imageId);
		Image imageAdd = imgDesc.createImage();
		addMethodButton.setImage(imageAdd);
		addMethodButton.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));


		removeMethodButton = new Button(composite, SWT.NONE);
		removeMethodButton.addListener(SWT.Selection, new RemoveButtonListener());
		imageId = org.dafoe.terminologiclevel.Activator.REMOVE_RELATION_IMG_ID;
		imgDesc = org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry().getDescriptor(imageId);
		Image imageRemove = imgDesc.createImage();
		removeMethodButton.setImage(imageRemove);
		removeMethodButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		
		methodViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				
				TableItem[] sel = methodViewer.getTable().getSelection();

				if (sel.length != 0) {
					
					currentMethod = (IMethod) (sel[0].getData());
						
					ControlerFactoryImpl.getTerminologyControler().setMethod(currentMethod);
				}
				
			}
		});
		
		
		methodViewer.setInput(DatabaseAdapter.getMethods());
		
		generalComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = generalComposite.getClientArea();
				Point preferredSize = methodViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * methodViewer.getTable().getBorderWidth();
			
				if (preferredSize.y > area.height + methodViewer.getTable().getHeaderHeight()) {

					Point vBarSize = methodViewer.getTable().getVerticalBar().getSize();
					width -= vBarSize.x;

				}

				Point oldSize = methodViewer.getTable().getSize();

				if (oldSize.x > area.width) {

					patternColumn.getColumn().setWidth((width * 50 / 100) - 5);
					toolColumn.getColumn().setWidth(width - 5);
				} else {

					methodViewer.getTable().setSize(area.width, area.height);
					toolColumn.getColumn().setWidth(width - 5);
				}
			}
		});
		
		return composite;

    }

    public List<IMethod> createData(){
    	methods = DatabaseAdapter.getMethods();
    	return methods;
    }
    
	public void createButtonsForButtonBar(Composite parent){
		this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	public IMethod getSelection(){
		return this.currentMethod;
	}

	
	
	class MethodTableContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			return ((List<IMethod>) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
			
		
	}
	
	class MethodLabelProvider extends ColumnLabelProvider {

		public MethodLabelProvider() {
			super();
		}

		public String getText(Object element) {
			IMethod method = (IMethod) element;

			return method.getTool();

		}
	}

	
	class myCellMofidier implements ICellModifier{
		
		public boolean canModify(Object element, String property) {
			return true;
		}

		public Object getValue(Object element, String property) {
			IMethod method = (IMethod) element;
					
			return method.getTool();
			
		}

		public void modify(Object element, String property, Object value) {
			TableItem currentItem = (TableItem)element;
			IMethod currentMethod = (IMethod)currentItem.getData();

			currentMethod.setTool((String)value);
			DatabaseAdapter.updateMethod(currentMethod, (String)value, null);
			
			methodViewer.refresh(currentMethod);

		}
		
	}
	
	// 
	
	public void executeCommand(String cmd) throws Exception {

		IHandlerService handlerservice = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);
		
		handlerservice.executeCommand(cmd, null);
	}
	
	//
	
	class AddButtonListener implements Listener {

		public void handleEvent(Event event) {
			
			try {
				executeCommand("org.dafoe.terminologiclevel.saillance.commands.NewMethod"); //$NON-NLS-1$
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			methodViewer.setInput(DatabaseAdapter.getMethods());
		
		}
	}
	
	//
	
	class RemoveButtonListener implements Listener {

		public void handleEvent(Event event) {

			try {
				executeCommand("org.dafoe.terminologiclevel.saillance.commands.DeleteMethod"); //$NON-NLS-1$

				methodRemoved = true;
			} catch (Exception e) {
				e.printStackTrace();
			}

			methodViewer.setInput(DatabaseAdapter.getMethods());
		}
	}
	
	public boolean methodRemoved(){
		return methodRemoved;
	}

	//
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		
		shell.open();
		
		while (!shell.isDisposed()){
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}

}
