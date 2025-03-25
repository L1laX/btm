// package classes;

// import java.util.*;

// public class YenAlgorithm {
// private final PathMap pathMap;

// public YenAlgorithm(PathMap pathMap) {
// this.pathMap = pathMap;
// }

// /**
// * Find k shortest paths from source to target using Yen's algorithm
// *
// * @param source Source station name
// * @param target Target station name
// * @param k Number of paths to find
// * @return List of PathResult objects representing the k shortest paths
// */
// public List<PathResult> findKShortestPaths(String source, String target, int
// k) {
// List<PathResult> result = new ArrayList<>();

// // Find the shortest path using Dijkstra's algorithm
// PathResult shortestPath = findShortestPath(source, target);
// if (shortestPath == null || shortestPath.getPath().isEmpty()) {
// return result; // No path found
// }

// // Add the shortest path to the result
// System.out.println("shortestPath == " + shortestPath);
// result.add(shortestPath);

// // Priority queue to store potential k-shortest paths
// PriorityQueue<PathResult> candidates = new PriorityQueue<>(
// Comparator.comparingInt(p -> p.getPath().size()));

// // Find k-1 more paths
// for (int i = 1; i < k; i++) {
// // Get the previous shortest path
// List<String> prevPath = result.get(i - 1).getPath();

// // For each node in the previous path except the last one
// for (int j = 0; j < prevPath.size() - 1; j++) {
// String spurNode = prevPath.get(j);
// List<String> rootPath = new ArrayList<>(prevPath.subList(0, j + 1));

// // Store the original graph state
// Map<String, List<String>> removedEdges = new HashMap<>();
// Set<String> removedNodes = new HashSet<>();

// // Remove the nodes in the root path from the graph
// for (PathResult p : result) {
// List<String> path = p.getPath();
// if (path.size() > j + 1 && rootPath.equals(path.subList(0, j + 1))) {
// String nextNode = path.get(j + 1);

// // Store and remove the edge
// if (!removedEdges.containsKey(spurNode)) {
// removedEdges.put(spurNode, new ArrayList<>(pathMap.getPath(spurNode)));
// }

// List<String> newPaths = new ArrayList<>(pathMap.getPath(spurNode));
// newPaths.remove(nextNode);
// pathMap.removePath(spurNode);
// pathMap.addPath(spurNode, newPaths);
// }
// }

// // Remove nodes in the root path except the spur node
// for (int n = 0; n < j; n++) {
// String nodeToRemove = rootPath.get(n);
// if (!removedNodes.contains(nodeToRemove)) {
// removedNodes.add(nodeToRemove);
// pathMap.removeStation(nodeToRemove);
// }
// }

// // Find the shortest path from the spur node to the target
// PathResult spurPath = findShortestPath(spurNode, target);
// System.out.println("Path line 78: " + spurNode);
// System.out.println("prev path: " + rootPath);
// System.out.println("spur path: " + spurPath);
// // If a path was found
// if (spurPath != null && !spurPath.getPath().isEmpty()) {
// // Create a new path by concatenating the root path and spur path
// List<String> totalPath = new ArrayList<>(rootPath);
// totalPath.addAll(spurPath.getPath().subList(1, spurPath.getPath().size()));

// PathResult totalPathResult = new PathResult(totalPath, 0);
// totalPathResult.setInterchange(pathMap.getNumberOfInterchange(totalPath));

// // Add the path to candidates if it's not already in the result
// boolean hasCycle = false;
// Set<String> stationSet = new HashSet<>();
// for (String station : totalPath) {
// if (!stationSet.add(station)) {
// hasCycle = true;
// break;
// }
// }

// // Only add cycle-free paths
// if (!hasCycle && !result.contains(totalPathResult) &&
// !candidates.contains(totalPathResult)) {
// candidates.add(totalPathResult);
// }
// }

// // Restore the graph
// for (Map.Entry<String, List<String>> entry : removedEdges.entrySet()) {
// pathMap.removePath(entry.getKey());
// pathMap.addPath(entry.getKey(), entry.getValue());
// }

