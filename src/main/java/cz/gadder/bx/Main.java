package cz.gadder.bx;

import cz.gadder.bx.instructions.InstructionMapFactory;
import cz.gadder.bx.interpreters.MachineInterpreter;
import cz.gadder.bx.interpreters.MemorySectorSize;
import cz.gadder.bx.mappings.ValueMapping;
import cz.gadder.bx.mappings.ValueMappingFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class Main {
    static void main() {
        //String programString = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";

//        char i = 0;
//        for (int j = 0; j < 255; j++) {
//            char a = ((char)i++);
//            IO.print("\'" + a + "\', ");
//        }


        //String programString = "+++++++++[>++++++++++<-]>+++++++.+++++++.+++++++.-----.";

//        String programString = """
//                +++++++++[>+++++++++++<-]>--. //a
//                +++++++.                      //h
//                +++++++.                      //o
//                -----.                        //j
//                """;



//        String programString = """
//                --<-<<+[+[<+>--->->->-<<<]>]<<--.<++++++.<<-..<<.<+.>>.>>.<<<.+++.>>.>>-.<<<+.
//                """;
//
//        MachineInterpreter.forProgram(Program.from(programString)).runProgram();

//        IO.println();
//
//        List<Character> values = List.of(
//                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
//                          'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
//        );
//
//                String simpleProgramString = """
//                .                             //a
//                +++++++.                      //h
//                +++++++.                      //o
//                -----.                        //j
//                """;
//        BFInterpreter.forProgramAndMapping(Program.from(simpleProgramString),
//                ValueMappingFactory.createNormalizedSetMappingFromList(values)).runProgram();

    }
}
