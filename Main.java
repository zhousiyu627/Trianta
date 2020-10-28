/* Siyu Zhou
 * 	Main : The start point of the project, can choose which game to play.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("You can choose Blackjack(1) or Trianta Ena(2) to play.(3 to quit)");
        int choose = ScanInput.scanInt(1,3);
        while(choose != 3)
        if(choose == 1){
            new BlackJack();
        }
        else{
            new Trianta();
        }
    }
}
