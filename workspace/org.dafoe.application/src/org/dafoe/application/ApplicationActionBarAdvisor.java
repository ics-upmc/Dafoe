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
package org.dafoe.application;

import org.dafoe.contextlevel.IDafoeContextLevel;
import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.model.Project;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.themes.IThemeManager;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private IContributionItem fenetreAction;
	private Action genericAction;

	private IWorkbenchAction preferenceAction;// VT

	MenuManager showViewMenuMgr;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(final IWorkbenchWindow window) {
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);

		preferenceAction = ActionFactory.PREFERENCES.create(window);
		preferenceAction.setText("Preferences");
		register(preferenceAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);

		// showViewMenuMgr = new MenuManager("Show View", "showView");
		fenetreAction = ContributionItemFactory.VIEWS_SHORTLIST.create(window);

		// This is a blank action in order to display the menus into the menu
		// bar.
		genericAction = new Action() {
			@Override
			public void run() {
				System.out.println("test d'action"); //$NON-NLS-1$
			}
		};
		genericAction.setText(Messages
				.getString("ApplicationActionBarAdvisor.0")); //$NON-NLS-1$
		genericAction.setId("GenericId"); //$NON-NLS-1$
		register(genericAction);
	}

	Action CreerAction(final IViewDescriptor viewDescriptor, String id) {
		Action resultat = new Action() {
			@Override
			public void run() {

				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().showView(viewDescriptor.getId(),
									null,
									org.eclipse.ui.IWorkbenchPage.VIEW_CREATE);

					IViewReference ref = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.findViewReference(viewDescriptor.getId());

					((WorkbenchPage) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage())
							.getActivePerspective().getPresentation()
							.detachPart(ref);

					// ref.getPart(true).getViewSite().getShell().setLocation(10,
					// 10);

				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		};

		resultat.setText(viewDescriptor.getLabel()); //$NON-NLS-1$
		resultat.setImageDescriptor(viewDescriptor.getImageDescriptor());
		resultat.setId(id); //$NON-NLS-1$
		register(resultat);

		return resultat;

	}

	Action CreerFermeViewContext() {
		Action resultat = new Action() {
			@Override
			public void run() {

				Activator.getDefault().showContext = !Activator.getDefault().showContext;
				IViewPart ref = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().findView(
								"org.dafoe.contextlevel.contextviewId2"); //$NON-NLS-1$
				if (!Activator.getDefault().showContext) {
					((WorkbenchPage) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage())
							.hideView(ref);

				} else {
					try {
						((WorkbenchPage) PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage())
								.showView("org.dafoe.contextlevel.contextviewId2"); //$NON-NLS-1$
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		};

		String montre_cache = Messages
				.getString("ApplicationActionBarAdvisor.16"); //$NON-NLS-1$

		resultat.setText(montre_cache); //$NON-NLS-1$

		resultat.setId("cache_vue_contex"); //$NON-NLS-1$
		register(resultat);

		return resultat;

	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager(
				Messages.getString("ApplicationActionBarAdvisor.2"), IWorkbenchActionConstants.M_FILE); //$NON-NLS-1$

		/*
		 * MenuManager traceabilityMenu = new MenuManager(
		 * Messages.getString("ApplicationActionBarAdvisor.3"),
		 * Messages.getString("ApplicationActionBarAdvisor.4")); //$NON-NLS-1$
		 * //$NON-NLS-2$
		 */

		MenuManager editMenu = new MenuManager(
				Messages.getString("ApplicationActionBarAdvisor.1"), IWorkbenchActionConstants.M_EDIT); //$NON-NLS-1$

		MenuManager windowMenu = new MenuManager(
				Messages.getString("ApplicationActionBarAdvisor.5"), IWorkbenchActionConstants.M_WINDOW); //$NON-NLS-1$

		/*
		 * MenuManager statisticMenu = new MenuManager(
		 * Messages.getString("ApplicationActionBarAdvisor.6"),
		 * Messages.getString("ApplicationActionBarAdvisor.7")); //$NON-NLS-1$
		 * //$NON-NLS-2$
		 */

		MenuManager helpMenu = new MenuManager(
				Messages.getString("ApplicationActionBarAdvisor.8"), IWorkbenchActionConstants.M_HELP); //$NON-NLS-1$

		IViewCategory[] cate = PlatformUI.getWorkbench().getViewRegistry()
				.getCategories();

		IPerspectiveDescriptor[] perpDesc = PlatformUI.getWorkbench()
				.getPerspectiveRegistry().getPerspectives();

		//MenuManager showViewMenuMgr = new MenuManager(IDEWorkbenchMessages.Workbench_showView, "showView"); //$NON-NLS-1$

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(windowMenu);

		menuBar.add(helpMenu);

		GroupMarker gmfichier = new GroupMarker("additions"); //$NON-NLS-1$
		fileMenu.add(gmfichier);
		fileMenu.add(new Separator());
		fileMenu.add(exitAction);
		fileMenu.add(new Separator());

		editMenu.add(genericAction);
		// VT:
		editMenu.add(new Separator());
		editMenu.add(preferenceAction);
		// fin VT:

		for (int i = 0; i < cate.length; i++) {

			MenuManager cateMan = new MenuManager(cate[i].getLabel(), "cate"); //$NON-NLS-1$

			IViewDescriptor[] ivd = cate[i].getViews();

			for (int j = 0; j < ivd.length; j++) {
				System.out.println("ivd label = " + ivd[j].getLabel());
				Action act = CreerAction(ivd[j], "des_" + j); //$NON-NLS-1$
				cateMan.add(act);
			}

			windowMenu.add(cateMan);

		}

		MenuManager perspMan = new MenuManager("Perspective", "persp");

		Action act = new Action() {
			public void run() {
				switchPerspective("welcome");
			}
		};
		act.setText("Welcome");
		perspMan.add(act);

		act = new Action() {
			public void run() {
				switchPerspective("corpus");
			}
		};
		act.setText("Corpus");
		perspMan.add(act);

		act = new Action() {
			public void run() {
				switchPerspective("terminologic");
			}
		};
		act.setText("Terminology");
		perspMan.add(act);

		act = new Action() {
			public void run() {
				switchPerspective("terminoontologic");
			}
		};
		act.setText("Termino-Ontology");
		perspMan.add(act);

		act = new Action() {
			public void run() {
				switchPerspective("ontologic");
			}
		};
		act.setText("Ontology");
		perspMan.add(act);

		windowMenu.add(perspMan);

		windowMenu.add(new Separator());
		GroupMarker gmwindow = new GroupMarker("additions"); //$NON-NLS-1$
		windowMenu.add(gmwindow);
		windowMenu.add(new Separator());
		windowMenu.add(CreerFermeViewContext());

		// windowMenu.add(fenetreAction);

		helpMenu.add(aboutAction);
	}

	private void switchPerspective(String perspectiveId) {

		IPerspectiveRegistry perspectiveRegistry = PlatformUI.getWorkbench()
				.getPerspectiveRegistry();
		IDafoeContextLevel contextLevel = org.dafoe.contextlevel.Activator
				.getDefault().getContextLevel();
		String currentPerspectiveId = contextLevel
				.getPerspectiveIdFromContextLevel(perspectiveId);
		IPerspectiveDescriptor perspectiveWithId = perspectiveRegistry
				.findPerspectiveWithId(currentPerspectiveId);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.setPerspective(perspectiveWithId);

	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {

		IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle()
				| SWT.BOTTOM);
		coolBar.add(toolbar);

		IThemeManager themeManager = PlatformUI.getWorkbench()
				.getThemeManager();

		final Color DAFOE_LEVEL_COLOR = themeManager
				.getTheme("org.dafoe.application.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
		final Color CORPUS_LEVEL_COLOR = themeManager
				.getTheme("org.dafoe.corpuslevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
		final Color TERMINOLOGIC_LEVEL_COLOR = themeManager
				.getTheme("org.dafoe.terminologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
		final Color TERMINO_ONTOLOGIC_LEVEL_COLOR = themeManager
				.getTheme("org.dafoe.terminoontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$
		final Color ONTOLOGIC_LEVEL_COLOR = themeManager
				.getTheme("org.dafoe.ontologiclevel.themeId").getColorRegistry().get("org.dafoe.application.backcolor"); //$NON-NLS-1$ //$NON-NLS-2$

		// ToolBarContributionItem.
		IContributionItem myCont = new ToolBarContributionItem(toolbar) {
			public void fill(CoolBar coolBar, int index) {
				CoolItem item = new CoolItem(coolBar, SWT.NONE);

				CoolBarButton myButton = new CoolBarButton(
						coolBar,
						SWT.PUSH,
						Messages.getString("ApplicationActionBarAdvisor.9"), "welcome", //$NON-NLS-1$ //$NON-NLS-2$
						DAFOE_LEVEL_COLOR, Display.getDefault().getSystemColor(
								SWT.COLOR_WHITE)); //$NON-NLS-1$ //$NON-NLS-2$

				coolBar.setLocked(true);

				Point size = new Point(165, 40);
				item.setPreferredSize(item.computeSize(size.x, size.y));
				item.setControl(myButton.getButton());
			}
		};
		coolBar.add(myCont);

		myCont = new ToolBarContributionItem(toolbar) {
			public void fill(CoolBar coolBar, int index) {
				CoolItem item = new CoolItem(coolBar, SWT.NONE);
				CoolBarButton myButton = new CoolBarButton(
						coolBar,
						SWT.PUSH,
						Messages.getString("ApplicationActionBarAdvisor.11"), "corpus", //$NON-NLS-1$ //$NON-NLS-2$
						CORPUS_LEVEL_COLOR, Display.getDefault()
								.getSystemColor(SWT.COLOR_WHITE)); //$NON-NLS-1$ //$NON-NLS-2$

				coolBar.setLocked(true);
				Point size = new Point(165, 40);
				item.setPreferredSize(item.computeSize(size.x, size.y));
				item.setControl(myButton.getButton());
			}
		};
		coolBar.add(myCont);

		myCont = new ToolBarContributionItem(toolbar) {
			public void fill(CoolBar coolBar, int index) {
				CoolItem item = new CoolItem(coolBar, SWT.NONE);
				CoolBarButton myButton = new CoolBarButton(
						coolBar,
						SWT.PUSH,
						Messages.getString("ApplicationActionBarAdvisor.13"), "terminologic", //$NON-NLS-1$ //$NON-NLS-2$
						TERMINOLOGIC_LEVEL_COLOR, Display.getDefault()
								.getSystemColor(SWT.COLOR_WHITE)); //$NON-NLS-1$ //$NON-NLS-2$
				coolBar.setLocked(true);
				Point size = new Point(165, 40);
				item.setPreferredSize(item.computeSize(size.x, size.y));
				item.setControl(myButton.getButton());
			}
		};
		coolBar.add(myCont);

		myCont = new ToolBarContributionItem(toolbar) {
			public void fill(CoolBar coolBar, int index) {
				CoolItem item = new CoolItem(coolBar, SWT.NONE);
				CoolBarButton myButton = new CoolBarButton(
						coolBar,
						SWT.PUSH,
						Messages.getString("ApplicationActionBarAdvisor.15"), "terminoontologic", //$NON-NLS-1$ //$NON-NLS-2$
						TERMINO_ONTOLOGIC_LEVEL_COLOR, Display.getDefault()
								.getSystemColor(SWT.COLOR_WHITE)); //$NON-NLS-1$ //$NON-NLS-2$

				coolBar.setLocked(true);
				Point size = new Point(165, 40);
				item.setPreferredSize(item.computeSize(size.x, size.y));
				item.setControl(myButton.getButton());
			}
		};
		coolBar.add(myCont);

		myCont = new ToolBarContributionItem(toolbar) {
			public void fill(CoolBar coolBar, int index) {
				CoolItem item = new CoolItem(coolBar, SWT.NONE);

				CoolBarButton myButton = new CoolBarButton(
						coolBar,
						SWT.PUSH,
						Messages.getString("ApplicationActionBarAdvisor.17"), "ontologic", //$NON-NLS-1$ //$NON-NLS-2$
						ONTOLOGIC_LEVEL_COLOR, Display.getDefault()
								.getSystemColor(SWT.COLOR_WHITE)); //$NON-NLS-1$ //$NON-NLS-2$
				coolBar.setLocked(true);
				Point size = new Point(165, 40);
				item.setPreferredSize(item.computeSize(size.x, size.y));
				item.setControl(myButton.getButton());

			}
		};
		coolBar.add(myCont);

		myCont = new ToolBarContributionItem(toolbar) {
			public void fill(CoolBar coolBar, int index) {
				CoolItem item = new CoolItem(coolBar, SWT.NONE);
				Label myButton = new Label(coolBar, SWT.PUSH);

				coolBar.setLocked(true);

				Point size = new Point(165, 40);
				item.setPreferredSize(item.computeSize(size.x, size.y));
				item.setControl(myButton);
				myButton.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent e) {

						// System.out.println(name+" "+(isin?"in":"out"));

						GC gc = e.gc;

						Font font = new Font(Display.getDefault(),
								"Verdana", 9, SWT.BOLD | SWT.CENTER); //$NON-NLS-1$
						gc.setFont(font);

						// gc.fillRectangle(0, 0, e.width, e.height-10);

						//VT
						Project currentProject= ControlerFactoryImpl.getPlatformControler().getCurrentProject();
						
						Point pt = gc.stringExtent(currentProject.getName());//"Jean Charlet"); //$NON-NLS-1$
						Image image = Activator.getDefault().getImageRegistry()
								.get(Activator.DRAPEAU);

						gc.drawString(currentProject.getName()/*"Jean Charlet"*/, (e.width - pt.x - 5) - image.getImageData().width - 10, (e.height - pt.y - 6) / 2, true); //$NON-NLS-1$

						gc.drawImage(image, (e.width
								- image.getImageData().width - 10), (e.height
								- pt.y - 6) / 2);

						IThemeManager themeManager = PlatformUI.getWorkbench()
								.getThemeManager();
						Color col = themeManager.getCurrentTheme()
								.getColorRegistry().get(
										"org.dafoe.application.backcolor"); //$NON-NLS-1$

						gc.setBackground(col);

						gc.fillRectangle(0, e.height - 10, e.width, 10);

						gc.setForeground(new Color(Display.getDefault(), 0, 0,
								0));

						gc.drawLine(0, e.height - 10, e.width, e.height - 10);

					}
				});

			}
		};
		coolBar.add(myCont);

	}
}
