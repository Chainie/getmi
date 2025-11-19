package cz.gadger.gtmi.interpreter;

import cz.gadger.gtmi.cmd.Args;
import cz.gadger.gtmi.instructions.Instruction;
import cz.gadger.gtmi.instructions.InstructionsSetFactory;
import cz.gadger.gtmi.listeners.ExecutionListener;
import cz.gadger.gtmi.listeners.ExecutionPauseListener;
import cz.gadger.gtmi.mappings.ValueMapping;
import cz.gadger.gtmi.mappings.ValueMappingFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interpreter manifest hold the configuration for the interpreter.
 *
 * @param valueMapping       - I/O mapping of the characters
 * @param instructionMap     - map from instruction codes to the instructions
 * @param memorySectorSize   - memory sectors settings
 * @param executionListeners - list of execution listeners
 */
public record InterpreterManifest(ValueMapping valueMapping,
                                  Map<Character, Instruction> instructionMap,
                                  MemorySectorSize memorySectorSize,
                                  List<ExecutionListener> executionListeners) implements StateMachineSettings {

    public static InterpreterManifest create(ValueMapping valueMapping,
                                             Map<Character, Instruction> instructionMap,
                                             MemorySectorSize memorySectorSize,
                                             List<ExecutionListener> executionListeners) {
        return new InterpreterManifest(valueMapping, instructionMap, memorySectorSize, executionListeners);
    }

    public static InterpreterManifest create(Map<Character, Instruction> instructionMap) {
        return new InterpreterManifest(ValueMappingFactory.createDefault(), instructionMap, MemorySectorSize.createByteSize(), List.of());
    }

    public static InterpreterManifest createDefault() {
        return new InterpreterManifest(ValueMappingFactory.createDefault(),
                InstructionsSetFactory.createBFInstructionMap(),
                MemorySectorSize.createByteSize(),
                List.of());
    }

    public static InterpreterManifest createFromArgs(Args args) {
        return InterpreterManifest.create(
                loadValueMapping(args),
                constructInstructionMap(args),
                initMemorySectorSize(args),
                constructExecutionListeners(args)
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
        if (args.isMemFit() && !args.getCharSet().isEmpty()) {
            return MemorySectorSize.create(args.getMinMemorySize(), args.getCharSet().size());
        }
        return MemorySectorSize.create(args.getMinMemorySize(), args.getMaxMemorySize());
    }

    private static List<ExecutionListener> constructExecutionListeners(Args args) {
        if (args.getStepDelayMs() > 0) {
            List<Character> instructionCodesToListen = args.getDelayInstructionCodes().isEmpty() ? List.of('.') :args.getDelayInstructionCodes();
            return List.of(ExecutionPauseListener.of(Duration.ofMillis(args.getStepDelayMs()), Set.copyOf(instructionCodesToListen)));
        }

        return List.of();
    }
}
