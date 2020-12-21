import javax.print.DocFlavor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.System.exit;

public class scanner {

    // global state and methods
    private Token lastToken;
    private char nextChar;
    private String code;
    private int counter;
    FileInputStream filePointer;// next unprocessed input character

    public scanner(String location) throws IOException {
        filePointer = openFile(location);
        code = skipWhiteSpace(filePointer);
        counter = 0;

    }

    // advance to next input char
    void getChar() {
        if (counter < code.length()) {
            nextChar = code.charAt(counter);
            counter++;
        } else {
            exit(0);
        }
    }

    FileInputStream openFile(String location) throws FileNotFoundException {
        File file = new File(location);
        return new FileInputStream(file);
    }

    // skip whitespace and comments
    String skipWhiteSpace(FileInputStream filePointer) throws IOException {
        int r = 0;
        StringBuilder code = new StringBuilder();
        while ((r = filePointer.read()) != -1) {
            if (r == '/') {
                char temp = (char) filePointer.read();
                if (temp == '/') {
                    while (temp != '\n') {
                        temp = (char) filePointer.read();
                    }
                } else if (temp == '*') {
                    temp = (char) filePointer.read();
                    while (temp != '/') {
                        temp = (char) filePointer.read();
                    }
                }
            } else if (r == '\n') {
                filePointer.read();
            } else if (r == '\r') {
                filePointer.read();
            } else {
                code.append((char) r);
            }
        }
        return code.toString();
    }

    //     return next input token
    public Token getToken() {
        Token result;
        getChar();
        if (nextChar == ' ') {
            while (nextChar == ' ') {
                getChar();
            }
        }
        if (Character.isLetter(nextChar)) {
            StringBuilder str = new StringBuilder();
            str.append(nextChar);
            getChar();
            while (Character.isLetter(nextChar)) {
                str.append(nextChar);
                getChar();
                while (Character.isLetter(nextChar) || Character.isDigit(nextChar) || nextChar == '_') {
                    str.append(nextChar);
                    getChar();
                }
            }
            if (str.toString().equals("if")) {
                result = new Token(Token.IF);
                lastToken = result;
                counter--;
                return result;
            } else if (str.toString().equals("while")) {
                result = new Token(Token.WHILE);
                lastToken = result;
                counter--;
                return result;
            } else {
                result = new Token(Token.ID, str.toString());
                lastToken = result;
                counter--;
                return result;
            }
        } else if (Character.isDigit(nextChar)) {
            StringBuilder num = new StringBuilder();
            num.append(nextChar);
            getChar();
            while (Character.isDigit(nextChar)) {
                num.append(nextChar);
                getChar();
            }
            result = new Token(Token.INT, Integer.parseInt(num.toString()));
            lastToken = result;
            counter--;
            return result;
        } else if (nextChar == '(') {
            result = new Token(Token.CLPAREN);
            lastToken = result;
            return result;
        } else if (nextChar == '=') {
            getChar();
            if (nextChar == '=') {
                result = new Token(Token.EE);
            } else {
                counter--;
                result = new Token(Token.EQ);
            }
            lastToken = result;
            return result;
        } else if (nextChar == '<') {
            getChar();
            if (nextChar == '=') {
                result = new Token(Token.LTE);
            } else {
                counter--;
                result = new Token(Token.LT);
            }
            lastToken = result;
            return result;
        } else if (nextChar == '>') {
            getChar();
            if (nextChar == '=') {
                result = new Token(Token.GTE);
            } else {
                counter--;
                result = new Token(Token.GT);
            }
            lastToken = result;
            return result;
        } else if (nextChar == '!') {
            getChar();
            if (nextChar == '=') {
                result = new Token(Token.NE);
            } else {
                counter--;
                result = new Token(Token.N);
            }
            lastToken = result;
            return result;
        } else if (nextChar == ')') {
            result = new Token(Token.CRPAREN);
            lastToken = result;
            return result;
        } else if (nextChar == '$') {
            result = new Token(Token.EOF, "$");
            lastToken = result;
            return result;
        }
        return new Token(Token.OTHERS);
    }
}
