import java.io.IOException;

public class testParser {
    public static void main(String[] args) throws IOException {
        parserAST p = new parserAST("code.txt", 66, 18, 15, 32);
        System.out.println(p.parse());
    }
}
