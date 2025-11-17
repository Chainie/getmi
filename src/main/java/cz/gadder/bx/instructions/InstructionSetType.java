package cz.gadder.bx.instructions;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum InstructionSetType {
    B_F_STANDARD_SET,
    MULTI_TAPE_SET;

    public static String getAvailableOptions() {
        return Arrays.stream(InstructionSetType.values()).map(InstructionSetType::name).collect(Collectors.joining(", "));
    }
}
