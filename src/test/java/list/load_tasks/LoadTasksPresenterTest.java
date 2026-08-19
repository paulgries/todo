package list.load_tasks;

import static org.assertj.core.api.Assertions.assertThat;

import java.beans.PropertyChangeListener;
import java.util.List;
import list.TodoListViewModel;
import list.load_tasks.use_case.LoadTasksOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.domain.Task;
import todo.domain.TaskId;

class LoadTasksPresenterTest {

    private TodoListViewModel viewModel;
    private LoadTasksPresenter presenter;
    private int fireCount;

    @BeforeEach
    void setUp() {
        viewModel = new TodoListViewModel();
        PropertyChangeListener listener = evt -> fireCount++;
        viewModel.addPropertyChangeListener(listener);
        presenter = new LoadTasksPresenter(viewModel);
    }

    @Test
    void prepareSuccessView_SetsTasksAndFires() {
        Task task = new Task(new TaskId(1), "buy milk", false);

        presenter.prepareSuccessView(new LoadTasksOutputData(List.of(task)));

        assertThat(viewModel.getState().getTasks()).hasSize(1);
        assertThat(viewModel.getState().getTasks().get(0).description()).isEqualTo("buy milk");
        assertThat(fireCount).isEqualTo(1);
    }
}