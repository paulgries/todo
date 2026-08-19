package list.load_tasks;

import list.load_tasks.use_case.LoadTasksInputBoundary;
import list.load_tasks.use_case.LoadTasksInputData;

/**
 * The Controller for the Load Tasks Use Case. Runs the use case with empty
 * input; called by the composition root at startup.
 */
public class LoadTasksController {

    private final LoadTasksInputBoundary loadTasksUseCase;

    public LoadTasksController(LoadTasksInputBoundary loadTasksUseCase) {
        this.loadTasksUseCase = loadTasksUseCase;
    }

    public void execute() {
        loadTasksUseCase.execute(new LoadTasksInputData());
    }
}