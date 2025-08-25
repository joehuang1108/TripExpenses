import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        // You went on trip with 4 other friends (total 5 ppl)
        // Each day everyone forgot to bring their wallet except ONE person
        // and that person paid for that day
        // Scenario repeats for the rest of the trip
        // After a 5 day trip, calculate the total amount of money 
        // each person owe to each and every one

        List<String> friends = Arrays.asList("Alice", "Bob", "Carol", "Dave", "Eve");
        String[] payers = {"Alice", "Bob", "Bob", "Carol", "Alice"};
        double[] amounts = {150.0, 200.0, 180.0, 220.0, 250.0};

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

        for(int day = 0; day < 5; day++){
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



        


    }
}
