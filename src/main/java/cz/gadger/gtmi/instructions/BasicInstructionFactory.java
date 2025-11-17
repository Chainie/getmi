package cz.gadger.gtmi.instructions;

import cz.gadger.gtmi.interpreter.Interpreter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * Factory for creating instructions.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BasicInstructionFactory {

    public static Consumer<Interpreter> createConsumer(InstructionType instructionType) {
        return switch (instructionType) {
            case INCREMENT_ACTIVE_MEMORY_INDEX -> inp -> inp.getActiveStateMachine().incrementActiveMemoryIndex();
            case DECREMENT_ACTIVE_MEMORY_INDEX -> inp -> inp.getActiveStateMachine().decrementActiveMemoryIndex();
            case INCREMENT_VALUE_AT_ACTIVE_MEMORY -> inp -> inp.getActiveStateMachine().incrementValueAtActiveMemorySector();
            case DECREMENT_VALUE_AT_ACTIVE_MEMORY -> inp -> inp.getActiveStateMachine().decrementValueAtActiveMemorySector();
            case PRINT_DATA_FROM_ACTIVE_MEMORY -> inp -> inp.getActiveStateMachine().printDataFromActiveMemorySector();
            case READ_INPUT_TO_ACTIVE_MEMORY -> inp -> inp.getActiveStateMachine().readInputToActiveMemorySector();
            case ITERATION_START -> inp -> inp.resolveIterationStart(']', '[');
            case ITERATION_END -> inp -> inp.resolveIterationEnd('[', ']');
            case SWITCH_TO_NEXT_TAPE -> Interpreter::switchToNextStateMachine;
            case SWITCH_TO_PREVIOUS_TAPE -> Interpreter::switchToPreviousStateMachine;
        };
    }

    public static Instruction createForType(InstructionType instructionType) {
        return BaseInstructionImpl.of(instructionType.getDefaultInstructionCode(), createConsumer(instructionType));
    }

    public static Instruction createForTypeWithoutPointerPostIncrement(InstructionType instructionType) {
        return BaseInstructionImpl.of(instructionType.getDefaultInstructionCode(), createConsumer(instructionType), false);
    }
}
