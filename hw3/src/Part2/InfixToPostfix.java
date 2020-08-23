package Part2;

import java.util.EmptyStackException;
import java.util.Scanner;


public class InfixToPostfix {

    //Nested Class
    /*** Class to report a syntax error.*/
    public static class SyntaxErrorException extends Exception {
        /**
         * Construc a SyntaxErrorException with the specified message.
         * @param message The message
         */
        SyntaxErrorException(String message) {
            super(message);
        }
    }

    /*** Collects the operator like a stack*/
    private String[] operatorArray;

    /*** Collects the operands to evaluate*/
    private String[] arr;

    /*** Index to top of stack*/
    int topOfStack = -1; //initially empty stack.

    /*** capacity of stack array*/
    private int cap = 10;

    /*** size of stack array*/
    private int size=0;

    /*** The operators*/
    private static final String OPERATORS = "+"+"-"+"*"+"/"+"("+")"+"sin"+"cos"+"abs"+"="+".";

    /*** The precedence of the operators, matches order of OPERATORS.*/
    private static final int[] PRECEDENCE = {1, 1, 2, 2, -1, -1,3,4,5,4,4,4,4};

    /*** The postfix string*/
    public StringBuilder postfix;

    /*** for input variable*/
    private String var1;

    /*** for input variable's values*/
    private String var2;

    /*** this variable using for keeping variables*/
    private int m=2;

    /*** keeps input variables*/
    private String[] neww= new String[200];

    /*** No parameter constructor initializes arrays*/
    public InfixToPostfix() {
        operatorArray = new  String[size];
        postfix = new StringBuilder();
        arr= new String[size];
    }

    /** Insert a new item on top of the stack.
     post: The new item is the top item on the stack.
     All other items are one position lower.
     @param obj The item to be inserted
     @return The item that was inserted
     */
    public String push(String obj) {
        if (topOfStack == operatorArray.length - 1) {
            reallocate();
        }
        topOfStack++;
        operatorArray[topOfStack] = obj;
        size++;
        return obj;
    }

    /** Remove and return the top item on the stack.
     pre: The stack is not empty.
     post: The top item on the stack has been
     removed and the stack is one item smaller.
     @return The top item on the stack
     @throws EmptyStackException if the stack is empty
     */
    public String pop() {
        if (empty())
            throw new EmptyStackException();
        size--;
        return operatorArray[topOfStack--];
    }

    /** Return the top item on the stack
     Pre: The stack is not empty
     Post: The stack remains unchanged
     @return The top item on the stack
     @throws EmptyStackException If the stack
     is empty
     */
    public String peek() {
        if (empty())
            throw new EmptyStackException();
        return operatorArray[topOfStack];
    }

    /** Return true if the stack is empty
     @return True if the stack is empty
     */
    private boolean empty() {
        return topOfStack == -1;
    }

    /** Method to reallocate the array containing the stack data
     Postcondition: The size of the data array has been doubled
     and all of the data has been copied to the new array
     */
    private void reallocate() {
        String[] temp = new String[2 * cap];
        cap*=2;
        System.arraycopy(operatorArray, 0, temp, 0, operatorArray.length);
        operatorArray = temp;
    }

    /**
     * Convert a string from infix to postfix
     * @param infix The infix expression
     * @return postfix
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public String convert(String infix) throws SyntaxErrorException {
        try {
            //Process each token in the infix string
            String nextToken;
            Scanner scan = new Scanner(infix);
            while ((nextToken = scan.findInLine("[\\p{L}\\p{N}\\.\\-]+|[\\=+/\\*()]")) != null) { //to see +,*,-,/,(,) operators keep going

                char firstChar = nextToken.charAt(0); //looks the firs char of elements of string

                //If nextToken is character or digit include negative numbers and except 'sin', 'cos', 'abs' because they are operators
                if ( (!nextToken.equals("sin") && !nextToken.equals("cos") && !nextToken.equals("abs") && Character.isJavaIdentifierStart(firstChar))
                        || (Character.isDigit(firstChar)) || (firstChar=='-' && nextToken.length()>1)) {
                    postfix.append(nextToken); //appends in the string
                    postfix.append(' ');
                }
                //Is an operator?
                else if (isOperator(nextToken))
                    processOperator(nextToken); //go to processOperator to process
                else
                    throw new SyntaxErrorException("Unexpected String Encountered: " + firstChar);

            }// end while loop

            //Pop any remaining operators and append them to postfix
            while (!empty()) {
                String  op = pop();
                //Any '(' on the stack is not matched
                if (op == "(")
                    throw new SyntaxErrorException("Unmatching opening paranthesis");
                postfix.append(op);
                postfix.append(' ');
            }
            //assert: stack is empty, return result.
            return postfix.toString();
        } catch (EmptyStackException ex) {
            throw new SyntaxErrorException("Syntax Error: The stack is empty.");
        }
    }

    /**
     * Method to process operators
     * @param op The operator
     * @throws EmptyStackException
     */
    private void processOperator(String  op){
        //These operators are pushes whether they are
        if (empty() || op.equals("(") || op.equals("sin") || op.equals("cos") || op.equals("abs") || op.equals("="))
            push(op);
        else{
            //Peek the operator stack and let topOp be the top operator
            String  topOp=peek();
            //If precedence is bigger then push it otherwise pop the top element
            if(precedence(op) > precedence(topOp))
                push(op);
            else{
                while (!empty()&& precedence(op) <= precedence(topOp)) {
                    pop();
                    if (topOp.equals("("))
                        break; // Matching '(' popped - exit loop.

                    postfix.append(topOp);
                    postfix.append(' ');
                    if (!empty())
                        topOp = peek(); // Reset topOp.
                }
                //assert: Operator stack is empty or current operator precedence > top of stack operator precedence
                if(!op.equals(")")) {
                    push(op);
                }
            }
        }
    }

