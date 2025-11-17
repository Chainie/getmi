package cz.gadder.bx.interpreter;

import cz.gadder.bx.mappings.ValueMapping;

public interface StateMachineSettings {

    ValueMapping valueMapping();
    MemorySectorSize memorySectorSize();
}
