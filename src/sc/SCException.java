package sc;

/**
 * Use this {@link Exception} class to indicate that something went wrong during
 * the process of calculating the final payments.
 * 
 * @author Eugene Yip
 * @author Jan Boockmann
 *
 */
public class SCException extends Exception {

	private static final long serialVersionUID = 8827577212929142636L;

	/**
	 * A simple {@link SCException} constructor.
	 * 
	 * @param message
	 *            A message describing what went wrong.
	 */
	public SCException(String message) {
		super(message);
	}

}
