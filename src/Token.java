public class Token extends Node {
    private int kind; // token’s lexical class
    private int intVal; // integer value if class = INT
    private String id;// actual identifier if class = ID
    private boolean isInt;
    // lexical classes
    public static final int EOF = 0; // “end of file” token
    public static final int ID = 1; // identifier, not keyword
    public static final int INT = 2; // integer
    public static final int WHILE = 6;
    public static final int IF = 8;
    public static final int OTHERS = 9;
    public static final int CLPAREN = 10;
    public static final int CRPAREN = 11;
    public static final int LT = 12;
    public static final int LTE = 13;
    public static final int GT = 14;
    public static final int GTE = 15;
    public static final int EE = 16;
    public static final int NE = 17;
    public static final int N = 19;
    public static final int EQ = 18;
    public static final int P = 20;
    public static final int M = 21;
    public static final int MU = 22;
    public static final int D = 23;

    public Token(int kind, int intVal) {
        this.kind = kind;
        this.intVal = intVal;
        this.isInt = true;
    }

    public Token(int kind, String id) {
        this.kind = kind;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Token(int kind) {
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }

    public String getID() {
        return id;
    }

    public int getIntVal() {
        return intVal;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("TOKEN: { ");
        str.append("kind: ");
        switch (kind) {
            case 0:
                str.append("EOF");
                break;
            case 1:
                str.append("ID");
                break;
            case 2:
                str.append("INT");
                break;
            case 6:
                str.append("WHILE");
                break;
            case 8:
                str.append("IF");
                break;
            case 9:
                str.append("OTHER");
                break;
            case 12:
                str.append("LT");
                break;
            case 13:
                str.append("LTE");
                break;
            case 14:
                str.append("GT");
                break;
            case 15:
                str.append("GTE");
                break;
            case 16:
                str.append("EE");
                break;
            case 17:
                str.append("NE");
                break;
            case 18:
                str.append("EQ");
                break;
            case 10:
                str.append("CLPAREN");
                break;
            case 11:
                str.append("CRPAREN");
                break;
        }
        str.append(' ');
        if (id != null) {
            str.append("id: ").append(id).append(" }").append('\n');
        } else if (isInt) {
            str.append("value: ").append(intVal).append(" }").append('\n');
        } else {
            str.append(" }").append('\n');
        }
        return str.toString();
    }
}