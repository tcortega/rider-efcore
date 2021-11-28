package me.seclerp.rider.plugins.efcore.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.jetbrains.rider.util.idea.getService
import me.seclerp.rider.plugins.efcore.clients.MigrationsClient
import me.seclerp.rider.plugins.efcore.dialogs.AddMigrationDialogWrapper

class AddMigrationAction : BaseEfCoreAction() {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val intellijProject = actionEvent.project!!
        val model = getEfCoreRiderModel(actionEvent)
        val dialog = buildDialogInstance(actionEvent, intellijProject) {
            AddMigrationDialogWrapper(model, intellijProject, currentProject, migrationsProjects, startupProjects)
        }

        if (dialog.showAndGet()) {
            val migrationsClient = intellijProject.getService<MigrationsClient>()
            val commonOptions = getCommonOptions(dialog)

            executeCommandUnderProgress(intellijProject, "Creating migration...", "New migration has been created") {
                migrationsClient.add(commonOptions, dialog.migrationName)
            }
        }
    }
}