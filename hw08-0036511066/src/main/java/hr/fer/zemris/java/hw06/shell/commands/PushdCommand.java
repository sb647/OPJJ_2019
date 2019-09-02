package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.PathParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * Pushd command takes one single argument.
 * Given argument is path to directory to be
 * set as new working directory.
 * If given argument is invalid, current
 * working directory won't change. Command will
 * display appropriate message.
 * <p>
 * Command pushes previous working directory
 * on stack, so that user can use it again later.
 * 
 * @author Silvana
 *
 */
public class PushdCommand implements ShellCommand{
	
	private static final String STACK_KEY = "cdstak";

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
			env.writeln("pushd command takes only one argument!");
			return ShellStatus.CONTINUE;
		}
		
		Path path = env.getCurrentDirectory().resolve(parser.getPath()).normalize();
	
		if(env.getSharedData(STACK_KEY)== null) {
			env.setSharedData(STACK_KEY, new Stack<Path>());
		}
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK_KEY);
		Path previous = env.getCurrentDirectory();
		try {
			env.setCurrentDirectory(path);
			stack.add(previous);
			return ShellStatus.CONTINUE;
			
		}catch(ShellIOException ex) {
			env.writeln("Invalid directory!");
			return ShellStatus.CONTINUE;
		}
		
	}

	@Override
	public String getCommandName() {
		
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("pushd command takes one argument.");
		description.add("Given argument is directory to be set as new working directory.");
		description.add("Previous working directory is kept on stack.");
		description.add("If given argument is invalid,current working directory is not going to be changed.");
		
		return description;
		
	}

}
