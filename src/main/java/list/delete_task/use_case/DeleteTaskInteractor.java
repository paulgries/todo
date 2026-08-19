package list.delete_task.use_case;

import todo.TodoDataAccess;
import todo.domain.Task;

/**
 * The Interactor for the Delete Task Use Case. Removes the task by id from
 * the application-layer data access. Missing ids fail through the output
 * boundary, never to the view.
 */
public final class DeleteTaskInteractor implements DeleteTaskInputBoundary {

    private final DeleteTaskOutputBoundary presenter;
    private final TodoDataAccess dataAccess;

    public DeleteTaskInteractor(DeleteTaskOutputBoundary presenter, TodoDataAccess dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(DeleteTaskInputData inputData) {
        final Task found = dataAccess.findById(inputData.id()).orElse(null);
        if (found == null) {
            presenter.prepareFailView("task not found");
            return;
        }
        dataAccess.deleteTask(inputData.id());
        presenter.prepareSuccessView(new DeleteTaskOutputData(dataAccess.getTasks()));
    }
}