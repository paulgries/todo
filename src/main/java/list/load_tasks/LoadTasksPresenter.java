package list.load_tasks;

import list.TodoListViewModel;
import list.TodoViewModelMapper;
import list.load_tasks.use_case.LoadTasksOutputBoundary;
import list.load_tasks.use_case.LoadTasksOutputData;

/**
 * The Presenter for the Load Tasks Use Case. Renders the task list from the
 * output data and fires a property change so the list view shows it.
 */
public class LoadTasksPresenter implements LoadTasksOutputBoundary {

    private final TodoListViewModel viewModel;

    public LoadTasksPresenter(TodoListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(LoadTasksOutputData outputData) {
        viewModel.getState().setTasks(TodoViewModelMapper.toRenderStates(outputData.tasks()));
        viewModel.firePropertyChanged();
    }
}