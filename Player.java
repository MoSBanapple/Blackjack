package aldz_Blackjack;

import java.util.LinkedList;
import java.util.List;


public class Player
{
    String name;

    List<Card> hand;

    Card pocket;

    int[] chips; // 1 5 25 100

    boolean stayed;


    public Player()
    {
        name = "";
        hand = new LinkedList<Card>();
        chips = new int[] { 25, 15, 20, 14 };
        stayed = false;
        pocket = null;
    }


    String getName()
    {
        return name;
    }


    String setName( String inputName )
    {
        String temp = name;
        name = inputName;
        return temp;
    }


    public boolean hasAce()
    {
        for ( Card c : hand )
            if ( c.getBlackValue() == 1 )
                return true;
        return false;
    }


    public void addCard( Card card )
    {
        hand.add( card );
    }


    public void resetHand()
    {
        hand = new LinkedList<Card>();
    }


    public List<Card> getHand()
    {
        return hand;
    }


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


    public int getHandValue()
    {
        int elevens = 0;
        int i = 0;

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
            i++;
        }
        return sum;
    }


    public void swapCard()
    {
        Card temp = hand.get( 0 );
        hand.set( 0, pocket );
        pocket = temp;
    }


    // abstract void makeMove( int whatAction );

    public int[] setChips( int one, int five, int twentyfive, int hundred )
    {
        int[] temp = getChips();
        chips[0] = one;
        chips[1] = five;
        chips[2] = twentyfive;
        chips[3] = hundred;
        return temp;
    }


    public int[] getChips()
    {
        return chips;
    }


    public String stringChips()
    {
        String s = "1:\t" + chips[0] + "\n";
        s += "5:\t" + chips[1] + "\n";
        s += "25:\t" + chips[2] + "\n";
        s += "100:\t" + chips[3] + "\n";
        s += "sum:\t" + sumChips();
        return s;
    }


    public int sumChips()
    {
        int sum = chips[0];
        sum += 5 * chips[1];
        sum += 25 * chips[2];
        sum += 100 * chips[3];
        return sum;
    }


    public void changeStayed()
    {
        stayed = !stayed;
    }
}
