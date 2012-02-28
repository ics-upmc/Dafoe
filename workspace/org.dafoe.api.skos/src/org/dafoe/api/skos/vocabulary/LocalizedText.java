/*
 * (c) CRITT Informatique - Copyright 2009 
 */

package org.dafoe.api.skos.vocabulary;

/**
 *  The LocalizedText class.
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class LocalizedText {
	private String text;
	private String lg;
	
	public LocalizedText(){
		this.text = "";
		this.lg = "";
	}

	public LocalizedText(String text, String language){
		this.text = text;
		this.lg = language;
	}
	
	public String getLanguage(){
		return this.lg;
	}
	
	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}

	public void setLanguage(String language){
		this.lg = language;
	}
}
