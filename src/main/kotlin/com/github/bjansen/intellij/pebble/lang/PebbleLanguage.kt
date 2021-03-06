package com.github.bjansen.intellij.pebble.lang

import com.intellij.lang.Language

object PebbleLanguage : Language("Pebble") {
    val codeSample = """
        {# Pebble template example #}
        Greetings, {{ who | capitalize }}!
        
        {% <kw>block</kw> content %}{% <kw>endblock</kw> %}
        
        {% <kw>if</kw> fun("string", 1 + 2 % 3, {"a": [12]}) ^^ %}
           Your neighbor.
        {% <kw>else</kw> %}
           Your boss.
        {% <kw>endif</kw> %}
        """.trimIndent()
}
