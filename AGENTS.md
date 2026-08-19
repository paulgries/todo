# AGENTS.md

Project conventions and decisions for AI-assisted work. This repo was
scaffolded from the template distilled in tictactoe: the workflow sections
(Commits, Branching & PRs, AI-usage tracking) are kept close to verbatim,
and the repo-specific details below are filled in for the todo project.

## Commits

- Use **Conventional Commits**: `feat`, `fix`, `refactor`, `build`, `test`,
  `chore`, `deps`, `docs`.
- Concise subject; lowercase, no trailing period.
- Body explains the "why" when it isn't obvious.
- **Show the commit message to the user for approval before every commit.**
  Stage the intended files first, then present the message (subject + body)
  and wait for explicit approval; commit only after the user approves.

## Branching & PRs

- Branch off `main` for each piece of work. Branches may be **stacked**
  (branch off the previous branch) when several PRs will merge to `main`
  sequentially.
- Push to the user's fork; open PRs against upstream with `gh`:
  `gh pr create --repo <upstream> --base main --head <fork>:<branch>`.
- **Only submit a PR when the user explicitly requests it.** Never open a PR
  automatically (e.g. after finishing a piece of work or a commit).
- Open a PR to `main`, **Rebase and merge** to keep history linear.
- The user reviews and approves/merges the PR themselves.
- Branches are **kept** (not deleted) as `[teaching/archive artifacts]`.
- Use `gh` for PRs, issues, and checks.

## Architecture

- **Clean Architecture**, package-by-capability: layers live *inside*
  capabilities rather than as top-level layers.
- Each use case gets a boundary set under its `use_case` package:
  `InputBoundary`, `InputData`, `Interactor`, `OutputBoundary`, `OutputData`.
  Interactors are `void` and receive the `OutputBoundary` in their
  constructor; the `OutputBoundary` is implemented by the capability's
  **Presenter**; the `InputBoundary` is held by the capability's thin
  **Controller**, which builds the `InputData` from view primitives.
- View-model pattern: one `ViewModel<T>` per view; beans are named
  `XxxState`, view models `XxxViewModel`, and views bind to their view
  model (register as a PropertyChangeListener and render from
  `evt.getNewValue()`). Presenters update the state bean and fire one
  property change. Domain/session data lives behind an application-layer
  boundary (e.g. a `XxxSessionDataAccess` implemented by a
  `data_access/InMemoryXxx`), not on the state beans, which stay dumb.
- Navigation is presenter-driven: a `framework/ViewManager` +
  `ViewManagerModel` (`extends ViewModel<String>`) switches the card
  layout; presenters navigate by setting the view name on success.
- A cross-cutting concern that only serves one screen belongs inside that
  screen's view, not in dedicated classes.

### Example layout

```text
todo/                  (application-layer boundary: TodoDataAccess)
todo/domain/           (shared domain model: Task, TaskId, TaskFactory,
                        CommonTaskFactory)
list/                  (todo list screen: view, state, view model, mapper)
list/[use_case]/       (controller/presenter, e.g. add_task, toggle_task,
                        delete_task, load_tasks)
list/[use_case]/use_case (InputBoundary, InputData, Interactor,
                          OutputBoundary, OutputData)
data_access/           (concrete implementations of boundaries:
                        InMemoryTodoDataAccess)
app/                   (composition root / entry point: Main, AppBuilder,
                        MainFrame)
framework/             (generic reusable code: ViewModel, ViewManagerModel,
                        ViewManager)
```

The list is a single screen, so all four use cases (add, toggle, delete,
load) render through the one `TodoListViewModel`/`TodoListState`. The tasks
themselves live in the application-layer `TodoDataAccess` (implemented by
`data_access/InMemoryTodoDataAccess`, like CAWithBuilder's
`InMemoryUserDataAccessObject`), not on the state bean. The add-task use
case receives the `TaskFactory` in its constructor, as the CAWithBuilder
interactors receive their `UserFactory`. Failures (blank description, task
not found) route through the output boundary to a transient message in the
state; successes just re-render the list.

## Testing

- **JUnit 5 + Mockito** (`mockito-junit-jupiter`).
- Build the class under test in `@BeforeEach`, after mocks are injected.
- Use real entities/implementations where possible; mock only boundaries
  (DAOs, presenters).
- Assert on the real effect (e.g. captured saved user), not just method calls.
- Boundary/interactor tests verify **wiring** (presenter called / never
  called, what was passed) without re-asserting domain mechanics covered by
  domain tests; drop tests that fully duplicate lower-layer coverage (one
  fail path suffices when cases share the same translation).
- Extract repeated fixtures into a shared test helper (`testutil`) instead
  of duplicating setup sequences in each test class.
- Run `mvn clean test` — incremental compilation can report false positives.
  `mvn` output is the source of truth; ignore stale editor/LSP diagnostics
  on in-progress branches.
- Test names: `Method_Condition_Expectation` style.

## Naming conventions

- **Types**: `PascalCase` (`SignupInteractor`).
- **Methods/fields**: `camelCase`.
- **Constants**: `SCREAMING_SNAKE_CASE`.
- **Packages**: all lowercase; capability-based, no underscores/camelCase.
- **Branches**: `kebab-case`, short and descriptive (`add-conversation-log`).
- **Commits**: Conventional Commit types (see above).

## Environment

- macOS, zsh shell.
- **BSD `sed`** — does not support GNU `\b` word boundaries; use `[^...]`
  classes or alternate tools.

## AI-usage tracking

- Commit the live session transcript (`conversations/<session>.md`) as the
  durable record of AI-assisted work; git history is the per-commit record.
- To resume context, use opencode's `/sessions` (or `/compact`), **not** by
  loading the transcript file back in as context.

## Build

- Maven project (`pom.xml`), Java 17. Verify with `mvn clean test`.