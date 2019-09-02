package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.PathParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of
 * cd command. 
 * <p>
 * cd command takes one argument. Given argument
 * is path to directory to be set as new working
 * directory. For invalid inputs, command displays
 * appropriate message.
 * 
 * @author Silvana
 *
 */
public class CdCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		PathParser parser = new PathParser();
		try {
			parser.parse(arguments);
		}catch (IllegalArgumentException ex) {
			env.writeln("Invalid path!");
			return ShellStatus.CONTINUE;
		}
		if(parser.getPath().length() + parser.getSkippedSymbols() != arguments.trim().length()) {
			env.writeln("cd command takes only one argument!");
			return ShellStatus.CONTINUE;
		}
		
		Path path = env.getCurrentDirectory().resolve(parser.getPath()).normalize();
		
		try {
			env.setCurrentDirectory(path);
		}catch(ShellIOException ex) {
			env.writeln(ex.getMessage());
		}
		
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Cd command takes one argument.");
		description.add("Given argument is directory to be set as new working directory.");
		description.add("If given argument is invalid, command displays appropriate message.");
		
		return description;
	}
	

}
