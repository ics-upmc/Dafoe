package org.dafoe.framework.provider.hibernate.ontological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m23_enumeration_value table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m23_enumeration_value"
 */
public abstract class BaseEnumerationValueImpl  implements Serializable {

	public static String PROP_ORDINAL = "Ordinal";
	public static String PROP_ID = "Id";
	public static String PROP_LABEL = "Label";
	public static String PROP_ENUMERATION = "Enumeration";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.String _label;
	private java.lang.Integer _ordinal;

	// many to one
	private org.dafoe.framework.provider.hibernate.ontological.model.impl.EnumerationImpl _enumeration;


	// constructors
	public BaseEnumerationValueImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseEnumerationValueImpl (java.lang.Integer _id) {
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
	 * Return the value associated with the column: ordinal
	 */
	public java.lang.Integer getOrdinal () {
		return _ordinal;
	}

	/**
	 * Set the value related to the column: ordinal
	 * @param _ordinal the ordinal value
	 */
	public void setOrdinal (java.lang.Integer _ordinal) {
		this._ordinal = _ordinal;
	}


	/**
     * @hibernate.property
     *  column=enum_id
	 */
	public org.dafoe.framework.provider.hibernate.ontological.model.impl.EnumerationImpl getEnumeration () {
		return this._enumeration;
	}

	/**
	 * Set the value related to the column: enum_id
	 * @param _enumeration the enum_id value
	 */
	public void setEnumeration (org.dafoe.framework.provider.hibernate.ontological.model.impl.EnumerationImpl _enumeration) {
		this._enumeration = _enumeration;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseEnumerationValueImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseEnumerationValueImpl mObj = (org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseEnumerationValueImpl) obj;
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