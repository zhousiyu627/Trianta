/* Siyu Zhou
 *  CardGame.java : This class represents Card Games like BlackJack and Trianta Ena...
 * 	CardGame() : Welcome words.
 *  inquirePlayerName(int num) : Ask players' name.
 *  isPlayerWillingToNR(int num) : Ask if the player go on playing.
 *  inquirePlayerBet(int num) : Get bet and check if the bet is valid.
 *  askPlayerToDoActions(int num) : Do options like hit/double/split...
 *  inquirePlayerBank() : Get players' bank value.
 *  printDealerCards()/printCurrentCards() : Print current cards in hand.
 */
public class CardGame {
    protected GameState currentState;

    protected Rule rule;

    protected ScanInput scanInput;

    protected int playerNumber;

    protected int count = 0;

    public CardGame() {
        System.out.println("Welcome to Card Game!");
    }
    public String inquirePlayerName(int num) {
        if (num == 0) {
            System.out.print("Please input Player's name: ");
        }
        else {
            System.out.printf("Please input Player %d's name: ", num);
        }
        return this.scanInput.scanString();
    }

    public void printDivisionLine() {
        System.out.println("--+--+--+--+--+--+--+--+--+--+--+--+--+--");
    }

    public void printbankState() {
        for(int i=0;i<playerNumber-1;i++)
            if(this.rule.getPlayer(i).getGame()){
                System.out.println("Player " + this.rule.getPlayer(i).getName() + "'s bank: " + this.rule.getPlayer(i).getBank());
            }
        System.out.println("Dealer " + this.rule.getDealer().getName() + "'s bank: " + this.rule.getDealer().getBank());
    }

    public boolean isPlayerWillingToNR(int num) {
        System.out.println(this.rule.getPlayer(num).getName() + ", do you want to play next round? (Y/N) ");
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
            if(this.rule.getPlayer(i).getGame()){
                System.out.println("Player " + this.rule.getPlayer(i).getName() + "'s current hand " + (this.rule.getPlayer(i).getIndexOfCurrentHand() + 1) + ":");
                this.rule.getPlayer(i).getCurrentHand().printHand();
                this.printDivisionLine();
            }
        }

        // print the dealer's hand cards
        System.out.println("Dealer " + this.rule.getDealer().getName() + "'s hand:");
        this.rule.getDealer().getCurrentHand().printHand();
    }

    public int inquirePlayerBet(int num) {
        System.out.print("Please input your bet, "+this.rule.getPlayer(num).getName()+", your current money is: "+this.rule.getPlayer(num).getBank() +": ");
        boolean isValidateInput = false;
        int bet = 0;
        while (!isValidateInput) {
            bet = scanInput.scanInt();
            if (bet <= 0) { // bet must be positive
                System.out.println("Your bet must be positive, please input again: ");
            } else if (bet <= this.rule.getPlayer(num).getBank()) {   // bet cannot exceed the capital of the player
                isValidateInput = true;
            } else {
                System.out.print("Your bet must be no more than your capital, please input again: ");
            }
        }
        return bet;
    }

    public int askPlayerToDoActions(int num) {
        String name = this.rule.getPlayer(num).getName();
        int index = this.rule.getPlayer(num).getIndexOfCurrentHand();

        System.out.println("Player " + name + "'s hand " + (index + 1) + ":");
        this.rule.getPlayer(num).getCurrentHand().printHand();
        System.out.println("Please take an action from the following options, " + name + "'s hand " + (index + 1) + " (Enter the number): ");
        System.out.println("1.Hit 2.Stand");

        int action = 0;
        boolean isValidateInput = false;
        while (!isValidateInput) {
            action = this.scanInput.scanInt();
            this.printDivisionLine();
            if (action >= 1 && action <= 2) {
                isValidateInput = true;
            }
            else {
                System.out.println("You can only choose from these three actions! Please input again: ");
                System.out.println("1.Hit 2.Stand");
            }
        }
        return action;
    }

        public int inquirePlayerBank() {
            System.out.print("Please input your bank value (Positive Integer): ");
            boolean isValidateInput = false;
            int capital = 0;
            while (!isValidateInput) {
                capital = this.scanInput.scanInt();
                if (capital > 0) {
                    isValidateInput = true;
                } else {    // capital cannot be 0 or negative
                    System.out.print("Your money must be positive, please input again: ");
                }
            }
            return capital;
        }

        public boolean inquirePlayerDealer() {
            System.out.println("Do you want to be the dealer? (Y/N) ");
            return ScanInput.readBoolean();
        }
    public void printDealerCards() {
        // print the dealer's hand cards
        System.out.println("Dealer " + this.rule.getDealer().getName() + "'s hand:");
        this.rule.getDealer().getCurrentHand().printHand();
    }
}
