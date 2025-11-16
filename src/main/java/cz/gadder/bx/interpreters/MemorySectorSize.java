package cz.gadder.bx.interpreters;

public record MemorySectorSize(int minVal, int maxVal) {

    public static MemorySectorSize createByteSize() {
        return new MemorySectorSize(0, 255);
    }

    public static MemorySectorSize createZeroTo(int maxVal) {
        return new MemorySectorSize(0, maxVal);
    }

    public int incrementValue(int value) {
        if(value == maxVal) {
            return minVal;
        }
        return value + 1;
    }

    public int decrementValue(int value) {
        if(value == minVal) {
            return maxVal;
        }
        return value - 1;
    }
}
