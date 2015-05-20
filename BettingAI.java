package aldz_Blackjack;

public class BettingAI
{
    private int hiLo;

    private int hiOptI;

    private int hiOptII;

    private int ko;

    private int omegaII;

    private int red;

    private int zen;

    private static final int DECK_MAX = 52;


    /**
     * This is the constructor. It sets all of the card counters to 0.
     */
    public BettingAI()
    {
        reset();
    }


    /**
     * 
     * This resets the counts of all of the card counters to 0.
     */
    public void reset()
    {
        hiLo = 0;
        hiOptI = 0;
        hiOptII = 0;
        ko = 0;
        omegaII = 0;
        red = 0;
        zen = 0;
    }


    /**
     * 
     * This updates the current count of the card, using the various card
     * counting strategies.
     * 
     * @param c
     *            Card that was revealed to the card counter
     */
    public void update( Card c )
    {
        int value = c.getBlackValue();
        if ( value == 2 )
        {
            hiLo += 1;
            // hiOptI += 0;
            hiOptII += 1;
            ko += 1;
            omegaII += 1;
            red += 1;
            zen += 1;
        }
        else if ( value == 3 )
        {
            hiLo += 1;
            hiOptI += 1;
            hiOptII += 1;
            ko += 1;
            omegaII += 1;
            red += 1;
            zen += 1;
        }
        else if ( value == 4 )
        {
            hiLo += 1;
            hiOptI += 1;
            hiOptII += 2;
            ko += 1;
            omegaII += 2;
            red += 1;
            zen += 2;
        }
        else if ( value == 5 )
        {
            hiLo += 1;
            hiOptI += 1;
            hiOptII += 2;
            ko += 1;
            omegaII += 2;
            red += 1;
            zen += 2;
        }
        else if ( value == 6 )
        {
            hiLo += 1;
            hiOptI += 1;
            hiOptII += 1;
            ko += 1;
            omegaII += 2;
            red += 1;
            zen += 2;
        }
        else if ( value == 7 )
        {
            // hiLo += 0;
            // hiOptI += 0;
            hiOptII += 1;
            ko += 1;
            omegaII += 1;
            red += 1;
            zen += 1;
        }
        // else if(value == 8) everything += 0
        else if ( value == 9 )
        {
            // hiLo -= 0;
            // hiOptI -= 0;
            // hiOptII -= 0;
            // ko -= 0;
            omegaII -= 1;
            // red -= 0;
            // zen -= 0;
        }
        else if ( value == 10 )
        {
            hiLo -= 1;
            hiOptI -= 1;
            hiOptII -= 2;
            ko -= 1;
            omegaII -= 2;
            red -= 1;
            zen -= 2;
        }
        else if ( value == 1 )
        {
            hiLo -= 1;
            // hiOptI -= 0;
            // hiOptII -= 0;
            ko -= 1;
            // omegaII -= 0;
            red -= 1;
            zen -= 1;
        }
    }


    /**
     * 
     * This calculates the amount of chips a player should bet.
     * 
     * Skill levels:
     * 
     * -1 Drunkard
     * 
     * 0 Average
     * 
     * 1 Amateur
     * 
     * 2 Pro
     * 
     * @param cash
     *            int amount of chips the Player betting has
     * @param skill
     *            int the skill level of the Player betting
     * @param deckSize
     *            int the current size of the deck
     * @return int amound
     */
    int calculateBet( int cash, int skill, int deckSize )
    {
        double d = Math.random();

        double score = 0;

        if ( skill == -1 )
        {
            d = -1;
        }
        else if ( skill == 0 )
        {
            d *= 2;
        }
        else if ( skill == 1 )
        {
            d = 3 * d + 1;
        }
        else
        {
            // if(skill == 2)
            d = 4 * d + 4;
        }
        int res = (int)d;

        if ( res == -1 )
        {
            score = 20 - hiLo;
        }
        else if ( res == 0 )
        {
            score = 0;
        }
        else if ( res == 1 )
        {
            score = hiLo;
        }
        else if ( res == 2 )
        {
            score = hiOptI;
        }
        else if ( res == 3 )
        {
            score = hiOptII;
        }
        else if ( res == 4 )
        {
            score = ko;
        }
        else if ( res == 5 )
        {
            score = omegaII;
        }
        else if ( res == 6 )
        {
            score = red;
        }
        else if ( res == 7 )
        {
            score = zen;
        }

        score = score * DECK_MAX / deckSize;

        double proportionToBet = ( score + 10 ) / 100;
        int betAmount = (int)( cash * proportionToBet );

        return betAmount;
    }

    // public int getHiLo()
    // {
    // return hiLo;
    // }
    //
    //
    // public int getHiOptI()
    // {
    // return hiOptI;
    // }
    //
    //
    // public int getHiOptII()
    // {
    // return hiOptII;
    // }
    //
    //
    // public int getKO()
    // {
    // return ko;
    // }
    //
    //
    // public int getOmegaII()
    // {
    // return omegaII;
    // }
    //
    //
    // public int getRed7()
    // {
    // return red;
    // }
    //
    //
    // public int getZenCount()
    // {
    // return zen;
    // }
}
