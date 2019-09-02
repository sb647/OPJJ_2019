package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represent an implementation of a
 * listd command. 
 * <p>
 * Listd command takes no argument. It lists
 * all previous working directories stored on
 * shared stack.
 * If user invokes this command upon empty stack,
 * it will display appropriate message.
 * 
 * 
 * @author Silvana
 *
 */
public class ListdCommand implements ShellCommand{

	private static final String STACK_KEY = "cdstak";
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(! arguments.isEmpty()) {
			env.writeln("listd command takes no argument!");
			return ShellStatus.CONTINUE;		
		}
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK_KEY);
		if(stack == null || stack.isEmpty()) {
			env.writeln("Nema pohranjenih direktorija.");
			return ShellStatus.CONTINUE;
		}
		List<Path> list = stack.subList(0, stack.size());
		for(Path l : list) {
			env.writeln(l.toString());
			
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Listd command takes no argument.");
		description.add("It lists previous working directories. Directories are stored on the shared stack.");
		description.add("If command is invoked upon empty stack, command displays appropriate message.");
		
		return description;
	}

}
