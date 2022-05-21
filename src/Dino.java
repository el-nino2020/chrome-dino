import javax.jnlp.DownloadService;
import javax.swing.*;
import java.awt.*;

/**
 * 游戏中的小恐龙
 */
public class Dino {
    public static Image[] images;
    public static final int IMAGE_NUM = 3;


    public static final int STAND_LENGTH = 42;//下面三个状态(dino站立时)图片的尺寸
    public static final int STAND_WIDTH = 45;

    public static final int STAND_FULL = 0;//双脚在地
    public static final int LEFT_UP = 1; //左脚在空中
    public static final int RIGHT_UP = 2;//右脚在空中

    public static final int BELOW_LENGTH = 57;//下面这两个状态(dino蹲下)图片的尺寸
    public static final int BELOW_WIDTH = 28;

    public static final int BELOW_LEFT_UP = 3; //左脚在空中，且蹲下
    public static final int BELOW_RIGHT_UP = 4; //右脚在空中，且蹲下


    public static final int UP = 5;//上升
    public static final int DOWN = 6;//下降

    /**
     * 能起跳的最大高度
     */
    public static final int JUMP_HEIGHT = 96;


    //dino图片的左下角的坐标 (x, y)
    int x;//x没用，因为dino只在竖直方向跳跃、下潜
    int y;


    int length;//dino图片向x轴的投影的长度
    int width;//dino图片向y轴的投影的长度

    public int state = LEFT_UP;
    public int jumpSpeed = 10;

    public Dino(int x, int y) {
        this.x = x;
        this.y = y;

    }

    //将图片加载入内存
    static {
        images = new Image[IMAGE_NUM];
        images[0] = readImage("/images/Dino-stand.png");
        images[1] = readImage("/images/Dino-left-up.png");
        images[2] = readImage("/images/Dino-right-up.png");
    }

    //工具函数
    public static Image readImage(String fileName) {
        return Toolkit.getDefaultToolkit().getImage(
                JPanel.class.getResource(fileName));
    }

    public void jump() {
        //检查是否处于空中
        if (!(state == UP || state == DOWN))
            return;

        if (state == DOWN) {
            if (y >= GamePanel.groundY - 40) {
                state = LEFT_UP;
            } else {
                y += jumpSpeed;
            }
        } else {//state == UP
            if (y <= GamePanel.groundY - 40 - JUMP_HEIGHT) {
                state = DOWN;
            } else {
                y -= jumpSpeed;
            }
        }
    }

    public void walk() {
        if (state == UP || state == DOWN) {
            return;
        }
        if (state == LEFT_UP) {
            state = RIGHT_UP;
        } else {
            state = LEFT_UP;
        }
    }


    public void setState(int state) {
        this.state = state;
    }

}
