package todo.domain;

/**
 * The identity of a task, mirroring how game domain values are modeled in
 * tictactoe. Value-based, so equality is by id.
 */
public record TaskId(long value) {
}