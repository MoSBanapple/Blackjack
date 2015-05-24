package aldz_Blackjack;

import java.util.*;


/**
 * The main class for the <i>Blackjack</i> application.
 *
 * @author Andrew Lee and Derek Zhang
 * @version May 24, 2015
 * @author Period: 6
 * @author Assignment: ALDZ_Blackjack
 *
 * @author Sources: TODO
 */
public class Blackjack
{
    private Player[] players;

    private int[] skill;

    private Deck deck1;

    private AI love;

    private BettingAI betAI;

    private Stack<Card> discardPile;

    private static final int LIMIT = 26;

    private static final int NUMBER_OF_PLAYERS = 4;

    private Scanner scan;

    private int humanIndex;

    private static final int MIN_BET = 15;

    private static final int MAX_BET = 500;

    private static final String[] NAME = { "Alice", "Bob", "Capulet", "Dave",
        "Eve", "Fredrika", "George", "Hoover", "Ivan", "Jose", "Katherine",
        "Lisa", "Montague", "Nancy", "Obama", "Putin", "Que", "Rosas", "Sally",
        "Ted", "Utena", "Vern", "Walther", "XZAVIER", "Yetal",
        "Znow i know my abcs" };


    public Blackjack()
    {
        humanIndex = 3;
        // humanIndex = (int)( Math.random() * NUMBER_OF_PLAYERS );
        // System.out.println( "You are player number " + ( humanIndex + 1 ) +
        // "!" );

        scan = new Scanner( System.in );

        love = new AI();
        discardPile = new Stack<Card>();
        players = new Player[NUMBER_OF_PLAYERS];
        for ( int i = 0; i < NUMBER_OF_PLAYERS; i++ )
        {
            players[i] = new Player();
        }

        System.out.println( "What is your name?" );
        String name = scan.nextLine();
        while ( name.length() == 0 )
        {
            System.out.println( "Please enter your name." );
            name = scan.nextLine();
        }

        players[humanIndex].setName( name );

        for ( int i = 0; i < NUMBER_OF_PLAYERS; i++ )
        {
            if ( i != humanIndex )
            {
                String tempName = NAME[(int)( Math.random() * NAME.length )];
                while ( tempName.compareToIgnoreCase( name ) == 0 )
                {
                    tempName = NAME[(int)( Math.random() * NAME.length )];
                }
                players[i].setName( tempName );
            }
        }

        skill = new int[NUMBER_OF_PLAYERS];
        updateSkill();

        betAI = new BettingAI();

        deck1 = new Deck( false );
        // System.out.println(deck1.toString());
        deck1.shuffle();
        // System.out.println("test");
        // System.out.println(deck1.toString());

        Card c = deck1.draw();
        System.out.println( "You sneak a card into your sleeve! It is the "
            + c.toString() + "." );
        players[humanIndex].addPocket( c );
    }


    public static void main( String[] args )
    {
        Blackjack aldz = new Blackjack();

        aldz.playBlackjack();

    }


