import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Calc extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID= -1415422854830572413L;
	private JTextArea display;
	
	private JButton clear;
	private JButton equals;
	private JButton decimal;
	private JButton posneg;
	
	private JButton[] numbers;
	private JButton[] operations; // 0=divide, 1=multiply, 2=subtract, 3=add
	private int operation = -1; // 0=divide, 1=multiply, 2=subtract, 3=add
	
	private double tempFirst = 0.0;
		
	public static void main(String[] args) {
		new Calc();
	}
	
	public Calc() {
		super("Calculator");
		sendDisplay();
		sendButtons();
		sendUI(this);
		addKeyListener(this);
		this.requestFocus();
	}
	
	private void sendDisplay() {
		display = new JTextArea("0");
		display.setBounds(10,10,280,50);
		display.setEditable(false);
		add(display);
	}
	private void sendButtons() {
		operations = new JButton[4];
		operations[0] = new JButton("/");
		operations[0].setBounds(226,70,65,55);
		operations[1] = new JButton("*");
		operations[1].setBounds(226,132,65,55);
		operations[2] = new JButton("-");
		operations[2].setBounds(226,194,65,55);
		operations[3] = new JButton("+");
		operations[3].setBounds(226,256,65,55);
		for(int i=0 ; i<4 ; i++){
			operations[i].addActionListener(this);
			add(operations[i]);
		}
		
		clear = new JButton("Clear");
		clear.setBounds(154, 318, 137,55);
		clear.addActionListener(this);
		add(clear);
		 
		equals = new JButton("=");
		equals.setBounds(10,318,137,55);
		equals.addActionListener(this);
		add(equals);
		
		decimal = new JButton(".");
		decimal.setBounds(82,256,65,55);
		decimal.addActionListener(this);
		add(decimal);
		
		posneg = new JButton("-/+");
		posneg.setBounds(154,256,65,55);
		posneg.addActionListener(this);
		add(posneg);
		
		numbers = new JButton[10];
		for(int i=0 ; i<10 ; i++){
			numbers[i] = new JButton("" + i);
			numbers[i].addActionListener(this);
			add(numbers[i]);
		}
		numbers[0].setBounds(10,256,65,55);		
		numbers[1].setBounds(10,194,65,55);
		numbers[2].setBounds(82,194,65,55);
		numbers[3].setBounds(154,194,65,55);
		numbers[4].setBounds(10,132,65,55);
		numbers[5].setBounds(82,132,65,55);
		numbers[6].setBounds(154,132,65,55);
		numbers[7].setBounds(10,70,65,55);
		numbers[8].setBounds(82,70,65,55);
		numbers[9].setBounds(154,70,65,55);

		
	}
	
	private void sendUI(Calc app){
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(309, 430);
		app.setResizable(false);
		app.setLayout(null);
		app.setLocationRelativeTo(null);
		app.setVisible(true);
	}
		
	public double getTempFirst() {
		return tempFirst;
	}

	public void setTempFirst(double tempFirst) {
		this.tempFirst = tempFirst;
	}
	
	private void doOperation(int op){
		setTempFirst(Double.parseDouble(display.getText()));
		display.setText("0");
		operation = op;
	}
	
	private void doNumber(int i){
		if(display.getText().length()> 10)return;
		if (display.getText().equalsIgnoreCase("0"))display.setText("");
		display.append("" + i);
	}
	
	private void doClear(){
		display.setText("0");
		setTempFirst(0.0);
		operation = -1;
	}
	
	private void doEquals(){
		switch (operation){
		case 0 : display.setText(Double.toString(getTempFirst() / Double.parseDouble(display.getText()))); break;
		case 1 : display.setText(Double.toString(getTempFirst() * Double.parseDouble(display.getText()))); break;
		case 2 : display.setText(Double.toString(getTempFirst() - Double.parseDouble(display.getText()))); break;
		case 3 : display.setText(Double.toString(getTempFirst() + Double.parseDouble(display.getText()))); break;
		}
		operation = -1;
		if(display.getText().endsWith(".0")) display.setText(display.getText().replace(".0", ""));
		setTempFirst(0.0);
	}
	
	private void doDecimal(){
		if (display.getText().contains("."))return;
		display.append(".");
	}
	
	private void doPosNeg(){
		if(display.getText().equalsIgnoreCase("0"))	return;
		display.setText(Double.toString(Double.parseDouble(display.getText())*(-1)));
		if (display.getText().endsWith(".0"))display.setText(display.getText().replace(".0",""));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		for(int i=0 ; i<4 ; i++){
			if(source == operations[i])doOperation(i);
		}
		for(int i=0 ; i<10 ; i++){
			if(source == numbers[i])doNumber(i);
		}
		if(source == clear) doClear();
		if(source == equals) doEquals();
		if(source == decimal) doDecimal();
		if(source == posneg) doPosNeg();
		this.requestFocus();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyChar();
		System.out.println(key);
		switch (key){
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9': doNumber(key - '0'); break;
		case '/': doOperation(0); break;
		case '*': doOperation(1); break;
		case '-': doOperation(2); break;
		case '+': doOperation(3); break;
		case '\b': doClear(); break;
		case '=' :  
		case ' ' :
		case '\n': doEquals(); break;
		case '.' :  doDecimal(); break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
		
}
	
