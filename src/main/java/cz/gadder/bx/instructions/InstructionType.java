package cz.gadder.bx.instructions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InstructionType {
    INCREMENT_ACTIVE_MEMORY_INDEX('>'),
    DECREMENT_ACTIVE_MEMORY_INDEX('<'),
    INCREMENT_VALUE_AT_ACTIVE_MEMORY('+'),
    DECREMENT_VALUE_AT_ACTIVE_MEMORY('-'),
    PRINT_DATA_FROM_ACTIVE_MEMORY('.'),
    READ_INPUT_TO_ACTIVE_MEMORY(','),
    ITERATION_START('['),
    ITERATION_END(']');

    private final char defaultInstructionCode;

}
