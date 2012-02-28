package org.dafoe.plugin.load.corpus.yatea.samples;


/**
 * YateaCorpus Loader: Test class
 */
public class Test {

	// ************ Valéry
	// public static final String pathToCorpus =
	// "/home/sylvie/recherche/workspace/Dafoe";

	//public static final String pathToCorpus = "D:/THESIS/DAFOE/SOURCES/20090915_SVN_FULL/org.dafoe.plugin.load.corpus.yatea/resource";

	// ************ fin valéry
	//VT:
	
	/*
	public static final String pathToCorpus = "D:/THESIS/DAFOE/LIVRAISON/YateaAPI_SYLVIE/2010-04-16/Samples_YateaResources/old";

	public static void main(String[] args) {

		
		//load a corpus
//		File pathToCorpusFile = new File(pathToCorpus);
		AbstractResourceTerminological yateaRes;
		try {
			yateaRes = new YateaResource();
			yateaRes.setResourceName("YateaOld");
			
			// init files location
			yateaRes.initConnection(new File(pathToCorpus));
			
			//
			yateaRes.setResourceName("CorpusYatea4");
			
			// begin importation
			yateaRes.importData();
			/*
		List<String> occ= myCorpus.getAllTermOccurenceIds();
		System.out.println("occ size="+occ.size());
		for(String id: occ){
			System.out.println("id= "+id);
			System.out.println(myCorpus.getTermOccurrenceLabel(id) + "pos= "+myCorpus.getTermOccurrencePosition(id));
		}
			
			*/
			
			/*
			List<String> sent= myCorpus.getAllSentenceIds();
			System.out.println("sent size="+sent.size());
			for(String id: sent){
				System.out.println("id= "+id);
				System.out.println("content="+myCorpus.getSentenceContent(id));
				
			}
			*/
			
	  /*List<String> corpusIds = myCorpus.getAllCorpusIds();

		
		System.out.println("Corpus Ids:");
		
			
		for(String corpusId : myCorpus.getAllCorpusIds()){
			System.out.println(corpusId);
			
			
		}
		//List<String> docIds = myCorpus.getAllDocumentIdsFromCorpusId(currentCorpusId);
		
		System.out.println("\nDocument Ids:");		//myCorpus.getAllDocumentIds()	
		for(String docId : myCorpus.getAllDocumentIds()){
			System.out.println(docId);
		}
		
		System.out.println("\n sentences of term27 "+myCorpus.getAllRelatedSentenceIdsFromTermId("term27"));
		System.out.println("\n term Id of sentence 2"+myCorpus.getAllRelatedTermIdsFromSentenceId("2"));
		System.out.println("\nNumber of terms: "+myCorpus.getAllTermIds().size());
		System.out.println("\nNumber of sentences: "+myCorpus.getAllSentenceIds().size());
		//System.out.println("\nNumber of sentences: "+myCorpus.getAllSentenceIdsFromDocumentId("dirEur_en_2").size());
		System.out.println("\nNumber of sentences: "+myCorpus.getAllSentenceIdsFromDocumentId("corpusSardetResuYatea_1").size());
		List<String> occId = myCorpus.getAllTermOccurenceIds();
//		System.out.println("\nlist of all term occurence Ids: "+myCorpus.getAllTermOccurenceIds());
		
		System.out.println("\nlist of occurences of term27: "+myCorpus.getAllTermOccurenceIdsFromTermId("term27"));
		System.out.println("\n Occurrence Id from sentence 2: "+myCorpus.getAllTermOccurrenceIdsFromSentenceId("2"));
		
		String termId = myCorpus.getRelatedTermIdFromTermOccurrenceId("occ363");
		System.out.println("occurence occ363: "+ myCorpus.getTermOccurrenceLabel("occ363"));
		String senId = myCorpus.getRelatedSentenceIdFromTermOccurrenceId("occ363");
		System.out.println("\n sentence of occ363: "+senId);
		System.out.println("Content of sent 2: "+ myCorpus.getSentenceContent(senId));
		System.out.println("start index of occ363 : "+myCorpus.getTermOccurrencePosition("occ363"));
		System.out.println("length of occ27 : "+myCorpus.getTermOccurrenceLength("occ363"));
		
		
		/
		}
		
		catch (Exception e)
		{
			System.err.println("error "+e.getMessage());
			e.printStackTrace();
		}
		

	}
	*/
	
}