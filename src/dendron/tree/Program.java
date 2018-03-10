package dendron.tree;

import dendron.machine.*;
import java.util.*;

public class Program implements ActionNode {

    public LinkedList<ActionNode> nodes;

    public Program(){
        //Initialize this instance as an empty sequence of ActionNode children
        this.nodes = new LinkedList<>();
    }

    public void addAction(ActionNode newNode){
        //Add a child of this program node
            this.nodes.add(newNode);
    }

    public java.util.List<Machine.Instruction> emit(){
        //Create a list of instructions emitted by each child, from the first added child to the last-added
        List<Machine.Instruction> q = new LinkedList<>();
        for (ActionNode aN: this.nodes) {
            q.addAll(aN.emit());
        }
        return q;
    }

    public void execute(java.util.Map<String, Integer> symTab){
        //execute each ActionNode in this object from first-added to last-added
        for (ActionNode aN: this.nodes) {
            aN.execute(symTab);
        }
    }

    public void infixDisplay(){
        //Show the infix displays of all children on standard output
        for (ActionNode aN:this.nodes) {
            aN.infixDisplay();
        }
    }
}
