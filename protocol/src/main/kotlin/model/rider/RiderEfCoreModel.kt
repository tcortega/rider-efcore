package model.rider

import com.jetbrains.rider.model.nova.ide.SolutionModel
import com.jetbrains.rd.generator.nova.*
import com.jetbrains.rd.generator.nova.PredefinedType.*
import com.jetbrains.rd.generator.nova.csharp.CSharp50Generator
import com.jetbrains.rd.generator.nova.kotlin.Kotlin11Generator

@Suppress("unused")
object RiderEfCoreModel : Ext(SolutionModel.Solution) {
    private val StartupProjectInfo = structdef {
        field("id", guid)
        field("name", string)
        field("fullPath", string)
        field("targetFrameworks", immutableList(string))
        field("namespace", string)
    }

    private val MigrationsProjectInfo = structdef {
        field("id", guid)
        field("name", string)
        field("fullPath", string)
        field("namespace", string)
    }

    private val MigrationsIdentity = structdef {
        field("projectId", guid)
        field("dbContextClassFullName", string)
    }

    private val MigrationInfo = structdef {
        field("dbContextClassFullName", string)
        field("migrationShortName", string)
        field("migrationLongName", string)
        field("migrationFolderAbsolutePath", string)
    }

    private val DbContextInfo = structdef {
        field("name", string)
        field("fullName", string)
    }

    private val DbProviderInfo = structdef {
        field("id", string)
        field("version", string)
    }

    private val EfToolDefinition = structdef {
        field("version", string)
        field("toolKind", enum {
            +"None"
            +"Local"
            +"Global"
        })
    }

    init {
        setting(CSharp50Generator.Namespace, "Rider.Plugins.EfCore.Rd")
        setting(Kotlin11Generator.Namespace, "com.jetbrains.rider.plugins.efcore.rd")

        property("efToolsDefinition", EfToolDefinition)
        property("availableStartupProjects", immutableList(StartupProjectInfo))
        property("availableMigrationProjects", immutableList(MigrationsProjectInfo))

        call("hasAvailableMigrations", MigrationsIdentity, bool)
        call("getAvailableMigrations", MigrationsIdentity, immutableList(MigrationInfo))
        call("getAvailableDbContexts", guid, immutableList(DbContextInfo))
        call("getAvailableDbProviders", guid, immutableList(DbProviderInfo))

        callback("onMissingEfCoreToolsDetected", void, void)
    }
}