import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Lists all builtin filters and their arguments.
 */
interface Filters {

    /**
     * The {@code abbreviate} filter will abbreviate a string using an ellipsis. It takes one argument which is the max
     * width of the desired output including the length of the ellipsis.
     *
     * <code><pre>
     * {{ "this is a long sentence." | abbreviate(7) }}
     * </pre></code>
     * The above example will output the following:
     * <code><pre>
     * this...
     * </pre></code>
     * @param length
     */
    abbreviate(int length);

    /**
     * The {@code abs} filter is used to obtain the absolute value.
     *
     * <code><pre>
     * {{ -7 | abs }}
     *
     * {# output: 7 #}
     * </pre></code>
     */
    abs();

    /**
     * The {@code capitalize} filter will capitalize the first letter of the string.
     *
     * <code><pre>
     * {% verbatim %}
     * {{ "article title" | capitalize }}
     * {% endverbatim %}
     * </pre></code>
     * The above example will output the following:
     * <code><pre>
     * Article title
     * </pre></code>
     * @see #title()
     */
    capitalize();

    /**
     * The {@code date} filter is used to format an existing {@link Date} object. The filter will construct a
     * {@link SimpleDateFormat} using the provided pattern and then use this newly created
     * {@link SimpleDateFormat} to format the provided {@link Date} or {@link Number} object.
     *
     * <code><pre>
     * {{ user.birthday | date("yyyy-MM-dd") }}
     * </pre></code>
     *
     * The alternative way to use this	filter is to use it on a string but then provide two arguments:
     * first is the desired pattern for the output and the second is the existing format used to parse the
     * input string into a {@link Date} object.
     * <code><pre>
     * {{ "July 24, 2001" | date("yyyy-MM-dd", existingFormat="MMMM dd, yyyy") }}
     * </pre></code>
     * The above example will output the following:
     * <code><pre>
     * 2001-07-24
     * </pre></code>
     *
     * @param format
     * @param existingFormat
     */
    date(String format, String existingFormat);

    /**
     * The {@code default} filter will render a default value if and only if the object being filtered is empty.
     * A variable is empty if it is null, an empty string, an empty collection, or an empty map.
     * <code><pre>
     * {{ user.phoneNumber | default("No phone number") }}
     * </pre></code>
     * In the following example, if {@code foo}, {@code bar}, or {@code baz} are null the output will become an empty string which is a perfect use case for the default filter:
     * <code><pre>
     * {{ foo.bar.baz | default("No baz") }}
     * </pre></code>
     * Note that the default filter will suppress any {@link com.mitchellbosecke.pebble.error.AttributeNotFoundException} exceptions that will usually be thrown when {@code strictVariables} is set to {@code true}.
     *
     * @param $$default
     */
    $$default(Object $$default);

    /**
     * The {@code escape} filter will turn special characters into safe character references in order to avoid XSS
     * vulnerabilities. This filter will typically only need to be used if you've turned off autoescaping.
     * <code><pre>
     * {% verbatim %}
     * {{ "&lt;div&gt;" | escape }}
     * {# output: &amp;lt;div&amp;gt; #}
     * {% endverbatim %}
     * </pre></code>
     * Please read the {{ anchor('escaping guide', 'Escaping') }} for more information about escaping.
     */
    escape();

    /**
     * The {@code first} filter will return the first item of a collection, or the first letter of a string.
     * <code><pre>
     * {{ users | first }}
     * {# will output the first item in the collection named 'user' #}
     *
     * {{ 'Mitch' | first }}
     * {# will output 'M' #}
     * </pre></code>
     */
    first();

    /**
     * The {@code join} filter will concatenate all items of a collection into a string. An optional argument can be given
     * to be used as the separator between items.
     * <code><pre>
     * {#
     *     List&lt;String> names = new ArrayList&lt;>();
     *     names.add("Alex");
     *     names.add("Joe");
     *     names.add("Bob");
     * #}
     * {{ names | join(',') }}
     * {# will output: Alex,Joe,Bob #}
     * </pre></code>
     *
     * @param separator
     */
    join(String separator);

    /**
     * The {@code last} filter will return the last item of a collection, or the last letter of a string.
     * <code><pre>
     * {{ users | last }}
     * {# will output the last item in the collection named 'users' #}
     *
     * {{ 'Mitch' | last }}
     * {# will output 'h' #}
     * </pre></code>
     */
    last();

    /**
     * The {@code length} filter returns the number of items of collection, map or the length of a string:
     *
     * <code><pre>
     * {% if users|length > 10 %}
     *     ...
     * {% endif %}
     * </pre></code>
     */
    length();

    /**
     * The {@code lower} filter makes an entire string lower case.
     * <code><pre>
     * {{ "THIS IS A LOUD SENTENCE" | lower }}
     * </pre></code>
     * The above example will output the following:
     * <code><pre>
     * this is a loud sentence
     * </pre></code>
     */
    lower();

