import java.util.ArrayList;

abstract class Node {
    ArrayList<Node> children;
}

public class Program extends Node {
    Statement statementNode;

    public Program(Statement statementNode) {
        this.statementNode = statementNode;
        super.children = new ArrayList<>();
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
        super.children = new ArrayList<>();
        children.add(this.SNodeA);
    }

    public Statement(Statement SNodeA, Statement SNodeB) {
        this.SNodeA = SNodeA;
        this.SNodeB = SNodeB;
        super.children = new ArrayList<>();
        children.add(this.SNodeA);
        children.add(this.SNodeB);
    }

    public Statement(AssignStatement assignStatement) {
        this.assignStatement = assignStatement;
        super.children = new ArrayList<>();
        children.add(this.assignStatement);
    }

    public Statement(IfStatement If) {
        this.If = If;
        super.children = new ArrayList<>();
        children.add(this.If);
    }

    public Statement(WhileStatement WhileStatement) {
        this.While = WhileStatement;
        super.children = new ArrayList<>();
        children.add(this.While);
    }

    @Override
    public String toString() {
        return "Statement";
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
        super.children = new ArrayList<>();
        children.add(this.identifierNode);
        children.add(this.equalNode);
        children.add(this.expressionNode);
    }

    @Override
    public String toString() {
        return "Assign";
    }
}

class Equal extends Node {
    String name;

    public Equal() {
        this.name = "=";
    }

    @Override
    public String toString() {
        return "=";
    }
}

class IfStatement extends Node {
    CompareStatement compareStatement;
    Statement statement;

    public IfStatement(CompareStatement compareStatement, Statement statement) {
        this.compareStatement = compareStatement;
        this.statement = statement;
        super.children = new ArrayList<>();
        children.add(this.compareStatement);
        children.add(this.statement);
    }

    @Override
    public String toString() {
        return "If";
    }
}

class WhileStatement extends Node {
    CompareStatement compareStatement;
    Statement statement;

    public WhileStatement(CompareStatement compareStatement, Statement statement) {
        this.compareStatement = compareStatement;
        this.statement = statement;
        super.children = new ArrayList<>();
        children.add(this.compareStatement);
        children.add(this.statement);
    }

    @Override
    public String toString() {
        return "While";
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
        super.children = new ArrayList<>();
        children.add(this.idA);
        children.add(this.compOp);
        children.add(this.number);
    }

    public CompareStatement(ID idA, CompOp compOp, ID idB) {
        this.idA = idA;
        this.compOp = compOp;
        this.idB = idB;
        super.children = new ArrayList<>();
        children.add(this.idA);
        children.add(this.compOp);
        children.add(this.idB);
    }

    @Override
    public String toString() {
        return "Comparison";
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
        super.children = new ArrayList<>();
        children.add(this.equal);
    }

    @Override
    public String toString() {
        if (equal != null) {
            return "CompOp";
        } else {
            return this.name;
        }
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
        super.children = new ArrayList<>();
        children.add(this.expr);
        children.add(this.operation);
        children.add(this.term);
    }

    public Expr(Term term) {
        this.term = term;
        super.children = new ArrayList<>();
        children.add(this.term);
    }

    @Override
    public String toString() {
        return "Expr";
    }
}

class OperationA extends Node {
    String operator;

    public OperationA(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return this.operator;
    }
}

class OperationB extends Node {
    String operator;

    public OperationB(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return this.operator;
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
        super.children = new ArrayList<>();
        children.add(this.term);
        children.add(this.operation);
        children.add(this.factor);
    }

    public Term(Factor factor) {
        this.factor = factor;
        super.children = new ArrayList<>();
        children.add(this.factor);
    }

    @Override
    public String toString() {
        return "Term";
    }
}

class Factor extends Node {
    Expr expr;
    ID id;
    Number number;

    public Factor(Expr expr) {
        this.expr = expr;
        super.children = new ArrayList<>();
        children.add(this.expr);
    }

    public Factor(ID id) {
        this.id = id;
        super.children = new ArrayList<>();
        children.add(this.id);
    }

    public Factor(Number number) {
        this.number = number;
        super.children = new ArrayList<>();
        children.add(this.number);
    }

    @Override
    public String toString() {
        return "Factor";
    }
}

class Number extends Node {
    int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}

class ID extends Node {
    String id;

    public ID(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
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

