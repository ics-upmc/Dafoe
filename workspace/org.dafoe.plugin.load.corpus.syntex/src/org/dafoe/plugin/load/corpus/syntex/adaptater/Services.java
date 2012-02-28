package org.dafoe.plugin.load.corpus.syntex.adaptater;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.hibernate.Session;

/**
 * The Class Services.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class Services {

	public static Session getDafoeSession() {
		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	
	
	public static  List<File> getAllFiles(File directory){
		List<File> files= new ArrayList<File>();
		
		if(directory.isDirectory()){
			for (File f: directory.listFiles()){
				if(f.isFile()){
					files.add(f);
				}
			}
		}
		
		//System.out.println("size files= "+files.size()); //$NON-NLS-1$
		return files;
	}
}
