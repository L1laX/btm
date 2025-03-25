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
    // Priority queue to store vertices that are being processed
    PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));

    // Map to store the shortest distance to each node
    Map<String, Integer> distances = new HashMap<>();

    // Map to store the previous node in the shortest path
    Map<String, String> previous = new HashMap<>();

    // Set to keep track of processed nodes
    Set<String> processed = new HashSet<>();
    processed.addAll(prevPath);

    int countInterChange = 0;

    // Initialize distances with all stations from the map
    for (String station : allStations) {
      distances.put(station, Integer.MAX_VALUE);
    }

    // Distance to source is 0
    distances.put(source, 0);
    pq.add(new Node(source, 0));

    // Process nodes
    while (!pq.isEmpty()) {
      Node current = pq.poll();
      String currentStation = current.name;
      // If we've reached the target, we're done
      if (currentStation.equals(target)) {
        break;
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

        // If the neighbor is of a different type than the target, add 1 to the distance to run the main path
        if (pathMap.getStation(target).getType() != null && pathMap.getStation(neighbor) != null
            && !pathMap.getStation(target).getType().equals(pathMap.getStation(neighbor).getType())) {
          newDistance += 1;
        }
        // Update if new distance is shorter
        if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
          distances.put(neighbor, newDistance);
          previous.put(neighbor, currentStation);
          pq.add(new Node(neighbor, newDistance));
        }
      }
    }

    // Reconstruct the path
    List<String> path = new ArrayList<>();
    String current = target;
    while (previous.containsKey(current)) {
      if (pathMap.getStation(current) != null
          && !pathMap.getStation(previous.get(current)).getType().equals(pathMap.getStation(current).getType())) {
        // incase Cen is not interchange
        if (!"CEN".equals(previous.get(current)) && !"CEN".equals(current)) {
          countInterChange++;
        }
      }
      path.add(0, current);
      current = previous.get(current);
    }
    path.add(0, source);
    pq.clear();
    return new PathResult(path, 0, countInterChange);
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

  private static class Node {
    String name;
    int distance;

    Node(String name, int distance) {
      this.name = name;
      this.distance = distance;
    }
  }

}
