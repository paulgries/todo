package list.load_tasks.use_case;

import java.util.List;
import todo.domain.Task;

/**
 * The output data for the Load Tasks Use Case: the current task list, which
 * the presenter renders into the initial view.
 */
public record LoadTasksOutputData(List<Task> tasks) {
}