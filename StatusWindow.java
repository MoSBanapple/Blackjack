package aldz_Blackjack;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *  This window displays the cards and status of each player.
 *
 *  @author  Derek Zhang and Andrew Lee
 *  @version May 31, 2015
 *  @author  Period: 6
 *  @author  Assignment: ALDZ_Blackjack
 *
 *  @author  Sources: TODO
 */
public class StatusWindow extends JFrame implements ActionListener
{

    private Box[] b;

    private JLabel[] name;

    private Box chips1;

    private Box chips2;

    private Box chips3;

    private Box chips4;

    private JTextField[] value;

    private Box[] hand;

    private JLabel[][] hands;

    private Blackjack game;
    

    private HashMap<String, ImageIcon> cards;
    private String dealer;


    /**
     * @param togame The associated blackjack game.
     */
    public StatusWindow( Blackjack togame )
    {
        super( "StatusWindow" );
        game = togame;
        dealer = "";
        cards = new HashMap<String, ImageIcon>();
        cards.put( "back", new ImageIcon( "Cards/b1fv.png" ) );

        for ( int j = 1; j <= 13; j++ )
        {

            cards.put( new Card( j, "Clubs" ).toString(),
                new ImageIcon( "Cards/c" + j + ".png" ) );
            cards.put( new Card( j, "Diamonds" ).toString(),
                new ImageIcon( "Cards/d" + j + ".png" ) );
            cards.put( new Card( j, "Hearts" ).toString(),
                new ImageIcon( "Cards/h" + j + ".png" ) );
            cards.put( new Card( j, "Spades" ).toString(),
                new ImageIcon( "Cards/s" + j + ".png" ) );
        }
        JPanel panel = new JPanel();
        BoxLayout grid = new BoxLayout( panel, BoxLayout.Y_AXIS );
        panel.setLayout( grid );
        Player[] array = game.getPlayers();
        b = new Box[4];
        for ( int x = 0; x < b.length; x++ )
        {
            b[x] = new Box( BoxLayout.Y_AXIS );
        }
        name = new JLabel[4];
        for ( int j = 0; j < name.length; j++ )
        {
            name[j] = new JLabel( array[j].getName() );
        }
        chips1 = new Box( BoxLayout.X_AXIS );
        chips2 = new Box( BoxLayout.X_AXIS );
        chips3 = new Box( BoxLayout.X_AXIS );
        chips4 = new Box( BoxLayout.X_AXIS );
        chips1.add( new JLabel( "Chips:  " ) );
        chips2.add( new JLabel( "Chips:  " ) );
        chips3.add( new JLabel( "Chips:  " ) );
        chips4.add( new JLabel( "Chips:  " ) );
        value = new JTextField[4];
        for ( int j = 0; j < value.length; j++ )
        {
            value[j] = new JTextField( 5 );
            value[j].setEditable( false );
            value[j].setText( array[j].getChips() + "" );
            value[j].setBackground( Color.WHITE );
        }

        chips1.add( value[0] );
        chips2.add( value[1] );
        chips3.add( value[2] );
        chips4.add( value[3] );
        for ( int x = 0; x < b.length; x++ )
        {
            panel.add( b[x] );
            panel.add( new JLabel( " " ) );
            panel.add( new JLabel( " " ) );
        }
        for ( int j = 0; j < b.length; j++ )
        {
            b[j].add( name[j] );
        }
        b[0].add( chips1 );
        b[1].add( chips2 );
        b[2].add( chips3 );
        b[3].add( chips4 );
        hand = new Box[4];
        for ( int j = 0; j < hand.length; j++ )
        {
            hand[j] = new Box( BoxLayout.X_AXIS );
            b[j].add( hand[j] );
        }
        hands = new JLabel[4][12];
        for ( int j = 0; j < hands.length; j++ )
        {
            for ( int k = 0; k < hands[j].length; k++ )
            {
                hands[j][k] = new JLabel();
                hand[j].add( hands[j][k] );
            }
        }
        setVisible( true );
        int x = (int)( Math.random() * 500 );
        int y = (int)( Math.random() * 300 );
        setBounds( 1100, 0, 500, 280 );
        setVisible( true );
        this.setSize( 500, 800 );
        this.add( panel );
        this.setResizable( false );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
        this.setBackground( Color.GREEN );

    }


    @Override
    public void actionPerformed( ActionEvent arg0 )
    {

    }


    /**
     * Updates the display, while showing only the user's cards and the dealer's displayed cards.
     */
    public void update()
    {
        System.out.println("update");
        Player[] array = game.getPlayers();
        for ( int j = 0; j < array.length; j++ )
        {
            if (array[j].getName().equals( dealer ))
            {
                name[j].setText( array[j].getName() + " (Dealer)" );
            }
            else
            {
                name[j].setText( array[j].getName() );
            }
            value[j].setText( array[j].getChips() + "" );
            for (int k = 2; k < hands[j].length; k++)
            {
                hands[j][k].setIcon( new ImageIcon() );
            }
            for ( int k = 0; k < hands[j].length
                && k < array[j].getHand().size(); k++ )
            {

                if ( j == 0 )
                {
                    hands[j][k].setIcon( cards.get( array[j].getHand()
                        .get( k )
                        .toString() ) );
                }
                else
                {
                    
                    if (array[j].getName().equals( dealer )&& k > 0)
                    {
                        hands[j][k].setIcon( cards.get( array[j].getHand()
                            .get( k )
                            .toString() ) );
                    }
                    else
                    {
                        hands[j][k].setIcon( cards.get( "back" ) );
                    }
                }
                

            }

        }
    }


    /**
     * Updates the name of the dealer. This is used in conjunction with the other update methods.
     * @param name The name of the dealer.
     */
    public void updateDealer(String name)
    {
        dealer = name;
    }
    /**
     * Updates the display with all cards shown.
     */
    public void updateAll()
    {
        System.out.println("All");
        
        Player[] array = game.getPlayers();
        for ( int j = 0; j < array.length; j++ )
        {
            if (array[j].getName().equals( dealer ))
            {
                name[j].setText( array[j].getName() + " (Dealer)" );
            }
            else
            {
                name[j].setText( array[j].getName() );
            }
            value[j].setText( array[j].getChips() + "" );
            
            for ( int k = 0; k < hands[j].length
                && k < array[j].getHand().size(); k++ )
            {

                hands[j][k].setIcon( cards.get( array[j].getHand()
                    .get( k )
                    .toString() ) );

            }

        }
    }


    /**
     * Updates the display with no cards shown.
     */
    public void hideCards()
    {
        Player[] array = game.getPlayers();
        for ( int j = 0; j < array.length; j++ )
        {
            if (array[j].getName().equals( dealer ))
            {
                name[j].setText( array[j].getName() + " (Dealer)" );
            }
            else
            {
                name[j].setText( array[j].getName() );
            }
            value[j].setText( array[j].getChips() + "" );
            for (int k = 2; k < hands[j].length; k++)
            {
                hands[j][k].setIcon( new ImageIcon() );
            }
            for ( int k = 0; k < hands[j].length
                && k < array[j].getHand().size(); k++ )
            {

                hands[j][k].setIcon( cards.get( "back" ) );

            }

        }
    }

}
