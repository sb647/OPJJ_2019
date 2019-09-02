package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of 
 * pwd command.
 * pwd command takes no argument and it is used
 * to display absolute path to current working
 * directory.
 * 
 * 
 * @author Korisnik
 *
 */
public class PwdCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(! arguments.isEmpty()) {
			env.writeln("pwd command does not take any arguments!");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("pwd command takes no argument.");
		instructions.add("It is used to display absolute path to current working directory.");
		
		return Collections.unmodifiableList(instructions);
		
	}

}
