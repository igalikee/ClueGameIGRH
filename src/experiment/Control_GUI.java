package experiment;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Control_GUI extends JPanel {

	private JTextField name;

	public Control_GUI()
	{
		
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,3));
		add(whoseTurn());
		add(nextPlayerButton());
		add(dieRoll());
		add(guess());
		add(guessResult());
		
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
	 
	 private JPanel dieRoll() {
		 JPanel panel = new JPanel();
		 //panel.setLayout(new GridLayout(2, 1));
		 JLabel nameLabel = new JLabel("Die");
		 name = new JTextField(10);
		 name.setEditable(false);
		 panel.add(nameLabel);
		 panel.add(name);
		 panel.setBorder(new TitledBorder(new EtchedBorder(), "Roll"));
		 return panel;
	 }
	 
	 private JPanel guess() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new GridLayout(1, 2));
		 JLabel nameLabel = new JLabel("Guess");
		 name = new JTextField(10);
		 name.setEditable(false);
		 panel.add(nameLabel);
		 panel.add(name);
		 return panel;
	 }
	 
	 private JPanel guessResult() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new GridLayout(2, 2));
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
		JButton accusation = new JButton("Accuse!");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(nextPlayer);
		panel.add(accusation);
		
		return panel;
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Control GUI");	
		// Create the JPanel and add it to the JFrame
		Control_GUI gui = new Control_GUI();
		gui.setVisible(true);
		frame.add(gui, BorderLayout.EAST);
		// Now let's view it
		frame.pack();
		frame.setVisible(true);
	}
}
