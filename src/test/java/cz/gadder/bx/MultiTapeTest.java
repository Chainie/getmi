package cz.gadder.bx;

import cz.gadder.bx.instructions.InstructionMapFactory;
import cz.gadder.bx.interpreter.InterpreterManifest;
import cz.gadder.bx.interpreter.MachineInterpreter;
import cz.gadder.bx.interpreter.MemorySectorSize;
import cz.gadder.bx.interpreter.Program;
import cz.gadder.bx.mappings.ValueMapping;
import cz.gadder.bx.mappings.ValueMappingFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.List;

import static cz.gadder.bx.instructions.InstructionMapFactory.createBFInstructionSet;
import static cz.gadder.bx.instructions.InstructionMapFactory.createMultiTapeInstructionSet;
import static org.assertj.core.api.Assertions.assertThat;

class MultiTapeTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Multi tape Hello, world! works!")
    void testMultiTape() {

        List<Character> words = List.of('H', 'e', 'l', 'o', ',' , ' ', 'W', 'r', 'd', '!');
        ValueMapping valueMapping = ValueMappingFactory.createNormalizedSetMappingFromList(words);

        MemorySectorSize memorySectorSize = MemorySectorSize.createZeroTo(words.size());
        InterpreterManifest manifest = InterpreterManifest.create(
                valueMapping,
                InstructionMapFactory.builder()
                        .addInstructionSet(createBFInstructionSet())
                        .addInstructionSet(createMultiTapeInstructionSet())
                        .build(),
                memorySectorSize,
                Duration.ZERO
        );

        Program program = Program.from(".+.+..▲+++.▲++++.+.+.▼.▲+.▼▼.▲▲+.+.");

        MachineInterpreter interpreter = MachineInterpreter.forProgramAndManifest(program, manifest);
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("Hello, World!");
    }

    @Test
    @DisplayName("Reverted Multi tape Hello, world! works!")
    void testMultiTapeReverted() {

        List<Character> words = List.of('H', 'e', 'l', 'o', ',' , ' ', 'W', 'r', 'd', '!');
        ValueMapping valueMapping = ValueMappingFactory.createNormalizedSetMappingFromList(words);

        MemorySectorSize memorySectorSize = MemorySectorSize.createZeroTo(words.size());
        InterpreterManifest manifest = InterpreterManifest.create(
                valueMapping,
                InstructionMapFactory.builder()
                        .addInstructionSet(createBFInstructionSet())
                        .addInstructionSet(createMultiTapeInstructionSet())
                        .build(),
                memorySectorSize,
                Duration.ZERO
        );

        Program program = Program.from(".+.+..▼+++.▼++++.+.+.▲.▼+.▲▲.▼▼+.+.");

        MachineInterpreter interpreter = MachineInterpreter.forProgramAndManifest(program, manifest);
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("Hello, World!");
    }
}
