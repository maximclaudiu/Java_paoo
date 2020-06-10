package mechanics;

import java.util.Random;
import java.awt.*;
import java.util.LinkedList;

public abstract class GameObject {
    protected ID id;
    protected int x,y;
    protected int velX;
    protected int velY;
    Random rand=new Random();

    public GameObject (int x, int y, ID id){
        this.x=x;
        this.y=y;
        this.velX=0;
        this.velY=0;
        if(id==ID.Ball){
            while(velX>=-2&&velX<=2)
                velX=rand.nextInt(8)-4;
            while(velY>=-2&&velY<=2)
                velY=rand.nextInt(8)-4;
        }
        this.id=id;
    }
    public void destroyGameObject(Handler hand){
        x=0;
        y=0;
        id=null;
        hand.removeObject(this);
    }
    public abstract void tick(LinkedList<GameObject> object);
    public abstract void render (Graphics g, GameObject obj);

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public abstract void setx (int x);
    public abstract void sety (int y);
    public int getvelX(){
        return this.velX;
    }
    public int getvelY(){
        return this.velY;
    }
    public void setvelX (int x){
        this.velX=x;
    }
    public void setvelY (int y){
        this.velY=y;
    }
    public ID getID(){
        return this.id;
    }
    public abstract void GetHit();

    public abstract Rectangle getBounds();

}
