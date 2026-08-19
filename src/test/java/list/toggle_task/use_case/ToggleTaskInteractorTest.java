package list.toggle_task.use_case;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
class ToggleTaskInteractorTest {

    @Mock
    private ToggleTaskOutputBoundary presenter;

    private InMemoryTodoDataAccess dataAccess;
    private ToggleTaskInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryTodoDataAccess();
        dataAccess.addTask(new Task(new TaskId(1), "buy milk", false));
        interactor = new ToggleTaskInteractor(presenter, dataAccess);
    }

    @Test
    void execute_OpenTask_CompletesItAndPresentsUpdatedList() {
        interactor.execute(new ToggleTaskInputData(new TaskId(1)));

        ArgumentCaptor<ToggleTaskOutputData> captor =
                ArgumentCaptor.forClass(ToggleTaskOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        assertThat(dataAccess.getTasks().get(0).completed()).isTrue();
        assertThat(captor.getValue().tasks()).containsExactly(dataAccess.getTasks().get(0));
        verify(presenter, never()).prepareFailView(any());
    }

    @Test
    void execute_CompletedTask_ReopensIt() {
        dataAccess.updateTask(new Task(new TaskId(1), "buy milk", true));

        interactor.execute(new ToggleTaskInputData(new TaskId(1)));

        assertThat(dataAccess.getTasks().get(0).completed()).isFalse();
        verify(presenter, never()).prepareFailView(any());
    }

    @Test
    void execute_MissingTask_PresentsFail() {
        interactor.execute(new ToggleTaskInputData(new TaskId(99)));

        verify(presenter).prepareFailView("task not found");
        verify(presenter, never()).prepareSuccessView(any());
    }
}