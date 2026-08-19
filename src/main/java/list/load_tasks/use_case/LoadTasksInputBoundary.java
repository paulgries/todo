package list.load_tasks.use_case;

/**
 * The input boundary for the Load Tasks Use Case.
 */
public interface LoadTasksInputBoundary {

    void execute(LoadTasksInputData inputData);
}