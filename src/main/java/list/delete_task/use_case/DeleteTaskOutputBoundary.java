package list.delete_task.use_case;

import java.util.List;
import todo.domain.Task;

/**
 * The output boundary for the Delete Task Use Case.
 */
public interface DeleteTaskOutputBoundary {

    void prepareSuccessView(DeleteTaskOutputData outputData);

    void prepareFailView(String error);
}