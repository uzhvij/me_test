package main;

import java.util.Map.Entry;
import java.util.TreeMap;
import data.TableLayoutData;

public class ConsolePrinter {											//print dynamically changing table
	
	private TreeMap<String, Integer> scanningResult;
	private TableLayoutData layoutData;
	
	public ConsolePrinter(TreeMap<String, Integer> scanningResult) {
		this.scanningResult = scanningResult;
		layoutData = new TableLayoutData(scanningResult);				//calculate and save data for table layout
	}
	
	public void print() {												//print result to console
		for (Entry<String, Integer> row : scanningResult.entrySet()){
			printRow(row);
		}		
	}

	private void printRow(Entry<String, Integer> line) {
		separatingLine(layoutData.tableWidth, false);
		printColumn((++layoutData.serialNumber).toString(), layoutData.firstColumnWidth);
		printColumn(line.getKey(), layoutData.middleColumnWidth);
		printColumn(line.getValue().toString(), layoutData.lastColumnWidth);				
		if(layoutData.serialNumber == layoutData.numberOfLines){				//true if the string is last
			separatingLine(layoutData.tableWidth, true);
		}
	}
	
	private void printColumn(String columnData, int columnWidth){
		int numberOfSpaces = columnWidth - columnData.length();					
		for (int j = 0; j < numberOfSpaces ; j++) {
			System.out.print(" ");
		}
		System.out.print(" " + columnData + " |");
	}
	
	private void separatingLine(int tableWidth, boolean isLast) {
		System.out.print("\n");
		for (int j = 0; j < tableWidth + 10; j++) {
			System.out.print("-");
		}
		System.out.print(isLast?"\n\n":"\n|");
	}
}
