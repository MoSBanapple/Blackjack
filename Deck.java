package aldz_Blackjack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


/**
 * This class represents the deck of cards.
 *
 * @author Derek Zhang and Andrew Lee
 * @version May 23, 2015
 * @author Period: 6
 * @author Assignment: ALDZ_Blackjack
 *
 * @author Sources: NA
 */
public class Deck
{
    private Queue<Card> cards;

    private Random rand;

    private int size;


    /**
     * Creates the deck of cards.
     * 
     * @param shuffled
     *            whether the deck starts out shuffled or not.
     */
    public Deck( boolean shuffled )
    {
        rand = new Random();
        cards = new LinkedList<Card>();
        for ( int j = 1; j <= 13; j++ )
        {
            cards.add( new Card( j, "Clubs" ) );
            cards.add( new Card( j, "Diamonds" ) );
            cards.add( new Card( j, "Hearts" ) );
            cards.add( new Card( j, "Spades" ) );
            size += 4;
        }
        if ( shuffled )
        {
            shuffle();
        }
    }


    /**
     * no-param constructor. Defaults to a shuffled deck.
     */
    public Deck()
    {
        this( true );
    }


    /**
     * Shuffles the deck of cards randomly.
     */
    public void shuffle()
    {
        ArrayList<Card> array = new ArrayList<Card>();
        int sizeArchive = cards.size();
        for ( int j = 0; j < sizeArchive; j++ )
        {
            array.add( cards.poll() );
        }
        for ( int j = 0; j < sizeArchive; j++ )
        {
            cards.add( array.remove( rand.nextInt( array.size() ) ) );
        }
    }


    /**
     * Draws the next card from the deck, removing it.
     * 
     * @return the next card.
     */
    public Card draw()
    {
        size--;
        return cards.poll();
    }


    /**
     * Takes a card and puts it at the bottom of the deck.
     * 
     * @param c
     *            the card collected.
     */
    public void collect( Card c )
    {
        cards.add( c );
        size++;
    }


    /**
     * Finds a card from the deck and draws it, replacing the deck how it was
     * before.
     * 
     * @param val
     *            value of the target card
     * @return the target card, or a card with a -1 value and null suit if the
     *         card isn't found.
     */
    public Card find( int val )
    {
        boolean check = true;
        Card target = new Card( -1, "null" );
        for ( int j = 0; j < cards.size(); j++ )
        {
            if ( cards.peek().getValue() == val && check )
            {
                target = draw();
                j++;
                check = false;
            }
            cards.add( cards.poll() );
        }
        return target;
    }


    /**
     * Gets the current size of the deck
     * 
     * @return the current size
     */
    public int getSize()
    {
        return size;
    }


    /**
     * Prints info of the deck
     * 
     * @return String of the deck
     */
    public String toString()
    {
        String result = "";
        for ( int j = 0; j < cards.size(); j++ )
        {
            result += cards.peek().toString() + "\n";
            cards.add( cards.poll() );
        }
        return result;
    }
}
