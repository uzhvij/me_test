package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import data.ResultWriter;
import main.MainThread;

public class ScannersManager implements Runnable, WorkStoper{				//class that managed threads, which scan directories

	ExecutorService executor;												//service that launched scanning threads
	CountDownLatch latch; 													//used for triggering, when all scanning stop 
	List<DirectoryScanner> listStoppableThreads;							//list used for stop by demand all scanning threads
	TreeMap<String, Integer> scanningResult;
	List<String> listScanningPaths;
	String resultFileName;;
	
	public ScannersManager(List<String> listScanningPaths, String resultFileName) {
		this.resultFileName = resultFileName;
		this.listScanningPaths = listScanningPaths;
		latch = new CountDownLatch(listScanningPaths.size());
		executor = Executors.newFixedThreadPool(listScanningPaths.size());
		listStoppableThreads = new ArrayList<>(listScanningPaths.size());		
		this.scanningResult = new TreeMap<>((aStr, bStr) -> aStr.length() - bStr.length()); 	//sorted map, from shortest to longest string 
	}
	
	@Override
	public void run() {
		for (String scanningPath : listScanningPaths) {
			DirectoryScanner directoryScanner = new DirectoryScanner(scanningPath, latch, scanningResult);
			listStoppableThreads.add(directoryScanner);
			executor.execute(directoryScanner);								//start scanning thread
		}
		try {												
			latch.await();													//waiting a moment when all scanning threads stop
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
		
		ResultWriter.writeResultToFile(resultFileName, scanningResult);		//write result to file
		MainThread.printTable(scanningResult);								//print result to console
	}

	@Override
	public void stopThreadWork() {											//used for stop all scanning threads
		for (WorkStoper workStoper : listStoppableThreads) {
			workStoper.stopThreadWork();						
		}
	}
}
