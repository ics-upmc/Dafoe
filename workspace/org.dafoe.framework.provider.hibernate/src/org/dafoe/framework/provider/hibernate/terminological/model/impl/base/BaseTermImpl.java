package org.dafoe.framework.provider.hibernate.terminological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m21_term table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m21_term"
 */
public abstract class BaseTermImpl  implements Serializable {

	public static String PROP_STATUS = "Status";
	public static String PROP_TERMINOLOGY = "Terminology";
	public static String PROP_STAR_TERM = "StarTerm";
	public static String PROP_ID = "Id";
	public static String PROP_LABEL = "Label";
	public static String PROP_SAILLANCE = "Saillance";
	public static String PROP_IS_NAMED_ENTITY = "IsNamedEntity";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.String _label;
	private java.lang.Integer _status;
	private java.lang.Integer _isNamedEntity;

	// one to one
	private org.dafoe.framework.provider.hibernate.terminological.model.impl.TermSaillanceImpl _saillance;

	// many to one
	private org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl _starTerm;
	private org.dafoe.framework.provider.hibernate.terminological.model.impl.TerminologyImpl _terminology;

	// collections
	private java.util.Set _termOccurrences;
	private java.util.Set _relationsWhereInTerm1;
	private java.util.Set _relationsWhereInTerm2;
	private java.util.Set _termProperties;
	private java.util.Set _variantes;
	private java.util.Set _modifierInSyntacticRelation;
	private java.util.Set _headInSyntacticRelation;
	private java.util.Set _mappedTerminoConcepts;
	private java.util.Set _annotations;


	// constructors
	public BaseTermImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTermImpl (java.lang.Integer _id) {
		this.setId(_id);
		initialize();
	}

