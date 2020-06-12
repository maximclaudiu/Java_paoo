package Objects;
import mechanics.GameObject;
import mechanics.Handler;
import mechanics.ID;

import java.awt.*;
import java.util.LinkedList;

import static Assets.Images.*;

public class Tile extends GameObject {
    private int hp;
    private Handler handler;
    private boolean hit=false;
    public Tile(int x, int y, int hp,Handler h, ID id){
        super(x,y,id);
        this.hp=hp;
        handler=h;
    }
    public void tick(LinkedList<GameObject> object) {   }
    // Afiseaza pe tabla de joc tile-ul de culoarea corespunzatoare punctelor de viata:
    //          Albastru 1
    //          Rosu     2
    //          Galben   3
    @Override
    public void render(Graphics g, GameObject obj) {
        if (hp==1) {
            g.drawImage(tile_T1,x,y,null);
        }
        else if (hp==2) {
            g.drawImage(tile_T2,x,y,null);
        }
        else if (hp==3) {
            g.drawImage(tile_T3,x,y,null);
        }
    }
    @Override
    public void setx(int x) {    }
    @Override
    public void sety(int y) {    }
    // Creeaza un hitbox al actorului
    @Override
    public Rectangle getBounds() {
        hit=true;
        return new Rectangle(x,y,100,50);
    }
    // In urma coliziunii cu mingea de joc, punctele de viata ale actorilor Tile va scadea cu 1 si jucatorul
    //      va primi puncte de scor, pana cand punctele de viata ajung la 0, iar actorul este distrus.
    public void GetHit() {
        if (hit == true) {
            if (hp == 3) {
                hp = 2;
                handler.Gamescore += 50;
            }
            else if (hp == 2) {
                handler.Gamescore+=50;
                hp = 1;
            }
            else if (hp == 1) {
                hp=0;
                handler.Gamescore+=100;
                this.destroyGameObject(handler);
            }
            hit=false;
        }
    }
}
