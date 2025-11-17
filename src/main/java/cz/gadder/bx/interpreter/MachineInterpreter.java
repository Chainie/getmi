package cz.gadder.bx.interpreter;

import cz.gadder.bx.instructions.Instruction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jspecify.annotations.NonNull;

import java.util.Map;

import static cz.gadder.bx.instructions.InstructionMapFactory.EMPTY_INSTRUCTION;

@Getter(AccessLevel.PUBLIC)
public class MachineInterpreter implements Interpreter {

    @Getter(AccessLevel.PACKAGE)
    private final Map<Character, Instruction> instructionMap;

    private StateMachine activeStateMachine;
    private final Program program;

    private final InterpreterManifest manifest;

    private MachineInterpreter(Program program) {
        this(program, InterpreterManifest.createDefault());
    }

    private MachineInterpreter(Program program,
                               InterpreterManifest manifest) {
        this.program = program;
        this.manifest = manifest;
        this.activeStateMachine = StateMachine.of(manifest);
        this.instructionMap = manifest.instructionMap();
    }

    @NonNull
    public static MachineInterpreter forProgram(@NonNull Program program) {
        return new MachineInterpreter(program);
    }

    @NonNull
    public static MachineInterpreter forProgramAndManifest(@NonNull Program program,
                                                           @NonNull InterpreterManifest manifest) {
        return new MachineInterpreter(program, manifest);
    }

    @SneakyThrows
    public int runProgram() {
        while (program.isNotFinished()) {
            executeNextInstruction();
            if (manifest.stepDelay().isPositive()) {
               Thread.sleep(manifest.stepDelay());
            }
        }
        return 0;
    }

    private void executeNextInstruction() {
        Instruction instruction = instructionMap.getOrDefault(program.getActiveInstructionCode(), EMPTY_INSTRUCTION);
        instruction.accept(this);
        if (instruction.shouldPostIncrementInstructionPointer()) {
            program.incrementInstructionPointer();
        }
    }

    public void resolveIterationEnd(char instruction, char matchingInstruction) {
        if (!activeStateMachine.isDataAtActiveMemorySectorEqualToZero()) {
            program.iterateToPreviousInstructionCode(instruction, matchingInstruction);
        } else {
            program.incrementInstructionPointer();
        }
    }

    public void resolveIterationStart(char instruction, char matchingInstruction) {
        if (activeStateMachine.isDataAtActiveMemorySectorEqualToZero()) {
            program.iterateToNextInstructionCode(instruction, matchingInstruction);
        }
    }

    @Override
    public void switchToNextStateMachine() {
        if (activeStateMachine.getNextStateMachine() != null) {
            activeStateMachine = activeStateMachine.getNextStateMachine();
        } else {
            StateMachine nextStateMachine = StateMachine.of(manifest);
            nextStateMachine.setPreviousStateMachine(activeStateMachine);
            activeStateMachine.setNextStateMachine(nextStateMachine);
            activeStateMachine = nextStateMachine;
        }
    }

    @Override
    public void switchToPreviousStateMachine() {
        if (activeStateMachine.getPreviousStateMachine() != null) {
            activeStateMachine = activeStateMachine.getPreviousStateMachine();
        } else {
            StateMachine previousStateMachine = StateMachine.of(manifest);
            previousStateMachine.setNextStateMachine(activeStateMachine);
            activeStateMachine.setPreviousStateMachine(previousStateMachine);
            activeStateMachine = previousStateMachine;
        }
    }
}
