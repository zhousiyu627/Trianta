/* Siyu Zhou
 *  TriantaRule.java : This class represents how to play Trianta, easier than Blackjack.
 * 	init() : This init the deck, players and dealer.
 * 	setPlayers(int playerNumber) : This method take players into records.
 *  getCard(Player player, boolean isFaceUp) : This method draws a card and faces up the card.
 *  dealEachTwoCards() : This method draws two cards and faces up the card.
 *  addDealerToSeventeen(): This method do dealer's work.
 *  judgeSplit(int num) : Check if players have enough money to split.
 *  isBlackJack(Hand hand) : This method check current state. 31/Natural 31/others
 *  doAction(int action,int num) : Give the user the choice to hit (draw a card) or stand (end their turn). Repeat until the
 * 						   user chooses to stand or until they bust (go over 31). If the player busts the hand is over
 * 						   and the dealer wins. If the player doesn’t bust the dealer continues to draw cards until she
 * 						   busts or her hand exceeds 27.
 *  judgeResult(boolean isDealerBust, int num) : This method informs the player about whether they won, lost, or pushed. The
 *                                              player with the greatest hand, not exceeding 31, wins. It also discards the player's cards
 * 								                to prepare for the next round.
 *  finishRoundDealer(int totalbets) : Displays total money of Dealer.
 *  finishRoundPlayerer(int totalbets) : Displays total money of Player.
 */
public class TriantaRule extends Rule{
    private static final int NORMAL_TRIANTA = 1;

    private static final int NATURAL_TRIANTA = 2;

