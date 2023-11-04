import java.util.HashMap;
import java.util.Scanner;

class TelefonBok {
    public static void main(String[] args) {
        HashMap<String, String> telefonBok = new HashMap<String, String>();
        
        telefonBok.put("Arne", "22334455");
        telefonBok.put("Lisa", "95959595");
        telefonBok.put("Jonas", "97959795");
        telefonBok.put("Peder", "12345678");
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("0: Søk etter person\n1: Avslutt\n$ ");
            int response = scanner.nextInt();
            
            switch (response) {
                // Avslutt
                case 1: 
                    System.out.println("Telefonbok oversikt:");
                    for (String navn : telefonBok.keySet()) {
                        System.out.println(
                            "Navn: " + navn + ", tlf: " + telefonBok.get(navn)
                            );
                    } return;
                
                // Søk person
                case 0:
                    System.out.print("Hvem vil du ha nummeret til?\n$ ");
                    String navn = scanner.next(); 
                    if (telefonBok.containsKey(navn)) {
                        System.out.println(
                        "Navn: " + navn + ", tlf: " 
                        + telefonBok.get(navn) + "\n"
                        );
                    } else {
                        System.out.println("Fant ikke " + navn + " i telefonbok.\n");
                    }
            }
        }
        
    }
}
