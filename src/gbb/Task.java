package gbb;

import java.util.Collection;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/16/19)
 */
public abstract class Task<T extends State> {

    private Job job;

    /**
     * The task method with returns the viable next states
     * of a given state.
     * @param state current state
     * @return {@link Collection} of states
     */
    public abstract Collection<T> compute(T state);

    /**
     * Returns the {@link Job} associated for the current
     * {@link Task} instance.
     * @return {@link Task} instance.
     */
    public Job getJob() {
        return job;
    }

    protected void setJob(Job job) {
        this.job = job;
    }
}
