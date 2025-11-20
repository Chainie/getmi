package cz.gadger.gtmi.instructions;

import cz.gadger.gtmi.interpreter.Interpreter;

import java.util.function.Consumer;

public interface Instruction extends Consumer<Interpreter> {

    char getInstructionCode();

    default boolean shouldPostIncrementInstructionPointer() {
        return true;
    }
}
