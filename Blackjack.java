package aldz_Blackjack;

import java.util.*;


/**
 * The main class for the <i>Blackjack</i> application.
 */
public class Blackjack
{
    Player[] players;

    Deck deck1;

    AI love;

    Stack<Card> discardPile;

    private static final int LIMIT = 26;

    private static final int NUMBER_OF_PLAYERS = 4;


    public Blackjack()
    {
        love = new AI();
        discardPile = new Stack<Card>();
        players = new Player[NUMBER_OF_PLAYERS];
        for ( int i = 0; i < players.length; i++ )
        {
            players[i] = new Player();
        }

        players[0].setName( "Alice" );
        players[1].setName( "Bob" );
        players[2].setName( "Charile" );
        players[3].setName( "Dave" );

        deck1 = new Deck( false );
        // System.out.println(deck1.toString());
        deck1.shuffle();
        // System.out.println("test");
        // System.out.println(deck1.toString());
    }


    public static void main( String[] args )
    {
        Blackjack aldz = new Blackjack();

        aldz.doTheThing();

    }


    public void doTheThing()
    {
        int dealer = 0;

        for ( int i = 1; i <= 4; i++ )
        {
            System.out.println( "--ROUND " + i + "--" );
            doRound( dealer );
            System.out.println( "--END OF ROUND " + i + "-- CARDS LEFT: "
                + deck1.size + "\n\n" );
        }
    }


    public void doRound( int dealer )
    {
        dealer %= NUMBER_OF_PLAYERS;

        deal();

        // System.out.println( players[0].stringChips() );
        int who = dealer; // whose turn is it?

        System.out.println( players[who].getName() + " the dealer's turn:" );
        love.dealerStrategy( players[who], deck1 );
        System.out.println( players[who].printHand() );
        System.out.println( "value " + players[who].getHandValue() );
        System.out.println();
        //
        // cleanHands();
        //
        // System.out.println( deck1.size );
        // System.out.println( resetDeck() );

        who++;
        doWikipedia( who, dealer );

        who++;
        doWikipedia( who, dealer );

        who++;
        doWikipedia( who, dealer );

        cleanHands();

        // System.out.println( deck1.toString() );

        // System.out.println( printStats( testStats( 100 ) ) );
    }


    void doWikipedia( int whoseTurn, int dealer )
    {
        whoseTurn %= NUMBER_OF_PLAYERS;
        dealer %= NUMBER_OF_PLAYERS;
        System.out.println( players[whoseTurn].getName() + "'s turn:" );
        love.wikipediaStrategy( players[whoseTurn], players[dealer], deck1 );
        System.out.println( players[whoseTurn].printHand() );
        System.out.println( "value " + players[whoseTurn].getHandValue() );
        System.out.println();
    }


    void deal()
    {
        cleanHands();
        if ( deck1.getSize() < LIMIT )
        {
            System.out.println( "\nRESET THE DECK\n" );
            resetDeck();
        }
        for ( int i = 0; i < players.length; i++ )
        {
            love.drawFirstTwoCards( players[i], deck1 );
        }
    }


    int[] testStats( int howManyTimes )
    {
        int[] stats = new int[30];

        for ( int i = 0; i < howManyTimes; i++ )
        {
            deal();

            love.dealerStrategy( players[0], deck1 );
            stats[players[0].getHandValue()]++;
        }

        return stats;
    }


    String printStats( int[] stats )
    {
        String s = "";
        for ( int i = 0; i < stats.length; i++ )
        {
            s += i + "\t";
            for ( int j = 0; j < stats[i]; j++ )
            {
                s += "X";
            }
            s += "\n";
        }
        return s;
    }


    public void cleanHands()
    {
        for ( Player p : players )
        {
            List<Card> hand = p.getHand();
            p.resetHand();
            for ( Card c : hand )
            {
                discardPile.push( c );
            }
        }
    }


    public int resetDeck()
    {
        while ( !discardPile.isEmpty() )
        {
            deck1.collect( discardPile.pop() );
        }
        deck1.shuffle();

        return deck1.getSize();
    }
}
