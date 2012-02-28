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
import java.util.HashMap;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermSaillance;
import org.dafoe.terminologiclevel.terminologycard.dialogs.TerminologyInformationConfigurerDialog;
import org.dafoe.terminologiclevel.terminologycard.dialogs.models.TerminologyInformationModel;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class TerminologyInformationAreaViewPart extends ViewPart {
	public static final String ID = "org.dafoe.terminologiclevel.terminologycard.InformationAreaViewPartId"; //$NON-NLS-1$

	private TerminologyInformationModel data;
	private ScrolledComposite scrolledComposite;
	private Composite parent, generalComposite;
	
	private HashMap<String, Text> textAreasHashMap = new HashMap<String, Text>();
	private HashMap<String, String> textAreaValuesHashMap = new HashMap<String, String>();

	private ITerm currentTerm;

	public TerminologyInformationAreaViewPart() {
		data = new TerminologyInformationModel();
	}

	public TerminologyInformationModel getData(){
		return this.data;
	}
	
	public Composite getParent(){
		return this.parent;
	}
	
	private void updateInformationArea(){
		
		String key;
		Text text;
		
		if (currentTerm != null) {
			ITermSaillance saillance = currentTerm.getSaillanceCriteria();

			key = TerminologyInformationModel.NAMES[0];
			text = (Text)textAreasHashMap.get(key);
			if (text != null)
				if (saillance != null)
					text.setText(currentTerm.getSaillanceCriteria().getHeadProductivity()+ ""); //$NON-NLS-1$
				else
					text.setText(""); //$NON-NLS-1$

			key = TerminologyInformationModel.NAMES[1];
			text = (Text)textAreasHashMap.get(key);
			if (text != null)
				if (saillance != null)
					text.setText(currentTerm.getSaillanceCriteria().getModifierProductivity()+ ""); //$NON-NLS-1$
				else
					text.setText(""); //$NON-NLS-1$

			key = TerminologyInformationModel.NAMES[2];
			text = (Text)textAreasHashMap.get(key);
			if (text != null)
				if (saillance != null)
					text.setText(currentTerm.getSaillanceCriteria().getTfIdf() + ""); //$NON-NLS-1$
				else
					text.setText(""); //$NON-NLS-1$

			key = TerminologyInformationModel.NAMES[3];
			text = (Text)textAreasHashMap.get(key);

			if (text != null)
				if (saillance != null)
					text.setText(currentTerm.getSaillanceCriteria().getNbVar() + ""); //$NON-NLS-1$
				else
					text.setText(""); //$NON-NLS-1$

			key = TerminologyInformationModel.NAMES[4];
			text = (Text)textAreasHashMap.get(key);
			if (text != null)
				if (saillance != null)
					text.setText(currentTerm.getSaillanceCriteria().getFrequency() + ""); //$NON-NLS-1$
				else
					text.setText(""); //$NON-NLS-1$

			key = TerminologyInformationModel.NAMES[5];
			text = (Text)textAreasHashMap.get(key);
			if (text != null)
				if (saillance != null)
					text.setText(currentTerm.getSaillanceCriteria().getTypographicalWeight() + ""); //$NON-NLS-1$
				else
					text.setText(""); //$NON-NLS-1$

			key = TerminologyInformationModel.NAMES[6];
			text = (Text)textAreasHashMap.get(key);
			if (text != null)
				if (saillance != null)
					text.setText(currentTerm.getSaillanceCriteria().getPositionWeight()+ ""); //$NON-NLS-1$
				else
					text.setText(""); //$NON-NLS-1$
		}
	}

	private HashMap<String, String> getSelectedTermInformation(){
		HashMap<String, String> res = new HashMap<String, String>();
		String txt = ""; //$NON-NLS-1$
							
		if(currentTerm != null){
			txt = currentTerm.getSaillanceCriteria().getHeadProductivity() + ""; //$NON-NLS-1$
			res.put(TerminologyInformationModel.NAMES[0], txt);
			txt = currentTerm.getSaillanceCriteria().getModifierProductivity() + ""; //$NON-NLS-1$
			res.put(TerminologyInformationModel.NAMES[1], txt);
			txt = currentTerm.getSaillanceCriteria().getTfIdf() + ""; //$NON-NLS-1$
			res.put(TerminologyInformationModel.NAMES[2], txt);
			txt = currentTerm.getSaillanceCriteria().getNbVar() + ""; //$NON-NLS-1$
			res.put(TerminologyInformationModel.NAMES[3], txt);
			txt = currentTerm.getSaillanceCriteria().getFrequency() + ""; //$NON-NLS-1$
			res.put(TerminologyInformationModel.NAMES[4], txt);
			txt = currentTerm.getSaillanceCriteria().getTypographicalWeight() + ""; //$NON-NLS-1$
			res.put(TerminologyInformationModel.NAMES[5], txt);
			txt = currentTerm.getSaillanceCriteria().getPositionWeight()+ ""; //$NON-NLS-1$
			res.put(TerminologyInformationModel.NAMES[6], txt);


		} else {
			for (int i = 0; i < TerminologyInformationModel.NAMES.length; i++) {
				res.put(TerminologyInformationModel.NAMES[i], ""); //$NON-NLS-1$
			}
		}

		return res;
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		
		currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();
		
		updateContent(parent);
		
		updateInformationArea();	
		
	}

	
	
	public Composite getComposite(){
		return this.generalComposite;
	}
	
	public void disposeContent(){
		try{
			this.scrolledComposite.dispose();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void updateContent(Composite parent){
		
		parent.setLayout(new FillLayout());
		scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL);	
		
		generalComposite = new Composite(scrolledComposite, SWT.NONE);	
		GridLayout generalLayout = new GridLayout();
		generalLayout.numColumns = 2;		
		generalComposite.setLayout(generalLayout);
		
		textAreasHashMap = new HashMap<String, Text>();
		
		for(int i = 0; i < TerminologyInformationModel.NAMES.length; i++){
			
			if(this.data.get(TerminologyInformationModel.NAMES[i])){
				
				GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.horizontalIndent = 15;
				Label label = new Label(generalComposite, SWT.NULL);
				label.setText(TerminologyInformationModel.NAMES[i] + ":"); //$NON-NLS-1$
				label.setLayoutData(gridData);
				
				Text text = new Text(generalComposite, SWT.BORDER);
				gridData = new GridData(GridData.FILL_HORIZONTAL);	
				text.setLayoutData(gridData);
				text.setEditable(false);
				text.setEnabled(false);
				text.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				
				if (textAreaValuesHashMap.size() != 0) {
					text.setText(textAreaValuesHashMap.get(TerminologyInformationModel.NAMES[i]));
				}
				
				text.update();
				textAreasHashMap.put(TerminologyInformationModel.NAMES[i], text);
				
				
			} else {
				try{
				textAreasHashMap.put(TerminologyInformationModel.NAMES[i], null);
				;
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
		}
		
		scrolledComposite.setContent(generalComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinSize(parent.getSize().x, parent.getSize().y);
		scrolledComposite.setSize(parent.getSize().x, parent.getSize().y + 50);		
		generalComposite.setSize(parent.getSize().x, parent.getSize().y);
		

		updateInformationArea();
		
		ControlerFactoryImpl.getTerminologyControler().addPropertyChangeListener(ControlerFactoryImpl.currentTermEvent, new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				
				currentTerm = ControlerFactoryImpl.getTerminologyControler().getCurrentTerm();

				updateInformationArea();		
			}
			
		});
	}
	
	@Override
	public void setFocus() {

	}

}
