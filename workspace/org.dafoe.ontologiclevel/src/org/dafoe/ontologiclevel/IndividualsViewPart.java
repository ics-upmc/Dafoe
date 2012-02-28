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



import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

public class IndividualsViewPart extends ViewPart {

	public IndividualsViewPart() {
		
	}
		
	public void createPartControl(Composite parent) {
		Composite composite_localprop = new Composite(parent, SWT.NONE);

		GridData GDParent = new GridData(GridData.FILL_HORIZONTAL);
		
		composite_localprop.setLayoutData(GDParent);
		composite_localprop.setLayout(new GridLayout(1,true));
		{
			Composite composite_bouton = new Composite(composite_localprop, SWT.NONE);
			
			GridData GDbuttons = new GridData(GridData.FILL_HORIZONTAL);
			
			composite_bouton.setLayoutData(GDbuttons);
			
			composite_bouton.setLayout(new GridLayout(2, false));
			{
				Button btnAdd = new Button(composite_bouton, SWT.NONE);
				btnAdd.setText(Messages.getString("IndividualsViewPart.0")); //$NON-NLS-1$
			}
			{
				Button btnDelete = new Button(composite_bouton, SWT.NONE);
				btnDelete.setText(Messages.getString("IndividualsViewPart.1")); //$NON-NLS-1$
			}
		}
		{
			Composite composite_arbre = new Composite(composite_localprop, SWT.NONE);
			GridData GDarbres = new GridData(GridData.FILL_BOTH);
			composite_arbre.setLayoutData(GDarbres);
			composite_arbre.setLayout(new GridLayout(1,true));
			{
				Tree tree = new Tree(composite_arbre, SWT.BORDER);
				GridData GDtree = new GridData(GridData.FILL_BOTH);
				tree.setLayoutData(GDtree);
				{
					TreeItem trtmP1 = new TreeItem(tree, SWT.NONE);
					//trtmP1.setImage(ResourceManager.getPluginImage("org.dafoe.ontologiclevel", "icons/dataproperty.gif")); //$NON-NLS-1$ //$NON-NLS-2$
					
					trtmP1.setImage(org.dafoe.ontologiclevel.Activator.getDefault()
					.getImageRegistry().get(
							org.dafoe.ontologiclevel.Activator.DATA_PROPERTIES_ID));
					trtmP1.setText("P1"); //$NON-NLS-1$
					trtmP1.addListener(SWT.MouseEnter, new Listener() {

						public void handleEvent(Event event) {
							// TODO Auto-generated method stub
							System.out.println("ici"); //$NON-NLS-1$
						}});
					
					
				}
				
				
			}
		}
	}

	public void setFocus() {
	}
}
