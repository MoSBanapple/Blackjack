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
     * Swaps the first card of the hand into the pocket
     */
    public void swapCard()
    {
        Card temp = hand.get( 0 );
        hand.set( 0, pocket );
        pocket = temp;
    }
    
    /**
     * Adds a card to the pocket
     * @param card Card to be pocketed
     */
    public void addPocket(Card card)
    {
        pocket = card;
    }


    // abstract void makeMove( int whatAction );

    /**
     * Sets the value of the chips
     * @param newChips new value of chips
     * @return old value of chips
     */
    public int setChips( int newChips )
    {
        int temp = getChips();
        chips = newChips;
        return temp;
    }
    
    
    


    /**
     * Adds to the value of the chips
     * @param newChips amount to be added
     */
    public void addChips( int newChips )
    {
        chips += newChips;
    }


    /**
     * Adds to the current bet
     * @param bet Amount to be added
     * @return the new bet amount
     */
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


    /**
     * Reduces the current bet to 0.
     */
    public void resetBet()
    {
        currentBet = 0;
    }


    /**
     * Gives the amount of chips the player has
     * @return the amount of chips
     */
    public int getChips()
    {
        return chips;
    }


    /**
     * Returns the name and number of chips
     * @return the name and number of chips
     */
    public String stringChips()
    {
        return name + "\thas " + chips + "\tchips";
    }





    /**
     * Gets the current bet of the player
     * @return the current bet 
     */
    public int getBet()
    {
        return currentBet;
    }


    /**
     * Changes whether the current player has stayed or gone
     * @param boo
     */
    public void changeStayed( boolean boo )
    {
        stayed = boo;
    }


    /**
     * Checks if the player has stayed
     * @return whether the player is stayed or not
     */
    public boolean isStayed()
    {
        return stayed;
    }


    /**
     * Doubles the bet and forces the player to stay
     * @return whether the doubledown was successful
     */
    public boolean doubleDown()
    {


        if ( chips >= currentBet )
        {
            stayed = true;
            addBet( currentBet );
            return true;
        }
        return false;
    }


    /**
     * Compares the chip value to another player
     * @param other player being compared to
     * @return This chip value minus the other chip value
     */
    public int compareTo( Player other )
    {
        return this.getChips() - other.getChips();
    }


    /**
     * Checks whether chip values are equal
     * @param other Player being compared
     * @return whether chip values are equal
     */
    public boolean equals( Player other )
    {
        return this.getChips() == other.getChips();
    }

}
