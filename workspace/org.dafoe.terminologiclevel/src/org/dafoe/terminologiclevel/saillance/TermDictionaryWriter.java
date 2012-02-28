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
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.terminology.common.DatabaseAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class TermDictionaryWriter {
	private static final String TERMS_DICTIONARY_SCHEMA = "termsDictionary.xsd"; //$NON-NLS-1$
	private File xmlFile;

	private Document doc;

	public static void buildExport(String fileName){
		new TermDictionaryWriter(fileName);
	}
	
	private TermDictionaryWriter(String fileName) {
		this.xmlFile = new File(fileName);
		
		this.doc = null;
		
		try {
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			
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
			
			doc = builder.newDocument();
			
			createDocumentContent();
			
			serializeDocumentContent();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			
		} 
	}

	
	private void createDocumentContent(){
		List<ITerm> terms = DatabaseAdapter.getTerms();
		Element root, node;
		Text text;
		Iterator<ITerm> it;
		ITerm term;
		
		if (terms != null) {
			root = doc.createElement("ensTermes");	 //$NON-NLS-1$
			doc.appendChild(root);
			
			it = terms.iterator();
			
			while (it.hasNext()){
				term = it.next();
				node = doc.createElement("terme"); //$NON-NLS-1$
				root.appendChild(node);
				text = doc.createTextNode(term.getLabel());
				node.appendChild(text);
			}
			
		}		
	}
	
	private void serializeDocumentContent(){
		try {
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(this.doc);
			StreamResult result =  new StreamResult(this.xmlFile);
			transformer.transform(source, result);
		
        } catch (TransformerException e) {
			e.printStackTrace();
		
        }
		
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