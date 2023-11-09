public class TestResepter {
    public static void main(String[] args) {
        BlaaResept blaa = new BlaaResept(
            new Vanlig("blaa", 15, 0.2),
            new Lege("test"),
            0000,
            10,
            100
        );
        HvitResept hvit = new HvitResept(
            new Vanlig("hvit", 10, 0.5),
            new Lege("test"),
            0001,
            11,
            20
        );
        MilResept mil = new MilResept(
            new Vanlig("c", 1, 0.1),
            new Lege("test"),
            0003,
            9
        );
        PResept p = new PResept(
            new Vanlig("p", 2, 0.45),
            new Lege("test"),
            0004,
            15,
            200
        );

        assert (blaa.hentId() == 0) : "Feil id i blaa resept: " + blaa.hentId();
        assert (hvit.hentId() == 1) : "Feil id i hvit resept: " + hvit.hentId();
        assert (mil.hentId() == 2) : "Feil id i militaer resept: " + mil.hentId();
        assert (p.hentId() == 3) : "Feil id i p-resept: " + p.hentId();

        assert (blaa.farge().equals("Blaa")) : 
            "Feil farge i blaa resept: " + blaa.farge();
        assert (hvit.farge().equals("Hvit")) : 
           "Feil farge i hvit resept: " + hvit.farge();
        assert (mil.farge().equals("Hvit")) : 
            "Feil farge i mil resept: " + mil.farge();
        assert (p.farge().equals("Hvit")) : 
         "Feil farge i p-resept: " + p.farge();

        assert (blaa.prisAaBetale() == 25) : 
            "feil prisAaBetale() i blaa resept: " + blaa.prisAaBetale();
        assert (hvit.prisAaBetale() == 20) : 
            "feil prisAaBetale() i hvit resept: " + hvit.prisAaBetale();
        assert (mil.prisAaBetale() == 0) : 
            "feil prisAaBetale() i mil resept: " + mil.prisAaBetale();
        assert (p.prisAaBetale() == 200 - 108) : 
            "feil prisAaBetale() i p-resept: " + p.prisAaBetale();

        assert (mil.henttReit() == 3);

        System.out.println(blaa);
        System.out.println(hvit);
        System.out.println(mil);
        System.out.println(p);
    }   
}

