package gbb;

import gbb.exceptions.StateException;
import gbb.exceptions.TaskException;
import gbb.exploring.SearchStrategy;
import gbb.exploring.SearchStrategyFactory;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/16/19)
 */
public class Job {
    private Configuration configuration;
    private Class<? extends Task> task;
    private Map<String, Object[]> registeredArrays;
    private Map<String, Object[]> registeredLocks;
    private State initialState;

    /**
     * Constructor with configuration.
     *
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
        registeredLocks = new HashMap<>();
    }

    /**
     * Method for get a copy of the {@link Configuration}.
     *
     * The idea behind returning a copy is to guarantee
     * the immutability.
     * @return copy of the {@link Configuration}
     */
    public Configuration getConfiguration() {
        return new Configuration(configuration);
    }

    /**
     * Setter for the {@link Configuration} field.
     *
     * The given object is not used directly to ensure immutability.
     * @param configuration to be set
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = new Configuration(configuration);
    }

    /**
     * Setter for the {@link Task} implementation.
     *
     * Should expose a constructor because it will be
     * instantiate through reflection.
     * @param task class for {@link Task} implementation
     */
    public void setTask(Class<? extends Task> task) {
        this.task = task;
    }

    /**
     * Method for registering a resource array.
     *
     * @param name key for the resource
     * @param arr array structure
     */
    public <T> void registerArray(String name, T[] arr) {
        if (registeredArrays.containsKey(name)) {
            throw new IllegalArgumentException("An array with the same name is already registered.");
        }

        registeredArrays.put(name, arr.clone());
        registeredLocks.put(name, createLockArray(arr.length));
    }

    /**
     * Setter for the initial state.
     *
     * @param initialState initial state instance
     */
    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    /**
     * Entry point for starting the computation
     * for the current {@link Job}.
     *
     * @throws StateException on issues with adding or retrieving a {@link State} to be explored.
     */
    public void start() {
        Task taskInstance = getTaskInstance();
        SearchStrategyFactory<State> searchStrategyFactory = new SearchStrategyFactory<>();
        Deque<Future<Void>> futureStates = new ArrayDeque<>();
        SearchStrategy<State> states = searchStrategyFactory.getInstance(configuration.getSearchStrategyType());

        ExecutorService workers = Executors.newFixedThreadPool(configuration.getNumberOfExecutors());

        try {
            states.put(this.initialState);
        } catch(InterruptedException exception) {
            throw new StateException("Failed to add a next state.", exception);
        }

        while (!(states.isEmpty() && futureStates.isEmpty())) {
            while (!states.isEmpty()) {
                State state = states.poll();
                Future<Void> future = workers.submit(
                        new RunnableTask(state, taskInstance, nextStates -> addStates(states, nextStates)));
                futureStates.addLast(future);
            }

            while (!futureStates.isEmpty() && futureStates.peekFirst().isDone()) {
                futureStates.pollFirst();
            }
        }

        workers.shutdown();
    }

    /**
     * Method for querying an registered resource.
     *
     * @param name key for the resource
     * @param position array position in the resource
     * @return value at the given position
     */
    public <T> T queryArray(String name, int position) {
        T[] arr = getArray(name);
        return arr[position];
    }

    /**
     * Method for updating an registered resource.
     *
     * @param name key for the resource
     * @param value new value
     * @param position array position in the resource
     */
    public <T> void updateArray(String name, T value, int position) {
        T[] arr = getArray(name);

        synchronized (registeredLocks.get(name)[position]) {
            arr[position] = value;
        }
    }

    /**
     * Method for applying a function on
     * a given position in a registered resource.
     *
     * @param name key for the resource
     * @param function to be applied
     * @param position array position in the resource
     */
    public void applyOnArray(String name, Function function, int position) {
        Object[] arr = getArray(name);

        synchronized (registeredLocks.get(name)[position]) {
            arr[position] = function.apply(arr[position]);
        }
    }

    private void addStates(SearchStrategy<State> states, Collection<? extends State> nextStates) {
        for (State nextState : nextStates) {
            try {
                states.put(nextState);
            } catch (InterruptedException interruptedException) {
                throw new StateException("Failed to add a next state.", interruptedException);
            }
        }
    }

    private <T> T[] getArray(String name) {
        if (!registeredArrays.containsKey(name)) {
            throw new IllegalArgumentException(String.format("No array found for the name: %s.", name));
        }

        return (T[]) registeredArrays.get(name);
    }

    private Object[] createLockArray(int length) {
        Object[] lock = new Object[length];
        for (int i = 0; i < lock.length; i++) {
            lock[i] = new Object();
        }
        return lock;
    }

    private Task getTaskInstance() {
        try {
            Task taskInstance = task.newInstance();
            taskInstance.setJob(this);
            return taskInstance;
        } catch (InstantiationException | IllegalAccessException exception) {
            throw new TaskException(String.format("Failed to instantiate the Task: %s.", task.getName()),
                    exception);
        }
    }

    /**
     * Implementation of the Builder creational pattern.
     */
    public static final class Builder {
        private Configuration configuration = new Configuration.Builder().build();
        private Class<? extends Task> task;
        private Map<String, Object[]> registeredArrays = new HashMap<>();
        private State initialState;

        /**
         * Return {@link Builder} instance with the {@link Configuration}
         * field set.
         *
         * @param configuration value for the {@link Configuration} field
         * @return {@link Builder} instance
         */
        public Builder withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        /**
         * Returns {@link Builder} instance with the {@link Task} class set.
         *
         * @param task class for {@link Task} implementation
         * @return {@link Builder} instance
         */
        public Builder withTask(Class<? extends Task> task) {
            this.task = task;
            return this;
        }

        /**
         * Returns {@link Builder} instance with a new resource registered.
         *
         * @param name key for the resource
         * @param arr array structure
         * @return
         */
        public Builder registerArray(String name, Object[] arr) {
            if (this.registeredArrays.containsKey(name)) {
                throw new IllegalArgumentException("An array with the same name is already registered.");
            }

            registeredArrays.put(name, arr.clone());
            return this;
        }

        /**
         * Returns {@link Builder} instance with the initial state set.
         *
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
         *
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
