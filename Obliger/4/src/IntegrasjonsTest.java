public class IntegrasjonsTest {
    public static void main(String[] args) {
        Vanlig vanlig = new Vanlig("vanlig", 12, 0.2);
        Narkotisk narkotisk = new Narkotisk("narkotisk", 150, 0.1, 20);
        Vanedannende vanedannende = new Vanedannende("vanlig", 5, 0.5, 10);

        Lege lege = new Lege("lege");
        Spesialist spesialist = new Spesialist("spesialist", "abc2123");

        BlaaResept blaa = new BlaaResept(vanlig, lege,
                new Pasient("test", "00123013"), 10, 100);
        HvitResept hvit = new HvitResept(narkotisk, lege,
                new Pasient("test", "00123013"), 11, 20);
        MilResept mil = new MilResept(vanedannende, lege,
                new Pasient("test", "00123013"), 9);
        PResept p = new PResept(vanlig, spesialist,
                new Pasient("test", "00123013"), 15, 200);

        System.out.println(vanlig);
        System.out.println(narkotisk);
        System.out.println(vanedannende);
        System.out.println(lege);
        System.out.println(spesialist);
        System.out.println(blaa);
        System.out.println(hvit);
        System.out.println(mil);
        System.out.println(p);
    }
}
