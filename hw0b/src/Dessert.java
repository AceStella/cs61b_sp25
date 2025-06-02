public class Dessert {
    private int flavor;
    private int price;
    private static int num;

    public Dessert(int flavor, int price) {
        this.flavor = flavor;
        this.price = price;
        num++;
    }

    public void printDessert() {
        System.out.println(flavor + " " + price + " " + num);
    }

    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }
}
