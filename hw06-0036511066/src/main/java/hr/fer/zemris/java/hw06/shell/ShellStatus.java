package hr.fer.zemris.java.hw06.shell;
/**
 * Shell status enumeration.
 * @author Silvana
 *
 */
public enum ShellStatus {
	/**
	 * It is used to signify shell
	 * to continue with communication with user.
	 */
	CONTINUE, 
	/**
	 * This status is set when shell catches 
	 * ShellIOException because  no communication
	 * is possible with the user, or when user
	 * enters "kraj".
	 */
	TERMINATE

}
