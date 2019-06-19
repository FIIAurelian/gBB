package gbb.exploring;

/**
 * Enum for storing the available methods
 * for exploring the tree of candidate solutions.
 *
 * @author <a href="mailto:hreapca@amazon.com">Aurelian Hreapca</a> (created on 6/19/19)
 */
public enum SearchStrategyType {
    /**
     * Explore the candidate solutions
     * in a level-by-level fashion.
     */
    BREADTH_FIRST_SEARCH,

    /**
     * Explore the candidate solutions
     * in a top-down fashion.
     */
    DEPTH_FIRST_SEARCH;
}
