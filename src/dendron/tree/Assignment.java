package dendron.tree;

import dendron.machine.Machine;
import java.util.*;

public class Assignment implements ActionNode {
    private String ident;
    private ExpressionNode rhs;

    /**
     * Sets up an assignment node
     * @param ident
     * @param rhs
     */
    public Assignment(String ident, ExpressionNode rhs){
        this.ident = ident;
        this.rhs = rhs;
    }

    /**
     * Evaluate instructions, push the value onto the stack
     * @return
     */
    public java.util.List<Machine.Instruction> emit(){
        List<Machine.Instruction> q = this.rhs.emit();
        q.add(new Machine.Store(ident));
        return q;
    }

    public void execute(java.util.Map<String, Integer> symTab){
        symTab.put(ident,rhs.evaluate(symTab));
    }

    /**
     * Shows assignment of variable in standard output
     */
    public void infixDisplay(){
        System.out.print(ident + " := ");
        rhs.infixDisplay();
        System.out.println();
    }
}
