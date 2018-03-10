package dendron.tree;

import dendron.Errors;
import dendron.machine.*;
import java.util.*;

public class UnaryOperation implements ExpressionNode{
    private static String NEG = "_";
    private static String SQRT = "#";
    public static java.util.Collection<String> OPERATORS = List.of(NEG,SQRT);

    private String op;
    private ExpressionNode exp;

    public UnaryOperation(String operator, ExpressionNode expr){
        this.exp = expr;
        this.op = operator;
        if(!OPERATORS.contains(this.op)){
            dendron.Errors.report(Errors.Type.ILLEGAL_VALUE,this.op);
        }
    }

    public java.util.List<Machine.Instruction> emit(){
        //Emit the Machine instructions neccessary to preform the computation of this UnaryOperation
        List<Machine.Instruction> q = this.exp.emit();
        if(this.op.equals(SQRT)){
            q.add(new Machine.SquareRoot());
        }
        else{
            q.add(new Machine.Negate());
        }
        return q;
    }

    public int evaluate(java.util.Map<String,Integer> symTab){
        if(this.op.equals(NEG)){
            return 0 - exp.evaluate(symTab);
        }
        else{
            if(exp.evaluate(symTab) < 0){
                Errors.report(Errors.Type.ILLEGAL_VALUE,this.exp);
            }
            return (int)Math.sqrt(exp.evaluate(symTab));
        }
    }

    public void infixDisplay(){
        //Print, on standard output, the infixDisplay of the child nodes preceded by the operator and
        //without an intervening blank
        if(this.op.equals("#")) {
            System.out.print("(#");
            exp.infixDisplay();
            System.out.print(")");
        }
        else{
            System.out.print("(_");
            exp.infixDisplay();
            System.out.print(")");
        }
    }
}
