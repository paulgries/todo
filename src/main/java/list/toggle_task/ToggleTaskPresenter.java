package list.toggle_task;

import list.TodoListViewModel;
import list.TodoViewModelMapper;
import list.toggle_task.use_case.ToggleTaskOutputBoundary;
import list.toggle_task.use_case.ToggleTaskOutputData;

/**
 * The Presenter for the Toggle Task Use Case. Renders the updated task list
 * from the output data and fires a property change so the list view
 * re-renders. Failures go to the view model, whose view shows them.
 */
public class ToggleTaskPresenter implements ToggleTaskOutputBoundary {

    private final TodoListViewModel viewModel;

    public ToggleTaskPresenter(TodoListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ToggleTaskOutputData outputData) {
        viewModel.getState().setTasks(TodoViewModelMapper.toRenderStates(outputData.tasks()));
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.getState().setMessage(error);
        viewModel.firePropertyChanged();
    }
}