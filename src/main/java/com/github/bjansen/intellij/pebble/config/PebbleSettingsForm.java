package com.github.bjansen.intellij.pebble.config;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PebbleSettingsForm extends DialogWrapper {

    private JPanel content;
    private JBTextField customPrefix;
    private JBTextField customSuffix;
    private JPanel pebbleLoader;

    protected PebbleSettingsForm(@Nullable Project project) {
        super(project, false);

        initComponents();
    }

    private void initComponents() {
        pebbleLoader.setBorder(IdeBorderFactory.createTitledBorder("Pebble loader", true));
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return content;
    }

    public String getCustomPrefix() {
        return customPrefix.getText();
    }

    public void setCustomPrefix(String prefix) {
        customPrefix.setText(prefix);
    }

    public String getCustomSuffix() {
        return customSuffix.getText();
    }

    public void setCustomSuffix(String suffix) {
        this.customSuffix.setText(suffix);
    }
}
