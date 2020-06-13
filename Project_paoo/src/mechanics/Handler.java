package mechanics;

import Assets.ImageLoader;
import Controls.KeyInput;
import Controls.MySQLite;
import Objects.Ball;
import Objects.GameObject;
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
    public MySQLite DB;
    public Handler (KeyInput key, MySQLite DB){
        this.key=key;
        this.addObject(new Player(150,22,ID.Player));
        this.addObject(new Ball(16,16,ID.Ball));
        this.DB = DB;
    }
    public void tick() {
        if (gameover) {
            pause();
            DB.Update(Gamescore);
            System.exit(1);
        }
        if (object.size()==2)
        {
            if(lvl_nr<4) {
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
        if (gameover) //Testarea si afisarea mesajului de final de joc.
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
        // Afiseaza pe rand pe ecran toate obiectele din lista
        for (int i=0;i<object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.render(g,tempObject);
        }
        if(object.size()==2) { // Testarea si afisarea mesajului de nivel complet.
            if (lvl_nr<4) {
                Graphics G= g;
                G.setColor(Color.WHITE);
                G.setFont(new Font("serif",Font.BOLD,35));
                G.drawString("Nivel Complet",400,300);
                G.dispose();
            }
            else { //Testarea si afisarea mesajului de joc castigat.
                Graphics G= g;
                G.setColor(Color.WHITE);
                G.setFont(new Font("serif",Font.BOLD,35));
                G.drawString("Ai Castigat",400,300);
                G.dispose();
            }
        }
        // Afiseaza Score-ul jocului
        Graphics2D G= (Graphics2D)g;
        G.setColor(Color.WHITE);
        G.setFont(new Font("serif",Font.BOLD,25));
        G.drawString("Score:"+Gamescore,850,20);
        G.dispose();
    }
    public void LoadTextures(BufferedImage level){
        // Incarcarea pozei nivelului curent.
        this.level=level;
        int w= level.getWidth();
        int h= level.getHeight();
        // Pentru fiecare pixel din imagine, in functie de valorile RGB ale acestuia, se va crea o instanta de Tile
        //      cu valori diferite ale punctelor de viata:
        //          Albastru 1
        //          Rosu     2
        //          Galben   3
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
    // Opreste temporar firul de executie pentru a da timp jucatorului sa citeasca diferitele mesaje
    //      ce apar pe ecran, in functie de ce conditii se indeplinesc
    public void pause(){
        try {
            Thread.sleep(4000);
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // Adauga o instanta (Tile, Player sau Ball) la lista de GameObject-uri
    public void addObject (GameObject obj){
        this.object.add(obj);
    }
    public void removeObject(GameObject obj){
        this.object.remove(obj);
    }
}
