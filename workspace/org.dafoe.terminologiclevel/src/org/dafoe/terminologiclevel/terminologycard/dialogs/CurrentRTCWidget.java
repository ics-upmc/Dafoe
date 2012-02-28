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

import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class CurrentRTCWidget extends Composite {

	private Text tc1Text;
	private Text tc2Text;
	private Text rtcTypeText;
	private Button linkRTC;
	private Button unlinkRTC;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private BinaryTCRelation currentRTC;
	
	private String linkEventType;
	private String unlinkEventType;
	
	public CurrentRTCWidget(Composite parent, int style) {
		super(parent, style);
		
		this.setLayout(new GridLayout());

		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		final Color TERMINO_ONTOLOGIC_LEVEL_COLOR = themeManager.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setBackground(TERMINO_ONTOLOGIC_LEVEL_COLOR);

		currentRTC = new BinaryTCRelation();
		
		createContent();;
		
	}
	
	private void createContent(){
		
		GridData gd;

		Composite newRTCComposite = new Composite(this, SWT.NONE);
		newRTCComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));
		newRTCComposite.setLayout(new GridLayout(3, true));   	

		FontRegistry fontRegistry = new FontRegistry(this.getDisplay());
		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		Label label = new Label(newRTCComposite, SWT.NONE);
		label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
		label.setText(Messages.getString("CurrentRTCWidget.3")); //$NON-NLS-1$
		
		new Label(newRTCComposite, SWT.NONE);
		new Label(newRTCComposite, SWT.NONE);
		
		Label titleBarSeparator = new Label(newRTCComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Label tc1Label = new Label(newRTCComposite, SWT.NONE);
		tc1Label.setText(Messages.getString("CurrentRTCWidget.4") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		
		Label tcrTypeLabel = new Label(newRTCComposite, SWT.NONE);
		tcrTypeLabel.setText(Messages.getString("CurrentRTCWidget.6") + ":"); //$NON-NLS-1$ //$NON-NLS-2$

		Label tc2Label = new Label(newRTCComposite, SWT.NONE);
		tc2Label.setText(Messages.getString("CurrentRTCWidget.8") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		
		
		tc1Text = new Text(newRTCComposite, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.widthHint = 150;
		tc1Text.setLayoutData(gd);
		
		rtcTypeText = new Text(newRTCComposite, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.widthHint = 150;
		rtcTypeText.setLayoutData(gd);
		
		tc2Text = new Text(newRTCComposite, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.widthHint = 150;
		tc2Text.setLayoutData(gd);

		linkRTC = new Button(newRTCComposite, SWT.PUSH);
		linkRTC.setText(Messages.getString("CurrentRTCWidget.10")); //$NON-NLS-1$
		gd = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		linkRTC.setLayoutData(gd);
		linkRTC.setEnabled(false);

		linkRTC.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e){

				propertyChangeSupport.firePropertyChange(linkEventType, true, false);
				
			}
		});
	
		new Label(newRTCComposite, SWT.NONE);
		
		unlinkRTC = new Button(newRTCComposite, SWT.PUSH);
		unlinkRTC.setText(Messages.getString("CurrentRTCWidget.11")); //$NON-NLS-1$
		gd = new GridData(SWT.END, SWT.CENTER, false, false);
		unlinkRTC.setLayoutData(gd);
		unlinkRTC.setEnabled(false);
		
		unlinkRTC.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e){

				propertyChangeSupport.firePropertyChange(unlinkEventType, true, false);
				
			}
		});
	}

	public void setLinkEventType(String event){
		this.linkEventType = event;
	}
	
	public String getLinkEventType(){
		return this.linkEventType;
	}
	
	public void setUnlinkEventType(String event){
		this.unlinkEventType = event;
	}
	
	public String getUnlinkEventType(){
		return this.unlinkEventType;
	}
	
	public void setLinkEnabled(boolean enabled){
		this.linkRTC.setEnabled(enabled);
	}
	
	public void setUnlinkEnabled(boolean enabled){
		this.unlinkRTC.setEnabled(enabled);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}

	public Text getTC1Text(){
		return this.tc1Text;
	}

	public Text getTC2Text(){
		return this.tc2Text;
	}

	public Text getRTCTypeText(){
		return this.rtcTypeText;
	}

}