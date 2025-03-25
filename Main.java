import classes.Dikstra;
import classes.PathMap;
import classes.PathResult;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    String start;
    String end;
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Enter start station: ");
      start = sc.nextLine().trim().toUpperCase();
      System.out.print("Enter end station: ");
      end = sc.nextLine().trim().toUpperCase();
    }

    findPath(start, end);
  }

  public static void findPath(String start, String end) {
    // init value
    PathMap pathMap = new PathMap();

    // Create Yen's algorithm instance
    // YenAlgorithm yenAlgorithm = new YenAlgorithm(pathMap);
    // Find k shortest paths (k=3 for example)
    // List<PathResult> kShortestPaths = yenAlgorithm.findKShortestPaths(start, end,
    // 3);

    Dikstra dikstra = new Dikstra(pathMap);
    List<PathResult> kShortestPaths = dikstra.findShortestPathsByInterchange(start, end);

    // Print all found paths
    System.out.println("\nAll paths found:");
    for (int i = 0; i < kShortestPaths.size(); i++) {
      System.out.println("Path " + (i + 1) + ": " + kShortestPaths.get(i));
    }

    // if (kShortestPaths.isEmpty()) {
    // System.out.println("No path found between " + start + " and " + end);
    // return;
    // }

    // // The first path is the shortest
    // PathResult shortestPath = kShortestPaths.get(0);

    // // Find the path with the least interchanges
    // PathResult leastInterchangePath = kShortestPaths.get(0);
    // for (PathResult path : kShortestPaths) {
    // if (path.getInterchange() < leastInterchangePath.getInterchange()) {
    // leastInterchangePath = path;
    // }
    // }

    // System.out.println("Shortest Path: " + shortestPath);
    // System.out.println("Path with least interchanges: " + leastInterchangePath);

    // // Print all found paths
    // System.out.println("\nAll paths found:");
    // for (int i = 0; i < kShortestPaths.size(); i++) {
    // System.out.println("Path " + (i + 1) + ": " + kShortestPaths.get(i));
    // }
  }
}
