package dendron.tree;

import java.util.*;
import dendron.machine.*;

public class Print implements ActionNode {

    private ExpressionNode eNode;

    public Print(ExpressionNode eNode){
        this.eNode = eNode;
    }

    public void execute(Map<String, Integer> symTab){
        System.out.println("===" + eNode.evaluate(symTab));
    }

    public void infixDisplay(){
        System.out.print("PRINT ");
        eNode.infixDisplay();
    }

    public List<Machine.Instruction> emit(){
        List<Machine.Instruction> q = eNode.emit();
        q.add(new Machine.Print());
        return q;
    }
}
