package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class MakeGuessGUI extends JDialog {
	private ArrayList<String> weapons = Board.getInstance().getWeapons();
	private ArrayList<Player> players = Board.getInstance().getPlayers();
	private Map<Character, String> rooms = Board.getInstance().getLegend();

	private String currentWeapon, currentPlayer, currentRoom;

	private Solution suggestion;

	public MakeGuessGUI() {
		setTitle("Make a Guess");
		setModal(true);

		JPanel ComboBoxPanel = new JPanel();
		ComboBoxPanel.setLayout(new GridLayout(0,1));
		ComboBoxPanel.add(GuessWeapon());
		ComboBoxPanel.add(GuessPerson());
		ComboBoxPanel.add(GuessRoom());
		ComboBoxPanel.add(CancelButton());
		ComboBoxPanel.add(SubmitButton());

		setContentPane(ComboBoxPanel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public JComboBox<String> GuessWeapon() {
		JComboBox<String> guess = new JComboBox<String>();

		for (String s: weapons) {
			guess.addItem(s);
		}

		if (currentWeapon != null) {
			currentWeapon = guess.getSelectedItem().toString();
		}

		return guess;
	}

	public JComboBox<String> GuessPerson() {
		JComboBox<String> guess = new JComboBox<String>();

		for (Player player : players) {
			if (!player.isHuman()) {
				guess.addItem(player.getPlayerName());
			}
		}

		if (currentPlayer != null) {
			currentPlayer = guess.getSelectedItem().toString();
		}

		return guess;
	}

	public JComboBox<String> GuessRoom() {
		JComboBox<String> guess = new JComboBox<String>();
		HumanPlayer h = (HumanPlayer)players.get(0);
		for (Player player : players) {
			if (!Board.getInstance().getGrid()[player.getRow()][player.getCol()].isDoorway()) {
				for (Character thisChar : rooms.keySet()) {
					guess.addItem(rooms.get(thisChar));
				}
			}
			else {
				guess.addItem(rooms.get(player.getVisited().getInitial()));
				break;
			}
		}

		if (currentRoom != null) {
			currentRoom = guess.getSelectedItem().toString();
		}

		return guess;
	}

	private JPanel SubmitButton() {
		JButton submit = new JButton("Submit");
		JPanel panel = new JPanel();
		submit.addActionListener(new SubmitButtonListener());
		panel.add(submit);
		return panel;
	}

	private JPanel CancelButton() {
		JButton cancel = new JButton("Cancel");
		JPanel panel = new JPanel();
		cancel.addActionListener(new CancelButtonListener());
		panel.add(cancel);
		return panel;
	}

	class SubmitButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setSuggestion(currentPlayer, currentRoom, currentWeapon);
			dispose();
		}
	}

	class CancelButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			HumanPlayer h = (HumanPlayer)Board.getInstance().getPlayers().get(0);
			h.setAccusing(false);
			suggestion = new Solution();
			dispose();
		}
	}

	public Solution getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String person, String room, String weapon) {
		this.suggestion = new Solution(person, room, weapon);
	}

}
