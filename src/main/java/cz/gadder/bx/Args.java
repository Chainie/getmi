package cz.gadder.bx;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

@Getter
@SuppressWarnings("unused")
public class Args {

    private static final String PROGRAM_CODE_DESCRIPTION = "Program code to run. The default BrainFuck instruction set with one byte memory slot in ASCI code will be used.";

    @Parameter(description = PROGRAM_CODE_DESCRIPTION)
    private String programCode = "";

    @Parameter(names = {"-h", "--help"}, description = "shows help", help = true)
    private boolean help;

    @Parameter(names = {"-p", "--program"}, description = PROGRAM_CODE_DESCRIPTION)
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    @Parameter(names = {"-f", "--file"}, converter = FileConverter.class, description = "Run program from the selected file")
    public void setProgramCodeFromFile(File file) throws IOException {
        try {
            this.programCode = String.join("", Files.readAllLines(file.toPath()));
        } catch (NoSuchFileException e) {
            System.err.println("ERROR: Cannot find the specified file: " + e.getMessage());
        }
    }
}
