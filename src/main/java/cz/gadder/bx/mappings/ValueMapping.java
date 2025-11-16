package cz.gadder.bx.mappings;

public interface ValueMapping {
    char mapToInput(char to);
    char mapFromInput(char from);
}
