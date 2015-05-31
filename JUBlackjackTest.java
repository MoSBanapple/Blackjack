package aldz_Blackjack;

import static org.junit.Assert.*;

import java.util.*;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;


/**
 * JUBlackjactTest tests the classes Card, Deck, Player, AI, BettingAI, and
 * Blackjack.
 *
 * @author Andrew Lee and Derek Zhang
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: ALDZ_Blackjack
 *
 * @author Sources: NA
 */
public class JUBlackjackTest
{
    // --Test Card
    /**
     * TradeOrder tests:
     * 
     * CardConstructor - constructs Card and then compare toString
     * 
     * CardGetValue - compares value returned to constructed value
     * 
     * CardGetBlackValue - compares value returned to constructed value
     * 
     * CardGetSuit - compares value returned to constructed value
     * 
     * CardToString - compares value returned to null
     */
    private int value = 9;

    private int value2 = 11;

    private int blackValue = 10;

    private String suit = "Spades";


    /**
     * constructs Card and then compare toString
     */
    @Test
    public void cardConstructor()
    {
        Card c = new Card( value, suit );

        String toStr = c.toString();

        assertTrue( "<< Invalid Card Constructor >>",
            toStr.contains( value + " of " + suit ) );
    }


    /**
     * compares value returned to constructed value
     */
    @Test
    public void cardGetValue()
    {
        Card c = new Card( value, suit );
        assertEquals( "<< Card: " + c.getValue() + " should be " + value
            + " >>", value, c.getValue() );
    }


    /**
     * 
     * compares value returned to constructed value
     */
    @Test
    public void cardGetBlackValue()
    {
        Card c = new Card( value, suit );
        assertEquals( "<< Card: " + c.getBlackValue() + " should be " + value
            + " >>", value, c.getValue() );
        Card c2 = new Card( value2, suit );
        assertEquals( "<< Card: " + c2.getBlackValue() + " should be "
            + blackValue + " >>", blackValue, c2.getBlackValue() );
    }


    /**
     * compares value returned to constructed value
     */
    @Test
    public void cardGetSuit()
    {
        Card c = new Card( value, suit );
        assertEquals( "<< Card: " + c.getSuit() + " should be " + suit + " >>",
            suit,
            c.getSuit() );
    }


    /**
     * 
     * compares value returned to null
     */
    @Test
    public void cardToString()
    {
        Card c = new Card( value, suit );
        assertNotNull( c.toString() );
    }

    // -- Test Deck
    /**
     * Deck tests:
     * 
     * DeckConstructor - constructs Deck and then compare toString
     * 
     * DeckDefaultConstructor - constructs Deck and then compare toString
     * 
     * DeckShuffle - compares before and after
     * 
     * DeckDraw - compares value returned to constructed value
     * 
     * DeckCollect - compares before and after
     * 
     * DeckFind - compares value returned to constructed value
     * 
     * DeckGetSize - compares value returned to constructed value
     * 
     * DeckToString - compares value returned to null
     * 
     */

    private boolean shuffled = true;

    private int size = 52;


    /**
     * constructs Deck and then compare toString
     */
    @Test
    public void deckConstructor()
    {
        Deck d = new Deck( shuffled );

        String toStr = d.toString();

        assertTrue( "<< Invalid Deck Constructor >>",
            toStr.contains( value + " of " + suit ) );
    }


    /**
     * constructs Deck and then compare toString
     */
    @Test
    public void deckDefaultConstructor()
    {
        Deck d = new Deck();

        String toStr = d.toString();

        assertTrue( "<< Invalid Deck Constructor >>",
            toStr.contains( value + " of " + suit ) );
    }


    /**
     * compares before and after
     */
    @Test
    public void deckShuffle()
    {
        Deck d = new Deck( !shuffled );
        d.shuffle();
        assertEquals( "<< Deck: " + d.getSize() + " should be " + d.getSize()
            + " >>", size, d.getSize() );
    }


    /**
     * compares value returned to constructed value
     */
    @Test
    public void deckDraw()
    {
        Deck d = new Deck( !shuffled );
        Card c = d.draw();
        assertEquals( "<< Deck: " + c.toString() + " should be "
            + "Ace of Clubs", c.toString(), "Ace of Clubs" );
        assertEquals( "<< Deck: " + d.getSize() + " should be " + ( size - 1 )
            + " >>", size - 1, d.getSize() );
    }


