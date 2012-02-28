package org.dafoe.api.skos.samples;



import java.io.File;

import org.dafoe.api.skos.vocabulary.LocalizedText;
import org.dafoe.api.skos.vocabulary.SKOSCollection;
import org.dafoe.api.skos.vocabulary.SKOSConcept;
import org.dafoe.api.skos.vocabulary.SKOSConceptScheme;
import org.dafoe.api.skos.vocabulary.SKOSModel;
import org.dafoe.api.skos.vocabulary.SKOSOrderedCollection;

/**
 * 
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class SkosTest4 {

	public static void main(String[] args) {
		SKOSModel.createModel();
		File f = new File("d:/sample_EUROVOC-skos.rdf");
		System.out.println(f.getAbsolutePath());
		
		SKOSModel.read(f);
	
		System.out.println("**************************************************************");
		System.out.println("ConceptSchemes");
		System.out.println("**************************************************************");
		
		SKOSConceptScheme[] conceptSchemes = SKOSModel.getConceptSchemes();
		for(int i = 0; i < conceptSchemes.length; i++){
			System.out.println(conceptSchemes[i].getUri());
		}	

		System.out.println("**************************************************************");
		System.out.println("Concepts");
		System.out.println("**************************************************************");

		SKOSConcept[] concepts = SKOSModel.getConcepts();
		for(int i = 0; i < concepts.length; i++){
			System.out.println(concepts[i].getUri());
		}	

		System.out.println("**************************************************************");
		System.out.println("Collections");
		System.out.println("**************************************************************");

		SKOSCollection[] collections = SKOSModel.getCollections();
		for(int i = 0; i < collections.length; i++){
			System.out.println(collections[i].getUri());
		}	

		System.out.println("**************************************************************");
		System.out.println("OrderedCollections");
		System.out.println("**************************************************************");

		SKOSOrderedCollection[] orderedCollections = SKOSModel.getOrderedCollections();
		for(int i = 0; i < orderedCollections.length; i++){
			System.out.println(orderedCollections[i].getUri());
		}	

		System.out.println("**************************************************************");
		System.out.println("Top Concepts of the concept scheme");
		System.out.println("**************************************************************");
		
		SKOSConcept[] topConcepts;
		if (conceptSchemes.length > 0) {
			topConcepts= conceptSchemes[0].getTopConcepts();
			for(int i = 0; i < topConcepts.length; i++){
				System.out.println(topConcepts[i].getUri());
			}	
		}

		System.out.println("**************************************************************");
		System.out.println("Broaders of http://www.eu.gov/ontologies/2008/09/eurovoc#C1048");
		System.out.println("**************************************************************");
	
		SKOSConcept myConcept;
		
		myConcept = SKOSModel.getConcept("http://www.eu.gov/ontologies/2008/09/eurovoc#C1048");
		SKOSConcept[] broaderConcepts = myConcept.getBroaders();
		for(int i = 0; i < broaderConcepts.length; i++){
			System.out.println(broaderConcepts[i].getUri());
		}	
/*		
		System.out.println("**************************************************************");
		System.out.println("Narrowers of http://purl.org/ontology/mo/mit#Wind_instruments");
		System.out.println("**************************************************************");
	
		myConcept = SKOSModel.getConcept("http://purl.org/ontology/mo/mit#Wind_instruments");
		SKOSConcept[] narrowerConcepts = myConcept.getNarrowers();
		for(int i = 0; i < narrowerConcepts.length; i++){
			System.out.println(narrowerConcepts[i].getUri());
		}	
*/
		System.out.println("**************************************************************");
		System.out.println("prefLabel of http://www.eu.gov/ontologies/2008/09/eurovoc#C1048");
		System.out.println("**************************************************************");
		
		LocalizedText[] lt = myConcept.getPrefLabels();
		for(int i = 0; i < lt.length; i++){
			System.out.println("prefLabel = " + lt[i].getText() + "; language = " + lt[i].getLanguage());
		}
		
		System.out.println("");
		System.out.println(myConcept.toString());

		System.out.println("**************************************************************");
		System.out.println("altLabel of http://www.eu.gov/ontologies/2008/09/eurovoc#C1048");
		System.out.println("**************************************************************");
		
		LocalizedText[] lt2 = myConcept.getAltLabels();
		for(int i = 0; i < lt2.length; i++){
			System.out.println("altLabel = " + lt2[i].getText() + "; language = " + lt2[i].getLanguage());
		}
		
		System.out.println("");
		System.out.println(myConcept.toString());

	}
}
