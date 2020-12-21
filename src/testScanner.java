import java.io.FileWriter;
import java.io.IOException;

public class testScanner {
    public static void main(String[] args) throws IOException {
//        scanner scanner = new scanner("code.txt");
//        FileWriter firstWriter = new FileWriter("token.txt");
//        Token token = scanner.getToken();
//        System.out.println(token);
//        while (token.getKind() != Token.EOF) {
//            token = scanner.getToken();
//            if(token.getKind() != 9){
//                firstWriter.write(token.toString());
//                System.out.println(token);
//            }
//        }
//        firstWriter.close();
        String regex = "(";
        System.out.println(regex.matches("\\("));
    }
}
