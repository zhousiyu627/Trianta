/* Siyu Zhou
 * Hand.java : This class represents the set of cards held by one player (or the dealer).
 * 	Hand() : This constructor builds a hand (with no cards, initially).
 * 	public ArrayList<Card> getCards() : This method retrieves a particular card in this hand.  The card number is zero-based.
 *  countValue() : This method computes the score of this hand.
 *  discardAll() : This methods discards all cards in this hand.
 *  printHand() : These methods return String of card in hand. Player returns all cards.  Dealer returns
 * 					   one card + face down.
 */
import java.util.ArrayList;

public class Hand
{
	// The set of cards in this hand
	protected ArrayList<Card> cards;

	// Bet on this hand
	protected int bet;

	protected boolean hasOneAce = false;

	// TotalValue of cards i this hand
	protected int totalValue;
	
	public Hand() {
		this.cards = new ArrayList<>();
		this.totalValue = 0;
	}
	
	public Hand(int bet, Card card) {
		this.cards = new ArrayList<>();
		this.cards.add(card);
		this.bet = bet;
		this.totalValue = 0;
		this.countValue();
	}

	public void setBet(int bet)
	{
		this.bet = bet;
	}

	public int getBet()
	{
		return this.bet;
	}

	public ArrayList<Card> getCards()
	{
		return cards;
	}
	
	public int getTotalValue()
	{
		return this.totalValue;
	}

	public void hit(Card newCard)
	{
		cards.add(newCard);
		this.countValue();
	}

	public boolean bust() {
		if (this.totalValue > 21) {
			return true;
		} else {
			return false;
		}
	}

	public void countValue() {
		this.totalValue = 0;
		int aceNum = 0;
		for (Card card : cards) {
			if (!card.getFaceUp())
				continue;	// If the card face down, don't calculate it at this time
			if(card.getCardName()==1) {	// If the card is Ace, mark down it but don't calculate it right now
				aceNum++;
				continue;
			}
			this.totalValue += card.getCardValue();
		}
		while(aceNum-- > 0) {	// Determine the value of all Aces after calculating all other cards
			if (this.totalValue + 11 <= 21) {
				this.totalValue += 11;
			} else {
				this.totalValue += 1;
			}
		}
	}

	public void printHand() {
		System.out.println();
		System.out.println("Cards: ");
		for (Card card : cards) {
			if (card.getFaceUp()) {
				System.out.println(card.toString() + " ");
			} else {	// If the card face down, print "?" instead
				System.out.println("A face down card.");
			}
		}
		System.out.println();
		System.out.println("Current Value: " + this.totalValue);
		System.out.println();
	}

	public void stand()
	{
		//Count the total value.
		this.countValue();
		this.printHand();
	}

	public void doubleUp()
	{
		this.bet = this.bet * 2;
	}
}
