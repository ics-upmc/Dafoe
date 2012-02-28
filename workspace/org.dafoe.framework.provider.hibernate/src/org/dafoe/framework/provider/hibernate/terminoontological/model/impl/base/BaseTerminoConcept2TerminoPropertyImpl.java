package org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m22_terminoconcept_2_terminoproperty table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m22_terminoconcept_2_terminoproperty"
 */
public abstract class BaseTerminoConcept2TerminoPropertyImpl  implements Serializable {

	public static String PROP_TERMINO_CONCEPT = "TerminoConcept";
	public static String PROP_ID = "Id";
	public static String PROP_TERMINO_PROPERTY = "TerminoProperty";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// many to one
	private org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl _terminoConcept;
	private org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoPropertyImpl _terminoProperty;


	// constructors
	public BaseTerminoConcept2TerminoPropertyImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTerminoConcept2TerminoPropertyImpl (java.lang.Integer _id) {
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
     * @hibernate.property
     *  column=tc_id
	 */
	public org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl getTerminoConcept () {
		return this._terminoConcept;
	}

	/**
	 * Set the value related to the column: tc_id
	 * @param _terminoConcept the tc_id value
	 */
	public void setTerminoConcept (org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl _terminoConcept) {
		this._terminoConcept = _terminoConcept;
	}


	/**
     * @hibernate.property
     *  column=terminoproperty_id
	 */
	public org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoPropertyImpl getTerminoProperty () {
		return this._terminoProperty;
	}

	/**
	 * Set the value related to the column: terminoproperty_id
	 * @param _terminoProperty the terminoproperty_id value
	 */
	public void setTerminoProperty (org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoPropertyImpl _terminoProperty) {
		this._terminoProperty = _terminoProperty;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTerminoConcept2TerminoPropertyImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTerminoConcept2TerminoPropertyImpl mObj = (org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTerminoConcept2TerminoPropertyImpl) obj;
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