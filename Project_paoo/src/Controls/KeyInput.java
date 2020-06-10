package Controls;

import mechanics.Handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{

    private boolean[] keys;
    public boolean left;
    public boolean right;
    public KeyInput()
    {
        keys = new boolean[256];
    }
    public void Update()
    {    }
    @Override
    public void keyPressed(KeyEvent Event) {

        keys[Event.getKeyCode()] = true;
        if (Event.getKeyCode()==Event.VK_A||Event.getKeyCode()==Event.VK_LEFT) {
            left = true;
        }
        else left=false;
        if (Event.getKeyCode()==Event.VK_D||Event.getKeyCode()==Event.VK_RIGHT)
            right=true;
        else right=false;
        if(Event.getKeyCode()==KeyEvent.VK_ESCAPE)
            System.exit(1);
    }
    @Override
    public void keyReleased(KeyEvent Event) {
        keys[Event.getKeyCode()] = false;
        if (Event.getKeyCode()==Event.VK_A||Event.getKeyCode()==Event.VK_LEFT)
            left = false;
        if (Event.getKeyCode()==Event.VK_D||Event.getKeyCode()==Event.VK_RIGHT)
            right=false;
    }
    @Override
    public void keyTyped(KeyEvent Event) {

    }

}
