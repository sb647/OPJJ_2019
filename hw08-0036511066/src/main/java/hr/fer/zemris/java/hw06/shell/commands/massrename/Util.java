package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.PathParser;
/**
 * This class creates and returns list of extracted
 * arguments from given string.
 * If given string is valid, number of extracted elements
 * will always be 4 or 5.
 * 
 * @author  Silvana
 *
 */
public class Util {
	/**
	 * Keyword for filter subcommand.
	 */
	private static final String FILTER= "filter";
	/**
	 * Keyword for show subcommand.
	 */
	private static final String SHOW = "show";
	/**
	 * Keyword for groups subcommand.
	 */
	private static final String GROUPS = "groups";
	/**
	 * Keyword for execute subcommand.
	 */
	private static final String EXECUTE = "execute";
	
	/**
	 * Creates and returns list of extracted arguments.
	 * 
	 * @param arg given string 
	 * @return list of extracted arguments
	 * @throws IllegalArgumentException if given string is invalid
	 */
	public static List<Object> extractArguments(String arg) {
		
		List<Object> result = new ArrayList<>();
		int index;
		
		//parse directories
		try {
			
			PathParser parser = new PathParser();
			parser.parse(arg);
			result.add(Paths.get(parser.getPath()));
			index = parser.getSkippedSymbols() + parser.getPath().length() + 1; //plus space
			
			parser.setSkippedSymbolsToZero();
			parser.parse(arg.substring(index));
			result.add(Paths.get(parser.getPath()));
			index += parser.getPath().length() + parser.getSkippedSymbols() + 1;
			
		} catch(IllegalArgumentException ex) {
			
			throw new IllegalArgumentException("Invalid directories!");
		}
		
		if(! checkForIndex(index, arg)) {
			throw new IllegalArgumentException("Invalid number of arguments!");
		}
		
		String[] parts = arg.substring(index).split(" ",2); //first space
		//parse CMD
		switch(parts[0]) {
		
			case FILTER:
				index += FILTER.length();
				result.add(FILTER);
				break;
				
			case GROUPS:
				index += GROUPS.length();
				result.add(GROUPS);
				break;
				
			case SHOW:
				index += SHOW.length();
				result.add(SHOW);
				break;
				
			case EXECUTE:
				index += EXECUTE.length();
				result.add(EXECUTE);
				break;
				
			default:
				throw new IllegalArgumentException("CMD is not recognized!");
				
		}
		index++;
		
		if(! checkForIndex(index, arg)) {
			throw new IllegalArgumentException("Invalid number of arguments!");
		}
		
		PathParser parser = new PathParser();
		
		try {
			parser.parse(arg.substring(index));
			result.add(parser.getPath());
			
		}catch(IllegalArgumentException ex) {
	
			throw new IllegalArgumentException("Invalid mask expression!");
		}
		
		
		index += parser.getPath().length() + parser.getSkippedSymbols();
		
		
		if(! checkForIndex(index, arg)) { // there is no "others"
			return result;
		}
		
		try {
			parser.setSkippedSymbolsToZero();
			parser.parse(arg.substring(++index));
			result.add(parser.getPath());
		}catch(IllegalArgumentException ex) {
			
			throw new IllegalArgumentException("Invalid expression!");
		}
		
		index += parser.getSkippedSymbols() + parser.getPath().length();
		
		if(index != arg.length()) {
			throw new IllegalArgumentException("Invalid number of arguments!");

		}
		return result;
			
	}
	/**
	 * Returns true if current position in string is less than
	 * the length of given string. Otherwise,returns false.
	 * 
	 * @param index current position
	 * @param str given string
	 * @return <code>true</code> or <code>false</code>
	 */
	private static boolean checkForIndex(int index, String str) {
		if(index >= str.length()) {
			return false;
		}
		return true;
	}

}
