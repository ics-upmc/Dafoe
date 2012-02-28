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
package org.dafoe.corpuslevel.ui.views;

import java.util.List;

//import org.dafoe.ui.thirdparty.corpus.model.ICorpusApi;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public class PhraseOccurenceLabelProvider extends OwnerDrawLabelProvider {

	TableViewerColumn columv;

	SentenceView view;

	public PhraseOccurenceLabelProvider(SentenceView view,
			TableViewerColumn columv) {
		super();
		// TODO Auto-generated constructor stub
		this.view = view;
		this.columv = columv;

	}

	@Override
	protected void measure(Event event, Object element) {
		// System.out.println("PhraseOccurenceLabelProvider.measure()");

		// TODO Auto-generated method stub
		ISentence phrase = (ISentence) element;
		int height = event.gc.textExtent(phrase.getContent()).y + 5;
		int width = event.gc.textExtent(phrase.getContent()).x;
		event.setBounds(new Rectangle(0, 0, width, height));

	}

	@Override
	protected void paint(Event event, Object element) {
		// TODO Auto-generated method stub
		ISentence phrase = (ISentence) element;
		Display display = view.GetViewer().getControl().getDisplay();
		TextLayout layout = new TextLayout(display);
		layout.setText(phrase.getContent());
		TextStyle plain = new TextStyle(
				JFaceResources.getFont(JFaceResources.DEFAULT_FONT),
				display.getSystemColor(SWT.COLOR_LIST_FOREGROUND), null);
		TextStyle italic = new TextStyle(JFaceResources.getFontRegistry()
				.getItalic(JFaceResources.DEFAULT_FONT),
				display.getSystemColor(SWT.COLOR_BLUE), null);
		Font newFont = new Font(display, "Arial", 9, SWT.BOLD); //$NON-NLS-1$
		TextStyle font = new TextStyle(newFont,
				display.getSystemColor(SWT.COLOR_WHITE),
				display.getSystemColor(SWT.COLOR_BLUE));

		List<ITermOccurrence> occurences = view.GetListOccurence(phrase);
		if (occurences != null) {
			for (int i = 0; i < occurences.size(); i++) {
				ITermOccurrence occ = occurences.get(i);
				int pos = occ.getPosition(); //
				int len = occ.getLength();
				layout.setStyle(font, pos, pos + len - 1);
			}
		}

		// layout.setStyle(plain, 0, 2);
		// layout.setStyle(italic, 3, 5);
		// layout.setStyle(font, 6, phrase.getText().length() - 1);

		layout.draw(event.gc, event.x, event.y);

		if (this.columv.getColumn().getWidth() < event.gc.textExtent(phrase
				.getContent()).x + 10) {
			this.columv.getColumn().setWidth(
					event.gc.textExtent(phrase.getContent()).x + 10);
		}

	}
}
