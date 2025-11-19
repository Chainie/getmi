package cz.gadger.gtmi.listeners;

import cz.gadger.gtmi.instructions.Instruction;
import cz.gadger.gtmi.interpreter.Interpreter;

/**
 * Execution listener interface for creating executionListeners for before or after instruction execution.
 */
public interface ExecutionListener {

    default void beforeInstructionExecution(Interpreter interpreter, Instruction instruction) {}
    default void afterInstructionExecution(Interpreter interpreter, Instruction instruction) {}
}
