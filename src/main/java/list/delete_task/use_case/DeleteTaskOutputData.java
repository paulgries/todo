package list.delete_task.use_case;

import java.util.List;
import todo.domain.Task;

/**
 * The output data for the Delete Task Use Case: the updated task list, which
 * the presenter renders so the view reflects the change.
 */
public record DeleteTaskOutputData(List<Task> tasks) {
}