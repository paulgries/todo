package todo;

import java.util.List;
import java.util.Optional;
import todo.domain.Task;
import todo.domain.TaskId;

/**
 * The application-layer data access for the todo list: the tasks themselves,
 * shared by the use cases that operate on them. The concrete in-memory
 * implementation lives in {@code data_access}, mirroring CAWithBuilder's
 * {@code InMemoryUserDataAccessObject}.
 */
public interface TodoDataAccess {

    List<Task> getTasks();

    void addTask(Task task);

    void updateTask(Task task);

    void deleteTask(TaskId id);

    Optional<Task> findById(TaskId id);
}