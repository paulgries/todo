package list.load_tasks.use_case;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import data_access.InMemoryTodoDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import todo.domain.Task;
import todo.domain.TaskId;

@ExtendWith(MockitoExtension.class)
class LoadTasksInteractorTest {

    @Mock
    private LoadTasksOutputBoundary presenter;

    private InMemoryTodoDataAccess dataAccess;
    private LoadTasksInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryTodoDataAccess();
        dataAccess.addTask(new Task(new TaskId(1), "buy milk", false));
        dataAccess.addTask(new Task(new TaskId(2), "write tests", true));
        interactor = new LoadTasksInteractor(presenter, dataAccess);
    }

    @Test
    void execute_WithStoredTasks_PresentsCurrentList() {
        interactor.execute(new LoadTasksInputData());

        ArgumentCaptor<LoadTasksOutputData> captor =
                ArgumentCaptor.forClass(LoadTasksOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        assertThat(captor.getValue().tasks())
                .extracting(Task::id)
                .containsExactly(new TaskId(1), new TaskId(2));
    }
}