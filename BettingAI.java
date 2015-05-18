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

    public BettingAI()
    {
        hiLo = 0;
        hiOptI = 0;
        hiOptII = 0;
        ko = 0;
        omegaII = 0;
        red = 0;
        zen = 0;
    }


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


    public int getHiLo()
    {
        return hiLo;
    }


    public int getHiOptI()
    {
        return hiOptI;
    }


    public int getHiOptII()
    {
        return hiOptII;
    }


    public int getKO()
    {
        return ko;
    }


    public int getOmegaII()
    {
        return omegaII;
    }


    public int getRed7()
    {
        return red;
    }


    public int getZenCount()
    {
        return zen;
    }
}
