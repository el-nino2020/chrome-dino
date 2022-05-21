import javax.swing.*;

public class GameView extends JFrame {

    GamePanel panel = null;

    public static final int FRAME_LENGTH = 800;
    public static final int FRAME_WIDTH = 500;

    public static void main(String[] args) {
        new GameView();
    }

    public GameView() {
        panel = new GamePanel();

        new Thread(panel).start();

        this.add(panel);
        this.addKeyListener(panel); //使JFrame对象可以监听mp面板上发生的键盘事件

        this.setSize(FRAME_LENGTH, FRAME_WIDTH);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
