package gbb.exploring;

/**
 * Enum for storing the available methods
 * for gbb.exploring the tree of candidate solutions.
 *
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/18/19)
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
