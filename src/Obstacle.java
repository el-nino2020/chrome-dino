import java.awt.*;

public interface Obstacle {
    //图片左上角(x, y)
    int getX();

    int getY();

    //图片左移，即x减小
    void moveLeft();

    //图片长宽
    int getLength();

    int getWidth();

    //返回图片对象
    Image getImage();

    int MOVE_SPEED = 7;
}
