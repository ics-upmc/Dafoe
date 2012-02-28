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

package org.dafoe.ontologiclevel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.ontologiclevel.Dialog.AnnotationDialog2;
import org.dafoe.ontologiclevel.Dialog.MappingDialog;
import org.dafoe.ontologiclevel.Dialog.NewPropertyDialog2;
import org.dafoe.ontologiclevel.Dialog.SubsumeDialog;
import org.dafoe.ontologiclevel.Elements.ClassAnnotation;
import org.dafoe.ontologiclevel.Elements.ClassEquivalence;
import org.dafoe.ontologiclevel.Elements.MappingUIElement;
import org.dafoe.ontologiclevel.Elements.SupperClass;
import org.dafoe.ontologiclevel.common.DatabaseAdapter;
import org.dafoe.ui.widgets.AddElementWidget;
import org.dafoe.ui.widgets.ElementWidget;
import org.dafoe.ui.widgets.IGenericAddListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

public class ClassePropertyViewPart extends ViewPart {

	private int direction_name_direct = 1;
	private int direction_range_direct = 1;
	private int direction_name_herited = 1;
	private int direction_range_herited = 1;

	private ArrayList<ElementWidget> lesAnnotations = new ArrayList<ElementWidget>();

	private Composite compAnnotation;

	private Composite compsuper;
	private Composite compEquivalent;

	private Composite complabel;
	private Text textLabel;
	private Button modButton;

	private ScrolledComposite scrolledAnnotation;

	private Browser browser;

	private TableViewer tableProperties;
	private TableViewer tableInheritedProperties;

	private Action deleteAction;
	
	//

	public ClassePropertyViewPart() {
	}

	//

	private void deleteAnnotationsWidgets() {
		Iterator<ElementWidget> iter = lesAnnotations.iterator();
		while (iter.hasNext()) {
			iter.next().getEnvelope().dispose();
		}
		lesAnnotations.clear();
	}

	//

	private void showLabel() {
		if (Activator.getCurrentClass()!=null) {
			textLabel.setText(Activator.getCurrentClass().getLabel());
		}
	}

	//

	private void clearSuper() {
		Control[] ctl = compsuper.getChildren();
		for (int i=0;i<ctl.length;i++) {
			ctl[i].dispose();
		}
	}

	//

	private void clearEquivalent() {
		Control[] ctl = compEquivalent.getChildren();
		for (int i=0;i<ctl.length;i++) {
			ctl[i].dispose();
		}
	}

	//

	private void showSuper() {
		Iterator<IClass> itr = Activator.getCurrentClass().getParents().iterator();

		while (itr.hasNext()) {

			ElementWidget EW = new ElementWidget(compsuper,SWT.NONE,true,false);
			IClass per = itr.next();

			SupperClass SC = new SupperClass(per,Activator.getCurrentClass());

			EW.setElement(SC);
			GridData gd = new GridData(SWT.FILL,SWT.TOP,true,false);
			EW.getEnvelope().setLayoutData(gd);
			compsuper.pack();
		}
	}

	//

	private void showEquivalence() {

		if (Activator.getCurrentClass() != null) {

			IClass laclasse = Activator.getCurrentClass();

			if (laclasse.getLogicalDefinition() != null) {

				if (laclasse.getLogicalDefinition().compareTo("") != 0) {
					String logicalDefinition = laclasse.getLogicalDefinition();

					ElementWidget ew = new ElementWidget(compEquivalent, SWT.NONE, true, true);
					ClassEquivalence ce = new ClassEquivalence(Activator.getCurrentClass(), logicalDefinition);
					ce.setLabel("");
					ew.setElement(ce);
					ew.setEditToolTip(Messages.getString("ClassePropertyViewPart.0")); //$NON-NLS-1$
					ew.setDeleteToolTip(Messages.getString("ClassePropertyViewPart.1")); //$NON-NLS-1$
					GridData gd = new GridData(GridData.FILL_HORIZONTAL);
					ew.getEnvelope().setLayoutData(gd);
					compEquivalent.pack();

				}

			}
		}

	}


	//

