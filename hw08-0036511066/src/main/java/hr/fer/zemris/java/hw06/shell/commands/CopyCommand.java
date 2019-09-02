package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.PathParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents an implementation of copy command.
 * Copy command works only with files.
 * @author Silvana
 *
 */
public class CopyCommand implements ShellCommand{
	/**
	 * Size of buffer array.
	 */
	private static final int BUFFER_SIZE = 4096;
	/**
	 * This method expects two extracted arguments from {@code arguments} : 
	 * source file name and destination file name.
	 * If destination file exists, user needs to allow to overwrite it.
	 * @throws IOException if copying fails
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		PathParser srcParser = new PathParser();
		PathParser destParser = new PathParser();
		Path src;
		Path dest;
		String srcPath;
		String destPath;
		
		try {
			srcParser.parse(arguments);
			destParser.parse(arguments.substring(srcParser.getPath().length() + srcParser.getSkippedSymbols()).trim());
			srcPath = srcParser.getPath();
			destPath = destParser.getPath();
			
		}catch (IllegalArgumentException ex) {
			env.writeln("Invalid path !");
			return ShellStatus.CONTINUE;
			
		}
			
		if(srcPath.isEmpty() || destPath.isEmpty()) {
			env.writeln("Copy command requires two arguments!");
			return ShellStatus.CONTINUE;
		}
		
		src = env.getCurrentDirectory().resolve(srcPath);
		dest = env.getCurrentDirectory().resolve(destPath);
		
		if(src.equals(dest)) {
			env.writeln("You cannot copy a file to itself!");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(src)) {
			env.writeln("Copy command works only with files!");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(dest)) {
			dest = new File(dest.toFile(),src.getFileName().toString()).toPath();
		}
		
		if(Files.exists(dest)) {
			env.writeln("File already exists. Do you want to overwrite it? Enter \"yes\" for yes,"
					+ "and anything else for no: ");
			String answer = env.readLine();
			if(!answer.trim().equals("yes")) {
				env.writeln("Copying canceled! ");
				return ShellStatus.CONTINUE;
			}
		}
	
		try ( InputStream is = Files.newInputStream(src , StandardOpenOption.READ);
			 OutputStream os = Files.newOutputStream(dest)){
				
			 byte[] buffer = new byte[BUFFER_SIZE];
			 int readBytes;
			 while((readBytes = is.read(buffer)) != -1) {
					os.write(buffer,0,readBytes);
			 }
			 
		 }catch (IOException ex) {
			
			 env.writeln("Cannot perform copying!");
			 return ShellStatus.CONTINUE;
		}
			
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("Command copy takes two arguments.");
		instructions.add("The first argument is source file to be copied. ");
		instructions.add("The second argument is destination file.");
		instructions.add("Copy works only with files.");
		instructions.add("If destination file exists,user needs to allow to overwrite it");
		
		return Collections.unmodifiableList(instructions);
		
	}

}
