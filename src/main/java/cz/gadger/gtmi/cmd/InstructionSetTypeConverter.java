package cz.gadger.gtmi.cmd;

import com.beust.jcommander.IStringConverter;
import cz.gadger.gtmi.instructions.InstructionSetType;

public class InstructionSetTypeConverter implements IStringConverter<InstructionSetType> {
    @Override
    public InstructionSetType convert(String value) {
        try {
            String upperCasedTrim = value.trim().toUpperCase();
            return InstructionSetType.valueOf(upperCasedTrim);
        } catch (IllegalArgumentException _) {
            String availableOptions = InstructionSetType.getAvailableOptions();
            System.err.println("ERROR: Invalid instruction set type: " + value + " use one of the following available: " + availableOptions);
            System.exit(1);
        }
        return InstructionSetType.B_F_STANDARD_SET;
    }
}
