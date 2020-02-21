package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//write result to the file(results.txt by default)
public class ResultWriter {
	private static boolean append = false;
	
	synchronized public static void writeOneDirectoryScanningResultToFile(String resultFileName, String directoryPath, Integer numberOfFiles) {
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(resultFileName, append));
			fileWriter.write(directoryPath + ";" + numberOfFiles);
			fileWriter.newLine();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!append) append = true;
	}
}
