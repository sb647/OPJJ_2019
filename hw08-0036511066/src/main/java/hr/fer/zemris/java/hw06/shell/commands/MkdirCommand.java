package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.PathParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of mkdir command.
 * The mkdir command takes a single argument: directory name, 
 * and creates the appropriate directory structure.
 * @author Silvana 
 *
 */
public class MkdirCommand implements ShellCommand{
	/**
	 * Extracts path from arguments and creates
	 * the appropriate directory structure.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		PathParser parser = new PathParser();
		Path dir;
		try {
			parser.parse(arguments);
			if(parser.getPath().length() + parser.getSkippedSymbols() != arguments.trim().length()) {
				env.writeln("mkdir command takes only one argument!");
				return ShellStatus.CONTINUE;
			}
			dir = env.getCurrentDirectory().resolve(parser.getPath());
			Files.createDirectory(dir);
			return ShellStatus.CONTINUE;
						
		}catch(IOException | IllegalArgumentException ex) {
			env.writeln("Illegal path!");
			return ShellStatus.CONTINUE;
					
		}
			
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("The mkdir command takes a single argument.");
		instructions.add("That argument represents a directory name.");
		instructions.add("Command creates the appropriate directory structure");
		
		return Collections.unmodifiableList(instructions);
		
	}

}
