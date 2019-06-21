package gbb.exploring;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/20/19)
 */
public class BreadthFirstStrategyTest {

    @Test
    public void bfsOrderTest() throws InterruptedException {
        /** GIVEN */
        List<Integer> expectedOrder = Arrays.asList(1, 2, 3);
        List<Integer> actualOrder = new ArrayList<>();
        BreadthFirstStrategy<TestState> states = new BreadthFirstStrategy<>();

        for (int x : expectedOrder) {
            states.put(new TestState(x));
        }

        /** WHEN */
        while (!states.isEmpty()) {
            actualOrder.add(states.poll().getNumber());
        }

        assertEquals(expectedOrder, actualOrder);
        assertTrue(states.isEmpty());
    }

    @Test
    public void emptyCollectionTest() {
        /** GIVEN */
        BreadthFirstStrategy<TestState> states = new BreadthFirstStrategy<>();

        /** WHEN & THEN */
        assertTrue(states.isEmpty());
    }

    @Test
    public void nonEmptyCollectionTest() throws InterruptedException {
        /** GIVEN */
        List<Integer> elements = Arrays.asList(2, 5, 7, 11);
        BreadthFirstStrategy<TestState> states = new BreadthFirstStrategy<>();

        for (int x : elements) {
            states.put(new TestState(x));
        }

        /** WHEN & THEN */
        assertFalse(states.isEmpty());
        assertEquals(elements.size(), states.size());
    }
}
