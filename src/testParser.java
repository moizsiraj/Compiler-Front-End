import java.io.IOException;

public class testParser {
    public static void main(String[] args) throws IOException {
//        parser p = new parser("code.txt", 66, 18, 15, 32);
//        System.out.println(p.parse());
        parserAST p = new parserAST("code.txt", 66, 18, 15, 32);
        System.out.println(p.parse());
//        String regex = "a5";
//        System.out.println(regex.matches("[a-zA-Z][a-zA-Z0-9_]*"));
    }
}
