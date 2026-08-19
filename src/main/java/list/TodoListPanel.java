package list;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import list.add_task.AddTaskController;
import list.delete_task.DeleteTaskController;
import list.toggle_task.ToggleTaskController;

/**
 * The view for the todo list screen, following the CAWithBuilder pattern: it
 * binds to its {@link TodoListViewModel}, writes the add-field's value into
 * the {@link TodoListState} as it changes, renders the task rows and the
 * open/completed status whenever a presenter fires a property change, and
 * shows transient messages (e.g. an empty description) presenters put in the
 * state.
 */
public final class TodoListPanel extends JPanel implements PropertyChangeListener {

    private final TodoListViewModel viewModel;

    private AddTaskController addTaskController;
    private ToggleTaskController toggleTaskController;
    private DeleteTaskController deleteTaskController;

    private final JTextField inputField = new JTextField(20);
    private final JButton addButton = new JButton("Add");
    private final JPanel tasksPanel = new JPanel(new GridLayout(0, 1));
    private final JLabel statusLabel = new JLabel(" ");

    public TodoListPanel(TodoListViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);
        setLayout(new BorderLayout());

        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputRow.add(inputField);
        inputRow.add(addButton);
        add(inputRow, BorderLayout.NORTH);

        add(new JScrollPane(tasksPanel), BorderLayout.CENTER);

        add(statusLabel, BorderLayout.SOUTH);

        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                syncInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                syncInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                syncInput();
            }
        });
        addButton.addActionListener(e -> onAddClicked());
    }

    public void setAddTaskController(AddTaskController addTaskController) {
        this.addTaskController = addTaskController;
    }

    public void setToggleTaskController(ToggleTaskController toggleTaskController) {
        this.toggleTaskController = toggleTaskController;
    }

    public void setDeleteTaskController(DeleteTaskController deleteTaskController) {
        this.deleteTaskController = deleteTaskController;
    }

    public String getViewName() {
        return viewModel.getViewName();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final TodoListState state = viewModel.getState();
        inputField.setText(state.getInputText());
        renderTasks(state.getTasks());
        renderStatus(state.getTasks());
        if (state.getMessage() != null) {
            String message = state.getMessage();
            state.setMessage(null);
            JOptionPane.showMessageDialog(this, message, "Todo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void syncInput() {
        viewModel.getState().setInputText(inputField.getText());
    }

    private void onAddClicked() {
        addTaskController.execute(viewModel.getState().getInputText());
    }

    private void renderTasks(java.util.List<TaskRenderState> tasks) {
        tasksPanel.removeAll();
        for (TaskRenderState task : tasks) {
            tasksPanel.add(taskRow(task));
        }
        tasksPanel.revalidate();
        tasksPanel.repaint();
    }

    private JPanel taskRow(TaskRenderState task) {
        JPanel row = new JPanel(new BorderLayout());
        JCheckBox checkBox = new JCheckBox(task.description(), task.completed());
        checkBox.addItemListener(e -> toggleTaskController.execute(task.id()));
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteTaskController.execute(task.id()));
        row.add(checkBox, BorderLayout.CENTER);
        row.add(deleteButton, BorderLayout.EAST);
        return row;
    }

    private void renderStatus(java.util.List<TaskRenderState> tasks) {
        long open = tasks.stream().filter(task -> !task.completed()).count();
        long completed = tasks.size() - open;
        statusLabel.setText(open + " open \u00b7 " + completed + " completed");
    }
}