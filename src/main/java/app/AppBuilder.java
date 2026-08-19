package app;

import app.MainFrame;
import data_access.InMemoryTodoDataAccess;
import framework.ViewManager;
import framework.ViewManagerModel;
import list.TodoListPanel;
import list.TodoListViewModel;
import list.add_task.AddTaskController;
import list.add_task.AddTaskPresenter;
import list.add_task.use_case.AddTaskInteractor;
import list.delete_task.DeleteTaskController;
import list.delete_task.DeleteTaskPresenter;
import list.delete_task.use_case.DeleteTaskInteractor;
import list.load_tasks.LoadTasksController;
import list.load_tasks.LoadTasksPresenter;
import list.load_tasks.use_case.LoadTasksInteractor;
import list.toggle_task.ToggleTaskController;
import list.toggle_task.ToggleTaskPresenter;
import list.toggle_task.use_case.ToggleTaskInteractor;
import todo.TodoDataAccess;
import todo.domain.CommonTaskFactory;
import todo.domain.TaskFactory;
import javax.swing.JFrame;

/**
 * Wires the whole application with one fluent method per frame and per use
 * case, mirroring the AppBuilder in CAWithBuilder: the builder creates the
 * view's ViewModel, registers the view on the frame's card panel under its
 * view name, and hands each controller to its view. Build time runs the
 * load-tasks use case so the list view starts rendered.
 */
public class AppBuilder {

    private final TodoListViewModel todoListViewModel = new TodoListViewModel();
    private final TaskFactory taskFactory = new CommonTaskFactory();
    private final TodoDataAccess todoDataAccess = new InMemoryTodoDataAccess();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private MainFrame frame;
    private TodoListPanel todoListPanel;
    private LoadTasksController loadTasksController;

    public AppBuilder addTodoView() {
        frame = new MainFrame();
        ViewManager viewManager =
                new ViewManager(frame.getCardPanel(), frame.getCardLayout(), viewManagerModel);
        todoListPanel = new TodoListPanel(todoListViewModel);
        frame.addView(todoListPanel, todoListPanel.getViewName());
        return this;
    }

    public AppBuilder addAddTaskUseCase() {
        final AddTaskPresenter addTaskPresenter = new AddTaskPresenter(todoListViewModel);
        final AddTaskController addTaskController = new AddTaskController(
                new AddTaskInteractor(addTaskPresenter, taskFactory, todoDataAccess));
        todoListPanel.setAddTaskController(addTaskController);
        return this;
    }

    public AppBuilder addToggleTaskUseCase() {
        final ToggleTaskPresenter toggleTaskPresenter = new ToggleTaskPresenter(todoListViewModel);
        final ToggleTaskController toggleTaskController = new ToggleTaskController(
                new ToggleTaskInteractor(toggleTaskPresenter, todoDataAccess));
        todoListPanel.setToggleTaskController(toggleTaskController);
        return this;
    }

    public AppBuilder addDeleteTaskUseCase() {
        final DeleteTaskPresenter deleteTaskPresenter = new DeleteTaskPresenter(todoListViewModel);
        final DeleteTaskController deleteTaskController = new DeleteTaskController(
                new DeleteTaskInteractor(deleteTaskPresenter, todoDataAccess));
        todoListPanel.setDeleteTaskController(deleteTaskController);
        return this;
    }

    public AppBuilder addLoadTasksUseCase() {
        final LoadTasksPresenter loadTasksPresenter = new LoadTasksPresenter(todoListViewModel);
        loadTasksController = new LoadTasksController(
                new LoadTasksInteractor(loadTasksPresenter, todoDataAccess));
        return this;
    }

    public JFrame build() {
        if (loadTasksController != null) {
            loadTasksController.execute();
        }
        viewManagerModel.setState(todoListViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
        return frame;
    }
}