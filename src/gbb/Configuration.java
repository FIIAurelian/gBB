package gbb;

import gbb.exploring.SearchStrategyType;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/16/19)
 */
public class Configuration {

    private int numberOfExecutors;
    private SearchStrategyType searchStrategyType;

    /**
     * Default constructor.
     */
    private Configuration() {
    }

    /**
     * Deep copy constructor.
     * @param configuration to be copied
     */
    public Configuration(Configuration configuration) {
        this.numberOfExecutors = configuration.numberOfExecutors;
    }

    /**
     * Returns number of executors that will handle a job
     * (i.e. maximum number of tasks that will run in
     * parallel at a given point in time)
     * @return number of executors
     */
    public int getNumberOfExecutors() {
        return this.numberOfExecutors;
    }

    /**
     * Returns the search strategy type set.
     * @return {@link SearchStrategyType}
     */
    public SearchStrategyType getSearchStrategyType() {
        return searchStrategyType;
    }

    /**
     * Implementation of the Builder creational pattern.
     */
    public static final class Builder {
        private int numberOfExecutors = 1;
        private SearchStrategyType searchStrategyType = SearchStrategyType.DEPTH_FIRST_SEARCH;

        /**
         * Return {@link Builder} instance with the number of executors
         * field set.
         * @param numberOfExecutors value for the number of executors field
         * @return {@link Builder} instance
         */
        public Builder withNumberOfExecutors(int numberOfExecutors) {
            this.numberOfExecutors = numberOfExecutors;
            return this;
        }

        /**
         * Return {@link Builder} instance with the
         * searching strategy type set.
         * @param searchStrategyType strategy type for exploring states
         * @return {@link Builder} instance
         */
        public Builder withSearchStrategy(SearchStrategyType searchStrategyType) {
            this.searchStrategyType = searchStrategyType;
            return this;
        }

        /**
         * Returns the corresponding {@link Configuration} instance
         * for the current {@link Builder} instance.
         * @return {@link Configuration}
         */
        public Configuration build() {
            Configuration conf = new Configuration();
            conf.numberOfExecutors = this.numberOfExecutors;
            conf.searchStrategyType = this.searchStrategyType;

            return conf;
        }
    }
}
