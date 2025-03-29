package classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Dikstra {

  private final PathMap pathMap;

  public Dikstra(PathMap pathMap) {
    this.pathMap = pathMap;
  }

  public List<PathResult> findShortestPathsByInterchange(String source, String target) {
    List<PathResult> result = new ArrayList<>();

    // Check if source and target are the same
    if (source.equals(target)) {
      return result;
    }

    PathResult shortestPath = findShortestPath(source, target, new ArrayList<>());
    if (shortestPath == null || shortestPath.getPath().isEmpty()) {
      return result; // No path found
    }

    result.add(shortestPath);

    List<String> prevPath = shortestPath.getPath();

    // loop to find interchange to find new path
    for (int i = 0; i < prevPath.size() - 1; i++) {
      // Station currentStation = pathMap.getStation(prevPath.get(i));
      // if (currentStation.getIsInterChange()) {
      // if find interchange, find new path
      String spurNode = prevPath.get(i);
      // Skip if interchange station is the target
      if (spurNode.equals(target)) {
        continue;
      }

      // Save original paths to restore later
      List<String> originalPaths = new ArrayList<>(pathMap.getPath(spurNode));

      // Temporarily modify the path map
      List<String> newPaths = new ArrayList<>(originalPaths);
      newPaths.remove(prevPath.get(i + 1));
      pathMap.removePath(spurNode);
      pathMap.addPath(spurNode, newPaths);

      // Find path from interchange to target
      PathResult partialPath = findShortestPath(spurNode, target, prevPath.subList(0, i));

      // Only proceed if we found a valid path that's not just the station itself
      if (partialPath != null && partialPath.getPath().size() > 1) {
        // Find path from source to interchange
        List<String> pathToInterchange = new ArrayList<>(prevPath.subList(0, i + 1));

        // Combine paths: source to interchange + interchange to target
        List<String> completePath = new ArrayList<>(pathToInterchange);
        // Remove the interchange station from the second path to avoid duplication
        completePath.addAll(partialPath.getPath().subList(1, partialPath.getPath().size()));

        // Create a new PathResult with the complete path
        PathResult alternativePath = new PathResult(completePath, 0, partialPath.getInterchange());

        // Only add if it's different from existing paths
        if (!containsPath(result, alternativePath.getPath())) {
          result.add(alternativePath);
        }

        // Restore original paths
        pathMap.removePath(spurNode);
        pathMap.addPath(spurNode, originalPaths);
      }

      // }
    }

    // Sort paths by interchange count
    return result;
  }

  // Helper method to check if a list of paths already contains a specific path
  private boolean containsPath(List<PathResult> pathResults, List<String> path) {
    for (PathResult pr : pathResults) {
      if (pr.getPath().equals(path)) {
        return true;
      }
    }
    return false;
  }

  private PathResult findShortestPath(String source, String target, List<String> prevPath) {
    Set<String> allStations = getAllStationNames();
    // Priority queue to store vertices that are being processed, with their paths
    // and interchange counts
    PriorityQueue<NodeWithPath> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));

    // Map to store the shortest distance to each node
    Map<String, Integer> distances = new HashMap<>();

    // Set to keep track of processed nodes
    Set<String> processed = new HashSet<>();
    processed.addAll(prevPath);

    // Initialize distances with all stations from the map
    for (String station : allStations) {
      distances.put(station, Integer.MAX_VALUE);
    }

    // Distance to source is 0
    distances.put(source, 0);

    // Create initial path with just the source
    List<String> initialPath = new ArrayList<>();
    initialPath.add(source);

    // Add source to priority queue with initial path and 0 interchanges
    pq.add(new NodeWithPath(source, 0, initialPath, 0));

    // Process nodes
    while (!pq.isEmpty()) {
      NodeWithPath current = pq.poll();
      String currentStation = current.name;

      // If we've reached the target, we're done
      if (currentStation.equals(target)) {
        return new PathResult(current.path, 0, current.interchanges);
      }

      // Skip if already processed
      if (processed.contains(currentStation)) {
        continue;
      }

      processed.add(currentStation);

      // Process all neighbors
      List<String> neighbors = pathMap.getPath(currentStation);
      if (neighbors == null)
        continue;

      for (String neighbor : neighbors) {
        // Skip if already processed
        if (processed.contains(neighbor)) {
          continue;
        }

        // Calculate new distance (each edge has weight 1)
        int newDistance = distances.get(currentStation) + 1;

        // Check if different line types (for interchange counting)
        int newInterchanges = current.interchanges;

        if (pathMap.getStation(currentStation).getType() != null && pathMap.getStation(neighbor) != null
            && !pathMap.getStation(currentStation).getType().equals(pathMap.getStation(neighbor).getType())
            && !"CEN".equals(currentStation) && !"CEN".equals(neighbor)) {
          newDistance += 1;
          newInterchanges += 1;
        }

        // Update if new distance is shorter
        if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
          distances.put(neighbor, newDistance);

          // Create new path by copying current path and adding neighbor
          List<String> newPath = new ArrayList<>(current.path);

          newPath.add(neighbor);

          // Add to priority queue with updated path and interchange count
          pq.add(new NodeWithPath(neighbor, newDistance, newPath, newInterchanges));
        }
      }
    }

    return null; // No path found
  }

  private Set<String> getAllStationNames() {
    Set<String> stations = new HashSet<>();
    // Start with a few known stations and expand from there
    String startStation = "CEN"; // Central station as a starting point
    Queue<String> queue = new LinkedList<>();
    queue.add(startStation);

    while (!queue.isEmpty()) {
      String current = queue.poll();
      if (stations.contains(current)) {
        continue;
      }
      stations.add(current);

      List<String> neighbors = pathMap.getPath(current);
      if (neighbors != null) {
        for (String neighbor : neighbors) {
          if (!stations.contains(neighbor)) {
            queue.add(neighbor);
          }
        }
      }
    }
    return stations;
  }

  // Helper class to store node with its path and interchange count
  private static class NodeWithPath {
    String name;
    int distance;
    List<String> path;
    int interchanges;

    NodeWithPath(String name, int distance, List<String> path, int interchanges) {
      this.name = name;
      this.distance = distance;
      this.path = path;
      this.interchanges = interchanges;
    }
  }
}

