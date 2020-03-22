package gbb;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/17/19)
 */
final class RunnableTask implements Callable<Void> {
    private Task task;
    private State state;
    private Consumer<Collection<? extends State>> callback;

    RunnableTask(State state, Task task, Consumer<Collection<? extends State>> callback) {
        this.state = state;
        this.task = task;
        this.callback = callback;
    }

    @Override
    public Void call() {
        Collection<State> states = task.compute(state);
        callback.accept(states);
        return null;
    }
}
