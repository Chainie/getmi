package cz.gadger.gtmi.listeners;

import cz.gadger.gtmi.instructions.Instruction;
import cz.gadger.gtmi.interpreter.Interpreter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.Duration;
import java.util.Set;

@RequiredArgsConstructor(staticName = "of")
public class ExecutionPauseListener implements ExecutionListener {

    private final Duration sleepDuration;

    private final Set<Character> instructionCodes;

    @Override
    @SneakyThrows
    public void afterInstructionExecution(Interpreter interpreter, Instruction instruction) {
        if (instructionCodes.contains(instruction.getInstructionCode())) {
            Thread.sleep(sleepDuration);
        }
    }
}
