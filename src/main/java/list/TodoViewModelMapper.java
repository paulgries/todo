package list;

import java.util.List;
import todo.domain.Task;

/**
 * Maps domain {@link Task}s to the {@link TaskRenderState} beans the list
 * view renders from, mirroring the GameViewModelMapper in tictactoe.
 */
public final class TodoViewModelMapper {

    private TodoViewModelMapper() {
    }

    public static TaskRenderState toRenderState(Task task) {
        return new TaskRenderState(task.id(), task.description(), task.completed());
    }

    public static List<TaskRenderState> toRenderStates(List<Task> tasks) {
        return tasks.stream().map(TodoViewModelMapper::toRenderState).toList();
    }
}