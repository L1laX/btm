package classes;

import java.util.HashMap;
import java.util.List;

public class PathMap {
  private HashMap<String, Station> stations;
  private HashMap<String, List<String>> path;

  public PathMap() {
    this.stations = new HashMap<>();
    this.path = new HashMap<>();
    initStationToMap();
  }

  public void addStationAndPath(Station station, List<String> path) {
    this.stations.put(station.getName(), station);
    this.path.put(station.getName(), path);
  }

  public Station getStation(String name) {
    return this.stations.get(name);
  }

  public List<String> getPath(String name) {
    return this.path.get(name);
  }

  public void setPath(HashMap<String, List<String>> path) {
    this.path = path;
  }

  public void setStations(HashMap<String, Station> stations) {
    this.stations = stations;
  }

  public void removeStation(String name) {
    this.stations.remove(name);
  }

  public void addPath(String name, List<String> path) {
    this.path.put(name, path);
  }

  public void removePath(String name) {
    this.path.remove(name);
  }

  private void initStationToMap() {
    // Central Station (Hub)
    this.addStationAndPath(new Station("CEN", "CEN", false), List.of("S1", "W1", "E1", "N1"));

    // BTS Silom Line (S)
    this.addStationAndPath(new Station("S1", "BTS-S", false), List.of("CEN", "S2"));
    this.addStationAndPath(new Station("S2", "BTS-S", false), List.of("S1", "S3", "BL26"));
    this.addStationAndPath(new Station("S3", "BTS-S", true), List.of("S2", "S4", "EX1"));
    this.addStationAndPath(new Station("S4", "BTS-S", false), List.of("S3", "S5"));
    this.addStationAndPath(new Station("S5", "BTS-S", false), List.of("S4", "S6"));
    this.addStationAndPath(new Station("S6", "BTS-S", false), List.of("S5", "S7"));
    this.addStationAndPath(new Station("S7", "BTS-S", true), List.of("S6", "S8", "G1"));
    this.addStationAndPath(new Station("S8", "BTS-S", false), List.of("S7", "S9"));
    this.addStationAndPath(new Station("S9", "BTS-S", false), List.of("S8", "S10"));
    this.addStationAndPath(new Station("S10", "BTS-S", true), List.of("S9", "S11", "EX12"));
    this.addStationAndPath(new Station("S11", "BTS-S", false), List.of("S10", "S12"));
    this.addStationAndPath(new Station("S12", "BTS-S", true), List.of("S11", "BL34"));

    // BTS Sukhumvit Line (E)
    this.addStationAndPath(new Station("E1", "BTS-E", false), List.of("CEN", "E2"));
    this.addStationAndPath(new Station("E2", "BTS-E", false), List.of("E1", "E3"));
    this.addStationAndPath(new Station("E3", "BTS-E", false), List.of("E2", "E4"));
    this.addStationAndPath(new Station("E4", "BTS-E", true), List.of("E3", "E5", "BL22"));
    this.addStationAndPath(new Station("E5", "BTS-E", false), List.of("E4", "E6"));
    this.addStationAndPath(new Station("E6", "BTS-E", true), List.of("E5", "E7"));
    this.addStationAndPath(new Station("E7", "BTS-E", true), List.of("E6", "E8"));
    this.addStationAndPath(new Station("E8", "BTS-E", false), List.of("E7", "E9"));
    this.addStationAndPath(new Station("E9", "BTS-E", true), List.of("E8", "E10"));
    this.addStationAndPath(new Station("E10", "BTS-E", false), List.of("E9", "E11"));
    this.addStationAndPath(new Station("E11", "BTS-E", false), List.of("E10", "E12"));
    this.addStationAndPath(new Station("E12", "BTS-E", false), List.of("E11", "E13"));
    this.addStationAndPath(new Station("E13", "BTS-E", false), List.of("E12", "E14"));
    this.addStationAndPath(new Station("E14", "BTS-E", false), List.of("E13", "E15"));
    this.addStationAndPath(new Station("E15", "BTS-E", false), List.of("E14", "E16", "YL23"));

    // BTS Northern Line (N)
    this.addStationAndPath(new Station("N1", "BTS-N", false), List.of("CEN", "N2"));
    this.addStationAndPath(new Station("N2", "BTS-N", true), List.of("N1", "N3", "A8"));
    this.addStationAndPath(new Station("N3", "BTS-N", false), List.of("N2", "N4"));
    this.addStationAndPath(new Station("N4", "BTS-N", false), List.of("N3", "N5"));
    this.addStationAndPath(new Station("N5", "BTS-N", false), List.of("N4", "N6"));
    this.addStationAndPath(new Station("N6", "BTS-N", false), List.of("N5", "N7"));
    this.addStationAndPath(new Station("N7", "BTS-N", false), List.of("N6", "N8"));
    this.addStationAndPath(new Station("N8", "BTS-N", true), List.of("N7", "N9", "BL13"));
    this.addStationAndPath(new Station("N9", "BTS-N", true), List.of("N10", "BL14", "N8"));

    // Western Line (W)
    this.addStationAndPath(new Station("W1", "BTS-W", false), List.of("CEN"));

    // MRT Blue Line (BL)
    this.addStationAndPath(new Station("BL1", "MRT-BL", true), List.of("BL2", "BL32", "BL33"));
    this.addStationAndPath(new Station("BL2", "MRT-BL", false), List.of("BL1", "BL3"));
    this.addStationAndPath(new Station("BL3", "MRT-BL", false), List.of("BL2", "BL4"));
    this.addStationAndPath(new Station("BL4", "MRT-BL", false), List.of("BL3", "BL5"));
    this.addStationAndPath(new Station("BL5", "MRT-BL", false), List.of("BL4", "BL6"));
    this.addStationAndPath(new Station("BL6", "MRT-BL", false), List.of("BL5", "BL7"));
    this.addStationAndPath(new Station("BL7", "MRT-BL", false), List.of("BL6", "BL8"));
    this.addStationAndPath(new Station("BL8", "MRT-BL", false), List.of("BL7", "BL9"));
    this.addStationAndPath(new Station("BL9", "MRT-BL", false), List.of("BL8", "BL10"));
    this.addStationAndPath(new Station("BL10", "MRT-BL", true), List.of("BL9", "BL11", "PP1"));
    this.addStationAndPath(new Station("BL11", "MRT-BL", false), List.of("BL10", "BL12"));
    this.addStationAndPath(new Station("BL12", "MRT-BL", false), List.of("BL11", "BL13"));
    this.addStationAndPath(new Station("BL13", "MRT-BL", true), List.of("BL12", "BL14", "N8"));
    this.addStationAndPath(new Station("BL14", "MRT-BL", true), List.of("BL13", "BL15", "N9"));
    this.addStationAndPath(new Station("BL15", "MRT-BL", true), List.of("BL14", "BL16", "YL1"));
    this.addStationAndPath(new Station("BL16", "MRT-BL", false), List.of("BL15", "BL17"));
    this.addStationAndPath(new Station("BL17", "MRT-BL", false), List.of("BL16", "BL18"));
    this.addStationAndPath(new Station("BL18", "MRT-BL", false), List.of("BL17", "BL19"));
    this.addStationAndPath(new Station("BL19", "MRT-BL", false), List.of("BL18", "BL20"));
    this.addStationAndPath(new Station("BL20", "MRT-BL", false), List.of("BL19", "BL21"));
    this.addStationAndPath(new Station("BL21", "MRT-BL", true), List.of("BL20", "BL22", "A8"));
    this.addStationAndPath(new Station("BL22", "MRT-BL", true), List.of("BL21", "BL23", "E4"));
    this.addStationAndPath(new Station("BL23", "MRT-BL", false), List.of("BL22", "BL24"));
    this.addStationAndPath(new Station("BL24", "MRT-BL", false), List.of("BL23", "BL25"));
    this.addStationAndPath(new Station("BL25", "MRT-BL", false), List.of("BL24", "BL26"));
    this.addStationAndPath(new Station("BL26", "MRT-BL", true), List.of("BL25", "BL27", "S2"));
    this.addStationAndPath(new Station("BL27", "MRT-BL", false), List.of("BL26", "BL28"));
    this.addStationAndPath(new Station("BL28", "MRT-BL", false), List.of("BL27", "BL29"));
    this.addStationAndPath(new Station("BL29", "MRT-BL", false), List.of("BL28", "BL30"));
    this.addStationAndPath(new Station("BL30", "MRT-BL", false), List.of("BL29", "BL31"));
    this.addStationAndPath(new Station("BL31", "MRT-BL", false), List.of("BL30", "BL32"));
    this.addStationAndPath(new Station("BL32", "MRT-BL", false), List.of("BL31", "BL1"));
    this.addStationAndPath(new Station("BL33", "MRT-BL", false), List.of("BL34", "BL1"));
    this.addStationAndPath(new Station("BL34", "MRT-BL", true), List.of("BL33", "BL35", "S12"));
    this.addStationAndPath(new Station("BL35", "MRT-BL", false), List.of("BL34", "BL36"));
    this.addStationAndPath(new Station("BL36", "MRT-BL", false), List.of("BL35", "BL37"));
    this.addStationAndPath(new Station("BL37", "MRT-BL", false), List.of("BL36", "BL38"));
    this.addStationAndPath(new Station("BL38", "MRT-BL", false), List.of("BL37", "BL39"));
    this.addStationAndPath(new Station("BL39", "MRT-BL", false), List.of("BL38"));

    // Airport Rail Link (A)
    this.addStationAndPath(new Station("A1", "ARL", false), List.of("A2"));
    this.addStationAndPath(new Station("A2", "ARL", false), List.of("A1", "A3"));
    this.addStationAndPath(new Station("A3", "ARL", false), List.of("A2", "A4"));
    this.addStationAndPath(new Station("A4", "ARL", true), List.of("A3", "A5", "Y11"));
    this.addStationAndPath(new Station("A5", "ARL", false), List.of("A4", "A6"));
    this.addStationAndPath(new Station("A6", "ARL", true), List.of("A5", "A7", "BL21"));
    this.addStationAndPath(new Station("A7", "ARL", false), List.of("A6", "A8"));
    this.addStationAndPath(new Station("A8", "ARL", false), List.of("A7", "N2"));

    // Yellow Line (YL)
    this.addStationAndPath(new Station("YL1", "MRT-YL", true), List.of("YL2", "BL15"));
    this.addStationAndPath(new Station("YL2", "MRT-YL", false), List.of("YL1", "YL3"));
    this.addStationAndPath(new Station("YL3", "MRT-YL", false), List.of("YL2", "YL4"));
    this.addStationAndPath(new Station("YL4", "MRT-YL", false), List.of("YL3", "YL5"));
    this.addStationAndPath(new Station("YL5", "MRT-YL", false), List.of("YL4", "YL6"));
    this.addStationAndPath(new Station("YL6", "MRT-YL", false), List.of("YL5", "YL7"));
    this.addStationAndPath(new Station("YL7", "MRT-YL", false), List.of("YL6", "YL8"));
    this.addStationAndPath(new Station("YL8", "MRT-YL", false), List.of("YL7", "YL9"));
    this.addStationAndPath(new Station("YL9", "MRT-YL", false), List.of("YL8", "YL10"));
    this.addStationAndPath(new Station("YL10", "MRT-YL", false), List.of("YL9", "YL11"));
    this.addStationAndPath(new Station("YL11", "MRT-YL", true), List.of("YL10", "YL12", "A4"));
    this.addStationAndPath(new Station("YL12", "MRT-YL", false), List.of("YL11", "YL13"));
    this.addStationAndPath(new Station("YL13", "MRT-YL", false), List.of("YL12", "YL14"));
    this.addStationAndPath(new Station("YL14", "MRT-YL", false), List.of("YL13", "YL15"));
    this.addStationAndPath(new Station("YL15", "MRT-YL", false), List.of("YL14", "YL16"));
    this.addStationAndPath(new Station("YL16", "MRT-YL", false), List.of("YL15", "YL17"));
    this.addStationAndPath(new Station("YL17", "MRT-YL", false), List.of("YL16", "YL18"));
    this.addStationAndPath(new Station("YL18", "MRT-YL", false), List.of("YL17", "Y19"));
    this.addStationAndPath(new Station("YL19", "MRT-YL", false), List.of("YL18", "YL20"));
    this.addStationAndPath(new Station("YL20", "MRT-YL", false), List.of("YL19", "YL21"));
    this.addStationAndPath(new Station("YL21", "MRT-YL", false), List.of("YL20", "YL22"));
    this.addStationAndPath(new Station("YL22", "MRT-YL", false), List.of("YL21", "YL23"));
    this.addStationAndPath(new Station("YL23", "MRT-YL", true), List.of("YL22", "E15"));

    // Purple Line (PP)
    this.addStationAndPath(new Station("PP1", "MRT-PP", false), List.of("PP2"));
    this.addStationAndPath(new Station("PP2", "MRT-PP", false), List.of("PP1", "PP3"));
    this.addStationAndPath(new Station("PP3", "MRT-PP", false), List.of("PP2", "PP4"));
    this.addStationAndPath(new Station("PP4", "MRT-PP", false), List.of("PP3", "PP5"));
    this.addStationAndPath(new Station("PP5", "MRT-PP", false), List.of("PP4", "PP6"));
    this.addStationAndPath(new Station("PP6", "MRT-PP", false), List.of("PP5", "PP7"));
    this.addStationAndPath(new Station("PP7", "MRT-PP", false), List.of("PP6", "PP8"));
    this.addStationAndPath(new Station("PP8", "MRT-PP", false), List.of("PP7", "PP9"));
    this.addStationAndPath(new Station("PP9", "MRT-PP", false), List.of("PP8", "PP10"));
    this.addStationAndPath(new Station("PP10", "MRT-PP", false), List.of("PP9", "PP11"));
    this.addStationAndPath(new Station("PP11", "MRT-PP", false), List.of("PP10", "PP12"));
    this.addStationAndPath(new Station("PP12", "MRT-PP", false), List.of("PP11", "PP13"));
    this.addStationAndPath(new Station("PP13", "MRT-PP", false), List.of("PP12", "PP14"));
    this.addStationAndPath(new Station("PP14", "MRT-PP", false), List.of("PP13", "PP15"));
    this.addStationAndPath(new Station("PP15", "MRT-PP", true), List.of("PP14", "PP16", "RW2"));
    this.addStationAndPath(new Station("PP16", "MRT-PP", true), List.of("PP15", "BL10"));

    // Red Line West (RW)
    this.addStationAndPath(new Station("RW1", "RW", true), List.of("RW2", "BL11", "BL12", "RN1"));
    this.addStationAndPath(new Station("RW2", "RW", true), List.of("RW1", "RW5", "PP15"));
    this.addStationAndPath(new Station("RW5", "RW", false), List.of("RW2", "RW6"));
    this.addStationAndPath(new Station("RW6", "RW", false), List.of("RW5"));

    // Red Line North (RN)
    this.addStationAndPath(new Station("RN1", "MRT-RN", true), List.of("RN2", "BL13"));
    this.addStationAndPath(new Station("RN2", "MRT-RN", false), List.of("RN1", "RN3"));
    this.addStationAndPath(new Station("RN3", "MRT-RN", false), List.of("RN2", "RN4"));
    this.addStationAndPath(new Station("RN4", "MRT-RN", false), List.of("RN3", "RN5"));
    this.addStationAndPath(new Station("RN5", "MRT-RN", false), List.of("RN4", "RN6"));
    this.addStationAndPath(new Station("RN6", "MRT-RN", true), List.of("RN5", "RN7", "PK14"));
    this.addStationAndPath(new Station("RN7", "MRT-RN", false), List.of("RN6", "RN8"));
    this.addStationAndPath(new Station("RN8", "MRT-RN", false), List.of("RN7", "RN9"));
    this.addStationAndPath(new Station("RN9", "MRT-RN", false), List.of("RN8"));

    // Gold
    this.addStationAndPath(new Station("G1", "Gold", true), List.of("G2", "S7"));
    this.addStationAndPath(new Station("G2", "Gold", false), List.of("G1", "G3"));
    this.addStationAndPath(new Station("G3", "Gold", false), List.of("G2"));

    // Extra
    this.addStationAndPath(new Station("EX1", "Extra", true), List.of("EX2"));
    this.addStationAndPath(new Station("EX2", "Extra", false), List.of("EX1", "EX3"));
    this.addStationAndPath(new Station("EX3", "Extra", false), List.of("EX2", "EX4"));
    this.addStationAndPath(new Station("EX4", "Extra", false), List.of("EX3", "EX5"));
    this.addStationAndPath(new Station("EX5", "Extra", false), List.of("EX4", "EX6"));
    this.addStationAndPath(new Station("EX6", "Extra", false), List.of("EX5", "EX7"));
    this.addStationAndPath(new Station("EX7", "Extra", false), List.of("EX6", "EX8"));
    this.addStationAndPath(new Station("EX8", "Extra", false), List.of("EX7", "EX9"));
    this.addStationAndPath(new Station("EX9", "Extra", false), List.of("EX8", "EX10"));
    this.addStationAndPath(new Station("EX10", "Extra", false), List.of("EX9", "EX11"));
    this.addStationAndPath(new Station("EX11", "Extra", false), List.of("EX10", "EX12"));
    this.addStationAndPath(new Station("EX12", "Extra", true), List.of("EX11", "S10"));
  }

  public void printAllStationsAndPaths() {
    for (Station station : this.stations.values()) {
      System.out.println(station);
      System.out.println(path.get(station.getName()));
      System.out.println("------------------");
    }
  }

  public int getNumberOfInterchange(List<String> path) {
    int count = 0;
    for (String station : path) {
      if (this.stations.get(station).getIsInterChange()) {
        count++;
      }
    }
    return count;
  }

}