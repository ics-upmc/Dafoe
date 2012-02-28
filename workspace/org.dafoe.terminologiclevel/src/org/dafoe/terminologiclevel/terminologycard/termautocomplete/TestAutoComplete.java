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

package org.dafoe.terminologiclevel.terminologycard.termautocomplete;

import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class TestAutoComplete {

	private static List<ITerm> terms = new ArrayList<ITerm>();

	private static Composite composite = null;
	
	public static String ID = "net.sf.swtaddons.tests.UITestView";
	
	private static AutocompleteTermSelector auto;
	
	public static void initDataSource(){
		IDataSource con= new DataSource("jdbc:postgresql://localhost:5432/DAFOE2","postgres","postgres");

		SessionFactoryImpl.openDynamicSession(con);
	}

	//

	static Session getDafoeSession()  {
		initDataSource();		
		//VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	//

	public static List<ITerm> getAllTerms() throws HibernateException{

		Session hSession = getDafoeSession();

		Transaction tx = hSession.beginTransaction();

		terms =  hSession.createCriteria(TermImpl.class).list();

		tx.commit();

		return terms;
	}

	//

	public static void createContents(Shell shell){
	    shell.setLayout(new GridLayout(1, false));
	    shell.setSize(400, 400);
		shell.setText("ComboViewer auto-completion");


		composite  = new Composite(shell, SWT.NONE);
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		gl.makeColumnsEqualWidth = true;
		composite.setLayout(gl);
		composite.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));

		Label label2 = new Label(composite, SWT.NONE);
		label2.setText("Autocomplete Selector:");
		label2.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		
		final Text text2 = new Text(composite, SWT.SINGLE | SWT.BORDER);
		text2.setText("");
		
		auto = new AutocompleteTermSelector(text2, terms);
		
		
		text2.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent me){
				System.out.println("ModifyEvent generated");
			}
		});
		
		text2.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR){
									
					text2.selectAll();
					
					System.out.println(auto.getTermFromSelectedLabel(text2.getText()));
					
				} 
				
			}

			public void keyReleased(KeyEvent e) {
				
			}
			
		});
		
	}

	//

	class ComboContentProvider  {

	}
	//

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);

		terms = getAllTerms();
		
		createContents(shell);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();

	}
}
