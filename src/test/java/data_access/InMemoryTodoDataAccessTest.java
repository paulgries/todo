package data_access;

import static org.assertj.core.api.Assertions.assertThat;

import static list.testutil.TodoFixtures.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.domain.Task;
import todo.domain.TaskId;

class InMemoryTodoDataAccessTest {

    private InMemoryTodoDataAccess dataAccess;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryTodoDataAccess();
    }

    @Test
    void addTask_AddsInOrder() {
        dataAccess.addTask(task(1, "buy milk", false));
        dataAccess.addTask(task(2, "write tests", true));

        assertThat(dataAccess.getTasks())
                .extracting(Task::id)
                .containsExactly(new TaskId(1), new TaskId(2));
    }

    @Test
    void getTasks_ReturnsANewSnapshotEachCall() {
        dataAccess.addTask(task(1, "buy milk", false));

        assertThat(dataAccess.getTasks()).isNotSameAs(dataAccess.getTasks());
        assertThat(dataAccess.getTasks()).hasSize(1);
    }

    @Test
    void updateTask_ReplacesTaskById() {
        dataAccess.addTask(task(1, "buy milk", false));

        dataAccess.updateTask(task(1, "buy milk", true));

        assertThat(dataAccess.getTasks().get(0).completed()).isTrue();
        assertThat(dataAccess.getTasks()).hasSize(1);
    }

    @Test
    void deleteTask_RemovesTaskById() {
        dataAccess.addTask(task(1, "buy milk", false));
        dataAccess.addTask(task(2, "write tests", false));

        dataAccess.deleteTask(new TaskId(1));

        assertThat(dataAccess.getTasks())
                .extracting(Task::id)
                .containsExactly(new TaskId(2));
    }

    @Test
    void findById_PresentTask_ReturnsIt() {
        dataAccess.addTask(task(5, "buy milk", false));

        assertThat(dataAccess.findById(new TaskId(5)))
                .contains(task(5, "buy milk", false));
    }

    @Test
    void findById_MissingTask_ReturnsEmpty() {
        assertThat(dataAccess.findById(new TaskId(9))).isEmpty();
    }
}