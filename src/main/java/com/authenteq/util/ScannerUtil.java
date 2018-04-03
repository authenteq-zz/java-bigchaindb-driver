package com.authenteq.util;
import java.util.Scanner;



/**
 * The Class ScannerUtil.
 */
public class ScannerUtil {

	/**
	 * exit when enter string "exit".
	 */
	public static void monitorExit() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				String line = scanner.nextLine();
				if ("exit".equals(line)) {
					break;
				}
			}
		}
	}
}
