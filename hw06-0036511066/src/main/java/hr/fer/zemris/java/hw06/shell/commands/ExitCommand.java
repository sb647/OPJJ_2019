package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of exit command.
 * This command takes no arguments.
 * It is used to terminate the program.
 * @author Silvana
 *
 */
public class ExitCommand implements ShellCommand{
	/**
	 * Returns TERMINATE shell status.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(! arguments.isEmpty()) {
			env.writeln("Command exit takes no arguments!");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;	
		
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("Command exit takes no arguments.");
		instructions.add("Command is used to terminate the program.");
		
		return Collections.unmodifiableList(instructions);
	}

}
