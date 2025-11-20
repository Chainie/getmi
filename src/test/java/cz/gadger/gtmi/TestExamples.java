package cz.gadger.gtmi;

import cz.gadger.gtmi.instructions.Instruction;
import cz.gadger.gtmi.instructions.InstructionsSetFactory;
import cz.gadger.gtmi.interpreter.*;
import cz.gadger.gtmi.mappings.ValueMapping;
import cz.gadger.gtmi.mappings.ValueMappingFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

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
                ++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.
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
                InstructionsSetFactory.createBFInstructionMap(),
                memorySectorSize,
                List.of()
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
                InstructionsSetFactory.createBFInstructionMap(),
                MemorySectorSize.createByteSize(),
                List.of()
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
                InstructionsSetFactory.createBFInstructionMap(),
                MemorySectorSize.createByteSize(),
                List.of()
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

    @Test
    @DisplayName("Custom instruction test")
    void testCustomInstruction() {
        String code = "X";

        Map<Character, Instruction> customInstructionSet = InstructionsSetFactory.builder()
                .addInstruction(new Instruction() {
                    @Override
                    public char getInstructionCode() {
                        return 'X';
                    }

                    @Override
                    public void accept(Interpreter interpreter) {
                        IO.println("Hello World!");
                    }
                })
                .build();

        InterpreterManifest manifest = InterpreterManifest.create(
                ValueMappingFactory.createDefault(),
                customInstructionSet,
                MemorySectorSize.createByteSize(),
                List.of()
        );

        MachineInterpreter interpreter = MachineInterpreter.forProgramAndManifest(Program.from(code), manifest);
        assertThat(interpreter.runProgram()).isZero();

        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("Hello World!");
    }
}
