import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // You went on trip with 4 other friends (total 5 ppl)
        // Each day everyone forgot to bring their wallet except ONE person
        // and that person paid for that day
        // Scenario repeats for the rest of the trip
        // After a 5 day trip, calculate the total amount of money 
        // each person owe to each and every one


        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of friends: ");
        int numFriends = sc.nextInt();
        sc.nextLine();

        List<String> friends = new ArrayList<>(); // Empty arraylist
        for(int i = 0; i < numFriends; i++){
            System.out.print("Enter friend " + (i + 1) + " name: ");
            friends.add(sc.nextLine().trim());
        }

        System.out.print("Enter number of expense records: ");
        int numRecords = sc.nextInt();  // number of purchase entries
        sc.nextLine();

        String[] payers = new String[numRecords];
        double[] amounts = new double[numRecords];

        for (int i = 0; i < numRecords; i++) {
            System.out.println("Record " + (i + 1) + ":");
            System.out.print("  Who paid? ");
            payers[i] = sc.nextLine().trim();
            System.out.print("  How much? ");
            amounts[i] = sc.nextDouble();
            sc.nextLine(); // consume newline
        }


        // Day 1
        // Alice +120
        // Bob -30
        // Carol -30
        // Dave -30
        // Eve -30

        // Day 2
        // Alice +80    (-40)
        // Bob +130       (+160)
        // Carol -70     (-40)
        // Dave -70      (-40)
        // Eve -70       (-40)

        HashMap<String, Double> balances = new HashMap<>();
        for(String friend : friends){
            balances.put(friend, 0.0);
        }
        System.out.println(balances);

        for(int day = 0; day < numRecords; day++){
            // retrieve the total and the payer for each day
            String payer = payers[day];
            double total = amounts[day]; 
            double share = total / friends.size();

            for(String friend : friends){
                if(friend.equals(payer)){
                    balances.put(friend, balances.get(friend) + (total - share));
                }
                else{
                    balances.put(friend, balances.get(friend) - share);
                }
            }
        }

        System.out.println("Net Balances: ");
        for(String friend : friends){
            System.out.println(friend + " " + balances.get(friend));
        }

        System.out.println("Simplified Debts");
        simplifyDebts(balances);


        // Simplify Expenses 
        // End goal: The payers get their money back
        // Creditors: 
        // Debtors: 
        // Find the largest creditor, and smallest debtor and pay out (repeat)
        // Alice - Dave --> Alice: 0, Dave: 0
        // Bob - Eve --> Bob: 0, Eve: -20 
        // Alice 0.0
        // Bob 0.0
        // Carol 20.0
        // Dave 0.0
        // Eve -20.0
    }

    static void simplifyDebts(HashMap <String, Double> balances){
        // Find the largest creditor, and smallest debtor and pay out (repeat)
        PriorityQueue <Person> creditors = new PriorityQueue<>((a,b) -> Double.compare(b.amount, a.amount)); 
        PriorityQueue <Person> debtors = new PriorityQueue<>((a,b) -> Double.compare(a.amount, b.amount));
        
        // Entry - Key/value pair
        for(HashMap.Entry<String, Double> entry: balances.entrySet()){
            String name = entry.getKey();
            double amount = entry.getValue();
            if(amount > 0){
                creditors.offer(new Person(name, amount));
            }
            else if(amount < 0){
                debtors.offer(new Person(name, -amount));
            }
        }

        while(!creditors.isEmpty() && !debtors.isEmpty()){
            Person creditor = creditors.poll();
            Person debtor = debtors.poll();

            double min = Math.min(creditor.amount, debtor.amount);
            System.out.printf("%s owes %s: $%.2f\n", debtor.name, creditor.name, min);

            if(creditor.amount > min){
                creditors.offer(new Person(creditor.name, creditor.amount - min));
            }
            if(debtor.amount > min){
                debtors.offer(new Person(debtor.name, debtor.amount - min));
            }
        }
        

    }

    static class Person{
        String name;
        double amount;
        Person(String name, double amount){
            this.name = name;
            this.amount = amount;
        }
    }
}
