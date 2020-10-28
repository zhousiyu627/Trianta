/* Siyu Zhou
 * Deck.java : This class represents the deck of cards from which cards are dealt to players
 * 	Deck() : This constructor builds a deck of 52 cards.
 *  Deck(int num) : This constructor builds n deck of 52 cards.
 * 	Card randomDealt() : This method shuffles the deck and draw a card.
 */
import java.util.ArrayList;

public class Deck {
    // a arrayList storing all the cards in a round of a game and indicating the deck
    private ArrayList<Card> cards;
    public ArrayList<Card> discard;

    // initialize the deck
    // Cards except King and Queen will compose the deck and will be added into the arrayList
    public Deck() {
        this.cards = new ArrayList<>();
        for (int i = 1; i<14; i++){
            for (int j = 1; j < 5; j++) {
                cards.add(new Card(j,i));
            }
        }
    }
    public Deck(int num) {
        this.cards = new ArrayList<>();
        this.discard = new ArrayList<>();
        for(int k = 0; k < num ; k++){
            for (int i = 1; i<14; i++){
                for (int j = 1; j < 5; j++) {
                    cards.add(new Card(j,i));
                }
            }
        }
    }
    public Deck(ArrayList<Card> discard) {
        this.cards = new ArrayList<>();
        cards.addAll(discard);
    }

    public Card randomDealt() {
        if(this.cards.isEmpty()){
            this.cards.addAll(discard);
            this.discard.clear();
        }
        Card card = this.cards.get((int) (Math.random() * this.cards.size()));
        if(!card.getFaceUp())
            card.reverseCard();
        this.discard.add(card);
        this.cards.remove(card);
        return card;
    }
}