	private void refresh() {
		try {
			clearEquivalent();
			clearSuper();
			clearProp();
			deleteAnnotationsWidgets();

			if (Activator.getCurrentClass()!=null) {

				showEquivalence();

				showSuper();

				showLabel();

				showAnnotations();

				showPropriete();

				showUsage();

				modButton.setEnabled(true);
				textLabel.setEnabled(true);

			}  else {

				browser.setText(""); //$NON-NLS-1$
				tableProperties.setInput(new ArrayList());
				tableInheritedProperties.setInput(new ArrayList());

				modButton.setEnabled(false);
				textLabel.setEnabled(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	private void showAnnotations() {
		int max_env = 0;

		if (Activator.getCurrentClass() != null) {

			IClass laclasse = Activator.getCurrentClass();

			if (laclasse.getAnnotations()!=null) {
				Iterator<IOntoAnnotation> itr = laclasse.getAnnotations().iterator();
				//org.dafoe.ontologiclevel.common.DatabaseAdapter.getAnnotations(laclasse).iterator();

				while (itr.hasNext()) {

					IOntoAnnotation anot = itr.next();
					ElementWidget ew = new ElementWidget(compAnnotation, SWT.NONE, true, true);
					ClassAnnotation ca = new ClassAnnotation(Activator.getCurrentClass(),anot);
					ca.setLabel(Messages.getString("ClassePropertyViewPart.Annotation")); //$NON-NLS-1$

					ew.setElement(ca);
					ew.setEditToolTip(Messages.getString("ClassePropertyViewPart.0")); //$NON-NLS-1$
					ew.setDeleteToolTip(Messages.getString("ClassePropertyViewPart.1")); //$NON-NLS-1$
					GridData gd = new GridData(GridData.FILL_HORIZONTAL);

					ew.getEnvelope().setLayoutData(gd);

					max_env += ew.getEnvelope().getBounds().height + 10;
					lesAnnotations.add(ew);
				}
			}

			if (laclasse.getMappedTerminoOntoObjects()!=null) {

				Iterator<ITerminoOntoObject> itr2 = laclasse.getMappedTerminoOntoObjects().iterator();

				while (itr2.hasNext()) {

					ITerminoOntoObject tero = itr2.next();
					ElementWidget EW = new ElementWidget(compAnnotation, SWT.NONE, true, false);
					MappingUIElement CA = new MappingUIElement(Activator.getCurrentClass(),tero);
					CA.setLabel(Messages.getString("ClassePropertyViewPart.Lien")); //$NON-NLS-1$

					EW.setElement(CA);
					EW.setEditToolTip(Messages.getString("ClassePropertyViewPart.0")); //$NON-NLS-1$
					EW.setDeleteToolTip(Messages.getString("ClassePropertyViewPart.1")); //$NON-NLS-1$
					GridData gd = new GridData(GridData.FILL_HORIZONTAL);

					EW.getEnvelope().setLayoutData(gd);

					max_env += EW.getEnvelope().getBounds().height+10;
					lesAnnotations.add(EW);
				}
			}

		}

		compAnnotation.pack();
		//scrolledAnnotation.setMinSize(400, 60 + max_env); //VT
		
		scrolledAnnotation.setMinSize(400, 80+lesAnnotations.size()*(70)); //VT


	}

	//

	private String showClass(IClass classe) {
		String bc = "style2"; //$NON-NLS-1$

		if(classe != null) {
			System.out.println("OK");
			if (classe.getId().equals(Activator.getCurrentClass().getId())) {
				bc="style1"; //$NON-NLS-1$
			}

			return "<a href=\"#?classe="+classe.getId()+"\"><span class=\""+bc+"\">"+classe.getLabel() +"</span></a>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		} else {
			return "";
		}
	}

	//

	private String showDataProp(IDatatypeProperty prop) {
		String range = "";

		if (prop.getRange() != null) {
			range = " (" + prop.getRange().getLabel() + ")";
		} 

		return "<a href=\"#?dataproperty="+prop.getId()+"\">"+prop.getLabel() +"</a>" + range; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	//

	private String showObjectProp(IObjectProperty prop) {
		String range = "";

		if (prop.getRange() != null) {
			range = " (" + showClass(prop.getRange()) + ")";
		} 

		return "<a href=\"#?objectproperty="+prop.getId()+"\">"+prop.getLabel() +"</a>"+ range; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	//

	private String getClassImageId(IClass clazz){
		String imageId;

		imageId = Activator.icone_classe_path;

		if (clazz.getLogicalDefinition() != null) {
			if (clazz.getLogicalDefinition().compareTo("") != 0) {
				imageId = Activator.icone_defined_classe_path;
			}
		}

		return imageId;
	}

	//

	private void showUsage() {
		IClass currentClass = null;

		if (Activator.getCurrentClass()==null) {
			browser.setText(""); //$NON-NLS-1$
			return;
		}

		currentClass = Activator.getCurrentClass();

		String html = "<HTML><HEAD><TITLE></TITLE>\r\n"; //$NON-NLS-1$
		html+="<style type=\"text/css\">"; //$NON-NLS-1$
		html+=".style1 {"; //$NON-NLS-1$
		//html+="background-color:#99CCFF;"; //$NON-NLS-1$
		//html+="color:#000000;"; //$NON-NLS-1$
		html+="color:#FF0000;"; //$NON-NLS-1$
		html+="}\r\n"; //$NON-NLS-1$
		html+=".style2 {"; //$NON-NLS-1$
		html+="}\r\n"; //$NON-NLS-1$
		html+="</style></HEAD><BODY>\r\n"; //$NON-NLS-1$


		html+="<b>&nbsp;"+"<img src=\""+getClassImageId(currentClass)+"\">&nbsp;"+showClass(currentClass)+"</b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		if (currentClass.getChildren() != null) {
			Iterator<IClass> itr = currentClass.getChildren().iterator();
			if (itr.hasNext()) {
				html+="<br/>";
				while (itr.hasNext()) {
					IClass cl = itr.next();
					//html+="<br/><b>&nbsp;&nbsp;<a href=\"#?classe="+cl.getId()+"&mere="+Activator.currentClasse.getId()+"\">"+cl.getLabel() +"</a>"+" subClassOf "+Activator.currentClasse.getLabel()+"</b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					html += "<br/><b>&nbsp;<img src=\""  //$NON-NLS-1$ 
						+ getClassImageId(cl)
						+ "\">&nbsp;"  //$NON-NLS-1$
						+ showClass(cl)
						+ Messages.getString("ClassePropertyViewPart.32")  //$NON-NLS-1$
						+ showClass(currentClass)
						+ "</b>"; //$NON-NLS-1$ 
				}
			}
		}

		if (currentClass.getParents()!=null) {
			Iterator<IClass> itr = currentClass.getParents().iterator();
			if (itr.hasNext()) {
				html+="<br/>"; //$NON-NLS-1$
				while (itr.hasNext()) {
					IClass cl = itr.next();
					// html+="<br/><b>&nbsp;&nbsp;<a href=\"#?classe="+cl.getId()+"&fille="+Activator.currentClasse.getId()+"\">"+cl.getLabel() +"</a></b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					html += "<br/><b>&nbsp;<img src=\""  //$NON-NLS-1$
						+ getClassImageId(cl)
						+ "\">&nbsp;"  //$NON-NLS-1$
						+ showClass(cl)
						+ Messages.getString("ClassePropertyViewPart.50")  //$NON-NLS-1$
						+ showClass(currentClass)
						+ "</b>"; //$NON-NLS-1$
				}

			}
		}

		if (currentClass.getObjectProperties()!=null) {
			Iterator<IObjectProperty> itr2 = currentClass.getObjectProperties().iterator();
			if (itr2.hasNext()) {
				//html+="<br/><b>"+Messages.getString("ClassePropertyViewPart.20")+"</b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

				html+="<br/>"; //$NON-NLS-1$
				while (itr2.hasNext()) {
					IObjectProperty cl = itr2.next();
					html+="<br/><b>&nbsp;<img src=\""+Activator.icone_object_property_path+"\">&nbsp;"+showObjectProp(cl)+" domain "+showClass(currentClass)+"</b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

				}

			}
		}

		if (Activator.getCurrentClass().getDatatypeProperties()!=null) 
		{
			Iterator<IDatatypeProperty> itr3 = currentClass.getDatatypeProperties().iterator();
			if (itr3.hasNext()) {
				html+="<br/>"; //$NON-NLS-1$
				while (itr3.hasNext()) {
					IDatatypeProperty cl = itr3.next();
					//html+="<br/><b>&nbsp;&nbsp;<a href=\"#?dataproperty="+cl.getId()+"&mere="+Activator.currentClasse.getId()+"\">"+cl.getLabel() +"</a></b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					html+="<br/><b>&nbsp;<img src=\""+Activator.icone_data_property_path+"\">&nbsp;"+showDataProp(cl)+" domain "+showClass(currentClass)+"</b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

				}

			}
		}

		boolean marque = false;

		//Iterator<IObjectProperty> itr4 = Activator.getDefault().oSession.getAllObjectProperty().iterator();
		//val�ry
		org.dafoe.ontologiclevel.common.DatabaseAdapter.loadObjectProperties();
		Iterator<IObjectProperty> itr4 = org.dafoe.ontologiclevel.common.DatabaseAdapter.getObjectProperties().iterator();

		if (itr4.hasNext()) {

			while (itr4.hasNext()) {
				IObjectProperty cl = itr4.next();
				if (cl.getRange() != null) {
					if (cl.getRange().getId().equals( Activator.getCurrentClass().getId())) {
						if (!marque) {
							html+="<br/>";  //$NON-NLS-1$
							marque=true;
						}
						// html+="<br/><b>&nbsp;&nbsp;<a href=\"#?objectproperty="+cl.getId()+"&mere="+Activator.currentClasse.getId()+"\">"+cl.getLabel() +"</a></b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						html+="<br/><b>&nbsp;<img src=\""+Activator.icone_object_property_path+"\">&nbsp;"+showObjectProp(cl)+Messages.getString("ClassePropertyViewPart.57")+showClass(Activator.getCurrentClass())+"</b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					}
				}
			}

		}

		html += "</BODY></HTML>"; //$NON-NLS-1$
		browser.setText(html);
	}

	//

	private void clearProp() {
		/*Control[] ctl = compproperty.getChildren();
		for (int i=0;i<ctl.length;i++) {
			ctl[i].dispose();
		}
		ctl = compherited.getChildren();
		for (int i=0;i<ctl.length;i++) {
			ctl[i].dispose();
		}*/
	}

	//

	private void showPropriete() {

		tableProperties.setInput(Activator.getCurrentClass().getProperties());

		tableProperties.refresh();

		tableInheritedProperties.setInput(Activator.getCurrentClass().getInheritedProperties());

		tableInheritedProperties.refresh();

	}

	//

	/*
	private ArrayList<IProperty> getInherited(IClass classe) {

		ArrayList<IProperty> result = new ArrayList();
		result.addAll(classe.getProperties());
		Iterator<IClass> itr = classe.getParents().iterator();
		while (itr.hasNext()) {

			result.addAll(getInherited(itr.next()));

		}

		return result;
	}

*/
	//

	@Override
	public void createPartControl(Composite parent) {

		FontRegistry fontRegistry = new FontRegistry(parent.getShell().getDisplay());

		fontRegistry.put("BIG", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.currentClassEvent, 
				new CurrentClassChangeListener());

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.selectNoClassEvent,
				new SelectNoClassListener());

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.updateClasseEvent, 
				new UpdateCurrentClasse());

		Composite comp = new Composite(parent,SWT.NONE);
		comp.setLayout(new FillLayout(SWT.VERTICAL));
		SashForm sashForm = new SashForm(comp, SWT.BORDER);
		sashForm.setOrientation(SWT.VERTICAL);
		//sashForm.setWeights(new int[] {180, 150});
		{
			createClassDescriptionPart(sashForm);

		}

		{
			TabFolder tabFolder = new TabFolder(sashForm, SWT.NONE);
			{
				TabItem tbtmAnnotations = new TabItem(tabFolder, SWT.NONE);
				tbtmAnnotations.setText(Messages.getString("ClassePropertyViewPart.35")); //$NON-NLS-1$
				{
					Composite composite = new Composite(tabFolder, SWT.NONE);

					//composite.setBackground(new Color(getSite().getShell().getDisplay(), 125, 0, 0));

					composite.setLayout(new GridLayout(1,false));
					//composite.setLayout(new FillLayout(SWT.VERTICAL));
					tbtmAnnotations.setControl(composite);
					{
						complabel = new Composite(composite, SWT.NONE);
						GridData myGD2 = new GridData(GridData.FILL_HORIZONTAL);
						//myGD2.heightHint=35;
						complabel.setLayoutData(myGD2 );
						complabel.setLayout(new GridLayout(3,false)); 

						Label lab = new Label(complabel,SWT.NONE);
						lab.setText(Messages.getString("ClassePropertyViewPart.SaisissezLabel")); //$NON-NLS-1$

						textLabel = new Text(complabel,SWT.BORDER);
						textLabel.setFont(fontRegistry.get("BIG"));
						GridData myGD = new GridData(GridData.FILL_HORIZONTAL);
						//myGD.heightHint=35;
						textLabel.setLayoutData(myGD);

						modButton = new Button(complabel,SWT.NONE);
						modButton.setText(Messages.getString("ClassePropertyViewPart.Modifier")); //$NON-NLS-1$
						modButton.addSelectionListener(new SelectionListener() {

							public void widgetDefaultSelected(SelectionEvent e) {
							}

							public void widgetSelected(SelectionEvent e) {
								try {
									if (textLabel.getText()=="") { //$NON-NLS-1$
										MessageBox msg = new MessageBox(getSite().getShell(),SWT.ICON_WARNING | SWT.OK);
										msg.setMessage(Messages.getString("ClassePropertyViewPart.ErreurDeSaisie")); //$NON-NLS-1$
										msg.setText(Messages.getString("ClassePropertyViewPart.VousDevezSaisirUnLabel")); //$NON-NLS-1$
										msg.open();
										return;

									} else {
										Activator.getCurrentClass().setLabel(textLabel.getText());
										org.dafoe.ontologiclevel.common.DatabaseAdapter.saveClass(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), Activator.getCurrentClass(), null);
										Activator.ontoControler.UpdateCurrentClass();
									}
								} catch (Exception excep) {

								}
							}});

						createClasseAnnotationPart(composite);
					}
				}
			}
			{
				TabItem tbtmClasseUsage = new TabItem(tabFolder, SWT.NONE);
				tbtmClasseUsage.setText(Messages.getString("ClassePropertyViewPart.36")); //$NON-NLS-1$
				{
					Composite composite = new Composite(tabFolder, SWT.NONE);
					composite.setLayout(new FillLayout(SWT.VERTICAL));
					tbtmClasseUsage.setControl(composite);
					{
						createUsagePart(composite);
					}
				}
			}
			{
				TabItem tbtmProprietes = new TabItem(tabFolder, SWT.NONE);
				tbtmProprietes.setText(Messages.getString("ClassePropertyViewPart.37")); //$NON-NLS-1$
				{
					Composite composite = new Composite(tabFolder, SWT.NONE);
					composite.setLayout(new FillLayout(SWT.VERTICAL));
					tbtmProprietes.setControl(composite);

					createPropertiesPart(composite);

				}
			}
		}
		sashForm.setWeights(new int[] {250,250});

		ControlerFactoryImpl.getTerminoOntoControler().addPropertyChangeListener(ControlerFactoryImpl.newTCToOntoObjectMappingEvent, new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {

				refresh();
			}

		});

		createActions();
		createContextMenu();
		refresh();

	}

	//

	private void createPropertiesPart(Composite composite) {
		SashForm sashFormProp = new SashForm(composite, SWT.BORDER);
		sashFormProp.setOrientation(SWT.VERTICAL);
		//sashFormProp.setWeights(new int[] {50,50});
		{
			FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
			ScrolledForm form = toolkit.createScrolledForm(sashFormProp);
			GridLayout layout = new GridLayout(1, false);
			form.getBody().setLayout(layout);
			form.setText(Messages.getString("ClassePropertyViewPart.38")); //$NON-NLS-1$


			//this.form.setImage(this.image.createImage());
			toolkit.decorateFormHeading(form.getForm());
			GridData gd = new GridData(SWT.FILL, SWT.FILL, true ,true);
			form.setLayoutData(gd);


			AddElementWidget GAW = new AddElementWidget(form.getBody(), SWT.NONE);
			GAW.set_text("Ajouter une propi�t�"); //$NON-NLS-1$
			GAW.setEnabled(true);
			GAW.getEnvelope().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			GAW.addGenericAddListener(new IGenericAddListener() {

				public void OnSelected(Object source) {

					if (Activator.getCurrentClass() != null) {

						NewPropertyDialog2 dial = new NewPropertyDialog2(getSite().getShell(),Activator.getCurrentOntology());

						dial.setDoamaine(Activator.getCurrentClass());
						if (dial.open()==Window.OK) {
							refresh();
						}
					}
				}});


			Composite composite_localprop = new Composite(form.getBody(), SWT.NONE);
			GridData GDParent = new GridData(GridData.FILL_BOTH);

			composite_localprop.setLayoutData(GDParent);
			composite_localprop.setLayout(new GridLayout(1,true));
			{
				Composite composite_arbre = new Composite(composite_localprop, SWT.NONE);
				GridData GDarbres = new GridData(GridData.FILL_BOTH);
				composite_arbre.setLayoutData(GDarbres);
				composite_arbre.setLayout(new GridLayout(1,true));
				{
					tableProperties = new TableViewer(composite_arbre, SWT.BORDER|SWT.FULL_SELECTION);
					tableProperties.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
					tableProperties.setContentProvider(new TreePropertyContrentProvider());
					tableProperties.setLabelProvider(new TreePropertyLabelProvider());
					tableProperties.getTable().setHeaderVisible(true);
					Table table = tableProperties.getTable();

					TableColumn TC = new TableColumn(table, SWT.CENTER);
					TC.setText(Messages.getString("ClassePropertyViewPart.2")); //$NON-NLS-1$
					TC.setWidth(200);
					table.showColumn(TC);

					TC.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							selectColum(tableProperties,direction_name_direct,0);
							direction_name_direct = direction_name_direct*-1;

						}
					});


					TC = new TableColumn(table, SWT.CENTER);
					TC.setText(Messages.getString("ClassePropertyViewPart.3")); //$NON-NLS-1$
					TC.setWidth(200);
					table.showColumn(TC);
					TC.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {

							selectColum(tableProperties,direction_range_direct,1);

							direction_range_direct = direction_range_direct * -1;

						}
					});

					//ES
					tableProperties.addSelectionChangedListener(new ISelectionChangedListener() {

						public void selectionChanged(SelectionChangedEvent event) {
							TableItem[] sel = tableProperties.getTable().getSelection();

							if (sel != null){

								deleteAction.setEnabled(sel.length == 1);
							}
						}
					});

					tableProperties.addDoubleClickListener(proplistener);

					tableProperties.refresh();
				}
			}
		}
		{
			FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
			ScrolledForm form = toolkit.createScrolledForm(sashFormProp);
			GridLayout layout = new GridLayout(1, false);
			form.getBody().setLayout(layout);
			form.setText(Messages.getString("ClassePropertyViewPart.43")); //$NON-NLS-1$


			//this.form.setImage(this.image.createImage());
			toolkit.decorateFormHeading(form.getForm());

			Composite composite_heritedprop = new Composite(form.getBody(), SWT.NONE);
			composite_heritedprop.setLayoutData(new GridData(GridData.FILL_BOTH));
			composite_heritedprop.setLayout(new FillLayout(SWT.VERTICAL));
			{
				tableInheritedProperties = new TableViewer(composite_heritedprop, SWT.FULL_SELECTION |SWT.BORDER);
				tableInheritedProperties.setContentProvider(new TreePropertyContrentProvider());
				tableInheritedProperties.setLabelProvider(new TreePropertyLabelProvider());
				tableInheritedProperties.getTable().setHeaderVisible(true);
				tableInheritedProperties.addDoubleClickListener(proplistener);
				Table table = tableInheritedProperties.getTable();

				TableColumn TC = new TableColumn(table, SWT.CENTER);
				TC.setText(Messages.getString("ClassePropertyViewPart.4")); //$NON-NLS-1$
				TC.setWidth(200);
				table.showColumn(TC);
				TC.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						selectColum(tableInheritedProperties,direction_name_herited,0);
						direction_name_herited = direction_name_herited*-1;

					}
				});
				TC = new TableColumn(table, SWT.CENTER);
				TC.setText(Messages.getString("ClassePropertyViewPart.5")); //$NON-NLS-1$
				TC.setWidth(200);
				table.showColumn(TC);
				TC.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						selectColum(tableInheritedProperties,direction_range_herited,1);
						direction_range_herited = direction_range_herited*-1;

					}
				});

				tableInheritedProperties.refresh();

			}
		}
	}

	//

	private void createContextMenu() {
		MenuManager menuMgr = new MenuManager();
		menuMgr.add(deleteAction);
		Menu menu = menuMgr.createContextMenu(tableProperties.getTable());
		tableProperties.getControl().setMenu(menu);
	}

	//

	public void createActions(){
		deleteAction = new Action() { //$NON-NLS-1$

			public void run() {

				if (tableProperties.getSelection() != null) {

					IProperty prop = (IProperty) ((IStructuredSelection) tableProperties.getSelection()).getFirstElement();

					MessageBox msg = new MessageBox(getSite().getShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);

					String messag = Messages.getString("ClassePropertyViewPart.VoulezVousSupprimerLaPropriete"); //$NON-NLS-1$ //$NON-NLS-2$

					msg.setMessage(messag.replace("%prop%", prop.getLabel())); //$NON-NLS-1$

					int res = msg.open();

					if (res == SWT.OK) {

						org.dafoe.ontologiclevel.common.DatabaseAdapter.removeAssoc(Activator.getCurrentClass(), prop);

						tableProperties.setInput(Activator.getCurrentClass().getProperties());
					}
				}
			}
		};
		deleteAction.setEnabled(true);
		deleteAction.setToolTipText(Messages.getString("ClassesViewPart.13")); //$NON-NLS-1$
		deleteAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.DELETE_ID));

	}

	//

	/*
	private void fillContextMenu(IMenuManager mgr) {
		mgr.add(addSiblingAction);
		mgr.add(addChildAction);
		mgr.add(subsumeAction);
		mgr.add(new Separator());
		mgr.add(deleteAction);
		mgr.add(deleteRecursiveAction);
		mgr.add(removeSubsumeAction);
	}
	*/
	//

	private void selectColum(TableViewer tv, int direction, int colum_index ) {

		Table myTable = tv.getTable();
		if (direction <1) {

			tv.setComparator(new PropertyComparator(direction,colum_index));
			myTable.setSortColumn(myTable.getColumn(colum_index));
			myTable.setSortDirection(SWT.UP);

		} else if (direction == 1) {

			tv.setComparator(new PropertyComparator(direction,colum_index));
			myTable.setSortColumn(myTable.getColumn(colum_index));
			myTable.setSortDirection(SWT.DOWN);

		}
		tv.refresh();
	}

	//

	private void createClassDescriptionPart( Composite composite) {
		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
		ScrolledForm form = toolkit.createScrolledForm(composite);

		form.getBody().setLayout(new GridLayout(1,true));

		form.setText(Messages.getString("ClassePropertyViewPart.44")); //$NON-NLS-1$
		toolkit.decorateFormHeading(form.getForm());

		// equivalent classes

		AddElementWidget equAV = new AddElementWidget(form.getBody(), SWT.NONE);
		equAV.set_text("Equivalent classes");
		equAV.setEnabled(true);
		equAV.getEnvelope().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		ScrolledComposite scrollEquivalent = new ScrolledComposite(form.getBody(),SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrollEquivalent.setLayout(new GridLayout(1,false));

		scrollEquivalent.setExpandHorizontal(true);
		scrollEquivalent.setExpandVertical(true);

		scrollEquivalent.setMinSize(400,60);

		scrollEquivalent.setLayoutData(new GridData(GridData.FILL_BOTH));

		compEquivalent = new Composite(scrollEquivalent,SWT.NONE);
		scrollEquivalent.setContent(compEquivalent);
		compEquivalent.setLayout(new GridLayout(1,false));
		compEquivalent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false ,false));

		// super classes

		AddElementWidget GAW = new AddElementWidget(form.getBody(), SWT.NONE);
		GAW.set_text(Messages.getString("ClassePropertyViewPart.45")); //$NON-NLS-1$
		GAW.setEnabled(true);
		GAW.getEnvelope().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GAW.addGenericAddListener(new IGenericAddListener() {

			public void OnSelected(Object source) {

				IClass current = Activator.ontoControler.getCurrentClass();

				if (current != null) {
					SubsumeDialog dial = new SubsumeDialog(ClassePropertyViewPart.this
							.getSite().getShell(),Activator.getCurrentOntology());

					dial.SetClassToSubsume(current);

					if (dial.open() == IDialogConstants.OK_ID) {
						IClass mere = dial.getSelectedClass();
						mere.addChild(current);
						DatabaseAdapter.saveClass(ControlerFactoryImpl.getOntoControler().getCurrentOntology(), mere, null);
						refresh();
						//Activator.ontoControler.AddClasseParent(current,mere);
						org.dafoe.ontologiclevel.Activator.ontoControler.UpdateClassTree();

					}
				}
			}});

		{
			// zone concernant les superclasses

			ScrolledComposite scrollsuper = new ScrolledComposite(form.getBody(),SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			scrollsuper.setLayout(new GridLayout(1,false));

			scrollsuper.setExpandHorizontal(true);
			scrollsuper.setExpandVertical(true);

			scrollsuper.setMinSize(400,60);

			scrollsuper.setLayoutData(new GridData(GridData.FILL_BOTH));

			compsuper = new Composite(scrollsuper,SWT.NONE);
			scrollsuper.setContent(compsuper);
			compsuper.setLayout(new GridLayout(1,false));
			compsuper.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false ,false));

		}

	}

	//

	private void createClasseAnnotationPart( Composite composite) {

		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));

		ScrolledForm form = toolkit.createScrolledForm(composite);
		GridData myGD2 = new GridData(GridData.FILL_BOTH);
		form.setLayoutData(myGD2);
		form.setText(Messages.getString("ClassePropertyViewPart.46")); //$NON-NLS-1$
		
		toolkit.decorateFormHeading(form.getForm());

		form.getBody().setLayout(new GridLayout(1,true));

		Composite comp_fonctions = new Composite(form.getBody(), SWT.NONE);
		comp_fonctions.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comp_fonctions.setLayout(new GridLayout(3,true));
		comp_fonctions.setBackground(new Color(composite.getDisplay(), 255, 255, 255));
		{
			AddElementWidget GAW = new AddElementWidget(comp_fonctions, SWT.NONE);
			GAW.set_text(Messages.getString("ClassePropertyViewPart.47")); //$NON-NLS-1$
			GAW.setEnabled(true);
			//GAW.getEnvelope().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			GAW.addGenericAddListener(new IGenericAddListener() {

				public void OnSelected(Object source) {
					// creation d'annotation
					if(Activator.getCurrentClass() != null){

						AnnotationDialog2 dial = new AnnotationDialog2(getSite().getShell(),Activator.getCurrentClass());
						dial.open();
						refresh();

					}

				}});

			AddElementWidget GAW2 = new AddElementWidget(comp_fonctions, SWT.NONE);
			GAW2.set_text("Add level 2 object reference"); //$NON-NLS-1$
			GAW2.setEnabled(true);
			//GAW2.getEnvelope().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			GAW2.addGenericAddListener(new IGenericAddListener() {

				public void OnSelected(Object source) {
					// creation d'annotation

					if(Activator.getCurrentClass() != null){
						MappingDialog dial = new MappingDialog(getSite().getShell(),Activator.getCurrentClass());
						dial.open();
						refresh();
					}

				}});
		}

		scrolledAnnotation = new ScrolledComposite(form.getBody(),SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledAnnotation.setLayout(new GridLayout(1,false)); //VT
		
		scrolledAnnotation.setExpandHorizontal(true);
		scrolledAnnotation.setExpandVertical(true);
		scrolledAnnotation.setMinSize(400,60);
		
		scrolledAnnotation.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		compAnnotation = new Composite(scrolledAnnotation, SWT.NONE);
		scrolledAnnotation.setContent(compAnnotation);
		compAnnotation.setLayout(new GridLayout(1,false));
		compAnnotation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false ,false));

	
		/*FormLayout FL = new FormLayout();
		FL.marginWidth=0;
		FL.marginHeight=0;*/

		compAnnotation.setLayout(new GridLayout(1,false));

		//GAW.getEnvelope().setLayoutData(new GridData(SWT.LEFT,SWT.TOP,false,false));

	}

	//

	private void createUsagePart( Composite composite) {
		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
		ScrolledForm form = toolkit.createScrolledForm(composite);

		form.getBody().setLayout(new FillLayout(SWT.VERTICAL));

		form.setText(Messages.getString("ClassePropertyViewPart.48")); //$NON-NLS-1$
		toolkit.decorateFormHeading(form.getForm());


		Composite composite_description = new Composite(form.getBody(), SWT.NONE);
		composite_description.setLayout(new FillLayout(SWT.VERTICAL));
		composite_description.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));

		browser = new Browser(composite_description, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
		setBrowservalue(browser,Messages.getString("ClassePropertyViewPart.49")); //$NON-NLS-1$

		browser.addLocationListener(new LocationListener() {

			public void changed(LocationEvent event) {

				if (event.location.contains("?classe=")) { //$NON-NLS-1$
					java.util.regex.Pattern patern = java.util.regex.Pattern.compile("classe=[0-9]*"); //$NON-NLS-1$
					Matcher m = patern.matcher(event.location);

					while(m.find()) {
						String val = event.location.substring(m.start(),m.end());
						String [] tab=val.split("="); //$NON-NLS-1$
						int valid = Integer.parseInt(tab[1]);

						Activator.setCurrentClass(org.dafoe.ontologiclevel.common.DatabaseAdapter.getClasse(valid));

						refresh();
					}

				} else 
					if (event.location.contains("?objectproperty=")) { //$NON-NLS-1$
						java.util.regex.Pattern patern = java.util.regex.Pattern.compile("objectproperty=[0-9]*"); //$NON-NLS-1$
						Matcher m = patern.matcher(event.location);

						while(m.find()) {
							String val = event.location.substring(m.start(),m.end());
							String [] tab=val.split("="); //$NON-NLS-1$
							int valid = Integer.parseInt(tab[1]);
							Activator.setCurrentProperty(org.dafoe.ontologiclevel.common.DatabaseAdapter.getObjectProperty(valid));	
							refresh();
							showPropertyPage();
						}

					} else if (event.location.contains("?dataproperty=")) { //$NON-NLS-1$
						java.util.regex.Pattern patern = java.util.regex.Pattern.compile("dataproperty=[0-9]*"); //$NON-NLS-1$
						Matcher m = patern.matcher(event.location);

						while(m.find()) {
							String val = event.location.substring(m.start(),m.end());
							String [] tab=val.split("="); //$NON-NLS-1$
							int valid = Integer.parseInt(tab[1]);
							Activator.setCurrentProperty(org.dafoe.ontologiclevel.common.DatabaseAdapter.getDatatypeProperty(valid));
							refresh();
							showPropertyPage();
						}

					}

			}

			public void changing(LocationEvent event) {
			}});

	}

	//

	private void setBrowservalue(Browser b,String prefixe) {
		String html = "<HTML><HEAD><TITLE>HTML Test</TITLE></HEAD><BODY>"; //$NON-NLS-1$
		for (int i = 0; i < 10; i++) html += "<P><a href='#"+prefixe+":"+i+"'> This is "+prefixe+" "+i+"</a></P>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		html += "</BODY></HTML>"; //$NON-NLS-1$
	}

	//

	@Override
	public void setFocus() {
	}

	//

	IDoubleClickListener proplistener = new IDoubleClickListener() {

		public void doubleClick(DoubleClickEvent event) {

			if (event.getSelection() instanceof org.eclipse.jface.viewers.IStructuredSelection) {
				org.eclipse.jface.viewers.IStructuredSelection ss = (org.eclipse.jface.viewers.IStructuredSelection) event.getSelection();
				if (!ss.isEmpty()) {
					java.lang.Object selo = ss.getFirstElement();
					if (selo instanceof IProperty) {
						Activator.setCurrentProperty((IProperty)selo);
						showPropertyPage();
					}
				}
			}

		}};

		//

		private void showPropertyPage() {
			List<String> perpsectiveIdsFromContextLevel = org.dafoe.contextlevel.Activator.getDefault().getContextLevel().getPerpsectiveIdsFromContextLevel("ontologic"); //$NON-NLS-1$

			IPerspectiveRegistry perspectiveRegistry = PlatformUI.getWorkbench().getPerspectiveRegistry();
			IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry.findPerspectiveWithId(perpsectiveIdsFromContextLevel.get(1));
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().setPerspective(perspectiveWithId);
		}

		class CurrentClassChangeListener implements PropertyChangeListener {

			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("Change de classe ! "); //$NON-NLS-1$
				refresh();
			}


		}

		class SelectNoClassListener implements PropertyChangeListener {

			public void propertyChange(PropertyChangeEvent arg0) {
				refresh();
			}

		}
		class UpdateCurrentClasse implements PropertyChangeListener {

			public void propertyChange(PropertyChangeEvent arg0) {
				refresh();
			}

		}

		private static class PropertyComparator extends ViewerComparator {
			int sens =1;
			int column = 0;

			public PropertyComparator(int s,int c) {
				sens=s;
				column=c;
			}

			public int compare(Viewer viewer,Object e1,Object e2) {
				try {
					String s1=""; //$NON-NLS-1$
					String s2 =""; //$NON-NLS-1$

					if (e1 instanceof IObjectProperty) {
						IObjectProperty t1 = (IObjectProperty)e1;

						switch(column) {
						case 0:
							s1=t1.getLabel();
							break;
						case 1:
							s1=t1.getRange().getLabel();
							break;
						}
					} else {
						IDatatypeProperty t1 = (IDatatypeProperty)e1;
						switch(column) {
						case 0:
							s1=t1.getLabel();
							break;
						case 1:
							s1=t1.getRange().getLabel();
							break;
						}

					}
					if (e2 instanceof IObjectProperty) {
						IObjectProperty t2 = (IObjectProperty)e2;

						switch(column) {
						case 0:
							s2=t2.getLabel();
							break;
						case 1:
							s2=t2.getRange().getLabel();
							break;
						}
					} else {
						IDatatypeProperty t2 = (IDatatypeProperty)e2;
						switch(column) {
						case 0:
							s2=t2.getLabel();
							break;
						case 1:
							s2=t2.getRange().getLabel();
							break;
						}

					}
					Collator myCollator = Collator.getInstance();
					return myCollator.compare(s1, s2)*sens;
				} catch (Exception excep) {
					return 0;
				}

			}
		}

}


