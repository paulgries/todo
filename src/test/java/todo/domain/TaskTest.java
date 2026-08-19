package todo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void constructor_BlankDescription_Throws() {
        assertThatThrownBy(() -> new Task(new TaskId(1), "   ", false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("description must not be blank");
    }

    @Test
    void constructor_SurroundingWhitespace_TrimsDescription() {
        Task task = new Task(new TaskId(1), "  buy milk  ", false);

        assertThat(task.description()).isEqualTo("buy milk");
    }

    @Test
    void toggled_OpenTask_ReturnsCompletedTaskWithSameIdentity() {
        Task task = new Task(new TaskId(7), "write tests", false);

        Task toggled = task.toggled();

        assertThat(toggled.id()).isEqualTo(task.id());
        assertThat(toggled.description()).isEqualTo(task.description());
        assertThat(toggled.completed()).isTrue();
        assertThat(task.completed()).isFalse();
    }

    @Test
    void toggled_CompletedTask_ReturnsOpenTask() {
        Task task = new Task(new TaskId(7), "write tests", true);

        assertThat(task.toggled().completed()).isFalse();
    }
}