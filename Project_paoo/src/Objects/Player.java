package Objects;

import mechanics.GameObject;
import mechanics.ID;

import java.awt.*;
import java.util.LinkedList;
import static Assets.Images.*;

public class Player extends GameObject {
    public Player(int x, int y, ID id){
        super(x,y,id);
        this.x=440;
        this.y=650;
    }
    @Override
    public void tick(LinkedList<GameObject> obj) {
        if (x<33) {
            setvelX(0);
            x=33;
        }
        else if (x >820) {
            setvelX(0);
            x=820;
        }
        else x = x +velX;
    }
    @Override
    public void render(Graphics g, GameObject obj) {
        if (obj.getID()==ID.Player) {
            g.drawImage(player,x,y, playerWidth, playerHeight,null);
        }
    }
    @Override
    public void setx(int x) { this.x=x; }
    @Override
    public void sety(int y) { this.y=y; }
    @Override
    public void GetHit() {    }
    @Override
    public Rectangle getBounds(){
        return new Rectangle(x,y,150,22);
    }


}
