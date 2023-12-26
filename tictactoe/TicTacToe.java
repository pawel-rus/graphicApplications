package tictactoe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class TicTacToe implements ActionListener{
	
	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel title_panel  = new JPanel();
	JPanel button_panel  = new JPanel();
	JLabel textfield = new JLabel();
	JButton newGameButton = new JButton("new game");
	JButton[] buttons = new JButton[9];
	boolean xTurn;
	private final int[][] winConditions = {
			{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
			{0, 3, 6}, {1, 4, 7}, {2, 5, 8},
			{0, 4, 8}, {6, 4, 2}
	};
	
	TicTacToe(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.getContentPane().setBackground(new Color(65, 65, 65));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		textfield.setBackground(new Color(30, 30, 30));
		textfield.setForeground(new Color(25, 255, 0));
		textfield.setFont(new Font("Helvetica",Font.BOLD,55));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("tic-tac-toe");
		textfield.setOpaque(true);
		
		newGameButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        resetGame();
		    }
		});
		newGameButton.setFont(new Font("Helvetica",Font.BOLD, 20));
		newGameButton.setForeground(new Color(25, 255, 0));
		newGameButton.setBackground(new Color(30, 30, 30));
		newGameButton.setFocusable(false);		
		
		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0, 0, 600, 80);
		
		button_panel.setLayout(new GridLayout(3,3));
		button_panel.setBackground(new Color(150, 150, 150));
		
		for(int i=0; i<9; i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
			buttons[i].setBackground(new Color(128, 128, 128));
		}
		
		title_panel.add(textfield);
		title_panel.add(newGameButton, BorderLayout.EAST);
		frame.add(title_panel, BorderLayout.NORTH);
		frame.add(button_panel);
		
		firstTurn();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0; i<9; i++) {
			if(e.getSource() == buttons[i]) {
				if(xTurn) {
					if(buttons[i].getText() == "") {
						buttons[i].setForeground(new Color(0,0,255));
						buttons[i].setText("X");
						if( check() ) {
							//END GAME
						}	
						else {
							xTurn = false;
							textfield.setText("O turn");
						}
					}
				} else {
					if(buttons[i].getText() == "") {
						buttons[i].setForeground(new Color(255,0,0));
						buttons[i].setText("O");
						if( check() ) {
							// END GAME
						}else {	
							xTurn = true;
							textfield.setText("X turn");
						}
					}
				}
			}
		}
	}
	
	public void firstTurn() {
		try {
			Thread.sleep(4000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(random.nextInt(2) == 1) {
			xTurn = true;
			textfield.setText("X turn");
		} else {
			xTurn = false;
			textfield.setText("O turn");
		}
	}
	
	public boolean check() {
		for(int[] con : winConditions) {
			if( xTurn &&
				buttons[con[0]].getText() == "X" &&
				buttons[con[1]].getText() == "X" &&
				buttons[con[2]].getText() == "X" ) {
				xWon(con);
				return true;
				//break;
			} else if( buttons[con[0]].getText() == "O" &&
					buttons[con[1]].getText() == "O" &&
					buttons[con[2]].getText() == "O" ){
				oWon(con);
				return true;
			}
		}
		return false;
	}
	
	public void xWon(int[] con) {
		textfield.setText("X won the game!");
		for(int a : con) {
			buttons[a].setBackground(Color.GREEN);
		}
		for (JButton button : buttons) {
			if(button.getText() == "") button.setEnabled(false);
	    }
	}
	
	public void oWon(int[] con) {
		textfield.setText("O won the game!");
		for(int a : con) {
			buttons[a].setBackground(Color.GREEN);
		}
		for (JButton button : buttons) {
			if(button.getText() == "") button.setEnabled(false);
	    }
	}
	
	public void resetGame() {
		for(JButton button : buttons) {
			button.setText("");
			button.setBackground(new Color(128, 128, 128));
			button.setEnabled(true);
		}
		firstTurn();
	}
}
