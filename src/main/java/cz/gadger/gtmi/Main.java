package cz.gadger.gtmi;

import cz.gadger.gtmi.cmd.Args;
import cz.gadger.gtmi.interpreter.InterpreterManifest;
import cz.gadger.gtmi.interpreter.MachineInterpreter;
import com.beust.jcommander.JCommander;
import cz.gadger.gtmi.interpreter.Program;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "create")
public class Main {

    private final Args args;

    static void main(String[] argv)  {
        Main.create(parseArguments(argv)).run();
    }

    private void run() {
        MachineInterpreter
                .forProgramAndManifest(Program.from(args.getProgramCode()), InterpreterManifest.createFromArgs(args))
                .runProgram();
    }

    private static Args parseArguments(String[] argv) {
        Args args = new Args();
        JCommander commander = JCommander.newBuilder()
                .addObject(args)
                .build();
        commander.parse(argv);
        if (args.isHelp()) {
            if(argv.length == 1) {
                IO.println("This is a general Turing machine code interpreter. The default setting interprets the input code in BrainF**k standard notation.\n");
            }
            commander.usage();
            System.exit(0);
        }
        return args;
    }
}
