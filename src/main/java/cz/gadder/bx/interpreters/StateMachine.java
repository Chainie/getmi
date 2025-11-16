package cz.gadder.bx.interpreters;

import cz.gadder.bx.mappings.ValueMapping;
import cz.gadder.bx.mappings.ValueMappingFactory;

public class StateMachine {

    private final Tape tape;
    private final ValueMapping valueMapping;

    private StateMachine(ValueMapping valueMapping, MemorySectorSize sectorSize) {
        this.tape = Tape.create(sectorSize);
        this.valueMapping = valueMapping;
    }

    public static StateMachine of() {
        return new StateMachine(ValueMappingFactory.createDefault(), MemorySectorSize.createByteSize());
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
