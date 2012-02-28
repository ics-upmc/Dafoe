package org.dafoe.plugin.exp.skos.util;


public class LocalizedText {

	private String text;
	
	private String language;
	
	public LocalizedText(String text, String language) {
		this.text= text;
		this.language= language;
	}
	
	public LocalizedText(String text) {
		
		int idx= text.indexOf("@");
		
		if (idx != -1){// text is a localizedText
			this.text= text.substring(0, idx);
			this.language= text.substring(idx+1);
		}else{// text is not a localizedText
			this.text= text;
			this.language="";
		}
		
	}
	
	public String getLanguage() {
		return language;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		
		return text+" @"+language;
	}
	
	public static void main(String[] args) {
		
		LocalizedText txt= new LocalizedText("tot");
		
		System.out.println(txt.toString());

	}
}
