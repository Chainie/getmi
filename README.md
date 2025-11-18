# GeTMI 🧠

Generic Turing Machine Interpret (GeTMI) is used for running programs from turing machine family such as [BrainF**k](https://esolangs.org/wiki/Brainfuck)
and other esoteric programming languages. Its goal is to be easily extensible with implementation of additional instructions and functionalities these languages requires.

## Version

GeTMi is in its initial version 1.0.0

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

## Download
Gradle:
```
dependencies {
implementation("io.github.chainie:getmi:1.0.0")
}
```
Maven:
```
<dependency>
    <groupId>io.github.chainie</groupId>
    <artifactId>getmi</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Currently supported features

* Infinite tape support* - memory sectors implemented as linked list 
* Multi-tape support - using extended instruction set for switching between tapes
* Configurable memory sector size - not limited to standard 0-255 size
* Configurable input/output char set - not limited to standard ASCI char set
* CLI - can interpret programs using command line
* Configurable delay time between executing individual instructions
* Configurable instruction set
  * Currently in v1.0.0, supports only [BrainF**k](https://esolangs.org/wiki/Brainfuck) notation and instruction for multi-tapes
  * Can be easily extended with user-defined instruction 

\*Up to a memory limit ofc..

## Planned features

* More esoteric programming languages support
* Debug mode
* Dockerization
* Better documentation
* Stacks, functions, network, sound, objects support
* String to code compiler
* IntelliJIdea plugin
