import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bubble_Burst implements ActionListener, ChangeListener {
    private static JFrame frame;
    private static JButton startButton, restartButton;
    private static JSlider slider;
    public int difficulty_level;

    public void Bubble_Burst_Function() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        slider = new JSlider(1,3,1);

        frame.setSize(500, 160);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(250, 30));
        startButton.addActionListener(this);
        restartButton = new JButton("Restart");
        restartButton.addActionListener(this);
        slider.addChangeListener(this);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setFont(new Font("MV Boli", Font.PLAIN, 15));

        panel.setBorder(BorderFactory.createEmptyBorder(150, 80, 100, 80));
        panel.setLayout(new GridLayout(0, 1, 0, 20));

        panel.add(startButton);
        panel.add(restartButton);
        panel.add(slider);


        frame.setTitle("Bubble Burst Game");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Bubble_Burst Bubble = new Bubble_Burst();
        Bubble.Bubble_Burst_Function();
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == startButton) {
//            System.out.println("Hey, you clicked me. I'm the Start button");
            frame.dispose();
            Bubbles Game = new Bubbles();
            Game.go();
        }
        else if (actionEvent.getSource() == restartButton) {
//            System.out.println("Hey, you clicked me. I'm the Restart button");
            frame.dispose();
            Bubbles Game = new Bubbles();
            Game.go();
        }
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
//        System.out.println(slider.getValue());
//        System.out.println(difficulty_level);
//        System.out.println(difficultyLevel());
        difficulty_level = slider.getValue() + 2;
    }
}
