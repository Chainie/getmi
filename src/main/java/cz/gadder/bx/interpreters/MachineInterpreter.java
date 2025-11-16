package cz.gadder.bx.interpreters;

import cz.gadder.bx.InterpreterManifest;
import cz.gadder.bx.Program;
import cz.gadder.bx.instructions.Instruction;
import cz.gadder.bx.instructions.InstructionMapFactory;
import lombok.AccessLevel;
import lombok.Getter;
import org.jspecify.annotations.NonNull;

import java.util.Map;

import static cz.gadder.bx.instructions.InstructionMapFactory.EMPTY_INSTRUCTION;

@Getter(AccessLevel.PUBLIC)
public class MachineInterpreter implements Interpreter {

    @Getter(AccessLevel.PACKAGE)
    private final Map<Character, Instruction> instructionMap;

    private final StateMachine stateMachine;
    private final Program program;

    private MachineInterpreter(Program program) {
        this.program = program;
        this.stateMachine = StateMachine.of();
        this.instructionMap = InstructionMapFactory.createBFInstructionMap();
    }

    private MachineInterpreter(Program program,
                               InterpreterManifest manifest) {
        this.program = program;
        this.stateMachine = StateMachine.of(manifest);
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

    public int runProgram() {
        while (program.isNotFinished()) {
            executeNextInstruction();
        }
        return 0;
    }

    private void executeNextInstruction() {
        Instruction instruction = instructionMap.getOrDefault(program.getActiveInstructionCode(), EMPTY_INSTRUCTION);
        instruction.accept(this);
        if(instruction.shouldPostIncrementInstructionPointer()) {
            program.incrementInstructionPointer();
        }
    }

    public void resolveIterationEnd(char instruction, char matchingInstruction) {
        if (!stateMachine.isDataAtActiveMemorySectorEqualToZero()) {
            program.iterateToPreviousInstructionCode(instruction, matchingInstruction);
        } else {
            program.incrementInstructionPointer();
        }
    }

    public void resolveIterationStart(char instruction, char matchingInstruction) {
        if (stateMachine.isDataAtActiveMemorySectorEqualToZero()) {
            program.iterateToNextInstructionCode(instruction, matchingInstruction);
        }
    }
}
