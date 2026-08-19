package list.load_tasks.use_case;

import java.util.List;
import todo.domain.Task;

/**
 * The output boundary for the Load Tasks Use Case.
 */
public interface LoadTasksOutputBoundary {

    void prepareSuccessView(LoadTasksOutputData outputData);
}