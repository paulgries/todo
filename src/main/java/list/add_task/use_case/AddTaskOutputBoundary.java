package list.add_task.use_case;

import java.util.List;
import todo.domain.Task;

/**
 * The output boundary for the Add Task Use Case.
 */
public interface AddTaskOutputBoundary {

    void prepareSuccessView(AddTaskOutputData outputData);

    void prepareFailView(String error);
}