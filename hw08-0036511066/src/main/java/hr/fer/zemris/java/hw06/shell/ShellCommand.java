package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * A class that implements this interface is used
 * to perform some operations and actions in a particular environment.
 * @author Silvana
 *
 */
public interface ShellCommand {
	
	/**
	 * Implements and executes command in some environment.
	 * Each command takes certain number of arguments.
	 * @param env given environment
	 * @param arguments user's input
	 * @return ShellStatus,CONTINUE or TERMINATE
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * It is used to get command name.
	 * @return command name
	 */
	String getCommandName();
	/**
	 * Returns a description and usage instructions
	 * of command.
	 * @return list of strings
	 */
	List<String> getCommandDescription();

}
