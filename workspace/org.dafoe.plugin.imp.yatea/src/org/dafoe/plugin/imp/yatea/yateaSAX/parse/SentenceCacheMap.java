package org.dafoe.plugin.imp.yatea.yateaSAX.parse;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Allows to memories cache map map between Yatea Sentence and Dafoe Hibernate
 * Id.
 * 
 * @author Laurent Mazuel
 */
public class SentenceCacheMap {

	/* The equivalence table (equi-Table ouarf ouarf :-) ) */
	private SortedMap<Integer, SortedMap<Integer, Integer>> equiTable = null;

	public SentenceCacheMap() {
		equiTable = new TreeMap<Integer, SortedMap<Integer, Integer>>();
	}

	public void put(Integer docId, Integer sentenceId, Integer hibernateId) {
		SortedMap<Integer, Integer> internTabble = null;
		if (equiTable.containsKey(docId)) {
			internTabble = equiTable.get(docId);
		} else {
			internTabble = new TreeMap<Integer, Integer>();
			equiTable.put(docId, internTabble);
		}
		internTabble.put(sentenceId, hibernateId);
	}
	
	public Integer get(Integer docId, Integer sentenceId) {
		if(!equiTable.containsKey(docId)) {
			throw new IllegalArgumentException("Docuement id does not exist: "+docId);
		}
		SortedMap<Integer, Integer> internTable = equiTable.get(docId);
		if(!internTable.containsKey(sentenceId)) {
			throw new IllegalArgumentException("Sentence id does not exist: "+sentenceId);
		}
		return internTable.get(sentenceId);
	}
	
	public void clear() {
		equiTable.clear();
	}

}
