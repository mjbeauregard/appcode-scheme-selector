package com.insightfulminds.appcode.schemeselector;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.ui.SizedIcon;
import com.intellij.util.ui.EmptyIcon;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

public class DeviceComboBoxAction extends ComboBoxAction implements DumbAware {
    public static final Icon CHECKED_ICON;
    public static final Icon CHECKED_SELECTED_ICON;
    public static final Icon EMPTY_ICON;

    static {
        CHECKED_ICON = new SizedIcon(AllIcons.Actions.Checked, 16, 16);
        CHECKED_SELECTED_ICON = new SizedIcon(AllIcons.Actions.Checked_selected, 16, 16);
        EMPTY_ICON = EmptyIcon.ICON_16;
    }

    @NotNull
    @Override
    protected DefaultActionGroup createPopupActionGroup(JComponent jComponent) {
        DefaultActionGroup actionGroup = new DefaultActionGroup();

        AppCode appCode = new AppCode(jComponent);

        List<Device> devices = appCode.getAvailableDevices();
        for (Device device : devices) {
            actionGroup.add(new SelectDeviceAction(device));
        }

        return actionGroup;
    }

    public void update(AnActionEvent event) {
        AppCode appCode = new AppCode(event);
        Presentation presentation = event.getPresentation();

        if (appCode.isProjectReady()) {
            Device activeDevice = appCode.getActiveDevice();

            presentation.setEnabled(true);
            presentation.setText(activeDevice.getDisplayName(), false);
        }
        else {
            presentation.setEnabled(false);
            presentation.setText(" ", false);
        }
    }

    public JComponent createCustomComponent(Presentation presentation) {
        ComboBoxButton button = new ComboBoxButton(presentation);
        button.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        return button;
    }

    private class SelectDeviceAction extends AnAction {
        private Device device;

        public SelectDeviceAction(Device device) {
            this.device = device;
            render(this.getTemplatePresentation());
        }

        @Override
        public void actionPerformed(AnActionEvent event) {
            device.activate();

            Presentation presentation = event.getPresentation();
            render(presentation);
        }

        public void update(AnActionEvent event) {
            render(event.getPresentation());
        }

        private void render(Presentation presentation) {
            presentation.setText(device.getDisplayName(), false);
            presentation.setDescription(device.getDescription());
            presentation.setIcon(device.isSelected() ? CHECKED_ICON : EMPTY_ICON);
            presentation.setSelectedIcon(device.isSelected() ? CHECKED_SELECTED_ICON : EMPTY_ICON);
        }
    }
}
