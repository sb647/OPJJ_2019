package hr.fer.zemris.java.hw06.shell;
/**
 * If reading or writing fails, methods 
 * {@link readLine},{@link write} and {@link writeln}
 * will throw this exception.
 * @author Silvana
 *
 */
public class ShellIOException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	/**
	 * This constructs new {@link ShellIOException}.
	 */
	public ShellIOException() {
		super();
	}
	/**
	 * This constructs new {@link ShellIOException}
	 * with given exception message.
	 * @param message given message
	 */
	public ShellIOException(String message) {
		super(message);
	}

}
