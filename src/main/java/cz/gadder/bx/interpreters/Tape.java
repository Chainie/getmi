package cz.gadder.bx.interpreters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor(staticName = "create")
public class Tape {

    private final MemorySectorSize memorySectorSize;
    private MemorySector activeSector = MemorySector.create(0);

    public void switchToNextMemorySector() {
        if (activeSector.getNextSector() != null) {
            activeSector = activeSector.getNextSector();
        } else {
            MemorySector nextSector = MemorySector.create(activeSector.getIndex() + 1);
            nextSector.setPreviousSector(activeSector);
            activeSector.setNextSector(nextSector);
            activeSector = nextSector;
        }
    }

    public void switchToPreviousMemorySector() {
        if (activeSector.getPreviousSector() != null) {
            activeSector = activeSector.getPreviousSector();
        } else {
            MemorySector previousSector = MemorySector.create(activeSector.getIndex() - 1);
            previousSector.setNextSector(activeSector);
            activeSector.setPreviousSector(previousSector);
            activeSector = previousSector;
        }
    }

    public void incrementActiveMemorySectorValue() {
        activeSector.incrementValue(memorySectorSize);
    }

    public void decrementActiveMemorySectorValue() {
        activeSector.decrementValue(memorySectorSize);
    }

    public char getActiveMemorySectorValue() {
        return activeSector.getValue();
    }

    public void setValueInActiveMemorySector(char value) {
        activeSector.setValue(value);
    }

    public int getActiveMemoryIndex() {
        return activeSector.getIndex();
    }


    @Getter
    @Setter
    @RequiredArgsConstructor(staticName = "create")
    private static class MemorySector {
        private final int index;
        private char value = 0;
        private MemorySector previousSector = null;
        private MemorySector nextSector = null;

        public void incrementValue(MemorySectorSize memorySectorSize) {
            value = (char) memorySectorSize.incrementValue(value);
        }

        public void decrementValue(MemorySectorSize memorySectorSize) {
            value = (char) memorySectorSize.decrementValue(value);
        }
    }

}
