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

package org.dafoe.ontologiclevel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import org.dafoe.ontologiclevel.Dialog.AnnotationDialog2;
import org.dafoe.ontologiclevel.Dialog.ChangeRangeDataTypeDialog;
import org.dafoe.ontologiclevel.Dialog.ChangeRangeObjectDialog;
import org.dafoe.ontologiclevel.Dialog.ChoixClasse;
import org.dafoe.ontologiclevel.Dialog.ChoixInverse;
import org.dafoe.ontologiclevel.Dialog.MappingDialog;
import org.dafoe.ontologiclevel.Elements.ClassAnnotation;
import org.dafoe.ontologiclevel.Elements.MappingUIElement;
import org.dafoe.ontologiclevel.Elements.PropertyDomain;
import org.dafoe.ui.widgets.AddElementWidget;
import org.dafoe.ui.widgets.ElementWidget;
import org.dafoe.ui.widgets.IGenericAddListener;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;


public class PropertyInfoViewPart extends ViewPart implements ISelectionProvider {


	private ArrayList<ElementWidget> annotationWidgets = new ArrayList<ElementWidget>();
	private ArrayList<ElementWidget> domainWidgets = new ArrayList<ElementWidget>();

	private Composite compAnnotation;

	private Text textLabel;
	private Text minCard;
	private Text maxCard;
	private Button btnChangeCard;
	
	private Button btnInverse;
	
	
	private Composite compDomain;

	private Button btnLabel;
	private Button btnSym;
	private Button btnTrans;
	private Label lbRange;

	private org.eclipse.swt.widgets.Link labelinverseDe;
	//Label labelinverseDe;

	private ScrolledComposite scrolledAnnotation;
	
	private ScrolledComposite scrolledDomain;

	private Composite containerInverse;

	private ListenerList postSelectionChangedListeners = new ListenerList();

	private ISelection currentSelection = StructuredSelection.EMPTY;


	private Browser browser;

	//

	public PropertyInfoViewPart() {
	}

	//

	private void deleteAnnotationsWidgets() {
		Iterator<ElementWidget> iter = annotationWidgets.iterator();
		while (iter.hasNext()) {
			iter.next().getEnvelope().dispose();
		}
		annotationWidgets.clear();
	}

	//

	private void showAnnotations() {
		int max_env = 0;
		
		if (Activator.getCurrentProperty() != null) {
			
			IProperty prop = Activator.getCurrentProperty();
			
			if (prop.getAnnotations() != null) {
				
				Iterator<IOntoAnnotation> itr = prop.getAnnotations().iterator();
				
				while (itr.hasNext()) {
				
					IOntoAnnotation anot = itr.next();
					ElementWidget EW = new ElementWidget(compAnnotation, SWT.NONE, true, true);
					ClassAnnotation CA = new ClassAnnotation(Activator.getCurrentProperty(), anot);
					CA.setLabel(Messages.getString("PropertyInfoViewPart.Anntation")); //$NON-NLS-1$

					EW.setElement(CA);
					EW.setEditToolTip(Messages.getString("ClassePropertyViewPart.0")); //$NON-NLS-1$
					EW.setDeleteToolTip(Messages.getString("ClassePropertyViewPart.1")); //$NON-NLS-1$
					GridData gd = new GridData(GridData.FILL_HORIZONTAL);

					EW.getEnvelope().setLayoutData(gd);

					max_env += EW.getEnvelope().getBounds().height+10;
					annotationWidgets.add(EW);
				}
			}
			
			if (prop.getMappedTerminoOntoObjects() != null) {

				Iterator<ITerminoOntoObject> itr2 = prop.getMappedTerminoOntoObjects().iterator();

				while (itr2.hasNext()) {
		
					ITerminoOntoObject tero = itr2.next();
					ElementWidget EW = new ElementWidget(compAnnotation, SWT.NONE, true, false);
					MappingUIElement CA = new MappingUIElement(Activator.getCurrentProperty(),tero);
					CA.setLabel(Messages.getString("PropertyInfoViewPart.Lien")); //$NON-NLS-1$

					EW.setElement(CA);
					EW.setEditToolTip(Messages.getString("ClassePropertyViewPart.0")); //$NON-NLS-1$
					EW.setDeleteToolTip(Messages.getString("ClassePropertyViewPart.1")); //$NON-NLS-1$
					GridData gd = new GridData(GridData.FILL_HORIZONTAL);

					EW.getEnvelope().setLayoutData(gd);

					max_env += EW.getEnvelope().getBounds().height+10;
					annotationWidgets.add(EW);
				}
			}

		}


		compAnnotation.pack();
		//scrolledAnnotation.setMinSize(400,60+max_env);
		scrolledAnnotation.setMinSize(400, 80+annotationWidgets.size()*(70));

	}

