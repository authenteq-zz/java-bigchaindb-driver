package com.authenteq.util;
import java.util.Scanner;


public class ScannerUtil {

	/**
	 * exit when enter string "exit"
	 */
	public static void monitorExit() {
		Scanner scanner = new Scanner(System.in);
		try{
	        while (true) { 
		        String line = scanner.nextLine();
		        if("exit".equals(line)){
		        	break;
		        }
	        }
		} catch (Exception ex) {
			
		} finally {
			scanner.close();
		}
	}
}
