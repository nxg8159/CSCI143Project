package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;
import dendron.tree.ActionNode;
import dendron.tree.ExpressionNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Operations that are done on a Dendron code parse tree.
 *
 * THIS CLASS IS UNIMPLEMENTED. All methods are stubbed out.
 *
 * @author YOUR NAME HERE
 */
public class ParseTree {

    /**
     * Parse the entire list of program tokens. The program is a
     * sequence of actions (statements), each of which modifies something
     * in the program's set of variables. The resulting parse tree is
     * stored internally.
     * @param program the token list (Strings)
     */

    public List<String> program;
    public Map<String, Integer> symTab;
    //Long story as to why I named this yeet, I'll tell it to you sometime later
    ActionNode yeet;

    public ParseTree( List< String > program ) {
        this.program = program;
        this.symTab = new HashMap();
        yeet = this.parseAction(program);
    }

    /**
     * Parse the next action (statement) in the list.
     * (This method is not required, just suggested.)
     * @param program the list of tokens
     * @return a parse tree for the action
     */
    private ActionNode parseAction( List< String > program ) {
        Program p = new Program();
        String temp = program.get(0);
        List<String> tokenList = program;
        String token;
        System.out.println(tokenList);
        while(!tokenList.isEmpty()) {
            if (tokenList.get(0).equals(":=")) {
                token = tokenList.get(1);
                tokenList = tokenList.subList(2, tokenList.size());
                p.addAction(new Assignment(token,parseExpr(tokenList)));
            } else if (tokenList.get(0).equals("@")) {
                tokenList = tokenList.subList(1, tokenList.size());
                p.addAction(new Print(parseExpr(tokenList)));
            }
            else{
                Errors.report(Errors.Type.ILLEGAL_VALUE,"token");
            }
        }
        return p;
    }

    /**
     * Parse the next expression in the list.
     * (This method is not required, just suggested.)
     *
     * @return a parse tree for this expression
     */
    private ExpressionNode parseExpr( List< String > tokenList ) {
            String token = tokenList.remove(0);
            if (BinaryOperation.OPERATORS.contains(token)) {
                return new BinaryOperation(token, parseExpr(tokenList), parseExpr(tokenList));
            } else if (UnaryOperation.OPERATORS.contains(token)) {
                return new UnaryOperation(token, parseExpr(tokenList));
            } else if (token.matches("^[a-zA-Z].*")) {
                return new Variable(token);
            } else if (token.matches("[-+]?\\d+")) {
                return new Constant(Integer.parseInt(token));
            }
            else {
                Errors.report(Errors.Type.ILLEGAL_VALUE, "Illegal value in program");
                return null;
            }
    }

    /**
     * Print the program the tree represents in a more typical
     * infix style, and with one statement per line.
     * @see dendron.tree.ActionNode#infixDisplay()
     */
    public void displayProgram() {
        this.yeet.infixDisplay();
    }

    /**
     * Run the program represented by the tree directly
     * @see dendron.tree.ActionNode#execute(Map)
     */
    public void interpret() {
        this.yeet.execute(symTab);
    }

    /**
     * Build the list of machine instructions for
     * the program represented by the tree.
     * @return the Machine.Instruction list
     * @see Machine.Instruction#execute()
     */
    public List< Machine.Instruction > compile() {
        return this.yeet.emit();
    }

}
