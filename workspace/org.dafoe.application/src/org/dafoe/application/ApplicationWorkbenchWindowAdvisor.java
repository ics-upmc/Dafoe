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

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.LayoutPart;
import org.eclipse.ui.internal.WorkbenchPage;

@SuppressWarnings("restriction")
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	@Override
	public Control createEmptyWindowContents(Composite parent) {
		return super.createEmptyWindowContents(parent);
	}

	@Override
	public void createWindowContents(Shell shell) {
		super.createWindowContents(shell);

	}

	void ShowControls(Composite comp, String space) {

		Control[] ctls = comp.getChildren();
		for (int i = 0; i < ctls.length; i++) {
			System.out.println(space + "  " + ctls[i].getClass().toString());
			if (ctls[i] instanceof Composite) {
				if (ctls[i] instanceof org.eclipse.swt.widgets.TabFolder) {
					((Composite) ctls[i]).setBackground(new Color(Display
							.getCurrent(), 255, 255, 0));
				} else {

					((Composite) ctls[i]).setBackground(new Color(Display
							.getCurrent(), 0, 255, 255));
				}
				ShowControls(((Composite) ctls[i]), space + "  ");

			}
		}
	}

	protected Hashtable<IViewReference, Rectangle> listedetache = new Hashtable<IViewReference, Rectangle>();

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		listedetache.clear();
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1024, 768));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);
		configurer.setShowMenuBar(true);
		configurer.setShowPerspectiveBar(false);
		//VT: to show progession in status line when running task 
        configurer.setShowProgressIndicator(true);
		configurer.setShowFastViewBars(false);

		configurer.setTitle(Messages
				.getString("ApplicationWorkbenchWindowAdvisor.0")); //$NON-NLS-1$

	}

	public void postWindowCreate() {
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().get

		IWorkbenchWindow[] IWWS = PlatformUI.getWorkbench()
				.getWorkbenchWindows();

		for (int i = 0; i < IWWS.length; i++) {
			System.out.println(IWWS[i].toString() + "  " + i);
		}

		IWorkbenchPage[] IWPS = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getPages();

		for (int j = 0; j < IWPS.length; j++) {
			System.out.println(IWPS[j].toString() + "  " + j + " "
					+ IWPS[j].getLabel() + " ");
			// IWPS[j].addPartListener(new IPartListener() {});

		}

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService()
				.addPartListener(partlistener);

		PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.addPerspectiveListener(new IPerspectiveListener() {

					public void perspectiveActivated(IWorkbenchPage page,
							IPerspectiveDescriptor perspective) {

						// System.out.println("Page = " + page.getLabel() +
						// "; Perspective = " + perspective.getId());

						// page.addPartListener(partlistener);

						IViewPart ref = page
								.findView("org.dafoe.contextlevel.contextviewId2");
						if (!Activator.getDefault().showContext) {
							(page).hideView(ref);

						} else {
							try {
								((WorkbenchPage) page)
										.showView("org.dafoe.contextlevel.contextviewId2");
							} catch (PartInitException e) {

								e.printStackTrace();
							}
						}

						Enumeration<IViewReference> e = listedetache.keys();

						while (e.hasMoreElements()) {
							IViewReference vid = (IViewReference) e
									.nextElement();
							IViewReference[] refs = page.getViewReferences();

							boolean exist = false;

							// check if the vid detached view exists in the
							// current page
							for (int i = 0; i < refs.length; i++) {
								String refsId = refs[i].getId();
								exist = exist || refsId.equals(vid.getId());
							}

							if (!exist) {
								try {
									// System.out.println(perspective.getId() +
									// "." + vid.getId() + "." +
									// listedetache.size());
									// IViewPart mapart =
									// page.showView(vid.getId(), null,
									// org.eclipse.ui.IWorkbenchPage.VIEW_ACTIVATE);
									IViewPart mapart = page
											.showView(
													vid.getId(),
													null,
													org.eclipse.ui.IWorkbenchPage.VIEW_VISIBLE);

									System.out.println("Secondary Id = "
											+ mapart.getViewSite()
													.getSecondaryId());

									((WorkbenchPage) page)
											.getActivePerspective()
											.getPresentation().detachPart(vid);

									// page.addPartListener(partlistener);

									Rectangle R = (Rectangle) listedetache
											.get(vid);

									vid.getView(true).getViewSite().getShell()
											.setLocation(R.x, R.y);
									vid.getView(true).getViewSite().getShell()
											.setSize(R.width, R.height);

								} catch (PartInitException e1) {
									e1.printStackTrace();
								}

							}
						}

					}

					public void perspectiveChanged(IWorkbenchPage page,
							IPerspectiveDescriptor perspective, String changeId) {
						// System.out.println("Perspective changed: " +
						// changeId);
						// System.out.println("Page = " + page.getLabel() +
						// "; Perspective = " + perspective.getId());

						IViewReference[] refs = page.getViewReferences();

						for (int i = 0; i < refs.length; i++) {
							String refsId = refs[i].getId();
							System.out.println("ViewRef = " + refsId);
						}

						// displayDetache();

					}
				});

	}

	IPartListener2 partlistener = new IPartListener2() {

		public void partActivated(IWorkbenchPartReference partRef) {
		}

		public void partBroughtToTop(IWorkbenchPartReference partRef) {
			// System.out.println("partBroughtToTop 2 "+partRef.getTitle());
		}

		public void partClosed(IWorkbenchPartReference partRef) {
			// System.out.println("partClosed ************"+partRef.getTitle());

			try {
				Enumeration<IViewReference> itr = listedetache.keys();

				while (itr.hasMoreElements()) {
					IViewReference vid = (IViewReference) itr.nextElement();
					System.out.println("PartClosed ------> " + partRef.getId()
							+ " " + vid.getId());
					if (vid.getId().equals(partRef.getId())) {
						listedetache.remove(vid);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void partDeactivated(IWorkbenchPartReference partRef) {
			// System.out.println("partDeactivated 2 "+partRef.getTitle());
		}

		public void partHidden(IWorkbenchPartReference partRef) {
			// System.out.println("partHidden 2 " + partRef.getTitle());
			IViewReference ref = (IViewReference) partRef;
			listedetache.remove(ref);
			// displayDetache();
		}

		public void partInputChanged(IWorkbenchPartReference partRef) {
			// System.out.println("partInputChanged 2 "+partRef.getTitle());
		}

		public void partOpened(IWorkbenchPartReference partRef) {
			// System.out.println("partOpened 2 "+partRef.getTitle());
		}

		public void partVisible(IWorkbenchPartReference partRef) {
			// System.out.println("partVisible 2 " + partRef.getTitle());
			try {
				if (partRef instanceof IViewReference) {

					IViewReference ref = (IViewReference) partRef;
					/*
					 * System.out.println("PlatformUI.getWorkbench() = " +
					 * PlatformUI.getWorkbench());System.out.println(
					 * "PlatformUI.getWorkbench().getActiveWorkbenchWindow() = "
					 * + PlatformUI.getWorkbench().getActiveWorkbenchWindow());
					 * System.out.println(
					 * "PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() = "
					 * +PlatformUI.getWorkbench().getActiveWorkbenchWindow().
					 * getActivePage());System.out.println(
					 * "((WorkbenchPage)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()).getActivePerspective() = "
					 * +((WorkbenchPage)PlatformUI.getWorkbench().
					 * getActiveWorkbenchWindow
					 * ().getActivePage()).getActivePerspective());
					 * System.out.println(
					 * "((WorkbenchPage)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()).getActivePerspective().getPresentation() = "
					 * +((WorkbenchPage)PlatformUI.getWorkbench().
					 * getActiveWorkbenchWindow
					 * ().getActivePage()).getActivePerspective().
					 * getPresentation());
					 */

					final LayoutPart refPart = ((WorkbenchPage) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage())
							.getActivePerspective().getPresentation().findPart(
									partRef.getId(), ref.getSecondaryId());
					if (refPart != null && !refPart
							.isDocked()
							&& !listedetache.containsKey(ref)) {

						IViewReference foundView = null;

						Enumeration<IViewReference> itr = listedetache.keys();

						System.out.println("ref.getId() = " + ref.getId());
						while (itr.hasMoreElements()) {
							IViewReference vid = (IViewReference) itr
									.nextElement();
							System.out.println("vid.getId() = " + vid.getId());
							if (vid.getId().compareTo((ref.getId())) == 0) {
								foundView = vid;
							}
						}

						Rectangle rectangle;

						if (foundView != null) {

							rectangle = (Rectangle) listedetache.get(foundView);

						} else {
							rectangle = ref.getView(true).getViewSite()
									.getShell().getBounds();
						}

						listedetache.put(ref, rectangle);

					}
				}
				// displayDetache();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};

	private void displayDetache() {
		Enumeration<IViewReference> itr = listedetache.keys();

		while (itr.hasMoreElements()) {
			System.out
					.println("***********************************************************");
			System.out.println("Detached views: ");
			IViewReference vid = (IViewReference) itr.nextElement();
			System.out.println(vid.getId());
			System.out
					.println("***********************************************************");

		}

	}
}
