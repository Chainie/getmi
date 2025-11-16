package cz.gadder.bx;

import cz.gadder.bx.instructions.Instruction;
import cz.gadder.bx.instructions.InstructionMapFactory;
import cz.gadder.bx.interpreters.MemorySectorSize;
import cz.gadder.bx.interpreters.StateMachineSettings;
import cz.gadder.bx.mappings.ValueMapping;
import cz.gadder.bx.mappings.ValueMappingFactory;

import java.util.Map;

public record InterpreterManifest(ValueMapping valueMapping,
                                  Map<Character, Instruction> instructionMap,
                                  MemorySectorSize memorySectorSize) implements StateMachineSettings {

    public static InterpreterManifest create(ValueMapping valueMapping,
                                             Map<Character, Instruction> instructionMap,
                                             MemorySectorSize memorySectorSize) {
        return new InterpreterManifest(valueMapping, instructionMap, memorySectorSize);
    }

    public static InterpreterManifest create(Map<Character, Instruction> instructionMap) {
        return new InterpreterManifest(ValueMappingFactory.createDefault(), instructionMap, MemorySectorSize.createByteSize());
    }

    public static InterpreterManifest createDefault() {
        return new InterpreterManifest(ValueMappingFactory.createDefault(), InstructionMapFactory.createBFInstructionMap(), MemorySectorSize.createByteSize());
    }
}
