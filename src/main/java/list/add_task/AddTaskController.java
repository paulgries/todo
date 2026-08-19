package list.add_task;

import list.add_task.use_case.AddTaskInputBoundary;
import list.add_task.use_case.AddTaskInputData;

/**
 * The Controller for the Add Task Use Case. Builds the input data from the
 * view primitive (the description), as the CAWithBuilder controllers do.
 */
public class AddTaskController {

    private final AddTaskInputBoundary addTaskUseCase;

    public AddTaskController(AddTaskInputBoundary addTaskUseCase) {
        this.addTaskUseCase = addTaskUseCase;
    }

    public void execute(String description) {
        addTaskUseCase.execute(new AddTaskInputData(description));
    }
}