	protected void initialize () {}



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="id"
     */
	public java.lang.Integer getId () {
		return _id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param _id the new ID
	 */
	public void setId (java.lang.Integer _id) {
		this._id = _id;
		this.hashCode = Integer.MIN_VALUE;
	}


	/**
	 * Return the value associated with the column: label
	 */
	public java.lang.String getLabel () {
		return _label;
	}

	/**
	 * Set the value related to the column: label
	 * @param _label the label value
	 */
	public void setLabel (java.lang.String _label) {
		this._label = _label;
	}


	/**
	 * Return the value associated with the column: status
	 */
	public java.lang.Integer getStatus () {
		return _status;
	}

	/**
	 * Set the value related to the column: status
	 * @param _status the status value
	 */
	public void setStatus (java.lang.Integer _status) {
		this._status = _status;
	}


	/**
	 * Return the value associated with the column: is_named_entity
	 */
	public java.lang.Integer getIsNamedEntity () {
		return _isNamedEntity;
	}

	/**
	 * Set the value related to the column: is_named_entity
	 * @param _isNamedEntity the is_named_entity value
	 */
	public void setIsNamedEntity (java.lang.Integer _isNamedEntity) {
		this._isNamedEntity = _isNamedEntity;
	}


	/**
     * @hibernate.property
     *  column=Saillance
	 */
	public org.dafoe.framework.provider.hibernate.terminological.model.impl.TermSaillanceImpl getSaillance () {
		return this._saillance;
	}

	/**
	 * Set the value related to the column: Saillance
	 * @param _saillance the Saillance value
	 */
	public void setSaillance (org.dafoe.framework.provider.hibernate.terminological.model.impl.TermSaillanceImpl _saillance) {
		this._saillance = _saillance;
	}


	/**
     * @hibernate.property
     *  column=star_term_id
	 */
	public org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl getStarTerm () {
		return this._starTerm;
	}

	/**
	 * Set the value related to the column: star_term_id
	 * @param _starTerm the star_term_id value
	 */
	public void setStarTerm (org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl _starTerm) {
		this._starTerm = _starTerm;
	}


	/**
     * @hibernate.property
     *  column=terminology_id
	 */
	public org.dafoe.framework.provider.hibernate.terminological.model.impl.TerminologyImpl getTerminology () {
		return this._terminology;
	}

	/**
	 * Set the value related to the column: terminology_id
	 * @param _terminology the terminology_id value
	 */
	public void setTerminology (org.dafoe.framework.provider.hibernate.terminological.model.impl.TerminologyImpl _terminology) {
		this._terminology = _terminology;
	}


	/**
	 * Return the value associated with the column: TermOccurrences
	 */
	public java.util.Set getTermOccurrences () {
		return this._termOccurrences;
	}

	/**
	 * Set the value related to the column: TermOccurrences
	 * @param _termOccurrences the TermOccurrences value
	 */
	public void setTermOccurrences (java.util.Set _termOccurrences) {
		this._termOccurrences = _termOccurrences;
	}
	
	public void addToTermOccurrences (Object obj) {
		if (null == this._termOccurrences) this._termOccurrences = new java.util.HashSet();
		this._termOccurrences.add(obj);
	}



	/**
	 * Return the value associated with the column: RelationsWhereInTerm1
	 */
	public java.util.Set getRelationsWhereInTerm1 () {
		return this._relationsWhereInTerm1;
	}

	/**
	 * Set the value related to the column: RelationsWhereInTerm1
	 * @param _relationsWhereInTerm1 the RelationsWhereInTerm1 value
	 */
	public void setRelationsWhereInTerm1 (java.util.Set _relationsWhereInTerm1) {
		this._relationsWhereInTerm1 = _relationsWhereInTerm1;
	}
	
	public void addToRelationsWhereInTerm1 (Object obj) {
		if (null == this._relationsWhereInTerm1) this._relationsWhereInTerm1 = new java.util.HashSet();
		this._relationsWhereInTerm1.add(obj);
	}



	/**
	 * Return the value associated with the column: RelationsWhereInTerm2
	 */
	public java.util.Set getRelationsWhereInTerm2 () {
		return this._relationsWhereInTerm2;
	}

	/**
	 * Set the value related to the column: RelationsWhereInTerm2
	 * @param _relationsWhereInTerm2 the RelationsWhereInTerm2 value
	 */
	public void setRelationsWhereInTerm2 (java.util.Set _relationsWhereInTerm2) {
		this._relationsWhereInTerm2 = _relationsWhereInTerm2;
	}
	
	public void addToRelationsWhereInTerm2 (Object obj) {
		if (null == this._relationsWhereInTerm2) this._relationsWhereInTerm2 = new java.util.HashSet();
		this._relationsWhereInTerm2.add(obj);
	}



	/**
	 * Return the value associated with the column: TermProperties
	 */
	public java.util.Set getTermProperties () {
		return this._termProperties;
	}

	/**
	 * Set the value related to the column: TermProperties
	 * @param _termProperties the TermProperties value
	 */
	public void setTermProperties (java.util.Set _termProperties) {
		this._termProperties = _termProperties;
	}
	
	public void addToTermProperties (Object obj) {
		if (null == this._termProperties) this._termProperties = new java.util.HashSet();
		this._termProperties.add(obj);
	}



	/**
	 * Return the value associated with the column: Variantes
	 */
	public java.util.Set getVariantes () {
		return this._variantes;
	}

	/**
	 * Set the value related to the column: Variantes
	 * @param _variantes the Variantes value
	 */
	public void setVariantes (java.util.Set _variantes) {
		this._variantes = _variantes;
	}
	
	public void addToVariantes (Object obj) {
		if (null == this._variantes) this._variantes = new java.util.HashSet();
		this._variantes.add(obj);
	}



	/**
	 * Return the value associated with the column: ModifierInSyntacticRelation
	 */
	public java.util.Set getModifierInSyntacticRelation () {
		return this._modifierInSyntacticRelation;
	}

	/**
	 * Set the value related to the column: ModifierInSyntacticRelation
	 * @param _modifierInSyntacticRelation the ModifierInSyntacticRelation value
	 */
	public void setModifierInSyntacticRelation (java.util.Set _modifierInSyntacticRelation) {
		this._modifierInSyntacticRelation = _modifierInSyntacticRelation;
	}
	
	public void addToModifierInSyntacticRelation (Object obj) {
		if (null == this._modifierInSyntacticRelation) this._modifierInSyntacticRelation = new java.util.HashSet();
		this._modifierInSyntacticRelation.add(obj);
	}



	/**
	 * Return the value associated with the column: HeadInSyntacticRelation
	 */
	public java.util.Set getHeadInSyntacticRelation () {
		return this._headInSyntacticRelation;
	}

	/**
	 * Set the value related to the column: HeadInSyntacticRelation
	 * @param _headInSyntacticRelation the HeadInSyntacticRelation value
	 */
	public void setHeadInSyntacticRelation (java.util.Set _headInSyntacticRelation) {
		this._headInSyntacticRelation = _headInSyntacticRelation;
	}
	
	public void addToHeadInSyntacticRelation (Object obj) {
		if (null == this._headInSyntacticRelation) this._headInSyntacticRelation = new java.util.HashSet();
		this._headInSyntacticRelation.add(obj);
	}



	/**
	 * Return the value associated with the column: MappedTerminoConcepts
	 */
	public java.util.Set getMappedTerminoConcepts () {
		return this._mappedTerminoConcepts;
	}

	/**
	 * Set the value related to the column: MappedTerminoConcepts
	 * @param _mappedTerminoConcepts the MappedTerminoConcepts value
	 */
	public void setMappedTerminoConcepts (java.util.Set _mappedTerminoConcepts) {
		this._mappedTerminoConcepts = _mappedTerminoConcepts;
	}
	
	public void addToMappedTerminoConcepts (Object obj) {
		if (null == this._mappedTerminoConcepts) this._mappedTerminoConcepts = new java.util.HashSet();
		this._mappedTerminoConcepts.add(obj);
	}



	/**
	 * Return the value associated with the column: Annotations
	 */
	public java.util.Set getAnnotations () {
		return this._annotations;
	}

	/**
	 * Set the value related to the column: Annotations
	 * @param _annotations the Annotations value
	 */
	public void setAnnotations (java.util.Set _annotations) {
		this._annotations = _annotations;
	}
	
	public void addToAnnotations (Object obj) {
		if (null == this._annotations) this._annotations = new java.util.HashSet();
		this._annotations.add(obj);
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermImpl mObj = (org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermImpl) obj;
			if (null == this.getId() || null == mObj.getId()) return false;
			else return (this.getId().equals(mObj.getId()));
		}
	}


	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}

}