package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;

        System.out.println("a == b = " + (a == b));
        TestAddress address = new TestAddress("city", "Street", "10000");
        TestAddress address1 = new TestAddress("city", "Street", "10000");
        System.out.println("address == address1= " + (address == address1));
        System.out.println("address equals address1 " + (address.equals(address1)));
    }
}
