# Usage Documentation
Here is a usage documentation for GeTMI used as a dependency or as a command line tool.

## Library usage

### General architecture
TBD...

### Download

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

### Usage

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

## Command line usage