    /**
     * The {@code numberformat} filter is used to format a decimal number. Behind the scenes it uses {@link java.text.DecimalFormat}.
     * <code><pre>
     * {{ 3.141592653 | numberformat("#.##") }}
     * </pre></code>
     * The above example will output the following:
     * <code><pre>
     * 3.14
     * </pre></code>
     *
     * @param format
     */
    numberformat(String format);

    /**
     * The {@code raw} filter prevents the output of an expression from being escaped by the autoescaper.
     * The {@code raw} filter must be the very last operation performed within the expression otherwise the
     * autoescaper will deem the expression as potentially unsafe and escape it regardless.
     *
     * <code><pre>
     * {% verbatim %}
     * {% set danger = "&lt;div>" %}
     * {{ danger | upper | raw }}
     * {# ouptut: &lt;DIV> #}
     * {% endverbatim %}
     * </pre></code>
     *
     * If the {@code raw} filter is not the last operation performed then the expression will be escaped:
     * <code><pre>
     * {% verbatim %}
     * {% set danger = "&lt;div>" %}
     * {{ danger | raw | upper }}
     * {# output: &amp;lt;DIV&amp;gt; #}
     * {% endverbatim %}
     * </pre></code>
     * Please read the {{ anchor('escaping guide', 'Escaping') }} for more information about escaping.
     */
    raw();

    /**
     * The 'replace' filter formats a given string by replacing the placeholders (placeholders are free-form):
     * <code><pre>
     * {{ "I like %this% and %that%." | replace({'%this%': foo, '%that%': "bar"}) }}
     * </pre></code>
     *
     * @param replace_pairs the placeholders to replace
     */
    replace(Map<String, Object> replace_pairs);

    /**
     * The 'reverse' filter reverses a List:
     * <code><pre>
     * {% for user in users | reverse %} {{ user }} {% endfor %}
     * </pre></code>
     */
    reverse();

    /**
     * The {@code rsort} filter will sort a list in reversed order. The items of the list must implement {@link Comparable}.
     * <code><pre>
     * {% for user in users | rsort %}
     *        {{ user.name }}
     * {% endfor %}
     * </pre></code>
     */
    rsort();

    /**
     * The {@code slice} filter returns a portion of a list, array, or string.
     * <code><pre>
     * {{ ['apple', 'peach', 'pear', 'banana'] | slice(1,3) }}
     * {# results in: [peach, pear] #}
     *
     *
     * {{ 'Mitchell' | slice(1,3) }}
     * {# results in: 'it' #}
     * </pre></code>
     *
     * @param fromIdex 0-based and inclusive
     * @param toIndex 0-based and inclusive
     */
    slice(int fromIdex, int toIndex);

    /**
     * The {@code sort} filter will sort a list. The items of the list must implement {@link Comparable}.
     * <code><pre>
     * {% for user in users | sort %}
     *        {{ user.name }}
     * {% endfor %}
     * </pre></code>
     */
    sort();

    /**
     * The {@code split} filter splits a string by the given delimiter and returns a list of strings.
     * <code><pre>
     * {% set foo = "one,two,three" | split(',') %}
     * {# foo contains ['one', 'two', 'three'] #}
     * </pre></code>
     *
     * You can also pass a limit argument:
     * - If {@code limit} is positive, then the pattern will be applied at most n - 1 times, the array's length will be no greater than n, and the array's last entry will contain all input beyond the last matched delimiter;
     * - If {@code limit} is negative, then the pattern will be applied as many times as possible and the array can have any length;
     * - If {@code limit} is zero, then the pattern will be applied as many times as possible, the array can have any length, and trailing empty strings will be discarded;
     *
     * <code><pre>
     * {% set foo = "one,two,three,four,five" | split(',', 3) %}
     * {# foo contains ['one', 'two', 'three,four,five'] #}
     * </pre></code>
     */
    split(String delimiter, int limit);

    /**
     * The {@code title} filter will capitalize the first letter of each word.
     * <code><pre>
     * {% verbatim %}
     * {{ "article title" | title }}
     * {% endverbatim %}
     * </pre></code>
     * The above example will output the following:
     * <code><pre>
     * Article Title
     * </pre></code>
     *
     * @see #capitalize()
     */
    title();

    /**
     * The {@code trim} filter is used to trim whitespace off the beginning and end of a string.
     * <code><pre>
     * {{ "    This text has too much whitespace.    " | trim }}
     * </pre></code>
     * The above example will output the following:
     * <code><pre>
     * This text has too much whitespace.
     * </pre></code>
     */
    trim();

    /**
     * The {@code upper} filter makes an entire string upper case.
     *
     * <code><pre>
     * {{ "this is a quiet sentence." | upper }}
     * </pre></code>
     * The above example will output the following:
     * <code><pre>
     * THIS IS A QUIET SENTENCE.
     * </pre></code>
     */
    upper();

    /**
     * The {@code urlencod} translates a string into {@code application/x-www-form-urlencoded} format using the "UTF-8" encoding scheme.
     *
     * <code><pre>
     * {{ "The string ü@foo-bar" | urlencode }}
     * </pre></code>
     *
     * The above example will output the following:
     * <code><pre>
     * The+string+%C3%BC%40foo-bar
     * </pre></code>
     */
    urlencode();
}
