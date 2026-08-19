package list;

import framework.ViewModel;
import todo.TodoDataAccess;

/**
 * The ViewModel for the todo list screen. All the list use cases render
 * through this single view model because the list is one screen. The tasks
 * themselves live in the application layer ({@link TodoDataAccess}), not in
 * the presentation.
 */
public class TodoListViewModel extends ViewModel<TodoListState> {

    public TodoListViewModel() {
        super("todo list");
        setState(new TodoListState());
    }
}