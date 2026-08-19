package list.add_task.use_case;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import data_access.InMemoryTodoDataAccess;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import todo.domain.CommonTaskFactory;
import todo.domain.Task;
import todo.domain.TaskId;

@ExtendWith(MockitoExtension.class)
class AddTaskInteractorTest {

    @Mock
    private AddTaskOutputBoundary presenter;

    private InMemoryTodoDataAccess dataAccess;
    private AddTaskInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryTodoDataAccess();
        interactor = new AddTaskInteractor(presenter, new CommonTaskFactory(), dataAccess);
    }

    @Test
    void execute_ValidDescription_TrimsAddsTaskAndPresentsUpdatedList() {
        interactor.execute(new AddTaskInputData("  buy milk  "));

        ArgumentCaptor<AddTaskOutputData> captor =
                ArgumentCaptor.forClass(AddTaskOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        assertThat(dataAccess.getTasks()).hasSize(1);
        Task stored = dataAccess.getTasks().get(0);
        assertThat(stored.id()).isEqualTo(new TaskId(1));
        assertThat(stored.description()).isEqualTo("buy milk");
        assertThat(stored.completed()).isFalse();
        assertThat(captor.getValue().tasks()).containsExactly(stored);
        verify(presenter, never()).prepareFailView(any());
    }

    @Test
    void execute_BlankDescription_PresentsFailWithoutAdding() {
        interactor.execute(new AddTaskInputData("   "));

        verify(presenter).prepareFailView("task description cannot be empty");
        verify(presenter, never()).prepareSuccessView(any());
        assertThat(dataAccess.getTasks()).isEmpty();
    }
}