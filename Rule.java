/* Siyu Zhou
 *  Rule.java : This class represents how to play Blackjack
 * 	init() : This init the deck, players and dealer.
 * 	setPlayers(int playerNumber) : This method take players into records.
 *  getCard(Player player, boolean isFaceUp) : This method draws a card and faces up the card.
 *  dealEachTwoCards() : This method draws two cards and faces up the card.
 *  addDealerToSeventeen(): This method do dealer's work.
 *  judgeSplit(int num) : Check if players have enough money to split.
 *  isBlackJack(Hand hand) : This method check current state. Blackjack/Natural Black Jack/others
 *  doAction(int action,int num) : Give the user the choice to hit (draw a card) or stand (end their turn). Repeat until the
 * 						   user chooses to stand or until they bust (go over 21). If the player busts the hand is over
 * 						   and the dealer wins. If the player doesn’t bust the dealer continues to draw cards until she
 * 						   busts or her hand exceeds 17.
 *  judgeResult(boolean isDealerBust, int num) : This method informs the player about whether they won, lost, or pushed. The
 *                                              player with the greatest hand, not exceeding 21, wins. It also discards the player's cards
 * 								                to prepare for the next round.
 *  finishRoundDealer(int totalbets) : Displays total money of Dealer.
 *  finishRoundPlayerer(int totalbets) : Displays total money of Player.
 */
public class Rule {

    private static final int NORMAL_BLACK_JACK = 1;

    private static final int NATURAL_BLACK_JACK = 2;

    // the numeber of the player(s) (1 means player vs computer, 2 means player vs player)
    public int playerNumber;

    protected Player[] player = new Player[10];

    protected Player dealer;

    protected Deck deck;

    public void init() {
        this.shuffleDeck(1);
        for(int i =0 ;i<playerNumber-1 ; i++)
            this.player[i].init();
        this.dealer.init();
    }


    public static int getNaturalBlackJack() {
        return NATURAL_BLACK_JACK;
    }


