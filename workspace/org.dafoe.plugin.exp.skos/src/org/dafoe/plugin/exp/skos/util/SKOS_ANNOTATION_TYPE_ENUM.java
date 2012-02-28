package org.dafoe.plugin.exp.skos.util;

public enum SKOS_ANNOTATION_TYPE_ENUM {
		
	SKOS_PREF_LABEL("skos:prefLabel"), 
	SKOS_ALT_LABEL("skos:altLabel"), 
	SKOS_HIDDEN_LABEL("skos:hiddenLabel"), 
	SKOS_NOTE("skos:note"), 
	SKOS_DEFINITION("skos:definition"), 
	SKOS_EXAMPLE("skos:example"), 
	SKOS_HISTORY_NOTE("skos:historyNote"), 
	SKOS_EDITORIAL_NOTE("skos:editorialNote"), 
	SKOS_CHANGE_NOTE("skos:changeNote"); 
	
	private String label;
	
	SKOS_ANNOTATION_TYPE_ENUM(String lb){
		this.label= lb;
	}
	
	public String getLabel() {
		return label;
	}
	
}