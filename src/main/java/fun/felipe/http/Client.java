package fun.felipe.http;

import com.google.gson.Gson;
import fun.felipe.models.ProjectModel;
import fun.felipe.models.VersionModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Optional;

public class Client implements AutoCloseable {
    private final OkHttpClient client;
    private final Gson gson;

    public Client(String pluginSlug) {
        this.client = new OkHttpClient.Builder()
                .addNetworkInterceptor(chain -> chain.proceed(
                        chain.request()
                                .newBuilder()
                                .header(pluginSlug, pluginSlug).build()
                ))
                .build();
        this.gson = new Gson();
    }

    public Optional<ProjectModel> fetchProject(String projectSlug) {
        Request request = new Request.Builder()
                .url("https://api.modrinth.com/v2/project/" + projectSlug)
                .build();

        try (Response response = this.client.newCall(request).execute()) {
            if (response.body() == null) {
                response.close();
                return Optional.empty();
            }

            ProjectModel projectResult = this.gson.fromJson(response.body().string(), ProjectModel.class);
            response.body().close();
            response.close();

            return Optional.of(projectResult);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Optional<VersionModel> fetchVersion(String projectSlug, String version) {
        Request request = new Request.Builder()
                .url("https://api.modrinth.com/v2/project/" + projectSlug + "/version/" + version)
                .build();

        try (Response response = this.client.newCall(request).execute()) {
            if (response.body() == null) {
                response.close();
                return Optional.empty();
            }

            VersionModel versionResult = this.gson.fromJson(response.body().string(), VersionModel.class);
            response.body().close();
            response.close();

            return Optional.of(versionResult);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void close() {
        this.client.dispatcher().executorService().shutdown();
        this.client.connectionPool().evictAll();
        if (this.client.cache() == null) return;
        try {
            this.client.cache().close();
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
}
