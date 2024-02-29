/**
 * Lists all builtin filters and their arguments.
 */
interface Functions {

    /**
     * <p>The block function is used to render the contents of a block more than once.
     * It is not to be confused with the block tag which is used to declare blocks.</p>
     *
     * <p>The following example will render the contents of the "post" block twice; once where it
     * was declared and again using the block function:</p>
     *
     * <code><pre>
     * {% block "post" %} content {% endblock %}
     *
     * {{ block("post") }}
     * </pre></code>
     *
     * The above example will output the following:
     *
     * <code><pre>
     * content
     *
     * content
     * </pre></code>
     * @param blockName
     */
    block(String blockName);

    /**
     * <p>The i18n function is used to retrieve messages from a locale-specific ResourceBundle.
     * Every PebbleTemplate is assigned a default locale from the PebbleEngine. At the point of evaluation,
     * this locale can be changed with an argument to the evaluate(...) method of the individual template.</p>
     *
     * <p>The i18n function wraps around ResourceBundle.getBundle(name, locale).getObject(key).
     * The first argument to the i18n function is the name of the bundle and the second argument is the key
     * within the bundle.</p>
     *
     * <code><pre>
     * {{ i18n("messages","greeting") }}
     * </pre></code>
     *
     * <p>The above example assumes you have messages.properties on your classpath and that that file contains
     * a key by the name of greeting. If the locale of that template was es_US for example, it would look
     * for a message_es_US.properties file instead.</p>
     *
     * <p>Going a little further, you can use variables within your message and pass a list of params
     * to this function which will replace your variables using MessageFormat:</p>
     *
     * <code><pre>
     * {# greeting.someone=Hello, {0} #}
     * {{ i18n("messages","greeting", "Jacob") }}
     *
     * {# output: Hello, Jacob #}
     * </pre></code>
     *
     * @param bundle
     * @param key
     * @param params
     */
    i18n(String bundle, String key, Object... params);

    /**
     * The max function will return the largest of it's numerical arguments.
     *
     * <code><pre>
     * {{ max(user.age, 80) }}
     * </pre></code>
     *
     * @param left
     * @param right
     */
    max(Object left, Object right);

    /**
     * The min function will return the smallest of it's numerical arguments.
     *
     * <code><pre>
     * {{ min(user.age, 80) }}
     * </pre></code>
     *
     * @param left
     * @param right
     */
    min(Object left, Object right);

    /**
     * <p>The parent function is used inside of a block to render the content that the parent template
     * would have rendered inside of the block had the current template not overriden it. It is similar
     * to Java's super keyword.</p>
     *
     * <p>Let's assume you have a template, "parent.peb" that looks something like this:</p>
     *
     * <code><pre>
     * {% block "content" %}
     * 	parent contents
     * {% endblock %}
     * </pre></code>
     *
     * <p>And then you have another template, "child.peb" that extends "parent.peb":</p>
     *
     * <code><pre>
     * {% extends "parent.peb" %}
     *
     * {% block "content" %}
     *   child contents
     * 	 {{ parent() }}
     * {% endblock %}
     * </pre></code>
     *
     * <p>The output will look something like the following:</p>
     *
     * <code><pre>
     * parent contents
     * child contents
     * </pre></code>
     */
    parent();

    /**
     * The range function will return a list containing an arithmetic progression of numbers:
     *
     * <code><pre>
     * {% for i in range(0, 3) %}
     *     {{ i }},
     * {% endfor %}
     *
     * {# outputs 0, 1, 2, 3, #}
     * </pre></code>
     *
     * When step is given (as the third parameter), it specifies the increment (or decrement):
     *
     * <code><pre>
     * {% for i in range(0, 6, 2) %}
     *     {{ i }},
     * {% endfor %}
     *
     * {# outputs 0, 2, 4, 6, #}
     * </pre></code>
     *
     * Pebble built-in .. operator is just a shortcut for the range function with a step of 1+
     *
     * <code><pre>
     * {% for i in 0..3 %}
     *     {{ i }},
     * {% endfor %}
     *
     * {# outputs 0, 1, 2, 3, #}
     * </pre></code>
     */
    range(int start, int end, int step);
}
