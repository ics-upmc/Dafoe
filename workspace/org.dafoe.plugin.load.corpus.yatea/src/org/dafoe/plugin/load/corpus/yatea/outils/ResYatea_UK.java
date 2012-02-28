package org.dafoe.plugin.load.corpus.yatea.outils;
/**
 * 
 * @author sylvie Szulman
 * LIPN
 * Projet Dafoe
 * Aout 2009
 * class for strings in english 
 */
import java.util.ListResourceBundle;

public class ResYatea_UK extends ListResourceBundle {
	static final String[][] contents= new String[][] {
		{"ouvrir_un_fichier_treetagger","Load Treetager file"},
		{ "ce_fichier_existe_d_j",
		"This file already exists, do you want to destroy it?" },
		{"Para_null","Parameter cannot be null."},
		{"load_file_Yatea","Load Yatea file"},
		{"pas_charg_yatea", "cancel file reading"}
	};
	
	protected Object[][] getContents() {
		
		return contents;
	}

}
