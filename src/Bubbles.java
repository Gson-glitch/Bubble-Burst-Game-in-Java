import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Bubbles extends JFrame {
    public static JFrame frame = new JFrame("Bubble Burst Game");
    public static JPanel panel;
    public static JLabel label, timerLabel;
    public static drawPanel D;
    public boolean firstTime = true;
    public int bound = 50;
    public int timeToCompleteGame = 10;

    public static void main(String[] args) {
        Bubbles GUI = new Bubbles();
        GUI.go();
    }

    public void updateLabel(int n) {
        label.setText("Round: "+n);
        panel.repaint();
    }
    public void updateTimeLabel(int t) {
        if (t>=0) {
            timerLabel.setText("Time: "+t);
            panel.repaint();
        }
    }
    public void destroyFrame() {
        JOptionPane.showMessageDialog(frame, "Game Over", "Game Over", JOptionPane.PLAIN_MESSAGE);
        frame.dispose();
    }

    public void go() {
        D = new drawPanel();
        panel = new JPanel();
        label = new JLabel("Round: 1");
        timerLabel = new JLabel("Time: ");
        label.setBounds(0,0, 50, 18);
        timerLabel.setBounds(200, 200, 100, 50);
//        panel.setBackground(Color.BLUE);
        panel.setBounds(0,0, 800, 18);
        panel.add(label);
        panel.add(timerLabel);

        frame.add(panel);
        frame.getContentPane().add(D);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }

}

class drawPanel extends JPanel {
    Bubbles B = new Bubbles();
    Bubble_Burst B2 = new Bubble_Burst();
    int bubblesToBeDrawn = 5;
    int radiusOfBubble = 50;
    ArrayList<Ellipse2D> myArray = new ArrayList<>();
//    ArrayList<Ellipse2D> tempArray = new ArrayList<>();
    int[] xAxis = new int[bubblesToBeDrawn];
    int[] yAxis = new int[bubblesToBeDrawn];
    int numberOfBubblesClicked = 0;
    int numberOfRounds = 1;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
//        System.out.println(B2.difficulty_level);
       if (myArray.isEmpty() && B.firstTime) {
           B.updateLabel(numberOfRounds);
           java.util.Timer timer = new Timer();
           TimerTask timerTask = new TimerTask() {
               @Override
               public void run() {
                   // TODO Auto-generated method stub
//                   System.out.println("Hello from timer" + " " + B.timeToCompleteGame);
                   new Stopwatch(B.timeToCompleteGame);
                   B.timeToCompleteGame -= 0.25;
               }
           };
           timer.schedule(timerTask, B.timeToCompleteGame);

           for (int counter = 0; counter < bubblesToBeDrawn; counter++) {
               xAxis[counter] = Reposition_Global()[0];
               yAxis[counter] = Reposition_Global()[1];
               Ellipse2D oval = new Ellipse2D.Double(xAxis[counter] + 20, yAxis[counter] + 20, radiusOfBubble, radiusOfBubble);
               myArray.add(oval);
           }
           while(checkCollision(myArray)) {
               myArray.clear();
               for (int counter = 0; counter < bubblesToBeDrawn; counter++) {
                   xAxis[counter] = Reposition_Global()[0];
                   yAxis[counter] = Reposition_Global()[1];
                   Ellipse2D oval = new Ellipse2D.Double(xAxis[counter] + 20, yAxis[counter] + 20, radiusOfBubble, radiusOfBubble);
                   myArray.add(oval);
               }
               checkCollision(myArray);
           }
           drawCircle(myArray, g);
       }
       else {
           drawCircle(myArray, g);
           if (myArray.isEmpty()) {
               B.bound += 15;
               numberOfRounds++;
               B.firstTime = true;
               repaint();
           }
           if (numberOfRounds > 10 && myArray.isEmpty()) {
               numberOfRounds = 1;
               System.out.println("Game over");
//               JOptionPane.showMessageDialog(this, "Game Over");
               B.destroyFrame();
               B2.Bubble_Burst_Function();
           }
       }
        for(Ellipse2D i : myArray) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getButton()==1 && i.contains(e.getX(), e.getY())) {
                        numberOfBubblesClicked++;
                        if (numberOfBubblesClicked == bubblesToBeDrawn) {
//                            B.updateLabel(numberOfRounds);
                            numberOfBubblesClicked = 0;
                        }
                        B.firstTime = false;
                        myArray.remove(i);
                        drawCircle(myArray, g);
                        repaint();
                    }
                }
            });
        }

    }

    public void drawCircle(ArrayList<Ellipse2D> L, Graphics G) {
        int val = 0;
        Graphics2D g2d = (Graphics2D) G;
        while (L.size() > val) {
//            System.out.println(myArray.get(val));
            g2d.fill(L.get(val));
            val++;
        }
    }

    public int[] Reposition_Global() {
        int[] coordinates = new int[2];
        Random random = new Random();
        int x = random.nextInt(700);
        int y = random.nextInt(500);
        int finalX = Reposition_Local(x, B.bound);
        int finalY = Reposition_Local(y, B.bound);
        coordinates[0] = finalX;
        coordinates[1] = finalY;
//        coordinates[0] = x;
//        coordinates[1] = y;
        return coordinates;
    }

    public int Reposition_Local(int a, int bound) {
        int minvalue = a;
        int maxValue = a+bound;
        return ThreadLocalRandom.current().nextInt(minvalue, maxValue);
    }
    public boolean checkCollision(ArrayList<Ellipse2D> A) {
        for(Ellipse2D i : A) {
            for (Ellipse2D j : A) {
                if (i.contains(j.getX(), j.getY())) {
                    return true;
                }
            }
        }
        return false;
    }
}