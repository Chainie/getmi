package cz.gadder.bx;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "from")
public class Program {

    private final String code;
    private int instructionPointer = 0;

    public void incrementInstructionPointer() {
        instructionPointer++;
    }

    public boolean isNotFinished() {
        return instructionPointer < code.length();
    }

    public char getActiveInstructionCode() {
        return code.charAt(instructionPointer);
    }

    public void iterateToNextInstructionCode(char instructionCode, char matchingInstructionCode) {
        int match = 0;
        while ((instructionPointer < code.length() && code.charAt(instructionPointer) != instructionCode) || match != 1) {
            if(code.charAt(instructionPointer) == matchingInstructionCode) {
                match++;
            }
            if(code.charAt(instructionPointer) == instructionCode) {
                match--;
            }
            instructionPointer++;
        }
    }

    public void iterateToPreviousInstructionCode(char instructionCode, char matchingInstructionCode) {
        int match = 0;
        while ((instructionPointer > 0 && code.charAt(instructionPointer) != instructionCode) || match != 1) {
            if(code.charAt(instructionPointer) == matchingInstructionCode) {
                match++;
            }
            if(code.charAt(instructionPointer) == instructionCode) {
                match--;
            }
            instructionPointer--;
        }
    }
}
