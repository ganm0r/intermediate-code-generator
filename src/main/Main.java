package main;

import intermediatecodegenerator.IntermediateCodeGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        final String INPUT_FILE_NAME = "/home/gndhrv/Documents/Sem VI/SPCC/IntermediateCodeGenerator/lib/inputFile.txt";
        final String OUTPUT_FILE_NAME = "/home/gndhrv/Documents/Sem VI/SPCC/IntermediateCodeGenerator/lib/outputFile.txt";

        IntermediateCodeGenerator.simulate(INPUT_FILE_NAME, OUTPUT_FILE_NAME);
    }
}
