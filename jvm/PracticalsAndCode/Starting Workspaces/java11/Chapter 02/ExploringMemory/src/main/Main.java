package main;


public class Main {
    public static void main(String[] args) {
        int localValue = 5;
        calculate(localValue);
        System.out.println(localValue);

        final Customer c = new Customer("John");
        // c = new Customer("Jn"); not works
        c.setName("Susan");
        System.out.println(c.getName());
        System.out.println(c.getName());
    }

    public static void calculate(int calcValue) {
        calcValue = calcValue * 1000;
    }

}

class Customer {
    private String name;
    
    Customer(String name) {
        this.name = name;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}