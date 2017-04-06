package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	// Constructors
	public Card(String cardName, CardType cardType)  {
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
	public Card() {
		cardName = null;
		cardType = null;
	}
	
	// Check equivalence b/w cards
	public boolean equals() {
		return false;
	}
	
	public boolean equals(Card otherCard) {
		if (otherCard.getCardName() == this.cardName && otherCard.getCardType() == this.cardType) {
			return true;
		}
		return false;
	}
	
	// Getters and setters
	public String getCardName() {
		return cardName;
	}


	public void setCardName(String cardName) {
		this.cardName = cardName;
	}


	public CardType getCardType() {
		return cardType;
	}


	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	// Properly print card information
	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", cardType=" + cardType + "]" + "\n";
	}
	

}
