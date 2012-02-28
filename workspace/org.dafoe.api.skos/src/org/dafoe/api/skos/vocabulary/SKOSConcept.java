/*
 * (c) CRITT Informatique - Copyright 2009 
 */

package org.dafoe.api.skos.vocabulary;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * The SKOSConcept class.
 *  @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class SKOSConcept extends SKOSResource{	
   	
	private boolean isTopConcept= false;
	
	public SKOSConcept(String uri){
		super(SKOS.Concept, uri);
		SKOSModel.addConcept(this);
		
	}

	public SKOSConcept(Resource rsc){
		super(rsc);
		SKOSModel.addConcept(this);
	}
		
	public String getAltLabel(String language){
		return getTextInLanguage(SKOS.altLabel, language);
	}
		
	public LocalizedText[] getAltLabels(){
		return getLocalizedText(SKOS.altLabel);
	}

	public SKOSConcept[] getBroaders(){
		return getSKOSResourceForProperty(SKOS.broader).toArray(new SKOSConcept[0]);
	}

	public String getChangeNote(String language){
		return getTextInLanguage(SKOS.changeNote, language);
	}

	public LocalizedText[] getChangeNote(){
		return getLocalizedText(SKOS.changeNote);
	}
		
	public String getDefinition(String language){
		return getTextInLanguage(SKOS.definition, language);
	}

	public LocalizedText[] getDefinition(){
		return getLocalizedText(SKOS.definition);
	}

	public String getEditorialNote(String language){
		return getTextInLanguage(SKOS.editorialNote, language);
	}

	public LocalizedText[] getEditorialNote(){
		return getLocalizedText(SKOS.editorialNote);
	}
		
	public String getExample(String language){
		return getTextInLanguage(SKOS.example, language);
	}

	public LocalizedText[] getExample(){
		return getLocalizedText(SKOS.example);
	}
			
	public String getHiddenLabel(String language){
		return getTextInLanguage(SKOS.hiddenLabel, language);
	}
		
	public LocalizedText[] getHiddenLabels(){
		return getLocalizedText(SKOS.hiddenLabel);
	}

	public String getHistoryNote(String language){
		return getTextInLanguage(SKOS.historyNote, language);
	}

	public LocalizedText[] getHistoryNote(){
		return getLocalizedText(SKOS.editorialNote);
	}

	public SKOSConceptScheme[] getInSchemes(){
		return getSKOSResourceForProperty(SKOS.inScheme).toArray(new SKOSConceptScheme[0]);
	}
	
	public SKOSConcept[] getNarrowers(){
		Set<SKOSResource> nar= new HashSet<SKOSResource>();
		// calculate narrowers using the narrow  property
		nar.addAll(getSKOSResourceForProperty(SKOS.narrower));
		
		// calculate narrowers using the broader  property through ther narrow skosconcept
		  SKOSConcept[] allConcepts= SKOSModel.getConcepts();
		   for(SKOSConcept c: allConcepts){
			   for(SKOSConcept cBroader: c.getBroaders()){
				   if(cBroader.equals(this)){
					   nar.add(c);
				   }
			   }
		   }
		//return getSKOSResourceForProperty(SKOS.narrower).toArray(new SKOSConcept[0]);
		   return nar.toArray(new SKOSConcept[0]);
	}

	public String getNotation(){
		// to be implemented
		return("");
	}
	
	public String getNote(String language){
		return getTextInLanguage(SKOS.note, language);
	}

	public LocalizedText[] getNote(){
		return getLocalizedText(SKOS.note);
	}

	public String getPrefLabel(String language){
		return getTextInLanguage(SKOS.prefLabel, language);
	}

	public LocalizedText[] getPrefLabels(){
		return getLocalizedText(SKOS.prefLabel);
	}
		
	public SKOSConcept[] getRelateds(){	
		return getSKOSResourceForProperty(SKOS.related).toArray(new SKOSConcept[0]);
	}
	
	public SKOSConcept[] getSemanticRelations(){
		return getSKOSResourceForProperty(SKOS.semanticRelation).toArray(new SKOSConcept[0]);
	}
	
	public SKOSConceptScheme[] getTopConceptOfs(){		
		return getSKOSResourceForProperty(SKOS.topConceptOf).toArray(new SKOSConceptScheme[0]);
	}
	
	public void addAltLabel(String label){
		this.rsc.addProperty(SKOS.altLabel, label);
	}

	public void addAltLabel(String label, String language){
		this.rsc.addProperty(SKOS.altLabel, SKOSModel.getModel().createLiteral(label, language));
	}

	public void addBroader(SKOSConcept c){
		this.rsc.addProperty(SKOS.broader, c.getResource());
	}
	
	public void addBroaderTransitive(SKOSConcept c){
		this.rsc.addProperty(SKOS.broaderTransitive, c.getResource());
	}

	public void addChangeNote(String changeNote){
		this.rsc.addProperty(SKOS.changeNote, changeNote);
	}

	public void addChangeNote(String changeNote, String language){
		this.rsc.addProperty(SKOS.changeNote, SKOSModel.getModel().createLiteral(changeNote, language));
	}

	public void addDefinition(String definition){
		this.rsc.addProperty(SKOS.definition, definition);
	}

	public void addDefinition(String definition, String language){
		this.rsc.addProperty(SKOS.definition, SKOSModel.getModel().createLiteral(definition, language));
	}

	public void addEditorialNote(String editorialNote){
		this.rsc.addProperty(SKOS.editorialNote, editorialNote);
	}

	public void addEditorialNote(String editorialNote, String language){
		this.rsc.addProperty(SKOS.editorialNote, SKOSModel.getModel().createLiteral(editorialNote, language));
	}

	public void addExample(String example){
		this.rsc.addProperty(SKOS.example, example);
	}

	public void addExample(String example, String language){
		this.rsc.addProperty(SKOS.example, SKOSModel.getModel().createLiteral(example, language));
	}

	public void addHiddenLabel(String label){
		this.rsc.addProperty(SKOS.hiddenLabel, label);
	}

	public void addHiddenLabel(String label, String language){
		this.rsc.addProperty(SKOS.hiddenLabel, SKOSModel.getModel().createLiteral(label, language));
	}

	public void addHistoryNote(String historyNote){
		this.rsc.addProperty(SKOS.historyNote, historyNote);
	}

	public void addHistoryNote(String historyNote, String language){
		this.rsc.addProperty(SKOS.historyNote, SKOSModel.getModel().createLiteral(historyNote, language));
	}

	public void setInScheme(SKOSConceptScheme cs){
		this.rsc.addProperty(SKOS.inScheme, cs.getResource());
	}

	public void addNarrower(SKOSConcept c){
		this.rsc.addProperty(SKOS.narrower, c.getResource());
	}
	
	public void addNarrowerTransitive(SKOSConcept c){
		this.rsc.addProperty(SKOS.narrowerTransitive, c.getResource());
	}
	
	public void addNotation(String notation, String datatype){
		this.rsc.addProperty(SKOS.notation, SKOSModel.getModel().createTypedLiteral(notation, datatype));
	}

	public void addNote(String note){
		this.rsc.addProperty(SKOS.note, note);
	}

	public void addNote(String note, String language){
		this.rsc.addProperty(SKOS.note, SKOSModel.getModel().createLiteral(note, language));
	}

	public void addPrefLabel(String label){
		this.rsc.addProperty(SKOS.prefLabel, label);
	}

	public void addPrefLabel(String label, String language){
		this.rsc.addProperty(SKOS.prefLabel, SKOSModel.getModel().createLiteral(label, language));
	}

	public void addRelated(SKOSConcept c){
		this.rsc.addProperty(SKOS.related, c.getResource());
	}
	
	public void addSemanticRelation(SKOSConcept c){
		this.rsc.addProperty(SKOS.semanticRelation, c.getResource());
	}
	
	public void addTopConceptOf(SKOSConceptScheme cs){
		this.rsc.addProperty(SKOS.topConceptOf, cs.getResource());
	}

	public String toString(){
		return this.getResource().toString();
	}
	
	private LocalizedText[] getLocalizedText(Property property){
		Set<LocalizedText> hs = new HashSet<LocalizedText>();
		LocalizedText lt;
		StmtIterator iter = this.getResource().listProperties(property);
		while (iter.hasNext()) {
			lt = new LocalizedText();
			Statement st = iter.nextStatement();
			Literal lit = (Literal) st.getObject().as(Literal.class);
			lt.setText(lit.getLexicalForm());
			lt.setLanguage(lit.getLanguage());
			hs.add(lt);
		}
		return hs.toArray(new LocalizedText[0]);		
	}

	private String getTextInLanguage(Property property, String language){
		String res = "";
		LocalizedText[] hs = getLocalizedText(property);
		for(int i = 0; i < hs.length; i++){
			if (language.equalsIgnoreCase(hs[i].getLanguage())){
				res = hs[i].getText();
			}
		}
		return res;
	}
	
	//VT: 
	public boolean isTopConcept(){
		return this.isTopConcept;
	}
	
	public void setAsTopConcept(boolean status){
		this.isTopConcept= status;
		
		if(status){
			this.rsc.addLiteral(SKOS.hasTopConcept, this.getResource());
		}
	}
	
}
