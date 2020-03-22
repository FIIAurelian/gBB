package gbb.exploring;

import gbb.State;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/18/19)
 */
public class DepthFirstStrategy<T extends State> implements SearchStrategy<T> {
    private Deque<T> stack;

    /**
     * Constructor with no states
     * for {@link DepthFirstStrategy}.
     */
    public DepthFirstStrategy() {
        stack = new ConcurrentLinkedDeque<>();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public T poll() {
        return stack.pollFirst();
    }

    @Override
    public void put(T state) throws InterruptedException {
        stack.addFirst(state);
    }
}
