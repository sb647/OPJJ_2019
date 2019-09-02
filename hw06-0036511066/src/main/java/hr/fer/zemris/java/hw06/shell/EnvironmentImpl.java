package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.Help;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LSCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;
/**
 * Implementation of {@link Environment}.
 * This class represents mediator between
 * user and shell commands.
 * @author Silvana
 *
 */
public class EnvironmentImpl implements Environment{
	/**
	 * It is used to get user's input.
	 */
	private Scanner sc;
	/**
	 * Map used to store available shell commands.
	 */
	private SortedMap<String, ShellCommand> commands;
	/**
	 * Symbol used when text spans multiple lines.
	 */
	private Character multilineSymbol;
	/**
	 *  Symbol used to represent the system's readiness to perform the next command.
	 */
	private Character promptSymbol;
	/**
	 * Symbol used to represent the system's readiness to read the next line of 
	 * multiple lines command.
	 */
	private Character morelinesSymbol;
	/**
	 * This constructs new {@link EnvironmentImpl}.
	 * It sets default symbols: multiline symbol is '|',
	 * prompt symbol is '>' and morelines symbol
	 * is '\\'.
	 */
	public EnvironmentImpl() {
		sc = new Scanner(System.in);
		createMapOfCommands();
		multilineSymbol = '|';
		promptSymbol = '>' ;
		morelinesSymbol = '\\';
		helloMessage();
	}
	/**
	 * Displays hello message.
	 */
	private void helloMessage() {
		System.out.println("Welcome to MyShell v 1.0");
		
	}
	
	@Override
	public String readLine() {
		StringBuilder sb = new StringBuilder();
		String line;
		while(sc.hasNextLine()) {
			line = sc.nextLine().trim();
			sb.append(line);
			if(! line.endsWith(morelinesSymbol.toString())) {
				return sb.toString();
			}
			System.out.print(multilineSymbol + " ");
			sb.deleteCharAt(sb.length()-1);
			sb.append(" ");
			
		}
		throw new ShellIOException("No more lines!");
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.printf(text);
		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
		
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
		
	}
	/**
	 * Creates map of available commands.
	 */
	private void createMapOfCommands() {
		commands = new TreeMap<>();
		commands.put("charsets" , new CharsetsCommand());
		commands.put("cat", new CatCommand());
		commands.put("copy", new CopyCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("ls", new LSCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("tree", new TreeCommand());	
		commands.put("symbol", new SymbolCommand());
		commands.put("help", new Help());
		commands.put("exit", new ExitCommand());
	}

}
