package list.delete_task.use_case;

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
class DeleteTaskInteractorTest {

    @Mock
    private DeleteTaskOutputBoundary presenter;

    private InMemoryTodoDataAccess dataAccess;
    private DeleteTaskInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryTodoDataAccess();
        dataAccess.addTask(new Task(new TaskId(1), "buy milk", false));
        dataAccess.addTask(new Task(new TaskId(2), "write tests", false));
        interactor = new DeleteTaskInteractor(presenter, dataAccess);
    }

    @Test
    void execute_PresentTask_RemovesItAndPresentsUpdatedList() {
        interactor.execute(new DeleteTaskInputData(new TaskId(1)));

        ArgumentCaptor<DeleteTaskOutputData> captor =
                ArgumentCaptor.forClass(DeleteTaskOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        assertThat(dataAccess.getTasks())
                .extracting(Task::id)
                .containsExactly(new TaskId(2));
        assertThat(captor.getValue().tasks()).hasSize(1);
        verify(presenter, never()).prepareFailView(any());
    }

    @Test
    void execute_MissingTask_PresentsFailWithoutRemovingAnything() {
        interactor.execute(new DeleteTaskInputData(new TaskId(99)));

        verify(presenter).prepareFailView("task not found");
        verify(presenter, never()).prepareSuccessView(any());
        assertThat(dataAccess.getTasks()).hasSize(2);
    }
}