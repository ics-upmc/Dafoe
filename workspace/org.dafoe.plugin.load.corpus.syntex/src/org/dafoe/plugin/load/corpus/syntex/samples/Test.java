package org.dafoe.plugin.load.corpus.syntex.samples;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.dafoe.plugin.load.corpus.syntex.model.ImportSYNTEX;


/**
 * AlvisCorpus Loader: Test class
 */
public class Test {
	//****************************************begin VALERY ************************
	//public static final String pathToCorpus = "D:/THESIS/DAFOE/SOURCES/20090915_SVN_FULL/org.dafoe.plugin.load.corpus.syntex/resource/";
	public static final String ONE_SYNTEX_FILE = "D:/THESIS/DAFOE/SOURCES/20090915_SVN_FULL/org.dafoe.plugin.load.corpus.syntex/resource/syntex_occ.txt";

	//******************* end VALERY *************************************
	public static void main(String[] args) throws Exception {
		
		try {
			//****************************************begin VALERY***********************
			//File pathToCorpusFile = new File(pathToCorpus);
			//SyntexResourceCorpus myCorpus =  new SyntexResourceCorpus(pathToCorpusFile);
			
			File pathToOneSyntexFile = new File(ONE_SYNTEX_FILE);
			ImportSYNTEX myCorpus =  new ImportSYNTEX();
			
			myCorpus.initConnection(pathToOneSyntexFile);
			
			//******************* end VALERY *************************************
			System.out.println("\nCorpus Ids:");			
			for(String corpusId : myCorpus.getAllCorpusIds()){
				System.out.println(corpusId);
			}

			System.out.println("\nDocument Ids:");			
			for(String docId : myCorpus.getAllDocumentIds()){
				System.out.println(docId);
			}
			
			System.out.println("\nNumber of terms: "+myCorpus.getAllTermIds().size());
			
			List<String> occId = myCorpus.getAllTermOccurenceIds();
			String anOccurence = occId.get(15);
			String termId = myCorpus.getRelatedTermIdFromTermOccurrenceId(anOccurence);
			System.out.println("Terme de l'occurence 15: "+ myCorpus.getTermCanonicalForm(termId));
			String senId = myCorpus.getRelatedSentenceIdFromTermOccurrenceId(anOccurence);
			System.out.println("Phrase de l'occurence 15: "+ myCorpus.getSentenceContent(senId));
			System.out.println("Indice départ: "+myCorpus.getTermOccurrencePosition(anOccurence));
			//System.out.println("Longueur Occ: "+myCorpus.getTermOccurrenceEndPosition(anOccurence));
			System.out.println("Longueur Occ: "+myCorpus.getTermOccurrenceLength(anOccurence));

			
			// Stat
//			for(SyntexCategory sCat : SyntexCategory.values()) {
//				System.out.println(sCat + " "+ sCat.getCount());
//			}


		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}