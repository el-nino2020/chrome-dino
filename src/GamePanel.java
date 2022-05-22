import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.omg.PortableInterceptor.DISCARDING;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener, Runnable {

    private int score = 0;

    private Image ground;//地面先暂且做成不变的
    public static int groundY = 400;//地面在坐标系的y坐标
    public static int groundWidth = 5;//地面图片的宽度

    private Dino dino;

    public GamePanel() {
        ground = Toolkit.getDefaultToolkit().getImage(
                JPanel.class.getResource("/images/Ground.png"));
        dino = new Dino();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        paintGround(g);
        paintDino(g);
        paintScore(g);

    }

    private void paintGround(Graphics g) {
        g.drawImage(ground, 0, groundY, 1200, 12, this);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下按键发生的事件
    @Override
    public void keyPressed(KeyEvent e) {
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
        repaint();
    }

    //释放按键发生的事件
    @Override
    public void keyReleased(KeyEvent e) {
        //dino 在空中时不允许任何操作
        if (dino.jumping) {
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            //dino不再蹲下
            dino.setState(Dino.LEFT_UP);
            dino.y = Dino.STAND_Y;

        }
        repaint();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            dino.walk();
            dino.jump();
            score++;

            repaint();
        }
    }
}