// private List<String> findByDijkstra(String current, String target,
// List<String> prevPath, Set<String> process) {
// // Base case: If current equals target, we found the path
// if (current.equals(target)) {
// prevPath.add(target);
// return prevPath;
// }

// // Mark the current node as processed
// process.add(current);

// // Get neighbors of the current node
// List<String> neighbors = pathMap.getPath(current);
// if (neighbors == null || neighbors.isEmpty()) {
// return null;
// }

// // Create a map to store distances to each neighbor
// Map<String, Integer> distances = new HashMap<>();

// // Calculate initial distances to all neighbors (all have distance 1 in this
// // graph)
// if (distances.isEmpty()) {
// return null;
// }

// for (String neighbor : neighbors) {
// if (!process.contains(neighbor)) {
// int distance = 1;
// if
// (pathMap.getStation(current).getType().equals(pathMap.getStation(neighbor).getType()))
// {
// distance += 1;
// }
// distances.put(neighbor, distance);
// }
// }

// // If no unprocessed neighbors, return null

// // Find the neighbor with minimum distance
// List<String> shortestPath = null;
// int minDistance = Integer.MAX_VALUE;

// // Process neighbors in order of increasing distance (Dijkstra's key
// property)
// while (!distances.isEmpty()) {
// // Find the neighbor with minimum distance
// String nextNode = null;
// int nextDistance = Integer.MAX_VALUE;

// for (Map.Entry<String, Integer> entry : distances.entrySet()) {

// if (entry.getValue() < nextDistance) {
// nextDistance = entry.getValue();
// nextNode = entry.getKey();
// }
// }

// // Remove the node with minimum distance
// distances.remove(nextNode);

// // Skip if already processed
// if (process.contains(nextNode)) {
// continue;
// }

// // Recursively find path from this neighbor to target
// List<String> path = findByDijkstra(nextNode, target, new ArrayList<>(), new
// HashSet<>(process));

// // If path is found and it's shorter than current shortest path
// if (path != null) {
// int pathDistance = path.size();
// if (pathDistance < minDistance) {
// minDistance = pathDistance;
// shortestPath = path;
// }
// }
// }

// // If a path was found, add the current node to the beginning and return
// if (shortestPath != null) {
// shortestPath.add(0, current);
// return shortestPath;
// }

// return null;
// }

// List<String> prevPath, Set<String> process) {
// // Base case: If current equals target, we found the path
// if (current.equals(target)) {
// prevPath.add(target);
// return prevPath;
// }

// // Mark the current node as processed
// process.add(current);

// // Get neighbors of the current node
// List<String> neighbors = pathMap.getPath(current);
// if (neighbors == null || neighbors.isEmpty()) {
// return null;
// }

// // Create a map to store distances to each neighbor
// Map<String, Integer> distances = new HashMap<>();

// // Calculate initial distances to all neighbors (all have distance 1 in this
// // graph)
// if (distances.isEmpty()) {
// return null;
// }

// for (String neighbor : neighbors) {
// if (!process.contains(neighbor)) {
// int distance = 1;
// if
// (pathMap.getStation(current).getType().equals(pathMap.getStation(neighbor).getType()))
// {
// distance += 1;
// }
// distances.put(neighbor, distance);
// }
// }

// // If no unprocessed neighbors, return null

// // Find the neighbor with minimum distance
// List<String> shortestPath = null;
// int minDistance = Integer.MAX_VALUE;

// // Process neighbors in order of increasing distance (Dijkstra's key
// property)
// while (!distances.isEmpty()) {
// // Find the neighbor with minimum distance
// String nextNode = null;
// int nextDistance = Integer.MAX_VALUE;

// for (Map.Entry<String, Integer> entry : distances.entrySet()) {

// if (entry.getValue() < nextDistance) {
// nextDistance = entry.getValue();
// nextNode = entry.getKey();
// }
// }

// // Remove the node with minimum distance
// distances.remove(nextNode);

// // Skip if already processed
// if (process.contains(nextNode)) {
// continue;
// }

// // Recursively find path from this neighbor to target
// List<String> path = findByDijkstra(nextNode, target, new ArrayList<>(), new
// HashSet<>(process));

// // If path is found and it's shorter than current shortest path
// if (path != null) {
// int pathDistance = path.size();
// if (pathDistance < minDistance) {
// minDistance = pathDistance;
// shortestPath = path;
// }
// }
// }

// // If a path was found, add the current node to the beginning and return
// if (shortestPath != null) {
// shortestPath.add(0, current);
// return shortestPath;
// }

// return null;
// }
