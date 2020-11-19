package com.github.bjansen.intellij.pebble.codeStyle

import com.intellij.application.options.codeStyle.CustomizableLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import com.intellij.ui.OptionGroup
import com.intellij.ui.components.JBTextField
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * A panel that shows text fields to customize delimiters.
 */
class DelimitersConfigurationPanel(settings: CodeStyleSettings) : CustomizableLanguageCodeStylePanel(settings) {

    private val myPanel = JPanel(GridBagLayout())
    private val tagOpen = JBTextField("{%", 10)
    private val tagClose = JBTextField("%}", 10)
    private val printOpen = JBTextField("{{", 10)
    private val printClose = JBTextField("}}", 10)

    init {
        init()
    }

    override fun init() {
        super.init()

        val constraints = GridBagConstraints()
        constraints.anchor = GridBagConstraints.NORTHWEST
        constraints.gridwidth = 700
        constraints.gridx = 0
        constraints.weightx = 1.0
        constraints.weighty = 0.0

        val tagsOptionGroup = OptionGroup("Tag directives")
        tagsOptionGroup.add(JLabel("Opening delimiter"), tagOpen)
        tagsOptionGroup.add(JLabel("Closing delimiter"), tagClose)

        constraints.gridy = 0
        myPanel.add(tagsOptionGroup.createPanel(), constraints)

        val printOptionGroup = OptionGroup("Print directives")
        printOptionGroup.add(JLabel("Opening delimiter"), printOpen)
        printOptionGroup.add(JLabel("Closing delimiter"), printClose)

        constraints.weighty = 1.0
        constraints.gridy = 1
        myPanel.add(printOptionGroup.createPanel(), constraints)

        myPanel.border = BorderFactory.createEmptyBorder(0, 10, 0, 0)
    }

    override fun getTabTitle() = "Delimiters"

    override fun resetImpl(settings: CodeStyleSettings) {
        val pebbleSettings = settings.getCustomSettings(PebbleCodeStyleSettings::class.java)

        tagOpen.text = pebbleSettings.tagOpen
        tagClose.text = pebbleSettings.tagClose
        printOpen.text = pebbleSettings.printOpen
        printClose.text = pebbleSettings.printClose
    }

    override fun isModified(settings: CodeStyleSettings): Boolean {
        val pebbleSettings = settings.getCustomSettings(PebbleCodeStyleSettings::class.java)

        return tagOpen.text != pebbleSettings.tagOpen
            || tagClose.text != pebbleSettings.tagClose
            || printOpen.text != pebbleSettings.printOpen
            || printClose.text != pebbleSettings.printClose
    }

    override fun apply(settings: CodeStyleSettings) {
        val pebbleSettings = settings.getCustomSettings(PebbleCodeStyleSettings::class.java)

        pebbleSettings.tagOpen = tagOpen.text
        pebbleSettings.tagClose = tagClose.text
        pebbleSettings.printOpen = printOpen.text
        pebbleSettings.printClose = printClose.text
    }

    override fun getSettingsType(): LanguageCodeStyleSettingsProvider.SettingsType {
        return LanguageCodeStyleSettingsProvider.SettingsType.LANGUAGE_SPECIFIC
    }

    override fun getPanel() = myPanel

    override fun showStandardOptions(vararg optionNames: String) {
    }

    override fun showAllStandardOptions() {
    }
}
