import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener, Runnable {

    private int score = 0;

    private Image ground;//地面先暂且做成不变的
    public static int groundY = 400;

    private Dino dino;

    public GamePanel() {
        ground = Toolkit.getDefaultToolkit().getImage(
                JPanel.class.getResource("/images/Ground.png"));
        dino = new Dino(20, groundY - 40);

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

        g.drawImage(Dino.images[picIndex], dino.x, dino.y, Dino.STAND_LENGTH, Dino.STAND_WIDTH, this);
    }

    private void paintScore(Graphics g) {
        g.setColor(Color.black);

        g.setFont(new Font("黑体", Font.BOLD, 25));
        g.drawString(score + "", GameView.FRAME_LENGTH - 100, 50);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {

        } else if (e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_SPACE) {
            //dino起跳
            dino.setState(Dino.UP);
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_SPACE) {

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
