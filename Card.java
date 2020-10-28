/* Siyu Zhou
 * Card.java : This class represents one playing card.
 * 	Card(int cardSuit, int cardFace) : This constructor builds a card with the given suit and face, turned face down.
 * 	getCardName() : This method retrieves the face (ace through king) of this card.
 *  getCardValue() : This method retrieves the numerical value of this card, usually same as card face, except 1 for ace
 * 		         and 10 for jack/queen/king
 *  turnFaceUp() : This method records that the front of the card should be visible.
 * 	reverseCard() : This method records that only the back of the card should be visible.
 *  toString() : Express the cards into words.
 */
public class Card {

    public static final int SPADES   = 1;
    public static final int HEARTS   = 2;
    public static final int CLUBS    = 3;
    public static final int DIAMONDS = 4;

    public static final int ACE      = 1;
    public static final int TWO      = 2;
    public static final int THREE    = 3;
    public static final int FOUR     = 4;
    public static final int FIVE     = 5;
    public static final int SIX      = 6;
    public static final int SEVEN    = 7;
    public static final int EIGHT    = 8;
    public static final int NINE     = 9;
    public static final int TEN      = 10;
    public static final int JACK     = 11;
    public static final int QUEEN    = 12;
    public static final int KING     = 13;

    private int cardSuit;

    private int cardFace;

    private boolean isFaceUp = false;

    public Card(int cardSuit, int cardFace) {
        this.cardFace = cardFace;
        this.cardSuit = cardSuit;
    }
    public String toString(){
        String cardname = "";

        String suitname = "";
        if(this.cardSuit == 1)
            suitname = "Hearts❤";
        if(this.cardSuit == 2)
            suitname = "Clubs️️️️♣";
        if(this.cardSuit == 3)
            suitname = "Spades♠";
        if(this.cardSuit == 4)
            suitname = "Diamonds♦";

        if(this.cardFace == 1 || this.cardFace > 10){
            String facename = "";
            if(this.cardFace == 1)
                facename = "Ace(1/11)";
            if(this.cardFace == 11)
                facename = "Jack(10)";
            if(this.cardFace == 12)
                facename = "Queen(10)";
            if(this.cardFace == 13)
                facename = "King(10)";

            cardname = suitname + "--" + facename;
        }
        else if(this.cardFace != 1 && this.cardFace <= 10)
            cardname = suitname + "--" + this.cardFace;
        return cardname;
    }

    public int getCardName() {
        return this.cardFace;
    }

    public int getCardValue() {
        int value = Math.min(this.cardFace, 10);
        return value;
    }

    public void reverseCard() {
        this.isFaceUp = !this.isFaceUp;
    }

    public boolean getFaceUp() {
        return this.isFaceUp;
    }
}

