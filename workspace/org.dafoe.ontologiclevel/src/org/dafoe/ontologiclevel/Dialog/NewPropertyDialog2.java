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

package org.dafoe.ontologiclevel.Dialog;

import java.util.Iterator;
import java.util.List;

import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl;
import org.dafoe.terminoontologiclevel.common.BinaryTCRelation;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class NewPropertyDialog2 extends TitleAreaDialog {
	private IClass laclasse = null;
	private Text text;
	private Tree tree;

	private TreeItem tcRoot;
	private TreeItem rtcRoot;
	private TreeItem rtcTypeRoot;

	private Tree treetermino;
	private TreeItem rootit;
	private TabFolder tabFolder;

	private Text mincard;
	private Text maxcard;

	private Composite containerDatatype = null;
	private Composite containerObjet = null;

	private final static String DEFAULT_MIN_CARD="1";
	private final static String DEFAULT_MAX_CARD="1";

	private Combo comboDropDown;

	private List<IBasicDatatype> listeType;

	private IObjectProperty oprop = null;
	private IDatatypeProperty dprop = null;

	private IProperty parentProp=null;

	private IOntology ontology;

	//
	
	private Text txtNameSpace;
	private String nameSpace=""; //$NON-NLS-1$

	public IProperty getProperty() {
		if (oprop != null) {
			return oprop;
		} else {
			return dprop;
		}
	}

	public String getNameSpace() {
		if(txtNameSpace.getText()!= null)
			nameSpace= txtNameSpace.getText();
		return nameSpace;
	}
	
	//

	public void setParentProp(IProperty parentProp) {
		this.parentProp = parentProp;
	}

	//

	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE | SWT.MAX);
	}

	//

	public NewPropertyDialog2(Shell parentShell, IOntology onto) {
		super(parentShell);
		ontology = onto;
		parentShell.setText(Messages.getString("NewPropertyDialog2.0")); //$NON-NLS-1$
	}

	//

	public void setDoamaine(IClass _classe) {
		laclasse = _classe;
	}

	//

	protected Control createDialogArea(Composite parent) {

		FontRegistry fontRegistry = new FontRegistry(parent.getShell().getDisplay());

		fontRegistry.put("BIG", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$


		listeType = org.dafoe.ontologiclevel.common.DatabaseAdapter.getDataTypes();
		setMessage(Messages.getString("NewPropertyDialog2.Prop_Message"));  //$NON-NLS-1$
		setTitle(Messages.getString("NewPropertyDialog2.Prop_Titre"));  //$NON-NLS-1$
		this.setTitleImage(org.dafoe.ontologiclevel.Activator.getDefault().getImageRegistry().get(org.dafoe.ontologiclevel.Activator.NEW_CLASS_ID));

		Composite area = (Composite) super.createDialogArea(parent);

		area.setLayout(new GridLayout(1,true));

		Composite zone_nom  = new Composite(area, SWT.NONE);
		zone_nom.setLayout(new GridLayout(2,false));
		zone_nom.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		{
			Label LabelNom = new Label(zone_nom,SWT.NONE);

			LabelNom.setText(Messages.getString("NewPropertyDialog2.NomProp")); //$NON-NLS-1$

			text = new Text(zone_nom,SWT.BORDER);
			text.setFont(fontRegistry.get("BIG")); //$NON-NLS-1$
			GridData GD = new GridData(GridData.FILL_HORIZONTAL);
			GD.heightHint=15;
			text.setLayoutData(GD);
			
			Label lbNameSpace = new Label(zone_nom,SWT.NONE);

			lbNameSpace.setText("Name space:"); //$NON-NLS-1$

			txtNameSpace = new Text(zone_nom,SWT.BORDER);
			txtNameSpace.setFont(fontRegistry.get("BIG")); //$NON-NLS-1$
			txtNameSpace.setLayoutData(GD);
			
			
		}

		
		
		
		
		/// begin cardinality space
		Group cardinalityArea = new Group(area, SWT.NONE);
		cardinalityArea.setText(Messages.getString("NewPropertyDialog2.3")); //$NON-NLS-1$
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 5;
		cardinalityArea.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData.heightHint =35;
		gridData.horizontalSpan = 2;
		cardinalityArea.setLayoutData(gridData);

		
		final Button checkCardinality= new Button(cardinalityArea, SWT.CHECK);
		//labcard.setText(Messages.getString("NewPropertyDialog2.Cardinalité")); //$NON-NLS-1$
		GridData GDlab = new GridData(GridData.FILL_BOTH);
		GDlab.heightHint =35;
		GDlab.verticalAlignment=SWT.CENTER;
		
		
		
		//
		Label lbMin= new Label(cardinalityArea,SWT.NONE);
		lbMin.setText(Messages.getString("NewPropertyDialog2.min")); //$NON-NLS-1$
		mincard = new Text(cardinalityArea,SWT.BORDER);
		GridData GDmincard = new GridData(GridData.FILL_BOTH);
		GDmincard.heightHint =20;
		mincard.setLayoutData(GDmincard);
		mincard.setText(DEFAULT_MIN_CARD); //$NON-NLS-1$
		mincard.setEnabled(false);
		
		//
		Label lbMax= new Label(cardinalityArea,SWT.NONE);
		lbMax.setText(Messages.getString("NewPropertyDialog2.Max")); //$NON-NLS-1$
		maxcard = new Text(cardinalityArea,SWT.BORDER);
		GridData GDmaxcard = new GridData(GridData.FILL_BOTH);
		GDmaxcard.heightHint = 20;
		maxcard.setLayoutData(GDmaxcard);
		maxcard.setText(DEFAULT_MAX_CARD); //$NON-NLS-1$
		maxcard.setEnabled(false);
		maxcard.setToolTipText("Please enter -1 to specify INFINITE cardinality");
		///
		
checkCardinality.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				if(checkCardinality.getSelection()){
					mincard.setEnabled(true);
					maxcard.setEnabled(true);	
				}else{
					mincard.setEnabled(false);
					maxcard.setEnabled(false);
				}
				
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});

