import javax.swing.*;
import java.awt.*;

/**
 * 游戏中的仙人掌
 */
public class Cactus implements Obstacle {

    public static Image[] images;

    public static final int IMAGE_NUM = 2;

    //仙人掌图片的宽度相同，但长度不同
    public static int picWidth = 48;
    public static int[] picLength = {25, 51};

    //将图片加载入内存
    static {
        images = new Image[IMAGE_NUM];
        images[0] = readImage("/images/Cactus-1.png");
        images[1] = readImage("/images/Cactus-2.png");
    }

    //工具函数
    public static Image readImage(String fileName) {
        return Toolkit.getDefaultToolkit().getImage(
                JPanel.class.getResource(fileName));
    }

    public int x;
    public int y;

    //type == 0 || type == 1
    public int type;

    public Cactus(int type) {
        this.type = type;
        y = GamePanel.groundY + GamePanel.groundWidth - picWidth;
        x = GameView.FRAME_LENGTH;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getLength() {
        return picLength[type];
    }

    @Override
    public int getWidth() {
        return picWidth;
    }

    @Override
    public Image getImage() {
        return images[type];
    }

    @Override
    public void moveLeft() {
        x -= Obstacle.MOVE_SPEED;
    }
}
