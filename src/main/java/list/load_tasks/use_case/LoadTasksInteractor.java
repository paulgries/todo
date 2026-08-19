package list.load_tasks.use_case;

import todo.TodoDataAccess;

/**
 * The Interactor for the Load Tasks Use Case. Hands the current task list
 * from the application-layer data access to the output boundary so the view
 * can render it on startup.
 */
public final class LoadTasksInteractor implements LoadTasksInputBoundary {

    private final LoadTasksOutputBoundary presenter;
    private final TodoDataAccess dataAccess;

    public LoadTasksInteractor(LoadTasksOutputBoundary presenter, TodoDataAccess dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(LoadTasksInputData inputData) {
        presenter.prepareSuccessView(new LoadTasksOutputData(dataAccess.getTasks()));
    }
}