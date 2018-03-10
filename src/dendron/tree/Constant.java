package dendron.tree;

import dendron.machine.Machine;
import java.util.*;

public class Constant implements ExpressionNode {

    private int value;

    public Constant(int value){
        //Store the integer value in this new constant
        this.value = value;
    }

    public java.util.List<Machine.Instruction> emit(){
        //emit an instruction to push a value onto the stack
        List<Machine.Instruction> q = new LinkedList<>();
        q.add(new Machine.PushConst(this.value));
        return q;
    }

    public int evaluate(java.util.Map<String, Integer> symTab){
        //Evaluate the constant
        return this.value;
    }

    public void infixDisplay(){
        //Print the constant's value on standard output
        System.out.print(this.value);
    }
}
