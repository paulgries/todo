package list.toggle_task.use_case;

import todo.domain.TaskId;

/**
 * The input data for the Toggle Task Use Case. Carries the id of the task the
 * view toggled; the interactor looks it up and flips its completion flag.
 */
public record ToggleTaskInputData(TaskId id) {
}