package fun.felipe;

import fun.felipe.http.Client;
import fun.felipe.models.ProjectModel;
import fun.felipe.models.VersionModel;

import java.util.Optional;

public class Updater {
    public static boolean verification(String projectSlug, String pluginVersion) {
        Client client = new Client(projectSlug);

        Optional<ProjectModel> project = client.fetchProject(projectSlug);
        if (project.isEmpty())
            throw new RuntimeException("Project not Found!");

        String lastVersion = project.get().versions().get(project.get().versions().size() - 1);

        Optional<VersionModel> version = client.fetchVersion(projectSlug, lastVersion);
        if (version.isEmpty())
            throw new RuntimeException("Version not Found!");

        String formattedVersion = project.get().title() + " " + pluginVersion;
        client.close();
        return version.get().name().equals(formattedVersion);
    }
}