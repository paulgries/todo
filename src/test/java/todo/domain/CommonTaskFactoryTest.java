package todo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CommonTaskFactoryTest {

    @Test
    void create_SuccessiveCalls_AssignsIncreasingIdsAndOpenTasks() {
        CommonTaskFactory factory = new CommonTaskFactory();

        Task first = factory.create("buy milk");
        Task second = factory.create("write tests");

        assertThat(first.id()).isEqualTo(new TaskId(1));
        assertThat(second.id()).isEqualTo(new TaskId(2));
        assertThat(first.completed()).isFalse();
        assertThat(second.completed()).isFalse();
    }
}