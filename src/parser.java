import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.System.exit;

public class parser {
    private int noOfStates;
    private int noOfTerminals;
    private int noOfNonTerminals;
    private int noOfProductions;
    private scanner Scanner;
    private int currentToken;
    private String[] token;
    private String[][] actionTable;
    private String[][] gotoTable;
    private String[][] productions;

    public parser(String location, int noOfStates, int noOfTerminals, int noOfNonTerminals, int noOfProductions) throws IOException {
        this.Scanner = new scanner(location);
        this.noOfStates = noOfStates + 1;
        this.noOfTerminals = noOfTerminals;
        this.noOfNonTerminals = noOfNonTerminals;
        this.noOfProductions = noOfProductions;
        currentToken = -1;
        actionTable = new String[this.noOfStates][this.noOfTerminals];
        gotoTable = new String[this.noOfStates][this.noOfNonTerminals];
        productions = new String[this.noOfProductions][2];
        populateTables();
    }

    private String getToken() {
        Token token = Scanner.getToken();
        switch (token.getKind()) {
            case 0:
                return "$";
            case 1:
                return token.getID();
            case 2:
                return String.valueOf(token.getIntVal());
            case 6:
                return "while";
            case 8:
                return "if";
            case 9:
                getToken();
            case 12:
                return "<";
            case 13:
                return "<=";
            case 14:
                return ">";
            case 15:
                return ">=";
            case 16:
                return "==";
            case 17:
                return "!=";
            case 18:
                return "=";
            case 10:
                return "(";
            case 11:
                return ")";
            case 20:
                return "+";
            case 21:
                return "-";
            case 22:
                return "*";
            case 23:
                return "/";
            default:
                return null;
        }
    }

    public void print(String[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void printTransitionTable(String[][] table) {
        System.out.println("Transition Table:");
        for (String[] row : table) {
            for (String text : row) {
                if (text == null) {
                    text = "-";
                }
                System.out.print(text + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }


    private void populateTables() throws FileNotFoundException {
        //Scanner input = new Scanner(System.in);
        File code = new File("C:\\Users\\moizs\\OneDrive\\Documents\\NetBeansProjects\\compilerFrontEnd\\parse file.txt");
        Scanner myReader = new Scanner(code);
        String str;
//        System.out.println("Enter terminals in form \n" +
//                "<terminal 1> <terminal 2> <terminal 3>");
        str = myReader.nextLine();
        String[] terminals = str.split("\t");
        for (int i = 0; i < actionTable[0].length; i++) {
            actionTable[0][i] = terminals[i];
        }
        actionTable[0][2] = "\\(";
        actionTable[0][3] = "\\)";
        actionTable[0][11] = "\\+";
        actionTable[0][14] = "\\*";
        actionTable[0][15] = "[a-zA-Z][a-zA-Z0-9_]*";
        actionTable[0][16] = "0|[1-9][0-9]*";
        actionTable[0][17] = "\\$";

//        System.out.println("Enter non-terminals in form \n" +
//                "<non-terminal 1> <non-terminal 2> <non-terminal 3>");
        str = myReader.nextLine();
        String[] nonTerminals = str.split("\t");
        for (int i = 0; i < gotoTable[0].length; i++) {
            gotoTable[0][i] = nonTerminals[i];
        }
//        System.out.println("Enter the transition for terminals in form \n" +
//                "<state> <terminal> <transition> examples: 1 a s2, 1 a r3, 1 $ acc \n" +
//                "input \"end\" when done");
        str = myReader.nextLine();
        while (!str.equals("end")) {
            String[] values = str.split(" ");
            int row = Integer.parseInt(values[0]);
            int col = getCol(values[1], actionTable);
            String transition = values[2];
            if (transition.equals("acc")) {
                actionTable[row + 1][col] = transition;
            } else {
                String value = transition.substring(1);
                int index = Integer.parseInt(value);
                index = index + 1;
                actionTable[row + 1][col] = String.valueOf(transition.charAt(0)) + index;
            }
            str = myReader.nextLine();
        }
//        System.out.println("Enter the transition for non-terminals in form \n" +
//                "<state> <non-terminal> <transition> examples: 1 A g1, 3 B g8\n" +
//                "input \"end\" when done");
        str = myReader.nextLine();
        while (!str.equals("end")) {
            String[] values = str.split(" ");
            int row = Integer.parseInt(values[0]);
            int col = getCol(values[1], gotoTable);
            String transition = values[2];
            String value = transition.substring(1);
            int index = Integer.parseInt(value);
            index = index + 1;
            gotoTable[row + 1][col] = String.valueOf(transition.charAt(0)) + index;
            str = myReader.nextLine();
        }
//        System.out.println("Enter the production rules in form \n" +
//                "<Right side> <Left side> examples: S aABe, A Abc\n" +
//                "input \"end\" when done");
        str = myReader.nextLine();
        int index = 0;
        while (!str.equals("end")) {
            String[] values = str.split(" ");
            productions[index][0] = values[0];
            productions[index][1] = values[1];
            index++;
            str = myReader.nextLine();
        }
    }

    private int getCol(String str, String[][] table) {
        for (int i = 0; i < table[0].length; i++) {
            if (str.matches(table[0][i])) {
                return i;
            }
        }
        return -1;
    }

    private int getRow(String str) {
        switch (str.length()) {
            case 2:
                return Integer.parseInt(String.valueOf(str.charAt(1)));
            case 3:
                return Integer.parseInt(str.substring(1, 3));
            case 4:
                return Integer.parseInt(str.substring(1, 4));
            default:
                return -1;
        }
    }

    private Stack<String> stack = new Stack<>();

    public String parse() {
        stack.push("s1");
        String word = getToken();
        while (true) {
//            try {
            String currentState = stack.peek();
            int row = getRow(currentState);
            int col = getCol(word, actionTable);
            if (actionTable[row][col] == null) {
                System.out.println("Invalid Syntax");
                System.out.println("Error at \"" + word + "\"");
                exit(0);
            } else if (actionTable[row][col].charAt(0) == 's') {
                stack.push(word);
                stack.push(actionTable[row][col]);
                word = getToken();
            } else if (actionTable[row][col].charAt(0) == 'r') {
                int ruleNo = getRow(actionTable[row][col]);
                int rowNo = ruleNo - 1;
                String LHS = productions[rowNo][0];
                String RHS = productions[rowNo][1];
                for (int i = 0; i < (RHS.length() * 2); i++) {
                    stack.pop();
                }
                String newState = stack.peek();
                row = getRow(newState);
                col = getCol(LHS, gotoTable);
                stack.push(LHS);
                stack.push(gotoTable[row][col]);
            } else if (actionTable[row][col].equals("acc")) {
                return "The code is valid";
            }
//            } catch (Exception e) {
//                return "The code is invalid";
//            }
        }
    }
}
