package list.add_task.use_case;

/**
 * The input data for the Add Task Use Case. Carries the raw description the
 * view submitted; the interactor trims and validates it so blank input fails
 * through the output boundary, not as an exception to the view.
 */
public record AddTaskInputData(String description) {
}