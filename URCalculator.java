/*
------------------------
Author: Matvii Repetskyi
------------------------
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class URCalculator {
    public String input_path;
    public String output_path;

    //Constructor
    public URCalculator(String input_path, String output_path) {
        this.input_path = input_path;
        this.output_path = output_path;
    }

    public void calculate(){
        //reading lines and adding them to ArrayList
        ArrayList<String> input_list = new ArrayList<>();
        try {
            File file = new File(input_path);
            Scanner s = new Scanner(file);

            while(s.hasNextLine()) {
                String line = s.nextLine();
                input_list.add(line);
            }
            s.close();
        } catch (Exception e) {
            System.out.println("File not found.");
        }

        //infix to postfix conversion and postfix evaluation
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output_path))) {

            //Comment out if you don't want name and NetID in the beginning
            writer.write("Name: Matvii Repetskyi\nNetID: mrepetsk\n");
            writer.newLine();
            //going through expressions on each line
            for (String line : input_list) {
                List<String> tokenList = tokenize(line);
                Queue<String> queue = new Queue<>();
                Stack<String> stack = new Stack<>();

                //going through tokens on a single line
                for (String token : tokenList) {
                    //if token is an operand
                    if (!Operator.isOperator(token) && !token.equals("(") && !token.equals(")")) {
                        queue.enqueue(token);
                    }

                    // If token is '(', push to stack
                    else if (token.equals("(")) {
                        stack.push(token);
                    }

                    // If token is ')', pop from stack to queue until '(' is found
                    else if (token.equals(")")) {
                        while (!stack.isEmpty() && !stack.peek().equals("(")) {
                            queue.enqueue(stack.pop());
                        }
                        if (!stack.isEmpty() && stack.peek().equals("(")) {
                            stack.pop(); //Remove '(' from stack
                        }
                        else {
                            System.out.println("Mismatched parantheses");
                            queue.clear();
                            break;
                        }

                    }
                    //if token is an operator
                    else if(Operator.isOperator(token)) {
                        Operator o1 = Operator.getOperator(token);
                        while(!stack.isEmpty() && Operator.isOperator(stack.peek())) {
                            Operator o2 = Operator.getOperator(stack.peek());

                            //uses operator precedence to correctly order postfix expression
                            if ((o1.getAssociativity() == Associativity.LEFT && o1.getPrecedence() <= o2.getPrecedence()) ||
                                    (o1.getAssociativity() == Associativity.RIGHT && o1.getPrecedence() < o2.getPrecedence())) {
                                queue.enqueue(stack.pop());
                            }
                            else {
                                break;
                            }
                        }
                        stack.push(token);
                    }
                }
                //if end of input - pop every token from stack and enqueue one by one
                while (!stack.isEmpty()) {
                    String top = stack.pop();
                    if (top.equals("(") || top.equals(")")) {
                        System.out.println("Mismatched parantheses");
                        queue.clear();
                        return;
                    }
                    queue.enqueue(top);
                }

                //postfix evaluation part
                while (!queue.isEmpty()) {
                    String token = queue.dequeue();

                    //if token is operand - push onto stack
                    if (!Operator.isOperator(token)) {
                        stack.push(token);
                    }
                    //if token is an operator
                    else {
                        // if token not !, take two operands
                        if (!token.equals("!")) {
                            double n1 = Double.parseDouble(stack.pop());
                            double n2 = Double.parseDouble(stack.pop());

                            String new_value = String.valueOf(performOperation(n1, n2, token));
                            stack.push(new_value);
                        }
                        // if token is !, take one operand
                        else {
                            double n1 = Double.parseDouble(stack.pop());
                            double result = 0;
                            if (n1 == 0) {
                                result = 1;
                            }
                            else {
                                result = 0;
                            }

                            String new_value = String.valueOf(result);
                            stack.push(new_value);
                        }
                    }
                }

                String expr_result = String.format("%.2f", Double.parseDouble(stack.pop()));

                writer.write(expr_result.toString().trim());
                writer.newLine(); // Move to the next line for the next input
            }
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
    }

    public double performOperation(double n1, double n2, String token) {
        double result = 0;

        switch (token) {
            case "+":
                result = n2 + n1;
                break;
            case "-":
                result = n2 - n1;
                break;
            case "*":
                result = n2 * n1;
                break;
            case "/":
                result = n2 / n1;
                break;
            case "<":
                if (n2 < n1) {
                    result = 1;
                }
                else {
                    result = 0;
                }
                break;
            case ">":
                if (n2 > n1) {
                    result = 1;
                }
                else {
                    result = 0;
                }
                break;
            case "&":
                if (n1 == 1 && n2 == 1) {
                    result = 1;
                }
                else {
                    result = 0;
                }
                break;
            case "|":
                if (n1 == 1 || n2 == 1) {
                    result = 1;
                }
                else {
                    result = 0;
                }
                break;
            case "=":
                if (n1 == n2) {
                    result = 1;
                }
                else {
                    result = 0;
                }
                break;
        }
        return result;
    }

    //method that turns tokens on the line into a list of Strings
    public List<String> tokenize(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder numberBuffer = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (Character.isWhitespace(ch)) {
                continue; // Skip whitespaces
            }

            if (Character.isDigit(ch) || ch == '.') {
                numberBuffer.append(ch);
            } else {
                if (numberBuffer.length() > 0) {
                    tokens.add(numberBuffer.toString());
                    numberBuffer.setLength(0);
                }
                if (ch == '(' || ch == ')' || Operator.isOperator(Character.toString(ch))) {
                    tokens.add(Character.toString(ch));
                } else {
                    // Handle invalid characters if necessary
                    System.out.println("Invalid character: " + ch);
                }
            }
        }

        // Add the last number if present
        if (numberBuffer.length() > 0) {
            tokens.add(numberBuffer.toString());
        }

        return tokens;
    }

    //main method only used to instantiate URCalculator and run calculate()
    public static void main(String[] args) {
        String input_path = "";
        String output_path = "";

        if (args.length > 0) {
            input_path = args[0];
            output_path = args[1];
        }

        URCalculator myCalculator = new URCalculator(input_path,output_path);
        myCalculator.calculate();
    }
}


