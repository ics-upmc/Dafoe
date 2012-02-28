package org.dafoe.plugin.imp.syntex.samples;



/**
 * Syntex Loader: Test class
 * @author Laurent Mazuel
 */
public class SyntexTest {

	public static final String[] pathsToCorpus = {
		//		"/home/mazman/SharedVBox/res-v2/",
		"/home/mazman/PostDoc/UrgencesDMP/Corpus Guide de bonnes pratiques/4- Corpus Syntex/",
		"/home/mazman/Bureau/res-syntex_BOURIGAULT",
		"/home/mazman/Bureau/2009-11-16_Syntex"	
	};

	/*
	public static void main(String[] args) {

		for(String pathToCorpus : pathsToCorpus) {
			System.out.println("Traitement du corpus: "+pathToCorpus);
			try {
				File pathToCorpusFile = new File(pathToCorpus);
				IResourceTerminological myCorpus =  new SyntexResourceTerminological(pathToCorpusFile);

				System.out.println("\nCorpus Ids:");			
				for(String corpusId : myCorpus.getAllCorpusIds()){
					System.out.println(corpusId);
				}

				System.out.println("\nDocument Ids:");			
				for(String docId : myCorpus.getAllDocumentIds()){
					System.out.println(docId);
				}

				System.out.println("\nNumber of terms: "+myCorpus.getAllTermIds().size());
				System.out.println("Number of documents: "+myCorpus.getAllDocumentIds().size());

				List<String> occId = myCorpus.getAllTermOccurenceIds();
				String anOccurence = occId.get(15);
				String termId = myCorpus.getRelatedTermIdFromTermOccurrenceId(anOccurence);
				//System.out.println("Terme de l'occurence 15: "+ myCorpus.getTermLabel(termId));
				//VALERY 
				System.out.println("Terme de l'occurence 15: "+ myCorpus.getTermCanonicalForm(termId));
				String senId = myCorpus.getRelatedSentenceIdFromTermOccurrenceId(anOccurence);
				System.out.println("Phrase de l'occurence 15: "+ myCorpus.getSentenceContent(senId));
				System.out.println("Indice départ: "+myCorpus.getTermOccurrencePosition(anOccurence));
				System.out.println("Longueur Occ: "+myCorpus.getTermOccurrenceLength(anOccurence));
				System.out.println("Frequence de ce terme: "+myCorpus.getTermFrequency(termId));
				System.out.println("Productivity en tête: "+myCorpus.getTermHeadProductivity(termId));

				// Stat
				//			for(SyntexCategory sCat : SyntexCategory.values()) {
				//				System.out.println(sCat + " "+ sCat.getCount());
				//			}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

*/
}