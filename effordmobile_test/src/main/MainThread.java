package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import data.PathsKeeper;
import logic.ScannersManager;

public class MainThread{
	
	public static int delay = 0;												//args[2] - delay to slow scanning for check scanning interruption
	private static Scanner scanner = new Scanner(System.in);
	private static String sourceFileName;										//args[0] 
	private static String resultFileName;										//args[1]
	private static List<String> listScanningPaths = new ArrayList<>();
	private static ScannersManager manager = null;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//only for debugging, set args without paths.txt
		//args = new String[]{"paths.txt","results.txt", "10"};
		
		if(checkAndSetArguments(args)){																
			if(PathsKeeper.getPathsFromFile(sourceFileName, listScanningPaths)){				//gets paths from paths.txt
				manager = new ScannersManager(listScanningPaths, resultFileName);
				new Thread(manager).start();													//scanning threads start 
			} else System.out.println("paths.txt is empty");
		} else System.out.println("check arguments or existing paths.txt");
		
		System.out.println("\ntype 's' to stop scanning\ntype 'q' to quit programm\n");
		String input = "";
		while (!input.equals("q")) {
			input = scanner.nextLine();
			if((input.equals("s") || input.equals("q")) && manager != null){
				manager.stopThreadWork();														//scanning threads stop on demand 
			}
		}
		scanner.close();		
	}
	
	private static boolean checkAndSetArguments(String[] args) {							
		if(args.length < 2) {
			return false;																		//return false if count of arguments is not valid
		}else if (args.length == 3) {
			delay = Integer.valueOf(args[2]);													//if user set 3 parameter
		}
		sourceFileName = args[0];
		resultFileName = args[1];
		return (new File(args[0])).exists();													//return true if file from args[0] exist
	}
	
	public static void printTable(TreeMap<String, Integer> scanningResult) {					//print scanning result to the console
		new ConsolePrinter(scanningResult).print();
	}
	
	
}