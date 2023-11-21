import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.exit(1);
        }
        JFrame vindu = new JFrame("Rutenett");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

        JLabel tid = new JLabel();
        panel.add(tid);
        Klokke klokke = new Klokke(tid);
        klokke.start();

        vindu.pack();
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);

    }
}
