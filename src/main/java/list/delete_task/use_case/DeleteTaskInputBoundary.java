package list.delete_task.use_case;

/**
 * The input boundary for the Delete Task Use Case.
 */
public interface DeleteTaskInputBoundary {

    void execute(DeleteTaskInputData inputData);
}