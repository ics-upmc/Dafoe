/**
 * 
 */
package org.dafoe.plugin.imp.yatea.yateaSAX.item;

/**
 * Object representing an occurrence found in Yatea's XML.
 * 
 * @author Athier
 */
public class YateaOccurrence
{
	String id;
	String mnp;
	String doc;
	String sentence;
	String start_position;
	String end_position;
	
	public YateaOccurrence()
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
	 * @return the mnp
	 */
	public String getMnp() {
		return mnp;
	}

	/**
	 * @param mnp the mnp to set
	 */
	public void setMnp(String mnp) {
		this.mnp = mnp;
	}

	/**
	 * @return the doc
	 */
	public String getDoc() {
		return doc;
	}

	/**
	 * @param doc the doc to set
	 */
	public void setDoc(String doc) {
		this.doc = doc;
	}

	/**
	 * @return the sentence
	 */
	public String getSentence() {
		return sentence;
	}

	/**
	 * @param sentence the sentence to set
	 */
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	/**
	 * @return the start_position
	 */
	public String getStart_position() {
		return start_position;
	}

	/**
	 * @param startPosition the start_position to set
	 */
	public void setStart_position(String startPosition) {
		start_position = startPosition;
	}

	/**
	 * @return the end_position
	 */
	public String getEnd_position() {
		return end_position;
	}

	/**
	 * @param endPosition the end_position to set
	 */
	public void setEnd_position(String endPosition) {
		end_position = endPosition;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YateaOccurrence [id=" + id + ", doc=" + doc
				+ ", mnp=" + mnp + ", sentence=" + sentence
				+ ", start_position=" + start_position
				+ ", end_position=" + end_position + "]";
	}

}
