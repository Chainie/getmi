package cz.gadder.bx.instructions;

import cz.gadder.bx.interpreter.Interpreter;

import java.util.function.Consumer;

public interface Instruction extends Consumer<Interpreter> {

    char getInstructionCode();

    boolean shouldPostIncrementInstructionPointer();
}
