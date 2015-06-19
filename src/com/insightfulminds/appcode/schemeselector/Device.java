package com.insightfulminds.appcode.schemeselector;

import com.intellij.execution.DefaultExecutionTarget;
import com.intellij.execution.ExecutionTarget;
import com.intellij.execution.RunnerAndConfigurationSettings;

public class Device {

    private AppCode appCode;
    private ExecutionTarget executionTarget;
    private RunnerAndConfigurationSettings settings;
    private boolean selected;

    public Device(AppCode appCode, ExecutionTarget executionTarget, RunnerAndConfigurationSettings settings, boolean selected) {
        this.appCode = appCode;
        this.executionTarget = executionTarget;
        this.settings = settings;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public ExecutionTarget getExecutionTarget() {
        return executionTarget;
    }

    public String getDisplayName() {
        String text = "Select Device";
        if(executionTarget != DefaultExecutionTarget.INSTANCE) {
            text = executionTarget.getDisplayName();
        } else if(!settings.canRunOn(executionTarget)) {
            text = "Nothing to run on";
        }
        return text;
    }

    public String getDescription() {
        return "Select " + getDisplayName();
    }

    public void activate() {
        appCode.setActiveDevice(this);
    }
}
