package gbb.exploring;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/21/19)
 */
public class SearchStrategyFactoryTest {

    @Test
    public void getBreadthFirstSearchStrategyTest() {
        /** GIVEN */
        SearchStrategyFactory<TestState> factory = new SearchStrategyFactory<>();

        /** WHEN */
        SearchStrategy<TestState> strategy = factory.getInstance(SearchStrategyType.BREADTH_FIRST_SEARCH);

        /** THEN */
        assertTrue(strategy instanceof BreadthFirstStrategy);
        assertFalse(strategy instanceof DepthFirstStrategy);
    }

    @Test
    public void getDepthFirstSearchStrategyTest() {
        /** GIVEN */
        SearchStrategyFactory<TestState> factory = new SearchStrategyFactory<>();

        /** WHEN */
        SearchStrategy<TestState> strategy = factory.getInstance(SearchStrategyType.DEPTH_FIRST_SEARCH);

        /** THEN */
        assertTrue(strategy instanceof DepthFirstStrategy);
        assertFalse(strategy instanceof BreadthFirstStrategy);
    }

    @Test
    public void notReturningTheSameInstanceTest() {
        /** GIVEN */
        SearchStrategyFactory<TestState> factory = new SearchStrategyFactory<>();

        /** WHEN */
        SearchStrategy<TestState> firstStrategy = factory.getInstance(SearchStrategyType.DEPTH_FIRST_SEARCH);
        SearchStrategy<TestState> secondStrategy = factory.getInstance(SearchStrategyType.DEPTH_FIRST_SEARCH);

        /** THEN */
        assertNotEquals(firstStrategy, secondStrategy);
    }

    @Test
    public void registerNewStrategyTest() {
        /** GIVEN */
        SearchStrategyFactory<TestState> factory = new SearchStrategyFactory<>();
        String strategyName = "custom";

        /** WHEN */
        factory.registerSearchStrategy(strategyName, DepthFirstStrategy.class);

        /** THEN */
        assertNotNull(factory.getInstance(strategyName));
        assertTrue(factory.getInstance(strategyName) instanceof DepthFirstStrategy);
    }

    @Test (expected = IllegalArgumentException.class)
    public void retrieveNonRegisteredStrategyTest() {
        /** GIVEN */
        SearchStrategyFactory<TestState> factory = new SearchStrategyFactory<>();

        /** WHEN */
        factory.getInstance("custom");
    }

    @Test (expected = IllegalArgumentException.class)
    public void registerStrategyWithoutNameTest() {
        /** GIVEN */
        SearchStrategyFactory<TestState> factory = new SearchStrategyFactory<>();

        /** WHEN */
        factory.registerSearchStrategy(null, DepthFirstStrategy.class);
    }

    @Test (expected = IllegalArgumentException.class)
    public void registerStrategyMultipleTimesTest() {
        /** GIVEN */
        SearchStrategyFactory<TestState> factory = new SearchStrategyFactory<>();
        String strategyName = "custom";

        /** WHEN */
        factory.registerSearchStrategy(strategyName, DepthFirstStrategy.class);
        factory.registerSearchStrategy(strategyName, BreadthFirstStrategy.class);
    }
}
