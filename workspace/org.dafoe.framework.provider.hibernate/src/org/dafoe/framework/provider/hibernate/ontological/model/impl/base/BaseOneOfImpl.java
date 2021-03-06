package org.dafoe.framework.provider.hibernate.ontological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m23_constraint_one_of table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m23_constraint_one_of"
 */
public abstract class BaseOneOfImpl  implements Serializable {

	public static String PROP_RELATED_ENUMERATION = "RelatedEnumeration";
	public static String PROP_RELATED_PROPERTY = "RelatedProperty";
	public static String PROP_RELATED_CLASS = "RelatedClass";
	public static String PROP_ID = "Id";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// many to one
	private org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl _relatedClass;
	private org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl _relatedProperty;
	private org.dafoe.framework.provider.hibernate.ontological.model.impl.EnumerationImpl _relatedEnumeration;


	// constructors
	public BaseOneOfImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOneOfImpl (java.lang.Integer _id) {
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
     *  column=class_id
	 */
	public org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl getRelatedClass () {
		return this._relatedClass;
	}

	/**
	 * Set the value related to the column: class_id
	 * @param _relatedClass the class_id value
	 */
	public void setRelatedClass (org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl _relatedClass) {
		this._relatedClass = _relatedClass;
	}


	/**
     * @hibernate.property
     *  column=datatype_prop_id
	 */
	public org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl getRelatedProperty () {
		return this._relatedProperty;
	}

	/**
	 * Set the value related to the column: datatype_prop_id
	 * @param _relatedProperty the datatype_prop_id value
	 */
	public void setRelatedProperty (org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl _relatedProperty) {
		this._relatedProperty = _relatedProperty;
	}


	/**
     * @hibernate.property
     *  column=enumeration_id
	 */
	public org.dafoe.framework.provider.hibernate.ontological.model.impl.EnumerationImpl getRelatedEnumeration () {
		return this._relatedEnumeration;
	}

	/**
	 * Set the value related to the column: enumeration_id
	 * @param _relatedEnumeration the enumeration_id value
	 */
	public void setRelatedEnumeration (org.dafoe.framework.provider.hibernate.ontological.model.impl.EnumerationImpl _relatedEnumeration) {
		this._relatedEnumeration = _relatedEnumeration;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseOneOfImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseOneOfImpl mObj = (org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseOneOfImpl) obj;
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