package project.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Frame {
	Stack<String> stack = new Stack<String>();
	int p = -1, op = 0, ex = 1;


	public Frame() {
		setFrame();
	}

	private void setFrame() {

		JFrame frame = new JFrame("Calculator");
		frame.setSize(250, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(71, 71, 71));
		panel.setLayout(null);
		
		JLabel label = new JLabel("0");
		
		label.setBounds(0, 10, 230, 50);
		label.setHorizontalAlignment(JLabel.RIGHT);
		label.setFont(new Font("Avenir", Font.PLAIN, 50));
		label.setForeground(new Color(255, 255, 255));
		
		Font font = new Font("Avenir", Font.PLAIN, 20);
		if(label.getText().length()>10) {
			font = new Font("Avenir", Font.PLAIN, 15);
			label.setFont(font);
		}
		frame.add(label);


		JButton[] b = new JButton[19];
		
		int count = 0, y = 270;
		stack.push("0");
		
		for(int i = 0 ; i<10; i++) {
			if(count%3 == 1)
				y -= 50;
			count++;
			
			b[i] = new JButton(String.valueOf((i)));
			if(i == 0)
				b[i].setBounds(0, y, (250/4)*2, 50);
			else if(i%3 == 0 && i != 0)
				b[i].setBounds((250/4)*2, y, 250/4, 50);
			else if(i%3 == 1)
				b[i].setBounds(0, y, 250/4, 50);
			else
				b[i].setBounds(250/4, y, 250/4, 50);
			
			
			b[i].setFont(font);
			panel.add(b[i]);

			b[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					JButton bt = (JButton)e.getSource();
					String value = bt.getText();
					
					if(op == 0) {
						double num = Double.parseDouble(value);
						String str_num = stack.pop();
						double n = Double.parseDouble(str_num);

						if(str_num.contains(".")) {
							num = n + num * Math.pow(10, (-1)*ex);
							ex++;
						}

						else {
							num = num + n * 10;
						}
						value = String.valueOf(num);
						
						while(value.contains(".") && (value.endsWith("0") || value.endsWith("."))) {	
							value = value.substring(0, value.length()-1);
						}

						stack.push(value);
						label.setText(value);
					}
					else if(op == 1){
						stack.push(value);
						label.setText(value);
						op = 0;
					}					
					else {
						stack.pop();
						stack.push(value);
						label.setText(value);
						op = 0;
					}
				}
			});
		}
		
		b[10] = new JButton(".");
		b[11] = new JButton("AC");
		b[12] = new JButton("C");
		b[13] = new JButton("+/-");
		b[14] = new JButton("÷");
		b[15] = new JButton("X");
		b[16] = new JButton("-");
		b[17] = new JButton("+");
		b[18] = new JButton("=");
		
		int op_x = 0, op_y = 120;
		for(int i = 10; i < 19; i++) {
			if(i == 10) {
				b[10].setBounds((250/4)*2, 270, 250/4, 50);
			}
			else if(i < 15) {
				b[i].setBounds(op_x, 70, 250/4, 50);
				op_x += (250/4);
			}
			else {
				b[i].setBounds((250/4)*3, op_y, 250/4, 50);
				op_y += 50;
			}
			b[i].setFont(font);
			panel.add(b[i]);
			
			b[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					JButton bt = (JButton)e.getSource();
					String operation = bt.getText();
					String first;
					
					Calculate cal = new Calculate();
					
					if(operation.equals("x") || operation.equals("÷")) {
						if(p == 1) stack.push(operation);
						else {
							cal.calculate(stack, 0);
							first = stack.pop();
							System.out.println(first);
							label.setText(first);
							stack.push(first);
							stack.push(operation);
						}
						p = 2;
						op = 1;
						ex = 1;
					}
					else if(operation.equals("+") || operation.equals("-")) {
						p = 1;
						cal.calculate(stack, 1);
						first = stack.pop();
//						while(first.contains(".") && (first.endsWith("0") || first.endsWith("."))) {	
//							first = first.substring(0, first.length()-1);
//						}
						label.setText(first);
						stack.push(first);
						stack.push(operation);
						op = 1;
						ex = 1;
					}

					else if(operation.equals("=")) {
						cal.calculate(stack, 1);
						first = stack.pop();
						label.setText(first);
						stack.push(first);
						ini();
					}
					else if(operation.equals(".")) {
						first = stack.pop();
						first = first + operation;
						label.setText(first);
						stack.push(first);
					}
					else if(operation.equals("AC")) {
						while(!stack.empty()) stack.pop();
						stack.push("0");
						first = stack.pop();
						label.setText(first);
						stack.push(first);
						ini();
					}
					else if(operation.equals("C")) {
						first = stack.pop();
						first = first.substring(0, first.length()-1);
						if(first.length()<1)first = "0";
						label.setText(first);
						stack.push(first);
					}
					else if(operation.equals("+/-")){
						first = stack.pop();
						double num = Double.parseDouble(first);
						num *= -1;
						operation = String.valueOf(num);
						
						while(operation.contains(".") && (operation.endsWith("0") || operation.endsWith("."))) {	
							operation = operation.substring(0, operation.length()-1);
						}
						label.setText(operation);
						stack.push(operation);
					}
				}
			});
		}
		
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	public void ini() {
		p = -1;
		op = -1;
		ex = 1;

	}
}