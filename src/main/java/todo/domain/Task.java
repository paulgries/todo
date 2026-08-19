package todo.domain;

/**
 * A task in the todo list. Descriptions are trimmed on construction and must
 * not be blank; {@link #toggled()} returns a copy with the completion flag
 * flipped, keeping identity and description.
 */
public record Task(TaskId id, String description, boolean completed) {

    public Task {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description must not be blank");
        }
        description = description.trim();
    }

    public Task toggled() {
        return new Task(id, description, !completed);
    }
}