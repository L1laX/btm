package classes;

import java.util.List;

public class PathResult {
  private List<String> Path;
  private int Price;
  private int Interchange;
  final private int totalStation;

  public PathResult(List<String> path, int price, int Interchange) {
    this.Path = path;
    this.Price = price;
    this.Interchange = Interchange;
    this.totalStation = path.size() - Interchange;
  }

  public int getTotalStation() {
    return Path.size() - Interchange;
  }

  public List<String> getPath() {
    return Path;
  }

  public void setPath(List<String> path) {
    Path = path;
  }

  public int getPrice() {
    return Price;
  }

  public void setPrice(int price) {
    Price = price;
  }

  public int getInterchange() {
    return Interchange;
  }

  public int getStation() {
    return Path.size() - Interchange;
  }

  public void setInterchange(int interchange) {
    this.Interchange = interchange;
  }

  @Override
  public String toString() {
    return "PathResult [Path=" + Path + ", Price=" + Price + ", Interchange=" + Interchange + ", totalStation="
        + totalStation + "]";
  }

}
