package aldz_Blackjack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NameWindow extends JFrame implements ActionListener
{

    private JLabel name;
    private JPanel panel;
    private JTextField input;
    private Blackjack game;
    

    public NameWindow (Blackjack toGame)
    {
        super("Blackjack");
        game = toGame;
        panel = new JPanel();
        name = new JLabel("Please enter your name:  ");
        input = new JTextField(15);
        panel.add( name );
        panel.add( input );
        input.setEditable( true );
        this.add( panel );
        input.addActionListener( this );
        
        this.setBounds( 400, 400, 400, 400 );
        this.setSize( 200, 100 );
        setVisible( true );
        
    }
    
    
    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        game.inputChange( input.getText() );
        this.dispose();
        
    }

}




//package aldz_Blackjack;
//public class NameWindow
//{
//    private Blackjack game;
//    public NameWindow(Blackjack toGame)
//    {
//        game = toGame;
//        game.inputChange( "Test" );
//    }
//    
//}
