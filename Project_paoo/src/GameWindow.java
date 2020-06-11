import mechanics.STATE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends Canvas implements ActionListener{
    public JFrame  wndFrame;
    private String  wndTitle = "Space Pong";
    private int     wndWidth = 1014;
    private int     wndHeight = 720;
    private STATE state;
    private Canvas  canvas;
    private JPanel panel;
    private JButton play;
    private JButton exit;

    public GameWindow ( STATE state)
    {
        wndFrame    = null;
        this.state  = state;
    }
    public void BuildGameWindow()  {
        if (wndFrame != null) {
            return;
        }
        wndFrame = new JFrame(wndTitle);
        wndFrame.setSize(wndWidth, wndHeight);
        wndFrame.setResizable(false);
        wndFrame.setLocationRelativeTo(null);
        wndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wndFrame.setVisible(true);
        wndFrame.setFocusable(true);

        if (state == STATE.Menu) {
            panel = new JPanel();
            play = new JButton("Play");
            exit =new JButton("Exit");
            play.addActionListener(this);
            exit.addActionListener(x ->{System.exit(1);});
            panel.add(play);
            panel.add(exit);
            panel.setBorder(BorderFactory.createEmptyBorder(100, 400, 200, 400));
            panel.setLayout(new GridLayout(3, 1));
            wndFrame.add(panel, BorderLayout.CENTER);
            wndFrame.setVisible(true);
        }
    }
    public int GetWndWidth() { return wndWidth; }
    public int GetWndHeight() { return wndHeight; }
    public Canvas GetCanvas() { return canvas; }
    public JFrame GetWndFrame() { return wndFrame; }
    public STATE setState (){
        return state;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        panel.setVisible(false);
        wndFrame.remove(panel);
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
