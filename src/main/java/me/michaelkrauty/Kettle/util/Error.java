package me.michaelkrauty.Kettle.util;

import me.michaelkrauty.Kettle.Kettle;

import java.util.logging.Level;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class Error {

	private final Kettle kettle;

	public Error(Kettle instance) {
		kettle = instance;
	}

	public void printError(String message, String detail) {
		kettle.log.log(Level.WARNING, message);
		kettle.log.log(Level.WARNING, detail);
	}
}
