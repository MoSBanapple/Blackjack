package aldz_Blackjack;

import java.util.LinkedList;
import java.util.List;


/**
 * This is the player class. Holds and receives cards. It also enacts actions
 * within itself. It represents a player n the game of blackjack.
 *
 * @author dzhang640
 * @version May 20, 2015
 * @author Period: TODO
 * @author Assignment: ALDZ_Blackjack
 *
 * @author Sources: TODO
 */
public class Player
{
    private String name;

    private List<Card> hand;

    private Card pocket;

    private int chips; // 1 5 25 100

    private boolean stayed;

    private int currentBet;

    private Player secondHand;


    /**
     * This is the constructor for the player class.
     */
    public Player()
    {
        secondHand = null;
        name = "";
        hand = new LinkedList<Card>();
        chips = 2000;
        stayed = false;
        pocket = null;
        currentBet = 0;
    }


    /**
     * Gives the name for the player class.
     * @return the name of the player.
     */
    String getName()
    {
        return name;
    }


    /**
     * Sets a new name for the player class.
     * @param inputName the desired name
     * @return the previous name of the class.
     */
    String setName( String inputName )
    {
        String temp = name;
        name = inputName;
        return temp;
    }


    /**
     * Checks if the player has an ace
     * @return whether the player has an ace
     */
    public boolean hasAce()
    {
        for ( Card c : hand )
            if ( c.getBlackValue() == 1 )
                return true;
        return false;
    }


    /**
     * Adds a card to the player's hand
     * @param card The card to be added
     */
    public void addCard( Card card )
    {
        hand.add( card );
    }


    /**
     * Clears the hand of the player
     */
    public void resetHand()
    {
        hand = new LinkedList<Card>();
    }


    /**
     * Returns the list of cards of the player
     * @return the hand of cards
     */
    public List<Card> getHand()
    {
        return hand;
    }


    /**
     * Prints the hand of the player
     * @return a list of cards of the player's hands
     */
    public String printHand()
    {
        String s = "";
        Card c = null;
        for ( int i = 0; i < hand.size() - 1; i++ )
        {
            c = hand.get( i );
            s += c.toString() + ", ";
        }
        c = hand.get( hand.size() - 1 );
        s += c.toString();
        return s;
    }


    /**
     * Gives the blackjack value of the player's hand
     * @return the blackjack value of the player's hand
     */
    public int getHandValue()
    {
        int elevens = 0;

        int sum = 0;
        for ( Card c : hand )
        {
            int temp = c.getBlackValue();

            if ( temp == 1 )
            {
                temp = 11;
                elevens++;
            }

            sum += temp;
            while ( sum > 21 && elevens > 0 )
            {
                elevens--;
                sum -= 10;
            }
        }
        return sum;
    }


    /**
     * Swaps a card into the packet
     */
    public void swapCard()
    {
        Card temp = hand.get( 0 );
        hand.set( 0, pocket );
        pocket = temp;
    }
    
    public void addPocket(Card card)
    {
        pocket = card;
    }


    // abstract void makeMove( int whatAction );

    public int setChips( int newChips )
    {
        int temp = getChips();
        chips = newChips;
        return temp;
    }


    public void addChips( int newChips )
    {
        chips += newChips;
    }


    public int addBet( int bet )
    {
        if ( chips < bet )
        {
            bet = chips;
        }

        chips -= bet;
        currentBet += bet;
        return bet;
    }


    public void resetBet()
    {
        currentBet = 0;
    }


    public int getChips()
    {
        return chips;
    }


    public String stringChips()
    {
        return name + "\thas " + chips + "\tchips";
    }


    public int sumChips()
    {
        return chips;
    }


    public int sumBet()
    {
        return currentBet;
    }


    public int getBet()
    {
        return currentBet;
    }


    public void changeStayed( boolean boo )
    {
        stayed = boo;
    }


    public boolean isStayed()
    {
        return stayed;
    }


    public boolean doubleDown()
    {
        stayed = true;

        if ( chips >= currentBet )
        {
            addBet( currentBet );
            return true;
        }
        return false;
    }


    public int compareTo( Player other )
    {
        return this.sumChips() - other.sumChips();
    }


    public boolean equals( Player other )
    {
        return this.sumChips() == other.sumChips();
    }

}
