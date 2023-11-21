import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Rutenett {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.exit(1);
        }
        JFrame vindu = new JFrame("Rutenett");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

        panel.setLayout(new GridLayout(3, 3));
        
        for (int i = 0; i < 9; i++) {
            JLabel tall = new JLabel("" + i);
            int size = 80;
            tall.setPreferredSize(new Dimension(size, size));
            tall.setHorizontalAlignment(JLabel.CENTER);
            tall.setVerticalAlignment(JLabel.CENTER);
            tall.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(tall);
        }

        vindu.pack();
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);

    }
}
