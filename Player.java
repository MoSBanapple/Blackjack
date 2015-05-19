package aldz_Blackjack;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Player
{
    String name;

    List<Card> hand;

    Card pocket;

    int[] chips; // 1 5 25 100

    boolean stayed;
    
    int[] currentBet;

    boolean surrendered;
    
    Player secondHand;

    public Player()
    {
        name = "";
        hand = new LinkedList<Card>();
        chips = new int[] { 25, 15, 20, 14 };
        stayed = false;
        pocket = null;
        currentBet = new int[4];
        surrendered = false;
        secondHand = null;
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
        if (surrendered)
        {
            return -1;
        }
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


    public boolean swapCard()
    {
        Card temp = hand.get( 0 );
        hand.set( 0, pocket );
        pocket = temp;
        Random rand = new Random();
        return rand.nextInt( 4 ) == 0;
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
    
    public int[] addBet(int[] bet)
    {
        for (int j = 0; j < chips.length; j++)
        {
            if (bet[j] < chips[j])
            {
                chips[j] -= bet[j];
                currentBet[j] += bet[j];
            }
            else
            {
                currentBet[j] += chips[j];
                chips[j] = 0;
                 
            }
        }
        if (secondHand != null)
        {
            secondHand.addBet( bet );
        }
        return bet;
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
    
    public int sumBet()
    {
        int sum = currentBet[0];
        sum += 5 * currentBet[1];
        sum += 25 * currentBet[2];
        sum += 100 * currentBet[3];
        return sum;
    }


    public void changeStayed(boolean boo)
    {
        stayed = boo;
        if (secondHand != null)
        {
            secondHand.stayed = boo;
        }
    }
    
    public boolean isStayed()
    {
        return stayed;
    }
    
    public void doubleDown()
    {
        int[] values = new int[]{1, 5, 25, 100};
        stayed = true;
        int sum = this.sumChips();
        int add = 0;
        for (int j = chips.length - 1; j >= 0; j--)
        {
            while (chips[j] > 0 && add + values[j] > sum );
            {
                chips[j] --;
                currentBet[j] ++;
                add += values[j];
            }
        }
        if (secondHand != null)
        {
            //doubledown from main person;s chips
        }
        
    }
    public void surrender()
    {
        stayed = true;
        surrendered = true;
        for (int j = 0; j < chips.length; j++)
        {
            chips[j] += currentBet[j] - currentBet[j]/2;
            currentBet[j] /= 2;
        }
        
    }
    
    public void split()
    {
        if (hand.size() > 2)
        {
            return;
        }
        
        secondHand = new Player();
        secondHand.setChips( currentBet[0], currentBet[1],currentBet[2],currentBet[3] );
        secondHand.addBet( currentBet );
        
    }
    
    public Player splitHand()
    {
        return secondHand;
    }
    

    
    public Card[] reconcileSplit()
    {
        Card[] result = null;
        if (secondHand != null)
        {
            result = new Card[secondHand.getHand().size()];
            for (int j = 0; j < result.length; j++)
            {
                result[j] = secondHand.getHand().get( j );
            }

            for (int j = 0; j < chips.length; j++)
            {
                chips[j] += secondHand.getChips()[j];
            }
            secondHand = null;
        }
        return result;
    }
}
