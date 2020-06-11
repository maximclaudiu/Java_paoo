package mechanics;

import Assets.ImageLoader;
import Controls.KeyInput;
import Objects.Ball;
import Objects.Player;
import Objects.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler{
    private boolean gameover =false;
    private int lvl_nr=1;
    private KeyInput key;
    public int Gamescore=0;
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private BufferedImage level;
    public Handler (KeyInput key){
        this.key=key;
        this.addObject(new Player(150,22,ID.Player));
        this.addObject(new Ball(16,16,ID.Ball));
    }
    public void tick(){
        if (gameover){
            pause();
            System.exit(1);
        }
        if (object.size()==2)
        {
            if(lvl_nr<4){
                lvl_nr++;
                pause();
            }
            else {
                pause();
                System.exit(1);
            }
            ImageLoader loader = new ImageLoader();
            level=loader.LoadImage("/textures/maps/map_"+lvl_nr+".png");
            LoadTextures(level);
            tempObject= object.get(1);
            tempObject.sety(500);
            tempObject.setx(500);
            tempObject=object.get(0);
            tempObject.setx(440);
        }
        for (int i=0;i<object.size();i++) {
            tempObject = object.get(i);
            if(tempObject.getID()==ID.Player) {
                if (key.left) {
                    tempObject.setvelX(-5);
                }
                else if (key.right) tempObject.setvelX(5);
                else tempObject.setvelX(0);
            }
            if (tempObject.getID()==ID.Ball){
                if (tempObject.getY()>720){
                    gameover=true;
                }
            }
            tempObject.tick(object);
        }
    }
    public void render(Graphics g) {
        if (gameover)
        {
            for (int i=0;i<object.size();i++) {
                tempObject = object.get(i);
                tempObject.destroyGameObject(this);
            }
            Graphics G= g;
            G.setColor(Color.WHITE);
            G.setFont(new Font("serif",Font.BOLD,35));
            G.drawString("Game Over",400,300);
            G.dispose();
        }
        for (int i=0;i<object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.render(g,tempObject);
        }
        if(object.size()==2) {
            if (lvl_nr<4) {
                Graphics G= g;
                G.setColor(Color.WHITE);
                G.setFont(new Font("serif",Font.BOLD,35));
                G.drawString("Nivel Complet",400,300);
                G.dispose();
            }
            else {
                Graphics G= g;
                G.setColor(Color.WHITE);
                G.setFont(new Font("serif",Font.BOLD,35));
                G.drawString("Ai Castigat",400,300);
                G.dispose();
            }
        }
        Graphics2D G= (Graphics2D)g;
        G.setColor(Color.WHITE);
        G.setFont(new Font("serif",Font.BOLD,25));
        G.drawString("Score:"+Gamescore,850,25);
        G.dispose();
    }
    public void LoadTextures(BufferedImage level){
        this.level=level;
        int w= level.getWidth();
        int h= level.getHeight();
        for (int i=0;i<w;i++){
            for (int j=0;j<h;j++){
                int pixel=level.getRGB(i,j);
                int red=(pixel >>16)& 0xff;
                int green=(pixel >>8)& 0xff;
                int blue=(pixel)& 0xff;
                if (blue==255 && green == 255 && red==0) this.addObject(new Tile(i*51,j*51,1,this,ID.Tile));
                else if (red==255 && green==0 && blue==0) this.addObject(new Tile(i*51,j*51,2,this,ID.Tile));
                else if (red==255 && green == 255 &&blue==0) this.addObject(new Tile(i*51,j*51,3,this,ID.Tile));
            }
        }
    }
    public void pause(){
        try {
            Thread.sleep(4000);
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void addObject (GameObject obj){
        this.object.add(obj);
    }
    public void removeObject(GameObject obj){
        this.object.remove(obj);
    }
}
