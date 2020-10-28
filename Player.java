/* Siyu Zhou
 *  Player.java : This class represents one blackjack player (or the dealer)
 * 	Player() : This constructor creates a player.
 *  split() : Check if the hand can spilt. If can, split new a hand.
 *  compareTo(Player a) : Use to sort. Get an ordered array of players whose bank is bigger than dealer's.
 */
import java.util.ArrayList;

public class Player implements Dealer, java.lang.Comparable<Player>
{
	private String name;
	private boolean game;
	private boolean fold;
	private ArrayList<Hand> hands;

	//total money of a player
	private int bank;
	private boolean Trianta;

	//the hand player is dealing with
	private Hand currentHand;

	public Player(String name, int bank) {
		this.name = name;
		this.hands = new ArrayList<>();
		Hand newHand = new Hand();
		this.hands.add(newHand);
		this.currentHand = newHand;
		this.bank = bank;
		this.game = true;
		this.fold = false;
	}
	public Player(String name, int bank,boolean Trianta) {
		this.name = name;
		this.hands = new ArrayList<>();
		TriantaHand newHand = new TriantaHand();
		this.hands.add(newHand);
		this.currentHand = newHand;
		this.bank = bank;
		this.game = true;
		this.fold = false;
	}

	public void setBet(int bet) {
		this.currentHand.setBet(bet);
	}

	public void init() {
		this.hands = new ArrayList<>();
		Hand newHand = new Hand();
		this.hands.add(newHand);
		this.currentHand = newHand;
		this.fold = false;
	}
	public void init(boolean Trianta){
		this.hands = new ArrayList<>();
		TriantaHand newHand = new TriantaHand();
		this.hands.add(newHand);
		this.currentHand = newHand;
		this.fold = false;
	}

	public void quit(){
		this.game = false;
	}
	public void fold(){
		this.fold = true;
	}

	public boolean getGame(){
		return this.game;
	}
	public boolean getFold(){
		return this.fold;
	}

	public void split() {
		//this.currentHand.getCards() returns an array, which has two same card. get(1) gets one of two cards.
		Card newCard = this.currentHand.getCards().get(1);
		//same bet in new hand, and the split card.
		Hand newHand = new Hand(this.currentHand.getBet(), newCard);
		this.bank = this.bank - this.currentHand.getBet();	// set another bet of another hand
		//array hands: hands[0],hands[1]
		this.hands.add(newHand);
		this.currentHand.getCards().remove(1);	// remove the card, as it has been split into another hand
		//same as new hand value
		this.currentHand.countValue();
	}

	public void nextHand() {
		int index = hands.indexOf(this.currentHand);
		//1.this.hands.size()=2
		//2.index == 0
		if(index < this.hands.size() - 1)
		{
			//next hand
			this.currentHand = hands.get(index + 1);
		}
	}
	
	public int getIndexOfCurrentHand() {
		return hands.indexOf(this.currentHand);
	}

	public String getName() {
		return this.name;
	}

	public int getBank() {
		return this.bank;
	}

	public void setBank(int bank) {
		this.bank = bank;
	}

	public Hand getCurrentHand() {
		return this.currentHand;
	}

	public ArrayList<Hand> getHands() {
		return this.hands;
	}

	/**
	 * Dealer deal a new deck from cad deck
	 *
	 * @param deck the card deck where dealer deal new card.
	 * @return return the new card
	 */
	public Card distribute(Deck deck, boolean isFaceUp) {
		return deck.randomDealt();
	}
	@Override
	public int compareTo(Player a){
		return this.getBank()-a.getBank();
	}
}
