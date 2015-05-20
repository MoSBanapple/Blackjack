package aldz_Blackjack;

import java.util.*;


/**
 * The main class for the <i>Blackjack</i> application.
 */
public class Blackjack
{
    Player[] players;

    int[] skill;

    Deck deck1;

    AI love;

    BettingAI betAI;

    Stack<Card> discardPile;

    private static final int LIMIT = 26;

    private static final int NUMBER_OF_PLAYERS = 4;

    Scanner kbd;


    public Blackjack()
    {
        kbd = new Scanner( System.in );

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

        skill = new int[NUMBER_OF_PLAYERS];
        updateSkill();

        betAI = new BettingAI();

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
        int i = 0;
        int dealer = 0;

        while ( true )
        {
            i++;
            System.out.println( "--ROUND " + i + "--" );
            doRound( dealer );
            System.out.println( "--END OF ROUND " + i + "-- CARDS LEFT: "
                + deck1.size + "\n\n" );

            for ( Player p : players )
            {
                if ( p.getChips() <= 0 )
                {
                    System.out.println( "GAME OVER" );
                    return;
                }
            }

            if ( i % 4 == 0 )
                dealer++;

            System.out.println( "\"q\" to quit." );
            String response = kbd.nextLine().toUpperCase();
            if ( response.equals( "Q" ) )
            {
                System.out.println( "Goodbye!" );
                return;
            }
        }
    }


    public void doRound( int dealer )
    {
        updateSkill();

        dealer %= NUMBER_OF_PLAYERS;

        int who = dealer; // whose turn is it?

        System.out.println( deal() );

        // Dealer
        System.out.println( describePerson( players[who] ) );

        int currentBet = 0;
        // determine bets
        who = ( who + 1 ) % NUMBER_OF_PLAYERS;
        currentBet = makeBet( who, dealer );
        // System.out.println( stringBet( who, dealer, currentBet ) );
        players[who].addBet( currentBet );
        System.out.println( describePerson( players[who] ) );

        who = ( who + 1 ) % NUMBER_OF_PLAYERS;
        currentBet = makeBet( who, dealer );
        // System.out.println( stringBet( who, dealer, currentBet ) );
        players[who].addBet( currentBet );
        System.out.println( describePerson( players[who] ) );

        who = ( who + 1 ) % NUMBER_OF_PLAYERS;
        currentBet = makeBet( who, dealer );
        // System.out.println( stringBet( who, dealer, currentBet ) );
        players[who].addBet( currentBet );
        System.out.println( describePerson( players[who] ) );

        System.out.println();

        // Make moves
        who = dealer; // whose turn is it?

        who++;
        System.out.println( doMove( who, dealer ) );

        who++;
        System.out.println( doMove( who, dealer ) );

        who++;
        System.out.println( doMove( who, dealer ) );

        System.out.println( doMove( dealer, dealer ) );

        // check victories, shuffle bets around

        who = dealer;

        for ( int i = 0; i < NUMBER_OF_PLAYERS - 1; i++ )
        {
            consolidateBets( who, dealer );
        }

        cleanHands();

        // System.out.println( deck1.toString() );

        // System.out.println( printStats( testStats( 250 ) ) );
    }


    int consolidateBets( int who, int dealer )
    {
        who = ( who + 1 ) % NUMBER_OF_PLAYERS;
        int result = result( players[dealer], players[who] );

        if ( result == -2 )
        {
            players[who].addBet( players[who].getBet() / 2 );
            players[dealer].addChips( players[who].getBet() );
            players[who].resetBet();
        }
        else if ( result == -1 )
        {
            players[dealer].addChips( players[who].getBet() );
            players[who].resetBet();
        }
        else if ( result == 1 )
        {
            players[dealer].addBet( players[who].getBet() );
            players[who].addChips( 2 * players[who].getBet() );
            players[who].resetBet();
            players[dealer].resetBet();
        }
        else if ( result == 2 )
        {
            players[who].addBet( players[who].getBet() / 2 );
            players[dealer].addBet( players[who].getBet() );
            players[who].addChips( 2 * players[who].getBet() );
            players[who].resetBet();
            players[dealer].resetBet();
        }
        // if == 0, don't do anything
        return result;
    }


    String resultToString( int result )
    {
        // -2: dealer blackjack
        //
        // -1: dealer wins
        //
        // 0: tie
        //
        // 1: player wins
        //
        // 2: player blackjack

        if ( result <= -2 )
        {
            return "dealer blackjack";
        }
        else if ( result == -1 )
        {
            return "dealer win";
        }
        else if ( result == 0 )
        {
            return "push";
        }
        else if ( result == 1 )
        {
            return "player win";
        }
        else
        {
            return "player blackjack";
        }
    }


    String describePerson( Player p )
    {
        String s = p.getName();
        s += "\nhas " + p.getChips() + " chips";
        s += "\ncurrently betting " + p.getBet() + " chips";
        // s += "\nHand: " + p.printHand();
        // s += "\nWith a value of " + p.getHandValue();
        s += "\n";
        return s;
    }


    String stringBet( int whoseTurn, int dealer, int bet )
    {
        whoseTurn %= NUMBER_OF_PLAYERS;
        dealer %= NUMBER_OF_PLAYERS;

        if ( whoseTurn == dealer )
        {
            return "The dealer does not bet!";
        }

        return makeIntroduction( whoseTurn, dealer ) + " bet " + bet;
    }


