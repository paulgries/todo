package list.add_task;

import static org.assertj.core.api.Assertions.assertThat;

import java.beans.PropertyChangeListener;
import java.util.List;
import list.TodoListViewModel;
import list.add_task.use_case.AddTaskOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.domain.Task;
import todo.domain.TaskId;

class AddTaskPresenterTest {

    private TodoListViewModel viewModel;
    private AddTaskPresenter presenter;
    private int fireCount;

    @BeforeEach
    void setUp() {
        viewModel = new TodoListViewModel();
        PropertyChangeListener listener = evt -> fireCount++;
        viewModel.addPropertyChangeListener(listener);
        presenter = new AddTaskPresenter(viewModel);
    }

    @Test
    void prepareSuccessView_SetsTasksClearsInputAndFires() {
        Task task = new Task(new TaskId(1), "buy milk", false);

        presenter.prepareSuccessView(new AddTaskOutputData(List.of(task)));

        assertThat(viewModel.getState().getTasks()).hasSize(1);
        assertThat(viewModel.getState().getTasks().get(0).id()).isEqualTo(new TaskId(1));
        assertThat(viewModel.getState().getTasks().get(0).description()).isEqualTo("buy milk");
        assertThat(viewModel.getState().getInputText()).isEmpty();
        assertThat(fireCount).isEqualTo(1);
    }

    @Test
    void prepareFailView_SetsErrorMessageAndFires() {
        presenter.prepareFailView("task description cannot be empty");

        assertThat(viewModel.getState().getMessage()).isEqualTo("task description cannot be empty");
        assertThat(fireCount).isEqualTo(1);
    }
}