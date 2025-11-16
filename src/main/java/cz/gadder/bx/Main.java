package cz.gadder.bx;

import cz.gadder.bx.interpreters.MachineInterpreter;
import com.beust.jcommander.JCommander;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "create")
public class Main {

    private final Args args;

    static void main(String[] argv)  {
        Main.create(parseArguments(argv)).run();
    }

    private void run() {
        MachineInterpreter.forProgram(Program.from(args.getProgramCode())).runProgram();
    }

    private static Args parseArguments(String[] argv) {
        Args args = new Args();
        JCommander commander = JCommander.newBuilder()
                .addObject(args)
                .build();
        commander.parse(argv);
        if (args.isHelp()) {
            if(argv.length == 1) {
                IO.println("This is a general Turing machine code interpreter. The default setting interprets the input code in BrainFuck standard notation.\n");
            }
            commander.usage();
            System.exit(0);
        }
        return args;
    }
}
