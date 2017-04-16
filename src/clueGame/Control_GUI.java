package clueGame;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Control_GUI extends JPanel {

	private JTextField name = new JTextField(2);
	private JTextField roll = new JTextField(2);
	

	JPanel bottomPanel;
	JPanel topPanel;

	public Control_GUI()
	{
		
		bottomPanel = new JPanel();
		topPanel = new JPanel();
		
		setLayout(new GridLayout(2,0));
		topPanel();
		SetBottomPanel();	
	}
	
	public void topPanel() {
		topPanel.setLayout(new FlowLayout());
		topPanel.add(whoseTurn());
		topPanel.add(nextPlayerButton());
		topPanel.add(AccuseButton());	
		add(topPanel);
	}
	
	public void SetBottomPanel() {
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(dieRoll());
		bottomPanel.add(guess());
		bottomPanel.add(guessResult());
		add(bottomPanel);
	}
	
	public void updateRoll(String n) {
		roll.setText(n);
	}
	
	public void updateTurnName(String n) {
		name.setText(n);
	}
	
	//=======================================================
	// PANELS
	//=======================================================
	
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
		roll.setEditable(false);
		panel.add(nameLabel);
		panel.add(roll);
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
