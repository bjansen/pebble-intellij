package com.github.bjansen.intellij.pebble.codeStyle

import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.lang.Language
import com.intellij.openapi.options.Configurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import com.intellij.util.xmlb.XmlSerializer
import org.jdom.Element


class PebbleCodeStyleSettings(container: CodeStyleSettings)
    : CustomCodeStyleSettings("PebbleCodeStyleSettings", container) {

    var tagOpen = "{%"
    var tagClose = "%}"
    var printOpen = "{{"
    var printClose = "}}"

    fun useDefaultDelimiters(): Boolean {
        return tagOpen == "{%"
            && tagClose == "%}"
            && printOpen == "{{"
            && printClose == "}}"
    }

    // We have to override those because Kotlin fields are compiled to getters/setters and
    // DefaultJDOMExternalizer only looks for Java fields
    override fun readExternal(parentElement: Element) {
        XmlSerializer.deserializeInto(this, parentElement)
    }

    override fun writeExternal(parentElement: Element, parentSettings: CustomCodeStyleSettings) {
        val defaults = PebbleCodeStyleSettings(container)

        XmlSerializer.serializeInto(this, parentElement
        ) { accessor, bean -> accessor.read(bean) != accessor.read(defaults) }
    }
}

/**
 * Adds a 'Pebble' entry to the 'Code Style' UI.
 */
class PebbleCodeStyleSettingsProvider : CodeStyleSettingsProvider() {
    override fun createSettingsPage(settings: CodeStyleSettings, originalSettings: CodeStyleSettings?): Configurable {
        return object : CodeStyleAbstractConfigurable(settings, originalSettings, "Pebble") {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
                return MyCodeStyleMainPanel(currentSettings, settings)
            }

            override fun getHelpTopic(): String? = null
        }
    }

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings {
        return PebbleCodeStyleSettings(settings)
    }

    override fun getLanguage() = PebbleLanguage.INSTANCE

    private class MyCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings)
        : TabbedLanguageCodeStylePanel(PebbleLanguage.INSTANCE, currentSettings, settings) {

        // Only show tabs we're interested in
        override fun initTabs(settings: CodeStyleSettings) {
            addTab(DelimitersConfigurationPanel(settings))
        }
    }
}

/**
 * The actual configurable part of the code style.
 */
class PebbleLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage(): Language {
        return PebbleLanguage.INSTANCE
    }

    // TODO share with PebbleColorsAndFontsPage
    override fun getCodeSample(settingsType: SettingsType): String {
        return "{# Pebble template example #}\n" +
                "Greetings, {{ who | capitalize }}!\n" +
                "\n" +
                "{% <kw>block</kw> content %}{% <kw>endblock</kw> %}\n" +
                "\n" +
                "{% <kw>if</kw> fun(\"string\", 1 + 2 % 3, {\"a\": [12]}) ^^ %}\n" +
                "   Your neighbor.\n" +
                "{% <kw>else</kw> %}\n" +
                "   Your boss.\n" +
                "{% <kw>endif</kw> %}"
    }
}
