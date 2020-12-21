import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class testParser {
    public static void main(String[] args) throws IOException {
        parser p = new parser("code.txt", 42, 12, 11, 21);
        System.out.println(p.parse());
    }
}
