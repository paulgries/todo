package todo.domain;

/**
 * Factory for building {@link Task} instances, mirroring
 * {@code UserFactory} in CAWithBuilder. Use cases receive an instance through
 * their constructor.
 */
public interface TaskFactory {

    Task create(String description);
}