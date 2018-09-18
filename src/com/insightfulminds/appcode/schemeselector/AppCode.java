package com.insightfulminds.appcode.schemeselector;

import com.intellij.execution.*;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Attribute;
import org.jdom.Element;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AppCode {
    static Logger LOG = Logger.getInstance(AppCode.class.getName());

    private Project project;

    public AppCode(JComponent component) {
        this(CommonDataKeys.PROJECT.getData(DataManager.getInstance().getDataContext(component)));
    }

    public AppCode(AnActionEvent event) {
        this(event.getData(CommonDataKeys.PROJECT));
    }

    public AppCode(Project project) {
        this.project = project;
    }

    public boolean isProjectReady() {
        try {
            return project != null && !project.isDisposed() && project.isInitialized() && !DumbService.getInstance(project).isDumb();
        } catch (IndexNotReadyException exception) {
            return false;
        }
    }

    //
    // Targets
    //

    public Target getActiveTarget() {
        if (project == null) {
            return null;
        }

        RunnerAndConfigurationSettings settings = RunManagerEx.getInstanceEx(project).getSelectedConfiguration();
        if (settings == null) {
            return null;
        }

        return new Target(this, settings, getConfigurationIcon(settings));
    }

    public void setActiveTarget(Target target) {
        RunManager.getInstance(this.project).setSelectedConfiguration(target.getSettings());
    }

    public List<Target> getAvailableTargets() {
        LinkedList<Target> targets = new LinkedList<Target>();
        if (project == null) {
            return null;
        }

        RunManagerEx runManager = RunManagerEx.getInstanceEx(project);
        if (runManager == null) {
            return targets;
        }

        for (RunnerAndConfigurationSettings settings : runManager.getAllSettings()) {
            try {
                targets.add(new Target(this, settings, getConfigurationIcon(settings)));
            } catch (WriteExternalException e) {
                LOG.error(e);
            }
        }

        return targets;
    }

    private Icon getConfigurationIcon(RunnerAndConfigurationSettings settings) {
        try {
            return RunManagerEx.getInstanceEx(project).getConfigurationIcon(settings);
        } catch (IndexNotReadyException ignored) {
        }

        return null;
    }

    //
    // Devices
    //

    public void setActiveDevice(Device device) {
        ExecutionTargetManager.setActiveTarget(this.project, device.getExecutionTarget());
    }

    public Device getActiveDevice() {
        if (project == null) {
            return null;
        }

        RunnerAndConfigurationSettings settings = RunManagerEx.getInstanceEx(project).getSelectedConfiguration();
        if (settings == null) {
            return null;
        }

        ExecutionTarget executionTarget = ExecutionTargetManager.getActiveTarget(project);
        return new Device(this, executionTarget, settings, true);
    }

    public List<Device> getAvailableDevices() {
        LinkedList<Device> devices = new LinkedList<Device>();
        if (project == null) {
            return devices;
        }

        RunnerAndConfigurationSettings settings = RunManager.getInstance(project).getSelectedConfiguration();

        if (settings != null) {
            ExecutionTarget activeTarget = ExecutionTargetManager.getActiveTarget(project);
            List<ExecutionTarget> targets = ExecutionTargetManager.getTargetsToChooseFor(project, settings);

            for (ExecutionTarget executionTarget : targets) {
                devices.add(new Device(this, executionTarget, settings, executionTarget.equals(activeTarget)));
            }
        }

        return devices;
    }
}
