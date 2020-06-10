import Assets.*;
import Controls.KeyInput;
import Objects.MENU;
import mechanics.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Thread{

    private boolean runState=false;
    private GameWindow wnd;
    private Thread thread;
    private BufferedImage background=null, level=null, shadow=null;
    private STATE state ;
    private Handler handler ;
    private KeyInput input;
    private MENU menu;
    BufferStrategy bs;
    ImageLoader loader = new ImageLoader();
    Graphics G;
    public Game (String Title,int Width, int Height) {
        wnd = new GameWindow(Width, Height,Title );
    }
    private void init() {
        Assets.Images.Init();
        input= new KeyInput();
        handler= new Handler(input);
        state=STATE.Menu;
        wnd.BuildGameWindow();
        wnd.GetWndFrame().addKeyListener(input);
        background = loader.LoadImage("/textures/background.png");
        shadow =loader.LoadImage("/textures/shadow.png");
        level =loader.LoadImage("/textures/maps/map_1.png");
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
    // will make an exit button
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
    private void Update(){
        if (state==STATE.Menu)
            state=wnd.setState();
        if(state==STATE.Game){
            handler.tick();
            input.Update();
        }
    }
    private void Draw() {
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
        int margin=30;
        G.drawImage(background,0,0,null);
        G.drawImage(shadow,margin,margin,wnd.GetWndWidth()-(margin*2),wnd.GetWndHeight()-(margin*2),null);
        if (state==STATE.Game)
            handler.render(G);
        else if(state==STATE.Menu){
            menu=new MENU(state,G);
        }
        G.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game paooGame = new Game("PaooGame", 1014, 720);
        paooGame.StartGame();
    }
}
