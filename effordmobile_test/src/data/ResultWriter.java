package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

//write result to the file(results.txt by default)
public class ResultWriter {
	public static void writeResultToFile(String resultFileName, TreeMap<String, Integer> scanningResult) {
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(resultFileName));
			for (Entry<String, Integer> line : scanningResult.entrySet()) {
				fileWriter.write(line.getKey() + ";" + line.getValue().toString());
				fileWriter.newLine();
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
