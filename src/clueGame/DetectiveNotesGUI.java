package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesGUI extends JDialog {

	ArrayList<String> weapons = Board.getWeapons();
	ArrayList<Player> players = Board.getPlayers();
	Map<Character, String> Rooms = Board.getLegend();

	public DetectiveNotesGUI() {
		setTitle("Detective Notes");

		JPanel CheckBoxesPanel = new JPanel();
		CheckBoxesPanel.setLayout(new GridLayout(3,0));
		CheckBoxesPanel.add(People());
		CheckBoxesPanel.add(Weapons());
		CheckBoxesPanel.add(Rooms());

		JPanel ComboBoxPanel = new JPanel();
		ComboBoxPanel.setLayout(new GridLayout(3,0));
		ComboBoxPanel.add(PeopleGuess());
		ComboBoxPanel.add(WeaponGuess());
		ComboBoxPanel.add(RoomGuess());

		setLayout(new GridLayout(0,2));
		add(CheckBoxesPanel);
		add(ComboBoxPanel);

		pack();
	}

	public JPanel People() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		panel.setLayout(new GridLayout(0,2));


		JCheckBox checkBox = new JCheckBox();

		for (Player p : players) {
			checkBox = new JCheckBox(p.getPlayerName());
			panel.add(checkBox);
		}

		return panel;
	}

	public JPanel Weapons() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));

		panel.setLayout(new GridLayout(0,2));



		JCheckBox checkBox = new JCheckBox();

		for (String s : weapons) {
			checkBox = new JCheckBox(s);
			panel.add(checkBox);
		}

		return panel;	
	}


	public JPanel Rooms() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));

		panel.setLayout(new GridLayout(0,2));

		JCheckBox checkBox = new JCheckBox();

		for (String s : Rooms.values()) {
			if (!(s.equals("Walkway") || s.equalsIgnoreCase("AirLock"))) {
				checkBox = new JCheckBox(s);
				panel.add(checkBox);
			}
		}

		return panel;	
	}

	public JComboBox<String> PeopleGuess() {
		JComboBox<String> people = new JComboBox<String>();

		for (Player p: players) {
			people.addItem(p.getPlayerName());
		}

		return people;
	}

	public JComboBox<String> WeaponGuess() {
		JComboBox<String> WeaponGuess = new JComboBox<String>();

		for (String s: weapons) {
			WeaponGuess.addItem(s);
		}

		return WeaponGuess;
	}

	public JComboBox<String> RoomGuess() {
		JComboBox<String> rooms = new JComboBox<String>();

		for (String s: Rooms.values()) {
			if (!(s.equals("Walkway") || s.equalsIgnoreCase("AirLock"))) {
				rooms.addItem(s);
			}
		}

		return rooms;
	}


}
