package list.delete_task;

import list.delete_task.use_case.DeleteTaskInputBoundary;
import list.delete_task.use_case.DeleteTaskInputData;
import todo.domain.TaskId;

/**
 * The Controller for the Delete Task Use Case. Builds the input data from the
 * view primitive (the task id), as the CAWithBuilder controllers do.
 */
public class DeleteTaskController {

    private final DeleteTaskInputBoundary deleteTaskUseCase;

    public DeleteTaskController(DeleteTaskInputBoundary deleteTaskUseCase) {
        this.deleteTaskUseCase = deleteTaskUseCase;
    }

    public void execute(TaskId id) {
        deleteTaskUseCase.execute(new DeleteTaskInputData(id));
    }
}