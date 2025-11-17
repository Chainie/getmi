# GeTMI 🧠

Generic Turing Machine Interpret (GeTMI) is used for running programs from turing machine family such as [BrainF**k](https://esolangs.org/wiki/Brainfuck) and other esoteric programming languages. Its goal is to be easily extensible with implementation of additional instructions and functionalities these languages requires.

## General usage example

To use GeTMI one needs to just have a code to run and create a MachineInterpreter instance and run the program like so:

```
String code = """
                ++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.
                """;
MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from(code));
interpreter.runProgram();
```

The default configuration of the interpreter is a [BrainF**k](https://esolangs.org/wiki/Brainfuck) standard notation with a byte ASCI char set.

The output of the program will be printed to standard output:

```
Hello World!
```
