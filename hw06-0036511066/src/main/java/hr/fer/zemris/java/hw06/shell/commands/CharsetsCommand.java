package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of
 * command charsets. Command charsets takes no arguments 
 * and lists names of supported charsets for Java platform.
 * @author Silvana
 *
 */
public class CharsetsCommand implements ShellCommand{
	/**
	 * It is used to display supported charsets for
	 * Java platform.
	 * @param env instance of environment
	 * @param arguments is expected to be empty
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(! arguments.isEmpty()) {
			env.writeln("Command charsets takes no arguments!");
			return ShellStatus.CONTINUE;
		}
		for (String str : Charset.availableCharsets().keySet()) {
		      env.writeln(str);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("Command charsets takes no arguments.");
		instructions.add("It lists names of supported charsets for your Java platform");
		instructions.add("A single charset name is written per line");
		
		return Collections.unmodifiableList(instructions);
	}

}
