package Model;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game extends JFrame{
	private Board chessboard;
	private int level;
	private User user;
	public Game(){
	     super("Knight's Move: Manual Version");
	 	chessboard = new Board();
	        Container contents = getContentPane();
	        contents.setLayout(new GridLayout(8, 8));
	        // Add Menu Bar
	        JMenuBar menuBar = new JMenuBar();
	        setJMenuBar(menuBar);
	        // Add Menu Option
	        JMenu game = new JMenu("Game");
	        menuBar.add(game);
	        // Add Exit option
	        JMenuItem restart = new JMenuItem("Exit Game");
	        game.add(restart);
	        restart.addActionListener((new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                dispose();
	   
	            }
	        }));
	        for (int row = 0; row < Board.BOARD_SIZE; row++) {
	            for (int column = 0; column < Board.BOARD_SIZE; column++) {
	                contents.add(chessboard.getTile(row, column));
	            }
	        }

	        setSize(600, 600);
	        setResizable(false);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setVisible(true);
	    }

	public Board getChessboard() {
		return chessboard;
	}
	public void setChessboard(Board chessboard) {
		this.chessboard = chessboard;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}