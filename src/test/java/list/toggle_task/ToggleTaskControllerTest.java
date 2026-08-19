package list.toggle_task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import list.toggle_task.use_case.ToggleTaskInputBoundary;
import list.toggle_task.use_case.ToggleTaskInputData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import todo.domain.TaskId;

class ToggleTaskControllerTest {

    @Test
    void execute_BuildsInputDataWithTaskId() {
        ToggleTaskInputBoundary useCase = Mockito.mock(ToggleTaskInputBoundary.class);
        ToggleTaskController controller = new ToggleTaskController(useCase);

        controller.execute(new TaskId(3));

        ArgumentCaptor<ToggleTaskInputData> captor =
                ArgumentCaptor.forClass(ToggleTaskInputData.class);
        verify(useCase).execute(captor.capture());
        assertThat(captor.getValue().id()).isEqualTo(new TaskId(3));
    }
}