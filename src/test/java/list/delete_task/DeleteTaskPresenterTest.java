package list.delete_task;

import static org.assertj.core.api.Assertions.assertThat;

import java.beans.PropertyChangeListener;
import java.util.List;
import list.TodoListViewModel;
import list.delete_task.use_case.DeleteTaskOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.domain.Task;
import todo.domain.TaskId;

class DeleteTaskPresenterTest {

    private TodoListViewModel viewModel;
    private DeleteTaskPresenter presenter;
    private int fireCount;

    @BeforeEach
    void setUp() {
        viewModel = new TodoListViewModel();
        PropertyChangeListener listener = evt -> fireCount++;
        viewModel.addPropertyChangeListener(listener);
        presenter = new DeleteTaskPresenter(viewModel);
    }

    @Test
    void prepareSuccessView_SetsTasksAndFires() {
        Task task = new Task(new TaskId(2), "write tests", false);

        presenter.prepareSuccessView(new DeleteTaskOutputData(List.of(task)));

        assertThat(viewModel.getState().getTasks()).hasSize(1);
        assertThat(viewModel.getState().getTasks().get(0).id()).isEqualTo(new TaskId(2));
        assertThat(fireCount).isEqualTo(1);
    }

    @Test
    void prepareFailView_SetsErrorMessageAndFires() {
        presenter.prepareFailView("task not found");

        assertThat(viewModel.getState().getMessage()).isEqualTo("task not found");
        assertThat(fireCount).isEqualTo(1);
    }
}