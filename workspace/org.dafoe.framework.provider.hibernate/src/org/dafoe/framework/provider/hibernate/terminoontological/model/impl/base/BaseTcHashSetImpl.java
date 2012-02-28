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
public abstract class BaseTcHashSetImpl  extends org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TcCollectionImpl  implements Serializable {

	public static String PROP_ID = "Id";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;


	// constructors
	public BaseTcHashSetImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTcHashSetImpl (java.lang.Integer _id) {
		super(_id);
	}



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


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTcHashSetImpl)) return false;
		else {
			org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTcHashSetImpl mObj = (org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTcHashSetImpl) obj;
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