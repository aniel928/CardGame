import java.util.Random;

public class Card implements Comparable<Card>{
	// ordered lowest to highest, so we can compare indexes
	final String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
	// ordered lowest to highest, so we can compare indexes - putting Penalty here so there are 4 total, one for each suit.
	final String[] CARDS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Penalty"};
	
	//card variables
	int suit;
	int card;

	//Constructor - assign a random card and suit.
	public Card() {
		this.suit = new Random().nextInt(SUITS.length);
		this.card = new Random().nextInt(CARDS.length);
	}
	
	//Print out card to screen (e.g. "2 of hearts", "ace of spades"
	public String toString() {
		int suit = this.suit;
		int card = this.card;
		if(CARDS[card].equals("Penalty")) {
			return "Penalty Card " + (suit + 1); //e.g. displays "Penalty Card 1" or "Penalty Card 4"
		}
		return CARDS[card] + " of " + SUITS[suit];
	}
	
	//compares to find highest, returns -1 if lower, 1 if higher, 0 if equal
	@Override
	public int compareTo(Card card) {
		if(CARDS[this.card].equals("Penalty")) {
			return -1;
		}
		
		if(CARDS[card.card].equals("Penalty")) {
			return 1;
		}
		
		if(this.card < card.card) {
			return -1;
		}
		else if(this.card > card.card){
			return 1;
		}
		else{
			if(this.suit < card.suit) {
				return -1;
			}
			else if (this.suit > card.suit) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
	}
	
	//checks to see if card is penalty card.
	public boolean isPenalty() {
		if(CARDS[this.card].equals("Penalty")) {
			return true;
		}
		else {
			return false;
		}
	}
}
