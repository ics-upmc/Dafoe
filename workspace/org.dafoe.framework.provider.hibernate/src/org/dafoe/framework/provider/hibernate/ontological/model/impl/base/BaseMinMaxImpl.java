package org.dafoe.framework.provider.hibernate.ontological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m23_constraint_min_max table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m23_constraint_min_max"
 */
public abstract class BaseMinMaxImpl  implements Serializable {

	public static String PROP_MIN_VALUE = "MinValue";
	public static String PROP_RELATED_PROPERTY = "RelatedProperty";
	public static String PROP_RELATED_CLASS = "RelatedClass";
	public static String PROP_MAX_VALUE = "MaxValue";
	public static String PROP_ID = "Id";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.Integer _minValue;
	private java.lang.Integer _maxValue;

	// many to one
	private org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl _relatedClass;
	private org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl _relatedProperty;


	// constructors
	public BaseMinMaxImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMinMaxImpl (java.lang.Integer _id) {
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
	 * Return the value associated with the column: match_min_value
	 */
	public java.lang.Integer getMinValue () {
		return _minValue;
	}

	/**
	 * Set the value related to the column: match_min_value
	 * @param _minValue the match_min_value value
	 */
	public void setMinValue (java.lang.Integer _minValue) {
		this._minValue = _minValue;
	}


	/**
	 * Return the value associated with the column: match_max_value
	 */
	public java.lang.Integer getMaxValue () {
		return _maxValue;
	}

	/**
	 * Set the value related to the column: match_max_value
	 * @param _maxValue the match_max_value value
	 */
	public void setMaxValue (java.lang.Integer _maxValue) {
		this._maxValue = _maxValue;
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
     *  column=object_prop_id
	 */
	public org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl getRelatedProperty () {
		return this._relatedProperty;
	}

	/**
	 * Set the value related to the column: object_prop_id
	 * @param _relatedProperty the object_prop_id value
	 */
	public void setRelatedProperty (org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl _relatedProperty) {
		this._relatedProperty = _relatedProperty;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseMinMaxImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseMinMaxImpl mObj = (org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseMinMaxImpl) obj;
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