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

#### Simple usage
To use GeTMI one needs to just have a code to run and create a MachineInterpreter instance and run the program like so:

```
    String code = """
                    ++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.
                    """;
    MachineInterpreter interpreter = MachineInterpreter.forProgram(Program.from(code));
    interpreter.runProgram();
```

The default configuration of the interpreter is a [BrainF**k](https://esolangs.org/wiki/Brainfuck) standard notation with a byte ASCII char set.

The output of the program will be printed to standard output:

```
    Hello World!
```

#### Defining value map and the memory sector size
You can replace standard ASCII value mapping with custom I/O mapping: 

```
        List<Character> words = List.of('H', 'e', 'l', 'o', ',' , ' ', 'W', 'r', 'd', '!');
        ValueMapping valueMapping = ValueMappingFactory.createNormalizedSetMappingFromList(words);
```
This mapping specifies 10 I/O characters mapped from index 0 to 9.
Using this mapping, printing memory sector with value 0 will output H and when reading inout value H will insert 0 to the memory sector.
Using ValueMapping factory you can also define an index for each character - for example X -> 16 etc..

Next you can limit the memory sector minimum and maximum value. By default, the memory sector has range 0..255. 
The maximum/minimum memory sector size is integer. Use the class MemorySectorSize: 

```
    MemorySectorSize memorySectorSize = create(-1024, 1024);
    or: 
    MemorySectorSize memorySectorSize = createByteSize(); //0..255
    or:
    MemorySectorSize memorySectorSize = MemorySectorSize.createZeroTo(words.size());
```
ValueMapping and the MemorySectorSize are the other setting for the interpreter: 

```
    InterpreterManifest manifest = InterpreterManifest.create(
          valueMapping,
          InstructionsSetFactory.createBFInstructionMap(),
          memorySectorSize,
          List.of()  //List of execution listerners - explained later
    );    
```
This way you can limit the necessary incrementation/decremental steps and simplify the program: 

```
    Program program = Program.from(".+.+..+.+.+.+.---.++++.-----.++++++.+.");

    MachineInterpreter interpreter = MachineInterpreter.forProgramAndManifest(program, manifest);
    interpreter.runProgram());
```

Will also print: 
```
    Hello, World!
```

### Overflows
### Execution listeners
### Custom instruction set
## Command line usage