	//

	private void showLabel() {
		if (Activator.getCurrentProperty() != null) {
			textLabel.setText(Activator.getCurrentProperty().getLabel());
		}
		if (Activator.getCurrentProperty() != null) {
			if (Activator.getCurrentProperty() instanceof IObjectProperty) {
				IObjectProperty oprop = (IObjectProperty) Activator.getCurrentProperty();
				maxCard.setText(oprop.getMaximalCardinality().toString());
				minCard.setText(oprop.getMinimalCardinality().toString());
			} else {
				IDatatypeProperty oprop = (IDatatypeProperty) Activator.getCurrentProperty();
				maxCard.setText(oprop.getMaximalCardinality().toString());
				minCard.setText(oprop.getMinimalCardinality().toString());
			}
		}
	}

	//

	private void clearDomain() {
		Control[] ctl = compDomain.getChildren();
		for (int i=0;i<ctl.length;i++) {
			ctl[i].dispose();
		}
	}

	//

	private void showDomain() {

		if (Activator.getCurrentProperty().getDomains() == null) {
			return;
		}
		
		Set<IClass> domains = Activator.getCurrentProperty().getDomains();
		
		if (domains != null) {
			Iterator<IClass> itr = domains.iterator();

			while (itr.hasNext()) {
				ElementWidget EW = new ElementWidget(compDomain,SWT.NONE,true,false);
				//SupperClass SC = new SupperClass(itr.next(),null);
				PropertyDomain PD = new PropertyDomain(Activator.getCurrentProperty(),itr.next());
				EW.setElement(PD);
				GridData gd = new GridData(SWT.FILL,SWT.TOP,true,false);
				EW.getEnvelope().setLayoutData(gd);
				//compDomain.pack(); //VT

				//max_env += ew.getEnvelope().getBounds().height + 10;
				domainWidgets.add(EW);
			}
			
			compDomain.pack(); //VT
			scrolledDomain.setMinSize(400, 80+domainWidgets.size()*(70)); //VT
		}
	}

	//

	private void showCharac() {

		if (Activator.getCurrentProperty() instanceof IObjectProperty) {
			
			containerInverse.setVisible(true);
			btnSym.setVisible(true);
			btnTrans.setVisible(true);
			
			IObjectProperty op = ((IObjectProperty) Activator.getCurrentProperty()).getInverseOf();
			
			if (op!=null && op.getId()!=null && op.getId()!=0) {
				labelinverseDe.setText(Messages.getString("PropertyInfoViewPart.InserseDe")+op.getLabel()); //$NON-NLS-1$
			} else {
				labelinverseDe.setText(Messages.getString("PropertyInfoViewPart.InverseDeRien")); //$NON-NLS-1$
			}
			btnSym.setSelection(((IObjectProperty) Activator.getCurrentProperty()).isSymetric());
			btnTrans.setSelection(((IObjectProperty) Activator.getCurrentProperty()).isTransitive());

		} else  {
			containerInverse.setVisible(false);
			btnSym.setVisible(false);
			btnTrans.setVisible(false);

		}
		
		if (Activator.getCurrentProperty() instanceof IObjectProperty) {
			IObjectProperty op = (IObjectProperty) Activator.getCurrentProperty();

			if (op.getRange() != null) {
				lbRange.setText(Messages.getString("PropertyInfoViewPart.2")+op.getRange().getLabel()); //$NON-NLS-1$
			} else {
				lbRange.setText(Messages.getString("PropertyInfoViewPart.2")); //$NON-NLS-1$
			}
			
		} else if (Activator.getCurrentProperty() instanceof IDatatypeProperty) {
			IDatatypeProperty op = (IDatatypeProperty) Activator.getCurrentProperty();
			
			if (op.getRange()!=null) {
				lbRange.setText(Messages.getString("PropertyInfoViewPart.10")+op.getRange().getLabel()); //$NON-NLS-1$
			} else {
				lbRange.setText(Messages.getString("PropertyInfoViewPart.10")); //$NON-NLS-1$
			}
		}


	}

