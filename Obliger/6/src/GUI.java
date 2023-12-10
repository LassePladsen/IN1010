import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.LocalTime;

public class GUI {
    private boolean kjoer; // status variabel
    private int ms = 1000; // tid aa vente mellom oppdateringer
    private Verden verden;
    private int rader;
    private int kolonner;
    private JFrame vindu;
    private JPanel panelMeny;
    private JPanel panelRutenett;
    private JLabel antLevendeTekst;
    private JButton startStoppKnapp;
    private JButton plussKnapp;
    private JButton minusKnapp;
    private JButton avsluttKnapp;
    private JButton[][] celleKnapper;

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
        vindu.setLayout(new BorderLayout());
        initMeny();
        initRutenett();
    }

    public void startProgram() {
        vindu.pack();
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);
        loop();
    }

    private void oppdaterVindu() {
        verden.oppdatering();
        Rutenett rutenett = verden.hentRutenett();
        for (int i = 0; i < rader; i++) {
            for (int j = 0; j < kolonner; j++) {
                celleKnapper[i][j].setText(
                        rutenett.hentCelle(i, j).konverterStatusTegn());
            }
        }
    }

    private void initMeny() {
        panelMeny = new JPanel();
        panelMeny.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Antall levende tekst label
        antLevendeTekst = new JLabel("Antall levende: N/A");
        panelMeny.add(antLevendeTekst);

        // Start/stopp knapp
        class StartStoppKnapp extends JButton {
            StartStoppKnapp() {
                super("Start");
                super.addActionListener(new StartStoppAction());
            }

            class StartStoppAction implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(getText());
                    if (getText().equals("Stopp")) {
                        setText("Start");
                        kjoer = false;
                    } else if (getText().equals("Start")) {
                        setText("Stopp");
                        kjoer = true;
                    }
                }
            }
        }
        startStoppKnapp = new StartStoppKnapp();
        panelMeny.add(startStoppKnapp);

        // minsk ventetid knapp
        int faktor = 3; // faktor aa oeke/minske ventetiden med med
        class minskTidAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                ms = ms / faktor;
            }
        }

        plussKnapp = new JButton("+");
        plussKnapp.addActionListener(new minskTidAction());
        panelMeny.add(plussKnapp);

        // oek ventetid knapp
        class oekTidAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                ms = ms * faktor;
            }
        }
        minusKnapp = new JButton("-");
        minusKnapp.addActionListener(new oekTidAction());
        panelMeny.add(minusKnapp);

        // Avslutt knapp
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
        panelRutenett = new JPanel();
        panelRutenett.setLayout(new GridLayout(rader, kolonner));

        Rutenett rutenett = verden.hentRutenett();
        celleKnapper = new JButton[rader][kolonner];

        // Celle knapp
        class CelleKnapp extends JButton {
            Celle celle;

            CelleKnapp(Celle celle) {
                super(celle.konverterStatusTegn());
                this.celle = celle;
                super.addActionListener(new CelleKnappAction());
            }

            class CelleKnappAction implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(getText());
                    if (celle.erLevende()) {
                        celle.settDoed();
                    } else {
                        celle.settLevende();
                    }
                    setText(celle.konverterStatusTegn());
                }
            }
        }

        for (int i = 0; i < rader; i++) {
            for (int j = 0; j < kolonner; j++) {
                JButton knapp = new CelleKnapp(rutenett.hentCelle(i, j));
                celleKnapper[i][j] = knapp;
                panelRutenett.add(knapp);
            }
        }

        vindu.add(panelRutenett, BorderLayout.CENTER);
    }

    private void loop() {
        while (true) {
            System.out.print("");
            if (kjoer) {
                System.out.println("oppdater");
                oppdaterVindu();
            }

            // Vent tid
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
