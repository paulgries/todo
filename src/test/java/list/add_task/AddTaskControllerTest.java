package list.add_task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import list.add_task.use_case.AddTaskInputBoundary;
import list.add_task.use_case.AddTaskInputData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class AddTaskControllerTest {

    @Test
    void execute_BuildsInputDataWithDescription() {
        AddTaskInputBoundary useCase = Mockito.mock(AddTaskInputBoundary.class);
        AddTaskController controller = new AddTaskController(useCase);

        controller.execute("buy milk");

        ArgumentCaptor<AddTaskInputData> captor =
                ArgumentCaptor.forClass(AddTaskInputData.class);
        verify(useCase).execute(captor.capture());
        assertThat(captor.getValue().description()).isEqualTo("buy milk");
    }
}