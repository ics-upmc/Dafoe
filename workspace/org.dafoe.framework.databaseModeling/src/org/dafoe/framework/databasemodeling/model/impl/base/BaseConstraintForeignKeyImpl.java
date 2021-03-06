package org.dafoe.framework.databasemodeling.model.impl.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the m24_constraint table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="m24_constraint"
 */
public abstract class BaseConstraintForeignKeyImpl  extends org.dafoe.framework.databasemodeling.model.impl.ConstraintImpl  implements Serializable {

	public static String PROP_TARGET_TABLE = "TargetTable";
	public static String PROP_ID = "Id";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// many to one
	private org.dafoe.framework.databasemodeling.model.impl.TableImpl _targetTable;


	// constructors
	public BaseConstraintForeignKeyImpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseConstraintForeignKeyImpl (java.lang.Integer _id) {
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


	/**
     * @hibernate.property
     *  column=fk_table_id
	 */
	public org.dafoe.framework.databasemodeling.model.impl.TableImpl getTargetTable () {
		return this._targetTable;
	}

	/**
	 * Set the value related to the column: fk_table_id
	 * @param _targetTable the fk_table_id value
	 */
	public void setTargetTable (org.dafoe.framework.databasemodeling.model.impl.TableImpl _targetTable) {
		this._targetTable = _targetTable;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.dafoe.framework.databasemodeling.model.impl.base.BaseConstraintForeignKeyImpl)) return false;
		else {
			org.dafoe.framework.databasemodeling.model.impl.base.BaseConstraintForeignKeyImpl mObj = (org.dafoe.framework.databasemodeling.model.impl.base.BaseConstraintForeignKeyImpl) obj;
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