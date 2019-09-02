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
 * dropd command.
 * <p>
 * Dropd command takes no argument. Command removes
 * the object of a type Path at the top of shared 
 * stack and current state remains unchanged.
 * If command is invoked upon empty stack, it will 
 * display appropriate message.
 * 
 * @author Silvana
 *
 */
public class DropdCommand implements ShellCommand {
	
	private static final String STACK_KEY = "cdstak";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(! arguments.isEmpty()) {
			env.writeln("popd command takes no argument!");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>)env.getSharedData(STACK_KEY);
		if(stack == null) {
			env.writeln("There is no any paths stored on stack!");
			return ShellStatus.CONTINUE;
		}
		try {
			stack.pop();
			return ShellStatus.CONTINUE;
		}catch(EmptyStackException ex) {
			env.writeln("Stack is empty!");
			return ShellStatus.CONTINUE;
		}
	}

	@Override
	public String getCommandName() {
		
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("dropd command takes no argument.");
		description.add("Command removes the object of a type Path at the top of shared stack.");
		description.add("If command is invoked upon empty stack, it displays appropriate message.");
		
		return description;
	}


}