// for (String node : removedNodes) {
// Station station = pathMap.getStation(node);
// if (station != null) {
// pathMap.addStationAndPath(station, pathMap.getPath(node));
// }
// }
// }

// // If there are no more candidates, break
// if (candidates.isEmpty()) {
// break;
// }

// // Add the shortest candidate to the result
// result.add(candidates.poll());
// }

// return result;
// }

// /**
// * Find the shortest path from source to target using Dijkstra's algorithm
// *
// * @param source Source station name
// * @param target Target station name
// * @return PathResult object representing the shortest path
// */
// private PathResult findShortestPath(String source, String target) {
// // Priority queue to store vertices that are being processed
// PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node ->
// node.distance));

// // Map to store the shortest distance to each node
// Map<String, Integer> distances = new HashMap<>();

// // Map to store the previous node in the shortest path
// Map<String, String> previous = new HashMap<>();

// // Set to keep track of processed nodes
// Set<String> processed = new HashSet<>();

// // Initialize distances with all stations from the map
// Set<String> allStations = getAllStationNames();
// for (String station : allStations) {
// distances.put(station, Integer.MAX_VALUE);
// }
// System.out.println("allStations: " + allStations);
// // Distance to source is 0
// distances.put(source, 0);
// pq.add(new Node(source, 0));

// // Process nodes
// while (!pq.isEmpty()) {
// Node current = pq.poll();
// String currentStation = current.name;
// System.out.println("current: " + currentStation);
// // If we've reached the target, we're done
// if (currentStation.equals(target)) {
// break;
// }

// // Skip if already processed
// if (processed.contains(currentStation)) {
// continue;
// }

// processed.add(currentStation);

// // Process all neighbors
// List<String> neighbors = pathMap.getPath(currentStation);
// if (neighbors == null)
// continue;

// for (String neighbor : neighbors) {
// // Skip if already processed
// if (processed.contains(neighbor)) {
// continue;
// }

// // Calculate new distance (each edge has weight 1)
// int newDistance = distances.get(currentStation) + 1;
// System.out.println("newDistance: " + newDistance);

// // Update if new distance is shorter
// if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
// distances.put(neighbor, newDistance);
// previous.put(neighbor, currentStation);
// pq.add(new Node(neighbor, newDistance));
// }
// }

// for (Node node : pq) {
// System.out.println(node.name + " - " + node.distance);
// }

// }

// // Reconstruct the path
// if (!previous.containsKey(target)) {
// return null; // No path found
// }

// List<String> path = new ArrayList<>();
// String current = target;

// while (current != null) {
// path.add(0, current);
// current = previous.get(current);
// }

// PathResult result = new PathResult(path, 0);
// result.setInterchange(pathMap.getNumberOfInterchange(path));

// return result;
// }

// /**
// * Get all station names from the PathMap
// *
// * @return Set of all station names
// */
// private Set<String> getAllStationNames() {
// // Collect all station names by iterating through the map
// // Start with a few known stations and expand from there
// Set<String> stations = new HashSet<>();
// // Start with a few known stations and expand from there
// String startStation = "CEN"; // Central station as a starting point
// Queue<String> queue = new LinkedList<>();
// queue.add(startStation);

// while (!queue.isEmpty()) {
// String current = queue.poll();
// if (stations.contains(current)) {
// continue;
// }
// stations.add(current);

// List<String> neighbors = pathMap.getPath(current);
// if (neighbors != null) {
// for (String neighbor : neighbors) {
// if (!stations.contains(neighbor)) {
// queue.add(neighbor);
// }
// }
// }
// }
// return stations;
// }

// /**
// * Node class for Dijkstra's algorithm
// */
// private static class Node {
// String name;
// int distance;

// Node(String name, int distance) {
// this.name = name;
// this.distance = distance;
// }
// }
// }

// // find shorted path from dijkstra
// // unlink n staion from n+1 station and find shorted path from dijkstra
// // add to result