package cz.gadder.bx;

import cz.gadder.bx.interpreter.Program;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProgramTest {

    @Test
    @DisplayName("All basic operations over state machine works")
    void simpleProgramTest() {
        String code = "+-+--[++]";
        Program program = Program.from(code);

        assertThat(program.getCode()).isEqualTo(code);
        assertThat(program.getActiveInstructionCode()).isEqualTo('+');
        assertThat(program.getInstructionPointer()).isZero();
        assertThat(program.isNotFinished()).isTrue();

        program.incrementInstructionPointer();
        assertThat(program.getActiveInstructionCode()).isEqualTo('-');
        assertThat(program.getInstructionPointer()).isEqualTo(1);
        assertThat(program.isNotFinished()).isTrue();

        program.iterateToNextInstructionCode(']', '[');
        assertThat(program.getActiveInstructionCode()).isEqualTo(']');
        assertThat(program.getInstructionPointer()).isEqualTo(8);
        assertThat(program.isNotFinished()).isTrue();

        program.iterateToPreviousInstructionCode('[', ']');
        assertThat(program.getActiveInstructionCode()).isEqualTo('[');
        assertThat(program.getInstructionPointer()).isEqualTo(5);
        assertThat(program.isNotFinished()).isTrue();

        program.iterateToNextInstructionCode(']', '[');
        program.incrementInstructionPointer();
        assertThat(program.isNotFinished()).isFalse();
    }

}