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
import java.util.ArrayList;
import java.util.List;

import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class RTCTypeToRTTypeWidget extends Composite {

	private Shell shell;
	
	private DropTarget dropTarget;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private ListViewer rtTypeListViewer;
	private Text currentTerminoRelationTypeText;
	private Text newRTTypeText;
	
	private Image oldImage;
	private Color terminologic_level_color; 
	private Color terminoontologic_level_color; 

	private ITypeRelationTc currentRTCType;
	private ITypeRelationTermino currentRTType;

	private String rtTypeSelectionEventType, newRTEventType, unlinkRTEventType;

	private Action unlinkAction;
	
	public RTCTypeToRTTypeWidget(Composite parent, int style) {
		super(parent, style);
		
		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		terminologic_level_color = themeManager.getTheme("org.dafoe.terminologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
		terminoontologic_level_color = themeManager.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setLayout(new GridLayout());
		
		GridData gd;
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		this.setLayoutData(gd);
		
		shell = this.getShell();

		createContent();
	}

	//
	
	private void createContent(){
		GridData gridData;

		Composite composite = new Composite(this, SWT.NONE);
    	composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    	composite.setLayout(new GridLayout(3, false));
   	
		FontRegistry fontRegistry = new FontRegistry(shell.getDisplay());
		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$
		
    	Label label = new Label(composite, SWT.NONE);
    	label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
    	label.setText(Messages.getString("RTCTypeToRTTypeWidget.3")); //$NON-NLS-1$
    	gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1);
    	label.setLayoutData(gridData);
    	
		Label titleBarSeparator = new Label(composite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Label currentTerminoRelationTypeLabel = new Label(composite, SWT.NONE); 
    	currentTerminoRelationTypeLabel.setText(Messages.getString("RTCTypeToRTTypeWidget.4") + " :"); //$NON-NLS-1$ //$NON-NLS-2$

    	currentTerminoRelationTypeText = new Text(composite, SWT.SINGLE | SWT.BORDER);
    	currentTerminoRelationTypeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

    	Label associatedTypeLabel = new Label(composite, SWT.NONE);
    	associatedTypeLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 1, 2));
    	associatedTypeLabel.setText(Messages.getString("RTCTypeToRTTypeWidget.6") + " :"); //$NON-NLS-1$ //$NON-NLS-2$

    	rtTypeListViewer = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL);
    	rtTypeListViewer.setContentProvider(new RTTypeContentProvider());
    	rtTypeListViewer.setLabelProvider(new RTLabelProvider());
    	gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
    	gridData.heightHint = 100;
    	rtTypeListViewer.getList().setLayoutData(gridData);

    	rtTypeListViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				
				if (selection.getFirstElement() != null ) {
				
					unlinkAction.setEnabled(true);

					currentRTType = (ITypeRelationTermino)selection.getFirstElement();

					propertyChangeSupport.firePropertyChange(rtTypeSelectionEventType, null, currentRTType);

				} else {
					
					unlinkAction.setEnabled(false);
					
				}
			}
    	});
    	  	
    	Label newRTTypeLabel = new Label(composite, SWT.NONE); 
    	gridData = new GridData();
    	gridData.verticalAlignment = SWT.CENTER;
    	newRTTypeLabel.setLayoutData(gridData);
    	newRTTypeLabel.setText(Messages.getString("RTCTypeToRTTypeWidget.8") + " :"); //$NON-NLS-1$ //$NON-NLS-2$

    	newRTTypeText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.WRAP);
    	gridData = new GridData();
    	gridData.horizontalAlignment = SWT.FILL;
    	gridData.widthHint = 150;
    	gridData.grabExcessHorizontalSpace = true;
    	newRTTypeText.setLayoutData(gridData);

    	Button newRTTypeButton = new Button(composite, SWT.NONE);
 		String imageId = org.dafoe.terminologiclevel.Activator.NEW_IMG_ID;
		ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry().getDescriptor(imageId);
		Image imageAdd = imgDesc.createImage();
		newRTTypeButton.setImage(imageAdd);
		newRTTypeButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

		newRTTypeButton.addSelectionListener(new SelectionAdapter(){
			
			public void widgetSelected(SelectionEvent e){
				
				String newRTTypeLabel = newRTTypeText.getText().trim();;
				
				if (newRTTypeLabel.compareTo("") != 0){ //$NON-NLS-1$
				
					ITypeRelationTermino testRTType = DatabaseAdapter.getRTTypeByLabel(newRTTypeLabel);
				
					// there isn't another RT type with the same label
					if (testRTType == null) {
						
						ITypeRelationTermino newRTType = DatabaseAdapter.createRelationType(newRTTypeLabel);
						
						org.dafoe.terminoontologiclevel.common.DatabaseAdapter.linkRTCTypeWithRTType(currentRTCType, newRTType);
						
						newRTTypeText.setText(""); //$NON-NLS-1$
						
						updateContent(currentRTCType);
						
						propertyChangeSupport.firePropertyChange(newRTEventType, null, newRTType);

					} else {
						
						String msg = Messages.getString("RTCTypeToRTTypeWidget.12") + newRTTypeLabel + Messages.getString("RTCTypeToRTTypeWidget.13"); //$NON-NLS-1$ //$NON-NLS-2$
						msg += Messages.getString("RTCTypeToRTTypeWidget.14"); //$NON-NLS-1$
						
						MessageDialog.openWarning(shell, Messages.getString("RTCTypeToRTTypeWidget.15"), msg); //$NON-NLS-1$
						
						newRTTypeText.selectAll();

					}
					
				}
			}
			
		});
		
		
		composite.addListener (SWT.Resize, new Listener () {
			public void handleEvent (Event event) {
				Rectangle rect = RTCTypeToRTTypeWidget.this.getClientArea();
				Image newImage = new Image (shell.getDisplay(), Math.max (1, rect.width), 1);	
				GC gc = new GC (newImage);
				gc.setForeground (terminologic_level_color);
				gc.setBackground (terminoontologic_level_color);
				gc.fillGradientRectangle (rect.x, rect.y, rect.width, 1, false);
				gc.dispose ();
				RTCTypeToRTTypeWidget.this.setBackgroundImage (newImage);
				if (oldImage != null) {
					oldImage.dispose ();
				}
				oldImage = newImage;
			}
		});
		
		makeActions();
		
		hookPopupMenu();

	}
	
	//
	
	public void createDropTarget() {
		
		if (dropTarget != null) {
			dropTarget.dispose();
		}
		dropTarget = new DropTarget(this.rtTypeListViewer.getList(), DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);

		final TextTransfer textTransfer = TextTransfer.getInstance(); 
		Transfer[] types = new Transfer[] {textTransfer}; 
		dropTarget.setTransfer(types); 

		dropTarget.addDropListener(new DropTargetAdapter() {

			public void dragEnter(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = (event.operations & DND.DROP_COPY) != 0 ? DND.DROP_COPY : DND.DROP_NONE;
				}

				for (int i = 0, n = event.dataTypes.length; i < n; i++) {
					if (TextTransfer.getInstance().isSupportedType(event.dataTypes[i])) {
						event.currentDataType = event.dataTypes[i];
					}
				}
			}

			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
			}

			public void drop(DropTargetEvent event) {

				if (currentRTCType != null) {
					if (TextTransfer.getInstance().isSupportedType(event.currentDataType)) {
						// Get the dropped data
						//DropTarget target = (DropTarget) event.widget;
						
						int data = Integer.parseInt((String) event.data);

						ITypeRelationTermino rtType = DatabaseAdapter.getRTTypeById(data);

						if (!currentRTCType.getMappedTermRelationTypes().contains(rtType)){

							org.dafoe.terminoontologiclevel.common.DatabaseAdapter.linkRTCTypeWithRTType(currentRTCType, rtType);

							RTCTypeToRTTypeWidget.this.updateContent(currentRTCType);

							rtTypeListViewer.setSelection(new StructuredSelection(rtType));

						} else {

							String msg = Messages.getString("RTCTypeToRTTypeWidget.16"); //$NON-NLS-1$

							MessageDialog.openWarning(shell, Messages.getString("RTCTypeToRTTypeWidget.17"), msg); //$NON-NLS-1$
						}
					}

				} else {

					String msg = Messages.getString("RTCTypeToRTTypeWidget.18"); //$NON-NLS-1$
					msg += Messages.getString("RTCTypeToRTTypeWidget.19"); //$NON-NLS-1$
					MessageDialog.openWarning(shell, Messages.getString("RTCTypeToRTTypeWidget.20"), msg); //$NON-NLS-1$

				}
			}				

		});


	}
	
	//
	
	public void removeDropTarget(){
		if (dropTarget != null) {
			dropTarget.dispose();
		}
		dropTarget = null;
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
	
	public void setRTTypeSelectionEventType(String eventType){
		rtTypeSelectionEventType = eventType;
	}
	
	//
	
	public void setNewRTTypeEventType(String eventType){
		newRTEventType = eventType;
	}

	//
	
	public void setUnlinkRTTypeEventType(String eventType){
		unlinkRTEventType = eventType;
	}

	//
	
	class RTTypeContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			return ((List<ITypeRelationTermino>) inputElement).toArray();
		}

		public void dispose() {			
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
		
	}
	
	//
	
	class RTLabelProvider extends LabelProvider {
		
		public String getText(Object element) {
			if (element instanceof ITypeRelationTermino) {
				return ((ITypeRelationTermino)element).getLabel();
			} else {
				return ""; //$NON-NLS-1$
			}
		}
	}
	
	//
	
	public void updateContent(ITypeRelationTc rtcType) {
		List<ITypeRelationTermino> linkedRTTypes = new ArrayList<ITypeRelationTermino>();
		String rtcTypeName = ""; //$NON-NLS-1$
		
		this.currentRTCType = rtcType;
		
		if (currentRTCType != null) {
			
			rtcTypeName = currentRTCType.getName();
			
			linkedRTTypes = UtilTools.setToList(currentRTCType.getMappedTermRelationTypes());
			
		}
		
		this.currentTerminoRelationTypeText.setText(rtcTypeName);
		
		this.rtTypeListViewer.setInput(linkedRTTypes);
		
		this.newRTTypeText.setText(""); //$NON-NLS-1$
	}

	private void makeActions(){
		
		unlinkAction = new Action(){
			
			public void run(){
				
				org.dafoe.terminoontologiclevel.common.DatabaseAdapter.unlinkRTType(currentRTCType, currentRTType);
				
				updateContent(currentRTCType);
				
				unlinkAction.setEnabled(false);
				
				propertyChangeSupport.firePropertyChange(unlinkRTEventType, true, false);
				
			}
		};	
	}
	
	//
	
	private void hookPopupMenu(){
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(rtTypeListViewer.getList());
		rtTypeListViewer.getControl().setMenu(menu);
		
		Action act = this.unlinkAction;
		act.setText(Messages.getString("RTCTypeToRTTypeWidget.24")); //$NON-NLS-1$
		act.setEnabled(false);
		menuMgr.add(act);
		
	}


}
