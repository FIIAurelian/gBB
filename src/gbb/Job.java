package gbb;

import gbb.exploring.SearchStrategy;
import gbb.exploring.SearchStrategyFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/16/19)
 */
public class Job {
    private Configuration configuration;
    private Class<? extends Task> task;
    private Map<String, Object[]> registeredArrays;
    private State initialState;

    /**
     * Constructor with configuration.
     * @param configuration configuration for the job
     */
    public Job(Configuration configuration) {
        this();
        setConfiguration(configuration);
    }

    /**
     * Default constructor.
     */
    private Job() {
        registeredArrays = new HashMap<>();
    }

    /**
     * Method for get a copy of the {@link Configuration}.
     * The idea behind returning a copy is to guarantee
     * the immutability.
     * @return copy of the {@link Configuration}
     */
    public Configuration getConfiguration() {
        return new Configuration(configuration);
    }

    /**
     * Setter for the {@link Configuration} field.
     * The given object is not used directly to ensure immutability.
     * @param configuration to be set
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = new Configuration(configuration);
    }

    /**
     * Setter for the {@link Task} implementation.
     * Should expose a constructor because it will be
     * instantiate through reflection.
     * @param task class for {@link Task} implementation
     */
    public void setTask(Class<? extends Task> task) {
        this.task = task;
    }

    /**
     * Method for registering a resource.
     * @param name key for the resource
     * @param arr array structure
     */
    public void registerArray(String name, Object[] arr) {
        if (registeredArrays.containsKey(name)) {
            throw new IllegalArgumentException("An array with the same name is already registered.");
        }

        registeredArrays.put(name, new Object[arr.length]);
    }

    /**
     * Setter for the initial state.
     * @param initialState initial state instance
     */
    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    /**
     * Entry point for starting the computation
     * for the current {@link Job}.
     */
    public void start() {
        SearchStrategyFactory<State> searchStrategyFactory = new SearchStrategyFactory<>();
        SearchStrategy<State> states = searchStrategyFactory.getInstance(configuration.getSearchStrategyType());

        ExecutorService slaves = Executors.newFixedThreadPool(configuration.getNumberOfExecutors());

        try {
            states.put(this.initialState);
        } catch(InterruptedException exception) {
            throw new RuntimeException(exception);
        }

        while (!states.isEmpty()) {
            State state = states.poll();
            Future<Collection<State>> nextStates = slaves.submit(new RunnableTask(state, this.task, this));
            try {
                for (State nextState : nextStates.get()) {
                    states.put(nextState);
                }
            } catch (InterruptedException | ExecutionException exception) {
                throw new RuntimeException(exception);
            }
        }

        slaves.shutdown();
    }

    /**
     * Method for querying an registered resource.
     * @param name key for the resource
     * @param position array position in the resource
     * @return value at the given position
     */
    public Object queryArray(String name, int position) {
        Object[] arr = getArray(name);
        Object result = null;
        synchronized (arr) {
            result = arr[position];
        }

        return result;
    }

    /**
     * Method for updating an registered resource.
     * @param name key for the resource
     * @param value new value
     * @param position array position in the resource
     */
    public void updateArray(String name, Object value, int position) {
        Object[] arr = getArray(name);

        synchronized (arr) {
            arr[position] = value;
        }
    }

    private Object[] getArray(String name) {
        if (!registeredArrays.containsKey(name)) {
            throw new IllegalArgumentException(String.format("No array found for the name: %s.", name));
        }

        return registeredArrays.get(name);
    }

    /**
     * Implementation of the Builder creational pattern.
     */
    public static final class Builder {
        private Configuration configuration;
        private Class<? extends Task> task;
        private Map<String, Object[]> registeredArrays = new HashMap<>();
        private State initialState;

        /**
         * Return {@link Builder} instance with the {@link Configuration}
         * field set.
         * @param configuration value for the {@link Configuration} field
         * @return {@link Builder} instance
         */
        public Builder withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        /**
         * Returns {@link Builder} instance with the {@link Task} class set.
         * @param task class for {@link Task} implementation
         * @return {@link Builder} instance
         */
        public Builder withTask(Class<? extends Task> task) {
            this.task = task;
            return this;
        }

        /**
         * Returns {@link Builder} instance with a new resource registered.
         * @param name key for the resource
         * @param arr array structure
         * @return
         */
        public Builder registerArray(String name, Object[] arr) {
            if (this.registeredArrays.containsKey(name)) {
                throw new IllegalArgumentException("An array with the same name is already registered.");
            }

            registeredArrays.put(name, arr);
            return this;
        }

        /**
         * Returns {@link Builder} instance with the initial state set.
         * @param initialState value for the initial state
         * @return {@link Builder} instance
         */
        public Builder withInitialState(State initialState) {
            this.initialState = initialState;
            return this;
        }

        /**
         * Returns the corresponding {@link Job} instance for the
         * current {@link Builder} instance.
         * @return {@link Job} instance
         */
        public Job build() {
            Job job = new Job();
            job.setConfiguration(this.configuration);
            job.setTask(this.task);
            this.registeredArrays.forEach(job::registerArray);
            job.setInitialState(this.initialState);

            return job;
        }
    }
}
