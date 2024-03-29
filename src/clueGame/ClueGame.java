package clueGame;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ClueGame extends JFrame {
	

	private static Board board;

	
	ClueGame() {
		setTitle("Clue Game");
		setSize(750,650); //actually call pack 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		add(menuBar, BorderLayout.NORTH);
		
		setUp();

		add(board, BorderLayout.CENTER);
		
		Control_GUI control = new Control_GUI();
		board.setGameControl(control);
		add(control, BorderLayout.SOUTH);
		add(new DisplayCardsGUI(), BorderLayout.EAST);	
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		menu.add(createFileDetectiveNotes());
		return menu;
	}
	
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createFileDetectiveNotes() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				DetectiveNotesGUI notes = new DetectiveNotesGUI();
				notes.setVisible(true);		
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	public void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/GVIG_ClueLayout.csv", "data/GVIG_ClueLegend.txt", "data/GVIG_Players.txt", "data/GVIG_Weapons.txt");		
		Board.getInstance().initialize();
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		JOptionPane.showMessageDialog(game,"You are Captain Zapp, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
	}
}
