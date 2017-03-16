package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	
	
	public void setPerson(String person) {
		this.person = person;
	}



	public void setRoom(String room) {
		this.room = room;
	}



	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}



	Solution(String room, String person, String weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
	
	
}
