package cz.gadger.gtmi.interpreter;

import org.jspecify.annotations.Nullable;

/**
 * Memory sector size defines min/max size of a single memory sector and overflow/underflow logic.
 * @param minVal of the memory (default 0)
 * @param maxVal of the memory (default 255)
 */
public record MemorySectorSize(int minVal, int maxVal) {

    public static int DEFAULT_MIN_MEMORY_VALUE = 0;
    public static int DEFAULT_MAX_MEMORY_VALUE = 255;

    public static MemorySectorSize create(@Nullable Integer minVal, @Nullable Integer maxVal) {
        minVal = minVal == null ? DEFAULT_MIN_MEMORY_VALUE : minVal;
        maxVal = maxVal == null ? DEFAULT_MAX_MEMORY_VALUE : maxVal;

        return new MemorySectorSize(minVal, maxVal);
    }

    public static MemorySectorSize createByteSize() {
        return create(DEFAULT_MIN_MEMORY_VALUE, DEFAULT_MAX_MEMORY_VALUE);
    }

    public static MemorySectorSize createZeroTo(int maxVal) {
        return create(DEFAULT_MIN_MEMORY_VALUE, maxVal);
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
