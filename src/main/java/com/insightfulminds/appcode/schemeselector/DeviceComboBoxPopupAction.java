package com.insightfulminds.appcode.schemeselector;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.IdeFocusManager;

public class DeviceComboBoxPopupAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        IdeFocusManager.getGlobalInstance().doWhenFocusSettlesDown(new Runnable() {
            public void run() {
                DeviceComboBoxAction action = (DeviceComboBoxAction) ActionManager.getInstance().getAction(DeviceComboBoxAction.class.getName());
                action.showPopup();
            }
        });
    }
}
