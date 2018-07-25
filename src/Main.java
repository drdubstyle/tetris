import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static final int WIDTH = 200;
    public static final int HEIGHT = 500;

    public static void main(String[] args){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Tetris");
        Field f = new Field(WIDTH,HEIGHT);
        frame.setLocation((dimension.width - WIDTH)  / 2 , (dimension.height - HEIGHT) / 2 );
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(f);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                int i = e.getKeyCode();
                if (i == KeyEvent.VK_LEFT){
                    f.left = false;
                }
                if (i == KeyEvent.VK_RIGHT){
                    f.right = false;
                }
                if (i == KeyEvent.VK_DOWN){
                    f.boost = false;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int i = e.getKeyCode();
                if (i == KeyEvent.VK_LEFT){
                    f.left = true;
                }
                if (i == KeyEvent.VK_RIGHT){
                    f.right = true;
                }
                if (i == KeyEvent.VK_SPACE){
                    f.rot = true;
                }
                if (i == KeyEvent.VK_DOWN){
                    f.boost = true;
                }
            }
        });


    }
}
