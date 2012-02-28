package org.dafoe.plugin.load.corpus.syntex.controler;

/**
 * The Class ControlerFactory.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Val�ry TEGUIAK</a>
 */
public class ControlerFactory {
	private static Controler instanceCtrl;
	
	public static Controler getControler(){
		if(instanceCtrl==null){
			instanceCtrl= new Controler();
		}
		return instanceCtrl;
	}
	
	
}
