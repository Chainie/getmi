package cz.gadder.bx.instructions;

import cz.gadder.bx.interpreters.Interpreter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BasicInstructionFactory {

    public static Consumer<Interpreter> createConsumer(InstructionType instructionType) {
        return switch (instructionType) {
            case INCREMENT_ACTIVE_MEMORY_INDEX -> inp -> inp.getStateMachine().incrementActiveMemoryIndex();
            case DECREMENT_ACTIVE_MEMORY_INDEX -> inp -> inp.getStateMachine().decrementActiveMemoryIndex();
            case INCREMENT_VALUE_AT_ACTIVE_MEMORY -> inp -> inp.getStateMachine().incrementValueAtActiveMemorySector();
            case DECREMENT_VALUE_AT_ACTIVE_MEMORY -> inp -> inp.getStateMachine().decrementValueAtActiveMemorySector();
            case PRINT_DATA_FROM_ACTIVE_MEMORY -> inp -> inp.getStateMachine().printDataFromActiveMemorySector();
            case READ_INPUT_TO_ACTIVE_MEMORY -> inp -> inp.getStateMachine().readInputToActiveMemorySector();
            case ITERATION_START -> inp -> inp.resolveIterationStart(']', '[');
            case ITERATION_END -> inp -> inp.resolveIterationEnd('[', ']');
        };
    }

    public static Instruction createForType(InstructionType instructionType) {
        return BaseInstructionImpl.of(instructionType.getDefaultInstructionCode(), createConsumer(instructionType));
    }

    public static Instruction createForType(InstructionType instructionType, boolean shouldPostIncrementInstructionPointer) {
        return BaseInstructionImpl.of(instructionType.getDefaultInstructionCode(), createConsumer(instructionType), shouldPostIncrementInstructionPointer);
    }
}
