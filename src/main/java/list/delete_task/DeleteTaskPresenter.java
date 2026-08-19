package list.delete_task;

import list.TodoListViewModel;
import list.TodoViewModelMapper;
import list.delete_task.use_case.DeleteTaskOutputBoundary;
import list.delete_task.use_case.DeleteTaskOutputData;

/**
 * The Presenter for the Delete Task Use Case. Renders the updated task list
 * from the output data and fires a property change so the list view
 * re-renders. Failures go to the view model, whose view shows them.
 */
public class DeleteTaskPresenter implements DeleteTaskOutputBoundary {

    private final TodoListViewModel viewModel;

    public DeleteTaskPresenter(TodoListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(DeleteTaskOutputData outputData) {
        viewModel.getState().setTasks(TodoViewModelMapper.toRenderStates(outputData.tasks()));
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.getState().setMessage(error);
        viewModel.firePropertyChanged();
    }
}