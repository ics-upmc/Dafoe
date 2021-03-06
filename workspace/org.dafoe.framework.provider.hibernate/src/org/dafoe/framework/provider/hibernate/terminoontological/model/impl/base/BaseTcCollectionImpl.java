package org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m22_tc_collection table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m22_tc_collection"
 */
public abstract class BaseTcCollectionImpl  implements Serializable {

	public static String PROP_TERMINO_ONTOLOGY = "TerminoOntology";
	public static String PROP_ID = "Id";
	public static String PROP_LABEL = "Label";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.String _label;

	// many to one
	private org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl _terminoOntology;

	// collections
	private java.util.Set _elements;


	// constructors
	public BaseTcCollectionImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTcCollectionImpl (java.lang.Integer _id) {
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
     * @hibernate.property
     *  column=termino_ontology_id
	 */
	public org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl getTerminoOntology () {
		return this._terminoOntology;
	}

	/**
	 * Set the value related to the column: termino_ontology_id
	 * @param _terminoOntology the termino_ontology_id value
	 */
	public void setTerminoOntology (org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl _terminoOntology) {
		this._terminoOntology = _terminoOntology;
	}


	/**
	 * Return the value associated with the column: Elements
	 */
	public java.util.Set getElements () {
		return this._elements;
	}

	/**
	 * Set the value related to the column: Elements
	 * @param _elements the Elements value
	 */
	public void setElements (java.util.Set _elements) {
		this._elements = _elements;
	}
	
	public void addToElements (Object obj) {
		if (null == this._elements) this._elements = new java.util.HashSet();
		this._elements.add(obj);
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTcCollectionImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTcCollectionImpl mObj = (org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTcCollectionImpl) obj;
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