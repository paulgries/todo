package list;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import todo.domain.Task;
import todo.domain.TaskId;

class TodoViewModelMapperTest {

    @Test
    void toRenderStates_MapsEachTaskPreservingData() {
        List<Task> tasks = List.of(
                new Task(new TaskId(1), "buy milk", false),
                new Task(new TaskId(2), "write tests", true));

        List<TaskRenderState> render = TodoViewModelMapper.toRenderStates(tasks);

        assertThat(render)
                .extracting(TaskRenderState::id)
                .containsExactly(new TaskId(1), new TaskId(2));
        assertThat(render.get(0).description()).isEqualTo("buy milk");
        assertThat(render.get(0).completed()).isFalse();
        assertThat(render.get(1).completed()).isTrue();
    }
}