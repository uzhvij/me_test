package data;

import java.util.Map;
import java.util.TreeMap;

//calculate and save data for dynamic table layout
public class TableLayoutData {
	
	public int firstColumnWidth;
	public int middleColumnWidth;
	public int lastColumnWidth;
	public int tableWidth;
	public Integer serialNumber;
	public int numberOfLines;
	
	public TableLayoutData(TreeMap<String, Integer> scanningResult) {
		firstColumnWidth = String.valueOf(scanningResult.size()).length();				//longest serial number	set width first column
		middleColumnWidth = scanningResult.lastKey().length();							//longest path set width middle column
		lastColumnWidth = scanningResult.entrySet().stream().							//longest number of files set width third column
				max(Map.Entry.comparingByValue()).get().getValue().toString().length();
		tableWidth = firstColumnWidth + middleColumnWidth + lastColumnWidth;
		serialNumber = 0;
		numberOfLines = scanningResult.size();
	}
}