    public void playBlackjack()
    {
        int i = 0;
        int dealer = 0;

        while ( true )
        {
            i++;
            System.out.println( "--ROUND " + i + "--" );
            doRound( dealer );
            System.out.println( "--END OF ROUND " + i + "-- CARDS LEFT: "
                + deck1.getSize() + "\n\n" );

            for ( Player p : players )
            {
                if ( p.getChips() <= 0 )
                {
                    Arrays.sort( players );
                    int index = NUMBER_OF_PLAYERS;

                    System.out.println( "GAME OVER" );
                    for ( Player play : players )
                    {
                        play.addChips( play.getBet() );
                        play.resetBet();
                        System.out.println( index + ": " + play.getName()
                            + " has " + play.getChips() + " chips." );
                        index--;
                    }
                    return;
                }
            }

            if ( i % 4 == 0 )
                dealer++;

            System.out.println( "\"q\" to quit." );
            String response = scan.nextLine().toUpperCase();
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
        // System.out.println( Arrays.toString( skill ) );
        dealer %= NUMBER_OF_PLAYERS;

        System.out.println( deal() );

        int currentBet = 0;
        int j = 0;
        for ( int i = dealer; j < NUMBER_OF_PLAYERS; i = ( i + 1 )
            % NUMBER_OF_PLAYERS )
        {
            currentBet = makeBet( i, dealer );
            players[i].addBet( currentBet );
            System.out.println( describePerson( players[i] ) );

            j++;
        }

        System.out.println();

        j = 0;
        for ( int i = dealer; j < NUMBER_OF_PLAYERS; i = ( i + 1 )
            % NUMBER_OF_PLAYERS )
        {
            // Print befuddled hand
            String str = befuddledHand( players[i] );
            if ( i == dealer )
                str += " and is also the dealer.";
            System.out.println( players[i].getName() + " has a hand of " + str );
            j++;
        }

        System.out.println();
        // Make moves
        j = 0;
        for ( int i = ( dealer + 1 ) % NUMBER_OF_PLAYERS; j < NUMBER_OF_PLAYERS; i = ( i + 1 )
            % NUMBER_OF_PLAYERS )
        {
            System.out.println( doMove( i, dealer ) );
            j++;
        }

        // check victories, shuffle bets around

        for ( int i = 0; i < NUMBER_OF_PLAYERS; i++ )
        {
            consolidateBets( i, dealer );
        }

        cleanHands();
    }


    String befuddledHand( Player p )
    {
        List<Card> hand = p.getHand();
        String s = "";

        int index = 0;
        for ( Card c : hand )
        {
            if ( index == 0 )
            {
                s += "UNKNOWN";
            }
            else
            {
                s += c.toString();
            }
            if ( index < hand.size() - 1 )
            {
                s += ", ";
            }
            index++;
        }
        return s;
    }


    int consolidateBets( int who, int dealer )
    {
        who %= NUMBER_OF_PLAYERS;
        if ( who == dealer )
            return 0;
        int result = result( players[dealer], players[who] );

        if ( result == -3 )
        {
            int bet = players[who].getBet();
            int toReturn = bet / 2;
            players[who].addChips( toReturn );
            players[dealer].addChips( bet - toReturn );
            players[who].resetBet();
        }
        else if ( result == -2 )
        {
            players[who].addBet( players[who].getBet() / 2 );
            players[who].transferBet( players[dealer] );
        }
        else if ( result == -1 )
        {
            players[who].transferBet( players[dealer] );
        }
        else if ( result == 1 )
        {
            players[dealer].addBet( players[who].getBet() );
            players[dealer].transferBet( players[who] );
            players[who].transferBet( players[who] );
        }
        else if ( result == 2 )
        {
            players[dealer].addBet( players[who].getBet() * 3 / 2 );
            players[dealer].transferBet( players[who] );
            players[who].transferBet( players[who] );
        }
        else
        {
            // if result == 0
            players[who].transferBet( players[who] );
        }
        return result;
    }


    String resultToString( int result )
    {
        // -3: player surrender
        //
        // -2: dealer blackjack
        //
        // -1: dealer wins
        //
        // 0: tie
        //
        // 1: player wins
        //
        // 2: player blackjack

        if ( result == -3 )
        {
            return "player surrender";
        }
        else if ( result == -2 )
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
        if ( whoseTurn == humanIndex )
        {
            System.out.println( "Make your bet! You have "
                + players[humanIndex].getChips() + " chips." );
            String s = scan.nextLine();
            int val;
            while ( true )
            {
                try
                {
                    val = Integer.parseInt( s );
                    if ( val < Math.min( MIN_BET,
                        players[humanIndex].getChips() ) )
                    {
                        System.out.println( "Please bet a larger amount."
                            + "\nThe minimum bet is the minimum of " + MIN_BET
                            + " and how many chips you currently have." );
                        s = scan.nextLine();
                    }
                    else if ( val > MAX_BET )
                    {
                        System.out.println( "Please bet a lower amount"
                            + "\nThe maximum bet is " + MAX_BET );
                        s = scan.nextLine();
                    }
                    else
                    {
                        break;
                    }
                }
                catch ( NumberFormatException ex )
                {
                    System.out.println( "Invalid input. Please enter an integer"
                        + " value for the bet." );
                    s = scan.nextLine();
                }
            }
            return val;
        }
        int cash = players[whoseTurn].getChips();
        int bet = Math.max( MIN_BET,
            Math.min( betAI.calculateBet( cash,
                skill[whoseTurn],
                deck1.getSize() ),
                MAX_BET ) );
        if ( cash < bet )
        {
            bet = cash;
        }
        return bet;
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

        if ( whoseTurn == humanIndex )
        {
            humanMove( dealer );
        }
        else
        {
            chooseMove( whoseTurn, dealer );
        }
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


    void humanDealer()
    {
        System.out.println( "It's your turn to be the dealer!" );
        System.out.println( "Your hand is:\n" + players[humanIndex].printHand()
            + "\nwith a value of " + players[humanIndex].getHandValue() );
        cheat();
        System.out.println( "As dealer, you have to hit until 17." );
        love.dealerStrategy( players[humanIndex], deck1 );
    }


    void cheat()
    {
        System.out.println( "The card in your sleeve is "
            + players[humanIndex].getPocket() );
        System.out.println( "Enter \"cheat\" to cheat. "
            + "Enter anything else to continue." );
        String in = scan.nextLine();
        if ( !in.equalsIgnoreCase( "cheat" ) )
        {
            return;
        }

        // switch card in sleeve to facedown card.
        players[humanIndex].swapCard();
        System.out.println( "Your hand is now:\n"
            + players[humanIndex].printHand() + "\nwith a value of "
            + players[humanIndex].getHandValue() );
        System.out.println( "and the card in your sleeve is "
            + players[humanIndex].getPocket() );
    }


    void humanMove( int dealer )
    {
        if ( dealer == humanIndex )
        {
            humanDealer();
            return;
        }

        System.out.println( "It is now your turn. Your hand is:\n"
            + players[humanIndex].printHand() + "\nwith a value of "
            + players[humanIndex].getHandValue() );
        cheat();
        System.out.println( "Make a decision! Your hand is:\n"
            + players[humanIndex].printHand() + "\nwith a value of "
            + players[humanIndex].getHandValue() );
        System.out.println( "\"f\" to surrender, \"h\" to hit, "
            + "\n\"s\" to stand, \"d\" to double down" );
        char c = ' ';
        // f forfeit
        // h hit
        // s stand
        // d double down
        String in = scan.nextLine();
        while ( in.length() == 0 )
        {
            in = scan.nextLine();
        }
        c = in.charAt( 0 );
        while ( c != 'f' && c != 'h' && c != 's' && c != 'd' )
        {
            System.out.println( "Please enter a valid command." );
            in = scan.nextLine();
            while ( in.length() == 0 )
            {
                in = scan.nextLine();
            }
            c = in.charAt( 0 );
        }

        while ( true )
        {
            if ( c == 'f' )
            {
                System.out.println( "You surrender." );
                players[humanIndex].changeSurrendered( true );
                return;
            }
            else if ( c == 'h' )
            {
                System.out.println( "You decide to hit." );
                Card card = deck1.draw();
                System.out.println( "You get a " + card.toString() + "!" );
                players[humanIndex].addCard( card );
                System.out.println( "Your current hand is "
                    + players[humanIndex].printHand() );
                if ( players[humanIndex].getHandValue() > 21 )
                {
                    System.out.println( "You busted!" );
                    return;
                }
            }
            else if ( c == 's' )
            {
                System.out.println( "You decide to stand. Your turn ends." );
                return;
            }
            else
            {
                // if(c == 'd' )
                System.out.println( "You decide to double down." );

                if ( players[humanIndex].doubleDown() )
                {
                    Card card = deck1.draw();
                    System.out.println( "You get a " + card.toString() + "!" );
                    players[humanIndex].addCard( card );
                    System.out.println( "Your current hand is "
                        + players[humanIndex].printHand() );
                    if ( players[humanIndex].getHandValue() > 21 )
                    {
                        System.out.println( "You busted!" );
                    }
                    return;
                }
                else
                {
                    System.out.println( "You don't have enough chips!" );
                    System.out.println( "You hit instead." );
                    Card card = deck1.draw();
                    System.out.println( "You get a " + card.toString() + "!" );
                    players[humanIndex].addCard( card );
                    System.out.println( "Your current hand is "
                        + players[humanIndex].printHand() );
                    if ( players[humanIndex].getHandValue() > 21 )
                    {
                        System.out.println( "You busted!" );
                        return;
                    }
                }
            }

            System.out.println( "What is your next move?" );
            in = scan.nextLine();
            while ( in.length() == 0 )
            {
                in = scan.nextLine();
            }
            c = in.charAt( 0 );

            while ( c != 'f' && c != 'h' && c != 's' && c != 'd' )
            {
                System.out.println( "Please enter a valid command." );
                System.out.println( "\"f\" to surrender, \"h\" to hit, "
                    + "\n\"s\" to stand, \"d\" to double down" );
                c = scan.nextLine().charAt( 0 );
            }
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
     * -3: player surrender
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

        if ( p.isSurrendered() )
        {
            return -3;
        }
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
        if ( pHand > 21 )
        {
            return -1;
        }
        if ( dHand > 21 )
        {
            return 1;
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
}
