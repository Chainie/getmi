package cz.gadger.gtmi.mappings;

public interface ValueMapping {
    char mapToInput(char to);
    char mapFromInput(char from);
}
