package cz.gadder.bx.instructions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cz.gadder.bx.instructions.BasicInstructionFactory.createForType;
import static cz.gadder.bx.instructions.BasicInstructionFactory.createForTypeWithoutPointerPostIncrement;
import static cz.gadder.bx.instructions.InstructionType.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstructionMapFactory {

    public static final Instruction EMPTY_INSTRUCTION = BaseInstructionImpl.of((char) -1, inp -> {
    });

    private final Stream.Builder<Instruction> builder = Stream.<Instruction>builder();

    public static InstructionMapFactory builder() {
        return new InstructionMapFactory();
    }

    public InstructionMapFactory addInstructionSet(Set<Instruction> instructionSet) {
        instructionSet.forEach(builder::add);
        return this;
    }

    public Map<Character, Instruction> build() {
        return builder.build().collect(Collectors.toMap(Instruction::getInstructionCode, Function.identity()));
    }

    public static Map<Character, Instruction> createBFInstructionMap() {
        return createBFInstructionSet()
                .stream()
                .collect(Collectors.toMap(Instruction::getInstructionCode, Function.identity()));
    }

    public static Set<Instruction> createBFInstructionSet() {


        return Set.of(
                createForType(INCREMENT_ACTIVE_MEMORY_INDEX),
                createForType(DECREMENT_ACTIVE_MEMORY_INDEX),
                createForType(INCREMENT_VALUE_AT_ACTIVE_MEMORY),
                createForType(DECREMENT_VALUE_AT_ACTIVE_MEMORY),
                createForType(PRINT_DATA_FROM_ACTIVE_MEMORY),
                createForType(READ_INPUT_TO_ACTIVE_MEMORY),
                createForType(ITERATION_START),
                createForTypeWithoutPointerPostIncrement(ITERATION_END)
        );
    }

    public static Set<Instruction> createMultiTapeInstructionSet() {
        return Set.of(
                createForType(SWITCH_TO_NEXT_TAPE),
                createForType(SWITCH_TO_PREVIOUS_TAPE)
        );
    }


}
