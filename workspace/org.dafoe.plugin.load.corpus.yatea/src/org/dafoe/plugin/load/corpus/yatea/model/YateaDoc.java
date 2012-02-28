package org.dafoe.plugin.load.corpus.yatea.model;
import java.io.Serializable;
import java.util.*;
import org.jdom.Element;
/**
 * 
 * @author sylvie Szulman
 * LIPN
 * Projet Dafoe
 * Aout 2009
 * Describe a document which contains many sentences
 *
 */
@SuppressWarnings("serial")
public class YateaDoc implements Serializable{
	private String ID; //identifiant du document
	private ArrayList<YateaSentence> ensSent; 
	
	public YateaDoc(String nom) {
		super();
		this.ID = nom;
		ensSent = new ArrayList<YateaSentence>();
	}
	public YateaDoc(){
		ensSent = new ArrayList<YateaSentence>();
	}
	public void litXml(Element elem){
		this.ID = elem.getAttributeValue("ID");
		List<Element> aList =  elem.getChildren("SENTENCE");
		for(Iterator<Element> i= aList.iterator();i.hasNext();){
			Element unElem = i.next();
			YateaSentence sent = new YateaSentence();
			sent.litXml(unElem);
			ensSent.add(sent);
		}
	}

	public ArrayList<YateaSentence> getEnsSent() {
		return ensSent;
	}

	public void setEnsSent(ArrayList<YateaSentence> ensSent) {
		this.ensSent = ensSent;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		this.ID = id;
	}
	 
	public  YateaSentence getUneSentence(String numero) {
		
		return ensSent.get(Integer.parseInt(numero));
	}
	

}
