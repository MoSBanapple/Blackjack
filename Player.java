package aldz_Blackjack;

import java.util.LinkedList;
import java.util.List;


public class Player
{
    private String name;

    private List<Card> hand;

    private Card pocket;

    private int chips; // 1 5 25 100

    private boolean stayed;

    private int currentBet;


    public Player()
    {
        name = "";
        hand = new LinkedList<Card>();
        chips = 2000;
        stayed = false;
        pocket = null;
        currentBet = 0;
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


    public void swapCard()
    {
        Card temp = hand.get( 0 );
        hand.set( 0, pocket );
        pocket = temp;
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
}
