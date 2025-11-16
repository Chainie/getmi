package cz.gadder.bx.interpreters;

import cz.gadder.bx.Program;

public interface Interpreter {

    StateMachine getActiveStateMachine();

    Program getProgram();

    void resolveIterationEnd(char instruction, char matchingInstruction);

    void resolveIterationStart(char instruction, char matchingInstruction);

    void switchToNextStateMachine();

    void switchToPreviousStateMachine();

}
