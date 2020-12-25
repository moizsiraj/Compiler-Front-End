abstract class Node {
}

public class Program extends Node {
    Statement statementNode;

    public Program(Statement statementNode) {
        this.statementNode = statementNode;
    }
}

class Statement extends Node {
    Statement SNodeA;
    Statement SNodeB;
    AssignStatement assignStatement;
    IfStatement If;
    WhileStatement While;

    public Statement(Statement SNode) {
        this.SNodeA = SNode;
    }

    public Statement(Statement SNodeA, Statement SNodeB) {
        this.SNodeA = SNodeA;
        this.SNodeB = SNodeB;
    }

    public Statement(AssignStatement assignStatement) {
        this.assignStatement = assignStatement;
    }

    public Statement(IfStatement If) {
        this.If = If;
    }

    public Statement(WhileStatement WhileStatement) {
        this.While = WhileStatement;
    }
}


class AssignStatement extends Node {
    ID identifierNode;
    Equal equalNode;
    Expr expressionNode;

    public AssignStatement(ID identifierNode, Equal equalNode, Expr expressionNode) {
        this.identifierNode = identifierNode;
        this.equalNode = equalNode;
        this.expressionNode = expressionNode;
    }
}

class Equal extends Node {
    String name;

    public Equal() {
        this.name = "=";
    }
}

class IfStatement extends Node {
    CompareStatement compareStatement;
    Statement statement;

    public IfStatement(CompareStatement compareStatement, Statement statement) {
        this.compareStatement = compareStatement;
        this.statement = statement;
    }
}

class WhileStatement extends Node {
    CompareStatement compareStatement;
    Statement statement;

    public WhileStatement(CompareStatement compareStatement, Statement statement) {
        this.compareStatement = compareStatement;
        this.statement = statement;
    }
}

class CompareStatement extends Node { //Y
    ID idA;
    CompOp compOp;
    Number number;
    ID idB;

    public CompareStatement(ID idA, CompOp compOp, Number number) {
        this.idA = idA;
        this.compOp = compOp;
        this.number = number;
    }

    public CompareStatement(ID idA, CompOp compOp, ID idB) {
        this.idA = idA;
        this.compOp = compOp;
        this.idB = idB;
    }

}


class CompOp extends Node {
    String name;
    Equal equal;

    public CompOp(String name) {
        this.name = name;
    }

    public CompOp(Equal equal) {
        this.equal = equal;
    }
}

class Expr extends Node {
    Expr expr;
    OperationA operation;
    Term term;

    public Expr(Expr expr, OperationA operation, Term term) {
        this.expr = expr;
        this.operation = operation;
        this.term = term;
    }

    public Expr(Term term) {
        this.term = term;
    }
}

class OperationA extends Node {
    String operator;

    public OperationA(String operator) {
        this.operator = operator;
    }
}

class OperationB extends Node {
    String operator;

    public OperationB(String operator) {
        this.operator = operator;
    }
}

class Term extends Node {
    Term term;
    OperationB operation;
    Factor factor;

    public Term(Term term, OperationB operation, Factor factor) {
        this.term = term;
        this.operation = operation;
        this.factor = factor;
    }

    public Term(Factor factor) {
        this.factor = factor;
    }
}

class Factor extends Node {
    Expr expr;
    ID id;
    Number number;

    public Factor(Expr expr) {
        this.expr = expr;
    }

    public Factor(ID id) {
        this.id = id;
    }

    public Factor(Number number) {
        this.number = number;
    }
}

class Number extends Node {
    int number;

    public Number(int number) {
        this.number = number;
    }
}

class ID extends Node {
    String id;

    public ID(String id) {
        this.id = id;
    }
}

class State extends Node {
    private String state;

    public State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}

