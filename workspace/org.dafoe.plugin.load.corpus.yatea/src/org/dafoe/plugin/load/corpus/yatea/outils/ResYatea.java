package org.dafoe.plugin.load.corpus.yatea.outils;
/**
 * 
 * @author sylvie Szulman
 * LIPN
 * Projet Dafoe
 * Aout 2009
 * class for strings in french 
 */
import java.util.ListResourceBundle;

public class ResYatea extends ListResourceBundle {
	static final String[][] contents = new String[][] {
		{"ouvrir_un_fichier_treetagger","Ouvrir le fichier Treetagger"},
		{ "ce_fichier_existe_d_j",
		"Ce fichier existe déjà, voulez-vous le détruire?" },
		{"Para_null", "Le paramètre ne peut être null."},
		{"load_file_Yatea","Chargement du fichier Yatea"},
		{"pas_charg_yatea", "lecture du fichier annulée"}
		
	};
	public Object[][] getContents() {
		return contents;
	}

}
