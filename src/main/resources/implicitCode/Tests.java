
/**
 * Lists all builtin tests and their arguments.
 */
interface Tests {

    /**
     * The {@code empty} test checks if a variable is empty. A variable is empty if it is null, an
     * empty string, an empty collection, or an empty map.
     *
     * <code><pre>
     * {% if user.email is empty %}
     *   ...
     * {% endif %}
     * </pre></code>
     */
    boolean empty();

    /**
     * The {@code even} test checks if an integer is even.
     *
     * <code><pre>
     * {% if 2 is even %}
     *   ...
     * {% endif %}
     * </pre></code>
     */
    boolean even();

    /**
     * The {@code map} test checks if a variable is an instance of a map.
     *
     * <code><pre>
     * {% if {"apple":"red", "banana":"yellow"} is map %}
     *   ...
     * {% endif %}
     * </pre></code>
     */
    boolean map();

    /**
     * The {@code null} test checks if a variable is null.
     *
     * <code><pre>
     * {% if user.email is null %}
     *   ...
     * {% endif %}
     * </pre></code>
     */
    boolean null();

    /**
     * The {@code odd} test checks if an integer is odd.
     *
     * <code><pre>
     * {% if 3 is odd %}
     *   ...
     * {% endif %}
     * </pre></code>
     */
    boolean odd();

    /**
     * The {@code iterable} test checks if a variable implements {@link java.lang.Iterable}.
     *
     * <code><pre>
     * {% if users is iterable %}
     *   {% for user in users %}
     *     ...
     *   {% endfor %}
     * {% endif %}
     * </pre></code>
     */
    boolean iterable();

}
