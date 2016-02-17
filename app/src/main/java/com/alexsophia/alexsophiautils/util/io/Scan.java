/**
 * CopyRight (C) 1989-2989 <Alexander>
 * Copy Right Author     : Alexander_LWZ
 * Package Space         : java.util.Scanner
 * JDK Version Used      : jdk1.7.0_45
 * Comments              : The use of get legal numbers
 * Version               : 1.0.0
 * Create Date           : 2014.1.5
 * Modification History  
 * Sr Date       Why & What is modified
 * 1  2014.1.9   Separate integer, float and double
 * 2  2014.2.1   Set this to GitHub
 */

package com.alexsophia.alexsophiautils.util.io;

import java.util.Scanner;

public class Scan {
	/**
	 * Scan a String object from console.
	 * 
	 * @param prompt
	 *            The prompt which is used to print for user.
	 * @return The scanned String data.
	 */
	public static String scan(String prompt) {
		System.out.print("Please input " + prompt + ": ");
		Scanner scanner = new Scanner(System.in);
		String st = scanner.nextLine();
		return st;
	}

	/**
	 * Scan a legal Integer from console.
	 * 
	 * @param prompt
	 *            The prompt which is used to print for user.
	 * @return The scanned Integer number.
	 */
	public static Integer scanInt(String prompt) {
		System.out.print("Please input " + prompt + ": ");
		return getInt();
	}

	/**
	 * Scan a legal Integer between the range MIN and MAX from console.
	 * 
	 * @param prompt
	 *            The prompt which is used to print for user.
	 * @param Min
	 *            The Minimum of the required number.
	 * @param Max
	 *            The Maximum of the required number.
	 * @return The scanned legal Integer number.
	 */
	public static Integer scanInt(String prompt, Integer Min, Integer Max) {
		System.out.print("Please input " + prompt + " between " + Min + " and "
				+ Max + ": ");
		Boolean flag;
		int numInt;
		do {
			numInt = getInt();
			if (available(Min, Max, numInt)) {
				flag = true;
			} else {
				flag = false;
				System.out.println("Range illegal! ");
				System.out.print("Please input " + prompt + " between " + Min
						+ " and " + Max + ": ");
			}
		} while (!flag);
		return numInt;
	}

	/**
	 * Scan a legal Float from console.
	 * 
	 * @param prompt
	 *            The prompt which is used to print for user.
	 * @return The scanned Float number.
	 */
	public static Float scanFloat(String prompt) {
		System.out.print("Please input " + prompt + ": ");
		return getFloat();
	}

	/**
	 * Scan a legal Float between the range MIN and MAX from console.
	 * 
	 * @param prompt
	 *            The prompt which is used to print for user.
	 * @param Min
	 *            The Minimum of the required number.
	 * @param Max
	 *            The Maximum of the required number.
	 * @return The scanned legal Float number.
	 */
	public static Float scanFloat(String prompt, Float Min, Float Max) {
		System.out.print("Please input " + prompt + " between " + Min + " and "
				+ Max + ": ");
		Boolean flag;
		Float numFloat;
		do {
			numFloat = getFloat();
			if (available(Min, Max, numFloat)) {
				flag = true;
			} else {
				flag = false;
				System.out.println("Range illegal! ");
				System.out.print("Please input " + prompt + " between " + Min
						+ " and " + Max + ": ");
			}
		} while (!flag);
		return numFloat;
	}

	/**
	 * Description: Scan a legal Double from console.
	 * 
	 * @param prompt
	 *            The prompt which is used to print for user.
	 * @return The scanned Double number.
	 */
	public static Double scanDouble(String prompt) {
		System.out.print("Please input " + prompt + ": ");
		return getDouble();
	}

	/**
	 * Description: Scan a legal Double between the range MIN and MAX from
	 * console.
	 * 
	 * @param prompt
	 *            The prompt which is used to print for user.
	 * @param Min
	 *            The Minimum of the required number.
	 * @param Max
	 *            The Maximum of the required number.
	 * @return The scanned legal Double number.
	 */
	public static Double scanDouble(String prompt, Double Min, Double Max) {
		System.out.print("Please input " + prompt + " between " + Min + " and "
				+ Max + ": ");
		Boolean flag;
		Double numDouble;
		do {
			numDouble = getDouble();
			if (available(Min, Max, numDouble)) {
				flag = true;
			} else {
				flag = false;
				System.out.println("Range illegal! ");
				System.out.print("Please input " + prompt + " between " + Min
						+ " and " + Max + ": ");
			}
		} while (!flag);
		return numDouble;
	}

	/**
	 * Description: get a Integer
	 * 
	 * @return Integer
	 */
	private static Integer getInt() {
		Boolean flag;
		int numInt = 0;
		do {
			Scanner scanner = new Scanner(System.in);
			try {
				numInt = scanner.nextInt();
				flag = true;
			} catch (Exception e) {
				flag = false;
				System.out.println("Input type mismatch!");
				System.out.print("Please input in Integer: ");
			}
		} while (!flag);
		return numInt;
	}

	/**
	 * Description: get a Float
	 * 
	 * @return Float
	 */
	private static Float getFloat() {
		Boolean flag;
		float numFloat = 0;
		do {
			Scanner scanner = new Scanner(System.in);
			try {
				numFloat = scanner.nextFloat();
				flag = true;
			} catch (Exception e) {
				flag = false;
				System.out.println("Input type mismach!");
				System.out.print("Please input in Float: ");
			}
		} while (!flag);
		return numFloat;
	}

	/**
	 * Description: get a Double
	 * 
	 * @return Double
	 */
	private static Double getDouble() {
		Boolean flag;
		double numDouble = 0.0;
		do {
			Scanner scanner = new Scanner(System.in);
			try {
				numDouble = scanner.nextDouble();
				flag = true;
			} catch (Exception e) {
				flag = false;
				System.out.println("Input type mismach!");
				System.out.print("Please input in Double: ");
			}
		} while (!flag);
		return numDouble;
	}

	/**
	 * Description: Examine the given input number within the legal scope or
	 * not.
	 * 
	 * @param Min
	 *            The Minimum of the required number.
	 * @param Max
	 *            The Maximum of the required number.
	 * @param input
	 *            The required type of number
	 * @return True if the number is legal
	 * @return False if the number is illegal
	 */
	private static Boolean available(Integer Min, Integer Max, Integer input) {
		if (Min <= input && Max >= input)
			return true;
		else
			return false;
	}

	/**
	 * Description: Examine the given input number within the legal scope or
	 * not.
	 * 
	 * @param Min
	 *            The Minimum of the required number.
	 * @param Max
	 *            The Maximum of the required number.
	 * @param input
	 *            The required type of number
	 * @return True if the number is legal
	 * @return False if the number is illegal
	 */
	private static Boolean available(Float Min, Float Max, Float input) {
		if (Min <= input && Max >= input)
			return true;
		else
			return false;
	}

	/**
	 * Description: Examine the given input number within the legal scope or not
	 * 
	 * @param Min
	 *            The Minimum of the required number.
	 * @param Max
	 *            The Maximum of the required number.
	 * @param input
	 *            The required type of number
	 * @return True if the number is legal
	 * @return False if the number is illegal
	 */
	private static Boolean available(Double Min, Double Max, Double input) {
		if (Min <= input && Max >= input)
			return true;
		else
			return false;
	}
}
