package gbb.exploring;

import gbb.State;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/18/19)
 */
public class BreadthFirstStrategy<T extends State> implements SearchStrategy<T> {
    private BlockingDeque<T> queue;

    /**
     * Constructor with no states for
     * {@link BreadthFirstStrategy}.
     */
    public BreadthFirstStrategy() {
        queue = new LinkedBlockingDeque<>();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public T poll() {
        return queue.pollFirst();
    }

    @Override
    public void put(T state) throws InterruptedException {
        queue.putLast(state);
    }
}
