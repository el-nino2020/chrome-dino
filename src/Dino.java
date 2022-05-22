import javax.jnlp.DownloadService;
import javax.swing.*;
import java.awt.*;

/**
 * 游戏中的小恐龙
 */
public class Dino {
    public static Image[] images;


    public static final int STAND_LENGTH = 42;//dino站立时图片的尺寸
    public static final int STAND_WIDTH = 45;

    public static final int BELOW_LENGTH = 57;//dino蹲下时图片的尺寸
    public static final int BELOW_WIDTH = 28;


    public static final int STAND_FULL = 0;//双脚在地
    public static final int LEFT_UP = 1; //左脚在空中
    public static final int RIGHT_UP = 2;//右脚在空中
    public static final int BELOW_LEFT_UP = 3; //左脚在空中，且蹲下
    public static final int BELOW_RIGHT_UP = 4; //右脚在空中，且蹲下


    public static final int UP = 5;//上升
    public static final int DOWN = 6;//下降

    /**
     * 能起跳的最大高度
     */
    public static final int JUMP_HEIGHT = 96;


    //dino图片的左上角的坐标 (x, y)
    int x = 20;//x不会变，因为dino只在竖直方向跳跃、下潜
    int y = STAND_Y;

    public static final int STAND_Y = GamePanel.groundY - (Dino.STAND_WIDTH - GamePanel.groundWidth);
    public static final int BELOW_Y = GamePanel.groundY - (Dino.BELOW_WIDTH - GamePanel.groundWidth);

    public int state = LEFT_UP;
    public int jumpSpeed = 10;
    public boolean jumping = false;//在空中的时候不能再次跳


    public static final int IMAGE_NUM = 5;

    //将图片加载入内存
    static {
        images = new Image[IMAGE_NUM];
        images[0] = readImage("/images/Dino-stand.png");
        images[1] = readImage("/images/Dino-left-up.png");
        images[2] = readImage("/images/Dino-right-up.png");
        images[3] = readImage("/images/Dino-below-left-up.png");
        images[4] = readImage("/images/Dino-below-right-up.png");
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
        jumping = true;

        if (state == DOWN) {
            if (y >= GamePanel.groundY - (STAND_WIDTH - GamePanel.groundWidth)) {
                state = LEFT_UP;
                jumping = false;
            } else {
                y += jumpSpeed;
            }
        } else {//state == UP
            if (y <= GamePanel.groundY - (STAND_WIDTH - GamePanel.groundWidth) - JUMP_HEIGHT) {
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
        } else if (state == RIGHT_UP) {
            state = LEFT_UP;
        } else if (state == BELOW_LEFT_UP) {
            state = BELOW_RIGHT_UP;
        } else if (state == BELOW_LEFT_UP) {
            state = BELOW_LEFT_UP;
        }
    }


    public void setState(int state) {
        this.state = state;
    }

}
