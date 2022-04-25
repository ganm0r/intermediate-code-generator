package intermediatecodegenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

public class IntermediateCodeGenerator {
    public static void simulate(String sourceCodeFile, String outputFile) throws IOException, FileNotFoundException {
        Vector<String> targetCodeFile = new Vector<String>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceCodeFile));

        String currentLine;

        while((currentLine = bufferedReader.readLine()) != null) {

            String[] currentLineSplitLHSRHS = currentLine.split("=");
            String infix = currentLineSplitLHSRHS[1];

            String postfix = convertInfixToPostfix(infix);
            Stack<String> postfixToIcgStack = new Stack<String>();

            int tempVariableNameCount = 1;
            String tempVariableOne, tempVariableTwo;

            for(int i = 0; i < postfix.length() + 1; i++) {
                if(i == postfix.length()) {
                    targetCodeFile.addElement(currentLineSplitLHSRHS[0] + "=" + postfixToIcgStack.pop());
                    break;
                }

                String currentChar = postfix.charAt(i) + "";

                if(!isOperator(postfix.charAt(i))) {
                    postfixToIcgStack.push(currentChar);
                } else {
                    tempVariableOne = postfixToIcgStack.pop();
                    tempVariableTwo = postfixToIcgStack.pop();

                    targetCodeFile.add("t" + tempVariableNameCount + "=" + tempVariableTwo + "" + postfix.charAt(i) + "" + tempVariableOne);

                    postfixToIcgStack.push("t" + tempVariableNameCount);
                    tempVariableNameCount++;
                }
            }
        }

        bufferedReader.close();

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

        for(int i = 1; i <= targetCodeFile.size(); i++) {
            bufferedWriter.write(targetCodeFile.get(i-1));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }

    private static int getOperatorPrecedence(char ch) {
        if(ch == '*' || ch == '/') {
            return 3;
        } else if(ch == '+' || ch == '-') {
            return 2;
        } else {
            return 0;
        }
    }

    private static boolean isOperator(char op) {
        if(op >= 'a' && op <= 'z') {
            return false;
        } else {
            return true;
        }
    }

    private static String convertInfixToPostfix(String infix) {
        Stack<Character> infixToPostfixStack = new Stack<Character>();
        String postfix = "";

        for(int i = 0; i < infix.length(); ++i) {
            char currentChar = infix.charAt(i);

            if(Character.isLetterOrDigit(currentChar)) {
                postfix += currentChar;
            } else {
                while(!infixToPostfixStack.isEmpty() && getOperatorPrecedence(currentChar) <= getOperatorPrecedence(infixToPostfixStack.peek())) {
                    postfix += infixToPostfixStack.pop();
                }

                infixToPostfixStack.push(currentChar);
            }
        }

        while(!infixToPostfixStack.isEmpty()) {
            postfix += infixToPostfixStack.pop();
        }
        System.out.println(postfix);
        return postfix;
    }
}