//// end cardinality space
		
		tabFolder = new TabFolder(area, SWT.NONE);
		GridData gdtab = new GridData(GridData.FILL_BOTH);
		gdtab.heightHint=100;
		tabFolder.setLayoutData(gdtab);

		{
			
			// begin space for datatype property
			TabItem tbtmDataType = new TabItem(tabFolder, SWT.NONE);
			tbtmDataType.setText(Messages.getString("NewPropertyDialog2.DataType"));  //$NON-NLS-1$

			containerDatatype  = new Composite(tabFolder, SWT.BORDER);
			containerDatatype.setLayout(new GridLayout(2,false));
			containerDatatype.setLayoutData(new GridData(GridData.FILL_BOTH));
			{
				Label lblDataType = new Label(containerDatatype, SWT.NONE);
				lblDataType.setBounds(10, 10, 103, 15);
				lblDataType.setText(Messages.getString("NewPropertyDialog2.ChoisissezLeType")); //$NON-NLS-1$
				comboDropDown = new Combo(containerDatatype, SWT.DROP_DOWN | SWT.BORDER);
				comboDropDown.setBounds(10, 10, 103, 75);
				Iterator<IBasicDatatype> iter = listeType.iterator();
				while(iter.hasNext()) {
					IBasicDatatype BDT = iter.next();
					comboDropDown.add(BDT.getLabel());

				}
			}

			tbtmDataType.setControl(containerDatatype);
            
			// end datatype property space
			
			/// begin space for object property type
			TabItem tbtmObject = new TabItem(tabFolder, SWT.NONE);
			tbtmObject.setText(Messages.getString("NewPropertyDialog2.ObjectType"));  //$NON-NLS-1$

			containerObjet = new Composite(tabFolder, SWT.BORDER);
			containerObjet.setLayout(new GridLayout(1,true));
			containerObjet.setLayoutData(new GridData(GridData.FILL_BOTH));
			{
				Label lblClassName = new Label(containerObjet, SWT.NONE);

				lblClassName.setText(Messages.getString("NewPropertyDialog2.Choisissezlaclasse"));  //$NON-NLS-1$
				tree = new Tree(containerObjet,SWT.NONE);
				tree.setLayoutData(new GridData(GridData.FILL_BOTH));
			}
			containerObjet.setVisible(true);
			tbtmObject.setControl(containerObjet);
			loadClasses(tree);

						
			
			
		}

		if (parentProp != null) {
			if (parentProp instanceof IObjectProperty) {
				
				containerObjet.setVisible(true);
				containerDatatype.setVisible(false);
			} else {
				
				containerObjet.setVisible(false);
				containerDatatype.setVisible(true);

			}

		}
		{
			Composite containerObjet = new Composite(area, SWT.BORDER);
			containerObjet.setLayout(new GridLayout(1,true));
			containerObjet.setLayoutData(new GridData(GridData.FILL_BOTH));
			{
				Label lblClassName = new Label(containerObjet, SWT.NONE);

				lblClassName.setText(Messages.getString("NewClassDialog2.3"));  //$NON-NLS-1$
				lblClassName.setFont(fontRegistry.get("BIG")); //$NON-NLS-1$
				treetermino = new Tree(containerObjet,SWT.NONE);
				treetermino.setLayoutData(new GridData(GridData.FILL_BOTH));

			}
			containerObjet.setVisible(true);
		}

		loadTerminoConcepts(treetermino);

		return area;
	}

	//

	private void loadTerminoConcepts(Tree tree) {
		tree.removeAll();

		// TC tree

		tcRoot = new TreeItem(tree,SWT.NONE);
		tcRoot.setText("Termino-Concepts"); //$NON-NLS-1$
		tcRoot.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_ID));

		List<ITerminoConcept> tcs = org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopTerminoConcepts();

		for (int i = 0; i < tcs.size(); i++) {
			ITerminoConcept tc = tcs.get(i);
			creerArbre(tc, tcRoot);
		}

		// RTC tree

		rtcRoot = new TreeItem(tree,SWT.NONE);
		rtcRoot.setText("Termino-Concept Relations"); //$NON-NLS-1$
		rtcRoot.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_RELATION_ID));

		List<BinaryTCRelation> tcrs = org.dafoe.terminoontologiclevel.common.DatabaseAdapter.getTerminoConceptualRelations();

		for (int i = 0; i < tcrs.size(); i++) {
			BinaryTCRelation rtc = tcrs.get(i);
			creerArbre(rtc, rtcRoot);
		}

		// RTC Type tree

		rtcTypeRoot = new TreeItem(tree,SWT.NONE);
		rtcTypeRoot.setText("Termino-Concept Relation Types"); //$NON-NLS-1$
		rtcTypeRoot.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_RELATION_TYPE_ID));

		List<ITypeRelationTc> tcrTypes = org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopTerminoConceptRelationTypes();

		for (int i = 0; i < tcrTypes.size(); i++) {
			ITypeRelationTc rtcType = tcrTypes.get(i);
			creerArbre(rtcType, rtcTypeRoot);
		}

		tcRoot.setExpanded(true);
		rtcRoot.setExpanded(true);
		rtcTypeRoot.setExpanded(true);

		tree.setTopItem(tcRoot);


	}

	//

	private void creerArbre(ITerminoConcept tc, TreeItem pere) {
		TreeItem it = creerTreeItem(tc, pere);
		Iterator<ITerminoConcept> itcl = tc.getChildren().iterator();

		while(itcl.hasNext()) {

			ITerminoConcept child = itcl.next();

			creerArbre(child, it);
		}
	}

	//


	private TreeItem creerTreeItem (ITerminoConcept tc, TreeItem pere) {
		TreeItem it = new TreeItem(pere, SWT.NONE);
		it.setData(tc);
		it.setText(tc.getLabel());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_ID));

		return it;
	}

	//

	private void creerArbre(BinaryTCRelation rtc, TreeItem pere) {
		TreeItem it = creerTreeItem(rtc, pere);
	}

	//

	private TreeItem creerTreeItem (BinaryTCRelation rtc, TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);

		it.setData(rtc.getOrigin());

		String rtcTypeName = rtc.getType().getName();
		String tc1Label = rtc.getTc1().getLabel();
		String tc2Label = rtc.getTc2().getLabel();

		String s = tc1Label + ", " + rtcTypeName + ", " + tc2Label; //$NON-NLS-1$ //$NON-NLS-2$

		it.setText(s); //$NON-NLS-1$

		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_RELATION_ID));

		return it;
	}

	//

	private void creerArbre(ITypeRelationTc rtcType, TreeItem pere) {
		TreeItem it = creerTreeItem(rtcType, pere);

		Iterator<ITypeRelationTc> itRtcType = rtcType.getChildren().iterator();

		while(itRtcType.hasNext()) {
			ITypeRelationTc child = itRtcType.next();

			creerArbre(child, it);
		}
	}

	//

	private TreeItem creerTreeItem (ITypeRelationTc rtcType, TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);
		it.setData(rtcType);
		it.setText(rtcType.getName());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.TERMINO_CONCEPT_RELATION_TYPE_ID));

		return it;
	}

	//

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(IDialogConstants.OK_LABEL);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(IDialogConstants.OK_ID));
		GridData GD = new GridData(GridData.FILL_HORIZONTAL);
		GD.widthHint=56;
		button.setLayoutData(GD);
		Shell shell = parent.getShell();
		if (shell != null) {
			shell.setDefaultButton(button);
		}

		//createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (creerPropriete()) {
					setReturnCode(OK);
					close();
				}

			}
		});

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	//

	TreeItem creerTreeItem (IClass classe,TreeItem pere) {
		TreeItem it = new TreeItem(pere,SWT.NONE);
		it.setData(classe);



		it.setText(classe.getLabel());
		it.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.CLASSES_ID));
		return it;
	}

	//

	void loadClasses(Tree tree) {

		List<IClass> lesClasses = org.dafoe.ontologiclevel.common.DatabaseAdapter.getTopClasses(ontology);

		tree.removeAll();

		rootit = new TreeItem(tree,SWT.NONE);
		rootit.setText(Messages.getString("NewPropertyDialog2.Rien")); //$NON-NLS-1$
		rootit.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
				.getImageRegistry().get(
						org.dafoe.ontologiclevel.Activator.CLASSES_ID));

		for (int i=0;i<lesClasses.size();i++) {

			//System.out.println(lesClasses.get(i).getLabel()+"  "+lesClasses.get(i).getParents().toString());
			IClass curclasse = lesClasses.get(i);
			creerArbre(curclasse,rootit);

		}
		rootit.setExpanded(true);
		tree.select(rootit);
		tree.setTopItem(rootit);

	}

	//

	void creerArbre(IClass classe,TreeItem pere) {
		TreeItem it = creerTreeItem(classe,pere);
		Iterator<IClass> itcl = classe.getChildren().iterator();

		while(itcl.hasNext()) {
			IClass mc =itcl.next();

			creerArbre(mc,it);

		}
	}

	//

	boolean creerPropriete () {
		int style = SWT.ICON_ERROR;
		MessageBox messageBox = new MessageBox(this.getShell(), style);

		if (text.getText()=="") { //$NON-NLS-1$

			messageBox.setMessage(Messages.getString("NewPropertyDialog2.Vous_devez_saisir_nom_prop")); //$NON-NLS-1$
			messageBox.open();
			return false;

		}
		int min =0;
		int max =0;
		if (mincard.getText()=="") { //$NON-NLS-1$

			messageBox.setMessage(Messages.getString("NewPropertyDialog2.VousDevSaisirMin")); //$NON-NLS-1$
			messageBox.open();
			return false;

		}

		try {
			min = Integer.parseInt(mincard.getText());

		} catch (Exception excep) {
			messageBox.setMessage(Messages.getString("NewPropertyDialog2.MinNonValide")); //$NON-NLS-1$
			messageBox.open();
			return false;
		}
		if (min<0) {
			messageBox.setMessage(Messages.getString("NewPropertyDialog2.MinSupEgal0")); //$NON-NLS-1$
			messageBox.open();
			return false;
		}
		if (maxcard.getText()=="") { //$NON-NLS-1$

			messageBox.setMessage(Messages.getString("NewPropertyDialog2.VousDevSaisirMax")); //$NON-NLS-1$
			messageBox.open();
			return false;

		}

		try {
			max = Integer.parseInt(maxcard.getText());

		} catch (Exception excep) {
			messageBox.setMessage(Messages.getString("NewPropertyDialog2.MaxNonValide")); //$NON-NLS-1$
			messageBox.open();
			return false;
		}
		if (max<-1) {
			messageBox.setMessage(Messages.getString("NewPropertyDialog2.MAxSupEgal_1")); //$NON-NLS-1$
			messageBox.open();
			return false;
		}
		if (max!=-1) {
			if (min>max) {
				messageBox.setMessage(Messages.getString("NewPropertyDialog2.MaxSupMin")); //$NON-NLS-1$
				messageBox.open();
				return false;
			}
		}


		if (tabFolder.getSelectionIndex() == 1) {
			/*
			if (tree.getSelection().length==0) {
				messageBox.setMessage(Messages.getString("NewPropertyDialog2.VousDevezChoisirClasse")); //$NON-NLS-1$
				messageBox.open();
				return false;
			} else {
			 */
			IClass classe = null;

			if (tree.getSelection().length==0) {
				TreeItem it = tree.getSelection()[0];
				classe = (IClass) it.getData();
			}

			//ES
			/*
				if (classe == null) {
					messageBox.setMessage(Messages.getString("NewPropertyDialog2.VousDevezChoisirClasse")); //$NON-NLS-1$
					messageBox.open();
					return false;
				}
			 */

			oprop = new ObjectPropertyImpl();

			oprop.setRange(classe);

			if (laclasse!=null) {
				oprop.addDomain(laclasse);
			}

			oprop.setLabel(text.getText());
			oprop.setState(ONTO_OBJECT_STATE.VALIDATED);
			oprop.setMaximalCardinality(max);
			oprop.setMinimalCardinality(min);
			oprop.setNameSpace(getNameSpace());

			if (parentProp != null) {
				//oprop.setParent((IObjectProperty) parentProp);
				((IObjectProperty) parentProp).addChild(oprop);

			}

			/*
				ontology.addOntoObject(oprop);
				if (treetermino.getSelection().length>0) {
					ITerminoOntoObject itc = (ITerminoOntoObject)treetermino.getSelection()[0].getData();
					if (itc!=null ) {
						oprop.addRelatedTerminoOntoObject(itc);
					}
				}

			 */

			ITerminoOntoObject itc = null;

			if (treetermino.getSelection() != null) {

				if (treetermino.getSelection().length > 0) {
					itc = (ITerminoOntoObject)treetermino.getSelection()[0].getData();
				}
			}

			org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(ontology, oprop, itc);
			//}

		} else {

			if (comboDropDown.getSelectionIndex() == -1) {

				messageBox.setMessage(Messages.getString("NewPropertyDialog2.VousDevezChoisirType")); //$NON-NLS-1$
				messageBox.open();

				return false;

			} else {

				dprop=new DatatypePropertyImpl();
				dprop.setRange(
						listeType.get(comboDropDown.getSelectionIndex()));
				if (laclasse!=null) {
					dprop.addDomain(laclasse);
				}
				dprop.setLabel(text.getText());
				if (parentProp!=null) {
					//dprop.setParent((IDatatypeProperty) parentProp);
					((IDatatypeProperty) parentProp).addChild(dprop);
				}
				dprop.setState(ONTO_OBJECT_STATE.VALIDATED);
				dprop.setMaximalCardinality(max);
				dprop.setMinimalCardinality(min);
				dprop.setNameSpace(getNameSpace());
				
				/*
				ontology.addOntoObject(dprop);
				if (treetermino.getSelection().length>0) {
					ITerminoOntoObject itc = (ITerminoOntoObject)treetermino.getSelection()[0].getData();
					if (itc!=null ) {
						dprop.addRelatedTerminoOntoObject(itc);
					}
				}
				 */
				
				
				
				//VT: connect a datatype property to a TcObject is optional
				ITerminoOntoObject itc = null;

				if (treetermino.getSelection() != null) {

					if (treetermino.getSelection().length > 0) {
						itc = (ITerminoOntoObject)treetermino.getSelection()[0].getData();
					}
				}

				//ITerminoOntoObject itc = (ITerminoOntoObject)treetermino.getSelection()[0].getData();

				org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(ontology, dprop, itc);
			}
		}

		return true;

	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, 800);
	}
}
