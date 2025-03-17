import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    printShortWords(vertex, k, new HashSet<Vertex<String>>());
  }

  private static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return;

    if (vertex.data.length() < k) {
      System.out.println(vertex.data);
    }

    visited.add(vertex);

    for (var neighbor : vertex.neighbors) {
      printShortWords(neighbor, k, visited);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    return longestWord(vertex, "", new HashSet<Vertex<String>>());
  }

  private static String longestWord(Vertex<String> vertex, String longest, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return "";

    if (vertex.data.length() > longest.length()) longest = vertex.data;

    visited.add(vertex);

    for (var neighbor : vertex.neighbors) {
      String word = longestWord(neighbor, longest, visited);
      if (word.length() > longest.length()) longest = word;
    }

    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    printSelfLoopers(vertex, new HashSet<Vertex<T>>());
  }

  private static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> visited) {
    if (vertex == null || visited.contains(vertex)) return;

    if (vertex.neighbors != null && vertex.neighbors.contains(vertex)) {
      System.out.println(vertex.data);
    }

    visited.add(vertex);

    for (var neighbor : vertex.neighbors) {
      printSelfLoopers(neighbor, visited);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    return canReach(start, destination, new HashSet<Airport>());
  }

  private static boolean canReach(Airport location, Airport destination, Set<Airport> visited) {
    if (location == null || visited.contains(location)) return false;
    if (location == destination) return true;

    visited.add(location);

    for (var airport : location.getOutboundFlights()) {
      if (canReach(airport, destination, visited)) return true;
    }

    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> visited = new HashSet<>();
    unreachable(graph, starting, visited);

    Set<T> unreached = new HashSet<>();

    for (var vertex : graph.keySet()) {
      if (!visited.contains(vertex)) {
        unreached.add(vertex);
      }
    }
    return unreached;
  }

  private static <T> void unreachable(Map<T, List<T>> graph, T num, Set<T> visited) {
    if (visited.contains(num)) return;

    visited.add(num);

    for (var number : graph.getOrDefault(num, Collections.emptyList())) {
      unreachable(graph, number, visited);
    }

  }
}
