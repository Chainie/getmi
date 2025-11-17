package cz.gadder.bx;

import cz.gadder.bx.interpreter.InterpreterManifest;
import cz.gadder.bx.interpreter.StateMachine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class StateMachineTest {

    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("All basic operations over state machine works")
    void simpleMachineTest() throws IOException {
        StateMachine stateMachine = StateMachine.of(InterpreterManifest.createDefault());

        assertThat(stateMachine.getValueAtActiveMemorySector()).isEqualTo((char) 0);
        assertThat(stateMachine.getActiveMemorySectorIndex()).isZero();

        stateMachine.incrementValueAtActiveMemorySector();
        assertThat(stateMachine.getValueAtActiveMemorySector()).isEqualTo((char) 1);

        stateMachine.decrementValueAtActiveMemorySector();
        assertThat(stateMachine.getValueAtActiveMemorySector()).isEqualTo((char) 0);

        stateMachine.incrementValueAtActiveMemorySector();
        stateMachine.incrementActiveMemoryIndex();
        assertThat(stateMachine.getValueAtActiveMemorySector()).isEqualTo((char) 0);
        assertThat(stateMachine.getActiveMemorySectorIndex()).isEqualTo(1);

        stateMachine.decrementActiveMemoryIndex();
        assertThat(stateMachine.getActiveMemorySectorIndex()).isZero();
        assertThat(stateMachine.getValueAtActiveMemorySector()).isEqualTo((char) 1);
        stateMachine.decrementValueAtActiveMemorySector();

        for (int i = 0; i < 45; i++) {
            stateMachine.incrementValueAtActiveMemorySector();
        }
        stateMachine.printDataFromActiveMemorySector();
        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("" + ((char) 45));

        String input = "" + ('a');
        standardOut.println(">>" + input);
        InputStream testInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(testInput);
        stateMachine.readInputToActiveMemorySector();
        assertThat(stateMachine.getValueAtActiveMemorySector()).isEqualTo('a');

        testInput.reset();
    }
}