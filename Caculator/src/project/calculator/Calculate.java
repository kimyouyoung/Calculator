package project.calculator;

import java.util.Stack;

public class Calculate {
	
	public void calculate(Stack<String> stack, int check) {
		int op = 0;
		String str = stack.pop();		
		double num2 = Double.parseDouble(str);
		double num1 = 0;
		
		
		
		while(!stack.isEmpty()) {
			String temp = stack.pop();

			if(check == 0 && (temp.equals("-") || temp.equals("+"))) {
				stack.push(temp);
				break;
			}

			
			if(temp.equals("+") || temp.equals("-") || temp.equals("÷") || temp.equals("x")) {
				
				if(op == 1) num2 = num1 + num2;
				else if(op == 2) num2 = num1 - num2;
				else if(op == 3) num2 = num1 * num2;
				else if(op == 4) num2 = num1 / num2;
				

				if(temp.equals("+")) op = 1;
				else if(temp.equals("-")) op = 2;
				else if(temp.equals("x")) op = 3;
				else if(temp.equals("÷")) op = 4;

			}
			else num1 = Double.parseDouble(temp); 
		}
		if(op == 1) num1 += num2;
		else if(op == 2) num1 -= num2;
		else if(op == 3) num1 *= num2;
		else if(op == 4) num1 /= num2;
		else if(op == 0) num1 = num2;
		
		
		String temp = String.valueOf(num1);
		float number = Float.parseFloat(temp);
		temp = String.valueOf(number);
		
		while(temp.contains(".") && (temp.endsWith("0") || temp.endsWith("."))) {	
			temp = temp.substring(0, temp.length()-1);
		}
		stack.push(temp);
	}
	
	
}
