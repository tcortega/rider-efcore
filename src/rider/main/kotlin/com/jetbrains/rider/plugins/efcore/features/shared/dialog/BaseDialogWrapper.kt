package com.jetbrains.rider.plugins.efcore.features.shared.dialog

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.ui.DialogWrapper
import com.jetbrains.rider.plugins.efcore.cli.execution.CliCommandResult

abstract class BaseDialogWrapper : DialogWrapper(true) {
    abstract fun generateCommand(): GeneralCommandLine

    open fun postCommandExecute(commandResult: CliCommandResult) {}
}