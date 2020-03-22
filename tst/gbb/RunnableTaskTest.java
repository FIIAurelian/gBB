package gbb;

import gbb.exceptions.TaskException;
import org.junit.Test;

import java.util.Collection;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/21/19)
 */
public class RunnableTaskTest {

    @Test (expected = IllegalArgumentException.class)
    public void createRunnableTaskWithNullTaskTest() {
        RunnableTask runnableTask = new RunnableTask(null, null, null);
    }

    @Test (expected = TaskException.class)
    public void createRunnableTaskWithPrivateTaskTest() {
        RunnableTask runnableTask = new RunnableTask(null, new DummyTask(), null);
    }

    private static final class DummyTask extends Task<State> {

        private DummyTask() {
        }

        @Override
        public Collection<State> compute(State state) {
            return null;
        }
    }
}
