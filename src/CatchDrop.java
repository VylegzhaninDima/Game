import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

public class CatchDrop extends JFrame{
    private JFrame mainWindow;
    private JPanel panel;
    private JLabel labelScore;
    private static Image background;
    private static Image backgroundMenu;
    private static Image drop;
    private static Image gameover;
    private static long last_frame_time;
    private static float drop_left=200;
    private static float drop_top=-100;
    private static float drop_v = 200;
    private static int score=0;

    public enum STATES{
        MENU,PLAY
    }
    public static STATES state=STATES.MENU;


    public static void main(String[] args) {
        if(state.equals(STATES.PLAY))
            new CatchDrop(STATES.PLAY);
        if(state.equals(STATES.MENU))
            new CatchDrop(STATES.MENU);
    }



    protected CatchDrop(STATES state) {
        getImage();
        initPanel();
        initFrame();
    }

    private void initFrame(){
        mainWindow=new JFrame();
        mainWindow.setTitle("Catch a Drop");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800,600);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.setLocationRelativeTo(null);//позиция в центре
        last_frame_time=System.nanoTime();
        mainWindow.add(panel);
        pack();
    }
    private void initPanel(){
        panel=new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(state.equals(STATES.MENU)){
                    g.drawImage(backgroundMenu,0,0,this);

                }
                if(state.equals(STATES.PLAY)) {
                    g.drawImage(background, 0, 0, this);
                    g.drawImage(drop, (int) drop_left, (int) drop_top, null);
                    g.drawString("Score: " + score, 2, 10);
                    long current_time = System.nanoTime();
                    float delta_time = (current_time - last_frame_time) * 0.000000001f;
                    last_frame_time = current_time;
                    drop_top = drop_top + drop_v * delta_time;
                    if (drop_top > mainWindow.getHeight())
                        g.drawImage(gameover, 100, 100, this);
                    panel.repaint();
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x=e.getX();
                int y=e.getY();
                float drop_right=drop_left+drop.getWidth(null);
                float drop_bottom=drop_top+drop.getHeight(null);

                if(x>=drop_left && x<=drop_right && y>=drop_top && y<=drop_bottom){
                    drop_top=-100;
                    Random rnd = new Random();
                    drop_left=rnd.nextInt(mainWindow.getWidth()-drop.getWidth(null));
                    drop_v=drop_v+20;
                    score++;
                }
            }
        });

       // panel.setSize(800,600);
        add(panel);
    }

    private void getImage(){
        try {
            background=ImageIO.read(CatchDrop.class.getResourceAsStream("bg.jpg"));
            drop= ImageIO.read(CatchDrop.class.getResourceAsStream("drop.png"));
            gameover=ImageIO.read(CatchDrop.class.getResourceAsStream("gameover.png"));
            backgroundMenu=ImageIO.read(CatchDrop.class.getResourceAsStream("bgMenu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



