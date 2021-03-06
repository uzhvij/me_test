package logic;

import java.io.File;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

import data.ResultWriter;
import main.MainThread;

//class that scan given file directory and save result to file
public class DirectoryScanner implements Runnable, WorkStoper {		
	
	TreeMap<String,Integer> scanningResult;
	CountDownLatch latch;
	private String directoryPath;
	int fileCounter = 0;
	boolean isWorking = true;
	String resultFileName;
	
	public DirectoryScanner(String directoryPath, CountDownLatch latch, TreeMap<String,Integer> scanningResult, String resultFileName) {
		this.directoryPath = directoryPath;
		this.latch = latch;
		this.scanningResult = scanningResult;
		this.resultFileName = resultFileName;
	}

	@Override
	public void run() {
		scanDirectory(directoryPath);
		reportResult();
		latch.countDown();											//signals to the latch that this thread have finished work
	}

	private void reportResult() {
		synchronized (scanningResult) {
			scanningResult.put(directoryPath, fileCounter);			//thread-safe writing result to Map
		}
		ResultWriter.writeOneDirectoryScanningResultToFile( resultFileName, directoryPath, fileCounter);		//thread-safe write result to file
	}

	private void scanDirectory(String directoryPath){				//Recursive function, enter into each folder of the specified directory 
		File directory = new File(directoryPath);					//to the full depth and count files
		if(directory.exists()){
			File[] listFiles = directory.listFiles();
			for (File element : listFiles) {
				if(isWorking == false) {							//stop work on demand
					break;
				}
				try {
					Thread.sleep(MainThread.delay);					//delay for debug catching stop work on demand
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(element.isDirectory()){
					scanDirectory(element.getPath());				//recursive call themself
				}else{
					fileCounter++;	
				}
			}
		}
	}

	@Override
	public void stopThreadWork() {									//used for stop this thread from outside
		isWorking = false;		
	}
}
