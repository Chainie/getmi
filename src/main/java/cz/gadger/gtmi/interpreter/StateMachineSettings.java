package cz.gadger.gtmi.interpreter;

import cz.gadger.gtmi.mappings.ValueMapping;

public interface StateMachineSettings {

    ValueMapping valueMapping();
    MemorySectorSize memorySectorSize();
}
