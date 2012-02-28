package org.dafoe.api.skos.samples;

import org.dafoe.api.skos.vocabulary.SKOSCollection;
import org.dafoe.api.skos.vocabulary.SKOSConcept;
import org.dafoe.api.skos.vocabulary.SKOSConceptScheme;
import org.dafoe.api.skos.vocabulary.SKOSModel;
import org.dafoe.api.skos.vocabulary.SKOSOrderedCollection;


/**
 * 
 *  @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class SkosTest {
	static String myNamespacePrefix = "skos";
	static String mySchemaURI = "http://somewhere/MySchema";
	static String myConceptURI = "http://somewhere/MyConcept";
	static String myAnotherConceptURI = "http://somewhere/MyAnotherConcept";
	static String myDatatypeURI = "http://somewhere/ChemicalSymbol";
	static String myCollectionURI = "http://somewhere/Collection1";
	static String myOrderedCollectionURI = "http://somewhere/OrderedCollection";

	public static void main(String[] args) {

		SKOSModel.createModel();
		
		SKOSConceptScheme myScheme = new SKOSConceptScheme(mySchemaURI); 
		SKOSConcept myConcept = new SKOSConcept(myConceptURI);
		SKOSConcept myAnotherConcept = new SKOSConcept(myAnotherConceptURI);
		SKOSCollection myCollection = new SKOSCollection(myCollectionURI);
		SKOSOrderedCollection myOrderedCollection = new SKOSOrderedCollection(myOrderedCollectionURI);
		
		//myScheme.addTopConcept(myConcept);
		myConcept.setAsTopConcept(true); //ok
		myConcept.addBroader(myAnotherConcept);
		myAnotherConcept.setInScheme(myScheme);
		
		myConcept.addPrefLabel("totoen", "en");
		myConcept.addPrefLabel("titi");
		myConcept.addPrefLabel("totofr", "fr");
		myConcept.addPrefLabel("toto2fr", "fr");
		myConcept.addNotation("K", myDatatypeURI);
		
	    myCollection.addMember(myConcept);
	    myCollection.addMember(myAnotherConcept);
	    
	    myOrderedCollection.addMemberList(myConcept);
	    myOrderedCollection.addMemberList(myAnotherConcept);
	    
	    //myScheme.addCollection(myCollection);
	    
	    System.out.println("size collections= "+myScheme.getCollections().length);
	    
	    for(SKOSCollection c: myScheme.getCollections()){
	    	 System.out.println("size member= "+c.getMembers().length);
	    }
	   // System.out.println("size= "+myScheme.getTopCollections().size());

	   
	    SKOSModel.getModel().write(System.out, "RDF/XML");
		//RDF/XML, RDF/XML-ABBREV, N-TRIPLE, TURTLE
	    
	    //System.out.println("my Concept prefLabel = " + myConcept.getPrefLabel());
	    //System.out.println("my Concept languages= " + myConcept.getPrefLabels().toString());
	    //System.out.println("myConcept prefLabel in '' = " + myConcept.getPrefLabel(""));
	    //System.out.println("http://somewhere/MyConcept = " + SKOSModel.getConcept("http://somewhere/MyAnotherConcept"));

	    //VT
	   // File f = new File("d:/ex1.rdf");
	   // SKOSModel.writeFile(f, "RDF/XML");
	}

}
