package org.dafoe.framework.provider.hibernate.terminological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m11_termoccurrence table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m11_termoccurrence"
 */
public abstract class BaseTermOccurrenceImpl  implements Serializable {

	public static String PROP_RELATED_TERM = "RelatedTerm";
	public static String PROP_RELATED_SENTENCE = "RelatedSentence";
	public static String PROP_LENGTH = "Length";
	public static String PROP_POSITION = "Position";
	public static String PROP_ID = "Id";
	public static String PROP_RELATED_TERMINO_CONCEPT_OCCURRENCE = "RelatedTerminoConceptOccurrence";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.Integer _position;
	private java.lang.Integer _length;

	// one to one
	private org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptOccurrenceImpl _relatedTerminoConceptOccurrence;

	// many to one
	private org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl _relatedSentence;
	private org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl _relatedTerm;


	// constructors
	public BaseTermOccurrenceImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTermOccurrenceImpl (java.lang.Integer _id) {
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
	 * Return the value associated with the column: position
	 */
	public java.lang.Integer getPosition () {
		return _position;
	}

	/**
	 * Set the value related to the column: position
	 * @param _position the position value
	 */
	public void setPosition (java.lang.Integer _position) {
		this._position = _position;
	}


	/**
	 * Return the value associated with the column: length
	 */
	public java.lang.Integer getLength () {
		return _length;
	}

	/**
	 * Set the value related to the column: length
	 * @param _length the length value
	 */
	public void setLength (java.lang.Integer _length) {
		this._length = _length;
	}


	/**
     * @hibernate.property
     *  column=RelatedTerminoConceptOccurrence
	 */
	public org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptOccurrenceImpl getRelatedTerminoConceptOccurrence () {
		return this._relatedTerminoConceptOccurrence;
	}

	/**
	 * Set the value related to the column: RelatedTerminoConceptOccurrence
	 * @param _relatedTerminoConceptOccurrence the RelatedTerminoConceptOccurrence value
	 */
	public void setRelatedTerminoConceptOccurrence (org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptOccurrenceImpl _relatedTerminoConceptOccurrence) {
		this._relatedTerminoConceptOccurrence = _relatedTerminoConceptOccurrence;
	}


	/**
     * @hibernate.property
     *  column=sentence_id
	 */
	public org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl getRelatedSentence () {
		return this._relatedSentence;
	}

	/**
	 * Set the value related to the column: sentence_id
	 * @param _relatedSentence the sentence_id value
	 */
	public void setRelatedSentence (org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl _relatedSentence) {
		this._relatedSentence = _relatedSentence;
	}


	/**
     * @hibernate.property
     *  column=term_id
	 */
	public org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl getRelatedTerm () {
		return this._relatedTerm;
	}

	/**
	 * Set the value related to the column: term_id
	 * @param _relatedTerm the term_id value
	 */
	public void setRelatedTerm (org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl _relatedTerm) {
		this._relatedTerm = _relatedTerm;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermOccurrenceImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermOccurrenceImpl mObj = (org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermOccurrenceImpl) obj;
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