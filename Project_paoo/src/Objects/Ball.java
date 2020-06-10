package Objects;

import mechanics.GameObject;
import mechanics.ID;

import java.awt.*;
import java.util.LinkedList;

import static Assets.Images.*;
public class Ball extends GameObject {
    private int x;
    private int y;
    private int height =26;
    private int width =26;
    public Ball(int x, int y, ID id) {
        super(x, y, id);
        this.y=500;
        this.x=500;
    }
    @Override
    public void tick(LinkedList<GameObject> object) {
        if (x<30) {
            setvelX(0-getvelX());
            x=30;
        }
        else if (x >955) {
            setvelX(0-getvelX());
            x=955;
        }
        else x = x +velX;
        if (y<30) {
            setvelY(0-getvelY());
            y=30;
        }
        else y = y +velY;
        Collision(object);
    }
    @Override
    public void render(Graphics g, GameObject obj) {
        if (obj.getID()==ID.Ball) {
            g.drawImage(Ball,x,y, width, height,null);
        }
    }
    @Override
    public void GetHit() {  }

    public void Collision (LinkedList<GameObject>Obj) {
        int centerX, centerY;
        centerX=x+width/2;
        centerY=y+height/2;
        for (int i=0;i<Obj.size();i++) {
            GameObject temp= Obj.get(i);
            if (temp.getID()==ID.Player) {
                if(getBounds().intersects(temp.getBounds()))
                    setvelY(0-getvelY());
            }
            if(temp.getID()==ID.Tile) {
                if(getBounds().intersects(temp.getBounds())) {
                    if (centerY<=temp.getY()+tileHeight+height/2 && centerX>temp.getX() && centerX<temp.getX()+tileWidth){ //jos
                        setvelY(0-getvelY());
                        temp.GetHit();
                        break ;
                    }
                    else if (centerX<=temp.getX()+tileWidth+width/2 && centerY>temp.getY() && centerY<temp.getY()+tileHeight) { //dreapta
                        setvelX(0 - getvelX());
                        temp.GetHit();
                        break ;
                    }
                    else if(centerY+height/2>=temp.getY() && centerX>temp.getX() && centerX<temp.getX()+tileWidth) { //sus
                        setvelY(0 - getvelY());
                        temp.GetHit();
                        break ;
                    }
                    else if(centerX+width/2>=temp.getX() && centerY>temp.getY() && centerX<temp.getY()+tileHeight) { //stanga
                        setvelX(0 - getvelX());
                        temp.GetHit();
                        break ;
                    }
                }
            }
        }
    }
    public void setx(int x){
        this.x=x;
    }
    public void sety(int y){
        this.y=y;
    }
    public Rectangle getBounds() {
        return new Rectangle(x,y,26,26);
       }

}
