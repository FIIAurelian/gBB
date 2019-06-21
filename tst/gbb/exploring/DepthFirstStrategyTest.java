package gbb.exploring;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/21/19)
 */
public class DepthFirstStrategyTest {
    @Test
    public void dfsOrderTest() throws InterruptedException {
        /** GIVEN */
        List<Integer> expectedOrder = Arrays.asList(1, 2, 3);
        List<Integer> actualOrder = new ArrayList<>();
        DepthFirstStrategy<TestState> states = new DepthFirstStrategy<>();

        for (int i = expectedOrder.size() - 1; i >= 0; i--) {
            states.put(new TestState(expectedOrder.get(i)));
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
        DepthFirstStrategy<TestState> states = new DepthFirstStrategy<>();

        /** WHEN & THEN */
        assertTrue(states.isEmpty());
    }

    @Test
    public void nonEmptyCollectionTest() throws InterruptedException {
        /** GIVEN */
        List<Integer> elements = Arrays.asList(2, 5, 7, 11);
        DepthFirstStrategy<TestState> states = new DepthFirstStrategy<>();

        for (int x : elements) {
            states.put(new TestState(x));
        }

        /** WHEN & THEN */
        assertFalse(states.isEmpty());
        assertEquals(elements.size(), states.size());
    }
}
