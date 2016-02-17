/**
 * CopyRight (C) 1989-2989 <Alexander>
 * Copy Right Author     : Alexander_LWZ
 * Package Space         : com.alexsophia.alexsophialib.util.io
 * JDK Version Used      : jdk1.7.0_45
 * Comments              : Use java.util.logging to reports exceptions.
 * Version               : 1.0.0
 * Create Date           : 2014.2.1
 * Modification History  
 * Sr Date       Why & What is modified
 */

package com.alexsophia.alexsophiautils.util.io;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class LoggingException extends Exception {
	private static Logger logger = Logger.getLogger("LoggingException");

	public LoggingException() {
		StringWriter trace = new StringWriter();
		printStackTrace(new PrintWriter(trace));
		logger.severe(trace.toString());
	}
}
