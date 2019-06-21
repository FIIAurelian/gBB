package gbb;

import gbb.exceptions.TaskException;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/17/19)
 */
final class RunnableTask implements Callable<Collection<State>> {
    private Task task;
    private State state;

    RunnableTask(State state, Class<? extends Task> taskClass, Job job) {
        if (taskClass == null) {
            throw new IllegalArgumentException("The Task class should not be null.");
        }

        try {
            this.state = state;
            this.task = taskClass.newInstance();
            this.task.setJob(job);
        } catch (InstantiationException | IllegalAccessException exception) {
            throw new TaskException(String.format("Failed to instantiate the Task: %s.", taskClass.getName()),
                    exception);
        }
    }

    @Override
    public Collection<State> call() throws Exception {
        return task.compute(state);
    }
}
