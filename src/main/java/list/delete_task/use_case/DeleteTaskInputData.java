package list.delete_task.use_case;

import todo.domain.TaskId;

/**
 * The input data for the Delete Task Use Case. Carries the id of the task the
 * view deleted.
 */
public record DeleteTaskInputData(TaskId id) {
}