	//

	private String showClass(IClass classe) {
		if (classe==null) {
			return ""; //$NON-NLS-1$
		}
		try {
			return "<a href=\"#?classe="+classe.getId()+"\">"+classe.getLabel() +"</a>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} catch(Exception execp) {
			return ""; //$NON-NLS-1$
		}
	}

	//

	private String showDataProp(IDatatypeProperty prop) {
		String bc = "style2"; //$NON-NLS-1$
		String range = "";
		
		if (prop.getId().equals(Activator.getCurrentProperty().getId())) {
			bc = "style1"; //$NON-NLS-1$
		}

		if (prop.getRange() != null) {
			range = " (" + prop.getRange().getLabel() + ")";
		} 

		return "<a href=\"#?dataproperty="+prop.getId()+"\"><span class=\""+bc+"\">"+prop.getLabel() +"</span></a> (" + range; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}

	//

	private String showObjectProp(IObjectProperty prop) {
		String bc = "style2"; //$NON-NLS-1$
		String range = "";
		
		if (prop.getId().equals(Activator.getCurrentProperty().getId())) {
			bc = "style1"; //$NON-NLS-1$
		}
		
		if (prop.getRange() != null) {
			range = " (" + showClass(prop.getRange()) + ")";
		} 
		
		return "<a href=\"#?dataproperty="+prop.getId()+"\"><span class=\""+bc+"\">"+prop.getLabel() +"</span></a>" + range; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}

	//

	private String showProp(IProperty prop) {
		if (prop instanceof IObjectProperty) {
			return showObjectProp((IObjectProperty) prop);
		} else {
			return showDataProp((IDatatypeProperty) prop);
		}
	}

	//

