import javax.swing.JFrame;

public class Frame extends JFrame
{
    Frame()
    {
        GameWindow window = new GameWindow();
        this.add(window);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
}
