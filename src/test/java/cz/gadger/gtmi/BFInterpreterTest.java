package cz.gadger.gtmi;

import cz.gadger.gtmi.interpreter.MachineInterpreter;
import cz.gadger.gtmi.interpreter.Program;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BFInterpreterTest {

    @Test
    @DisplayName("Empty program can be executed successfully")
    void simpleTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from(""));
        assertThat(interpreter.runProgram()).isZero();
    }

    @Test
    @DisplayName("Simple incrementation works")
    void simpleIncrementationTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from("+++++"));
        assertThat(interpreter.runProgram()).isZero();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 5);
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isZero();
    }

    @Test
    @DisplayName("Simple decrementation works")
    void simpleDecrementationTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from("-----"));
        assertThat(interpreter.runProgram()).isZero();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 251);
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isZero();
    }

    @Test
    @DisplayName("Negative memory segments works")
    void negativeMemorySegmentsTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from("<<<<<+++++"));
        assertThat(interpreter.runProgram()).isZero();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 5);
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isEqualTo(-5);
    }

    @Test
    @DisplayName("Unknown instructions are ignored")
    void ignoreUnknownInstructionsTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from("#+v+ \n+aa+T_X_Y_Z_+"));
        assertThat(interpreter.runProgram()).isZero();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 5);
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isZero();
    }

    @Test
    @DisplayName("Data tape iterations works")
    void tapeIterationsTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from("+>>>--<<<"));
        assertThat(interpreter.runProgram()).isZero();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 1);
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isZero();

        interpreter.getActiveStateMachine().incrementActiveMemoryIndex();
        interpreter.getActiveStateMachine().incrementActiveMemoryIndex();
        interpreter.getActiveStateMachine().incrementActiveMemoryIndex();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 254);
    }

    @Test
    @DisplayName("Single iteration cycles works")
    void tapeSingleCycleTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from("+[>+++<-]>"));
        assertThat(interpreter.runProgram()).isZero();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 3);
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isEqualTo(1);
    }

    @Test
    @DisplayName("Multi iteration cycles works")
    void tapeMultiIterationCycleTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from("+++[>+++<-]>"));
        assertThat(interpreter.runProgram()).isZero();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 9);
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isEqualTo(1);
    }

    @Test
    @DisplayName("Empty cycle works")
    void emptyCycleTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from("[>+++<-]>"));
        assertThat(interpreter.runProgram()).isZero();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 0);
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isEqualTo(1);

        interpreter.getActiveStateMachine().decrementActiveMemoryIndex();
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isZero();
    }

    @Test
    @DisplayName("Multiple inner cycles works")
    void innerCyclesTest() {
        MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from("+++[>+++[>+++<-]<-]>>")); // 3*3*3
        assertThat(interpreter.runProgram()).isZero();
        assertThat(interpreter.getActiveStateMachine().getValueAtActiveMemorySector()).isEqualTo((char) 27);
        assertThat(interpreter.getActiveStateMachine().getActiveMemorySectorIndex()).isEqualTo(2);
    }
}