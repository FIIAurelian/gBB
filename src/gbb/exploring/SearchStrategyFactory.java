package gbb.exploring;

import gbb.State;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/18/19)
 */
public class SearchStrategyFactory<T extends State> {

    private Map<String, Class<? extends SearchStrategy>> searchStrategyByType;

    public SearchStrategyFactory() {
        searchStrategyByType = new HashMap<>();

        searchStrategyByType.put(SearchStrategyType.BREADTH_FIRST_SEARCH.name(), BreadthFirstStrategy.class);
        searchStrategyByType.put(SearchStrategyType.DEPTH_FIRST_SEARCH.name(), DepthFirstStrategy.class);
    }

    /**
     * Method for retrieving the {@link SearchStrategy}
     * associated with a name value.
     * @param name value associated with the desired {@link SearchStrategy}
     * @return {@link SearchStrategy} for the given name value.
     * @throws IllegalArgumentException when fails to instantiate {@link SearchStrategy}
     *                                  or on null values.
     */
    public SearchStrategy<T> getInstance(String name) {
        if (name == null) {
            throw new IllegalArgumentException("No search strategy type has been provided.");
        }

        try {
            return searchStrategyByType.get(name).newInstance();
        } catch (InstantiationException | IllegalAccessException exception) {
            throw new IllegalArgumentException(String.format("The search strategy for the" +
                    " name: %s can not be instantiate.", name));
        }
    }

    /**
     * Method for retrieving the {@link SearchStrategy}
     * associated with a search strategy type.
     * @param searchStrategyType type associated with the desired {@link SearchStrategy}
     * @return {@link SearchStrategy} for the given type value.
     * @throws IllegalArgumentException when fails to instantiate {@link SearchStrategy}
     *                                  or on null values.
     */
    public SearchStrategy<T> getInstance(SearchStrategyType searchStrategyType) {
        if (searchStrategyType == null) {
            throw new IllegalArgumentException("No search strategy type has been provided.");
        }

        return getInstance(searchStrategyType.name());
    }

    /**
     * Method for registering a new {@link SearchStrategy} with a given name.
     * @param name given name
     * @param searchStrategy {@link SearchStrategy} to register
     * @throws IllegalArgumentException when the name value is already registered.
     */
    public void registerSearchStrategy(String name, Class<? extends SearchStrategy<T>> searchStrategy) {
        if (searchStrategyByType.containsKey(name)) {
            throw new IllegalArgumentException(String.format("A search strategy" +
                    " for name: %s is already registered.", name));
        }

        searchStrategyByType.put(name, searchStrategy);
    }
}
