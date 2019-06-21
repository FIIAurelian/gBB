package gbb.exploring;

import gbb.State;

/**
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/20/19)
 */
public class TestState implements State {
    private int number;

    public TestState(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }
}
