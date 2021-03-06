package org.dafoe.framework.provider.hibernate.terminological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m21_terminology table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m21_terminology"
 */
public abstract class BaseTerminologyImpl  implements Serializable {

	public static String PROP_NAME = "Name";
	public static String PROP_ID = "Id";
	public static String PROP_PROJECT = "Project";
	public static String PROP_LANGUAGE = "Language";
	public static String PROP_NAME_SPACE = "NameSpace";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.String _name;
	private java.lang.String _nameSpace;
	private java.lang.String _language;

	// many to one
	private org.dafoe.framework.provider.hibernate.workspace.model.impl.ProjectImpl _project;

	// collections
	private java.util.Set _terms;
	private java.util.Set _termRelations;
	private java.util.Set _termSyntacticRelations;
	private java.util.Set _typeRelationTermino;
	private java.util.Set _mappedTerminoOntologies;


	// constructors
	public BaseTerminologyImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTerminologyImpl (java.lang.Integer _id) {
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
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return _name;
	}

	/**
	 * Set the value related to the column: name
	 * @param _name the name value
	 */
	public void setName (java.lang.String _name) {
		this._name = _name;
	}


	/**
	 * Return the value associated with the column: namespace
	 */
	public java.lang.String getNameSpace () {
		return _nameSpace;
	}

	/**
	 * Set the value related to the column: namespace
	 * @param _nameSpace the namespace value
	 */
	public void setNameSpace (java.lang.String _nameSpace) {
		this._nameSpace = _nameSpace;
	}


	/**
	 * Return the value associated with the column: language
	 */
	public java.lang.String getLanguage () {
		return _language;
	}

	/**
	 * Set the value related to the column: language
	 * @param _language the language value
	 */
	public void setLanguage (java.lang.String _language) {
		this._language = _language;
	}


	/**
     * @hibernate.property
     *  column=project_id
	 */
	public org.dafoe.framework.provider.hibernate.workspace.model.impl.ProjectImpl getProject () {
		return this._project;
	}

	/**
	 * Set the value related to the column: project_id
	 * @param _project the project_id value
	 */
	public void setProject (org.dafoe.framework.provider.hibernate.workspace.model.impl.ProjectImpl _project) {
		this._project = _project;
	}


	/**
	 * Return the value associated with the column: Terms
	 */
	public java.util.Set getTerms () {
		return this._terms;
	}

	/**
	 * Set the value related to the column: Terms
	 * @param _terms the Terms value
	 */
	public void setTerms (java.util.Set _terms) {
		this._terms = _terms;
	}
	
	public void addToTerms (Object obj) {
		if (null == this._terms) this._terms = new java.util.HashSet();
		this._terms.add(obj);
	}



	/**
	 * Return the value associated with the column: TermRelations
	 */
	public java.util.Set getTermRelations () {
		return this._termRelations;
	}

	/**
	 * Set the value related to the column: TermRelations
	 * @param _termRelations the TermRelations value
	 */
	public void setTermRelations (java.util.Set _termRelations) {
		this._termRelations = _termRelations;
	}
	
	public void addToTermRelations (Object obj) {
		if (null == this._termRelations) this._termRelations = new java.util.HashSet();
		this._termRelations.add(obj);
	}



	/**
	 * Return the value associated with the column: TermSyntacticRelations
	 */
	public java.util.Set getTermSyntacticRelations () {
		return this._termSyntacticRelations;
	}

	/**
	 * Set the value related to the column: TermSyntacticRelations
	 * @param _termSyntacticRelations the TermSyntacticRelations value
	 */
	public void setTermSyntacticRelations (java.util.Set _termSyntacticRelations) {
		this._termSyntacticRelations = _termSyntacticRelations;
	}
	
	public void addToTermSyntacticRelations (Object obj) {
		if (null == this._termSyntacticRelations) this._termSyntacticRelations = new java.util.HashSet();
		this._termSyntacticRelations.add(obj);
	}



	/**
	 * Return the value associated with the column: TypeRelationTermino
	 */
	public java.util.Set getTypeRelationTermino () {
		return this._typeRelationTermino;
	}

	/**
	 * Set the value related to the column: TypeRelationTermino
	 * @param _typeRelationTermino the TypeRelationTermino value
	 */
	public void setTypeRelationTermino (java.util.Set _typeRelationTermino) {
		this._typeRelationTermino = _typeRelationTermino;
	}
	
	public void addToTypeRelationTermino (Object obj) {
		if (null == this._typeRelationTermino) this._typeRelationTermino = new java.util.HashSet();
		this._typeRelationTermino.add(obj);
	}



	/**
	 * Return the value associated with the column: MappedTerminoOntologies
	 */
	public java.util.Set getMappedTerminoOntologies () {
		return this._mappedTerminoOntologies;
	}

	/**
	 * Set the value related to the column: MappedTerminoOntologies
	 * @param _mappedTerminoOntologies the MappedTerminoOntologies value
	 */
	public void setMappedTerminoOntologies (java.util.Set _mappedTerminoOntologies) {
		this._mappedTerminoOntologies = _mappedTerminoOntologies;
	}
	
	public void addToMappedTerminoOntologies (Object obj) {
		if (null == this._mappedTerminoOntologies) this._mappedTerminoOntologies = new java.util.HashSet();
		this._mappedTerminoOntologies.add(obj);
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTerminologyImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTerminologyImpl mObj = (org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTerminologyImpl) obj;
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