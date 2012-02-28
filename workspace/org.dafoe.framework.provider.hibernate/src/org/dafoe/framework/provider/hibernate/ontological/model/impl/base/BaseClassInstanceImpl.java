package org.dafoe.framework.provider.hibernate.ontological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m13_class_instance table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m13_class_instance"
 */
public abstract class BaseClassInstanceImpl  implements Serializable {

	public static String PROP_PROPERTY_VALUE = "PropertyValue";
	public static String PROP_ID = "Id";
	public static String PROP_PROPERTY_LABEL = "PropertyLabel";
	public static String PROP_OBJECT_PROPERTY = "ObjectProperty";
	public static String PROP_ITS_CLASS = "ItsClass";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.String _propertyLabel;
	private java.lang.String _propertyValue;
	private boolean _objectProperty;

	// many to one
	private org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl _itsClass;


	// constructors
	public BaseClassInstanceImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseClassInstanceImpl (java.lang.Integer _id) {
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
	 * Return the value associated with the column: property_label
	 */
	public java.lang.String getPropertyLabel () {
		return _propertyLabel;
	}

	/**
	 * Set the value related to the column: property_label
	 * @param _propertyLabel the property_label value
	 */
	public void setPropertyLabel (java.lang.String _propertyLabel) {
		this._propertyLabel = _propertyLabel;
	}


	/**
	 * Return the value associated with the column: property_value
	 */
	public java.lang.String getPropertyValue () {
		return _propertyValue;
	}

	/**
	 * Set the value related to the column: property_value
	 * @param _propertyValue the property_value value
	 */
	public void setPropertyValue (java.lang.String _propertyValue) {
		this._propertyValue = _propertyValue;
	}


	/**
	 * Return the value associated with the column: is_object_property
	 */
	public boolean isObjectProperty () {
		return _objectProperty;
	}

	/**
	 * Set the value related to the column: is_object_property
	 * @param _objectProperty the is_object_property value
	 */
	public void setObjectProperty (boolean _objectProperty) {
		this._objectProperty = _objectProperty;
	}


	/**
     * @hibernate.property
     *  column=class_id
	 */
	public org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl getItsClass () {
		return this._itsClass;
	}

	/**
	 * Set the value related to the column: class_id
	 * @param _itsClass the class_id value
	 */
	public void setItsClass (org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl _itsClass) {
		this._itsClass = _itsClass;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseClassInstanceImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseClassInstanceImpl mObj = (org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseClassInstanceImpl) obj;
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