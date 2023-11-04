public class Testing {
    public static void main(String[] args) {
        Narkotisk test = new Narkotisk("test", 20, 0.5, 5);
        System.out.println(test.id);
        Vanedannende test2 = new Vanedannende("test2", 10, 0.1, 9);
        System.out.println(test2.id);
        Vanlig test3 = new Vanlig("test3", 15, 0.9);
        System.out.println(test3.id);

        System.out.println(test);
        System.out.println(test2);
        System.out.println(test3);
    }
}
