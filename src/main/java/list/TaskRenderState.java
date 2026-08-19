package list;

import todo.domain.TaskId;

/**
 * The render part of the todo list: everything the frame needs to draw one
 * task row. Presenters build these from domain {@code Task}s; the frame only
 * reads them. It holds no session data and no domain policy.
 */
public record TaskRenderState(TaskId id, String description, boolean completed) {
}