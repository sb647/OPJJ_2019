package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.PathParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of tree command.
 * The tree command expects a single argument: directory name 
 * and prints a tree (each directory level shifts
 * output two charatcers to the right).
 * @author Silvana
 *
 */
public class TreeCommand implements ShellCommand{
	/**
	 * Displays a directory tree.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		PathParser parser = new PathParser();
		if(arguments.isEmpty()) {
			env.writeln("Directory expected!");
			return ShellStatus.CONTINUE;
		}
		Path path;
		try {
			parser.parse(arguments);
			path = env.getCurrentDirectory().resolve(parser.getPath());
			if(parser.getPath().length() + parser.getSkippedSymbols() != arguments.trim().length()) {
				env.writeln("Tree command takes only one argument!");
				return ShellStatus.CONTINUE;
			}
			ispisiPolje(path.toFile(), 0,env);
			return ShellStatus.CONTINUE;
			
		}
		catch(IllegalArgumentException ex) {
			env.writeln("Invalid path!");
			return ShellStatus.CONTINUE;
		}
		
	}
	
	private void ispisiPolje(File file,int razina,Environment env) {
		env.writeln(" ".repeat(razina)+ file.getName());
		File[] files = file.listFiles();
		
		if(files == null) {
			return;
		}
		for(File f : files) {
			if(f.isDirectory()) {
				ispisiPolje(f,razina+2,env);	
			}
			else {
				env.writeln(" ".repeat(razina)+ f.getName());
			}
		}
		
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("Command tree takes a single argument.");
		instructions.add("That argument represents some directory.");
		instructions.add("Command prints a tree (each directory level shifts output two charatcers to the right).");
		
		return Collections.unmodifiableList(instructions);
	}

}
