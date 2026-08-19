package list.add_task;

import list.TodoListViewModel;
import list.TodoViewModelMapper;
import list.add_task.use_case.AddTaskOutputBoundary;
import list.add_task.use_case.AddTaskOutputData;

/**
 * The Presenter for the Add Task Use Case. Renders the updated task list from
 * the output data, clears the add field, and fires a property change so the
 * list view re-renders. Failures go to the view model, whose view shows them.
 */
public class AddTaskPresenter implements AddTaskOutputBoundary {

    private final TodoListViewModel viewModel;

    public AddTaskPresenter(TodoListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(AddTaskOutputData outputData) {
        viewModel.getState().setTasks(TodoViewModelMapper.toRenderStates(outputData.tasks()));
        viewModel.getState().setInputText("");
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.getState().setMessage(error);
        viewModel.firePropertyChanged();
    }
}