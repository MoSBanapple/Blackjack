package aldz_Blackjack;

/**
 * The main class for the <i>Blackjack</i> application.
 */
public class Blackjack
{
    Player alice;
    Player bob;
    Deck deck1;
    
    public Blackjack()
    {
        alice = new Player();
        bob = new Player();
        
        
        deck1 = new Deck(false);
        System.out.println(deck1.toString());
        deck1.shuffle();
        System.out.println("test");
        System.out.println(deck1.toString());
    }
    
    public static void main( String[] args )
    {       
        Blackjack aldz = new Blackjack();
        
        aldz.doRound();
        
    }
    
    void doRound()
    {
        System.out.println(alice.stringChips());
        alice.addCard( deck1.draw() );
        System.out.println(alice.printHand());
        System.out.println();
        System.out.println(deck1.toString());
    }
}
