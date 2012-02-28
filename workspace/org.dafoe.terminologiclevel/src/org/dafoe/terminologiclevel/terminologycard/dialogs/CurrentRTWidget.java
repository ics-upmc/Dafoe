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

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
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

public class CurrentRTWidget extends Composite {

	private ITerm term1, term2;
	private ITypeRelationTermino typeRelation;
	
	private Text term1Text;
	private Text term2Text;
	private Text relationTypeText;
	
	public CurrentRTWidget(Composite parent, int style) {
		
		super(parent, style);

		this.setLayout(new GridLayout());

		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		
		final Color TERMINO_LEVEL_COLOR = themeManager.getTheme("org.dafoe.terminologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		this.setBackground(TERMINO_LEVEL_COLOR);

		createContent();
	}
	
	private void createContent(){
		
		GridData gd;

		Composite currentRTComposite = new Composite(this, SWT.NONE);
		currentRTComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));
		currentRTComposite.setLayout(new GridLayout(3, false));

		FontRegistry fontRegistry = new FontRegistry(this.getDisplay());
		fontRegistry.put("WidgetTitle", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		Label label = new Label(currentRTComposite, SWT.NONE);
		label.setFont(fontRegistry.get("WidgetTitle")); //$NON-NLS-1$
		label.setText(Messages.getString("CurrentRTWidget.3")); //$NON-NLS-1$
		
		new Label(currentRTComposite, SWT.NONE);
		new Label(currentRTComposite, SWT.NONE);
		
		Label titleBarSeparator = new Label(currentRTComposite, SWT.HORIZONTAL| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Label term1Label = new Label(currentRTComposite, SWT.NONE);
		term1Label.setText(Messages.getString("CurrentRTWidget.4") + ":"); //$NON-NLS-1$ //$NON-NLS-2$

		Label relationTypeLabel = new Label(currentRTComposite, SWT.NONE);
		relationTypeLabel.setText(Messages.getString("CurrentRTWidget.6") + ":"); //$NON-NLS-1$ //$NON-NLS-2$

		Label term2Label = new Label(currentRTComposite, SWT.NONE);
		term2Label.setText(Messages.getString("CurrentRTWidget.8") + ":"); //$NON-NLS-1$ //$NON-NLS-2$

		term1Text = new Text(currentRTComposite, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.widthHint = 150;
		term1Text.setLayoutData(gd);
		
		relationTypeText = new Text(currentRTComposite, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.widthHint = 150;
		relationTypeText.setLayoutData(gd);

		term2Text = new Text(currentRTComposite, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.widthHint = 150;
		term2Text.setLayoutData(gd);

		Button validateTR = new Button(currentRTComposite, SWT.PUSH);
		validateTR.setText(Messages.getString("CurrentRTWidget.10")); //$NON-NLS-1$
		gd = new GridData(SWT.END, SWT.CENTER, false, false, 3, 1);
		validateTR.setLayoutData(gd);
	
		updateInformation();
	}
	
	public void updateInformation(){
		
		ITermRelation relation = ControlerFactoryImpl.getTerminologyControler().getTermRelation();
		
		if (relation != null) {
			this.term1 = relation.getTerm1();
			this.term2 = relation.getTerm2();
			this.typeRelation = relation.getTypeRel();
		
			term1Text.setText(term1.getLabel());
			term2Text.setText(term2.getLabel());
			relationTypeText.setText(typeRelation.getLabel());
		}

	
		
	}
	
}
