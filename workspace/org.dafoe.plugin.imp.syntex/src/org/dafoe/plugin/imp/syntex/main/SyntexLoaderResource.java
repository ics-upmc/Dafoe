package org.dafoe.plugin.imp.syntex.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dafoe.application.extensionpoint.terminological.AbstractTerminoImportationWidget;
import org.dafoe.plugin.imp.syntex.adaptater.Services;
import org.dafoe.plugin.imp.syntex.model.SyntexDocument;
import org.dafoe.plugin.imp.syntex.model.SyntexResourceTerminological;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class SyntexLoaderResource extends AbstractTerminoImportationWidget {

	protected TableViewer fileTableViewer;
	protected static Text txtResourceName ;

	protected static Text txtDirectory ;
	protected CheckboxTableViewer fileCheckBoxTableViewer;

	private List<File> currentFiles= new ArrayList<File>();
	
	/** Logger system */
	private static Logger log = null;
	
	static final Level logLevel = Level.INFO;
	
	private static Logger getLogger() {
		if(log == null) {
			// Log system
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(SyntexLoaderResource.logLevel);
			log = Logger.getLogger(SyntexDocument.class.getName());
			log.setUseParentHandlers(false); // Do not use default handlers
			log.addHandler(consoleHandler);
			log.setLevel(SyntexLoaderResource.logLevel);
		}
		return log;
	}

	public SyntexLoaderResource() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "This plug-in will import data from Syntex";
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Select directory";
	}

	@Override
	public boolean importData() {
		// Use and UIThread in order to advoid "org.eclipse.swt.SWTException: Invalid thread access"
		PlatformUI.getWorkbench().getDisplay().syncExec(
				new Runnable() {
					public void run(){
						String dir=txtDirectory.getText();

						if (dir != null) {
							// Set the text box to the new selection
							try {
								SyntexResourceTerminological imp = new SyntexResourceTerminological();
								imp.parseSyntexCorpus(new File(dir));
							} catch (Exception e) {
								getLogger().warning("Something goes wrong:");
								e.printStackTrace();
							}
						}
					}
				}
		);
		return true;
	}

	@Override
	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createUI(Composite parent) {
		final Composite body = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		body.setLayout(layout);

		new Label(body, SWT.NONE).setText("Resource name:");

		// Create the text box extra wide to show long paths
		txtResourceName = new Text(body, SWT.BORDER);
		GridData dataRes = new GridData(GridData.FILL_HORIZONTAL);
		dataRes.horizontalSpan = 2;
		txtResourceName.setLayoutData(dataRes);


		new Label(body, SWT.NONE).setText("Directory:");

		// Create the text box extra wide to show long paths
		txtDirectory = new Text(body, SWT.BORDER);
		txtDirectory.setEnabled(false);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		txtDirectory.setLayoutData(data);

		// Clicking the button will allow the user
		// to select a directory
		Button btnBrowse = new Button(body, SWT.PUSH);
		btnBrowse.setText("Browse...");
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				DirectoryDialog dlg = new DirectoryDialog(body.getShell());

				// Set the initial filter path according
				// to anything they've selected or typed in
				dlg.setFilterPath(txtDirectory.getText());

				// Change the title bar text
				dlg.setText("Directory Dialog");

				// Customizable message displayed in the dialog
				dlg.setMessage("Select a directory");

				// Calling open() will open and run the dialog.
				// It will return the selected directory, or
				// null if user cancels
				String dir = dlg.open();
				if (dir != null) {
					// Set the text box to the new selection
					txtDirectory.setText(dir);

					//remove all already loaded files
					fileCheckBoxTableViewer.getTable().removeAll();

					// load files of the selected dir in fileTableviewer
					currentFiles= Services.getAllFiles(new File(dir));

					fileCheckBoxTableViewer.setInput(currentFiles);

					fileCheckBoxTableViewer.refresh();
				}
			}
		});

		//final Composite areaFile= new Composite(body, SWT.NONE);
		GridData gdAreaFile = new GridData(GridData.FILL_HORIZONTAL);
		//areaFile.setLayoutData(GDCOMP);
		//GridLayout GL2 = new GridLayout(3, false);
		//areaFile.setLayout(GL2);
		gdAreaFile.horizontalSpan=2;
		gdAreaFile.verticalSpan=1;
		Label lbFile= new Label(body, SWT.NONE);
		lbFile.setText("File(s):");
		//lbFile.setLayoutData(gdAreaFile);
		// create blank ui
		Label lbBlank= new Label(body, SWT.NONE);
		lbBlank.setLayoutData(gdAreaFile);
		// create blank ui
		//new Label(body, SWT.NONE);

		//final Composite areaViewer= new Composite(body, SWT.NONE);
		GridData gdAreaViewer = new GridData(GridData.FILL_HORIZONTAL);
		gdAreaViewer.horizontalSpan=2;
		gdAreaViewer.verticalSpan=10;

		// Create the CheckboxTableViewer to display the files in the source dir
		fileCheckBoxTableViewer = CheckboxTableViewer.newCheckList(
				body, SWT.BORDER);
		fileCheckBoxTableViewer.getTable().setLayoutData(gdAreaViewer);
		fileCheckBoxTableViewer.setContentProvider(new FileContenProvider());
		fileCheckBoxTableViewer.setLabelProvider(new FileLabelProvider());


		Button btnSelectAll = new Button(body, SWT.PUSH);
		btnSelectAll.setText("Select all");
		btnSelectAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {


			}
		});
		btnSelectAll.setEnabled(false);

		Button btnDeselectAll = new Button(body, SWT.PUSH);
		btnDeselectAll.setText("Deselect all");
		btnDeselectAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {			        
			}
		});
		btnDeselectAll.setEnabled(false);

		Button btnRefresh = new Button(body, SWT.PUSH);
		btnRefresh.setText("Refresh");
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {


			}
		});
		btnRefresh.setEnabled(false);

		// all plugin must explicit notify the change of the content after build it
		setContent(body);

	}


	class FileContenProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return  ((List<File>) parent).toArray();
		}
	}

	class FileLabelProvider extends LabelProvider  {
		public String getText(Object obj) {
			if (obj instanceof File){
				return  ((File) obj).getName();
			}
			return null;
		}
		public Image getColumnImage(Object obj, int index) {
			return null;
		}
		public Image getImage(Object obj) {
			return null;
		}

	}

}
