package gbb;

import gbb.Configuration;
import gbb.exploring.SearchStrategyType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/21/19)
 */
public class ConfigurationTest {

    @Test
    public void defaultConfigurationTest() {
        /** GIVEN */
        Configuration configuration = new Configuration.Builder().build();

        /** THEN */
        assertEquals(1, configuration.getNumberOfExecutors());
        assertEquals(SearchStrategyType.DEPTH_FIRST_SEARCH, configuration.getSearchStrategyType());
    }

    @Test
    public void customConfigurationTest() {
        /** GIVEN */
        int numberOfExecutors = 4;
        SearchStrategyType searchStrategyType = SearchStrategyType.BREADTH_FIRST_SEARCH;
        Configuration configuration = new Configuration.Builder()
                .withNumberOfExecutors(numberOfExecutors)
                .withSearchStrategy(searchStrategyType)
                .build();

        /** THEN */
        assertEquals(numberOfExecutors, configuration.getNumberOfExecutors());
        assertEquals(searchStrategyType, configuration.getSearchStrategyType());
    }

    @Test
    public void deepCopyConstructorTest() {
        /** GIVEN */
        int numberOfExecutors = 4;
        SearchStrategyType searchStrategyType = SearchStrategyType.BREADTH_FIRST_SEARCH;
        Configuration configuration = new Configuration.Builder()
                .withNumberOfExecutors(numberOfExecutors)
                .withSearchStrategy(searchStrategyType)
                .build();

        /** WHEN */
        Configuration copyConfiguration = new Configuration(configuration);

        /** THEN */
        assertNotEquals(configuration, copyConfiguration);
        assertEquals(numberOfExecutors, copyConfiguration.getNumberOfExecutors());
        assertEquals(searchStrategyType, copyConfiguration.getSearchStrategyType());
    }
}
