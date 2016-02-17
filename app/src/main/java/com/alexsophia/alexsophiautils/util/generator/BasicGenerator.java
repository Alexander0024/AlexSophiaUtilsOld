/**
 * CopyRight (C) 1989-2989 <Alexander>
 * Copy Right Author     : Alexander_LWZ
 * JDK Version Used      : jdk1.7.0_45
 * Comments              : Automatically create a Generator by given a class
 * Version               : 1.0.0
 * Create Date           : 2014.2.5
 * Modification History  
 * Sr Date       Why & What is modified
 */

package com.alexsophia.alexsophiautils.util.generator;


public class BasicGenerator<T> implements Generator<T> {
	private Class<T> type;

	public BasicGenerator(Class<T> type) {
		this.type = type;
	}

	public T next() {
		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Produce a Default generator given a type token
	 * 
	 * @param type
	 *            type token
	 * @return generator
	 */
	public static <T> Generator<T> create(Class<T> type) {
		return new BasicGenerator<T>(type);
	}
}