    public void printDivisionLine() {
        System.out.println("--+--+--+--+--+--+--+--+--+--+--+--+--+--");
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void shuffleDeck() {
        this.deck = new Deck();
    }
    public void shuffleDeck(int num) {
        this.deck = new Deck(num);
    }

    public void setPlayerAndDealer(String playerName, int capital) {
        this.player[0] = new Player(playerName, capital);
        // dealer is the computer
        this.dealer = new Computer();
    }
    public void setDealer(Player p){
        for(int i=0;i<playerNumber;i++){
            if(this.getPlayer(i).equals(p)){
                Player temp = this.dealer;
                this.dealer = p;
                this.player[i] = temp;
            }
        }
    }

    public void setPlayers(int playerNumber){
        this.playerNumber = playerNumber;
//        Player[] player = new Player[playerNumber];
        boolean hasDealer = false;
        int index =0;
        for(int i=0;i<playerNumber;i++){
            System.out.println("What's your name, player " + (i+1) + "?");
            String name = ScanInput.readString();
            System.out.println("What's your bank value? (Please input integer, 0<x<100000)");
            int bank = ScanInput.scanPInt();
            //get dealer
            System.out.println("Do you want to be the dealer? (Y/N) ");
            boolean isdealer = ScanInput.readBoolean();
            if(!isdealer&&!hasDealer&&(i==playerNumber-1)){
                System.out.println("There must have a dealer. So you are the dealer");
                this.dealer = new Player(name, bank);
                hasDealer = true;
            }
            if(isdealer){
                if(hasDealer){
                    System.out.println("There is a dealer exist. Do you want to replace it?");
                    boolean x = ScanInput.readBoolean();
                    if(x){
                        hasDealer = true;
                        Player player1 = this.dealer;
                        this.player[index] = player1;
                        index++;
                        this.dealer = new Player(name, bank);
                    }
                    else{
                        Player player = new Player(name, bank);
                        this.player[index] = player;
                    }
                }
                else{
                    this.dealer = new Player(name, bank);
                    hasDealer = true;
                }

            }
            else{
                Player player = new Player(name, bank);
                this.player[index] = player;
                index++;
            }
            this.printDivisionLine();
        }System.out.println(this.dealer.getName()+" is the dealer!");

    }


    public void setBet(int bet,int num) {
        this.player[num].setBet(bet);
        this.player[num].setBank(this.player[num].getBank() - bet);
    }

    public Player getPlayer(int num) {
        return this.player[num];
    }

    public Player getDealer() {
        return this.dealer;
    }

//get one card
    public void getCard(Player player, boolean isFaceUp) {
        Card newCard = this.dealer.distribute(deck, isFaceUp);
        if(newCard != null) {
        }
        else
        {
            this.deck = new Deck(this.deck.discard);
            this.deck.discard.clear();
            newCard = this.dealer.distribute(deck, isFaceUp);
        }
        player.getCurrentHand().hit(newCard);
    }

    public void dealEachTwoCards() {
        for(int i =0 ;i<playerNumber-1 ; i++){
            if(this.player[i].getGame()&&!this.player[i].getFold()){
                this.getCard(this.player[i], true);
                this.getCard(this.player[i], true);
            }
        }
        this.getCard(this.dealer, false);
        this.getCard(this.dealer, true);
    }

    public boolean judgeSplit(int num) {
        // judge whether can split player's cards
        boolean canSplit = false;
        Card card1 = this.player[num].getCurrentHand().getCards().get(0);
        Card card2 = this.player[num].getCurrentHand().getCards().get(1);
        if (card1.getCardName()==card2.getCardName() ||   // if card 1 face equals to card 2 face
                card1.getCardValue() == card2.getCardValue()) {    // or card 1 number equals to card 2 number
            canSplit = true;    // the cards can be split
        }
        return canSplit;
    }

    public int isBlackJack(Hand hand) {
        if (hand.getTotalValue() == 21) {
            //natural
            if (hand.getCards().size() == 2) {
                return 2;
            }
            //not natural
            else {
                return 1;
            }
        }
        //not blackjack
        else {
            return 0;
        }
    }

    public boolean isBust(Hand hand) {
        return hand.getTotalValue() > 21;
    }

    public boolean playerBust(int num) {
        boolean isPlayerBust = true;

        // traverse the hands
        for (Hand hand : this.player[num].getHands()) {
            if (!isBust(hand)) {   // as long as one hand doesn't go bust, player doesn't go bust
                isPlayerBust = false;
                break;
            }
        }
        return isPlayerBust;
    }

    public boolean addDealerToSeventeen() {

        // reverse the face down card first and calculate total value
        this.dealer.getCurrentHand().getCards().get(0).reverseCard();
        this.dealer.getCurrentHand().countValue();
        System.out.print("Dealer:");
        this.dealer.getCurrentHand().printHand();
        this.printDivisionLine();

        // hit until 17
        while (this.dealer.getCurrentHand().getTotalValue() < 17) {
            System.out.println("Dealer draws a card.");
            this.getCard(this.dealer, true);
            System.out.print("Dealer:");
            this.dealer.getCurrentHand().printHand();
            this.printDivisionLine();
        }

        // if goes bust, return true
        if (this.isBust(this.dealer.getCurrentHand())) {
            System.out.println("Dealer " + this.dealer.getName() + " bust!");
            this.printDivisionLine();
            return true;
        }
        return false;
    }

    public boolean BANKRUPTP(int num) {
        // if someone goes bankrupt
        return this.player[num].getBank() <= 0;
    }
    public boolean BANKRUPTD() {
        // if someone goes bankrupt
        return this.dealer.getBank() <= 0;
    }

    public boolean doAction(int action,int num) {
        String name = this.getPlayer(num).getName();
        int index = this.getPlayer(num).getIndexOfCurrentHand();
        switch (action) {
            case 1: {   // if player hits
                System.out.println("Player " + name + "'s hand " + (index + 1) + " hit!");
                System.out.println("Draw a card.");
                this.getCard(this.getPlayer(num), true);
                this.getPlayer(num).getCurrentHand().printHand();

                if (this.isBlackJack(this.getPlayer(num).getCurrentHand()) == NORMAL_BLACK_JACK) {    // if BlackJack, then go to next hand immediately
                    System.out.println("Black Jack!");
                    return true;
                }

                if (this.isBust(this.getPlayer(num).getCurrentHand())) {  // if bust, then go to next hand immediately
                    System.out.println("Player " + name + "'s hand " + (index + 1) + " bust!");
                    return true;
                }
                return false;   // hit but not bust or BlackJack, same hand next round
            }

            case 2: {   // if player stands, go to next hand immediately
                System.out.println("Player " + name + "'s hand " + (index + 1) + " stand!");
                this.getPlayer(num).getCurrentHand().stand();
                return true;
            }

            case 3: {   // if player doubles up, go to next hand immediately
                if (this.getPlayer(num).getBank() < this.getPlayer(num).getCurrentHand().getBet()) { // if player has no money to double up
                    System.out.println("Player " + name + ", you don't have enough money to double up!");
                    return false;
                }
                else {
                    System.out.println("Player " + name + "'s hand " + (index + 1) + " double up!");
                    System.out.println("draw a card");
                    // deduct the bet from the player's capital if double up
                    this.getPlayer(num).setBank(this.getPlayer(num).getBank() - this.getPlayer(num).getCurrentHand().getBet());
                    this.getPlayer(num).getCurrentHand().doubleUp();
                    this.getCard(this.getPlayer(num), true);
                    this.getPlayer(num).getCurrentHand().printHand();

                    if (this.isBlackJack(this.getPlayer(num).getCurrentHand()) != 0) {
                        System.out.println("Natural Black Jack!");
                        return true;
                    }
                    else if (this.isBust(this.getPlayer(num).getCurrentHand())) {
                        System.out.println("Player " + name + "'s hand " + (index + 1) + " bust!");
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void judgeResult(boolean isDealerBust, int num) {
        String playerName = this.player[num].getName();
        String dealerName = this.dealer.getName();

        if (!isDealerBust)
        {    // if dealer not go bust
            for (Hand hand : this.player[num].getHands()) {
                int index = this.player[num].getHands().indexOf(hand);
                if (hand.getTotalValue() <= 21)
                { // if this hand not goes bust, then compare
                    if (hand.getTotalValue() > this.dealer.getCurrentHand().getTotalValue())
                    {  // if hand wins
                        System.out.println("Player " + playerName + "'s hand " + (index + 1) + " wins!");
                        System.out.println("Player " + playerName + " win money: " + (this.player[num].getBank() + hand.getBet())+" + "+ hand.getBet() +" = "+(this.player[num].getBank() + hand.getBet() * 2));
                        // give money to player, deduct money from dealer
                        this.player[num].setBank(this.player[num].getBank() + hand.getBet() * 2);
                        this.dealer.setBank(this.dealer.getBank() - hand.getBet());
                    }
                    else if (hand.getTotalValue() == this.dealer.getCurrentHand().getTotalValue())
                    {  // if result is the same
                        if (hand.getTotalValue() == 21) {   // if natural blackjack
                            // get number of the cards, if number is 2, means natural BlackJack
                            int playerCardNum = hand.getCards().size();
                            int dealerCardNum = this.dealer.getCurrentHand().getCards().size();
                            if ((playerCardNum != 2 && dealerCardNum != 2)||(playerCardNum == 2 && dealerCardNum == 2)) { // player and dealer both not natural BlackJack
                                this.player[num].setBank(this.player[num].getBank() + hand.getBet());
                                System.out.println("Player " + playerName + "'s hand " + (index + 1) + " tie!");
                            }
                            else {
                                if (playerCardNum == 2) {   // player is natural BlackJack while dealer not

                                    // player wins
                                    System.out.println("Natural blackjack! Player " + playerName + "'s hand " + (index + 1) + " wins!");
                                    System.out.println("Player " + playerName + " win money: " + (this.player[num].getBank() + hand.getBet())+" + "+ hand.getBet() +" = "+(this.player[num].getBank() + hand.getBet() * 2));
                                    this.player[num].setBank(this.player[num].getBank() + hand.getBet() * 2);
                                    this.dealer.setBank(this.dealer.getBank() - hand.getBet());
                                } else{    // dealer is natural BlackJack while player not
                                    // dealer wins
                                    System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                                    System.out.println("Natural blackjack! Dealer " + dealerName + " wins! Player " + playerName + "'s hand " + (index + 1) + " loses!");
                                    this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                                }
                            }
                        }
                        else{
                            this.player[num].setBank(this.player[num].getBank() + hand.getBet());
                            System.out.println("Player " + playerName + "'s hand " + (index + 1) + " tie!");
                        }
                    }
                    else {    // if dealer wins
                        System.out.println("Player " + playerName + "'s hand " + (index + 1) + " loses!");
                        System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                        this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                    }
                }
                else {    // if dealer wins
                    System.out.println("Player " + playerName + "'s hand " + (index + 1) + " bust!");
                    System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                    this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                }
            }
        }
        else {    // if dealer goes bust
            for (Hand hand : this.player[num].getHands()) {
                int index = this.player[num].getHands().indexOf(hand);
                if (hand.getTotalValue() > 21) {    // if player goes bust, too. They ties
                    System.out.println("Player " + playerName + "'s hand " + (index + 1) + " bust!");
                    System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                    this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                }
                else {    // player wins
                    System.out.println("Player " + playerName + "'s hand " + (index + 1) + " wins!");
                    System.out.println("Player " + playerName + " win money: " + (this.player[num].getBank() + hand.getBet())+" + "+ hand.getBet() +" = "+(this.player[num].getBank() + hand.getBet() * 2));

                    this.player[num].setBank(this.player[num].getBank() + hand.getBet() * 2);
                    this.dealer.setBank(this.dealer.getBank() - hand.getBet());
                }
            }
        }
    }

    public void dealOneCard() {
        for(int i =0 ;i<playerNumber-1 ; i++){
            if(this.player[i].getGame()&&!this.player[i].getFold()){
                this.getCard(this.player[i], true);
            }
        }
        this.getCard(this.dealer, true);
    }

}
