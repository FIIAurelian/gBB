package gbb;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/16/19)
 */
public class Configuration {
    private int numberOfExecutors;

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
     * Implementation of the Builder creational pattern.
     */
    public static final class Builder {
        private int numberOfExecutors;

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
         * Returns the corresponding {@link Configuration} instance
         * for the current {@link Builder} instance.
         * @return {@link Configuration}
         */
        public Configuration build() {
            Configuration conf = new Configuration();
            conf.numberOfExecutors = this.numberOfExecutors;

            return conf;
        }
    }
}
