import javax.swing.*;

public class DinoView extends JFrame {
    DinoPanel panel = null;

    public static void main(String[] args) {
        new DinoView();
    }

    public DinoView() {
        panel = new DinoPanel();

        this.add(panel);
        this.addKeyListener(panel); //使JFrame对象可以监听mp面板上发生的键盘事件

        this.setSize(800, 500);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
