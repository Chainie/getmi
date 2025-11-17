package cz.gadder.bx.instructions;

import cz.gadder.bx.interpreter.Interpreter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseInstructionImpl implements Instruction {
    private final char instructionCode;
    private final Consumer<Interpreter> instructionExecution;

    @Getter(AccessLevel.PRIVATE)
    private final boolean shouldPostIncrementInstructionPointer;

    public static BaseInstructionImpl of(char instructionCode, Consumer<Interpreter> instructionExecution) {
        return of(instructionCode, instructionExecution, true);
    }

    public static BaseInstructionImpl of(char instructionCode, Consumer<Interpreter> instructionExecution, boolean shouldPostIncrementInstructionPointer) {
        return new BaseInstructionImpl(instructionCode, instructionExecution, shouldPostIncrementInstructionPointer);
    }

    @Override
    public void accept(Interpreter interpreter) {
        instructionExecution.accept(interpreter);
    }

    @Override
    public boolean shouldPostIncrementInstructionPointer() {
        return shouldPostIncrementInstructionPointer;
    }
}
