package org.dafoe.api.skos.samples;



import java.io.File;

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
public class SkosTest2 {

	public static void main(String[] args) {
		SKOSModel.createModel();
		File f = new File("d:/skos11.rdf");
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
			if (!collections[i].isAnon()){
				System.out.println(collections[i].getUri());
			} else {
				System.out.println(collections[i].getResource().getId().toString());
			}
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
		
		/*
		SKOSConcept[] topConcepts = conceptSchemes[0].getTopConcepts();
		for(int i = 0; i < topConcepts.length; i++){
			System.out.println(topConcepts[i].getUri());
		}	
		*/
		
		SKOSConcept[] topConcepts = conceptSchemes[0].getTopConcepts();
		for(int i = 0; i < topConcepts.length; i++){
			System.out.println(topConcepts[i].getUri());
		}	
		
		System.out.println("**************************************************************");
		System.out.println("Broaders of http://www.ivoa.net/Document/WD/vocabularies/vocabularies-20080222/AAkeys#CosmologyCosmicMicrowaveBackground");
		System.out.println("**************************************************************");
	
		SKOSConcept myConcept;
		
		myConcept = SKOSModel.getConcept("http://www.ivoa.net/Document/WD/vocabularies/vocabularies-20080222/AAkeys#CosmologyCosmicMicrowaveBackground");
		SKOSConcept[] broaderConcepts = myConcept.getBroaders();
		for(int i = 0; i < broaderConcepts.length; i++){
			System.out.println(broaderConcepts[i].getUri());
		}	
		
		System.out.println("**************************************************************");
		System.out.println("Narrowers of http://www.ivoa.net/Document/WD/vocabularies/vocabularies-20080222/AAkeys#Cosmology");
		System.out.println("**************************************************************");
	
		myConcept = SKOSModel.getConcept("http://www.ivoa.net/Document/WD/vocabularies/vocabularies-20080222/AAkeys#Cosmology");
		SKOSConcept[] narrowerConcepts = myConcept.getNarrowers();
		for(int i = 0; i < narrowerConcepts.length; i++){
			System.out.println(narrowerConcepts[i].getUri());
		}	

		System.out.println("**************************************************************");
		System.out.println("english prefLabel of http://www.ivoa.net/Document/WD/vocabularies/vocabularies-20080222/AAkeys#Cosmology");
		System.out.println("**************************************************************");
		
		System.out.println(myConcept.getPrefLabel("en"));
		
		System.out.println("**************************************************************");
		System.out.println("Members of collection[0]");
		System.out.println("**************************************************************");

		SKOSConcept[] memberConcepts = collections[0].getMembers();  
		for(int i = 0; i < memberConcepts.length; i++){
			System.out.println(memberConcepts[i].getUri());
		}
		
		SKOSModel.getModel().write(System.out, "RDF/XML");
	}
}
