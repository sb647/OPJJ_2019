package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of
 * popd command.
 * <p>
 * Popd command takes no argument. Command removes
 * the object of a type Path at the top of shared 
 * stack and sets that Path as a new working 
 * directory. If command is invoked upon empty 
 * stack, it will display appropriate message.
 * 
 * @author Silvana
 *
 */
public class PopdCommand implements ShellCommand{
	
	private static final String STACK_KEY = "cdstak";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(! arguments.isEmpty()) {
			env.writeln("popd command takes no argument!");
			return ShellStatus.CONTINUE;
		}
		if(env.getSharedData(STACK_KEY)== null) {
			env.writeln("There is no any paths stored on stack!");
			return ShellStatus.CONTINUE;
		}
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>)env.getSharedData(STACK_KEY);
		try {
			Path newPath = stack.pop();
			env.setCurrentDirectory(newPath);   // path is valid
			return ShellStatus.CONTINUE;
		}catch(EmptyStackException ex) {
			env.writeln("Stack is empty!");
			return ShellStatus.CONTINUE;
		}
	}

	@Override
	public String getCommandName() {
		
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("popd command takes no argument.");
		description.add("Command removes the object of a type Path at the top of shared stack.");
		description.add("Removed path is set as new working directory.");
		
		return description;
	}

}