    /**
     * compares before and after
     */
    @Test
    public void deckCollect()
    {
        Deck d = new Deck();
        Card c = d.draw();
        d.collect( c );
        assertEquals( "<< Deck: " + d.getSize() + " should be " + size + " >>",
            size,
            d.getSize() );
    }


    /**
     * compares value returned to constructed value
     */
    @Test
    public void deckFind()
    {
        Deck d = new Deck();
        Card c = d.find( 9 );
        assertEquals( "<< Deck: " + c.toString() + " should be " + 9 + " >>",
            c.getValue(),
            9 );
        assertEquals( "<< Deck: " + d.getSize() + " should be " + ( size - 1 )
            + " >>", size - 1, d.getSize() );

        Card c2 = d.find( 42 );
        assertEquals( "<< Deck: " + c2.getValue() + " should be " + -1 + " >>",
            -1,
            c2.getValue() );
        assertEquals( "<< Deck: " + c2.getSuit() + " should be " + "null"
            + " >>", "null", c2.getSuit() );
        assertEquals( "<< Deck: " + d.getSize() + " should be " + ( size - 1 )
            + " >>", size - 1, d.getSize() );
    }


    /**
     * compares value returned to constructed value
     */
    @Test
    public void deckGetSize()
    {
        Deck d = new Deck();
        assertEquals( "<< Deck: " + d.getSize() + " should be " + size + " >>",
            size,
            d.getSize() );
    }


    /**
     * compares value returned to null
     */
    @Test
    public void deckToString()
    {
        Deck d = new Deck();
        assertNotNull( d.toString() );
    }

    // -- Test Player
    /**
     * Player Tests:
     * 
     * PlayerConstructor - constructs Player then compare to null
     * 
     * PlayerTransferBet - compares before and after
     * 
     * PlayerGetName - compares returned value to constructed value
     * 
     * PlayerSetName - compares postcondition to expected
     * 
     * PlayerHasAce - compares returned value to constructed value
     * 
     * PlayerAddCard - compares before and after
     * 
     * PlayerResetHand - compares postcondition to expected
     * 
     * PlayerGetHand - compares returned value to constructed value
     * 
     * PlayerPrintHand - compares returned value to constructed value
     * 
     * PlayerGetHandValue - compares returned value to constructed value
     * 
     * PlayerSwapCard - compares before and after
     * 
     * PlayerAddPocket - compares before and after
     * 
     * PlayerGetPocket - compares returned value to constructed value
     * 
     * PlayerSetChips - compares postcondition to expected
     * 
     * PlayerAddChips - compares postcondition to expected
     * 
     * PlayerAddBet - compares postcondition to expected
     * 
     * PlayerResetBet - compares postcondition to expected
     * 
     * PlayerGetChips - compares returned value to constructed value
     * 
     * PlayerStringChips - compares returned value to constructed value
     * 
     * PlayerGetBet - compares returned value to constructed value
     * 
     * PlayerChangeSurrendered - compares postcondition to expected
     * 
     * PlayerIsSurrendered - compares returned value to constructed value
     * 
     * PlayerDoubleDown - compares postcondition to expected
     * 
     * PlayerCompareTo - compares returned value to constructed value
     * 
     * PlayerEquals - compares returned value to constructed value
     */
    private int start = 2000;

    private int bet = 100;

    private String name = "Alice";


    /**
     * constructs Player then compare to null
     */
    @Test
    public void playerConstructor()
    {
        Player p = new Player();
        assertNotNull( "<< Invalid Player Constructor >>", p );
    }


