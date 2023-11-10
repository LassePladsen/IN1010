import java.util.InputMismatchException;
import java.util.Scanner;
import javax.naming.NameNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    private static final IndeksertListe<Pasient> pasientListe = new IndeksertListe<>();
    private static final IndeksertListe<Legemiddel> legemiddelListe = new IndeksertListe<>();
    private static final Prioritetskoe<Lege> legeListe = new Prioritetskoe<>();
    private static final IndeksertListe<Resept> reseptListe = new IndeksertListe<>();
    private static final Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            lesObjekterFraFil(new File("../data/legedata.txt"));
        }
        catch (NameNotFoundException | UlovligUtskrift e) {
        }
        catch (FileNotFoundException e) {

        }  
        hovedKommandoLokke();
        in.close();
    }

    private static void lesObjekterFraFil(File fil) 
                        throws FileNotFoundException,
                        NameNotFoundException,
                        UlovligUtskrift {
        Scanner scanner = new Scanner(fil);
        String objektType = "";

        // Loop over linjene og lag objekter
        while (scanner.hasNextLine()) {

            String linje = scanner.nextLine();
            // Ny objekt-type
            if (linje.charAt(0) == '#') {
                objektType = linje.split(" ")[1];
                // System.err.println(objektType);
                // System.err.println(scanner.nextLine());
                continue;
            }

            String[] verdier = linje.split(",");
            String navn;

            switch (objektType) {
                case "Pasienter":
                    pasientListe.leggTil(new Pasient(
                        verdier[0].trim(), verdier[1].trim()));
                    break;

                case "Legemidler":
                    navn = verdier[0].trim();
                    String type = verdier[1].trim();
                    switch (type) {
                        case "narkotisk":
                            legemiddelListe.leggTil(new Narkotisk(
                                navn,
                                Integer.parseInt(verdier[2].trim()), 
                                Double.parseDouble(verdier[3].trim()), 
                                Integer.parseInt(verdier[4].trim())
                            ));
                            break;
                        
                        case "vanedannende":
                            legemiddelListe.leggTil(new Vanedannende(
                                navn,
                                Integer.parseInt(verdier[2].trim()), 
                                Double.parseDouble(verdier[3].trim()), 
                                Integer.parseInt(verdier[4].trim())
                            ));
                            break;
                        
                        case "vanlig":
                            legemiddelListe.leggTil(new Vanlig(
                                navn,
                                Integer.parseInt(verdier[2].trim()), 
                                Double.parseDouble(verdier[3].trim()) 
                            ));
                            break;
                        }
                    break;
                case "Leger": 
                    navn = verdier[0].trim();
                    String kontrollId = verdier[1].trim();
                    
                    // Kontrollid = 0 -> vanlig lege
                    if (kontrollId.equals("0")) {
                        legeListe.leggTil(new Lege(navn));
                    }
                    // Kontrollid =/= 0 -> spesialist
                    else {
                        legeListe.leggTil(new Spesialist(navn, kontrollId));
                    }
                    break;

                case "Resepter":
                    // Hent legemiddel fra legemiddelNummer
                    Legemiddel legemiddel = legemiddelListe.hent(
                        Integer.parseInt(verdier[0].trim())
                    );

                    // Hent riktig lege fra navn
                    Lege utskrivendeLege = null;
                    navn = verdier[1].trim();
                    for (Lege lege : legeListe) {
                        if (lege.hentNavn().equals(navn)) {
                            utskrivendeLege = lege;
                        }
                    }
                    if (utskrivendeLege == null) {
                        throw (new NameNotFoundException(
                            "Fant ikke utskrivende lege med legenavn: " + verdier[1])
                            );
                    }

                    // Hent pasient fra pasientId
                    Pasient pasient = pasientListe.hent(Integer.parseInt(verdier[2].trim()));
                    
                    // Match resepttype
                    switch (verdier[3].trim()) {

                        case "hvit":
                            reseptListe.leggTil(utskrivendeLege.skrivHvitResept(
                                legemiddel, 
                                pasient, 
                                Integer.parseInt(verdier[4].trim())
                            ));
                            break;

                        case "blaa":
                            reseptListe.leggTil(utskrivendeLege.skrivBlaaResept(
                                legemiddel, 
                                pasient, 
                                Integer.parseInt(verdier[4].trim())
                            ));
                            break;

                        case "militaer":
                            reseptListe.leggTil(utskrivendeLege.skrivMilResept(
                                legemiddel, 
                                pasient
                            ));
                            break;

                        case "x":
                            reseptListe.leggTil(utskrivendeLege.skrivPResept(
                                legemiddel, 
                                pasient, 
                                Integer.parseInt(verdier[4].trim())
                            ));
                            break;
                        }
                    break;
            }   
        }
    scanner.close();
    }

    /* Skriver ut oversikt over hvor mange elementer i hver kategori */
    private static void skrivUtAntallObjekter() {
        System.out.println("Pasienter:  " + pasientListe.stoerrelse());
        System.out.println("Legemidler: " + legemiddelListe.stoerrelse());
        System.out.println("Leger:      " + legeListe.stoerrelse());
        System.out.println("Resepter:   " + reseptListe.stoerrelse());
    }

    /* skriver ut liste over alle pasienter */
    private static void skrivUtPasienter() {
        for (Pasient x : pasientListe) {
            System.out.println(
                x.hentId() + ": " + x.hentNavn() + " (fnr " + x.hentFodsNr() + ")"
            );
        }
    }

    /* Skriver ut liste all legemidler */
    private static void skrivUtLegemidler() {
        for (Legemiddel x : legemiddelListe) {
            if (x instanceof Narkotisk) {
                Narkotisk y = (Narkotisk) x;
                System.out.println(
                y.hentId() + ": " + x.hentNavn() + " (virkestoff " 
                + y.hentVirkestoff() + ", styrke " + y.hentStyrke() + ")"
            );
            } else if (x instanceof Vanedannende) {
                Vanedannende y = (Vanedannende) x;
                System.out.println(
                y.hentId() + ": " + x.hentNavn() + " (virkestoff " 
                + y.hentVirkestoff() + ", styrke " + y.hentStyrke() + ")"
            );
            } else {
            System.out.println(
                x.hentId() + ": " + x.hentNavn() + " (virkestoff " 
                + x.hentVirkestoff() + ")"
            );
            }
        }
    }

    /* Skriver ut liste over alle leger i alfabetisk (leksikonsk) rekkefølge */
    private static void skrivUtLeger() {
        for (Lege x : legeListe) {
            if (x instanceof Spesialist) {
                Spesialist y = (Spesialist) x;
                System.out.println(
                    y.hentNavn() + " (kode " + y.hentKontrollkode() + ")"
                );}
            else {
                System.out.println(x.hentNavn());
            }
        }
    }

    /* Skriver ut liste over alle resepter */
    private static void skrivUtResepter() {
        for (Resept x : reseptListe) {
            System.out.println(
                x.hentId() + ": " + x.hentLegemiddel().hentNavn() 
                + " (" + x.hentReit()  + " reit)"
            );
        }
    }

    /* Haandterer Scanner int input */
    private static int skanInt() {
        String valg;
        try {
            valg = in.next();
            if (valg.equals("...")) {
                return -1;
            }
            int ut = Integer.parseInt(valg);
            System.out.println();
            return ut;
        }
        catch (InputMismatchException | NumberFormatException e) {
            return -2;
        }

    }/* Haandterer Scanner double input */
    private static double skanDouble() {
        String valg;
        try {
            valg = in.next();
            double ut = Double.parseDouble(valg);
            System.out.println();
            return ut;
        }
        catch (InputMismatchException | NumberFormatException e) {
            return -2;
        }
    }

    /* Haandterer Scanner String input */
    private static String skanString() {
        String valg;
        while (true) {
            valg = in.nextLine().trim();
            if (valg.equals("")) {
                ugyldigInput();
                System.out.print("> ");
                continue;
            }
            break;
        }
        return valg;
    }

    private static void ugyldigInput() {
        System.out.println("\nUgyldig input, proev igjen");
    }
    
    /* Skriv ut objekt */
    private static void objektKommandolokke() {
        int valg;
        boolean run = true;

        while (run) {
            System.out.println("\nTilbake til hovedmeny:");
            System.out.println("[...]\n");
            System.out.println("Velg objekt for fullstendig oversikt:");
            System.out.println("0: Alle objekter");
            System.out.println("1: Pasienter");
            System.out.println("2: Legemidler");
            System.out.println("3: Leger");
            System.out.println("4: Resepter");
            System.out.print("> ");
            valg = skanInt();

            switch (valg) {
                
                // .../-1: tilbake til hovedmeny
                case -1:
                    run = false;
                    break;

                // 0: alle objekter
                case 0:
                    System.out.println("Pasienter:");
                    skrivUtPasienter();
                    System.err.println("\nLegemidler:");
                    skrivUtLegemidler();
                    System.out.println("\nLeger:");
                    skrivUtLeger();
                    System.out.println("\nResepter:");
                    skrivUtResepter();
                    break;
                
                // 1: Pasienter
                case 1:
                    System.out.println("Pasienter:");
                    skrivUtPasienter();
                    break;
                
                // 2: Legemidler
                case 2:
                    System.out.println("Legemidler:");
                    skrivUtLegemidler();
                    break;

                // 3: Leger
                case 3:
                    System.out.println("Leger:");
                    skrivUtLeger();
                    break;

                // 4: Resepter
                case 4:
                    System.out.println("Resepter:");
                    skrivUtResepter();
                    break;
                
                default:
                    ugyldigInput();
            }
        }
    }

    private static void lagObjektKommandolokke() {
        int valg;
        String navn;
        boolean run = true;

        while (run) {
            System.out.println("\nTilbake til hovedmeny:");
            System.out.println("[...]\n");
            System.out.println("Velg objekt for oppretting:");
            System.out.println("0: Pasienter");
            System.out.println("1: Legemidler");
            System.out.println("2: Leger");
            System.out.println("3: Resepter");
            System.out.print("> ");
            valg = skanInt();
            in.nextLine();

            switch (valg) {
                
                // .../-1: tilbake til hovedmeny
                case -1:
                    run = false;
                    break;

                // 0: Pasienter
                case 0:
                    System.out.println("Pasienter:");

                    System.out.print("Navn > ");
                    navn = skanString();

                    System.out.print("\nfnr > ");
                    String fnr = skanString();

                    pasientListe.leggTil(new Pasient(navn, fnr));
                    break;
                
                // 1: Legemidler
                case 1:
                    System.out.println("Legemidler:");

                    System.out.print("Navn > ");
                    navn = skanString();

                    System.out.print("Type [vanlig | vanedannende| narkotisk] > ");
                    String type = skanString().toLowerCase();

                    switch (type) {
                        
                        case "narkotisk":
                            System.out.print("Pris > ");
                            int pris = skanInt();
                            while (pris < 0) {
                                ugyldigInput();
                                System.out.print("> ");
                                pris = skanInt();
                            }

                            System.out.print("Virkestoff > ");
                            double virkestoff = skanDouble();
                            System.out.println(virkestoff);
                            // legemiddelListe.leggTil(new Narkotisk(
                            //     navn,
                            //     Integer.parseInt(verdier[2].trim()), 
                            //     Double.parseDouble(verdier[3].trim()), 
                            //     Integer.parseInt(verdier[4].trim())
                            // ));
                            break;
                        
                        // case "vanedannende":
                        //     legemiddelListe.leggTil(new Vanedannende(
                        //         navn,
                        //         Integer.parseInt(verdier[2].trim()), 
                        //         Double.parseDouble(verdier[3].trim()), 
                        //         Integer.parseInt(verdier[4].trim())
                        //     ));
                        //     break;
                        
                        // case "vanlig":
                        //     legemiddelListe.leggTil(new Vanlig(
                        //         navn,
                        //         Integer.parseInt(verdier[2].trim()), 
                        //         Double.parseDouble(verdier[3].trim()) 
                        //     ));
                        //     break;
                    }

                    // System.out.print("\nfnr > ");
                    // String fnr = in.next().trim();
                    // pasientListe.leggTil(new Pasient(navn, fnr));
                    break;

                // 2: Leger
                case 2:
                    System.out.println("Leger:");
                    skrivUtLeger();
                    break;

                // 3: Resepter
                case 3:
                    System.out.println("Resepter:");
                    skrivUtResepter();
                    break;

                default:
                    ugyldigInput();
            }
        }
    }

    /* Hovedmeny kommandoloekke */
    private static void hovedKommandoLokke() {
        int valg;
        boolean run = true;
        
        // Hoved-kommandoloekke
        while (run) {
            
            // HOVEDMENY
            System.out.println("\n---------------Hovedmeny---------------");
            System.out.println("0: Avslutt program");
            System.out.println("1: Skriv ut fullstendig objekt-oversikt");
            System.out.println("2: Opprett et nytt objekt");
            System.out.println("3: Bruk en resept for en pasient");
            System.out.println("4: Skriv ut statistikk");
            System.out.println("5: Skriv all data til fil");
            System.out.print("> ");
            valg = skanInt();

            switch (valg) {

                // 0: Avslutt program
                case 0:
                    System.out.println("Avslutter program...");
                    run = false;
                    break;

                // 1: Fullstendig objekt-oversikt
                case 1:
                    System.out.println("Antall objekter:");
                    skrivUtAntallObjekter();
                    objektKommandolokke(); 
                    break;

                // 2: Opprett nytt objekt
                case 2:
                    lagObjektKommandolokke();  
                    break;

                default:
                    ugyldigInput();
            }
        }
    }
}
    
    