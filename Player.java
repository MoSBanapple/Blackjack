package aldz_Blackjack;

import java.util.LinkedList;
import java.util.List;


public class Player
{
    List<Card> hand;

    int[] chips; // 1 5 10 20 50 100
    
    boolean stayed;
    
    public Player()
    {
        hand = new LinkedList<Card>();
        chips = new int[] { 8, 1, 7, 5, 2, 10 };
        stayed = false;
    }


    public void addCard( Card card )
    {
        hand.add( card );
    }

    public void resetHand()
    {
        hand.clear();
    }
    
    public String printHand()
    {
        String s = "";
        for( Card c: hand)
        {
           s += c.toString() + " ";
        }
        return s;
    }
    
//    public int getHandValue()
//    {
//        int sum = 0;
//        for( )
//        return sum;
//    }
    //TODO
    
    // abstract void makeMove( int whatAction );

    public int[] getChips()
    {
        return chips;
    }


    public String stringChips()
    {
        String s = "1:\t" + chips[0] + "\n";
        s += "5:\t" + chips[1] + "\n";
        s += "10:\t" + chips[2] + "\n";
        s += "20:\t" + chips[3] + "\n";
        s += "50:\t" + chips[4] + "\n";
        s += "100:\t" + chips[5] + "\n";
        s += "sum:\t" + sumChips();
        return s;
    }


    public int sumChips()
    {
        int sum = chips[0];
        sum += 5 * chips[1];
        sum += 10 * chips[2];
        sum += 20 * chips[3];
        sum += 50 * chips[4];
        sum += 100 * chips[5];
        return sum;
    }
    
    public void changeStayed()
    {
        stayed = !stayed;
    }
}
