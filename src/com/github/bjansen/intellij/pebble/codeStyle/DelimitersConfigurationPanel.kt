package com.github.bjansen.intellij.pebble.codeStyle

import com.intellij.application.options.codeStyle.CustomizableLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.CustomCodeStyleSettings
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import com.intellij.ui.OptionGroup
import com.intellij.ui.components.JBTextField
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * A panel that shows text fields to customize delimiters.
 */
class DelimitersConfigurationPanel(settings: CodeStyleSettings) : CustomizableLanguageCodeStylePanel(settings) {

    private val myPanel = JPanel(GridLayout(0, 1))
    private val tagOpen = JBTextField("{%")
    private val tagClose = JBTextField("%}")
    private val printOpen = JBTextField("{{")
    private val printClose = JBTextField("}}")

    init {
        init()
    }

    override fun init() {
        super.init()

        val tagsOptionGroup = OptionGroup("Tag directives")
        tagsOptionGroup.add(JLabel("Opening delimiter"), tagOpen)
        tagsOptionGroup.add(JLabel("Closing delimiter"), tagClose)

        myPanel.add(tagsOptionGroup.createPanel())

        val printOptionGroup = OptionGroup("Print directives")
        printOptionGroup.add(JLabel("Opening delimiter"), printOpen)
        printOptionGroup.add(JLabel("Closing delimiter"), printClose)

        myPanel.add(printOptionGroup.createPanel())
    }

    override fun getTabTitle() = "Delimiters"

    override fun resetImpl(settings: CodeStyleSettings) {
        val pebbleSettings = settings.getCustomSettings(PebbleCodeStyleSettings::class.java)

        tagOpen.text = pebbleSettings.TAG_OPEN
        tagClose.text = pebbleSettings.TAG_CLOSE
        printOpen.text = pebbleSettings.PRINT_OPEN
        printClose.text = pebbleSettings.PRINT_CLOSE
    }

    override fun isModified(settings: CodeStyleSettings): Boolean {
        val pebbleSettings = settings.getCustomSettings(PebbleCodeStyleSettings::class.java)

        return tagOpen.text != pebbleSettings.TAG_OPEN
            || tagClose.text != pebbleSettings.TAG_CLOSE
            || printOpen.text != pebbleSettings.PRINT_OPEN
            || printClose.text != pebbleSettings.PRINT_CLOSE
    }

    override fun apply(settings: CodeStyleSettings) {
        val pebbleSettings = settings.getCustomSettings(PebbleCodeStyleSettings::class.java)

        pebbleSettings.TAG_OPEN = tagOpen.text
        pebbleSettings.TAG_CLOSE = tagClose.text
        pebbleSettings.PRINT_OPEN = printOpen.text
        pebbleSettings.PRINT_CLOSE = printClose.text
    }

    override fun getSettingsType(): LanguageCodeStyleSettingsProvider.SettingsType {
        return LanguageCodeStyleSettingsProvider.SettingsType.LANGUAGE_SPECIFIC
    }

    override fun getPanel() = myPanel

    override fun showStandardOptions(vararg optionNames: String) {
    }

    override fun showAllStandardOptions() {
    }

    /* ** For compatibility with IntelliJ 15 ** */
    override fun showCustomOption(settingsClass: Class<out CustomCodeStyleSettings>?, fieldName: String?, title: String?, groupName: String?, vararg options: Any?) {
    }

    override fun showCustomOption(settingsClass: Class<out CustomCodeStyleSettings>?, fieldName: String?, title: String?, groupName: String?, anchor: CodeStyleSettingsCustomizable.OptionAnchor?, anchorFieldName: String?, vararg options: Any?) {
    }

    override fun renameStandardOption(fieldName: String?, newTitle: String?) {
    }

    override fun moveStandardOption(fieldName: String?, newGroup: String?) {
    }
}
