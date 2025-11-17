package cz.gadger.gtmi.interpreter;

import cz.gadger.gtmi.cmd.Args;
import cz.gadger.gtmi.instructions.Instruction;
import cz.gadger.gtmi.instructions.InstructionsSetFactory;
import cz.gadger.gtmi.mappings.ValueMapping;
import cz.gadger.gtmi.mappings.ValueMappingFactory;

import java.time.Duration;
import java.util.Map;

/**
 * Interpreter manifest hold the configuration for the interpreter.
 * @param valueMapping - I/O mapping of the characters
 * @param instructionMap - map from instruction codes to the instructions
 * @param memorySectorSize - memory sectors settings
 * @param stepDelay - delay between program instruction code execution
 */
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
                InstructionsSetFactory.createBFInstructionMap(),
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
            return InstructionsSetFactory.createBFInstructionMap();
        }
        return InstructionsSetFactory.forInstructionSetTypes(args.getInstructionSets());
    }

    private static MemorySectorSize initMemorySectorSize(Args args) {
        if(args.isMemFit() && !args.getCharSet().isEmpty()) {
            return MemorySectorSize.create(args.getMinMemorySize(), args.getCharSet().size());
        }
        return MemorySectorSize.create(args.getMinMemorySize(), args.getMaxMemorySize());
    }
}
