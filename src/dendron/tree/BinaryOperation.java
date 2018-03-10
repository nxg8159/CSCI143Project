package dendron.tree;

import java.util.*;

import dendron.Errors;
import dendron.machine.Machine;

public class BinaryOperation implements ExpressionNode{
    private static String ADD = "+";
    private static String DIV = "/";
    private static String MUL = "*";
    private static String SUB = "-";
    public static java.util.Collection<String> OPERATORS = List.of(ADD,SUB,MUL,DIV);

    private String operator;
    private ExpressionNode right;
    private ExpressionNode left;

    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild){
        this.operator = operator;
        this.left = leftChild;
        this.right = rightChild;
        if(!OPERATORS.contains(this.operator)){
            dendron.Errors.report(Errors.Type.ILLEGAL_VALUE,this.operator);
        }
    }



    public java.util.List<Machine.Instruction> emit(){
        //Emit the Machine instructions neccessary to perform the computation of thie BinaryOP. The operator itself is
        //realized by an instruction that pops two values off the stack, applies the operator, and pushes the answer

        List<Machine.Instruction> q = this.left.emit();
        q.addAll(this.right.emit());

        //Check to see what the operator is, and add that instruction to the stack

        if(this.operator.equals(ADD)){
            q.add(new Machine.Add());
        }
        else if(this.operator.equals(DIV)){
            q.add(new Machine.Divide());
        }
        else if(this.operator.equals(SUB)){
            q.add(new Machine.Subtract());
        }
        else{
            q.add(new Machine.Multiply());
        }

        //Return the new list of commands

        return q;
    }

    public void infixDisplay(){
        //Print, on standard output, the infixDisplay of the two child nodes separated by the operator and surrounded by
        //parentheses. Blanks are inserted throughout
        System.out.print("(");
        left.infixDisplay();
        System.out.print(this.operator);
        right.infixDisplay();
        System.out.print(")");

    }

    public int evaluate(java.util.Map<String,Integer> symTab){
        //Compute the result of evaluating both operands and applying the operator to them
        if(this.operator.equals(ADD)){
            return left.evaluate(symTab) + right.evaluate(symTab);
        }
        else if(this.operator.equals(DIV)){
            return left.evaluate(symTab) / right.evaluate(symTab);
        }
        else if(this.operator.equals(MUL)){
            return left.evaluate(symTab) * right.evaluate(symTab);
        }
        else if(this.operator.equals(DIV)){
            if(right.evaluate(symTab) == 0){
                Errors.report(Errors.Type.DIVIDE_BY_ZERO,right);
            }
            return left.evaluate(symTab) / right.evaluate(symTab);
        }
        else{
            return left.evaluate(symTab) - right.evaluate(symTab);
        }
    }
}
