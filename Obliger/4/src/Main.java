import java.util.Scanner;

import javax.naming.NameNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    private static final IndeksertListe<Pasient> pasientListe = new IndeksertListe<>();
    private static final IndeksertListe<Legemiddel> legemiddelListe = new IndeksertListe<>();
    private static final IndeksertListe<Lege> legeListe = new IndeksertListe<>();
    private static final IndeksertListe<Resept> reseptListe = new IndeksertListe<>();
    private static String objektType = "";
    
    public static void lesObjekterFraFil(File fil) 
                        throws FileNotFoundException,
                        NameNotFoundException,
                        UlovligUtskrift {
        Scanner scanner = new Scanner(fil);

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

                        case "p":
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
    
    public static void main(String[] args) {
        try {
            lesObjekterFraFil(new File("../data/legedata.txt"));

            for (Resept x : reseptListe) {
                System.out.println(x);
            }
        }
        catch (FileNotFoundException | NameNotFoundException | UlovligUtskrift e) {
            System.err.println(e);
        }
    }
}
    
    