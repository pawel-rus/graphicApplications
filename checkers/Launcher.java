package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;


public class Launcher {
    private JFrame frame = new JFrame("Checkers");
    private JPanel mainPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JPanel gamesPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();

    static final int NO_OF_MODES = 3;
    private JButton[] games = new JButton[NO_OF_MODES];
    private JButton[] rules = new JButton[NO_OF_MODES];
    private String[] modeNames = {"American Checkers", "Brazilian Draughts", "International Draughts"};
    private String[][] ruleDescriptions = {
    	    {"Board 8x8", "Short moves only", "Cannot capture backwards", "Capture - choose any"},
    	    {"Board 8x8", "Long moves possible", "Can capture backwards", "Must capture max pieces"},
    	    {"Board 10x10", "Long moves possible", "Can capture backwards", "Must capture max pieces"}
    	};
    
    private Color myColor = new Color(240, 220, 130);
    public Launcher() {
    	initMainPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public void initMainPanel() {
    	initInfoPanel();
    	initGamesPanel();
    	initRulesPanel();
    	
    	mainPanel.setLayout(new BorderLayout());
    	mainPanel.setBackground(Color.WHITE);
    	mainPanel.add(infoPanel, BorderLayout.NORTH); 
        mainPanel.add(gamesPanel, BorderLayout.CENTER); 
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    	
    }
    
    public void initInfoPanel(){
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Play Checkers");
        title.setFont(new Font("Arial",Font.BOLD, 35));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel textLabel = new JLabel("Choose the mode you want to play");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.setBackground(myColor);
        infoPanel.add(title);
        infoPanel.add(textLabel);
    }
    
    public void initGamesPanel(){
        gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.X_AXIS));
        gamesPanel.setBackground(myColor);
        
        for (int i = 0; i < NO_OF_MODES; i++) {
        	JPanel modePanel = new JPanel();
        	modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.Y_AXIS));
        	modePanel.setBackground(myColor);
        	
        	JLabel nameLabel = new JLabel(modeNames[i]);
        	nameLabel.setFont(new Font("Arial",Font.BOLD, 18));
        	nameLabel.setForeground(new Color(34, 34, 34));
        	nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        	
        	JPanel rulesPanel = new JPanel();
            rulesPanel.setLayout(new GridLayout(4,1));
            rulesPanel.setBackground(myColor);
            
            for(int j=0; j<ruleDescriptions[i].length; j++) {
            	JPanel cellPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                cellPanel.setBackground(myColor);
                cellPanel.add(new JLabel(ruleDescriptions[i][j]));
                rulesPanel.add(cellPanel);
            }
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(myColor);
            games[i] = new JButton("Choose");
            games[i].setBackground(new Color(139, 69, 19));
            buttonPanel.add(games[i]);
            
            /*
            modePanel.add(nameLabel);
            modePanel.add(rulesPanel);
            modePanel.add(games[i]);
            gamesPanel.add(modePanel);
        	*/
            
            modePanel.add(Box.createVerticalStrut(10)); 
            modePanel.add(nameLabel);
            modePanel.add(Box.createVerticalStrut(10)); 
            modePanel.add(rulesPanel);
            modePanel.add(Box.createVerticalStrut(10)); 
            
            modePanel.add(buttonPanel);
            
            modePanel.add(Box.createVerticalStrut(10)); 
            gamesPanel.add(Box.createHorizontalStrut(30));
            gamesPanel.add(modePanel);
            gamesPanel.add(Box.createHorizontalStrut(30)); 
        }
    }
    
    
    public void initRulesPanel(){
    	bottomPanel.setBackground(myColor);
    }
    
    
    public static void main(String[] args) {
        new Launcher();
    }
}
