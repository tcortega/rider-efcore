package me.seclerp.rider.plugins.efcore.features.preview

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import me.seclerp.rider.plugins.efcore.ui.readonlyExpandableTextField
import java.awt.Dimension
import javax.swing.Action
import javax.swing.JComponent

class CommandPreviewDialogWrapper(
    private val cliCommand: GeneralCommandLine,
) : DialogWrapper(true) {
    init {
        init()

        title = "Command Preview"
        window.minimumSize = Dimension(500, 200)
    }

    override fun createCenterPanel(): JComponent = panel {
        row("Working directory:") {
            readonlyExpandableTextField { cliCommand.workDirectory.path }
                .align(AlignX.FILL)
        }

        row("Command:") {
            readonlyExpandableTextField { cliCommand.commandLineString }
                .align(AlignX.FILL)
        }
    }

    override fun createActions(): Array<Action> = arrayOf(okAction)
}