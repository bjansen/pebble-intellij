package com.github.bjansen.intellij.pebble.config

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.Disposable
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.Project

class PebbleProjectSettings(private val project: Project) : SearchableConfigurable, Disposable {

    private val form = PebbleSettingsForm(project)

    override fun isModified(): Boolean {
        val config = PropertiesComponent.getInstance(project)

        return form.customPrefix != config.getValue(prefixProperty) ?: ""
                || form.customSuffix != config.getValue(suffixProperty) ?: ""
    }

    override fun reset() {
        val config = PropertiesComponent.getInstance(project)

        form.customPrefix = config.getValue(prefixProperty)
        form.customSuffix = config.getValue(suffixProperty)
    }

    override fun apply() {
        val config = PropertiesComponent.getInstance(project)

        config.setValue(prefixProperty, form.customPrefix)
        config.setValue(suffixProperty, form.customSuffix)
    }

    override fun getId() = PebbleProjectSettings::javaClass.name

    override fun getDisplayName() = "Pebble"

    override fun createComponent() = form.createCenterPanel()

    override fun dispose() = form.disposable.dispose()

    companion object {
        val prefixProperty = "pebble.loader.prefix"
        val suffixProperty = "pebble.loader.suffix"
    }
}
