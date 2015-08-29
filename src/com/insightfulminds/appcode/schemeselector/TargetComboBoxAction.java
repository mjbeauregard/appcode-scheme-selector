package com.insightfulminds.appcode.schemeselector;

import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

public class TargetComboBoxAction extends ComboBoxAction implements DumbAware {

    private ComboBoxButton comboBoxButton;

    @NotNull
    @Override
    protected DefaultActionGroup createPopupActionGroup(JComponent jComponent) {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(ActionManager.getInstance().getAction("editRunConfigurations"));

        AppCode appCode = new AppCode(jComponent);
        List<Target> targets = appCode.getAvailableTargets();

        ConfigurationType currentConfigType = null;

        for (Target target : targets) {
            if (target.getConfigType() != currentConfigType) {
                actionGroup.addSeparator();
            }

            actionGroup.add(new SelectTargetAction(target));
            currentConfigType = target.getConfigType();
        }

        return actionGroup;
    }

    public void update(AnActionEvent event) {
        AppCode appCode = new AppCode(event);
        Presentation presentation = event.getPresentation();

        if (appCode.isProjectReady()) {
            Target activeTarget = appCode.getActiveTarget();

            presentation.setEnabled(true);
            presentation.setText(activeTarget.getDisplayName(), false);
            presentation.setIcon(activeTarget.getIcon());
        }
        else {
            presentation.setEnabled(false);
            presentation.setText(" ", false);
            presentation.setIcon(null);
        }
    }

    public void showPopup() {
        this.comboBoxButton.showPopup();
    }

    public JComponent createCustomComponent(Presentation presentation) {
        this.comboBoxButton = new ComboBoxButton(presentation);
        comboBoxButton.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        return comboBoxButton;
    }

    private class SelectTargetAction extends AnAction {
        private final Target target;

        public SelectTargetAction(Target target) {
            this.target = target;
            render(this.getTemplatePresentation());
        }

        public void actionPerformed(AnActionEvent event) {
            target.activate();
            TargetComboBoxAction.this.update(event);
        }

        public void update(AnActionEvent event) {
            render(event.getPresentation());
        }

        private void render(Presentation presentation) {
            presentation.setText(target.getDisplayName(), false);
            presentation.setDescription(target.getDescription());
            presentation.setIcon(target.getIcon());
        }
    }
}
