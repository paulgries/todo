package list.delete_task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import list.delete_task.use_case.DeleteTaskInputBoundary;
import list.delete_task.use_case.DeleteTaskInputData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import todo.domain.TaskId;

class DeleteTaskControllerTest {

    @Test
    void execute_BuildsInputDataWithTaskId() {
        DeleteTaskInputBoundary useCase = Mockito.mock(DeleteTaskInputBoundary.class);
        DeleteTaskController controller = new DeleteTaskController(useCase);

        controller.execute(new TaskId(3));

        ArgumentCaptor<DeleteTaskInputData> captor =
                ArgumentCaptor.forClass(DeleteTaskInputData.class);
        verify(useCase).execute(captor.capture());
        assertThat(captor.getValue().id()).isEqualTo(new TaskId(3));
    }
}