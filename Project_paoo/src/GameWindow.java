import mechanics.STATE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends Canvas implements ActionListener{
    public JFrame  wndFrame;
    private String  wndTitle;
    private int     wndWidth;
    private int     wndHeight;
    private STATE state;
    private Canvas  canvas;
    private JPanel panel;
    private JButton button;

    public GameWindow (int Width, int Height, String Title)
    {
        wndTitle    = Title;
        wndWidth    = Width;
        wndHeight   = Height;
        wndFrame    = null;
        state=STATE.Menu;
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
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(wndWidth, wndHeight));
        wndFrame.add(canvas);
        if (state == STATE.Menu) {
            panel = new JPanel();
            button = new JButton("play");
            button.addActionListener(this);
            panel.add(button);
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
        wndFrame.remove(panel);
        state=STATE.Game;
    }
}
