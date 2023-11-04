public class TestLegemiddel {
    public static void main(String[] args) {
        Vanlig vanlig = new Vanlig("vanlig", 10, 0.2);
        Vanedannende vanedannende = new Vanedannende("Vanedannende", 20, 0.8, 10);
        Narkotisk narkotisk = new Narkotisk("Narkotisk", 100, 0.2, 20);

        assert (0 == vanlig.id) :
            "Vanlig id er ikke " + 0 + ", men " + vanlig.id;
        assert (1 == vanedannende.id) :
            "Vanedannende id er ikke " + 1 + ", men " + vanedannende.id;
        assert (2 == narkotisk.id) :
            "Narkotisk id er ikke " + 2 + ", men " + narkotisk.id;

        assert (vanlig.hentPris() == 10) :
            "Vanlig.hentPris() returnerer ikke riktig verdi";

        vanlig.settPris(5);
        assert (vanlig.hentPris() == 5) :
            "Vanlig.settPris() setter ikke riktig verdi";
    }
}
