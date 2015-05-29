package aldz_Blackjack;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StatusWindow extends JFrame implements ActionListener
{

    
    private Box[] b;
    private Box b1;
    private Box b2;
    private Box b3;
    private Box b4;
    private JLabel name1;
    private JLabel name2;
    private JLabel name3;
    private JLabel name4;
    private Box chips1;
    private Box chips2;
    private Box chips3;
    private Box chips4;
    private JTextField[] value;
    private Box[] hand;
    Blackjack game;
    private HashMap<String, ImageIcon> cards;
    
    public StatusWindow(Blackjack togame)
    {
        super("StatusWindow");
        game = togame;
        cards = new HashMap<String, ImageIcon>();
        cards.put( "back", new ImageIcon("Cards/b1fv.png") );

        for (int j = 1; j <= 13; j++)
        {
            
            cards.put( new Card(j, "Clubs").toString(), new ImageIcon("Cards/c" + j + ".png") );
            cards.put( new Card(j, "Diamonds").toString(), new ImageIcon("Cards/d" + j + ".png") );
            cards.put( new Card(j, "Hearts").toString(), new ImageIcon("Cards/h" + j + ".png") );
            cards.put( new Card(j, "Spades").toString(), new ImageIcon("Cards/s" + j + ".png") );
        }
        JPanel panel = new JPanel();
        BoxLayout grid = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout( grid );
        Player[] array = game.getPlayers();
        b = new Box[4];
        for (int x = 0; x < b.length; x++)
        {
            b[x] = new Box(BoxLayout.Y_AXIS);
        }
        name1 = new JLabel(array[0].getName());
        name2 = new JLabel(array[1].getName());
        name3 = new JLabel(array[2].getName());
        name4 = new JLabel(array[3].getName());
        chips1 = new Box(BoxLayout.X_AXIS);
        chips2 = new Box(BoxLayout.X_AXIS);
        chips3 = new Box(BoxLayout.X_AXIS);
        chips4 = new Box(BoxLayout.X_AXIS);
        chips1.add( new JLabel("Chips:  ") );
        chips2.add( new JLabel("Chips:  ") );
        chips3.add( new JLabel("Chips:  ") );
        chips4.add( new JLabel("Chips:  ") );
        value = new JTextField[4];
        for (int j = 0; j < value.length; j++)
        {
            value[j] = new JTextField(5);
            value[j].setEditable( false );
            value[j].setText( array[j].getChips() + "" );
        }

        chips1.add( value[0] );
        chips2.add( value[1] );
        chips3.add( value[2] );
        chips4.add( value[3] );
        for (int x = 0; x < b.length; x++)
        {
            panel.add( b[x] );
            panel.add( new JLabel(" ") );
            panel.add( new JLabel(" ") );
        }
        b[0].add( name1 );
        b[1].add( name2 );
        b[2].add( name3 );
        b[3].add( name4 );
        b[0].add( chips1 );
        b[1].add( chips2 );
        b[2].add( chips3 );
        b[3].add( chips4 );
        hand = new Box[4];
        for (int j = 0; j < hand.length; j++)
        {
            hand[j] = new Box(BoxLayout.X_AXIS);
            b[j].add( hand[j] );
        }
        setVisible(true);
        int x = (int)( Math.random() * 500 );
        int y = (int)( Math.random() * 300 );
        setBounds( 1000, 0, 500, 280 );
        setVisible( true );
        this.setSize( 500, 800 );
        this.add( panel );
        this.setResizable( false );

        
    }
    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        
        
    }
    
    public void update()
    {
        Player[] array = game.getPlayers();
        for (int j = 0; j < array.length; j++)
        {
            value[j].setText( array[j].getChips() + "" );
            this.repaint();


            for (Card c : array[j].getHand())
            {
                hand[j].add( new JLabel(cards.get( c.toString() )) );
            }
        }
    }

}
