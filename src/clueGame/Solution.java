package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;

	public Solution() {}
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	@Override
	public String toString() {
		return "Solution [person=" + person + ", room=" + room + ", weapon=" + weapon + "]";
	}
}
