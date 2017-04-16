package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Control_GUI extends JPanel {

	private static JTextField name;
	
	private JPanel Roll = dieRoll();
	
	static JFrame frame = new JFrame();

	public Control_GUI()
	{
	
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(whoseTurn());
		topPanel.add(nextPlayerButton());
		topPanel.add(AccuseButton());
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(dieRoll());
		bottomPanel.add(guess());
		bottomPanel.add(guessResult());
		
		setLayout(new GridLayout(2,0));
		add(topPanel);
		add(bottomPanel);
	}
	
	public void update() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(whoseTurn());
		topPanel.add(nextPlayerButton());
		topPanel.add(AccuseButton());
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(Roll);
		bottomPanel.add(guess());
		bottomPanel.add(guessResult());
		
		setLayout(new GridLayout(2,0));
		add(topPanel);
		add(bottomPanel);
	}

	 private JPanel whoseTurn() {
		 	JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2,0));
		 	JLabel nameLabel = new JLabel("Whose Turn?");
			name = new JTextField(10);
			name.setEditable(false);
			panel.add(nameLabel);
			panel.add(name);
			return panel;
	}
	 
	 private static JPanel dieRoll() {
		 JPanel panel = new JPanel();
		 //panel.setLayout(new GridLayout(2, 1));
		 JLabel nameLabel = new JLabel("Die");
		 name = new JTextField(2);
		 name.setEditable(false);
		 panel.add(nameLabel);
		 panel.add(name);
		 panel.setBorder(new TitledBorder(new EtchedBorder(), "Roll"));
		 return panel;
	 }
	 
	 
	 
	 private JPanel guess() {
		 JPanel panel = new JPanel();
		 panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		 JLabel nameLabel = new JLabel("Guess");
		 name = new JTextField(30);
		 name.setEditable(false);
		 panel.add(nameLabel);
		 panel.add(name);
		 return panel;
	 }
	 
	 private JPanel guessResult() {
		 JPanel panel = new JPanel();
		 JLabel nameLabel = new JLabel("Response");
		 name = new JTextField(10);
		 name.setEditable(false);
		 panel.add(nameLabel);
		 panel.add(name);
		 panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));

		 return panel;
	 }
	 
	private JPanel nextPlayerButton() {
		JButton nextPlayer = new JButton("Next Player");
		JPanel panel = new JPanel();
		nextPlayer.addActionListener(new NextPlayerButtonListener());
		panel.add(nextPlayer);
		return panel;
	}
	
	private JPanel AccuseButton() {
		JButton accusation = new JButton("Accuse!");
		JPanel panel = new JPanel();
		accusation.addActionListener(new AccuseButtonListener());
		panel.add(accusation);
		return panel;
	}
	
	class NextPlayerButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Next Player"); //TODO
			name.setText("5");
			Roll = dieRoll();
			
			Board.getInstance().makeMove();
			
		}
	}
	
	class AccuseButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Accuse - TODO"); //TODO		
		}	
	}
	
}
