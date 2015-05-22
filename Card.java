package aldz_Blackjack;

/**
 *  This class represents a standard poker card in the game of BlackJack
 *
 * @author Andrew Lee and Derek Zhang
 * @version May 20, 2015
 * @author Period: 6
 * @author Assignment: ALDZ_Blackjack
 *
 * @author Sources: NA
 */
public class Card
{
    private int value;

    private String suit;


    /**
     * @param val
     *            The number of the card. 11-13 represent royals.
     * @param cardSuit
     *            The suit of the card.
     */
    public Card( int val, String cardSuit )
    {
        value = val;
        suit = cardSuit;
    }


    /**
     * Returns the number value of the card
     * @return the number value of the card
     */
    public int getValue()
    {
        return value;
    }


    /**
     * Returns the blackjack value of the card
     * @return the blackjack value of the card
     */
    public int getBlackValue()
    {
        if ( value > 10 )
        {
            return 10;
        }
        else
        {
            return value;
        }
    }


    public String getSuit()
    {
        return suit;
    }


    public String toString()
    {
        if ( value > 1 && value < 11 )
        {
            return value + " of " + suit;
        }
        else
        {
            if ( value == 1 )
            {
                return "Ace of " + suit;
            }
            String[] royals = new String[3];
            {
                royals[0] = "Jack";
                royals[1] = "Queen";
                royals[2] = "King";
            }
            return royals[value - 11] + " of " + suit;
        }
    }
}
