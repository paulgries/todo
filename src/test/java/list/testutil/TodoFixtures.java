package list.testutil;

import java.util.List;
import todo.domain.Task;
import todo.domain.TaskId;

/**
 * Reusable task fixtures for tests, mirroring tictactoe's GameFixtures.
 */
public final class TodoFixtures {

    private TodoFixtures() {
    }

    public static Task task(long id, String description, boolean completed) {
        return new Task(new TaskId(id), description, completed);
    }

    public static List<Task> tasksOf(Task... tasks) {
        return List.of(tasks);
    }
}