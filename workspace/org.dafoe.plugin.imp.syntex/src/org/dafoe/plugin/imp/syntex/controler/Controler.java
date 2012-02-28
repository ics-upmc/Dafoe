package org.dafoe.plugin.imp.syntex.controler;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.dafoe.framework.core.terminological.model.ITerminology;

/**
 * The Class Controler.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Val�ry TEGUIAK</a>
 */
public class Controler {
	
	private  ITerminology currentSelectedTerminology;
	
	
	
	private  PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public static final String SELECT_TERMINOLOGY_EVENT="selectTerminologyEvent";
	
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	public  ITerminology getCurrentSelectedTerminology(){
	 return 	currentSelectedTerminology;
	}
	
	public  void setCurrentSelectedOntology(
			ITerminology newTermino) {
		ITerminology oldSelectedTermino= currentSelectedTerminology;
		currentSelectedTerminology = newTermino;
		
		propertyChangeSupport.firePropertyChange(SELECT_TERMINOLOGY_EVENT, oldSelectedTermino, currentSelectedTerminology);	
	}
	
	
	

}
