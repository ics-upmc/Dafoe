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

import org.dafoe.ontologiclevel.Elements.ClassAnnotation;
import org.dafoe.ui.widgets.AddElementWidget;
import org.dafoe.ui.widgets.ElementWidget;
import org.dafoe.ui.widgets.IGenericAddListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.part.ViewPart;

public class ContentViewPart extends ViewPart {

	protected int nb_annotation;

	private Composite comp_annotation;
	private ScrolledComposite scrolledAnnotation;

	@Override
	public void createPartControl(Composite parent) {
		
		WorkbenchWindow ww = (WorkbenchWindow) this.getSite().getWorkbenchWindow();
		MenuItem [] items = ww.getMenuManager().getMenu().getItems();
		for (int i=0;i<items.length;i++) {
		//	items[i].setEnabled(false);
		}
		
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
					tbtmAnnotations.setText(Messages.getString("ContentViewPart.0")); //$NON-NLS-1$
					Composite composite = new Composite(tabFolder, SWT.NONE);
					composite.setLayout(new FillLayout(SWT.VERTICAL));
					tbtmAnnotations.setControl(composite);
					CreateAnnotationPart(composite);
					
				}
				{
					TabItem tbtmPropertyUsage = new TabItem(tabFolder, SWT.NONE);
					tbtmPropertyUsage.setText(Messages.getString("ContentViewPart.1")); //$NON-NLS-1$
					{
						Composite composite = new Composite(tabFolder, SWT.NONE);
						composite.setLayout(new FillLayout(SWT.VERTICAL));
						tbtmPropertyUsage.setControl(composite);
						{
							CreateUsagePart(composite);
						}
					}
				}
			}
			
			Composite composite2 = new Composite(sashForm, SWT.BORDER);
			composite2.setLayout(new FillLayout(SWT.VERTICAL));
			sashForm.setWeights(new int[] {180, 278});
			
			CreateDescriptionPart(composite2);
		}
		
	}
	
	
	void CreateDescriptionPart( Composite composite) {
		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
		ScrolledForm form = toolkit.createScrolledForm(composite);
		
	    form.getBody().setLayout(new FillLayout(SWT.VERTICAL));
	    
	    form.setText(Messages.getString("ContentViewPart.2")); //$NON-NLS-1$
	    toolkit.decorateFormHeading(form.getForm());
	    
	    
		Composite composite_description = new Composite(form.getBody(), SWT.NONE);
		composite_description.setLayout(new FillLayout(SWT.VERTICAL));
		composite_description.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
		
		{
			/// zone concernant les superclasses
			
			ScrolledComposite scrollsuper = new ScrolledComposite(composite_description,SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			scrollsuper.setLayout(new GridLayout(1,false));
			
			
			scrollsuper.setExpandHorizontal(true);
			scrollsuper.setExpandVertical(true);
			
			scrollsuper.setMinSize(400,60);
			
			//scrollsuper.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false ,false));
			
			Composite compsuper = new Composite(scrollsuper,SWT.NONE);
			scrollsuper.setContent(compsuper);
			compsuper.setLayout(new GridLayout(1,false));
			compsuper.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false ,false));
			
			
			AddElementWidget GAW = new AddElementWidget(compsuper, SWT.NONE);
			GAW.set_text(Messages.getString("ContentViewPart.3")); //$NON-NLS-1$
			GAW.setEnabled(true);
			GAW.getEnvelope().setLayoutData(new GridData(SWT.LEFT,SWT.TOP,false,false));
		}
		
		
	}

	void CreateAnnotationPart( Composite composite) {
		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
		ScrolledForm form = toolkit.createScrolledForm(composite);
		form.setText(Messages.getString("ContentViewPart.4")); //$NON-NLS-1$
		toolkit.decorateFormHeading(form.getForm());
		
		form.getBody().setLayout(new FillLayout(SWT.VERTICAL));
		
		scrolledAnnotation = new ScrolledComposite(form.getBody(),SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		
		
		scrolledAnnotation.setMinSize(1000,1000);
		scrolledAnnotation.setSize(1000,1000);
		
		comp_annotation = new Composite(scrolledAnnotation, SWT.NONE);

		scrolledAnnotation.setContent(comp_annotation);
		
		scrolledAnnotation.setExpandHorizontal(true);
		scrolledAnnotation.setExpandVertical(true);
		scrolledAnnotation.setMinSize(400,20);

		
		/*FormLayout FL = new FormLayout();
		FL.marginWidth=0;
		FL.marginHeight=0;*/
		
		comp_annotation.setLayout(new GridLayout(1,false));
		
		
		
	
		AddElementWidget GAW = new AddElementWidget(comp_annotation, SWT.NONE);
		GAW.set_text(Messages.getString("ContentViewPart.5")); //$NON-NLS-1$
		GAW.setEnabled(true);
		GAW.addGenericAddListener(new IGenericAddListener() {

			public void OnSelected(Object source) {

				ElementWidget EW = new ElementWidget(comp_annotation,SWT.NONE,true,true);
				ClassAnnotation CA = new ClassAnnotation(Activator.getCurrentClass(),null);
				EW.setElement(CA);
				EW.setEditToolTip(Messages.getString("ContentViewPart.6")); //$NON-NLS-1$
				EW.setDeleteToolTip(Messages.getString("ContentViewPart.7")); //$NON-NLS-1$
				GridData gd = new GridData(SWT.FILL,SWT.TOP,true,false);
				
				EW.getEnvelope().setLayoutData(gd);
				
				
				comp_annotation.redraw();
				comp_annotation.pack();
				nb_annotation++;
				scrolledAnnotation.setMinSize(400,60+nb_annotation*(EW.getEnvelope().getBounds().height+10));
				
				System.out.println(Messages.getString("ContentViewPart.8")); //$NON-NLS-1$
			}});
		
		
		
		GAW.getEnvelope().setLayoutData(new GridData(SWT.LEFT,SWT.TOP,false,false));
		
	}
	
	
	void CreateUsagePart( Composite composite) {
		FormToolkit toolkit = new FormToolkit(UIConstants.FORM_COLOR(composite.getDisplay()));
		ScrolledForm form = toolkit.createScrolledForm(composite);
		
	    form.getBody().setLayout(new FillLayout(SWT.VERTICAL));
	    
	    form.setText(Messages.getString("ContentViewPart.9")); //$NON-NLS-1$
	    toolkit.decorateFormHeading(form.getForm());
	    
	    
		Composite composite_description = new Composite(form.getBody(), SWT.NONE);
		composite_description.setLayout(new FillLayout(SWT.VERTICAL));
		composite_description.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
	    
	    final Browser browser = new Browser(composite_description, SWT.NONE);
	    browser.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
	    SetBrowservalue(browser,"Item"); //$NON-NLS-1$
	 
		browser.addLocationListener(new LocationListener() {

			public void changed(LocationEvent event) {
				System.out.println(event.location);
				//SetBrowservalue(browser,"Classe");
			}

			public void changing(LocationEvent event) {
				
			}});
		
	}
	
	void SetBrowservalue(Browser b,String prefixe) {
		   String html = "<HTML><HEAD><TITLE>HTML Test</TITLE></HEAD><BODY>"; //$NON-NLS-1$
			for (int i = 0; i < 10; i++) html += "<P><a href='#"+prefixe+":"+i+"'> This is "+prefixe+" "+i+"</a></P>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			html += "</BODY></HTML>"; //$NON-NLS-1$
			b.setText(html);
	}
	

	@Override
	public void setFocus() {
	}

}
