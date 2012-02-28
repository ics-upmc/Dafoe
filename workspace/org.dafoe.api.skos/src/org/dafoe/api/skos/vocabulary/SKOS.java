

package org.dafoe.api.skos.vocabulary;

import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * 17 * A SKOS vocabulary schema. 18 * 19 * @version July 28, 2007 20 * @author
 * Khalid Latif 21
 */
public class SKOS {

	/** SKOS Namespace */
	public static final String NS = "http://www.w3.org/2004/02/skos/core#";

	/** The RDF model that holds the SKOS vocabulary terms. */
	private static Model model = ModelFactory.createDefaultModel();

	/** SKOS vocabulary concepts */
	public static final Resource ConceptScheme = model.createResource(NS + "ConceptScheme");
	public static final Resource Concept = model.createResource(NS + "Concept");
	public static final Resource Collection = model.createResource(NS + "Collection");
	public static final Resource OrderedCollection = model.createResource(NS + "OrderedCollection");


	/** SKOS vocabulary properties */
	public static final Property prefLabel = model.createProperty(NS, "prefLabel");
	public static final Property altLabel = model.createProperty(NS, "altLabel");
	public static final Property hiddenLabel = model.createProperty(NS,	"hiddenLabel");

	public static final Property symbol = model.createProperty(NS, "symbol");
	public static final Property prefSymbol = model.createProperty(NS, "prefSymbol");
	public static final Property altSymbol = model.createProperty(NS, "altSymbol");

	public static final Property notation = model.createProperty(NS, "notation");
	public static final Property note = model.createProperty(NS, "note");
	public static final Property changeNote = model.createProperty(NS, "changeNote");
	public static final Property editorialNote = model.createProperty(NS, "editorialNote");
	public static final Property historyNote = model.createProperty(NS, "historyNote");
	public static final Property definition = model.createProperty(NS, "definition");
	public static final Property example = model.createProperty(NS, "example");

	public static final Property semanticRelation = model.createProperty(NS, "semanticRelation");
	public static final Property broader = model.createProperty(NS, "broader");
	public static final Property broaderTransitive = model.createProperty(NS, "broaderTransitive");
	public static final Property narrower = model.createProperty(NS, "narrower");
	public static final Property narrowerTransitive = model.createProperty(NS, "narrowerTransitive");
	public static final Property related = model.createProperty(NS, "related");

	public static final Property inScheme = model.createProperty(NS, "inScheme");
	public static final Property hasTopConcept = model.createProperty(NS, "hasTopConcept");
	public static final Property member = model.createProperty(NS, "member");
	public static final Property memberList = model.createProperty(NS, "memberList");
	public static final Property topConceptOf = model.createProperty(NS, "topConceptOf");

	public static final Property subject = model.createProperty(NS, "subject");
	public static final Property primarySubject = model.createProperty(NS, "primarySubject");
	public static final Property isSubjectOf = model.createProperty(NS,	"isSubjectOf");
	public static final Property isPrimarySubjectOf = model.createProperty(NS,"isPrimarySubjectOf");
	public static final Property subjectIndicator = model.createProperty(NS, "subjectIndicator");

		
	/**
	 * Returns the namespace of the SKOS vocabulary
	 * 
	 * @return The URL that represents the SKOS vocabulary namespace
	 */
	public static String getURI() {
		return NS;
	}

	/**
	 * Returns prefLabel for the given SKOS concept
	 * 
	 * @param res
	 *            The SKOS concept
	 * @param lang
	 *            Language such as EN, DE or null to find any prefLabel
	 * @return The label
	 */
	public static String getPrefLabel(OntResource concept, String lang) {
		if (lang != null && lang.length() > 0) {
			NodeIterator iter = concept.listPropertyValues(prefLabel);
			while (iter.hasNext()) {
				RDFNode node = iter.nextNode();
				Literal literal = (Literal) node.as(Literal.class);
				if (lang.equalsIgnoreCase(literal.getLanguage()))
					return literal.getString();
			}
		}
		RDFNode node = concept.getPropertyValue(prefLabel);
		Literal literal = (Literal) node.as(Literal.class);
		return literal.getString();
	}
}
