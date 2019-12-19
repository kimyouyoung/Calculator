package project.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Frame {
	Stack<String> stack = new Stack<String>();
	int p = -1, op = 0, ex = 1, r = 0, again = 0, dat = 0;
	String aga_n;


	public Frame() {
		setFrame();
	}

	private void setFrame() {

		JFrame frame = new JFrame("Calculator");
		frame.setSize(250+(250/4), 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(71, 71, 71));
		panel.setLayout(null);
		
		JLabel label = new JLabel("0");
		
		label.setBounds(0, 10, 230, 50);
		label.setHorizontalAlignment(JLabel.RIGHT);
		label.setFont(new Font("Avenir", Font.PLAIN, 40));
		label.setForeground(new Color(255, 255, 255));
		
		Font font = new Font("Avenir", Font.PLAIN, 17);

		if(label.getText().length()>10)
			label.setFont(font);
		frame.add(label);


		JButton[] b = new JButton[21];
		
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
					r = 0;
					again = 0;
					aga_n = "";
					
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
						if(value.length()>10)
							label.setFont(font);
						label.setText(value);
					}
					else if(op == 1){
						stack.push(value);
						if(value.length()>10)
							label.setFont(font);
						label.setText(value);
						op = 0;
					}					
					else {
						stack.pop();
						stack.push(value);
						if(value.length()>10)
							label.setFont(font);
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
		b[15] = new JButton("x");
		b[16] = new JButton("-");
		b[17] = new JButton("+");
		b[18] = new JButton("=");
		b[19] = new JButton("%");
		b[20] = new JButton("B");
		
		int op_x = 0, op_y = 120;
		for(int i = 10; i < 21; i++) {
			if(i == 10) {
				b[10].setBounds((250/4)*2, 270, 250/4, 50);
			}
			else if(i < 15) {
				b[i].setBounds(op_x, 70, 250/4, 50);
				b[i].setForeground(new Color(237, 169, 0));
				op_x += (250/4);
			}
			else if(i == 19) {
				b[i].setBounds((250/4)*4, 70, 250/4, 100);
				b[i].setForeground(new Color(237, 169, 0));
			}else if(i == 20) {
				b[i].setBounds((250/4)*4, 170, 250/4, 150);
				b[i].setForeground(new Color(237, 169, 0));
			}
			else {
				b[i].setBounds((250/4)*3, op_y, 250/4, 50);
				b[i].setForeground(new Color(237, 169, 0));
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
					if(!operation.equals("."))
						dat = 0;
						
					
					if(operation.equals("x") || operation.equals("÷")) {
						if(again != 0)
							stack.pop();
						if(p == 1) stack.push(operation);
						else {
							cal.calculate(stack, 0);
							first = stack.pop();
							if(first.length()>10)
								label.setFont(font);
							label.setText(first);
							stack.push(first);
							stack.push(operation);
						}
						p = 2;
						op = 1;
						ex = 1;
						again = 1;
						
						if(operation.equals("x")) r = 1;
						else if(operation.equals("÷")) r = 2;
						
					}
					else if(operation.equals("+") || operation.equals("-")) {
						if(again != 0)
							stack.pop();
						p = 1;
						cal.calculate(stack, 1);
						first = stack.pop();
						if(first.length()>10)
							label.setFont(font);
						label.setText(first);
						stack.push(first);
						stack.push(operation);
						op = 1;
						ex = 1;
						again = 1;
						
						if(operation.equals("+")) r = 3;
						else if(operation.equals("-")) r = 4;
						
					}

					else if(operation.equals("=")) {
						if(r > 0) {
							String str = stack.pop();
							
							if(str.equals("+") || str.equals("-") || str.equals("÷") || str.equals("x")|| str.equals("%")) {
								String s = stack.pop();
								aga_n = s;
								stack.push(s);
								stack.push(str);
								stack.push(s);
							}else {
								stack.push(str);
								if(r == 1) stack.push("x");
								else if(r == 2) stack.push("÷");
								else if(r == 3) stack.push("+");
								else if(r == 4) stack.push("-");
								else if(r == 5) stack.push("%");
								stack.push(aga_n);
				
							}
						}
						if(r == 5) {
							String per = stack.pop();
							Double p = Double.parseDouble(per);
							p = p * 0.01;
							first = String.valueOf(p);
							if(first.length()>10)
								label.setFont(font);
							while(first.contains(".") && (first.endsWith("0") || first.endsWith("."))) {	
								first = first.substring(0, first.length()-1);
							}
							label.setText(first);
							stack.push(first);
						}else {
							cal.calculate(stack, 1);
							first = stack.pop();
							if(first.length()>10)
								label.setFont(font);
							label.setText(first);
							stack.push(first);
						}
						initialize();
					}
					else if(operation.equals(".")) {
						first = stack.pop();
						if(dat == 0)
							first = first + operation;
						if(first.length()>10)
							label.setFont(font);
						label.setText(first);
						stack.push(first);
						dat = 1;
					}
					else if(operation.equals("AC")) {
						while(!stack.empty()) stack.pop();
						stack.push("0");
						first = stack.pop();
						label.setFont(new Font("Avenir", Font.PLAIN, 40));
						label.setText(first);
						stack.push(first);
						initialize();
					}
					else if(operation.equals("C")) {
						first = stack.pop();
						first = first.substring(0, first.length()-1);
						if(first.length()<1)first = "0";
						if(first.length()>10)
							label.setFont(font);
						else
							label.setFont(new Font("Avenir", Font.PLAIN, 40));
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
						if(operation.length()>10)
							label.setFont(font);
						label.setText(operation);
						stack.push(operation);
					}else if(operation.equals("%")) {
						if(again != 0)
							stack.pop();
						String per = stack.pop();
						Double p = Double.parseDouble(per);
						p = p * 0.01;
						first = String.valueOf(p);
						if(first.length()>10)
							label.setFont(font);
						while(first.contains(".") && (first.endsWith("0") || first.endsWith("."))) {	
							first = first.substring(0, first.length()-1);
						}
						label.setText(first);
						stack.push(first);
						stack.push(operation);
						op = 1;
						ex = 1;
						again = 1;
						r = 5;
					}else if(operation.equals("B")) {
						if(again != 0)
							stack.pop();
						String bi = stack.pop();
						int bin = Integer.parseInt(bi);
						bi = Integer.toBinaryString(bin);
						if(bi.length()>10)
							label.setFont(font);
						label.setText(bi);
					}
				}
			});
		}
		
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	public void initialize() {
		p = -1;
		op = -1;
		ex = 1;

	}
}