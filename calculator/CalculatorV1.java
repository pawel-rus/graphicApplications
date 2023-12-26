package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorV1 {
	private static JTextField resultField;
    private static int firstNumber;
    private static int secondNumber;
    private static char currentOperation;
    private static boolean isFirstDigit = true; //
    private static boolean isAlreadyResult;// "=" after "=", same operator as before  
    
	public static void createAndShowGUI() {
        
		ActionListener myActionListener = new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("[ActionListener] Button = " + e.getActionCommand());
    			choose(e.getActionCommand().charAt(0));
    		}    		
    	};
    	
		JFrame frame = new JFrame("My First Calculator - v.0.1");
        //panel wynikowy
        resultField = new JTextField("0");
        resultField.setHorizontalAlignment(JTextField.RIGHT);
        resultField.setEditable(false);
        //czcionka
        Font font = new Font("Arial", Font.BOLD, 16);
        resultField.setFont(font);
        
        //siatka na przyciski
        JPanel calcPanel = new JPanel(); 
        calcPanel.setLayout(new GridLayout(4, 4)); // Ustawienie siatki 4x4
        //calcPanel.setBackground(bgColor);
        
        //frame.getContentPane().setBackground(new Color(0, 255, 0));
        //frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(resultField, BorderLayout.NORTH);
        frame.getContentPane().add(calcPanel);
        
        String[] buttons = {"1", "2", "3", "+", "4", "5", "6","-", "7", "8", "9", "*", "0", "=", "C", "/"};
        //initialize buttons + add action listeners
        for(String tmp : buttons) {
            JButton button = new JButton(tmp);
            
        	button.addActionListener(myActionListener);
        	button.setBackground(new Color(233, 255, 142));
        	calcPanel.add(button);
        	
        }
        
        try {
        	BufferedImage iconImage = ImageIO.read(new File("icon.png"));
        	frame.setIconImage(iconImage);
        } catch(IOException e) {
        	System.err.println(e.getMessage());
        }
        frame.pack();

        // Zablokowanie zmiany rozmiaru okna
        frame.setResizable(false);
        frame.setSize(320, 200);
        // Umieszczenie okna na środku ekranu
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	
	private static void choose(char value) {
		try {
			if(Character.isDigit(value)) {
				handleDigit(String.valueOf(value));
			} else {
				handleOperator(value);
			}
		} catch(NumberFormatException e) {
			
		}
		
	}
	
	private static void handleDigit(String digit) {
		if(isFirstDigit) {
			resultField.setText(digit);
			isFirstDigit = false;
		} else {
			resultField.setText(resultField.getText()+ digit);
		}
		
	}



	private static void handleOperator(char operator) {
		if(operator == '=') {
			//currentOperation = '=';
			result();
		}else if(operator == 'C') {
			isAlreadyResult = false;
			isFirstDigit = true;
			resultField.setText("0");
			firstNumber = 0;
			secondNumber = 0;
			resultField.setForeground(Color.BLACK);
		}else{
			isAlreadyResult = false;
			firstNumber = Integer.parseInt(resultField.getText());
			currentOperation = operator;
			resultField.setText("");
			isFirstDigit = true;
		}
	}
	
	private static void result() {
		if(isAlreadyResult == true) {
			//nothing
		}else {
			secondNumber = Integer.parseInt(resultField.getText());
		}
		
		switch(currentOperation) {
			case '+':
				resultField.setText(String.valueOf(firstNumber + secondNumber));
				break;
			case '-':
				resultField.setText(String.valueOf(firstNumber - secondNumber));
				break;
			case '=':
				firstNumber = Integer.parseInt(resultField.getText());
				resultField.setText(String.valueOf(firstNumber));
				break;
			case '*':
				resultField.setText(String.valueOf(firstNumber * secondNumber));
				break;
			case '/':
				try {
					resultField.setText(String.valueOf(firstNumber / secondNumber));
				}catch(ArithmeticException e) {
					resultField.setForeground(Color.RED);
					resultField.setText("ERROR: Division by zero");
					
				}
				break;
		}
		isAlreadyResult = true;
		firstNumber = Integer.parseInt(resultField.getText());
		isFirstDigit = true;
	}
	


	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	createAndShowGUI();
            }
        });
    }

}
