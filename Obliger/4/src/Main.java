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
                        + " (narkotisk, virkestoff=" + y.hentVirkestoff()
                        + ", styrke=" + y.hentStyrke() + ")");
            } else if (x instanceof Vanedannende) {
                Vanedannende y = (Vanedannende) x;
                System.out.println(y.hentId() + ": " + x.hentNavn()
                        + " (vanedannende, virkestoff=" + y.hentVirkestoff()
                        + ", styrke=" + y.hentStyrke() + ")");
            } else if (x instanceof Vanlig) {
                System.out.println(x.hentId() + ": " + x.hentNavn()
                        + " (vanlig, virkestoff=" + x.hentVirkestoff() + ")");
            }
        }
    }

    /* Skriver ut liste over alle leger i alfabetisk (leksikonsk) rekkefølge */
    private static void skrivUtLeger() {
        int teller = 0;
        for (Lege x : legeListe) {
            System.out.println(teller + ": " + x.hentNavn());
            teller++;
        }
    }

    /* Skriver ut liste over alle resepter globalt */
    private static void skrivUtResepter() {
        for (Resept x : reseptListe) {
            System.out.println(x.hentId() + ": " + x.hentLegemiddel().hentNavn()
                    + " (" + x.hentReit() + " reit)");
        }
    }

    /* Skriver ut liste over alle resepter FOR EN GITT PASIENT */
    private static void skrivUtResepter(Pasient pasient) {
        int teller = 0;
        for (Resept x : pasient.hentReseptListe()) {
            System.out.println(teller + ": " + x.hentLegemiddel().hentNavn()
                    + " (" + x.hentReit() + " reit)");
            teller++;
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
                in.nextLine();
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
        return false; // vanlige leger faar ikke godkjent
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
            Legemiddel legemiddel;
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
                    System.out.println("\nVelg legemiddel:");
                    skrivUtLegemidler();
                    System.out.print("> ");
                    valg = skanInt();

                    // Finn legemiddel fra indeks
                    try {
                        legemiddel = legemiddelListe.hent(valg);
                    }

                    // Fant ikke legemiddel fra indeks; proev igjen
                    catch (UgyldigListeindeks e) {
                        ugyldigInput();
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
            Lege lege = legeListe.hent(); // maa initialiseres for kompiler
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
                    System.out.println("\nVelg lege:");
                    skrivUtLeger();
                    System.out.print("> ");
                    valg = skanInt();

                    // Finn lege fra indeks
                    int teller = 0;
                    boolean funnet = false;
                    for (Lege l : legeListe) {
                        if (teller == valg) {
                            lege = l;
                            funnet = true;
                            break;
                        }
                        teller++;
                    }

                    // Fant ikke pasient fra indeks; proev igjen
                    if (!funnet) {
                        ugyldigInput();
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
            if (legemiddel instanceof Narkotisk
                    && !haandterKontrollKode(lege)) {
                System.out.println("Lege '" + lege.hentNavn()
                        + "' har enten ikke godkjenning til aa skrive ut dette legemiddelet'"
                        + legemiddel.hentNavn()
                        + "', eller saa er gitt kontrollkode feil.");
                throw (new TilbakeSignal("Ikke godkjent lege/kontrollkode"));
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
                    System.out.println("Velg pasient:");
                    skrivUtPasienter();
                    System.out.print("> ");
                    valg = skanInt();

                    // Finn pasient fra indeks
                    try {
                        pasient = pasientListe.hent(valg);
                    }

                    // Fant ikke pasient fra indeks; proev igjen
                    catch (UgyldigListeindeks e) {
                        ugyldigInput();
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
                        reseptListe.leggTil(ny);
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
                        reseptListe.leggTil(ny);
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
                        reseptListe.leggTil(ny);
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
                        reseptListe.leggTil(ny);
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
    private static void skrivObjekterKommandolokke() {
        int valg;
        while (true) {
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

    private static void brukReseptKommandoLokke() {
        int valg;
        Pasient pasient;
        while (true) {
            try {
                System.out.println("\nTilbake:\n[...]\n");
                System.out.println("Hvilken pasient vil du se resepter for?");
                skrivUtPasienter();
                System.out.print("> ");
                valg = skanInt();

                // Finn pasient fra indeks
                try {
                    pasient = pasientListe.hent(valg);
                }

                // Fant ikke pasient fra indeks; proev igjen
                catch (UgyldigListeindeks e) {
                    ugyldigInput();
                    continue;
                }

                // Fant pasient: gaa videre
                try {
                    if (pasient.hentReseptListe().stoerrelse() == 0) {
                        System.out.println("Pasient '" + pasient.hentNavn()
                                + "' har ingen resepter");
                        throw (new TilbakeSignal(
                                "Pasient hadde ingen resepter aa bruke"));
                    }
                    while (true) {
                        System.out.println("\nTilbake:\n[...]\n");
                        System.out.println("Hvilken resept vil du bruke?");
                        skrivUtResepter(pasient);
                        System.out.print("> ");
                        valg = skanInt();

                        // Hent resept fra indeks
                        try {
                            IndeksertListe<Resept> liste = (IndeksertListe<Resept>) pasient
                                    .hentReseptListe();
                            Resept resept = liste.hent(valg);

                            if (resept.bruk()) {
                                System.out.println("Brukte valgt resept paa '"
                                        + resept.hentLegemiddel().hentNavn()
                                        + "', ny reit=" + resept.hentReit());
                            } else {
                                System.out.println(
                                        "Kunne ikke bruke valgt resept paa '"
                                                + resept.hentLegemiddel()
                                                        .hentNavn()
                                                + "' (ingen gjenvarende reit)");
                            }
                        }

                        // Fant ikke resept fra indeks; proev igjen
                        catch (UgyldigListeindeks e) {
                            ugyldigInput();
                            continue;
                        }

                    }

                }

                // TILBAKE TIL PASIENTLISTE-VALG
                catch (TilbakeSignal e) {
                    continue;
                }

            }

            // TILBAKE TIL HOVEDMENY
            catch (TilbakeSignal e) {
                return;
            }

        }

    }

    private static void skrivUtNarkotikaMisbrukKommandolokke() {
        int valg;
        int antallNarkotiske;
        Liste<Resept> resepter;
        while (true) {
            System.out.println("\nTilbake:");
            System.out.println("[...]\n");
            System.out.println("Velg narkotisk-misbruk statistikk:");
            System.out.println(
                    "0: Alle leger som har skrevet ut minst én resept på narkotiske legemidler");
            System.out.println(
                    "1: Alle pasienter som har minst én gyldig resept på narkotiske legemidler");
            System.out.print("> ");

            try {
                valg = skanInt();
                switch (valg) {
                case 0: // liste over leger med minst én narkotisk resept
                    System.out.println(
                            "\nNavn paa lege: antall utskrevne resepter paa narkotiske legemidler");

                    // Loop over leger
                    for (Lege l : legeListe) {
                        // Tell narkotiske resepter
                        antallNarkotiske = 0;
                        resepter = l.hentUtskrevneResepter();
                        for (Resept rs : resepter) {
                            if (rs.hentLegemiddel() instanceof Narkotisk) {
                                antallNarkotiske++;
                            }
                        }

                        if (antallNarkotiske == 0) {
                            continue;
                        }

                        System.out.println(
                                "- " + l.hentNavn() + ": " + antallNarkotiske);
                    }
                    break;
                case 1: // liste over pasienter med minst én narkotisk resept
                    System.out.println(
                            "\nNavn paa pasient: antall resepter paa narkotiske legemidler");

                    // Loop pasienter
                    for (Pasient ps : pasientListe) {
                        // Tell narkotiske resepter
                        antallNarkotiske = 0;
                        resepter = ps.hentReseptListe();
                        for (Resept rs : resepter) {
                            if (rs.hentLegemiddel() instanceof Narkotisk) {
                                antallNarkotiske++;
                            }
                        }

                        if (antallNarkotiske == 0) {
                            continue;
                        }

                        System.out.println(
                                "- " + ps.hentNavn() + ": " + antallNarkotiske);
                    }
                    break;
                }
            }

            // TILBAKE TIL STATISTIKK-MENY
            catch (TilbakeSignal e) {
                return;
            }
        }

    }

    private static void skrivUtStatistikkKommandolokke() {
        int valg;

        while (true) {
            System.out.println("\nTilbake til hovedmeny:");
            System.out.println("[...]\n");
            System.out.println("Velg statistikk:");
            System.out.println(
                    "0: Antall utskrevne resepter paa de forskjellige type legemidler");
            System.out.println("1: Misbruk av narkotiske midler");
            System.out.print("> ");

            try {
                valg = skanInt();
                switch (valg) {
                case 0: // 0: Antall resepter paa de forskjellige type
                        // legemidler
                    int antallVanlig = 0;
                    int antallVanedannende = 0;
                    int antallNarkotiske = 0;

                    // Tell antall av hver
                    for (Resept rs : reseptListe) {
                        Legemiddel lm = rs.hentLegemiddel();
                        if (lm instanceof Vanlig) {
                            antallVanlig++;
                        } else if (lm instanceof Vanedannende) {
                            antallVanedannende++;
                        } else if (lm instanceof Narkotisk) {
                            antallNarkotiske++;
                        }
                    }

                    System.out.println("\nResepter paa vanlige legemidler: "
                            + antallVanlig);
                    System.out.println("Resepter paa vanedannende legemidler: "
                            + antallVanedannende);
                    System.out.println("Resepter paa narkotiske legemidler: "
                            + antallNarkotiske);
                    break;
                case 1: // 1: Misbruk av narkotiske midler
                    skrivUtNarkotikaMisbrukKommandolokke();
                    break;
                }
            }

            // TILBAKE TIL HOVEDMENY
            catch (TilbakeSignal e) {
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
            }

            // Avslutt
            catch (TilbakeSignal e) {
                System.out.println("Avslutter program...\n");
                return;
            }

            switch (valg) {
            case 0: // 0: Avslutt program
                System.out.println("Avslutter program...\n");
                return;
            case 1: // 1: Fullstendig objekt-oversikt
                System.out.println("Antall objekter:");
                skrivUtAntallObjekter();
                skrivObjekterKommandolokke();
                break;
            case 2: // 2: Opprett nytt objekt
                lagObjektKommandolokke();
                break;
            case 3: // 3: Bruk en resept for en pasient
                brukReseptKommandoLokke();
                break;
            case 4: // 4: Skriv ut statistikk
                skrivUtStatistikkKommandolokke();
                break;
            default:
                ugyldigInput();
            }

        }
    }
}
