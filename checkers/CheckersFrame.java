package checkers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import snake.SnakePanel;

public class CheckersFrame extends JFrame implements ActionListener{
	JButton[][] board = new JButton[8][8];
	JPanel boardPanel = new JPanel();
	String blackPiece = "resources/black.png";
	String whitePiece = "resources/white.png";
	private int selectedRow = -1;
    private int selectedCol = -1;
    private int destinationRow;
    private int destinationCol;
	CheckersFrame(){
		initBoard();
		this.add(boardPanel);
		this.setTitle("Checkers");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public void initBoard(){
		boardPanel.setLayout(new GridLayout(8,8));
		for (int i = 0; i < 8; i++) {
		    for (int j = 0; j < 8; j++) {
		        board[i][j] = new JButton();
		        board[i][j].setPreferredSize(new Dimension(80,80));
		        // Ustawianie koloru tła w zależności od pola planszy
		        if ((i + j) % 2 == 0) {
		            board[i][j].setBackground(new Color(240, 220, 130));
		        } else {
		            board[i][j].setBackground(new Color(139, 69, 19));
		            board[i][j].addActionListener(this);
		            if(i<3) {
		            	setCheckerPiece(i, j, blackPiece);
		            } else if(i>4) {
		            	setCheckerPiece(i, j, whitePiece);
		            }
		            //Dodawanie ActionListenera do obsługi kliknięć na pola planszy
			        //board[i][j].addActionListener(e -> handleButtonClick(i, j));
		        }
		        
		        boardPanel.add(board[i][j]);
		    }
		}
	}
	private void setCheckerPiece(int row, int col, String imagePath) {
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            icon.setDescription(imagePath);
            board[row][col].setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            if (e.getSource() == board[i][j]) {
	                if(selectedRow == -1 && selectedCol == -1) {
	                	selectedRow = i;
                        selectedCol = j;
	                }else {
	                	destinationRow = i;
	                	destinationCol = j;
	                	handleMove(selectedRow, selectedCol, destinationRow, destinationCol);
	                	selectedRow = -1;
                        selectedCol = -1;
	                }
	                return; 
	            }
	        }
	    }
	}
	
	public void handleMove(int startRow, int startCol, int destRow, int destCol) {
	    ImageIcon startIcon = (ImageIcon) board[startRow][startCol].getIcon();
	    if (startIcon != null) {
	        if (startIcon.getDescription().equals(whitePiece)) {
	            System.out.println("Moving WHITE piece from (" + startRow + ", " + startCol + ") to (" + destRow + ", " + destCol + ").");
	            setCheckerPiece(destRow, destCol, whitePiece);
	        } else if (startIcon.getDescription().equals(blackPiece)) {
	            System.out.println("Moving BLACK piece from (" + startRow + ", " + startCol + ") to (" + destRow + ", " + destCol + ").");
	            setCheckerPiece(destRow, destCol, blackPiece);
	        }
	    }
				
	}
}
