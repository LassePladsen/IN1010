class FeilTest {
    public static void main(String[] args) {
        
        //Oppretter en array med noen strenger
        String[] tallstrenger = {"10", "1", "32", "hei", "14", "11"};
        
        // b)
        // //Forsoeker aa konvertere alle strengene til heltall og skrive ut
        // try {
        //     for (int i = 0; i < tallstrenger.length; i++) {
        //         int tmp = Integer.parseInt(tallstrenger[i]);
        //         System.out.println("Tallet er: " + tmp);
        //     }
        // }
        // catch (NumberFormatException e) {
        //     System.out.println("Kan ikke konvertere element til int.");
        // }
        
        // c)
        //Forsoeker aa konvertere alle strengene til heltall og skrive ut
        for (int i = 0; i < tallstrenger.length; i++) {
            try {
                int tmp = Integer.parseInt(tallstrenger[i]);
                System.out.println("Tallet er: " + tmp);
            }
            catch (NumberFormatException e) {
                System.out.println("Kan ikke konvertere element til int: " + tallstrenger[i]);
            }
            
        }
    }
}
