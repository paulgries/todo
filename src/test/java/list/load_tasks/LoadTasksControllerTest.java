package list.load_tasks;

import static org.mockito.Mockito.verify;

import list.load_tasks.use_case.LoadTasksInputBoundary;
import list.load_tasks.use_case.LoadTasksInputData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LoadTasksControllerTest {

    @Test
    void execute_RunsUseCaseWithEmptyInput() {
        LoadTasksInputBoundary useCase = Mockito.mock(LoadTasksInputBoundary.class);
        LoadTasksController controller = new LoadTasksController(useCase);

        controller.execute();

        verify(useCase).execute(new LoadTasksInputData());
    }
}