package cz.gadder.bx;

import cz.gadder.bx.instructions.Instruction;
import cz.gadder.bx.interpreters.MemorySectorSize;
import cz.gadder.bx.interpreters.StateMachineSettings;
import cz.gadder.bx.mappings.ValueMapping;

import java.util.Map;

public record InterpreterManifest(ValueMapping valueMapping,
                                  Map<Character, Instruction> instructionMap,
                                  MemorySectorSize memorySectorSize) implements StateMachineSettings {

    public static InterpreterManifest create(ValueMapping valueMapping,
                                             Map<Character, Instruction> instructionMap,
                                             MemorySectorSize memorySectorSize) {
        return new InterpreterManifest(valueMapping, instructionMap, memorySectorSize);
    }
}
