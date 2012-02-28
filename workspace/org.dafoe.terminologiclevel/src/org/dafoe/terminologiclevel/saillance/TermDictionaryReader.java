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

package org.dafoe.terminologiclevel.saillance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TermDictionaryReader {
	private static final String TERMS_DICTIONARY_SCHEMA = "termsDictionary.xsd"; //$NON-NLS-1$
	private File xmlFile;

	private Document doc;

	public TermDictionaryReader(String fileName) {
		this.xmlFile = new File(fileName);
		this.doc = null;
		try {
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			System.out.println("DocumentBuilderFactory: "+ builderFactory.getClass().getName()); //$NON-NLS-1$
			
			builderFactory.setNamespaceAware(true);
			builderFactory.setValidating(true);
			
			// specify the underlying XML representation model
			builderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema"); //$NON-NLS-1$ //$NON-NLS-2$
			
			// assign the termsDictionary XML Schema to the parser 
			builderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", "file:"  //$NON-NLS-1$ //$NON-NLS-2$
					+ org.dafoe.terminologiclevel.Activator.getPluginFolder().getPath()
					+ File.separator
					+ TERMS_DICTIONARY_SCHEMA);

			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			builder.setErrorHandler(new MyErrorHandler());
			
			doc = builder.parse(xmlFile);

		} catch (SAXException e) {
			e.printStackTrace();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getTerms() {
		ArrayList<String> arrayList = new ArrayList<String>();
		Element rootElement = doc.getDocumentElement();
		NodeList termElement = rootElement.getChildNodes();
		for (int i = 0; i < termElement.getLength(); i++) {
			if (termElement.item(i).getNodeType() == Node.ELEMENT_NODE) {
				arrayList.add(termElement.item(i).getTextContent().toString());
			}
		}
		return arrayList.toArray(new String[] {});
	}

	class MyErrorHandler implements ErrorHandler {
		public void warning(SAXParseException e) throws SAXException {
			show("Warning", e); //$NON-NLS-1$
			throw (e);
		}

		public void error(SAXParseException e) throws SAXException {
			show("Error", e); //$NON-NLS-1$
			throw (e);
		}

		public void fatalError(SAXParseException e) throws SAXException {
			show("Fatal Error", e); //$NON-NLS-1$
			throw (e);
		}

		private void show(String type, SAXParseException e) {
			System.out.println(type + ": " + e.getMessage()); //$NON-NLS-1$
			System.out.println("Line " + e.getLineNumber() + " Column " //$NON-NLS-1$ //$NON-NLS-2$
					+ e.getColumnNumber());
			System.out.println("System ID: " + e.getSystemId()); //$NON-NLS-1$
		}
	}
}