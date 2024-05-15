package fun.felipe.models;


import java.util.List;

public record ProjectModel(String id, String slug, String title, List<String> versions) {
}
