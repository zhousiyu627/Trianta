/* Siyu Zhou
 *  Blackjack.java : This class holds the game process of BlackJack, begin from choose the number of players and input
 *  players’ names and money, end at the time when all player quits or runs out of money.
 */
public class BlackJack extends CardGame{

    public BlackJack() throws InterruptedException {
        this.start();
    }

    public void start() throws InterruptedException {
        System.out.println("Welcome to BlackJack Game!");
        this.printDivisionLine();

        // create a rule object in which logical calculus of the game will be done
        this.rule = new Rule();

        this.scanInput = new ScanInput();

        // inquire of the user about the number of player(s)
        System.out.print("Please enter the number of player(s): ");
        this.playerNumber = this.scanInput.scanInt();

        // set the number of player(s) to the rule object
        this.rule.setPlayerNumber(this.playerNumber);
        this.printDivisionLine();

        // set the player and the dealer
        if (playerNumber == 1) {
            // inquire of the player about the name and the capital
            String playerName = this.inquirePlayerName(0);
            int capital = this.inquirePlayerBank();

            // set the player and dealer in the rule object
            this.rule.setPlayerAndDealer(playerName, capital);
            this.printDivisionLine();
            System.out.println("Computer is the dealer!");
        }
        else
            {
                rule.playerNumber = this.playerNumber;
                rule.setPlayers(this.playerNumber);

        }
        this.printDivisionLine();

        System.out.println("Game Start!");

        // if game starts, set current state as PLAYING
        this.currentState = GameState.PLAYING;

        while (this.currentState == GameState.PLAYING) { // while is playing
            // new round with new deck && clear player and dealer's hands
            this.rule.init();
            this.printDivisionLine();

            // inquire of the player about the new bet for this round
            for(int i=0;i<playerNumber-1;i++){
                if(this.rule.getPlayer(i).getGame()){
                    int bet = this.inquirePlayerBet(i);
                    this.rule.setBet(bet,i);
                    this.printDivisionLine();
                }
            }
            System.out.println("dealing cards");
            this.rule.dealEachTwoCards();
            this.printCurrentCards();
            this.printDivisionLine();

            // judge whether the current hand can be splited
            for(int i=0;i<playerNumber-1;i++){
                if(this.rule.getPlayer(i).getGame()){
                    boolean canSplit = this.rule.judgeSplit(i);
                    // ask player whether to split his current hand or not
                    if (canSplit) { // if cards can be split
                        if (isPlayerWillingToSplit(i)) { // if the player is willing to split
                            this.rule.getPlayer(i).split();
                            this.printDivisionLine();
                        }
                    }
                    if (this.rule.isBlackJack(this.rule.getPlayer(i).getCurrentHand()) == Rule.getNaturalBlackJack()) {    // if player gets natural BlackJack
                        System.out.println(this.rule.getPlayer(i).getName() + ", Natural lack Jack!!!");
                        this.printDivisionLine();
                    }
                    else {    // if player is not natural BlackJack
                        // ask player to do actions
                        int doubleup = 0;
                        while (true) {
                            int action;
                            if(doubleup == 0){
                                action = this.askPlayerToDoActions(i);
                                doubleup = 1;
                            }
                            else
                                action = this.askPlayerToDoActions1(i);
                            // do action
                            boolean changeNextHand = this.rule.doAction(action,i);
                            if (changeNextHand) {   // if need to change to the next hand
                                int index = this.rule.getPlayer(i).getIndexOfCurrentHand();
                                if (index == this.rule.getPlayer(i).getHands().size() - 1) { // if this is the last hand
                                    this.printDivisionLine();

                                    // not go to next hand, jump out of the loop
                                    break;
                                }
                                this.printDivisionLine();
                                // if this is not the last hand (player choose to split), then go to next hand
                                System.out.printf("Current hand changes from hand %d to hand %d\n", index + 1, index + 2);
                                this.rule.getPlayer(i).nextHand();
                            }
                            this.printDivisionLine();
                        }
                    }
                    boolean isPlayerBust = this.rule.playerBust(i);
                    if (isPlayerBust) { // if player bust, player loses immediately
                        this.rule.getDealer().setBank(this.rule.getDealer().getBank() + this.rule.getPlayer(i).getHands().size() * this.rule.getPlayer(i).getCurrentHand().getBet());
                        // if dealer is computer, do nothing, because bet has been deduct from the player's capital
                    }
                }

            }
                 // if player is not bust

                // judge whether dealer is bust
            boolean isDealerBust = this.rule.addDealerToSeventeen();
            for(int i=0;i<playerNumber-1;i++) {
                if (this.rule.getPlayer(i).getGame()) {
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
                if(this.rule.getPlayer(i).getGame()){
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
        }

        if (this.currentState == GameState.PLAYER_QUIT) {    // if every player doesn't want to continue to play
            System.out.println("All player quits the game! Game over!");
        }
    }


    public boolean isPlayerWillingToSplit(int num) {
        System.out.print("Are you willing to split your hand? (Y/N) ");
        boolean isValidateInput = false;
        boolean playerWillingToSplit = false;
        while (!isValidateInput) {
            String input = this.scanInput.scanString();
            if (input.toUpperCase().equals("Y")) {
                if (this.rule.getPlayer(num).getBank() < this.rule.getPlayer(num).getCurrentHand().getBet()) { // if don't have enough money to split
                    System.out.println("Player " + this.rule.getPlayer(num).getName() + ", you don't have enough capital to split!");
                    playerWillingToSplit = false;
                } else {
                    playerWillingToSplit = true;
                }
                isValidateInput = true;
            } else if (input.toUpperCase().equals("N")) {
                isValidateInput = true;
            } else {
                System.out.print("Please input Y or N: ");
            }
        }
        return playerWillingToSplit;
    }

    public int askPlayerToDoActions(int num) {
        String name = this.rule.getPlayer(num).getName();
        int index = this.rule.getPlayer(num).getIndexOfCurrentHand();

        System.out.println("Player " + name + "'s hand " + (index + 1) + ":");
        this.rule.getPlayer(num).getCurrentHand().printHand();
        System.out.println("Please take an action from the following options, " + name + "'s hand " + (index + 1) + " (Enter the number): ");
        System.out.println("1.Hit 2.Stand 3.DoubleUp");

        int action = 0;
        boolean isValidateInput = false;
        while (!isValidateInput) {
            action = this.scanInput.scanInt();
            this.printDivisionLine();
            if (action >= 1 && action <= 3) {
                isValidateInput = true;
            }
            else {
                System.out.println("You can only choose from these three actions! Please input again: ");
                System.out.println("1.Hit 2.Stand 3.DoubleUp");
            }
        }
        return action;
    }

    public int askPlayerToDoActions1(int num) {
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

}
