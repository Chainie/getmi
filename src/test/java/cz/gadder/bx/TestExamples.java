package cz.gadder.bx;

import cz.gadder.bx.instructions.InstructionMapFactory;
import cz.gadder.bx.interpreters.MachineInterpreter;
import cz.gadder.bx.interpreters.MemorySectorSize;
import cz.gadder.bx.mappings.ValueMapping;
import cz.gadder.bx.mappings.ValueMappingFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TestExamples {

    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setIn(standardIn);
    }

    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Hello World! works")
    void testHelloWorld() {
        String code = """
                ++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.
                >---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.
                """;
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from(code));
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("Hello World!");
    }

    @Test
    @DisplayName("Quine works")
    void testQuine() {
        String code = """
                -->+++>+>+>+>+++++>++>++>->+++>++>+>>>>>>>>>>>>>>>>->++++>>>>->+++>+++>+++>+++>+
                ++>+++>+>+>>>->->>++++>+>>>>->>++++>+>+>>->->++>++>++>++++>+>++>->++>++++>+>+>++
                >++>->->++>++>++++>+>+>>>>>->>->>++++>++>++>++++>>>>>->>>>>+++>->++++>->->->+++>
                >>+>+>+++>+>++++>>+++>->>>>>->>>++++>++>++>+>+++>->++++>>->->+++>+>+++>+>++++>>>
                +++>->++++>>->->++>++++>++>++++>>++[-[->>+[>]++[<]<]>>+[>]<--[++>++++>]+[<]<<++]
                >>>[>]++++>++++[--[+>+>++++<<[-->>--<<[->-<[--->>+<<[+>+++<[+>>++<<]]]]]]>+++[>+
                ++++++++++++++<-]>--.<<<]
                """;
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from(code));
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo(code.trim().replace("\n", ""));
    }

    @Test
    @DisplayName("Reduced Value Mapping and memory size works")
    void reducedMappingWorksTest(){
        List<Character> words = List.of('H', 'e', 'l', 'o', ',' , ' ', 'W', 'r', 'd', '!');
        ValueMapping valueMapping = ValueMappingFactory.createNormalizedSetMappingFromList(words);

        MemorySectorSize memorySectorSize = MemorySectorSize.createZeroTo(words.size());
        InterpreterManifest manifest = InterpreterManifest.create(
                valueMapping,
                InstructionMapFactory.createBFInstructionMap(),
                memorySectorSize
        );

        Program program = Program.from(".+.+..+.+.+.+.---.++++.-----.++++++.+.");

        MachineInterpreter interpreter = MachineInterpreter.forProgramAndManifest(program, manifest);
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("Hello, World!");

        outputStreamCaptor.reset();

        program = Program.from(".+.+..+.+.+.+.---.++++.++++++.-----.+.");

        interpreter = MachineInterpreter.forProgramAndManifest(program, manifest);
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("Hello, World!");
    }

    @Test
    @DisplayName("Super Reduced Value Mapping Output works")
    void superReducedMappingOutputTest() {
        List<Character> words = List.of('.', '-');
        ValueMapping valueMapping = ValueMappingFactory.createNormalizedSetMappingFromList(words);

        InterpreterManifest manifest = InterpreterManifest.create(
                valueMapping,
                InstructionMapFactory.createBFInstructionMap(),
                MemorySectorSize.createByteSize()
        );

        Program program = Program.from("++++.");

        MachineInterpreter interpreter = MachineInterpreter.forProgramAndManifest(program, manifest);
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("?");
    }

    @Test
    @DisplayName("Super Reduced Value Mapping Unknown Input works")
    void superReducedMappingUnknownInputTest() throws IOException {
        List<Character> words = List.of('c', 'd');
        ValueMapping valueMapping = ValueMappingFactory.createNormalizedSetMappingFromList(words);

        InterpreterManifest manifest = InterpreterManifest.create(
                valueMapping,
                InstructionMapFactory.createBFInstructionMap(),
                MemorySectorSize.createByteSize()
        );
        String input = ("a");
        InputStream testInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(testInput);

        Program program = Program.from(",.");

        MachineInterpreter interpreter = MachineInterpreter.forProgramAndManifest(program, manifest);
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("?");
        testInput.reset();
    }
}
