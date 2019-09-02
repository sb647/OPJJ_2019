package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Each object of a type {@code FilterResult} represents
 * one file and result of match operations on name of 
 * file by interpreting a given pattern.
 * <p>
 * This class offers public methods,such as {@link toString},
 * {@link numberOfGroups},{@link group} and one getter for
 * stored file. Each method behaves in accordance with it's
 * name. If given group itself did not match anything, method
 * {@link numberOfGroups} returns 0.
 *
 * @author Silvana
 *
 */
public class FilterResult {
	/**
	 * Stored file.
	 */
	private Path file;
	/**
	 * List of input subsequences captured by the given 
	 * named-capturing group during the match operation.
	 */
	private List<String> groups = new ArrayList<>();
	
	/**
	 * Sole constructor.
	 * 
	 * @param file given file
	 * @param pattern The pattern for which this matcher 
	 * was created
	 */
	public FilterResult(Path file, Pattern pattern) {
		this.file = file;
		Matcher m = pattern.matcher(file.getFileName().toString());
		if(m.matches()) {
			for(int i = 0; i<= m.groupCount(); i++) {
				groups.add(m.group(i));
			}
		}
	}
	/**
	 * Returns name of stored file.
	 */
	public String toString() {
		return file.getFileName().toString();
	}
	/**
	 * Returns the number of capturing groups in matcher's 
	 * pattern.
	 * @return number of capturing groups
	 */
	public int numberOfGroups() {
		return groups.size();  
	}
	/**
	 * Returns the input subsequence captured by the given 
	 * group during the match operation.
	 * @param index index of group, 0 is init.group
	 * @return indexed input subsequence
	 */
	public String group(int index) {
		return groups.get(index);
	}
	/**
	 * Getter for stored file.
	 * @return stored file
	 */
	public Path getFile() {
		return file;
	}

}
