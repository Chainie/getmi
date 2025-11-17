package cz.gadder.bx.interpreter;

public interface Interpreter {

    StateMachine getActiveStateMachine();

    Program getProgram();

    void resolveIterationEnd(char instruction, char matchingInstruction);

    void resolveIterationStart(char instruction, char matchingInstruction);

    void switchToNextStateMachine();

    void switchToPreviousStateMachine();

}
