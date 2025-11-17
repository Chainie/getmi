package cz.gadder.bx.interpreter;

import cz.gadder.bx.cmd.Args;
import cz.gadder.bx.instructions.Instruction;
import cz.gadder.bx.instructions.InstructionMapFactory;
import cz.gadder.bx.mappings.ValueMapping;
import cz.gadder.bx.mappings.ValueMappingFactory;

import java.time.Duration;
import java.util.Map;

public record InterpreterManifest(ValueMapping valueMapping,
                                  Map<Character, Instruction> instructionMap,
                                  MemorySectorSize memorySectorSize,
                                  Duration stepDelay) implements StateMachineSettings {

    public static InterpreterManifest create(ValueMapping valueMapping,
                                             Map<Character, Instruction> instructionMap,
                                             MemorySectorSize memorySectorSize,
                                             Duration stepDelay) {
        return new InterpreterManifest(valueMapping, instructionMap, memorySectorSize, stepDelay);
    }

    public static InterpreterManifest create(Map<Character, Instruction> instructionMap) {
        return new InterpreterManifest(ValueMappingFactory.createDefault(), instructionMap, MemorySectorSize.createByteSize(), Duration.ZERO);
    }

    public static InterpreterManifest createDefault() {
        return new InterpreterManifest(ValueMappingFactory.createDefault(),
                InstructionMapFactory.createBFInstructionMap(),
                MemorySectorSize.createByteSize(),
                Duration.ZERO);
    }

    public static InterpreterManifest createFromArgs(Args args) {
        return InterpreterManifest.create(
                loadValueMapping(args),
                constructInstructionMap(args),
                initMemorySectorSize(args),
                Duration.ofMillis(args.getStepDelayMs())
        );
    }

    private static ValueMapping loadValueMapping(Args args) {
        if (args.getCharSet().isEmpty()) {
            return ValueMappingFactory.createDefault();
        }
        return ValueMappingFactory.createNormalizedSetMappingFromList(args.getCharSet());
    }

    private static Map<Character, Instruction> constructInstructionMap(Args args) {
        if (args.getInstructionSets().isEmpty()) {
            return InstructionMapFactory.createBFInstructionMap();
        }
        return InstructionMapFactory.forInstructionSetTypes(args.getInstructionSets());
    }

    private static MemorySectorSize initMemorySectorSize(Args args) {
        if(args.isMemFit() && !args.getCharSet().isEmpty()) {
            return MemorySectorSize.create(args.getMinMemorySize(), args.getCharSet().size());
        }
        return MemorySectorSize.create(args.getMinMemorySize(), args.getMaxMemorySize());
    }
}
