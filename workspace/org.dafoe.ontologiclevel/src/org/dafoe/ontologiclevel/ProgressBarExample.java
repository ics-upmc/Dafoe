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
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * This class demonstrates ProgressBar
 */
public class ProgressBarExample {
  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setLayout(new GridLayout());
    
    // Create a smooth progress bar
    ProgressBar pb1 = new ProgressBar(shell, SWT.HORIZONTAL | SWT.SMOOTH);
    pb1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    pb1.setMinimum(0);
    pb1.setMaximum(30);
    
    // Create an indeterminate progress bar
    ProgressBar pb2 = new ProgressBar(shell, SWT.HORIZONTAL | SWT.INDETERMINATE);
    pb2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    
    // Start the first progress bar
    new LongRunningOperation(display, pb1).start();
    
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}
  
/**
 * This class simulates a long running operation
 */
class LongRunningOperation extends Thread {
  private Display display;
  private ProgressBar progressBar;

  public LongRunningOperation(Display display, ProgressBar progressBar) {
    this.display = display;
    this.progressBar = progressBar;
  }
  
  public void run() {
    // Perform work here--this operation just sleeps
    for (int i = 0; i < 30; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // Do nothing
      }
      display.asyncExec(new Runnable() {
        public void run() {
          if (progressBar.isDisposed()) return;
          
          // Increment the progress bar
          progressBar.setSelection(progressBar.getSelection() + 1);
        }
      });
    }
  }
}
