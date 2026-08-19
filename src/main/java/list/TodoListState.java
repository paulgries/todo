package list;

import java.util.ArrayList;
import java.util.List;

/**
 * The state of the todo list screen: the add-field's text as the player
 * types it, the rendered tasks, and transient messages (e.g. an empty
 * description) that the view displays. Written by the view's widgets and by
 * presenters; the add-task controller reads the input text from it.
 */
public class TodoListState {

    private String inputText = "";
    private List<TaskRenderState> tasks = new ArrayList<>();
    private String message;

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public List<TaskRenderState> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskRenderState> tasks) {
        this.tasks = tasks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}