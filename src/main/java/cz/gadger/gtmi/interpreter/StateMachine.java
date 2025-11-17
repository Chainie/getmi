package cz.gadger.gtmi.interpreter;

import cz.gadger.gtmi.mappings.ValueMapping;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

/**
 * State machine holds its tape and references to the sibling machines.
 */
public class StateMachine {

    private final Tape tape;
    private final ValueMapping valueMapping;
    @Setter
    @Getter
    @Nullable
    private StateMachine nextStateMachine;
    @Setter
    @Getter
    @Nullable
    private StateMachine previousStateMachine;

    private StateMachine(ValueMapping valueMapping, MemorySectorSize sectorSize) {
        this.tape = Tape.create(sectorSize);
        this.valueMapping = valueMapping;
    }

    public static StateMachine of(StateMachineSettings settings) {
        return new StateMachine(settings.valueMapping(), settings.memorySectorSize());
    }

    public int getActiveMemorySectorIndex() {
        return tape.getActiveMemoryIndex();
    }

    public void incrementActiveMemoryIndex() {
        tape.switchToNextMemorySector();
    }

    public void decrementActiveMemoryIndex() {
        tape.switchToPreviousMemorySector();
    }

    public void incrementValueAtActiveMemorySector() {
        tape.incrementActiveMemorySectorValue();
    }

    public void decrementValueAtActiveMemorySector() {
        tape.decrementActiveMemorySectorValue();
    }

    public char getValueAtActiveMemorySector() {
        return tape.getActiveMemorySectorValue();
    }

    public void readInputToActiveMemorySector() {
        tape.setValueInActiveMemorySector(readChar());
    }

    public void printDataFromActiveMemorySector() {
        IO.print(valueMapping.mapToInput(getValueAtActiveMemorySector()));
    }


    public boolean isDataAtActiveMemorySectorEqualToZero() {
        return getValueAtActiveMemorySector() == 0;
    }

    private char readChar() {
        String readln = IO.readln();
        return valueMapping.mapFromInput(readln.charAt(0));
    }
}
