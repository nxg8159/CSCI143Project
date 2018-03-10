package dendron.tree;

import dendron.Errors;
import dendron.machine.*;
import java.util.*;

public class Variable implements ExpressionNode {

    private String name;

    public Variable(String name){
        //Set the name of this new Variable
        this.name = name;
    }

    public java.util.List<Machine.Instruction> emit(){
        //Emit a load instruction that pushes the variable's value onto the stack
        List<Machine.Instruction> q = new LinkedList<>();
        System.out.println(this.name);
        System.out.println();
        q.add(new Machine.Load(this.name));
        return q;
    }

    public int evaluate(java.util.Map<String,Integer> symTab){
        //evaluate a variable by fetching its value
        if(symTab.containsKey(name)){
            return symTab.get(this.name);
        }
        else{
            Errors.report(Errors.Type.UNINITIALIZED,"Tried to access a variable that wasn't initialized");
            return 0;
        }
    }

    public void infixDisplay(){
        //Print on standard output the Variable's name
        System.out.print(this.name);
    }
}