	private void showUsage() {

		if (Activator.getCurrentProperty() == null) {
			browser.setText(""); //$NON-NLS-1$
			return;
		}

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

		String img  = "<img src=\"" + Activator.icone_data_property_path+"\">"; //$NON-NLS-1$ //$NON-NLS-2$
				
		if (Activator.getCurrentProperty() instanceof IObjectProperty) {
			img  = "<img src=\""+Activator.icone_object_property_path+"\">"; //$NON-NLS-1$ //$NON-NLS-2$
		} 


		html+="<b>&nbsp;" + img + "&nbsp;" + showProp(Activator.getCurrentProperty()) + "&nbsp;</b>"; //$NON-NLS-1$ //$NON-NLS-2$

		Iterator<IClass> itr = Activator.getCurrentProperty().getDomains().iterator();
		
		if (itr.hasNext()) {
			html+="<br/>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			while (itr.hasNext()) {
				IClass cl = itr.next();
				html+="<br/><b>&nbsp;"+img+"&nbsp;"+showProp(Activator.getCurrentProperty())+" domain "+showClass(cl)+"</b>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}

		}

		html += "</BODY></HTML>"; //$NON-NLS-1$
		browser.setText(html);
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
	
	private void refresh() {
		
		System.out.println(Activator.getCurrentProperty());
		
		if (Activator.getCurrentProperty() != null) {

			clearDomain();
			showDomain();

			deleteAnnotationsWidgets();
			showLabel();
			textLabel.setEnabled(true);
			btnLabel.setEnabled(true);

			showAnnotations();
			showCharac();
			showUsage();
			
			minCard.setEnabled(true);
			maxCard.setEnabled(true);
			btnChangeCard.setEnabled(true);
			btnInverse.setEnabled(true);
			
			lbRange.setVisible(true);
			btnSym.setVisible(true);
			btnTrans.setVisible(true);

		} else {
			
			textLabel.setText("");
			textLabel.setEnabled(false);
			btnLabel.setEnabled(false);
			
			clearDomain();
			deleteAnnotationsWidgets();
			showUsage();
			
			minCard.setEnabled(false);
			maxCard.setEnabled(false);
			btnChangeCard.setEnabled(false);
			btnInverse.setEnabled(false);
			
			lbRange.setVisible(false);
			btnSym.setVisible(false);
			btnTrans.setVisible(false);
			
		}

	}

	//

	@Override
	public void createPartControl(Composite parent) {
		
		FontRegistry fontRegistry = new FontRegistry(parent.getShell().getDisplay());

		fontRegistry.put("BIG", new FontData[]{new FontData("Arial", 10, SWT.BOLD)} ); //$NON-NLS-1$ //$NON-NLS-2$

		Activator.ontoControler.addPropertyChangeListener(
				org.dafoe.controler.factory.ControlerFactoryImpl.currentPropertyEvent, 
				new CurrentPropertyChangeListener());

		Composite comp = new Composite(parent,SWT.NONE);
		comp.setLayout(new FillLayout(SWT.VERTICAL));
		{
			SashForm sashForm = new SashForm(comp, SWT.BORDER);
			sashForm.setOrientation(SWT.VERTICAL);
			Composite composite1 = new Composite(sashForm, SWT.BORDER);
			composite1.setLayout(new FillLayout(SWT.HORIZONTAL));
			{
				TabFolder tabFolder = new TabFolder(composite1, SWT.NONE);
				{
					TabItem tbtmAnnotations = new TabItem(tabFolder, SWT.NONE);
					tbtmAnnotations.setText(Messages.getString("PropertyInfoViewPart.3")); //$NON-NLS-1$
					Composite composite = new Composite(tabFolder, SWT.NONE);
					composite.setLayout(new GridLayout(1,false));

					tbtmAnnotations.setControl(composite);
					Composite complabel = new Composite(composite, SWT.NONE);
					GridData myGD2 = new GridData(GridData.FILL_HORIZONTAL);
					//myGD2.heightHint=35;
					
					complabel.setLayoutData(myGD2 );
					complabel.setLayout(new GridLayout(3,false)); 
					Label lab = new Label(complabel,SWT.NONE);
					lab.setText(Messages.getString("PropertyInfoViewPart.SaisissezUnLabel")); //$NON-NLS-1$
					
					textLabel = new Text(complabel,SWT.BORDER);
					textLabel.setFont(fontRegistry.get("BIG"));
					GridData myGD = new GridData(GridData.FILL_BOTH);
					//myGD.heightHint=35;
					textLabel.setLayoutData(myGD);
					
					btnLabel = new Button(complabel,SWT.PUSH);
					btnLabel.setText(Messages.getString("PropertyInfoViewPart.Modifier")); //$NON-NLS-1$
					btnLabel.addSelectionListener(new SelectionListener(){

						public void widgetDefaultSelected(SelectionEvent e) {
						}

						public void widgetSelected(SelectionEvent e) {
							if(textLabel.getText()=="") { //$NON-NLS-1$

								MessageBox msg = new MessageBox(getSite().getShell(),SWT.ICON_WARNING | SWT.OK);
								msg.setMessage(Messages.getString("PropertyInfoViewPart.ErreurDeSaisie")); //$NON-NLS-1$
								msg.setText(Messages.getString("PropertyInfoViewPart.VousDevezSaisirUnLabel")); //$NON-NLS-1$
								msg.open();
								return;
							} else {
								if (Activator.getCurrentProperty()!=null) {
									Activator.getCurrentProperty().setLabel(textLabel.getText());
									org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(
											ControlerFactoryImpl.getOntoControler().getCurrentOntology(), Activator.getCurrentProperty(), null);
									refresh();
									Activator.ontoControler.updateCurrentProperty();
								}
							}
						}});

					createAnnotationPart(composite);

				}
				{
					TabItem tbtmPropertyUsage = new TabItem(tabFolder, SWT.NONE);
					tbtmPropertyUsage.setText(Messages.getString("PropertyInfoViewPart.4")); //$NON-NLS-1$
					{
						Composite composite = new Composite(tabFolder, SWT.NONE);
						composite.setLayout(new FillLayout(SWT.VERTICAL));
						tbtmPropertyUsage.setControl(composite);
						{
							createUsagePart(composite);
						}
					}
				}
			}

			Composite composite2 = new Composite(sashForm, SWT.BORDER);
			composite2.setLayout(new FillLayout(SWT.VERTICAL));
			sashForm.setWeights(new int[] {180, 278});

			SashForm sashForm2 = new SashForm(composite2, SWT.BORDER);
			sashForm2.setOrientation(SWT.HORIZONTAL);


			createBoolPart(sashForm2);
			createDescriptionPart(sashForm2);

			sashForm2.setWeights(new int[] {100, 278});
		}

		refresh();
		getSite().setSelectionProvider(this);
	}

	//
	
	void createBoolPart(Composite composite) {
		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
		ScrolledForm form = toolkit.createScrolledForm(composite);

		form.getBody().setLayout(new FillLayout(SWT.VERTICAL));
		form.getBody().setBackground(new Color(Display.getCurrent(),255,255,255));
		form.setText(Messages.getString("PropertyInfoViewPart.11")); //$NON-NLS-1$
		toolkit.decorateFormHeading(form.getForm());


		Composite props = new Composite(form.getBody(), SWT.NONE);
		props.setLayout(new GridLayout(1,true));

		Font f = new Font(Display.getCurrent(),"verdana",10,SWT.BOLD); //$NON-NLS-1$


		Composite compRange =  new Composite(props,SWT.BORDER);
		compRange.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		compRange.setLayout(new GridLayout(2,false));
		lbRange = new Label(compRange, SWT.NONE);


		lbRange.setText(""); //$NON-NLS-1$
		lbRange.setFont(f);
		lbRange.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button changerange = new Button(compRange,SWT.PUSH);
		changerange.setText(Messages.getString("PropertyInfoViewPart.BouttonRecherche")); //$NON-NLS-1$
		changerange.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				if (Activator.getCurrentProperty() instanceof IDatatypeProperty) {
					ChangeRangeDataTypeDialog dial = new ChangeRangeDataTypeDialog(e.display.getActiveShell(), Activator.getCurrentOntology());
					dial.setProperty((IDatatypeProperty) Activator.getCurrentProperty());
					dial.open();
					refresh();
				} else if (Activator.getCurrentProperty() instanceof IObjectProperty) {
					ChangeRangeObjectDialog dial = new ChangeRangeObjectDialog(e.display.getActiveShell(), Activator.getCurrentOntology());
					dial.setProperty((IObjectProperty) Activator.getCurrentProperty());
					dial.open();
					refresh();

				}
			}});


		Composite compCard = new Composite(props,SWT.BORDER);
		compCard.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		compCard.setLayout(new GridLayout(1,true));
		Label labCard = new Label(compCard,SWT.NONE);
		labCard.setText(Messages.getString("PropertyInfoViewPart.Cardinalite")); //$NON-NLS-1$
		labCard.setFont(f);
		GridData GDLabcard = new GridData(GridData.FILL_HORIZONTAL);
		GDLabcard.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
		labCard.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_BEGINNING));

		Composite compMin = new Composite(compCard,SWT.NONE);
		compMin.setLayout(new GridLayout(1,true));
		{
			Label labmin = new Label(compMin,SWT.NONE);
			labmin.setText(Messages.getString("PropertyInfoViewPart.Min")); //$NON-NLS-1$

			minCard = new Text(compMin,SWT.BORDER);
			GridData GDmin = new GridData(GridData.FILL_HORIZONTAL);
			GDmin.heightHint=15;
			minCard.setLayoutData(GDmin);
		}
		Composite compMax = new Composite(compCard,SWT.NONE);
		compMax.setLayout(new GridLayout(1,true));
		{
			Label labmax = new Label(compMax,SWT.NONE);
			labmax.setText(Messages.getString("PropertyInfoViewPart.Max")); //$NON-NLS-1$

			maxCard = new Text(compMax,SWT.BORDER);
			GridData GDmax = new GridData(GridData.FILL_HORIZONTAL);
			GDmax.heightHint=15;
			maxCard.setLayoutData(GDmax);
			maxCard.setToolTipText("Please enter -1 to specify INFINITE cardinality");
		}
		btnChangeCard = new Button(compCard,SWT.PUSH);
		btnChangeCard.setText(Messages.getString("PropertyInfoViewPart.Modifier")); //$NON-NLS-1$
		btnChangeCard.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(getSite().getShell(), SWT.ICON_ERROR );
				int min =1;
				int max =1;
				if (minCard.getText()=="") { //$NON-NLS-1$

					messageBox.setMessage(Messages.getString("PropertyInfoViewPart.VousDevezSaisirMin")); //$NON-NLS-1$
					messageBox.open();
					return;

				}

				try {
					min = Integer.parseInt(minCard.getText());

				} catch (Exception excep) {
					messageBox.setMessage(Messages.getString("PropertyInfoViewPart.MinPasValide")); //$NON-NLS-1$
					messageBox.open();
					return;
				}
				if (min<0) {
					messageBox.setMessage(Messages.getString("PropertyInfoViewPart.MinSupEgale0")); //$NON-NLS-1$
					messageBox.open();
					return;
				}
				if (maxCard.getText()=="") { //$NON-NLS-1$

					messageBox.setMessage(Messages.getString("PropertyInfoViewPart.CardMax")); //$NON-NLS-1$
					messageBox.open();
					return;

				}

				try {
					max = Integer.parseInt(maxCard.getText());

				} catch (Exception excep) {
					messageBox.setMessage(Messages.getString("PropertyInfoViewPart.MaxPasValide")); //$NON-NLS-1$
					messageBox.open();
					return;
				}
				if (max<-1) {
					messageBox.setMessage(Messages.getString("PropertyInfoViewPart.MaxSupEgal_1")); //$NON-NLS-1$
					messageBox.open();
					return;
				}
				if (max!=-1) {
					if (min>max) {
						messageBox.setMessage(Messages.getString("PropertyInfoViewPart.MaxSupMin")); //$NON-NLS-1$
						messageBox.open();
						return;
					}
				}

				if (Activator.getCurrentProperty()!=null) {
					if (Activator.getCurrentProperty() instanceof IObjectProperty) {
						IObjectProperty oprop = (IObjectProperty) Activator.getCurrentProperty();
						oprop.setMaximalCardinality(max);
						oprop.setMinimalCardinality(min);
						org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(
								ControlerFactoryImpl.getOntoControler().getCurrentOntology(), oprop, null);
					} else {
						IDatatypeProperty oprop = (IDatatypeProperty) Activator.getCurrentProperty();
						oprop.setMaximalCardinality(max);
						oprop.setMinimalCardinality(min);
						org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(
								ControlerFactoryImpl.getOntoControler().getCurrentOntology(), oprop, null);
					}
				}

			}});



		containerInverse = new Composite(props, SWT.BORDER);
		containerInverse.setLayout(new GridLayout(4,true));
		containerInverse.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		containerInverse.setVisible(false);

		labelinverseDe = new Link(containerInverse, SWT.NONE);
		labelinverseDe.setText("inverse de rien"); //$NON-NLS-1$
		labelinverseDe.setFont(f);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		labelinverseDe.setLayoutData(gd);

		labelinverseDe.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {
				Activator.setCurrentProperty(((IObjectProperty) Activator.getCurrentProperty()).getInverseOf());
			}});

		labelinverseDe.addMouseTrackListener(new MouseTrackListener() {

			public void mouseEnter(MouseEvent e) {
				labelinverseDe.setForeground(e.display.getSystemColor(SWT.COLOR_BLUE));
			}

			public void mouseExit(MouseEvent e) {
				labelinverseDe.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
			}

			public void mouseHover(MouseEvent e) {
			}});



		btnInverse = new Button(containerInverse,SWT.PUSH);
		btnInverse.setText(Messages.getString("PropertyInfoViewPart.PetitPoints")); //$NON-NLS-1$
		btnInverse.setFont(f);
		btnInverse.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		btnInverse.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				// montre la boite de choix de l'inverse

				IObjectProperty current =(IObjectProperty) Activator.ontoControler.getCurrentProperty();
				if (current!=null) {
					ChoixInverse dial = new ChoixInverse(PropertyInfoViewPart.this
							.getSite().getShell());

					dial.SetPropertytoInverse(current,Activator.getCurrentOntology());
					if (dial.open()==IDialogConstants.OK_ID) {
						IObjectProperty mere = dial.getSelectedProperty();
						current.setInverseOf(mere);
						mere.setInverseOf(current);
						org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(
								ControlerFactoryImpl.getOntoControler().getCurrentOntology(), current, null);
						org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(
								ControlerFactoryImpl.getOntoControler().getCurrentOntology(), mere, null);

						refresh();
					}
				}

			}});


		btnSym = new Button(props, SWT.CHECK);

		btnSym.setSelection(false);
		btnSym.setText(Messages.getString("PropertyInfoViewPart.42")); //$NON-NLS-1$
		btnSym.setFont(f);
		btnSym.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		btnSym.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				if (Activator.getCurrentProperty() instanceof IObjectProperty) {
					((IObjectProperty) Activator.getCurrentProperty()).setSymetric(((Button) e.getSource()).getSelection());
					org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(
							ControlerFactoryImpl.getOntoControler().getCurrentOntology(), Activator.getCurrentProperty(), null);
				}
			}});

		btnTrans = new Button(props, SWT.CHECK);

		btnTrans.setSelection(false);
		btnTrans.setText(Messages.getString("PropertyInfoViewPart.43")); //$NON-NLS-1$
		btnTrans.setFont(f);
		btnTrans.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		btnTrans.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				if (Activator.getCurrentProperty() instanceof IObjectProperty) {
					((IObjectProperty) Activator.getCurrentProperty()).setTransitive(((Button) e.getSource()).getSelection());
					org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(
							ControlerFactoryImpl.getOntoControler().getCurrentOntology(), Activator.getCurrentProperty(), null);
				}
			}});

	}

	//

	void createDescriptionPart( Composite composite) {
		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
		ScrolledForm form = toolkit.createScrolledForm(composite);

		form.getBody().setLayout(new FillLayout(SWT.VERTICAL));
		form.getBody().setBackground(new Color(Display.getCurrent(),255,255,255));

		form.setText(Messages.getString("PropertyInfoViewPart.5")); //$NON-NLS-1$
		toolkit.decorateFormHeading(form.getForm());


		Composite compositeDescription = new Composite(form.getBody(), SWT.NONE);
		compositeDescription.setLayout(new GridLayout(1,true));
		//composite_description.setLayoutData(new GridData(GridData.FILL_BOTH));
		compositeDescription.setBackground(new Color(Display.getCurrent(),255,255,255));

		AddElementWidget GAW = new AddElementWidget(compositeDescription, SWT.NONE);
		GAW.set_text(Messages.getString("PropertyInfoViewPart.6")); //$NON-NLS-1$
		GAW.setEnabled(true);
		GAW.getEnvelope().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GAW.addGenericAddListener(new IGenericAddListener() {

			public void OnSelected(Object source) {
				
				if (Activator.getCurrentProperty() != null) {

					ChoixClasse dial = new ChoixClasse(getSite().getShell(),Activator.getCurrentOntology());
					if (dial.open()==Window.OK) {
						IClass C = dial.getSelectedClass();
						if (C!=null) {
							IProperty prop = Activator.getCurrentProperty();
							if (prop!=null) {
								prop.addDomain(C);
								org.dafoe.ontologiclevel.common.DatabaseAdapter.saveProperty(
										ControlerFactoryImpl.getOntoControler().getCurrentOntology(), prop, null);
								refresh();
								Activator.setCurrentClass(C);

							}
						}
					}
				}
			}});

		{
			/// zone concernant les domaines

			//ScrolledComposite 
			scrolledDomain = new ScrolledComposite(compositeDescription,SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			scrolledDomain.setLayout(new GridLayout(1,false));
			scrolledDomain.setLayoutData(new GridData(GridData.FILL_BOTH));

			scrolledDomain.setExpandHorizontal(true);
			scrolledDomain.setExpandVertical(true);

			scrolledDomain.setMinSize(400,60);

			compDomain = new Composite(scrolledDomain,SWT.NONE);
			//compDomain.setBackground(new Color(Display.getCurrent(),255,255,255));
			scrolledDomain.setContent(compDomain);
			compDomain.setLayout(new GridLayout(1,false));
			compDomain.setLayoutData(new GridData(GridData.FILL_BOTH));

		}
	}

	// Build the Annotation section in the Annotations Tab
	
	private void createAnnotationPart( Composite composite) {
		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
		ScrolledForm form = toolkit.createScrolledForm(composite);
		GridData myGD2 = new GridData(GridData.FILL_BOTH);
		form.setLayoutData(myGD2);
		form.setText(Messages.getString("PropertyInfoViewPart.7")); //$NON-NLS-1$
		toolkit.decorateFormHeading(form.getForm());

		form.getBody().setLayout(new GridLayout(1,true));

		Composite compFonctions = new Composite(form.getBody(), SWT.NONE);
		compFonctions.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		compFonctions.setLayout(new GridLayout(3,true));
		compFonctions.setBackground(new Color(composite.getDisplay(),255,255,255));
		{
			AddElementWidget GAW = new AddElementWidget(compFonctions, SWT.NONE);
			GAW.set_text(Messages.getString("PropertyInfoViewPart.8")); //$NON-NLS-1$
			GAW.setEnabled(true);
			//GAW.getEnvelope().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			GAW.addGenericAddListener(new IGenericAddListener() {

				public void OnSelected(Object source) {
					// creation d'annotation
					
					if (Activator.getCurrentProperty() != null) {
					
						AnnotationDialog2 dial = new AnnotationDialog2(getSite().getShell(),Activator.getCurrentProperty());
						dial.open();
						refresh();
					}

				}});
			
			AddElementWidget GAW2 = new AddElementWidget(compFonctions, SWT.NONE);
			GAW2.set_text("Add level 2 object reference"); //$NON-NLS-1$
			GAW2.setEnabled(true);
			//GAW2.getEnvelope().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			GAW2.addGenericAddListener(new IGenericAddListener() {

				public void OnSelected(Object source) {
					// creation d'annotation

					if (Activator.getCurrentProperty() != null) {
						
						MappingDialog dial = new MappingDialog(getSite().getShell(),Activator.getCurrentProperty());
						dial.open();
						refresh();

					}
				}});
		}

		scrolledAnnotation = new ScrolledComposite(form.getBody(),SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledAnnotation.setLayoutData(new GridData(GridData.FILL_BOTH));

		scrolledAnnotation.setMinSize(1000,1000);
		scrolledAnnotation.setSize(1000,1000);

		compAnnotation = new Composite(scrolledAnnotation, SWT.NONE);

		scrolledAnnotation.setContent(compAnnotation);

		scrolledAnnotation.setExpandHorizontal(true);
		scrolledAnnotation.setExpandVertical(true);
		scrolledAnnotation.setMinSize(400,20);

		compAnnotation.setLayout(new GridLayout(1,false));

	}

	//
	
	void createUsagePart( Composite composite) {
		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
		ScrolledForm form = toolkit.createScrolledForm(composite);

		form.getBody().setLayout(new FillLayout(SWT.VERTICAL));

		form.setText(Messages.getString("PropertyInfoViewPart.9")); //$NON-NLS-1$
		toolkit.decorateFormHeading(form.getForm());


		Composite compositeDescription = new Composite(form.getBody(), SWT.NONE);
		compositeDescription.setLayout(new FillLayout(SWT.VERTICAL));
		compositeDescription.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));

		browser = new Browser(compositeDescription, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
		setBrowservalue(browser,"Property"); //$NON-NLS-1$

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
						//StructuredSelection SS= new StructuredSelection(Activator.currentClasse);
						//getSite().getSelectionProvider().setSelection(SS);
						refresh();
						showClassPage();
					}

				}
				//SetBrowservalue(browser,"Classe");
			}

			public void changing(LocationEvent event) {
			}});

	}

	//
	
	private void setBrowservalue(Browser b,String prefixe) {
		String html = "<HTML><HEAD><TITLE>HTML Test</TITLE></HEAD><BODY>"; //$NON-NLS-1$
		for (int i = 0; i < 10; i++) html += "<P><a href='#"+prefixe+":"+i+"'> This is "+prefixe+" "+i+"</a></P>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		html += "</BODY></HTML>"; //$NON-NLS-1$
		//b.setText(html);
		//b.setUrl("http://info.francetelevisions.fr/");
	}

	//
	
	@Override
	public void setFocus() {
	}

	//
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		postSelectionChangedListeners.add(listener);
	}


	//
	
	public ISelection getSelection() {
		return currentSelection;
	}

	//

	public void removeSelectionChangedListener(
		ISelectionChangedListener listener) {
		postSelectionChangedListeners.remove(listener);
	}

	//

	public void setSelection(ISelection selection) {
		currentSelection = selection;
		Object[] listeners = postSelectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			final SelectionChangedEvent event =new SelectionChangedEvent(this, selection);
			SafeRunnable.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(event);
				}
			});
		}
	}

	//
	
	private void showClassPage() {
		List<String> perpsectiveIdsFromContextLevel = org.dafoe.contextlevel.Activator.getDefault().getContextLevel().getPerpsectiveIdsFromContextLevel("ontologic"); //$NON-NLS-1$

		IPerspectiveRegistry perspectiveRegistry = PlatformUI
		.getWorkbench().getPerspectiveRegistry();
		IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
		.findPerspectiveWithId(perpsectiveIdsFromContextLevel.get(0));

		PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getActivePage().setPerspective(perspectiveWithId);
	}

	//
	
	class CurrentPropertyChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			refresh();
		}
	}
}
