package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.PathParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of 
 * ls command. Class is used to print a 
 * directory listing. The output consists of 4 columns.
 * First column indicates if current object is directory (d), 
 * readable (r), writable (w) and executable (x). Second 
 * column contains object size in bytes. Follows file creation 
 * date/time and finally file name.
 * @author Silvana
 *
 */
public class LSCommand implements ShellCommand {
	
	/**
	 * Displays a directory listing.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		PathParser parser = new PathParser();
		Path path;
		try {
			parser.parse(arguments);	
			if(parser.getPath().length() + parser.getSkippedSymbols() != arguments.trim().length()) {
				env.writeln("ls command takes only one argument!");
				return ShellStatus.CONTINUE;
			}
			path = env.getCurrentDirectory().resolve(parser.getPath());
			
		}catch (IllegalArgumentException ex) {
			env.writeln("Invalid path!");
			return ShellStatus.CONTINUE;
		}
		
		if(! path.toFile().isDirectory()) {
			env.writeln(arguments +" is not directory!"); 
			return ShellStatus.CONTINUE;
		}
		DirectoryStream<Path> stream;
		try {
			stream = Files.newDirectoryStream(path);
		
			for (Path p : stream) {
				StringBuilder sb = new StringBuilder();
				sb.append(Files.isDirectory(p)? 'd' : '-');
				sb.append(Files.isReadable(p) ? 'r' : '-');
				sb.append(Files.isWritable(p) ? 'w' : '-');
				sb.append(Files.isExecutable(p) ? 'x' : '-');
				sb.append(" ");
				
				long size = Files.size(p);
				sb.append(" ".repeat(10 - String.valueOf(size).length()));
				sb.append(size);
				sb.append(" ");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(
				path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
				);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				sb.append(formattedDateTime);
				
				sb.append(" ");
				sb.append(p.getFileName());
				
				env.writeln(sb.toString());
			}
		
		}catch(IOException ex) {
			env.writeln("Can't list this directory! ");
			
		}
		return ShellStatus.CONTINUE;	
	}
		
	

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("Command ls takes a single argument.");
		instructions.add("That argument represents some directory.");
		instructions.add("ls writes a directory listing.");
		instructions.add("First column indicates if current object is directory,readable,writeable and executable.");
		instructions.add("Second column contains object size in bytes.");
		instructions.add("Then follows file creation date/time and finally file name.");
		
		return Collections.unmodifiableList(instructions);
		
	}

}
