package list.add_task.use_case;

/**
 * The input boundary for the Add Task Use Case.
 */
public interface AddTaskInputBoundary {

    void execute(AddTaskInputData inputData);
}