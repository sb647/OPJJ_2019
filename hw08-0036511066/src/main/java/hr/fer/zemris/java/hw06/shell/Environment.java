package hr.fer.zemris.java.hw06.shell;
import java.nio.file.Path;
import java.util.SortedMap;

/**
 * This interface allows commands 
 * communicate with user. Environment
 * offers method for reading user's input
 * and methods to display messages from commands
 * to user.
 * @author Silvana
 *
 */
public interface Environment {
	/**
	 * Reads and returns user's input.
	 * @return user's input
	 * @throws ShellIOException if reading fails
	 */
	String readLine() throws ShellIOException;
	/**
	 * It is used to display given text.
	 * @param text given message
	 * @throws ShellIOException if writing fails
	 */
	 void write(String text) throws ShellIOException;
	 /**
	  * Displays given text and starts a new line.
	  * @param text given message
	  * @throws ShellIOException if writing fails
	  */
	 void writeln(String text) throws ShellIOException;
	 /**
	  * Commands getter.
	  * @return map with commands
	  */
	 SortedMap<String, ShellCommand> commands();
	 /**
	  * Multiline symbol getter.
	  * @return multilineSymbol
	  */
	 Character getMultilineSymbol();
	 /**
	  * Multiline symbol setter.
	  * @param symbol new symbol to be set
	  */
	 void setMultilineSymbol(Character symbol);
	 /**
	  * Prompt symbol getter.
	  * @return promptSymbol
	  */
	 Character getPromptSymbol();
	 /**
	  * Prompt symbol setter.
	  * @param symbol new symbol to be set
	  */
	 void setPromptSymbol(Character symbol);
	 /**
	  * Morelines symbol getter.
	  * @return morelinesSymbol
	  */
	 Character getMorelinesSymbol();
	 /**
	  * Morelines symbol setter.
	  * @param symbol new symbol to be set
	  */
	 void setMorelinesSymbol(Character symbol);
	 /**
	  * Returns current working directory.
	  * @return current directory
	  */
	 Path getCurrentDirectory();
	 /**
	  * Sets new working directory.
	  * @param path directory to be set
	  */
	 void setCurrentDirectory(Path path);
	 /**
	  * Returns map with shared data.
	  * @param key keyword mapped to data
	  * @return map with data
	  */
	 Object getSharedData(String key);
	 /**
	  * Adds data to map with shared data.
	  * @param key keyword mapped to data
	  * @param value value to be added
	  */
	 void setSharedData(String key, Object value);

}