    /**
     * compares before and after
     */
    @Test
    public void playerTransferBet()
    {
        Player p = new Player();
        Player q = new Player();
        p.addBet( bet );
        p.transferBet( q );
        assertEquals( "<< Player: " + p.getChips() + " should be "
            + ( start - bet ) + " >>", start - bet, p.getChips() );
        assertEquals( "<< Player: " + q.getChips() + " should be "
            + ( start + bet ) + " >>", start + bet, q.getChips() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerGetName()
    {
        Player p = new Player();
        assertEquals( "<< Player: " + p.getName() + " should be " + "" + " >>",
            "",
            p.getName() );
    }


    /**
     * compares postcondition to expected
     */
    @Test
    public void playerSetName()
    {
        Player p = new Player();
        p.setName( name );
        assertEquals( "<< Player: " + p.getName() + " should be " + name
            + " >>", name, p.getName() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerHasAce()
    {
        Player p = new Player();
        assertEquals( "<< Player: " + p.hasAce() + " should be " + false
            + " >>", false, p.hasAce() );
        p.addCard( new Card( 1, null ) );
        assertEquals( "<< Player: " + p.hasAce() + " should be " + true + " >>",
            true,
            p.hasAce() );
    }


    /**
     * compares before and after
     */
    @Test
    public void playerAddCard()
    {
        Player p = new Player();
        Card c = new Card( 5, "Spades" );
        p.addCard( c );
        assertEquals( "<< Player: " + p.getHand().get( 0 ).toString()
            + " should be " + c.toString() + " >>", c.toString(), p.getHand()
            .get( 0 )
            .toString() );
    }


    /**
     * compares postcondition to expected
     */
    @Test
    public void playerResetHand()
    {
        Player p = new Player();
        p.addCard( new Card( 1, null ) );
        p.resetHand();
        assertEquals( "<< Player: " + p.getHand().size() + " should be " + 0
            + " >>", 0, p.getHand().size() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerGetHand()
    {
        Player p = new Player();
        List<Card> l = new LinkedList<Card>();
        Card c = new Card( 1, null );
        l.add( c );
        p.addCard( c );
        assertEquals( "<< Player: " + p.getHand().get( 0 ) + " should be "
            + l.get( 0 ) + " >>",
            l.get( 0 ),
            p.getHand().get( 0 ) );
    }


    /**
     * compares returned value to constructed value
     * 
     */
    @Test
    public void playerPrintHand()
    {
        Player p = new Player();
        assertNotNull( p.printHand() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerGetHandValue()
    {
        Player p = new Player();
        p.addCard( new Card( 1, null ) );
        p.addCard( new Card( 2, null ) );
        assertEquals( "<< Player: " + p.getHandValue() + " should be " + 13
            + " >>", 13, p.getHandValue() );
        p.addCard( new Card( 10, null ) );
        assertEquals( "<< Player: " + p.getHandValue() + " should be " + 13
            + " >>", 13, p.getHandValue() );
    }


    /**
     * compares before and after
     */
    @Test
    public void playerSwapCard()
    {
        Player p = new Player();
        Card c = new Card( 1, "Clubs" );
        p.addCard( c );
        p.addPocket( new Card( 2, null ) );
        p.swapCard();
        assertEquals( "<< Player: " + p.getPocket().toString() + " should be "
            + c.toString() + " >>", c.toString(), p.getPocket().toString() );
    }


    /**
     * compares before and after
     */
    @Test
    public void playerAddPocket()
    {
        Player p = new Player();
        Card c = new Card( 1, "Clubs" );
        p.addPocket( c );
        assertEquals( "<< Player: " + p.getPocket().toString() + " should be "
            + c.toString() + " >>", c.toString(), p.getPocket().toString() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerGetPocket()
    {
        Player p = new Player();
        Card c = new Card( 1, "Clubs" );
        p.addPocket( c );
        assertEquals( "<< Player: " + p.getPocket().toString() + " should be "
            + c.toString() + " >>", c.toString(), p.getPocket().toString() );
    }


    /**
     * compares postcondition to expected
     */
    @Test
    public void playerSetChips()
    {
        Player p = new Player();
        int temp = p.setChips( bet );
        assertEquals( "<< Player: " + temp + " should be " + start + " >>",
            start,
            temp );
        assertEquals( "<< Player: " + p.getChips() + " should be " + bet
            + " >>", bet, p.getChips() );
    }


    /**
     * compares postcondition to expected
     */
    @Test
    public void playerAddChips()
    {
        Player p = new Player();
        p.addChips( bet );
        assertEquals( "<< Player: " + p.getChips() + " should be "
            + ( start + bet ) + " >>", start + bet, p.getChips() );
    }


    /**
     * compares postcondition to expected
     */
    @Test
    public void playerAddBet()
    {
        Player p = new Player();
        p.addBet( bet );
        assertEquals( "<< Player: " + p.getChips() + " should be "
            + ( start - bet ) + " >>", start - bet, p.getChips() );
        assertEquals( "<< Player: " + p.getBet() + " should be " + bet + " >>",
            bet,
            p.getBet() );
        p.addBet( start );
        assertEquals( "<< Player: " + p.getChips() + " should be " + 0 + " >>",
            0,
            p.getChips() );
        assertEquals( "<< Player: " + p.getBet() + " should be " + start
            + " >>", start, p.getBet() );
    }


    /**
     * compares postcondition to expected
     */
    @Test
    public void playerResetBet()
    {
        Player p = new Player();
        p.addBet( bet );
        p.resetBet();
        assertEquals( "<< Player: " + p.getBet() + " should be " + 0 + " >>",
            0,
            p.getBet() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerGetChips()
    {
        Player p = new Player();
        assertEquals( "<< Player: " + p.getChips() + " should be " + start
            + " >>", start, p.getChips() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerStringChips()
    {
        Player p = new Player();
        p.setName( name );
        assertEquals( "<< Player: " + p.stringChips() + " should be " + name
            + "\thas " + start + "\tchips" + " >>", name + "\thas " + start
            + "\tchips", p.stringChips() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerGetBet()
    {
        Player p = new Player();
        p.addBet( bet );
        assertEquals( "<< Player: " + p.getChips() + " should be "
            + ( start - bet ) + " >>", start - bet, p.getChips() );
        assertEquals( "<< Player: " + p.getBet() + " should be " + bet + " >>",
            bet,
            p.getBet() );
        p.addBet( start );
    }


    /**
     * compares postcondition to expected
     */
    @Test
    public void playerChangeSurrendered()
    {
        Player p = new Player();
        p.changeSurrendered( false );
        assertFalse( p.isSurrendered() );
        p.changeSurrendered( true );
        assertTrue( p.isSurrendered() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerIsSurrendered()
    {
        Player p = new Player();
        assertFalse( p.isSurrendered() );
    }


    /**
     * compares postcondition to expected
     */
    @Test
    public void playerDoubleDown()
    {
        Player p = new Player();
        p.addBet( 900 );
        assertTrue( p.doubleDown() );
        assertEquals( "<< Player: " + p.getChips() + " should be " + 200
            + " >>", 200, p.getChips() );
        assertEquals( "<< Player: " + p.getBet() + " should be " + 1800 + " >>",
            1800,
            p.getBet() );
        assertFalse( p.doubleDown() );
        assertEquals( "<< Player: " + p.getChips() + " should be " + 200
            + " >>", 200, p.getChips() );
        assertEquals( "<< Player: " + p.getBet() + " should be " + 1800 + " >>",
            1800,
            p.getBet() );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerCompareTo()
    {
        Player p = new Player();
        Player q = new Player();
        q.setChips( bet );
        int difference = start - bet;
        assertEquals( "<< Player: " + p.compareTo( q ) + " should be "
            + difference + " >>", difference, p.compareTo( q ) );
    }


    /**
     * compares returned value to constructed value
     */
    @Test
    public void playerEquals()
    {
        Player p = new Player();
        Player q = new Player();
        assertTrue( p.equals( q ) );
        q.setChips( bet );
        assertFalse( p.equals( q ) );
    }


    // -- Test AI
    /**
     * AI Tests:
     * 
     * AIConstructor - constructs AI then compare null
     * 
     * AIDealerStrategy - checks if throws any exceptions
     * 
     * AIGuessingStrategy - checks if throws any exceptions
     * 
     * AIRandomStrategy - checks if throws any exceptions
     * 
     * AIPeekingStrategy - checks if throws any exceptions
     * 
     * AIWikipediaStrategy - checks if throws any exceptions
     */
    /**
     * constructs AI then compare null
     */
    @Test
    public void aIConstructor()
    {
        AI love = new AI();
        assertTrue( "<< Invalid AI Constructor >>", love != null );
    }


    /**
     * checks if throws any exceptions
     */
    @Test
    public void aIDealerStrategy()
    {
        AI love = new AI();
        Player plai = new Player();
        Player deai = new Player();
        Deck dai = new Deck();
        plai.addCard( dai.draw() );
        plai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        boolean works = true;
        try
        {
            love.dealerStrategy( plai, dai );
        }
        catch ( Exception ex )
        {
            works = false;
        }
        assertTrue( "<< AI failed >>", works );
    }


    /**
     * checks if throws any exceptions
     */
    @Test
    public void aIGuessingStrategy()
    {
        AI love = new AI();
        Player plai = new Player();
        Player deai = new Player();
        Deck dai = new Deck();
        plai.addCard( dai.draw() );
        plai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        boolean works = true;
        try
        {
            love.guessingStrategy( plai, deai, dai );
        }
        catch ( Exception ex )
        {
            works = false;
        }
        assertTrue( "<< AI failed >>", works );
    }


    /**
     * checks if throws any exceptions
     */
    @Test
    public void aIRandomStrategy()
    {
        AI love = new AI();
        Player plai = new Player();
        Player deai = new Player();
        Deck dai = new Deck();
        plai.addCard( dai.draw() );
        plai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        boolean works = true;
        try
        {
            love.randomStrategy( plai, dai );
        }
        catch ( Exception ex )
        {
            works = false;
        }
        assertTrue( "<< AI failed >>", works );
    }


    /**
     * checks if throws any exceptions
     */
    @Test
    public void aIPeekingStrategy()
    {
        AI love = new AI();
        Player plai = new Player();
        Player deai = new Player();
        Deck dai = new Deck();
        plai.addCard( dai.draw() );
        plai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        boolean works = true;
        try
        {
            love.peekingStrategy( plai, deai, dai );
        }
        catch ( Exception ex )
        {
            works = false;
        }
        assertTrue( "<< AI failed >>", works );
    }


    /**
     * checks if throws any exceptions
     */
    @Test
    public void aIWikipeidaStrategy()
    {
        AI love = new AI();
        Player plai = new Player();
        Player deai = new Player();
        Deck dai = new Deck();
        plai.addCard( dai.draw() );
        plai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        deai.addCard( dai.draw() );
        boolean works = true;
        try
        {
            love.wikipediaStrategy( plai, deai, dai );
        }
        catch ( Exception ex )
        {
            works = false;

        }
        assertTrue( "<< AI failed >>", works );
    }

    // -- Test BettingAI
    /**
     * BettingAI Tests:
     * 
     * BettingAIConstructor - constructs BettingAI then compare getCount
     * 
     * BettingAIReset - compares postcondition with constructed value
     * 
     * BettingAIUpdate - compares postcondition with constructed value
     * 
     * BettingAICalcuateBet - compares value returned to constructed value
     * 
     * BettingAIGetCount - compares value returned to constructed value
     */

    private int[] blankArr = new int[] { 0, 0, 0, 0, 0, 0, 0 };

    private int[][] countResults = new int[][] { { -1, 0, 0, -1, 0, -1, -1 },
        { 1, 0, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 },
        { 1, 1, 2, 1, 2, 1, 2 }, { 1, 1, 2, 1, 2, 1, 2 },
        { 1, 1, 1, 1, 2, 1, 2 }, { 0, 0, 1, 1, 1, 1, 1 },
        { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, -1, 0, 0 },
        { -1, -1, -2, -1, -2, -1, -2 } };


    /**
     * constructs BettingAI then compare getCount
     */
    @Test
    public void bettingAIConstructor()
    {
        BettingAI b = new BettingAI();
        assertTrue( "<< Invalid BettingAI Constructor >>",
            Arrays.toString( b.getCount() )
                .equals( Arrays.toString( blankArr ) ) );
    }


    /**
     * compares postcondition with constructed value
     */
    @Test
    public void bettingAIReset()
    {
        BettingAI b = new BettingAI();
        b.update( new Card( value, suit ) );
        b.reset();
        assertEquals( "<< BettingAI: " + Arrays.toString( b.getCount() )
            + " should be " + Arrays.toString( blankArr ) + " >>",
            Arrays.toString( blankArr ),
            Arrays.toString( b.getCount() ) );
    }


    /**
     * compares postcondition with constructed value
     */
    @Test
    public void bettingAIUpdate()
    {
        BettingAI b = new BettingAI();
        for ( int i = 1; i <= 10; i++ )
        {
            b.update( new Card( i, null ) );
            assertEquals( "<< BettingAI: " + Arrays.toString( b.getCount() )
                + " should be " + Arrays.toString( countResults[i - 1] )
                + " >>",
                Arrays.toString( countResults[i - 1] ),
                Arrays.toString( b.getCount() ) );
            b.reset();
        }
    }


    /**
     * compares value returned to constructed value
     */
    @Test
    public void bettingAICalculateBet()
    {
        BettingAI b = new BettingAI();
        assertEquals( "<< BettingAI: " + b.calculateBet( 2000, 0, 52 )
            + " should be " + 200 + " >>", 200, b.calculateBet( 2000, 0, 52 ) );
    }


    /**
     * compares value returned to constructed value
     */
    @Test
    public void bettingAIGetCount()
    {
        BettingAI b = new BettingAI();
        assertEquals( "<< BettingAI: " + Arrays.toString( b.getCount() )
            + " should be " + Arrays.toString( blankArr ) + " >>",
            Arrays.toString( blankArr ),
            Arrays.toString( b.getCount() ) );
    }

    // -- Test Blackjack
    /**
     * Blackjack Tests:
     *
     * BlackjackConstructor - constructs Blackjack then compare to null
     *
     * BlackjackGetPlayers - compares returned value to constructed value
     *
     * BlackjackInputChange - checks if throws any exceptions
     */

    private String test = "Test";


    /**
     * constructs Blackjack then compare to null
     */
    @Test
    public void blackjackConstructor()
    {
        Blackjack black = new Blackjack();
        assertNotNull( "<< Blackjack Constructor Fail >>", black );
    }


    /**
     * compares returned value to constructed value
     * 
     */
    @Test
    public void blackjackGetPlayers()
    {
        Blackjack black = new Blackjack();
        Player[] res = black.getPlayers();
        for ( Player p : res )
        {
            assertEquals( "<< Blackjack: " + p.getChips() + " should be "
                + start + " >>", start, p.getChips() );
        }
    }


    /**
     * checks if throws any exceptions
     */
    @Test
    public void blackjackInputChange()
    {
        Blackjack black = new Blackjack();
        boolean works = true;
        try
        {
            black.inputChange( test );
        }
        catch ( Exception ex )
        {
            works = false;
        }
        assertTrue( "<< Blackjack failed >>", works );
    }
    
    /**
     * GameWindow stub tests
     * 
     * GameWindowConstructor - Tests the constructor for GameWindow
     * 
     * GameWindowSetPocket - Tests the setPocket method for GameWindow stub
     * 
     * GameWindowMessage - Tests the message method for GameWindow stub
     */
    
    /**
     * Tests the constructor for GameWindow
     */
    @Test
    public void gameWindowConstructor()
    {
        GameWindow gw = new GameWindow(new Blackjack());
        assertNotNull(gw);
    }
    
    /**
     * Tests the setPocket method for GameWindow stub
     */
    @Test
    public void gameWindowSetPocket()
    {
        GameWindow gw = new GameWindow(new Blackjack());
        assertNotNull(gw);
        gw.setPocket( null );
    }
    
    /**
     * Tests the message method for GameWindow stub
     */
    @Test
    public void gameWindowMessage()
    {
        GameWindow gw = new GameWindow(new Blackjack());
        assertNotNull(gw);
        gw.message( null );
    }
    
    
    /**
     * NameWindow stub tests
     * 
     * NameWindowConstructor - Tests the constructor for NameWindow
     */
    
    /**
     * Tests the constructor for NameWindow
     */
    @Test
    public void nameWindowConstructor()
    {
        NameWindow nw = new NameWindow(new Blackjack());
        assertNotNull(nw);
    }
    
    
    
    /**
     * StatusWindow stub tests
     * 
     * StatusWindowConstructor - Tests the constructor for StatusWindow stub
     * 
     * StatusWindowUpdateAll - Tests the updateAll method for StatusWindow stub
     * 
     * StatusWindowUpdate - Tests the update method for StatusWindow stub
     * 
     * StatusWindowUpdateDealer - Tests the updateDealer method for StatusWindow stub
     * 
     * StatusWindowHideCards - Tests the hideCards method for StatusWindow stub
     */
    
    /**
     * Tests the constructor for StatusWindow stub
     */
    @Test
    public void statusWindowConstructor()
    {
        StatusWindow sw = new StatusWindow(new Blackjack());
        assertNotNull(sw);
    }
    
    /**
     * Tests the updateAll method for StatusWindow stub
     */
    @Test
    public void statusWindowUpdateAll()
    {
        StatusWindow sw = new StatusWindow(new Blackjack());
        assertNotNull(sw);
        sw.updateAll();
    }
    
    /**
     * Tests the update method for StatusWindow stub
     */
    @Test
    public void statusWindowUpdate()
    {
        StatusWindow sw = new StatusWindow(new Blackjack());
        assertNotNull(sw);
        sw.update();
    }
    
    /**
     * Tests the updateDealer method for StatusWindow stub
     */
    @Test
    public void statusWindowUpdateDealer()
    {
        StatusWindow sw = new StatusWindow(new Blackjack());
        assertNotNull(sw);
        sw.updateDealer(null);
    }
    
    /**
     * Tests the hideCards method for StatusWindow stub
     */
    @Test
    public void statusWindowHideCards()
    {
        StatusWindow sw = new StatusWindow(new Blackjack());
        assertNotNull(sw);
        sw.hideCards();
    }

}
