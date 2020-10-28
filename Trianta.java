/* Siyu Zhou
 *  Trianta.java : This class holds the game process of Trianta Ena, begin from choose the number of players and input
 *  players’ names and money, end at the time when all player quits or runs out of money.
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
public class Trianta extends CardGame{
    public Trianta() throws InterruptedException {
        this.start();
    }

    public void start() throws InterruptedException {
        System.out.println("Welcome to Trianta Ena Game!");
        this.printDivisionLine();

        // create a rule object in which logical calculus of the game will be done
        this.rule = new TriantaRule();

        this.scanInput = new ScanInput();

        // inquire of the user about the number of player(s)
        System.out.print("Please enter the number of player(s): (2<=number<=9)");
        this.playerNumber = ScanInput.scanInt(2,9);

        // set the number of player(s) to the rule object
        this.rule.setPlayerNumber(this.playerNumber);
        this.printDivisionLine();

        rule.playerNumber = this.playerNumber;
        rule.setPlayers(this.playerNumber);

        this.printDivisionLine();

        System.out.println("Game Start!");

        // if game starts, set current state as PLAYING
        this.currentState = GameState.PLAYING;
        this.rule.shuffleDeck(2);

        while (this.currentState == GameState.PLAYING) { // while is playing
            // new round with same deck && clear player and dealer's hands
            this.rule.init();
            this.printDivisionLine();
            System.out.println("dealing cards");
            this.rule.dealOneCard();
            this.printCurrentCards();
            this.printDealerCards();

            // inquire of the player about the new bet for this round
            for(int i=0;i<playerNumber-1;i++){
                if(this.rule.getPlayer(i).getGame()){
                    if(isPlayerWillingToF(i)){
                        this.rule.getPlayer(i).fold();
                    }
                }
            }
            for(int i=0;i<playerNumber-1;i++){
                if(this.rule.getPlayer(i).getGame()&&!this.rule.getPlayer(i).getFold()){
                    int bet = this.inquirePlayerBet(i);
                    this.rule.setBet(bet,i);
                    this.printDivisionLine();
                }
            }
            System.out.println("dealing cards");
            this.rule.dealEachTwoCards();
            this.printDivisionLine();

            // judge whether the current hand can be splited
            for(int i=0;i<playerNumber-1;i++){
                if(this.rule.getPlayer(i).getGame()&&!this.rule.getPlayer(i).getFold()){
                    if (this.rule.isBlackJack(this.rule.getPlayer(i).getCurrentHand()) == Rule.getNaturalBlackJack()) {    // if player gets natural BlackJack
                        System.out.println(this.rule.getPlayer(i).getName() + ", Natural Trianta!!!");
                        this.printDivisionLine();
                    }
                    else {    // if player is not natural BlackJack
                        while (true) {
                            int action;
                            action = this.askPlayerToDoActions(i);
                            // do action
                            boolean changeNextHand = this.rule.doAction(action,i);
                            if (changeNextHand) {   // if need to change to the next hand
                                this.printDivisionLine();
                                break;
                            }
                            this.printDivisionLine();
                        }
                    }
                    boolean isPlayerBust = this.rule.playerBust(i);
                    if (isPlayerBust) { // if player bust, player loses immediately
                        this.rule.getDealer().setBank(this.rule.getDealer().getBank() + this.rule.getPlayer(i).getHands().size() * this.rule.getPlayer(i).getCurrentHand().getBet());
                    }
                }
            }
            // if player is not bust
            // judge whether dealer is bust
            boolean isDealerBust = this.rule.addDealerToSeventeen();
            for(int i=0;i<playerNumber-1;i++) {
                if (this.rule.getPlayer(i).getGame()&&!this.rule.getPlayer(i).getFold()) {
                    // judge the result of this round and calculate money
                    this.rule.judgeResult(isDealerBust,i);
                    this.printDivisionLine();
                }
            }

            // print the result of this round
            this.printbankState();
            this.printDivisionLine();

            // judge whether the player can start next round and whether the player is willing to start the next round
            for(int i=0;i<playerNumber-1;i++){
                if(this.rule.getPlayer(i).getGame()&&!this.rule.getPlayer(i).getFold()){
                    if (this.rule.BANKRUPTD())
                    {
                        System.out.println("Dealer goes bankrupt!");
                        this.currentState = GameState.PLAYER_GO_BANKRUPT;
                    }
                    else if (this.rule.BANKRUPTP(i)) {
                        System.out.println(this.rule.getPlayer(i).getName() + " goes bankrupt!");
                        this.rule.getPlayer(i).quit();
                    }
                    else if(!isPlayerWillingToNR(i)){
                        System.out.println(this.rule.getPlayer(i).getName() + " quit.");
                        this.rule.getPlayer(i).quit();
                    }
                }
            }
            count = 0;
            for(int i=0;i<playerNumber-1;i++){
                if(this.rule.getPlayer(i).getGame()){
                    count++;
                }
                if(count == 0){
                    this.currentState = GameState.PLAYER_QUIT;
                }
                else{
                    this.currentState = GameState.PLAYING;
                }
            }
            //change dealer
            int money = this.rule.dealer.getBank();
            ArrayList<Player> sortplayer = new ArrayList<Player>();
            for(int i=0;i<playerNumber-1;i++){
                if(this.rule.getPlayer(i).getGame()){
                    if(this.rule.getPlayer(i).getBank()>money){
                        sortplayer.add(this.rule.getPlayer(i));
                    }
                }
            }
            Collections.sort(sortplayer);
            for (Player dodealer : sortplayer) {
                System.out.println("Do you want to be the dealer, "+dodealer.getName()+"?");
                boolean isdealer = ScanInput.readBoolean();
                if(isdealer){
                    this.rule.setDealer(dodealer);
                    break;
                }
            }
        }

        if (this.currentState == GameState.PLAYER_QUIT) {    // if every player doesn't want to continue to play
            System.out.println("All player quits the game! Game over!");
        }
        System.out.println("Dealer is "+this.rule.getDealer().getName());
    }

    public boolean isPlayerWillingToF(int num) {
        System.out.println(this.rule.getPlayer(num).getName() + ", do you want to fold this round? (Y/N) ");
        boolean isPlayerWillingToNR = false;
        boolean isValidateInput = false;
        while (!isValidateInput) {
            String input = this.scanInput.scanString();
            if (input.toUpperCase().equals("Y")) {
                isPlayerWillingToNR = true;
                isValidateInput = true;
            } else if (input.toUpperCase().equals("N")) {
                isValidateInput = true;
            } else {
                System.out.println("Please input Y or N: ");
            }
        }
        return isPlayerWillingToNR;
    }
    public void printCurrentCards() {
        // print the player's hand cards
        for(int i=0;i<playerNumber-1;i++){
            if(this.rule.getPlayer(i).getGame()&&!this.rule.getPlayer(i).getFold()){
                System.out.println("Player " + this.rule.getPlayer(i).getName() + ":");
                this.rule.getPlayer(i).getCurrentHand().printHand();
                this.printDivisionLine();
            }
        }
    }
    public void printDealerCards() {
        // print the dealer's hand cards
        System.out.println("Dealer " + this.rule.getDealer().getName() + "'s hand:");
        this.rule.getDealer().getCurrentHand().printHand();
    }

}
