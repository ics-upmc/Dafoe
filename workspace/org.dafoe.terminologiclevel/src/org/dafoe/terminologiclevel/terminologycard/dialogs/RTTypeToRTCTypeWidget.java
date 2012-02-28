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
import org.dafoe.terminoontologiclevel.common.DatabaseAdapter;
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

public class RTTypeToRTCTypeWidget extends Composite {

	private ListViewer rtcTypeListViewer ;
	private Text newRTCTypeText;
	private Text currentRelationTypeText;
	
	private DropTarget dropTarget;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private Shell shell;
	
	private Image oldImage;
	private Color terminologic_level_color; 
	private Color terminoontologic_level_color; 
	
	private ITypeRelationTermino currentRTType;
	private ITypeRelationTc currentRTCType;
	
	private String rtcTypeSelectionEventType, newRTCEventType, unlinkRTCEventType;
	
	private Action unlinkAction;
	
	public RTTypeToRTCTypeWidget(Composite parent, int style) {
		super(parent, style);
		
		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		terminologic_level_color = themeManager.getTheme("org.dafoe.terminologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
		terminoontologic_level_color = themeManager.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setLayout(new GridLayout());
		
		GridData gd;
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		this.setLayoutData(gd);
		
		shell = parent.getShell();

		createContent();
	}
	
	private void createContent(){
	
		GridData gridData;

		Composite composite = new Composite(this, SWT.NONE);
    	gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
    	gridData.widthHint = 300;
    	composite.setLayoutData(gridData);
    	composite.setLayout(new GridLayout(3, false));

		FontRegistry fontRegistry = new FontRegistry(shell.getDisplay());
		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

    	Label label = new Label(composite, SWT.NONE);
    	label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
    	label.setText(Messages.getString("RTTypeToRTCTypeWidget.3")); //$NON-NLS-1$
    	gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1);
    	label.setLayoutData(gridData);

		Label titleBarSeparator = new Label(composite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Label currentRelationTypeLabel = new Label(composite, SWT.NONE); 
    	currentRelationTypeLabel.setText(Messages.getString("RTTypeToRTCTypeWidget.4") + " :"); //$NON-NLS-1$ //$NON-NLS-2$

    	currentRelationTypeText = new Text(composite, SWT.SINGLE | SWT.BORDER);
    	currentRelationTypeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

    	Label associatedConceptsLabel = new Label(composite, SWT.NONE);
    	associatedConceptsLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 1, 2));
    	associatedConceptsLabel.setText(Messages.getString("RTTypeToRTCTypeWidget.6") + " :"); //$NON-NLS-1$ //$NON-NLS-2$

