import java.util.ArrayList;
/* Siyu Zhou
 * TriantaHand.java : This class represents the set of cards held by Trianta Ena player (or the dealer).
 * 	TriantaHand() : This constructor builds a hand (with no cards, initially).
 *  countValue() : This method computes the score of this hand.
 *  printHand() : These methods return String of card in hand. Player returns all cards.  Dealer returns
 * 					   one card + face down.
 */
public class TriantaHand extends Hand {

    public TriantaHand() {
        super();
        this.hasOneAce = false;
    }

    public TriantaHand(int bet, Card card) {
        super(bet,card);
        this.hasOneAce = false;
    }

    @Override
    public void printHand() {
        System.out.println();
        System.out.println("Cards: ");
        for (Card card : cards) {
            System.out.println(card.toString() + " ");
        }
        System.out.println();
        System.out.println("Current Value: " + this.totalValue);
        System.out.println();
    }

    public void hit(Card newCard)
    {
        this.cards.add(newCard);
        this.countValue();
    }


    public void countValue() {
        this.totalValue = 0;
        int aceNum = 0;
        for (Card card : cards) {
            if(card.getCardName()==1) {	// If the card is Ace, mark down it but don't calculate it right now
                aceNum++;
                continue;
            }
            this.totalValue += card.getCardValue();
        }
        this.hasOneAce = false;
        while(aceNum-- > 0) {	// Determine the value of all Aces after calculating all other cards
            if(!this.hasOneAce){
                if (this.totalValue + 11 <= 31) {
                    this.totalValue += 11;
                } else {
                    this.totalValue += 1;
                    this.hasOneAce = true;
                }
            }
            else{
                this.totalValue += 11;
            }
        }
    }

    public void stand()
    {
        //Count the total value.
        this.countValue();
        this.printHand();
    }
}
