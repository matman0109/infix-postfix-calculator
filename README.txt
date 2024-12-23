https://github.com/matman0109

The program takes in a txt file with mathematical expressions (a sample was uploaded to repo under the name "infix_expr_short.txt"), converts them from infix to postfix notation (more efficient for large inputs) and evaluates them line by line.

Here is an a sample command line input that can be used to try out the program:
java URCalculator inputFile.txt outputFile.txt


List of files:
README.txt
OUTPUT.txt - sample output of a program
Queue.java - custom queue implementation
Stack.java - custom stack implementation
Operator.java - implements precedence for operators to correctly order the mathematical expression in postfix form
URCalculator.java - main method and primary logic of the program
URLinkedList.java - custom linked list implementation
URList.java - custom list interface that was provided as part of the assignment to make this project
URNode.java - custom node implementation for my double-linked linked list


Synopsis:

- Note:
Comment out lines 45 and 46 to generate output without name and NetID in the beginning

1) Main method instantiates URCalculator object and calls its primary method .calculate() where all the actual magic takes place.

2) The code starts by reading the lines of the input and adding them to ArrayList

3) For each line we are performing the process as described below:
	1. We start by turning the operands and operators on a single line into actual tokens stored in the a list of Strings
	2. For every token in the token list we are performing different operations as described by shunting-yard algorithm (if token is operand; if token is parantheses, if token is operator, etc.)
	3. At the end of this process we get a queue with tokens in postfix order
	4. Postfix evaluation part begins by checking whether the token in queue is an operator or an operand
	5. If the token is an operator, a bunch of arithmetic or logical operations are performed on the values
	6. The result of all the operations above is written on a new line in the OUTPUT file and we move on to work on the next line

4) By the end of this process of reading lines, performing infix_2_postfix and evaluating postfix, and finally writing the output on new lines, we will have a complete OUTPUT file

Notable obstacles:
I had the issue of parantheses not being correctly handled and deleted before being inserted into a queue. So they woud end up in a postfix queue and screw up the order of tokens in there. Fixed it by making sure my program ignores whitespaces, thus recognizing parantheses correctly.








