package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of help command.
 * If started with no arguments, it lists names of all supported commands.
 * If started with single argument, it must print name and the description 
 * of selected command.
 * @author Silvana
 *
 */
public class Help implements ShellCommand {
	/**
	 * Displays list of commands or command description.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isEmpty()) {
			for(String key : env.commands().keySet()) {
				env.writeln(key);
			}
			return ShellStatus.CONTINUE;
		}
		
		ShellCommand command = env.commands().get(arguments);
		if(command== null) {
			env.writeln("Command is not recognized!");
			return ShellStatus.CONTINUE;
		}
		List<String> instructions = command.getCommandDescription();
		env.writeln(command.getCommandName() + " command");
		for(String i : instructions) {
			env.writeln(i);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("Command help takes 0 or 1 argument.");
		instructions.add("If started with no arguments, it lists names of all supported commands.");
		instructions.add("If started with single argument, it must print name and the description of selected command.");
		
		return Collections.unmodifiableList(instructions);
		
	}

}
