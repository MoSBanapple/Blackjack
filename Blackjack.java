package aldz_Blackjack;

/**
 * The main class for the <i>Blackjack</i> application.
 */
public class Blackjack
{
    public static void main( String[] args )
    {
        Deck deck1 = new Deck(false);
        System.out.println(deck1.toString());
        deck1.shuffle();
        System.out.println("test");
        System.out.println(deck1.toString());
    }
}
