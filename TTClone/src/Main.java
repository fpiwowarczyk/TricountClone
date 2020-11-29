import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Billing bill = new Billing("Filip", "Maja", "Ola", "Dominika");

        bill.printBilling();

        bill.makePayment("Filip",10.0,"Ola");


        bill.makePayment("Maja", 10.0 ,"Dominika");


        bill.printBilling();

        bill.makePayment("Filip",10.0,"Maja");

        bill.printBilling();

    }

    static class Billing {
        private HashMap<String, Double> billing = new HashMap<String, Double>();

        public Billing(String... names) {
            for (String name : names) {
                billing.put(name, 0.0);
            }
        }
        public void addUser(String name) {
            billing.put(name, 0.0);
        }

        public void printBilling() {
            System.out.println("--------------------------");
            billing.forEach((k, v) -> {
                System.out.println(k + " have " + v + " money");
            });
            System.out.println("--------------------------");
        }

        public void makePayment(String name, Double value, String... debtors) {
            billing.forEach((user, money) -> {
                if (user.equals(name)) {
                    money += value;
                }
                if(debtors.length == 0){
                    money -= value/billing.size();
                } else {
                    for (String debtor : debtors) {
                        if (debtor.equals(user)) {
                            money -= value / debtors.length;
                        }
                    }
                }


                billing.put(user, money);
            });
        }
    }

}
