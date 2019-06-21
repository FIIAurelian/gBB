package gbb;

import gbb.Configuration;
import gbb.Job;
import gbb.State;
import gbb.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/21/19)
 */
public class JobTest {

    @Test
    public void backtrackingTest() {
        /** GIVEN */
        int n = 5;
        int expectedSolution = 32; //2 ** 5
        Job job = new Job.Builder()
                .withConfiguration(new Configuration.Builder().build())
                .withInitialState(SubsetState.getInitialState())
                .withTask(SubsetTask.class)
                .registerArray("n", new Object[1])
                .registerArray("solution", new Object[1])
                .build();

        /** WHEN */
        job.updateArray("n", n, 0);
        job.updateArray("solution", 0, 0);
        job.start();
        int solution = (int) job.queryArray("solution", 0);

        /** THEN */
        assertEquals(expectedSolution, solution);
    }

    private static final class SubsetState implements State {
        private List<Integer> positions;

        public SubsetState(List<Integer> positions) {
            this.positions = positions;

        }

        public List<Integer> getPositions() {
            return this.positions;
        }

        public static SubsetState getInitialState() {
            return new SubsetState(new ArrayList<>());
        }

        public String toString() {
            return positions.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
        }
    }

    public static final class SubsetTask extends Task<SubsetState> {

        @Override
        public Collection<SubsetState> compute(SubsetState state) {
            getJob().updateArray("solution", (int) getJob().queryArray("solution", 0) + 1, 0);

            List<SubsetState> nextStates = new ArrayList<>();
            List<Integer> positions = state.getPositions();
            int lower = 1 + (positions.isEmpty() ? 0 : positions.get(positions.size() - 1));
            int n = (int) getJob().queryArray("n", 0);

            for (int i = lower; i <= n; i++) {
                List<Integer> nextPositions = new ArrayList<>(positions);
                nextPositions.add(i);
                nextStates.add(new SubsetState(nextPositions));
            }

            return nextStates;
        }
    }
}
