import java.util.List;

/**
 * Implicit variables and functions defined by pebble-spring.
 */
abstract class PebbleSpring {

	/**
	 * A map of Spring beans by name.
	 */
	java.util.Map<String, Object> beans;

	/**
	 * The HTTP request.
	 */
	javax.servlet.http.HttpServletRequest request;

	/**
	 * The HTTP response.
	 */
	javax.servlet.http.HttpServletResponse response;

	/**
	 * The HTTP session.
	 */
	javax.servlet.http.HttpSession session;

	/**
	 * Function to automatically add the context path to a given url.
	 *
	 * @param url the URL for which to add the context path
	 */
	void href(String url);

	/**
	 * Function available to templates in Spring MVC applications in order to
	 * resolve message in the application context
	 */
	void message(String key, Object... parameters);

	/**
	 * Checks if the given {@code formName} has errors.
	 */
	boolean hasErrors(String formName);

	/**
	 * Checks if the given {@code formName} has global errors.
	 */
	boolean hasGlobalErrors(String formName);

	/**
	 * Checks if the given {@code fieldName} contained in {@code formName} has errors.
	 */
	boolean hasFieldErrors(String formName, String fieldName);

	/**
	 * Returns all the errors in the form {@code formName}.
	 */
	List<String> getAllErrors(String formName);

	/**
	 * Returns all the global errors in the form {@code formName}.
	 */
	List<String> getGlobalErrors(String formName);

	/**
	 * Returns all the errors for the field {@code fieldName} in the form {@code formName}.
	 */
	List<String> getFieldErrors(String formName, String fieldName);

}
