/**
 * 
 */
package org.dafoe.plugin.load.corpus.syntex.model;

import java.util.logging.Logger;


/** Saillance information about a term.
 * @author Laurent Mazuel
 */
public class SyntexTermSaillance {
	
	/** The linked term from which this information applied. */
	private SyntexTerm linkedTerm = null;
	
	// All Syntex informations
	/** Frequence. */
	private int freq = -1;
	/** Productivité en tete. */
	private int prodT = -1;
	/** Producitvité en expansition. */
	private int prodE = -1;
	/** Nombre de CT qui ont le CT comme descendant (en Tête ou en Expansion). */
	private int nbasc = -1;
	/** Nombre de voisins en Expansion. */
	private int nbvoisterm = -1;
	/** Nombre de voisin en tete. */
	private int nbvoiscont = -1;
	/** Nombre de variante morpho-syntaxique. */
	private int nbvar = -1;
	/** Nombre de docu où apparait le document. */
	private int nbdoc = -1;
	/** Frequence dans le sous-corpus 1. */
	private int fsc1 = -1;
	/** Frequence dans le sous-corpus 2. */
	private int fsc2 = -1;
	
	/** Build a saillance concept for the given term.
	 * @param syntexTerm
	 */
	public SyntexTermSaillance(SyntexTerm syntexTerm) {
		this.linkedTerm = syntexTerm;
	}
	
	/** Computes a syntex term saillance instance using a splitted line from "syntex_list".
	 * Does NEVER throw an exception. If not all information are available, build the 
	 * maximum possible (some field can return -1, which means unailable).
	 * @param splittedLine A splitted line from "syntex_list.txt"
	 * @param term The refering syntex term.
	 * @return The syntex term saillance instance.
	 */
	public SyntexTermSaillance(String[] splittedLine, SyntexTerm term) {
		Logger log = Logger.getLogger(ImportSYNTEX.class.getName());
		try {
			// Here, it is saillance informations
			int freq = parseValue(splittedLine[4]); // syntex freq
			this.setFreq(freq);
			//String length = splittedLine[5]; // ?
			//String nbexp = splittedLine[6]; // ?
			int prodT = parseValue(splittedLine[7]); // syntex prodT
			this.setProdT(prodT);
			int prodE = parseValue(splittedLine[8]); // syntex prodE
			this.setProdE(prodE);
			int nbasc = parseValue(splittedLine[9]); // syntex nbasc
			this.setNbasc(nbasc);
			// String val = splittedLine[10]; // validité		
			int nbvoisterm = parseValue(splittedLine[11]); // syntex nbvoisterm
			this.setNbvoisterm(nbvoisterm);
			int nbvoiscont = parseValue(splittedLine[12]); // syntex nbvoiscont
			this.setNbvoiscont(nbvoiscont);
			int nbvar = parseValue(splittedLine[13]); // syntex nbvar
			this.setNbvar(nbvar);
			//String freqisol = splittedLine[14]; // ?
			int nbdoc = parseValue(splittedLine[15]); // syntex nbdoc
			this.setNbdoc(nbdoc);
			int fsc1 = parseValue(splittedLine[16]); // syntex fsc1
			this.setFsc1(fsc1);
			int fsc2 = parseValue(splittedLine[17]); // sytnex fsc2
			this.setFsc2(fsc2);
		} catch (IndexOutOfBoundsException e) {
			// If this exception, "syntex_list" does not contains enough columns...
			// Do not propagate exception, simply end.
		} catch (Exception e) {
			log.severe("Something strange happens, incomplete saillance information for "+term.getTermId());
			e.printStackTrace(System.out);
		}
	}
	
	/** Try to parse value as an "int", or return -1.
	 * @param value A string which should be an "int"
	 * @return An integer which represente the value, or -1.
	 */
	private int parseValue(String value) {
		try {
			return Integer.valueOf(value);
		}
		catch(Exception e) {
			return -1;
		}
	}

	/**
	 * @return the linkedTerm
	 */
	public SyntexTerm getLinkedTerm() {
		return linkedTerm;
	}

	/** Frequency.
	 * @return the freq
	 */
	public int getFreq() {
		return freq;
	}

	/** Head productivity.
	 * @return the prodT
	 */
	public int getProdT() {
		return prodT;
	}

	/** Expansion productivity.
	 * @return the prodE
	 */
	public int getProdE() {
		return prodE;
	}

	/** Ascendant number.
	 * @return the nbasc
	 */
	public int getNbasc() {
		return nbasc;
	}

	/** Expansion neighbor count.
	 * @return the nbvoisterm
	 */
	public int getNbvoisterm() {
		return nbvoisterm;
	}

	/** Head neighbor count.
	 * @return the nbvoiscont
	 */
	public int getNbvoiscont() {
		return nbvoiscont;
	}

	/** Number of Morpĥo-syntactical variant. 
	 * @return the nbvar
	 */
	public int getNbvar() {
		return nbvar;
	}

	/** Number of document with this term.
	 * @return the nbdoc
	 */
	public int getNbdoc() {
		return nbdoc;
	}

	/** Frequency in sub-corpus 1.
	 * @return the fsc1
	 */
	public int getFsc1() {
		return fsc1;
	}

	/** Frequency in sub-corpus 2.
	 * @return the fsc2
	 */
	public int getFsc2() {
		return fsc2;
	}

	/**
	 * @param freq the freq to set
	 */
	void setFreq(int freq) {
		this.freq = freq;
	}

	/**
	 * @param prodT the prodT to set
	 */
	void setProdT(int prodT) {
		this.prodT = prodT;
	}

	/**
	 * @param prodE the prodE to set
	 */
	void setProdE(int prodE) {
		this.prodE = prodE;
	}

	/**
	 * @param nbasc the nbasc to set
	 */
	void setNbasc(int nbasc) {
		this.nbasc = nbasc;
	}

	/**
	 * @param nbvoisterm the nbvoisterm to set
	 */
	void setNbvoisterm(int nbvoisterm) {
		this.nbvoisterm = nbvoisterm;
	}

	/**
	 * @param nbvoiscont the nbvoiscont to set
	 */
	void setNbvoiscont(int nbvoiscont) {
		this.nbvoiscont = nbvoiscont;
	}

	/**
	 * @param nbvar the nbvar to set
	 */
	void setNbvar(int nbvar) {
		this.nbvar = nbvar;
	}

	/**
	 * @param nbdoc the nbdoc to set
	 */
	void setNbdoc(int nbdoc) {
		this.nbdoc = nbdoc;
	}

	/**
	 * @param fsc1 the fsc1 to set
	 */
	void setFsc1(int fsc1) {
		this.fsc1 = fsc1;
	}

	/**
	 * @param fsc2 the fsc2 to set
	 */
	void setFsc2(int fsc2) {
		this.fsc2 = fsc2;
	}

	
	
}
