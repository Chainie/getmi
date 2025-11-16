package cz.gadder.bx.instructions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstructionMapFactory {

    public static final Instruction EMPTY_INSTRUCTION = BaseInstructionImpl.of((char) -1, inp -> {});

    public static Map<Character, Instruction> createBFInstructionMap() {
        return Set.of(
                BaseInstructionImpl.of('>', inp -> inp.getStateMachine().incrementActiveMemoryIndex()),
                BaseInstructionImpl.of('<', inp -> inp.getStateMachine().decrementActiveMemoryIndex()),
                BaseInstructionImpl.of('+', inp -> inp.getStateMachine().incrementValueAtActiveMemorySector()),
                BaseInstructionImpl.of('-', inp -> inp.getStateMachine().decrementValueAtActiveMemorySector()),
                BaseInstructionImpl.of('.', inp -> inp.getStateMachine().printDataFromActiveMemorySector()),
                BaseInstructionImpl.of(',', inp -> inp.getStateMachine().readInputToActiveMemorySector()),
                BaseInstructionImpl.of('[', inp -> inp.resolveIterationStart(']', '[')),
                BaseInstructionImpl.of(']', inp -> inp.resolveIterationEnd('[', ']'), false)
        ).stream()
         .collect(Collectors.toMap(BaseInstructionImpl::getInstructionCode, Function.identity()));
    }
}