    	rtcTypeListViewer = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL);
    	rtcTypeListViewer.setContentProvider(new RTCTypeContentProvider());
    	rtcTypeListViewer.setLabelProvider(new RTCLabelProvider());
    	gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
    	gridData.heightHint = 100;
    	rtcTypeListViewer.getList().setLayoutData(gridData);

    	rtcTypeListViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {

				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				
				if (selection.getFirstElement() != null) {
					
					unlinkAction.setEnabled(true);
					
					currentRTCType = (ITypeRelationTc)selection.getFirstElement();

					propertyChangeSupport.firePropertyChange(rtcTypeSelectionEventType, null, currentRTCType);
				
				} else {
					
					unlinkAction.setEnabled(false);
					
				}
					
			}
    		
    	});

		
		Label newRTCTypeLabel = new Label(composite, SWT.NONE); 
    	gridData = new GridData();
    	gridData.verticalAlignment = SWT.CENTER;
    	newRTCTypeLabel.setLayoutData(gridData);
    	newRTCTypeLabel.setText(Messages.getString("RTTypeToRTCTypeWidget.8") + " :"); //$NON-NLS-1$ //$NON-NLS-2$

    	newRTCTypeText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.WRAP);
    	gridData = new GridData();
    	gridData.horizontalAlignment = SWT.FILL;
    	gridData.widthHint = 150;
    	gridData.grabExcessHorizontalSpace = true;
    	newRTCTypeText.setLayoutData(gridData);

    	Button newRTCTypeButton = new Button(composite, SWT.NONE);
 		String imageId = org.dafoe.terminologiclevel.Activator.NEW_IMG_ID;
		ImageDescriptor imgDesc = org.dafoe.terminologiclevel.Activator.getDefault().getImageRegistry().getDescriptor(imageId);
		Image imageAdd = imgDesc.createImage();
		newRTCTypeButton.setImage(imageAdd);
    	newRTCTypeButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
    	
    	newRTCTypeButton.addSelectionListener(new SelectionAdapter(){
    		
    		public void widgetSelected(SelectionEvent e){
				String newRTCTypeLabel = newRTCTypeText.getText().trim();
				
				if (newRTCTypeLabel.compareTo("") != 0){ //$NON-NLS-1$
					
					ITypeRelationTc testRTCType = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.getRTCTypeByLabel(newRTCTypeLabel);
			
					// there isn't another RTC type with the same label
					if (testRTCType == null) {
						
						ITypeRelationTc newRTCType = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.createRTCType(newRTCTypeLabel, null);
						
						org.dafoe.terminoontologiclevel.common.DatabaseAdapter.linkRTCTypeWithRTType(newRTCType, currentRTType);
						
						newRTCTypeText.setText(""); //$NON-NLS-1$
						
						updateContent(currentRTType);
						
						propertyChangeSupport.firePropertyChange(newRTCEventType, null, newRTCType);
						
					} else {
						
						String msg = Messages.getString("RTTypeToRTCTypeWidget.12") + newRTCTypeLabel + Messages.getString("RTTypeToRTCTypeWidget.13"); //$NON-NLS-1$ //$NON-NLS-2$
						msg += Messages.getString("RTTypeToRTCTypeWidget.14"); //$NON-NLS-1$
						
						MessageDialog.openWarning(shell, Messages.getString("RTTypeToRTCTypeWidget.15"), msg); //$NON-NLS-1$
						
						newRTCTypeText.selectAll();
						
					}
					
				}
   			
    		}
    	});
    	
		composite.addListener (SWT.Resize, new Listener () {
			public void handleEvent (Event event) {
				Rectangle rect = RTTypeToRTCTypeWidget.this.getClientArea();
				Image newImage = new Image (shell.getDisplay(), Math.max (1, rect.width), 1);	
				GC gc = new GC (newImage);
				gc.setForeground (terminologic_level_color);
				gc.setBackground (terminoontologic_level_color);
				gc.fillGradientRectangle (rect.x, rect.y, rect.width, 1, false);
				gc.dispose ();
				RTTypeToRTCTypeWidget.this.setBackgroundImage (newImage);
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
	
	public void createDropTarget(){
		if (dropTarget != null){
			dropTarget.dispose();
		}
		
	    dropTarget = new DropTarget(this.rtcTypeListViewer.getList(), DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);
	    
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
				
				if (currentRTType != null) {
					if (TextTransfer.getInstance().isSupportedType(event.currentDataType)) {
						// Get the dropped data
						// DropTarget target = (DropTarget) event.widget;
						int data = Integer.parseInt((String) event.data);

						ITypeRelationTc rtcType = DatabaseAdapter.getRTCTypeById(data);

						if (!currentRTType.getMappedTcRelationTypes().contains(rtcType)){

							DatabaseAdapter.linkRTCTypeWithRTType(rtcType, currentRTType);
							
							RTTypeToRTCTypeWidget.this.updateContent(currentRTType);

							rtcTypeListViewer.setSelection(new StructuredSelection(rtcType));
							
						} else {

							String msg = Messages.getString("RTTypeToRTCTypeWidget.16"); //$NON-NLS-1$

							MessageDialog.openWarning(shell, Messages.getString("RTTypeToRTCTypeWidget.17"), msg); //$NON-NLS-1$
						}
					}
					
				} else {
					
					String msg = Messages.getString("RTTypeToRTCTypeWidget.18"); //$NON-NLS-1$
					msg += Messages.getString("RTTypeToRTCTypeWidget.19"); //$NON-NLS-1$
					MessageDialog.openWarning(shell, Messages.getString("RTTypeToRTCTypeWidget.20"), msg); //$NON-NLS-1$
					
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
	
	public void setRTCTypeSelectionEventType(String eventType){
		rtcTypeSelectionEventType = eventType;
	}
	
	//
	
	public void setNewRTCTypeEventType(String eventType){
		newRTCEventType = eventType;
	}

	//
	
	public void setUnlinkRTCTypeEventType(String eventType){
		unlinkRTCEventType = eventType;
	}

	//
	
	class RTCTypeContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			return ((List<ITypeRelationTc>) inputElement).toArray();
		}

		public void dispose() {			
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
		
	}
	//
	
	class RTCLabelProvider extends LabelProvider {
		
		public String getText(Object element) {
			if (element instanceof ITypeRelationTc) {
				return ((ITypeRelationTc)element).getName();
			} else {
				return ""; //$NON-NLS-1$
			}
		}
	}
	
	//
	
	public void updateContent(ITypeRelationTermino rtType) {
		List<ITypeRelationTc> linkedRTCTypes = new ArrayList<ITypeRelationTc>();
		String rtLabel = ""; //$NON-NLS-1$
		
		this.currentRTType = rtType;
		
		if (currentRTType != null) {
			
			rtLabel = currentRTType.getLabel();
		
			linkedRTCTypes = UtilTools.setToList(currentRTType.getMappedTcRelationTypes());
		
		}
		
		this.currentRelationTypeText.setText(rtLabel);
		
		this.rtcTypeListViewer.setInput(linkedRTCTypes);
		
		this.newRTCTypeText.setText(""); //$NON-NLS-1$
	}

	//
	
	private void makeActions(){
		
		unlinkAction = new Action(){
			
			public void run(){
				
				DatabaseAdapter.unlinkRTType(currentRTCType, currentRTType);
				
				unlinkAction.setEnabled(false);
				
				updateContent(currentRTType);
				
				propertyChangeSupport.firePropertyChange(unlinkRTCEventType, true, false);
				
			}
		};	
	}
	
	//
	
	private void hookPopupMenu(){
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(rtcTypeListViewer.getList());
		rtcTypeListViewer.getControl().setMenu(menu);
		
		Action act = this.unlinkAction;
		act.setText(Messages.getString("RTTypeToRTCTypeWidget.24")); //$NON-NLS-1$
		act.setEnabled(false);
		menuMgr.add(act);
		
	}


}
