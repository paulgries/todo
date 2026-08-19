package data_access;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import todo.TodoDataAccess;
import todo.domain.Task;
import todo.domain.TaskId;

/**
 * The in-memory {@link TodoDataAccess}: holds the task list for the lifetime
 * of the session, like CAWithBuilder's
 * {@code InMemoryUserDataAccessObject}. This implementation does NOT persist
 * data between runs of the program.
 */
public final class InMemoryTodoDataAccess implements TodoDataAccess {

    private final List<Task> tasks = new ArrayList<>();

    @Override
    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void updateTask(Task task) {
        int index = indexOf(task.id());
        if (index >= 0) {
            tasks.set(index, task);
        }
    }

    @Override
    public void deleteTask(TaskId id) {
        tasks.removeIf(task -> task.id().equals(id));
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        return tasks.stream().filter(task -> task.id().equals(id)).findFirst();
    }

    private int indexOf(TaskId id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).id().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}