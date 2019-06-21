package gbb.exploring;

import gbb.State;

/**
 * A collection of {@link State} that should be
 * explored next given a search strategy.
 *
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/18/19)
 */
public interface SearchStrategy<T extends State> {
    /**
     * Returns <code>true</code> if this {@link SearchStrategy}
     * contains no states.
     * @return <code>true</code> if this
     *         {@link SearchStrategy} contains no states,
     *         <code>false</code> otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the number of {@link State} in collection.
     * @return number of {@link State} in collection
     */
    int size();

    /**
     * Retrieves and removes the next {@link State}
     * that should be explored.
     * @return the next {@link State}, or null if
     *         no states are available.
     */
    T poll();

    /**
     * Inserts the specified {@link State} into the
     * current search strategy.
     * @param state the state to add
     * @throws InterruptedException if interrupted while waiting
     */
    void put(T state) throws InterruptedException;
}
