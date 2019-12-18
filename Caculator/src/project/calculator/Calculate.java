package project.calculator;

import java.util.Stack;

public class Calculate {
	
	
	public void calculate(Stack<String> stack, int check) {
		int o = 0;
		double num2 = Double.parseDouble(stack.pop());
		double num1 = -1;
		
		while(!stack.isEmpty()) {
			String temp = stack.pop();
			if((temp.equals("-") || temp.equals("+")) && check == 0) {
				stack.push(temp);
				break;
			}
			if(temp.equals("+") || temp.equals("-") || temp.equals("÷") || temp.equals("x")) {

				if(o == 1) num2 = num1 + num2;
				else if(o == 2) num2 = num1 - num2;
				else if(o == 3) num2 = num1 * num2;
				else if(o == 4) num2 = num1 / num2;

				if(temp.equals("+")) o = 1;
				else if(temp.equals("-")) o = 2;
				else if(temp.equals("x")) o = 3;
				else if(temp.equals("÷")) o = 4;



			}
			else num1 = Double.parseDouble(temp); 
		}
		if(o == 1) num1 += num2;
		else if(o == 2) num1 -= num2;
		else if(o == 3) num1 *= num2;
		else if(o == 4) num1 /= num2;
		else if(o == 0) num1 = num2;
		
		
		String temp = String.valueOf(num1);
		float number = Float.parseFloat(temp);
		temp = String.valueOf(number);
		
		while(temp.contains(".") && (temp.endsWith("0") || temp.endsWith("."))) {	
			temp = temp.substring(0, temp.length()-1);
		}
		stack.push(temp);
	}
	
	
}
