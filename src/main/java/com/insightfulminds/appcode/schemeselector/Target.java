package com.insightfulminds.appcode.schemeselector;

import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.ConfigurationType;

import javax.swing.*;

public class Target {
    private AppCode appCode;
    private Icon icon;
    private RunnerAndConfigurationSettings settings;

    public Target(AppCode appCode, RunnerAndConfigurationSettings settings, Icon icon) {
        this.appCode = appCode;
        this.icon = icon;
        this.settings = settings;
    }

    public ConfigurationType getConfigType() {
        return settings.getConfiguration().getType();
    }

    public String getDisplayName() {
        String name = settings.getName();
        if (name == null || name.length() == 0) {
            name = " ";
        }
        return name;
    }

    public RunnerAndConfigurationSettings getSettings() {
        return settings;
    }

    public String getDescription() {
        return "Select " + getConfigType().getConfigurationTypeDescription() + " \'" + getDisplayName() + "\'";
    }

    public Icon getIcon() {
        return icon;
    }

    public void activate() {
        appCode.setActiveTarget(this);
    }
}
