package aldz_Blackjack;

import java.util.*;


public class AI
{
    private Random rand;


    public AI()
    {
        rand = new Random();
    }


    /**
     * 
     * This strategy will hit until the value of the player's hand reaches 17 or
     * above. It is also the strategy the Dealer has to do. This will hit on a
     * soft 17.
     * 
     * @param p
     *            user input Player to implement strategy
     * @param d
     *            user input Deck to draw from
     */
    public void dealerStrategy( Player p, Deck d )
    {
        if ( p.getHandValue() == 21 )
            return; // BLACKJACK
        int limit = 17;
        if ( p.hasAce() )
        {
            limit = 18;
        }

        while ( p.getHandValue() < limit )
        {
            p.addCard( d.draw() );
        }
    }


    /**
     * 
     * This strategy tries to guess the dealer's hand, and adjusts the strategy
     * accordingly.
     * 
     * @param p
     *            user input Player to implement strategy
     * @param dealer
     *            user input Deck to draw from
     * @param d
     *            user input Player who is the dealer of this round
     */
    public void guessingStrategy( Player p, Player dealer, Deck d )
    {
        int guessedDealerScore = 6 + rand.nextInt( 5 )
            + dealer.getHand().get( 1 ).getBlackValue();

        int tries = 2 + rand.nextInt( 2 );
        int currScore = p.getHandValue();
        while ( tries > 0 && currScore < guessedDealerScore )
        {
            p.addCard( d.draw() );
            currScore = p.getHandValue();
        }
    }


    /**
     * 
     * This strategy randomly determines whether to hit or stand or surrender.
     * It has a 50% chance of hitting, a 40% chance of standing, and a 10%
     * chance of surrender.
     * 
     * @param p
     *            user input Player to implement strategy
     * @param dealer
     *            user input Deck to draw from
     * @param d
     *            user input Player who is the dealer of this round
     */
    public void randomStrategy( Player p, Deck d )
    {
        if ( p.getHandValue() >= 20 )
            return;
        double r = Math.random();

        if ( r < 0.5 )
        {
            // Hit
            p.addCard( d.draw() );
            randomStrategy( p, d );
        }
        else if ( r < .9 || p.getHand().size() > 2 )
        {
            // Stay
        }
        else
        {
            // Surrender
        }
    }


    /**
     * 
     * This strategy determines whether to hit by peeking at the dealer's hand
     * value.
     * 
     * @param p
     *            user input Player to implement strategy
     * @param dealer
     *            user input Deck to draw from
     * @param d
     *            user input Player who is the dealer of this round
     */
    public void peekingStrategy( Player p, Player dealer, Deck d )
    {
        int dealerHand = dealer.getHandValue();
        int ownHand = p.getHandValue();
        if ( dealerHand == 21 && ownHand != 21 )
        {
            // surrender
            return;
        }
        while ( dealerHand < 17 )
        {
            dealerHand += 5;
        }
        if ( dealerHand > 21 || ownHand >= 20 )
        {
            // stay
            return;
        }
        if ( dealerHand - ownHand > 4 )
        {
            p.addCard( d.draw() );
            peekingStrategy( p, dealer, d );
        }
    }


    /**
     * 
     * This implements the Wikipedia basic strategy outlined in the Wikipedia
     * article for blackjack.
     * 
     * @param p
     *            user input Player to implement strategy
     * @param dealer
     *            user input Deck to draw from
     * @param d
     *            user input Player who is the dealer of this round
     */
    public void wikipediaStrategy( Player p, Player dealer, Deck d )
    {
        int unrevealed = dealer.getHand().get( 1 ).getBlackValue();
        if ( unrevealed == 1 )
            unrevealed += 10;
        // TODO chart in wikipedia
        // add a boolean pair() method in Player?
        // add a int numAces() method in Player?
        // add a boolean soft() method in Player?

        // the following assumes you have a hard total.
        int total = p.getHandValue();
        if ( p.getHand().size() == 2 && total == 21 )
            return; // BLACKJACK
        if ( total >= 21 )
            return; // BUSTED

        // -1 = surrender, 0 = stay, 1 = hit, 2 = double down hit
        int[][] hardWiki = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
            { 0, 0, 0, 0, 0, 1, 1, -1, -1, -1 },
            { 0, 0, 0, 0, 0, 1, 1, 1, -1, -1 },
            { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 }, { 1, 1, 0, 0, 0, 1, 1, 1, 1, 1 },
            { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 }, { 2, 2, 2, 2, 2, 2, 2, 2, 1, 1 },
            { 1, 2, 2, 2, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

        int row = 0;
        if ( total == 20 || row == 19 || row == 18 || row == 17 )
            row = 0;
        else if ( total == 16 )
            row = 1;
        else if ( total == 15 )
            row = 2;
        else if ( total == 14 || total == 13 )
            row = 3;
        else if ( total == 12 )
            row = 4;
        else if ( total == 11 )
            row = 5;
        else if ( total == 10 )
            row = 6;
        else if ( total == 9 )
            row = 7;
        else
            row = 8;

        int result = hardWiki[row][unrevealed - 2];
        if ( result == -1 )
        {
            // surrender
            return;
        }
        else if ( result == 0 )
        {
            // stand
            return;
        }
        else if ( result == 1 )
        {
            // hit
            p.addCard( d.draw() );
            wikipediaStrategy( p, dealer, d );
        }
        else
        // if (result == 2)
        {
            // double down
            p.addCard( d.draw() );
            if ( !p.doubleDown() )
            {
                wikipediaStrategy( p, dealer, d );
            }
        }
    }
}
