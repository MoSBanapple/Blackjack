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
 * @author Sources: 
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

    private static final String[] NAME = { "Alice", "Bob", "Capulet",
        "Dave",
        "Eve", "Fredrika", "George", "Hoover", "Ivan", "Jose", "Katherine",
        "Lisa", "Montague", "Nancy", "Obama", "Putin", "Que", "Rosas", "Sally",
        "Ted", "Utena", "Vern", "Walther", "XZAVIER", "Yetal", "Zinnia",
        "Washington", "Carver", "Bush", "Rin", "Fortuna", "Ferrel",
        "Robin Williams", "Spock", "Kirk", "Antonio", "Eugeo", " Leonardo",
        "Raphael", "Michelangelo", "Donatello", "Deadpool", "Bruce Wayne",
        "Oak", "Gary", "Kris", "Lyra", "Burce", "Louis", "Adams", "Jefferson",
        "Madison", "Lincoln", "Wilkes", "Waldo", "Jones", "Peter", "Plato",
        "Aristotle", "Heracles", "Sophacles", "Peck", "Stark", "Bill Nye",
        "Tyson", "Mrs. Frizzle", "Liz", "Clark Kent", "Arctic Seal", "Pablo",
        "Ralphie", "Pablo", "Pedro", "Jahohoy", "Harry Potter",
        "Percy Jackson", "Artemis Fowl", "J. K. Rowling", "Eion Colfer",
        "Bradbury", "Guy Montag", "Salinger", "Holden", "Tim O'Brien",
        "Hester", "Dimmesdale", "Chillingworth", "Huck Finn", "Jay Gatsby",
        "Nick Carraway", "Tom Buchanan", "Daisy Buchanan", "Wolfsheim", "Agar",
        "Hodor", "Fitzgerald", "Dorothy Ann", "Phoebe", "Carlos", "Wanda",
        "Arnold", "Kesha", "Rick James", "John Cena", "Schwarzenegger",
        "Lex Luthor", "Spassky", "Usain Bolt", "John Henry", "Humerus",
        "Clancy", "Soap", "Price", "Zebra", "Rat", "Pig", "Goat", "Garfield",
        "Bucky", "Bambi", "Simba", "Mufasa", "Sapphire", "Ruby", "Cronk" };

    private GameWindow window;
    
    private StatusWindow cardWindow;
    
    private String input;
    
    private NameWindow nameWindow;


    /**
     * The constructor for the Blackjack game.
     */
    public Blackjack()
    {
        input = code;

        humanIndex = 3;
        // humanIndex = (int)( Math.random() * NUMBER_OF_PLAYERS );
        // window.message( "You are player number " + ( humanIndex + 1 ) +
        // "!" );

        scan = new Scanner( System.in );

        nameWindow = new NameWindow(this);
        love = new AI();
        discardPile = new Stack<Card>();
        players = new Player[NUMBER_OF_PLAYERS];
        for ( int i = 0; i < NUMBER_OF_PLAYERS; i++ )
        {
            players[i] = new Player();
        }

        while (input.equals( code ))
        {
            
        }
        String name = input;
        input = code;
        
        while ( name.length() == 0 )
        {
            while (input.equals( code ))
            {
                
            }
            name = input;
            input = code;
        }

        players[humanIndex].setName( name );
        int random = 0;

        for ( int i = 0; i < NUMBER_OF_PLAYERS; i++ )
        {
            if ( i != humanIndex )
            {
                random = (int)( Math.random() * NAME.length );
                String tempName = NAME[random];
                while ( tempName.compareToIgnoreCase( name ) == 0 )
                {
                    random = (int)( Math.random() * NAME.length );
                    tempName = NAME[random];
                    
                }
                NAME[random] = name;
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
        cardWindow.update();
        window.message( "You sneak a card into your sleeve! It is the "
            + c.toString() + "." );
        players[humanIndex].addPocket( c );
        window.setPocket( c.toString() );
        
    }


    /**
     * The main method for the class.
     * @param args arguments.
     */
    public static void main( String[] args )
    {
        Blackjack aldz = new Blackjack();

        aldz.playBlackjack();

    }


    /**
     * Plays a round of Blackjack. This dictates the game.
     */
    private void playBlackjack()
    {
        int i = 0;
        int dealer = 0;

        while ( i < MAX_ROUNDS )
        {
            i++;
            window.message( "--ROUND " + i + "--" );
            
            doRound( dealer );
            window.message( "--END OF ROUND " + i + "-- CARDS LEFT IN DECK: "
                + deck1.getSize() + "\n" );
            window.message( "Press anything to continue." );
            while (input.equals( code ))
            {
                
            }
            input = code;
            window.message( "\n\n" );
            

            for ( Player p : players )
            {
                if ( p.getChips() <= 0 )
                {
                    Arrays.sort( players );
                    int index = NUMBER_OF_PLAYERS;

                    window.message( "GAME OVER" );
                    for ( Player play : players )
                    {
                        
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
            cardWindow.updateDealer( players[dealer].getName() );

            
//            window.message( "\"q\" to quit." );
//            
//            String response = scan.nextLine().toUpperCase();
//            if ( response.equals( "Q" ) )
//            {
//                window.message( "Goodbye!" );
//                return;
//            }
        }

        window.message( "Time is up!" );
        
        Arrays.sort( players );
        int index = NUMBER_OF_PLAYERS;
        for ( Player play : players )
        {
            play.addChips( play.getBet() );
            play.resetBet();
            window.message( index + ": " + play.getName() + " has "
                + play.getChips() + " chips." );
            index--;
            
        }
    }


    /**
     * Manages a round of blackjack.
     * @param dealer The dealer for the round.
     */
    private void doRound( int dealer )
    {
        updateSkill();
        // window.message( Arrays.toString( skill ) );
        dealer %= NUMBER_OF_PLAYERS;

        cardWindow.updateDealer( players[dealer].getName() );
        window.message( deal() );

        int currentBet = 0;
        int j = 0;
        for ( int i = dealer; j < NUMBER_OF_PLAYERS; i = ( i + 1 )
            % NUMBER_OF_PLAYERS )
        {
            currentBet = makeBet( i, dealer );
            cardWindow.update();
            players[i].addBet( currentBet );
            window.message( describePerson( players[i] ) );

            j++;
        }
        

        window.message("");

        j = 0;
        for ( int i = dealer; j < NUMBER_OF_PLAYERS; i = ( i + 1 )
            % NUMBER_OF_PLAYERS )
        {
            // Print befuddled hand
            String str = "";
            if ( i == dealer )
                str = befuddledHand( players[i] ) + " and is also the dealer.";
            else if (i == humanIndex)
            {
                str = fuddledHand( players[i] ) + ".";
            }
            else
            {
                str = players[i].getHand().size() + " cards.";
            }
            window.message( players[i].getName() + " has a hand of " + str );
            j++;
        }
        cardWindow.updateDealer( players[dealer].getName() );
        

        window.message("");
        // Make moves
        j = 0;
        for ( int i = ( dealer + 1 ) % NUMBER_OF_PLAYERS; j < NUMBER_OF_PLAYERS; i = ( i + 1 )
            % NUMBER_OF_PLAYERS )
        {
            window.message( doMove( i, dealer ) );
            j++;
        }

        // check victories, shuffle bets around

        for ( int i = 0; i < NUMBER_OF_PLAYERS; i++ )
        {
            consolidateBets( i, dealer );
        }
        cardWindow.updateDealer( players[dealer].getName() );
        

        
        cleanHands();

        window.message( "The amount of chips you have changed by "
            + ( players[humanIndex].getChips() - previousChips ) + "." );
    }


    /**
     * Prints a string of cards, sans the first one.
     * @param p Player with the hand to be printed.
     * @return Hand minus first card, string representation.
     */
    private String befuddledHand( Player p )
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
    
    /**
     * Prints a string of cards.
     * @param p Player with the hand to be printed.
     * @return Hand, string representation.
     */
    private String fuddledHand( Player p )
    {
        List<Card> hand = p.getHand();
        String s = "";

        int index = 0;
        for ( Card c : hand )
        {

                s += c.toString();
            
            if ( index < hand.size() - 1 )
            {
                s += ", ";
            }
            index++;
        }
        
        return s;
    }


    /**
     * Consolidates the bets of all players.
     * @param who Player consolidated
     * @param dealer The dealer for the round
     * @return The result of the bet
     */
    private int consolidateBets( int who, int dealer )
    {
        who %= NUMBER_OF_PLAYERS;
        if ( who == dealer )
            return 0;
        int result = result( players[dealer], players[who] );
        cardWindow.updateDealer( players[dealer].getName() );

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
        cardWindow.updateDealer( players[dealer].getName() );
        
        return result;
    }


    /**
     * Returns a string summary of the player
     * @param p Player to be summarized
     * @return String representation of the player
     */
    private String describePerson( Player p )
    {
        String s = p.getName();
        s += "\nhas " + p.getChips() + " chips";
        s += "\ncurrently betting " + p.getBet() + " chips";
        // s += "\nHand: " + p.printHand();
        // s += "\nWith a value of " + p.getHandValue();
        s += "\n";
        return s;
    }


    /**
     * String representation of bet
     * @param whoseTurn Turn
     * @param dealer Dealer for the roud
     * @param bet Bet value
     * @return String representation
     */
    private String stringBet( int whoseTurn, int dealer, int bet )
    {
        whoseTurn %= NUMBER_OF_PLAYERS;
        dealer %= NUMBER_OF_PLAYERS;

        if ( whoseTurn == dealer )
        {
            return "The dealer does not bet!";
        }
        cardWindow.updateDealer( players[dealer].getName() );
        

        return makeIntroduction( whoseTurn, dealer ) + " bet " + bet;
    }


    /**
     * Allows the player to make a bet.
     * @param whoseTurn Turn of player
     * @param dealer Dealer for the round
     * @return Bet
     */
    private int makeBet( int whoseTurn, int dealer )
    {
        whoseTurn %= NUMBER_OF_PLAYERS;
        dealer %= NUMBER_OF_PLAYERS;
        cardWindow.updateDealer( players[dealer].getName() );
        

        if ( whoseTurn == dealer )
        {
            return 0;
        }
        if ( whoseTurn == humanIndex )
        {
            window.message( "Make your bet! You have "
                + players[humanIndex].getChips() + " chips." );
            cardWindow.hideCards();
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
                    window.message( "Invalid input. Please enter a number into the betting box and press \"bet\"." );
                    
                    while (input.equals( code ))
                    {
                        
                    }
                    s = input;
                    input = code;
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


    /**
     * Introduces the player
     * @param whoseTurn Turn of player to be introduced
     * @param dealer Dealer for the round
     * @return String of the introduction
     */
    private String makeIntroduction( int whoseTurn, int dealer )
    {
        String s = players[whoseTurn].getName();
        cardWindow.updateDealer( players[dealer].getName() );

        if ( whoseTurn == dealer )
        {
            s += " the dealer";
        }
        
        s += "'s hand:";
        
        return s;
    }


    /**
     * Determines the move of the player
     * @param whoseTurn Turn of player
     * @param dealer Dealer for the round
     * @return String representation of move
     */
    private String doMove( int whoseTurn, int dealer )
    {
        whoseTurn %= NUMBER_OF_PLAYERS;
        dealer %= NUMBER_OF_PLAYERS;

        String s = makeIntroduction( whoseTurn, dealer );
        cardWindow.updateDealer( players[dealer].getName() );

        
        if ( whoseTurn == humanIndex )
        {
            humanMove( dealer );
        }
        else
        {
            chooseMove( whoseTurn, dealer );
        }
        
        s += "\n" + players[whoseTurn].printHand();
        if (players[whoseTurn].getHandValue() == 0)
        {
            s += "\nValue: " + players[whoseTurn].getHandValue() + " (Surrendered)\n";
        }
        else
        {
            s += "\nValue: " + players[whoseTurn].getHandValue() + "\n";
        }
        
        cardWindow.updateDealer( players[dealer].getName() );
        return s;
    }


    /**
     * Choose move of player
     * @param whoseTurn Turn of player
     * @param dealer Dealer for the round
     */
    private void chooseMove( int whoseTurn, int dealer )
    {
        
        if ( dealer == whoseTurn )
        {
            love.dealerStrategy( players[whoseTurn], deck1 );
        }
        else
        {
            love.wikipediaStrategy( players[whoseTurn], players[dealer], deck1 );
        }
        cardWindow.updateDealer( players[dealer].getName() );
    }


    /**
     * Prints out lines and sets the player to the dealer strategy.
     */
    private void humanDealer()
    {
        window.message( "It's your turn to be the dealer!" );
        window.message( "Your hand is:\n" + players[humanIndex].printHand()
            + "\nwith a value of " + players[humanIndex].getHandValue() );
        cheat();
        window.message( "As dealer, you have to hit until 17." );
        love.dealerStrategy( players[humanIndex], deck1 );
    }


    /**
     * Manages the cheating aspect of the game.
     * @return Whether cheated or not.
     */
    private boolean cheat()
    {
        window.message( "The card in your sleeve is "
            + players[humanIndex].getPocket() + ". You can cheat by swapping this card with your " + players[humanIndex].getHand().get( 0 ).toString() + "." );
        window.message( "Press \"cheat\" to cheat. "
            + "Press anything else to continue." );
        while (input.equals( code ))
        {
            
        }
        String in = input;
        input = code;
        
        if ( !in.equalsIgnoreCase( "cheat" ) )
        {
            return false;
        }
        

        // switch card in sleeve to facedown card.
        players[humanIndex].swapCard();
        cardWindow.update();
        window.message( "" );
        window.message( "Your hand is now:\n"
            + players[humanIndex].printHand() + "\nwith a value of "
            + players[humanIndex].getHandValue() );
        window.message( "and the card in your sleeve is "
            + players[humanIndex].getPocket() );
        
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
            cardWindow.updateAll();
            
            return true;
        }
        return false;
    }


    /**
     * Determines the move of the player.
     * @param dealer Dealer for the round.
     */
    private void humanMove( int dealer )
    {
        if ( dealer == humanIndex )
        {
            humanDealer();
            return;
        }
        cardWindow.updateDealer( players[dealer].getName() );
        

        window.message( "It is now your turn. Your hand is:\n"
            + players[humanIndex].printHand() + "\nwith a value of "
            + players[humanIndex].getHandValue() );
        if ( cheat() )
        {
            return;
        }
        cardWindow.updateDealer( players[dealer].getName() );
        

        window.message( "" );
        window.message( "Make a decision! Your hand is:\n"
            + players[humanIndex].printHand() + "\nwith a value of "
            + players[humanIndex].getHandValue() );
        window.message( "Press a button to determine your action." );
        
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
            
        }

        while ( true )
        {
            window.message( "" );
            if ( c == 'f' )
            {
                window.message( "You surrender." );
                players[humanIndex].changeSurrendered( true );
                cardWindow.updateAll();
                
                return;
            }
            else if ( c == 'h' )
            {
                window.message( "You decide to hit." );
                Card card = deck1.draw();
                cardWindow.update();
                window.message( "You get a " + card.toString() + "!" );
                players[humanIndex].addCard( card );
                cardWindow.update();
                
                window.message( "Your current hand is "
                    + players[humanIndex].printHand() );
                
                if ( players[humanIndex].getHandValue() > 21 )
                {
                    window.message( "You busted!\n" );
                    cardWindow.updateAll();
                    
                    return;
                }
            }
            else if ( c == 's' )
            {
                window.message( "You decide to stand. Your turn ends.\n" );
                cardWindow.updateAll();
                
                return;
            }
            else
            {
                // if(c == 'd' )
                window.message( "You decide to double down." );
                

                if ( players[humanIndex].doubleDown() )
                {
                    Card card = deck1.draw();
                    cardWindow.update();
                    window.message( "You get a " + card.toString() + "!" );
                    players[humanIndex].addCard( card );
                    cardWindow.update();
                    window.message( "Your current hand is "
                        + players[humanIndex].printHand() );
                    
                    if ( players[humanIndex].getHandValue() > 21 )
                    {
                        window.message( "You busted!\n" );
                        cardWindow.updateAll();
                    }
                    return;
                }
                else
                {
                    window.message( "You don't have enough chips!" );
                    window.message( "You hit instead." );
                    Card card = deck1.draw();
                    cardWindow.update();
                    window.message( "You get a " + card.toString() + "!" );
                    players[humanIndex].addCard( card );
                    cardWindow.update();
                    window.message( "Your current hand is "
                        + players[humanIndex].printHand() );
                    
                    if ( players[humanIndex].getHandValue() > 21 )
                    {
                        window.message( "You busted!\n" );
                        cardWindow.updateAll();
                        return;
                    }
                }
            }
            cardWindow.updateDealer( players[dealer].getName() );

            
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
            

            while ( c != 'f' && c != 'h' && c != 's' && c != 'd' )
            {
                window.message( "Please enter a valid command." );
                while (input.equals( code ))
                {
                    
                }
                c = input.charAt( 0 );
                input = code;
                
            }
            cardWindow.updateDealer( players[dealer].getName() );
        }
    }


    /**
     * Updates the skill of the player.
     */
    private void updateSkill()
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
    private int result( Player dealer, Player p )
    {
        int dHand = dealer.getHandValue();
        int dCards = dealer.getHand().size();
        int pHand = p.getHandValue();
        int pCards = p.getHand().size();

        
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

        
        return 0;
    }


    /**
     * Deals cards to all players.
     * @return String of recieved cards.
     */
    private String deal()
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


    /**
     * Clears hands of all players and returns cards to deck.
     */
    private  void cleanHands()
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


    /**
     * Resets and reshuffles the deck.
     * @return The size of the deck.
     */
    private int resetDeck()
    {
        betAI.reset();
        while ( !discardPile.isEmpty() )
        {
            deck1.collect( discardPile.pop() );
        }
        deck1.shuffle();

        
        return deck1.getSize();
    }
    
    
    /**
     * Returns a list of the players within the game, starting with the human player.
     * @return The list of players.
     */
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
    
    /**
     * Changes the value of the string "input". This method simulates a button press.
     * @param change The string input is to be changed to.
     */
    public void inputChange(String change)
    {
        input = change;
    }
}
