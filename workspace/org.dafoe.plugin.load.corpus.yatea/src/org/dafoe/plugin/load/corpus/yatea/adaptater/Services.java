package org.dafoe.plugin.load.corpus.yatea.adaptater;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

	
	//VT: 
	// Identification of terms files:
	// a suffixed approach based on the file name
	private final static String YATEA_FILE_TERMS_SUFFIX="_terms.xml";
		
	public static File findYateaFileTerms(File yateaDirectory){
		List<File> filesInDirectory = Arrays.asList(yateaDirectory.listFiles());
				
		for(File f: filesInDirectory){
			
			if(f.isFile() && f.getName().endsWith(YATEA_FILE_TERMS_SUFFIX)){
				return f;
			}
		}
		return null;
	}

// VT: end	
	
	
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
