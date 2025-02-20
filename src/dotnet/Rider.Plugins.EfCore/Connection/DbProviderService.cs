using System;
using System.Collections.Generic;
using System.Linq;
using JetBrains.ProjectModel;
using JetBrains.ProjectModel.NuGet.Operations;
using JetBrains.ProjectModel.NuGet.Packaging;
using Rider.Plugins.EfCore.Rd;

namespace Rider.Plugins.EfCore.Connection
{
  [SolutionComponent]
  public class DbProviderService
  {
    // Since it's hard to detect provider by NuGet package dependencies and structure, we will rely on a well-known list.
    private static string[] KnownDbProviderIds =
    {
      "Microsoft.EntityFrameworkCore.SqlServer",
      "Microsoft.EntityFrameworkCore.Sqlite",
      "Microsoft.EntityFrameworkCore.InMemory",
      "Microsoft.EntityFrameworkCore.Cosmos",
      "Npgsql.EntityFrameworkCore.PostgreSQL",
      "Pomelo.EntityFrameworkCore.MySql",
      "MySql.EntityFrameworkCore",
      "Oracle.EntityFrameworkCore",
      "Devart.Data.MySql.EFCore",
      "Devart.Data.Oracle.EFCore",
      "Devart.Data.PostgreSql.EFCore",
      "Devart.Data.SQLite.EFCore",
      "FirebirdSql.EntityFrameworkCore.Firebird",
      "IBM.EntityFrameworkCore",
      "IBM.EntityFrameworkCore-lnx",
      "IBM.EntityFrameworkCore-osx",
      "EntityFrameworkCore.Jet",
      "Google.Cloud.EntityFrameworkCore.Spanner",
      "Teradata.EntityFrameworkCore",
      "FileContextCore",
      "FileBaseContext"
    };

    private readonly NuGetDisembowelOperation _disembowelOperation;
    private readonly NuGetPackageReferenceTracker _nuGetPackageReferenceTracker;

    public DbProviderService(
      NuGetDisembowelOperation disembowelOperation,
      NuGetPackageReferenceTracker nuGetPackageReferenceTracker)
    {
      _disembowelOperation = disembowelOperation;
      _nuGetPackageReferenceTracker = nuGetPackageReferenceTracker;
    }

    /// <summary>
    /// Returns detected DB providers (aka EF Core providers) that could be used in "Provider" input box.
    /// </summary>
    /// <param name="project"></param>
    /// <returns></returns>
    public IReadOnlyCollection<DbProviderInfo> GetDbProviders(IProject project)
    {
      _disembowelOperation.WaitForCleanState();

      return _nuGetPackageReferenceTracker
        .GetInstalledPackages(project)
        .Where(package => KnownDbProviderIds.Contains(package.PackageIdentity.Id, StringComparer.InvariantCultureIgnoreCase))
        .Select(package => new DbProviderInfo(package.PackageIdentity.Id, package.PackageIdentity.Version.ToString()))
        .ToList();
    }
  }
}
