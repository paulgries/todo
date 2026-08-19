package list.toggle_task.use_case;

import java.util.List;
import todo.domain.Task;

/**
 * The output boundary for the Toggle Task Use Case.
 */
public interface ToggleTaskOutputBoundary {

    void prepareSuccessView(ToggleTaskOutputData outputData);

    void prepareFailView(String error);
}