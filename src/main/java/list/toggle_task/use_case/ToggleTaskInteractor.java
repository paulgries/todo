package list.toggle_task.use_case;

import todo.TodoDataAccess;
import todo.domain.Task;

/**
 * The Interactor for the Toggle Task Use Case. Looks up the task by id,
 * flips its completion flag through the domain ({@link Task#toggled()}), and
 * writes it back to the application-layer data access. Missing ids fail
 * through the output boundary, never to the view.
 */
public final class ToggleTaskInteractor implements ToggleTaskInputBoundary {

    private final ToggleTaskOutputBoundary presenter;
    private final TodoDataAccess dataAccess;

    public ToggleTaskInteractor(ToggleTaskOutputBoundary presenter, TodoDataAccess dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(ToggleTaskInputData inputData) {
        final Task found = dataAccess.findById(inputData.id()).orElse(null);
        if (found == null) {
            presenter.prepareFailView("task not found");
            return;
        }
        dataAccess.updateTask(found.toggled());
        presenter.prepareSuccessView(new ToggleTaskOutputData(dataAccess.getTasks()));
    }
}