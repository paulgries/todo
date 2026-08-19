package todo.domain;

/**
 * Default {@link TaskFactory} implementation, mirroring
 * {@code CommonUserFactory} in CAWithBuilder: it hands out fresh ids in
 * increasing order.
 */
public final class CommonTaskFactory implements TaskFactory {

    private long nextId = 1;

    @Override
    public Task create(String description) {
        return new Task(new TaskId(nextId++), description, false);
    }
}