import mechanics.Handler;
import mechanics.STATE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameWindow extends Canvas implements ActionListener{
    public JFrame  wndFrame;
    private String  wndTitle = "Space Pong";
    private int     wndWidth = 1014;
    private int     wndHeight = 720;
    private STATE state;
    private Canvas  canvas;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel mainpanel;
    private JButton play;
    private JButton space;
    private JButton space2;
    private JButton text;
    private JButton Highscore;
    private JButton space3;
    private JButton exit;
    private Graphics G;
    private Handler handler;

    public GameWindow ( STATE state)
    {
        wndFrame    = null;
        this.state  = state;
    }
    public void BuildGameWindow(Handler handler)  {
        if (wndFrame != null) {
            return;
        }
        this.handler=handler;
        wndFrame = new JFrame(wndTitle);
        wndFrame.setSize(wndWidth, wndHeight);
        wndFrame.setResizable(false);
        wndFrame.setLocationRelativeTo(null);
        wndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wndFrame.setVisible(true);
        wndFrame.setFocusable(true);
        if (state == STATE.Menu) {
            panel1 = new JPanel();
            panel2 = new JPanel();
            mainpanel = new JPanel();
            mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.X_AXIS));
            panel1.setLayout(new GridLayout(5, 1));
            panel2.setLayout(new GridLayout(1, 1));
            panel1.setBorder(BorderFactory.createEmptyBorder(200, 100, 100, 300));
            panel2.setBorder(BorderFactory.createEmptyBorder(150, 0, 100, 100));
            // Butoanele din meniu
            play = new JButton("Play");
            Highscore = new JButton("Highscore");
            exit =new JButton("Exit");
            space = new JButton("");
            String string="<html>HighScores<br>";
            ResultSet rs =handler.DB.Generate_scores("ORDER BY Score DESC");
            try {
                int i=1;
                while(rs.next()){
                    string= string +"<br> <br>Locul "+i+":  "+ rs.getInt("Score");
                    i++;
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
            text = new JButton(string);
            text.setVisible(false);
            space2 = new JButton("");
            space3 = new JButton("");
            space.setVisible(false);
            space2.setVisible(false);
            space3.setVisible(false);
            play.setPreferredSize(new Dimension(140, 140));
            exit.setPreferredSize(new Dimension(140, 140));
            text.setPreferredSize(new Dimension(300, 500));
            play.addActionListener(this);
            Highscore.addActionListener(x-> {
                if(text.isVisible()==true)
                    text.setVisible(false);
                else if (text.isVisible()==false)
                    text.setVisible(true);
            });
            exit.addActionListener(x ->{System.exit(1);}); //Butonul de exit ne scoate din aplicatie

            panel1.add(play);
            panel1.add(space);
            panel1.add(Highscore);
            panel1.add(space2);
            panel1.add(exit);
            panel2.add(space3);
            panel2.add(text);
            mainpanel.add(panel1);
            mainpanel.add(panel2);
            wndFrame.add(mainpanel, BorderLayout.CENTER);
            wndFrame.setVisible(true);
            wndFrame.pack();
            wndFrame.setSize(wndWidth, wndHeight);
        }
    }
    public int GetWndWidth() { return wndWidth; }
    public int GetWndHeight() { return wndHeight; }
    public Canvas GetCanvas() { return canvas; }
    public JFrame GetWndFrame() { return wndFrame; }
    public STATE setState (){
        return state;
    }
    // Butonul de play da drumul la joc
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        mainpanel.setVisible(false);
        wndFrame.remove(mainpanel);
        state=STATE.Game;
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(wndWidth, wndHeight));
        wndFrame.add(canvas);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
