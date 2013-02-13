package evaluatortester;

import java.util.*;
/**
 * @author Rachel Sershon
 * CSC 413.01
 * Date: February 13, 2013
 */
public class Evaluator {
    
    private Stack<Operand> opdStack;
    private Stack<Operator> oprStack;
        
    public Evaluator() {
        opdStack = new Stack<Operand>();
        oprStack = new Stack<Operator>();
         //initialize the HashMap with the operators we are using
        Operator.init();  
    }
    /* 
     * Allows access to operators HashMap
     */
    public static Operator getOpr(String tok) {
        return ((Operator)Operator.getOp(tok));
    }
    
    public int eval(String expr) {
        
        String tok;
        
        oprStack.push(getOpr("#"));
  
        String delimiters = "+-*/#!";
       
        StringTokenizer st = new StringTokenizer(expr, delimiters, true);
        
        while (st.hasMoreTokens()) {
            if(!(tok=st.nextToken()).equals(" ")) {
                if(Operand.check(tok)) {
                    opdStack.push(new Operand(tok));
                } else {
                    if(!Operator.check(tok)) {
                        System.out.println("*****invalid token*****");
                        System.exit(1);
                    }
                
                Operator newOpr = getOpr(tok);
                    
                while( ((Operator)oprStack.peek()).priority() >= newOpr.priority()) {
                    Operator oldOpr = ((Operator)oprStack.pop());
                    Operand op2 = (Operand)opdStack.pop();
                    Operand op1 = (Operand)opdStack.pop();
                    opdStack.push(oldOpr.execute(op1, op2));
                    
                }
                oprStack.push(newOpr);
                }  
                
            } 

        } 
        
        //process until initializing operator, #
        while (!oprStack.peek().getSymbol().equals("#")) {
            Operator currentOp = oprStack.pop();
            Operand opd2 = opdStack.pop();
            Operand opd1 = opdStack.pop();
                        
            opdStack.push(currentOp.execute(opd1, opd2));
        }
        
        return opdStack.pop().getValue();
    }
}  //end class Evaluator
    
class Operand {
    //constructors
    private int numValue;
            
    public Operand(String tok) { 
        try {
            numValue = Integer.parseInt(tok.trim());
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
    }
    
    public Operand(int value) { 
        numValue = value;
    }
    
    public static boolean check(String tok) {
        boolean isOperand = false;
        //assuming everything that is not an operator is an operand
        if (!Operator.check(tok)) {
            isOperand = true;
        }
        return isOperand;
    }
    
    public int getValue() {  return numValue; }
}

abstract class Operator {
    
    private static HashMap<String, Operator> operators = 
            new HashMap<String,Operator>();
    
    public abstract Operand execute(Operand op1, Operand op2);
    public abstract int priority();
    
    public static Operator getOp(String tok) {
        return operators.get(tok);
    }
    
    public abstract String getSymbol();
    
    static void init() {
        operators.put("+", new AdditionOperator());
        operators.put("-", new SubtractionOperator());
        operators.put("*", new MultiplicationOperator());
        operators.put("/", new DivisionOperator());
        operators.put("#", new PoundOperator());
    }
    
    static boolean check(String tok){ 
        boolean isThere = false;
        if (operators.containsKey(tok)) {
            isThere = true;
        }
        return isThere; }
    
}


class AdditionOperator extends Operator {

    public AdditionOperator() {}
    
    @Override
    public Operand execute(Operand op1, Operand op2) {
        //do an addition, return the sum
        Operand sum = new Operand(op1.getValue()+op2.getValue());
        return sum;
    }

    @Override
    public int priority() {
        int priority = 2;
        return priority;
    }

    @Override
    public String getSymbol() {
        return("+");
    }
    
}

class SubtractionOperator extends Operator {

    public SubtractionOperator() { }
    
    @Override
    public Operand execute(Operand op1, Operand op2) {
        Operand difference = new Operand(op1.getValue()-op2.getValue());
        return difference;
    }

    @Override
    public int priority() {
        int priority = 2;
        return priority;
    }
    
    @Override
    public String getSymbol() {
        return("-");
    }
    
}

class MultiplicationOperator extends Operator {

    public MultiplicationOperator() { }
    
    @Override
    public Operand execute(Operand op1, Operand op2) {
        Operand product = new Operand(op1.getValue()*op2.getValue());
        return product;
    }

    @Override
    public int priority() {
        int priority = 3;
        return priority;
    }
    
    @Override
    public String getSymbol() {
        return("*");
    }
    
}

class DivisionOperator extends Operator {

    public DivisionOperator() { }
    
    @Override
    public Operand execute(Operand op1, Operand op2) {
        Operand quotient = new Operand(op1.getValue()/op2.getValue());
        return quotient;
    }

    @Override
    public int priority() {
        int priority = 3;
        return priority;
    }
    
    @Override
    public String getSymbol() {
        return("/");
    }
    
}

class PoundOperator extends Operator {

    public PoundOperator() { }
    
    @Override
    public Operand execute(Operand op1, Operand op2) { 
        if (op1 == null) {
            return op2;
        }
        else {
            return null;
        }
    }

    @Override
    public int priority() {
        int priority = 1;
        return priority;
    }
    
    @Override
    public String getSymbol() {
        return("#");
    }
}