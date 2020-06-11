package Objects;

import mechanics.STATE;

import java.awt.*;

public class MENU {
    public MENU (STATE state, Graphics G){
        if (state==STATE.Menu);
          //  MenuRender(G);
    }
    public void MenuRender(Graphics g){

        Font fnt = new Font ("arial", Font.BOLD,50);
        g.setFont(fnt);
        g.setColor(Color.WHITE);
        g.drawString("Space Pong",380,100);

    }
}
