import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class testParser {
    public static void main(String[] args) throws IOException {
        parser p = new parser("code.txt", 66, 18, 15, 32);
        System.out.println(p.parse());
//        String regex = "a5";
//        System.out.println(regex.matches("[a-zA-Z][a-zA-Z0-9_]*"));
    }
}
