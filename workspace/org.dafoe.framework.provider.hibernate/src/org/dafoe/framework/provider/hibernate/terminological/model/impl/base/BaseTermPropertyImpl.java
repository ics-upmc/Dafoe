package org.dafoe.framework.provider.hibernate.terminological.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m21_termproperty table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m21_termproperty"
 */
public abstract class BaseTermPropertyImpl  implements Serializable {

	public static String PROP_TYPE = "Type";
	public static String PROP_ID = "Id";
	public static String PROP_LABEL = "Label";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.String _label;
	private java.lang.String _type;

	// collections
	private java.util.Set _terms;


	// constructors
	public BaseTermPropertyImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTermPropertyImpl (java.lang.Integer _id) {
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
	 * Return the value associated with the column: type
	 */
	public java.lang.String getType () {
		return _type;
	}

	/**
	 * Set the value related to the column: type
	 * @param _type the type value
	 */
	public void setType (java.lang.String _type) {
		this._type = _type;
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



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermPropertyImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermPropertyImpl mObj = (org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermPropertyImpl) obj;
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