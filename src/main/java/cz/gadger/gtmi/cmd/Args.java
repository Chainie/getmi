package cz.gadger.gtmi.cmd;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import cz.gadger.gtmi.instructions.InstructionSetType;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

/**
 * GTMI input arguments from console.
 */
@Getter
@SuppressWarnings("unused")
public class Args {

    private static final String PROGRAM_CODE_DESCRIPTION = "Program code to run. The default BrainF**k instruction set with one byte memory slot in ASCI code will be used.";

    @Parameter(description = PROGRAM_CODE_DESCRIPTION)
    private String programCode = "";

    @Parameter(names = {"-h", "--help"}, description = "shows help", help = true)
    private boolean help;

    @Parameter(names = {"-p", "--program"}, description = PROGRAM_CODE_DESCRIPTION)
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    @Parameter(names = {"--minMem"},  description = "Min size of the memory sector (default 0)")
    private Integer minMemorySize;

    @Parameter(names = {"--maxMem"},  description = "Max size of the memory sector (default 255)")
    private Integer maxMemorySize;

    @Parameter(names = {"-d", "--delay"},  description = "Step delay between code instruction execution (default = 0)")
    private Integer stepDelayMs = 0;

    @Parameter(names = {"--memFit"},  description = "Fit max memory size to the world list size (default false)")
    private boolean memFit;

    @Parameter(names = {"-is", "--instructionSets"},  converter = InstructionSetTypeConverter.class, description = "A comma-separated list of instruction sets to be used. (default B_F_STANDARD_SET)")
    private List<InstructionSetType> instructionSets = new ArrayList<>();

    private List<Character> charSet = new ArrayList<>();

    @Parameter(names = {"-f", "--file"}, converter = FileConverter.class, description = "Run program from the selected file")
    public void setProgramCodeFromFile(File file) throws IOException {
        try {
            this.programCode = String.join("", Files.readAllLines(file.toPath()));
        } catch (NoSuchFileException e) {
            System.err.println("ERROR: Cannot find the specified file: " + e.getMessage());
        }
    }

    @Parameter(names = {"-ch", "--charSet"}, description = "File with charset to be used. Each char is put on new line index as its line number (default ASCI 255 bits charset)")
    public void setCharSet(File file) throws IOException {
        try {
            for (String s : Files.readAllLines(file.toPath())) {
                if(s.isEmpty()) {
                    charSet.add(' ');
                } else {
                    charSet.add(s.charAt(0));
                }
            }
        } catch (NoSuchFileException e) {
            System.err.println("ERROR: Cannot find the specified file: " + e.getMessage());
        }
    }
}
