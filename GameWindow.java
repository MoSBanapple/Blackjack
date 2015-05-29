package aldz_Blackjack;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class GameWindow extends JFrame implements ActionListener
{
    private Blackjack game;
    private JButton hButton, sButton, dButton, fButton, bButton, cButton;
    private JTextField bet;
    private JTextArea reporter;
    private JTextField pocket;
    private HashMap<String, ImageIcon> cards;
    private String s;
    public GameWindow(Blackjack toGame)
    {
        super("GameWindow");
        cards = new HashMap<String, ImageIcon>();
        cards.put( "back", new ImageIcon("Cards/b1fv.png") );

        s = "";
        for (int j = 1; j <= 13; j++)
        {
            
            cards.put( new Card(j, "Clubs").toString(), new ImageIcon("Cards/c" + j + ".png") );
            cards.put( new Card(j, "Diamonds").toString(), new ImageIcon("Cards/d" + j + ".png") );
            cards.put( new Card(j, "Hearts").toString(), new ImageIcon("Cards/h" + j + ".png") );
            cards.put( new Card(j, "Spades").toString(), new ImageIcon("Cards/s" + j + ".png") );
        }
       
        
        game = toGame;
        
        JLabel blank = new JLabel("        ");
        
        JPanel panel = new JPanel();
        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout( gbLayout );
        gbc.weightx = 0.5;
        hButton = new JButton("Hit");
        sButton = new JButton("Stand");
        dButton = new JButton("Double Down");
        fButton = new JButton("Surrender");
        cButton = new JButton("Cheat");
        bButton = new JButton("Bet");
        reporter = new JTextArea(30, 50);
        reporter.setEditable( false );
        bet = new JTextField(16);
        bet.setEditable( true );
        gbc.fill = GridBagConstraints.VERTICAL;
        Box betBox = new Box(0);
        betBox.add( bButton );
        betBox.add( bet );
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add( reporter, gbc );
        Box buttonBox = new Box(0);
        buttonBox.add( hButton );
        buttonBox.add( sButton );
        buttonBox.add( dButton );
        buttonBox.add( fButton );
        buttonBox.add( cButton );
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add( buttonBox, gbc );
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add( new JLabel(" "), gbc );
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add( betBox, gbc );
        gbc.gridx = 1;
        gbc.gridy = 0;
        


        
        
        
        
        bButton.addActionListener( this );
        hButton.addActionListener( this );
        sButton.addActionListener( this );
        dButton.addActionListener( this );
        fButton.addActionListener( this );
        cButton.addActionListener( this );
       
        setVisible(true);
        int x = (int)( Math.random() * 500 );
        int y = (int)( Math.random() * 300 );
        setBounds( 0, 0, 500, 280 );
        setVisible( true );
        this.setSize( 1000, 800 );
        this.add( panel );
        this.setResizable( false );
        reporter.setWrapStyleWord( true );
        JScrollPane areaScrollPane = new JScrollPane( reporter );
        areaScrollPane
        .setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        panel.add( areaScrollPane );
        pocket = new JTextField(17);
        Box pocketBox = new Box(BoxLayout.X_AXIS);
        {
            pocketBox.add( new JLabel("Pocket:  ") );
            pocketBox.add( pocket );
        }
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add( new JLabel(" "), gbc );
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add( pocketBox, gbc );
        
    }

    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        AbstractButton button = (AbstractButton)arg0.getSource();
        if (button == bButton)
        {
            game.inputChange( bet.getText() );
        }
        if (button == hButton)
        {
            game.inputChange( "h" );
        }
        if (button == sButton)
        {
            game.inputChange( "s" );
        }
        if (button == dButton)
        {
            game.inputChange( "d" );
        }
        if (button == fButton)
        {
            game.inputChange( "f" );
        }
        if (button == cButton)
        {
            game.inputChange( "cheat" );
        }
        
        
    }
    
    public void setPocket(String card)
    {
        pocket.setText( card );
    }
    
    public void message(String report)
    {
        reporter.append( report + "\n");
        reporter.setCaretPosition(reporter.getDocument().getLength());
        
    }
    
    public JTextArea getReporter()
    {
        return reporter;
    }

}

























