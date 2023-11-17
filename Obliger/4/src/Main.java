import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.naming.NameNotFoundException;

public class Main {

    private static final IndeksertListe<Pasient> pasientListe = new IndeksertListe<>();
    private static final IndeksertListe<Legemiddel> legemiddelListe = new IndeksertListe<>();
    private static final Prioritetskoe<Lege> legeListe = new Prioritetskoe<>();
    private static final IndeksertListe<Resept> reseptListe = new IndeksertListe<>();
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            lesObjekterFraFil(new File("../data/legedata.txt"));
        } catch (NameNotFoundException | UlovligUtskrift e) {
        } catch (FileNotFoundException e) {
        }
        hovedKommandoLokke();
        in.close();
    }

    private static void lesObjekterFraFil(File fil)
            throws FileNotFoundException, NameNotFoundException,
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
                pasientListe.leggTil(
                        new Pasient(verdier[0].trim(), verdier[1].trim()));
                break;
            case "Legemidler":
                navn = verdier[0].trim();
                String type = verdier[1].trim();
                switch (type) {
                case "narkotisk":
                    legemiddelListe.leggTil(new Narkotisk(navn,
                            Integer.parseInt(verdier[2].trim()),
                            Double.parseDouble(verdier[3].trim()),
                            Integer.parseInt(verdier[4].trim())));
                    break;
                case "vanedannende":
                    legemiddelListe.leggTil(new Vanedannende(navn,
                            Integer.parseInt(verdier[2].trim()),
                            Double.parseDouble(verdier[3].trim()),
                            Integer.parseInt(verdier[4].trim())));
                    break;
                case "vanlig":
                    legemiddelListe.leggTil(new Vanlig(navn,
                            Integer.parseInt(verdier[2].trim()),
                            Double.parseDouble(verdier[3].trim())));
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
                Legemiddel legemiddel = legemiddelListe
                        .hent(Integer.parseInt(verdier[0].trim()));

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
                            "Fant ikke utskrivende lege med legenavn: "
                                    + verdier[1]));
                }

                // Hent pasient fra pasientId
                Pasient pasient = pasientListe
                        .hent(Integer.parseInt(verdier[2].trim()));

                // Match resepttype
                switch (verdier[3].trim()) {
                case "hvit":
                    reseptListe.leggTil(
                            utskrivendeLege.skrivHvitResept(legemiddel, pasient,
                                    Integer.parseInt(verdier[4].trim())));
                    break;
                case "blaa":
                    reseptListe.leggTil(
                            utskrivendeLege.skrivBlaaResept(legemiddel, pasient,
                                    Integer.parseInt(verdier[4].trim())));
                    break;
                case "militaer":
                    reseptListe.leggTil(utskrivendeLege
                            .skrivMilResept(legemiddel, pasient));
                    break;
                case "p":
                    reseptListe.leggTil(utskrivendeLege.skrivPResept(legemiddel,
                            pasient, Integer.parseInt(verdier[4].trim())));
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
            System.out.println(x.hentId() + ": " + x.hentNavn() + " (fnr "
                    + x.hentFodsNr() + ")");
        }
    }

    /* Skriver ut liste all legemidler */
    private static void skrivUtLegemidler() {
        for (Legemiddel x : legemiddelListe) {
            if (x instanceof Narkotisk) {
                Narkotisk y = (Narkotisk) x;
                System.out.println(y.hentId() + ": " + x.hentNavn()
                        + " (virkestoff " + y.hentVirkestoff() + ", styrke "
                        + y.hentStyrke() + ")");
            } else if (x instanceof Vanedannende) {
                Vanedannende y = (Vanedannende) x;
                System.out.println(y.hentId() + ": " + x.hentNavn()
                        + " (virkestoff " + y.hentVirkestoff() + ", styrke "
                        + y.hentStyrke() + ")");
            } else {
                System.out.println(x.hentId() + ": " + x.hentNavn()
                        + " (virkestoff " + x.hentVirkestoff() + ")");
            }
        }
    }

    /* Skriver ut liste over alle leger i alfabetisk (leksikonsk) rekkefølge */
    private static void skrivUtLeger() {
        for (Lege x : legeListe) {
            System.out.println(x.hentNavn());
        }
    }

    /* Skriver ut liste over alle resepter */
    private static void skrivUtResepter() {
        for (Resept x : reseptListe) {
            System.out.println(x.hentId() + ": " + x.hentLegemiddel().hentNavn()
                    + " (" + x.hentReit() + " reit)");
        }
    }

    /* Haandterer Scanner positiv int input */
    private static int skanInt() throws TilbakeSignal {
        String valg = "";
        while (true) {
            try {
                valg = in.next();
                int ut = Integer.parseInt(valg);
                if (ut < 0) {
                    throw (new NumberFormatException(
                            "Maa vaere positivt tall"));
                }
                return ut;
            } catch (InputMismatchException | NumberFormatException e) {
                // Gaa bak i meny
                if (valg.equals("...")) {
                    throw (new TilbakeSignal("Tilbake"));
                }
                ugyldigInput();
                System.out.print("> ");
            }
        }
    }

    /* Haandterer Scanner positiv double input */
    private static double skanDouble() throws TilbakeSignal {
        String valg = "";
        while (true) {
            try {
                valg = in.next();
                double ut = Double.parseDouble(valg);
                if (ut < 0) {
                    throw (new NumberFormatException(
                            "Maa vaere positivt tall"));
                }
                return ut;
            } catch (InputMismatchException | NumberFormatException e) {
                // Gaa bak i meny
                if (valg.equals("...")) {
                    throw (new TilbakeSignal("Tilbake"));
                }
                ugyldigInput();
                System.out.print("> ");
            }
        }
    }

    /* Haandterer Scanner String input */
    private static String skanString() throws TilbakeSignal {
        String valg;
        while (true) {
            valg = in.nextLine().trim();
            if (valg.equals("")) {
                ugyldigInput();
                System.out.print("> ");
                continue;
            } else if (valg.equals("...")) {
                throw (new TilbakeSignal("Tilbake"));
            }
            break;
        }
        return valg;
    }

    private static void ugyldigInput() {
        System.out.println("\nUgyldig input, proev igjen");
    }

    private static boolean haandterKontrollKode(Lege lege) {
        if (lege instanceof Spesialist) {
            Spesialist spes = (Spesialist) lege;
            System.out.println("\nHandling krever kontrollkode for lege '"
                    + spes.hentNavn() + "':");
            while (true) {
                System.out.print("Kontrollkode > ");
                try {
                    String kode = skanString();

                    if (kode.equals(spes.hentKontrollkode())) {
                        return true;
                    } else {
                        System.out.println("Feil kontrollkode for lege '"
                                + spes.hentNavn()
                                + "', proev igjen eller avslutt med '...':");
                    }
                }

                // TILBAKE
                catch (TilbakeSignal e) {
                    return false;
                }
            }

        }
        return true;
    }

    private static Legemiddel lagNyttLegemiddelLokke() {
        int styrke;
        String navn;

        try {
            while (true) {
                System.out.println("\nTilbake: [...]\n");
                System.out.println("- Nytt legemiddel -");
                System.out.print("Navn > ");
                navn = skanString();

                // Stor foerstebokstav
                navn = navn.substring(0, 1).toUpperCase() + navn.substring(1);

                System.out.print("Pris (heltall) > ");

                int pris = skanInt();
                while (pris < 0) {
                    ugyldigInput();
                    System.out.print("> ");
                    pris = skanInt();
                }

                System.out.print("Virkestoff > ");
                double virkestoff = skanDouble();

                in.nextLine();

                // FINN LEGEMIDDEL-TYPE
                System.out.println("\nTilbake:");
                System.out.println("[...]\n");
                System.out.println("Velg type legemiddel:");
                System.out.println("0: Vanlig");
                System.out.println("1: Vanedannende");
                System.out.println("2: Narkotisk");
                System.out.print("> ");
                int type = skanInt();
                in.nextLine();

                Legemiddel ny;
                switch (type) {
                case 0: // Vanlig
                    ny = new Vanlig(navn, pris, virkestoff);
                    legemiddelListe.leggTil(ny);
                    System.out.println("\nOpprettet nytt vanlig legemiddel '"
                            + navn + "' med pris=" + pris + ", virkestoff="
                            + virkestoff);
                    return ny;
                case 1: // Vanedannende
                    System.out.print("Styrke (heltall) > ");
                    styrke = skanInt();

                    ny = new Vanedannende(navn, pris, virkestoff, styrke);
                    legemiddelListe.leggTil(ny);
                    System.out.println(
                            "\nOpprettet nytt vanedannende legemiddel '" + navn
                                    + "' med pris=" + pris + ", virkestoff="
                                    + virkestoff + ", styrke=" + styrke);
                    return ny;
                case 2: // Narkotisk
                    System.out.print("Styrke (heltall) > ");
                    styrke = skanInt();

                    ny = new Narkotisk(navn, pris, virkestoff, styrke);
                    legemiddelListe.leggTil(ny);
                    System.out.println("\nOpprettet nytt narkotisk legemiddel '"
                            + navn + "' med pris=" + pris + ", virkestoff="
                            + virkestoff + ", styrke=" + styrke);
                    return ny;
                default:
                    ugyldigInput();
                }
            }
        } catch (TilbakeSignal e) { // AVBRYT NYTT LEGEMIDDEL
            return null;
        }
    }

    private static Pasient lagNyPasientLokke() {
        String navn;
        try {
            System.out.println("\nTilbake: [...]\n");
            System.out.println("- Ny pasient -");

            System.out.print("Navn > ");
            navn = skanString();

            // Stor foerstebokstav
            navn = navn.substring(0, 1).toUpperCase() + navn.substring(1);

            System.out.print("fnr > ");
            String fnr = skanString();

            Pasient ny = new Pasient(navn, fnr);
            System.out.println("\nOpprettet ny pasient '" + navn
                    + "' med foedselsnummer: " + fnr);
            pasientListe.leggTil(ny);
            return ny;
        } catch (TilbakeSignal e) { // AVBRYT NY PASIENT
            return null;
        }
    }

    private static Lege lagNyLegeLokke() {
        try {
            System.out.println("\nTilbake: [...]\n");
            System.out.println("- Ny lege -");
            System.out.print("Navn > Dr. ");
            String navn = skanString();

            // Stor foerste bokstav og legg til 'Dr. '
            navn = "Dr. " + navn.substring(0, 1).toUpperCase()
                    + navn.substring(1);
            while (true) {
                // FINN LEGE-TYPE
                System.out.println("Velg type lege:");
                System.out.println("0: Vanlig");
                System.out.println("1: Spesialist");
                System.out.print("> ");
                int type = skanInt();
                in.nextLine();

                Lege ny;
                switch (type) {
                case 0: // VANLIG LEGE
                    ny = new Lege(navn);
                    legeListe.leggTil(ny);
                    System.out.println("\nOpprettet ny lege '" + navn + "'");
                    return ny;
                case 1: // SPESIALIST LEGE
                    System.out.print("Kontrollkode > ");
                    String kontrollkode = skanString();
                    ny = new Spesialist(navn, kontrollkode);
                    legeListe.leggTil(ny);
                    System.out.println("\nOpprettet ny spesialist lege '" + navn
                            + "' med kontrollkode: " + kontrollkode);
                    return ny;
                default:
                    ugyldigInput();
                }
            }
        } catch (TilbakeSignal e) { // AVBRYT NY LEGE
            return null;
        }
    }

    private static Resept lagNyReseptLokke() {
        Resept ny;

        System.out.println("\nTilbake: [...]\n");
        System.out.println("- Ny resept -");

        try {
            // LEGEMIDDEL
            Legemiddel legemiddel = legemiddelListe.hent(0); // for aa
            // initialisere;
            while (true) {
                System.out.println("Legemiddel for resept:");
                System.out.println("0: Opprett nytt legemiddel");
                System.out.println("1: Bruk eksisterende legemiddel");
                System.out.print("> ");
                int valg = skanInt();
                in.nextLine();

                switch (valg) {
                case 0: // 0: Lag ny
                    legemiddel = lagNyttLegemiddelLokke();
                    break;
                case 1: // 1: Hent eksisterende
                    System.out.print("ID paa legemiddel > ");
                    int id = skanInt();

                    // Finn riktig legemiddel fra id:
                    boolean funnet = false;
                    for (Legemiddel lm : legemiddelListe) {
                        if (lm.hentId() == id) {
                            legemiddel = lm;
                            funnet = true;
                            break;
                        }
                    }

                    // Fant ikke legemiddel; gaa tilbake til forrige meny
                    if (!funnet) {
                        System.out.println("Fant ikke legemiddelet med id: "
                                + id
                                + ", sjekk at det er riktig ID eller lag et nytt legemiddel");
                        continue;
                    }
                    break;
                default:
                    ugyldigInput();
                    continue;
                }

                // Fant legemiddel: fortsett lage ny resept nedenfor
                break;
            }

            // LEGE
            Lege lege = legeListe.hent(); // for aa initialisere
            while (true) {
                System.out.println("\nUtskrivende lege for resept:");
                System.out.println("0: Opprett ny lege");
                System.out.println("1: Bruk eksisterende lege");
                System.out.print("> ");

                int valg = skanInt();
                in.nextLine();

                switch (valg) {
                case 0: // Lag ny
                    lege = lagNyLegeLokke();
                    break;
                case 1: // Finn eksisterende
                    System.out.print("Navn paa utskrivende lege > Dr. ");
                    String navn = skanString();

                    // Stor foerstebokstav
                    navn = "Dr. " + navn.substring(0, 1).toUpperCase()
                            + navn.substring(1);

                    // Finn lege fra navn
                    boolean funnet = false;
                    for (Lege l : legeListe) {
                        if (l.hentNavn().toLowerCase()
                                .equals(navn.toLowerCase())) {
                            lege = l;
                            funnet = true;
                            break;
                        }
                    }

                    // Fant ikke lege; gaa tilbake til forrige meny
                    if (!funnet) {
                        System.out.println("\nFant ikke lege med navn: " + navn
                                + ", sjekk at det er riktig navn");
                        continue;
                    }
                    break;
                default:
                    ugyldigInput();
                    continue;
                }

                // Fant lege: fortsett med my resept nedenfor
                break;
            }

            // SJEKK SPESIALIST-KODE HVIS LEGEMIDDEL ER NARKOTISK
            if (legemiddel instanceof Narkotisk) {
                if (!haandterKontrollKode(lege)) { // Ikke faat godkjent kode
                    throw (new TilbakeSignal("Ikke godkjent kontrollkode"));
                }
            }

            // PASIENT
            Pasient pasient = pasientListe.hent(0); // for aa initialisere
            while (true) {
                System.out.println("\nPasient for resept:");
                System.out.println("0: Opprett ny pasient");
                System.out.println("1: Bruk eksisterende pasient");
                System.out.print("> ");

                int valg = skanInt();
                in.nextLine();

                switch (valg) {
                case 0: // Lag ny
                    pasient = lagNyPasientLokke();
                    break;
                case 1: // Finn eksisterende
                    System.out.print("Navn paa pasient > ");
                    String navn = (skanString()).trim();

                    // Stor foerstebokstav
                    navn = navn.substring(0, 1).toUpperCase()
                            + navn.substring(1);

                    // Finn pasient fra navn
                    boolean funnet = false;
                    for (Pasient pas : pasientListe) {
                        if (pas.hentNavn().toLowerCase()
                                .equals(navn.toLowerCase())) {
                            pasient = pas;
                            funnet = true;
                            break;
                        }
                    }

                    // Fant ikke pasient; gaa tilbake til forrige meny
                    if (!funnet) {
                        System.out.println("\nFant ikke pasient med navn: "
                                + navn + ", sjekk at det er riktig navn");
                        continue;
                    }
                    break;
                default:
                    ugyldigInput();
                    continue;
                }

                // Fant pasient: fortsett med my resept nedenfor
                break;
            }

            // RESEPTTYPE
            while (true) {
                System.out.println("\nResepttype:");
                System.out.println("0: Blaa");
                System.out.println("1: Hvit");
                System.out.println("2: P-resept");
                System.out.println("3: Militaer");
                System.out.print("> ");
                int type = skanInt();
                int reit;

                try {
                    switch (type) {
                    case 0: // 0: Blaa
                        System.out.print("Reit (heltall) > ");
                        reit = skanInt();

                        ny = lege.skrivBlaaResept(legemiddel, pasient, reit);
                        System.out.println(
                                "\nOpprettet ny blaa resept for legemiddelet '"
                                        + legemiddel.hentNavn()
                                        + "' av utskrivende lege '"
                                        + lege.hentNavn() + "' for pasient '"
                                        + pasient.hentNavn() + "', med reit="
                                        + reit);
                        return ny;
                    case 1: // 1: Hvit
                        System.out.print("Reit (heltall) > ");
                        reit = skanInt();
                        ny = lege.skrivHvitResept(legemiddel, pasient, reit);
                        System.out.println(
                                "\nOpprettet ny hvit resept for legemiddelet '"
                                        + legemiddel.hentNavn()
                                        + "' av utskrivende lege"
                                        + lege.hentNavn() + "' for pasient '"
                                        + pasient.hentNavn() + "', med reit="
                                        + reit);
                        return ny;
                    case 2: // 2: P-resept
                        System.out.print("Reit (heltall) > ");
                        reit = skanInt();
                        ny = lege.skrivPResept(legemiddel, pasient, reit);
                        System.out.println(
                                "\nOpprettet ny p-resept for legemiddelet '"
                                        + legemiddel.hentNavn()
                                        + "' av utskrivende lege"
                                        + lege.hentNavn() + "' for pasient '"
                                        + pasient.hentNavn() + "', med reit="
                                        + reit);
                        return ny;
                    case 3: // 3: Militaer
                        ny = lege.skrivMilResept(legemiddel, pasient);
                        System.out.println(
                                "\nOpprettet ny militaer resept for legemiddelet '"
                                        + legemiddel.hentNavn()
                                        + "' av utskrivende lege"
                                        + lege.hentNavn() + "' for pasient '"
                                        + pasient.hentNavn() + "' (reit=3)");
                        return ny;
                    }
                } catch (UlovligUtskrift e) {
                    System.out.println("Lege '" + lege.hentNavn()
                            + "' har ikke rettigheter til aa skrive ut det narkotiske legemiddelet '"
                            + legemiddel.hentNavn()
                            + "', gaar tilbake til objektmeny\n");
                    throw (new TilbakeSignal(
                            "Lege hadde ikke rettigheter til aa skrive denne resepten"));
                }
            }
        } catch (TilbakeSignal e) { // AVBRYT NY RESEPT
            return null;
        }
    }

    /* Kommandoloekke for aa lage nye objekter */
    private static void lagObjektKommandolokke() {
        int valg;

        while (true) {
            System.out.println("\nTilbake til hovedmeny:");
            System.out.println("[...]\n");
            System.out.println("Velg objekt for oppretting:");
            System.out.println("0: Pasient");
            System.out.println("1: Legemiddel");
            System.out.println("2: Lege");
            System.out.println("3: Resept");
            System.out.print("> ");

            try {
                valg = skanInt();
                in.nextLine();

                switch (valg) {
                case 0: // 0: Ny pasient
                    lagNyPasientLokke();
                    break;
                case 1: // 1: Nytt legemiddel
                    lagNyttLegemiddelLokke();
                    break;
                case 2: // 2: Lege
                    lagNyLegeLokke();
                    break;
                case 3: // 3: Ny resept
                    lagNyReseptLokke();
                    break;
                default:
                    ugyldigInput();
                }
            } catch (TilbakeSignal e) { // TILBAKE TIL HOVEDMENY
                return;
            }
        }
    }

    /* Kommandoloekke for aa skrive ut objektliste */
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

            try {
                valg = skanInt();

                switch (valg) {
                case 0: // 0: alle objekter
                    System.out.println("Pasienter:");
                    skrivUtPasienter();
                    System.err.println("\nLegemidler:");
                    skrivUtLegemidler();
                    System.out.println("\nLeger:");
                    skrivUtLeger();
                    System.out.println("\nResepter:");
                    skrivUtResepter();
                    break;
                case 1: // 1: Pasienter
                    System.out.println("Pasienter:");
                    skrivUtPasienter();
                    break;
                case 2: // 2: Legemidler
                    System.out.println("Legemidler:");
                    skrivUtLegemidler();
                    break;
                case 3: // 3: Leger
                    System.out.println("Leger:");
                    skrivUtLeger();
                    break;
                case 4: // 4: Resepter
                    System.out.println("Resepter:");
                    skrivUtResepter();
                    break;
                default:
                    ugyldigInput();
                }
            } catch (TilbakeSignal e) { // TILBAKE TIL HOVEDMENY
                return;
            }
        }
    }

    /* Hovedmeny kommandoloekke */
    private static void hovedKommandoLokke() {
        int valg;

        // Hoved-kommandoloekke
        while (true) {
            // HOVEDMENY
            System.out.println("\n---------------Hovedmeny---------------");
            System.out.println("0: Avslutt program");
            System.out.println("1: Skriv ut fullstendig objekt-oversikt");
            System.out.println("2: Opprett et nytt objekt");
            System.out.println("3: Bruk en resept for en pasient");
            System.out.println("4: Skriv ut statistikk");
            System.out.println("5: Skriv all data til fil");
            System.out.print("> ");

            try {
                valg = skanInt();

                switch (valg) {
                // 0: Avslutt program
                case 0:
                    System.out.println("Avslutter program...\n");
                    return;
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
            } catch (TilbakeSignal e) {
                continue;
            }
        }
    }
}
