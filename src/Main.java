import java.util.*;

// maine class to run and test the code.
class Main {
    public static void main(String[] args) {

        String start_city = "Akron OH";

        List<String> attractions = new ArrayList<String>();
        attractions.add("Rock and Roll Hall of Fame");
        attractions.add("Cedar Point");

        String end_city = "Toledo OH";

        List<String> path = new Route().route(start_city, end_city, attractions);

        for (String city : path) {
            System.out.print(city + "\t\t");
        }
        System.out.println();
    }
}