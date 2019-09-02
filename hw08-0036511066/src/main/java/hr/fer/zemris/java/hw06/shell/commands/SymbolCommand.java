package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of symbol command.
 * This class is used to print or change enviroment 
 * special symbols.
 * @author Silvana
 *
 */
public class SymbolCommand implements ShellCommand {
	/**
	 * Prints or changes prompt/morelines/multiline symbol.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts = arguments.split("\\s+");
		
		if(parts.length == 1) {
			switch(parts[0]) {
				case "PROMPT":
					env.writeln("Symbol for PROMPT is " + env.getPromptSymbol());
					break;
				case "MORELINES":
					env.writeln("Symbol for MORELINES is " + env.getMorelinesSymbol());
					break;
				case "MULTILINE":
					env.writeln("Symbol for MULTILINE is "+ env.getMultilineSymbol());
					break;
				default :
					env.writeln("Invalid symbol name!");
			 }
			return ShellStatus.CONTINUE;
			
		}else if(parts.length == 2 && parts[1].length() == 1) {
			
			switch(parts[0]) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT changed from" + env.getPromptSymbol() + " to " +parts[1]);
				env.setPromptSymbol(parts[1].charAt(0));
				break;
			case "MORELINES":
				env.writeln("Symbol for MORELINES changed from " + env.getMorelinesSymbol()+ " to " + parts[1]);
				env.setMorelinesSymbol(parts[1].charAt(0));
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE changed from "+ env.getMultilineSymbol() + " to " +parts[1]);
				env.setMultilineSymbol(parts[1].charAt(0));
				break;
			default :
				env.writeln("Invalid symbol name!");
		 }
		return ShellStatus.CONTINUE;
		}
		
		env.writeln("Invalid arguments for symbol command!");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("Command symbol takes one or two arguments.");
		instructions.add("The first argument is name of symbol. ");
		instructions.add("The second argument is new symbol to be set.");
		instructions.add("If there is a single argument,command prints appropriate symbol.");
		instructions.add("And if two,command sets new symbol to first argument.");
		
		return Collections.unmodifiableList(instructions);
		
		
	}

}
