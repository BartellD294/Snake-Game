import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random; //used for generating food

public class GameWindow extends JPanel implements ActionListener
{
    static final int WINDOW_WIDTH = 500;
    static final int WINDOW_HEIGHT = 500;
    static final int UNIT_SIZE = 25;
    static final int MAX_UNITS = (WINDOW_WIDTH*WINDOW_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75; //game speed
    final int x[] = new int[MAX_UNITS];
    final int y[] = new int[MAX_UNITS];
    int bodyParts = 5;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'r';
    boolean running = false;
    Timer timer;
    Random random;

    GameWindow()
    {
        random = new Random();
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame()
    {
        cook();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g)
    {
        if (running)
        {
            for (int i = 0; i < WINDOW_HEIGHT/UNIT_SIZE; i++)
            {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, WINDOW_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, WINDOW_WIDTH, i*UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i< bodyParts; i++)
            {
                if (i == 0)
                {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else
                {
                    g.setColor(Color.blue);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (WINDOW_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        }
        else
        {
            gameOver(g);
        }
    }

    public void cook() //make new food/apple
    {
        appleX = random.nextInt((int)(WINDOW_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(WINDOW_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move()
    {
        for (int i = bodyParts; i > 0; i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction)
        {
            case ('u'):
            y[0] = y[0] - UNIT_SIZE;
            break;
            case ('d'):
            y[0] = y[0] + UNIT_SIZE;
            break;
            case ('l'):
            x[0] = x[0] - UNIT_SIZE;
            break;
            case ('r'):
            x[0] = x[0] + UNIT_SIZE;
            break;
        }

    }

    public void checkFood()
    {
        if ((x[0] == appleX) && (y[0] == appleY))
        {
            bodyParts++;
            applesEaten++;
            cook();
        }
    }

    public void checkCollision()
    {
        for (int i = bodyParts; i > 0; i--)         //
        {                                           //
            if ((x[0] == x[i]) && (y[0] == y[i]))   // checks for body collision
            {                                       //
                running = false;                    //
            }                                       //
        }                                           //

        if (x[0]<0)             //if hits left border
            running = false;    //

        if (x[0] > WINDOW_WIDTH)//if hits right border
            running = false;    //

        if (y[0] < 0)
            running = false;

        if (y[0] > WINDOW_HEIGHT)
            running = false;

        if (!running)
            timer.stop();

    }

    public void gameOver(Graphics g)
    {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (WINDOW_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (WINDOW_WIDTH - metrics2.stringWidth("Game Over!"))/2, WINDOW_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (running)
        {
            move();
            checkFood();
            checkCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent k)
        {
            switch(k.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                if (direction != 'r')
                    direction = 'l';
                
                break;
                case KeyEvent.VK_RIGHT:
                if (direction != 'l')
                    direction = 'r';
                
                break;
                case KeyEvent.VK_UP:
                if (direction != 'd')
                    direction = 'u';
                
                break;
                case KeyEvent.VK_DOWN:
                if (direction != 'u')
                    direction = 'd';
                
                break;
            }

        }
    }
    
}
