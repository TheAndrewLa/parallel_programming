package org.nsu.syspro.parprog.solution;

/**
 * This class contains calling information of method.
 * <br>
 * Calling information includes state of method (interpreting or execution compiled code) and number of calling count.
 * <br>
 * Default state is interpreting, default number of usages is 1
 */
class MethodInfo {

    private ExecutionState state = ExecutionState.INTERPRETING;
    private int usages = 0;

    void incrementUsages() {
        usages++;
    }

    int getUsages() {
        return usages;
    }

    void incrementState() {
        switch (state) {
            case INTERPRETING: {
                state = ExecutionState.L1_COMPILED;
                return;
            }

            case L1_COMPILED:
            case L2_COMPILED: {
                state = ExecutionState.L2_COMPILED;
                return;
            }

            default: {
                throw new RuntimeException("Unknown enum value for MethodState!");
            }
        }
    }

    void setState(ExecutionState state) {
        this.state = state;
    }

    ExecutionState getState() {
        return state;
    }

    /**
     * This enum describes three possible states of method.
     * <br>
     * 1. Method is not already compiled and is needed to be interpreted.
     * <br>
     * 2. Method is compiled with L1 compiler and can be executed.
     * <br>
     * 3. Method is compiled with L2 compiler and can be executed.
     */
    public enum ExecutionState {
        INTERPRETING,
        L1_COMPILED,
        L2_COMPILED,
    }
}
