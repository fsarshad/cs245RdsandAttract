import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Route {

    private final HashMap<String, String> attractionCityMap;
    private final HashMap<String, Integer> cityMap;
    private final List<String> reverseCityMap;
    // maximum number of unique cities
    private int N;
    // adjacency matrix
    int[][] graph;


    public Route() {
        // Route private HashMap<String, String> attractionCityMap
        attractionCityMap = new HashMap<String, String>();
        // Route private HashMap<String, Integer> cityMap
        cityMap = new HashMap<String, Integer>();
        // Route private List<String> reverseCityMap
        reverseCityMap = new ArrayList<>();
        N = -1;
        // created a try and catch which contains file reader for attraction and put a while loop inside the try to
        // read the file for attraction.
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader("attraction.csv"));
            while ((line = br.readLine()) != null) {
                String[] attraction = line.split(",");
                // Route private HashMap<String, String> attractionCityMap
                attractionCityMap.put(attraction[0], attraction[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // created a try and catch which contains file reader for roads and put a while loop inside the try to
        // read the file for roads.
        try {
            String line = "";
            int index = 0;
            BufferedReader br = new BufferedReader(new FileReader("roads.csv"));
            while ((line = br.readLine()) != null) {
                String[] roads = line.split(",");
                // if city maps gets roads 0 than I set it equal to null
                if (cityMap.get(roads[0]) == null) {
                    cityMap.put(roads[0], index);
                    // Route private List<String> reverseCityMap ,
                    reverseCityMap.add(roads[0]);
                    index += 1;
                }
                if (cityMap.get(roads[1]) == null) {
                    cityMap.put(roads[1], index);
                    reverseCityMap.add(roads[1]);
                    index += 1;
                }
            }
            N = index;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // adjacency matrix, max number of cities "N"
        graph = new int[N][N];

        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader("roads.csv"));
            while ((line = br.readLine()) != null) {
                String[] roads = line.split(",");
                // Route int[][] graph                    // Route private HashMap<String, Integer> cityMap
                graph[cityMap.get(roads[0])][cityMap.get(roads[1])] = Integer.valueOf(roads[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // The String class represents character strings. All string literals in Java programs, such as "abc",
// are implemented as instances of this class.
    public List<String> route(String start, String end, List<String> attractions) {
        // Constructs an empty list with an initial capacity of ten.
        List<String> output = new ArrayList<String>();
        // Constructs an empty list with an initial capacity of ten.
        List<String> cities = new ArrayList<String>();
        // List<String> attractions
        for (String attraction : attractions)
            // List<String> cities = new ArrayList<String>()
            // Route private HashMap<String, String> attractionCityMap
            cities.add(attractionCityMap.get(attraction));

        // gets the starting city map and the ending city
        int startIndex = cityMap.get(start),
                EndIndex = cityMap.get(end);
        // cost, shortest path using floydwarshal method
        int[][] cost = ShortestPath.floydWarshal(graph);
        // loops through the cityMap and gets cities.
        List<Integer> CityIndices = new ArrayList<Integer>();
        for(int i = 0; i < cities.size(); i++)
            CityIndices.add(cityMap.get(cities.get(i)));
        // permutation to generate cityindices
        List<List<Integer>> permutations = Permutation.generate(CityIndices);

        List<Integer> shortestPath = new ArrayList<Integer>();
        int CostOfShortestPath = Integer.MAX_VALUE;
        boolean found_path = false;
        for (List<Integer> permutation : permutations) {
            List<Integer> path = new ArrayList<Integer>();
            path.add(startIndex);
            for (Integer index : permutation) {
                path.add(index);
            }
            // Appends the specified element to the end of this list, int end_index = cityMap.get(end)
            path.add(EndIndex);

            // intialize the cost of current path
            int costOfCurrentPath = 0;
            // broken path is false
            boolean brokenPath = false;
            // path is cost path get i - 1 path . get is i equals shortest path . infinity
            for (int i = 1; i < path.size(); i++) {
                if (cost[path.get(i-1)][path.get(i)] == ShortestPath.INFINITY) {
                    brokenPath = true;
                    break;
                }
                // cost of current path is cost path get i - 1 path . get is i
                costOfCurrentPath += cost[path.get(i-1)][path.get(i)];
            }
            //  not broken path, shortest path = path and found path is true
            if (!brokenPath) {
                if (costOfCurrentPath < CostOfShortestPath) {
                    CostOfShortestPath = costOfCurrentPath;
                    shortestPath = path;
                    found_path = true;
                }
            }
        }
        // path not found return output
        if (!found_path) return output;
        // shortest path size, using dijkstra
        for (int i = 1; i < shortestPath.size(); i++) {
            List<Integer> shortest_route = ShortestPath.dijkstra(graph, shortestPath.get(i-1), shortestPath.get(i));
            if (i == 1) output.add(reverseCityMap.get(shortestPath.get(i-1)));
            // shortest route size
            for (int j = 2; j < shortest_route.size(); j++) {
                output.add(reverseCityMap.get(shortest_route.get(j)));
            }
        }

        return output;
    }
}