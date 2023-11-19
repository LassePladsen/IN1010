public class TestLegemiddel {
    public static void main(String[] args) {
        Vanlig vanlig = new Vanlig("vanlig", 10, 0.2);
        Vanedannende vanedannende = new Vanedannende("Vanedannende", 20, 0.8, 10);
        Narkotisk narkotisk = new Narkotisk("Narkotisk", 100, 0.2, 20);

        assert (0 == vanlig.hentId()) :
            "Vanlig id er ikke " + 0 + ", men " + vanlig.hentId();
        assert (1 == vanedannende.hentId()) :
            "Vanedannende id er ikke " + 1 + ", men " + vanedannende.hentId();
        assert (2 == narkotisk.hentId()) :
            "Narkotisk id er ikke " + 2 + ", men " + narkotisk.hentId();

        assert (vanlig.hentPris() == 10) :
            "Vanlig.hentPris() returnerer ikke riktig verdi";

        vanlig.settPris(5);
        assert (vanlig.hentPris() == 5) :
            "Vanlig.settPris() setter ikke riktig verdi";
    }
}
