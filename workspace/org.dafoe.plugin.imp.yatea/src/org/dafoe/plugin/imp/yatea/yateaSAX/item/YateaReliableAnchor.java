/**
 * 
 */
package org.dafoe.plugin.imp.yatea.yateaSAX.item;

/**
 * Object representing a "reliable anchor" in Yatea's XML.
 * 
 * @author Athier
 */
public class YateaReliableAnchor
{
	String id;
	String form;
	String origin;
	
	public YateaReliableAnchor()
	{ }

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YateaReliableAnchor ["
				+ (form != null ? "form=" + form + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (origin != null ? "origin=" + origin : "") + "]";
	}

}
