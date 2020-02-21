package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class PathsKeeper {

	//Read directories paths from the file(paths.txt by default)
	public static  boolean getPathsFromFile(String sourceFileName, List<String> listScanningPaths) {
		BufferedReader reader;
        String line;
        try {
        	reader = new BufferedReader(new FileReader(sourceFileName));
			while ((line = reader.readLine()) != null) {
				if((new File(line)).exists()){								//check - does path from paths.txt exist?
					listScanningPaths.add(line);							//add path from file to list
				}else {
					listScanningPaths.add(line + " does not exist");
				}
			}
			reader.close();
		} catch (IOException e ) {
			e.printStackTrace();
			return false;
		}
        if(listScanningPaths.size() > 0){									//check - was any path added to list?
    		return true;
        }else {
			return false;
		}
	}
}