    int makeBet( int whoseTurn, int dealer )
    {
        whoseTurn %= NUMBER_OF_PLAYERS;
        dealer %= NUMBER_OF_PLAYERS;

        if ( whoseTurn == dealer )
        {
            return 0;
        }
        int cash = players[whoseTurn].sumChips();
        return betAI.calculateBet( cash, skill[whoseTurn], deck1.getSize() );
    }


    String makeIntroduction( int whoseTurn, int dealer )
    {
        String s = players[whoseTurn].getName();

        if ( whoseTurn == dealer )
        {
            s += " the dealer";
        }
        s += "'s turn:";
        return s;
    }


    String doMove( int whoseTurn, int dealer )
    {
        whoseTurn %= NUMBER_OF_PLAYERS;
        dealer %= NUMBER_OF_PLAYERS;

        String s = makeIntroduction( whoseTurn, dealer );

        chooseMove( whoseTurn, dealer );

        s += "\n" + players[whoseTurn].printHand();
        s += "\nValue: " + players[whoseTurn].getHandValue() + "\n";
        return s;
    }


    void chooseMove( int whoseTurn, int dealer )
    {
        if ( dealer == whoseTurn )
        {
            love.dealerStrategy( players[whoseTurn], deck1 );
        }
        else
        {
            love.wikipediaStrategy( players[whoseTurn], players[dealer], deck1 );
        }
    }


    public void updateSkill()
    {
        for ( int i = 0; i < NUMBER_OF_PLAYERS; i++ )
        {
            int change = 0;
            double d = Math.random();
            if ( ( skill[i] == 2 && d > 0.5 ) || ( d < 0.1 && skill[i] != -1 ) )
            {
                change = -1;
            }
            else if ( d > 0.7 && skill[i] != 2 )
            {
                change = 1;
            }
            skill[i] += change;
        }
    }


    /**
     * 
     * This calculates who wins a blackjack hand, between a player and a dealer.
     * 
     * -2: dealer blackjack
     * 
     * -1: dealer wins
     * 
     * 0: tie
     * 
     * 1: player wins
     * 
     * 2: player blackjack
     * 
     * @param dealer
     *            user input Player who is the dealer of this hand
     * @param p
     *            user input Player to compare with the dealer
     * @return the int number that corresponds to a result
     */
    int result( Player dealer, Player p )
    {
        int dHand = dealer.getHandValue();
        int dCards = dealer.getHand().size();
        int pHand = p.getHandValue();
        int pCards = p.getHand().size();

        if ( pCards == 2 && pHand == 21 )
        {
            if ( dCards == 2 && dHand == 21 )
                return 0;
            return 2;
        }
        if ( dCards == 2 && dHand == 21 )
        {
            return -2;
        }
        if ( dHand > 21 )
        {
            return 1;
        }
        if ( pHand > 21 )
        {
            return -1;
        }
        if ( pHand - dHand > 0 )
        {
            return 1;
        }
        if ( pHand - dHand < 0 )
        {
            return -1;
        }

        return 0;
    }


    String deal()
    {
        String s = "";
        cleanHands();
        if ( deck1.getSize() < LIMIT )
        {
            s = "\nReshuffling\n";
            resetDeck();
        }
        for ( int i = 0; i < players.length; i++ )
        {
            players[i].addCard( deck1.draw() );
        }
        for ( int i = 0; i < players.length; i++ )
        {
            players[i].addCard( deck1.draw() );
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
                betAI.update( c );
                discardPile.push( c );
            }
        }
    }


    public int resetDeck()
    {
        betAI.reset();
        while ( !discardPile.isEmpty() )
        {
            deck1.collect( discardPile.pop() );
        }
        deck1.shuffle();

        return deck1.getSize();
    }

    // /**
    // * The below is for testing purposes only
    // */
    //
    // private int[] testStats( int howManyTimes )
    // {
    // int[] stats = new int[33];
    // // stats[30] = player wins
    // // stats[31] = draws
    // // stats[32] = dealer wins
    //
    // for ( int i = 0; i < howManyTimes; i++ )
    // {
    // deal();
    //
    // // love.dealerStrategy( players[1], deck1 );
    // // love.guessingStrategy( players[1], players[0], deck1 );
    // // love.randomStrategy( players[1], deck1 );
    // love.peekingStrategy( players[1], players[0], deck1 );
    // // love.wikipediaStrategy( players[1], players[0], deck1 );
    // love.dealerStrategy( players[0], deck1 );
    // stats[players[1].getHandValue()]++;
    //
    // int result = result( players[0], players[1] );
    // if ( result > 0 )
    // stats[30]++;
    // else if ( result == 0 )
    // stats[31]++;
    // else
    // stats[32]++;
    // }
    //
    // return stats;
    // }
    //
    //
    // private String printStats( int[] stats )
    // {
    // String s = "";
    // for ( int i = 0; i < 30; i++ )
    // {
    // s += i + "\t";
    // for ( int j = 0; j < stats[i]; j++ )
    // {
    // s += "X";
    // }
    // s += "\n";
    // }
    //
    // s += "Player wins: " + stats[30] + "\n";
    // s += "Draws:       " + stats[31] + "\n";
    // s += "Dealer wins: " + stats[32] + "\n";
    // s += "Win rate:    " + ( stats[30] + 0.1 - 0.1 )
    // / ( stats[30] + stats[31] + stats[32] ) + "\n";
    // return s;
    // }

}
