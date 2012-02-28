package org.dafoe.framework.provider.hibernate.ontological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m23_annotation_type table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m23_annotation_type"
 */
public abstract class BaseOntoAnnotationTypeImpl  implements Serializable {

	public static String PROP_TYPE = "Type";
	public static String PROP_ID = "Id";
	public static String PROP_LABEL = "Label";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.String _label;
	private java.lang.String _type;


	// constructors
	public BaseOntoAnnotationTypeImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseOntoAnnotationTypeImpl (java.lang.Integer _id) {
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
	 * Return the value associated with the column: annotation_type
	 */
	public java.lang.String getType () {
		return _type;
	}

	/**
	 * Set the value related to the column: annotation_type
	 * @param _type the annotation_type value
	 */
	public void setType (java.lang.String _type) {
		this._type = _type;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseOntoAnnotationTypeImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseOntoAnnotationTypeImpl mObj = (org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseOntoAnnotationTypeImpl) obj;
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