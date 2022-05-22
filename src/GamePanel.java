import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.omg.PortableInterceptor.DISCARDING;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, Runnable {

    private int score = 0;

    private Image ground;//地面先暂且做成不变的
    public static int groundY = 400;//地面在坐标系的y坐标
    public static int groundX = 0;//地面在坐标系的x坐标
    public static int groundWidth = 5;//地面图片的宽度

    private long lastObstacleCreated = System.currentTimeMillis();
    private long obstacleCreatedInterval = 1000;//隔多少 毫秒 生成一个障碍物
    private static final int OBSTACLE_NUM = 2;//一共有几种障碍物

    private int gameState;

    private static final int START_MENU = 0;
    private static final int GAMING = 1;
    private static final int END_MENU = 2;

    private Dino dino;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    public GamePanel() {
        gameState = START_MENU;
        ground = Toolkit.getDefaultToolkit().getImage(
                JPanel.class.getResource("/images/Ground.png"));
        dino = new Dino();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (gameState == START_MENU) {
            paintStartMenu(g);
        } else if (gameState == GAMING) {
            paintGround(g);
            paintDino(g);
            paintScore(g);
            paintObstacle(g);
        } else if (gameState == END_MENU) {

        }
    }

    private void paintGround(Graphics g) {
        g.drawImage(ground, groundX, groundY, 1200, 12, this);
        groundX -= Obstacle.MOVE_SPEED;//和障碍物移动速度一致
        if (groundX + 1200 < GameView.FRAME_LENGTH) groundX = 0;
    }

    private void paintDino(Graphics g) {
        int picIndex = dino.state;

        //如果dino在空中，使用的图片和站在地上的是同一张
        if (dino.state == Dino.UP || dino.state == Dino.DOWN) {
            picIndex = Dino.STAND_FULL;
        }
        int picLength = Dino.STAND_LENGTH;
        int picWidth = Dino.STAND_WIDTH;

        //dino蹲下和站立时的图片尺寸是不一样的
        if (dino.state == Dino.BELOW_LEFT_UP || dino.state == Dino.BELOW_RIGHT_UP) {
            picLength = Dino.BELOW_LENGTH;
            picWidth = Dino.BELOW_WIDTH;
        }

        //state同时是所使用的的图片的下标
        g.drawImage(Dino.images[picIndex], dino.x, dino.y, picLength, picWidth, this);
    }

    private void paintScore(Graphics g) {
        g.setColor(Color.black);

        g.setFont(new Font("黑体", Font.BOLD, 25));
        g.drawString(score + "", GameView.FRAME_LENGTH - 100, 50);
    }

    private void paintObstacle(Graphics g) {

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);

            if (obstacle.getX() <= 0) {
                obstacles.remove(i);
                i--;//使用索引删除元素的时候要注意
                continue;
            }

            Image image = obstacle.getImage();
            g.drawImage(image, obstacle.getX(), obstacle.getY(),
                    obstacle.getLength(), obstacle.getWidth(), this);
            obstacle.moveLeft();
        }
    }

    private void paintStartMenu(Graphics g) {
        g.setColor(Color.black);

        g.setFont(new Font("黑体", Font.BOLD, 50));
        g.drawString("Google Dino", GameView.FRAME_LENGTH / 4, GameView.FRAME_WIDTH / 3);
        g.setFont(new Font("黑体", Font.BOLD, 25));

        g.drawString("press space to play", GameView.FRAME_LENGTH / 4, GameView.FRAME_WIDTH / 2);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下按键发生的事件
    @Override
    public void keyPressed(KeyEvent e) {

        if (gameState == GAMING) {

            //dino 在空中时不允许任何操作
            if (dino.jumping) {
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                //dino下蹲

                dino.setState(Dino.BELOW_LEFT_UP);
                dino.y = Dino.BELOW_Y;
            } else if (e.getKeyCode() == KeyEvent.VK_UP
                    || e.getKeyCode() == KeyEvent.VK_SPACE) {
                //dino起跳
                dino.setState(Dino.UP);
            }
        } else if (gameState == START_MENU) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameState = GAMING;
            }
        }
//        repaint();
    }

    //释放按键发生的事件
    @Override
    public void keyReleased(KeyEvent e) {
        if (gameState == GAMING) {

            //dino 在空中时不允许任何操作
            if (dino.jumping) {
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                //dino不再蹲下
                dino.setState(Dino.LEFT_UP);
                dino.y = Dino.STAND_Y;

            }
        }
//        repaint();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (gameState == GAMING) {
                createObstacle();
                dino.walk();
                dino.jump();
                score++;
            }

            repaint();
            if (gameState == END_MENU) break;
        }
    }


    public void createObstacle() {
        if (System.currentTimeMillis() < lastObstacleCreated + obstacleCreatedInterval) {
            return;
        }
        lastObstacleCreated = System.currentTimeMillis();

        //返回[0, 1)的随机数
        double choice = Math.random();

        if (choice < 1 / (double) OBSTACLE_NUM) {
            obstacles.add(new Plant(0));
        } else if (choice < 2 / (double) OBSTACLE_NUM) {
            obstacles.add(new Plant(1));
        }


    }
}
