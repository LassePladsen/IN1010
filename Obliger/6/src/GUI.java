import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI {
    private Verden verden;
    private int rader;
    private int kolonner;
    private JFrame vindu;
    private JPanel panelMeny;
    private JPanel panelRutenett;
    private JLabel antLevendeTekst;
    private JButton startStoppKnapp;
    private JButton avsluttKnapp;

    public GUI(Verden verden, int rader, int kolonner) {
        this.verden = verden;
        this.rader = rader;
        this.kolonner = kolonner;

        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Feil i GUI sette LookAndFeel...");
            System.exit(1);
        }

        // Initialiser elementer
        vindu = new JFrame("Game of life");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindu.setLayout(new GridLayout());
        initMeny();
        initRutenett();
    }

    public void startProgram() {
        vindu.pack();
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);
    }

    private void oppdaterVindu() {
        verden.oppdatering();

        // Vis vindu
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);
    }

    private void initMeny() {
        panelMeny = new JPanel();
        panelMeny.setLayout(new GridLayout(rader, kolonner));

        // Antall levende tekst label
        antLevendeTekst = new JLabel("Antall levende: N/A");
        panelMeny.add(antLevendeTekst);

        // Start/stopp knapp
        class StartStoppKnapp extends JButton {
            String tekst = "Start";

            StartStoppKnapp() {
                super("Start");
                super.addActionListener(new StartStoppAction());
            }

            class StartStoppAction implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tekst.equals("Stopp")) {
                        start();
                    } else if (tekst.equals("Start")) {
                        stopp();
                    }
                }

                private void stopp() {
                    setText("Start");
                }

                private void start() {
                    setText("Stopp");
                }
            }
        }
        startStoppKnapp = new StartStoppKnapp();
        panelMeny.add(startStoppKnapp);

        // Avsluttknapp
        class AvsluttAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
        avsluttKnapp = new JButton("Avslutt");
        avsluttKnapp.addActionListener(new AvsluttAction());
        panelMeny.add(avsluttKnapp);

        vindu.add(panelMeny, BorderLayout.NORTH);
    }

    private void initRutenett() {

    }
}
