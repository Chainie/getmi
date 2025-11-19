package cz.gadger.gtmi;

import cz.gadger.gtmi.instructions.InstructionsSetFactory;
import cz.gadger.gtmi.interpreter.InterpreterManifest;
import cz.gadger.gtmi.interpreter.MachineInterpreter;
import cz.gadger.gtmi.interpreter.MemorySectorSize;
import cz.gadger.gtmi.interpreter.Program;
import cz.gadger.gtmi.mappings.ValueMapping;
import cz.gadger.gtmi.mappings.ValueMappingFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static cz.gadger.gtmi.instructions.InstructionsSetFactory.createBFInstructionSet;
import static cz.gadger.gtmi.instructions.InstructionsSetFactory.createMultiTapeInstructionSet;
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
                InstructionsSetFactory.builder()
                        .addInstructionSet(createBFInstructionSet())
                        .addInstructionSet(createMultiTapeInstructionSet())
                        .build(),
                memorySectorSize,
                List.of()
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
                InstructionsSetFactory.builder()
                        .addInstructionSet(createBFInstructionSet())
                        .addInstructionSet(createMultiTapeInstructionSet())
                        .build(),
                memorySectorSize,
                List.of()
        );

        Program program = Program.from(".+.+..▼+++.▼++++.+.+.▲.▼+.▲▲.▼▼+.+.");

        MachineInterpreter interpreter = MachineInterpreter.forProgramAndManifest(program, manifest);
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("Hello, World!");
    }
}
