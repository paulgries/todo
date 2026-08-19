package list.toggle_task;

import static org.assertj.core.api.Assertions.assertThat;

import java.beans.PropertyChangeListener;
import java.util.List;
import list.TodoListViewModel;
import list.toggle_task.use_case.ToggleTaskOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.domain.Task;
import todo.domain.TaskId;

class ToggleTaskPresenterTest {

    private TodoListViewModel viewModel;
    private ToggleTaskPresenter presenter;
    private int fireCount;

    @BeforeEach
    void setUp() {
        viewModel = new TodoListViewModel();
        PropertyChangeListener listener = evt -> fireCount++;
        viewModel.addPropertyChangeListener(listener);
        presenter = new ToggleTaskPresenter(viewModel);
    }

    @Test
    void prepareSuccessView_SetsTasksAndFiresWithoutClearingInput() {
        Task task = new Task(new TaskId(1), "buy milk", true);
        viewModel.getState().setInputText("half-typed text");

        presenter.prepareSuccessView(new ToggleTaskOutputData(List.of(task)));

        assertThat(viewModel.getState().getTasks()).hasSize(1);
        assertThat(viewModel.getState().getTasks().get(0).completed()).isTrue();
        assertThat(viewModel.getState().getInputText()).isEqualTo("half-typed text");
        assertThat(fireCount).isEqualTo(1);
    }

    @Test
    void prepareFailView_SetsErrorMessageAndFires() {
        presenter.prepareFailView("task not found");

        assertThat(viewModel.getState().getMessage()).isEqualTo("task not found");
        assertThat(fireCount).isEqualTo(1);
    }
}