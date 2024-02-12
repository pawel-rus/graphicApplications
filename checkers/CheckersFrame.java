package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JTextField;

import snake.SnakePanel;

public class CheckersFrame extends JFrame implements ActionListener{
	JPanel turnPanel = new JPanel();
	JTextField turnField;
	JButton[][] board = new JButton[8][8];
	JPanel boardPanel = new JPanel();
	String blackPiece = "resources/black.png";
	String whitePiece = "resources/white.png";
	String blackQueen = "resources/blackQueen.png";
	String whiteQueen = "resources/whiteQueen.png";
	String turn = whitePiece;
	boolean queenMove = false;
	private int selectedRow = -1;
    private int selectedCol = -1;
    private int destinationRow;
    private int destinationCol;
	CheckersFrame(){
		initBoard();
		this.setLayout(new BorderLayout());
		this.add(turnPanel, BorderLayout.NORTH);
		this.add(boardPanel,BorderLayout.SOUTH);
		this.setTitle("Checkers");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public void initBoard(){
		turnField = new JTextField("WHITE's turn");
		turnField.setEditable(false);
		turnField.setHorizontalAlignment(JTextField.CENTER);
		turnField.setBackground(new Color(139, 69, 19));
		turnField.setForeground(new Color(240, 220, 130));
		turnField.setFont(new Font("Arial", Font.BOLD, 20));
		turnPanel.setLayout(new BorderLayout());
		turnPanel.add(turnField, BorderLayout.CENTER);
		//turnPanel.setBackground(Color.LIGHT_GRAY);
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
	                	if (isValidPiece(i, j)) {
	            		selectedRow = i;
                        selectedCol = j;
	                	}else {
	                        System.out.println("Invalid piece. Select a valid piece.");
	                	}
	                }else {
	                	destinationRow = i;
	                	destinationCol = j;
	                	if(queenMove) {
	                		handleQueenMove(selectedRow, selectedCol, destinationRow, destinationCol);
	                		queenMove = false;
	                	}else {
	                		handleMove(selectedRow, selectedCol, destinationRow, destinationCol);
	                	}
	                	selectedRow = -1;
                        selectedCol = -1;
                        //updateTurn(); 
	                }
	                return; 
	            }
	        }
	    }
	}
	
	public void handleMove(int startRow, int startCol, int destRow, int destCol) {
	    ImageIcon startIcon = (ImageIcon) board[startRow][startCol].getIcon();
	    if (startIcon != null) {
	    	if (board[destRow][destCol].getIcon() == null) {
	    		int rowDifference = Math.abs(destRow - startRow);
	            int colDifference = Math.abs(destCol - startCol);
	            
	            if ((turn.equals(whitePiece) && destRow < startRow) || (turn.equals(blackPiece) && destRow > startRow)) {
	                // Ruch do przodu dla białego lub czarnego pionka
	                if (rowDifference == 1 && colDifference == 1) {
	                	System.out.println("Moving piece from (" + startRow + ", " + startCol + ") to (" + destRow + ", " + destCol + ").");
	    	            setCheckerPiece(destRow, destCol, turn);
	    	            board[startRow][startCol].setIcon(null);
	    	            if ((turn.equals(whitePiece) && destRow == 0) || (turn.equals(blackPiece) && destRow == 7)) {
	                        changeToQueen(destRow, destCol);
	                    }
	    	            updateTurn();
	                } else if(rowDifference == 2 && colDifference == 2){
	                	int middleRow = (startRow + destRow) / 2;
	                    int middleCol = (startCol + destCol) / 2;
	                    ImageIcon middleIcon = (ImageIcon) board[middleRow][middleCol].getIcon();
	                    if (middleIcon != null && !middleIcon.getDescription().equals(turn)) {
	                    	System.out.println("Jumping piece from (" + startRow + ", " + startCol + ") over (" + middleRow + ", " + middleCol + ") to (" + destRow + ", " + destCol + ").");
	                        setCheckerPiece(destRow, destCol, turn);
	                        board[startRow][startCol].setIcon(null);
	                        board[middleRow][middleCol].setIcon(null);
	                        if ((turn.equals(whitePiece) && destRow == 0) || (turn.equals(blackPiece) && destRow == 7)) {
	                            changeToQueen(destRow, destCol);
	                        }
	                        updateTurn();
	                    }else {
	                        System.out.println("Invalid move. Jump only over opponent's piece.");
	                    }
	                } else {
	                    System.out.println("Invalid move. Move only one square diagonally forward or jump over opponent's piece.");
	                }
	            } else {
	                System.out.println("Invalid move. Move only in the forward direction.");
	            }

		    } else {
	            System.out.println("Invalid move. Destination square is not empty.");
		    }
				
	    }
	}
	
	public void handleQueenMove(int startRow, int startCol, int destRow, int destCol) {
		System.out.println("Queen move!");
	    ImageIcon startIcon = (ImageIcon) board[startRow][startCol].getIcon();
	    String queen = startIcon.getDescription();
		int rowDifference = Math.abs(destRow - startRow);
	    int colDifference = Math.abs(destCol - startCol);
	    if (board[destRow][destCol].getIcon() == null) {
	    	if(rowDifference == 1 && colDifference == 1) {
		    	System.out.println("Moving queen from (" + selectedRow + ", " + selectedCol + ") to (" + destinationRow + ", " + destinationCol + ").");
		        setCheckerPiece(destinationRow, destinationCol, queen);
		        board[selectedRow][selectedCol].setIcon(null);
		        updateTurn();
	    	}else if(rowDifference == 2 && colDifference == 2) {
		    	int middleRow = (startRow + destRow) / 2;
	            int middleCol = (startCol + destCol) / 2;
	            ImageIcon middleIcon = (ImageIcon) board[middleRow][middleCol].getIcon();
	            if (middleIcon != null && !middleIcon.getDescription().equals(queen) && !middleIcon.getDescription().equals(turn)) {
	            	System.out.println("Jumping queen from (" + startRow + ", " + startCol + ") over (" + middleRow + ", " + middleCol + ") to (" + destRow + ", " + destCol + ").");
	            	setCheckerPiece(destRow, destCol, queen);
	            	board[startRow][startCol].setIcon(null);
	                board[middleRow][middleCol].setIcon(null);
	                updateTurn();
	            }else {
            	System.out.println("Invalid move. Jump only over opponent's piece.");
	            }
	    	} else {
	    		System.out.println("Invalid queen move. Move only one square diagonally or jump over opponent's piece.");
	    	}
	    }else {
	    	System.out.println("Invalid move. Destination square is not empty.");
	    }
	    
	    	
	}
	
	public boolean isValidPiece(int row, int col) {
	    ImageIcon selectedIcon = (ImageIcon) board[row][col].getIcon();
	    if(selectedIcon != null) {
	    	String iconDescription = selectedIcon.getDescription();
	    	if(iconDescription.equals(whiteQueen) && turn.equals(whitePiece)) {
	    		queenMove = true;
	    		return true;
	    	}else if(iconDescription.equals(blackQueen) && turn.equals(blackPiece)) {
	    		queenMove = true;
	    		return true;
	    	} else {
	    		return selectedIcon.getDescription().equals(turn);
	    	}
	    } else {
	    	return false;
	    }
	}
	
	public void updateTurn() {
	    if (turn.equals(whitePiece)) {
	        turn = blackPiece;
	        turnField.setText("BLACK's turn");
	    } else {
	        turn = whitePiece;
	        turnField.setText("WHITE's turn");
	    }
	}
	
	public void changeToQueen(int row, int col) {
		if (turn.equals(whitePiece)) {
			setCheckerPiece(row, col, whiteQueen);
		} else {
			setCheckerPiece(row, col, blackQueen);
		}
	}
	

}

//dodac krolowa, ktora moze w dowolnym kierunku sie ruszac 