    public static int getNaturalBlackJack() {
        return NATURAL_TRIANTA;
    }
    @Override
    public void init() {
//        this.shuffleDeck(2);
        for(int i =0 ;i<playerNumber-1 ; i++)
            this.player[i].init(true);
        this.dealer.init();
    }
    public void setDealer(Player p){
        for(int i=0;i<playerNumber-1;i++){
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
        System.out.println("What's everybody's bank value? (Please input integer, 0<x<100000)");
        int bank = ScanInput.scanPInt();
        System.out.println("Dealer's bank value will be "+bank*3);

        for(int i=0;i<playerNumber;i++){
            System.out.println("What's your name, player " + (i+1) + "?");
            String name = ScanInput.readString();
            //get dealer
            System.out.println("Do you want to be the dealer? (Y/N) ");
            boolean isdealer = ScanInput.readBoolean();
            if(!isdealer&&!hasDealer&&(i==playerNumber-1)){
                System.out.println("There must have a dealer. So you are the dealer");
                this.dealer = new Player(name, bank*3,true);
                hasDealer = true;
            }
            if(isdealer){
                if(hasDealer){
                    System.out.println("There is a dealer exist. Do you want to replace it?");
                    boolean x = ScanInput.readBoolean();
                    if(x){
                        Player player1 = this.dealer;
                        this.player[index] = player1;
                        this.player[index].setBank(bank);
                        index++;
                        this.dealer = new Player(name, bank*3,true);
                    }
                    else{
                        Player player = new Player(name, bank,true);
                        this.player[index] = player;
                    }
                }
                else{
                    this.dealer = new Player(name, bank*3,true);
                    hasDealer = true;
                }

            }
            else{
                Player player = new Player(name, bank,true);
                this.player[index] = player;
                index++;
            }
            this.printDivisionLine();
        }System.out.println(this.dealer.getName()+" is the dealer!");
    }

    public int isBlackJack(Hand hand) {
        if (hand.getTotalValue() == 31) {
            //natural
            if (hand.getCards().size() == 3) {
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
        return hand.getTotalValue() > 31;
    }

    public void dealOneCard() {
        for(int i =0 ;i<playerNumber-1 ; i++){
            if(this.player[i].getGame()&&!this.player[i].getFold()){
                this.getCard(this.player[i], true);
            }
        }
        this.getCard(this.dealer, true);
    }

    public void dealEachTwoCards() {
        for(int i =0 ;i<playerNumber-1 ; i++){
            if(this.player[i].getGame()&&!this.player[i].getFold()){
                this.getCard(this.player[i], true);
                this.getCard(this.player[i], true);
            }
        }
    }


    public boolean addDealerToSeventeen() {

        // reverse the face down card first and calculate total value
        this.dealer.getCurrentHand().countValue();
        System.out.print("Dealer:");
        this.dealer.getCurrentHand().printHand();
        this.printDivisionLine();

        // hit until 17
        while (this.dealer.getCurrentHand().getTotalValue() < 27) {
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

    public boolean doAction(int action,int num){
        String name = this.getPlayer(num).getName();
        switch (action) {
            case 1: {   // if player hits
                System.out.println("Player " + name + " hit!");
                System.out.println("Draw a card.");
                this.getCard(this.getPlayer(num), true);
                this.getPlayer(num).getCurrentHand().printHand();

                if (this.isBlackJack(this.getPlayer(num).getCurrentHand()) == NORMAL_TRIANTA) {    // if BlackJack, then go to next hand immediately
                    System.out.println("Trianta Ena!");
                    return true;
                }

                if (this.isBust(this.getPlayer(num).getCurrentHand())) {  // if bust, then go to next hand immediately
                    System.out.println("Player " + name + " bust!");
                    return true;
                }
                return false;   // hit but not bust or BlackJack, same hand next round
            }

            case 2: {   // if player stands, go to next hand immediately
                System.out.println("Player " + name + " stand!");
                this.getPlayer(num).getCurrentHand().stand();
                return true;
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
                if (hand.getTotalValue() <= 31)
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
                        if (hand.getTotalValue() == 31) {   // if natural blackjack
                            // get number of the cards, if number is 2, means natural BlackJack
                            int playerCardNum = hand.getCards().size();
                            int dealerCardNum = this.dealer.getCurrentHand().getCards().size();
                            if ((playerCardNum != 3 && dealerCardNum != 3)||(playerCardNum == 3 && dealerCardNum == 3)) { // player and dealer both not natural BlackJack
                                System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                                System.out.println("Tie! Dealer " + dealerName + " wins! Player " + playerName + " loses!");
                                this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                            }
                            else {
                                if (playerCardNum == 3) {   // player is natural BlackJack while dealer not
                                    // player wins
                                    System.out.println("Natural Trianta Ena! Player " + playerName + " wins!");
                                    System.out.println("Player " + playerName + " win money: " + (this.player[num].getBank() + hand.getBet())+" + "+ hand.getBet() +" = "+(this.player[num].getBank() + hand.getBet() * 2));
                                    this.player[num].setBank(this.player[num].getBank() + hand.getBet() * 2);
                                    this.dealer.setBank(this.dealer.getBank() - hand.getBet());
                                }
                                else{    // dealer is natural BlackJack while player not
                                    // dealer wins
                                    System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                                    System.out.println("Natural Trianta Ena! Dealer " + dealerName + " wins! Player " + playerName + " loses!");
                                    this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                                }
                            }
                        }
                        else{
                            System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                            System.out.println("Tie! Dealer " + dealerName + " wins! Player " + playerName + " loses!");
                            this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                        }
                    }
                    else {    // if dealer wins
                        System.out.println("Player " + playerName + " loses!");
                        System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                        this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                    }
                }
                else {    // if dealer wins
                    System.out.println("Player " + playerName + " bust!");
                    System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                    this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                }
            }
        }
        else {    // if dealer goes bust
            for (Hand hand : this.player[num].getHands()) {
                if (hand.getTotalValue() > 31) {    // if player goes bust, too. They ties
                    System.out.println("Player " + playerName + " bust!");
                    System.out.println("Player " + playerName + " lose money: " + (this.player[num].getBank() + hand.getBet())+" - "+ hand.getBet() +" = "+this.player[num].getBank());
                    this.dealer.setBank(this.dealer.getBank() + hand.getBet());
                }
                else {    // player wins
                    System.out.println("Player " + playerName + " wins!");
                    System.out.println("Player " + playerName + " win money: " + (this.player[num].getBank() + hand.getBet())+" + "+ hand.getBet() +" = "+(this.player[num].getBank() + hand.getBet() * 2));
                    this.player[num].setBank(this.player[num].getBank() + hand.getBet() * 2);
                    this.dealer.setBank(this.dealer.getBank() - hand.getBet());
                }
            }
        }
    }
}