    /**
     * Evaluates a postfix expression
     * @param expression The expression to be evaluated
     * @return The value of the expression
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public double eval(String expression) throws SyntaxErrorException{
        String nextToken;
        Scanner scan = new Scanner(expression);
        try {
            //Process each token in the infix string
            while ((nextToken = scan.findInLine("[\\p{L}\\p{N}\\.\\-]+|[\\=+/\\*()]")) != null) {
                char firstChar = nextToken.charAt(0);

                //If nextToken is character or digit include negative numbers and except 'sin', 'cos', 'abs' because they are operators
                if((!nextToken.equals("sin") && !nextToken.equals("cos") && !nextToken.equals("abs") && Character.isJavaIdentifierStart(firstChar))
                       || (Character.isDigit(firstChar)) || (firstChar=='-' && nextToken.length()>1)) {
                    //firstChar can be normal variable or input varible. If it is input variable it has to take its value.
                    if(Character.isJavaIdentifierStart(firstChar)) {
                       for (int i = 0; i < neww.length; i++) {
                           if (nextToken.equals(neww[i])) {
                               nextToken = neww[i - 1];
                           }
                       }
                   }
                    push(nextToken);
                }
                else if(firstChar=='=') //does assign operator
                {
                    String rhs=pop();
                    var1=rhs;
                    String lhs=pop();
                    var2=lhs;
                    //neww array collects the element that they are input variables
                    neww[m-2]=var1;
                    neww[m-1]=var2;

                    lhs=rhs; //variable takes its value

                    m+=2;
                }
                //is it an operator
                else if (isOperator(nextToken)) {
                    //Evaluate the operator
                    double result = evalOp(firstChar);
                    //push result onto the arr
                    push(Double.toString(result));
                } else {
                    throw new SyntaxErrorException("Invalid character encountered: " + firstChar);
                }
            }

            //No more tokens-pop result from operand stack.
            String answer = pop();

            //Operand stack should be empty
            if (empty()) {
                return Double.parseDouble(answer);
            }
            else {
                throw new SyntaxErrorException("Syntax Error: The should be empty.");
            }
        }catch (EmptyStackException ex){
            throw new SyntaxErrorException("Syntax Error: The stack is empty.");
        }
    }

    /**
     * Evaluates the current operation.
     * This function pops the operands anf applies the operator
     * @param op A character representing the operator
     * @return The result of applying the operator
     * @throws EmptyStackException if pop is attempted on an empty stack array.
     * @throws ArithmeticException if numerator is zero in '/' operator
     */
    private double evalOp(char op) throws SyntaxErrorException {
        double result=0;
        try{
            //If the operator is one of them 'sin', 'cos' or 'abs', pops one operand than evaluate
            if(op=='s' || op=='c' || op=='a'){
                double rhs=Double.parseDouble(pop());
                switch (op){
                    case 's' : result = (sin(rhs));
                        break;
                    case 'c' : result = (cos(rhs));
                        break;
                    case 'a' : result = (abs(rhs));
                        break;
                }
            }
            //If the operator is one of them '+' , '-' , '*' and '/' pops two operands than evaluate
            else {
                double rhs = Double.parseDouble(pop());
                double lhs = Double.parseDouble(pop());

                switch (op) {
                    case '+':
                        result = (lhs + rhs);
                        break;
                    case '-':
                        result = (lhs - (rhs));
                        break;
                    case '*':
                        result = ((lhs) * (rhs));
                        break;
                    case '/':
                        result = (lhs / rhs);
                        break;
                }
            }
        }catch (ArithmeticException e){
            throw new SyntaxErrorException("Numerator can not be zero.");
        }
      //  System.out.println("res "+result);
        return result;
    }

    /**
     * Determine whether a String is an operator
     * @param ch The String to be tested
     * @return true if ch is an operator
     */
    private boolean isOperator(String  ch){
        return OPERATORS.indexOf(ch)!=-1;
    }

    /**
     * Determine the precedence of an operator
     * @param op The operator
     * @return the precedence
     **/
    private int precedence(String  op){
        return PRECEDENCE[OPERATORS.indexOf(op)];
    }

    /**
     * Determine the sinus value of the given value from taylor theorem
     * @param value to be determined
     * @return the sinus value
     */
    public double sin(double value) {
        value = value / 180 * 3.14; //radian value

        double result;
        result =  (value - value * value * value / (3 * 2)) + (value * value * value * value * value) / (5 * 4 * 3 * 2)
                  - (value * value * value * value * value * value * value) / (7 * 6 * 5 * 4 * 3 * 2)
                  + (value * value * value * value * value * value * value * value * value) / (9 * 8 * 7 * 6 * 5 * 4 * 3 * 2);
         return result;
    }

    /**
     * Determine the cosinus value of the given value from taylor theorem
     * @param value to be determined
     * @return the cosinus value
     */
    public double cos(double value){
        value = value / 180 * 3.14; //radian value

        double result;
        result = 1 - (value * value) / 2 + (value * value * value * value) / (4 * 3 * 2)
                   - (value * value * value * value * value * value) / (6 * 5 * 4 * 3 * 2)
                   + (value * value * value * value * value * value * value * value) / (8 * 7 * 6 * 5 * 4 * 3 * 2);
        return result;
    }

    /**
     * Determine the absolute value of the given value
     * @param value to be determined
     * @return the absolute value
     */
    public double abs(double value){
        if (value<0)
            value= -value;
        return value;
    }
}

