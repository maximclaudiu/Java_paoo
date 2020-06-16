import Assets.*;
import Controls.KeyInput;
import Controls.MySQLite;
import mechanics.MENU;
import mechanics.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Thread{

    private static Game instance =null;
    private boolean runState=false;
    private GameWindow wnd;
    private Thread thread;
    private BufferedImage background=null, level=null;
    private STATE state = STATE.Menu;
    private Handler handler ;
    private KeyInput input;
    private MENU menu;
    private MySQLite DB;
    BufferStrategy bs;
    ImageLoader loader = new ImageLoader();
    Graphics G;

    public Game () {
        wnd = new GameWindow(state);
    }
    // Initializarea instantelor si a texturilor
    private void init() {
        DB =new MySQLite();
        Assets.Images.Init();
        input= new KeyInput();
        handler= new Handler(input, DB);
        wnd.BuildGameWindow(handler);
        wnd.GetWndFrame().addKeyListener(input);
        background = loader.LoadImage("/textures/background.png");
        level =loader.LoadImage("/textures/maps/map_2.png");
        handler.LoadTextures(level);
    }
    public synchronized void StartGame() {
        if(runState == false) {
             runState = true;
             thread = new Thread(this);
             thread.start();
        }
        else { return; }
    }
    public synchronized void StopGame() {
        if(runState == true) {
             runState = false;
            try  {
                thread.join();
            }
            catch(InterruptedException ex) {
                 ex.printStackTrace();
            }
        }
        else { return; }
    }
    public void run() {
        init();
        StartGame();
        long oldTime = System.nanoTime();
        long curentTime;

        final int framesPerSecond   = 60;
        final double timeFrame      = 1000000000 / framesPerSecond;

        while (runState == true) {
            curentTime = System.nanoTime();
            if((curentTime - oldTime) > timeFrame) {
                Update();
                Draw();
                oldTime = curentTime;
            }
        }
    }
    private void Update() {
        if (state==STATE.Menu)
            state=wnd.setState();
        if (state==STATE.Game) {
            handler.tick();
            input.Update();
        }
    }
    // Afiseaza fundalul sau meniul in functie de starea jocului
    private void Draw() {
        if (state==STATE.Game){
            bs = wnd.GetCanvas().getBufferStrategy();
            if (bs == null) {
                try {
                    wnd.GetCanvas().createBufferStrategy(3);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            G =bs.getDrawGraphics() ;
            G.drawImage(background,0,0,null);
            handler.render(G);
            G.dispose();
            bs.show();
        }
        else if(state==STATE.Menu) {
            menu=new MENU(state,G);
        }
    }
    // Metoda statica pentru crearea instantei Game
    public static Game getInstance(){
        if (instance==null){
            instance = new Game();
        }
        return instance;
    }
    public static void main(String[] args) {
        Game paooGame = Game.getInstance();
        paooGame.StartGame();
    }
}
