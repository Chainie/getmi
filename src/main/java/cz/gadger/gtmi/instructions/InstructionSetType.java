package cz.gadger.gtmi.instructions;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Names for existing instruction sets to be created in a single package.
 */
public enum InstructionSetType {
    B_F_STANDARD_SET,
    MULTI_TAPE_SET;

    public static String getAvailableOptions() {
        return Arrays.stream(InstructionSetType.values()).map(InstructionSetType::name).collect(Collectors.joining(", "));
    }
}
