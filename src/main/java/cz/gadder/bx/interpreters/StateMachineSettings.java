package cz.gadder.bx.interpreters;

import cz.gadder.bx.mappings.ValueMapping;

public interface StateMachineSettings {

    ValueMapping valueMapping();
    MemorySectorSize memorySectorSize();
}
