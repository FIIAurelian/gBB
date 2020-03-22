package gbb.exploring;

import gbb.State;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/18/19)
 */
public class BreadthFirstStrategy<T extends State> implements SearchStrategy<T> {
    private Deque<T> queue;

    /**
     * Constructor with no states for
     * {@link BreadthFirstStrategy}.
     */
    public BreadthFirstStrategy() {
        queue = new ConcurrentLinkedDeque<>();
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
        queue.addLast(state);
    }
}
