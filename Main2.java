// import classes.PathMap;
// import classes.PathResult;
// import classes.Station;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;

// public class Main2 {

// public static void main(String[] args) {
// String start;
// String end;
// try (Scanner sc = new Scanner(System.in)) {
// System.out.print("Enter start station: ");
// start = sc.nextLine().trim().toUpperCase();
// System.out.print("Enter end station: ");
// end = sc.nextLine().trim().toUpperCase();
// }

// findPath(start, end);
// }

// public static void findPath(String start, String end) {
// // init value
// PathMap pathMap = new PathMap();
// List<PathResult> allPaths = new ArrayList<>();

// // find path
// List<String> currentPath = new ArrayList<>();
// Station startStation = pathMap.getStation(start);
// currentPath.add(startStation.getName());
// findPathHelper(start, end, currentPath, allPaths, pathMap);

// PathResult shortedPath = new PathResult(new ArrayList<>(), 0);
// PathResult cheepedPath = new PathResult(new ArrayList<>(), 0);
// for (PathResult p : allPaths) {
// if (shortedPath.getPath().isEmpty() || p.getPath().size() <
// shortedPath.getPath().size()) {
// shortedPath.setPath(p.getPath());
// shortedPath.setInterchange(pathMap.getNumberOfInterchange(p.getPath()));
// }
// // if (cheepedPath.getPath().isEmpty() || p.getCost() <
// cheepedPath.getCost()) {
// // cheepedPath.setPath(p.getPath());
// // }
// }

// System.out.println("Shortest Path: " + shortedPath);
// System.out.println("Cheapest Path: " + cheepedPath);
// }

// private static void findPathHelper(String current, String end, List<String>
// currentPath, List<PathResult> allPaths,
// PathMap pathMap) {
// // TOFIX: implement to yens shorted path type
// // System.out.println("Current: " + current + " | End: " + end + " | Current
// // Path: " + currentPath);
// if (current.equals(end)) {
// allPaths.add(new PathResult(new ArrayList<>(currentPath), 0));
// } else {
// Station currentStation = pathMap.getStation(current);
// if (currentStation == null) {
// return;
// }
// List<String> nextPaths = pathMap.getPath(current);
// for (String next : nextPaths) {
// if (!currentPath.contains(next)) {
// currentPath.add(next);
// findPathHelper(next, end, currentPath, allPaths, pathMap);
// currentPath.remove(currentPath.size() - 1);
// }
// }
// }
// }

// }
