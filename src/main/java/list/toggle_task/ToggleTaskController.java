package list.toggle_task;

import list.toggle_task.use_case.ToggleTaskInputBoundary;
import list.toggle_task.use_case.ToggleTaskInputData;
import todo.domain.TaskId;

/**
 * The Controller for the Toggle Task Use Case. Builds the input data from the
 * view primitive (the task id), as the CAWithBuilder controllers do.
 */
public class ToggleTaskController {

    private final ToggleTaskInputBoundary toggleTaskUseCase;

    public ToggleTaskController(ToggleTaskInputBoundary toggleTaskUseCase) {
        this.toggleTaskUseCase = toggleTaskUseCase;
    }

    public void execute(TaskId id) {
        toggleTaskUseCase.execute(new ToggleTaskInputData(id));
    }
}