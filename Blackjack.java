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
    private static final String code = "AYIEKDSANALDSIE76666300";
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

    private int previousChips;

    private static final int MIN_BET = 15;

    private static final int MAX_BET = 500;

    private static final int MAX_ROUNDS = 16;

    private static final double CHEAT_FAIL_CHANCE = 0.25;

    private static final String[] NAME = { "Alice", "Bob", "Capulet", "Dave",
        "Eve", "Fredrika", "George", "Hoover", "Ivan", "Jose", "Katherine",
        "Lisa", "Montague", "Nancy", "Obama", "Putin", "Que", "Rosas", "Sally",
        "Ted", "Utena", "Vern", "Walther", "XZAVIER", "Yetal",
        "Znow i know my abcs" };
    
    private GameWindow window;
    
    private StatusWindow cardWindow;
    
    private String input;


    public Blackjack()
    {
        input = code;

        humanIndex = 3;
        // humanIndex = (int)( Math.random() * NUMBER_OF_PLAYERS );
        // window.message( "You are player number " + ( humanIndex + 1 ) +
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

        previousChips = players[humanIndex].getChips();
        window = new GameWindow(this);
        cardWindow = new StatusWindow(this);

        Card c = deck1.draw();
        window.message( "You sneak a card into your sleeve! It is the "
            + c.toString() + "." );
        players[humanIndex].addPocket( c );
        window.setPocket( c.toString() );
        cardWindow.update();
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

        while ( i < MAX_ROUNDS )
        {
            i++;
            window.message( "--ROUND " + i + "--" );
            cardWindow.update();
            doRound( dealer );
            window.message( "--END OF ROUND " + i + "-- CARDS LEFT: "
                + deck1.getSize() + "\n\n" );
            cardWindow.update();

            for ( Player p : players )
            {
                if ( p.getChips() <= 0 )
                {
                    Arrays.sort( players );
                    int index = NUMBER_OF_PLAYERS;

                    window.message( "GAME OVER" );
                    for ( Player play : players )
                    {
                        cardWindow.update();
                        play.addChips( play.getBet() );
                        play.resetBet();
                        window.message( index + ": " + play.getName()
                            + " has " + play.getChips() + " chips." );
                        index--;
                    }
                    return;
                }
            }

            if ( i % 4 == 0 )
                dealer++;

            cardWindow.update();
//            window.message( "\"q\" to quit." );
//            cardWindow.update();
//            String response = scan.nextLine().toUpperCase();
//            if ( response.equals( "Q" ) )
//            {
//                window.message( "Goodbye!" );
//                return;
//            }
        }

        window.message( "Time is up!" );
        cardWindow.update();
        Arrays.sort( players );
        int index = NUMBER_OF_PLAYERS;
        for ( Player play : players )
        {
            play.addChips( play.getBet() );
            play.resetBet();
            window.message( index + ": " + play.getName() + " has "
                + play.getChips() + " chips." );
            index--;
            cardWindow.update();
        }
    }


    public void doRound( int dealer )
    {
        updateSkill();
        // window.message( Arrays.toString( skill ) );
        dealer %= NUMBER_OF_PLAYERS;

        window.message( deal() );

        int currentBet = 0;
        int j = 0;
        for ( int i = dealer; j < NUMBER_OF_PLAYERS; i = ( i + 1 )
            % NUMBER_OF_PLAYERS )
        {
            currentBet = makeBet( i, dealer );
            players[i].addBet( currentBet );
            window.message( describePerson( players[i] ) );

            j++;
        }
        cardWindow.update();

        window.message("");

        j = 0;
        for ( int i = dealer; j < NUMBER_OF_PLAYERS; i = ( i + 1 )
            % NUMBER_OF_PLAYERS )
        {
            // Print befuddled hand
            String str = befuddledHand( players[i] );
            if ( i == dealer )
                str += " and is also the dealer.";
            window.message( players[i].getName() + " has a hand of " + str );
            j++;
        }
        cardWindow.update();

        window.message("");
        // Make moves
        j = 0;
        for ( int i = ( dealer + 1 ) % NUMBER_OF_PLAYERS; j < NUMBER_OF_PLAYERS; i = ( i + 1 )
            % NUMBER_OF_PLAYERS )
        {
            window.message( doMove( i, dealer ) );
            j++;
        }
        cardWindow.update();

        // check victories, shuffle bets around

        for ( int i = 0; i < NUMBER_OF_PLAYERS; i++ )
        {
            consolidateBets( i, dealer );
        }
        cardWindow.update();

        cleanHands();
        cardWindow.update();

        window.message( "The amount of chips you have changed by "
            + ( players[humanIndex].getChips() - previousChips ) + "." );
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
        cardWindow.update();
        return s;
    }


    int consolidateBets( int who, int dealer )
    {
        who %= NUMBER_OF_PLAYERS;
        if ( who == dealer )
            return 0;
        int result = result( players[dealer], players[who] );
        cardWindow.update();

        if ( result == -4 || result == 3 )
        {
            // your bet to everyone
            int bet = players[who].getBet();
            players[who].addBet( bet * 2 );
            players[who].resetBet();
            for ( int i = 0; i < NUMBER_OF_PLAYERS; i++ )
            {
                if ( i != who )
                {
                    players[i].addChips( bet );
                }
            }
        }
        else if ( result == -3 )
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
        cardWindow.update();
        return result;
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
        cardWindow.update();

        return makeIntroduction( whoseTurn, dealer ) + " bet " + bet;
    }


    int makeBet( int whoseTurn, int dealer )
    {
        whoseTurn %= NUMBER_OF_PLAYERS;
        dealer %= NUMBER_OF_PLAYERS;
        cardWindow.update();
        if ( whoseTurn == dealer )
        {
            return 0;
        }
        if ( whoseTurn == humanIndex )
        {
            window.message( "Make your bet! You have "
                + players[humanIndex].getChips() + " chips." );
            while (input.equals( code ))
            {
                
            }
            String s = input;
            input = code;
            int val;
            while ( true )
            {
                try
                {
                    val = Integer.parseInt( s );
                    if ( val < Math.min( MIN_BET,
                        players[humanIndex].getChips() ) )
                    {
                        window.message( "Please bet a larger amount."
                            + "\nThe minimum bet is the minimum of " + MIN_BET
                            + " and how many chips you currently have." );
                        cardWindow.update();
                        while (input.equals( code ))
                        {
                            
                        }
                        s = input;
                        input = code;
                    }
                    else if ( val > MAX_BET )
                    {
                        window.message( "Please bet a lower amount"
                            + "\nThe maximum bet is " + MAX_BET );
                        cardWindow.update();
                        while (input.equals( "standby" ))
                        {
                            
                        }
                        s = input;
                        input = code;
                    }
                    else
                    {
                        break;
                    }
                }
                catch ( NumberFormatException ex )
                {
                    window.message( "Invalid input. Please enter an integer"
                        + " value for the bet." );
                    cardWindow.update();
                    while (input.equals( code ))
                    {
                        
                    }
                    s = input;
                    input = code;
                }
            }
            return val;
        }
        cardWindow.update();
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
        cardWindow.update();
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
        cardWindow.update();
        return s;
    }


    String doMove( int whoseTurn, int dealer )
    {
        whoseTurn %= NUMBER_OF_PLAYERS;
        dealer %= NUMBER_OF_PLAYERS;

        String s = makeIntroduction( whoseTurn, dealer );

        cardWindow.update();
        if ( whoseTurn == humanIndex )
        {
            humanMove( dealer );
        }
        else
        {
            chooseMove( whoseTurn, dealer );
        }
        cardWindow.update();
        s += "\n" + players[whoseTurn].printHand();
        s += "\nValue: " + players[whoseTurn].getHandValue() + "\n";
        return s;
    }


    void chooseMove( int whoseTurn, int dealer )
    {
        cardWindow.update();
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
        window.message( "It's your turn to be the dealer!" );
        window.message( "Your hand is:\n" + players[humanIndex].printHand()
            + "\nwith a value of " + players[humanIndex].getHandValue() );
        cheat();
        window.message( "As dealer, you have to hit until 17." );
        love.dealerStrategy( players[humanIndex], deck1 );
    }


    boolean cheat()
    {
        window.message( "The card in your sleeve is "
            + players[humanIndex].getPocket() );
        window.message( "Enter \"cheat\" to cheat. "
            + "Enter anything else to continue." );
        while (input.equals( code ))
        {
            
        }
        String in = input;
        input = code;
        cardWindow.update();
        if ( !in.equalsIgnoreCase( "cheat" ) )
        {
            return false;
        }
        cardWindow.update();

        // switch card in sleeve to facedown card.
        players[humanIndex].swapCard();
        window.message( "Your hand is now:\n"
            + players[humanIndex].printHand() + "\nwith a value of "
            + players[humanIndex].getHandValue() );
        window.message( "and the card in your sleeve is "
            + players[humanIndex].getPocket() );
        cardWindow.update();
        window.setPocket( players[humanIndex].getPocket().toString() );

        if ( Math.random() < CHEAT_FAIL_CHANCE )
        {
            window.message( "You were caught cheating! "
                + "Be prepared to face punishment..." );
            List<Card> hand = players[humanIndex].getHand();
            players[humanIndex].resetHand();
            for ( Card c : hand )
            {
                discardPile.push( c );
            }
            cardWindow.update();
            return true;
        }
        return false;
    }


    void humanMove( int dealer )
    {
        if ( dealer == humanIndex )
        {
            humanDealer();
            return;
        }
        cardWindow.update();

        window.message( "It is now your turn. Your hand is:\n"
            + players[humanIndex].printHand() + "\nwith a value of "
            + players[humanIndex].getHandValue() );
        if ( cheat() )
        {
            return;
        }
        cardWindow.update();

        window.message( "Make a decision! Your hand is:\n"
            + players[humanIndex].printHand() + "\nwith a value of "
            + players[humanIndex].getHandValue() );
        window.message( "\"f\" to surrender, \"h\" to hit, "
            + "\n\"s\" to stand, \"d\" to double down" );
        cardWindow.update();
        char c = ' ';
        // f forfeit
        // h hit
        // s stand
        // d double down
        while (input.equals( code ))
        {
            
        }
        String in = input;
        input = code;
        while ( in.length() == 0 )
        {
            while (input.equals( code ))
            {
                
            }
            in = input;
            input = code;
        }
        c = in.charAt( 0 );
        while ( c != 'f' && c != 'h' && c != 's' && c != 'd' )
        {
            window.message( "Please enter a valid command." );
            while (input.equals( code ))
            {
                
            }
            in = input;
            input = code;
            while ( in.length() == 0 )
            {
                while (input.equals( code ))
                {
                    
                }
                in = input;
                input = code;
            }
            c = in.charAt( 0 );
            cardWindow.update();
        }

        while ( true )
        {
            if ( c == 'f' )
            {
                window.message( "You surrender." );
                players[humanIndex].changeSurrendered( true );
                cardWindow.update();
                return;
            }
            else if ( c == 'h' )
            {
                window.message( "You decide to hit." );
                Card card = deck1.draw();
                window.message( "You get a " + card.toString() + "!" );
                players[humanIndex].addCard( card );
                cardWindow.update();
                window.message( "Your current hand is "
                    + players[humanIndex].printHand() );
                cardWindow.update();
                if ( players[humanIndex].getHandValue() > 21 )
                {
                    window.message( "You busted!" );
                    cardWindow.update();
                    return;
                }
            }
            else if ( c == 's' )
            {
                window.message( "You decide to stand. Your turn ends." );
                cardWindow.update();
                return;
            }
            else
            {
                // if(c == 'd' )
                window.message( "You decide to double down." );
                cardWindow.update();

                if ( players[humanIndex].doubleDown() )
                {
                    Card card = deck1.draw();
                    window.message( "You get a " + card.toString() + "!" );
                    players[humanIndex].addCard( card );
                    window.message( "Your current hand is "
                        + players[humanIndex].printHand() );
                    cardWindow.update();
                    if ( players[humanIndex].getHandValue() > 21 )
                    {
                        window.message( "You busted!" );
                    }
                    return;
                }
                else
                {
                    window.message( "You don't have enough chips!" );
                    window.message( "You hit instead." );
                    Card card = deck1.draw();
                    window.message( "You get a " + card.toString() + "!" );
                    players[humanIndex].addCard( card );
                    window.message( "Your current hand is "
                        + players[humanIndex].printHand() );
                    cardWindow.update();
                    if ( players[humanIndex].getHandValue() > 21 )
                    {
                        window.message( "You busted!" );
                        return;
                    }
                }
            }

            cardWindow.update();
            window.message( "What is your next move?" );
            while (input.equals( code ))
            {
                
            }
            in = input;
            input = code;
            while ( in.length() == 0 )
            {
                while (input.equals( code ))
                {
                    
                }
                in = input;
                input = code;
            }
            c = in.charAt( 0 );
            cardWindow.update();

            while ( c != 'f' && c != 'h' && c != 's' && c != 'd' )
            {
                window.message( "Please enter a valid command." );
                window.message( "\"f\" to surrender, \"h\" to hit, "
                    + "\n\"s\" to stand, \"d\" to double down" );
                while (input.equals( code ))
                {
                    
                }
                c = input.charAt( 0 );
                input = code;
                cardWindow.update();
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
     * -4: player cheated
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
     * 3: dealer cheated
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

        cardWindow.update();
        if ( pCards == 0 )
        {
            return -4;
        }
        if ( dCards == 0 )
        {
            return 3;
        }
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

        cardWindow.update();
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
        cardWindow.update();
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
        cardWindow.update();
    }


    public int resetDeck()
    {
        betAI.reset();
        while ( !discardPile.isEmpty() )
        {
            deck1.collect( discardPile.pop() );
        }
        deck1.shuffle();

        cardWindow.update();
        return deck1.getSize();
    }
    
    public Player getHuman()
    {
        return players[humanIndex];
    }
    
    public Player[] getPlayers()
    {
        Player[] result = new Player[players.length];
        int x = 0;
        for (int j = humanIndex; j < players.length; j++)
        {
            result[j - humanIndex] = players[j];
            x++;
        }
        for (int j = 0; j < humanIndex; j++)
        {
            result[j + x] = players[j];
        }
        return result;
    }
    
    public void inputChange(String change)
    {
        input = change;
    }
}
