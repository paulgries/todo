package list.add_task.use_case;

import todo.TodoDataAccess;
import todo.domain.Task;
import todo.domain.TaskFactory;

/**
 * The Interactor for the Add Task Use Case. Builds a fresh task through the
 * injected {@link TaskFactory} — as CAWithBuilder's interactors receive their
 * factories in the constructor — validates the description, and writes the
 * task to the application-layer data access. Failures (blank descriptions)
 * route through the output boundary, never to the view.
 */
public final class AddTaskInteractor implements AddTaskInputBoundary {

    private final AddTaskOutputBoundary presenter;
    private final TaskFactory taskFactory;
    private final TodoDataAccess dataAccess;

    public AddTaskInteractor(
            AddTaskOutputBoundary presenter,
            TaskFactory taskFactory,
            TodoDataAccess dataAccess) {
        this.presenter = presenter;
        this.taskFactory = taskFactory;
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(AddTaskInputData inputData) {
        final String description = inputData.description().trim();
        if (description.isEmpty()) {
            presenter.prepareFailView("task description cannot be empty");
            return;
        }
        final Task task = taskFactory.create(description);
        dataAccess.addTask(task);
        presenter.prepareSuccessView(new AddTaskOutputData(dataAccess.getTasks()));
    }
}