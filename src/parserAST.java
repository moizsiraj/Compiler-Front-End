import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.System.exit;

public class parserAST {
    private int noOfStates;
    private int noOfTerminals;
    private int noOfNonTerminals;
    private int noOfProductions;
    private scanner Scanner;
    private Token token;
    private String[][] actionTable;
    private String[][] gotoTable;
    private String[][] productions;

    public parserAST(String location, int noOfStates, int noOfTerminals, int noOfNonTerminals, int noOfProductions) throws IOException {
        this.Scanner = new scanner(location);
        this.noOfStates = noOfStates + 1;
        this.noOfTerminals = noOfTerminals;
        this.noOfNonTerminals = noOfNonTerminals;
        this.noOfProductions = noOfProductions;
        actionTable = new String[this.noOfStates][this.noOfTerminals];
        gotoTable = new String[this.noOfStates][this.noOfNonTerminals];
        productions = new String[this.noOfProductions][2];
        populateTables();
    }

    private Token getToken() {
        token = Scanner.getToken();
        switch (token.getKind()) {
            case 0:
                return new Token(token.getKind(), "$");
            case 1:
                return token;
            case 2:
                return new Token(token.getKind(), Integer.toString(token.getIntVal()));
            case 6:
                return new Token(token.getKind(), "while");
            case 8:
                return new Token(token.getKind(), "if");
            case 9:
                return new Token(token.getKind(), "}");
            case 12:
                return new Token(token.getKind(), "<");
            case 13:
                return new Token(token.getKind(), "<=");
            case 14:
                return new Token(token.getKind(), ">");
            case 15:
                return new Token(token.getKind(), ">=");
            case 16:
                return new Token(token.getKind(), "==");
            case 17:
                return new Token(token.getKind(), "!=");
            case 18:
                return new Token(token.getKind(), "=");
            case 10:
                return new Token(token.getKind(), "(");
            case 11:
                return new Token(token.getKind(), ")");
            case 20:
                return new Token(token.getKind(), "+");
            case 21:
                return new Token(token.getKind(), "-");
            case 22:
                return new Token(token.getKind(), "*");
            case 23:
                return new Token(token.getKind(), "/");
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

        //reading in terminals
        str = myReader.nextLine();
        String[] terminals = str.split("\t");
        for (int i = 0; i < actionTable[0].length; i++) {
            actionTable[0][i] = terminals[i];
        }

        //fixing tables for matching regex
        actionTable[0][2] = "\\(";
        actionTable[0][3] = "\\)";
        actionTable[0][11] = "\\+";
        actionTable[0][14] = "\\*";
        actionTable[0][15] = "[a-zA-Z][a-zA-Z0-9_]*";
        actionTable[0][16] = "0|[1-9][0-9]*";
        actionTable[0][17] = "\\$";

        //reading in non-terminals
        str = myReader.nextLine();
        String[] nonTerminals = str.split("\t");
        for (int i = 0; i < gotoTable[0].length; i++) {
            gotoTable[0][i] = nonTerminals[i];
        }

        //reading in transitions for action table
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

        //reading in transitions for goto table
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

        //reading in production rules
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

    private Stack<Node> stack = new Stack<>();

    public String parse() {
        ArrayList<Node> poppedNodes = new ArrayList<>();
        Token lastToken = new Token(Token.OTHERS, "check");
        State state = new State("s1");
        stack.push(state);
        Token word = getToken();
        while (true) {
            State getNode = (State) stack.peek();
            String currentState = getNode.getState();
            int row = getRow(currentState);
            int col = getCol(word.getID(), actionTable);
            if (col == -1 || actionTable[row][col] == null) {
                System.out.println("Invalid Syntax");
                System.out.println("Error at \"" + lastToken.getID() + "\"");
                exit(0);
            } else if (actionTable[row][col].charAt(0) == 's') {
                stack.push(word);
                State newState = new State(actionTable[row][col]);
                stack.push(newState);
                lastToken = word;
                word = getToken();
            } else if (actionTable[row][col].charAt(0) == 'r') {
                poppedNodes.clear();
                int ruleNo = getRow(actionTable[row][col]);
                int rowNo = ruleNo - 1;
                String LHS = productions[rowNo][0];
                String RHS = productions[rowNo][1];
                for (int i = 0; i < (RHS.length() * 2); i++) {
                    if (i % 2 == 0) {
                        stack.pop();
                    } else {
                        if (stack.peek() instanceof Token) {
                            Token check = (Token) stack.peek();
                            if (check.getID().equals("(") || check.getID().equals(")")) {
                                stack.pop();
                            } else {
                                poppedNodes.add(stack.pop());
                            }
                        } else {
                            poppedNodes.add(stack.pop());
                        }
                    }
                }
                getNode = (State) stack.peek();
                String newState = getNode.getState();
                row = getRow(newState);
                col = getCol(LHS, gotoTable);
                Node nodeLHS = InitiateNode(LHS, poppedNodes);
                stack.push(nodeLHS);
                State NewState = new State(gotoTable[row][col]);
                stack.push(NewState);
            } else if (actionTable[row][col].equals("acc")) {
                printTree(stack.elementAt(1), "", true);
                return "The code is valid";
            }
        }
    }

    public Node InitiateNode(String LHS, ArrayList<Node> poppedNodes) {
        switch (LHS) {
            case "P":
                return new Program((Statement) poppedNodes.get(0));
            case "S":
                Statement SNodeA;
                if (poppedNodes.size() == 1) {
                    if (poppedNodes.get(0) instanceof Statement) {
                        SNodeA = new Statement((Statement) poppedNodes.get(0));
                        return SNodeA;
                    } else if (poppedNodes.get(0) instanceof IfStatement) {
                        SNodeA = new Statement((IfStatement) poppedNodes.get(0));
                        return SNodeA;
                    } else if (poppedNodes.get(0) instanceof WhileStatement) {
                        SNodeA = new Statement((WhileStatement) poppedNodes.get(0));
                        return SNodeA;
                    } else if (poppedNodes.get(0) instanceof AssignStatement) {
                        SNodeA = new Statement((AssignStatement) poppedNodes.get(0));
                        return SNodeA;
                    }
                } else {
                    SNodeA = new Statement((Statement) poppedNodes.get(0), (Statement) poppedNodes.get(1));
                    return SNodeA;
                }
                break;
            case "A":
                return new AssignStatement((ID) poppedNodes.get(2), (Equal) poppedNodes.get(1), (Expr) poppedNodes.get(0));
            case "X":
                return new Equal();
            case "L":
                return new IfStatement((CompareStatement) poppedNodes.get(1), (Statement) poppedNodes.get(0));
            case "W":
                return new WhileStatement((CompareStatement) poppedNodes.get(1), (Statement) poppedNodes.get(0));
            case "Y":
                if (poppedNodes.get(2) instanceof Number) {
                    return new CompareStatement((ID) poppedNodes.get(0), (CompOp) poppedNodes.get(1), (Number) poppedNodes.get(2));
                } else {
                    return new CompareStatement((ID) poppedNodes.get(0), (CompOp) poppedNodes.get(1), (ID) poppedNodes.get(2));
                }
            case "C":
                if (poppedNodes.get(0) instanceof Equal) {
                    return new CompOp((Equal) poppedNodes.get(0));
                } else {
                    Token check = (Token) poppedNodes.get(0);
                    switch (check.getID()) {
                        case "<=":
                            return new CompOp("<=");
                        case "<":
                            return new CompOp("<");
                        case ">=":
                            return new CompOp(">=");
                        case ">":
                            return new CompOp(">");
                        case "==":
                            return new CompOp("==");
                        case "!=":
                            return new CompOp("!=");
                    }
                }
                break;
            case "E":
                if (poppedNodes.size() == 3) {
                    return new Expr((Expr) poppedNodes.get(2), (OperationA) poppedNodes.get(1), (Term) poppedNodes.get(0));
                } else {
                    return new Expr((Term) poppedNodes.get(0));
                }
            case "O": {
                Token check = (Token) poppedNodes.get(0);
                switch (check.getID()) {
                    case "+":
                        return new OperationA("+");
                    case "-":
                        return new OperationA("-");
                }
                break;
            }
            case "T":
                if (poppedNodes.size() == 3) {
                    return new Term((Term) poppedNodes.get(2), (OperationB) poppedNodes.get(1), (Factor) poppedNodes.get(0));
                } else {
                    return new Term((Factor) poppedNodes.get(0));
                }
            case "U": {
                Token check = (Token) poppedNodes.get(0);
                switch (check.getID()) {
                    case "*":
                        return new OperationB("*");
                    case "/":
                        return new OperationB("/");
                }
                break;
            }
            case "F":
                if (poppedNodes.get(0) instanceof Expr) {
                    return new Factor((Expr) poppedNodes.get(0));
                } else if (poppedNodes.get(0) instanceof ID) {
                    return new Factor((ID) poppedNodes.get(0));
                } else {
                    return new Factor((Number) poppedNodes.get(0));
                }
            case "I":
            case "N": {
                Token check = (Token) poppedNodes.get(0);
                return new ID(check.getID());
            }
        }
        return null;
    }

    public void printTree(Node node, String prefix, boolean last) {
        if (last) {
            System.out.println(prefix + "->" + node + "");
            prefix = prefix + " ";
        } else {
            System.out.println(prefix + "->" + "|:" + node + "");
            prefix = prefix + " " + "| ";
        }
        if (node.children != null) {
            int count = node.children.size();
            for (int i = 0; i < count; i++) {
                last = i == count - 1;
                printTree(node.children.get(i), prefix, last);
            }
        }
    }
}
